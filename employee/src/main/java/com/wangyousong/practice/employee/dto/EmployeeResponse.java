package com.wangyousong.practice.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String department;

    private String position;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
