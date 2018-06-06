package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.MaintainanceTypes;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for MaintainanceTypesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface MaintainanceTypesService extends
		GenericService<MaintainanceTypes> {

	/**
	 * Find all MaintainanceTypes entities.
	 * 
	 * @return List<MaintainanceTypes> all MaintainanceTypes entities
	 */
	public List<MaintainanceTypes> findAll();

	/**
	 * @param model
	 * @return
	 */
	public com.bcits.bfm.model.MaintainanceTypes getBeanObject(
			Map<String, Object> model);
}