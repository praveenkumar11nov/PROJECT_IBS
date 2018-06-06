package com.bcits.bfm.service.tariffManagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ELTodRates;
import com.bcits.bfm.service.GenericService;

public interface ELTODRatesService extends  GenericService<ELTodRates> 
{
	List<ELTodRates> findALL(int elrsId);
	void updateslabStatus(int eltiId, HttpServletResponse response);
	Date getRateSlabs(int elrsId);
	Timestamp getmaxTimeStamp(int elrsId);
	int getMaxIncrementalRate(int elrsId);
}
