package com.wangyousong.practice.attendance.service;

import com.wangyousong.practice.attendance.dto.AttendanceRequest;
import com.wangyousong.practice.attendance.dto.AttendanceResponse;
import com.wangyousong.practice.attendance.entity.AttendanceRecord;
import com.wangyousong.practice.attendance.repository.AttendanceRepository;
import com.wangyousong.practice.employee.api.EmployeeFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeFeignClient employeeFeignClient;

    /**
     * 创建考勤记录（签到）
     */
    @Transactional
    public AttendanceResponse checkIn(AttendanceRequest request) {
        // 通过 Feign Client 调用验证员工是否存在
        if (!employeeFeignClient.existsEmployee(request.getEmployeeId())) {
            throw new RuntimeException("员工不存在，ID: " + request.getEmployeeId());
        }

        // 检查是否已存在当天的考勤记录
        if (attendanceRepository.findByEmployeeIdAndAttendanceDate(
                request.getEmployeeId(), request.getAttendanceDate()).isPresent()) {
            throw new RuntimeException("今天已经签到过了");
        }

        AttendanceRecord record = new AttendanceRecord();
        record.setEmployeeId(request.getEmployeeId());
        record.setAttendanceDate(request.getAttendanceDate());
        record.setCheckInTime(request.getCheckInTime());
        record.setStatus(request.getStatus());
        record.setRemark(request.getRemark());

        return convertToResponse(attendanceRepository.save(record));
    }

    /**
     * 签退
     */
    @Transactional
    public AttendanceResponse checkOut(Long id, LocalDateTime checkOutTime) {
        AttendanceRecord record = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("考勤记录不存在"));

        record.setCheckOutTime(checkOutTime);

        // 根据签到签退时间判断状态
        if (record.getCheckInTime() != null && record.getCheckOutTime() != null) {
            record.setStatus(calculateStatus(record.getCheckInTime(), record.getCheckOutTime()));
        }

        return convertToResponse(attendanceRepository.save(record));
    }

    /**
     * 更新考勤记录
     */
    @Transactional
    public AttendanceResponse update(Long id, AttendanceRequest request) {
        AttendanceRecord record = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("考勤记录不存在"));

        record.setEmployeeId(request.getEmployeeId());
        record.setAttendanceDate(request.getAttendanceDate());
        record.setCheckInTime(request.getCheckInTime());
        record.setCheckOutTime(request.getCheckOutTime());
        record.setStatus(request.getStatus());
        record.setRemark(request.getRemark());

        return convertToResponse(attendanceRepository.save(record));
    }

    /**
     * 删除考勤记录
     */
    @Transactional
    public void delete(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("考勤记录不存在");
        }
        attendanceRepository.deleteById(id);
    }

    /**
     * 查询单个考勤记录
     */
    public AttendanceResponse getById(Long id) {
        AttendanceRecord record = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("考勤记录不存在"));
        return convertToResponse(record);
    }

    /**
     * 分页查询员工考勤记录
     */
    public Page<AttendanceResponse> getByEmployeeId(Long employeeId, Pageable pageable) {
        return attendanceRepository.findByEmployeeId(employeeId, pageable)
                .map(this::convertToResponse);
    }

    /**
     * 查询指定日期范围内的考勤记录
     */
    public List<AttendanceResponse> getByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByAttendanceDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * 分页查询员工在指定日期范围内的考勤记录
     */
    public Page<AttendanceResponse> getByEmployeeIdAndDateRange(
            Long employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(
                        employeeId, startDate, endDate, pageable)
                .map(this::convertToResponse);
    }

    /**
     * 计算考勤状态
     */
    private String calculateStatus(LocalDateTime checkIn, LocalDateTime checkOut) {
        // 简化逻辑：9:00 后签到算迟到，18:00 前签退算早退
        int checkInHour = checkIn.getHour();
        int checkOutHour = checkOut.getHour();

        boolean isLate = checkInHour >= 9;
        boolean isEarlyLeave = checkOutHour < 18;

        if (isLate && isEarlyLeave) {
            return "LATE_AND_EARLY_LEAVE";
        } else if (isLate) {
            return "LATE";
        } else if (isEarlyLeave) {
            return "EARLY_LEAVE";
        } else {
            return "NORMAL";
        }
    }

    /**
     * 转换为响应 DTO
     */
    private AttendanceResponse convertToResponse(AttendanceRecord record) {
        return AttendanceResponse.builder()
                .id(record.getId())
                .employeeId(record.getEmployeeId())
                .attendanceDate(record.getAttendanceDate())
                .checkInTime(record.getCheckInTime())
                .checkOutTime(record.getCheckOutTime())
                .status(record.getStatus())
                .remark(record.getRemark())
                .createTime(record.getCreateTime())
                .createdBy(record.getCreatedBy())
                .updateTime(record.getUpdateTime())
                .updatedBy(record.getUpdatedBy())
                .build();
    }
}
