package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.bcits.bfm.model.AssetMaintenanceSchedule;
import com.bcits.bfm.service.GenericService;

public interface AssetMaintenanceScheduleService extends GenericService<AssetMaintenanceSchedule> 
{
	List<Map<String, Object>> setResponse();

	void populateJobCalender(AssetMaintenanceSchedule assetMaintenanceSchedule,List<DateTime> dates, int i);

	void setAmsStatus(int amsId, String operation, HttpServletResponse response);
}

