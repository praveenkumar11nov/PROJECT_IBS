package com.bcits.bfm.serviceImpl.servicePointsImpl;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ServicePointInstructionsEntity;
import com.bcits.bfm.service.servicePoints.ServicePointInstructionService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServicePointInstructionServiceImpl extends GenericServiceImpl<ServicePointInstructionsEntity> implements ServicePointInstructionService  {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServicePointInstructionsEntity> findALL() {
		return entityManager.createNamedQuery("ServicePointInstructionsEntity.findAll").getResultList();
	}

	@Override
	public void setServicePointStatus(int servicePointId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ServicePointInstructionsEntity.setServicePointStatus").setParameter("status", "Active").setParameter("servicePointId", servicePointId).executeUpdate();
				out.write("Service Point active");
			}
			else
			{
				entityManager.createNamedQuery("ServicePointInstructionsEntity.setServicePointStatus").setParameter("status", "Inactive").setParameter("servicePointId", servicePointId).executeUpdate();
				out.write("Service Point inactive");
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
	public List<ServicePointInstructionsEntity> findAllById(int srId) {
		return setResponse(entityManager.createNamedQuery("ServicePointInstructionsEntity.findAllById").setParameter("srId", srId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List setResponse(List<?> instructionEnities) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> instructionMap = null;
		 for (Iterator<?> iterator = instructionEnities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				instructionMap = new HashMap<String, Object>();
				
				instructionMap.put("spInstructionId", (Integer)values[0]);
				instructionMap.put("instructionDate", (Date)values[1]);				
				instructionMap.put("alert", (String)values[2]);
				instructionMap.put("instructions", (String)values[3]);
				instructionMap.put("status", (String)values[4]);
				instructionMap.put("createdBy", (String)values[5]);
				instructionMap.put("srId",(Integer)values[6]);
			
			result.add(instructionMap);
	     }
      return result;
	}
	
	/*private List<ServicePointInstructionsEntity> selectedList(List<ServicePointInstructionsEntity> list) 
	{
		List<ServicePointInstructionsEntity> listNew = new ArrayList<ServicePointInstructionsEntity>();
		for (Iterator<ServicePointInstructionsEntity> iterator = list.iterator(); iterator.hasNext();) {
			ServicePointInstructionsEntity spInstructions = (ServicePointInstructionsEntity) iterator.next();
			spInstructions.setServiceRoute(null);
			listNew.add(spInstructions);
		}
		return listNew;
	}*/
	
	@Override
	public void updateInstructionStatusFromInnerGrid(int spInstructionId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			ServicePointInstructionsEntity pointInstructionsEntity = find(spInstructionId);
			
			if(pointInstructionsEntity.getStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("ServicePointInstructionsEntity.updateInstructionStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("spInstructionId", spInstructionId).executeUpdate();
				out.write("Instruction is de-activated");
			}
			else
			{
				entityManager.createNamedQuery("ServicePointInstructionsEntity.updateInstructionStatusFromInnerGrid").setParameter("status", "Active").setParameter("spInstructionId", spInstructionId).executeUpdate();
				out.write("Instruction is activated");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
