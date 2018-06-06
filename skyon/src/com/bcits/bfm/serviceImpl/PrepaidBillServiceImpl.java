package com.bcits.bfm.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BatchBillsEntity;
import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.service.PrepaidBillService;

@Repository
public class PrepaidBillServiceImpl  extends GenericServiceImpl<PrepaidBillDetails> implements PrepaidBillService{

	@Override
	public List<PrepaidBillDetails> getData() {
		try{
		 return entityManager.createNamedQuery("PrepaidBillDetails.getPreData").getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getCount(int propertyId, Date presentDate) {
		    Calendar cal=Calendar.getInstance();
		    cal.setTime(presentDate);
		    int mnth=cal.get(Calendar.MONTH);
		    int month=mnth+1;
		    int year=cal.get(Calendar.YEAR);
		try{
			return (long) entityManager.createNamedQuery("PrepaidBillDetails.getCount").setParameter("propertyId", propertyId).setParameter("month", month).setParameter("year", year).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public List<?> ReadPropertys() {
		try{
			return entityManager.createNamedQuery("PrePaidPaymentEntity.getPropertyNum").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
	return null;
	}

	@Override
	public void updateMailSent_Status(String billNo, String status) {
		entityManager.createQuery("Update PrepaidBillDetails o SET o.mailStatus=:status Where o.billNo=:billNo").setParameter("status", status).setParameter("billNo", billNo).executeUpdate();
		
	}
	
	@Override
	public List<BatchBillsEntity> getPostData() {
		try{
			 return entityManager.createNamedQuery("BatchBillsEntity.getPostData").getResultList();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}


}
