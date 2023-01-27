package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    // 예정된 내 모임 리스트 조회
    @Override
    public List<ProgramDto> findMemberReadyProgramList(Long memberIdx) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
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
    public List<ProgramDto> findMemberClosedProgramList(Long memberIdx) {

        Optional<Member> member = memberRepository.findById(memberIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        List<Program> programs = programRepository.findMemberClosedPrograms(member.get());
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
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
                program.getThisMonthStatus()
        );
    }
}
