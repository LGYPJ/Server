package com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo;

/** 알림 종류
 * COLLECTIONS : 모아보기
 * SOON_CLOSE : 마감 임박
 * APPLY_COMPLETE : 신청 완료
 * APPLY_CANCEL_COMPLETE : 신청 취소 완료
 * APPLY_CONFIRM : 신청 확정
 * NON_DEPOSIT_CANCEL : 미입금 신청 취소
 * REFUND_COMPLETE : 환불 완료
 */
public enum NotificationType {
    COLLECTIONS, SOON_CLOSE, APPLY_COMPLETE, APPLY_CANCEL_COMPLETE, APPLY_CONFIRM, NON_DEPOSIT_CANCEL, REFUND_COMPLETE
}
