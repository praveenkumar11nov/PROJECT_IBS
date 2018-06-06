package com.bcits.bfm.serviceImpl.billingCollectionManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BillingPaymentsEntity;
import com.bcits.bfm.model.ChequeBounceEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.model.PaymentAdjustmentEntity;
import com.bcits.bfm.model.PaymentSegmentsEntity;
import com.bcits.bfm.service.accountsManagement.ElectricityLedgerService;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentService;
import com.bcits.bfm.service.billingCollectionManagement.ChequeBounceService;
import com.bcits.bfm.service.billingCollectionManagement.PaymentService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ChequeBounceServiceImpl extends GenericServiceImpl<ChequeBounceEntity> implements ChequeBounceService  {
	
	@Autowired
	private AdjustmentService adjustmentService; 
	
	@Autowired
	private ElectricityLedgerService electricityLedgerService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Override
	public List<?> findAll() {
		return getAllDetails(entityManager.createNamedQuery("ChequeBounceEntity.findAll").getResultList());
	}
	
	int count=0;
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> adjustmentEntities){
		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> chequeBounceMap = null;
		 for (Iterator<?> iterator = adjustmentEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				chequeBounceMap = new HashMap<String, Object>();
				
				chequeBounceMap.put("chequeBounceId", (Integer)values[0]);
				chequeBounceMap.put("accountId", (Integer)values[1]);
				chequeBounceMap.put("receiptNo", (String)values[2]);
				chequeBounceMap.put("chequeNo", (String)values[3]);
				chequeBounceMap.put("bankName", (String)values[4]);
				chequeBounceMap.put("chequeGivenDate", (Date)values[5]);
				chequeBounceMap.put("bouncedDate", (Date)values[6]);
				chequeBounceMap.put("chequeAmount", (Double)values[7]);
				chequeBounceMap.put("penalityAmount", (Double)values[8]);
				chequeBounceMap.put("status", (String)values[9]);
				chequeBounceMap.put("accountNo", (String)values[10]);
				String personName = "";
				personName = personName.concat((String)values[11]);
				if((String)values[12] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[12]);
				}
				chequeBounceMap.put("personName", personName);
				chequeBounceMap.put("property_No",(String)values[13]);
				chequeBounceMap.put("remarks",(String)values[14]);
				chequeBounceMap.put("bankCharges", (Double)values[15]);
				chequeBounceMap.put("amountValid",(String)values[16]);
				chequeBounceMap.put("previousLateAmount", (Double)values[17]);
			
			result.add(chequeBounceMap);
	     }
      return result;
	}

	@Override
	public List<String> getAllReceiptNos(int accountId) {
		return entityManager.createNamedQuery("ChequeBounceEntity.getAllReceiptNos",String.class).setParameter("accountId", accountId).getResultList();
	}

	@SuppressWarnings("null")
	@Override
	public void changeChequeBounceStatus(int chequeBounceId,HttpServletResponse response) {

		PrintWriter out = null;
		try {
			out = response.getWriter();
			entityManager.createNamedQuery("ChequeBounceEntity.changeChequeBounceStatus").setParameter("chequeBounceId", chequeBounceId).executeUpdate();
			out.write("Cheque Bounce Details Approved Successfully");
			chequeBounceDetailsPostToAdjustmentAndLedger(chequeBounceId);
			count=0;
		} catch (IOException e){
			out.write("Cheque Bounce Details Not Approved,Got Some Exceptions");
		}
	}
	
	private void chequeBounceDetailsPostToAdjustmentAndLedger(int chequeBounceId){
		
		ChequeBounceEntity bounceEntity = find(chequeBounceId);
		
		List<Integer> paymentList = getPaymentIdBasedOnChequeBounce(bounceEntity);
		
		if(!paymentList.isEmpty()){
			
			BillingPaymentsEntity paymentsEntity = paymentService.find(paymentList.get(0));
			
			List<PaymentSegmentsEntity> segmentsList = paymentService.getPaymentSegmentsList(paymentList.get(0));
			
			for (PaymentSegmentsEntity paymentSegmentsEntity : segmentsList) {
				
				if(!(paymentSegmentsEntity.getBillSegment().equalsIgnoreCase("Late Payment"))){
					
					PaymentAdjustmentEntity adjustmentEntity = new PaymentAdjustmentEntity();
					
					adjustmentEntity.setAccountId(bounceEntity.getAccountId());
					adjustmentEntity.setAdjustmentAmount(-paymentSegmentsEntity.getAmount());
					adjustmentEntity.setClearedStatus("Yes");
					adjustmentEntity.setAdjustmentDate(new java.sql.Date(new java.util.Date().getTime()));
					adjustmentEntity.setAdjustmentLedger(paymentSegmentsEntity.getBillSegment()+" Ledger");
					adjustmentEntity.setJvDate(new Timestamp(new java.util.Date().getTime()));
					try {
						adjustmentEntity.setAdjustmentNo(genrateAdjustmentNumber());
					} catch (Exception e) {
						e.printStackTrace();
					}
					adjustmentEntity.setPostedToGl("Yes");
					adjustmentEntity.setStatus("Posted");
					adjustmentEntity.setPostedGlDate(new Timestamp(new java.util.Date().getTime()));
					adjustmentEntity.setAdjustmentType("Cheque Bounce");
					adjustmentEntity.setTallystatus("Not Posted");
					
					adjustmentService.save(adjustmentEntity);
					
					chequeBounceAdjustmentDetailsPostedToLedger(adjustmentEntity,bounceEntity);
					
				}
			}
			
			if(paymentsEntity.getExcessAmount()>0){
				PaymentAdjustmentEntity adjustmentEntity = new PaymentAdjustmentEntity();
				
				adjustmentEntity.setAccountId(bounceEntity.getAccountId());
				adjustmentEntity.setAdjustmentAmount(-paymentsEntity.getExcessAmount());
				adjustmentEntity.setClearedStatus("Yes");
				adjustmentEntity.setAdjustmentDate(new java.sql.Date(new java.util.Date().getTime()));
				adjustmentEntity.setAdjustmentLedger("CAM Ledger");
				adjustmentEntity.setJvDate(new Timestamp(new java.util.Date().getTime()));
				try {
					adjustmentEntity.setAdjustmentNo(genrateAdjustmentNumber());
				} catch (Exception e) {
					e.printStackTrace();
				}
				adjustmentEntity.setPostedToGl("Yes");
				adjustmentEntity.setStatus("Posted");
				adjustmentEntity.setPostedGlDate(new Timestamp(new java.util.Date().getTime()));
				adjustmentEntity.setAdjustmentType("Cheque Bounce");
				adjustmentEntity.setTallystatus("Not Posted");
				
				adjustmentService.save(adjustmentEntity);
				
				chequeBounceAdjustmentDetailsPostedToLedger(adjustmentEntity,bounceEntity);
			}
		}
	}
	
	private void chequeBounceAdjustmentDetailsPostedToLedger(PaymentAdjustmentEntity adjustmentEntity,ChequeBounceEntity bounceEntity){
		
		System.err.println("Count="+count);
		System.err.println("adj accountId="+adjustmentEntity.getAccountId()+"  adjustmentEntity.getAdjustmentLedger()="+adjustmentEntity.getAdjustmentLedger()); 
		int lastTransactionLedgerId = electricityLedgerService.getLastLedgerTransactionAmount(adjustmentEntity.getAccountId(),adjustmentEntity.getAdjustmentLedger());
		ElectricityLedgerEntity lastTransactionLedgerEntity = electricityLedgerService.find(lastTransactionLedgerId);

		ElectricityLedgerEntity ledgerEntity = new ElectricityLedgerEntity();
		ledgerEntity.setTransactionSequence((electricityLedgerService.getLedgerSequence(adjustmentEntity.getAccountId()).intValue()) + 1);
		ledgerEntity.setAccountId(adjustmentEntity.getAccountId());

		String segmentType = adjustmentEntity.getAdjustmentLedger();
		ledgerEntity.setLedgerType(segmentType);
		ledgerEntity.setPostType("ADJUSTMENT");

		if (segmentType.equals("Electricity Ledger")) {
			ledgerEntity.setTransactionCode("EL");
		}
		if (segmentType.equals("Gas Ledger")) {
			ledgerEntity.setTransactionCode("GA");
		}
		if (segmentType.equals("Solid Waste Ledger")) {
			ledgerEntity.setTransactionCode("SW");
		}
		if (segmentType.equals("Water Ledger")) {
			ledgerEntity.setTransactionCode("WT");
		}
		if (segmentType.equals("Common Ledger")) {
			ledgerEntity.setTransactionCode("OT");
		}
		if (segmentType.equals("CAM Ledger")) {
			ledgerEntity.setTransactionCode("CAM");
		}
		if (segmentType.equals("Telephone Broadband Ledger")) {
			ledgerEntity.setTransactionCode("TEL");
		}

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		Calendar calLast = Calendar.getInstance();
		int lastYear = calLast.get(Calendar.YEAR)-1;
		double totalpenalty = bounceEntity.getBankCharges()+bounceEntity.getPenalityAmount();
		
		ledgerEntity.setLedgerPeriod(lastYear+"-"+currentYear);
		ledgerEntity.setPostReference(adjustmentEntity.getAdjustmentNo());
		ledgerEntity.setLedgerDate(new java.sql.Date(new java.util.Date().getTime()));
		ledgerEntity.setLedgerDate(adjustmentEntity.getAdjustmentDate());
		/*-----------------------------------------------------------------------------------------*/
		if(count==0){//First adjustment penalty is charged
			ledgerEntity.setAmount(adjustmentEntity.getAdjustmentAmount() - totalpenalty);
			ledgerEntity.setBalance((lastTransactionLedgerEntity.getBalance()) - (adjustmentEntity.getAdjustmentAmount()-totalpenalty));
		}else{//second adjustment penalty is not charged
			ledgerEntity.setAmount(adjustmentEntity.getAdjustmentAmount());
			ledgerEntity.setBalance((lastTransactionLedgerEntity.getBalance()) - (adjustmentEntity.getAdjustmentAmount()));
		}
		/*-----------------------------------------------------------------------------------------*/
		ledgerEntity.setPostedBillDate(new Timestamp(new java.util.Date().getTime()));
		ledgerEntity.setStatus("Approved");
		ledgerEntity.setRemarks(adjustmentEntity.getAdjustmentType()+"/"+bounceEntity.getRemarks()+"/BankCharges:"+bounceEntity.getBankCharges()+"/Penalty:"+bounceEntity.getPenalityAmount());

		electricityLedgerService.save(ledgerEntity);
		count++;
	}
	
	public String genrateAdjustmentNumber() throws Exception {  
		/*Random generator = new Random();  
		generator.setSeed(System.currentTimeMillis());  
		   
		int num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		num = generator.nextInt(99999) + 99999;  
		if (num < 100000 || num > 999999) {  
		throw new Exception("Unable to generate PIN at this time..");  
		}  
		}  
		return "AD"+num; */ 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new java.util.Date());
		int nextSeqVal = adjustmentService.autoGeneratedAdjustmentNumber();	 
		
		return "AD"+year+nextSeqVal;
	}
	

	@Override
	public List<?> getChequeDetailsBasedOnChequeNumber(String chequeNo, String bankName, String receiptNo) {		
		return entityManager.createNamedQuery("ChequeBounceEntity.getChequeDetailsBasedOnChequeNumber").setParameter("chequeNo", chequeNo).setParameter("bankName", bankName).setParameter("receiptNo", receiptNo).getResultList();
	}
	
	@Override
	public List<Integer> getPaymentIdBasedOnChequeBounce(ChequeBounceEntity bounceEntity){
		return entityManager.createNamedQuery("ChequeBounceEntity.getPaymentIdBasedOnChequeBounce",Integer.class).setParameter("accountId", bounceEntity.getAccountId()).setParameter("receiptNo", bounceEntity.getReceiptNo()).setParameter("chequeNo", bounceEntity.getChequeNo()).setParameter("bankName", bounceEntity.getBankName()).getResultList();
	}

	@Override
	public void updateChequeBounceDetailsStatusBasedOnBillsPosting(int accountId) {
		entityManager.createNamedQuery("ChequeBounceEntity.updateChequeBounceDetailsStatusBasedOnBillsPosting").setParameter("accountId", accountId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAll() {
	      try{
		return entityManager.createNamedQuery("ChequeBounceEntity.findAll").getResultList();
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
	      return null;
	}
}
