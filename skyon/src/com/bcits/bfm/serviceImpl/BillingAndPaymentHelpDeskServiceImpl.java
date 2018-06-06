package com.bcits.bfm.serviceImpl;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BillingAndPaymentHelpDesk;
import com.bcits.bfm.service.BillingAndPaymentHelpDeskService;

@Repository
public class BillingAndPaymentHelpDeskServiceImpl extends GenericServiceImpl<BillingAndPaymentHelpDesk> implements BillingAndPaymentHelpDeskService{

	@Override
	public List<?> getAllData() {
		try{
		return getData(entityManager.createNamedQuery("BillingAndPaymentHelpDesk.readAllData").getResultList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
    
	@SuppressWarnings("rawtypes")
	public List getData(List<?> bpHelpDesk){
		
		List<Map<String, Object>> resultList=new ArrayList<>();
		Map<String, Object> map=null;
		
		for(Iterator<?> iterator=bpHelpDesk.iterator(); iterator.hasNext();){
			final Object[] values=(Object[]) iterator.next();
			map=new HashMap<String, Object>();
			map.put("id", (Integer)values[0]);
			map.put("propertyNo", (String)values[1]);
			map.put("person_Name", (String)values[2]);
			map.put("helpTopic", (String)values[3]);
			map.put("issue_Details", (String)values[4]);
			map.put("otherHelpTopic", (String)values[5]);
		    DateFormat fDateFormat=new SimpleDateFormat("MM-dd-yyyy");
		    if(values[6]!=null){
		    	try{
			map.put("createdDate",fDateFormat.format((Timestamp)values[6]));
		    	}catch(Exception e){}
		    }
			map.put("status", (String)values[7]);
			map.put("remarks", (String)values[8]);
			if(values[9]!=null)
				try{
			map.put("reSolvedDate",fDateFormat.format((Timestamp)values[9]));
				}catch (Exception e) {
					
				}
			map.put("mobileNo", (String)values[10]);
			map.put("emailId", (String)values[11]);
			resultList.add(map);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchData() {
		try{
		return entityManager.createNamedQuery("BillingAndPaymentHelpDesk.readAllData").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
