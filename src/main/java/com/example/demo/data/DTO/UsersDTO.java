
package com.example.demo.data.DTO;

import com.example.demo.data.entity.Users;

public class UsersDTO {

    private String username;
    private String password;
    private Users.Role role;

    // Getter e setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Users.Role getRole() { return role; }
    public void setRole(Users.Role role) { this.role = role; }
}