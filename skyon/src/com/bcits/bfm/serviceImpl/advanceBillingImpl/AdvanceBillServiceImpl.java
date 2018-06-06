package com.bcits.bfm.serviceImpl.advanceBillingImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.service.advanceBilling.AdvanceBillingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AdvanceBillServiceImpl extends GenericServiceImpl<AdvanceBill> implements AdvanceBillingService{

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvanceBill> findAll() {
		
		return getAllDetailsList(entityManager.createNamedQuery("AdvanceBill.findAll").getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> billEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
									
				billsMap.put("abBillId", (Integer)values[0]);	
				billsMap.put("typeOfService",(String)values[1]);				
				billsMap.put("accountId",(Integer)values[2]);
				billsMap.put("abBillDate",(Date)values[3]);			
				billsMap.put("abEndDate",(Date)values[4]);				
				billsMap.put("abBillAmount", (Double)values[5]);
				billsMap.put("abBillNo", (String)values[6]);
				billsMap.put("status", (String)values[7]);
				billsMap.put("postType", (String)values[8]);
				billsMap.put("noMonth",(Integer)values[9]);
				billsMap.put("units",(Float)values[10]);
				billsMap.put("transactionCode",(String)values[11]);
				billsMap.put("accountNo", (String)values[12]);
				String personName = "";
				personName = personName.concat((String)values[13]);
				if((String)values[13] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[14]);
				}
				billsMap.put("personName", personName);
				billsMap.put("property_No",(String)values[15]);
				billsMap.put("blockName",(String)values[16]);
				
			result.add(billsMap);
	     }
      return result;
	}
	@Override
	public void setApproveBill(int elBillId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("AdvanceBill.setApproveBill").setParameter("status", "Approved").setParameter("elBillId", elBillId).executeUpdate();
				out.write("Bill Approved Successfully");
			}
			else
			{
				out.write("Bill Already Approved");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
		
	}
	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("AdvanceBill.findPersonForFilters").getResultList();
		return details;
	}
	@Override
	public List<?> findAdvanceBillData() {
		return getAllDetailsList(entityManager.createNamedQuery("AdvanceBill.findAll").getResultList());	
	}
}
