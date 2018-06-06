package com.bcits.bfm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Classified;
import com.bcits.bfm.service.ClassifiedService;

@Repository
public class ClassifiedServiceImpl extends GenericServiceImpl<Classified>  implements ClassifiedService{



	@Override
	public List<?> findAllCheckedData() {
		return readClassifiedCheckedData(entityManager.createNamedQuery("Classified.findAllCheckedData").getResultList());
		
	}


	private List<?> readClassifiedCheckedData(List resultCHaeckedList) {
		
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		 Map<String, Object> classifiedData = null;
		 for (Iterator<?> iterator = resultCHaeckedList.iterator(); iterator.hasNext();)
		 {
			 final Object[] values = (Object[]) iterator.next();
			 classifiedData = new HashMap<String, Object>();
			 
			 classifiedData.put("prePaidId", (Integer)values[0]);
			 classifiedData.put("propertyName",(String)values[1]);		
			 classifiedData.put("personName",(String)values[2]);
			 classifiedData.put("emailId", (String)values[3]);
			 classifiedData.put("mobile_No",(String)values[4]);		
			 classifiedData.put("information",(String)values[5]);		
			
			 resultData.add(classifiedData); 
		 }
		 return resultData;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> exportToExcel() {
		try{
			return entityManager.createNamedQuery("Classified.findAllCheckedData").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
