package com.example.yesable_be.service.auth;

import com.example.yesable_be.enums.auth.ValidateType;
import com.example.yesable_be.security.PasetoTokenProvider;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * 범용적 auth 사용자 인증 처리
 */
@GrpcService
@RequiredArgsConstructor
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    private final PasetoTokenProvider pasetoTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * 로그인
     */
    @Override
    public void createAuth(CreatedTokenRequest request, StreamObserver<CreatedTokenResponse> responseObserver) {
        // 사용자 검증
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getAuth().getId(), request.getAuth().getPw()));

        // 토큰 생성
        String token = pasetoTokenProvider.generateToken(authentication);

        // TODO : create DateUtil
        Instant now = Instant.now();
        CreatedTokenResponse response = CreatedTokenResponse.newBuilder().setAuth(
                AuthData.newBuilder()
                        .setId(request.getAuth().getId())
                        .setPw(request.getAuth().getPw())
                        .setCreateTime(now.toEpochMilli())
                        .setExpireTime(now.plus(2, ChronoUnit.HOURS).toEpochMilli())
                        .setToken(token))
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    /**
     * 토큰 검증
     */
    @Override
    public void verifyAuth(VerifyTokenRequest request, StreamObserver<VerifyTokenResponse> responseObserver) {
        ValidateType v = pasetoTokenProvider.validateToken(request.getToken());

        Verify.Builder verifyBuilder = Verify.newBuilder();
        pasetoTokenProvider.parseToken(request.getToken());

        int statusValue = switch (v.getValue()) {
            case 0 -> ResponseType.SUCCESS_VALUE;
            case 1 -> ResponseType.EXPIRED_TIME_VALUE;
            default -> ResponseType.FAILED_VALUE;
        };

        VerifyTokenResponse response = VerifyTokenResponse.newBuilder()
                .setV(verifyBuilder
                        .setStatusValue(statusValue)
                        .setAuth(AuthData.newBuilder()
                                .setId(pasetoTokenProvider.extractId(request.getToken()))
                                .setToken(request.getToken()))
                        .build())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
