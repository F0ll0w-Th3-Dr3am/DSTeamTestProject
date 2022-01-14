package com.example.h2_DiSpProject.services;

import com.example.h2_DiSpProject.entity.PersonEntity;
import com.example.h2_DiSpProject.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    void insertPerson() throws Exception {
        PersonEntity personEntity = new PersonEntity("Ыушкин Александр Сергеевич", "06.06.1799");
        Boolean isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 1

        personEntity = new PersonEntity("Пушкин Александр Сергеевич", "06.06.1799");
        isInserted = personService.insertPerson(personEntity);

        assertTrue(isInserted);  // 2

        personEntity = new PersonEntity("1231 321 22", "03.12.2001");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 3

        personEntity = new PersonEntity("Джон Уик", "29.02.2016");
        isInserted = personService.insertPerson(personEntity);

        assertTrue(isInserted);  // 4

        personEntity = new PersonEntity("Джон Уик", "29.02.2015");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 5

        personEntity = new PersonEntity("Александр Пушкин Сергеевич", "06.06.1799");
        isInserted = personService.insertPerson(personEntity);

        assertTrue(isInserted);  // 6

        personEntity = new PersonEntity("Петр Васильев", "6.02.2012");
        isInserted = personService.insertPerson(personEntity);

        assertTrue(isInserted);  // 7

        personEntity = new PersonEntity("@@@", "06.06.1799");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 8

        personEntity = new PersonEntity("Илон Маск", "28.6.1971");
        isInserted = personService.insertPerson(personEntity);

        assertTrue(isInserted);  // 9

        personEntity = new PersonEntity("", "");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 10
    }


}