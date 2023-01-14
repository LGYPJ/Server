package com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BankAccount {
    @Column(name = "bank")
    private String bank;

    @Column(name = "account")
    private String account;
}
