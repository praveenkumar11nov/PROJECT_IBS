package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.MedicalEmergencyDisability;
import com.bcits.bfm.service.customerOccupancyManagement.MedicalEmergencyDisabilityService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class MedicalEmergencyDisabilityServiceImpl extends GenericServiceImpl<MedicalEmergencyDisability> implements MedicalEmergencyDisabilityService
{
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.MedicalEmergencyDisabilityService#findAllMedicalEmergencyDisabilityBasedOnPersonID(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MedicalEmergencyDisability> findAllMedicalEmergencyDisabilityBasedOnPersonID(int personId) {
		return entityManager.createNamedQuery("MedicalEmergencyDisability.findAllMedicalEmergencyDisabilityBasedOnPersonID").setParameter("personId", personId).getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.MedicalEmergencyDisabilityService#getMedicalEmergencyDisabilityIdBasedOnCreatedByAndLastUpdatedDt(java.lang.String, java.sql.Timestamp)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getMedicalEmergencyDisabilityIdBasedOnCreatedByAndLastUpdatedDt(String createdBy,
			Timestamp lastUpdatedDt) {
		return entityManager.createNamedQuery("MedicalEmergencyDisability.getMedicalEmergencyDisabilityIdBasedOnCreatedByAndLastUpdatedDt", Integer.class).setParameter("createdBy", createdBy).setParameter("lastUpdatedDt", lastUpdatedDt).getSingleResult();		
	}
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.MedicalEmergencyDisabilityService#getMedicalEmergencyDisabilityObject(java.util.Map, java.lang.String, com.bcits.bfm.model.MedicalEmergencyDisability)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public MedicalEmergencyDisability getMedicalEmergencyDisabilityObject(Map<String, Object> map, String type,
			MedicalEmergencyDisability medicalEmergencyDisability) {
		
		if(type == "update")
		{
			
			medicalEmergencyDisability.setMeId((Integer) map.get("meId"));
			medicalEmergencyDisability.setPersonId((Integer) map.get("personId"));
			medicalEmergencyDisability.setCreatedBy((String) map.get("createdBy"));
		}
		else if (type == "save")
		{
			medicalEmergencyDisability.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		
		medicalEmergencyDisability.setDescription((String) map.get("description"));
		medicalEmergencyDisability.setDisabilityType((String) map.get("disabilityType"));
		medicalEmergencyDisability.setMeCategory((String) map.get("meCategory"));
		
		medicalEmergencyDisability.setMeHospitalName((String) map.get("meHospitalName"));
		medicalEmergencyDisability.setMeHospitalContact((String) map.get("meHospitalContact"));
		medicalEmergencyDisability.setMeHospitalAddress((String) map.get("meHospitalAddress"));
				
		medicalEmergencyDisability.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		medicalEmergencyDisability.setLastUpdatedDt(new Timestamp(new Date().getTime()));

		return medicalEmergencyDisability;

	}
}
