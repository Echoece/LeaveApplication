package com.echo.leaveapplication.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AddLeaveTypeDTO {
    private String leaveTypeName;
    private String remark;
}
