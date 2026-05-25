package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.config.JwtService;
import com.example.backend.dto.AuthResponseDto;
import com.example.backend.dto.LoginRequestDto;
import com.example.backend.dto.RegisterRequestDto;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;

@Service
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDto register(
            RegisterRequestDto dto) {

        boolean userExists = userRepository
                .findByEmail(
                        dto.getEmail())
                .isPresent();

        if (userExists) {
            throw new RuntimeException(
                    "User already exists");
        }

        User user = new User();

        user.setName(
                dto.getName());

        user.setEmail(
                dto.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()));

        user.setRole("USER");

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                savedUser.getEmail());

        return new AuthResponseDto(
                token,
                savedUser.getRole(),
                "Registration successful");
    }

    @Override
    public AuthResponseDto login(
            LoginRequestDto dto) {

        User user = userRepository
                .findByEmail(
                        dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException(
                        "Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword());

        if (!passwordMatches) {
            throw new UnauthorizedException(
                    "Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail());

        return new AuthResponseDto(
                token,
                user.getRole(),
                "Login successful");
    }
}