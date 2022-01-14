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
import static org.junit.jupiter.api.Assertions.assertEquals;


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

        personEntity = new PersonEntity("", "3.11.2000");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 11

        personEntity = new PersonEntity("Петр Петров", "");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 12

        personEntity = new PersonEntity("ПеТрОв ПеТоР", "11.12.2013");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 13

        personEntity = new PersonEntity("Петр Петр Петр", "11.11.2011");
        isInserted = personService.insertPerson(personEntity);

        assertFalse(isInserted);  // 14

        personEntity = new PersonEntity("Николаева Анна Ильинична", "03.04.1993");
        isInserted = personService.insertPerson(personEntity);

        assertTrue(isInserted);  // 15
    }


    @Test
    void getOneUser() {
        int id = 1;
        PersonEntity person = personRepository.findById(id).get();
        PersonEntity check_person = PersonService.getOneUser(id);
        assertEquals(personRepository.findById(id), check_person);
    }
}