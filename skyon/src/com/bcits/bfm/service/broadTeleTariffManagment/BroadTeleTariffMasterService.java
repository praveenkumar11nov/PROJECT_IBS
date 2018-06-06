package com.bcits.bfm.service.broadTeleTariffManagment;

import java.util.List;

import com.bcits.bfm.model.BroadTeleTariffMaster;
import com.bcits.bfm.service.GenericService;

public interface BroadTeleTariffMasterService extends GenericService<BroadTeleTariffMaster> {
	public List<BroadTeleTariffMaster> findAllOnParentId(Integer wtTariffId, String status);
	public BroadTeleTariffMaster getNodeDetails(Integer nodeid);
	public List<BroadTeleTariffMaster> getTariffDetail(int parentId,String tariffname);
	public List<BroadTeleTariffMaster > getTariffNameBasedonTariffid(int wtTariffId);
	public List<BroadTeleTariffMaster> findAllOnParentIdwoStatus(Integer wtTariffId);
}
