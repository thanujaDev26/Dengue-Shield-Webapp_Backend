package com.dengue_webapp.dengue_webapp.service.impl;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectResult;
import com.dengue_webapp.dengue_webapp.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceIMPl implements S3Service {
    @Autowired
    private S3Client s3Client;
//    private final AmazonS3 s3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    @Override
    public String uploadFile(MultipartFile image) throws IOException {
        String fileName = generateUniqueFileName(image.getOriginalFilename());
        System.out.println("hello in impl");
        System.out.println(fileName);
        // Upload file to S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(image.getContentType())
                .build();
        System.out.println(putObjectRequest);
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        // Return the URL of the uploaded file
        String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3Client.serviceClientConfiguration().region(), fileName);
        System.out.println(url);
        return url;


        
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        return UUID.randomUUID().toString() + extension;
    }
}
