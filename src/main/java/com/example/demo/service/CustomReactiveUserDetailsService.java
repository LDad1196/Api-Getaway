
package com.example.demo.service;

import com.example.demo.data.entity.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> usersRepository.findByUsername(username))
                .flatMap(userOptional -> {
                    if (userOptional.isPresent()) {
                        Users user = userOptional.get();
                        Collection<GrantedAuthority> authorities =
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

                        UserDetails userDetails = User.builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .authorities(authorities)
                                .build();

                        return Mono.just(userDetails);
                    } else {
                        return Mono.error(new UsernameNotFoundException("User not found: " + username));
                    }
                });
    }
}