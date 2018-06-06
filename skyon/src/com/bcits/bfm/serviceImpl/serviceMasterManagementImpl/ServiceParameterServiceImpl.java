package com.bcits.bfm.serviceImpl.serviceMasterManagementImpl;

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

import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServiceParameterServiceImpl extends GenericServiceImpl<ServiceParametersEntity> implements ServiceParameterService  {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServiceParametersEntity> findALL() {
		return entityManager.createNamedQuery("ServiceParametersEntity.findAll").getResultList();
		//return getAllDetailsList(entityManager.createNamedQuery("ServiceParametersEntity.findAll").getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServiceParametersEntity> findAllById(int serviceMasterId) {
		return getAllDetailsList(entityManager.createNamedQuery("ServiceParametersEntity.findAllById").setParameter("serviceMasterId", serviceMasterId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> serviceParameterEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> meterMap = null;
		 for (Iterator<?> iterator = serviceParameterEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				meterMap = new HashMap<String, Object>();
				
				meterMap.put("serviceParameterId", (Integer)values[0]);	
				meterMap.put("spmId",(Integer)values[1]);
				meterMap.put("serviceParameterDataType", (String)values[2]);	
				meterMap.put("serviceParameterValue",(String)values[3]);
				meterMap.put("serviceParameterSequence",(Integer)values[4]);
				meterMap.put("status",(String)values[5]);
				meterMap.put("spmName",(String)values[6]);
				meterMap.put("serviceMasterId",(Integer)values[7]);
			
			result.add(meterMap);
	     }
      return result;
	}
	
	/*private List<ServiceParametersEntity> selectedList(List<ServiceParametersEntity> list) 
	{
		List<ServiceParametersEntity> listNew = new ArrayList<ServiceParametersEntity>();
		for (Iterator<ServiceParametersEntity> iterator = list.iterator(); iterator.hasNext();) {
			ServiceParametersEntity parametersEntity = (ServiceParametersEntity) iterator.next();
			parametersEntity.setServiceMastersEntity(null);
			parametersEntity.setServiceParameterMaster(null);
			listNew.add(parametersEntity);
		}
		return listNew;
	}*/
	
	@Override
	public void updateParameterStatusFromInnerGrid(int serviceParameterId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("status");

			ServiceParametersEntity serviceParametersEntity = find(serviceParameterId);
			
			if(serviceParametersEntity.getStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("ServiceParametersEntity.updateParameterStatusFromInnerGrid").setParameter("status", "Inactive").setParameter("serviceParameterId", serviceParameterId).executeUpdate();
				out.write("Service parameter is activated");
			}
			else
			{
				entityManager.createNamedQuery("ServiceParametersEntity.updateParameterStatusFromInnerGrid").setParameter("status", "Active").setParameter("serviceParameterId", serviceParameterId).executeUpdate();
				out.write("Service parameter is de-activated");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ServiceParameterMaster getServiceDataType(int spmId) {
		return entityManager.createNamedQuery("ServiceParametersEntity.getServiceDataType",ServiceParameterMaster.class).setParameter("spmId", spmId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getNameandValue(int serviceMasterId) {
		
		return entityManager.createNamedQuery("ServiceParametersEntity.getNameAndValue").setParameter("serviceMasterId", serviceMasterId).getResultList();
		
	}

	@Override
	public List<ServiceParametersEntity> findAllByParentId(int serviceMasterId) {
		return entityManager.createNamedQuery("ServiceParametersEntity.findAllByParentId",ServiceParametersEntity.class).setParameter("serviceMasterId", serviceMasterId).getResultList();
	}

	@Override
	public int getSequenceForAverageUnits(int serviceID) {
		return (Integer)entityManager.createNamedQuery("ServiceParametersEntity.getSequenceForAverageUnits").setParameter("serviceMasterId", serviceID).getSingleResult();
	}

	@Override
	public int getServiceParameterMasterId(String spmName) {
		return (Integer)entityManager.createNamedQuery("ServiceParametersEntity.getServiceParameterMasterId").setParameter("spmName", spmName).getSingleResult();
	}

	@Override
	public List<ServiceParametersEntity> findAllByParentIdForDG(int serviceMasterId) {
		return entityManager.createNamedQuery("ServiceParametersEntity.findAllByParentIdForDG",ServiceParametersEntity.class).setParameter("serviceMasterId", serviceMasterId).getResultList();
	}
}
