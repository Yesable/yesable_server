package com.example.yesable_be.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String s3AccessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String s3SecretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean(name = "amazonS3Builder")
    public AmazonS3 amazonS3() {

        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(buildAWSStaticCredentialsProvider(s3AccessKey, s3SecretKey))
                .build();
    }

    private AWSStaticCredentialsProvider buildAWSStaticCredentialsProvider(String accessKey, String secretKey) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

}
