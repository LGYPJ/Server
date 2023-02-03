package com.garamgaebi.GaramgaebiServer.domain.notification.listener;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import org.springframework.stereotype.Component;

public interface ProgramEventListener {

    // 세미나 오픈 이벤트 핸들링
    public void handleSeminarOpenEvent(Program program);
    // 네트워킹 오픈 이벤트 핸들링
    public void handleNetworkingOpenEvent(Program program);
    // 프로그램 마감 임박 이벤트 핸들링
    public void handleProgramDeadlineEvent(Program program);
}
