package com.example.demo.controller;

import com.example.demo.data.DTO.UsersDTO;
import com.example.demo.data.entity.Users;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/list")
    public Mono<ResponseEntity<List<UsersDTO>>> findAllUsers(){
        return Mono.fromCallable(() -> usersService.findAllUsers())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping
    public Mono<ResponseEntity<UsersDTO>> saveUser(@RequestBody UsersDTO usersDTO){
        return Mono.fromCallable(() -> usersService.saveUser(usersDTO))
                .map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body(createdUser))
                .onErrorReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UsersDTO>> updateUser(@PathVariable Long id, @RequestBody UsersDTO userDTO) {
        return Mono.fromCallable(() -> usersService.updateUser(id, userDTO))
                .map(optional -> optional.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return Mono.fromCallable(() -> usersService.deleteUser(id))
                .map(deleted -> deleted ? ResponseEntity.noContent().<Void>build()
                        : ResponseEntity.notFound().<Void>build());
    }
}