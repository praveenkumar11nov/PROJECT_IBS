package com.bcits.bfm.serviceImpl;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.PrepaidServiceMaster;
import com.bcits.bfm.service.PrepaidServiceMasterSs;

@Repository
public class PrepaidServiceMasterImpl extends GenericServiceImpl<PrepaidServiceMaster> implements PrepaidServiceMasterSs {

	@Override
	public List<?> getAllServiceNames() {
       try{
    	   return readData(entityManager.createNamedQuery("PrepaidServiceMaster.getData").getResultList());
       }catch(Exception e){
    	   e.printStackTrace();
       }
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List readData(List<?> seList){
		List<Map<String, Object>> resultList=new ArrayList<>();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		Map<String, Object> maplist=null;
		for(Iterator<?> iterator=seList.iterator();iterator.hasNext();){
			final Object[] vaObjects=(Object[]) iterator.next();
			
			maplist=new HashMap<>();
			maplist.put("serviceId", vaObjects[0]);
			maplist.put("service_Name", vaObjects[1]);
			maplist.put("fromDate", dateFormat.format((Date)vaObjects[2]));
			maplist.put("toDate", dateFormat.format((Date)vaObjects[3]));
			maplist.put("status", vaObjects[4]);
			resultList.add(maplist);
		}
		
		return resultList;
	}

	@Override
	public List<?> readAllServices() {
		try{
			return entityManager.createNamedQuery("PrepaidServiceMaster.getDropService").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setServiceStatus(int serviceId, String operation, HttpServletResponse response) {
          try{
        	  PrintWriter out=response.getWriter();
        	  if(operation.equalsIgnoreCase("activate")){
        		  entityManager.createNamedQuery("PrepaidServiceMaster.setStatus").setParameter("serviceId", serviceId).setParameter("status", "Active").executeUpdate();
        		  out.write("Service is activated");
        	  }else{
        		  entityManager.createNamedQuery("PrepaidServiceMaster.setStatus").setParameter("serviceId", serviceId).setParameter("status", "Inactive").executeUpdate();
        		  out.write("Service is de-activated");
        	  }
          }catch(Exception e){
        	  e.printStackTrace();
          }
	}

	@Override
	public List<Map<String, Object>> getMinMaxDate(int serviceId) {
		try{
			return getMinMaxDate1(entityManager.createNamedQuery("PrepaidServiceMaster.minMaxDate").setParameter("serviceId", serviceId).getResultList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private List getMinMaxDate1(List<?> datesList)
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> minMaxDates = null;
		 for (Iterator<?> iterator = datesList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				minMaxDates = new HashMap<String, Object>();
				minMaxDates.put("fromDate",(Date)values[0]);
				minMaxDates.put("toDate",(Date)values[1]);
			    result.add(minMaxDates);
	     }
      return result;
	}
}
