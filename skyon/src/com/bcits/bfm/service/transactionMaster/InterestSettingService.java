package com.bcits.bfm.service.transactionMaster;

import java.util.List;

import com.bcits.bfm.model.InterestSettingsEntity;
import com.bcits.bfm.service.GenericService;

public interface InterestSettingService extends GenericService<InterestSettingsEntity> {

	List<InterestSettingsEntity> findAll();

	List<InterestSettingsEntity> findAllData();

}
