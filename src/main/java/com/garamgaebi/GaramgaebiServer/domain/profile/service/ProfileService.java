package com.garamgaebi.GaramgaebiServer.domain.profile.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostCareerReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostEducationReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostQnaReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.PostSNSReq;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    // POST 고객센터(QnA)신청 API
    @Transactional
    public void saveQna(PostQnaReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        QnA qna = new QnA();
        qna.setMember(member);
        qna.setEmail(req.getEmail());
        qna.setCategory(req.getCategory());
        qna.setContent(req.getContent());

        profileRepository.saveQna(qna);
    }

    // POST SNS추가 API
    @Transactional
    public void saveSns(PostSNSReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        SNS sns = new SNS();
        sns.setMember(member);
        sns.setAddress(req.getAddress());

        profileRepository.saveSns(sns);
    }

    // POST 교육추가 API
    @Transactional
    public void saveEducation(PostEducationReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        Education education = new Education();
        education.setMember(member);
        education.setInstitution(req.getInstitution());
        education.setMajor(req.getMajor());
        education.setStartDate(req.getStartDate());
        if (req.getIsLearning().equals("TRUE")) {
            education.setIsLearning(IsLearning.TRUE);
        } else {
            education.setIsLearning(IsLearning.FALSE);
            education.setEndDate(req.getEndDate());
        }

        profileRepository.saveEducation(education);
    }

    // POST 경력추가 API
    @Transactional
    public void saveCareer(PostCareerReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        Career career = new Career();
        career.setMember(member);
        career.setCompany(req.getCompany());
        career.setPosition(req.getPosition());
        career.setStartDate(req.getStartDate());
        if (req.getIsWorking().equals("TRUE")) {
            career.setIsWorking(IsWorking.TRUE);
        } else {
            career.setIsWorking(IsWorking.FALSE);
            career.setEndDate(req.getEndDate());
        }

        profileRepository.saveCareer(career);
    }
}
