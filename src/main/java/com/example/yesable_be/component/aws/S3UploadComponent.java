package com.example.yesable_be.component.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploadComponent {

    @Qualifier("amazonS3Builder")
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String yesableBucket;

    /**
     * s3 버킷에 올라간 파일 삭제
     */
    public void deleteFile(String filePath) {
        boolean isExistObject = amazonS3.doesObjectExist(yesableBucket, filePath);
        if (isExistObject) {
            amazonS3.deleteObject(yesableBucket, filePath);
        }
    }

    /**
     * s3 버킷에 파일 업로드
     */
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        objectMetadata.setContentLength(bytes.length);
        objectMetadata.setCacheControl("max-age=10");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        PutObjectRequest request = new PutObjectRequest(yesableBucket, fileName, byteArrayInputStream, objectMetadata).withCannedAcl(CannedAccessControlList.Private);
        try {
            amazonS3.putObject(request);
        } catch (Exception e) {
            // TODO : Error Handling
        }
    }

    /**
     * S3 버킷 파일 경로 획득
     */
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(yesableBucket, fileName).toString();
    }


}
