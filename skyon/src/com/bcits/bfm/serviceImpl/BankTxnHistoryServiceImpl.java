package com.bcits.bfm.serviceImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BankTransactionHistory;
import com.bcits.bfm.service.BankTxnHstyservice;
/*
 * ****vema***
 * 
 */
@Repository
public class BankTxnHistoryServiceImpl extends GenericServiceImpl<BankTransactionHistory> implements BankTxnHstyservice {

	@SuppressWarnings("unchecked")
	@Override
	public List<?> readAllPayTimeData() {
		System.out.println("1");
		try{
			List<?> result=entityManager.createNamedQuery("BankTransactionHistory.getPayTimeDetails").getResultList();
			System.out.println("******"+result.size());
			/*List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
			Map<String, Object> mapList=null;
			for (BankTransactionHistory bankTransactionHistory : result) {
				System.out.println("txn details  ="+bankTransactionHistory);
				mapList=new HashMap<>();
				System.out.println("id  "+bankTransactionHistory.getId());
				mapList.put("id", bankTransactionHistory.getId());
				mapList.put("txn_ID", bankTransactionHistory.getTxn_ID());
				if(bankTransactionHistory.getTxn_ID().equals("GRANDM44940339368982")){
				mapList.put("mid","payTM");
				}
				DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK);
				mapList.put("txn_Date",dateFormat.format(bankTransactionHistory.getTxn_Date()));
				mapList.put("txn_Amount",bankTransactionHistory.getTxn_Amount());
				mapList.put("issuing_Bank", bankTransactionHistory.getIssuing_Bank());
				mapList.put("status", bankTransactionHistory.getStatus());
				mapList.put("settled_Amt",bankTransactionHistory.getSettled_Amt() );
				mapList.put("commission_Amt",bankTransactionHistory.getCommission_Amt() );
				mapList.put("settled_Date", dateFormat.format(bankTransactionHistory.getSettled_Date()) );
				if(bankTransactionHistory.getCust_ID()!=null){
					String propertyNo=(String) entityManager.createQuery("select pt.property_No from Property pt WHERE pt.propertyId IN (select a.propertyId from Account a  WHERE a.accountNo =:accountNo)").setParameter("accountNo", bankTransactionHistory.getCust_ID()).getSingleResult();
					mapList.put("propertyNo", propertyNo);
				}
				mapList.put("cust_ID",bankTransactionHistory.getCust_ID() );
				if(bankTransactionHistory.getCust_ID()!=null){
					String qry="select  p.firstName, p.lastName from Account a INNER JOIN a.person p WHERE a.accountNo =:accountNo";
					List<?> banktxn=entityManager.createQuery(qry).setParameter("accountNo", bankTransactionHistory.getCust_ID()).getResultList();
				
					for(Iterator<?> iterator1=banktxn.iterator();iterator1.hasNext();){
						final Object[] value1=(Object[]) iterator1.next();
					
					mapList.put("personName",value1[0]+" "+value1[1]);
					System.out.println("8888888888888888         "+value1[0]+" "+value1[1]);
					}
				}
				
				resultList.add(mapList);
			}*/
		return getPayTimeData(result);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getPayTimeData(List<?> bankTransactionList){
		System.out.println("2");
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String, Object> mapList=null;
		System.out.println(bankTransactionList.size());
		for(Iterator<?> iterator=bankTransactionList.iterator();iterator.hasNext();){
			
			final Object[] value=(Object[]) iterator.next();
			mapList=new HashMap<>();
			mapList.put("id", value[0]);
			System.out.println("id  ="+value[0]);
			mapList.put("txn_ID", value[1]);
			System.out.println("txn ID "+value[1]);
			try{
			if(value[2].equals("GRANDM44940339368982")){
				mapList.put("mid", "PayTM");
			}
			else if(value[2]==null){
			mapList.put("mid","PayU");
			}else{
			  mapList.put("mid","PayU");
			}
			}catch(Exception e){}
			
			if((Timestamp)value[3]==null){
				try{
				mapList.put("txn_Date", value[3]);
				}catch(Exception e){}
			}else{
			String S = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK).format((Timestamp)value[3]);
			mapList.put("txn_Date", S);
			}
			
			mapList.put("txn_Amount", value[4]);
			System.out.println("txn_Amount==" + value[4]);
			mapList.put("payment_Mode", value[5]);
			mapList.put("cust_ID", value[6]);
			mapList.put("issuing_Bank", value[7]);
			mapList.put("status", value[8]);
			mapList.put("settled_Amt", value[9]);
			mapList.put("commission_Amt", value[10]);
			
			if((Timestamp)value[11]==null){
				try{
				mapList.put("settled_Date", value[11]);
				}catch(Exception e){}
			}else{
			String S = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK).format((Timestamp)value[11]);
			mapList.put("settled_Date", S);
			} 
			System.out.println("accountnumber"+value[6]);
			try{
			if(value[6]!=null){
			String propertyNo=(String) entityManager.createQuery("select pt.property_No from Property pt WHERE pt.propertyId IN (select a.propertyId from Account a  WHERE a.accountNo =:accountNo)").setParameter("accountNo", value[6]).getSingleResult();
			mapList.put("propertyNo", propertyNo);
		}
			}catch(Exception e){e.printStackTrace();};
			
			try{
			if(value[6]!=null){
			String qry="select  p.firstName, p.lastName from Account a INNER JOIN a.person p WHERE a.accountNo =:accountNo";
			List<?> banktxn=entityManager.createQuery(qry).setParameter("accountNo", value[6]).getResultList();
		
			for(Iterator<?> iterator1=banktxn.iterator();iterator1.hasNext();){
				final Object[] value1=(Object[]) iterator1.next();
			
			mapList.put("personName",value1[0]+" "+value1[1]);
			System.out.println("8888888888888888         "+value1[0]+" "+value1[1]);
			}
			}}catch(Exception e){e.printStackTrace();}
			/*if ((Timestamp)value[23]==null) {
				try{
				mapList.put("txn_Updated_Date", value[23]);
				}catch(Exception e){}
			}else{
			String S = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK).format((Timestamp)value[23]);
			mapList.put("txn_Updated_Date", S);
			}*/
			System.out.println(mapList);
			resultList.add(mapList);
		}
			
			
		return resultList;
	}

	@Override
	public String getAccId(String customerName,String customerMail) {
		
		System.out.println("in side accid");
		try{
		Integer accId=(Integer) entityManager.createQuery("SELECT a.accountId  FROM Account a,Contact c INNER JOIN a.person p WHERE upper(p.firstName) like '"+customerName.toUpperCase()+"' AND upper(c.contactContent) like '"+customerMail.toUpperCase()+"'").getSingleResult();
		System.out.println(accId);
		String acid=accId.toString();
		System.out.println("ACCOUNT ID = "+acid);
		return acid;
		}catch(Exception e){
			
		}
		return null;
	}

	@Override
	public String getAccountID(String accountNo) {
		System.out.println("in side paytime");
		try{
			Integer accId=(Integer)  entityManager.createQuery("SELECT a.accountId FROM Account a WHERE a.accountNo=:accountNo").setParameter("accountNo", accountNo).getSingleResult();
			System.out.println(accId);
			String acid=accId.toString();
			System.out.println("Account ID ="+acid);
			return acid;
		}catch(Exception e){}
		return null;
	}

	@Override
	public List<?> getAllByMonth(Date fromDate,Date toDate) {
		System.out.println("1111111111111111");
		
		String S = DateFormatUtils.format((Date)fromDate,"yyyy/MM/dd");
		String S1= DateFormatUtils.format((Date)toDate,"yyyy/MM/dd");
	/*	Calendar cal = Calendar.getInstance();
	 	 cal.setTime(PresentMonth);
		 int month = cal.get(Calendar.MONTH);
		 int montOne = month +1;
		 int year = cal.get(Calendar.YEAR);*/
		/*BETWEEN to_date('"+from+"','yyyy/mm/dd') and to_date('"+to+"','yyyy/mm/dd')")
*/		return getData(entityManager.createQuery("SELECT b.id,b.txn_ID,b.mid,b.txn_Date,b.txn_Amount,b.payment_Mode,b.bank_Txn_ID,b.cust_ID,b.issuing_Bank,b.status,b.settled_Amt,b.status_type,b.commission_Amt,b.settled_Date,b.txn_Updated_Date,b.account_Id  FROM BankTransactionHistory b WHERE b.txn_Date BETWEEN to_date('"+S+"','yyyy/mm/dd') and to_date('"+S1+"','yyyy/mm/dd') and b.status IN ('SETTLED','Money Settled','Settlement in Process') and b.txn_ID NOT IN(SELECT o.transaction_id FROM OnlinePaymentTransactions o WHERE  o.created_date BETWEEN to_date('"+S+"','yyyy/mm/dd') and to_date('"+S1+"','yyyy/mm/dd'))").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getData(List<?> bankTransactionList){
		
		System.out.println("222222222222222222222");
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String, Object> mapList=null;
		for(Iterator<?> iterator=bankTransactionList.iterator();iterator.hasNext();){
			final Object[] value=(Object[]) iterator.next();
			mapList=new HashMap<>();
			mapList.put("id", value[0]);
			mapList.put("txn_ID", value[1]);
			try{
				if(value[2].equals("GRANDM44940339368982")){
					mapList.put("mid", "PayTM");
				}
				else{
				mapList.put("mid","PayU");
				}
				}catch(Exception e){}
			
			if((Timestamp)value[3]==null){
				try{
				mapList.put("txn_Date", value[3]);
				}catch(Exception e){}
			}else{
			String S = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK).format((Timestamp)value[3]);
			mapList.put("txn_Date", S);
			}
			
		
			mapList.put("txn_Amount", value[4]);
			
			mapList.put("payment_Mode", value[5]);
			mapList.put("bank_Txn_ID", value[6]);
			mapList.put("cust_ID", value[7]);
			mapList.put("issuing_Bank", value[8]);
			mapList.put("status", value[9]);
			mapList.put("settled_Amt", value[10]);
			mapList.put("status_type", value[11]);
			mapList.put("commission_Amt", value[12]);
			
			if((Timestamp)value[13]==null){
				try{
				mapList.put("settled_Date", value[13]);
				}catch(Exception e){}
			}else{
			String S = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK).format((Timestamp)value[13]);
			mapList.put("settled_Date", S);
			}
			
			if ((Timestamp)value[14]==null) {
				try{
				mapList.put("txn_Updated_Date", value[14]);
				}catch(Exception e){}
			}else{
			String S = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.UK).format((Timestamp)value[14]);
			mapList.put("txn_Updated_Date", S);
			}
			
			mapList.put("account_Id", value[15]);
			
			if(value[7]!=null){
			String propertyNo=(String) entityManager.createQuery("select pt.property_No from Property pt WHERE pt.propertyId IN (select a.propertyId from Account a  WHERE a.accountNo =:accountNo)").setParameter("accountNo", value[7]).getSingleResult();
			mapList.put("propertyNo", propertyNo);
			
			String qry="select  p.firstName, p.lastName from Account a INNER JOIN a.person p WHERE a.accountNo =:accountNo";
			List<?> banktxn=entityManager.createQuery(qry).setParameter("accountNo", value[7]).getResultList();
		
			for(Iterator<?> iterator1=banktxn.iterator();iterator1.hasNext();){
				final Object[] value1=(Object[]) iterator1.next();
			
			mapList.put("personName",value1[0]+" "+value1[1]);
			}
			}
			resultList.add(mapList);
		}
			
			
		return resultList;
	}

	@Override
	public String getAccountNo(int accountId) {
		System.out.println("in side accNum");
		try{
			String accno=(String) entityManager.createQuery("SELECT a.accountNo FROM Account a WHERE a.accountId = :accountId").setParameter("accountId",accountId).getSingleResult();
			/*System.out.println(accno);
			String acNo=accno.toString();*/
			System.out.println("Account Number="+accno);
			return accno;
		}catch(Exception e){}
		return null;
	}

	/*@Override
	public List<?> getPropertyNumForFilter() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("BankTransactionHistory.getPropertyNoForFilter").getResultList();
	}*/
}
