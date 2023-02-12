package com.garamgaebi.GaramgaebiServer.domain.entity.status.program;

/** 프로그램 유/무료 여부
 * PREMIUM : 유료
 * FREE : 무료
 * ERROR : 잘못된 값(음수)
 */
public enum ProgramPayStatus {
    PREMIUM, FREE, ERROR
}
