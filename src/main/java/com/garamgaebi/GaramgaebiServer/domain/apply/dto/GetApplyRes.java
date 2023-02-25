package com.garamgaebi.GaramgaebiServer.domain.apply.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.checkerframework.checker.units.qual.N;

@NoArgsConstructor
@Getter @Setter
public class GetApplyRes {

    private String name;
    private String nickname;
    private String phone;

    @Builder
    public GetApplyRes(String name, String nickname, String phone) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
    }
}
