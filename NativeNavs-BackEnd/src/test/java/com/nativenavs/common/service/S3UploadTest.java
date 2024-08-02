//package com.nativenavs.common.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//public class S3UploadTest {
//
//    @Autowired
//    private AwsS3ObjectStorage awsS3ObjectStorage;
//
//    @Test
//    public void testFileUpload() throws Exception {
//        // 테스트할 파일 준비
//        String filePath = "src/test/resources/test-image.jpg";
//        InputStream inputStream = new FileInputStream(filePath);
//        MultipartFile multipartFile = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", inputStream);
//
//        // 파일 업로드 테스트
//        String fileUrl = awsS3ObjectStorage.uploadFile(multipartFile);
//        assertNotNull(fileUrl);
//        System.out.println("Uploaded File URL: " + fileUrl);
//    }
//}