package com.fb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.model.request.EmailPropCreateRequest;
import com.fb.demo.model.response.EmailPropResponse;
import com.fb.demo.service.EmailPropService;
import com.fb.demo.service.impl.InvalidInputException;

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
}
