package com.irojas.demojwt.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.irojas.demojwt.User.UserDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;  // Solo el token


    private UserDTO user;  // Aquí va la info del usuario
}


