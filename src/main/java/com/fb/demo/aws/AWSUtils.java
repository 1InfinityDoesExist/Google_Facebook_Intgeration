package com.fb.demo.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.redshift.model.BucketNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fb.demo.exception.BucketAlreadyExistException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AWSUtils {

    @Value("${amazon.aws.access.key}")
    private String accessKey;
    @Value("${amazon.aws.secret.key}")
    private String secretKey;
    @Value("${amazon.aws.bucken.name}")
    private String bucketName;
    @Value("${amazon.aws.region}")
    private String region;

    /*
     * Given BucketName Retrieve All The Folders And Their Contents
     * In S3 we have buckets and keys. here folder means keys.
     */
    public List<String> listAllFolders() {
        log.info(":::::Inside AwsUtil Class, getAllFolders:::::");
        ArrayList<String> listOfFolders = new ArrayList<String>();
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
        ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);
        for (;;) {
            List<S3ObjectSummary> s3ObjectSummary = objectListing.getObjectSummaries();
            if (s3ObjectSummary.size() < 1) {
                break;
            }
            s3ObjectSummary.forEach(s -> listOfFolders.add(s.getKey()));
            objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }
        return listOfFolders;
    }

    /*
     * Get the list of pictures stored in a given folder
     */
    public List<String> getAllPicsOfGivenFolder(String folderName) {
        log.info(":::::Inside AwsUtils Class, getAllPicsOfGivenFolder method:::::");
        List<String> keys = new ArrayList<String>();
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        ListObjectsRequest listObjectRequest = new ListObjectsRequest().withBucketName(bucketName)
                        .withPrefix(folderName + "/");
        ObjectListing objectListing = s3Client.listObjects(listObjectRequest);
        for (;;) {
            List<S3ObjectSummary> s3ObjectSummary = objectListing.getObjectSummaries();
            if (s3ObjectSummary.size() < 1) {
                break;
            }
            s3ObjectSummary.forEach(s -> {
                if (!s.getKey().endsWith("/")) {
                    keys.add("https://" + bucketName + ".s3." + region + ".amazonaws.com/"
                                    + s.getKey());
                }
            });
            objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }
        return keys;
    }

    public String uploadProfilePicToS3(MultipartFile file, String folderName) throws Exception {
        AWSCredentials awsCredentails = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentails)).build();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                        folderName + "/" + generateFileName(file),
                        multipartFileToFile(file))
                                        .withCannedAcl(CannedAccessControlList.PublicReadWrite);
        PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
        log.info(":::::putObjectResult {}", putObjectResult.getContentMd5());
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" +
                        putObjectRequest.getKey();
    }

    /*
     * Convert MultiPart file to File because PutObjectRequest( String bucketName, String fileName,
     * File file)
     */
    private File multipartFileToFile(MultipartFile multiPartFile) throws Exception {
        File file = new File(multiPartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multiPartFile.getBytes());
        fos.close();
        return file;
    }

    /*
     * This is just to remove any spaces in the file Name , spaces created lots of issues.
     */
    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + file.getOriginalFilename().replace(" ", "_");
    }

    /*
     * Delete a particular file in from the S3 bucket
     */
    public void deleteFile(String url) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        String input = url.replace("https://", "");
        log.info(":::::bucketName {}", input.substring(0, input.indexOf(".")));
        log.info("::::::: key {}", input.substring(input.indexOf("/") + 1));
        DeleteObjectRequest deleteObjectRequest =
                        new DeleteObjectRequest(input.substring(0, input.indexOf(".")),
                                        input.substring(input.indexOf("/") + 1));
        s3Client.deleteObject(deleteObjectRequest);
        return;
    }

    /*
     * Create A Bucket
     */
    public void createBucket(String bucketName) throws Exception {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        if (s3Client.doesBucketExistV2(bucketName)) {
            throw new BucketAlreadyExistException("Bucket already exist");
        }
        s3Client.createBucket(bucketName);
    }

    /*
     * List Down all the buckets
     */
    public List<String> listAllBucket() {
        List<String> bucketsList = new ArrayList<String>();
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        List<Bucket> listOfBuckets = s3Client.listBuckets();
        listOfBuckets.forEach(b -> bucketsList.add(b.getName()));
        return bucketsList;
    }

    /*
     * Delete bucket for the given bucket Name
     */
    public void deleteBucket(String bucketName2) {
        log.info("::::bucketName {}", bucketName2);
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        if (!s3Client.doesBucketExistV2(bucketName2)) {
            throw new BucketNotFoundException("Bucket : " + bucketName2 + " not found");
        }
        log.info(":::::About to delete buckete");
        s3Client.deleteBucket(bucketName2);
    }
}
