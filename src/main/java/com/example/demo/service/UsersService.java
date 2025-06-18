package com.example.demo.service;

import com.example.demo.data.DTO.UserWithoutPasswordDTO;
import com.example.demo.data.DTO.UsersDTO;
import com.example.demo.data.entity.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersMapper = usersMapper;
    }

    public Mono<Users> register(UsersDTO dto) {
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return usersRepository.save(user);
    }

    public Mono<Users> authenticate(String username, String password) {
        return usersRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public Flux<UserWithoutPasswordDTO> findAll() {
        return usersRepository.findAll()
                .map(usersMapper::toDto);
    }
}
