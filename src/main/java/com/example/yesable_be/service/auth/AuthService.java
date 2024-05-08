package com.example.yesable_be.service.auth;

import com.example.yesable_be.model.request.auth.LoginReq;
import com.example.yesable_be.model.response.auth.LoginRes;
import com.example.yesable_be.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public final JwtTokenProvider jwtTokenProvider;
    public LoginRes doLogin(LoginReq loginReq){
        // TODO dayeon : loginRequest 기반 user 조회 후 TokenSettingDto build
        Claims loginClaims = Jwts.claims().setSubject(loginReq.getId());
        String accessToken = jwtTokenProvider.createAccessToken(loginClaims);
        String refreshToken = jwtTokenProvider.createRefreshToken(loginClaims);

        // TODO dayeon : refresh token 정보 DB 저장 추가
        return LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(loginReq.getId())
                .pw(loginReq.getPw())
                .userName(loginReq.getUserName())
                .build();
    }

}