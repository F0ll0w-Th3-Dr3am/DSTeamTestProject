package com.example.h2_DiSpProject.services;

import com.example.h2_DiSpProject.entity.PersonEntity;
import com.example.h2_DiSpProject.exceptions.PersonNotFoundException;
import com.example.h2_DiSpProject.exceptions.PersonUpdateException;
import com.example.h2_DiSpProject.exceptions.WrongPersonDataException;
import com.example.h2_DiSpProject.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    private PersonEntity person = new PersonEntity(
            "Пушкин Александр Сергеевич",
            "06.06.1799"
    );

    PersonServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.personService = new PersonService(personRepository);
    }

    @Test
    void insertPersonShouldBeSuccess() throws WrongPersonDataException {
        when(personRepository.save(any())).thenReturn(person);

        PersonEntity result = personService.insertPerson(person);

        assertNotNull(result);

        assertEquals(person, result);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void insertPersonFailTest() {
        PersonEntity result = new PersonEntity(anyString(), anyString());

        try {
            personService.insertPerson(result);
        } catch (WrongPersonDataException e) {
            if (e.getMessage().equals("Was input wrong data!")) {
                return;
            }
        }

        fail();
        verify(personRepository, times(0)).save(any());
    }

    @Test
    void findPersonShouldBeSuccess() throws PersonNotFoundException {
        when(personRepository.findById(anyInt())).thenReturn(Optional.ofNullable(person));
        PersonEntity test_person = personService.getOnePerson(anyInt());

        assertNotNull(test_person);
    }

    @Test
    void findPersonFailTest() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());
        try {
            personService.getOnePerson(anyInt());
        } catch (PersonNotFoundException e) {
            if (e.getMessage().equals("Person not found")) {
                return;
            }
        }

        fail();
        verify(personRepository, times(1)).findById(anyInt());
    }

    @Test
    void updatePersonShouldBeSuccess() throws PersonUpdateException, WrongPersonDataException {
        when(personRepository.findById(anyInt())).thenReturn(Optional.ofNullable(person));

        PersonEntity person_spy = spy(person);

        when(person_spy.getFullName()).thenReturn(person.getFullName());
        when(person_spy.getBirthDate()).thenReturn(person.getBirthDate());

        when(personService.updatePerson(anyInt(), person_spy)).thenReturn(person);

        PersonEntity result = personService.updatePerson(anyInt(), person_spy);

        verify(personRepository, times(1)).save(any(PersonEntity.class));

        assertNotNull(result);
        assertEquals(person, result);
    }

    @Test
    void updatePersonFailTest_WrongPersonDataException_FullName() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.ofNullable(person));

        PersonEntity person_spy = spy(person);

        when(person_spy.getFullName()).thenReturn("Non-correct Name");
        when(person_spy.getBirthDate()).thenReturn(person.getBirthDate());

        PersonEntity result = new PersonEntity();

        try {
            result = personService.updatePerson(anyInt(), person_spy);
        } catch (WrongPersonDataException e) {
            if(e.getMessage().equals("Was input wrong data")){
                return;
            }
        } catch (PersonUpdateException e) {
            e.printStackTrace();
        }

        fail();
        verify(personRepository, times(0)).save(any(PersonEntity.class));
        assertNotEquals(person, result);
    }

    @Test
    void updatePersonFailTest_WrongPersonDataException_Date() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.ofNullable(person));

        PersonEntity person_spy = spy(person);

        when(person_spy.getFullName()).thenReturn(person.getFullName());
        when(person_spy.getBirthDate()).thenReturn("NonCorrectDate");

        PersonEntity result = new PersonEntity();

        try {
            result = personService.updatePerson(anyInt(), person_spy);
        } catch (WrongPersonDataException e) {
            if(e.getMessage().equals("Was input wrong date\tright format:\tdd.mm.YYYY")){
                return;
            }
        } catch (PersonUpdateException e) {
            e.printStackTrace();
        }

        fail();
        verify(personRepository, times(0)).save(any(PersonEntity.class));
        assertNotEquals(person, result);
    }

    @Test
    void updatePersonFailTest_PersonUpdateException() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());

        PersonEntity person_spy = spy(person);
        PersonEntity result = new PersonEntity();

        when(person_spy.getFullName()).thenReturn(person.getFullName());
        when(person_spy.getBirthDate()).thenReturn("NonCorrectDate");

        try {
            result = personService.updatePerson(anyInt(), person_spy);
        } catch (WrongPersonDataException e) {
            e.printStackTrace();
        } catch (PersonUpdateException e) {
            if(e.getMessage().equals("Person not found")) {
                return;
            }
        }

        fail();
        verify(personRepository, times(0)).save(any(PersonEntity.class));
        assertNotEquals(person, result);
    }

    @Test
    void deletePersonShouldBeSuccess() throws PersonNotFoundException {
        PersonEntity result;

        when(personRepository.findById(anyInt())).thenReturn(Optional.ofNullable(person));

        result = personService.deletePerson(anyInt());

        assertNotNull(result);
        verify(personRepository, times(1)).deleteById(anyInt());
        assertEquals(person, result);
    }

    @Test
    void deletePersonFailTest() {
        PersonEntity result = null;

        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());

        try {
            result = personService.deletePerson(anyInt());
        } catch (PersonNotFoundException e) {
            if(e.getMessage().equals("Person not found")) {
                return;
            }
        }

        fail();
        assertNull(result);
        assertNotEquals(person, result);

        verify(personRepository, times(0)).deleteById(anyInt());
    }
}