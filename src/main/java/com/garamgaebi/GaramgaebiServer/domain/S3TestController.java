package com.garamgaebi.GaramgaebiServer.domain;

import com.garamgaebi.GaramgaebiServer.global.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3TestController {

    private final S3Uploader s3Uploader;

    // 프론트에서 이미지 어떻게 보내는지 알아보고 받는 방식 수정
    @PostMapping("/images")
    public String upload(@RequestPart("info") S3TestMemberDto s3TestDto, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        // S3Uploader.upload(업로드 할 이미지 파일, S3 디렉토리명) : S3에 저장된 이미지의 주소(url) 반환
        return s3Uploader.upload(multipartFile, "test");
    }

}
