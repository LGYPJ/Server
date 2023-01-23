package com.garamgaebi.GaramgaebiServer.programtest;

import com.garamgaebi.GaramgaebiServer.admin.program.dto.NetworkingDto;
import com.garamgaebi.GaramgaebiServer.admin.program.dto.PresentationDto;
import com.garamgaebi.GaramgaebiServer.admin.program.dto.SeminarDto;
import com.garamgaebi.GaramgaebiServer.admin.program.repository.AdminProgramRepository;
import com.garamgaebi.GaramgaebiServer.admin.program.service.AdminProgramService;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AdminTest {

    @Autowired
    AdminProgramService adminProgramService;
    @Autowired
    AdminProgramRepository adminProgramRepository;

    // 세미나 글 등록
    @Test
    public void 세미나_글_등록() {
        // given
        PresentationDto presentationDto = new PresentationDto(
                "발표자료1",
                "닉네임",
                "재학생",
                "발표자료1",
                "발표자료url",
                "발표자프로필이미지url"
        );
        List<PresentationDto> presentationDtos = new ArrayList<PresentationDto>();
        presentationDtos.add(presentationDto);
        SeminarDto seminarDto = new SeminarDto(
                "세미나1",
                now().plusDays(50),
                "위치",
                10000,
                presentationDtos
        );

        // when
        Long idx = adminProgramService.addSeminar(seminarDto);
        Optional<Program> program = adminProgramRepository.findById(idx);

        // then
        Assertions.assertThat(program.get().getTitle()).isEqualTo("세미나1");
        System.out.println(program.get().getPresentations().get(0).getTitle());

    }

    // 네트워킹 글 등록
    @Test
    public void 네트워킹_글_등록() {
        // given
        NetworkingDto networkingDto = new NetworkingDto(
                "네트워킹1",
                now().plusDays(50),
                "위치",
                10000
        );

        // when
        Long idx = adminProgramService.addNetworking(networkingDto);
        Optional<Program> program = adminProgramRepository.findById(idx);

        // then
        Assertions.assertThat(program.get().getTitle()).isEqualTo("네트워킹1");

    }
}
