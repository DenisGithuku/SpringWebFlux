package com.springcamp.reactive_webflux.reactive_webflux.controller;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import com.springcamp.reactive_webflux.reactive_webflux.entity.Employee;
import com.springcamp.reactive_webflux.reactive_webflux.mapper.EmployeeMapper;
import com.springcamp.reactive_webflux.reactive_webflux.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }
}