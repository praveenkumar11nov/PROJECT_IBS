package com.bcits.bfm.service.solidWasteTariffManagment;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.SolidWasteRateMaster;
import com.bcits.bfm.model.SolidWasteRateSlab;
import com.bcits.bfm.service.GenericService;

public interface SolidWasteRateSlabService extends GenericService<SolidWasteRateSlab>
{

	List<SolidWasteRateSlab> rateSlabListByParentID(int solidWasteRmId);
	Object setResponse(List<SolidWasteRateSlab> solidWasteRateSlabsList);
	void updateslabStatus(int solidWastersId, HttpServletResponse response);
	SolidWasteRateSlab findRateSlabByPrimayID(int parseInt);
	List<SolidWasteRateSlab> getElRateSlabGreaterThanUpdateSlabNoEq(int solidWasteSlabNo, int solidWasteRsId);
	SolidWasteRateMaster getStatusofSolidWasteRateMaster(int solidWasteRmId);
	List<SolidWasteRateSlab> getSolidWasteRateSlabGreaterThanUpdateSlabNo(int solidWasteSlabNo, int solidWasteRmId);

}
