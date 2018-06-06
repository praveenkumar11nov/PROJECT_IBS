package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.VendorPriceList;
import com.bcits.bfm.service.VendorsManagement.VendorPriceListService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class VendorPriceListServiceImpl extends GenericServiceImpl<VendorPriceList> implements VendorPriceListService
{
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorPriceList> findAll() 
	{
		List<VendorPriceList> vendorPricelist = entityManager.createNamedQuery("VendorPriceList.findAll").getResultList();
		return vendorPricelist;
	}	
	
	@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		
		List<VendorPriceList> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final VendorPriceList record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				put("vpId", record.getVpId());
            	put("firstName", record.getVendors().getPerson().getFirstName());
            	put("vendorId",record.getVendors().getVendorId());
            	put("imId",record.getItemMaster().getImId());
            	put("imName", record.getItemMaster().getImName());
            	put("uomId", record.getUnitOfMeasurement().getUomId());
            	put("uom",record.getUnitOfMeasurement().getUom());
            	put("rate", record.getRate());
            	put("deliveryAt", record.getDeliveryAt());
            	put("itemType", record.getItemMaster().getImType());
            	put("paymentTerms", record.getPaymentTerms());            	
            	put("Invoice_Payable_days", record.getInvoice_Payable_days());
            	put("validFrom", record.getValidFrom());
            	put("validTo", record.getValidTo());
            	put("status", record.getStatus());
			}});
		}
		return response;
	}
	
	@SuppressWarnings("unused")
	@Override
	public VendorPriceList getBeanObject(Map<String, Object> map,String type, VendorPriceList vendorPriceList) 
	{		
		String userName = (String) SessionData.getUserDetails().get("userID");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(sdf, true);
		
		String validFrom = (String)map.get("validFrom");
		String validTo = (String)map.get("validTo");	
		
		String[] fromDate = validFrom.split("T");
		String[] toDate = validTo.split("T");
		
		java.util.Date validFromDates = null;
		java.util.Date validToDates = null;
		try
		{
			validFromDates = sdf.parse(fromDate[0] + " " + fromDate[1]);
			validToDates = sdf.parse(toDate[0] + " " + toDate[1]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		int uid = (int)map.get("uomId");
		
		if(type.equals("save"))
		{	
			int vendorid = Integer.parseInt(map.get("vendorId").toString());
			int imid = Integer.parseInt(map.get("imName").toString());			
			vendorPriceList.setVendorId(vendorid);
			vendorPriceList.setImId(imid);
			vendorPriceList.setRate(Long.valueOf(map.get("rate").toString()));
			vendorPriceList.setValidFrom(new java.sql.Date(validFromDates.getTime()));
			vendorPriceList.setValidTo(new java.sql.Date(validToDates.getTime()));
			vendorPriceList.setDeliveryAt((String)map.get("deliveryAt"));
			vendorPriceList.setPaymentTerms((String)map.get("paymentTerms"));
			vendorPriceList.setInvoice_Payable_days((int)map.get("Invoice_Payable_days"));
			vendorPriceList.setStatus((String) map.get("status"));
			vendorPriceList.setItemType((String)map.get("itemType"));
			
			vendorPriceList.setUomId(uid);
			
			vendorPriceList.setCreatedBy(userName);
			vendorPriceList.setLastUpdatedBy(userName);
		}
		else if(type.equals("update"))
		{	
			int vendorid = Integer.parseInt(map.get("vendorId").toString());
			int imid = Integer.parseInt(map.get("imId").toString());
			vendorPriceList.setUomId(uid);
			String st = (String)map.get("createdBy");
		    vendorPriceList.setVpId((int)map.get("vpId"));
			vendorPriceList.setVendorId(vendorid);
			vendorPriceList.setImId(imid);
			vendorPriceList.setRate(Long.valueOf(map.get("rate").toString()));
			vendorPriceList.setValidFrom(new java.sql.Date(validFromDates.getTime()));
			vendorPriceList.setValidTo(new java.sql.Date(validToDates.getTime()));
			vendorPriceList.setDeliveryAt((String)map.get("deliveryAt"));
			vendorPriceList.setPaymentTerms((String)map.get("paymentTerms"));
			vendorPriceList.setInvoice_Payable_days((int)map.get("Invoice_Payable_days"));
			vendorPriceList.setStatus((String) map.get("status"));
			vendorPriceList.setItemType((String)map.get("itemType"));
			vendorPriceList.setCreatedBy((String)map.get("createdBy"));
			vendorPriceList.setLastUpdatedBy(userName);
		}
		return vendorPriceList;
	}
	
	/***********  FOR STATUS UPDATION  ************/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setVendorPriceListStatus(int vpId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			PrintWriter out = response.getWriter();			
			if(operation.equalsIgnoreCase("approved"))
			{
				entityManager.createNamedQuery("VendorPriceList.UpdateStatus").setParameter("status", "Approved").setParameter("vpId", vpId).executeUpdate();
				out.write("Vendor Price List Approved");
			}
			else
			{
				entityManager.createNamedQuery("VendorPriceList.UpdateStatus").setParameter("status", "Rejected").setParameter("vpId", vpId).executeUpdate();
				out.write("Vendor Price List Rejected");				
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	/*****  END FOR STATUS UPDATION  *********/
	
}
