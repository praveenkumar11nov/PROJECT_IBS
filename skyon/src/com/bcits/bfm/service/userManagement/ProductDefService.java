package com.bcits.bfm.service.userManagement;

import java.util.List;

import com.bcits.bfm.model.Product;
import com.bcits.bfm.service.GenericService;

public interface ProductDefService extends GenericService<Product> {

	// find product by id
	public Product findById(int id);

	public List<Product> getAll();

	public int count();

}