/*package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.controller.VisitorManagementController;
import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.Visitor;
import com.bcits.bfm.model.VisitorParking;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.facilityManagement.VisitorParkingService;
import com.bcits.bfm.service.facilityManagement.VisitorService;
import com.bcits.bfm.service.facilityManagement.VisitorVisitsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.SessionData;

@Repository
public class VisitorParkingServiceImpl extends
		GenericServiceImpl<VisitorParking> implements VisitorParkingService {
	
	
	
	private static final Log logger = LogFactory
			.getLog(VisitorParkingServiceImpl.class);

	@Autowired
	private VisitorVisitsService visitorVisitsService;

	
	@Autowired
private VisitorService  visitorService;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<VisitorParking> findAllparking() {
		logger.info("finding all VisitorParking instances");
		try {

			return entityManager.createNamedQuery("VisitorParkingRecords").getResultList();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	
	public int count_VisitorParking(){
		return findAllparking().size();
		
		
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer updateVisitorParking(int vpId,int vmId,
			String vmContactNo,int psId,String status,int vpExpectedHours,String createdBy,String lastUpdatedBy,Timestamp lastUpdatedDt){
				
		
		return entityManager.createNamedQuery("updatevisitorparking").setParameter("vpId", vpId).setParameter("vmId", vmId).setParameter("vmContactNo", vmContactNo).setParameter("vpExpectedHours", vpExpectedHours).
				setParameter("status", status).setParameter("psId", psId).setParameter("createdBy", createdBy).setParameter("lastUpdatedBy", lastUpdatedBy).setParameter("lastUpdatedDt", lastUpdatedDt).executeUpdate();

		
		
		
	}
	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getParkingSlotForFilter(){
		try{
		return entityManager.createNamedQuery("ParkingSlotsForFilter").getResultList();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
		}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public VisitorParking getVisitorParkingObject(Map<String, Object> map,
			String type, VisitorParking visitorparking) {
		return getVisitorParkingBeanObject(map, type, visitorparking);
	}

	private VisitorParking getVisitorParkingBeanObject(Map<String, Object> map,
			String type, VisitorParking visitorparking) {
		// TODO Auto-generated method stub
int id=0;
		String username = (String) SessionData.getUserDetails().get("userID");

		if (type.equals("save")) {

			--------------------------visitor------------------------------------
logger.info("i am in wizard save &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			Visitor visitor = new Visitor();

			visitor.setCreatedBy(username);

			visitor.setLastUpdatedBy(username);

			visitor.setVmContactNo((String) map.get("vmContactNo"));

			visitor.setVmFrom((String) map.get("vmFrom"));

			visitor.setVmName((String) map.get("vmName"));
			
		String name=(String) map.get("vmContactNo");
		String contactNo=	(String) map.get("vmContactNo");

			
			
			List<Integer> visitorId = visitorService.getVisitorId( name,contactNo);
			
			logger.info(">>>"+visitor.getLastUpdateDt());
			
			
			Integer vmId = visitorService.getVisitorIdBasedOnLastUpdateDt(visitor.getLastUpdateDt(),username);
			
			logger.info("VmId>>>>>>>>>>>>"+vmId);
			
			
		

			id = visitorId.get(0);
			logger.info("vm name  id is======================" + id);

			

			--------------------------visitorvisits------------------------------------
			VisitorVisits visitorVisits = new VisitorVisits();

			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			visitorVisits.setVmId(id);
//visitorVisits.setVmContactNo((String) map.get("vmContactNo"));
			visitorVisits.setCreatedBy(username);
			visitorVisits.setLastUpdatedBy(username);
			visitorVisits.setAcId((Integer) map.get("acId"));

			logger.info("-------------access id is----------"
					+ map.get("acId"));
			visitorVisits.setPropertyId((Integer) map.get("propertyId"));
			logger.info("--------------property id is--------------"
					+ map.get("propertyId"));
			visitorVisits.setVPurpose((String) map.get("vpurpose"));
visitorVisits.setVvstatus((String)map.get("vvstatus"));



//java.util.Date date = new java.util.Date();
logger.info(new Timestamp(date.getTime()));
logger.info("*************************date is-----------------"
		+ new Timestamp(date.getTime()));
visitorVisits.setVInDt(new Timestamp(date.getTime()));
logger.info("name is-----------------" + name);
		//	visitorVisits.setVisitor(visitor);
//visitorVisitsService.save(visitorVisits);
			

visitorparking.setVmId(id);
visitorparking.setVmContactNo((String) map.get("vmContactNo"));
visitorparking.setVisitor(visitor);
visitorparking.setCreatedBy(username);
visitorparking.setLastUpdatedBy(username);
//visitorparking.setVpStatus((String) map.get("status"));

//visitorparking.setVpExpectedHours((Integer) map
	//	.get("vpExpectedHours"));
//visitorparkingservice.save(visitorParking);


			 ************************************************************************************** 

			--------------------------visitorvisits------------------------------------

			VisitorVisits visitorVisits = new VisitorVisits();

			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			visitorVisits.setCreatedBy(username);
			visitorVisits.setLastUpdatedBy(username);
			visitorVisits.setAcId((Integer) map.get("acId"));

			logger.info("-------------access id is----------"
					+ map.get("acId"));
			visitorVisits.setPropertyId((Integer) map.get("propertyId"));
			logger.info("--------------property id is--------------"
					+ map.get("propertyId"));
			visitorVisits.setVPurpose((String) map.get("vpurpose"));

			visitorVisits.setVisitor(visitor);

			 ----------visitorParking--------------------- 

			//visitorparking.setVisitorVisits(visitorVisits);
			
			visitorparking.setVisitor(visitor);
			visitorparking.setCreatedBy(username);
			visitorparking.setLastUpdatedBy(username);
			//visitorparking.setVpStatus((String) map.get("status"));

			//visitorparking.setVpExpectedHours((Integer) map
					//.get("vpExpectedHours"));

		} else if (type.equals("update")) {

			long yourmilliseconds = 1390377389;
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

			Date resultdate = new Date(yourmilliseconds);
			logger.info("^^^^^^^^" + sdf.format(resultdate));

			--------------------------visitor------------------------------------
			Visitor visitor = new Visitor();
			logger.info("----------vmid is-----" + map.get("vmId"));

			visitor.setVmId((Integer) map.get("vmId"));

			visitor.setCreatedBy(username);

			visitor.setLastUpdatedBy(username);

			visitor.setVmContactNo((String) map.get("vmContactNo"));

			visitor.setVmFrom((String) map.get("vmFrom"));

			visitor.setVmName((String) map.get("vmName"));

			--------------------------visitorvisits------------------------------------
			VisitorVisits visitorVisits = new VisitorVisits();

			visitorVisits.setVvId((Integer) map.get("vvId"));
			visitorVisits.setCreatedBy(username);
			visitorVisits.setLastUpdatedBy(username);
			visitorVisits.setAcId((Integer) map.get("acId"));

			logger.info("-------------access id is----------"
					+ map.get("acId"));
			visitorVisits.setPropertyId((Integer) map.get("propertyId"));
			logger.info("--------------property id is--------------"
					+ map.get("propertyId"));
			visitorVisits.setVPurpose((String) map.get("vpurpose"));
			// visitorVisits.setVInDt((String)map.get("vinDt"));

			// visitorVisits.setVOutDt((String)map.get("voutDt"));
			visitorVisits.setVisitor(visitor);
			 ----------visitorParking--------------------- 
			//visitorparking.setVisitorVisits(visitorVisits);
			//visitorparking.setVpId((Integer) map.get("vpId"));
			visitorparking.setCreatedBy(username);
			visitorparking.setLastUpdatedBy(username);
			//visitorparking.setVpStatus((String) map.get("status"));

			//visitorparking.setVpExpectedHours((Integer) map
					//.get("vpExpectedHours"));

		}

		return visitorparking;
	}

	------------------------------------read vistor parking---------------------------

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getVisitorParkingfields() {

		List visitorParkinglist = entityManager.createNamedQuery(
				"visitorparking_vVisit_visitor").getResultList();
		return getVisitorParkingList(visitorParkinglist);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List getVisitorParkingList(List visitorParkinglist) {

		Map<Integer, VisitorParking> visitorParkingMap = new HashMap<Integer, VisitorParking>();

		for (Iterator i = visitorParkinglist.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			if (!(visitorParkingMap.containsKey((Integer) values[0]))) {

				VisitorParking visitorParking = new VisitorParking();

				//visitorParking.setVpId((Integer) values[0]);
				//visitorParking.setVvId((Integer) values[1]);
				//visitorParking.setVpExpectedHours((Integer) values[1]);
				//visitorParking.setVpStatus((String) values[2]);
			//	visitorParking.setParking_slot_No((String) values[3]);
				ParkingSlots parkingSlots =new ParkingSlots();
				parkingSlots.setPsId((Integer)values[3]);
				parkingSlots.setPsSlotNo((String)values[4]);

				

				VisitorVisits visitorVisits = new VisitorVisits();
				visitorVisits.setVvId((Integer) values[4]);
				Visitor visitor = new Visitor();
				visitor.setVmId((Integer) values[5]);
				visitor.setVmName((String) values[6]);
				visitor.setVmContactNo((String)values[7]);
				//visitorVisits.setVisitor(visitor);

				visitorParking.setVisitor(visitor);
				//visitorParking.setParkingSlots(parkingSlots);
				visitorParkingMap.put((Integer) values[0], visitorParking);

			}

		}
		List<Map<String, Object>> visitorparking_list = new ArrayList<Map<String, Object>>();

		Collection<VisitorParking> col = visitorParkingMap.values();
		for (Iterator<VisitorParking> iterator = col.iterator(); iterator
				.hasNext();) {
			Map<String, Object> visitorparkingMap = new HashMap<String, Object>();

			VisitorParking visitorparkingInstance = (VisitorParking) iterator.next();
			
			
			visitorparkingMap.put("vpId", visitorparkingInstance.getVpId());
			//visitorparkingMap.put("vvId", visitorparkingInstance.getVisitorVisits().getVvId());
			visitorparkingMap.put("vmName",visitorparkingInstance.getVisitor().getVmName());
			visitorparkingMap.put("vmId",visitorparkingInstance.getVisitor().getVmId());
			visitorparkingMap.put("vmContactNo",visitorparkingInstance.getVisitor().getVmContactNo());
			//visitorparkingMap.put("psSlotNo", visitorparkingInstance.getParkingSlots().getPsSlotNo());
			//visitorparkingMap.put("psId", visitorparkingInstance.getParkingSlots().getPsId());
			

			//visitorparkingMap.put("vpExpectedHours", visitorparkingInstance.getVpExpectedHours());
			//visitorparkingMap.put("status", visitorparkingInstance.getVpStatus());
		
		
			visitorparking_list.add(visitorparkingMap);
		}

		return visitorparking_list;

	}
	
	
	------------------------readvistorname,contact,id-------------------------------
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public VisitorParking getVisitorParking(Map<String, Object> map,
			String type, VisitorParking visitorParking) {
		return getVisitorParkingBean(map, type, visitorParking);
	}

	private VisitorParking getVisitorParkingBean(Map<String, Object> map,
			String type, VisitorParking visitorParking) {
		// TODO Auto-generated method stub

		Visitor visitor = new Visitor();
		VisitorVisits visitorVisits=new VisitorVisits();
		
		String username = (String) SessionData.getUserDetails().get("userID");

		if (type.equals("save")) {

			logger.info("i am in save method+++++++++++++");
			visitorParking.setCreatedBy(username);
			visitorParking.setLastUpdatedBy(username);
			visitorParking.setVpExpectedHours((Integer)map.get("vpExpectedHours"));
			java.util.Date date = new java.util.Date();
			logger.info(new Timestamp(date.getTime()));
			
			
			visitorParking.setLastUpdatedDt(new Timestamp(date.getTime()));
			
			if(map.get("vpStatus") instanceof java.lang.String){
				
			}else{
			visitorParking.setVpStatus((String)map.get("vpStatus"));
			}
			
			
			visitorParking.setVpStatus((String)map.get("vpStatus"));
		//visitorParking.setPsSlotNo((String)map.get("psSlotNo"));
		if(map.get("psSlotNo")  instanceof java.lang.String )
			{
				//patrolTrackStaff.setPersonId(0);
			}
			else{
				
			
				visitorParking.setPsId((Integer)map.get("psSlotNo"));
			}
			
		
		if( map.get("vmContactNo") == "")
		{
			logger.info(">>>>>>>>>>>> null no");
			//patrolTrackStaff.setPersonId(0);
		}
		else if(map.get("vmContactNo") != "") {
			
			visitorParking.setVmContactNo((String)map.get("vmContactNo"));
			visitorParking.setVmId((Integer)map.get("vmName"));
			
		}
		
		

		}
		
		

		return visitorParking;
	}
	
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<VisitorParking> getSlotNos(){
	return entityManager.createNamedQuery("ParkingSlot_Nos").setParameter("psOwnership", "VISITORS").setParameter("status", "true").setParameter("vpStatus", "required").getResultList();
		
		//return entityManager.createNamedQuery("ParkingSlot_Nos").setParameter("VISITORS", "VISITORS").getResultList();
		
		
	}


*//*******************************used by controller***************************//*
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getVisitorBasedOnId(){
		
		
		List visitorParkinglist= entityManager.createNamedQuery(
				"visitorBasedOnId").getResultList();
		
//return entityManager.createNamedQuery("Visitordeatils").getResultList();
		return getVisitorParkingDetails(visitorParkinglist);
		
		
	}
	
	*//*******************************used by controller***************************//*
	*//*******************************************************************************//*
	
	
	

	private List getVisitorParkingDetails(
			List visitorParkinglist) {
		
		List<Map<String, Object>> visitorparking_lists = new ArrayList<Map<String, Object>>();

		for (Iterator i = visitorParkinglist.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			Map<String, Object> visitorparkingMap = new HashMap<String, Object>();

			visitorparkingMap.put("vmId", (Integer)values[0]);
			visitorparkingMap.put("vmName",(String)values[1]);
			visitorparkingMap.put("vmContactNo",(String)values[2]);
			visitorparkingMap.put("vmFrom",(String)values[3]);
			visitorparkingMap.put("createdBy",(String)values[4]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[5]);
			visitorparkingMap.put("lastUpdateDt",(Timestamp)values[6]);
			
			
			visitorparkingMap.put("vvId", (Integer)values[7]);
			visitorparkingMap.put("gender", (String)values[8]);
			visitorparkingMap.put("category", (String)values[9]);
			
			
			
			//visitorparkingMap.put("acId",(Integer)values[10]);
			
			
			visitorparkingMap.put("vpurpose",(String)values[10]);
			
			
			
			
			final String timeformat = "yyyy/MM/dd HH:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
			
			Timestamp intime = (Timestamp)values[11];
			logger.info("*********************in time is***************"+intime);
		if(intime!=null){
				visitorparkingMap.put("vinDt", sdf.format(intime));
				//visitorparkingMap.put("vinDt", ConvertDate.TimeStampString((Timestamp)values[11]));
				
				
			}
			else{
				
				//visitorparkingMap.put("vinDt",null);
			}
			
	Timestamp outtime = (Timestamp)values[12];
		if(outtime!=null){
			visitorparkingMap.put("voutDt", sdf.format(outtime));
		}
		else{
			
			//visitorparkingMap.put("voutDt",null);
		}
	
			visitorparkingMap.put("createdBy",(String)values[13]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[14]);
			visitorparkingMap.put("vvstatus",(String)values[15]);
			
			visitorparkingMap.put("propertyId",(Integer)values[16]);
			visitorparkingMap.put("property_No",(String)values[17]);
			visitorparkingMap.put("blockId",(Integer)values[18]);
			visitorparkingMap.put("blockName",(String)values[19]);
			
		
			
			visitorparkingMap.put("vpId", (Integer)values[20]);
			visitorparkingMap.put("vpExpectedHours",(Integer)values[21]);
			visitorparkingMap.put("vpStatus",(String)values[22]);
			visitorparkingMap.put("createdBy",(String)values[23]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[24]);
			visitorparkingMap.put("vehicleNo", (String)values[25]);
			if(values[26]==null){
				String slotNo="No Parking Used";
				visitorparkingMap.put("psSlotNo",slotNo);
			}else{
			visitorparkingMap.put("psId",(Integer)values[26]);
			visitorparkingMap.put("psSlotNo",(String)values[27]);
			visitorparkingMap.put("acId", (Integer)values[28]);
			visitorparkingMap.put("acNo", (String)values[29]);
			
		
			visitorparking_lists.add(visitorparkingMap);

		}

		return visitorparking_lists;

}
	*//*******************************************************************************************//*
	*//************************************to show all visitor record****************************//*
	
	
	*//***********************used by controller***************************************************//*
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getVisitorAllRecordBasedOnId(){
		
		
		List visitorParkinglist= entityManager.createNamedQuery(
				"visitorAllRecordBasedOnId").getResultList();
		
//return entityManager.createNamedQuery("Visitordeatils").getResultList();
		return getVisitorAllRecordParkingDetails(visitorParkinglist);
		
		
	}
	
	*//***********************used by controller***************************************************//*
	*//*************************************************************************************************//*
	
	
	private List getVisitorAllRecordParkingDetails(
			List visitorParkinglist) {
		
		List<Map<String, Object>> visitorparking_lists = new ArrayList<Map<String, Object>>();

		for (Iterator i = visitorParkinglist.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			Map<String, Object> visitorparkingMap = new HashMap<String, Object>();

			visitorparkingMap.put("vmId", (Integer)values[0]);
			visitorparkingMap.put("vmName",(String)values[1]);
			visitorparkingMap.put("vmContactNo",(String)values[2]);
			visitorparkingMap.put("vmFrom",(String)values[3]);
			visitorparkingMap.put("createdBy",(String)values[4]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[5]);
			visitorparkingMap.put("lastUpdateDt",(Timestamp)values[6]);
			
			
			visitorparkingMap.put("vvId", (Integer)values[7]);
			visitorparkingMap.put("gender", (String)values[8]);
			visitorparkingMap.put("category", (String)values[9]);
			
			
			
			//visitorparkingMap.put("acId",(Integer)values[10]);
			
			
			visitorparkingMap.put("vpurpose",(String)values[10]);
			
			
			
			
			final String timeformat = "yyyy/MM/dd HH:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
			
			Timestamp intime = (Timestamp)values[11];
			logger.info("*********************in time is***************"+intime);
		if(intime!=null){
				visitorparkingMap.put("vinDt", sdf.format(intime));
			}
			else{
				
				//visitorparkingMap.put("vinDt",null);
			}
			
	Timestamp outtime = (Timestamp)values[12];
		if(outtime!=null){
			visitorparkingMap.put("voutDt", sdf.format(outtime));
		}
		else{
			
			//visitorparkingMap.put("voutDt",null);
		}
	
			visitorparkingMap.put("createdBy",(String)values[13]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[14]);
			visitorparkingMap.put("vvstatus",(String)values[15]);
			
			visitorparkingMap.put("propertyId",(Integer)values[16]);
			visitorparkingMap.put("property_No",(String)values[17]);
			visitorparkingMap.put("blockId",(Integer)values[18]);
			visitorparkingMap.put("blockName",(String)values[19]);
			
		
			
			visitorparkingMap.put("vpId", (Integer)values[20]);
			visitorparkingMap.put("vpExpectedHours",(Integer)values[21]);
			visitorparkingMap.put("vpStatus",(String)values[22]);
			visitorparkingMap.put("createdBy",(String)values[23]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[24]);
			visitorparkingMap.put("vehicleNo", (String)values[25]);
			visitorparkingMap.put("psId",(Integer)values[26]);
			visitorparkingMap.put("psSlotNo",(String)values[27]);
			visitorparkingMap.put("acId", (Integer)values[28]);
			visitorparkingMap.put("acNo", (String)values[29]);
		
			visitorparking_lists.add(visitorparkingMap);

		}

		return visitorparking_lists;

}
	
	
	
	
	
	
	
	//VisitorParking.getVisitorParkingSlot
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getVisitorParkingSlotslist(){
		
		
		//List listparkingslot= entityManager.createNamedQuery("VisitorParking.getVisitorParkingSlot").setParameter("psOwnership", "VISITORS").setParameter("status","true").setParameter("vpStatus", "Vacant").getResultList();
		List listparkingslot= entityManager.createNamedQuery("VisitorParking.getVisitorParkingSlot").getResultList();

	return getVisitorParkingSlot_Available(listparkingslot);
	}
	
	
	
	
	private List getVisitorParkingSlot_Available(List listparkingslot) {
		// TODO Auto-generated method stub
		
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
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getParkingSlot_Vparking_Null(){
		
		List listparkingslot= entityManager.createNamedQuery("VisitorParking.getVisitorParkingSlot_VparkingisNull").setParameter("psOwnership", "VISITORS").setParameter("status", "true").getResultList();
	
	
	
	return getVisitorParkingSlot_VParking_Null(listparkingslot);
			}
	
	


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


	*//******************************used by controller***************//*
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer updateVisitorParkingStatusByButton(int vpId,String vpStatus){
		
		
		return entityManager.createNamedQuery("updateVisitorParkingStatus").setParameter("vpId",vpId).setParameter("vpStatus", "Vacant").executeUpdate();
		
		
	}
	*//******************************used by controller***************//*
	*//******************************************************************//*
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getVisitorRecordSearchBasedonId(int vvId){
		
		List visitorRecordList=  entityManager.createNamedQuery("visitorRecordSearchBasedOnId").setParameter("vvId", vvId).getResultList();
	return getVisitorAllRecordParkingDetailsBasedOnSearch(visitorRecordList);
	}

	

	private List getVisitorAllRecordParkingDetailsBasedOnSearch(
			List visitorRecordList) {
		
		
		List<Map<String, Object>> visitorparking_lists = new ArrayList<Map<String, Object>>();

		for (Iterator i = visitorRecordList.iterator(); i.hasNext();) {
			final Object[] values = (Object[]) i.next();

			Map<String, Object> visitorparkingMap = new HashMap<String, Object>();

			visitorparkingMap.put("vmId", (Integer)values[0]);
			visitorparkingMap.put("vmName",(String)values[1]);
			visitorparkingMap.put("vmContactNo",(String)values[2]);
			visitorparkingMap.put("vmFrom",(String)values[3]);
			visitorparkingMap.put("createdBy",(String)values[4]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[5]);
			visitorparkingMap.put("lastUpdateDt",(Timestamp)values[6]);
			
			
			visitorparkingMap.put("vvId", (Integer)values[7]);
			visitorparkingMap.put("gender", (String)values[8]);
			visitorparkingMap.put("category", (String)values[9]);
			
			
			
			//visitorparkingMap.put("acId",(Integer)values[10]);
			
			
			visitorparkingMap.put("vpurpose",(String)values[10]);
			
			
			
			
			final String timeformat = "dd/MM/yyyy HH:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
			
			Timestamp intime = (Timestamp)values[11];
			logger.info("*********************in time is***************"+intime);
		if(intime!=null){
				visitorparkingMap.put("vinDt", sdf.format(intime));
			}
			else{
				
				//visitorparkingMap.put("vinDt",null);
			}
			
	Timestamp outtime = (Timestamp)values[12];
		if(outtime!=null){
			visitorparkingMap.put("voutDt", sdf.format(outtime));
		}
		else{
			
			//visitorparkingMap.put("voutDt",null);
		}
	
			visitorparkingMap.put("createdBy",(String)values[13]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[14]);
			visitorparkingMap.put("vvstatus",(String)values[15]);
			
			visitorparkingMap.put("propertyId",(Integer)values[16]);
			visitorparkingMap.put("property_No",(String)values[17]);
			visitorparkingMap.put("blockId",(Integer)values[18]);
			visitorparkingMap.put("blockName",(String)values[19]);
			
		
			
			visitorparkingMap.put("vpId", (Integer)values[20]);
			visitorparkingMap.put("vpExpectedHours",(Integer)values[21]);
			visitorparkingMap.put("vpStatus",(String)values[22]);
			visitorparkingMap.put("createdBy",(String)values[23]);
			visitorparkingMap.put("lastUpdatedBy",(String)values[24]);
			visitorparkingMap.put("vehicleNo", (String)values[25]);
			visitorparkingMap.put("psId",(Integer)values[26]);
			visitorparkingMap.put("psSlotNo",(String)values[27]);
			visitorparkingMap.put("acId", (Integer)values[28]);
			visitorparkingMap.put("acNo", (String)values[29]);
			
		
			visitorparking_lists.add(visitorparkingMap);

		}

		return visitorparking_lists;
		
		
		
		
	}


	@Transactional(propagation=Propagation.SUPPORTS)
	public VisitorParking getVisitorBasedOnId(int vmId){
		return (VisitorParking) entityManager.createNamedQuery("VisitorParkingSearchBasedOnId").setParameter("vmId", vmId).getSingleResult();
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	public Integer updateVsisitorParkingBasaedOnSearch(int vpId,int vmId,String vpStatus,Timestamp lastUpdatedDt,String lastUpdatedBy){
		Date date=new Date();
		return entityManager.createNamedQuery("updateVisitorParkingSearchRecord")
				.setParameter("vpStatus", "Vacant")
				.setParameter("lastUpdatedBy", lastUpdatedBy)
				.setParameter("lastUpdatedDt",new Timestamp(date.getTime()))
				.setParameter("vpId", vpId)
				.setParameter("vmId", vmId)
				.executeUpdate();
		
	}
	
	
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

	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VisitorParking>  getVisitorVehicleNoForFilter(){	
		return entityManager.createNamedQuery("Visitorparking.VehicleNo").getResultList();
		
	}
	
	
	
	
}*/