package com.example.demo.mapper;

import com.example.demo.data.DTO.UsersDTO;
import com.example.demo.data.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {

    public abstract UsersDTO toDto(Users user);

    public abstract Users toEntity(UsersDTO usersDTO);

}
