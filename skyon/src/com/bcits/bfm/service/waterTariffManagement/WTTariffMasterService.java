package com.bcits.bfm.service.waterTariffManagement;

import java.util.List;

import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.GenericService;

public interface WTTariffMasterService extends GenericService<WTTariffMaster>
{
	public List<WTTariffMaster> findAllOnParentId(Integer wtTariffId, String status);
	public WTTariffMaster getNodeDetails(Integer nodeid);
	public List<WTTariffMaster> getTariffDetail(int parentId,String tariffname);
	public List<WTTariffMaster > getTariffNameBasedonTariffid(int wtTariffId);
	public List<WTTariffMaster> findAllOnParentIdwoStatus(Integer wtTariffId);
	
}