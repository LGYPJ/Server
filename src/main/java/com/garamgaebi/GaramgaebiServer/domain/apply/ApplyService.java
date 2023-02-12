package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.apply.ApplyStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.program.ProgramStatus;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ApplyCancelEvent;
import com.garamgaebi.GaramgaebiServer.domain.notification.event.ApplyEvent;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public Long enroll(ApplyDto applyDto) {
        Long memberIdx = applyDto.getMemberIdx();
        Long programIdx = applyDto.getProgramIdx();

        Optional<Member> member = memberRepository.findById(memberIdx);
        Optional<Program> program = programRepository.findById(programIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        if(program.isEmpty() || program.get().getStatus() == ProgramStatus.DELETE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }
        if(program.get().getStatus() != ProgramStatus.OPEN) {
            throw new RestApiException(ErrorCode.PROGRAM_NOT_OPEN);
        }

        Apply apply = applyRepository.findByProgramAndMember(program.get(), member.get());
        Apply newApply = null;

        if(apply == null) {
            newApply = new Apply();
            newApply.setProgram(program.get());
            newApply.setMember(member.get());
            newApply.setName(applyDto.getName());
            newApply.setNickname(applyDto.getNickname());
            newApply.setPhone(applyDto.getPhone());
            newApply.setStatus(ApplyStatus.APPLY);
        }

        else if(apply.getStatus() == ApplyStatus.APPLY) {
            throw new RestApiException(ErrorCode.ALREADY_APPLY_PROGRAM);
        }

        else {
            newApply = apply;
            newApply.setName(applyDto.getName());
            newApply.setNickname(applyDto.getNickname());
            newApply.setPhone(applyDto.getPhone());
            newApply.setStatus(ApplyStatus.APPLY);
            newApply.setUpdatedAt(LocalDateTime.now());
        }


        newApply = applyRepository.save(newApply);

        // 신청완료 알림 이벤트 발생
        publisher.publishEvent(new ApplyEvent(newApply));

        return newApply.getApply_idx();
    }

    @Transactional
    public Long leave(ApplyCancelDto applyCancelDto) {
        Long memberIdx = applyCancelDto.getMemberIdx();
        Long programIdx = applyCancelDto.getProgramIdx();

        Optional<Member> member = memberRepository.findById(memberIdx);
        Optional<Program> program = programRepository.findById(programIdx);

        if(member.isEmpty() || member.get().getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        if(program.isEmpty() || program.get().getStatus() == ProgramStatus.DELETE) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }
        if(program.get().getStatus() != ProgramStatus.OPEN) {
            throw new RestApiException(ErrorCode.PROGRAM_NOT_OPEN);
        }

        Apply apply = applyRepository.findByProgramAndMember(program.get(), member.get());


        if(apply == null) {
            throw new RestApiException(ErrorCode.NOT_APPLY_STATUS);
        }

        else if(apply.getStatus() != ApplyStatus.APPLY) {
            throw new RestApiException(ErrorCode.NOT_APPLY_STATUS);
        }

        else {
            apply.setBank(applyCancelDto.getBank());
            apply.setAccount(applyCancelDto.getAccount());
            // newApply.setAccount(Encryption(applyCancelDto.getAccount())); 계좌 암호화 알고리즘으로 암호화하기
            apply.setStatus(ApplyStatus.CANCEL);
            apply.setUpdatedAt(LocalDateTime.now());
        }


        apply = applyRepository.save(apply);

        // 신청취소 알림 이벤트 발생
        publisher.publishEvent(new ApplyCancelEvent(apply));

        return apply.getApply_idx();
    }

    @Transactional(readOnly = true)
    public GetApplyRes findApplyInfo(Long memberIdx, Long programIdx) {
        Program program = programRepository.findById(programIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND));

        if(program.getStatus() == ProgramStatus.DELETE || member.getStatus() == MemberStatus.INACTIVE) {
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        Apply apply;

        try {
            apply = applyRepository.findByProgramAndMember(program, member);
        } catch(NonUniqueResultException e ) {
            // 로그 찍기 : 신청 정보가 두 개 이상
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if(apply == null) {
            // 로그 찍기 : 신청 정보가 없음
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return GetApplyRes.builder()
                .name(apply.getName())
                .nickname(apply.getNickname())
                .phone(apply.getPhone())
                .build();
    }


    /*
    public void enroll(Program program, Member member) {
        if (!applyRepository.existsByProgramAndMember(program, member)) { //프로그램에 해당 멤버가 참가한 내역이 있는지 확인.
            Apply apply = Apply.of(member); //참가 정보 생성,  Apply Entity Apply.of 수정되면 같이 수정
            program.addApply(apply); //참가 정보 등록
            applyRepository.save(apply); //참가 정보 저장
        }
    }

    @Transactional
    public Long enrollMember(ApplyDto applyDto) {
        return applyRepository.save(applyDto.toEntity()).getApply_idx();
    }

    public void leave(Program program, Member member) {
        Apply apply = applyRepository.findByProgramAndMember(program, member); //참가 내역 조회
        program.removeApply(apply); //프로그램에서 참가 내역 삭제
        applyRepository.delete(apply); //참가 정보 삭제
    }


    private ProgramRepository programRepository;

    public Program getProgramToEnroll(String path) {
        return programRepository.findProgramOnlyByPath(path)
                .orElseThrow(() -> new IllegalArgumentException(path + "에 해당하는 프로그램이 존재하지 않습니다."));
    }

     */


}
