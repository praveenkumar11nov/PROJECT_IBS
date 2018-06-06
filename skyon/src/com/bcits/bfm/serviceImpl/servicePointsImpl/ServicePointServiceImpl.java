package com.bcits.bfm.serviceImpl.servicePointsImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ServicePointEntity;
import com.bcits.bfm.service.servicePoints.ServicePointService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServicePointServiceImpl extends GenericServiceImpl<ServicePointEntity> implements ServicePointService  {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServicePointEntity> findALL(int srId) {
		List<ServicePointEntity> list =  entityManager.createNamedQuery("ServicePointEntity.findAll").setParameter("srId", srId).getResultList();
		return getAllDetailsList(list);
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<ServicePointEntity> servicePointList) 
	{
		
		//List<Map<String, Object>> allPropertyDetailsList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> servicePointMap =  new ArrayList<Map<String, Object>>();    

		for (final ServicePointEntity servicePoint : servicePointList) 
		{
			servicePointMap.add(new HashMap<String, Object>() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{ 
			put("srId",servicePoint.getSrId());	
			put("propertyId", servicePoint.getPropertyId());	
			put("property_No",servicePoint.getPropertyObj().getProperty_No());
			put("blockId",servicePoint.getPropertyObj().getBlockId());
			put("blockName",servicePoint.getPropertyObj().getBlocks().getBlockName());
			put("servicePointId",servicePoint.getServicePointId());
			put("typeOfService", servicePoint.getTypeOfService());
			put("serviceMetered", servicePoint.getServiceMetered());
			put("commissionDate", servicePoint.getCommissionDate());
			put("deCommissionDate", servicePoint.getDeCommissionDate());
			put("serviceLocation", servicePoint.getServiceLocation());
			put("servicePointName", servicePoint.getServicePointName());
			put("status", servicePoint.getStatus());
			put("createdBy", servicePoint.getCreatedBy());
			put("lastUpdatedBy", servicePoint.getLastUpdatedBy());
			}});
			
		}
		return servicePointMap;
	}
	
    @Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPropertyNumbers()
	{
		return entityManager.createNamedQuery("ServicePointEntity.getAllPropertyNumbers").getResultList();
	}
    
    @Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllBlockNames()
	{
		return entityManager.createNamedQuery("ServicePointEntity.getAllBlockNames").getResultList();
	}
	
	
	@Override
	public void setServicePointStatus(int servicePointId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ServicePointEntity.setServicePointStatus").setParameter("status", "Active").setParameter("servicePointId", servicePointId).executeUpdate();
				out.write("Service point is activated");
			}
			else
			{
				entityManager.createNamedQuery("ServicePointEntity.setServicePointStatus").setParameter("status", "Inactive").setParameter("servicePointId", servicePointId).executeUpdate();
				out.write("Service point is de-activate");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ServicePointEntity> findAllServicePoints()
	{
		return entityManager.createNamedQuery("ServicePointEntity.findAllServiceTypes", ServicePointEntity.class).getResultList();
	}
}
