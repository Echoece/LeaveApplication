package com.echo.leaveapplication.controller;


import com.echo.leaveapplication.entity.dto.AddLeaveTypeDTO;
import com.echo.leaveapplication.entity.dto.AllocateYearlyLeaveDTO;
import com.echo.leaveapplication.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/")
    public void addNewLeaveType(@RequestBody AddLeaveTypeDTO addLeaveTypeDTO){
        adminService.addNewLeaveType(addLeaveTypeDTO);
    }

    @PatchMapping("/")
    public void allocateYearlyLeave(@RequestBody AllocateYearlyLeaveDTO allocateYearlyLeaveDTO){
        adminService.allocateLeavePerYear(allocateYearlyLeaveDTO);
    }
}
