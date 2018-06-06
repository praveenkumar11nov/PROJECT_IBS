package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.GenusMtrNoNotGetting;

public interface ConsumptionNotGettingMtrService extends GenericService<GenusMtrNoNotGetting>{

	List<?> getAllMeterBlankData(String readingdate);

}
