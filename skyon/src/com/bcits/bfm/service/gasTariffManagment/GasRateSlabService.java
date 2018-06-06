package com.bcits.bfm.service.gasTariffManagment;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.model.GasRateSlab;
import com.bcits.bfm.service.GenericService;

public interface GasRateSlabService extends GenericService<GasRateSlab>
{
	List<GasRateSlab> rateSlabListByParentID(int gasrmid);
	Object setResponse(List<GasRateSlab> gasRateSlabsList);
	void updateslabStatus(int gasrsId, HttpServletResponse response);
	GasRateSlab findRateSlabByPrimayID(int gasrsId);
	List<GasRateSlab> getElRateSlabGreaterThanUpdateSlabNoEq(int gasSlabNo,int gasrmId);
	GasRateMaster getStatusofGasRateMaster(int gasrmId);
	List<GasRateSlab> getGasRateSlabGreaterThanUpdateSlabNo(int gasSlabNo,int gasrmId);

}
