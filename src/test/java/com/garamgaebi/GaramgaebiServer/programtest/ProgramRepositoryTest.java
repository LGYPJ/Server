package com.garamgaebi.GaramgaebiServer.programtest;

import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.service.SeminarService;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProgramRepositoryTest {

    @Autowired
    ProgramRepository programRepository;

    Program overdueSeminar1 = new Program();
    Program overdueSeminar2 = new Program();
    Program thisMonthSeminar = new Program();
    Program nextMonthSeminar = new Program();
    Program twoMonthNextSeminar = new Program();
    Program overdueNetworking1 = new Program();
    Program overdueNetworking2 = new Program();
    Program thisMonthNetworking = new Program();
    Program nextMonthNetworking = new Program();
    Program twoMonthNextNetworking = new Program();


    @Test
    public void 마감_세미나_리스트_조회() {
        // given
        makeDummy(overdueSeminar1, overdueSeminar2, thisMonthSeminar, nextMonthSeminar, twoMonthNextSeminar,
                overdueNetworking1, overdueNetworking2, thisMonthNetworking, nextMonthNetworking, twoMonthNextNetworking);

        // when
        List<Program> programs = programRepository.findAllByDateBeforeAndProgramTypeOrderByDateDesc(LocalDateTime.now(), ProgramType.SEMINAR);

        // then
        Assertions.assertThat(programs.size()).isEqualTo(2);
        Assertions.assertThat(programs.contains(overdueSeminar1));
        Assertions.assertThat(programs.contains(overdueSeminar2));
        Assertions.assertThat(!programs.contains(thisMonthSeminar));
        Assertions.assertThat(!programs.contains(nextMonthSeminar));
        Assertions.assertThat(!programs.contains(twoMonthNextSeminar));

        for(Program program : programs) {
            System.out.println(program.getTitle());
        }

    }

    @Test
    public void 이번달_세미나_조회() {
        // given
        makeDummy(overdueSeminar1, overdueSeminar2, thisMonthSeminar, nextMonthSeminar, twoMonthNextSeminar,
                overdueNetworking1, overdueNetworking2, thisMonthNetworking, nextMonthNetworking, twoMonthNextNetworking);


        // when
        Optional<Program> program = programRepository.findFirstByDateBetweenAndProgramTypeOrderByDateAsc(LocalDateTime.now(), (LocalDateTime)(LocalDate.now().plusMonths(1)).atStartOfDay(), ProgramType.SEMINAR);

        // then
        Assertions.assertThat(program.get().getIdx()).isEqualTo(thisMonthSeminar.getIdx());

        System.out.println(program.get().getTitle());
    }

    @Test
    public void 예정_세미나_조회() {
        // given
        makeDummy(overdueSeminar1, overdueSeminar2, thisMonthSeminar, nextMonthSeminar, twoMonthNextSeminar,
                overdueNetworking1, overdueNetworking2, thisMonthNetworking, nextMonthNetworking, twoMonthNextNetworking);

        // when
        Optional<Program> program = programRepository.findFirstByDateAfterAndProgramTypeOrderByDateAsc((LocalDateTime)(LocalDate.now().plusMonths(1)).atStartOfDay(), ProgramType.SEMINAR);

        // then
        Assertions.assertThat(program.get().getIdx()).isEqualTo(nextMonthSeminar.getIdx());

        System.out.println(program.get().getTitle());
    }

    @Test
    public void 예정_세미나_리스트_조회() {
        // given
        makeDummy(overdueSeminar1, overdueSeminar2, thisMonthSeminar, nextMonthSeminar, twoMonthNextSeminar,
                overdueNetworking1, overdueNetworking2, thisMonthNetworking, nextMonthNetworking, twoMonthNextNetworking);

        // when
        List<Program> programs = programRepository.findAllByDateAfterAndProgramTypeOrderByDateAsc((LocalDateTime)(LocalDate.now().plusMonths(1)).atStartOfDay(), ProgramType.SEMINAR);

        // then

        Assertions.assertThat(programs.size()).isEqualTo(2);
        Assertions.assertThat(programs.get(0).getIdx()).isEqualTo(nextMonthSeminar.getIdx());
        Assertions.assertThat(programs.get(1).getIdx()).isEqualTo(twoMonthNextSeminar.getIdx());

        for(Program program : programs) {
            System.out.println(program.getTitle());
        }
    }


    public void makeDummy(Program overdueSeminar1, Program overdueSeminar2,
                          Program thisMonthSeminar, Program nextMonthSeminar,
                          Program twoMonthNextSeminar,
                          Program overdueNetworking1, Program overdueNetworking2,
                          Program thisMonthNetworking, Program nextMonthNetworking,
                          Program twoMonthNextNetworking) {

        overdueSeminar1.setTitle("마감 세미나1");
        overdueSeminar1.setLocation("가천관");
        overdueSeminar1.setDate(LocalDateTime.now().minusDays(10));
        overdueSeminar1.setFee(0);
        overdueSeminar1.setProgramType(ProgramType.SEMINAR);
        overdueSeminar1.setApplies(null);
        overdueSeminar1.setPresentations(null);
        overdueSeminar1.setStatus(overdueSeminar1.getStatus());

        overdueSeminar2.setTitle("마감 세미나2");
        overdueSeminar2.setLocation("가천관");
        overdueSeminar2.setDate(LocalDateTime.now().minusDays(5));
        overdueSeminar2.setFee(0);
        overdueSeminar2.setProgramType(ProgramType.SEMINAR);
        overdueSeminar2.setApplies(null);
        overdueSeminar2.setPresentations(null);
        overdueSeminar2.setStatus(overdueSeminar1.getStatus());

        thisMonthSeminar.setTitle("이번달 세미나");
        thisMonthSeminar.setLocation("가천관");
        thisMonthSeminar.setDate(LocalDateTime.now().plusDays(10));
        thisMonthSeminar.setFee(0);
        thisMonthSeminar.setProgramType(ProgramType.SEMINAR);
        thisMonthSeminar.setApplies(null);
        thisMonthSeminar.setPresentations(null);
        thisMonthSeminar.setStatus(overdueSeminar1.getStatus());

        nextMonthSeminar.setTitle("다음 세미나");
        nextMonthSeminar.setLocation("가천관");
        nextMonthSeminar.setDate(((LocalDateTime) LocalDate.now().plusMonths(1).atStartOfDay()).plusMinutes(1));
        nextMonthSeminar.setFee(0);
        nextMonthSeminar.setProgramType(ProgramType.SEMINAR);
        nextMonthSeminar.setApplies(null);
        nextMonthSeminar.setPresentations(null);
        nextMonthSeminar.setStatus(overdueSeminar1.getStatus());

        twoMonthNextSeminar.setTitle("다다음 세미나");
        twoMonthNextSeminar.setLocation("가천관");
        twoMonthNextSeminar.setDate(LocalDateTime.now().plusDays(110));
        twoMonthNextSeminar.setFee(0);
        twoMonthNextSeminar.setProgramType(ProgramType.SEMINAR);
        twoMonthNextSeminar.setApplies(null);
        twoMonthNextSeminar.setPresentations(null);
        twoMonthNextSeminar.setStatus(overdueSeminar1.getStatus());

        overdueNetworking1.setTitle("마감 네트워킹1");
        overdueNetworking1.setLocation("가천관");
        overdueNetworking1.setDate(LocalDateTime.now().minusDays(10));
        overdueNetworking1.setFee(0);
        overdueNetworking1.setProgramType(ProgramType.NETWORKING);
        overdueNetworking1.setApplies(null);
        overdueNetworking1.setPresentations(null);
        overdueNetworking1.setStatus(overdueNetworking1.getStatus());

        overdueNetworking2.setTitle("마감 네트워킹2");
        overdueNetworking2.setLocation("가천관");
        overdueNetworking2.setDate(LocalDateTime.now().minusDays(5));
        overdueNetworking2.setFee(0);
        overdueNetworking2.setProgramType(ProgramType.NETWORKING);
        overdueNetworking2.setApplies(null);
        overdueNetworking2.setPresentations(null);
        overdueNetworking2.setStatus(overdueNetworking1.getStatus());

        thisMonthNetworking.setTitle("이번달 네트워킹");
        thisMonthNetworking.setLocation("가천관");
        thisMonthNetworking.setDate(LocalDateTime.now().plusDays(10));
        thisMonthNetworking.setFee(0);
        thisMonthNetworking.setProgramType(ProgramType.NETWORKING);
        thisMonthNetworking.setApplies(null);
        thisMonthNetworking.setPresentations(null);
        thisMonthNetworking.setStatus(overdueNetworking1.getStatus());

        nextMonthNetworking.setTitle("다음 네트워킹");
        nextMonthNetworking.setLocation("가천관");
        nextMonthNetworking.setDate(((LocalDateTime) LocalDate.now().plusMonths(1).atStartOfDay()).plusMinutes(1));
        nextMonthNetworking.setFee(0);
        nextMonthNetworking.setProgramType(ProgramType.NETWORKING);
        nextMonthNetworking.setApplies(null);
        nextMonthNetworking.setPresentations(null);
        nextMonthNetworking.setStatus(overdueNetworking1.getStatus());

        twoMonthNextNetworking.setTitle("다다음 네트워킹");
        twoMonthNextNetworking.setLocation("가천관");
        twoMonthNextNetworking.setDate(LocalDateTime.now().plusDays(110));
        twoMonthNextNetworking.setFee(0);
        twoMonthNextNetworking.setProgramType(ProgramType.NETWORKING);
        twoMonthNextNetworking.setApplies(null);
        twoMonthNextNetworking.setPresentations(null);
        twoMonthNextNetworking.setStatus(overdueNetworking1.getStatus());

        programRepository.save(overdueSeminar1);
        programRepository.save(overdueSeminar2);
        programRepository.save(thisMonthSeminar);
        programRepository.save(nextMonthSeminar);
        programRepository.save(twoMonthNextSeminar);

        programRepository.save(overdueNetworking1);
        programRepository.save(overdueNetworking2);
        programRepository.save(thisMonthNetworking);
        programRepository.save(nextMonthNetworking);
        programRepository.save(twoMonthNextNetworking);

    }

}

