package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import com.garamgaebi.GaramgaebiServer.domain.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;

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



}