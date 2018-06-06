package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

















//import com.bcits.bfm.model.VisitorParking;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.VisitorService;
import com.bcits.bfm.service.facilityManagement.VisitorVisitsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class VisitorVisitsServiceImpl extends GenericServiceImpl<VisitorVisits>
		implements VisitorVisitsService {
	
	private static final Log logger = LogFactory.getLog(VisitorVisitsServiceImpl.class);

	@Autowired
	private VisitorService visitorService;
	
	
	
	@Autowired
	private PropertyService propertyService;

	
	
	
	/** 
     * this method is used to read visitor records having status 'IN' from the database
     * @return         : returns the records of visitor's
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<VisitorVisits> findVisitorIn() {
		logger.info("finding all VisitorVisits instances");
		try {
			return entityManager.createNamedQuery("VisitorVistisDetails").setParameter("vvstatus","IN").getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	
	/** 
     * this method is used to read visitor details) from the database
     * @return         : returns all  records of visitor's
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<VisitorVisits> findAll() {
		logger.info("finding all VisitorVisits instances");
		try {
			return entityManager.createNamedQuery("VisitorVisits.findAll").getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	
	
	/** 
     * this method is used to read property number for filtering  from the database
     * @return         : returns property number for filter
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getPropertyNoForFilter(){
		try{
		return entityManager.createNamedQuery("FindPropertyNoForFilter").getResultList();
		}
		catch (Exception e) {
           e.printStackTrace();
           return null;
		}
	}
	
	
	
	public int count_visitorVisits(){
		
		return findVisitorIn().size();
	}
	
	
	
	
	/** 
     * this method is used to read visitor visit status based on Id from the database
     * @param	vmId	:vmId
     * @return          : returns visitor's status
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getVisitorVisitsStatusBasedvInDt(int vmId) {
		return entityManager.createNamedQuery("VisitorVisits.getVisitorStatusBasedOnLastUpdatedDt")
				.setParameter("vmId", vmId)
				.getResultList();
	}
	
	
	/** 
     * this method is used to read visitor details based on Visitor Id  from the database
     * @param	vmId	:vmId
     * @return          : returns the records of visitor
     * @since           1.0
     */
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<VisitorVisits> findVisitorVisitsDetailsBasedOnId(int vmId) {
		logger.info("finding all VisitorVisits instances");
		try {
			return entityManager.createNamedQuery("VisitorVistisDetailsbasedOnId").setParameter("vmId", vmId)
					.getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	
	
	/** 
     * this method is used to update visitor record based on Visitor Id in  grid and to store in database
     * @param	vmId	:vmId
     * @return          : returns the updated record  of visitor
     * @since           1.0
     */
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer updateVsisitorVisitStatusByButton(int vvId,String vvstatus,String vpStatus,Timestamp vvLastUpdatedDt,Timestamp voutDt,String reason){
		Date date=new Date();
		return entityManager.createNamedQuery("updateVisitorVisitsStatusButton").setParameter("vvId", vvId).setParameter("vvstatus", "OUT").setParameter("vpStatus", "Vacant").setParameter("voutDt",new Timestamp(date.getTime())).setParameter("vvLastUpdatedDt", new Timestamp(date.getTime())).setParameter("reason",reason).executeUpdate();
		
	}

	/** 
     *this method is used to update visitor record based on Visitor Id and to store in database
     * @param	vmId	:vmId
     * @param   vvId    :vvId
     * @return          : returns the records of visitor
     * @since           1.0
     */
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer updateVsisitorVisitBasaedOnSearch(int vvId,int vmId,String vvstatus,Timestamp voutDt,Timestamp lastupdatedDt,String lastUpdatedBy){
		Date date=new Date();
		return entityManager.createNamedQuery("updateVisitorVisitSearchRecord").setParameter("vvId", vvId).setParameter("vvLastUpdatedBy", lastUpdatedBy).setParameter("vvstatus", "OUT").setParameter("voutDt",new Timestamp(date.getTime())).setParameter("vvLastUpdatedDt", new Timestamp(date.getTime())).setParameter("vmId", vmId).executeUpdate();
		
	}
	
	
	/** 
     *this method is used to search  visitor record based on Visitor Id from the database
     * @param	vmId	:vmId
     * @return          : returns the records of visitor based on vmId
     * @since           1.0 
     */
	@Transactional(propagation=Propagation.SUPPORTS)
	public VisitorVisits getVisitorVisitRecordBasedOnId(int vmId){
		return (VisitorVisits) entityManager.createNamedQuery("VisitorVisitrecordSearchBasedOnId").setParameter("vmId", vmId).setMaxResults(1).getSingleResult();
		
	}
	
	//Search By Name
	@Transactional(propagation=Propagation.SUPPORTS)
	public VisitorVisits searchVisitorBasedOnName(String vmName){
		return (VisitorVisits) entityManager.createNamedQuery("SearchVisitorBasedOnVisitorName").setParameter("vmName", vmName).setMaxResults(1).getSingleResult();
		
	}
	
	//Search By PropertyNo
	@Transactional(propagation=Propagation.SUPPORTS)
	public VisitorVisits searchVisitorBasedOnPropertyNo(String propertyNo){
		return (VisitorVisits) entityManager.createNamedQuery("SearchVisitorBasedOnPropertyNo").setParameter("propertyNo", propertyNo).setMaxResults(1).getSingleResult();
		
	}
	
	/** 
     *this method is used to find visitor visit Id from database.
     *@param	vmId	:vmId
     * @return          : returns vvId
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer>  getVisitorVvIdForFilter(int vmId){	
		return entityManager.createNamedQuery("visitorvisit.vvId").setParameter("vmId", vmId).getResultList();
		
	}
	
	
	
	/** 
     *this method is used to find list of access card number from 'Access Card' table when 'Visitor Visits'  table is Empty.
     * @return          : returns the list of Access card
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getfindAccessCardVisitor(){
		
		List listAccessCard= entityManager.createNamedQuery("AccessCards.findAccessCard").getResultList();
		return getVisitorAccessCard_Available(listAccessCard);
	}
	
	@SuppressWarnings("rawtypes")
	private List getVisitorAccessCard_Available(List listAccessCard) {
		
		List<Map<String, Object>> visitorAccessCard_lists = new ArrayList<Map<String, Object>>();
		for (Iterator i = listAccessCard.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			Map<String, Object> visitorAccessCardMap = new HashMap<String, Object>();
			visitorAccessCardMap.put("acId", (Integer)values[0]);
			visitorAccessCardMap.put("acNo",(String)values[1]);
			visitorAccessCard_lists.add(visitorAccessCardMap);
		}
		return visitorAccessCard_lists;
	}
	
	
	/** 
     *this method is used to find list of access card number from 'Access Card' table when 'Visitor Visits'  table is Not Empty.
     * @return          : returns the list of Access card
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getfindAccessCardVisitorNotNull(){
		
		List listAccessCard= entityManager.createNamedQuery("AccessCards.findAccessCard_visitorVisitNotNull").getResultList();
		return getVisitorAccessCard_Null(listAccessCard);
	}
	
	
	@SuppressWarnings("rawtypes")
	private List getVisitorAccessCard_Null(List listAccessCard) {
		
		List<Map<String, Object>> visitorAccessCard_lists = new ArrayList<Map<String, Object>>();
		for (Iterator i = listAccessCard.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			Map<String, Object> visitorAccessCardMap = new HashMap<String, Object>();

			visitorAccessCardMap.put("acId", (Integer)values[0]);
			visitorAccessCardMap.put("acNo",(String)values[1]);
			visitorAccessCard_lists.add(visitorAccessCardMap);
		}
		return visitorAccessCard_lists;
	}

	
	/** 
     *this method is used to find list of  parking slots available for Visitor's from 'Parking Slot' table when 'Visitor Visits'  table is Empty.
     * @return          : returns the list of Access card
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getParkingSlot_Vparking_Null(){
		
		List listparkingslot= entityManager.createNamedQuery("VisitorVisits.getVisitorParkingSlot_VparkingisNull").setParameter("psOwnership", "VISITORS").setParameter("status", "true").getResultList();
		return getVisitorParkingSlot_VParking_Null(listparkingslot);
			}
	
	
	@SuppressWarnings("rawtypes")
	private List  getVisitorParkingSlot_VParking_Null(
			List listparkingslot) {
		List<Map<String, Object>> visitorparking_lists = new ArrayList<Map<String, Object>>();

		for (Iterator i = listparkingslot.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			Map<String, Object> visitorparkingMap = new HashMap<String, Object>();
			visitorparkingMap.put("psId", (Integer)values[0]);
			visitorparkingMap.put("psSlotNo",(String)values[1]);
			visitorparking_lists.add(visitorparkingMap);
		}
		return visitorparking_lists;
	}

	
	/** 
     *this method is used to find list of  parking slots available for Visitor's from 'Parking Slots' table.
     * @return          : returns the list of Access card
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getVisitorParkingSlotslist(){
		
		List listparkingslot= entityManager.createNamedQuery("VisitorVisits.getVisitorParkingSlot").getResultList();
		return getVisitorParkingSlot_Available(listparkingslot);
		
	}
	
	@SuppressWarnings("rawtypes")
	private List getVisitorParkingSlot_Available(List listparkingslot) {
		List<Map<String, Object>> visitorparking_lists = new ArrayList<Map<String, Object>>();
		for (Iterator i = listparkingslot.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();
			Map<String, Object> visitorparkingMap = new HashMap<String, Object>();
			visitorparkingMap.put("psId", (Integer)values[0]);
			visitorparkingMap.put("psSlotNo",(String)values[1]);
			visitorparking_lists.add(visitorparkingMap);
		}
		return visitorparking_lists;
	}
	
	
	/** 
     *this method is used to find list of  paring slots available for Visitor's from 'Parking Slots' table when 'Visitor Visits'  table is Not Empty.
     * @return          : returns the list of Access card
     * @since           1.0
     */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation=Propagation.SUPPORTS)
	public int findIdBasedOnParkingSlot(String psSlotNo){
		
		List<Integer> accessRepositoryId =  entityManager.createNamedQuery("ParkingSlots.findIdbasedOnSlotName",
				Integer.class)
				.setParameter("psSlotNo", psSlotNo)
				.getResultList();
		Iterator it=accessRepositoryId.iterator();
		while(it.hasNext()){
		return (int) it.next();
		}
		return 0;
	}
	
	
	/** 
     *this method is used to find list of  vehicle number for filtering from database
     * @return          : returns the list of Access card
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VisitorVisits>  getVisitorVehicleNoForFilter(){	
		return entityManager.createNamedQuery("VisitorVisits.VehicleNo").getResultList();
		
	}


	/*@Override
	public List<?> findVistors() {
		return get(entityManager.createNamedQuery("VisitorVistisDetails.readData").setParameter("vvstatus","IN").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List get(List<?> meterStatusEntities){
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> transactionMasterMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				transactionMasterMap = new HashMap<String, Object>();
				
				transactionMasterMap.put("vvId", (Integer)values[0]);
				transactionMasterMap.put("category", (String)values[1]);
				transactionMasterMap.put("vvstatus", (String)values[2]);
				transactionMasterMap.put("vpurpose", (String)values[3]);
				transactionMasterMap.put("vehicleNo", (String)values[4]);
				transactionMasterMap.put("reason", (String)values[5]);
				
				final String timeformat = "dd/MM/yyyy HH:mm";
				SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
				Timestamp intime =(Timestamp)values[6];
				if(intime!=null){
					transactionMasterMap.put("vinDt", sdf.format(values[6]));
				}
				else{
				}
				Timestamp outtime = (Timestamp)values[7];
				if(outtime!=null){
					transactionMasterMap.put("voutDt",sdf.format((Timestamp)values[7]));
				}
				else{
				}
				
				
				
				transactionMasterMap.put("vpExpectedHours", (Integer)values[8]);
				
				transactionMasterMap.put("vpStatus", (String)values[9]);
				
				transactionMasterMap.put("vvCreatedBy", (String)values[10]);
				transactionMasterMap.put("vvLastUpdatedBy", (String)values[11]);
				transactionMasterMap.put("vvLastUpdatedDt", ConvertDate.TimeStampString((Timestamp)values[12]));
				
				
				if((Integer)values[13]==null){
				}else{
				
				transactionMasterMap.put("acId", (Integer)values[13]);
				transactionMasterMap.put("acNo", (String)values[14]);
				}
				transactionMasterMap.put("blockId", (Integer)values[15]);
				transactionMasterMap.put("blockName", (String)values[16]);
				transactionMasterMap.put("property_No", (String)values[17]);
				
				
				transactionMasterMap.put("propertyId", (Integer)values[18]);
				if((Integer)values[19]==null){
				}else
				{
				
				transactionMasterMap.put("psId", (Integer)values[19]);
				transactionMasterMap.put("psSlotNo", (String)values[20]);
				}
				transactionMasterMap.put("vmId", (Integer)values[21]);
				transactionMasterMap.put("vmContactNo", (String)values[22]);
				transactionMasterMap.put("vmFrom", (String)values[23]);
				transactionMasterMap.put("vmName", (String)values[24]);
				transactionMasterMap.put("gender", (String)values[25]);
				
				transactionMasterMap.put("vmCreatedBy", (String)values[26]);
				transactionMasterMap.put("documentNameType", (String)values[27]);
				transactionMasterMap.put("vmLastUpdatedBy", (String)values[28]);
				transactionMasterMap.put("vmLastUpdatedDt", ConvertDate.TimeStampString((Timestamp)values[29]));
				
			result.add(transactionMasterMap);
	     }
      return result;
	}*/
}