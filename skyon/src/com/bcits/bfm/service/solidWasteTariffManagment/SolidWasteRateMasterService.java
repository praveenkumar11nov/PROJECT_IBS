package com.bcits.bfm.service.solidWasteTariffManagment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.service.GenericService;

public interface SolidWasteRateMasterService extends GenericService<SolidWasteRateMaster>
{

	List<SolidWasteRateMaster> findActive();
	List<Map<String, Object>> setResponse(List<SolidWasteRateMaster> solidWasteRateMastersList);
	List<SolidWasteRateMaster> findALL();
	void setSolidWasteRateMasterStatus(int solidWastermid, String operation,HttpServletResponse response);
	void solidWasteRateMasterEndDate(int solidWastermid,HttpServletResponse response);

}
