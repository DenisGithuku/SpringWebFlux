package com.springcamp.reactive_webflux.reactive_webflux.controller;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import com.springcamp.reactive_webflux.reactive_webflux.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    // Junit test for save employee
    @DisplayName("Junit test for save employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // given--precondition for the setup
        // Provide stubs for service saveEmployee()
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Denis");
        employeeDto.setLastName("Githuku");
        employeeDto.setEmail("githukudenis@gmail.com");

        given(employeeService.saveEmployee(any(EmployeeDto.class))).willReturn(Mono.just(employeeDto));

        // when--action or behaviour to test
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/api/employees").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeDto), EmployeeDto.class).exchange();

        // then--verify output
        responseSpec.expectStatus().isCreated().expectBody().consumeWith(System.out::println).jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName()).jsonPath("$.lastName").isEqualTo(employeeDto.getLastName()).jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }
}