package com.garamgaebi.GaramgaebiServer.admin.program.dto;

import com.garamgaebi.GaramgaebiServer.domain.entity.Presentation;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Data
public class PostPresentationDto {

    private Long idx;
    private Program program;
    @NotBlank(message = "발표 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String organization;

    @NotBlank(message = "내용을 입력해주세요.")
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
