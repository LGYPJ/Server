package com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class S3ProfileDtoReq {
    private MultipartFile profileImg;
    private S3ProfileReq profile;
}
