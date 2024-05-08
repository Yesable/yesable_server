package com.example.yesable_be.model.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 관련 Response")
public class LoginRes {
    @Schema(description = "accessToken")
    private String accessToken;
    @Schema(description = "refreshToken")
    private String refreshToken;
    @Schema(description = "아이디")
    private String id;
    @Schema(description = "비밀번호")
    private String pw;
    @Schema(description = "사용자 설정 이름")
    private String userName;
}
