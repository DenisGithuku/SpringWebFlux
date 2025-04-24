package com.springcamp.reactive_webflux.reactive_webflux.service.impl;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import com.springcamp.reactive_webflux.reactive_webflux.entity.Employee;
import com.springcamp.reactive_webflux.reactive_webflux.mapper.EmployeeMapper;
import com.springcamp.reactive_webflux.reactive_webflux.repository.EmployeeRepository;
import com.springcamp.reactive_webflux.reactive_webflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Mono<EmployeeDto> getEmployeeById(String id) {
        Mono<Employee> savedEmployee = employeeRepository.findById(id);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().map(EmployeeMapper::mapToEmployeeDto).switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId) {
        Mono<Employee> employeeMono = employeeRepository.findById(employeeId);
        Mono<Employee> updatedEmployeeMono = employeeMono.flatMap(employee -> {
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            employee.setEmail(employeeDto.getEmail());
            return employeeRepository.save(employee);
        });
        return updatedEmployeeMono.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {
        return employeeRepository.deleteById(employeeId);
    }
}