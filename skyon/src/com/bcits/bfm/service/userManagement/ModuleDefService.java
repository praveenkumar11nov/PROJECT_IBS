package com.bcits.bfm.service.userManagement;

import java.util.List;

import com.bcits.bfm.model.Module;
import com.bcits.bfm.service.GenericService;

public interface ModuleDefService extends GenericService<Module> {

	// find product by id
	public Module findById(int id);

	public List<Module> getAll();

	public int count();

}