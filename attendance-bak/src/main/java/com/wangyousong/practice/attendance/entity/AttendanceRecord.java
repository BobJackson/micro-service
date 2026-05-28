package com.wangyousong.practice.attendance.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 考勤记录实体
 */
@Data
@Entity
@Table(name = "attendance_records")
@EntityListeners(AuditingEntityListener.class)
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 员工ID（通过 Dubbo 从 employee 服务获取）
     */
    @Column(nullable = false)
    private Long employeeId;

    /**
     * 考勤日期
     */
    @Column(nullable = false)
    private LocalDate attendanceDate;

    /**
     * 签到时间
     */
    private LocalDateTime checkInTime;

    /**
     * 签退时间
     */
    private LocalDateTime checkOutTime;

    /**
     * 考勤状态：NORMAL-正常, LATE-迟到, EARLY_LEAVE-早退, ABSENT-缺勤
     */
    @Column(length = 20)
    private String status;

    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime;

    @CreatedBy
    @Column(updatable = false, length = 50)
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @LastModifiedBy
    @Column(length = 50)
    private String updatedBy;
}
