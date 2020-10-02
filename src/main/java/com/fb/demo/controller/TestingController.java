package com.fb.demo.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fb.demo.utility.FileToBase64;

@RestController
@RequestMapping(path = "/testing")
public class TestingController {

    @Autowired
    private FileToBase64 fileToBase64;

    @GetMapping(path = "/get")
    public ResponseEntity<?> getBase64FromImageUrl(
                    @RequestParam(value = "imageUrl", required = true) String imageUrl)
                    throws Exception {
        try {
            String response = fileToBase64.imageUrlToBase64(imageUrl);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", response));
        } catch (final MalformedURLException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final IOException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/base64ToImage")
    public ResponseEntity<?> getBase64ToImage(
                    @RequestParam(value = "imageUrl", required = true) String imageUrl)
                    throws Exception {
        try {
            BufferedImage image =
                            fileToBase64.base64ToImage(fileToBase64.imageUrlToBase64(imageUrl));
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", image));
        } catch (final MalformedURLException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        } catch (final IOException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }
}
