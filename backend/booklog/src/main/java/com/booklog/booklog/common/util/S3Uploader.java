package com.booklog.booklog.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(String prefix, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            System.out.println("file is empty!!!!");
            return "";
        }

        String fileName = prefix + "_" + createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
            log.info("[%s] upload completed", fileName);
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new S3Exception(ErrorCode.S3_UPLOAD_FAILED);
        }
    }

    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, key);
            if (isObjectExist) {
                amazonS3Client.deleteObject(deleteObjectRequest);
                log.info("[%s] delete completed", key);
            } else {
                log.info("file not found");
            }
        } catch (Exception e) {
            throw new S3Exception(ErrorCode.S3_DELETE_FAILED);
        }
    }

    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    public String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일입니다.");
        }
    }
}
