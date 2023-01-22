package com.garamgaebi.GaramgaebiServer.domain.profile.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    // GET 유저프로필조회 API
    @Transactional
    public GetProfileRes getProfile(long memberIdx) {

        Member member = profileRepository.findMember(memberIdx);
        GetProfileRes res = new GetProfileRes();

        res.setMemberIdx(memberIdx);
        res.setNickName(member.getNickname());
        res.setProfileEmail(member.getProfileEmail());

        List<Education> major = profileRepository.findIsLearning(memberIdx);
        List<Career> career = profileRepository.findIsWorking(memberIdx);
        if (career.isEmpty()) {
            res.setBelong(major.get(0).getInstitution());
            res.setBelong2(major.get(0).getMajor());
        } else {
            res.setBelong(career.get(0).getCompany());
            res.setBelong2(career.get(0).getPosition());
        }

        res.setContent(member.getContent());
        res.setSNSs(profileRepository.findAllSNS(memberIdx));
        res.setCareers(profileRepository.findAllCareer(memberIdx));
        res.setEducations(profileRepository.findAllEducation(memberIdx));

        return res;
    }

    //GET 유저프로필 10명 API
    @Transactional
    public List<GetProfilesRes> getProfiles() {

        List<GetProfilesRes> resList = new ArrayList<>();
        List<Member> members = profileRepository.findMembers();
        for (int i = 0; i < members.size(); i++) {

            GetProfilesRes res = new GetProfilesRes();
            Long memberIdx = members.get(i).getMemberIdx();
            String nickname = members.get(i).getNickname();
            res.setMemberIdx(memberIdx);
            res.setNickName(nickname);
            List<Education> major = profileRepository.findIsLearning(memberIdx);
            List<Career> career = profileRepository.findIsWorking(memberIdx);
            System.out.println(major.size());
            System.out.println(career.size());

            if (career.isEmpty() && major.isEmpty()) {
                res.setBelong("소속이없습니다.");
                res.setBelong2("소속이없습니다.");
                resList.add(res);
                continue;
            }

            if (career.size() > 0) {
                res.setBelong(career.get(0).getCompany());
                res.setBelong2(career.get(0).getPosition());
            } else {
                res.setBelong(major.get(0).getInstitution());
                res.setBelong2(major.get(0).getMajor());
            }
            resList.add(res);
        }
        return resList;
    }

    // POST 유저 프로필 수정 API
    @Transactional
    public void updateProfile(PostUpdateProfileReq req) {
        Member member = profileRepository.findMember(req.getMemberIdx());
        member.setNickname(req.getNickName());
        member.setProfileEmail(req.getProfileEmail());
        member.setContent(req.getContent());
    }
}
