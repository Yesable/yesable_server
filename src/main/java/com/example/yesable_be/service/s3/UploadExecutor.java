package com.example.yesable_be.service.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.yesable_be.component.aws.S3UploadComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.example.yesable_be.constant.CommonConstants.*;

@Component
@RequiredArgsConstructor
public class UploadExecutor {
    private final S3UploadComponent s3UploadComponent;
    private final Environment env;

    /**
     * png 파일 업로드
     */
    public String pngFileUpload(InputStream file, String filePath) throws IOException {
        //TODO : exception handling 처리 후 try-catch 로 변경
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentType(PNG_CONTENT_TYPE);
        metaData.setContentLength(file.available());
        metaData.setCacheControl("max-age=600");

        s3UploadComponent.uploadFile(file, metaData, filePath);
        return "https://" + env.getProperty("aws.s3.bucket") + "/" + filePath;
    }

}
