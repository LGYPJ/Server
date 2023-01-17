package com.garamgaebi.GaramgaebiServer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Program")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class Program {
    // 오픈일 정책 : 모임 날짜 1달 전
    @Transient
    private static final int openPolicyMonth = 1;

    // 모임 마감일 정책 : 모임 날짜 1주 전
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

    @OneToMany(mappedBy = "program")
    private List<Presentation> presentations = new ArrayList<Presentation>();


    // == 연관관계 메서드 -- //
    public void addApply(Apply apply) {
        applies.add(apply);
        // 중복 루프 방지
        if(apply.getProgram() != this) {
            apply.setProgram(this);
        }
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
    public String getIsPay() {
        if(fee > 0) {
            return "PREMIUM";
        }
        else if(fee == 0) {
            return "FREE";
        }
        else {
            return "ERROR";
        }
    }


    // 세미나 상태 조회
    public ProgramStatus getStatus() {
        if(this.status != ProgramStatus.CLOSED_CONFIRM) {
            if(LocalDateTime.now().isBefore(getOpenDate())) {
                setStatus(ProgramStatus.READY_TO_OPEN);
            }
            else if(LocalDateTime.now().isBefore(getEndDate())) {
                setStatus(ProgramStatus.OPEN);
            }
            else if(!getIsPay().equals("FREE")) {
                setStatus(ProgramStatus.CLOSED);
            }
            else {
                setStatus(ProgramStatus.CLOSED_CONFIRM);
            }
        }
        return status;
    }

    // 오픈일 조회
    public LocalDateTime getOpenDate() {
        return getDate().minusMonths(openPolicyMonth);
    }

    // 마감일 조회
    public LocalDateTime getEndDate() {
        return getDate().minusWeeks(closePolicyWeek);
    }

}