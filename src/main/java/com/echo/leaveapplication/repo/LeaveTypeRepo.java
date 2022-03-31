package com.echo.leaveapplication.repo;

import com.echo.leaveapplication.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepo extends JpaRepository<LeaveType, Integer> {

}
