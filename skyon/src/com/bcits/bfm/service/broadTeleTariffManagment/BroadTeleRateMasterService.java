package com.bcits.bfm.service.broadTeleTariffManagment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BroadTeleRateMaster;
import com.bcits.bfm.service.GenericService;

public interface BroadTeleRateMasterService extends GenericService<BroadTeleRateMaster>
{
	List<BroadTeleRateMaster> findActive();
	List<Map<String, Object>> setResponse(List<BroadTeleRateMaster> broadTeleRateMastersList);
	List<BroadTeleRateMaster> findALL();
	void setBroadTeleRateMasterStatus(int broadTeleRmid, String operation,HttpServletResponse response);
	void broadTeleRateMasterEndDate(int broadTeleRmid,HttpServletResponse response);
}
