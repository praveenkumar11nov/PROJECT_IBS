package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.Classified;


public interface ClassifiedService extends GenericService<Classified>{

	List<?> findAllCheckedData();
	
	public List<Object[]> exportToExcel();
	
	

}