package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/user")
    public Mono<String> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .map(Authentication::getName)
                .map(username -> "Utente autenticato: " + username);
    }

    @GetMapping("/test")
    public Mono<String> testAuth() {
        return Mono.just("Autenticazione riuscita! Puoi accedere all'API Gateway.");
    }
}