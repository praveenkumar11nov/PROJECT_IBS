package com.bcits.bfm.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BMSSettingsEntity;

public interface BMSSettingsService extends GenericService<BMSSettingsEntity> {

	List<?> readBMSSettingsData();

	void setBMSSettingsStatus(int bmsSettingsId, String operation,
			HttpServletResponse response);

	List<?> findAllFilter();


	List<?> findTrendLogIds();

	List<BMSSettingsEntity> getHydroPneumaticPumpId();

	List<BMSSettingsEntity> getVentillationId();

	List<?> readTrendLogNamesUniqe();

	List<?> readTrendLogIdsUniqe();

	List<?> getSeweragePlantIds();

	List<?> getFightFightingIds();

	List<?> getDGSetIds();

	List<?> getLiftElevatorIds();

	List<?> readTrendLogIdWithDeptDesig();
	
	
	List<?> getYearWisedetails();

	List<?> monthWisedetails();

	List<?> getAccountId(int blockId, Date month);

}
