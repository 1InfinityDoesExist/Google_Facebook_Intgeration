package com.fb.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.FbDeveloperDetails;
import com.fb.demo.model.request.FbDeveloperDetailsCreateRequest;
import com.fb.demo.model.request.FbDeveloperDetailsUpdateRequest;
import com.fb.demo.model.response.FbDevCreateResponse;
import com.fb.demo.service.FbDeveloperDetailsService;

@Component
public class FacebookDeveloperDetailsServiceImpl implements FbDeveloperDetailsService {

    @Override
    public FbDevCreateResponse saveFbDeveloperDetails(
                    FbDeveloperDetailsCreateRequest fbDeveloperDetailsCreateRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FbDeveloperDetails getFbDeveloperDetailsByTenantName(String tenantName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<FbDeveloperDetails> getAllDeveloperDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteFbDeveloperDetails(String tenantName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateFbDeveloperDetails(FbDeveloperDetailsUpdateRequest fbDevUpdateRequest,
                    String tenantName) {
        // TODO Auto-generated method stub

    }

}
