package com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.vo;

// 유저 세미나 상태
// APPLY : 신청
// CANCEL : 신청 취소
// 아래로는 관리자가 처리한 뒤 상태
// APPLY_CONFIRM : 신청자 입금 확인
// APPLY_CANCEL : 신청자 미입금 취소
// CANCEL_REFUND : 신청취소자 환불 완료
// CANCEL_CONFIRM : 신청취소자 확인 완료
public enum ApplyStatus {
    APPLY,CANCEL,APPLY_CONFIRM,APPLY_CANCEL,CANCEL_REFUND,CANCEL_CONFIRM
}