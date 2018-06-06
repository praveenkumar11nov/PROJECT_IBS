package com.bcits.bfm.serviceImpl.electricityBillsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.GasDistributionLosses;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.GasDistributionLossesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class GasDistributionLossesServiceImpl extends GenericServiceImpl<GasDistributionLosses> implements GasDistributionLossesService 
{
	static Logger logger = LoggerFactory.getLogger(GasDistributionLossesServiceImpl.class);

	@Autowired
	ElectricityBillParameterService billParameterService;

	
	@Override
	public List<Map<String, Object>> findAll()
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsMap = null;
		 @SuppressWarnings("unchecked")
		List<Object> distributionLosses = entityManager.createNamedQuery("GasDistributionLosses.findAll").getResultList();
		 for (Iterator<?> iterator = distributionLosses.iterator(); iterator.hasNext();) 
		 {
			 final Object[] values = (Object[]) iterator.next();
				billsMap = new HashMap<String, Object>();				
				billsMap.put("gdlid", (Integer)values[0]);	
				billsMap.put("month",(Date)values[1]);
				billsMap.put("mainMeterReading", (Float)values[2]);	
				billsMap.put("subMeterSReading",(Float)values[3]);
				billsMap.put("lossUnits",(Float)values[4]);
				billsMap.put("lossPercentage",(Float)values[5]);
				billsMap.put("status",(String)values[6]);
				//billsMap.put("createdBy",gasDistributionLosses.getCreatedBy());
				//billsMap.put("lastUpdatedBy",gasDistributionLosses.getLastUpdatedBy());
				//billsMap.put("lastUpdatedDT",gasDistributionLosses.getLastUpdatedDT());
			    result.add(billsMap);
		}
     return result;
	}

	@Override
	public void setDistributionLossesStatus(int gdlid, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("Approve"))
			{
				entityManager.createNamedQuery("GasDistributionLosses.setDistributionLossesStatus").setParameter("status", "Approve").setParameter("gdlid", gdlid).executeUpdate();
				out.write("Record actived");
			}
			else
			{
				entityManager.createNamedQuery("GasDistributionLosses.setDistributionLossesStatus").setParameter("status", "Inactive").setParameter("gdlid", gdlid).executeUpdate();
				out.write("Record inactived");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getDistributionLoss(
			int month, int year) {
	
		List<Integer> elbillId=entityManager.createNamedQuery("GasDistributionLosses.getElBillId").setParameter("month", month).setParameter("year", year).getResultList();
        float totalUnit=0.0f;
		logger.info(""+elbillId.size());
		Map<Object,Object> response=new HashMap<>();
		for (Integer integer : elbillId) {
			logger.info("integer:::::::::::::"+integer);
			String paraMeterName4 = "Units";
			String units = null;
			units = billParameterService.getParameterValue(integer,"Gas", paraMeterName4);
			logger.info("units -------------- " + units);
			totalUnit+=Float.parseFloat(units);
			logger.info("totalUnit:::::::"+totalUnit);
			
		}
		response.put("totalUnit", totalUnit);
		String status=null;
		
		try {
			status=(String) entityManager.createQuery("SELECT g.status FROM GasDistributionLosses g WHERE EXTRACT(month FROM g.month)='"+month+"' AND EXTRACT(year FROM g.month)='"+year+"'").getSingleResult();
		} catch (NoResultException e) {
		}
		if(status==null){
			status="ADD";
		}
		response.put("status", status);
		return response;
	}

}
