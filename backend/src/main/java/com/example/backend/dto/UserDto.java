package com.example.backend.dto;

import com.example.backend.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private Role role;
}
