package com.fb.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.exception.FbAccessTokenValidationException;
import com.fb.demo.exception.FbDeveloperDetailsNotFoundException;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.service.ValidationAndVerificationOfATService;
import com.fb.demo.service.impl.InvalidInputException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

@RestController("v1ValidationAndVerificationOfAccessToken")
@RequestMapping(path = "/verfiyAndValidate")
@Slf4j
public class ValidationAndVerificationOfAT {


    @Autowired
    private ValidationAndVerificationOfATService validationAndVerificationOfATService;


    @GetMapping(path = "/google/{tenant}")
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", paramType = "path"),
                @ApiImplicitParam(name = "tenant", paramType = "path")})
    public ResponseEntity<?> verifyAndValidateGoogleAccessToken(
                    @RequestParam(name = "accessToken", required = true) String accessToken,
                    @PathVariable(name = "tenant", required = true) String tenant)
                    throws Exception {
        try {
            boolean isValidAndVerified = validationAndVerificationOfATService
                            .verifyAndValidateGoogleAccessToken(accessToken,
                                            tenant);
            if (isValidAndVerified) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(new ModelMap().addAttribute("validate", "Success")
                                                .addAttribute("verified", "Success"));
            }
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("mgs", "Invalid access token"));
        } catch (final MalformedURLException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final IOException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final ParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }


    @GetMapping(path = "/facebook/{tenant}")
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", paramType = "path"),
                @ApiImplicitParam(name = "tenant", paramType = "path")})
    public ResponseEntity<?> verifyAndValidateFacebookAccessToken(
                    @RequestParam(name = "accessToken", required = true) String accessToken,
                    @PathVariable(name = "tenant", required = true) String tenant)
                    throws Exception {
        try {
            validationAndVerificationOfATService.verifyAndValidateFacebookAccessToken(accessToken,
                            tenant);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("validation", "success")
                                            .addAttribute("verification", "success"));
        } catch (final InvalidInputException ex) {
            log.error(":::::Invalid input, access token must not be null or empty::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final FbAccessTokenValidationException ex) {
            log.error(":::::Invalid Access Token:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final TenantNotFoundException ex) {
            log.error(":::::Tenant does not exist:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final FbDeveloperDetailsNotFoundException ex) {
            log.error("::::FbDeveloper not found:::::");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }
}
