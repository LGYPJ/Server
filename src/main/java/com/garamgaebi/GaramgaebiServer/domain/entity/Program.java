package com.garamgaebi.GaramgaebiServer.domain.entity;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Program")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class Program {

    // 모임 신청 마감일 정책 : 모임 날짜 1주 전
    @Transient
    private static final int  closePolicyWeek = 1;

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "program_idx")
    private Long idx;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    private String location;
    private Integer fee;
    @Enumerated(EnumType.STRING)
    private ProgramStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType programType;

    @OneToMany(mappedBy = "program")
    private List<Apply> applies = new ArrayList<Apply>();

    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Presentation> presentations = new ArrayList<Presentation>();


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
        if(presentation.getProgram() != this) {
            presentation.setProgram(this);
        }
    }

    // == 조회 메서드 == //
    // 유료 여부 조회
    public ProgramPayStatus getIsPay() {
        if(fee > 0) {
            return ProgramPayStatus.PREMIUM;
        }
        else if(fee == 0) {
            return ProgramPayStatus.FREE;
        }
        else {
            return ProgramPayStatus.ERROR;
        }
    }


    // 프로그램 상태 조회
    public ProgramStatus getStatus() {
        if(this.status != ProgramStatus.DELETE && this.status != ProgramStatus.CLOSED_CONFIRM) {
            if (LocalDateTime.now().isAfter(getEndDate())) {
                if (this.getIsPay() == ProgramPayStatus.FREE){
                    this.setStatus(ProgramStatus.CLOSED_CONFIRM);
                    // 신청 완료 알림 발송
                }
                else {
                    this.setStatus(ProgramStatus.CLOSED);
                }
            }
        }
        return status;
    }

    // 신청 마감일 조회
    public LocalDateTime getEndDate() {
        return getDate().minusWeeks(closePolicyWeek);
    }

    // 이번달, 예정, 지난 프로그램 구분
    public ProgramThisMonthStatus getThisMonthStatus() {
        if(this.date.isBefore(LocalDateTime.now())) { return ProgramThisMonthStatus.CLOSED; }
        else if(this.date.isBefore((LocalDateTime) LocalDate.now().plusMonths(1).atStartOfDay())) { return ProgramThisMonthStatus.THIS_MONTH; }
        else { return ProgramThisMonthStatus.READY; }
    }

    // 오픈여부 조회
    public ProgramOpenStatus isOpen() {
        if(this.getStatus() == ProgramStatus.READY_TO_OPEN) { return ProgramOpenStatus.BEFORE_OPEN; }
        return ProgramOpenStatus.OPEN;
    }


    // == 비즈니스 로직 == //

    // 유저 신청 가능 여부 조회
    // CLOSED : 마감, 신청확인 중, 신청완료
    // OPEN : 신청하기(이미 신청한 사람), 신청취소()
    // READY_TO_OPEN : 상세페이지 비공개? 아니면 버튼 비활성화?
    public String checkMemberCanApply(Long memberIdx) {
        if(getStatus() == ProgramStatus.CLOSED_CONFIRM) {
            for(Apply apply : this.applies) {
                // 해당 멤버가 신청한 경우
                if (apply.getMember().getMemberIdx().equals(memberIdx) && apply.getStatus() == ApplyStatus.APPLY_CONFIRM) {
                    // 신청 완료 버튼 활성화
                    return "ApplyComplete";
                }
            }
            // 마감 버튼 활성화
            return "Closed";
        }
        else if(getStatus() == ProgramStatus.CLOSED) {
            for(Apply apply : this.applies) {
                // 해당 멤버가 신청한 경우
                if (apply.getMember().getMemberIdx().equals(memberIdx) && apply.getStatus() == ApplyStatus.APPLY) {
                    // 신청확인 중 버튼 활성화
                    return "BeforeConfirmApply";
                }
                else if (apply.getMember().getMemberIdx().equals(memberIdx) && apply.getStatus() == ApplyStatus.CANCEL) {
                    // 신청확인 중 버튼 활성화
                    return "ReadyToRefund";
                }
            }
            // 마감 버튼 활성화
            return "Closed";
        }
        else if (getStatus() == ProgramStatus.OPEN) {
            for(Apply apply : this.applies) {
                // 해당 멤버가 신청한 경우
                if (apply.getMember().getMemberIdx().equals(memberIdx) && apply.getStatus() == ApplyStatus.APPLY) {
                    // 신청 취소 버튼 활성화
                    return "ApplyCancel";
                }
            }
            // 신청 버튼 활성화
            return "Apply";
        }
        else if(getStatus() == ProgramStatus.READY_TO_OPEN) {
            // 오픈 예정 버튼 활성화
            return "NotOpen";
        }
        else {
            // Delete인 경우
            return "Error";
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
