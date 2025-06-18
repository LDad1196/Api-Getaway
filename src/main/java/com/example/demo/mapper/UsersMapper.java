package com.example.demo.mapper;

import com.example.demo.data.DTO.UsersDTO;
import com.example.demo.data.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersDTO toDTO(Users user);
    Users toEntity(UsersDTO dto);
}