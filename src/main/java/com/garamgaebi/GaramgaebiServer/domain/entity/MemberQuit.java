package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberQuitStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "MemberQuit")
public class MemberQuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_quit_id;

    @Column(name = "member_idx", nullable = false)
    private Long memberIdx;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberQuitStatus category;

    @Builder
    public MemberQuit(Long memberIdx,
                      String content,
                      MemberQuitStatus category) {
        this.memberIdx = memberIdx;
        this.content = content;
        this.category = category;
    }
}
