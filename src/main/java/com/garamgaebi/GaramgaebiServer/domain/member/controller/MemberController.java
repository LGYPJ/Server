package com.garamgaebi.GaramgaebiServer.domain.member.controller;

import com.garamgaebi.GaramgaebiServer.domain.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.InactivedMemberRes;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberReq;
import com.garamgaebi.GaramgaebiServer.domain.member.dto.PostMemberRes;
import com.garamgaebi.GaramgaebiServer.domain.member.service.MemberService;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) { this.memberService = memberService; }

    @PostMapping("/post")
    public BaseResponse<PostMemberRes> postMember(@RequestBody PostMemberReq postMemberReq) {
        return new BaseResponse<>(memberService.postMember(postMemberReq));
    }

    @PatchMapping("/{memberIdx}/member-inactived")
    public BaseResponse<InactivedMemberRes> inactivedMember(@PathVariable(name = "memberIdx") Long memberIdx) {
        return new BaseResponse<>(memberService.inactivedMember(memberIdx));
    }
}
