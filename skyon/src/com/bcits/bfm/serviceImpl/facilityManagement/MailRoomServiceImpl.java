package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.MailRoom;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.SessionData;

/**
 * MailRoomServiceImpl Implementatio class for its interface
 * @author Shashidhar Bemalgi 
 *
 */
@Repository
public class MailRoomServiceImpl extends GenericServiceImpl<MailRoom> implements MailRoomService 
{
	static Logger logger = LoggerFactory.getLogger(MailRoomServiceImpl.class);

	@Autowired
	private PropertyService propertyService;

	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MailRoom> findAll() 
	{
		List<MailRoom> mailroom =entityManager.createNamedQuery("MailRoom.findAll").getResultList();
		return mailroom;
	}


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findAllData() 
	{
		List<?> mailroom =get(entityManager.createNamedQuery("MailRoom.findAllData").getResultList());
		return mailroom;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findData() 
	{
		List<?> mailroom =getdata(entityManager.createNamedQuery("MailRoom.findData").getResultList());
		return mailroom;
	}

	
	@SuppressWarnings("rawtypes")
	private List get(List<?> meterStatusEntities){
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> transactionMasterMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				transactionMasterMap = new HashMap<String, Object>();
				
				transactionMasterMap.put("mlrId", (Integer)values[0]);
				transactionMasterMap.put("blockId", (Integer)values[1]);
				transactionMasterMap.put("blockName", (String)values[2]);
				transactionMasterMap.put("propertyId", (Integer)values[3]);
				transactionMasterMap.put("property_No", (String)values[4]);
				transactionMasterMap.put("propertyName", (String)values[5]);
				transactionMasterMap.put("addressedTo", (String)values[6]);
				transactionMasterMap.put("addressedFrom", (String)values[7]);
				transactionMasterMap.put("mailboxDt", ConvertDate.TimeStampString((Timestamp)values[8]));
				
				transactionMasterMap.put("status", (String)values[9]);
				
				transactionMasterMap.put("lastUpdatedDt", ConvertDate.TimeStampString((Timestamp)values[10]));
				transactionMasterMap.put("drGroupId", (Integer)values[11]);
				transactionMasterMap.put("createdBy", (String)values[12]);
				transactionMasterMap.put("lastUpdatedBy", (String)values[13]);
				transactionMasterMap.put("mailRedirectedStatus", (String)values[14]);
				transactionMasterMap.put("mailReturnedStatus", (String)values[15]);
				transactionMasterMap.put("reasons", (String)values[16]);
				transactionMasterMap.put("consignmentNo", (String)values[17]);
				
				
				
				
				
			result.add(transactionMasterMap);
	     }
      return result;
	}
	
	@SuppressWarnings("rawtypes")
	private List getdata(List<?> meterStatusEntities){
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> transactionMasterMap = null;
		 for (Iterator<?> iterator = meterStatusEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				transactionMasterMap = new HashMap<String, Object>();
				
				transactionMasterMap.put("mlrId", (Integer)values[0]);
				transactionMasterMap.put("blockId", (Integer)values[1]);
				transactionMasterMap.put("blockName", (String)values[2]);
				transactionMasterMap.put("propertyId", (Integer)values[3]);
				transactionMasterMap.put("property_No", (String)values[4]);
				transactionMasterMap.put("propertyName", (String)values[5]);
				transactionMasterMap.put("addressedTo", (String)values[6]);
				transactionMasterMap.put("addressedFrom", (String)values[7]);
				transactionMasterMap.put("mailboxDt", ConvertDate.TimeStampString((Timestamp)values[8]));
				
				transactionMasterMap.put("status", (String)values[9]);
				
				transactionMasterMap.put("lastUpdatedDt", ConvertDate.TimeStampString((Timestamp)values[10]));
				transactionMasterMap.put("drGroupId", (Integer)values[11]);
				transactionMasterMap.put("createdBy", (String)values[12]);
				transactionMasterMap.put("lastUpdatedBy", (String)values[13]);
				transactionMasterMap.put("mailRedirectedStatus", (String)values[14]);
				transactionMasterMap.put("mailReturnedStatus", (String)values[15]);
				transactionMasterMap.put("reasons", (String)values[16]);
				transactionMasterMap.put("consignmentNo", (String)values[17]);
				if(values[18]!=null){
				transactionMasterMap.put("mailNotifiedDt", ConvertDate.TimeStampString((Timestamp)values[18]));
				}
				else
				{
					transactionMasterMap.put("mailNotifiedDt","-");	
				}
				if(values[19]!=null){
				transactionMasterMap.put("lastUpdatedDt", ConvertDate.TimeStampString((Timestamp)values[19]));
				}
				else
					{
					transactionMasterMap.put("lastUpdatedDt","-");
					}
				if(values[20]!=null){
				transactionMasterMap.put("mailRedirectedDt", ConvertDate.TimeStampString((Timestamp)values[20]));
				}
				else
				{
					transactionMasterMap.put("mailRedirectedDt", "-");
				}
			result.add(transactionMasterMap);
	     }
      return result;
	}
	
	/* (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#findAllFilter()
	*/
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MailRoom> findAllFilter() 
	{
		List<MailRoom> mailroom = entityManager.createNamedQuery("MailRoom.findAll").getResultList();
		return mailroom;
	}
	
	/*
	* For Reading property names
	* (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#findPropertyNames()
	*/
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Property> findPropertyNames() 
	{
		List<Property> details = entityManager.createNamedQuery("Property.readPropertyNames").getResultList();
		logger.info("Property Details are:" + details);
		return details;
	}

	
	/*
	* 
	* (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#getPropNames()
	*/
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Property> getPropNames() 
	{
		return entityManager.createNamedQuery("Property.getPropName").getResultList();
	}

	/*
	* For Saving/updating the MailRepository details
	* (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#getBeanObject(java.util.Map, java.lang.String, com.bcits.bfm.model.MailRoom)
	*/
	@SuppressWarnings("unused")
	@Override
	public MailRoom getBeanObject(Map<String, Object> map, String type,MailRoom mailroom) 
	{
		String status = (String) map.get("status");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(sdf, true);

		String mailboxDt = (String) map.get("mailboxDt");
		String[] mailboxDate = mailboxDt.split("T");
		java.util.Date mailboxDates = null;
		try 
		{
			mailboxDates = sdf.parse(mailboxDate[0] + " " + mailboxDate[1]);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		String userName = (String) SessionData.getUserDetails().get("userID");
		BfmLogger.logger.info("UserName From Session Are:" + userName);
		if (type.equals("update")) 
		{
			if (status.equals("Mail_Received")) 
			{
				mailroom.setMlrId((int) map.get("mlrId"));
				int id = (Integer) map.get("propertyId");
				mailroom.setPropertyId(id);
				
				
				String addTo = (String)map.get("addressedTo");
				String addTo_trim = addTo.trim();				
				mailroom.setAddressedTo(addTo_trim);
				
				String addFrom = (String)map.get("addressedFrom");
				String addFrom_trim = addFrom.trim();				
				mailroom.setAddressedFrom(addFrom_trim);
				
				mailroom.setMailboxDt(ConvertDate.formattedDate(map.get("mailboxDt").toString()));
				mailroom.setStatus((String) map.get("status"));
				mailroom.setReceivedBy((String) map.get("receivedBy"));
				mailroom.setCreatedBy(userName);
				mailroom.setLastUpdatedBy(userName);
				mailroom.setDrGroupId((int)map.get("drGroupId"));
				mailroom.setConsignmentNo((String) map.get("consignmentNo"));
				mailroom.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				logger.info("UPDATED INSIDE IMPL");
			}
			return mailroom;
		}
		else if (type.equals("save")) 
		{
			if (map.get("property_No") != null) 
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				logger.info(dateFormat.format(date));

				String propNo = (String) map.get("property_No");
				mailroom.setPropertyId(Integer.parseInt(propNo));
				
				String addTo = (String)map.get("addressedTo");
				String addTo_trim = addTo.trim();				
				mailroom.setAddressedTo(addTo_trim);
				
				String addFrom = (String)map.get("addressedFrom");
				String addFrom_trim = addFrom.trim();				
				mailroom.setAddressedFrom(addFrom_trim);
				
				mailroom.setMailboxDt(ConvertDate.formattedDate(map.get("mailboxDt").toString()));
				mailroom.setStatus((String) map.get("status"));
				mailroom.setReceivedBy((String) map.get("receivedBy"));
				mailroom.setCreatedBy(userName);
				mailroom.setLastUpdatedBy(userName);
				mailroom.setLastUpdatedDt(new Timestamp(new Date().getTime()));

				String st = (String) map.get("consignmentNo");
				if (st != "") 
				{
					logger.info("VALUE SENT IF");
					mailroom.setConsignmentNo((String) map.get("consignmentNo"));
					logger.info("IF" + (String) map.get("consignmentNo"));
				}
				else if (st == "") 
				{
					logger.info("VALUE IS NULL ELSE");
					mailroom.setConsignmentNo(null);
					logger.info("ELSE IF");
				}
				return mailroom;
			} 
			else if (map.get("property_No") == null) 
			{
				mailroom.setPropertyId(0);
			}
		}
		return mailroom;
	}

	/*
	* 
	*  (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#updateStatus(int, java.lang.String)
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateStatus(int id, String newStatus) 
	{
		logger.info("Inside Impl" + id + "  " + newStatus);
		return entityManager.createNamedQuery("MailRoom.updateStatus").setParameter("id", id).setParameter("newStatus", newStatus).executeUpdate();
	}

	/*
	* 
	*  (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#updateMailRoomDeliveryStatus(int, java.lang.String, java.sql.Date, java.sql.Date, java.sql.Date, java.lang.String, java.sql.Date, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateMailRoomDeliveryStatus(int id, String newStatus,java.sql.Date d1, java.sql.Date d2, java.sql.Date d3,String previousStatus, java.sql.Date date, String deliveredBy,
											String reason, String redirectedAddress, String receivedBy) 
	{
		logger.info("Inside Impl" + id + " " + previousStatus + " " + newStatus);
		return entityManager.createNamedQuery("MailRoom.updateMailRoomDeliveryStatus").setParameter("id", id).setParameter("newStatus", newStatus)
				.setParameter("d1", d1).setParameter("d2", d2).setParameter("d3", d3)
				.setParameter("deliveredBy", deliveredBy).setParameter("reason", reason)
				.setParameter("redirectedAddress", redirectedAddress).setParameter("receivedBy", receivedBy).executeUpdate();
	}

	/*
	*  
	* (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#getFirstAndLastName(int)
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getFirstAndLastName(int pId) 
	{
		List name = entityManager.createNamedQuery("Person.getFirstAndLastName").setParameter("pId", pId).getResultList();
		return name;
	}

	/*
	* 
	*  (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#getMaxCount()
	*/
	@Override
	@SuppressWarnings({ "rawtypes" })
	@Transactional(propagation = Propagation.REQUIRED)
	public List getMaxCount() 
	{
		List id = entityManager.createNamedQuery("MailRoom.getMaxCount").getResultList();
		Iterator maxId = id.iterator();
		while (maxId.hasNext()) 
		{
			logger.info("maxId.next()\n" + maxId.next());
		}
		return id;
	}
	
	/*
	* For Updating Mailroom Consignment number
	* (non-Javadoc)
	* @see com.bcits.bfm.service.facilityManagement.MailRoomService#updateMailroomConsignmentNumber(int, java.lang.String)
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateMailroomConsignmentNumber(int id, String consignmentNo) 
	{
		return entityManager.createNamedQuery("MailRoom.updateConsignmentNumber").setParameter("id", id).setParameter("consignmentNo", consignmentNo).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MailRoom> readAllSentMails(String status)
	{	
		List<MailRoom> mailroom = entityManager.createNamedQuery("MailRoom.getSentMailDetails").setParameter("status", status).getResultList();
		return mailroom;
	}
	
}
