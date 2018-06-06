package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StaffTraining;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.StaffTrainingService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;


@Repository
public class StaffTrainingServiceImpl extends GenericServiceImpl<StaffTraining> implements StaffTrainingService
{

	@Autowired
	private PersonService personService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StaffTrainingServiceImpl.class);
	
	/** Read all the Staff Training record
	 * @see com.bcits.bfm.service.facilityManagement.StaffTrainingService#findAll()
	 * @returnList of Staff Training
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StaffTraining> findAll() {
		List<StaffTraining> stafftrain = entityManager.createNamedQuery("StaffTraining.findAll").getResultList();
		return stafftrain;
	}

	/** Read all Staff Training records on personId
	 * @param id 		Person Id
	 * @see com.bcits.bfm.service.facilityManagement.StaffTrainingService#findById(int)
	 * 
	 * @return List of Staff Training
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StaffTraining> findById(int id) {
		List<StaffTraining> stafftrain = entityManager.createNamedQuery("StaffTraining.findById").setParameter("personId", id).getResultList();
		return stafftrain;
	}

	/** Set All the field values to the object
	 *@param map				Staff Training information from the view
	 *@param staffTraining 		Staff Training object
	 * @see com.bcits.bfm.service.facilityManagement.StaffTrainingService#getStaffTrainObject(java.util.Map, java.lang.String, com.bcits.bfm.model.StaffTraining)
	 * @return Staff Training object
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public StaffTraining getStaffTrainObject(Map<String, Object> map,
			String type, StaffTraining staffTraining) {
		staffTraining = getStaffTrainBeanObject(map, type, staffTraining);
		return staffTraining;

	}

	/**
	 * @param map		Staff Training information
	 * @param type		Save or Update
	 * @param staffTraining	Staff Training Object
	 * @return Staff Training object
	 */
	public StaffTraining getStaffTrainBeanObject(Map<String, Object> map, String type,
			StaffTraining staffTraining) {
		String username = (String) SessionData.getUserDetails().get("userID");
		if (type.equals("update")) {
			staffTraining.setStId((Integer) map.get("stId"));
			staffTraining.setCreatedBy((String)map.get("createdBy"));
		}
		else if (type.equals("save")) {
			staffTraining.setCreatedBy(username);
		}
		staffTraining.setPtSlno((Integer)map.get("ptSlno"));
		staffTraining.setTrainedBy((String)map.get("trainedBy"));
		staffTraining.setTrainingName((String)map.get("trainingName"));
		if((String)map.get("fromDate")!=null && (String)map.get("toDate")!=null){
			Date d1 = dateTimeCalender.getDate1((String) map.get("fromDate"));
			Date d2 = dateTimeCalender.getDate1((String) map.get("toDate"));
			if(d1.compareTo(d2)<=0){
				staffTraining.setToDate(ConvertDate.formattedDate((String) map.get("toDate")));
				staffTraining.setFromDate(ConvertDate.formattedDate((String) map.get("fromDate")));
			}
			else{
				staffTraining.setToDate(null);
				staffTraining.setFromDate(null);
			}
		}
		staffTraining.setTrainingDesc((String)map.get("trainingDesc"));
		staffTraining.setCertificationAch((String)map.get("certificationAch"));
		staffTraining.setLastUpdatedBy(username);
		return staffTraining;
	}

	/** Update the staff training record with the training document
	 * @param stId 						Staff Training Id
	 * @param trainingDocument 			Document to upload
	 * @param trainingDocumentType 		Type of the document 
	 * @see com.bcits.bfm.service.facilityManagement.StaffTrainingService#uploadCertificateOnId(int, java.sql.Blob, java.lang.String)
	 * @return none
	 */
	@Override
	public void uploadCertificateOnId(int stId, Blob trainingDocument,String trainingDocumentType) {
		entityManager.createNamedQuery("StaffTraining.uploadCertificateOnId")
		.setParameter("stId", stId)
		.setParameter("trainingDocument", trainingDocument)
		.setParameter("trainingDocumentType", trainingDocumentType)
		.executeUpdate();
		
	}

	@Override
	public List<?> getUniqueTrainingName(String className, String attribute,String attribute1, int selectedRowId) {
		final StringBuffer queryString = new StringBuffer("SELECT bpm."+attribute+" FROM ");
		queryString.append(className+" bpm WHERE bpm."+attribute1+"=");
	    queryString.append("'"+selectedRowId+"'");
		
	    final Query query = this.entityManager.createQuery(queryString.toString());
	    return (List<?>)query.getResultList();	
	}

	
		
	

}
