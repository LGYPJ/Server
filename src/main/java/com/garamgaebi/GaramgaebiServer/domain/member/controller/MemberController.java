package com.garamgaebi.GaramgaebiServer.domain.member.controller;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import com.garamgaebi.GaramgaebiServer.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) { this.memberService = memberService; }

    @GetMapping("/{nickname}/member-duplicated")
    public boolean checkNicknameDuplication(@PathVariable(name = "nickname") String nickname) {
        return memberService.checkNicknameDuplication(nickname);
    }

    @PostMapping("/post")
    public Long postMember(@RequestBody PostMemberReq postMemberReq) {
        return memberService.postMember(postMemberReq);
    }

    @PatchMapping("/{memberIdx}/member-inactived")
    public boolean inactivedMember(@PathVariable(name = "memberIdx") Long memberIdx) {
        return memberService.inactivedMember(memberIdx);
    }
}
