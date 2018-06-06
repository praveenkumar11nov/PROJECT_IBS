package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.ClubHouseBooking;
import com.bcits.bfm.service.GenericService;

public interface ClubHouseBookingService extends GenericService<ClubHouseBooking>{

	List<?> findAll();

	List<ClubHouseBooking> findAllData();
	
	List<ClubHouseBooking> findServiceName(int sId);

	

}
