package com.example.demo.repository;

import com.example.demo.data.entity.Users;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersRepository extends ReactiveCrudRepository<Users, Long> {
    Mono<Users> findByUsername(String username);
}