package com.garamgaebi.GaramgaebiServer.domain.member.controller;

import com.garamgaebi.GaramgaebiServer.domain.member.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.member.service.MemberService;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MemberController", description = "멤버 컨트롤러(담당자:애플)")
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

    @PostMapping("/login")
    public BaseResponse<TokenInfo> login(@RequestBody MemberLoginReq memberLoginReq) {
        return new BaseResponse<>(memberService.login(memberLoginReq));
    }

    @PostMapping("/logout")
    public BaseResponse<MemberLogoutRes> logout(@RequestBody MemberLogoutReq memberLogoutReq) {
        return new BaseResponse<>(memberService.logout(memberLogoutReq));
    }
}
