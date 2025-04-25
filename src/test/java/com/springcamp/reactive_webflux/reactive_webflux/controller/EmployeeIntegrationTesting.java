package com.springcamp.reactive_webflux.reactive_webflux.controller;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import com.springcamp.reactive_webflux.reactive_webflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTesting {

    private EmployeeDto employeeDto;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        employeeDto = new EmployeeDto();
        employeeDto.setId("123");
        employeeDto.setFirstName("Denis");
        employeeDto.setLastName("Githuku");
        employeeDto.setEmail("githukudenis@gmail.com");
    }

    @Test
    @DisplayName("Integration test for save employee")
    public void saveEmployee() {
        // setup
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/employees").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeDto), EmployeeDto.class).exchange();

        // then--verify
        response.expectStatus().isCreated().expectBody().consumeWith(System.out::println).jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName()).jsonPath("$.lastName").isEqualTo(employeeDto.getLastName()).jsonPath("$.email").isEqualTo(employeeDto.getEmail());


    }
}
