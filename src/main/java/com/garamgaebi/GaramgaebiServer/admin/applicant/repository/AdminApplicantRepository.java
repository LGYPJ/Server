package com.garamgaebi.GaramgaebiServer.admin.applicant.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.Apply;
import com.garamgaebi.GaramgaebiServer.domain.entity.Program;
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

    //한 프로그램에 대한 모든 신청자 찾기
    public List<Apply> findAllApplicant(long programIdx) {
        String jpql = "SELECT a FROM Apply a WHERE a.program.idx = :programIdx";
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
}
