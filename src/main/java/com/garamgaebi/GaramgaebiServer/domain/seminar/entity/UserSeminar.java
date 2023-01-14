package com.garamgaebi.GaramgaebiServer.domain.seminar.entity;

import com.garamgaebi.GaramgaebiServer.domain.seminar.User;
import com.garamgaebi.GaramgaebiServer.domain.seminar.dto.SeminarApplyReq;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.UserSeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo.BankAccount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Table(name = "UserSeminar")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class UserSeminar {

    @Id @GeneratedValue
    @Column(name = "user_seminar_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seminar_idx")
    private Seminar seminar;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserSeminarStatus status;

    @Column(name = "phone")
    private String phoneNumber;

    @Embedded
    private BankAccount bankAccount;


    // == 연관관계 메서드 == //
    public void setUser(User user) {
        this.user = user;
        user.getUserSeminar().add(this);
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
        seminar.getUserSeminars().add(this);
    }

    // == 비즈니스 로직 == //
    public void create(User user, Seminar seminar, String phoneNumber) {
        setUser(user);
        setSeminar(seminar);
        setPhoneNumber(phoneNumber);
        setStatus(UserSeminarStatus.APPLY);
    }

    public void cancel(BankAccount bankAccount) {
        setBankAccount(bankAccount);
        setStatus(UserSeminarStatus.CANCEL);
    }
}
