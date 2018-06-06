package com.bcits.bfm.service.commonServicesTariffManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.CommonServicesRateSlab;
import com.bcits.bfm.service.GenericService;

public interface CommonServiceRateSlabService extends GenericService<CommonServicesRateSlab>
{
	List<CommonServicesRateSlab> rateSlabListByParentID(int csRmId);
	Object setResponse(List<CommonServicesRateSlab> commonServiceRateSlabsList);
	void updateslabStatus(int csRsId, HttpServletResponse response);
	List<CommonServicesRateSlab> findRateSlabByID(int csRmId);
	CommonServicesRateSlab findRateSlabByPrimayID(int csrsId);

}
