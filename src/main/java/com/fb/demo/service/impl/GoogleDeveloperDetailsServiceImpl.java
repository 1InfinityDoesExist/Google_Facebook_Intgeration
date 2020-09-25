package com.fb.demo.service.impl;

import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.demo.entity.GoogleDeveloperDetails;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.GoogleDeveloperDetailsNotFound;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.GoogleDevCreateRequest;
import com.fb.demo.model.request.GoogleDevUpdateRequest;
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
        googleDeveloperDetails.setParentTenant(tenant.getId());
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
    public GoogleDeveloperDetails getGoogleDeveloperDetailsByTenant(String tenantName)
                    throws Exception {
        log.info(":::::Inside GoogleDeveloperDetailsServiceImpl Class, getGoogleDeveloperDetailsByTenant method:::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails =
                        googleDeveloperDetailsRepository.findByTenant(tenant.getId());
        if (googleDeveloperDetails == null) {
            throw new GoogleDeveloperDetailsNotFound(
                            "No googleDeveloperDetails found for the given tenant :"
                                            + tenant.getName());
        }
        return googleDeveloperDetails;
    }

    /*
     * Sorted On The Basis of ParentTenant
     */
    @Override
    public List<GoogleDeveloperDetails> getAllGoogleDeveloperDetails() {
        log.info(":::::Inside GoogleDeveloperDetailsServiceImpl Class, getAllGoogleDeveloperDetails method:::::");
        List<GoogleDeveloperDetails> listOfDetails = googleDeveloperDetailsRepository
                        .findAll(Sort.by(Sort.Direction.ASC, "parentTenant"));
        return listOfDetails;
    }

    @Override
    public void deleteGoogeDeveloperDetails(String tenantName) throws Exception {
        log.info(":::::Inside GoogleDeveloperDetailsServiceImpl Class, deleteGoogeDeveloperDetails method:::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails =
                        googleDeveloperDetailsRepository.findByTenant(tenant.getId());
        if (googleDeveloperDetails == null) {
            throw new GoogleDeveloperDetailsNotFound(
                            "No googleDeveloperDetails found for the given tenant :"
                                            + tenant.getName());
        }
        googleDeveloperDetailsRepository.delete(googleDeveloperDetails);
        return;
    }

    @Override
    public void partiallyUpdateGoogleDevDetails(GoogleDevUpdateRequest googleDevUpdateRequest,
                    String tenantName) throws Exception {
        log.info(":::::Inside GoogleDeveloperDetailsServiceImpl Class, partiallyUpdateGoogleDevDetails method:::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        GoogleDeveloperDetails googleDeveloperDetails =
                        googleDeveloperDetailsRepository.findByTenant(tenant.getId());
        if (googleDeveloperDetails == null) {
            throw new GoogleDeveloperDetailsNotFound(
                            "No googleDeveloperDetails found for the given tenant :"
                                            + tenant.getName());
        }
        JSONObject dbGoogleDevDetails = (JSONObject) new JSONParser()
                        .parse(new ObjectMapper().writeValueAsString(googleDeveloperDetails));
        JSONObject payloadGoogleDevDetails = (JSONObject) new JSONParser()
                        .parse(new ObjectMapper().writeValueAsString(googleDevUpdateRequest));
        for (Object obj : payloadGoogleDevDetails.keySet()) {
            String param = (String) obj;
            dbGoogleDevDetails.put(param, payloadGoogleDevDetails.get(param));
        }
        googleDeveloperDetailsRepository
                        .save(new ObjectMapper().readValue(dbGoogleDevDetails.toJSONString(),
                                        GoogleDeveloperDetails.class));
    }

}
