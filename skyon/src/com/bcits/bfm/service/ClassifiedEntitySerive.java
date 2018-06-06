package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.ClassifiedEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Person;

public interface ClassifiedEntitySerive extends GenericService<ClassifiedEntity>{
	
	public List<?> getData();
	public List<Person> getPersonName(int personId);
	public List<Contact> getMobilenumbers();
    public List<Contact> geEmailId();
}
