package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.PostPresentationDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Presentation")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@Builder(builderMethodName = "PresentationBuilder")
public class Presentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presentation_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_idx")
    private Program program;

    private String title;
    private String nickname;
    private String organization;
    private String content;
    @Column(name = "presentation_url")
    private String presentationUrl;
    @Column(name = "profile_img")
    private String profileImg;

    public static PresentationBuilder builder(PostPresentationDto postPresentationDto) {
        return PresentationBuilder()
                .idx(postPresentationDto.getIdx())
                .program(postPresentationDto.getProgram())
                .title(postPresentationDto.getTitle())
                .nickname(postPresentationDto.getNickname())
                .organization(postPresentationDto.getOrganization())
                .content(postPresentationDto.getContent())
                .presentationUrl(postPresentationDto.getPresentationUrl())
                .profileImg(postPresentationDto.getProfileImgUrl());
    }

    // == 연관관계 메서드 == //
    public void setProgram(Program program) {
        // 기존에 연관관계가 있었다면 끊고
        if(this.program != null) {
            this.program.getPresentations().remove(this);
        }

        this.program = program;
        // 중복 루프 방지
        if(!program.getPresentations().contains(this)) {
            program.getPresentations().add(this);
        }
    }

}