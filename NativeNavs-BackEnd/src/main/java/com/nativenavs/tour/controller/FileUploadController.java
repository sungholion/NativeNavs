package com.nativenavs.tour.controller;

import com.nativenavs.common.service.AwsS3ObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;





// test용 컨트롤러
@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private AwsS3ObjectStorage awsS3ObjectStorage;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return awsS3ObjectStorage.uploadFile(file);
    }
}
