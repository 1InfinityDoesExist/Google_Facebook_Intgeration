package com.fb.demo.aws;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
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

}
