package com.bcits.bfm.service.commonServicesTariffManagement;

import java.util.List;

import com.bcits.bfm.model.CommonTariffMaster;

import com.bcits.bfm.service.GenericService;

public interface CommonServiceTariffMasterServices extends GenericService<CommonTariffMaster> {

	public List<CommonTariffMaster> findAllOnParentId(Integer wtTariffId, String status);
	public CommonTariffMaster getNodeDetails(Integer nodeid);
	public List<CommonTariffMaster> getTariffDetail(int parentId,String tariffname);
	public List<CommonTariffMaster > getTariffNameBasedonTariffid(int wtTariffId);
	public List<CommonTariffMaster> findAllOnParentIdwoStatus(Integer wtTariffId);
}
