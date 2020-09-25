package com.fb.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.FbDeveloperDetails;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.FbDeveloperDetailsNotFoundException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.FbDeveloperDetailsCreateRequest;
import com.fb.demo.model.request.FbDeveloperDetailsUpdateRequest;
import com.fb.demo.model.response.FbDevCreateResponse;
import com.fb.demo.repository.FbDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.FbDeveloperDetailsService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FacebookDeveloperDetailsServiceImpl implements FbDeveloperDetailsService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private FbDeveloperDetailsRepository fbDetailsRepository;

    @Override
    public FbDevCreateResponse saveFbDeveloperDetails(
                    FbDeveloperDetailsCreateRequest fbDeveloperDetailsCreateRequest,
                    String tenantName) throws Exception {
        log.info(":::::Inside FacebookDeveloperDetailServiceImpl Class, saveFbDeveloperDetails method::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        FbDeveloperDetails fbDeveloperDetails = new FbDeveloperDetails();
        fbDeveloperDetails.setParentTenant(tenant.getId());
        fbDeveloperDetails.setClientId(fbDeveloperDetailsCreateRequest.getClientId());
        fbDeveloperDetails.setClientSecret(fbDeveloperDetailsCreateRequest.getClientSecret());
        fbDeveloperDetails.setRedirectUrl(fbDeveloperDetailsCreateRequest.getRedirectUrl());
        fbDetailsRepository.save(fbDeveloperDetails);
        FbDevCreateResponse response = new FbDevCreateResponse();
        response.setId(fbDeveloperDetails.getId());
        response.setMsg("Successfully stored fbDeveloperDetails in database.");
        return response;
    }

    @Override
    public FbDeveloperDetails getFbDeveloperDetailsByTenantName(String tenantName)
                    throws Exception {
        log.info(":::::Inside FacebookDeveloperDetailServiceImpl Class, getFbDeveloperDetailsByTenantName method::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        FbDeveloperDetails fbDeveloperDetails =
                        fbDetailsRepository.findByParentTenant(tenant.getId());
        if (fbDeveloperDetails == null) {
            throw new FbDeveloperDetailsNotFoundException("FbDeveloperDetails not found.");
        }
        return fbDeveloperDetails;
    }

    @Override
    public List<FbDeveloperDetails> getAllDeveloperDetails() {
        log.info(":::::Inside FacebookDeveloperDetailServiceImpl Class, getAllDeveloperDetails method::::");
        List<FbDeveloperDetails> listOfFbDeveloperDetails =
                        fbDetailsRepository.getAllFbDeveloperDetails(
                                        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,
                                                        "parentTenant")));
        return listOfFbDeveloperDetails;
    }

    @Override
    public void deleteFbDeveloperDetails(String tenantName) throws Exception {
        log.info(":::::Inside FacebookDeveloperDetailServiceImpl Class, deleteFbDeveloperDetails method::::");
        Tenant tenant = tenantRepository.getTenantByName(tenantName);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        FbDeveloperDetails fbDeveloperDetails =
                        fbDetailsRepository.findByParentTenant(tenant.getId());
        if (fbDeveloperDetails == null) {
            throw new FbDeveloperDetailsNotFoundException("FbDeveloperDetails not found.");
        }
        fbDetailsRepository.delete(fbDeveloperDetails);
        return;
    }

    @Override
    public void updateFbDeveloperDetails(FbDeveloperDetailsUpdateRequest fbDevUpdateRequest,
                    String tenantName) {
        // TODO Auto-generated method stub

    }

}
