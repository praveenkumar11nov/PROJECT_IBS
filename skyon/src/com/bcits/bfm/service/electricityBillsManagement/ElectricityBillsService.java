package com.bcits.bfm.service.electricityBillsManagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.service.GenericService;

/**
 * @author user51
 *
 */
public interface ElectricityBillsService extends  GenericService<ElectricityBillEntity> {
	/**
	 */
	List<ElectricityBillEntity> findALL();
	/**
	 */
	/**
	 */
	List<Object[]> findALLBills();
	/**
	 */
	List<?> find();
	/**
	 */
	Integer checkForDuplicateMonth(int accountID, String typeOfService,int month, int year);
	/**
	 */
	List<ElectricityBillEntity> getBillEntityByAccountId(int accountID,String typeOfService);
	/**
	 */
	double getConsolidatedBill(String cbId, Date record);
	/**
	 */
	List<Object[]> distinctCbId();
	/**
	 */
	ElectricityBillEntity getBillEntityById(int elBillId);
	/**
	 */
	void setApproveBill(int elBillId, String operation,HttpServletResponse response);
	/**
	 */
	ElectricityBillEntity getPreviousBill(int accountID, String typeOfService,java.util.Date previousBillDate,String postType);
	/**
	 */
	Long getCBID(int accountID, java.util.Date previousBillDate);
	/**
	 * @param accountId 
	 * @param serviceType 
	 */
	void cancelApproveBill(int elBillId, String operation,HttpServletResponse response, int accountId, String serviceType);
	/**
	 */
	List<ElectricityBillEntity> findAllBillData(java.util.Date presentdate,String serviceType);
	/**
	 */
	void approveAllBlls(String status);
	/**
	 */
	void setBillStatus(int elBillId, String operation,HttpServletResponse response);
	/**
	 */
	List<BigDecimal> getLastAvgBills(int accountID,String typeOfService, java.util.Date previousBillDate,String string, int avgCount);
	/**
	 */
	Set<String> commonFilterForAccountNumbersUrl();
	/**
	 */
	List<?> findPersonForFilters();
	/**
	 */
	void setStatusApproved(HttpServletResponse response,java.util.Date presentdate,String serviceType);
	/**
	 */
	void setElectricityBillStatusAsPosted(int elBillId,	HttpServletResponse response);
	/**
	 */
	List<?> findServiceTypes();
	/**
	 */
	List<ElectricityBillEntity> getBillsNearToBillDueDate();
	/**
	 */
	List<ElectricityBillEntity> getBillsOnBillDueDate();
	/**
	 */
	List<ElectricityBillEntity> getBillsAfterBillDueDate();
	/**
	 */
	List<?> findServiceTypesForBackBill();
	/**
	 */
	ElectricityBillEntity getPreviousBillWithOutStatus(int accountId,String typeOfService, Date billDate, String postType);
	/**
	 */
	List<?> getLastSixMontsBills(int accountId, String typeOfService,Date billDate,String unitsString);
	/**
	 */
	List<?> getLastSixMonthsCAMBills(int accountId, String typeOfService,Date billDate);
	/**
	 */
	double getLineItemAmountBasedOnTransactionCode(int elBillId, String transactionCode);
	/**
	 */
	List<?> getLastSixMontsBillsOthers(int accountId, String typeOfService,Date billDate, String string);
	/**
	 */
	List<ElectricityBillEntity> downloadAllBills(String serviceType,java.util.Date month);
	/**
	 */
	String getBillingConfigValue(String configName, String status);
	/**
	 */
	List<?> readBillsMonthWise(java.util.Date monthToShow);
	List<Object[]> readBillMonthWise(java.util.Date monthToShow);
	/**
	 */
	void updateBillDoc(int elBillId, Blob blob);
	//void saveBillDoc(ElectricityBillEntity electricityBillEntity, Blob blob);
	Blob getBlog(int elBillId);
	List<?> getAllBillDetailsMIS();
	List<?> getAllBillDetails2(String month) throws ParseException;
	List<?> getAllUnitDetails2(String month) throws ParseException;
	List<?> getAllBillLinItemDetails2(String month) throws ParseException;
	List<?> getAllUnitDetailsMIS();
	List<?> getAllAmrDateDetails();
	List<?> getAllBillLinItemDetails();
	List<?> getAllBillPaymentDetails();
	List<ElectricityBillEntity> getFiftyRecordsForTally(String serviceType1,java.util.Date currntMonth);
	List<ElectricityBillEntity> downloadAllBillsOnAccountNo(String serviceType,	java.util.Date monthDate, java.util.Date fromDate, int accNo);
	List<?> readDuplicateBillMonthWise(java.util.Date monthToShow,
			String serviceType);
	List<?> fetchBillsDataBasedOnMonthAndServiceType(int actualmonth,int year,String serviceType);
	
	void cancelAllBillsByMonth(HttpServletResponse response,String serviceType,java.util.Date presentDate) throws IOException;
	void setElectricityBillStatusAsPaid(int elBillId,HttpServletResponse response);
	void setBillStatusAsPaid(int elBillId,String operation,HttpServletResponse response);
	List<ElectricityBillEntity> findBasedOnAccountId(Integer accountId,
			String typeOfService);
	void updateMailSent_Status(int elBillId,String serviceType,String status);
	List<Object[]> getCo_OwnerDetails(int owner_propertyId);
	List<?> saveLatePaymentWise(java.util.Date monthToShow, String serviceType);
	
	/*uncommented this method for generate XML functionality*/
	List<ElectricityBillEntity> getAllRecordsForTally(String serviceType1,java.util.Date currntMonth);
	
	//*************************************************** Temprorary method to generate XML **********************************
	public List<Map<String, String>> generateAllDetails();
	public String getPrepaidDailyData(String meterNo,String fromDate,String toDate);

	List<Object[]> searchNotGeneratedBillMonth(String service,int month,int year);
	
	/*=====================================================================================================*/
	List<?> fetch200BillsData(int actualmonth,int year,String serviceType);
}
