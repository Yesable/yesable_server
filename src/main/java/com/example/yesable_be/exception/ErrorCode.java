package com.example.yesable_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* common */
    SITE_EXCEPTION(500, "SITE EXCEPTION"),

    /* auth */
    UNAUTHORIZED_ACCESS(16, "사용자가 인증되지 않았습니다."),

    ACCESS_DENIED(7, "요청한 리소스나 작업에 대한 권한이 없습니다.")

    ;

    private final int status;
    private final String codeMessage;
}
