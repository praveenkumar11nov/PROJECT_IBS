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

import com.bcits.bfm.model.MeterParameterMaster;
import com.bcits.bfm.service.facilityManagement.MeterParameterMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class MeterParameterMasterImpl extends GenericServiceImpl<MeterParameterMaster> implements MeterParameterMasterService{
	static Logger logger = LoggerFactory.getLogger(MeterParameterMasterImpl.class);
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MeterParameterMaster> findAll() {
		// TODO Auto-generated method stub
				logger.debug("Going to retrieve data from table");
				return entityManager.createNamedQuery("MeterParameterMaster.findAll").getResultList();
		
	}

	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<MeterParameterMaster> serviceMaster =  entityManager.createNamedQuery("MeterParameterMaster.findAll").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		for (final MeterParameterMaster record : serviceMaster) {
			response.add(new HashMap<String, Object>() {
				{

					put("mpmId", record.getMpmId());
					put("mpmserviceType", record.getMpmserviceType());
					put("createdBy", record.getCreatedBy());
					put("lastupdatedBy", record.getLastupdatedBy());
					put("lastupdatedDt", record.getLastupdatedDt());
					put("mpmDataType", record.getMpmDataType());
					put("mpmDescription", record.getMpmDescription());
					put("mpmName", record.getMpmName());
					put("mpmSequence", record.getMpmSequence());
					put("status", record.getStatus());

				}
			});

		}
		return response;
	}

	@Override
	public MeterParameterMaster getBeanObject(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMeterParameterStatus(int mpmId, String operation,
			HttpServletResponse response) {
		 try
		  {
		   PrintWriter out = response.getWriter();

		   if(operation.equalsIgnoreCase("activate"))
		   {
		    entityManager.createNamedQuery("MeterParameterMaster.UpdateStatus").setParameter("status", "Active").setParameter("mpmId", mpmId).executeUpdate();
		    out.write("Meter Parameter active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("MeterParameterMaster.UpdateStatus").setParameter("status", "Inactive").setParameter("mpmId", mpmId).executeUpdate();
		    out.write("Meter Parameter inactive");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
		  
		
	}

	@Override
	public List<Object[]> getNameandValue(int elMeterId) {
			return entityManager.createNamedQuery("MeterParameterMaster.getNameAndValue").setParameter("elMeterId", elMeterId).getResultList();
	}

	@Override
	public String getMeterParameter(int accountId, int elMeterId,String typeOfServiceForMeters, String string) {
		try {
			return (String) entityManager.createNamedQuery("ElectricityMeterParametersEntity.getMeterParameter").setParameter("elMeterId", elMeterId).setParameter("typeOfServiceForMeters", typeOfServiceForMeters).setParameter("string", string).getSingleResult();
		} catch (Exception e) {
		return "";
		}
		
	}

	
}
