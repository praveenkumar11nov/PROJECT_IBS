package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.controller.BillController;
import com.bcits.bfm.model.DocumentDefiner;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentDefinerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
@Repository
public class DocumentDefinerServiceImpl extends GenericServiceImpl<DocumentDefiner> implements DocumentDefinerService
{
	DateTimeCalender dateTimeCalender = new DateTimeCalender(); 
	
	static Logger logger = LoggerFactory.getLogger(DocumentDefinerServiceImpl.class);
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public DocumentDefiner getdocumentDefinerObject(Map<String, Object> map,
			String type, DocumentDefiner documentDefiner)
	{
		
		if(type == "update")
		{
			
			logger.info("before Converting Date--------------"+map.get("ddStartDate"));
			
			java.sql.Date d=dateTimeCalender.getDate1((String)map.get("ddStartDate"));
			
			Calendar c=Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, 1);
			
			java.sql.Date startDate= new java.sql.Date(c.getTimeInMillis());
			
			logger.info("after Adding one Day in Start date-----------"+startDate);
			
			logger.info("end Date Before Converting --------"+map.get("ddEndDate"));
			
			java.sql.Date e=dateTimeCalender.getDate1((String)map.get("ddEndDate"));
			
			
			
			Calendar c2=Calendar.getInstance();
			c2.setTime(e);
			c2.add(Calendar.DATE, 1);
			
			logger.info("after Converting End Date by adding extra Date----------"+new java.sql.Date(c2.getTimeInMillis()));
			
			
			documentDefiner.setDdEndDate(new java.sql.Date(c2.getTimeInMillis()));
			documentDefiner.setDdStartDate(startDate);
			documentDefiner.setDdId((Integer)map.get("ddId"));
			documentDefiner.setCreatedBy((String)map.get("createdBy"));
			documentDefiner.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		else if (type == "save")
		{
			documentDefiner.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			documentDefiner.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			
			documentDefiner.setDdStartDate(dateTimeCalender.getDate1((String)map.get("ddStartDate")));
			documentDefiner.setDdEndDate(dateTimeCalender.getDate1((String)map.get("ddEndDate")));
			
		}
		
		documentDefiner.setDdType((String)map.get("ddType"));
		documentDefiner.setDdName((String)map.get("ddName"));
		documentDefiner.setDdDescription((String)map.get("ddDescription"));
		documentDefiner.setDdFormat((String)map.get("ddFormat"));
		documentDefiner.setDdOptional((String)map.get("ddOptional"));
		documentDefiner.setDdRvComplaince((String)map.get("ddRvComplaince"));
		documentDefiner.setStatus((String)map.get("status"));
		// TODO Auto-generated method stub
		return documentDefiner;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<DocumentDefiner> findAll() 
	{
		// TODO Auto-generated method stub
		BfmLogger.logger.info("finding all DocumentDefiner instances");
		try {
			return entityManager.createNamedQuery("DocumentDefiner.findAll").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<DocumentDefiner> getAllBasedOnType(String personType) {
		// TODO Auto-generated method stub
		BfmLogger.logger.info("finding all DocumentDefiner instances");
		try {
			return entityManager.createNamedQuery("DocumentDefiner.findAllByType").setParameter("ddType", personType).getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String getDocumentFormatOnPersonType(int selectedDocument,
			String personType) {
		BfmLogger.logger.info("Finding DocumentFormat - getDocumentFormatOnPersonType");
		try {
			return entityManager.createNamedQuery("DocumentDefiner.getDocumentFormatOnPersonType",String.class).setParameter("ddId", selectedDocument).setParameter("ddType", personType).getSingleResult();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public void updateAccessCardStatus(int ddId, String operation,
			HttpServletResponse response) 
	{
		// TODO Auto-generated method stub
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("DocumentDefiner.UpdateStatus").setParameter("status", "Active").setParameter("ddId", ddId).executeUpdate();
				out.write("Defined Document Activated");
			}
			else if(operation.equalsIgnoreCase("invalid"))
			{
				out.write("No Defined Document Records Found, User can't be activated");
			}
			else
			{
				entityManager.createNamedQuery("DocumentDefiner.UpdateStatus").setParameter("status", "Inactive").setParameter("ddId", ddId).executeUpdate();
				out.write("Defined Document Deactivated");
			}
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentDefiner> getAllBasedOnTypeAndCondition(
			String personType, String condition) {
		BfmLogger.logger.info("finding all DocumentDefiner instances");
		try {
			return entityManager.createNamedQuery("DocumentDefiner.getAllBasedOnTypeAndCondition")
					.setParameter("ddType", personType)
					.setParameter("ddOptional", condition)
					.getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

}
