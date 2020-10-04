package com.fb.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import com.fb.demo.entity.FbDeveloperDetails;
import com.fb.demo.exception.FbDeveloperDetailsNotFoundException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.model.request.FbDeveloperDetailsCreateRequest;
import com.fb.demo.model.request.FbDeveloperDetailsUpdateRequest;
import com.fb.demo.model.response.FbDevCreateResponse;
import com.fb.demo.service.FbDeveloperDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController("v1FacebookIntegrationController")
@RequestMapping(path = "/v1/facebook")
@Slf4j
@Api(value = "facebook integration", description = " Facebook integration api",
                tags = {"Facebook Integration"})
public class FacebookIntegrationController {

    @Autowired
    private FbDeveloperDetailsService fbDeveloperDetailsService;

    @PostMapping(path = "/{tenantName}/create", produces = {"application/json"},
                    consumes = {"application/json"})
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "tenantName", value = "", required = true,
                    paramType = "path", dataType = "String", example = "Google1")})
    @ApiOperation(value = "persistFbDeveloperDetailsInDB", nickname = "saveFbDeveoperDetails")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Success",
                    response = ModelMap.class)})
    public ResponseEntity<?> createFbDevDetails(
                    @RequestBody(required = true) FbDeveloperDetailsCreateRequest fbCredDetails,
                    @PathVariable(value = "tenantName", required = true) String tenantName)
                    throws Exception {
        log.info("Inside FacebookIntegrationController class, createFbDevDetails method:::::");
        try {
            FbDevCreateResponse response = fbDeveloperDetailsService.saveFbDeveloperDetails(
                            fbCredDetails, tenantName);
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ModelMap().addAttribute("msg", response.getMsg())
                                            .addAttribute("id", response.getId()));
        } catch (final TenantNotFoundException ex) {
            log.error("::::Tenant not found.:::::");
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/{tenantName}/get", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> getFbDeveloperDetailsByTenantName(
                    @ApiParam(value = "tenantName", required = true) @PathVariable(
                                    name = "tenantName", required = true) String tenantName)
                    throws Exception {
        log.info("Inside FacebookIntegrationController class, getFbDeveloperDetailsByTenantName method:::::");
        try {
            FbDeveloperDetails fbDeveloperDetails =
                            fbDeveloperDetailsService.getFbDeveloperDetailsByTenantName(tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", fbDeveloperDetails));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found.:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final FbDeveloperDetailsNotFoundException ex) {
            log.error(":::::FbDeveloperDetails not found.:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/getAll", produces = "application/json")
    public ResponseEntity<?> getAllFbDeveloperDetails() throws Exception {
        log.info("Inside FacebookIntegrationController class, getFbDeveloperDetailsByTenantName method:::::");
        List<FbDeveloperDetails> listOfFbDeveloperDetails =
                        fbDeveloperDetailsService.getAllDeveloperDetails();
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", listOfFbDeveloperDetails));

    }

    @DeleteMapping(path = "/{tenantName}/delete")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> deleteFbDeveloperDetails(
                    @ApiParam(value = "tenantName", required = true) @PathVariable(
                                    name = "tenantName", required = true) String tenantName)
                    throws Exception {
        log.info("Inside FacebookIntegrationController class, deleteFbDeveloperDetails method:::::");
        try {
            fbDeveloperDetailsService.deleteFbDeveloperDetails(tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "Successfully deleted."));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found.:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final FbDeveloperDetailsNotFoundException ex) {
            log.error(":::::FbDeveloperDetails not found.:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }


    @PutMapping(path = "/{tenantName}/update")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> updateFbDeveloperDetails(@RequestBody(
                    required = true) FbDeveloperDetailsUpdateRequest fbDevUpdateRequest,
                    @ApiParam(value = "tenantName", required = true) @PathVariable(
                                    name = "tenantName", required = true) String tenantName)
                    throws Exception {
        log.info("Inside FacebookIntegrationController class, updateFbDeveloperDetails method:::::");
        try {
            fbDeveloperDetailsService.updateFbDeveloperDetails(fbDevUpdateRequest, tenantName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "Successfully updated."));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant not found.:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final FbDeveloperDetailsNotFoundException ex) {
            log.error(":::::FbDeveloperDetails not found.:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

}
