package com.bcits.bfm.service.commonServicesTariffManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.CommonServicesTodRates;
import com.bcits.bfm.service.GenericService;

public interface CommonServiceTODRatesService extends GenericService<CommonServicesTodRates>
{
	List<CommonServicesTodRates> findALL(int csRsId);
	void updateslabStatus(int eltiId, HttpServletResponse response);
}
