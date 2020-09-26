package com.fb.demo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.FbDeveloperDetails;
import com.fb.demo.entity.GoogleDeveloperDetails;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.FbAccessTokenValidationException;
import com.fb.demo.exception.FbDeveloperDetailsNotFoundException;
import com.fb.demo.exception.GoogleDeveloperDetailsNotFound;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.repository.FbDeveloperDetailsRepository;
import com.fb.demo.repository.GoogleDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.ValidationAndVerificationOfATService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ValidationAndVerificationOfATServiceImpl
                implements ValidationAndVerificationOfATService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private FbDeveloperDetailsRepository fbDeveloperDetailsRepository;

    @Autowired
    private GoogleDeveloperDetailsRepository googleDeveloperDetailsRepository;

    @Value("${facebook.token.verification.url}")
    public String facebookTokenVerficationUrl;

    @Value("${facebook.dummy.access.token.generator.url}")
    public String facebookAccessTokenGeneratiorUrl;

    @Value("${facebook.dummy.access.token.verification.url}")
    public String facebookAccessTokenValidatorUrl;

    @Value("${google.token.verification.url1}")
    public String googleTokenVerificationUrl1;

    @Value("${gogole.token.verification.uril2}")
    public String googleTokenVerificationUrl2;


    @Override
    public void verifyAndValidateFacebookAccessToken(String accessToken, String tenant)
                    throws Exception {
        log.info(":::::AccessToken {} and Tenant {}", accessToken, tenant);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new InvalidInputException("Invalid accessToken.");
        }
        StringBuffer buffer;
        String details = null;
        try {
            log.info("Facebook Token Verification Url : {}", facebookTokenVerficationUrl);
            URL url = new URL(facebookTokenVerficationUrl + accessToken);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));
            buffer = new StringBuffer();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                buffer.append(inputLine);
            }
            bufferedReader.close();
            details = buffer.toString();
            log.info(":::::details {}", details);

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(details);
            for (Object obj : jsonObject.keySet()) {
                String param = (String) obj;
                if (param.equalsIgnoreCase("error")) {
                    throw new FbAccessTokenValidationException(
                                    (String) (((JSONObject) jsonObject.get(param)).get("message")));
                }
                if (param.equalsIgnoreCase("id")) {
                    if (verifiyFacebookAccessToken(accessToken, tenant)) {
                        return;
                    }
                }
            }
        } catch (final IOException ex) {
            log.error(":::::Unable to connect to the facebookTokenVerificationUrl:::::");
        }
    }

    private boolean verifiyFacebookAccessToken(String accessToken, String tenant) throws Exception {
        log.info("::::::Varifying input accesstoken with actual accessToken::::");
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        if (tenantFromDB == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        FbDeveloperDetails fbDeveloperDetails =
                        fbDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId());
        if (fbDeveloperDetails == null) {
            throw new FbDeveloperDetailsNotFoundException("FbDeveloperDetails not found.");
        }

        // Generating Actual Dummy Access Token Using clientId, Client Secret, and grant_type
        HttpResponse<JsonNode> jsonNode = Unirest.get(facebookAccessTokenGeneratiorUrl)
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .queryString("client_id", fbDeveloperDetails.getClientId())
                        .queryString("client_secret", fbDeveloperDetails.getClientSecret())
                        .queryString("grant_type", "client_credentials").asJson();

        org.json.JSONObject jsonObject = jsonNode.getBody().getObject();
        for (Object obj : jsonObject.keySet()) {
            String param = (String) obj;
            if (param.equalsIgnoreCase("error")) {
                throw new InvalidInputException("Invalid Input.");
            }
            if (param.equalsIgnoreCase("access_token")) {
                // Verifying the dummy access token with the input_access_token
                HttpResponse<JsonNode> finalJsonNode = Unirest.get(facebookAccessTokenValidatorUrl)
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .queryString("input_token", accessToken)
                                .queryString("access_token", jsonObject.get(param)).asJson();
                org.json.JSONObject finalJsonObject = finalJsonNode.getBody().getObject();
                for (Object object : finalJsonObject.keySet()) {
                    String key = (String) object;
                    if (key.equalsIgnoreCase("data")) {
                        org.json.JSONObject jsonData =
                                        (org.json.JSONObject) finalJsonObject.get(key);
                        if (!jsonData.isNull("error")) {
                            log.error("::::::Errro message {}", jsonData.get("message"));
                            return false;
                        }
                        for (Object finalObject : jsonData.keySet()) {
                            String finalParam = (String) finalObject;
                            if (finalParam.equalsIgnoreCase("app_id")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean verifyAndValidateGoogleAccessToken(String accessToken, String tenant)
                    throws Exception {
        log.info("::::::Varifying input accesstoken with actual accessToken::::");
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        if (tenantFromDB == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails =
                        googleDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId());
        if (googleDeveloperDetails == null) {
            throw new GoogleDeveloperDetailsNotFound(
                            "GoogleDeveloperDetails not found for the given tenant : "
                                            + tenantFromDB.getName());
        }
        String finalData = null;
        StringBuffer stringBuffer;
        try {
            URL url = new URL(googleTokenVerificationUrl2 + accessToken);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));
            stringBuffer = new StringBuffer();
            String inputLine;
            while ((inputLine = bufferReader.readLine()) != null) {
                stringBuffer.append(inputLine + "\n");
            }
            finalData = stringBuffer.toString();
            log.info("::::::finalData : {}", finalData);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(finalData);
            for (Object object : jsonObject.keySet()) {
                String param = (String) object;
                if (param.equalsIgnoreCase("azp") || param.equalsIgnoreCase("aup")) {
                    if (googleDeveloperDetails.getClientId().equals(jsonObject.get(param))) {
                        return true;
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
