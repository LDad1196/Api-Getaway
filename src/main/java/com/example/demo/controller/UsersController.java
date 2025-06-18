package com.example.demo.controller;

import com.example.demo.data.DTO.UserWithoutPasswordDTO;
import com.example.demo.data.entity.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<UserWithoutPasswordDTO> getAllUsers() {
        return usersService.findAll();
    }
}
