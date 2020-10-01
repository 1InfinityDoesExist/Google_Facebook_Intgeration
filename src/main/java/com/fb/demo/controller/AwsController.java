package com.fb.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.aws.AWSUtils;

@RestController
@RequestMapping(path = "/v1/aws")
public class AwsController {

    @Autowired
    private AWSUtils awsUtils;

    @GetMapping(path = "/getFolders")
    public ResponseEntity<?> getAllFolders() {
        List<String> listOfFolders = awsUtils.listAllFolders();
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", listOfFolders));


    }
}
