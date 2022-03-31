package com.echo.leaveapplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Builder
public class ApplicationForLeave {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer userId;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy" , shape = JsonFormat.Shape.STRING)
    private Date startDate;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy" , shape = JsonFormat.Shape.STRING)
    private Date endDate;

    @JsonFormat(pattern = "dd-MM-yyyy" , shape = JsonFormat.Shape.STRING)
    private Date approvalDate;

    private String remark;
    private boolean isSubmitted;        // if application is submitted or not
    private int duration;               // total number of leave days

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private LeaveType leaveType;
}
