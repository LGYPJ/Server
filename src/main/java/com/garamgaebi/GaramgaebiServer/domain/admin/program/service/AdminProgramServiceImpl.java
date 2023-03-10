package com.garamgaebi.GaramgaebiServer.domain.admin.program.service;

import com.garamgaebi.GaramgaebiServer.domain.admin.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.admin.program.repository.AdminPresentationRepository;
import com.garamgaebi.GaramgaebiServer.domain.admin.program.repository.AdminProgramRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Presentation;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.service.GameServiceImpl;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ProgramOpenEvent;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.util.s3.S3Uploader;
import com.garamgaebi.GaramgaebiServer.global.util.scheduler.event.DeleteProgramEvent;
import com.garamgaebi.GaramgaebiServer.global.util.scheduler.event.PatchProgramEvent;
import com.garamgaebi.GaramgaebiServer.global.util.scheduler.event.PostProgramEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProgramServiceImpl implements AdminProgramService {

    private final AdminProgramRepository adminProgramRepository;
    private final AdminPresentationRepository adminPresentationRepository;
    private final GameServiceImpl gameService;

    private final ApplicationEventPublisher publisher;
    private final S3Uploader s3Uploader;

    // ????????? ?????? ??????
    @Transactional(readOnly = true)
    @Override
    public List<GetProgramRes> findSeminarList(ProgramPayStatus payment, ProgramStatus status, LocalDateTime start, LocalDateTime end) {
        List<Program> programs = adminProgramRepository.findAdminSearchList(ProgramType.SEMINAR, payment, status, start, end);
        List<GetProgramRes> getProgramRes = new ArrayList<GetProgramRes>();

        for(Program program : programs) {
            getProgramRes.add(GetProgramRes.builder()
                            .programIdx(program.getIdx())
                            .title(program.getTitle())
                            .fee(program.getFee())
                            .location(program.getLocation())
                            .date(program.getDate())
                            .closeDate(program.getEndDate())
                            .status(program.getStatus())
                    .build());
        }

        return getProgramRes;
    }

    // ????????? ??? ??????
    @Transactional(readOnly = true)
    @Override
    public GetProgramDto findSeminar(Long seminarIdx) {
        Program program = adminProgramRepository.findById(seminarIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(program.getStatus() == ProgramStatus.DELETE || program.getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return GetProgramDto.builder()
                .idx(program.getIdx())
                .title(program.getTitle())
                .location(program.getLocation())
                .fee(program.getFee())
                .date(program.getDate())
                .closeDate(program.getEndDate())
                .build();

    }

    // ????????? ??????
    @Transactional
    @Override
    public ProgramRes addSeminar(SeminarDto seminarDto) {


        Program program = adminProgramRepository.save(Program.builder()
                .title(seminarDto.getTitle())
                .date(seminarDto.getDate())
                .fee(seminarDto.getFee())
                .location(seminarDto.getLocation())
                .programType(ProgramType.SEMINAR)
                .build());

        return new ProgramRes(program.getIdx());
    }

    // ???????????? ?????? ??????
    @Transactional(readOnly = true)
    @Override
    public List<GetPresentationDto> findPresentationList(Long seminarIdx) {
        Program program = adminProgramRepository.findById(seminarIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(program.getStatus() == ProgramStatus.DELETE || program.getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        List<GetPresentationDto> presentationDtos = new ArrayList<GetPresentationDto>();

        for(Presentation presentation : program.getPresentations()) {
            presentationDtos.add(GetPresentationDto.builder()
                            .idx(presentation.getIdx())
                            .title(presentation.getTitle())
                            .nickname(presentation.getNickname())
                            .content(presentation.getContent())
                            .organization(presentation.getOrganization())
                            .profileImg(presentation.getProfileImg())
                            .presentationUrl(presentation.getPresentationUrl())
                    .build());
        }

        return presentationDtos;
    }

    // ???????????? ??????
    @Transactional(readOnly = true)
    @Override
    public GetPresentationDto findPresentation(Long presentationIdx) {
        Presentation presentation = adminPresentationRepository.findById(presentationIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        return GetPresentationDto.builder()
                .idx(presentation.getIdx())
                .title(presentation.getTitle())
                .nickname(presentation.getNickname())
                .content(presentation.getContent())
                .organization(presentation.getOrganization())
                .profileImg(presentation.getProfileImg())
                .presentationUrl(presentation.getPresentationUrl())
                .build();
    }


    // ???????????? ??????
    @Transactional
    @Override
    public PresentationRes addPresentation(Long seminarIdx, PostPresentationDto postPresentationDto, MultipartFile multipartFile) {
        Optional<Program> seminarWrapper = adminProgramRepository.findById(seminarIdx);

        if(seminarWrapper == null) {
            throw new RestApiException(ErrorCode.NOT_EXIST_SEMINAR);
        }
        Program seminar = seminarWrapper.get();
        if(seminar.getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_EXIST_SEMINAR);
        }

        Presentation presentation = adminPresentationRepository.save(Presentation.builder()
                        .title(postPresentationDto.getTitle())
                        .nickname(postPresentationDto.getNickname())
                        .organization(postPresentationDto.getOrganization())
                        .program(seminar)
                        .content(postPresentationDto.getContent())
                .build());

        if(multipartFile != null) {
            try {
                String fileName = "Presentation" + presentation.getIdx() + "_Image.png";
                presentation.setProfileImg(uploadImgToS3(multipartFile, fileName));
            } catch (Exception e) {
                throw new RestApiException(ErrorCode.FAIL_IMAGE_UPLOAD);
            }
        }

        return new PresentationRes(presentation.getIdx());
    }

    // ???????????? ??????
    @Transactional
    @Override
    public PresentationRes modifyPresentation(PatchPresentationDto patchPresentationDto, MultipartFile multipartFile) {
        Presentation presentation = adminPresentationRepository.findById(patchPresentationDto.getIdx()).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_PREWRAPPER));


        presentation.setTitle(patchPresentationDto.getTitle());
        presentation.setNickname(patchPresentationDto.getNickname());
        presentation.setOrganization(patchPresentationDto.getOrganization());
        presentation.setContent(patchPresentationDto.getContent());
        presentation.setPresentationUrl(patchPresentationDto.getPresentationUrl());

        if(multipartFile != null) {
            try {
                String fileName = "Presentation" + presentation.getIdx() + "_Image.png";
                presentation.setPresentationUrl(uploadImgToS3(multipartFile, fileName));
            } catch (Exception e) {
                throw new RestApiException(ErrorCode.FAIL_IMAGE_UPLOAD);
            }
        }

        return new PresentationRes(presentation.getIdx());

    }

    // ???????????? ??????
    @Transactional
    @Override
    public PresentationRes deletePresentation(Long presentationIdx, DeletePresentationDto deletePresentationDto) {
        Optional<Presentation> presentationWrapper = adminPresentationRepository.findById(presentationIdx);

        if(presentationWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PREWRAPPER);
        }

        Presentation presentation = presentationWrapper.get();

        presentation.getProgram().getPresentations().remove(presentation);
        adminPresentationRepository.delete(presentation);

        return new PresentationRes(presentation.getIdx());

    }

    // ???????????? ??? ?????? ??????
    @Transactional(readOnly = true)
    @Override
    public List<GetProgramRes> findNetworkingList(ProgramPayStatus payment, ProgramStatus status, LocalDateTime start, LocalDateTime end) {
        List<Program> programs = adminProgramRepository.findAdminSearchList(ProgramType.NETWORKING, payment, status, start, end);
        List<GetProgramRes> getProgramRes = new ArrayList<GetProgramRes>();

        for(Program program : programs) {
            getProgramRes.add(GetProgramRes.builder()
                    .programIdx(program.getIdx())
                    .title(program.getTitle())
                    .fee(program.getFee())
                    .location(program.getLocation())
                    .date(program.getDate())
                    .closeDate(program.getEndDate())
                    .status(program.getStatus())
                    .build());
        }

        return getProgramRes;

    }

    // ???????????? ??? ??????
    @Transactional(readOnly = true)
    @Override
    public GetProgramDto findNetworking(Long networkingIdx) {
        Program program = adminProgramRepository.findById(networkingIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(program.getStatus() == ProgramStatus.DELETE || program.getProgramType() != ProgramType.NETWORKING) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return GetProgramDto.builder()
                .idx(program.getIdx())
                .title(program.getTitle())
                .location(program.getLocation())
                .fee(program.getFee())
                .date(program.getDate())
                .closeDate(program.getEndDate())
                .build();

    }

    // ???????????? ??????
    @Transactional
    @Override
    public ProgramRes addNetworking(NetworkingDto networkingDto) {

        Program program = adminProgramRepository.save(Program.builder()
                .title(networkingDto.getTitle())
                .date(networkingDto.getDate())
                .fee(networkingDto.getFee())
                .location(networkingDto.getLocation())
                .programType(ProgramType.NETWORKING)
                .build());

        gameService.createRooms(program.getIdx());

        return new ProgramRes(program.getIdx());
    }

    // ????????? ??????
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

        // ?????? ????????? ????????? ??????????????? ??????
        publisher.publishEvent(new PatchProgramEvent(program));

        return new ProgramRes(program.getIdx());
    }


    // ???????????? ??????
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

        // ?????? ????????? ????????? ??????????????? ??????
        publisher.publishEvent(new PatchProgramEvent(program));

        return new ProgramRes(program.getIdx());
    }

    // ??? ??????
    @Transactional
    @Override
    public ProgramRes deleteProgram(Long programIdx, DeleteDto deleteDto) {

        Optional<Program> programWrapper = adminProgramRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        Program program = programWrapper.get();
        program.setStatus(ProgramStatus.DELETE);

        // ?????????????????? ??????
        publisher.publishEvent(new DeleteProgramEvent(program));

        return new ProgramRes(program.getIdx());
    }

    // ???????????? ??????
    @Transactional
    @Override
    public ProgramRes openProgram(Long programIdx) {

        Optional<Program> programWrapper = adminProgramRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        Program program = programWrapper.get();
        program.setStatus(ProgramStatus.OPEN);

        adminProgramRepository.save(program);

        // ??????????????? ??????
        publisher.publishEvent(new PostProgramEvent(program));
        // ???????????? ?????? ?????? ????????? ??????
        publisher.publishEvent(new ProgramOpenEvent(program));

        return new ProgramRes(program.getIdx());
    }


    // util ????????? : ????????? ?????????
    private String uploadImgToS3(MultipartFile multipartFile, String fileName) throws Exception {
        if (!multipartFile.isEmpty()) {
            return s3Uploader.upload(multipartFile, fileName, "presentation");
        }

        return null;
    }
}
