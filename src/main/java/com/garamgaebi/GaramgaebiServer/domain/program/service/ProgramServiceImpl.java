package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private ProgramRepository programRepository;
    private ProfileRepository profileRepository;

    // 예정된 내 모임 리스트 조회
    @Override
    public List<ProgramDto> findMemberReadyProgramList(Long memberIdx) {

        Member member = profileRepository.findMember(memberIdx);

        if(member == null || member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        List<Program> programs = programRepository.findMemberReadyPrograms(member);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 지난 내 모임 리스트 조회
    @Override
    public List<ProgramDto> findMemberClosedProgramList(Long memberIdx) {

        Member member = profileRepository.findMember(memberIdx);

        if(member == null || member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        List<Program> programs = programRepository.findMemberClosedPrograms(member);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
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
}
