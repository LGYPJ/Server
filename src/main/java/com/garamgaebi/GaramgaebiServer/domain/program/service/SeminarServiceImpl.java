package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ParticipantDto;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.management.relation.RelationServiceNotRegisteredException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeminarServiceImpl implements SeminarService {

    private final ProgramRepository programRepository;
    private final ProfileRepository profileRepository;


    // 이번 달 세미나 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findThisMonthSeminar() {

        List<Program> thisMonthProgram = programRepository.findThisMonthProgram(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.SEMINAR, PageRequest.of(0,1));

        if(thisMonthProgram.isEmpty()) {
            return null;
        }

        return programDtoBuilder(thisMonthProgram.get(0));
    }


    // 가장 빠른 예정된 세미나 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findReadySeminar() {

        List<Program> readyProgram = programRepository.findReadyProgram(getLastDayOfMonth(), ProgramType.SEMINAR, PageRequest.of(0,1));

        if(readyProgram.isEmpty()) {
            return null;
        }

        return programDtoBuilder(readyProgram.get(0));
    }


    // 마감된 세미나 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ProgramDto> findClosedSeminarsList() {
        // validation 처리

        List<Program> closePrograms = programRepository.findClosedProgramList(LocalDateTime.now(), ProgramType.SEMINAR);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : closePrograms) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 홈 화면 세미나 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ProgramDto> findMainSeminarList() {

        ProgramDto thisMonthSeminar = findThisMonthSeminar();
        List<Program> readySeminar = programRepository.findReadyProgramList(getLastDayOfMonth(), ProgramType.SEMINAR);
        List<ProgramDto> closeProgramDtos = findClosedSeminarsList();

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        if(thisMonthSeminar != null) {
            programDtos.add(thisMonthSeminar);
        }

        for(Program program : readySeminar) {
            programDtos.add(programDtoBuilder(program));
        }

        for(ProgramDto programDto : closeProgramDtos) {
            programDtos.add(programDto);
        }

        return programDtos;
    }

    // 세미나 상세정보 상단 부분 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramInfoDto findSeminarDetails(ProgramDetailReq programDetailReq) {
        Long memberIdx = programDetailReq.getMemberIdx();
        Long seminarIdx = programDetailReq.getProgramIdx();


        Member member = profileRepository.findMember(memberIdx);

        if(member == null || member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        Optional<Program> seminarWrapper = programRepository.findById(seminarIdx);

        if(seminarWrapper.isEmpty() || seminarWrapper.get().getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        Program seminar = seminarWrapper.get();

        return new ProgramInfoDto(
                seminar.getIdx(),
                seminar.getTitle(),
                seminar.getDate(),
                seminar.getLocation(),
                seminar.getFee(),
                seminar.getEndDate(),
                seminar.getStatus(),
                seminar.checkMemberCanApply(memberIdx));
    }

    // 세미나 신청자 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ParticipantDto> findSeminarParticipantsList(Long seminarIdx) {

        Optional<Program> seminarWrapper = programRepository.findById(seminarIdx);

        if(seminarWrapper.isEmpty() || seminarWrapper.get().getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        Program seminar = seminarWrapper.get();
        List<ParticipantDto> participantDtos = new ArrayList<ParticipantDto>();

        for(Member member : seminar.getParticipants()) {
            if(member == null) {
                participantDtos.add(null);
            }
            else {
                participantDtos.add(new ParticipantDto(
                        member.getMemberIdx(),
                        member.getNickname(),
                        member.getProfileUrl()
                ));
            }
        }
        return participantDtos;
    }

    // 세미나 상세정보 발표자료 조회
    @Transactional(readOnly = true)
    @Override
    public List<PresentationDto> findSeminarPresentationList(Long seminarIdx) {

        Optional<Program> seminarWrapper = programRepository.findById(seminarIdx);
        // validation
        if(seminarWrapper.isEmpty() || seminarWrapper.get().getProgramType() != ProgramType.SEMINAR) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        Program seminar = seminarWrapper.get();
        List<PresentationDto> presentationDtos = new ArrayList<PresentationDto>();

        for(Presentation presentation : seminar.getPresentations()) {
            presentationDtos.add(new PresentationDto(
                    presentation.getIdx(),
                    presentation.getTitle(),
                    presentation.getNickname(),
                    presentation.getProfileImg(),
                    presentation.getOrganization(),
                    presentation.getContent(),
                    presentation.getPresentationUrl()
                    ));
        }

        return presentationDtos;

    }

    // programDto 빌더
    private ProgramDto programDtoBuilder(Program program) {
        if(program == null)
            return null;

        return new ProgramDto(
                program.getIdx(),
                program.getTitle(),
                program.getDate(),
                program.getLocation(),
                program.getProgramType(),
                program.getIsPay(),
                program.getThisMonthStatus()
        );
    }

    // 다음 달 1일 00:00:00 계산
    private LocalDateTime getLastDayOfMonth() {
        LocalDate date = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        return date.atStartOfDay();
    }
}

