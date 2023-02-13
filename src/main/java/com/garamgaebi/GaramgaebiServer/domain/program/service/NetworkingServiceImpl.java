package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.apply.ApplyRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramUserButtonStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.google.api.services.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NetworkingServiceImpl implements NetworkingService {

    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;

    // 이번 달 네트워킹 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findThisMonthNetworking() {

        List<Program> thisMonthProgram = programRepository.findThisMonthProgram(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING, PageRequest.of(0,1));

        if(thisMonthProgram.isEmpty()) {
            return null;
        }

        return programDtoBuilder(thisMonthProgram.get(0));
    }


    // 가장 빠른 예정된 네트워킹 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findReadyNetworking() {

        List<Program> readyProgram = programRepository.findReadyProgram(getLastDayOfMonth(), ProgramType.NETWORKING, PageRequest.of(0,1));

        if(readyProgram.isEmpty()) {
            return null;
        }

        return programDtoBuilder(readyProgram.get(0));
    }


    // 마감된 네트워킹 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ProgramDto> findClosedNetworkingList() {
        // validation 처리

        List<Program> closePrograms = programRepository.findClosedProgramList(LocalDateTime.now(), ProgramType.NETWORKING);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : closePrograms) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 홈 화면 네트워킹 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ProgramDto> findMainNetworkingList() {

        ProgramDto thisMonthNetworking = findThisMonthNetworking();

        List<Program> readyNetworkings = programRepository.findReadyProgramList(getLastDayOfMonth(), ProgramType.NETWORKING);

        List<ProgramDto> closeNetworkings = findClosedNetworkingList();

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        if(thisMonthNetworking != null) {
            programDtos.add(thisMonthNetworking);
        }

        for(Program program : readyNetworkings) {
            programDtos.add(programDtoBuilder(program));
        }

        for(ProgramDto programDto : closeNetworkings) {
            programDtos.add(programDto);
        }

        return programDtos;
    }

    // 네트워킹 상세페이지 상단 정보
    @Transactional(readOnly = true)
    @Override
    public ProgramInfoDto findNetworkingDetails(Long networkingIdx, Long memberIdx) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            // 없는 멤버 예외 처리
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        Optional<Program> networkingWrapper = programRepository.findById(networkingIdx);

        if(networkingWrapper.isEmpty()
                || networkingWrapper.get().getProgramType() != ProgramType.NETWORKING
                || networkingWrapper.get().getStatus() == ProgramStatus.DELETE) {
            // 없는 네트워킹 예외 처리
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        Program networking = networkingWrapper.get();

        ProgramInfoDto programInfoDto = new ProgramInfoDto(
                networking.getIdx(),
                networking.getTitle(),
                networking.getDate(),
                networking.getLocation(),
                networking.getFee(),
                networking.getEndDate(),
                networking.getStatus(),
                networking.checkMemberCanApply(memberIdx));

        if(programInfoDto.getUserButtonStatus() == ProgramUserButtonStatus.ERROR)
            throw new RestApiException(ErrorCode.FAIL_ACCESS_PROGRAM);

        return programInfoDto;
    }

    // 네트워킹 신청자 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public GetParticipantsRes findNetworkingParticipantsList(Long networkingIdx, Long memberIdx) {

        Optional<Member> memberWrapper = memberRepository.findById(memberIdx);

        if(memberWrapper.isEmpty() || memberWrapper.get().getStatus() == MemberStatus.INACTIVE) {
            // 없는 멤버 예외 처리
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        Optional<Program> networkingWrapper = programRepository.findById(networkingIdx);

        if(networkingWrapper.isEmpty()
                || networkingWrapper.get().getProgramType() != ProgramType.NETWORKING
                || networkingWrapper.get().getStatus() == ProgramStatus.DELETE) {
            // 없는 네트워킹 예외 처리
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        if(networkingWrapper.get().getStatus() == ProgramStatus.READY_TO_OPEN) {
            throw new RestApiException(ErrorCode.FAIL_ACCESS_PROGRAM);
        }

        Program networking = networkingWrapper.get();
        List<ParticipantDto> participantDtos = new ArrayList<ParticipantDto>();
        Boolean isApply = false;

        // 요정한 유저가 리스트에 있는지 check
        if(networking.getParticipants().contains(memberWrapper.get())) {
            participantDtos.add(new ParticipantDto(
                    memberWrapper.get().getMemberIdx(),
                    memberWrapper.get().getNickname(),
                    memberWrapper.get().getProfileUrl()
            ));
            isApply = true;
        }

        // 신청자 list DTO로 변환
        for(Member member : networking.getParticipants()) {
            if(member == null) {
                participantDtos.add(new ParticipantDto(
                        (long) -1,
                        "알수없음",
                        null
                ));
            }
            else if(member != memberWrapper.get()) {
                participantDtos.add(new ParticipantDto(
                        member.getMemberIdx(),
                        member.getNickname(),
                        member.getProfileUrl()
                ));
            }
        }

        return GetParticipantsRes.builder()
                .participantList(participantDtos)
                .isApply(isApply)
                .build();
    }

    // programDto 빌더
    private ProgramDto programDtoBuilder(Program program) {

        return new ProgramDto(
                program.getIdx(),
                program.getTitle(),
                program.getDate(),
                program.getLocation(),
                program.getProgramType(),
                program.getIsPay(),
                program.getThisMonthStatus(),
                program.isOpen()
        );
    }

    // 다음 달 1일 00:00:00 계산
    private LocalDateTime getLastDayOfMonth() {
        LocalDate date = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        return date.atStartOfDay();
    }
}

