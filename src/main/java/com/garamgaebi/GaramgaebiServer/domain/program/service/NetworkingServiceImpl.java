package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.GetProgramListRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NetworkingServiceImpl implements NetworkingService {

    private final ProgramRepository programRepository;


    // 세미나 모아보기 조회
    @Transactional
    @Override
    public GetProgramListRes findNetworkingCollectionList() {

        List<Program> closeNetworkings = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.NETWORKING);

        Optional<Program> readyNetworking = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.NETWORKING);

        Optional<Program> thisMonthNetworking = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING);

        List<ProgramDto> closeProgramDtos = new ArrayList<ProgramDto>();
        for(Program program : closeNetworkings) {
            closeProgramDtos.add(programDtoBuilder(program));
        }

        return new GetProgramListRes(
                programDtoBuilder((thisMonthNetworking.isEmpty() ? null : thisMonthNetworking.get())),
                programDtoBuilder(readyNetworking.isEmpty() ? null : readyNetworking.get()),
                closeProgramDtos
        );

    }

    // 홈 화면 세미나 조회
    @Transactional
    @Override
    public List<ProgramDto> findMainNetworkingList() {

        Optional<Program> thisMonthNetworking = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING);
        List<Program> readyNetworkings = programRepository.findAllByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.NETWORKING);
        List<Program> closeNetworkings = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.NETWORKING);

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        if(thisMonthNetworking.isPresent()) {
            programDtos.add(programDtoBuilder(thisMonthNetworking.get()));
        }

        for(Program program : readyNetworkings) {
            programDtos.add(programDtoBuilder(program));
        }

        for(Program program : closeNetworkings) {
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

