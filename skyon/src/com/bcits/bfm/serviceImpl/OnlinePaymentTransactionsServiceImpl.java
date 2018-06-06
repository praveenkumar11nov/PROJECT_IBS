package com.bcits.bfm.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.OnlinePaymentTransactions;
import com.bcits.bfm.service.OnlinePaymentTransactionsService;

@Repository
public class OnlinePaymentTransactionsServiceImpl extends GenericServiceImpl<OnlinePaymentTransactions> implements
		OnlinePaymentTransactionsService {

	/*@Override
	public List<OnlinePaymentTransactions> getAllTransactions() {
		return entityManager.createNamedQuery("OnlinePaymentTransactions.getAllTransactions").getResultList();
	}*/

	@Override
	public List<?> getTransactionDetailsByAccountId(String from , String to) {
		return entityManager.createQuery("select o.payumoney_id, o.service_type, o.transaction_id, o.payment_status, o.created_date, o.tx_amount, pt.property_No, p.firstName, p.lastName, ac.accountNo, o.id,o.orderId,o.failedReason,o.merchantId from OnlinePaymentTransactions o,Property pt INNER JOIN o.accountEntity ac INNER JOIN ac.person p WHERE ac.propertyId=pt.propertyId and o.created_date BETWEEN to_date('"+from+"','yyyy/mm/dd') and to_date('"+to+"','yyyy/mm/dd')").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("OnlinePaymentTransactions.commonFilterForAccountNumbersUrl").getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForPropertyNumbersUrl() {
		
		return new HashSet<String>(entityManager.createNamedQuery("OnlinePaymentTransactions.commonFilterForPropertyNumbersUrl").getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForPaymentStatusUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("OnlinePaymentTransactions.commonFilterForPaymentStatusUrl").getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForPayUMoneyIdUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("OnlinePaymentTransactions.commonFilterForPayUMoneyIdUrl").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForTransactionIdUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("OnlinePaymentTransactions.commonFilterTransactionIdUrl").getResultList());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForServiceTypeUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("OnlinePaymentTransactions.commonFilterForServiceTypeUrl").getResultList());
	}
	
	
	/*****************************************No of records for Dashboard Transaction*****************************************/
	@Override
	public Map<String, Object> getTransactionDetails() {
		
		
		
		List<?> txnData=entityManager.createNamedQuery("OnlinePaymentTransactions.getTransactionDetails").getResultList();
		Map<String,Object> txnMap=new HashMap<>();
		int flag=0;
		for (Iterator<?> i = txnData.iterator(); i.hasNext();) {
			flag=1;
			Object[] obj=(Object[]) i.next();
			if(obj[0]!=null){
				txnMap.put("count",(long)obj[0]);	
			}else{
				txnMap.put("count",(long)0);	
			}
			if(obj[1]!=null){
				txnMap.put("activeCount",(long)obj[1]);
			}else{
				txnMap.put("activeCount",(long)0);
			}
			if(obj[2]!=null){
				txnMap.put("inActiveCount",(long)obj[2]);
			}else{
				txnMap.put("inActiveCount",(long)0);	
			}
		
		}
		if(flag==0){
			txnMap.put("count",(long)0);	
			txnMap.put("activeCount",(long)0);
			txnMap.put("inActiveCount",(long)0);	
		}
		return txnMap;
	}
	


}
