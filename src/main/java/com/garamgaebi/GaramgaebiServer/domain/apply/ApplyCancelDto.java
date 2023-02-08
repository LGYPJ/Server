package com.garamgaebi.GaramgaebiServer.domain.apply;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedNotifications;

@NoArgsConstructor
@Getter
public class ApplyCancelDto {
    @NotNull
    private Long memberIdx;
    @NotNull
    private Long programIdx;
    private String bank;
    private String account;

    @Builder
    public ApplyCancelDto(Long memberIdx,
                    Long programIdx,
                    String bank,
                    String account) {
        this.memberIdx = memberIdx;
        this.programIdx = programIdx;
        this.bank = bank;
        this.account = account;
    }
}
