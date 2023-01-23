package com.garamgaebi.GaramgaebiServer.admin.program.service;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.admin.program.repository.AdminPresentationRepository;
import com.garamgaebi.GaramgaebiServer.admin.program.repository.AdminProgramRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.Presentation;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProgramServiceImpl implements AdminProgramService {

    private final AdminProgramRepository adminProgramRepository;
    private final AdminPresentationRepository adminPresentationRepository;

    // 세미나 등록
    @Transactional
    @Override
    public Long addSeminar(SeminarDto seminarDto) {
        // validation


        Program program = new Program();
        program.setTitle(seminarDto.getTitle());
        program.setDate(seminarDto.getDate());
        program.setFee(seminarDto.getFee());
        program.setLocation(seminarDto.getLocation());
        program.setStatus(ProgramStatus.READY_TO_OPEN);
        program.setProgramType(ProgramType.SEMINAR);
        program = adminProgramRepository.save(program);

        return program.getIdx();
    }

    // 발표자료 추가
    @Transactional
    @Override
    public Long addPresentation(Long seminarIdx, PresentationDto presentationDto) {
        Optional<Program> seminarWrapper = adminProgramRepository.findById(seminarIdx);

        if(seminarWrapper == null) {
            // 없는 세미나 예외처리
        }
        Program seminar = seminarWrapper.get();
        if(seminar.getProgramType() != ProgramType.SEMINAR) {
            //세미나가 아닌 프로그램 예외처리
        }

        presentationDto.setProgram(seminar);
        Presentation presentation = adminPresentationRepository.save(presentationDto.toEntity());

        return presentation.getIdx();
    }

    // 발표자료 수정
    @Transactional
    @Override
    public Long modifyPresentation(PresentationDto presentationDto) {
        Optional<Presentation> presentationWrapper = adminPresentationRepository.findById(presentationDto.getIdx());

        if(presentationWrapper.isEmpty()) {
            // 없는 발표자료 예외처리
        }

        Presentation presentation = presentationWrapper.get();

        presentation.builder(presentationDto);
        presentation = adminPresentationRepository.save(presentation);

        return presentation.getIdx();

    }

    // 발표자료 삭제
    @Transactional
    @Override
    public void deletePresentation(Long presentationIdx) {
        Optional<Presentation> presentationWrapper = adminPresentationRepository.findById(presentationIdx);

        if(presentationWrapper.isEmpty()) {
            // 없는 발표자료 예외처리
        }

        Presentation presentation = presentationWrapper.get();

        presentation.getProgram().getPresentations().remove(presentation);
        adminPresentationRepository.delete(presentation);

    }


    // 네트워킹 등록
    @Transactional
    @Override
    public Long addNetworking(NetworkingDto networkingDto) {
        // validation

        Program program = new Program();
        program.setTitle(networkingDto.getTitle());
        program.setDate(networkingDto.getDate());
        program.setFee(networkingDto.getFee());
        program.setLocation(networkingDto.getLocation());
        program.setStatus(ProgramStatus.READY_TO_OPEN);
        program.setProgramType(ProgramType.NETWORKING);
        program = adminProgramRepository.save(program);

        return program.getIdx();
    }

    // 세미나 수정
    @Transactional
    @Override
    public Long modifySeminar(PatchSeminarDto patchSeminarDto) {
        Optional<Program> programWrapper = adminProgramRepository.findById(patchSeminarDto.getIdx());

        if(programWrapper.isEmpty()) {
            // validation
        }

        Program program = programWrapper.get();

        program.setTitle(patchSeminarDto.getTitle());
        program.setDate(patchSeminarDto.getDate());
        program.setLocation(patchSeminarDto.getLocation());
        program.setFee(patchSeminarDto.getFee());

        return program.getIdx();
    }


    // 글 수정
    @Transactional
    @Override
    public Long modifyNetworking(PatchNetworkingDto patchNetworkingDto) {
        Optional<Program> programWrapper = adminProgramRepository.findById(patchNetworkingDto.getIdx());

        // validation
        if (programWrapper.isEmpty()) {
            // 없는 네트워킹 예외처리
        }

        Program program = programWrapper.get();

        program.setTitle(patchNetworkingDto.getTitle());
        program.setDate(patchNetworkingDto.getDate());
        program.setLocation(patchNetworkingDto.getLocation());
        program.setFee(patchNetworkingDto.getFee());

        return program.getIdx();
    }

    // 글 삭제
    @Transactional
    @Override
    public void deleteProgram(Long programIdx) {
        // validation

        Optional<Program> programWrapper = adminProgramRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            // 없는 프로그램 예외처리
        }

        Program program = programWrapper.get();
        program.setStatus(ProgramStatus.DELETE);
    }

    // 프로그램 오픈
    @Transactional
    @Override
    public Long openProgram(Long programIdx) {
        // validation

        Optional<Program> programWrapper = adminProgramRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            // 없는 프로그램 예외처리
        }

        Program program = programWrapper.get();
        program.setStatus(ProgramStatus.OPEN);

        return program.getIdx();
    }
}
