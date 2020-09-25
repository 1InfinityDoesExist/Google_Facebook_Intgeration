package com.fb.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.entity.GoogleDeveloperDetails;
import com.fb.demo.exception.GoogleDeveloperDetailsNotFound;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.GoogleDevCreateRequest;
import com.fb.demo.model.request.GoogleDevUpdateRequest;
import com.fb.demo.model.response.GoogleDevCreateResponse;
import com.fb.demo.service.GoogleDeveloperDetailsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

@RestController("v1GoogleIntegrationController")
@RequestMapping(path = "v1/google")
@Slf4j
public class GoogleIntegrationController {

    @Autowired
    private GoogleDeveloperDetailsService googleDevDetailsService;

    @PostMapping(path = "/{tenantName}/create", consumes = "application/json",
                    produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> saveGoogleDeveloperDetails(
                    @RequestBody GoogleDevCreateRequest googleDevCreateRequest,
                    @PathVariable(name = "tenantName", required = true) String tenantName)
                    throws Exception {
        try {
            GoogleDevCreateResponse response = googleDevDetailsService
                            .saveGoogleDeveloperDetails(googleDevCreateRequest, tenantName);
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ModelMap().addAttribute("msg", response.getMsg())
                                            .addAttribute("id", response.getId()));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::No tenant found:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/{tenantName}/get", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> getGoogleDeveloperDetails(
                    @PathVariable(name = "tenantName", required = true) String tenantName)
                    throws Exception {
        try {
            GoogleDeveloperDetails googleDeveloperDetails =
                            googleDevDetailsService.getGoogleDeveloperDetailsByTenant(tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", googleDeveloperDetails));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found. :::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final GoogleDeveloperDetailsNotFound ex) {
            log.error(":::::GoogleDeveloperDetails not found. :::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/getAll", produces = "application/json")
    public ResponseEntity<?> getAllGoogleDeveloperDetails() {
        List<GoogleDeveloperDetails> listOfGoogleDeveloperDetails =
                        googleDevDetailsService.getAllGoogleDeveloperDetails();
        return ResponseEntity.status(HttpStatus.OK).body(
                        new ModelMap().addAttribute("response", listOfGoogleDeveloperDetails));
    }

    @DeleteMapping(path = "/{tenantName}/delete")
    @ApiImplicitParams({@ApiImplicitParam(name = "teanntName", paramType = "path")})
    public ResponseEntity<?> deleteGoogleDeveloperDetails(
                    @PathVariable(name = "tenantName", required = true) String tenantName)
                    throws Exception {
        try {
            googleDevDetailsService.deleteGoogeDeveloperDetails(tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "Successfully deleted."));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found. :::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final GoogleDeveloperDetailsNotFound ex) {
            log.error(":::::GoogleDeveloperDetails not found. :::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @PutMapping(path = "/{tenantName}/update")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> updateGoogleDeveloperDetails(
                    @RequestBody GoogleDevUpdateRequest googleDevUpdateRequest,
                    @PathVariable(name = "tenantName", required = true) String tenantName)
                    throws Exception {
        try {
            googleDevDetailsService.partiallyUpdateGoogleDevDetails(googleDevUpdateRequest,
                            tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "Successfully updated."));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found. :::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final GoogleDeveloperDetailsNotFound ex) {
            log.error(":::::GoogleDeveloperDetails not found. :::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

}
