package com.garamgaebi.GaramgaebiServer.global.security;

import com.garamgaebi.GaramgaebiServer.domain.member.dto.TokenRefreshRes;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.garamgaebi.GaramgaebiServer.global.security.dto.TokenInfo;
import com.garamgaebi.GaramgaebiServer.global.util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            MemberRepository memberRepository,
                            RedisUtil redisUtil,
                            AuthenticationManagerBuilder authenticationManagerBuilder) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberRepository = memberRepository;
        this.redisUtil = redisUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;    // 30일

    // 유저 정보를 가지고 AccessToken, RefreshToken 생성
    public TokenInfo generateToken(Authentication authentication, Long memberIdx) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("memberIdx", memberIdx)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .claim("memberIdx", memberIdx)
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    public TokenRefreshRes refresh(String refreshToken) {
        String newAccessToken;
        String newRefreshToken;

        // refresh token 유효성 검사
        Claims claims = parseClaims(refreshToken);
        Long memberIdx = claims.get("memberIdx", Long.class);

        Member member = memberRepository.findByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_MEMBER));
        String identifier = member.getIdentifier();

        String tokenFromRedis = redisUtil.getData("RT: " + identifier);
        if (!tokenFromRedis.equals(refreshToken) || tokenFromRedis.isEmpty()) {
            throw new RestApiException(ErrorCode.INVALID_JWT_TOKEN);
        }

        // access token 재발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(identifier, memberIdx.toString());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        newAccessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("memberIdx", memberIdx)
                .setExpiration(new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 1주일 미만으로 남은 refresh token일 경우, refresh token도 갱신
        Long expiration = getExpiration(refreshToken);
        if (expiration < 7 * 24 * 60 * 60 * 1000L) {
            newRefreshToken = Jwts.builder()
                    .claim("memberIdx", memberIdx)
                    .setExpiration(new Date((new Date()).getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } else {
            newRefreshToken = refreshToken;
        }

        TokenRefreshRes res = new TokenRefreshRes(TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .memberIdx(memberIdx)
                .build(),
                member);

        redisUtil.setDataExpire("RT: " + authentication.getName(),
                res.getTokenInfo().getRefreshToken(),
                res.getTokenInfo().getRefreshTokenExpirationTime());

        return res;
    }

    // JWT Token을 복호화하여 토큰의 정보를 꺼냄
    public Authentication getAuthentication(String accessToken) {
        // Token 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getExpiration(String accessToken) {
        // accessToken 남은 시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    private String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // jwt에서 멤버 idx 추출
    public Long getMemberIdx() {
        String accessToken = getJwt();
        if(accessToken.isBlank()) {
            throw new RestApiException(ErrorCode.EMPTY_JWT_TOKEN);
        }

        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        return claims.get("memberIdx", Long.class);
    }

    // 멤버 idx 일치 여부 확인
    public Boolean checkMemberIdx(Long memberIdx) {
        return memberIdx == getMemberIdx();
    }
}
