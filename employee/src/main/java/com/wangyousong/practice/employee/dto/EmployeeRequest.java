package com.wangyousong.practice.employee.dto;

import lombok.Data;

@Data
public class EmployeeRequest {

    private String name;

    private String email;

    private String phone;

    private String department;

    private String position;
}
