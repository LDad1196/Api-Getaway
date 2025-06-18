package com.example.demo.data.DTO;

import com.example.demo.data.entity.Users;

public class UserWithoutPasswordDTO {

    private String username;
    private Users.Role role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }


    public Users.Role getRole() { return role; }
    public void setRole(Users.Role role) { this.role = role; }
}
