package com.echo.leaveapplication.entity.rowmapper;

import com.echo.leaveapplication.entity.dto.LeaveBalanceDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LeaveBalanceRowMapper implements RowMapper<LeaveBalanceDTO> {
    @Override
    public LeaveBalanceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();

        leaveBalanceDTO.setLeaveBalance(rs.getInt("leave_balance"));
        leaveBalanceDTO.setLeaveTypeId(rs.getInt("leave_type_id"));
        leaveBalanceDTO.setLeaveTypeName(rs.getString("leave_type_name"));
        leaveBalanceDTO.setYear(rs.getInt("year"));
        leaveBalanceDTO.setMaxAllowedLeave(rs.getInt("max_allowed_leave_per_year"));
        leaveBalanceDTO.setUserId(rs.getInt("user_id"));
        leaveBalanceDTO.setTotalTakenLeavePerYear(rs.getInt("total_taken_leave"));

        return leaveBalanceDTO;
    }
}
