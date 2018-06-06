package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JcNotes;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JcNotesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JcNotesService extends GenericService<JcNotes> {	
	/**
	 * Find all JcNotes entities.
	 * 
	 * @return List<JcNotes> all JcNotes entities
	 */
	public List<JcNotes> findAll();

	public List<?> readData(int jcId);

	public JcNotes setParameters(int jcId, JcNotes jobNotes, String userName,
			Map<String, Object> map);

	public Object delteJobNotes(JcNotes jobNotes);
}