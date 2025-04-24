package com.springcamp.reactive_webflux.reactive_webflux.repository;

import com.springcamp.reactive_webflux.reactive_webflux.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {}