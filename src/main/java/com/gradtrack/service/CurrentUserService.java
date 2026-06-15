package com.gradtrack.service;

import com.gradtrack.model.AppUser;
import com.gradtrack.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final AppUserRepository appUserRepository;

    public CurrentUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getName();

        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Authenticated user niot found"));

    }

}
