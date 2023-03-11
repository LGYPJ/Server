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
    public BaseResponse<PostMemberRes> postKakaoMember(@Validated @RequestBody PostMemberKakaoReq postMemberKakaoReq) throws IOException {
        return new BaseResponse<>(memberService.postMemberWithKakao(postMemberKakaoReq));
    }

    @Operation(summary = "애플 회원가입", description = "입력한 사용자의 정보들로 애플 회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/post/apple")
    public BaseResponse<PostMemberRes> postAppleMember(@Validated @RequestBody PostMemberAppleReq postMemberAppleReq) throws IOException {
        return new BaseResponse<>(memberService.postMemberWithApple(postMemberAppleReq));
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
    public BaseResponse<TokenInfo> loginWithKakao(@RequestBody MemberKakaoLoginReq memberKakaoLoginReq) throws IOException {
        return new BaseResponse<>(memberService.loginWithKakao(memberKakaoLoginReq));
    }

    @Operation(summary = "애플 로그인", description = "애플 id_token으로 로그인, jwt token 부여", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/login/apple")
    public BaseResponse<TokenInfo> loginWithApple(@RequestBody MemberAppleLoginReq memberAppleLoginReq) throws IOException {
        return new BaseResponse<>(memberService.loginWithApple(memberAppleLoginReq));
    }

    @Operation(summary = "자동 로그인", description = "refresh token으로 자동 로그인, access token & refresh token(필요시) 재발급", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content()),
            @ApiResponse(responseCode = "500", description = "알 수 없는 서버 에러", content = @Content())
    })
    @PostMapping("/login/auto")
    public BaseResponse<TokenInfo> autoLogin(@RequestBody AutoLoginReq autoLoginReq) {
        return new BaseResponse<>(memberService.autoLogin(autoLoginReq.getRefreshToken()));
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
