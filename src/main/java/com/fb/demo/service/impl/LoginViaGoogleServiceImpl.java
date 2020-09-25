package com.fb.demo.service.impl;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.GoogleDeveloperDetails;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.GoogleDeveloperDetailsNotFound;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.repository.GoogleDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.LoginViaGoogleService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginViaGoogleServiceImpl implements LoginViaGoogleService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private GoogleDeveloperDetailsRepository googleDeveloperDetailsRepository;

    @Override
    public String getRedirectUrl(String tenant) throws Exception {
        log.info(":::::Inside LoginViaGoogleSeriveImpl Class, getRedirectUrl method::::");
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails =
                        googleDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId());
        if (googleDeveloperDetails == null) {
            throw new GoogleDeveloperDetailsNotFound(
                            "GoogleDeveloperDetails not found for given tenant : " + tenant);
        }
        /* Import GroupId:com.google.api-client <artifactId>google-api-client</artifactId> : */
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                        new NetHttpTransport(), new JacksonFactory(),
                        googleDeveloperDetails.getClientId(),
                        googleDeveloperDetails.getClientSecret(), Arrays.asList("email", "profile"))
                                        .build();
        return flow.newAuthorizationUrl().setRedirectUri(googleDeveloperDetails.getRedirectUrl())
                        .build();
    }

    @Override
    public String getGoogleAccessToken(String code, String tenant) throws Exception {
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails =
                        googleDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId());
        if (googleDeveloperDetails == null) {
            throw new GoogleDeveloperDetailsNotFound(
                            "GoogleDeveloperDetails not found for given tenant : " + tenant);
        }
        GoogleAuthorizationCodeFlow flow =
                        new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
                                        new JacksonFactory(), googleDeveloperDetails.getClientId(),
                                        googleDeveloperDetails.getClientSecret(),
                                        Arrays.asList("email", "profile")).build();
        GoogleTokenResponse authorizationCode = flow.newTokenRequest(code)
                        .setGrantType("authorization_code")
                        .setRedirectUri(googleDeveloperDetails.getRedirectUrl()).execute();
        String googleAccessToken = authorizationCode.getIdToken();
        return googleAccessToken;
    }

}
