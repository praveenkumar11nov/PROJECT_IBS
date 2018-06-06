package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.PatrolTrackAlert;
import com.bcits.bfm.model.PatrolTrackPoints;
import com.bcits.bfm.service.GenericService;

public interface PatrolTrackAlertService extends GenericService<PatrolTrackAlert> {
	
	public List<PatrolTrackAlert> findAll();
	
	public List<PatrolTrackAlert> findAllBasedOnPtId(int ptId);

}
