package com.garamgaebi.GaramgaebiServer.domain.notification.entitiy.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 알림 종류
 * COLLECTIONS : 모아보기
 * SOON_CLOSE : 마감 임박
 * APPLY_COMPLETE : 신청 완료
 * APPLY_CANCEL_COMPLETE : 신청 취소 완료
 * APPLY_CONFIRM : 신청 확정
 * NON_DEPOSIT_CANCEL : 미입금 신청 취소
 * REFUND_COMPLETE : 환불 완료
 */

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    COLLECTIONS("모아보기"),
    SOON_CLOSE("마감임박"),
    APPLY_COMPLETE("신청완료"),
    APPLY_CANCEL_COMPLETE("신청취소완료"),
    APPLY_CONFIRM("신청확정"),
    NON_DEPOSIT_CANCEL("미입금취소"),
    REFUND_COMPLETE("환불완료");

    private final String kor;
}
