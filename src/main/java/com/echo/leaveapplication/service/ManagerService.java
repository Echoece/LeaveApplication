package com.echo.leaveapplication.service;


import com.echo.leaveapplication.entity.ApplicationForLeave;
import com.echo.leaveapplication.repo.ApplicationForLeaveRepo;
import com.echo.leaveapplication.entity.Status;
import com.echo.leaveapplication.entity.dto.LeaveBalanceDTO;
import com.echo.leaveapplication.entity.rowmapper.LeaveBalanceRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private final ApplicationForLeaveRepo applicationForLeaveRepo;
    private final NamedParameterJdbcTemplate template;
    private final LeaveBalanceRowMapper leaveBalanceRowMapper;

    public ManagerService(ApplicationForLeaveRepo applicationForLeaveRepo, NamedParameterJdbcTemplate template, LeaveBalanceRowMapper leaveBalanceRowMapper) {
        this.applicationForLeaveRepo = applicationForLeaveRepo;
        this.template = template;
        this.leaveBalanceRowMapper = leaveBalanceRowMapper;
    }

    public List<ApplicationForLeave> showPendingLeave(){
        List<ApplicationForLeave> applications = applicationForLeaveRepo.findAll();

        applications = applications.stream()
                .filter(applicationForLeave -> applicationForLeave.getStatus() == Status.PENDING)
                .collect(Collectors.toList());

        return applications;
    }

    public void approveApplication(int id){
        Optional<ApplicationForLeave> applicationOpt = applicationForLeaveRepo.findById(id);

        if(applicationOpt.isPresent()){
            ApplicationForLeave application = applicationOpt.get();
            application.setStatus(Status.APPROVED);
            application.setApprovalDate(Date.valueOf(LocalDate.now()));

            applicationForLeaveRepo.save(application);
        }
    }

    public void rejectApplication(int id){
        Optional<ApplicationForLeave> applicationOpt = applicationForLeaveRepo.findById(id);

        if(applicationOpt.isPresent()){
            ApplicationForLeave application = applicationOpt.get();
            application.setStatus(Status.REJECTED);

            applicationForLeaveRepo.save(application);
        }
    }

    public List<LeaveBalanceDTO>  showLeaveBalance(Integer userId ){
        String query =
                        "SELECT " +
                            "SUM(duration) AS total_taken_leave, " +
                            "user_id, leave_type_id, leave_type_name, " +
                            "max_allowed_leave_per_year, " +
                            "year, " +
                            "(max_allowed_leave_per_year - sum(duration)) AS leave_balance "
                        + "FROM leave_application.application_for_leave "
                        + "JOIN leave_application.leave_type "
                        + "ON application_for_leave.leave_type_id = leave_type.id "
                        + "WHERE user_id=:id AND status = :status "
                        + "GROUP BY leave_type_id";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id",userId)
                .addValue("status",Status.APPROVED);

        return template.query(query, sqlParameterSource, leaveBalanceRowMapper);
    }
}
