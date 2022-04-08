package com.echo.leaveapplication.repo;

import com.echo.leaveapplication.entity.ApplicationUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUserModel,Integer> {
    Optional<ApplicationUserModel> getApplicationUserModelByUsername(String username);
}

