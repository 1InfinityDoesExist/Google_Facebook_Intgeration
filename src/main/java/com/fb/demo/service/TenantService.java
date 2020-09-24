package com.fb.demo.service;

import java.util.Set;
import org.springframework.stereotype.Service;
import com.fb.demo.entity.Tenant;
import com.fb.demo.model.request.TenantCreateRequest;
import com.fb.demo.model.response.TenantCreateResponse;

@Service
public interface TenantService {

    public TenantCreateResponse createTenant(TenantCreateRequest tenantCreateRequest)
                    throws Exception;

    public Tenant getTenantByName(String tenantName) throws Exception;

    public Set<Tenant> getAllTenant();

}
