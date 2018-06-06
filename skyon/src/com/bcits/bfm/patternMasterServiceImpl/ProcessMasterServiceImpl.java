package com.bcits.bfm.patternMasterServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.patternMasterEntity.ProcessMaster;
import com.bcits.bfm.patternMasterService.ProcessMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ProcessMasterServiceImpl extends GenericServiceImpl<ProcessMaster> implements ProcessMasterService {

	@Override
	public List<?> readAllProcessName() {
		return getProcessnames(entityManager.createNamedQuery("ProcessMaster.readAllProcess").getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List getProcessnames(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("processId",(Integer) values[0]);
			 storeData.put("processName",(String)values[1]);		
			
		     result.add(storeData); 
		 }
		 return result;
	}
}
