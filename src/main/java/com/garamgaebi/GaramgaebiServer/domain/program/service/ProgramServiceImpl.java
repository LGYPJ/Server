package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramPayStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    // 예정된 내 모임 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<ProgramDto> findMemberReadyProgramList(Long memberIdx) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        List<Program> programs = programRepository.findMemberReadyPrograms(member.get());
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 지난 내 모임 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<ProgramDto> findMemberClosedProgramList(Long memberIdx) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        List<Program> programs = programRepository.findMemberClosedPrograms(member.get());
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
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
}
