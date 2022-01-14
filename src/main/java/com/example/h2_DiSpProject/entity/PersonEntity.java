package com.example.h2_DiSpProject.entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

@Table(name = "Person")
@Getter
@Setter
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 128)
    @NotEmpty
    private String fullName;

    @Size(max = 10)
    @DateTimeFormat
    @NotEmpty
    private String birthDate;

    public PersonEntity(String fullName, String birthDate) {
        setFullName(fullName);
        setBirthDate(birthDate);
    }
}
