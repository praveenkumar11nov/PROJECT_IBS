package com.bcits.bfm.service.waterTariffManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.service.GenericService;

public interface WTRateMasterService  extends GenericService<WTRateMaster>
{
	List<WTRateMaster> findALL();
	List<WTRateMaster> findActive();
	List<Map<String, Object>> setResponse(List<WTRateMaster> wtRateMastersList);
	void setWaterRateMasterStatus(int wtrmid, String operation,HttpServletResponse response);
	WTRateMaster getStatusofWTRateMaster(int wtrmid);
	

}
