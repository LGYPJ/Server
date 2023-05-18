package com.garamgaebi.GaramgaebiServer.domain.program.service;

import com.garamgaebi.GaramgaebiServer.domain.admin.program.repository.AdminPresentationRepository;
import com.garamgaebi.GaramgaebiServer.domain.apply.dto.ApplyDto;
import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.vo.ApplyStatus;
import com.garamgaebi.GaramgaebiServer.domain.apply.repository.ApplyRepository;
import com.garamgaebi.GaramgaebiServer.domain.apply.service.ApplyService;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.GetParticipantsRes;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramDto;
import com.garamgaebi.GaramgaebiServer.domain.program.dto.response.ProgramInfoDto;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.vo.ProgramType;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class NetworkingServiceTest {

    @Autowired
    private NetworkingService networkingService;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    @Transactional
    void createTestData() {
        // 테스트 무료 마감 네트워킹 엔티티 생성
        Program testFreeNetworkingClosed = Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_closed")
                .date(LocalDateTime.now().minusDays(10))
                .fee(0)
                .location("testLocation")
                .build();
        testFreeNetworkingClosed.setStatus(ProgramStatus.CLOSED);
        // 테스트 무료 오픈 네트워킹 엔티티 생성
        Program testFreeNetworkingOpened = Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_open")
                .date(LocalDateTime.now().plusDays(10))
                .fee(0)
                .location("testLocation")
                .build();
        testFreeNetworkingOpened.setStatus(ProgramStatus.OPEN);
        // 테스트 무료 오픈 예정 네트워킹 엔티티 생성
        Program testFreeNetworkingReady = Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_ready")
                .date(LocalDateTime.now().plusDays(20))
                .fee(0)
                .location("testLocation")
                .build();

        programRepository.save(testFreeNetworkingClosed);
        programRepository.save(testFreeNetworkingOpened);
        programRepository.save(testFreeNetworkingReady);
    }

    // 이번달 네트워킹 탐색 조회
    @DisplayName("이번달 네트워킹 한 건 조회 테스트")
    @Test
    void findThisMonthNetworking() {
        ProgramDto thisMonthProgram = networkingService.findThisMonthNetworking();

        Assertions.assertThat(LocalDateTime.now().getMonthValue()).isEqualTo(thisMonthProgram.getDate().getMonthValue());
        Assertions.assertThat(thisMonthProgram.getType() == ProgramType.NETWORKING);
    }

    // 가장 빠른 예정 네트워킹 조회
    @DisplayName("다음 달 이후 가장 빠른 네트워킹 한 건 조회 테스트")
    @Test
    void findReadyNetworking() {
        ProgramDto mostEarlyNetworking = networkingService.findReadyNetworking();

        Assertions.assertThat(mostEarlyNetworking.getDate().getMonthValue()).isEqualTo(LocalDateTime.now().plusMonths(1).getMonthValue());
    }

    // 마감 네트워킹 조회
    @DisplayName("지난 네트워킹 리스트 조회 테스트")
    @Test
    void findClosedNetworkingList() {
        List<ProgramDto> prevNetworkingList = networkingService.findClosedNetworkingList();

        for(ProgramDto prevNetworking : prevNetworkingList) {
            Assertions.assertThat(prevNetworking.getDate().isBefore(LocalDateTime.now()) && prevNetworking.getType() == ProgramType.NETWORKING);
        }
    }

    // 메인화면 네트워킹 리스트 조회
    @DisplayName("메인화면 네트워킹 리스트 조회 순서 테스트")
    @Test
    void findMainNetworkingList() {
        List<ProgramDto> mainNetworkingList = networkingService.findMainNetworkingList();

        Assertions.assertThat(mainNetworkingList.get(0).getTitle()).isEqualTo("testNetworking_open");
        Assertions.assertThat(mainNetworkingList.get(1).getTitle()).isEqualTo("testNetworking_ready");
        Assertions.assertThat(mainNetworkingList.get(2).getTitle()).isEqualTo("testNetworking_closed");
    }

    // 네트워킹 상세 조회 - 성공
    @DisplayName("네트워킹 상세 조회 - 성공")
    @Test
    @Transactional
    void findNetworkingDetails() {
        memberRepository.save(Member.builder()
                        .memberIdx(1L)
                        .content("test")
                        .nickname("test")
                        .belong("test")
                        .identifier("test")
                        .uniEmail("test")
                        .profileEmail("test")
                .build());

        Long openProgramIdx = programRepository.findByStatus(ProgramStatus.OPEN).get(0).getIdx();

        ProgramInfoDto programDto = networkingService.findNetworkingDetails(openProgramIdx, 1L);

        Assertions.assertThat(programDto.getProgramStatus()).isNotEqualTo(ProgramStatus.READY_TO_OPEN);
    }

    @DisplayName("네트워킹 상세 조회 - 존재하지 않는 멤버")
    @Test
    @Transactional
    void findNetworkingDetailsNotExistMember() {

        Long openProgramIdx = programRepository.findByStatus(ProgramStatus.OPEN).get(0).getIdx();

        Assertions.assertThatThrownBy(() -> networkingService.findNetworkingDetails(openProgramIdx, 1L))
                .isInstanceOf(RestApiException.class);
    }

    @DisplayName("네트워킹 상세 조회 - 존재하지 않는 네트워킹")
    @Test
    @Transactional
    void findNotExistNetworkingDetails() {
        memberRepository.save(Member.builder()
                .memberIdx(1L)
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Assertions.assertThatThrownBy(() -> networkingService.findNetworkingDetails(100L, 1L))
                .isInstanceOf(RestApiException.class);
    }

    @DisplayName("네트워킹 상세 조회 - 조회 불가능한 네트워킹(오픈 예정)")
    @Test
    @Transactional
    void findNetworkingDetailsAccessDenied() {
        memberRepository.save(Member.builder()
                .memberIdx(1L)
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Long readyProgramIdx = programRepository.findByStatus(ProgramStatus.READY_TO_OPEN).get(0).getIdx();

        Assertions.assertThatThrownBy(() -> networkingService.findNetworkingDetails(readyProgramIdx, 1L))
                .isInstanceOf(RestApiException.class);
    }

    // 참가자 없음
    @DisplayName("네트워킹 참가자 리스트 조회 - 없을 경우")
    @Test
    @Transactional
    void findNetworkingEmptyParticipantsList() {
        Member member = memberRepository.save(Member.builder()
                .memberIdx(1L)
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Program program = programRepository.save(Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_open")
                .date(LocalDateTime.now().plusDays(15))
                .fee(0)
                .location("testLocation")
                .build());
        program.setStatus(ProgramStatus.OPEN);

        GetParticipantsRes getParticipantsRes = networkingService.findNetworkingParticipantsList(program.getIdx(), member.getMemberIdx());

        Assertions.assertThat(getParticipantsRes.getParticipantList().isEmpty());
        Assertions.assertThat(getParticipantsRes.getIsApply()).isEqualTo(false);
    }

    @DisplayName("네트워킹 참가자 리스트 조회 - 조회 불가능한 네트워킹(오픈 예정)")
    @Test
    @Transactional
    void findNetworkingParticipantsListAccessDenied() {
        Member member = memberRepository.save(Member.builder()
                .memberIdx(1L)
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Program program = programRepository.save(Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_open")
                .date(LocalDateTime.now().plusDays(15))
                .fee(0)
                .location("testLocation")
                .build());

        Assertions.assertThatThrownBy(() -> networkingService.findNetworkingParticipantsList(program.getIdx(), member.getMemberIdx()))
                .isInstanceOf(RestApiException.class);

    }

    @DisplayName("네트워킹 참가자 리스트 조회 - 없는 멤버")
    @Test
    @Transactional
    void findNetworkingParticipantsListNotExistMember() {
        Program program = programRepository.save(Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_open")
                .date(LocalDateTime.now().plusDays(15))
                .fee(0)
                .location("testLocation")
                .build());

        Assertions.assertThatThrownBy(() -> networkingService.findNetworkingParticipantsList(program.getIdx(), 1L))
                .isInstanceOf(RestApiException.class);
    }

    @DisplayName("네트워킹 참가자 리스트 조회 - 없는 네트워킹")
    @Test
    @Transactional
    void findNetworkingParticipantsListNotExist() {
        Member member = memberRepository.save(Member.builder()
                .memberIdx(1L)
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Assertions.assertThatThrownBy(() -> networkingService.findNetworkingParticipantsList(100L, member.getMemberIdx()))
                .isInstanceOf(RestApiException.class);
    }

    // 네트워킹 참가자 리스트 조회
    @DisplayName("네트워킹 참가지 리스트 조회 - 본인")
    @Test
    @Transactional
    void findNetworkingParticipantsListHimSelf() {
        Member member = memberRepository.save(Member.builder()
                .memberIdx(1L)
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Program program = programRepository.save(Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_open")
                .date(LocalDateTime.now().plusDays(15))
                .fee(0)
                .location("testLocation")
                .build());
        program.setStatus(ProgramStatus.OPEN);

        program.addApply(Apply.builder()
                        .program(program)
                        .member(member)
                        .name("test")
                        .bank("test")
                        .phone("test")
                        .account("test")
                        .bank("test")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .status(ApplyStatus.APPLY_CONFIRM)
                .build());

        GetParticipantsRes getParticipantsRes = networkingService.findNetworkingParticipantsList(program.getIdx(), member.getMemberIdx());

        Assertions.assertThat(getParticipantsRes.getIsApply()).isTrue();
        Assertions.assertThat(getParticipantsRes.getParticipantList().get(0).getMemberIdx()).isEqualTo(member.getMemberIdx());

    }

    // 네트워킹 참가자 리스트 조회
    @DisplayName("네트워킹 참가지 리스트 조회 - 타인")
    @Test
    @Transactional
    void findNetworkingParticipantsListAnother() {
        Member member = memberRepository.save(Member.builder()
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Member memberToApply = memberRepository.save(Member.builder()
                .content("test")
                .nickname("test")
                .belong("test")
                .identifier("test")
                .uniEmail("test")
                .profileEmail("test")
                .build());

        Program program = programRepository.save(Program.builder()
                .programType(ProgramType.NETWORKING)
                .title("testNetworking_open")
                .date(LocalDateTime.now().plusDays(15))
                .fee(0)
                .location("testLocation")
                .build());
        program.setStatus(ProgramStatus.OPEN);

        program.addApply(Apply.builder()
                .program(program)
                .member(memberToApply)
                .name("test")
                .bank("test")
                .phone("test")
                .account("test")
                .bank("test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(ApplyStatus.APPLY_CONFIRM)
                .build());

        GetParticipantsRes getParticipantsRes = networkingService.findNetworkingParticipantsList(program.getIdx(), member.getMemberIdx());

        Assertions.assertThat(getParticipantsRes.getIsApply()).isFalse();
        Assertions.assertThat(getParticipantsRes.getParticipantList().get(0).getMemberIdx()).isEqualTo(memberToApply.getMemberIdx());
    }
}