package com.garamgaebi.GaramgaebiServer.domain.profile.repository;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<SNS> findAllSNS(Long id) {
        Member member = em.find(Member.class, id);
        List<SNS> snsList = member.getSNSs();
        return snsList;
    }

    public List<Education> findAllEducation(Long id) {
        Member member = em.find(Member.class, id);
        List<Education> educationList = member.getEducations();
        return educationList;
    }

    public List<Education> findIsLearning(Long id) {
        String jpql = "select e from Education e where e.member.memberIdx = :member_idx AND e.isLearning = 'TRUE'";
        List<Education> major = em.createQuery(jpql,Education.class)
                .setParameter("member_idx",id)
                .getResultList();
        return major;
    }

    public List<Career> findIsWorking(Long id) {
        String jpql = "select c from Career c where c.member.memberIdx = :member_idx AND c.isWorking = 'TRUE'";
        List<Career> career = em.createQuery(jpql,Career.class)
                .setParameter("member_idx",id)
                .getResultList();
        return career;
    }

    public List<Career> findAllCareer(long id) {
        Member member = em.find(Member.class, id);
        List<Career> careerList = member.getCareers();
        return careerList;
    }

    public List<Member> findMembers() {
        String jpql = "SELECT m FROM Member m";
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
        return resultList;
    }

}
