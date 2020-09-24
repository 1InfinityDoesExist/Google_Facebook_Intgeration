package com.fb.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.TenantAlreadyExistException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.TenantCreateRequest;
import com.fb.demo.model.response.TenantCreateResponse;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.TenantService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TenantServiceImpl implements TenantService {

  @Autowired
  private TenantRepository tenantRepository;

  @Override
  public TenantCreateResponse createTenant(TenantCreateRequest tenantCreateRequest)
        throws Exception {
    log.info(":::::Inside TenantServiceImpl Class, createTenant method:::::");
    Tenant tenantFromDB = tenantRepository.getTenantByName(tenantCreateRequest.getName());
    if (tenantFromDB != null) {
      throw new TenantAlreadyExistException("Tenant already Exist", tenantFromDB);
    }
    Tenant tenant = new Tenant();
    tenant.setDescription(tenantCreateRequest.getDescription());
    tenant.setName(tenantCreateRequest.getName());
    tenant.setOrganizationName(tenantCreateRequest.getOrganizationName());
    tenant.setRegistrationNumber(tenantCreateRequest.getRegistrationNumber());
    tenant.setTenantType(tenantCreateRequest.getTenantType());
    tenant.setUrl(tenantCreateRequest.getUrl());
    tenant.setVersion(tenantCreateRequest.getVersion());
    tenant.setWebsite(tenantCreateRequest.getWebsite());
    tenantRepository.save(tenant);
    TenantCreateResponse tenantCreateResponse = new TenantCreateResponse();
    tenantCreateResponse.setId(tenant.getId());
    tenantCreateResponse.setMessage("Tenant : " + tenant.getName() + " successfully created");
    return tenantCreateResponse;
  }

  @Override
  public Tenant getTenantByName(String tenantName) throws Exception {
    log.info(":::::Inside TenantServiceImpl Class, getTenantByName method::::");
    Tenant tenantFromDB = tenantRepository.getTenantByName(tenantName);
    if (tenantFromDB == null) {
      throw new TenantNotFoundException("Tenant not found.");
    }
    return tenantFromDB;
  }

}
