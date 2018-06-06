package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.OldMeterHistoryEntity;

public interface PrepaidMeterHistoryService extends GenericService<OldMeterHistoryEntity>{

	List<?> findmeterHtryDetails();

}
