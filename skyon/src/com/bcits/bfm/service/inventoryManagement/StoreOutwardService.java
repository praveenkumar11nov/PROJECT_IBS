package com.bcits.bfm.service.inventoryManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.StoreOutward;
import com.bcits.bfm.service.GenericService;

public interface StoreOutwardService extends GenericService<StoreOutward>{

	/**
	 * Find all StoreOutward entities.
	 * 
	 * @return List<StoreOutward> all StoreOutward entities
	 */
	public abstract List<StoreOutward> findAll();

	public abstract List<?> readAll();
	
	public abstract List<Object[]> readAllStore();

	public abstract StoreOutward setParameters(Map<String, Object> map, String userName);

	public abstract StoreOutward setParametersforupdate(
			Map<String, Object> map, String userName);

	public abstract void setoutStatus(int psId, String operation,
			HttpServletResponse response);

}