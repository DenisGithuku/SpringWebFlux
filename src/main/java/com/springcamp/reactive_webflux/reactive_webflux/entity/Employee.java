package com.springcamp.reactive_webflux.reactive_webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
}
