package com.bcits.bfm.serviceImpl;

import java.sql.Blob;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.model.PrepaidBillDocument;
import com.bcits.bfm.service.PrepaidBillDocService;

@Repository
public class PrepaidBillDocServiceImpl extends GenericServiceImpl<PrepaidBillDocument> implements PrepaidBillDocService{

	@Override
	public Blob getBlog(String billNo) {
		try{
			return (Blob) entityManager.createNamedQuery("PrepaidBillDocument.getBlog").setParameter("billNo", billNo).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	@Override
	public List<PrepaidBillDetails> downloadAllBills(java.util.Date currntMonth) {
		
			Calendar cal = Calendar.getInstance();
			cal.setTime(currntMonth);
			int month = cal.get(Calendar.MONTH);
			int montOne =month +1;
			int year = cal.get(Calendar.YEAR);
			try
			{
				return entityManager.createNamedQuery("PrepaidBillDetails.downloadAllBills",PrepaidBillDetails.class).setParameter("month", montOne).setParameter("year", year).getResultList();
			}
			catch(Exception exception)
			{
				return null;
			}
		}
		
	

	public List<PrepaidBillDetails> downloadAllBillsOnPropertyNo(java.util.Date monthDate, int propertyId) {
	
		 
		
        //String strDate = new SimpleDateFormat("YYYY-MM-dd").format(fromDate);
	  // String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(monthDate);
	   
	   Calendar cal = Calendar.getInstance();
		cal.setTime(monthDate);
		int month = cal.get(Calendar.MONTH);
		int montOne =month +1;
		int year = cal.get(Calendar.YEAR);
		
	   try
		{
			return entityManager.createNamedQuery("PrepaidBillDetails.downloadAllBillsOnPropertyNo",PrepaidBillDetails.class).setParameter("month", montOne).setParameter("year", year).setParameter("propertyId", propertyId).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	   
		
	}


}
