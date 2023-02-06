package com.garamgaebi.GaramgaebiServer.admin.program.service;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.admin.program.repository.AdminPresentationRepository;
import com.garamgaebi.GaramgaebiServer.admin.program.repository.AdminProgramRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.Presentation;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProgramServiceImpl implements AdminProgramService {

    private final AdminProgramRepository adminProgramRepository;
    private final AdminPresentationRepository adminPresentationRepository;
    private final GameService gameService;

    // 세미나 등록
    @Transactional
    @Override
    public ProgramRes addSeminar(SeminarDto seminarDto) {


        Program program = new Program();
        program.setTitle(seminarDto.getTitle());
        program.setDate(seminarDto.getDate());
        program.setFee(seminarDto.getFee());
        program.setLocation(seminarDto.getLocation());
        program.setStatus(ProgramStatus.READY_TO_OPEN);
        program.setProgramType(ProgramType.SEMINAR);
        program = adminProgramRepository.save(program);

        return new ProgramRes(program.getIdx());
    }

    // 발표자료 추가
    @Transactional
    @Override
    public PresentationRes addPresentation(Long seminarIdx, PostPresentationDto postPresentationDto) {
        Optional<Program> seminarWrapper = adminProgramRepository.findById(seminarIdx);

        if(seminarWrapper == null) {
            throw new RestApiException(ErrorCode.NOT_EXIST_SEMINAR);
        }
        Program seminar = seminarWrapper.get();
        if(seminar.getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_EXIST_SEMINAR);
        }

        postPresentationDto.setProgram(seminar);
        Presentation presentation = adminPresentationRepository.save(postPresentationDto.toEntity());

        return new PresentationRes(presentation.getIdx());
    }

    // 발표자료 수정
    @Transactional
    @Override
    public PresentationRes modifyPresentation(PostPresentationDto postPresentationDto) {
        Optional<Presentation> presentationWrapper = adminPresentationRepository.findById(postPresentationDto.getIdx());

        if(presentationWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PREWRAPPER);
        }

        Presentation presentation = presentationWrapper.get();

        presentation.setTitle(postPresentationDto.getTitle());
        presentation.setNickname(postPresentationDto.getNickname());
        presentation.setOrganization(postPresentationDto.getOrganization());
        presentation.setProfileImg(postPresentationDto.getProfileImgUrl());
        presentation.setContent(postPresentationDto.getContent());
        presentation.setPresentationUrl(postPresentationDto.getPresentationUrl());

        return new PresentationRes(presentation.getIdx());

    }

    // 발표자료 삭제
    @Transactional
    @Override
    public void deletePresentation(Long presentationIdx) {
        Optional<Presentation> presentationWrapper = adminPresentationRepository.findById(presentationIdx);

        if(presentationWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PREWRAPPER);
        }

        Presentation presentation = presentationWrapper.get();

        presentation.getProgram().getPresentations().remove(presentation);
        adminPresentationRepository.delete(presentation);

    }


    // 네트워킹 등록
    @Transactional
    @Override
    public ProgramRes addNetworking(NetworkingDto networkingDto) {

        Program program = new Program();
        program.setTitle(networkingDto.getTitle());
        program.setDate(networkingDto.getDate());
        program.setFee(networkingDto.getFee());
        program.setLocation(networkingDto.getLocation());
        program.setStatus(ProgramStatus.READY_TO_OPEN);
        program.setProgramType(ProgramType.NETWORKING);
        program = adminProgramRepository.save(program);

        gameService.createRooms(program.getIdx());

        return new ProgramRes(program.getIdx());
    }

    // 세미나 수정
    @Transactional
    @Override
    public ProgramRes modifySeminar(PatchSeminarDto patchSeminarDto) {
        Optional<Program> programWrapper = adminProgramRepository.findById(patchSeminarDto.getIdx());

        if(programWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        Program program = programWrapper.get();

        program.setTitle(patchSeminarDto.getTitle());
        program.setDate(patchSeminarDto.getDate());
        program.setLocation(patchSeminarDto.getLocation());
        program.setFee(patchSeminarDto.getFee());

        return new ProgramRes(program.getIdx());
    }


    // 글 수정
    @Transactional
    @Override
    public ProgramRes modifyNetworking(PatchNetworkingDto patchNetworkingDto) {
        Optional<Program> programWrapper = adminProgramRepository.findById(patchNetworkingDto.getIdx());

        if (programWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        Program program = programWrapper.get();

        program.setTitle(patchNetworkingDto.getTitle());
        program.setDate(patchNetworkingDto.getDate());
        program.setLocation(patchNetworkingDto.getLocation());
        program.setFee(patchNetworkingDto.getFee());

        return new ProgramRes(program.getIdx());
    }

    // 글 삭제
    @Transactional
    @Override
    public void deleteProgram(Long programIdx) {

        Optional<Program> programWrapper = adminProgramRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        Program program = programWrapper.get();
        program.setStatus(ProgramStatus.DELETE);
    }

    // 프로그램 오픈
    @Transactional
    @Override
    public ProgramRes openProgram(Long programIdx) {

        Optional<Program> programWrapper = adminProgramRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        Program program = programWrapper.get();
        program.setStatus(ProgramStatus.OPEN);

        return new ProgramRes(program.getIdx());
    }
}
