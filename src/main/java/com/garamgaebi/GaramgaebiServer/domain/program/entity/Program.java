package com.garamgaebi.GaramgaebiServer.domain.program.entity;

import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.vo.ApplyStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.vo.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.*;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramInfoDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Program")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Program {

    // 모임 신청 마감일 정책 : 모임 날짜 1주 전
    @Transient
    private static final int closePolicyWeek = 1;
    @Transient
    private static final int deadlineAlertTimeInterval = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_idx")
    private Long idx;

    @Setter
    private String title;

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Setter
    private String location;
    @Setter
    private Integer fee;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProgramStatus status;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType programType;

    @OneToMany(mappedBy = "program")
    private List<Apply> applies = new ArrayList<Apply>();

    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Presentation> presentations = new ArrayList<Presentation>();

    @Builder
    public Program(String title, LocalDateTime date, String location, Integer fee, ProgramType programType) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.fee = fee;
        this.programType = programType;
        this.status = ProgramStatus.READY_TO_OPEN;
    }

    // == 연관관계 메서드 -- //
    public void addApply(Apply apply) {
        this.applies.add(apply);
        apply.attach(this);
    }

    public void removeApply(Apply apply) {
        this.applies.remove(apply);
        apply.detachProgram();
    }

    public void addPresentation(Presentation presentation) {
        presentations.add(presentation);
        // 중복 루프 방지
        if (presentation.getProgram() != this) {
            presentation.setProgram(this);
        }
    }

    // DTO 변환 메서드
    public ProgramDto toProgramDto() {
        return ProgramDto.builder()
                .programIdx(this.getIdx())
                .title(this.getTitle())
                .date(this.getDate())
                .location(this.getLocation())
                .type(this.getProgramType())
                .isOpen(this.isOpen())
                .payment(this.getIsPay())
                .status(this.getThisMonthStatus())
                .build();
    }

    public ProgramInfoDto toProgramInfoDto(Member member) {
        return ProgramInfoDto.builder()
                .programIdx(this.getIdx())
                .title(this.getTitle())
                .date(this.getDate())
                .location(this.getLocation())
                .fee(this.getFee())
                .endDate(this.getEndDate())
                .userButtonStatus(this.checkMemberCanApply(member))
                .programStatus(this.getStatus())
                .build();
    }

    // == 조회 메서드 == //
    // 유료 여부 조회
    public ProgramPayStatus getIsPay() {
        if (fee > 0) {
            return ProgramPayStatus.PREMIUM;
        } else if (fee == 0) {
            return ProgramPayStatus.FREE;
        } else {
            return ProgramPayStatus.ERROR;
        }
    }

    // 신청 마감일 조회
    public LocalDateTime getEndDate() {
        return getDate().minusWeeks(closePolicyWeek);
    }

    // 마감 임박 알림 시간 조회
    public LocalDateTime getDeadlineAlertTime() {return this.getEndDate().minusHours(deadlineAlertTimeInterval);}

    // 이번달, 예정, 지난 프로그램 구분
    public ProgramThisMonthStatus getThisMonthStatus() {
        if (this.date.isBefore(LocalDateTime.now())) {
            return ProgramThisMonthStatus.CLOSED;
        } else if (this.date.isBefore(LocalDateTime.now().plusMonths(1).withDayOfMonth(1))) {
            return ProgramThisMonthStatus.THIS_MONTH;
        } else {
            return ProgramThisMonthStatus.READY;
        }
    }

    // 오픈여부 조회
    public ProgramOpenStatus isOpen() {
        if (this.getStatus() == ProgramStatus.READY_TO_OPEN) {
            return ProgramOpenStatus.BEFORE_OPEN;
        }
        return ProgramOpenStatus.OPEN;
    }


    // == 비즈니스 로직 == //

    // 유저 신청 가능 여부 조회
    public ProgramUserButtonStatus checkMemberCanApply(Member member) {
        // 프로그램 일자 지났으면 무조건 마감 버튼
        if (LocalDateTime.now().isAfter(this.getDate())) {
            return ProgramUserButtonStatus.CLOSED;
        }
        // 프로그램 일자 이전이면서 신청 마감이고 관리자 확인된 경우 -> 신청 확정자만 신청 완료 버튼, 이외는 마감 버튼
        else if (getStatus() == ProgramStatus.CLOSED_CONFIRM) {
            for (Apply apply : this.applies) {
                // 해당 멤버가 신청했으며, 신청 확정된 경우
                if (apply.getMember() == member && apply.getStatus() == ApplyStatus.APPLY_CONFIRM) {
                    // 신청 완료 버튼 활성화
                    return ProgramUserButtonStatus.APPLY_COMPLETE;
                }
            }
            // 마감 버튼 활성화
            return ProgramUserButtonStatus.CLOSED;
        }
        // 프로그램 일자 이전이면서 신청마감이고 관리자 미확인의 경우 -> 신청자는 신청 확인중, 취소자는 마감
        else if (getStatus() == ProgramStatus.CLOSED) {
            for (Apply apply : this.applies) {
                // 해당 멤버가 신청한 경우
                if (apply.getMember() == member && apply.getStatus() == ApplyStatus.APPLY) {
                    // 신청확인 중 버튼 활성화
                    return ProgramUserButtonStatus.BEFORE_APPLY_CONFIRM;
                }
            }
            // 마감 버튼 활성화
            return ProgramUserButtonStatus.CLOSED;
        }
        // 현재 오픈된 프로그램인 경우
        else if (getStatus() == ProgramStatus.OPEN) {
            for (Apply apply : this.applies) {
                // 해당 멤버가 신청한 경우
                if (apply.getMember() == member && apply.getStatus() == ApplyStatus.APPLY) {
                    // 무료이면 신청완료
                    if(getIsPay() == ProgramPayStatus.FREE) {
                        return ProgramUserButtonStatus.APPLY_COMPLETE;
                    }
                    // 유료이면 신청확인 중
                    return ProgramUserButtonStatus.BEFORE_APPLY_CONFIRM;
                }
            }
            // 신청 안한 경우 신청 버튼 활성화
            return ProgramUserButtonStatus.APPLY;
        }
        // 상세정보 진입이 불가능한 프로그램인 경우(삭제 또는 미오픈)
        else {
            return ProgramUserButtonStatus.ERROR;
        }
    }

    // 참가자 리스트
    public List<Member> getParticipants() {
        List<Member> participants = new ArrayList<Member>();

        for(Apply apply : this.applies) {
            if(apply.getStatus() == ApplyStatus.APPLY || apply.getStatus() == ApplyStatus.APPLY_CONFIRM) {
                // 탈퇴한 유저인 경우
                if(apply.getMember().getStatus() == MemberStatus.INACTIVE) {
                    participants.add(null);
                }
                else {
                    participants.add(apply.getMember());
                }
            }
        }
        return participants;
    }

}
