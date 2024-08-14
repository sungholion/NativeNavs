package com.nativenavs.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Log4j2
@Data
public class AwsS3ObjectStorage {
    private AmazonS3 amazonS3;
    private String bucket;
    private String aiS3Url;

    public AwsS3ObjectStorage(AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
            amazonS3.setObjectAcl(bucket, fileName, CannedAccessControlList.PublicRead);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String fileUrl) {
        try {

            URI uri = new URI(fileUrl);

            String key = uri.getPath().substring(1);


            if (amazonS3.doesObjectExist(bucket, key)) {

                amazonS3.deleteObject(bucket, key);
                log.info("File deleted successfully: {}", key);
                }

        } catch (Exception e) { //error
            log.error("Failed to delete file!: {}", fileUrl, e);

        }
    }

}
