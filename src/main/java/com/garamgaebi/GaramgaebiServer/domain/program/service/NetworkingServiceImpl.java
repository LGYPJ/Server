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

    /*
    // 세미나 모아보기 조회
    @Transactional
    @Override
    public GetProgramListRes findNetworkingCollectionList() {

        List<Program> closeNetworkings = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.NETWORKING);

        Program readyNetworking = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.NETWORKING);

        Program thisMonthNetworking = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING);

        List<ProgramDto> closeProgramDtos = new ArrayList<ProgramDto>();
        for(Program program : closeNetworkings) {
            closeProgramDtos.add(programDtoBuilder(program));
        }

        return new GetProgramListRes(
                programDtoBuilder(thisMonthNetworking),
                programDtoBuilder(readyNetworking),
                closeProgramDtos);

    }
     */

    // 이번 달 네트워킹 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findThisMonthNetworking() {

        Program thisMonthProgram = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING);

        return programDtoBuilder(thisMonthProgram);
    }


    // 예정된 세미나 조회
    @Transactional(readOnly = true)
    @Override
    public ProgramDto findReadyNetworking() {

        Program readyProgram = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.NETWORKING);

        return programDtoBuilder(readyProgram);
    }


    // 마감된 세미나 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public List<ProgramDto> findClosedNetworkingList() {
        // validation 처리

        List<Program> closePrograms = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.NETWORKING);
        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        for(Program program : closePrograms) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 홈 화면 세미나 조회
    @Transactional
    @Override
    public List<ProgramDto> findMainNetworkingList() {

        Program thisMonthNetworking = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), getLastDayOfMonth(), ProgramType.NETWORKING);

        List<Program> readyNetworkings = programRepository.findAllByDateAfterAndProgramTypeOrderByDateAsc(getLastDayOfMonth(), ProgramType.NETWORKING);

        List<Program> closeNetworkings = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.NETWORKING);

        List<ProgramDto> programDtos = new ArrayList<ProgramDto>();

        if(thisMonthNetworking != null) {
            programDtos.add(programDtoBuilder(thisMonthNetworking));
        }

        for(Program program : readyNetworkings) {
            programDtos.add(programDtoBuilder(program));
        }

        for(Program program : closeNetworkings) {
            programDtos.add(programDtoBuilder(program));
        }

        return programDtos;
    }

    // 네트워킹 상세페이지
    @Override
    public void findNetworkingDetails(Long networkingIdx) {

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

