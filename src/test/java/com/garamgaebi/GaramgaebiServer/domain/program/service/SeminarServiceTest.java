package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class SeminarServiceTest {

    @Autowired
    private SeminarService seminarService;

    @DisplayName("이번달 세미나 한 건 조회 테스트")
    @Test
    @Transactional
    void findThisMonthSeminar() {
        ProgramDto thisMonthProgram = seminarService.findThisMonthSeminar();

        if(thisMonthProgram != null) {
            Assertions.assertThat(LocalDateTime.now().getMonthValue()).isEqualTo(thisMonthProgram.getDate().getMonthValue());
            Assertions.assertThat(thisMonthProgram.getType() == ProgramType.SEMINAR);
        }
    }

    @Test
    void findReadySeminar() {
    }

    @DisplayName("지난 세미나 리스트 조회 테스트")
    @Test
    @Transactional
    void findClosedSeminarsList() {
        List<ProgramDto> prevSeminarList = seminarService.findClosedSeminarsList();

        for(ProgramDto prevSeminar : prevSeminarList) {
            Assertions.assertThat(prevSeminar.getDate().isBefore(LocalDateTime.now()) && prevSeminar.getType() == ProgramType.NETWORKING);
        }
    }

    @Test
    void findMainSeminarList() {
    }

    @Test
    void findSeminarDetails() {
    }

    @Test
    void findSeminarParticipantsList() {
    }

    @Test
    void findSeminarPresentationList() {
    }
}