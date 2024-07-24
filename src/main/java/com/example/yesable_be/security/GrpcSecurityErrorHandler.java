package com.example.yesable_be.security;

import com.example.yesable_be.exception.ErrorCode;
import io.grpc.*;
import org.springframework.stereotype.Component;

@Component
public class GrpcSecurityErrorHandler implements ServerInterceptor {

    /**
     * security 관련 발생한 Error Handling
     * Error Status 에 맞는 description custom
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        try {
            return next.startCall(call, headers);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().equals(Status.UNAUTHENTICATED)) {
                call.close(Status.UNAUTHENTICATED.withDescription(ErrorCode.UNAUTHORIZED_ACCESS.getCodeMessage()), headers);
            } else if (e.getStatus().equals(Status.PERMISSION_DENIED)) {
                call.close(Status.PERMISSION_DENIED.withDescription(ErrorCode.ACCESS_DENIED.getCodeMessage()), headers);
            } else {
                call.close(Status.UNKNOWN.withDescription(ErrorCode.SITE_EXCEPTION.getCodeMessage()), headers);
            }
            return new ServerCall.Listener<>() {
            };
        }
    }
}
