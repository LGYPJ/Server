package com.garamgaebi.GaramgaebiServer.domain.program.entity.vo;

/** 프로그램 상태
 * OPEN : 오픈
 * READY_TO_OPEN : 오픈 예정
 * CLOSED : 마감
 * CLOSED_CONFIRM : 마감 확인(환불 처리 완료)
 */
public enum ProgramStatus {
    OPEN, READY_TO_OPEN, CLOSED, CLOSED_CONFIRM, DELETE
}
