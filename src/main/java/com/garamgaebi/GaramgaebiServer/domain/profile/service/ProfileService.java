package com.garamgaebi.GaramgaebiServer.domain.profile.service;

import com.garamgaebi.GaramgaebiServer.domain.profile.dto.reqeust.*;
import com.garamgaebi.GaramgaebiServer.domain.profile.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    public void saveQna(PostQnaReq req); //POST 고객센터(QnA)신청 API
    public void saveSns(PostSNSReq req); //POST SNS추가 API
    public Boolean updateSNS(UpdateSNSReq req); //PATCH SNS 수정 API
    public Boolean deleteSNS(long snsIdx); //PATCH SNS 삭제 API
    public void saveEducation(PostEducationReq req); //POST 교육추가 API
    public Boolean updateEducation(UpdateEducationReq req); //PATCH 교육 수정
    public Boolean deleteEducation(long educationIdx); //DELETE 교육 삭제
    public void saveCareer(PostCareerReq req); //POST 경력추가 API
    public Boolean updateCareer(UpdateCareerReq req); //PATCH 경력 수정
    public Boolean deleteCareer(long careerIdx); //DELETE 경력 삭제
    public GetProfileRes getProfile(long memberIdx); //GET 유저프로필조회 API
    public List<GetSNSListRes> getSNSList(long memberIdx); //GET 프로필 SNS 조회 API
    public List<GetCareerListRes> getCareerList(long memberIdx); //GET 프로필 경력 조회 API
    public List<GetEducationListRes> getEducationList(long memberIdx); //GET 프로필 교육 조회 API
    public List<GetProfilesRes> getProfiles(); //GET 유저프로필 11명 API
    public Long updateProfile(PostUpdateProfileReq req, MultipartFile multipartFile); //POST 유저 프로필 수정(프로필 사진포함) API
    public boolean imageProfile(S3ProfileReq profile, String profileUrl); //프로필 이미지 저장/수정 API

}
