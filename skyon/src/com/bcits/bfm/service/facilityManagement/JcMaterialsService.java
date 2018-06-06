package com.bcits.bfm.service.facilityManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JcMaterials;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JcMaterialsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JcMaterialsService extends GenericService<JcMaterials> {	

	/**
	 * Find all JcMaterials entities.
	 * 
	 * @return List<JcMaterials> all JcMaterials entities
	 */
	public List<JcMaterials> findAll();

	public List<?> readData(int jcId);

	public JcMaterials setParameters(int jcId, JcMaterials jcMaterials,
			String userName, Map<String, Object> map);

	public Object deleteJcMaterial(JcMaterials jcMaterials);

	public List<JcMaterials> readJobMaterials(int jcId);

	public void updateIssue(int jcId, int imId, int storeId, Double imQuantity,
			int uomId,String str);

	public List<String> readJobMaterialsStatus(int jcId);
}