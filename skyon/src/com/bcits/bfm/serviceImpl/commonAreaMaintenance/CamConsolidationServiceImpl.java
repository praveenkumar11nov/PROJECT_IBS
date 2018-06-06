package com.bcits.bfm.serviceImpl.commonAreaMaintenance;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.CamConsolidationEntity;
import com.bcits.bfm.model.CamLedgerEntity;
import com.bcits.bfm.model.CamSubLedgerEntity;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CamConsolidationServiceImpl extends GenericServiceImpl<CamConsolidationEntity> implements CamConsolidationService {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamConsolidationEntity> findALL() {
		return getAllDetails(entityManager.createNamedQuery("CamConsolidationEntity.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAllDetails(List<?> ledgerList){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> camLedgerMap = null;
		 for (Iterator<?> iterator = ledgerList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				camLedgerMap = new HashMap<String, Object>();
								
				camLedgerMap.put("ccId", (Integer)values[0]);	
				camLedgerMap.put("camName", (String)values[1]);
				camLedgerMap.put("fromDate", (Date)values[2]);	
				camLedgerMap.put("toDate", (Date)values[3]);
				camLedgerMap.put("noOfFlats", (Integer)values[4]);
				camLedgerMap.put("totalSqft", (Integer)values[5]);
				camLedgerMap.put("ratePerFlat", (Double)values[6]);	
				camLedgerMap.put("ratePerSqft", (Double)values[7]);
				camLedgerMap.put("noFlatsBilled", (Integer)values[8]);	
				camLedgerMap.put("billed", (Double)values[9]);
				camLedgerMap.put("blanceAmount", (Double)values[10]);	
				camLedgerMap.put("status", (String)values[11]);	
				camLedgerMap.put("chargesType", (String)values[12]);
				camLedgerMap.put("fixedPerSqft", (Double)values[13]);
				camLedgerMap.put("toBeBilled", (Double)values[14]);
				camLedgerMap.put("paidAmount", (Double)values[15]);
				camLedgerMap.put("rebateRate", (Double)values[16]);
						
			result.add(camLedgerMap);
	     }
      return result;
	}
	
	@Override
	public Long getNoOfFlats(){
		return (Long)entityManager.createNamedQuery("CamConsolidationEntity.getNoOfFlats").getSingleResult();
	}
	
	@Override
	public Long getTotalSQFT(){
		return (Long)entityManager.createNamedQuery("CamConsolidationEntity.getTotalSQFT").getSingleResult();
	}
	
	@Override
	public Double getTotalAmountBetweenDates(Date fromDate,Date toDate){
		
		/*String queryString = "SELECT SUM(AMOUNT) FROM CAM_LEDGER  WHERE LEDGER_DATE BETWEEN to_date('"+fromDate+"','yyyy/mm/dd') and to_date('"+toDate+"','yyyy/mm/dd')";
		final Query query = this.entityManager.createNativeQuery(queryString);
		return (BigDecimal) query.getSingleResult();*/
		return (Double)entityManager.createNamedQuery("CamConsolidationEntity.getTotalAmountBetweenDates").setParameter("fromDate", fromDate,TemporalType.DATE).setParameter("toDate", toDate,TemporalType.DATE).getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamLedgerEntity> getHeadsData(Date fromDate,Date toDate) {
		return entityManager.createNamedQuery("CamConsolidationEntity.getHeadsData").setParameter("fromDate", fromDate,TemporalType.DATE).setParameter("toDate", toDate,TemporalType.DATE).getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<CamSubLedgerEntity> getCamSubLedgerData(int camLedgerid) {
		return entityManager.createNamedQuery("CamConsolidationEntity.getCamSubLedgerData").setParameter("camLedgerid", camLedgerid).getResultList();
	}
	
	@Override
	public String getCalcBasis(String transactionCode){
		return (String)entityManager.createNamedQuery("CamConsolidationEntity.getCalcBasis").setParameter("transactionCode", transactionCode).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getPersonIdFromOwnerProperty(int propertyId){
		return (List<Integer>)entityManager.createNamedQuery("CamConsolidationEntity.getPersonIdFromOwnerProperty").setParameter("propertyId", propertyId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getPersonIdFromTenantProperty(int propertyId){
		return (List<Integer>)entityManager.createNamedQuery("CamConsolidationEntity.getPersonIdFromTenantProperty").setParameter("propertyId", propertyId).getResultList();
	}
	
	@Override
	public Integer getAreaOfProperty(int propertyId){
		return (Integer)entityManager.createNamedQuery("CamConsolidationEntity.getAreaOfProperty").setParameter("propertyId", propertyId).getSingleResult();
	}
	
	@Override
	public void setCamBillsStatus(int ccId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			CamConsolidationEntity camConsolidationEntity = find(ccId);
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("CamConsolidationEntity.setCamBillsStatus").setParameter("status", "Approved").setParameter("ccId", ccId).executeUpdate();
				out.write("CAM Bill details are approved");
			}
			else if(camConsolidationEntity.getStatus().equals("Posted")) {
				out.write("CAM Bill details are already posted");
			}else{
				entityManager.createNamedQuery("CamConsolidationEntity.setCamBillsStatus").setParameter("status", "Posted").setParameter("ccId", ccId).executeUpdate();
				entityManager.createNamedQuery("CamConsolidationEntity.setCamLedgerStatus").setParameter("status", "Posted").setParameter("ccId", ccId).executeUpdate();
				entityManager.createNamedQuery("CamConsolidationEntity.setCamLedgerPostedBill").setParameter("postedToBill", "Yes").setParameter("ccId", ccId).executeUpdate();
				entityManager.createNamedQuery("CamConsolidationEntity.setCamLedgerBillPostedDate").setParameter("postedBillDate", new Timestamp(new java.util.Date().getTime())).setParameter("ccId", ccId).executeUpdate();
				out.write("CAM Bill details are posted");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<?> readAccountNumbers() {
		return getAccountNumbersData(entityManager.createNamedQuery("CamConsolidationEntity.readAccountNumbers").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountNumbersData(List<?> accountNumbers){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> accountNumberMap = null;
		 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				accountNumberMap = new HashMap<String, Object>();
				
				accountNumberMap.put("accountId", (Integer)values[0]);
				accountNumberMap.put("accountNo", (String)values[1]);
				
				String personName = "";
				personName = personName.concat((String)values[3]);
				if(((String)values[4]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[4]);
				}
				
				accountNumberMap.put("personId", (Integer)values[2]);
				accountNumberMap.put("personName", personName);
				accountNumberMap.put("personType", (String)values[5]);
				accountNumberMap.put("personStyle", (String)values[6]);
				accountNumberMap.put("propertyId", (Integer)values[7]);
			
			result.add(accountNumberMap);
	     }
     return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findAllAccountsOfCamService() {
		return entityManager.createNamedQuery("CamConsolidationEntity.findAllAccountsOfCamService").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> isEmptyCamConsolidationEntity() {
		try{
			return entityManager.createNamedQuery("CamConsolidationEntity.isEmptyCamConsolidationEntity").getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date getFromDateFromCamLedger() {
		List<Date> fromDateList = entityManager.createNamedQuery("CamConsolidationEntity.getFromDateFromCamLedger").getResultList();
		try{
			return fromDateList.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer getCamConsolidationMaxId() {
		return (Integer)entityManager.createNamedQuery("CamConsolidationEntity.getCamConsolidationMaxId").getSingleResult();
	}

	@Override
	public String getTransactionNameBasedOnCode(String transactionCode) {
		return (String)entityManager.createNamedQuery("CamConsolidationEntity.getTransactionNameBasedOnCode").setParameter("transactionCode", transactionCode).getSingleResult();
	}

	@Override
	public int getLastBillObj(Integer accountId,String typeOfService,String postType) {
		try{
			int billId = (Integer)entityManager.createNamedQuery("CamConsolidationEntity.getLastBillObj").setParameter("accountId", accountId).setParameter("typeOfService", typeOfService).getSingleResult();
			return billId;
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public int getServiceMasterObj(Integer accountId, String typeOfService) {
		try{
			return (Integer)entityManager.createNamedQuery("CamConsolidationEntity.getServiceMasterObj").setParameter("accountId", accountId).setParameter("typeOfService", typeOfService).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public BigDecimal getPreviousBill(int accountId, String typeOfService, String postType) {
		
		try
		{
			String queryString = "SELECT ELB_ID  FROM (SELECT ELB_ID, rank() over (order by ELB_ID desc) rnk FROM BILL WHERE TYPE_OF_SERVICE='"+typeOfService+"' AND ACCOUNT_ID="+accountId+" AND POST_TYPE IN ('Bill','Pre Bill','Post Bill') AND STATUS !='Cancelled'  )  WHERE rnk = 2";
			final Query query = this.entityManager.createNativeQuery(queryString);
			return (BigDecimal) query.getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
	}

	@Override
	public BigDecimal getPreviousLedger(int accountID, String transactionCode) {
		try
		{
			String queryString = "SELECT ELL_ID  FROM (SELECT ELL_ID, rank() over (order by ELL_ID desc) rnk FROM LEDGER WHERE TR_CODE='"+transactionCode+"' AND ACCOUNT_ID="+accountID+") WHERE rnk = 2";
			final Query query = this.entityManager.createNativeQuery(queryString);
			return (BigDecimal) query.getSingleResult();
		}
		catch(Exception exception)
		{
			return null;
		}
	}

	@Override
	public List<ElectricityLedgerEntity> getPreviousLedgerPayments(int accountId, String transactionCode) {
		
		List<ElectricityLedgerEntity> finalList = new ArrayList<ElectricityLedgerEntity>();
		List<ElectricityLedgerEntity> test = entityManager.createNamedQuery("CamConsolidationEntity.getPreviousLedgerPayments",ElectricityLedgerEntity.class).setParameter("accountId", accountId).setParameter("transactionCode", transactionCode).getResultList();
		finalList.add(test.get(0));
		return finalList;
	}

	@Override
	public double getTotalBillLineItemAmount(int elBillId) {
		return (Double)entityManager.createNamedQuery("CamConsolidationEntity.getTotalBillLineItemAmount").setParameter("elBillId", elBillId).getSingleResult();
	}

	@Override
	public double getTotalCamRates() {
		return (Double)entityManager.createNamedQuery("CamConsolidationEntity.getTotalCamRates").getSingleResult();
	}

	@Override
	public int getNoOfParkingSlots(int propertyId) {
		return (Integer)entityManager.createNamedQuery("CamConsolidationEntity.getNoOfParkingSlots").setParameter("propertyId", propertyId).getSingleResult();
	}

	@Override
	public Object[] getAddressDetailsForMail(int personId) {
		return (Object[]) entityManager.createNamedQuery("CamConsolidationEntity.getAddressDetailsForMail").setParameter("personId", personId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getContactDetailsForMail(int personId) {
		return entityManager.createNamedQuery("CamConsolidationEntity.getContactDetailsForMail").setParameter("personId", personId).getResultList();
	}

	@Override
	public double getParkingPerSlotAmount(String transactionCode) {
		try{
			return (Double)entityManager.createNamedQuery("CamConsolidationEntity.getParkingPerSlotAmount").setParameter("transactionCode", transactionCode).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public String getParameterValueBasedOnParameterName(String bvmName, int elBillId) {
		try{
			return (String)entityManager.createNamedQuery("CamConsolidationEntity.getParameterValueBasedOnParameterName").setParameter("bvmName", bvmName).setParameter("elBillId", elBillId).getSingleResult();
		}catch(Exception e){
			return "";
		}
	}
	@Override
	public List<String> getParkingNos(int propertyId) {
		return entityManager.createNamedQuery("CamConsolidationEntity.getParkingNos",String.class).setParameter("propertyId", propertyId).getResultList();
	}

	
}
