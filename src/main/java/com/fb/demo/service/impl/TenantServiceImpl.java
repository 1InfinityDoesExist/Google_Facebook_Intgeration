package com.fb.demo.service.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.TenantAlreadyExistException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.TenantCreateRequest;
import com.fb.demo.model.request.TenantUpdateReqeust;
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
        tenant.setOrganizationEmail(tenantCreateRequest.getOrganizationEmail());
        tenant.setOrganizationMobileNumber(tenantCreateRequest.getOrganizationMobileNumber());
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

    @Override
    public Page<Tenant> getAllTenant(Pageable pageable) {
        log.info(":::::Inside TenantServiceImpl Class, getAllTenant method:::::");;
        Page<Tenant> listOfTenant = tenantRepository.findTenantByIsActive(false,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                        Sort.by(Sort.Direction.DESC, "id")));
        return listOfTenant;
    }

    @Override
    public void partiallyUpdateTenant(TenantUpdateReqeust tenantUpdateReqeust, String tenantName)
                    throws Exception {
        log.info(":::::Inside TenantServiceImpl Class, updateTenant method:::::");
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenantName);
        if (tenantFromDB == null) {
            throw new TenantNotFoundException("Tenant not found. Please create a tenant first");
        }
        JSONObject dbTenantJson =
                        (JSONObject) new JSONParser()
                                        .parse(new ObjectMapper().writeValueAsString(tenantFromDB));
        JSONObject payloadTeanntJson = (JSONObject) new JSONParser()
                        .parse(new ObjectMapper().writeValueAsString(tenantUpdateReqeust));
        for (Object obj : payloadTeanntJson.keySet()) {
            String param = (String) obj;
            dbTenantJson.put(param, payloadTeanntJson.get(param));
        }
        tenantRepository.save(
                        new ObjectMapper().readValue(dbTenantJson.toJSONString(), Tenant.class));
        return;
    }

    @Override
    public void deleteTenant(String tenantName) throws Exception {
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenantName);
        if (tenantFromDB == null) {
            throw new TenantNotFoundException("Tenant not found.");
        }
        tenantRepository.delete(tenantFromDB);
        return;

    }

}
