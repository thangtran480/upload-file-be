package com.example.uploadfilebe.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint("http://192.168.32.101:9000")
                .credentials("49VhUKTtNyepTa7hWMKa", "4I0xRZGTWfCX7ONnPQQ2cNjfZm3KSOMhVHeziLgZ")
                .build();
    }
}
