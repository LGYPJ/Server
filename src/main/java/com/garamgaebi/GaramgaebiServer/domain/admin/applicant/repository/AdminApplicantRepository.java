package com.garamgaebi.GaramgaebiServer.domain.admin.applicant.repository;

import com.garamgaebi.GaramgaebiServer.domain.apply.entitiy.Apply;
import com.garamgaebi.GaramgaebiServer.domain.program.entity.Program;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
@RequiredArgsConstructor
@Repository
@Slf4j
public class AdminApplicantRepository {
    private final EntityManager em;

    //program_idx를 통해 프로그램 찾기
    public Program findProgram(long id) {
        return em.find(Program.class, id);
    }

    //한 프로그램에 대한 신청자 조회
    //APPLY, APPLY_CONFIRM, APPLY_CANCEL
    public List<Apply> findApplyList(long programIdx) {
        String jpql = "SELECT a FROM Apply a WHERE a.program.idx = :programIdx AND a.status IN('APPLY','APPLY_CONFIRM','APPLY_CANCEL')";
        List<Apply> applies = em.createQuery(jpql, Apply.class)
                .setParameter("programIdx", programIdx)
                .getResultList();
        return applies;
    }

    //한 프로그램에 대한 취소자 조회
    //CANCEL, CANCEL_REFUND, CANCEL_CONFIRM
    public List<Apply> findCancelList(long programIdx) {
        String jpql = "SELECT a FROM Apply a WHERE a.program.idx = :programIdx AND a.status IN('CANCEL','CANCEL_REFUND','CANCEL_CONFIRM')";
        List<Apply> applies = em.createQuery(jpql, Apply.class)
                .setParameter("programIdx", programIdx)
                .getResultList();
        return applies;
    }

    //memberIdx를 통해 Apply에 신청자 한명 조회
    public Apply findApply(long memberIdx) {
        String jpql = "SELECT a FROM Apply a WHERE a.member.memberIdx = : memberIdx";
        Apply res = em.createQuery(jpql, Apply.class)
                .setParameter("memberIdx", memberIdx)
                .getSingleResult();
        return res;
    }

    //memberIdx와 programIdx를 통해 신청자 조회
    public Apply findOneProgramApply(long memberIdx, long programIdx) {
        String jpql = "SELECT a FROM Apply a WHERE a.member.memberIdx = :memberIdx AND a.program.idx = : programIdx";
        Apply res = em.createQuery(jpql, Apply.class)
                .setParameter("memberIdx", memberIdx)
                .setParameter("programIdx", programIdx)
                .getSingleResult();
        return res;
    }
}
