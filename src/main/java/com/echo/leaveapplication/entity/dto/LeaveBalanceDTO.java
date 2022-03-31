package com.echo.leaveapplication.entity.dto;


import lombok.Data;

@Data
public class LeaveBalanceDTO {
    private Integer userId;
    private Integer leaveTypeId;

    private String leaveTypeName;
    private Integer year;
    private Integer maxAllowedLeave;

    private Integer totalTakenLeavePerYear;
    private Integer leaveBalance;
}
