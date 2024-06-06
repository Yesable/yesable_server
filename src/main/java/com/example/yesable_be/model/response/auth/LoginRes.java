package com.example.yesable_be.model.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRes {
    private String accessToken;
    private String refreshToken;
    private String id;
    private String pw;
    private String userName;
}
