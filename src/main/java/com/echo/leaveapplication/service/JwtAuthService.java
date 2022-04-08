package com.echo.leaveapplication.service;


import com.echo.leaveapplication.entity.ApplicationAuthorityModel;
import com.echo.leaveapplication.entity.ApplicationUserModel;
import com.echo.leaveapplication.entity.dto.UserLoginDTO;
import com.echo.leaveapplication.repo.ApplicationRoleRepo;
import com.echo.leaveapplication.repo.ApplicationUserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JwtAuthService {
    private final ApplicationUserRepo applicationUserRepo;
    private final ApplicationRoleRepo applicationRoleRepo;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthService(ApplicationUserRepo applicationUserRepo, ApplicationRoleRepo applicationRoleRepo, PasswordEncoder passwordEncoder) {
        this.applicationUserRepo = applicationUserRepo;
        this.applicationRoleRepo = applicationRoleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerEmployee(UserLoginDTO userLoginDTO){
        ApplicationUserModel user = new ApplicationUserModel();

        ApplicationAuthorityModel authorityModel  = applicationRoleRepo.getById(1);

        String encodedPassword = passwordEncoder.encode(userLoginDTO.getPassword());
        user.setUsername(userLoginDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setAuthorities(Set.of(authorityModel));

        applicationUserRepo.save(user);
    }

    public void login(UserLoginDTO userLoginDTO){

    }

}
