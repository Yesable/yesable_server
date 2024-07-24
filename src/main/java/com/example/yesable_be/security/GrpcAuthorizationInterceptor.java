package com.example.yesable_be.security;

import com.example.yesable_be.exception.ErrorCode;
import io.grpc.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GrpcAuthorizationInterceptor implements ServerInterceptor {

    /**
     * user authentication 확인 후 권한에 따라 인가(Authorization) 처리
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            call.close(Status.UNAUTHENTICATED.withDescription(ErrorCode.UNAUTHORIZED_ACCESS.getCodeMessage()), headers);
            return new ServerCall.Listener<>() {
            };
        }

        boolean hasAccess = checkUserAccess(authentication, call.getMethodDescriptor().getFullMethodName());
        if (!hasAccess) {
            call.close(Status.PERMISSION_DENIED.withDescription(ErrorCode.ACCESS_DENIED.getCodeMessage()), headers);
            return new ServerCall.Listener<>() {
            };
        }

        return next.startCall(call, headers);
    }

    /**
     * == private ==
     * user access 권한 (ROLE) 확인
     */
    private boolean checkUserAccess(Authentication authentication, String methodName) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
    }
}
