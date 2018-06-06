package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.Country;
import com.bcits.bfm.service.GenericService;

public interface CountryService extends GenericService<Country>
{

	public List<Country> findAll();

	public List<Country> findIdbyName(String cname);

	

}