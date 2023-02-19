package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramUserButtonStatus;
import com.garamgaebi.GaramgaebiServer.domain.ice_breaking.repository.ProgramGameroomRepository;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.GetParticipantsRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ParticipantDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramInfoDto;
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
@Transactional(readOnly = true)
public class NetworkingServiceImpl implements NetworkingService {
    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    // 이번 달 네트워킹 조회
    @Override
    public ProgramDto findThisMonthNetworking() {

        List<Program> thisMonthProgram = programRepository.findThisMonthProgram(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING, PageRequest.of(0,1));

        if(thisMonthProgram.isEmpty()) {
            return null;
        }

        return thisMonthProgram.get(0).toProgramDto();
    }


    // 가장 빠른 예정된 네트워킹 조회
    @Override
    public ProgramDto findReadyNetworking() {

        List<Program> readyProgram = programRepository.findReadyProgram(getLastDayOfMonth(), ProgramType.NETWORKING, PageRequest.of(0,1));

        if(readyProgram.isEmpty()) {
            return null;
        }

        return readyProgram.get(0).toProgramDto();
    }


    // 마감된 네트워킹 리스트 조회
    @Override
    public List<ProgramDto> findClosedNetworkingList() {
        List<Program> closePrograms = programRepository.findClosedProgramList(LocalDateTime.now(), ProgramType.NETWORKING);

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();
        closePrograms.stream().forEach(program -> programDtos.add(program.toProgramDto()));

        return programDtos;
    }

    // 홈 화면 네트워킹 리스트 조회
    @Override
    public List<ProgramDto> findMainNetworkingList() {

        ProgramDto thisMonthNetworking = findThisMonthNetworking();

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        if(thisMonthNetworking != null) {
            programDtos.add(thisMonthNetworking);
        }

        programRepository.findReadyProgramList(getLastDayOfMonth(), ProgramType.NETWORKING).stream().forEach(program -> programDtos.add(program.toProgramDto()));
        findClosedNetworkingList().forEach(program -> programDtos.add(program));


        return programDtos;
    }

    // 네트워킹 상세페이지 상단 정보
    @Override
    public ProgramInfoDto findNetworkingDetails(Long networkingIdx, Long memberIdx) {

        Member member = validMember(memberIdx);

        Program networking = validNetworking(networkingIdx);

        ProgramInfoDto programInfoDto = networking.toProgramInfoDto(member);

        if(programInfoDto.getUserButtonStatus() == ProgramUserButtonStatus.ERROR) {
            log.info("NETWORKING ACCESS DENIED : {}", networkingIdx);
            throw new RestApiException(ErrorCode.FAIL_ACCESS_PROGRAM);
        }

        return programInfoDto;
    }

    // 네트워킹 신청자 리스트 조회
    @Override
    public GetParticipantsRes findNetworkingParticipantsList(Long networkingIdx, Long memberIdx) {

        Member targetMember = validMember(memberIdx);

        Program networking = validNetworking(networkingIdx);

        List<ParticipantDto> participantDtos = new ArrayList<ParticipantDto>();
        Boolean isApply = false;

        // 요정한 유저가 리스트에 있는지 check
        if(networking.getParticipants().contains(targetMember)) {
            participantDtos.add(participantDtoBuilder(targetMember));
            isApply = true;
        }

        // 신청자 list DTO로 변환
        for(Member member : networking.getParticipants()) {
            if(member == null) {
                participantDtos.add(ParticipantDto.builder()
                            .memberIdx((long) -1)
                            .nickname("알수없음")
                            .profileImg(null)
                        .build());

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

    // networking validation
    private Program validNetworking(Long networkingIdx) {
        Program networking = programRepository.findById(networkingIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(networking.getProgramType() != ProgramType.NETWORKING || networking.getStatus() == ProgramStatus.DELETE) {
            log.info("NETWORKING NOT EXIST : {}", networkingIdx);
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        if(networking.getStatus() == ProgramStatus.READY_TO_OPEN) {
            log.info("NETWORKING ACCESS DENIED : {}", networkingIdx);
            throw new RestApiException(ErrorCode.FAIL_ACCESS_PROGRAM);
        }

        return networking;
    }

    // 다음 달 1일 00:00:00 계산
    private LocalDateTime getLastDayOfMonth() {
        LocalDate date = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        return date.atStartOfDay();
    }
}

