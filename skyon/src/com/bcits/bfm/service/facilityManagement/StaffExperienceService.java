package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.StaffExperience;
import com.bcits.bfm.service.GenericService;

public interface StaffExperienceService extends GenericService<StaffExperience> 
{
	public List<StaffExperience> findAll();
	public List<StaffExperience> findById(int id);
	public StaffExperience getStaffExpObject(Map<String, Object> map,
			String type, StaffExperience staffExpirence);
}

