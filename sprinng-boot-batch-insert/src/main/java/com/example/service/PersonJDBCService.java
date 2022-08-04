package com.example.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.dao.PersonDao;
import com.example.dto.PersonDto;
import com.example.entity.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PersonJDBCService {

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonDao personDao;

	@Async
	public void saveByJDBC() {
		// Get data from file
		try {
			File file = ResourceUtils.getFile("classpath:ExportJson.json");
			ObjectMapper mapper = new ObjectMapper();
			List<PersonDto> dtos = mapper.readValue(file, new TypeReference<List<PersonDto>>() {
			});
			personService.saveAll(dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public void updateByJDBC() {

		Person person = new Person();
		person.setId(1L);
		person.setEmailAddress("akshay@gmail.com");

		List<Person> persons = new ArrayList<>();
		persons.add(person);

		personDao.updateAllByJDBCBatch(persons);

	}

}
