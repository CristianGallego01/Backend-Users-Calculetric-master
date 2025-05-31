package com.irojas.demojwt.Auth;

import com.irojas.demojwt.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String lastname;
    private String firstname;
    private String country;
    private String password;
}
