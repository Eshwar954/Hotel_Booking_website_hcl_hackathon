package com.example.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.AuthResponseDto;
import com.example.backend.dto.LoginRequestDto;
import com.example.backend.dto.RegisterRequestDto;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.config.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDto register(RegisterRequestDto dto) {
        Optional<User> exists = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(dto.getUsername()) || u.getEmail().equals(dto.getEmail()))
                .findFirst();
        if (exists.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        AuthResponseDto resp = new AuthResponseDto();
        resp.setToken(token);
        return resp;
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findAll().stream().filter(u -> dto.getUsername().equals(u.getUsername())).findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getUsername());
        AuthResponseDto resp = new AuthResponseDto();
        resp.setToken(token);
        return resp;
    }

}

