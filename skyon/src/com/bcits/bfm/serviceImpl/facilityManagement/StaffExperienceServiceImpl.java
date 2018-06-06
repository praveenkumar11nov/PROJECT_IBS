package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StaffExperience;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.StaffExperienceService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class StaffExperienceServiceImpl extends
		GenericServiceImpl<StaffExperience> implements StaffExperienceService {

	@Autowired
	private PersonService personService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StaffExperienceServiceImpl.class);
	
	

	/** Read all the staff Experience records
	 * @see com.bcits.bfm.service.facilityManagement.StaffExperienceService#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StaffExperience> findAll() {
		List<StaffExperience> staffexp = entityManager.createNamedQuery(
				"StaffExperience.findAll").getResultList();
		return staffexp;
	}

	/** read all the experience records on personId
	 * @param id 	personId
	 * @see com.bcits.bfm.service.facilityManagement.StaffExperienceService#findById(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StaffExperience> findById(int id) {
		List<StaffExperience> staffexp = entityManager.createNamedQuery("StaffExperience.findById").setParameter("personId", id).getResultList();
		return staffexp;
	}

	/** Method to set the incoming information into the object
	 * @see com.bcits.bfm.service.facilityManagement.StaffExperienceService#getStaffExpObject(java.util.Map, java.lang.String, com.bcits.bfm.model.StaffExperience)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public StaffExperience getStaffExpObject(Map<String, Object> map,
			String type, StaffExperience staffExpirence) {
		staffExpirence = getStaffExpBeanObject(map, type, staffExpirence);
		return staffExpirence;
	}

	/**
	 * @param map   		Staff Experience information from the view
	 * @param type			Save or Update
	 * @param staffExperience	Staff Experience Object
	 * @return 	Staff Experience Object
	 */
	public StaffExperience getStaffExpBeanObject(Map<String, Object> map,
			String type, StaffExperience staffExperience) {

		String username = (String) SessionData.getUserDetails().get("userID");
		if (type.equals("update")) {
			staffExperience.setSeId((Integer) map.get("seId"));
			staffExperience.setCreatedBy((String) map.get("createdBy"));
		} else if (type.equals("save")) {
			staffExperience.setCreatedBy(username);
		}
		staffExperience.setCompany((String) map.get("company"));
		staffExperience.setDesignation((String) map.get("designation"));
		if ((String) map.get("startDate") != null
				&& (String) map.get("endDate") != null) {

			Date d1 = dateTimeCalender.getDate1((String) map.get("startDate"));
			Date d2 = dateTimeCalender.getDate1((String) map.get("endDate"));
			if (d1.compareTo(d2) <= 0) {
				
				staffExperience.setEndDate(ConvertDate.formattedDate((String) map.get("endDate")));
				staffExperience.setStartDate(ConvertDate.formattedDate((String) map.get("startDate")));
			} else {
				staffExperience.setEndDate(null);
				staffExperience.setStartDate(null);
			}
		}
		staffExperience.setPwSlno((Integer) map.get("pwSlno"));
		staffExperience.setWorkDesc((String) map.get("workDesc"));
		staffExperience.setLastUpdatedBy(username);
		return staffExperience;
	}
}
