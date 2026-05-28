package com.wangyousong.practice.attendance.repository;

import com.wangyousong.practice.attendance.entity.AttendanceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {

    /**
     * 查询指定员工某天的考勤记录
     */
    Optional<AttendanceRecord> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);

    /**
     * 分页查询指定员工的考勤记录
     */
    Page<AttendanceRecord> findByEmployeeId(Long employeeId, Pageable pageable);

    /**
     * 查询指定日期范围内的考勤记录
     */
    List<AttendanceRecord> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 查询指定员工在日期范围内的考勤记录
     */
    Page<AttendanceRecord> findByEmployeeIdAndAttendanceDateBetween(
            Long employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
