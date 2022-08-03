package com.example.controller;

import com.example.dto.PersonDto;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping(value = "/saves")
    public @ResponseBody  String saveAll(@RequestBody List<PersonDto> personsDto){
        personService.saveAll(personsDto);
        return "SUCCESS";
    }

}
