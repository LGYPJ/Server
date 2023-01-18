package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "QnA")
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qna_idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    //== 연관관계 메서드 == //
    public void setQnA(QnA qna){
        if(this.member != null) {
            this.member.getQnAs().remove(this);
        }
        this.member = member;
        member.getQnAs().add(this);
    }
}

