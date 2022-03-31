package com.echo.leaveapplication.controller;


import com.echo.leaveapplication.entity.ApplicationForLeave;
import com.echo.leaveapplication.entity.dto.LeaveBalanceDTO;
import com.echo.leaveapplication.service.ManagerService;
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
    public List<ApplicationForLeave> approvalPendingLeave(){
        return managerService.showPendingLeave();
    }

    @GetMapping("/accept/{id}")
    public void acceptApplication(@PathVariable("id") int id){
        managerService.approveApplication(id);
    }

    @GetMapping("/reject/{id}")
    public void rejectApplication(@PathVariable("id") int id){
        managerService.rejectApplication(id);
    }

    @GetMapping("/leaveBalance/{id}")
    public List<LeaveBalanceDTO> showLeaveBalance(@PathVariable("id") Integer userId){
        return managerService.showLeaveBalance(userId);
    }

}
