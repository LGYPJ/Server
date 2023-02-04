package com.garamgaebi.GaramgaebiServer.domain;

import com.garamgaebi.GaramgaebiServer.global.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3TestController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public String upload(@RequestBody MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "test");
    }

}
