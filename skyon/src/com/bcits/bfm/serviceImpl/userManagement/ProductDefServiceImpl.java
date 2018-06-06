package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Product;
import com.bcits.bfm.service.userManagement.ProductDefService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class ProductDefServiceImpl extends GenericServiceImpl<Product>
		implements ProductDefService {

	// find product by id
	
	 
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Product findById(int id) {
		Product instance = entityManager.find(Product.class, id);
		return instance;
	}

	// get all products

	
	 
	 
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Product> getAll() {
		return entityManager.createNamedQuery("Product.findAll")
				.getResultList();
	}

	
	
	@SuppressWarnings("rawtypes")
	@Override
	public int count() {
		List al = entityManager.createNamedQuery("Product.count")
				.getResultList();
		int val = Integer.parseInt(al.get(0).toString());
		BfmLogger.logger.info("size " + val);
		return val;
	}

}
