package com.example;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.service.PersonJDBCService;

@SpringBootApplication
@EnableAsync
public class SprinngBootBatchInsertApplication {

	@Autowired
	private PersonJDBCService personJDBCService;

	public static void main(String[] args) {
		SpringApplication.run(SprinngBootBatchInsertApplication.class, args);
	}

	@PostConstruct
	public void callSave() {
		personJDBCService.saveByJDBC();
	}

	@PostConstruct
	public void update() {
		personJDBCService.updateByJDBC();
	}
}
