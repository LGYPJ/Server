package com.garamgaebi.GaramgaebiServer.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class S3TestDto {
    private S3TestMemberDto s3TestMemberDto;
    private MultipartFile profileImg;
}
