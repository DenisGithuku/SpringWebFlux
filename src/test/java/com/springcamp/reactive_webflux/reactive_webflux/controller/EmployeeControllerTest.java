package com.springcamp.reactive_webflux.reactive_webflux.controller;

import com.springcamp.reactive_webflux.reactive_webflux.dto.EmployeeDto;
import com.springcamp.reactive_webflux.reactive_webflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    private EmployeeDto employeeDto;

    @BeforeEach
    public void setup() {
        employeeDto = new EmployeeDto();
        employeeDto.setId("123");
        employeeDto.setFirstName("Denis");
        employeeDto.setLastName("Githuku");
        employeeDto.setEmail("githukudenis@gmail.com");
    }

    // Junit test for save employee
    @DisplayName("Junit test for save employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // given--precondition for the setup
        // Provide stubs for service saveEmployee()
        given(employeeService.saveEmployee(any(EmployeeDto.class))).willReturn(Mono.just(employeeDto));

        // when--action or behaviour to test
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/api/employees").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeDto), EmployeeDto.class).exchange();

        // then--verify output
        responseSpec.expectStatus().isCreated().expectBody().consumeWith(System.out::println).jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName()).jsonPath("$.lastName").isEqualTo(employeeDto.getLastName()).jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    // Junit test for get employee by id
    @DisplayName("Junit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given--precondition for setup
        given(employeeService.getEmployeeById(employeeDto.getId())).willReturn(Mono.just(employeeDto));

        // when--action or behaviour to test
        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", employeeDto.getId())).exchange();

        // then--verify output
        responseSpec.expectStatus().isOk().expectBody().consumeWith(System.out::println).jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName()).jsonPath("$.lastName").isEqualTo(employeeDto.getLastName()).jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    // Junit test for get all employees
    @DisplayName("Junit test for get all employees")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
        // given--precondition for setup
        // Construct dto list
        List<EmployeeDto> list = new ArrayList<>();
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Peter");
        employeeDto1.setLastName("Odhiambo");
        employeeDto1.setEmail("peterodhiambo@gmail.com");

        // Add dtos to the employee list
        list.add(employeeDto);
        list.add(employeeDto1);

        // Construct employee flux
        Flux<EmployeeDto> employeeFlux = Flux.fromIterable(list);
        given(employeeService.getAllEmployees()).willReturn(employeeFlux);

        // when--action or behaviour to test
        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees").accept(MediaType.APPLICATION_JSON).exchange();

        // then--verify output
        responseSpec.expectStatus().isOk().expectBody().consumeWith(System.out::println);
    }

    // Junit test for update employee
    @DisplayName("Junit test for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        // given--precondition for setup
        given(employeeService.updateEmployee(ArgumentMatchers.any(EmployeeDto.class), ArgumentMatchers.any(String.class))).willReturn(Mono.just(employeeDto));

        // when--action or behaviour to test
        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employeeDto.getId())).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(Mono.just(employeeDto), EmployeeDto.class).exchange();

        // then--verify output
        response.expectStatus().isOk().expectBody().consumeWith(System.out::println).jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName()).jsonPath("$.lastName").isEqualTo(employeeDto.getLastName()).jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    // Junit test for delete employee
    @DisplayName("Junit test for delete employee")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing() {
        // given--precondition for setup
        given(employeeService.deleteEmployee(employeeDto.getId()))
                .willReturn(Mono.empty());

        // when--action or behaviour to test
        WebTestClient.ResponseSpec response = webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", employeeDto.getId())).exchange();

        // then--verify output
        response.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}