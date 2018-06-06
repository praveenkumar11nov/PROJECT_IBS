package com.bcits.bfm.service.commonAreaMaintenance;

import java.util.List;

import com.bcits.bfm.model.CamReportSettingsEntity;
import com.bcits.bfm.service.GenericService;

public interface CamReportSettingsService extends GenericService<CamReportSettingsEntity>{

	List<CamReportSettingsEntity> findAll();

	List<CamReportSettingsEntity> findAllData();

}
