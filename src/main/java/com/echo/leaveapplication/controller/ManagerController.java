package com.echo.leaveapplication.controller;


import com.echo.leaveapplication.entity.ApplicationForLeave;
import com.echo.leaveapplication.entity.dto.AddRemarkDTO;
import com.echo.leaveapplication.entity.dto.LeaveBalanceDTO;
import com.echo.leaveapplication.service.ManagerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('manager')")
    public List<ApplicationForLeave> approvalPendingLeave(){
        return managerService.showPendingLeave();
    }

    @GetMapping("/accept/{id}")
    @PreAuthorize("hasRole('manager')")
    public void acceptApplication(@PathVariable("id") int id){
        managerService.approveApplication(id);
    }

    @GetMapping("/reject/{id}")
    @PreAuthorize("hasRole('manager')")
    public void rejectApplication(@PathVariable("id") int id){
        managerService.rejectApplication(id);
    }

    @GetMapping("/leaveBalance/{id}")
    @PreAuthorize("hasRole('manager')")
    public List<LeaveBalanceDTO> showLeaveBalance(@PathVariable("id") Integer userId){
        return managerService.showLeaveBalance(userId);
    }

    @PostMapping("/remark")
    @PreAuthorize("hasRole('manager')")
    public void addRemark(@RequestBody AddRemarkDTO addRemarkDTO){
        managerService.addRemark(addRemarkDTO);
    }

}
