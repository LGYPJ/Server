package com.garamgaebi.GaramgaebiServer.domain.seminar.entity;

import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo.PresentationDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Presentation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Presentation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "presentation_idx")
    private Long idx;

    @Column(name = "title")
    private String title;

    @Column(name = "nickname")
    private String nickanme;

    @Column(name = "organization")
    private String organization;

    @Embedded
    private PresentationDetail presentationDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seminar_idx")
    private Seminar seminar;

    // == 연관관계 메서드 == //
    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
        seminar.getPresentations().add(this);
    }

}
