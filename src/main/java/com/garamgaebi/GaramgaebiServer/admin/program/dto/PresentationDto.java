package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Presentation;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PresentationDto {

    private Long idx;
    private Program program;
    private String title;

    private String nickname;

    private String organization;

    private String content;

    private String presentationUrl;
    private String profileImgUrl;

    public Presentation toEntity() {
        return new Presentation(
                null,
                program,
                title,
                nickname,
                organization,
                content,
                presentationUrl,
                profileImgUrl
        );
    }
}
