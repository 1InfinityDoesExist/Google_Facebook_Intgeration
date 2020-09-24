package com.fb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.TenantAlreadyExistException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.TenantCreateRequest;
import com.fb.demo.model.response.TenantCreateResponse;
import com.fb.demo.service.TenantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

@RestController("v1Tenant")
@RequestMapping(path = "v1/tenant")
@Slf4j
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTenant(@RequestBody TenantCreateRequest tenantCreateRequest)
                    throws Exception {
        log.info(":::::TenantController Class , createTenant method:::::");
        try {
            TenantCreateResponse tenantCreateResponse =
                            tenantService.createTenant(tenantCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ModelMap()
                            .addAttribute("msg", tenantCreateResponse.getMessage())
                            .addAttribute("id", tenantCreateResponse.getId()));
        } catch (TenantAlreadyExistException ex) {
            log.error("Tenant already exist in the database. Do change the tenant name and try once again");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage())
                                            .addAttribute("existing_tenant",
                                                            ex.getTenant().getName()));
        }
    }

    @GetMapping(path = "/get", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> getTenantByName(@RequestParam(required = true) String tenantName)
                    throws Exception {
        log.info(":::::TenantController Class , getTenantByNam method:::::");
        try {
            Tenant tenant = tenantService.getTenantByName(tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", tenant));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found for the given tenantName :" + tenantName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }
}