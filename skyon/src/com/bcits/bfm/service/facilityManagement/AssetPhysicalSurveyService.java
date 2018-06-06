package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AssetPhysicalSurvey;
import com.bcits.bfm.service.GenericService;

public interface AssetPhysicalSurveyService extends GenericService<AssetPhysicalSurvey> 
{

	List<?> setResponse();

	void setPhysicalSurveyStatus(int apId, String operation,
			HttpServletResponse response);

}

