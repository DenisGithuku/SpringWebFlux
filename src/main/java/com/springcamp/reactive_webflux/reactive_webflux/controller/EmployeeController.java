package com.springcamp.reactive_webflux.reactive_webflux.controller;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import com.springcamp.reactive_webflux.reactive_webflux.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable("id") String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable("id") String employeeId, @RequestBody @Valid EmployeeDto employeeDto) {
        return employeeService.updateEmployee(employeeDto, employeeId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}