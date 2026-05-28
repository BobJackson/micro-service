package com.wangyousong.practice.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.wangyousong.practice.employee.api")
public class AttendanceApplication {

    static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }

}
