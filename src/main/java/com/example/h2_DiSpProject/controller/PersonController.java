package com.example.h2_DiSpProject.controller;

import com.example.h2_DiSpProject.entity.PersonEntity;
import com.example.h2_DiSpProject.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity insertPerson(@RequestBody PersonEntity person) {
        try {
            return ResponseEntity.ok(personService.insertPerson(person));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getOnePerson(@RequestParam int id) {
        try {
            return ResponseEntity.ok(personService.getOnePerson(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updatePerson(@RequestParam int id, String[] newData) {
        try {
            return ResponseEntity.ok(personService.updatePerson(id, new PersonEntity(newData[0], newData[1])));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable int id) {
        try {
            return ResponseEntity.ok(personService.deletePerson(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
