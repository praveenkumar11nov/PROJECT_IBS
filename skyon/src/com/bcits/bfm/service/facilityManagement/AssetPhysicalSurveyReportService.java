package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.AssetPhysicalSurveyReport;
import com.bcits.bfm.service.GenericService;

public interface AssetPhysicalSurveyReportService extends GenericService<AssetPhysicalSurveyReport> 
{

	List<?> setResponse(int apmsId);

	List<AssetPhysicalSurveyReport> checkChildForPhySurvey(int apsmId);

}

