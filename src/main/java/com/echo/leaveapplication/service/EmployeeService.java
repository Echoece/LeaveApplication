package com.echo.leaveapplication.service;

import com.echo.leaveapplication.entity.*;
import com.echo.leaveapplication.entity.dto.AllLeaveFilterDTO;
import com.echo.leaveapplication.entity.dto.CreateLeaveApplicationDTO;
import com.echo.leaveapplication.entity.dto.LeaveBalanceDTO;
import com.echo.leaveapplication.entity.rowmapper.LeaveBalanceRowMapper;
import com.echo.leaveapplication.repo.ApplicationForLeaveRepo;
import com.echo.leaveapplication.repo.LeaveTypeRepo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final ApplicationForLeaveRepo applicationForLeaveRepo;
    private final LeaveTypeRepo leaveTypeRepo;
    private final NamedParameterJdbcTemplate template;
    private final LeaveBalanceRowMapper leaveBalanceRowMapper;

    public EmployeeService(ApplicationForLeaveRepo applicationForLeaveRepo, LeaveTypeRepo leaveTypeRepo, NamedParameterJdbcTemplate template, LeaveBalanceRowMapper leaveBalanceRowMapper) {
        this.applicationForLeaveRepo = applicationForLeaveRepo;
        this.leaveTypeRepo = leaveTypeRepo;
        this.template = template;
        this.leaveBalanceRowMapper = leaveBalanceRowMapper;
    }

    // creating a leave application, usable by employees
    public void createLeaveApplication(CreateLeaveApplicationDTO createLeaveApplicationDTO){
        // getting the leave type
        LeaveType leaveType = leaveTypeRepo.findById(createLeaveApplicationDTO.getLeaveTypeID()).get();

        // duration (days) of the leave
        final int duration = differenceInDays(
                createLeaveApplicationDTO.getStartDate(),
                createLeaveApplicationDTO.getEndDate()
        );

        ApplicationForLeave applicationForLeave = ApplicationForLeave.builder()
                .startDate(createLeaveApplicationDTO.getStartDate())
                .endDate(createLeaveApplicationDTO.getEndDate())
                .remark(createLeaveApplicationDTO.getRemark())
                .userId(createLeaveApplicationDTO.getUserId())
                .status(Status.PENDING)  // default status is pending during submission
                .isSubmitted(false)      // by default not submitted, employee can submit the application at a later time
                .leaveType(leaveType)
                .duration(duration)
                .build();

        applicationForLeaveRepo.save(applicationForLeave);
    }

    // submit the leave application
    public void submitApplication(Integer id){
        Optional<ApplicationForLeave> applicationForLeave = applicationForLeaveRepo.findById(id);

        if(applicationForLeave.isPresent()){
            applicationForLeave.get().setSubmitted(true);
            applicationForLeaveRepo.save(applicationForLeave.get());
        }
    }

    // get leave balance for employee
    public List<LeaveBalanceDTO> showLeaveBalance(Integer userId){
        String query =
                  "SELECT " +
                          "SUM(duration) AS total_taken_leave, " +
                          "user_id, leave_type_id, leave_type_name, " +
                          "max_allowed_leave_per_year, " +
                          "year, " +
                          "(max_allowed_leave_per_year - sum(duration)) AS leave_balance "
                + "FROM application_for_leave "
                + "JOIN leave_type "
                + "ON application_for_leave.leave_type_id = leave_type.id "
                + "WHERE user_id=:id AND status = :status "
                + "GROUP BY leave_type_id";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id",userId)
                .addValue("status",Status.APPROVED);

        return template.query(query, sqlParameterSource, leaveBalanceRowMapper);
    }

    // show all leave with status. optional filters : date range, leave type, status
    public List<ApplicationForLeave> showAllLeave(int userId, AllLeaveFilterDTO leaveFilterDTO){
        List<ApplicationForLeave> applications = applicationForLeaveRepo.findApplicationForLeavesByUserId(userId);


        if(leaveFilterDTO.getStatus()!=null){
            applications = applications.stream()
                    .filter( applicationForLeave -> applicationForLeave.getStatus() == leaveFilterDTO.getStatus() )
                    .collect(Collectors.toList());
        }

        if(leaveFilterDTO.getLeaveTypeId()!=null){
            LeaveType leaveType = leaveTypeRepo.findById(leaveFilterDTO.getLeaveTypeId()).get();

            applications = applications.stream()
                    .filter( applicationForLeave -> applicationForLeave.getLeaveType() == leaveType)
                    .collect(Collectors.toList());
        }

        if (leaveFilterDTO.getStartDate()!=null && leaveFilterDTO.getEndDate() !=null){
            applications = applications.stream()
                    .filter(filterByDateRange(leaveFilterDTO))
                    .collect(Collectors.toList());
        }

        return applications;
    }

    // utility function to get the difference in days between two date
    private int differenceInDays(Date startDate, Date endDate){
        // time difference is in milliseconds, converting it to days here.
        int milliSecToDays =  1000 * 60 * 60 * 24;
        long timeDifference = endDate.getTime() - startDate.getTime();
        //TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS); // another way
        return (int ) (timeDifference/ milliSecToDays);
    }

    // utility function to filter the date range
    private Predicate<ApplicationForLeave> filterByDateRange(AllLeaveFilterDTO leaveFilterDTO) {
        return applicationForLeave -> {

            // application start date should be equal or after the filter start date
            int compareStartDate = applicationForLeave.getStartDate().compareTo(leaveFilterDTO.getStartDate());
            // application end date should be equal or before the filter end date, value should be
            int compareEndDate = applicationForLeave.getEndDate().compareTo(leaveFilterDTO.getEndDate());

            if (compareStartDate >= 0 && compareEndDate <= 0)
                return true;
            return false;
        };
    }
}
