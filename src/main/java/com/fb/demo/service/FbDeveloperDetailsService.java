package com.fb.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.fb.demo.entity.FbDeveloperDetails;
import com.fb.demo.model.request.FbDeveloperDetailsCreateRequest;
import com.fb.demo.model.request.FbDeveloperDetailsUpdateRequest;
import com.fb.demo.model.response.FbDevCreateResponse;

@Service
public interface FbDeveloperDetailsService {

    public FbDevCreateResponse saveFbDeveloperDetails(
                    FbDeveloperDetailsCreateRequest fbDeveloperDetailsCreateRequest);

    public FbDeveloperDetails getFbDeveloperDetailsByTenantName(String tenantName);

    public List<FbDeveloperDetails> getAllDeveloperDetails();

    public void deleteFbDeveloperDetails(String tenantName);

    public void updateFbDeveloperDetails(FbDeveloperDetailsUpdateRequest fbDevUpdateRequest,
                    String tenantName);
}
