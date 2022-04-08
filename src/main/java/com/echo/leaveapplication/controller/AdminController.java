package com.echo.leaveapplication.controller;


import com.echo.leaveapplication.entity.dto.AddLeaveTypeDTO;
import com.echo.leaveapplication.entity.dto.AllocateYearlyLeaveDTO;
import com.echo.leaveapplication.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_admin')")
    public void addNewLeaveType(@RequestBody AddLeaveTypeDTO addLeaveTypeDTO){
        adminService.addNewLeaveType(addLeaveTypeDTO);
    }

    @PatchMapping("/")
    @PreAuthorize("hasRole('ROLE_admin')")
    public void allocateYearlyLeave(@RequestBody AllocateYearlyLeaveDTO allocateYearlyLeaveDTO){
        adminService.allocateLeavePerYear(allocateYearlyLeaveDTO);
    }
}
