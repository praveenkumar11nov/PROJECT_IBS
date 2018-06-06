package com.bcits.bfm.service.broadTeleTariffManagment;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BroadTeleRateSlab;
import com.bcits.bfm.service.GenericService;

public interface BroadTeleRateSlabService extends GenericService<BroadTeleRateSlab>
{

	List<BroadTeleRateSlab> rateSlabListByParentID(int broadTeleRmid);
	Object setResponse(List<BroadTeleRateSlab> broadTeleRateSlabsList);
	void updateslabStatus(int broadTelersId, HttpServletResponse response);

}
