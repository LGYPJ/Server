package com.garamgaebi.GaramgaebiServer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserSeminar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "bank")
    private String bank;

    @Column(name = "account")
    private String account;

    // == 연관관계 메서드 == //
    public void setUser(User user) {
        this.user = user;
        user.getUserSeminar().add(this);
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
        seminar.getUserSeminars().add(this);
    }
}
