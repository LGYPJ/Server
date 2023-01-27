package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;


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

        Apply apply = applyRepository.findByProgramAndMember(program.get(), member.get());
        Apply newApply = null;

        if(apply == null) {
            newApply = new Apply();
            newApply.setProgram(program.get());
            newApply.setMember(member.get());
            newApply.setName(applyDto.getName());
            newApply.setNickname(applyDto.getNickname());
            newApply.setPhone(applyDto.getPhone());
        }

        else if(apply.getStatus() != ApplyStatus.CANCEL) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PROGRAM);
        }

        else {
            newApply = apply;
            newApply.setName(applyDto.getName());
            newApply.setNickname(applyDto.getNickname());
            newApply.setPhone(applyDto.getPhone());
            newApply.setStatus(ApplyStatus.APPLY);
        }


        newApply = applyRepository.save(newApply);

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

        Apply apply = applyRepository.findByProgramAndMember(program.get(), member.get());


        if(apply == null) {
            throw new RestApiException(ErrorCode.NOT_ACCEPTABLE);
        }

        else if(apply.getStatus() != ApplyStatus.APPLY) {
            throw new RestApiException(ErrorCode.NOT_ACCEPTABLE);
        }

        else {
            apply.setBank(applyCancelDto.getBank());
            apply.setAccount(applyCancelDto.getAccount());
            // newApply.setAccount(Encryption(applyCancelDto.getAccount())); 계좌 암호화 알고리즘으로 암호화하기
            apply.setStatus(ApplyStatus.CANCEL);
        }


        apply = applyRepository.save(apply);

        return apply.getApply_idx();
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
