package com.echo.leaveapplication.auth;


import com.echo.leaveapplication.entity.ApplicationUserModel;
import com.echo.leaveapplication.repo.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepo applicationUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDetailsService(ApplicationUserRepo applicationUserRepo, PasswordEncoder passwordEncoder) {
        this.applicationUserRepo = applicationUserRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserRepo.getApplicationUserModelByUsername(username)
                .map(this::createUser)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User name %s not found",username))
                );
    }

    private ApplicationUser createUser(ApplicationUserModel user){
        Set<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toSet());

        return ApplicationUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
