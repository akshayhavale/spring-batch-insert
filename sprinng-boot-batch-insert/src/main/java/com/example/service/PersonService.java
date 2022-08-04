package com.example.service;

import com.example.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    public void saveAll(List<PersonDto> personsDto);
    
    
}
