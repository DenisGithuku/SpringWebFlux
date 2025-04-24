package com.springcamp.reactive_webflux.reactive_webflux.service;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

    Mono<EmployeeDto> getEmployeeById(String id);
}