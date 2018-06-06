package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.City;
import com.bcits.bfm.service.GenericService;

public interface CityService extends GenericService<City>
{

	public List<City> findAll();

	public Integer getIdBasedOnCityName(String string);

	public List<City> findNamebyId(int id);

	public List<City> cityListAfterPassingCityNameAndStId(String cityName,
			int stateId);

}