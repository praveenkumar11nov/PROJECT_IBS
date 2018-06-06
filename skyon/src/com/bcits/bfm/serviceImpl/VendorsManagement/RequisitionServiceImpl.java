package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.RequisitionDetails;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.service.VendorsManagement.RequisitionService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class RequisitionServiceImpl extends GenericServiceImpl<Requisition> implements RequisitionService 
{
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Requisition> findAll() 
	{
		List<Requisition> requisition = entityManager.createNamedQuery(
				"Requisition.findAll").getResultList();
		return requisition;
	}
		
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Requisition> findAllRequisitionRequiredFields() 
	{
		return getAllRequiredRequisitionFields(entityManager.createNamedQuery("Requisition.getAllRequiredRequisitionFields").getResultList());		 
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	private List getAllRequiredRequisitionFields(List<?> requisition)
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> requisitionData = null;
		 for (Iterator<?> iterator = requisition.iterator(); iterator.hasNext();)
		 {
			 final Object[] values = (Object[]) iterator.next();
			 requisitionData = new HashMap<String, Object>();
			 
			 int reqIdVal = (Integer)values[6];
			 if(reqIdVal!=1)
			 {
				 requisitionData.put("personId",(Integer)values[2]);				
				 String personName = "";
				 personName = personName.concat((String)values[0]);
				 if((String)values[1] != null)
				 {
				 	personName = personName.concat(" ");
				 	personName = personName.concat((String)values[1]);
				 }
				 requisitionData.put("fullName", personName);
				 requisitionData.put("vendorId", (Integer)values[3]);
				 requisitionData.put("dept_Id",(Integer)values[4]);
				 requisitionData.put("dept_Name",(String)values[5]);
				 requisitionData.put("reqId",(Integer)values[6]);
				 requisitionData.put("reqName",(String)values[7]);
				 requisitionData.put("reqDescription",(String)values[8]);
				 requisitionData.put("reqType",(String)values[9]);
				 requisitionData.put("reqDate",(java.sql.Date)values[10]);
				 requisitionData.put("reqByDate",(java.sql.Date)values[11]);
				 requisitionData.put("drGroupId",(Integer)values[12]);
				 requisitionData.put("reqVendorQuoteRequisition",(String)values[13]);
				 requisitionData.put("status",(String)values[14]);
				 requisitionData.put("createdBy",(String)values[15]);
				 requisitionData.put("lastUpdatedBy",(String)values[16]);
				 requisitionData.put("lastUpdatedDt", (Timestamp)values[17]);
				 
				 String fullVendorName = "";
				 fullVendorName = fullVendorName.concat((String)values[18]);
				 if((String)values[19] != null)
				 {
					 fullVendorName = fullVendorName.concat(" ");
					 fullVendorName = fullVendorName.concat((String)values[19]);
				 }
				 requisitionData.put("fullVendorName", fullVendorName);
				 
				 if((Integer)values[20]!=1)
				 {
					 requisitionData.put("storeId", (Integer)values[20]);			 
					 requisitionData.put("storeName", (String)values[21]); 
				 }
				 else if((Integer)values[20]==0)
				 {
					 requisitionData.put("storeId", (Integer)values[20]);			 
					 requisitionData.put("storeName", "");
				 }
				 result.add(requisitionData); 
			 }
		 }
		 return result;		
	}
	
	/**** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		
		List<Requisition> list1=entityManager.createNamedQuery("REQUISTAION.AllDaTa").getResultList();

		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();   
		
	/*	for (final Requisition record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				int storeid = record.getStoreId();
				if(storeid!=1)
				{
					put("storeId",record.getStoreId());
					put("storeName",record.getStoreMaster().getStoreName());
				}
				else if(storeid == 0)
				{
					put("storeId",record.getStoreId());
					put("storeName","");
				}
				
				put("reqId", record.getReqId());
				put("reqName", record.getReqName());
				put("reqDescription", record.getReqDescription());
            	put("personId", record.getPerson().getPersonId());
            	put("fullName", record.getPerson().getFirstName()+" "+record.getPerson().getLastName());
            	put("lastName", record.getPerson().getLastName());
            	put("dept_Id", record.getDepartment().getDept_Id());
            	put("dept_Name", record.getDepartment().getDept_Name());
            	
            	java.util.Date dt1 =  record.getReqDate();
				java.sql.Date reqDt1 = new java.sql.Date(dt1.getTime());
				
				java.util.Date dt2 =  record.getReqByDate();
				java.sql.Date reqDt2 = new java.sql.Date(dt2.getTime());
            	
            	put("reqDate", reqDt1);
            	put("reqType", record.getReqType());            	
            	put("reqByDate", reqDt2);
            	put("reqVendorQuoteRequisition", record.getReqVendorQuoteRequisition());
            	put("vendorId", record.getVendors().getVendorId());
            	put("fullVendorName", record.getVendors().getPerson().getFirstName()+" "+record.getPerson().getLastName());
            	put("status", record.getStatus());
            	put("createdBy", record.getCreatedBy());
            	put("lastUpdatedBy", record.getLastUpdatedBy());
            	put("lastUpdatedDt", record.getLastUpdatedDt());
			}});
		}*/
		
		for(Iterator<?> iterator=list1.iterator();iterator.hasNext();)
		{ 
			final Object[] values=(Object[])iterator.next();
			response.add(new HashMap<String, Object>() 
					{{
						int storeid = (Integer)values[0];
						if(storeid!=1)
						{
							put("storeId",(Integer)values[0]);
							put("storeName",(String)values[1]);
						}
						else if(storeid == 0)
						{
							put("storeId",(Integer)values[0]);
							put("storeName","");
						}
						
						put("reqId", (Integer)values[2]);
						put("reqName",(String)values[3]);
						put("reqDescription", (String)values[4]);
		            	put("personId", (Integer)values[5]);
		            	put("fullName", (String)values[6]+" "+(String)values[7]);
		            	put("lastName", (String)values[7]);
		            	put("dept_Id", (Integer)values[8]);
		            	put("dept_Name", (String)values[9]);
		            	
		            	java.util.Date dt1 =  (java.util.Date)values[10];
						java.sql.Date reqDt1 = new java.sql.Date(dt1.getTime());
						
						java.util.Date dt2 =  (java.util.Date)values[11];
						java.sql.Date reqDt2 = new java.sql.Date(dt2.getTime());
		            	
		            	put("reqDate", reqDt1);
		            	put("reqType", (String)values[12]);            	
		            	put("reqByDate", reqDt2);
		            	put("reqVendorQuoteRequisition", (String)values[13]);
		            	put("vendorId",(Integer)values[14]);
		            	if((String)values[16]==null){
			            put("fullVendorName", (String)values[15]);
		            	}
		            	else{
		            	put("fullVendorName", (String)values[15]+" "+(String)values[16]);
		            	}
		            	put("status", (String)values[17]);
		            	put("createdBy", (String)values[18]);
		            	put("lastUpdatedBy", (String)values[19]);
		            	put("lastUpdatedDt", (Timestamp)values[20]);

			
					}});
				
			
		}
		return response;
	}
	/***  END FOR READING THE CONTENTS TO JSP  ****/

	@Override
	public List<?> findVendors() 
	{
		return entityManager.createNamedQuery("ReadVendors.findAll").getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setRequisitionStatus(int reqId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			PrintWriter out = response.getWriter();			
			if(operation.equalsIgnoreCase("approved"))
			{
				entityManager.createNamedQuery("Requisition.UpdateStatus").setParameter("status", "Approved").setParameter("reqId", reqId).executeUpdate();
				out.write("Requisition Approved");
			}
			else
			{
				entityManager.createNamedQuery("Requisition.UpdateStatus").setParameter("status", "Rejected").setParameter("reqId", reqId).executeUpdate();
				out.write("Requisition Rejected");				
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public List<?> getDepartments()
	{
		Long count = (Long)entityManager.createNamedQuery("Req.getCountRecords").getSingleResult();
		
		if(count>1){
			return entityManager.createNamedQuery("Req.getDepartmentDeatils").getResultList();
		}else{
			return entityManager.createNamedQuery("Req.getDepartmentDeatilsEqualToOne").getResultList();
		}
	}
	
	@Override
	public List<?> getRequisitionName() {
		return entityManager.createNamedQuery("Requisition.getRequisitionName").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Requisition> getRequisitionDetails() 
	{		
		return entityManager.createNamedQuery("Requisition.readReqDetailsIfReqIdExists").getResultList();
	}
	
	@Override
	public List<?> getReqTypeBasedOnReqId(int reqid)
	{
		return entityManager.createNamedQuery("RequisitionDetails.getReqTypeBasedOnReqId").setParameter("reqid", reqid).getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int getReqIdFromChild(int idVal) 
	{
		int id = 0;
		List list = entityManager.createNamedQuery("Requisition.findParentIdExistenceinChild").setParameter("idVal", idVal).getResultList();
		Iterator<Integer> it = list.iterator();
		while(it.hasNext())
		{
			id = it.next();
		}
		return id;
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> getmanpowerrequisition() {
		
		List<Map<String, Object>> setResponse = new ArrayList<Map<String, Object>>();  
		for (final RequisitionDetails requisitionDetails : (List<RequisitionDetails>)entityManager.createNamedQuery("Requisition.getManpowerCount").getResultList()) {
			setResponse.add(new HashMap<String, Object>() {{
				put("reqId", requisitionDetails.getRequisition().getReqId());
				put("rdId", requisitionDetails.getRdId());
				put("reqName", requisitionDetails.getRequisition().getReqName());
				put("reqDescription", requisitionDetails.getRequisition().getReqDescription());
				put("reqDate", requisitionDetails.getRequisition().getReqDate());
				put("reqByDate", requisitionDetails.getRequisition().getReqByDate());
				put("status", requisitionDetails.getRequisition().getStatus());
				put("requirement", requisitionDetails.getRdQuantity());
				put("fulfilled", requisitionDetails.getReqFulfilled());
				put("designation", requisitionDetails.getDesignation().getDn_Name());
				put("department", requisitionDetails.getRequisition().getDepartment().getDept_Name());
				
			}});
		}
		return setResponse;
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> getManpowerRequisition() {
		

		List<Map<String, Object>> setResponse = new ArrayList<Map<String, Object>>();  
		for (final Requisition requisition : (List<Requisition>)entityManager.createNamedQuery("Requisition.getManpowerReq").getResultList()) {
			setResponse.add(new HashMap<String, Object>() {{
				put("reqId", requisition.getReqId());
				put("reqName", requisition.getReqName());
				put("reqDescription", requisition.getReqDescription());
				put("reqDate", new SimpleDateFormat("dd,MMM,YY").format(requisition.getReqDate()));
				put("reqByDate",new SimpleDateFormat("dd,MMM,YY").format(requisition.getReqByDate()));
				put("status", requisition.getStatus());
				put("department", requisition.getDepartment().getDept_Name());
				put("vendorName", "NA");
				put("contractName", "");	
				
			}});
		}
		return setResponse;
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> getManpowerRequisitionVC() {
		List<Map<String, Object>> setResponse = new ArrayList<Map<String, Object>>();  
		for (final VendorContracts vendorContracts : (List<VendorContracts>)entityManager.createNamedQuery("Requisition.getManpowerReqVC").getResultList()) {
			setResponse.add(new HashMap<String, Object>() {{
				put("reqId", vendorContracts.getRequisition().getReqId());
				put("reqName", vendorContracts.getRequisition().getReqName());
				put("reqDescription", vendorContracts.getRequisition().getReqDescription());
				put("reqDate", new SimpleDateFormat("dd,MMM,YY").format(vendorContracts.getRequisition().getReqDate()));
				put("reqByDate",new SimpleDateFormat("dd,MMM,YY").format(vendorContracts.getRequisition().getReqByDate()));
				put("status", vendorContracts.getRequisition().getStatus());
				put("department", vendorContracts.getRequisition().getDepartment().getDept_Name());
				
				String vendorName = "";
				if(vendorContracts.getVendors().getPerson().getFirstName()!=null)
					vendorName+=vendorContracts.getVendors().getPerson().getFirstName();
				if(vendorContracts.getVendors().getPerson().getLastName()!=null)
					vendorName+=vendorContracts.getVendors().getPerson().getLastName();
				put("vendorName", vendorName);
				put("contractName", vendorContracts.getContractName());	
			}});
		}
		return setResponse;
	}
	

	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public List<?> getManpowerRequisitionDetails(int reqId) {
		List<Map<String, Object>> setResponse = new ArrayList<Map<String, Object>>();  
		for (final RequisitionDetails requisitionDetails : (List<RequisitionDetails>)entityManager.createNamedQuery("Requisition.getReqDetails").setParameter("reqId", reqId).getResultList()) {
			setResponse.add(new HashMap<String, Object>() {{
				put("rdId", requisitionDetails.getRdId());
				put("rdDescription", requisitionDetails.getRdDescription());
				put("reqId", requisitionDetails.getRequisition().getReqId());
				put("designation", requisitionDetails.getDesignation().getDn_Name());
				put("required", requisitionDetails.getRdQuantity());
				put("filled", requisitionDetails.getReqFulfilled());
			}});
		}
		return setResponse;
	}


	
	
	
}
