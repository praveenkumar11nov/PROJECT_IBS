package com.bcits.bfm.serviceImpl.electricityMetersManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bcits.bfm.model.OTConsumptions;
import com.bcits.bfm.service.electricityMetersManagement.UnMeteredChildService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Service
public class UnMeteredChildimpl  extends GenericServiceImpl<OTConsumptions> implements UnMeteredChildService {

	@SuppressWarnings("unchecked")
	@Override
	public List<OTConsumptions> findAll(int id) {
		return getAllDetails(entityManager.createNamedQuery("OTConsumptions.findAll").setParameter("id", id).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> meterStatusEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> oTConsumptionsMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final OTConsumptions values = (OTConsumptions) iterator.next();
				oTConsumptionsMap = new HashMap<String, Object>();
				oTConsumptionsMap.put("id", values.getId());
				oTConsumptionsMap.put("otId", values.getOtId());
				oTConsumptionsMap.put("oTMonth", values.getoTMonth());
				oTConsumptionsMap.put("previousReading", values.getPreviousReading());
				oTConsumptionsMap.put("presentReading", values.getPresentReading());
				oTConsumptionsMap.put("meterConstant", values.getMeterConstant());
				oTConsumptionsMap.put("units",values.getUnits());	
				oTConsumptionsMap.put("status", values.getStatus());
			
			result.add(oTConsumptionsMap);
	     }
      return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OTConsumptions> getChildId(int id) {
		return entityManager.createNamedQuery("OTConsumptions.getAllChildIds").setParameter("id", id).getResultList();

	}
	
	@Override
	public Double getEntityByAccountId(int accountID,String typeOfService) {
		return (Double)entityManager.createNamedQuery("OTConsumptions.getBillEntityByAccountId").setParameter("typeOfService", typeOfService).setParameter("accountID", accountID).getSingleResult();
	}

	@Override
	public int getMaxConsuptionId(int installId) {
		try{
			return (Integer)entityManager.createNamedQuery("OTConsumptions.getMaxConsuptionId").setParameter("installId", installId).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}
	
	
	

}
