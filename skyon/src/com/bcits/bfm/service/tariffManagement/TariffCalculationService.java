package com.bcits.bfm.service.tariffManagement;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;








import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.model.ELRateSlabs;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GenericBillGeneration;
import com.bcits.bfm.model.GenericClassForTodCalculation;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.model.SlabDetails;
import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.WTRateMaster;

public interface TariffCalculationService 
{
	public List<Object[]> getTariffNameCal();
	public List<Object[]> getServiceName(int accountd);
	public List<Map<String, Object>> getServiceStatus(int nodeid,String serviceType, int serviceId);
	List<?> getAllAccuntNumbers();
	public List<Object> getBillOnDate(int date,int year,String serviceType,int accountId);
	public List<Object[]> getTariffNameCalTod();
	public List<Object[]> getRateNameCal(int eltariffId);
	public List<ELRateMaster> getRateUomCal(String ratename,int elTariffID);
	public HashMap<Object, Object> tariffTod(ELRateMaster elRateMaster,Integer tod1, Integer tod2,Integer tod3, Date startDate, Date endDate);
	public HashMap<Object, Object> tariffTodCalculation(GenericClassForTodCalculation<String, String, Integer, Integer,Integer,Date, Date> calucaltion,HttpServletResponse response);
	public ELTariffMaster getTariffMasterByName(String tariffName);
	public List<Map<String, Object>> getMinMaxDate(int elTariffID,String rateName);
	public List<ELRateMaster> getRateMasterByIdName(int elTariffID,String rateName);
	public HashMap<Object, Object> tariffCalculationSingleSlab(ELRateMaster elRateMaster, Date startDate, Date endDate,float consumptionPerMonth1);
	public HashMap<Object, Object> tariffCalculationMultiSlab(ELRateMaster elRateMaster, Date startDate, Date endDate,Float uomValue);
	public HashMap<Object, Object> tariffCalculationRangeSlab(ELRateMaster elRateMaster, Date startDate, Date endDate,Float uomValue);
	public HashMap<Object, Object> drAmount(ELRateMaster elRateMaster, Date startDate,Date endDate, float uomValue);
	public HashMap<Object, Object> dcAmount(ELRateMaster elRateMaster, Date startDate,Date endDate, Float uomValue, float uomValue2);
	public HashMap<Object, Object> powerFactorPenalty(ELRateMaster elRateMaster, Date startDate,Date endDate, Float uomValue, float contratDemand);
	public List<ELRateMaster> getElectricityRateMaster(int tariffId);
	public List<ELRateSlabs> getELRateSlabListByRateMasterId(int elrmid);
	public List<WTRateMaster> getWaterRateMaster(int waterTariffId);
	
	public List<Map<String, Object>> getWaterMinMaxDate(int wtTariffId,String wtRateName);
	public List<WTRateMaster> getWaterRateMasterByIdName(int wtTariffId,String wtRateName);
	public HashMap<String, Object> waterTariffCalculationMultiSlab(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public HashMap<String, Object> waterTariffCalculationSingleSlab(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	
	public List<GasRateMaster> getGasRateMaster(int gasTariffId);
	public List<Map<String, Object>> getGasMinMaxDate(int gasTariffId,String gasRateName);
	public List<GasRateMaster> getGasRateMasterByIdName(int gasTariffId,String gasRateName);
	public HashMap<Object, Object> gasTariffCalculationMultiSlab(GasRateMaster gasRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue);
	public HashMap<Object, Object> gasTariffCalculationSingleSlab(GasRateMaster gasRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue);
	
	public List<SolidWasteRateMaster> getSolidWasteRateMaster(int solidWasteTariffId);
	public List<Map<String, Object>> getSolidWasteMinMaxDate(int solidWasteTariffId, String solidWasteRateName);
	public List<SolidWasteRateMaster> getSolidWasteRateMasterByIdName(int solidWasteTariffId, String solidWasteRateName);
	public float solidWasteTariffCalculationMultiSlab(SolidWasteRateMaster solidWasteRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public float solidWasteTariffCalculationSingleSlab(SolidWasteRateMaster solidWasteRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public float solidWasteTariffCalculationRangeSlab(SolidWasteRateMaster solidWasteRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	
	public List<CommonServicesRateMaster> getCommonServicesRateMaster(int csTariffId);
	public List<Map<String, Object>> getCommonServiceMinMaxDate(int csTariffId,String csRateName);
	public List<CommonServicesRateMaster> getCommonServiceMasterByIdName(int csTariffId, String csRateName);
	public float commonServicesTariffCalculationMultiSlab(CommonServicesRateMaster commonServiceRateMaster1, Date date,Date date2, Float uomValue);
	public float commonServicesTariffCalculationSingleSlab(CommonServicesRateMaster commonServiceRateMaster1,Date previousBillDate, Date currentBillDate, Float uomValue);
	public String tariffCalculationMultiSlabString(ELRateMaster elRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public List<Object> todApplicable(String serviceType, Integer accountId);
	
	public void generateBill(GenericBillGeneration<Integer, String, Float, Date, Date, String, String, Float, Float, Float> billGeneration,
			ServiceMastersEntity mastersEntity,
			List<ServiceParametersEntity> serviceParametersEntities,
			ELRateMaster elRateMasterList);
	public List<?> findPropertyNo(int blockId);
	
	public List<Integer> findPersonDetail( int propertyId);
	public int findAccountDetail(int integer, int propid, String serviceTypeList);
	public int findNoOfPersonOfBlock(String propertyId);
	public Date getBillDate(int accountId, String serviceTypeList);
	public	Float getPreviousReading(int accountId, String serviceTypeList);
	public Date getPreviousddDate(int accountId, String serviceName);
	
	public String tariffCalculationMultiSlabStringWater(WTRateMaster wtRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public String tariffCalculationMultiSlabStringGas(GasRateMaster gasRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public List<?> getTowerNames();
	public List<Integer> getallBlocks();
	public List<Integer> getProertyId(int blocId);
	public String tariffCalculationMultiSlabStringAvgCount(ELRateMaster elRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount);
	public HashMap<Object, Object> tariffCalculationSingleSlabAvgCount(ELRateMaster elRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount);
	public HashMap<Object, Object> tariffCalculationMultiSlabAvgCount(ELRateMaster elRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount);
	public HashMap<Object, Object> tariffCalculationRangeSlabAvgCount(ELRateMaster elRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue, int avgCount);
	public float waterTariffCalculationMultiSlabAvgCount(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public float waterTariffCalculationSingleSlabAvgCount(WTRateMaster waterRateMaster1, Date previousBillDate,Date currentBillDate, Float uomValue);
	public Map<String,String> getPreviousStatusAlert(Integer accountId, String serviceType, Integer seviceId);
	
	public Map<Object, Object> getServiceStatusCheck(int accountId,String serviceType,int serviceId);
	public String getAccountMetered(int accountId, String serviceTypeList);
	public int getAccountIdOnAccountNo(String accountno);
	public String getMeterType(Integer accountId, String serviceType);
	public HashMap<Object, Object> mdiCalculation(ELRateMaster elRateMaster,Date previousBillDate, Date currentBillDate, Float uomValue,float extraParameter);
	public Map<String, Object> getBillDetails(ServiceMastersEntity serviceMastersEntities, Account account,String serviceType,Date strDate);
	public Map<Object, Object> getNewMeterDetails(String currentBillDate, String strDate,
			String typeOfService, int accountId, float previousReding,float presentreading);
	public Map<Object, Object> getDGNewMeterDetails(String currentBillDate, String strDate,
			String typeOfService, int accountId, float previousReding,float presentreading);
	public float claculateAvgforSimilarTypeofFlat(int accountId,
			Date currentBillDate, String typeOfService);
	public float claculateAvgforthreeYearData(int accountId,
			Date currentBillDate, String typeOfService);
	public Integer getservicIdByAccountIdServiceType(int accountId,String serviceType);
	public int findAccountDetailByServiceType(Integer personId,Integer propartyId, String serviceType);
	public Float getConsumptionUnitBasedOnSqFt(int propId, Float consumptionUnit);
	public String getPreviousBillStatus(int accountId, String serviceTypeList, Date getbilldate);
	public String getAccontNoONAccountId(int accountId);
	public float getMeterConstantForNewMeter(String pesentDate, String strDate,
			String typeOfService, int accountId, float previousReding,
			float presentReading);
	public Float getdistributionLossUnit(Date previousBillDate,
			int accountID);

	public List<Map<String, Object>> getTallyDetailData(int billId);

	public Map<Integer, Object> getAllUnits(int accountId, Date billdate,String typeOfService);
	public String getCamPreviousBillstatus();
	public String getCamPreviousBillstatusSpecific(Integer accountId);
	public List<SlabDetails> tariffCalculationMultiSlabDetails(
			ELRateMaster elRateMaster1, Date date, Date date2, Float uomValue);
	public List<Object> getAdvanceBillOnDate(int month, int year,
			String typeOfService, int accountid);
	public Map<Object, Object> getBatchBillDateOnBlockId(int blockId, String blockName, String serviceTypeList);
	public List<SlabDetails> tariffCalculationMultiSlabDetailsWater(
			WTRateMaster wtRateMaster1, Date date,
			Date date2, Float uomValue);
	public List<SlabDetails> tariffCalculationMultiSlabDetailsGas(
			GasRateMaster wtRateMaster1,Date previousBillDate,
			Date currentBillDate, Float uomValue);
	public List<Map<String, Object>> getMinMaxDate1(int elTariffID,String rateName, String category);
	public List<ELRateMaster> getRateMasterByIdName1(int elTariffID,
			String rateName, String category);
	public Integer getAccountIdOnPropertyId(int propId, String serviceTypeList);
	public Map<Object, Object> getPreviousBillReadingAmr(
			Integer accountIdOnProp, String serviceTypeList);
	public List<?> findPropertyNoAmr(int blockId);
	public List<Map<String, Object>> getallBlocksAmr();
	public String getpropertyNoOnPropertyId(int propid);
	public List<?> getAdvanceBillPreviousDate(String typeOfService,
			int accountId);
	public Float getNewMeterDgReading(int accountId);
	public List<Map<String, Object>> getLedgerDataOnAccountWise(int accountId,String strDate, String pesentDate);
	public List<?> getcheckbounceDetail(int accountID);
	public double getMisChargesDetail(int accountID,java.sql.Date billmonth);
	public List<?> getAllAccuntNumbersBasedOnProperty(int propertyId);
	
	/*//***************************************************************************************************************
		Methods for Webservices for PaytmApi
			public String getValidMeterDetails(int siteId,String meterNumber);
			public String postThePayment(String uniqueID,String paymentDate,String paymentTime,String siteId,String uniqueTxnId,String amountPayable) throws ParseException;
			public String paymentStatusCheck(String uniqueID, String txnid);
	//*************************************************************************************************************** 
*/			
			

}
