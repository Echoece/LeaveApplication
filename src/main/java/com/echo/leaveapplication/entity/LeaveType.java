package com.echo.leaveapplication.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String leaveTypeName;
    private String remark;
    private Integer year;
    private Integer maxAllowedLeavePerYear;
}
