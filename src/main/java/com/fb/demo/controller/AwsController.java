package com.fb.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fb.demo.aws.AWSUtils;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1/aws")
@Slf4j
public class AwsController {

    @Autowired
    private AWSUtils awsUtils;

    @Value("${aws.filePath.bu}")
    public String path;

    @GetMapping(path = "/getFolders")
    public ResponseEntity<?> getAllFolders() {
        List<String> listOfFolders = awsUtils.listAllFolders();
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", listOfFolders));
    }

    @GetMapping(path = "/getPics/{folderName}")
    public ResponseEntity<?> getAllPicsOfGivenFolder(
                    @PathVariable(value = "folderName", required = true) String folderName) {
        List<String> listOfFolders = awsUtils.getAllPicsOfGivenFolder(folderName);
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("response", listOfFolders));
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<?> uploadFileToS3Bucket(
                    @RequestParam(value = "file", required = true) MultipartFile file)
                    throws Exception {
        log.info(":::::Upload file to S3 Bucket::::");
        String imageUrl = awsUtils.uploadProfilePicToS3(file, path);
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("msg", "Successfully uploaded")
                                        .addAttribute("imageUrl", imageUrl));

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteFileFromS3Bucket(
                    @RequestParam(value = "fileUrl", required = true) String fileUrl) {
        log.info(":::::Delete File From S3 Bucket:::::");
        awsUtils.deleteFile(fileUrl);
        return ResponseEntity.status(HttpStatus.OK)
                        .body(new ModelMap().addAttribute("msg", "Successfully deleted"));
    }
}
