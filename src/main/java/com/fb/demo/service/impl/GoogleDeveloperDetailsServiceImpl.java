package com.fb.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.GoogleDeveloperDetails;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.GoogleDevCreateRequest;
import com.fb.demo.model.response.GoogleDevCreateResponse;
import com.fb.demo.repository.GoogleDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.GoogleDeveloperDetailsService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GoogleDeveloperDetailsServiceImpl implements GoogleDeveloperDetailsService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private GoogleDeveloperDetailsRepository googleDeveloperDetailsRepository;

    @Override
    public GoogleDevCreateResponse saveGoogleDeveloperDetails(
                    GoogleDevCreateRequest googleDevCreateRequest, String tenantName)
                    throws Exception {
        log.info(":::::Inside GoogleDeveloperDetailsServiceImpl Class, saveGoogleDeveloperDetails method:::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails = new GoogleDeveloperDetails();
        googleDeveloperDetails.setTenant(tenant.getId());
        googleDeveloperDetails.setClientId(googleDevCreateRequest.getClientId());
        googleDeveloperDetails.setClientSecret(googleDevCreateRequest.getClientSecret());
        googleDeveloperDetails.setRedirectUrl(googleDevCreateRequest.getRedirectUrl());
        googleDeveloperDetailsRepository.save(googleDeveloperDetails);
        GoogleDevCreateResponse response = new GoogleDevCreateResponse();
        response.setId(googleDeveloperDetails.getId());
        response.setMsg("GoogleDeveloperDetails for tenant :" + tenantName
                        + " successfully saved in database");
        return response;
    }

    @Override
    public GoogleDeveloperDetails getGoogleDeveloperDetailsByTenant(String tenantName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GoogleDeveloperDetails> getAllGoogleDeveloperDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteGoogeDeveloperDetails(String tenantName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void partiallyUpdateGoogleDevDetails(String tenantName) {
        // TODO Auto-generated method stub

    }

}
