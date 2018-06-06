package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StaffNotices;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.StaffNoticesService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;


@Repository
public class StaffNoticesServiceImpl extends GenericServiceImpl<StaffNotices> implements StaffNoticesService
{


	@Autowired
	private PersonService personService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StaffNoticesServiceImpl.class);
	
	
	/**
	 *  Read all the Staff notices
	 * @see com.bcits.bfm.service.facilityManagement.StaffNoticesService#findAll()
	 * @return List of staff notices
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StaffNotices> findAll() {
		List<StaffNotices> staffnot = entityManager.createNamedQuery("StaffNotices.findAll").getResultList();
		return staffnot;
	}

	/** set all the fields from view into the object
	 * @see com.bcits.bfm.service.facilityManagement.StaffNoticesService#getStaffNotObject(java.util.Map, java.lang.String, com.bcits.bfm.model.StaffNotices)
	 * @param map	staff notices form the view
	 * @param type	Save or Update
	 * @param staffNotice StaffNotices Object
	 * @return StaffNotices Object
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public StaffNotices getStaffNotObject(Map<String, Object> map,
			String type, StaffNotices staffNotice) {
		staffNotice = getStaffNotBeanObject(map, type, staffNotice);
		return staffNotice;
	}

	/**
	 * @param map 		Notices details from the view
	 * @param type		Save or Update
	 * @param staffNotices	StaffNotices Object
	 * @return			StaffNotices Object
	 */
	public StaffNotices getStaffNotBeanObject(Map<String, Object> map, String type,
			StaffNotices staffNotices) {

		String username = (String) SessionData.getUserDetails().get("userID");
		if (type.equals("update")) {
			staffNotices.setSnId((Integer) map.get("snId"));
			staffNotices.setCreatedBy((String)map.get("createdBy"));
		}
		else if (type.equals("save")) {
			staffNotices.setCreatedBy(username);
		}
		staffNotices.setNoticeType((String)map.get("noticeType"));
		staffNotices.setDescription((String)map.get("description"));
		staffNotices.setSnAction((String)map.get("snAction"));
		if((String)map.get("snDate")!=null && (String)map.get("snActionDate")!=null){

			Date d1 = dateTimeCalender.getDate1((String) map.get("snDate"));
			Date d2 = dateTimeCalender.getDate1((String) map.get("snActionDate"));
			if(d1.compareTo(d2)<=0)
			{
				staffNotices.setSnDate(ConvertDate.formattedDate((String) map.get("snDate")));
				staffNotices.setSnActionDate(ConvertDate.formattedDate((String) map.get("snActionDate")));
			}
			else{
				staffNotices.setSnDate(null);
				staffNotices.setSnActionDate(null);
			}
		}
		staffNotices.setLastUpdateBy(username);
		java.util.Date date= new java.util.Date();
		
		staffNotices.setLastUpdatedDate(new Timestamp(date.getTime()));
		return staffNotices;
	}


	/** Read All the Notices on personId
	 * @see com.bcits.bfm.service.facilityManagement.StaffNoticesService#findById(int)
	 * @param personId Person Id
	 * @return LIst of Notices
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StaffNotices> findById(int personId) 
	{
		@SuppressWarnings("unchecked")
		List<StaffNotices> staffNotice= entityManager.createNamedQuery("StaffNotices.findById").setParameter("personId", personId).getResultList();
		return staffNotice;
		
	}

	/** Get count of records in notices on personid
	 * @param personId Person Id
	 * @see com.bcits.bfm.service.facilityManagement.StaffNoticesService#getCountOfNotices(int)
	 * @return List of Objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getCountOfNotices(int personId) {
		return entityManager.createNamedQuery("StaffNotices.getCountOfNotices").setParameter("personId", personId).getResultList();
	}

	/** Update the notices document on notice id
	 * @param snId 						Staff notice id
	 * @param noticeDocument 			document to upload
	 * @param noticeDocumentType 		Type of ducument
	 * @see com.bcits.bfm.service.facilityManagement.StaffNoticesService#uploadNoticeOnId(int, java.sql.Blob, java.lang.String)
	 * @return null
	 */
	@Override
	public void uploadNoticeOnId(int snId,Blob noticeDocument,String noticeDocumentType) {
		entityManager.createNamedQuery("StaffNotices.uploadNoticeOnId")
				.setParameter("snId", snId)
				.setParameter("noticeDocument", noticeDocument)
				.setParameter("noticeDocumentType", noticeDocumentType)
				.executeUpdate();
	}

}
