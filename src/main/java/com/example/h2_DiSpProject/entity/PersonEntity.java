package com.example.h2_DiSpProject.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

@Table(name = "Person")
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

    public PersonEntity(){
    }

    public PersonEntity(String fullName, String birthDate) {
        setFullName(fullName);
        setBirthDate(birthDate);
    }

    public PersonEntity(int id, String fullName, String birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
