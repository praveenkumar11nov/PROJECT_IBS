package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Blob;
import java.sql.Timestamp;

import java.util.List;
import java.util.Map;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.Visitor;

import com.bcits.bfm.service.facilityManagement.VisitorService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class VisitorServiceImpl extends GenericServiceImpl<Visitor> implements
		VisitorService {
	private static final Log logger = LogFactory.getLog(VisitorServiceImpl.class);

	
	
	/** 
     *this method is used to find list Visitor master record(name,contact number,address) from database .
     * @return          : returns the list of visitor's record
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Visitor> getAllVisitorRecord() {
		logger.info("finding all VisitorMaster instances");
		try {
			return entityManager.createNamedQuery("allVisitorRecords")
					.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	
	
	/** 
     *this method is used to count the size of 'Visitor Master' table  .
     * @return          : returns the list of visitor's record
     * @since           1.0
     */
	public int count_VisitorMaster_count(){
		
		return getAllVisitorRecord().size();
	}

	
	/** 
     *this method is used to find list parking slot number  from database .
     * @return          : returns the list of parking slots
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkingSlots> getpsSlotNoAll(){
	return entityManager.createNamedQuery("ParkingSlot_No_AllSlots").setParameter("psOwnership", "VISITORS").setParameter("status", "true").getResultList();
	}
	
	
	/** 
     *this method is used to find list parking slot number  from database which are available.
     * @return          : returns the list of parking slots
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkingSlots> getSlotNo(){
		return entityManager.createNamedQuery("ParkingSlot_No").setParameter("psOwnership", "VISITORS").setParameter("status", "true").setParameter("vpStatus", "Required").getResultList();
	}
	
	/** 
     *this method is used to check the status of Visitor based on Id  .
     * @return          : returns status('IN'/'OUT')
     * @since           1.0
     */
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getvisitorRecord(int vmId){
		return  entityManager.createNamedQuery("Checkexistence",String.class).setParameter("vmId", vmId).getSingleResult();
		
		
		
		
	}
	
	/** 
     *this method is used to find visitor Id (vmId) based on Visitor Name and Contact Number  from database .
     * @return          : returns Visitor Id.
     * @since           1.0
     */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Visitor getVisitorObject(Map<String, Object> map, String type,
			Visitor visitor) {

		visitor = getVisitorBeanObject(map, type, visitor);
		return visitor;

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getVisitorId(String visitorname,String vmContactNo){
		
		return entityManager.createNamedQuery("Visitor.getId",
				Integer.class)
				.setParameter("vmName", visitorname).setParameter("vmContactNo", vmContactNo)
				.getResultList();
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer  VisitorId(String visitorname,String vmContactNo){
		
		return entityManager.createNamedQuery("Visitor.getId",Integer.class)
				.setParameter("vmName", visitorname).setParameter("vmContactNo", vmContactNo)
				.getFirstResult();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object> getAllRecord_join_list() {

		return entityManager.createNamedQuery("allRecord_join_list")
				.getResultList();

	}
		/** 
     *this method is used to read all the records of Visitor Master from database .
     * @return          : returns list of visitor record.
     * @since           1.0
     */
	public Visitor getVisitorBeanObject(Map<String, Object> map, String type,
			Visitor visitor) {
		String mobstatus=(String) map.get("mobstatus");
		String username;
		if(mobstatus!=null && mobstatus.equals("mobstatus")){
			 //SessionData.getUserDetails().set("userID");
			 username = (String) map.get("username");

		}
		else{
			 username = (String) SessionData.getUserDetails().get("userID");

		}
		//String username = (String) SessionData.getUserDetails().get("userID");
        // username="bcitsadmin";
		if (type.equals("update")) {
			logger.info("Inside update");
			visitor.setVmId(Integer.parseInt(map.get("vmId").toString()));
			logger.info("vmId is- "+map.get("vmId"));
			visitor.setVmLastUpdatedBy(username);
			visitor.setVmCreatedBy(username);

		}

		if (type.equals("save")) {
			visitor.setVmLastUpdatedBy(username);
		}

		visitor.setVmName((String) map.get("vmName"));
		logger.info("vmname is--"+map.get("vmName"));
		logger.info("vmFrom-"+map.get("vmFrom"));
		
		visitor.setVmFrom((String) map.get("vmFrom"));
		visitor.setGender((String)map.get("gender"));
		logger.info("vm from-"+map.get("vmFrom"));
		visitor.setVmContactNo((String) map.get("vmContactNo"));
		logger.info("contact no is--"+ map.get("vmContactNo"));
		visitor.setVmCreatedBy(username);
		java.util.Date date = new java.util.Date();
		logger.info(new Timestamp(date.getTime()));
		visitor.setVmLastUpdatedDt(new Timestamp(date.getTime()));
		
		
		
		
		return visitor;
	}


	/** 
     *this method is used to upload document of visitor based on vmId and store in  database .
     *@param  vmId		:vmId
     *@param  document  :document
     * @since           1.0
     */
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updateVistor(byte[] doc,int vmId){
		return entityManager.createNamedQuery("updateVisitorMaster").setParameter("document", doc).setParameter("vmId", vmId).executeUpdate();
		
	}


	/** 
     *this method is used to find visitor Id (vmId) based on 'created by' and 'last Updated by'   from database .
     *@param vmLastUpdatedDt:vmLastUpdatedDt
     *@param vmCreatedBy    :vmCreatedBy
     * @return              : returns Visitor Id.
     * @since               1.0
     */

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getVisitorIdBasedOnLastUpdateDt(Timestamp lastUpdateDt,
			String username) {
		return entityManager.createNamedQuery("Visitor.getVisitorIdBasedOnLastUpdatedDt", Integer.class)
				.setParameter("vmLastUpdatedDt", lastUpdateDt).setParameter("vmCreatedBy", username)
				.getSingleResult();
	}




	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateVisitorDocument(int vmId,Blob blob,String documentNameType) 
	{
		entityManager.createNamedQuery("Visitor.updateVisitorDocument")
		.setParameter("vmId", vmId).setParameter("document", blob).setParameter("documentNameType", documentNameType)
		.executeUpdate();
	}
	
	/** 
     *this method is used to update visitor address in 'Visitor Master'.
     * @since           1.0
     */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAddress(int vmId,String vmFrom){
		entityManager.createNamedQuery("Visitor.updateAddress").setParameter("vmFrom", vmFrom).setParameter("vmId", vmId).executeUpdate();
	}
	

	/** 
     *this method is used to upload Visitor image of visitor based on vmId and store in  database .
     *@param  vmId		:vmId
     *@param  blob      :blob
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateVisitorImage(int vmId,Blob blob) 
	{
		entityManager.createNamedQuery("Visitor.updateVisitorImage")
		.setParameter("vmId", vmId).setParameter("image", blob)
		.executeUpdate();
	}


	/** 
     *this method is used to read Visitor image  based on vmId from  database .
     *@param  vmId		:vmId
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getImage(int vmId) {
		return (Blob)entityManager.createNamedQuery("Visitor.getImage",Blob.class)
		.setParameter("vmId", vmId)
		.getSingleResult();
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getImageBasedOnVisitorName(String vmName) {
		return (Blob)entityManager.createNamedQuery("Visitor.getImageBasedOnVisitorName",Blob.class)
		.setParameter("vmName", vmName)
		.getSingleResult();
	}
	
	
	
	/** 
     *this method is used to find list of Visitor Name from database .
	 * @return          :list of Visitor Name
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Visitor>  getVisitorNameForFilter(){	
		return entityManager.createNamedQuery("Visitor.FilterVisitorName").getResultList();
		
	}
	   
	/** 
     *this method is used to find list of Visitor Contact Number from database .
	 * @return          :list of Visitor Contact Number
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Visitor>  getVisitorContactNoForFilter(){	
		return entityManager.createNamedQuery("Visitor.FilterVisitorContactNo").getResultList();
		
	}
	
	/** 
     *this method is used to find list of Visitor Address from database .
	 * @return          :list of Visitor Address
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Visitor>  getVisitorAddressForFilter(){	
		
		return entityManager.createNamedQuery("Visitor.FilterVisitorAddress").getResultList();
		
	}

	/** 
     *this method is used to find list of parking Id based on parking slot number from database .
	 * @return          :Id of parking slot number
     * @since           1.0
     */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getSlotId(String psSlotNo){		
		return entityManager.createNamedQuery("ParkingSlotId",Integer.class).setParameter("psSlotNo", psSlotNo).getResultList();
	}
	
	/** 
     *this method is used to find list of parking slot number from database .
	 * @return          :list of parking slot number
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ParkingSlots> getparkingSlotNoForFilter(){
		
		return entityManager.createNamedQuery("ParkingSlots.filterparkingSlotNo").getResultList();
	}
	
	
	
}