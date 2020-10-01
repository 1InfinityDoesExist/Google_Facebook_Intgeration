package com.fb.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.entity.EmailTemplate;
import com.fb.demo.exception.EmailTemplateNotFound;
import com.fb.demo.model.request.EmailTemplateCreateRequest;
import com.fb.demo.model.request.EmailTemplateUpdateRequest;
import com.fb.demo.model.response.EmailTemplateResponse;
import com.fb.demo.service.EmailTemplateService;
import com.fb.demo.service.impl.InvalidInputException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "v1/emailTemplate")
@Slf4j
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> persistEmailTemplate(
                    @RequestBody EmailTemplateCreateRequest emailTemplateCreateRequest)
                    throws Exception {
        log.info(":::::Inside EmailTemplateController Class, persistEmailTemplate:::::");
        try {
            EmailTemplateResponse response =
                            emailTemplateService.createEmailTemplate(emailTemplateCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ModelMap().addAttribute("msg", response.getMsg())
                                            .addAttribute("id", response.getId()));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/get")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", paramType = "path")})
    public ResponseEntity<?> getEmailTemplate(
                    @RequestParam(value = "id", required = true) Integer id) throws Exception {
        try {
            EmailTemplate emailTemplate = emailTemplateService.getEmailTemplate(id);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", emailTemplate));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final EmailTemplateNotFound ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }


    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAllEmailTemplate() {
        List<EmailTemplate> listOfEmailTemplate = emailTemplateService.getAllEmailTemplate();
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", listOfEmailTemplate));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteEmailTemplate(
                    @RequestParam(value = "id", required = true) Integer id) throws Exception {
        try {
            emailTemplateService.deleteEmailTemplate(id);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", "Successfully deleted"));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final EmailTemplateNotFound ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateEmailTemplate(
                    @RequestBody EmailTemplateUpdateRequest emailTemplateUpdateRequest,
                    @RequestParam(value = "id", required = true) Integer id) throws Exception {
        try {
            emailTemplateService.updateEmailTemplate(emailTemplateUpdateRequest, id);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", "Successfully udated"));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final EmailTemplateNotFound ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }
}
