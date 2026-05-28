package com.wangyousong.practice.attendance.controller;

import com.wangyousong.practice.attendance.dto.AttendanceRequest;
import com.wangyousong.practice.attendance.dto.AttendanceResponse;
import com.wangyousong.practice.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * 签到
     */
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.checkIn(request));
    }

    /**
     * 签退
     */
    @PutMapping("/{id}/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(
            @PathVariable Long id,
            @RequestParam LocalDateTime checkOutTime) {
        return ResponseEntity.ok(attendanceService.checkOut(id, checkOutTime));
    }

    /**
     * 更新考勤记录
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> update(
            @PathVariable Long id,
            @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.update(id, request));
    }

    /**
     * 删除考勤记录
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询单个考勤记录
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    /**
     * 分页查询员工考勤记录
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<AttendanceResponse>> getByEmployeeId(
            @PathVariable Long employeeId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(attendanceService.getByEmployeeId(employeeId, pageable));
    }

    /**
     * 查询日期范围内的考勤记录
     */
    @GetMapping("/range")
    public ResponseEntity<List<AttendanceResponse>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.getByDateRange(startDate, endDate));
    }

    /**
     * 分页查询员工在指定日期范围内的考勤记录
     */
    @GetMapping("/employee/{employeeId}/range")
    public ResponseEntity<Page<AttendanceResponse>> getByEmployeeIdAndDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(attendanceService.getByEmployeeIdAndDateRange(
                employeeId, startDate, endDate, pageable));
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "attendance");
        return ResponseEntity.ok(result);
    }
}
