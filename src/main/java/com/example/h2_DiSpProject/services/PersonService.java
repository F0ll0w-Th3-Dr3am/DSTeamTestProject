package com.example.h2_DiSpProject.services;

import com.example.h2_DiSpProject.entity.PersonEntity;
import com.example.h2_DiSpProject.exceptions.PersonNotFoundException;
import com.example.h2_DiSpProject.exceptions.PersonUpdateException;
import com.example.h2_DiSpProject.exceptions.WrongPersonDataException;
import com.example.h2_DiSpProject.repository.PersonRepository;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PersonService {
    private PersonRepository personRepository;

    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonEntity insertPerson(PersonEntity person) throws WrongPersonDataException {
        if (!isDataCorrect(person)) {
            throw new WrongPersonDataException("Was input wrong data!");
        }
        return personRepository.save(person);
    }

    public PersonEntity getOnePerson(int id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found"));
    }

    public PersonEntity updatePerson(int id, PersonEntity newPerson) throws WrongPersonDataException, PersonUpdateException {
        personRepository.findById(id).orElseThrow(() -> new PersonUpdateException("Person not found"));

        if (!newPerson.getFullName().equals("") && !isFullNameCorrect(newPerson)) {
            throw new WrongPersonDataException("Was input wrong data");
        }
        if (!newPerson.getBirthDate().equals("") && !isDateCorrect(newPerson)) {
            throw new WrongPersonDataException("Was input wrong date\tright format:\tdd.mm.YYYY");
        }

        newPerson.setId(id);
        return personRepository.save(newPerson);
    }

    public PersonEntity deletePerson(int id) throws PersonNotFoundException {
        PersonEntity deleted_person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found"));
        personRepository.deleteById(id);

        return deleted_person;
    }

    protected Boolean isDataCorrect(PersonEntity person) {
        return isFullNameCorrect(person) && isDateCorrect(person);
    }

    protected Boolean isFullNameCorrect(PersonEntity person) {
        String regex = "^([[^ЫЪЬ]&&[А-ЯЁ]][а-яё]+)( [[^ЫЪЬ]&&[А-ЯЁ]][а-яё{0,}]+)" +
                "( [[^ЫЪЬ]&&[А-ЯЁ]][а-яё{0,}]+(евич|ович|ивич|овна|евна|чна))?$";
        Pattern pattern = Pattern.compile(regex);

        Boolean isCorrect = pattern.matcher(person.getFullName()).matches();
        return isCorrect;
    }

    protected Boolean isDateCorrect(PersonEntity person) {
        String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)" +
                "(?:0?[1,3-9]|1[0-2])\\2))" +
                "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|" +
                "^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|" +
                "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)" +
                "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(person.getBirthDate()).matches();
    }
}
