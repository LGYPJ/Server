package com.garamgaebi.GaramgaebiServer.domain.apply;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;

    public void enroll(Program program, String account) {
        if (!applyRepository.existsByProgramAndMember(program, account)) { //프로그램에 해당 멤버가 참가한 내역이 있는지 확인.
            Apply apply = Apply.of(account); //참가 정보 생성(계좌 기준), Apply Entity Apply.of 수정되면 같이 수정
            program.addApply(apply); //참가 정보 등록
            applyRepository.save(apply); //참가 정보 저장
        }
    }

    public void
}
