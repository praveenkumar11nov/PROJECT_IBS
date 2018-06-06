package com.bcits.bfm.service.gasTariffManagment;

import java.util.List;

import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.service.GenericService;

public interface GasTariffMasterService extends GenericService<GasTariffMaster>{

	public List<GasTariffMaster> findAllOnParentId(Integer gasTariffId, String status);
	public GasTariffMaster getNodeDetails(Integer nodeid);
	public List<GasTariffMaster> getTariffDetail(int parentId,String tariffname);
	public List<GasTariffMaster > getTariffNameBasedonTariffid(int wtTariffId);
	public List<GasTariffMaster> findAllOnParentIdwoStatus(Integer wtTariffId);
}
