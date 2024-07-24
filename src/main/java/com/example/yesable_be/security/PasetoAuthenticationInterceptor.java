package com.example.yesable_be.security;

import com.example.yesable_be.enums.auth.ValidateType;
import io.grpc.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static com.example.yesable_be.constant.AuthConstants.*;

@Component
@AllArgsConstructor
public class PasetoAuthenticationInterceptor implements ServerInterceptor {

    private final PasetoTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * 요청 Intercept 후 header 의 token 을 통한 인증 (authentication) 설정
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String token = resolveHeaderToken(headers);
        if (token != null && tokenProvider.validateToken(token).equals(ValidateType.SUCCESS)) {
            String id = tokenProvider.extractId(token);
            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        return Contexts.interceptCall(Context.current(), call, headers, next);
    }

    /**
     * == private ==
     * header 에서 token 값 추출
     */
    private String resolveHeaderToken(Metadata headers) {
        Metadata.Key<String> authHeaderKey = Metadata.Key.of(AUTHORIZATION, Metadata.ASCII_STRING_MARSHALLER);
        String bearerToken = headers.get(authHeaderKey);
        return (bearerToken != null && bearerToken.startsWith(BEARER)) ? bearerToken.substring(7) : null;
    }
}
