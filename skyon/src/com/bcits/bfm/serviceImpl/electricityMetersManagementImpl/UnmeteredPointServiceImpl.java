package com.bcits.bfm.serviceImpl.electricityMetersManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.OtherInstallation;
import com.bcits.bfm.service.electricityMetersManagement.UnmeteredPointService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class UnmeteredPointServiceImpl extends GenericServiceImpl<OtherInstallation> implements UnmeteredPointService{

	@Override
	public List<?> findAll() {
		return getAllDetails(entityManager.createNamedQuery("OtherInstallation.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> otherInstallationEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> meterStatusMap = null;
		 for (Iterator<?> iterator = otherInstallationEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				meterStatusMap = new HashMap<String, Object>();
				
				meterStatusMap.put("id", (Integer)values[0]);
				meterStatusMap.put("insName", (String)values[1]);
				meterStatusMap.put("insLocation", (String)values[2]);
				meterStatusMap.put("meteredStatus", (String)values[3]);
				meterStatusMap.put("meteredConstant", (Double)values[4]);
				meterStatusMap.put("status", (String)values[5]);
			
			result.add(meterStatusMap);
	     }
     return result;
	}
	
	
	@Override
	public void setParameterStatus(int id, String operation,
			HttpServletResponse response) {
		try
		  {
		   PrintWriter out = response.getWriter();

		   if(operation.equalsIgnoreCase("activate"))
		   {
		    entityManager.createNamedQuery("OtherInstallation.UpdateStatus").setParameter("status", "Active").setParameter("id", id).executeUpdate();
		    out.write("Unmetered Point Activated");
		   }
		   else
		   {
		    entityManager.createNamedQuery("OtherInstallation.UpdateStatus").setParameter("status", "Inactive").setParameter("id", id).executeUpdate();
		    out.write("Unmetered Point DeActivated");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
		
	}
	
}
