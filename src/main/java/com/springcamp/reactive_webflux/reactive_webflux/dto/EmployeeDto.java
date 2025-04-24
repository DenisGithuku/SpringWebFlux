package com.springcamp.reactive_webflux.reactive_webflux.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String id;
    @NotBlank
    @Size(min = 2, max = 10)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 10)
    private String lastName;
    @Email
    private String email;
}