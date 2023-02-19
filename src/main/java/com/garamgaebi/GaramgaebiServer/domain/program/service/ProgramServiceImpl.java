package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    // 예정된 내 모임 리스트 조회
    @Override
    public List<ProgramDto> findMemberReadyProgramList(Long memberIdx) {

        Member member = validMember(memberIdx);

        List<Program> programs = programRepository.findMemberReadyPrograms(member);

        return programs.stream().map(program -> program.toProgramDto()).collect(Collectors.toList());
    }

    // 지난 내 모임 리스트 조회
    @Override
    public List<ProgramDto> findMemberClosedProgramList(Long memberIdx) {

        Member member = validMember(memberIdx);

        List<Program> programs = programRepository.findMemberClosedPrograms(member);

        return programs.stream().map(program -> program.toProgramDto()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void closeProgram(Long programIdx) {

        Optional<Program> programWrapper = programRepository.findById(programIdx);

        if(programWrapper.isEmpty()) {
            log.info("PROGRAM NOT EXIST : {}", programIdx);
            return;
        }

        Program program = programWrapper.get();

        if(program.getIsPay() == ProgramPayStatus.FREE) {
            program.setStatus(ProgramStatus.CLOSED_CONFIRM);
            for(Apply apply : program.getApplies()) {
                if(apply.getStatus() == ApplyStatus.APPLY) {
                    apply.setStatus(ApplyStatus.APPLY_CONFIRM);
                }
                else if(apply.getStatus() == ApplyStatus.CANCEL){
                    apply.setStatus(ApplyStatus.CANCEL_CONFIRM);
                }
            }
        }
        else {
            program.setStatus(ProgramStatus.CLOSED);
        }
        programRepository.save(program);
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
}
