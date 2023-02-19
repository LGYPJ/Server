package com.garamgaebi.GaramgaebiServer.domain.program.entity;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.PresentationDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Presentation")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@DynamicInsert
@DynamicUpdate
@Builder
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

    // DTO 변환 메서드
    public PresentationDto toPresentationDto() {
        return PresentationDto.builder()
                .presentationIdx(this.getIdx())
                .title(this.getTitle())
                .nickname(this.getNickname())
                .profileImgUrl(this.getProfileImg())
                .organization(this.getOrganization())
                .content(this.getContent())
                .presentationUrl(this.getPresentationUrl())
                .build();
    }

}