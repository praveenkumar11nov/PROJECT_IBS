package com.bcits.bfm.service.serviceMasterManagement;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.service.GenericService;

public interface ServiceMasterService extends  GenericService<ServiceMastersEntity> {
	
	List<ServiceMastersEntity> findALL();
	void setServiceMasterStatus(int serviceMasterId, String operation, HttpServletResponse response);
	List<Account> findAccountNumbers();
	void serviceEndDateUpdate(int serviceMasterId, HttpServletResponse response);
	Set<?> findPersons();
	List<?> findPersonBasedOnAccountIdForFilters();
	
	List<?> getElectricityTariffList(String serviceType, String todtype);
	List<?> getGasTariffList();
	List<?> getWaterTariffList();
	List<?> getSolidWasteTariffList();
	List<?> getOthersTariffList();
	List<?> getBroadBandTariffList();

	int getServiceMasterByAccountNumber(int accountID,String typeOfService);
	int getWaterTariffId(int accountID, String typeOfService);
	List<Integer> getWaterTariffIdList(int accountID, String typeOfService);
	int getGasTariffId(int accountID, String typeOfService);
	int getSolidWasteTariffId(int accountID, String typeOfService);
	int getCommonServicesTariffId(int accountID, String typeOfService);
	ServiceMastersEntity getServiceMasterServicType(Integer accoundId,String typeOfService);
	List<?> allTariffList();
	List<Integer> getServiceParameterBasedOnMasterId(int serviceID);
	List<?> serviceParameterList(String serviceType);
	List<String> getServiceParameterValueBasedOnMasterId(int serviceID);
	
	

}
