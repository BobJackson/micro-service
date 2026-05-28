package com.wangyousong.practice.employee.controller;

import com.wangyousong.practice.employee.dto.EmployeeRequest;
import com.wangyousong.practice.employee.dto.EmployeeResponse;
import com.wangyousong.practice.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<EmployeeResponse> response = employeeService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeResponse>> getByDepartment(@PathVariable String department) {
        List<EmployeeResponse> response = employeeService.getByDepartment(department);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchByName(@RequestParam String name) {
        List<EmployeeResponse> response = employeeService.searchByName(name);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 验证员工是否存在（供其他微服务调用）
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsEmployee(@PathVariable Long id) {
        boolean exists = employeeService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    /**
     * 获取员工姓名（供其他微服务调用）
     */
    @GetMapping("/{id}/name")
    public ResponseEntity<String> getEmployeeName(@PathVariable Long id) {
        String name = employeeService.getEmployeeName(id);
        return ResponseEntity.ok(name);
    }
}
