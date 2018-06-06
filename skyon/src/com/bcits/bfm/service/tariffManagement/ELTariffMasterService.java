package com.bcits.bfm.service.tariffManagement;

import java.util.List;

import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.service.GenericService;

public interface ELTariffMasterService extends  GenericService<ELTariffMaster>
{
	List<ELTariffMaster> getTariffMasterByType(String string);
	List<ELTariffMaster> findALL();
	String getTariffName(int elTariffID);
	List<?> getAllStates();
	List<String> getStateName();
	List<?> getAllTariffMasters();
	List<ELTariffMaster> getAllTariffNodes(String stateName);
/*	String getStateName(int tariffId);
	int getTariffIdByName(String category1Tariff);*/
	String getStateName(int elTariffID);
}
