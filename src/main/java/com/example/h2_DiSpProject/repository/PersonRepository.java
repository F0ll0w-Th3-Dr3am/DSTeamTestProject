package com.example.h2_DiSpProject.repository;

import com.example.h2_DiSpProject.entity.PersonEntity;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@EnableJpaRepositories
@Transactional(readOnly=true)
@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Integer> {
    PersonEntity findByFullName(String fullName);
    PersonEntity findByFullNameAndBirthDate(String fullName, String birthDate);
}
