package com.fb.demo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.FbDeveloperDetails;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.FbDeveloperDetailsNotFoundException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.repository.FbDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.LoginViaFacebookService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginViaFacebookServiceImpl implements LoginViaFacebookService {

    @Autowired
    private FbDeveloperDetailsRepository fbDeveloperDetailsRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public String getRedirectUrl(String tenant) throws Exception {
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        if (tenantFromDB == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        FbDeveloperDetails fbDeveloperDetails =
                        fbDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId());
        if (fbDeveloperDetails == null) {
            throw new FbDeveloperDetailsNotFoundException("FbDeveloperDetails not found.");
        }
        String fbRedirectUri = "http://www.facebook.com/dialog/oauth?" + "client_id="
                        + fbDeveloperDetails.getClientId() + "&redirect_uri="
                        + URLEncoder.encode(fbDeveloperDetails.getRedirectUrl(), "UTF-8")
                        + "&scope=email";
        return fbRedirectUri;
    }

    @Override
    public String getFacebookAccessToken(String code, String tenant) throws Exception {
        String fbAccessToken = "";
        if ("".equals(fbAccessToken)) {
            URL fbGraphURL;
            URLConnection fbConnection;
            StringBuffer b;
            try {
                fbGraphURL = new URL(getFbGraphUrl(code, tenant));
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in = new BufferedReader(
                                new InputStreamReader(fbConnection.getInputStream()));
                b = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    b.append(inputLine + "\n");
                }
                in.close();
                fbAccessToken = b.toString();
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(fbAccessToken);
                for (Object obj : jsonObject.keySet()) {
                    String param = (String) obj;
                    if (param.equals("access_token")) {
                        fbAccessToken = (String) jsonObject.get(param);
                        break;
                    }
                }
            } catch (MalformedURLException ex) {
                log.error(":::::Url could not be formed:::::");
            } catch (final IOException ex) {
                log.error("::::Unable to connect to facebook:::::");
            } catch (final ParseException ex) {
                log.info(":::::Could not parse the jsonObject:::::");

            }
        }
        return fbAccessToken;
    }

    private String getFbGraphUrl(String code, String tenant) throws Exception {
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        if (tenantFromDB == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        FbDeveloperDetails fbDeveloperDetails =
                        fbDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId());
        if (fbDeveloperDetails == null) {
            throw new FbDeveloperDetailsNotFoundException("FbDeveloperDetails not found.");
        }
        String fbGraphUrl = "";
        try {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?" + "client_id="
                            + fbDeveloperDetails.getClientId() + "&redirect_uri="
                            + URLEncoder.encode(fbDeveloperDetails.getRedirectUrl(),
                                            "UTF-8")
                            + "&client_secret="
                            + fbDeveloperDetails.getClientSecret() + "&code="
                            + code;
        } catch (final UnsupportedEncodingException ex) {
            log.error(":::::Unable to encode the url:::::");
        }
        return fbGraphUrl;
    }
}
