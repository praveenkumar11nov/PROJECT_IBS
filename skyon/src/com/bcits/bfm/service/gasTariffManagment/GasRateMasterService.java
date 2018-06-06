package com.bcits.bfm.service.gasTariffManagment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.service.GenericService;

public interface GasRateMasterService extends GenericService<GasRateMaster>
{

	List<GasRateMaster> findActive();
	List<Map<String, Object>> setResponse(List<GasRateMaster> gasRateMastersList);
	List<GasRateMaster> findALL();
	void setGasRateMasterStatus(int gasrmid, String operation,HttpServletResponse response);
	void gasRateMasterEndDate(int gasrmid, HttpServletResponse response);

}
