package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID cannot be null")
    private String id;

    @NotEmpty(message = "Username cannot be null")
    @Size(min = 6 , message = "Username length must be more than 5")
    private String username;

    @NotEmpty(message = "Username cannot be null")
    @Size(min = 7 , message = "Name length must be more than 6")
    //@Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{7,}$", message = "Please enter a valid password")
    @Pattern(regexp = "^[A-Za-z\\s]{1,}[0-9\\s]{1,}$",  message = "Please enter a valid password")
    private String password;

    @NotEmpty(message = "Email cannot be null")
    //@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String email;
    @NotEmpty(message = "Role cannot be null")
    @Pattern(regexp = "admin|customer|Admin|Customer", message = "Role must be admin or customer only")
    private String role;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be a positive number")
    private Double balance;
}
