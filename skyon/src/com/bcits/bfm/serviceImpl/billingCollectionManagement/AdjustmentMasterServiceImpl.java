package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AdjustmentMasterEntity;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdjustmentMasterServiceImpl extends GenericServiceImpl<AdjustmentMasterEntity> implements AdjustmentMasterService{

	@Override
	public List<?> findALL() {
		return entityManager.createNamedQuery("AdjustmentMasterEntity.findAll").getResultList();
	}

	@Override
	public void adjustmentMasterStatus(int id, String operation,
			HttpServletResponse response) {
		try
		{
			final PrintWriter out = response.getWriter();
			if("activate".equalsIgnoreCase(operation))
			{
				entityManager.createNamedQuery("AdjustmentMasterEntity.adjustmentMasterStatus").setParameter("status", "Active").setParameter("id", id).executeUpdate();
				out.write("Adjustment Master is actived");
			}
			else{
				entityManager.createNamedQuery("AdjustmentMasterEntity.adjustmentMasterStatus").setParameter("status", "Inactive").setParameter("id", id).executeUpdate();
				out.write("Adjustment Master is inactived");
			}
		} 
		catch (IOException e){
			//LOGGER.info("========== Error while creating print write object ===============");
		}
		
	}

	@Override
	public List<?> getAllAdjustmentName() {
		return getAccountNumbersData(entityManager.createNamedQuery("AdjustmentMasterEntity.getAllActiveAdjustmentName").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountNumbersData(List<?> accountNumbers){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> accountNumberMap = null;
		 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				accountNumberMap = new HashMap<String, Object>();
				
				accountNumberMap.put("adjMasterId", (Integer)values[0]);
				accountNumberMap.put("adjName", (String)values[1]);			
			     result.add(accountNumberMap);
	     }
     return result;
	}

}
