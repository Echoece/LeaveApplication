package com.echo.leaveapplication.service;

import com.echo.leaveapplication.entity.dto.AddLeaveTypeDTO;
import com.echo.leaveapplication.entity.dto.AllocateYearlyLeaveDTO;
import com.echo.leaveapplication.entity.LeaveType;
import com.echo.leaveapplication.repo.LeaveTypeRepo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final LeaveTypeRepo leaveTypeRepo;
    private final NamedParameterJdbcTemplate template;

    public AdminService(LeaveTypeRepo leaveTypeRepo, NamedParameterJdbcTemplate template) {
        this.leaveTypeRepo = leaveTypeRepo;
        this.template = template;
    }

    public void addNewLeaveType(AddLeaveTypeDTO leaveTypeDTO){
        LeaveType leaveType = LeaveType.builder()
                .leaveTypeName(leaveTypeDTO.getLeaveTypeName())
                .remark(leaveTypeDTO.getRemark())
                .build();

        leaveTypeRepo.save(leaveType);
    }

    public void allocateLeavePerYear(AllocateYearlyLeaveDTO allocateYearlyLeaveDTO){
        Optional<LeaveType> dbLeaveType = leaveTypeRepo.findById(allocateYearlyLeaveDTO.getId());

        if(dbLeaveType.isPresent()){
            String query =  "UPDATE leave_type "
                    + "SET  max_allowed_leave_per_year = :maxLeave, year = :year "
                    + "WHERE id = :id";

            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("id",allocateYearlyLeaveDTO.getId())
                    .addValue("year",allocateYearlyLeaveDTO.getYear())
                    .addValue("maxLeave",allocateYearlyLeaveDTO.getMaxAllowedLeavePerYear());

            template.update( query , sqlParameterSource);
        }
    }
}
