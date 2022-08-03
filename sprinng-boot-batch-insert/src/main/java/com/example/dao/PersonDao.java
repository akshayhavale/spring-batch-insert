package com.example.dao;

import java.util.List;

import com.example.entity.Person;

public interface PersonDao {
	
	public void saveByEntityManager(List<Person> persons);
	
	public void saveAllByJDBCBatch(List<Person> persons);

}
