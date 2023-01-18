package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NetworkingServiceImpl implements NetworkingService {

    private final ProgramRepository programRepository;

    // 이번달 네트워킹
    @Override
    public ProgramDto findThisMonthNetworking() {
        // 제약 조건 추가

        Optional<Program> program = programRepository.findOneByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING);

        if(program.isEmpty()) {
            return null;
        }

        return programDtoBuilder(program.get());
    }

    // 예정된 네트워킹
    @Override
    public ProgramDto findReadyNetworking() {
        // 제약 조건 추가

        // 더 보기 좋은 로직으로 바꾸기
        Optional<Program> program = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.NETWORKING);

        if(program.isEmpty()) {
            return null;
        }

        return programDtoBuilder(program.get());

    }

    // 마감된 네트워킹
    @Override
    public List<ProgramDto> findClosedNetworking() {
        // 제약조건 추가

        List<Program> programs = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.NETWORKING);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : programs) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
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
