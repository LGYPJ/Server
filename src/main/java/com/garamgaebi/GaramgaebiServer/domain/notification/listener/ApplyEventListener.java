package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.notification.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface ApplyEventListener {

    // 신청 이벤트 핸들링 -> 유저가 신청 누르면 신청완료 알림
    @Async
    @EventListener
    void handleApplyEvent(ApplyEvent applyEvent);

    // 신청 취소 이벤트 -> 유저가 신청 취소 누르면 신청 취소 알림
    @Async
    @EventListener
    void handleApplyCancelEvent(ApplyCancelEvent applyCancelEvent);

    // 환불 완료 이벤트 -> 환불된 유저에게 환불 완료 알림
    @Async
    @EventListener
    void handleRefundEvent(RefundEvent refundEvent);

    @Async
    @EventListener
    void handleApplyConfirmEvent(ApplyConfirmEvent applyConfirmEvent);

    @Async
    @EventListener
    void handleNonDepositCancelEvent(NonDepositCancelEvent nonDepositCancelEvent);
}
