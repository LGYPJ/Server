package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
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

        if(member == null) {
            // 예외 처리
        }
        else {
            // 멤버 entity 안에 isActive() 메서드 넣는게 나을지 고민
            if(member.getStatus() == MemberStatus.INACTIVE) {
                // 탈퇴회원 예외 처리
            }
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

        if(member == null) {
            // 예외 처리
        }
        else {
            // 멤버 entity 안에 isActive() 메서드 넣는게 나을지 고민
            if(member.getStatus() == MemberStatus.INACTIVE) {
                // 탈퇴회원 예외 처리
            }
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
