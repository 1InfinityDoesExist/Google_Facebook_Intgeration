package com.fb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.TenantAlreadyExistException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.TenantCreateRequest;
import com.fb.demo.model.request.TenantUpdateReqeust;
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

    @GetMapping(path = "/getAllTenant")
    @ApiImplicitParams({@ApiImplicitParam(name = "", paramType = "")})
    public ResponseEntity<?> getAllTenant(@PageableDefault(page = 0, value = 5) Pageable pagable) {
        log.info(":::::TenantController Class , getAllTenant method:::::");
        Page<Tenant> listOfTenant = tenantService.getAllTenant(pagable);
        if (listOfTenant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "No tenant exist in the db."));
        }
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", listOfTenant));
    }

    @PutMapping(path = "/update/{tenantName}")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> udpateTenant(
                    @RequestBody(required = true) TenantUpdateReqeust tenantUpdateReqeust,
                    @PathVariable(value = "tenantName", required = true) String tenantName)
                    throws Exception {
        log.info(":::::TenantController Class, Paritalially Update Tenant:::::");
        try {
            tenantService.partiallyUpdateTenant(tenantUpdateReqeust, tenantName);
            return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("msg",
                            "Tenant :" + tenantName + " successfully updated."));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::No tenant exist with name :" + tenantName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));

        }
    }
}
