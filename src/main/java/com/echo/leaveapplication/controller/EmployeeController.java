package com.echo.leaveapplication.controller;

import com.echo.leaveapplication.entity.ApplicationForLeave;
import com.echo.leaveapplication.entity.dto.AllLeaveFilterDTO;
import com.echo.leaveapplication.entity.dto.CreateLeaveApplicationDTO;
import com.echo.leaveapplication.entity.dto.LeaveBalanceDTO;
import com.echo.leaveapplication.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('employee')")
    public void createApplication(@RequestBody CreateLeaveApplicationDTO leave){
        employeeService.createLeaveApplication(leave);
    }

    @PostMapping("/submitApplication/{id}")
    @PreAuthorize("hasRole('employee')")
    public void submitApplication(@PathVariable("id") Integer id){
        employeeService.submitApplication(id);
    }

    @PostMapping("/allLeave/{id}")
    @PreAuthorize("hasRole('employee')")
    public List<ApplicationForLeave> showAllLeaveWithStatus(@PathVariable("id") int userId, @RequestBody AllLeaveFilterDTO leaveFilterDTO){
        return employeeService.showAllLeave(userId, leaveFilterDTO);
    }

    @GetMapping("/leaveBalance/{id}")
    @PreAuthorize("hasRole('employee')")
    public List<LeaveBalanceDTO> showLeaveBalance(@PathVariable("id") Integer id){
        return employeeService.showLeaveBalance(id);
    }

}
