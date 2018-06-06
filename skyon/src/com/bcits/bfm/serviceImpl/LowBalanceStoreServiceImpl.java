package com.bcits.bfm.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.SmsEmailForLowBal;
import com.bcits.bfm.service.LowBalanceStoreService;

@Repository
public class LowBalanceStoreServiceImpl extends GenericServiceImpl<SmsEmailForLowBal> implements LowBalanceStoreService{

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsEmailForLowBal> getAllData(String meterNo, String txnId) {
	       try{
	    	   System.out.println("in side1");
	    	   return entityManager.createNamedQuery("SmsEmailForLowBal.lowBal1").setParameter("meterNo", meterNo).setParameter("txn_Id", txnId).getResultList();
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
		return null;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsEmailForLowBal> getAllData1(String meterNo, String txnId) {
	       try{
	    	   System.out.println("in side2");
	    	   return entityManager.createNamedQuery("SmsEmailForLowBal.lowBal2").setParameter("meterNo", meterNo).setParameter("txn_Id", txnId).getResultList();
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsEmailForLowBal> getAllData2(String meterNo, String txnId) {
	       try{
	    	   System.out.println("in side3");
	    	   return entityManager.createNamedQuery("SmsEmailForLowBal.lowBal3").setParameter("meterNo", meterNo).setParameter("txn_Id", txnId).getResultList();
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
		return null;
	}

	@Override
	public int getEstateManager() {
		try{
			return (int) entityManager.createNamedQuery("SmsEmailForLowBal.EstateManager").getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

}
