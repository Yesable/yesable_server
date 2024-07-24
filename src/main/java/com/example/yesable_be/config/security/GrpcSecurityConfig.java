package com.example.yesable_be.config.security;

import com.example.yesable_be.security.GrpcAuthorizationInterceptor;
import com.example.yesable_be.security.GrpcSecurityErrorHandler;
import com.example.yesable_be.security.PasetoAuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class GrpcSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public GrpcServerConfigurer grpcServerConfigurer(PasetoAuthenticationInterceptor authenticationInterceptor, GrpcAuthorizationInterceptor authorizationInterceptor) {
        return serverBuilder -> {
            serverBuilder.intercept(authenticationInterceptor);
            serverBuilder.intercept(authorizationInterceptor);
            serverBuilder.intercept(new GrpcSecurityErrorHandler());
        };
    }

}
