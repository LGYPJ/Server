package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeminarServiceImpl implements SeminarService {

    private final ProgramRepository programRepository;


    // 마감 세미나 리스트 조회
    @Override
    public List<ProgramDto> findClosedSeminarList() {
        // 제약 조건 추가

        List<Program> programs = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.SEMINAR);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 이번달 세미나 조회
    @Override
    public ProgramDto findThisMonthSeminar() {
        // 제약 조건 추가

        Optional<Program> program = programRepository.findOneByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.SEMINAR);

        if(program.isEmpty()) {
            return null;
        }

        return programDtoBuilder(program.get());
    }

    // 오픈 예정 세미나 리스트 조회
    @Override
    public ProgramDto findReadySeminar() {
        // 제약 조건 추가

        List<Program> programs = programRepository.findByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.SEMINAR);

        if(programs.size() == 0) {
            return null;
        }

        return programDtoBuilder(programs.get(0));

    }


    private ProgramDto programDtoBuilder(Program program) {
        return new ProgramDto(
              program.getIdx(),
              program.getTitle(),
              program.getDate(),
              program.getLocation()
        );
    }

    private LocalDateTime getLastDayOfMonth() {
        LocalDate date = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        System.out.println(date);
        System.out.println(date.atStartOfDay());
        return date.atStartOfDay();
    }
}

