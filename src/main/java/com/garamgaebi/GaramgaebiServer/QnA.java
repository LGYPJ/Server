package com.garamgaebi.GaramgaebiServer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "QnA")
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long qna_idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_email")
    private User user;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String content;


}
