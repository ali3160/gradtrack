package com.gradtrack.service;

import com.gradtrack.dto.auth.AuthResponse;
import com.gradtrack.dto.auth.LoginRequest;
import com.gradtrack.dto.auth.RegisterRequest;
import com.gradtrack.exception.EmailAlreadyExistsException;
import com.gradtrack.exception.InvalidCredentialsException;
import com.gradtrack.model.AppUser;
import com.gradtrack.model.Role;
import com.gradtrack.repository.AppUserRepository;
import com.gradtrack.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register (RegisterRequest request ){

        String email  = request.getEmail().trim().toLowerCase();

        if (appUserRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException(email);
        }
        AppUser user = new AppUser();
        user.setFullName(request.getFullName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        AppUser savedUser = appUserRepository.save(user);

        String token  = jwtService.generateToken(savedUser);
        return new AuthResponse(
                token,
                "Bearer",
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail()
        );
        }


    public AuthResponse login (LoginRequest request){

        String email = request.getEmail().trim().toLowerCase();

        AppUser user = appUserRepository.findByEmail(email).orElseThrow(
                InvalidCredentialsException::new
        );

        boolean passwordMatches  = passwordEncoder.matches(
                request.getPassword(), user.getPassword());

        if (!passwordMatches){
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);
         return new AuthResponse(
                token,
                 "Bearer",
                user.getId(),
                user.getFullName(),
                user.getEmail()
        );



    }





}
