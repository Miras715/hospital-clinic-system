package com.example.final1.service;

import com.example.final1.dto.request.AmalbekMirasLoginRequest;
import com.example.final1.dto.request.AmalbekMirasRegisterRequest;
import com.example.final1.dto.response.AmalbekMirasAuthResponse;
import com.example.final1.entity.AmalbekMirasRole;
import com.example.final1.entity.AmalbekMirasUser;
import com.example.final1.exception.AmalbekMirasAlreadyExistsException;
import com.example.final1.repository.AmalbekMirasUserRepository;
import com.example.final1.security.AmalbekMirasJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasAuthService {

    private final AmalbekMirasUserRepository userRepository;
    private final AmalbekMirasJwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AmalbekMirasAuthResponse register(AmalbekMirasRegisterRequest request) {
        // check if exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AmalbekMirasAlreadyExistsException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AmalbekMirasAlreadyExistsException("Email already in use: " + request.getEmail());
        }

        var user = new AmalbekMirasUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // default role is ROLE_PATIENT
        AmalbekMirasRole role = AmalbekMirasRole.ROLE_PATIENT;
        if (request.getRole() != null) {
            try {
                role = AmalbekMirasRole.valueOf(request.getRole());
            } catch (IllegalArgumentException e) {
                log.warn("unknown role {}, using default", request.getRole());
            }
        }
        user.setRole(role);

        userRepository.save(user);
        log.info("registered new user: {}", user.getUsername());

        String token = jwtUtil.generateToken(user);
        return new AmalbekMirasAuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AmalbekMirasAuthResponse login(AmalbekMirasLoginRequest request) {
        // throws BadCredentialsException if wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        log.info("user logged in: {}", user.getUsername());

        String token = jwtUtil.generateToken(user);
        return new AmalbekMirasAuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
