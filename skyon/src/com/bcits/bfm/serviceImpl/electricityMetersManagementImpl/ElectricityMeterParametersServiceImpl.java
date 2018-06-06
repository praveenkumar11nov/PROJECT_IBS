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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ElectricityMeterParametersEntity;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterParametersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ElectricityMeterParametersServiceImpl extends GenericServiceImpl<ElectricityMeterParametersEntity> implements ElectricityMeterParametersService  {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityMeterParametersEntity> findALL() {
		return entityManager.createNamedQuery("ElectricityMeterParametersEntity.findAll").getResultList();
	}

	@Override
	public void setParameterStatus(int elMeterParameterId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricityMeterParametersEntity.setParameterStatus").setParameter("status", "Active").setParameter("elMeterParameterId", elMeterParameterId).executeUpdate();
				out.write(" Meter parameter is activated");
			}
			else
			{
				entityManager.createNamedQuery("ElectricityMeterParametersEntity.setParameterStatus").setParameter("status", "Inactive").setParameter("elMeterParameterId", elMeterParameterId).executeUpdate();
				out.write("Meter parameter is de-activated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityMeterParametersEntity> findAllById(int elMeterId) {
		return getAllDetails(entityManager.createNamedQuery("ElectricityMeterParametersEntity.findAllById").setParameter("elMeterId", elMeterId).getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> parameterEntities) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> parameterMap = null;
		 for (Iterator<?> iterator = parameterEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				parameterMap = new HashMap<String, Object>();				
								
				parameterMap.put("elMeterParameterId", (Integer)values[0]);	
				parameterMap.put("mpmId",(Integer)values[1]);
				parameterMap.put("elMeterParameterValue", (String)values[2]);	
				parameterMap.put("elMasterParameterDataType",(String)values[3]);
				parameterMap.put("notes",(String)values[4]);
				parameterMap.put("elMeterParameterSequence",(Integer)values[5]);
				parameterMap.put("status",(String)values[6]);
				parameterMap.put("mpmName",(String)values[7]);
							
			result.add(parameterMap);
	     }
     return result;
	}
	
	@Override
	public void updateParameterStatusFromInnerGrid(int elMeterParameterId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			ElectricityMeterParametersEntity account = find(elMeterParameterId);
			
			if(account.getStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("ElectricityMeterParametersEntity.updateParameterStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("elMeterParameterId", elMeterParameterId).executeUpdate();
				out.write("Meter parameter is de-activated");
			}
			else
			{
				entityManager.createNamedQuery("ElectricityMeterParametersEntity.updateParameterStatusFromInnerGrid").setParameter("status", "Active").setParameter("elMeterParameterId", elMeterParameterId).executeUpdate();
				out.write("Meter parameter is activated");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<?> getMeterParameterNamesList(String typeOfService,int meterId) {
		List<?> parameterList = entityManager.createNamedQuery("ElectricityMeterParametersEntity.getMeterParameterNamesList").setParameter("typeOfService", typeOfService).setParameter("meterId", meterId).getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> parameterNameMap = null;
		for (Iterator<?> iterator = parameterList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				parameterNameMap = new HashMap<String, Object>();				
								
				parameterNameMap.put("mpmId", (Integer)values[0]);	
				parameterNameMap.put("mpmName",(String)values[1]);
			
			result.add(parameterNameMap);
	     }
     return result;
	}
}
