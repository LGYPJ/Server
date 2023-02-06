package com.garamgaebi.GaramgaebiServer.domain.profile.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class S3ProfileDto {
    private MultipartFile profileImg;
    private S3Profile profile;
}
