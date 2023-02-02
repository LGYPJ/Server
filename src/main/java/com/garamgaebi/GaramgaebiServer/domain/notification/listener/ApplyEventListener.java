package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;

import java.util.List;

public interface ApplyEventListener {

    // 신청 이벤트 핸들링 -> 유저가 신청 누르면 신청완료 알림
    void handleApplyEvent(Apply apply);

    // 신청 취소 이벤트 -> 유저가 신청 취소 누르면 신청 취소 알림
    void handleApplyCancelEvent(Apply apply);

    // 환불 완료 이벤트 -> 환불된 유저에게 환불 완료 알림
    void handleRefundEvent(List<Apply> applyList);
}
