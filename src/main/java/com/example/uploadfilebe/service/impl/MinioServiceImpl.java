package com.example.uploadfilebe.service.impl;

import com.example.uploadfilebe.service.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioClient minioClient;

    @Override
    public String createUrlUpload(String mtId, String type) {
        try {
            String fileName = generateFileNameWithTimestamp(type);

            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket("test")
                            .object(String.format("%s/%s", mtId, fileName))
                            .expiry(30, TimeUnit.MINUTES)
                    .build());
            log.info(url);
            return url;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    private String generateFileNameWithTimestamp(String type){
        if(type == null) return null;
        String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return fileSuffix + "." + type;
    }
}
