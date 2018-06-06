package com.bcits.bfm.service.solidWasteTariffManagment;

import java.util.List;

import com.bcits.bfm.model.SolidWasteTariffMaster;

import com.bcits.bfm.service.GenericService;

public interface SolidWasteTariffMasterService extends GenericService<SolidWasteTariffMaster>{
	public List<SolidWasteTariffMaster> findAllOnParentId(Integer wtTariffId, String status);
	public SolidWasteTariffMaster getNodeDetails(Integer nodeid);
	public List<SolidWasteTariffMaster> getTariffDetail(int parentId,String tariffname);
	public List<SolidWasteTariffMaster > getTariffNameBasedonTariffid(int wtTariffId);
	public List<SolidWasteTariffMaster> findAllOnParentIdwoStatus(Integer wtTariffId);
}
