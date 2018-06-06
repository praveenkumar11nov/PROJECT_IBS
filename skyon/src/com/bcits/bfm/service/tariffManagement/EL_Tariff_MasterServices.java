package com.bcits.bfm.service.tariffManagement;

import java.util.*;

import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.service.GenericService;
public interface EL_Tariff_MasterServices extends GenericService<ELTariffMaster>{

	public void saveListEL_Tariff_Master(List<ELTariffMaster> eltariff);
	
	public List<ELTariffMaster> findAllOnParentId(Integer EL_Tariff_Id, String status);
	
	public List<Integer> getTariffNameBasedonTariffid(int EL_Tariff_Id);
	
	public void remove(int EL_Tariff_Id);
	
	public List<ELTariffMaster> getTariffDetail(int ISLEAF_Id,String tariffname);
	
	public void updateList(List<ELTariffMaster> taraiffMaster);
	
	public void delete(List<ELTariffMaster> ELTariffMaster);

	public ELTariffMaster getNodeDetails(int nodeid);
	
	public List<String> getTariffName(String tariffname);
	
	public List<ELTariffMaster> findAllOnParentIdwotStatus(Integer EL_Tariff_Id);
	
	
	public ELTariffMaster getTariffObject(Map<String, Object>map,String type,ELTariffMaster tariffmaster,int EL_Tariff_Id);
	
}
