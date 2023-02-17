package com.garamgaebi.GaramgaebiServer.domain.profile.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.*;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.IsLearning;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.IsWorking;
import com.garamgaebi.GaramgaebiServer.domain.entity.status.member.MemberStatus;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.repository.ProfileRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileRepository profileRepository;

    private final S3Uploader s3Uploader;

    /** POST 고객센터(QnA)신청 API*/
    @Transactional
    public void saveQna(PostQnaReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", req.getMemberIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        QnA qna = new QnA();
        qna.setMember(member);
        qna.setEmail(req.getEmail());
        qna.setCategory(req.getCategory());
        qna.setContent(req.getContent());

        profileRepository.saveQna(qna);
    }

    /** POST SNS추가 API*/
    @Transactional
    public void saveSns(PostSNSReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", req.getMemberIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        SNS sns = new SNS();
        sns.setMember(member);
        sns.setAddress(req.getAddress());
        sns.setType(req.getType());

        profileRepository.saveSns(sns);
    }

    /** PATCH SNS 수정 API*/
    @Transactional
    public Boolean updateSNS(UpdateSNSReq req) {
        SNS sns = profileRepository.findSNS(req.getSnsIdx());
        if (sns == null) {
            log.info("SNS NOT EXIST : {}", req.getSnsIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_PROFILE_INFO);
        }
        sns.setType(req.getType());
        sns.setAddress(req.getAddress());
        return true;
    }

    /** PATCH SNS 삭제 API*/
    @Transactional
    public Boolean deleteSNS(long snsIdx) {
        SNS sns = profileRepository.findSNS(snsIdx);
        if (sns == null) {
            log.info("SNS NOT EXIST : {}", snsIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_PROFILE_INFO);
        }
        profileRepository.deleteSNS(sns);
        return true;
    }

    /** POST 교육추가 API*/
    @Transactional
    public void saveEducation(PostEducationReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", req.getMemberIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        Education education = new Education();
        education.setMember(member);
        education.setInstitution(req.getInstitution());
        education.setMajor(req.getMajor());
        education.setStartDate(req.getStartDate());
        if (req.getIsLearning().equals("TRUE")) {
            education.setIsLearning(IsLearning.TRUE);
            if (member.getBelong() == null) {
                member.setBelong(req.getInstitution()+" "+req.getMajor());
            }
        } else {
            education.setIsLearning(IsLearning.FALSE);
            education.setEndDate(req.getEndDate());
        }

        profileRepository.saveEducation(education);
    }

    /** PATCH 교육 수정*/
    @Transactional
    public Boolean updateEducation(UpdateEducationReq req) {
        Education education = profileRepository.findEducation(req.getEducationIdx());
        if (education == null) {
            log.info("Education NOT EXIST : {}", req.getEducationIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_PROFILE_INFO);
        }
        education.setInstitution(req.getInstitution());
        education.setMajor(req.getMajor());
        education.setStartDate(req.getStartDate());
        if (req.getIsLearning().equals("TRUE")) {
            education.setIsLearning(IsLearning.TRUE);
            education.setEndDate(null);
        } else {
            education.setIsLearning(IsLearning.FALSE);
            education.setEndDate(req.getEndDate());
        }

        return true;
    }

    /** DELETE 교육 삭제*/
    @Transactional
    public Boolean deleteEducation(long educationIdx) {
        Education education = profileRepository.findEducation(educationIdx);
        if (education == null) {
            log.info("Education NOT EXIST : {}", educationIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_PROFILE_INFO);
        }
        profileRepository.deleteEducation(education);

        return true;
    }

    /** POST 경력추가 API*/
    @Transactional
    public void saveCareer(PostCareerReq req) {

        Member member = profileRepository.findMember(req.getMemberIdx());
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", req.getMemberIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        Career career = new Career();
        career.setMember(member);
        career.setCompany(req.getCompany());
        career.setPosition(req.getPosition());
        career.setStartDate(req.getStartDate());
        if (req.getIsWorking().equals("TRUE")) {
            career.setIsWorking(IsWorking.TRUE);
            if (member.getBelong() == null) {
                member.setBelong(req.getCompany()+" "+req.getPosition());
            }
        } else {
            career.setIsWorking(IsWorking.FALSE);
            career.setEndDate(req.getEndDate());
        }

        profileRepository.saveCareer(career);
    }

    /** PATCH 경력 수정*/
    @Transactional
    public Boolean updateCareer(UpdateCareerReq req) {
        Career career = profileRepository.findCareer(req.getCareerIdx());
        if (career == null) {
            log.info("Career NOT EXIST : {}", req.getCareerIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_PROFILE_INFO);
        }
        career.setCompany(req.getCompany());
        career.setPosition(req.getPosition());
        career.setStartDate(req.getStartDate());
        if (req.getIsWorking().equals("TRUE")) {
            career.setIsWorking(IsWorking.TRUE);
            career.setEndDate(null);
        } else {
            career.setIsWorking(IsWorking.FALSE);
            career.setEndDate(req.getEndDate());
        }

        return true;
    }

    /** DELETE 경력 삭제*/
    @Transactional
    public Boolean deleteCareer(long careerIdx) {
        Career career = profileRepository.findCareer(careerIdx);
        if (career == null) {
            log.info("Career NOT EXIST : {}", careerIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_PROFILE_INFO);
        }
        profileRepository.deleteCareer(career);

        return true;
    }

    /** GET 유저프로필조회 API*/
    @Transactional
    public GetProfileRes getProfile(long memberIdx) {
        Member member = profileRepository.findMember(memberIdx);
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        GetProfileRes res = new GetProfileRes();
        res.setMemberIdx(memberIdx);
        res.setNickName(member.getNickname());
        res.setProfileEmail(member.getProfileEmail());
        if (member.getBelong() == null) {
            findCareerOrEducation(memberIdx, res, member);
        } else {
            res.setBelong(member.getBelong());
        }
        res.setContent(member.getContent());
        res.setProfileUrl(member.getProfileUrl());
        return res;
    }

    /** 소속중인 경력이나 교육 찾기 메서드(유저프로필조회 API)*/
    private void findCareerOrEducation(long memberIdx, GetProfileRes res, Member member) {
        List<Education> major = profileRepository.findIsLearning(memberIdx);
        List<Career> career = profileRepository.findIsWorking(memberIdx);
        if (career.isEmpty() != true) {
            res.setBelong(career.get(0).getCompany() +" "+ career.get(0).getPosition());
//            member.setBelong(career.get(0).getCompany() +" "+ career.get(0).getPosition());
        } else if (major.isEmpty() != true) {
            res.setBelong(major.get(0).getInstitution() + " " + major.get(0).getMajor());
//            member.setBelong(major.get(0).getInstitution() + " " + major.get(0).getMajor());
        } else {
            res.setBelong("belong이 없습니다");
        }
    }

    /** GET 프로필 SNS 조회 API*/
    @Transactional
    public List<GetSNSList> getSNSList(long memberIdx) {
        Member member = profileRepository.findMember(memberIdx);
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        List<GetSNSList> allSNS = profileRepository.findAllSNS(memberIdx);
        return allSNS;
    }

    /** GET 프로필 경력 조회 API*/
    @Transactional
    public List<GetCareerList> getCareerList(long memberIdx) {
        Member member = profileRepository.findMember(memberIdx);
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        List<GetCareerList> allCareer = profileRepository.findAllCareer(memberIdx);
        return allCareer;
    }

    /** GET 프로필 교육 조회 API*/
    @Transactional
    public List<GetEducationList> getEducationList(long memberIdx) {
        Member member = profileRepository.findMember(memberIdx);
        if(member==null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", memberIdx);
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }
        List<GetEducationList> allEducation = profileRepository.findAllEducation(memberIdx);
        return allEducation;
    }

    /** GET 유저프로필 11명 API*/
    @Transactional
    public List<GetProfilesRes> getProfiles() {
        List<GetProfilesRes> resList = new ArrayList<>();
        //유저프로필 랜덤 11명 난수 설정
        Long count = profileRepository.countMember();
        int c = Math.toIntExact(count);
        int a;
        if (c <= 11) {
            a = 0;
        } else {
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            a = random.nextInt(c - 11);
        }

        List<Member> members = profileRepository.findMembers(a);
        for (int i = 0; i < members.size(); i++) {

            GetProfilesRes res = new GetProfilesRes();
            res.setMemberIdx(members.get(i).getMemberIdx());
            res.setNickName(members.get(i).getNickname());
            res.setBelong(members.get(i).getBelong());
            res.setProfileUrl(members.get(i).getProfileUrl());

            List<Education> major = profileRepository.findIsLearning(members.get(i).getMemberIdx());
            List<Career> career = profileRepository.findIsWorking(members.get(i).getMemberIdx());
            if (career.isEmpty() && major.isEmpty()) {
                res.setGroup("group이 없습니다.");
                res.setDetail("detail이 없습니다.");
                resList.add(res);
                continue;
            }
            if (career.size() > 0) {
                res.setGroup(career.get(0).getCompany());
                res.setDetail(career.get(0).getPosition());
            } else {
                res.setGroup(major.get(0).getInstitution());
                res.setDetail(major.get(0).getMajor());
            }
            resList.add(res);
        }
        return resList;
    }

    /** POST 유저 프로필 수정 API*/
    @Transactional
    public Long updateProfile(PostUpdateProfileReq req, MultipartFile multipartFile) {
        Member member = profileRepository.findMember(req.getMemberIdx());
        if(member == null || member.getStatus() == MemberStatus.INACTIVE) {
            log.info("MEMBER NOT EXIST : {}", req.getMemberIdx());
            throw new RestApiException(ErrorCode.NOT_EXIST_MEMBER);
        }

        member.setNickname(req.getNickname());
        member.setProfileEmail(req.getProfileEmail());
        member.setContent(req.getContent());
        member.setBelong(req.getBelong());

        String profileUrl = null;
        if(multipartFile != null) {
            if(!multipartFile.isEmpty()) {
                try {
                    // S3Uploader.upload(업로드 할 이미지 파일, S3 디렉토리명) : S3에 저장된 이미지의 주소(url) 반환
                    String fileName = "Member" + member.getMemberIdx() + "_Profile.png";
                    profileUrl = s3Uploader.upload(multipartFile, fileName, "profile");
                    member.setProfileUrl(profileUrl);
                } catch (Exception e) {
                    throw new RestApiException(ErrorCode.FAIL_IMAGE_UPLOAD);
                }
            }
            else {
                member.setProfileUrl(null);
            }
        }

        return member.getMemberIdx();
    }

    /**
     * 프로필 이미지 저장/수정 API
     */
    @Transactional
    public boolean imageProfile(S3Profile profile, String profileUrl) {
        Member member = profileRepository.findMember(profile.getMemberIdx());
        if (member.getProfileUrl() != null) {
            String url = member.getProfileUrl(); //기존 프로필 url -> s3에 기존꺼 지울때 쓰려구
            member.setProfileUrl(profileUrl);
        } else {
            member.setProfileUrl(profileUrl);
        }
        return true;

    }
}
