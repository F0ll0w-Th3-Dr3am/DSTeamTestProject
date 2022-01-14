package com.example.h2_DiSpProject.model;


import com.example.h2_DiSpProject.entity.PersonEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private int id;
    private String fullName, birthDate;

    public static Person toModel(PersonEntity entity) {
        Person model = new Person();
        model.setId(entity.getId());
        model.setFullName(entity.getFullName());
        model.setBirthDate(entity.getBirthDate());

        return model;
    }
}
