package com.bcits.bfm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.MeetingRequest;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;

public interface MeetingRequestService extends GenericService<MeetingRequest>{

	MeetingRequest getObject(Map<String, Object> map, String type,MeetingRequest meetingRequest, HttpServletRequest request);
	List<?> getMeetingObject();
	List<Property> getPropertiesByBlockId(int blockId);
	List<OwnerProperty> getOwnerPropertyBasedOnPropertyId(int propertyId);
	Integer getPersonIdByOwnerId(int ownerId);
	List<Contact> getContactsByPersonId(int personId);
	List<Blocks> getAllBlock();
	Person getPersonStatus(int personId);
	List<MeetingRequest> getAllRequestByCurrentDate(String decrDate , String incDate);
}
