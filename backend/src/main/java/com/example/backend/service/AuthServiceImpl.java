package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.config.JwtService;
import com.example.backend.dto.AuthResponseDto;
import com.example.backend.dto.LoginRequestDto;
import com.example.backend.dto.PasswordResetRequestDto;
import com.example.backend.dto.RegisterRequestDto;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.model.Role;
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

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return buildAuthResponse(
                savedUser,
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

        return buildAuthResponse(
                user,
                "Login successful");
    }

    @Override
    public String forgotPassword(
            PasswordResetRequestDto dto) {

        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException(
                        "No account found for this email"));

        user.setPassword(
                passwordEncoder.encode(
                        dto.getNewPassword()));

        userRepository.save(user);

        return "Password updated successfully";
    }

    private AuthResponseDto buildAuthResponse(
            User user,
            String message) {

        AuthResponseDto response = new AuthResponseDto();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setToken(jwtService.generateToken(user.getEmail()));
        response.setRole(user.getRole().name());
        response.setMessage(message);
        return response;
    }
}
