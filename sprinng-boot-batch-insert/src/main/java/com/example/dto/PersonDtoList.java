package com.example.dto;

import java.io.Serializable;
import java.util.List;

public class PersonDtoList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<PersonDto> personDtoList;

	public List<PersonDto> getPersonDtoList() {
		return personDtoList;
	}

	public void setPersonDtoList(List<PersonDto> personDtoList) {
		this.personDtoList = personDtoList;
	}

}
