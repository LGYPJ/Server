package com.garamgaebi.GaramgaebiServer.domain.member.controller;

import com.garamgaebi.GaramgaebiServer.domain.member.dto.*;
import com.garamgaebi.GaramgaebiServer.domain.member.service.KakaoService;
import com.garamgaebi.GaramgaebiServer.domain.member.service.MemberServiceImpl;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import com.garamgaebi.GaramgaebiServer.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Tag(name = "MemberController", description = "멤버 컨트롤러(담당자:애플)")
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberServiceImpl memberService;

    @Autowired
    public MemberController(MemberServiceImpl memberService, KakaoService kakaoService) {
        this.memberService = memberService;
    }

    @Operation(summary = "카카오 회원가입", description = "입력한 사용자의 정보들로 카카오 회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/post/kakao")
    public BaseResponse<PostMemberRes> postMember(@Validated @RequestBody PostMemberReq postMemberReq) throws IOException {
        return new BaseResponse<>(memberService.postMemberWithKakao(postMemberReq));
    }

    @Operation(summary = "회원탈퇴", description = "회원 비활성화", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/member-inactived")
    public BaseResponse<InactivedMemberRes> inactivedMember(@RequestBody InactivedMemberReq inactivedMemberReq) {
        return new BaseResponse<>(memberService.inactivedMember(inactivedMemberReq));
    }

    @Operation(summary = "카카오 로그인", description = "카카오 access_token으로 로그인, jwt token 부여", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/login/kakao")
    public BaseResponse<TokenInfo> login(@RequestBody MemberLoginReq memberLoginReq) throws IOException {
        return new BaseResponse<>(memberService.loginWithKakao(memberLoginReq));
    }

    @Operation(summary = "로그아웃", description = "사용자 로그아웃, jwt token 비활성화", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/logout")
    public BaseResponse<MemberLogoutRes> logout(@RequestBody MemberLogoutReq memberLogoutReq) {
        return new BaseResponse<>(memberService.logout(memberLogoutReq));
    }
}
