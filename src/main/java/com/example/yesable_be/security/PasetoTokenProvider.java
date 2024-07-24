package com.example.yesable_be.security;

import com.example.yesable_be.enums.auth.ValidateType;
import com.example.yesable_be.model.entity.mariadb.user.CoreUser;
import dev.paseto.jpaseto.*;
import dev.paseto.jpaseto.lang.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PasetoTokenProvider {
    private final SecretKey secretKey;

    private final KeyPair keyPair;

    public PasetoTokenProvider() {
        secretKey = Keys.secretKey();
        keyPair = Keys.keyPairFor(Version.V2);
    }

    /**
     * 사용자 id 값 추출
     */
    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * claims 값 추출
     */
    public Claims getClaims(String token) {
        return parseToken(token).getClaims();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * authentication 으로 token 생성
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        CoreUser userPrincipal = (CoreUser) authentication.getPrincipal();

        return Pasetos.V2.LOCAL.builder()
                .setSharedSecret(secretKey)
                .setIssuedAt(now)
                .setExpiration(now.plus(2, ChronoUnit.HOURS))
                .setSubject(userPrincipal.getUsername())
                .setKeyId(UUID.randomUUID().toString())
                .setAudience("bootlabs.com")
                .setIssuer("dev.com")
                .claim("auth", authorities)
                .compact();
    }

    /**
     * token parsing
     */
    public Paseto parseToken(String token) {
        PasetoParser parser = Pasetos.parserBuilder()
                .setSharedSecret(secretKey)
                .setPublicKey(keyPair.getPublic())
                .build();

        return parser.parse(token);
    }

    /**
     * token 값 검증
     */
    public ValidateType validateToken(String authToken) {
        try {
            parseToken(authToken);
             if(!isTokenExpired(authToken))
                 return ValidateType.SUCCESS;
             else return ValidateType.EXPIRED_TIME;
        } catch (PasetoException e) {
            log.error("Token validation error: {}", e.getMessage());
            return ValidateType.FAILED;
        }
    }

    /**
     * == private ==
     * token 만료 시간 추출
     */
    private Instant extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * == private ==
     * 토큰 만료 여부 조회
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }


}
