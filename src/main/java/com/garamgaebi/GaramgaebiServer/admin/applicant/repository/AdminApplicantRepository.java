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

    public Program findProgram(long id) {
        return em.find(Program.class, id);
    }

    public List<Apply> findAllApplicant(long programIdx) {
        String jpql = "SELECT a FROM Apply a WHERE a.program.idx = :programIdx";
        List<Apply> applies = em.createQuery(jpql, Apply.class)
                .setParameter("programIdx", programIdx)
                .getResultList();
        return applies;
    }
}
