package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.example.dao.PersonDao;
import com.example.dto.PersonDto;
import com.example.entity.Person;
import com.example.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	private PersonRepository personRepository;

	@Autowired
	private PersonDao personDao;

	@Autowired
	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public void saveAll(List<PersonDto> personsDto) {
		List<Person> persons = personsDto.stream().map(dto -> {
			Person person = new Person();
			person.setJobTitle(dto.getJobTitle());
			person.setFirstName(dto.getFirstName());
			person.setLastName(dto.getLastName());
			person.setEmailAddress(dto.getEmailAddress());
			return person;
		}).collect(Collectors.toList());

		StopWatch sw = new StopWatch();
		sw.start();
		/**
		 * Method 1) Saved by using JPA saveAll of method = takes 24 secs for 1 lack
		 * record insertion with No batch size
		 */
//        methodOne(persons);

		/**
		 * Method 2) Using Hibernate EntityManager = takes 21 secs for 1 lack record
		 * insertion with 500 batch size
		 */
//        personDao.saveByEntityManager(persons);

		/**
		 * Method 3) Using JDBC Batch =  3.9558005 secs By using ?rewriteBatchedStatements=true this in jdbc url
		 * or else gives worst performance
		 * 
		 * Winner -> SequenceId + Batch_Size + JDBC batch 
		 */
		personDao.saveAllByJDBCBatch(persons);

		sw.stop();
		logger.debug("Process completes in : {} ", sw.getTotalTimeSeconds());

	}

	private void methodOne(List<Person> persons) {
		personRepository.saveAll(persons);
	}
}
