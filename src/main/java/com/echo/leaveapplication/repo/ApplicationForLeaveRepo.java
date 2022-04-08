package com.echo.leaveapplication.repo;

import com.echo.leaveapplication.entity.ApplicationForLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationForLeaveRepo extends JpaRepository<ApplicationForLeave, Integer> {
    List<ApplicationForLeave> findApplicationForLeavesByUserId(Integer id);
}
