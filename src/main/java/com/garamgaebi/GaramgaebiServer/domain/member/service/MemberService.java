package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.InactivedMemberRes;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberRes;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private boolean checkNicknameValidation(String nickname) {
        // todo - 정규표현식 적용
        return false;
    }

    @Transactional
    public PostMemberRes postMember(PostMemberReq postMemberReq) {
        if (checkNicknameValidation(postMemberReq.getNickname())) { // 유효한 닉네임
            throw new RestApiException(ErrorCode.INVALID_NICKNAME);
        } else { // 유효하지 않은 닉네임
            PostMemberRes postMemberRes = new PostMemberRes(memberRepository.save(postMemberReq.toEntity()).getMemberIdx());
            return postMemberRes;
        }
    }

    @Transactional
    public InactivedMemberRes inactivedMember(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));

        System.out.println(member.getStatus());

        member.inactivedMember();

        System.out.println(member.getStatus());

        return new InactivedMemberRes(true);
    }
}
