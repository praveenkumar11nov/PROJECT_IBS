package com.bcits.bfm.service.commonAreaMaintenance;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.CamConsolidationEntity;
import com.bcits.bfm.model.CamLedgerEntity;
import com.bcits.bfm.model.CamSubLedgerEntity;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ElectricityLedgerEntity;
import com.bcits.bfm.service.GenericService;

public interface CamConsolidationService extends  GenericService<CamConsolidationEntity> {

	List<CamConsolidationEntity> findALL();

	Long getNoOfFlats();

	Long getTotalSQFT();

	Double getTotalAmountBetweenDates(Date fromDate,Date toDate);

	List<CamLedgerEntity> getHeadsData(Date fromDate, Date toDate);

	List<CamSubLedgerEntity> getCamSubLedgerData(int camLedgerid);

	String getCalcBasis(String transactionCode);

	List<Integer> getPersonIdFromOwnerProperty(int propertyId);

	List<Integer> getPersonIdFromTenantProperty(int propertyId);

	Integer getAreaOfProperty(int propertyId);

	void setCamBillsStatus(int ccId, String operation,HttpServletResponse response);

	List<?> readAccountNumbers();

	List<Integer> findAllAccountsOfCamService();

	List<Integer> isEmptyCamConsolidationEntity();

	java.sql.Date getFromDateFromCamLedger();

	Integer getCamConsolidationMaxId();

	String getTransactionNameBasedOnCode(String transactionCode);

	int getLastBillObj(Integer accountId, String typeOfService,String postType);

	int getServiceMasterObj(Integer accountId, String typeOfService);

	BigDecimal getPreviousBill(int accountID, String typeOfService,String postType);

	BigDecimal getPreviousLedger(int accountID, String transactionCode);

	List<ElectricityLedgerEntity> getPreviousLedgerPayments(int accountID,String transactionCode);

	double getTotalBillLineItemAmount(int elBillId);

	double getTotalCamRates();

	int getNoOfParkingSlots(int parseInt);

	Object[] getAddressDetailsForMail(int personId);

	List<String> getContactDetailsForMail(int personId);

	double getParkingPerSlotAmount(String transactionCode);

	String getParameterValueBasedOnParameterName(String string,int elBillId);

	List<String> getParkingNos(int propertyId);

}
