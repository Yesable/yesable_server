package com.example.yesable_be.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private Key secretKey;
    private static final long accessTokenValidTime = 60*60*1000L;
    private static final long refreshTokenValidTime = 30*24*60*60*1000L;

    @PostConstruct
    private void initSecretKey(){
        byte[] decodedKey = Base64.getEncoder().encode("somding-secretKey-authorization-jwt-token".getBytes());
        secretKey = Keys.hmacShaKeyFor(decodedKey);
    }
    /**
     * access Token 생성
     */
    public String createAccessToken(Claims claims){
        return createToken(claims, accessTokenValidTime);
    }

    /**
     * refresh Token 생성
     */
    public String createRefreshToken(Claims claims){
        return createToken(claims, refreshTokenValidTime);
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader("accessToken", accessToken);
    }

    public void setHeaderRefreshToken (HttpServletResponse response, String refreshToken){
        response.setHeader("refreshToken", refreshToken);
    }

    public String getAccessToken(HttpServletRequest request){
        return request.getHeader("accessToken");
    }

    public String getRefreshToken(HttpServletRequest request){
        return request.getHeader("refreshToken");
    }

    public boolean isTokenValidExpireTime(String jwtToken){
        if(jwtToken == null)
            return false;
        try {
            Claims claims = this.parseJwtTokenToClaims(jwtToken);
            return claims.getExpiration().after(new Date());
        } catch (Exception e){
            return false;
        }
    }

    // 내부 사용 (private)
    private Claims parseJwtTokenToClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Token 생성
     */
    private String createToken(Claims claims, long validTime){
        Date now = new Date();
        Date exprieTime = new Date(now.getTime() + validTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exprieTime)
                // TODO dayeon : secretKey 알고리즘에 대한 서칭
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}