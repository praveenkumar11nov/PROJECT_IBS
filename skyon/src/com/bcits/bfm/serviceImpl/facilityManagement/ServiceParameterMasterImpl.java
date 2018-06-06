package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ServiceParameterMaster;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServiceParameterMasterImpl extends GenericServiceImpl<ServiceParameterMaster> implements ServiceParameterMasterService{
	static Logger logger = LoggerFactory.getLogger(ServiceParameterMasterImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ServiceParameterMaster> findAll() {
		return entityManager.createNamedQuery("ServiceParameterMaster.findAll").getResultList();
	}
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() {
		
		@SuppressWarnings("unchecked")
		List<ServiceParameterMaster> serviceMaster =  entityManager.createNamedQuery("ServiceParameterMaster.findAll").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		for (final ServiceParameterMaster record : serviceMaster) {
			response.add(new HashMap<String, Object>() {
				{

					put("spmId", record.getSpmId());
					put("serviceType", record.getServiceType());
					put("createdBy", record.getCreatedBy());
					put("lastupdatedBy", record.getLastupdatedBy());
					put("lastupdatedDt", record.getLastupdatedDt());
					put("spmdataType", record.getSpmdataType());
					put("spmDescription", record.getSpmDescription());
					put("spmName", record.getSpmName());
					put("spmSequence", record.getSpmSequence());
					put("spmStatus", record.getStatus());

				}
			});

		}
		return response;
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public ServiceParameterMaster getBeanObject(Map<String, Object> map) {
		return null;
	}
	
@Override
	 public void setServiceParameterStatus(int spmId, String operation,HttpServletResponse response) {
	  try
	  {
	   PrintWriter out = response.getWriter();

	   if(operation.equalsIgnoreCase("activate"))
	   {
	    entityManager.createNamedQuery("ServiceParameterMaster.UpdateStatus").setParameter("status", "Active").setParameter("spmId", spmId).executeUpdate();
	    out.write("Service Parameter active");
	   }
	   else
	   {
	    entityManager.createNamedQuery("ServiceParameterMaster.UpdateStatus").setParameter("status", "Inactive").setParameter("spmId", spmId).executeUpdate();
	    out.write("Service Parameter inactive");
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
public List<ServiceParameterMaster> getServiceParameterName(String serviceType) {
	logger.info("Inside getServiceParameterName method!!!!!!"+serviceType);
	return entityManager.createNamedQuery("ServiceParameterMaster.getSpmName").setParameter("serviceType", serviceType).getResultList();
}
@SuppressWarnings("unchecked")
@Override
public List<ServiceParameterMaster> getServiceParameterId(String spmName) {
 return entityManager.createNamedQuery("ServiceParameterMaster.getSpmId").setParameter("spmName", spmName).getResultList();
 
 }
}
