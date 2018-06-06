package com.bcits.bfm.service.waterTariffManagement;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.bcits.bfm.model.WTRateMaster;
import com.bcits.bfm.model.WTRateSlabs;
import com.bcits.bfm.service.GenericService;

public interface WTRateSlabService extends GenericService<WTRateSlabs>
{
	List<WTRateSlabs> rateSlabListByParentID(int wtrmid);
	List<Map<String, Object>> setResponse(List<WTRateSlabs> wtRateSlabsList);
	WTRateMaster getWTRateMaster(int wtrmid);
	void updateslabStatus(int wtrsId, HttpServletResponse response);
	WTRateSlabs findRateSlabByPrimayID(int wtrsId);
	List<WTRateSlabs> getElRateSlabGreaterThanUpdateSlabNoEq(int wtSlabNo,int wtrmid);
	List<WTRateSlabs> getWTRateSlabGreaterThanUpdateSlabNo(int wtSlabNo,int wtrmid);
}
