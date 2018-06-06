package com.bcits.bfm.service.facilityManagement;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.MailRoom;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.GenericService;

/**
 * MailRoom Service Class
 * @author Shashidhar Bemalgi
 *
 */
public interface MailRoomService extends GenericService<MailRoom> 
{
	/**
	* for reading MailRepository details
	* @return Mailroom object
	*/
	public List<MailRoom> findAll();
	
	/**
	* For Reading property names
	* @return property object with property names 
	*/
	public List<Property> findPropertyNames();
	
	/**
	* For Saving/updating the MailRepository details
	* @param map mailroom details
	* @param string save/update
	* @param mailroom Entity class
	* @return mailroom object
	*/
	public MailRoom getBeanObject(Map<String, Object> map, String string,MailRoom mailroom);

	/**
	* For updating mail status sent for delivery(Out_For_Delivery) 
	* @param id mlrId Mail_Repository Id
	* @param newStatus Status of mail(Out_For_Delivery)
	* @return integer updated value
	*/
	public int updateStatus(int id, String newStatus);
		
	/**
	* For updating Mail Delivery details
	* @param id Mail_Repository Id
	* @param newStatus String of mail
	* @param d1 mailNotifiedDt i.e Mail Delivered Date
	* @param d2 mailRedirectedDt i.e Mail Redirected Date
	* @param d3 mailreturnedDt i.e Mail Returned Date
	* @param previousStatus status of mail
	* @param dt  mailBoxDt i.e Mail received date
	* @param deliveredBy note for capturing mail deliveryBoyName
	* @param reason for capturing reason for returned mail 
	* @param redirectedAddress for capturing redirected address of mail
	* @param receivedBy for capturing the name of person who received the mail
	* @return updated Mailrepository status
	*/
	public int updateMailRoomDeliveryStatus(int id, String newStatus,Date d1,Date d2,Date d3,String previousStatus,Date dt,String deliveredBy,String reason,String redirectedAddress,String receivedBy);
	
	/**
	* For Filter 
	* @return Mailroom object 
	*/
	public List<MailRoom> findAllFilter();
	
	/**
	* @return Property object
	*/
	public List<Property> getPropNames();
			
	/**
	* @param pId propertyId
	* @return String value with FirstName & LastName
	*/
	public List<String>  getFirstAndLastName(int pId);
		
	/**
	* @return MailRepository details count
	*/
	@SuppressWarnings("rawtypes")
	public List getMaxCount();		
	
	/**
	* For Updating Mailroom Consignment number
	* @param id mailrepositoryId
	* @param consignmentNo mailConsignment number
	* @return updated mailrepository with consignment number updated 
	*/
	public int updateMailroomConsignmentNumber(int id, String consignmentNo);
	
	public List<MailRoom> readAllSentMails(String status);

	public List<?> findAllData();

	public List<?> findData();
	
	
	
}
