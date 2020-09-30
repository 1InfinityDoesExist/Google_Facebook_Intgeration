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
import com.fb.demo.entity.EmailProp;
import com.fb.demo.exception.EmailPropNotFoundException;
import com.fb.demo.model.request.EmailPropCreateRequest;
import com.fb.demo.model.request.EmailPropUpdateRequest;
import com.fb.demo.model.response.EmailPropResponse;
import com.fb.demo.service.EmailPropService;
import com.fb.demo.service.impl.InvalidInputException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(path = "/v1/emailProp")
public class EmailPropController {

    @Autowired
    private EmailPropService emailPropService;

    @PostMapping(path = "/create", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> persistEmailProp(@RequestBody EmailPropCreateRequest request)
                    throws Exception {
        try {
            EmailPropResponse response = emailPropService.createEmailProp(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ModelMap().addAttribute("msg", response.getMsg())
                                            .addAttribute("id", response.getId()));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/get", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", paramType = "path")})
    public ResponseEntity<?> getEmailProp(
                    @RequestParam(value = "id", required = true) Integer emailPropId)
                    throws Exception {
        try {
            EmailProp emailProp = emailPropService.getEmailProp(emailPropId);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", emailProp));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final EmailPropNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAllEmailProp() {
        List<EmailProp> emailPropList = emailPropService.getAllEmailProp();
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", emailPropList));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteEmailProp(
                    @RequestParam(value = "id", required = true) Integer id) throws Exception {
        try {
            emailPropService.deleteEmilProp(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", "Successfully deleted"));
        } catch (final EmailPropNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @PutMapping(path = "/")
    public ResponseEntity<?> updateEmailProp(
                    @RequestBody EmailPropUpdateRequest emailPropUpdateRequest,
                    @RequestParam(value = "id", required = true) Integer id) throws Exception {
        try {
            emailPropService.updateEmailProp(emailPropUpdateRequest, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(new ModelMap().addAttribute("msg", "Successfully updated"));
        } catch (final InvalidInputException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final EmailPropNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }
}
