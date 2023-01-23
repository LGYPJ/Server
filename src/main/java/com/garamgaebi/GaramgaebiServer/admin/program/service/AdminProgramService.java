package com.garamgaebi.GaramgaebiServer.admin.program.service;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import org.springframework.transaction.annotation.Transactional;

public interface AdminProgramService {
    // 세미나 등록
    Long addSeminar(SeminarDto seminarDto);

    // 발표자료 추가
    Long addPresentation(Long seminarIdx, PresentationDto presentationDto);

    // 발표자료 수정
    Long modifyPresentation(PresentationDto presentationDto);

    // 발표자료 삭제
    void deletePresentation(Long presentationIdx);

    // 네트워킹 등록
    Long addNetworking(NetworkingDto networkingDto);

    // 세미나 수정
    Long modifySeminar(PatchSeminarDto patchSeminarDto);

    // 네트워킹 수정
    Long modifyNetworking(PatchNetworkingDto patchNetworkingDto);

    // 글 삭제
    void deleteProgram(Long programIdx);

    // 프로그램 오픈
    Long openProgram(Long programIdx);
}
