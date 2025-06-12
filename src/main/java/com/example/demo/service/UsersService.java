package com.example.demo.service;

import com.example.demo.data.DTO.UsersDTO;
import com.example.demo.data.entity.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsersDTO> findAllUsers(){
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(usersMapper::toDto)
                .collect(Collectors.toList());
    }

    public UsersDTO saveUser(UsersDTO usersDTO){
        if(usersRepository.existsByUsername(usersDTO.getUsername())){
            throw new IllegalArgumentException("Username già esistente: " + usersDTO.getUsername());
        }
        Users user = usersMapper.toEntity(usersDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser= usersRepository.save(user);
        return usersMapper.toDto(savedUser);
    }

    public Optional<UsersDTO> getUserById(Long id) {
        Optional<Users> user = usersRepository.findById(id);
        return user.map(usersMapper::toDto);
    }


    public Optional<UsersDTO> updateUser(Long id, UsersDTO usersDTO){
        return usersRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(usersDTO.getUsername());
                    if (usersDTO.getPassword() != null && !usersDTO.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
                    }
                    existingUser.setRole(usersDTO.getRole());
                    Users updatedUser = usersRepository.save(existingUser);
                    return usersMapper.toDto(updatedUser);
                });
    }

    public boolean deleteUser(Long id){
        if (usersRepository.existsById(id)){
            usersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id){
        return usersRepository.existsById(id);
    }

    public Optional<UsersDTO> getUserByUsername(String username) {
        Optional<Users> user = usersRepository.findByUsername(username);
        return user.map(usersMapper::toDto);
    }

    public boolean usernameExists(String username) {
        return usersRepository.existsByUsername(username);
    }

}
