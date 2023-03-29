package com.garamgaebi.GaramgaebiServer.domain.profile.repository;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.response.GetCareerListRes;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.response.GetEducationListRes;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.response.GetSNSListRes;
import com.garamgaebi.GaramgaebiServer.domain.profile.entity.Career;
import com.garamgaebi.GaramgaebiServer.domain.profile.entity.Education;
import com.garamgaebi.GaramgaebiServer.domain.profile.entity.QnA;
import com.garamgaebi.GaramgaebiServer.domain.profile.entity.SNS;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProfileRepository {

    private final EntityManager em;

    public Member findMember(Long id) {
        log.info("ProfileRepository findMember id = {}" , id);
        return em.find(Member.class, id);
    }

    public SNS findSNS(Long id){
        log.info("ProfileRepository findSNS id = {}" , id);
        return em.find(SNS.class, id);}

    public void deleteSNS(SNS sns) {
        log.info("ProfileRepository deleteSNS id = {}" , sns.getMember().getMemberIdx());
        em.remove(sns);
    }
    public void saveQna(QnA qna) {
        log.info("ProfileRepository saveQna id = {}" , qna.getMember().getMemberIdx());
        em.persist(qna);
    }

    public void saveSns(SNS sns) {
        log.info("ProfileRepository saveSns id = {}" , sns.getMember().getMemberIdx());
        em.persist(sns);
    }

    public void saveEducation(Education education) {
        log.info("ProfileRepository saveEducation id = {}" , education.getMember().getMemberIdx());
        em.persist(education);
    }

    public Education findEducation(Long id){
        log.info("ProfileRepository findEducation id = {}" , id);
        return em.find(Education.class, id);}

    public void deleteEducation(Education education) {
        log.info("ProfileRepository deleteEducation id = {}" , education.getMember().getMemberIdx());
        em.remove(education);
    }

    public void saveCareer(Career career){
        log.info("ProfileRepository saveCareer id = {}" , career.getMember().getMemberIdx());
        em.persist(career);}

    public Career findCareer(Long id){
        log.info("ProfileRepository findCareer id = {}" , id);
        return em.find(Career.class, id);}

    public void deleteCareer(Career career) {
        log.info("ProfileRepository deleteCareer id = {}" , career.getMember().getMemberIdx());
        em.remove(career);
    }

    public List<GetSNSListRes> findAllSNS(Long id) {
        log.info("ProfileRepository findAllSNS id = {}" , id);
        Member member = em.find(Member.class, id);
        List<SNS> s = member.getSNSs();

        List<GetSNSListRes> snsList = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            GetSNSListRes sns = new GetSNSListRes();
            sns.setSnsIdx(s.get(i).getSnsIdx());
            sns.setAddress(s.get(i).getAddress());
            sns.setType(s.get(i).getType());
            snsList.add(sns);
        }
        return snsList;
    }

    public List<GetEducationListRes> findAllEducation(Long id) {
        log.info("ProfileRepository findAllEducation id = {}" , id);
        Member member = em.find(Member.class, id);
        List<Education> m = member.getEducations();

        List<GetEducationListRes> educations = new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {
            GetEducationListRes education = new GetEducationListRes();
            education.setEducationIdx(m.get(i).getEducationIdx());
            education.setInstitution(m.get(i).getInstitution());
            education.setMajor(m.get(i).getMajor());
            education.setIsLearning(m.get(i).getIsLearning());
            education.setStartDate(m.get(i).getStartDate());
            education.setEndDate(m.get(i).getEndDate());
            educations.add(education);
        }
        return educations;
    }
    public List<GetCareerListRes> findAllCareer(long id) {
        log.info("ProfileRepository findAllCareer id = {}" , id);
        Member member = em.find(Member.class, id);
        List<Career> c = member.getCareers();

        List<GetCareerListRes> careers = new ArrayList<>();
        for (int i = 0; i < c.size(); i++) {
            GetCareerListRes career = new GetCareerListRes();
            career.setCareerIdx(c.get(i).getCareerIdx());
            career.setCompany(c.get(i).getCompany());
            career.setPosition(c.get(i).getPosition());
            career.setIsWorking(c.get(i).getIsWorking());
            career.setStartDate(c.get(i).getStartDate());
            career.setEndDate(c.get(i).getEndDate());
            careers.add(career);
        }
        return careers;
    }
    public List<Education> findIsLearning(Long id) {
        log.info("ProfileRepository findIsLearning id = {}" , id);
        String jpql = "select e from Education e where e.member.memberIdx = :member_idx AND e.isLearning = 'TRUE'";
        List<Education> major = em.createQuery(jpql,Education.class)
                .setParameter("member_idx",id)
                .getResultList();
        return major;
    }

    public List<Career> findIsWorking(Long id) {
        log.info("ProfileRepository findIsWorking id = {}" , id);
        String jpql = "select c from Career c where c.member.memberIdx = :member_idx AND c.isWorking = 'TRUE'";
        List<Career> career = em.createQuery(jpql,Career.class)
                .setParameter("member_idx",id)
                .getResultList();
        return career;
    }

    public List<Member> findMembers() {
        log.info("ProfileRepository findMembers");
        String jpql = "SELECT m FROM Member m WHERE status = 'ACTIVE'";
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setMaxResults(11)
                .getResultList();
        return resultList;
    }

    public List<Member> findMembers2() {
        log.info("ProfileRepository findMembers2");
        String jpql = "SELECT m FROM Member m WHERE status = 'ACTIVE' ORDER BY RAND()";
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setMaxResults(11)
                .getResultList();
        return resultList;
    }

    public long countMember() {
        log.info("ProfileRepository countMember");
        Query jpql = em.createQuery("SELECT COUNT(m) FROM Member m WHERE status = 'ACTIVE'");
        Long singleResult = (Long) jpql.getSingleResult();
        return singleResult;
    }

}
