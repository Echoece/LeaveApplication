package com.echo.leaveapplication.entity.dto;


import lombok.Data;

@Data
public class AllocateYearlyLeaveDTO {
    private int id;
    private int year;
    private int maxAllowedLeavePerYear;
}
