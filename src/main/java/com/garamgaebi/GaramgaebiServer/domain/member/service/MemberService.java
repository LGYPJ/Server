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
import java.util.regex.Pattern;

//@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private boolean checkNicknameValidation(String nickname) {
//        if (nickname.length() > 8) {
//            return false;
//        }
//
//        String pattern = String.valueOf(Pattern.compile("^[a-zA-Z0-9]*$"));  // 영문자와 숫자만 있는지 확인
//
//        boolean result = Pattern.matches(pattern, nickname);
//        System.out.println(result);
//
//        return result;

        return true;
    }

    @Transactional
    public PostMemberRes postMember(PostMemberReq postMemberReq) {
        if (checkNicknameValidation(postMemberReq.getNickname())) { // 유효한 닉네임
            PostMemberRes postMemberRes = new PostMemberRes(memberRepository.save(postMemberReq.toEntity()).getMemberIdx());
            return postMemberRes;
        } else { // 유효하지 않은 닉네임
            throw new RestApiException(ErrorCode.INVALID_NICKNAME);
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
