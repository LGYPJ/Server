package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramUserButtonStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.*;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeminarServiceImpl implements SeminarService {

    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;


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
    public ProgramInfoDto findSeminarDetails(Long seminarIdx, Long memberIdx) {

        Member member = validMember(memberIdx);

        Program seminar = validSeminar(seminarIdx);

        ProgramInfoDto programInfoDto = ProgramInfoDto.builder()
                .programIdx(seminar.getIdx())
                .title(seminar.getTitle())
                .date(seminar.getDate())
                .location(seminar.getLocation())
                .fee(seminar.getFee())
                .endDate(seminar.getEndDate())
                .programStatus(seminar.getStatus())
                .userButtonStatus(seminar.checkMemberCanApply(member))
                .build();

        if(programInfoDto.getUserButtonStatus() == ProgramUserButtonStatus.ERROR) {
            log.info("SEMINAR ACCESS DENIED : {}", seminarIdx);
            throw new RestApiException(ErrorCode.FAIL_ACCESS_PROGRAM);
        }

        return programInfoDto;
    }

    // 세미나 신청자 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public GetParticipantsRes findSeminarParticipantsList(Long seminarIdx, Long memberIdx) {

        Member targetMember = validMember(memberIdx);

        Program seminar = validSeminar(seminarIdx);
        List<ParticipantDto> participantDtos = new ArrayList<ParticipantDto>();
        Boolean isApply = false;

        if(seminar.getParticipants().contains(targetMember)) {
            participantDtos.add(participantDtoBuilder(targetMember));
            isApply = true;
        }

        for(Member member : seminar.getParticipants()) {
            if(member == null) {
                participantDtos.add(new ParticipantDto(
                        (long) -1,
                        "알수없음",
                        null
                ));
            }
            else if(member != targetMember) {
                participantDtos.add(participantDtoBuilder(member));
            }
        }
        return GetParticipantsRes.builder()
                .participantList(participantDtos)
                .isApply(isApply)
                .build();
    }

    // 세미나 상세정보 발표자료 조회
    @Transactional(readOnly = true)
    @Override
    public List<PresentationDto> findSeminarPresentationList(Long seminarIdx) {

        Program seminar = validSeminar(seminarIdx);

        List<PresentationDto> presentationDtos = new ArrayList<PresentationDto>();

        for(Presentation presentation : seminar.getPresentations()) {
            presentationDtos.add(PresentationDto.builder()
                            .presentationIdx(presentation.getIdx())
                            .title(presentation.getTitle())
                            .nickname(presentation.getNickname())
                            .profileImgUrl(presentation.getProfileImg())
                            .organization(presentation.getOrganization())
                            .content(presentation.getContent())
                            .presentationUrl(presentation.getPresentationUrl())
                            .build());
        }

        return presentationDtos;

    }

    // programDto 빌더
    private ProgramDto programDtoBuilder(Program program) {

        return ProgramDto.builder()
                .programIdx(program.getIdx())
                .title(program.getTitle())
                .date(program.getDate())
                .location(program.getLocation())
                .type(program.getProgramType())
                .payment(program.getIsPay())
                .status(program.getThisMonthStatus())
                .isOpen(program.isOpen())
                .build();
    }

    // participantsDto 빌더
    private ParticipantDto participantDtoBuilder(Member member) {
        return ParticipantDto.builder()
                .memberIdx(member.getMemberIdx())
                .nickname(member.getNickname())
                .profileImg(member.getProfileUrl())
                .build();
    }

    // member validation
    private Member validMember(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return member;
    }

    // seminar validation
    private Program validSeminar(Long seminarIdx) {
        Program seminar = programRepository.findById(seminarIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(seminar.getProgramType() != ProgramType.SEMINAR || seminar.getStatus() == ProgramStatus.DELETE) {
            log.info("SEMINAR NOT EXIST : {}", seminarIdx);
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        if(seminar.getStatus() == ProgramStatus.READY_TO_OPEN) {
            log.info("SEMINAR ACCESS DENIED : {}", seminarIdx);
            throw new RestApiException(ErrorCode.FAIL_ACCESS_PROGRAM);
        }

        return seminar;
    }

    // 다음 달 1일 00:00:00 계산
    private LocalDateTime getLastDayOfMonth() {
        LocalDate date = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        return date.atStartOfDay();
    }
}

