package com.bcits.bfm.serviceImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.CustomerCareNotification;
import com.bcits.bfm.model.MeetingRequest;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.MeetingRequestService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.util.ConvertDate;

@Repository
public class MeetingRequestServiceImpl extends GenericServiceImpl<MeetingRequest> implements MeetingRequestService{
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private BlocksService blocksService;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MeetingRequest getObject(Map<String, Object> map,
			String type, MeetingRequest meetingRequest,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		
		Set<Integer> blockIdSet = new HashSet<Integer>();
		String blockIds="";
		String propertNos ="";
		
		if( type == "save" ){
			String radioBtn = (String)map.get("radioBtn");
			String radioBtn2 = (String)map.get("radioBtn2");
			if( radioBtn.equals("property") )
			{
				final List<Map<String,Object>> propertyMap = (List<Map<String,Object>>)map.get("propertyNo");
				for (Iterator iterator = propertyMap.iterator(); iterator.hasNext();) {
					Map<String, Object> map2 = (Map<String, Object>) iterator.next();
					
					propertNos = propertNos + map2.get("propertyId")+","; 
					int blckId = propertyService.getBlockIdBasedOnPropertyId((Integer)map2.get("propertyId"));
					blockIdSet.add(blckId);
					
					
				}
				String propertyNo = propertNos.substring(0,propertNos.length()-1); 
				meetingRequest.setPropertyId(propertyNo);
				for( Integer rec : blockIdSet){
					blockIds = blockIds+rec+",";
				}
				String finalBkId = blockIds.substring(0,blockIds.length()-1);
				meetingRequest.setBlockId(finalBkId);
			}
			if( radioBtn.equals("block") )
			{
				if( radioBtn2.equals("all") )
				{
					meetingRequest.setPropertyId("All Properties Of This Block");
					meetingRequest.setBlockId("All Blocks");
				}
				else{
				final List<Map<String,Object>> blockMap = (List<Map<String,Object>>)map.get("blockName");
				for (Iterator iterator = blockMap.iterator(); iterator.hasNext();) {
					Map<String, Object> map2 = (Map<String, Object>) iterator.next();
					
					blockIds = blockIds + map2.get("blockId")+","; 
					
				}
				String blockId = blockIds.substring(0,blockIds.length()-1); 
				meetingRequest.setPropertyId("All Properties Of This Block");
				meetingRequest.setBlockId(blockId);
				}
			}
			meetingRequest.setMeetingSubject((String)map.get("meetingSubject"));
			meetingRequest.setMeetingMessage((String)map.get("MeetingMessage"));
			meetingRequest.setLocation((String)map.get("location"));
			meetingRequest.setStartTime(ConvertDate.formattedDate(map.get("startTime").toString()));			
			/*String startTime=ConvertDate.formattedDate(map.get("startTime").toString()).toString();
			String[] test=startTime.split(" ");
			String endDate=test[0];
			System.out.println("endDate +++++++++++++++++++"+endDate);
			String thirdDate=endDate+"T18:00:00.000Z";
			System.out.println("========================"+thirdDate);
			System.out.println("ConvertDate.formattedDate(thirdDate)+++++++++++++++++++++"+ConvertDate.formattedDate(thirdDate));*/
			meetingRequest.setEndTime(ConvertDate.formattedDate(map.get("endTime").toString()));
			meetingRequest.setCreatedBy(userName);
		}
		
		return meetingRequest;
	}
	@SuppressWarnings("serial")
	@Override
	public List<?> getMeetingObject() {
		@SuppressWarnings("unchecked")
		List<MeetingRequest> meetingRequestList =entityManager.createNamedQuery("MeetingRequest.findAll").getResultList();
		List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>(); 
		for (final MeetingRequest record : meetingRequestList) {
			list.add(new HashMap<String, Object>() {{
				put("MeetingMessage",record.getMeetingMessage());
				put("meetingSubject",record.getMeetingSubject());
				
				String blockids = record.getBlockId();
				if( blockids.equals("None") ||  blockids.equals("All Blocks") ){
					put("blockName",blockids);
				}
				else{
				String[] splitedBlockids = blockids.split(",");
				String blockNames = "";
				for (int i = 0; i < splitedBlockids.length; i++) {
					int blockId = Integer.parseInt(splitedBlockids[i]);
					Blocks block = blocksService.getBlockNameByBlockId(blockId);
					blockNames = blockNames+block.getBlockName()+",";
				}
				 String finalBlockNamea = blockNames.substring(0,blockNames.length()-1);
				put("blockName",finalBlockNamea);
				put("blockId","");
				}
			
				String propertyids = record.getPropertyId();
				if( propertyids.equals("All Properties") ||  propertyids.equals("All Properties Of This Block") ){
					put("propertyNo",propertyids);
				}
				else{
				String[] splitedPropertyIds = propertyids.split(",");
				String propertyNos = "";
				for (int i = 0; i < splitedPropertyIds.length; i++) {
					int propertyId = Integer.parseInt(splitedPropertyIds[i]);
					String propertyNo = propertyService.getPropertyNameBasedOnPropertyId(propertyId);
					propertyNos = propertyNos+propertyNo+",";
				}
				String finalPropertyNos = propertyNos.substring(0,propertyNos.length()-1);
				put("propertyNo",finalPropertyNos);
				put("propertyId","");
				}
				put("meeting_id",record.getMeeting_id());
				put("createdBy",record.getCreatedBy());				
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
				put("startTime",dateFormat.format(record.getStartTime()));		
				put("location",record.getLocation());
				put("endTime",dateFormat.format(record.getEndTime()));
				
			}});
			
		}
		
		return list;
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Property> getPropertiesByBlockId(int blockId) {
		return entityManager.createNamedQuery("Property.getPropertyDataBasedOnBlockId").setParameter("blockId",blockId).getResultList();
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OwnerProperty> getOwnerPropertyBasedOnPropertyId(
			int propertyId) {
		return entityManager.createNamedQuery("OwnerProperty.getOwnerPropertyBasedOnPropertyIdAndOwnerId").setParameter("propertyId",propertyId).getResultList();
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer getPersonIdByOwnerId(int ownerId) {
		return (Integer) entityManager.createNamedQuery("OwnerProp.ownerId").setParameter("ownerId",ownerId).getSingleResult();
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Contact> getContactsByPersonId(int personId) {
		return entityManager.createNamedQuery("Contact.findAllContactsBasedOnPersonID").setParameter("personId",personId).getResultList();
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Blocks> getAllBlock() {
		return entityManager.createNamedQuery("Blocks.getAll").getResultList();
	}
	@Override
	public Person getPersonStatus(int personId) {
		return (Person) entityManager.createNamedQuery("Person.getPersonBasedOnId").setParameter("personId",personId ).getSingleResult();
	}
	@Override
	public List<MeetingRequest> getAllRequestByCurrentDate(String decrDate , String incDate) {
		return entityManager.createNamedQuery("MeetingRequest.getAllRequestByCurrentDate").setParameter("decrDate", decrDate).setParameter("incDate", incDate).getResultList();
	}

	
}
