package com.garamgaebi.GaramgaebiServer.admin.program.service;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminProgramService {

    // 세미나 글 목록 조회
    public List<GetProgramRes> findSeminarList(ProgramPayStatus payment, ProgramStatus status, LocalDateTime start, LocalDateTime end);

    // 세미나 글 조회
    public GetProgramDto findSeminar(Long seminarIdx);

    // 세미나 등록
    public ProgramRes addSeminar(SeminarDto seminarDto);

    // 발표자료 목록 조회
    public List<GetPresentationDto> findPresentationList(Long seminarIdx);

    // 발표자료 조회
    public GetPresentationDto findPresentation(Long presentationIdx);

    // 발표자료 추가
    public PresentationRes addPresentation(Long seminarIdx, PostPresentationDto postPresentationDto, MultipartFile multipartFile);

    // 발표자료 수정
    public PresentationRes modifyPresentation(PostPresentationDto postPresentationDto, MultipartFile multipartFile);

    // 발표자료 삭제
    public PresentationRes deletePresentation(Long presentationIdx, DeletePresentationDto deletePresentationDto);

    // 네트워킹 글 목록 조회
    public List<GetProgramRes> findNetworkingList(ProgramPayStatus payment, ProgramStatus status, LocalDateTime start, LocalDateTime end);

    // 네트워킹 글 조회
    public GetProgramDto findNetworking(Long networkingIdx);

    // 네트워킹 등록
    public ProgramRes addNetworking(NetworkingDto networkingDto);

    // 세미나 수정
    public ProgramRes modifySeminar(PatchSeminarDto patchSeminarDto);

    // 네트워킹 수정
    public ProgramRes modifyNetworking(PatchNetworkingDto patchNetworkingDto);

    // 글 삭제
    public ProgramRes deleteProgram(Long programIdx, DeleteDto deleteDto);

    // 프로그램 오픈
    public ProgramRes openProgram(Long programIdx);

}
