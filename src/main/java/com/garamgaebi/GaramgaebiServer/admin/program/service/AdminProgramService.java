package com.garamgaebi.GaramgaebiServer.admin.program.service;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;

public interface AdminProgramService {
    // 세미나 등록
    ProgramRes addSeminar(SeminarDto seminarDto);

    // 발표자료 추가
    PresentationRes addPresentation(Long seminarIdx, PresentationDto presentationDto);

    // 발표자료 수정
    PresentationRes modifyPresentation(PresentationDto presentationDto);

    // 발표자료 삭제
    void deletePresentation(Long presentationIdx);

    // 네트워킹 등록
    ProgramRes addNetworking(NetworkingDto networkingDto);

    // 세미나 수정
    ProgramRes modifySeminar(PatchSeminarDto patchSeminarDto);

    // 네트워킹 수정
    ProgramRes modifyNetworking(PatchNetworkingDto patchNetworkingDto);

    // 글 삭제
    void deleteProgram(Long programIdx);

    // 프로그램 오픈
    ProgramRes openProgram(Long programIdx);
}
