package com.fb.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fb.demo.entity.Tenant;
import com.fb.demo.model.request.TenantCreateRequest;
import com.fb.demo.model.request.TenantUpdateReqeust;
import com.fb.demo.model.response.TenantCreateResponse;

@Service
public interface TenantService {

    public TenantCreateResponse createTenant(TenantCreateRequest tenantCreateRequest)
                    throws Exception;

    public Tenant getTenantByName(String tenantName) throws Exception;

    public Page<Tenant> getAllTenant(Pageable pagable);

    public void partiallyUpdateTenant(TenantUpdateReqeust tenantUpdateReqeust, String tenantName)
                    throws Exception;

    public void deleteTenant(String tenantName) throws Exception;

}
