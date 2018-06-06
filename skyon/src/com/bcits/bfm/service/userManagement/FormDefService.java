package com.bcits.bfm.service.userManagement;

import java.util.List;

import com.bcits.bfm.model.Form;
import com.bcits.bfm.model.Module;
import com.bcits.bfm.service.GenericService;

public interface FormDefService extends GenericService<Form> {

	// find product by id
	public Module findById(int id);

	public List<Form> getAll();

	public int count();

}