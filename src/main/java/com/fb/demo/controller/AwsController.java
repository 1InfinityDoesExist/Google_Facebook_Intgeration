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
import com.amazonaws.services.redshift.model.BucketNotFoundException;
import com.fb.demo.aws.AWSUtils;
import com.fb.demo.exception.BucketAlreadyExistException;
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

    @PostMapping(path = "/createBucket/{bucketName}")
    public ResponseEntity<?> createBucket(
                    @PathVariable(value = "bucketName", required = true) String bucketName)
                    throws Exception {
        log.info("::::Create bucket with name : " + bucketName);
        try {
            awsUtils.createBucket(bucketName);
            return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("msg",
                            "Bucket : " + bucketName + " successfully created"));
        } catch (final BucketAlreadyExistException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/getAllBuckets")
    public ResponseEntity<?> getAllBucket() {
        log.info("List down all the buckets");
        List<String> response = awsUtils.listAllBucket();
        return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("response",
                        response));
    }

    @DeleteMapping(path = "/deleteBucket/{bucketName}")
    public ResponseEntity<?> deleteBucket(
                    @PathVariable(value = "bucketName", required = true) String bucketName) {
        log.info("::::::delete Bucket via bucketName:::::");
        try {
            awsUtils.deleteBucket(bucketName);
            return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("msg",
                            "Successfully deleted bucket with name : " + bucketName));
        } catch (final BucketNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }
}
