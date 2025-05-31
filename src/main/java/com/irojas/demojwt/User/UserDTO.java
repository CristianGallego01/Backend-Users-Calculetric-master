package com.irojas.demojwt.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String country;
}
