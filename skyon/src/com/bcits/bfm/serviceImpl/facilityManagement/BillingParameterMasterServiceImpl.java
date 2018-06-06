package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class BillingParameterMasterServiceImpl extends GenericServiceImpl<BillParameterMasterEntity> implements BillingParameterMasterService {
	static Logger logger = LoggerFactory.getLogger(BillingParameterMasterServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<BillParameterMasterEntity> findAll() {
		return getAllDetailsList(entityManager.createNamedQuery("BillParameterMasterEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> billEntities) {		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> parameterMap = null;
		 for (Iterator<?> iterator = billEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				parameterMap = new HashMap<String, Object>();
				
				parameterMap.put("bvmId", (Integer)values[0]);
				parameterMap.put("serviceType", (String)values[1]);				
				parameterMap.put("createdBy", (String)values[2]);
				parameterMap.put("lastUpdatedBy", (String)values[3]);				
				parameterMap.put("lastUpdatedDT", (Timestamp)values[4]);
				parameterMap.put("bvmDataType", (String)values[5]);				
				parameterMap.put("bvmDescription", (String)values[6]);
				parameterMap.put("bvmName", (String)values[7]);				
				parameterMap.put("bvmSequence", (Integer)values[8]);
				parameterMap.put("status", (String)values[9]);
			
			result.add(parameterMap);
	     }
       return result;  
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<BillParameterMasterEntity> setResponse() {
		 return entityManager.createNamedQuery("BillParameterMaster.findAll").getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public BillParameterMasterEntity getBeanObject(Map<String, Object> map) {
		return null;
	}

	@Override
	public void setBillingParameterStatus(int bvmId, String operation,
			HttpServletResponse response) {
		try
		  {
		   PrintWriter out = response.getWriter();

		   if(operation.equalsIgnoreCase("activate"))
		   {
		    entityManager.createNamedQuery("BillParameterMaster.UpdateStatus").setParameter("status", "Active").setParameter("bvmId", bvmId).executeUpdate();
		    out.write("Billing Parameter active");
		   }
		   else
		   {
		    entityManager.createNamedQuery("BillParameterMaster.UpdateStatus").setParameter("status", "Inactive").setParameter("bvmId", bvmId).executeUpdate();
		    out.write("Billing Parameter inactive");
		   }
		  } 
		  catch (IOException e)
		  {
		   e.printStackTrace();
		  }
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getNameandValue(int elBillId) {
		return entityManager.createNamedQuery("BillParameterMaster.getNameAndValue").setParameter("elBillId", elBillId).getResultList();
	}
	@Override
	public List<BillParameterMasterEntity> getBillParameterMasterByServiceType(String typeOfService)
	{
		return entityManager.createNamedQuery("BillParameterMasterEntity.getBillParameterMasterByServiceType",BillParameterMasterEntity.class).setParameter("serviceType", typeOfService).getResultList();
	}

	@Override
	public Integer getServicMasterId(String serviceType, String bvmName) 
	{
		return entityManager.createNamedQuery("BillParameterMasterEntity.getServicMasterId",Integer.class).setParameter("serviceType", serviceType).setParameter("bvmName", bvmName).getSingleResult();
	}

	@Override
	public List<String> getParameterName(String serviceType) {
		return entityManager.createNamedQuery("BillParameterMasterEntity.getParameterName",String.class).setParameter("serviceType", serviceType).getResultList();
	}

	@Override
	public BillParameterMasterEntity getBillParameterMasterById(int bvmId) {
		return entityManager.createNamedQuery("BillParameterMasterEntity.getBillParameterMasterById",BillParameterMasterEntity.class).setParameter("bvmId", bvmId).getSingleResult();
	}

	@Override
	public List<?> getUniqueParamName(String className,String attribute,String attribute1,String serviceType) {
		final StringBuffer queryString = new StringBuffer("SELECT bpm."+attribute+" FROM ");
		queryString.append(className+" bpm WHERE bpm."+attribute1+"=");
	    queryString.append("'"+serviceType+"'");
		
	    final Query query = this.entityManager.createQuery(queryString.toString());
	    return (List<?>)query.getResultList();	
		
		
	}

	@Override
	public Object getMeterFixedDate(String className, String attribute,
			String attribute1, String serviceType, String attribute2,
			int accounId) {
		
		final StringBuffer queryString = new StringBuffer("SELECT bpm."+attribute+" FROM ");
		queryString.append(className+" bpm WHERE bpm."+attribute1+"=");
	    queryString.append("'"+serviceType+"' AND bpm."+attribute2+"=");
	    queryString.append(accounId);

	    final Query query = this.entityManager.createQuery(queryString.toString(),java.util.Date.class);
	    return query.getSingleResult();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillParameterMasterEntity> findAllBill() {
		return entityManager.createNamedQuery("BillParameterMasterEntity.findAllBill").getResultList();
	}
}
