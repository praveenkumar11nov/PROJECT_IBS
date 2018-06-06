package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.StaffAttendanceGateOutAlert;
import com.bcits.bfm.service.GenericService;

public interface StaffAttendanceGateOutAlertService extends GenericService<StaffAttendanceGateOutAlert> {
	
	public List<StaffAttendanceGateOutAlert> findAll();

	public List<PersonAccessCards> getPersonAccessCardsByAcid(int personId);

}
