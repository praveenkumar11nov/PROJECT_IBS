package com.bcits.bfm.service.commonServicesTariffManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.CommonServicesRateMaster;
import com.bcits.bfm.service.GenericService;

public interface CommonServicesRateMasterService extends GenericService<CommonServicesRateMaster> 
{

	List<CommonServicesRateMaster> findActive();
	List<?> setResponse(List<CommonServicesRateMaster> commonServicesRateMastersList);
	List<CommonServicesRateMaster> findALL();
	void commonServicesRateMasterEndDate(int csRmId,HttpServletResponse response);
	void setCommonServicesRateMasterStatus(int csRmId, String operation,HttpServletResponse response);
	CommonServicesRateMaster getTaxCommonServiceMaster(String rateName);


}
