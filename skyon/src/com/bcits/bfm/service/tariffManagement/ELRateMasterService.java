package com.bcits.bfm.service.tariffManagement;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ELRateMaster;
import com.bcits.bfm.service.GenericService;

public interface ELRateMasterService extends  GenericService<ELRateMaster>
{
	List<?> findALL1();
	List<?> findALL();
	void setRateMasterStatus(int elrmid, String operation, HttpServletResponse response);
	Set<String> getRateDescriptionList();
	List<String> getRateTypeList();
	Set<String> getRateRateUOMList();
	ELRateMaster getStatusofELRateMaster(int elRateMasterId);
	java.util.Date getMaxDate(String stateName,String rateName, int elTariffID,String category);
	void elRateMasterEndDateUpdate(int rateMasterId,HttpServletResponse response);
	String getTariffMasterByTariffId(int elTariffID);
	void UpdateDgUnit(Integer operationalCost, Integer depriciationCost,
			Integer fuleCost, Integer maintainanceCost/*, Date validTo, Date validfrom*/, Integer unitConsumed);
	ELRateMaster getRateMasterByRateName(int tariffId, String string);
	List<ELRateMaster> findDGUnits();
	Integer getRateMasterEC(int elTariffID, String string);
	int getRateMasterDomesticGeneral(int wtTariffId, String string);
	int getRateMasterGas(int gasTariffId, String string);
	int getRateMasterSW(int solidWasteTariffId, String string);
	int getRateMasterOT(int othersTariffId, String string);
	ELRateMaster getRateMasterByRateName(int elTariffID, String string,String category);

}
