package com.wangyousong.practice.employee.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Employee Feign Client 接口
 * 提供给其他微服务调用
 */
@FeignClient(name = "employee", path = "/employees")
public interface EmployeeFeignClient {

    /**
     * 验证员工是否存在
     *
     * @param employeeId 员工ID
     * @return true-存在，false-不存在
     */
    @GetMapping("/{id}/exists")
    boolean existsEmployee(@PathVariable("id") Long employeeId);

    /**
     * 获取员工姓名
     *
     * @param employeeId 员工ID
     * @return 员工姓名，不存在则返回 null
     */
    @GetMapping("/{id}/name")
    String getEmployeeName(@PathVariable("id") Long employeeId);
}
