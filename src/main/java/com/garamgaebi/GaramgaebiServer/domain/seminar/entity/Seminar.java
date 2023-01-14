package com.garamgaebi.GaramgaebiServer.domain.seminar.entity;

import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.SeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.UserSeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo.MeetingInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Seminar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Seminar {
    @Id @GeneratedValue
    @Column(name = "seminar_idx")
    private Long idx;

    @Embedded
    private MeetingInfo meetingInfo;

    @Column(name = "is_pay")
    private Boolean isPay;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SeminarStatus status;

    @OneToMany(mappedBy = "seminar")
    private List<UserSeminar> userSeminars = new ArrayList<>();

    @OneToMany(mappedBy = "seminar")
    private List<Presentation> presentations = new ArrayList<>();

    // == 연관관계 메서드 -- //
    public void addUserSeminar(UserSeminar userSeminar) {
        userSeminars.add(userSeminar);
        userSeminar.setSeminar(this);
    }

    public void addPresentation(Presentation presentation) {
        presentations.add(presentation);
        presentation.setSeminar(this);
    }

    // == 비즈니스 로직 == //
    // 참가자 리스트 반환
    public List<User> getParticipantList() {
        List<User> users = new ArrayList<>();

        for(UserSeminar userSeminar : userSeminars) {
            if(userSeminar.getStatus() == UserSeminarStatus.APPLY)
                users.add(userSeminar.getUser());
        }

        return users;
    }


}
