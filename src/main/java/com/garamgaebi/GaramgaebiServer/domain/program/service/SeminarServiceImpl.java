package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.apply.ApplyRepository;
import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramInfoDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeminarServiceImpl implements SeminarService {

    private final ProgramRepository programRepository;
    private final ApplyRepository applyRepository;


    /*
    // 세미나 모아보기 한페이지에
    // 이번달 세미나 조회
    @Transactional(readOnly = true)
    @Override
    public GetProgramListRes findSeminarCollectionList() {

        List<Program> closePrograms = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.SEMINAR);

        Program readyProgram = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.SEMINAR);

        Program thisMonthProgram = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.SEMINAR);

        List<ProgramDto> closeProgramDtos = new ArrayList<ProgramDto>();
        for(Program program : closePrograms) {
            closeProgramDtos.add(programDtoBuilder(program));
        }

        return new GetProgramListRes(
                programDtoBuilder(thisMonthProgram),
                programDtoBuilder(readyProgram),
                closeProgramDtos
        );
    }
     */

    // 이번 달 세미나 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findThisMonthSeminar() {

        Program thisMonthProgram = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.SEMINAR);

        return programDtoBuilder(thisMonthProgram);
    }


    // 예정된 세미나 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findReadySeminar() {

        Program readyProgram = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.SEMINAR);

        return programDtoBuilder(readyProgram);
    }


    // 마감된 세미나 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ProgramDto> findClosedSeminarsList() {
        // validation 처리

        List<Program> closePrograms = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.SEMINAR);
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

        Program thisMonthSeminar = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.SEMINAR);
        List<Program> readySeminar = programRepository.findAllByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.SEMINAR);
        List<ProgramDto> closeProgramDtos = findClosedSeminarsList();

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        if(thisMonthSeminar != null) {
            programDtos.add(programDtoBuilder(thisMonthSeminar));
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
    @Transactional
    @Override
    public ProgramInfoDto findSeminarDetails(Long memberIdx, Long seminarIdx) {
        Optional<Program> seminarWrapper = programRepository.findById(seminarIdx);
        // 유저 유효성 검사
        // Member를 직접 찾아야하나? -> 이 도메인에서 접근하는게 맞나?
        // MemberRepository에 의존해야 하나? -> 너무 멀리가는 것 같은데
        // 차라리 유효성 로직을 Apply에 위임하는게 낫나? -> 그럼 apply 도메인에 멤버 검사용 메서드가 추가될텐데 그건 좀 아닌듯

        if(seminarWrapper.isEmpty()) {
            // 없는 세미나 예외 처리
        }
        else {
            if(seminarWrapper.get().getProgramType() != ProgramType.SEMINAR) {
                // 예외 처리
            }
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

    // programDto 빌더
    private ProgramDto programDtoBuilder(Program program) {
        if(program == null)
            return null;

        return new ProgramDto(
                program.getIdx(),
                program.getTitle(),
                program.getDate(),
                program.getLocation(),
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

