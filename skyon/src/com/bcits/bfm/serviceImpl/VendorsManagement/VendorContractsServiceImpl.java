package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.service.VendorsManagement.VendorContractsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class VendorContractsServiceImpl extends GenericServiceImpl<VendorContracts> implements VendorContractsService
{
	static Logger logger = LoggerFactory.getLogger(VendorContractsServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorContracts> findAll() 
	{
		return entityManager.createNamedQuery("VendorContracts.findAll").getResultList();
	}

	/****** FOR READING THE CONTENTS TO JSP  ********/
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<VendorContracts> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final VendorContracts record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				put("vcId", record.getVcId());
            	put("vendorId", record.getVendors().getVendorId());
            	put("reqId", record.getRequisition().getReqId());
            	put("reqName", record.getRequisition().getReqName());
            	put("storeMasterDetails", record.getStoreMaster().getStoreName()+" "+record.getStoreMaster().getStoreLocation());
            	put("storeId", record.getStoreMaster().getStoreId());
            	put("camCategoryId", record.getCamCategoryId());
            	put("contractName", record.getContractName());
            	put("description", record.getDescription());
            	put("contractNo", record.getContractNo());
            	put("contractStartDate", record.getContractStartDate());
            	put("contractEndDate", record.getContractEndDate());            	
            	put("drGroupId", record.getDrGroupId()); 
            	put("invoicePayableDays", record.getInvoicePayableDays());
            	put("vendorSla", record.getVendorSla());
            	put("status", record.getStatus());
            	put("createdBy", record.getCreatedBy());
            	put("lastUpdatedBy", record.getLastUpdatedBy());
            	put("lastUpdatedDt", record.getLastUpdatedDt());
            	
            	String personName = "";
				personName = personName.concat(record.getVendors().getPerson().getFirstName());
				if((record.getVendors().getPerson().getLastName()) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat(record.getVendors().getPerson().getLastName());
				}
				
            	put("vendorName", personName);
			}});
		}
		logger.info(" Contents sent to VendorContracts Jsp From VendorContractsServiceImpl");
		return response;
	}
	
	
	/***********  FOR STATUS UPDATION  ************/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setVendorContractStatus(int vcId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			@SuppressWarnings("unused")
			PrintWriter out = response.getWriter();			
			if(operation.equalsIgnoreCase("approved"))
			{
				entityManager.createNamedQuery("VendorContracts.UpdateStatus").setParameter("status", "Approved").setParameter("vcId", vcId).executeUpdate();
				//out.write("VendorContracts Approved");
			}
			else
			{
				entityManager.createNamedQuery("VendorContracts.UpdateStatus").setParameter("status", "Rejected").setParameter("vcId", vcId).executeUpdate();
				//out.write("VendorContracts Rejected");				
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<?> getVendorNamesAutoUrl() {
		return entityManager.createNamedQuery("VendorContracts.getVendorNamesAutoUrl").getResultList();
	}
	
	@Override
	public List<?> checkForApprovedRequisitions() 
	{
		return entityManager.createNamedQuery("VendorContracts.checkForApprovedRequisitions").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorContracts> getAllRequiredVendorContractsRecord() 
	{
		return readAllVendorContractsRecord(entityManager.createNamedQuery("VendorContracts.getAllRequiredVendorContractsRecord").getResultList());		
	}
	
	@SuppressWarnings({ "rawtypes" })
	private List readAllVendorContractsRecord(List<?> vendorContracts)
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> vcData = null;
		 for (Iterator<?> iterator = vendorContracts.iterator(); iterator.hasNext();)
		 {
			 final Object[] values = (Object[]) iterator.next();
			 vcData = new HashMap<String, Object>();	
			 
			 if((Integer)values[14]!=1)
			 {
				 vcData.put("storeId", (Integer)values[14]);			 
				 vcData.put("storeMasterDetails", (String)values[15]); 
			 }
			 else if((Integer)values[14]==0)
			 {
				 vcData.put("storeId", (Integer)values[14]);			 
				 vcData.put("storeMasterDetails", "");
			 }
			 
			 vcData.put("vcId", (Integer)values[0]);
			 vcData.put("camCategoryId", (Integer)values[1]);
			 vcData.put("contractName", (String)values[2]);
			 vcData.put("description", (String)values[3]);
			 vcData.put("contractNo", (String)values[4]);
			 vcData.put("contractStartDate", (java.util.Date)values[5]);
			 vcData.put("contractEndDate", (java.util.Date)values[6]);
			 vcData.put("drGroupId", (Integer)values[7]);
			 vcData.put("invoicePayableDays", (Integer)values[8]);
			 vcData.put("vendorSla", (Integer)values[9]);
			 vcData.put("status", (String)values[10]);
			 vcData.put("createdBy", (String)values[11]);
			 vcData.put("lastUpdatedBy", (String)values[12]);
			 vcData.put("vendorId", (Integer)values[13]);
			 	
			 vcData.put("reqId", (Integer)values[16]);
			 vcData.put("reqName", (String)values[17]);
			 String personName = "";
			 personName = personName.concat((String)values[18]);
			 if((String)values[19] != null)
			 {
			 	personName = personName.concat(" ");
			 	personName = personName.concat((String)values[19]);
			 }			  
			 vcData.put("renewalRequired", (String)values[20]);
			 vcData.put("vendorName",personName);
			 result.add(vcData);
		 }
		 return result;		
	}
	/******  END FOR READING THE CONTENTS TO JSP ******/

	@Override
	public List<Person> readPersonIdBasedOnVendorId(int vendorId)
	{	
		return  entityManager.createNamedQuery("Person.getAllPersonOnvendorId",Person.class).setParameter("vendorId", vendorId).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VendorContracts> readDataForCronScheduler() 
	{	
		return entityManager.createNamedQuery("VendorContracts.getDataForCronScheduler").getResultList();
	}

	@Override
	public Long readBudgetUom(int reqIdInChild)
	{	
		/*int uomBudget = 0;
		List list = entityManager.createNamedQuery("VendorContracts.getBudgetUOMForSelectedRequisition").setParameter("reqIdInChild", reqIdInChild).getResultList();
		Iterator<Integer> it = list.iterator();
		while(it.hasNext())
		{
			uomBudget = it.next().intValue();
		}
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n\nFROM IMPL"+uomBudget);*/
		return (Long)entityManager.createNamedQuery("VendorContracts.getBudgetUOMForSelectedRequisition").setParameter("reqIdInChild", reqIdInChild).getSingleResult();
	}
}
