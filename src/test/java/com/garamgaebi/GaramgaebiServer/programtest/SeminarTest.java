package com.garamgaebi.GaramgaebiServer.programtest;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class SeminarTest {

    @Autowired
    SeminarService seminarService;
    @Autowired
    ProgramRepository programRepository;



    @Test
    public void 마감_세미나_리스트_조회() {
        // given
        Program overdueProgram1 = new Program();
        Program overdueProgram2 = new Program();
        Program thisMonthProgram = new Program();
        Program nextMonthProgram = new Program();
        Program twoMonthNextProgram = new Program();

        makeDummy(overdueProgram1, overdueProgram2, thisMonthProgram, nextMonthProgram, twoMonthNextProgram);

        programRepository.save(overdueProgram1);
        programRepository.save(overdueProgram2);
        programRepository.save(thisMonthProgram);
        programRepository.save(nextMonthProgram);
        programRepository.save(twoMonthNextProgram);

        // when
        List<ProgramDto> programDtos = seminarService.findClosedSeminarList();

        // then
        Assertions.assertThat(programDtos.size()).isEqualTo(2);
        Assertions.assertThat(programDtos.contains(overdueProgram1));
        Assertions.assertThat(programDtos.contains(overdueProgram2));
        Assertions.assertThat(!programDtos.contains(thisMonthProgram));
        Assertions.assertThat(!programDtos.contains(nextMonthProgram));
        Assertions.assertThat(!programDtos.contains(twoMonthNextProgram));

        for(ProgramDto programDto : programDtos) {
            System.out.println(programDto.getTitle());
        }


    }

    @Test
    public void 이번달_세미나_조회() {
        // given
        Program overdueProgram1 = new Program();
        Program overdueProgram2 = new Program();
        Program thisMonthProgram = new Program();
        Program nextMonthProgram = new Program();
        Program twoMonthNextProgram = new Program();

        makeDummy(overdueProgram1, overdueProgram2, thisMonthProgram, nextMonthProgram, twoMonthNextProgram);

        programRepository.save(overdueProgram1);
        programRepository.save(overdueProgram2);
        programRepository.save(thisMonthProgram);
        programRepository.save(nextMonthProgram);
        programRepository.save(twoMonthNextProgram);

        // when
        ProgramDto programDto = seminarService.findThisMonthSeminar();

        // then
        Assertions.assertThat(programDto.getProgramIdx()).isEqualTo(thisMonthProgram.getIdx());

        System.out.println(programDto.getTitle());
    }

    @Test
    public void 예정_세미나_조회() {
        // given
        Program overdueProgram1 = new Program();
        Program overdueProgram2 = new Program();
        Program thisMonthProgram = new Program();
        Program nextMonthProgram = new Program();
        Program twoMonthNextProgram = new Program();

        makeDummy(overdueProgram1, overdueProgram2, thisMonthProgram, nextMonthProgram, twoMonthNextProgram);

        programRepository.save(overdueProgram1);
        programRepository.save(overdueProgram2);
        programRepository.save(thisMonthProgram);
        programRepository.save(nextMonthProgram);
        programRepository.save(twoMonthNextProgram);

        // when
        ProgramDto programDto = seminarService.findReadySeminar();

        // then
        Assertions.assertThat(programDto.getProgramIdx()).isEqualTo(twoMonthNextProgram.getIdx());

        System.out.println(programDto.getTitle());
    }


    public void makeDummy(Program overdueProgram1, Program overdueProgram2,
                          Program thisMonthProgram, Program nextMonthProgram,
                          Program twoMonthNextProgram) {

        overdueProgram1.setTitle("마감 세미나1");
        overdueProgram1.setLocation("가천관");
        overdueProgram1.setDate(LocalDateTime.now().minusDays(10));
        overdueProgram1.setFee(0);
        overdueProgram1.setProgramType(ProgramType.SEMINAR);
        overdueProgram1.setApplies(null);
        overdueProgram1.setPresentations(null);
        overdueProgram1.setStatus(overdueProgram1.getStatus());

        overdueProgram2.setTitle("마감 세미나1");
        overdueProgram2.setLocation("가천관");
        overdueProgram2.setDate(LocalDateTime.now().minusDays(5));
        overdueProgram2.setFee(0);
        overdueProgram2.setProgramType(ProgramType.SEMINAR);
        overdueProgram2.setApplies(null);
        overdueProgram2.setPresentations(null);
        overdueProgram2.setStatus(overdueProgram1.getStatus());

        thisMonthProgram.setTitle("이번달 세미나");
        thisMonthProgram.setLocation("가천관");
        thisMonthProgram.setDate(LocalDateTime.now().plusDays(10));
        thisMonthProgram.setFee(0);
        thisMonthProgram.setProgramType(ProgramType.SEMINAR);
        thisMonthProgram.setApplies(null);
        thisMonthProgram.setPresentations(null);
        thisMonthProgram.setStatus(overdueProgram1.getStatus());

        nextMonthProgram.setTitle("다음 세미나");
        nextMonthProgram.setLocation("가천관");
        nextMonthProgram.setDate(LocalDateTime.now().plusDays(60));
        nextMonthProgram.setFee(0);
        nextMonthProgram.setProgramType(ProgramType.SEMINAR);
        nextMonthProgram.setApplies(null);
        nextMonthProgram.setPresentations(null);
        nextMonthProgram.setStatus(overdueProgram1.getStatus());

        twoMonthNextProgram.setTitle("다다음 세미나");
        twoMonthNextProgram.setLocation("가천관");
        twoMonthNextProgram.setDate(LocalDateTime.now().plusDays(35));
        twoMonthNextProgram.setFee(0);
        twoMonthNextProgram.setProgramType(ProgramType.SEMINAR);
        twoMonthNextProgram.setApplies(null);
        twoMonthNextProgram.setPresentations(null);
        twoMonthNextProgram.setStatus(overdueProgram1.getStatus());

    }

}
