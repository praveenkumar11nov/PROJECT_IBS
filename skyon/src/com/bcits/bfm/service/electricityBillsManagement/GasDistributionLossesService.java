package com.bcits.bfm.service.electricityBillsManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.GasDistributionLosses;
import com.bcits.bfm.service.GenericService;

public interface GasDistributionLossesService extends  GenericService<GasDistributionLosses>
{

	List<Map<String, Object>> findAll();

	void setDistributionLossesStatus(int gdlid, String operation,HttpServletResponse response);

	Map<Object, Object> getDistributionLoss(
			int actualmonth, int year);

}
