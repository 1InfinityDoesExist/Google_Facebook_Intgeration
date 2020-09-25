package com.fb.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.fb.demo.entity.GoogleDeveloperDetails;
import com.fb.demo.model.request.GoogleDevCreateRequest;
import com.fb.demo.model.response.GoogleDevCreateResponse;

@Service
public interface GoogleDeveloperDetailsService {

    public GoogleDevCreateResponse saveGoogleDeveloperDetails(
                    GoogleDevCreateRequest googleDevCreateRequest, String tenantName)
                    throws Exception;

    public GoogleDeveloperDetails getGoogleDeveloperDetailsByTenant(String tenantName);

    public List<GoogleDeveloperDetails> getAllGoogleDeveloperDetails();

    public void deleteGoogeDeveloperDetails(String tenantName);

    public void partiallyUpdateGoogleDevDetails(String tenantName);
}
