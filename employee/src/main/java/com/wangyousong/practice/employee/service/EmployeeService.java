package com.wangyousong.practice.employee.service;

import com.wangyousong.practice.employee.dto.EmployeeRequest;
import com.wangyousong.practice.employee.dto.EmployeeResponse;
import com.wangyousong.practice.employee.entity.Employee;
import com.wangyousong.practice.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setPosition(request.getPosition());

        employeeRepository.save(employee);

        return toResponse(employee);
    }

    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("员工不存在"));
        return toResponse(employee);
    }

    public Page<EmployeeResponse> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(this::toResponse);
    }

    public List<EmployeeResponse> getByDepartment(String department) {
        return employeeRepository.findByDepartment(department).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmployeeResponse> searchByName(String name) {
        return employeeRepository.findByNameContaining(name).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("员工不存在"));

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setPosition(request.getPosition());

        employeeRepository.save(employee);

        return toResponse(employee);
    }

    @Transactional
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("员工不存在");
        }
        employeeRepository.deleteById(id);
    }

    /**
     * 验证员工是否存在
     */
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    /**
     * 获取员工姓名
     */
    public String getEmployeeName(Long id) {
        return employeeRepository.findById(id)
                .map(Employee::getName)
                .orElse(null);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .department(employee.getDepartment())
                .position(employee.getPosition())
                .createTime(employee.getCreateTime())
                .updateTime(employee.getUpdateTime())
                .build();
    }
}
