package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.ClubHouse;
import com.bcits.bfm.service.GenericService;

public interface ClubHouseService extends GenericService<ClubHouse> {

	List<ClubHouse> findAllData();
	public Object findServiceName(int sId);

}
