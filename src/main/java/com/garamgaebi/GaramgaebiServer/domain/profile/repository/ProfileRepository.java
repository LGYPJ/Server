package com.garamgaebi.GaramgaebiServer.domain.profile.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileRepository {

    private final EntityManager em;

    public Member findMember(Long id) {
        return em.find(Member.class, id);
    }

    public void saveQna(QnA qna) {
        em.persist(qna);
    }

    public void saveSns(SNS sns) {
        em.persist(sns);
    }

    public void saveEducation(Education education) {
        em.persist(education);
    }

    public void saveCareer(Career career){ em.persist(career);}

}
