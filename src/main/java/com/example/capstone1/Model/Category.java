package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "ID cannot be null")
    private String id;

    @NotEmpty(message = "Name cannot be null")
    @Size(min = 4 , message = "Name length must be more than 3")
    private String name;
}
