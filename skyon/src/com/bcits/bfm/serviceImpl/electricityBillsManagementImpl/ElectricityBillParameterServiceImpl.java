package com.bcits.bfm.serviceImpl.electricityBillsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.BillParameterMasterEntity;
import com.bcits.bfm.model.ElectricityBillParametersEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ElectricityBillParameterServiceImpl extends GenericServiceImpl<ElectricityBillParametersEntity> implements ElectricityBillParameterService {

	@Override
	@SuppressWarnings("unchecked")
	public List<ElectricityBillParametersEntity> findALL() {
		return entityManager.createNamedQuery("ElectricityBillParametersEntity.findAll").getResultList();
	}

	@Override
	public void setBillParameterStatus(int elBillParameterId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricityBillParametersEntity.setELRateMasterStatus").setParameter("status", "Active").setParameter("elBillParameterId", elBillParameterId).executeUpdate();
				out.write("Parameter active");
			}else
			{
				entityManager.createNamedQuery("ElectricityBillParametersEntity.setELRateMasterStatus").setParameter("status", "Inactive").setParameter("elBillParameterId", elBillParameterId).executeUpdate();
				out.write("Parameter inactive");
			}
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityBillParametersEntity> findAllById(int elBillId) {
	
		return getAllDetailsList(entityManager.createNamedQuery("ElectricityBillParametersEntity.findAllById").setParameter("elBillId", elBillId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> billParameterEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> billsLineItemMap = null;
		 for (Iterator<?> iterator = billParameterEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				billsLineItemMap = new HashMap<String, Object>();				
				billsLineItemMap.put("elBillParameterId", (Integer)values[0]);	
				billsLineItemMap.put("elBillParameterDataType",(String)values[1]);
				billsLineItemMap.put("elBillParameterValue", (String)values[2]);	
				billsLineItemMap.put("notes",(String)values[3]);
				billsLineItemMap.put("bvmName",(String)values[4]);
			
			result.add(billsLineItemMap);
	     }
      return result;
	}

	@Override
	public BigDecimal getSequencyNumber() {
		return (BigDecimal) entityManager.createNativeQuery("SELECT ELBILL_SEQ.nextval FROM dual").getSingleResult();
	}

	@Override
	public Integer getCamParameter(String bvmName) {
		return (Integer)entityManager.createNamedQuery("ElectricityBillParametersEntity.getCamParameter").setParameter("bvmName", bvmName).getSingleResult();
	}

	@Override
	public List<String> getAverageUnits(int elBillId, int bvmId, Date lastYear,Date currentBillDate) {
		return entityManager.createNamedQuery("ElectricityBillParametersEntity.getAverageUnits",String.class).setParameter("lastYear", lastYear).setParameter("presentYear", currentBillDate).setParameter("bvmId", bvmId).setParameter("elBillId", elBillId).getResultList();
	}

	@Override
	public String getAverageAmountValue(int bpmId,int elBillId) {
		try {
			return entityManager.createNamedQuery("ElectricityBillParametersEntity.getAverageAmountValue",String.class).setParameter("elBillId", elBillId).setParameter("bpmId", bpmId).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getParameterValue(int elBillId, String typeOfService,String paraMeterName) {
		try {
			return entityManager.createNamedQuery("ElectricityBillParametersEntity.getParameterValue",String.class).setParameter("elBillId", elBillId).setParameter("typeOfService", typeOfService).setParameter("paraMeterName", paraMeterName).getSingleResult();
		} catch (Exception e)
		{
			return null;
		}
	}

	@Override
	public List<Integer> getBillParametersByBillId(int elBillId) {
		return entityManager.createNamedQuery("ElectricityBillParametersEntity.getBillParametersByBillId",Integer.class).setParameter("elBillId", elBillId).getResultList();
	}

	@Override
	public BillParameterMasterEntity getMasterObjBasedOnName(String bvmName,String serviceType) {
		return (BillParameterMasterEntity)entityManager.createNamedQuery("ElectricityBillParametersEntity.getMasterObjBasedOnName").setParameter("bvmName", bvmName).setParameter("serviceType", serviceType).getSingleResult();
	}

	@Override
	public List<ElectricityBillParametersEntity> findByBillId(int elBillId) {
		return entityManager.createNamedQuery("ElectricityBillParametersEntity.findByBillId",ElectricityBillParametersEntity.class).setParameter("elBillId", elBillId).getResultList();
	}
	
}
