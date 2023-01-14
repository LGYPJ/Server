package com.garamgaebi.GaramgaebiServer.domain.seminar.entity;

import com.garamgaebi.GaramgaebiServer.domain.seminar.User;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.SeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.status.UserSeminarStatus;
import com.garamgaebi.GaramgaebiServer.domain.seminar.entity.vo.MeetingInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalTime.now;

@Entity
@Table(name = "Seminar")
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

    private int openPolicyMonth = 1;
    private int closePolicyWeek = 1;

    // == 연관관계 메서드 -- //
    public void addUserSeminar(UserSeminar userSeminar) {
        userSeminars.add(userSeminar);
        userSeminar.setSeminar(this);
    }

    public void addPresentation(Presentation presentation) {
        presentations.add(presentation);
        presentation.setSeminar(this);
    }

    // == 조회 메서드 == //
    // 세미나 상태 조회
    // CLOSED_CONFIRM으로는 관리자만 변경 가능
    public SeminarStatus getStatus() {
        if(this.status != SeminarStatus.CLOSED_CONFIRM) {
            if(LocalDateTime.now().isBefore(meetingInfo.getDate().minusMonths(1))) {
                // 오픈 예정 로직 - 세미나 날짜 1달 전 오픈
                setStatus(SeminarStatus.READY_TO_OPEN);
            }
            else if(LocalDateTime.now().isBefore(meetingInfo.getDate().minusWeeks(1))) {
                setStatus(SeminarStatus.OPEN);
            }
            else { setStatus(SeminarStatus.CLOSED); }
        }
        return status;
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
