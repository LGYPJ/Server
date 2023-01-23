package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean checkNicknameDuplication(String nickname) {

        Optional<Member> result = memberRepository.findByNickname(nickname);

        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public Long postMember(PostMemberReq postMemberReq) {
        return memberRepository.save(postMemberReq.toEntity()).getMemberIdx();
    }

    public boolean inactivedMember(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new IllegalArgumentException("No exist member."));

        System.out.println(member.getStatus());

        member.inactivedMember();

        System.out.println(member.getStatus());

        return true;
    }
}
