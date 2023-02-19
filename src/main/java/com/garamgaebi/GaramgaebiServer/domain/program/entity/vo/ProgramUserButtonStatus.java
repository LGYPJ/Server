package com.garamgaebi.GaramgaebiServer.domain.program.entity.vo;

/** 프로그램 신청 버튼 status(신청 버튼에 들어가야하는 내용)
 * APPLY : 신청
 * BEFORE_APPLY_CONFIRM : 신청 확인 중
 * APPLY_CONFIRM : 신청 완료
 * CLOSED : 마감
 * ERROR : 버튼이 뜨면 안되는 경우(오픈 안된 프로그램, 삭제된 프로그램 등)
 */

public enum ProgramUserButtonStatus {
    APPLY, BEFORE_APPLY_CONFIRM, APPLY_COMPLETE, CLOSED, ERROR
}
