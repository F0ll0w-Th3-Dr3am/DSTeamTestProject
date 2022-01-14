package com.example.h2_DiSpProject.services;

import com.example.h2_DiSpProject.entity.PersonEntity;
import com.example.h2_DiSpProject.exceptions.PersonUpdateException;
import com.example.h2_DiSpProject.exceptions.WrongPersonDataException;
import com.example.h2_DiSpProject.repository.PersonRepository;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Log
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Boolean insertPerson(PersonEntity person) throws Exception{
        if (!isDataCorrect(person.getFullName(), person.getBirthDate())) {
            log.warning("Insert failed:\tWrong params!");

            return false;
//            throw new WrongPersonDataException("Was input wrong data!");
        }

        log.info("Person inserted:\t\t" +   // Если запрашивать через метод - при выводе в консоль id не инкрементируется
                person.getFullName() + '\t' + person.getBirthDate()
        );

        try{
            personRepository.save(person);
            return true;
        } catch (Exception e) {
            log.warning(e.getMessage());
            return false;
        }
    }

    public PersonEntity getOneUser(int id) {
        PersonEntity person = personRepository.findById(id).get();
        log.info("Person was found:\t\t" + getFullData(person));

        return person;
    }

    public PersonEntity updatePerson(int id, String[] newData) throws WrongPersonDataException, PersonUpdateException {
        if(newData.length == 0) {
            throw new PersonUpdateException("Don`t have new params");
        } else {
            PersonEntity person = personRepository.findById(id).get();
            log.info("Person found:\t\t\t" + person);

            if (!newData[0].equals("")) {
                if (isFullNameCorrect(newData[0])) {
                    log.info("New fullName:\t\t\t" + newData[0]);
                    person.setFullName(newData[0]);
                } else {
                    throw new WrongPersonDataException("Was input wrong data");
                }
            }
            if (!newData[1].equals("")) {
                if (isDateCorrect(newData[1])) {
                    log.info("New birthDate:\t\t\t" + newData[1]);
                    person.setBirthDate(newData[1]);
                } else {
                    log.warning("Was input wrong date:\t" + newData[1]);
                    throw new WrongPersonDataException("Was input wrong date\tright format:\tdd.mm.YYYY");
                }
            }

            log.info("Person was updated to:\t\t" + getFullData(person));

            return personRepository.save(person);
        }
    }

    public PersonEntity deletePerson(int id) {
        PersonEntity deleted_person = personRepository.findById(id).get();
        personRepository.deleteById(id);

//        updateDataBase(id);

        return deleted_person;
    }

    private String getFullData(PersonEntity person) {
        return String.format("{id:%s, fullName:%s, birthDate:%s}",
                person.getId(), person.getFullName(), person.getBirthDate()
        );
    }

    private Boolean isDataCorrect(String full_name, String birth_date) {
        return  isFullNameCorrect(full_name) && isDateCorrect(birth_date);
    }

    private Boolean isFullNameCorrect(String full_name) {
        String regex = "^([^Ы&&[А-ЯЁ]][а-яё]+)( [^Ы&&[А-ЯЁ]][а-яё{0,}]+)" +     // Вроде рабочая регулярка для проверки ФИО на кириллице
                "( [^Ы&&[А-ЯЁ]][а-яё{0,}]+(евич|ович|ивич|овна|евна|чна))?$";   // БАГУЕТСЯ ПЕРВЫЙ СИМВОЛ ПОДСТРОКИ(можно ставить любой символ кроме Ы)
        Pattern pattern = Pattern.compile(regex);

        Boolean isCorrect = pattern.matcher(full_name).matches();
        if(!isCorrect) log.warning("Wrong format!\tNeed format: 'Lastname Name Patronymic(not blank)'");
        return isCorrect;

//        return true;
    }

    private Boolean isDateCorrect(String birth_date) {                                          // Костыль для проверки корректности введенной даты формата дд.мм.ГГГГ
        String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)" +  // Regex справедло работает для [01.01.1600 - 31.12.9999]
                "(?:0?[1,3-9]|1[0-2])\\2))" +                                                   // Исключает возможность 30.02.xxxx, 29.02.2011 и т.д.
                "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|" +
                "^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|" +
                "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)" +
                "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(birth_date).matches();
    }

    // TODO Надо прикрутить сдвиг записей в БД при удалении элемента
//    private void updateDataBase(int id) {
//        int counter = 0;
//        for(var iter: personRepository.findAll()) {
//            if(iter.getId() >= id) {
//                iter.setId(id + counter++);
//                personRepository.save(iter);
//
//                log.info("Object " + getFullData(iter) + " update");
//            }
//        }
//    }
}
