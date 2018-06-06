package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Form;
import com.bcits.bfm.model.Module;
import com.bcits.bfm.service.userManagement.FormDefService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class FormDefServiceImpl extends GenericServiceImpl<Form> implements
		FormDefService {

	// find product by id
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.FormDefService#findById(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Module findById(int id) {
		Module instance = entityManager.find(Module.class, id);
		return instance;
	}

	// get all products

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.FormDefService#getAll()
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Form> getAll() {
		return entityManager.createNamedQuery("Form.findAll").getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.FormDefService#count()
	 */
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
