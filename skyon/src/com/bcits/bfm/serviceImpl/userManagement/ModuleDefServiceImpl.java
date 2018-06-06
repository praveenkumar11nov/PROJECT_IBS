package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Module;
import com.bcits.bfm.service.userManagement.ModuleDefService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class ModuleDefServiceImpl extends GenericServiceImpl<Module> implements
		ModuleDefService {

	// find product by id
	
	
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Module findById(int id) {
		Module instance = entityManager.find(Module.class, id);
		return instance;
	}

	// get all products

	
	
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Module> getAll() {
		return entityManager.createNamedQuery("Module.findAll").getResultList();
	}

	
	
	 
	@SuppressWarnings("rawtypes")
	@Override
	public int count() {
		List al = entityManager.createNamedQuery("Module.count")
				.getResultList();
		int val = Integer.parseInt(al.get(0).toString());
		BfmLogger.logger.info("size " + val);
		return val;
	}

}
