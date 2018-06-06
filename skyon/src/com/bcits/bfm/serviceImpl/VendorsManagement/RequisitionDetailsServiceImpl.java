package com.bcits.bfm.serviceImpl.VendorsManagement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.service.VendorsManagement.RequisitionDetailsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class RequisitionDetailsServiceImpl extends GenericServiceImpl<RequisitionDetails> implements RequisitionDetailsService
{
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RequisitionDetails> findAll()
	{
		List<RequisitionDetails> reqDetails = entityManager.createNamedQuery("ReqDetails.findAll").getResultList();
		return reqDetails;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findRequisitionDetailsBasedOnId(int id)
	{
		return entityManager.createNamedQuery("RequisitionDetails.findRequisitionDetails").setParameter("id",id).getResultList();				
	}

	@SuppressWarnings("unchecked")
	public List<Requisition> findId() 
	{
		List<Requisition> reqDetails = entityManager.createNamedQuery("RequisitionDetails.findRequisitionId").getResultList();
		return reqDetails;
	}
	@SuppressWarnings("unused")
	@Override
	public RequisitionDetails getBeanObject(Map<String, Object> map,String type, RequisitionDetails requisitionDetails) 
	{
		String username = (String) SessionData.getUserDetails().get("userID");	
		int rdQuantity = (int) map.get("rdQuantity");
		Long reqDetailsQuantity = Long.valueOf(rdQuantity);
		int dnid = (int)map.get("dn_Id");
		if(type.equals("save"))
		{	
			System.out.println("************************\n\nDesignation Id Is --->"+dnid);			
			int uid = (int)map.get("uomId");
			if(map.get("imId") instanceof Integer)
			{
				System.out.println("Saving REQ_DETAILS With Integer-Instance");
				int imId = (int)map.get("imId");
				if(imId == 0 && uid == 0)
				{
					requisitionDetails.setImId(1);
					requisitionDetails.setUomId(1);
				}
				else
				{
					requisitionDetails.setImId(imId);
					requisitionDetails.setUomId(uid);
				}
			}
			else if(map.get("imId") instanceof String)
			{
				System.out.println("Saving REQ_DETAILS With STRING-Instance");
				requisitionDetails.setImId(1);
				requisitionDetails.setUomId(1);				
			}
			if(dnid!=0)
			{
				requisitionDetails.setDn_Id(dnid);				
			}
			else if(dnid == 0)
			{
				requisitionDetails.setDn_Id(1);
			}
			requisitionDetails.setUomBudget((int)map.get("uomBudget"));
			requisitionDetails.setRdSlno((int)map.get("rdSlno"));
			requisitionDetails.setRdDescription((String)map.get("rdDescription"));
			requisitionDetails.setRdQuantity((int)map.get("rdQuantity"));			
			requisitionDetails.setCreatedBy(username);
			requisitionDetails.setLastUpdatedBy(username);
		}
		if(type.equals("update"))
		{
			int uid = (int)map.get("uomId");
			requisitionDetails.setDn_Id(dnid);
			requisitionDetails.setUomId(uid);
			requisitionDetails.setRdId((int)map.get("rdId"));
			requisitionDetails.setRdSlno((int)map.get("rdSlno"));
			requisitionDetails.setImId((int)map.get("imId"));
			requisitionDetails.setRdDescription((String)map.get("rdDescription"));
			requisitionDetails.setRdQuantity((int)map.get("rdQuantity"));	
			requisitionDetails.setUomBudget((int)map.get("uomBudget"));
			requisitionDetails.setCreatedBy((String)map.get("createdBy"));
			requisitionDetails.setLastUpdatedBy(username);
		}
		return requisitionDetails;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RequisitionDetails> findReqDetailsBasedOnReqId(int reqid) 
	{
		return entityManager.createNamedQuery("ReqDetails.findReqDetailsBasedOnReqId").setParameter("reqid",reqid).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getReqTypeBasedOnReqId(int reqid)
	{
		String requisitionType = "";
		List listVal = entityManager.createNamedQuery("RequisitionDetails.getReqTypeBasedOnReqId").setParameter("reqid", reqid).getResultList();
		Iterator<String> iterator = listVal.iterator();		
		while(iterator.hasNext())
		{
			requisitionType = iterator.next();
		}
		return requisitionType;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RequisitionDetails> findAllRequiredRequisitionDetailsFields(int reqId) 
	{
		return getAllRequiredRequisitionDetailsFields(entityManager.createNamedQuery("RequisitionDetails.findAllRequiredRequisitionDetailsFields").setParameter("reqId", reqId).getResultList());		 
	}
	
	@SuppressWarnings({ "rawtypes" })
	private List getAllRequiredRequisitionDetailsFields(List<?> reqDetails)
	{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> reqDetailsData = null;
		for (Iterator iterator = reqDetails.iterator(); iterator.hasNext();) 
		{	
			final Object[] values = (Object[]) iterator.next();
			reqDetailsData = new HashMap<String, Object>();		
			
			String imName = (String)values[9];
			String reqType = (String)values[12];
			
			if(reqType.equalsIgnoreCase("Item Supply"))
        	{
        		reqDetailsData.put("imName", (String)values[9]);
        		reqDetailsData.put("uom",(String)values[11]);
        		reqDetailsData.put("dn_Name", "");
        	}
			if(reqType.equalsIgnoreCase("Manpower"))
        	{
        		reqDetailsData.put("imName", "");
        		reqDetailsData.put("uom",(String)values[11]);
        		reqDetailsData.put("dn_Name", (String)values[14]);
        	}
        	if(reqType.equalsIgnoreCase("General Contract"))
        	{
        		reqDetailsData.put("imName", "");
        		reqDetailsData.put("uom","");
        		reqDetailsData.put("dn_Name", "");
        	}			
			reqDetailsData.put("rdId",(Integer)values[0]);
			reqDetailsData.put("requisitionId", (Integer)values[1]);
			reqDetailsData.put("drGroupId", (Integer)values[2]);
			reqDetailsData.put("rdSlno", (Integer)values[3]);
			reqDetailsData.put("rdDescription", (String)values[4]);
			reqDetailsData.put("rdQuantity", (Integer)values[5]);
			reqDetailsData.put("createdBy", (String)values[6]);
			reqDetailsData.put("lastUpdatedBy", (String)values[7]);
			reqDetailsData.put("imId", (Integer)values[8]);
			reqDetailsData.put("uomId", (Integer)values[10]);			
			reqDetailsData.put("dn_Id", (Integer)values[13]);
			reqDetailsData.put("uomBudget", (Integer)values[15]);
			reqDetailsData.put("reqFulfilled", (Integer)values[16]);
			result.add(reqDetailsData);
		}
		return result;
	}

	@Override
	public List<?> getDesignation(int deptId) 
	{	
		return entityManager.createNamedQuery("Requisition.readDesignationForDepartment").setParameter("deptId", deptId).getResultList();
	}
}
