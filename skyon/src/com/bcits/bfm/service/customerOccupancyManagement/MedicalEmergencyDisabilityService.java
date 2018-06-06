package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.MedicalEmergencyDisability;
import com.bcits.bfm.service.GenericService;

public interface MedicalEmergencyDisabilityService extends GenericService<MedicalEmergencyDisability>
{

	public List<MedicalEmergencyDisability> findAllMedicalEmergencyDisabilityBasedOnPersonID(
			int personId);

	public Integer getMedicalEmergencyDisabilityIdBasedOnCreatedByAndLastUpdatedDt(
			String createdBy, Timestamp lastUpdatedDt);

	public MedicalEmergencyDisability getMedicalEmergencyDisabilityObject(
			Map<String, Object> map, String type,
			MedicalEmergencyDisability medicalEmergencyDisability);

}