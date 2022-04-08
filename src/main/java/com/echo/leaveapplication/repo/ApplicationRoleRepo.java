package com.echo.leaveapplication.repo;


import com.echo.leaveapplication.entity.ApplicationAuthorityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRoleRepo extends JpaRepository<ApplicationAuthorityModel, Integer> {
}
