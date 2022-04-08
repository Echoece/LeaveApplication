package com.echo.leaveapplication.controller;


import com.echo.leaveapplication.entity.dto.UserLoginDTO;
import com.echo.leaveapplication.service.JwtAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class JwtAuthController {
    private final JwtAuthService jwtAuthService;

    public JwtAuthController(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserLoginDTO userLoginDTO){
        jwtAuthService.registerEmployee(userLoginDTO);
    }


}
