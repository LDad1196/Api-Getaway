package com.example.demo.controller;

import com.example.demo.data.DTO.UsersDTO;
import com.example.demo.data.entity.Users;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsersService usersService;
    private final JwtUtil jwtUtil;

    public AuthController(UsersService usersService, JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody UsersDTO dto) {
        return usersService.register(dto)
                .map(user -> ResponseEntity.ok("Utente registrato con successo"));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody UsersDTO dto) {
        return usersService.authenticate(dto.getUsername(), dto.getPassword())
                .map(user -> jwtUtil.generateToken(user))
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .switchIfEmpty(Mono.just(ResponseEntity.status(401).build()));
    }
}
