package com.garamgaebi.GaramgaebiServer.domain.apply.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplyDto {
    @NotNull
    private Long memberIdx;
    @NotNull
    private Long programIdx;
    @NotEmpty
    private String name;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String phone;

    @Builder
    public ApplyDto(Long memberIdx,
                    Long programIdx,
                    String name,
                    String nickname,
                    String phone) {
        this.memberIdx = memberIdx;
        this.programIdx = programIdx;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
    }

}

