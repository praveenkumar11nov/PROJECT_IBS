package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PreRegisteredVisitors;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.facilityManagement.PreRegisteredVisitorService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PreRegisteredVisitorServiceImpl extends GenericServiceImpl<PreRegisteredVisitors> implements PreRegisteredVisitorService
{
	@SuppressWarnings("unchecked")
	@Override
	public List<PreRegisteredVisitors> findAll()
	{	
		return entityManager.createNamedQuery("PreRegisteredVisitors.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PreRegisteredVisitors> getVisitorRequiredDetails() 
	{	
		return readAllVisitorRecords(entityManager.createNamedQuery("PreRegisteredVisitors.getVisitorsRequiredDetails").getResultList());
	}

	@SuppressWarnings({ "rawtypes" })
	private List readAllVisitorRecords(List<?> visitor)
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> visitorData = null;
		 for (Iterator<?> iterator = visitor.iterator(); iterator.hasNext();)
		 {
			 final Object[] values = (Object[]) iterator.next();
			 visitorData = new HashMap<String, Object>();	
			 visitorData.put("viId", (Integer)values[0]);
			 visitorData.put("visitorName", (String)values[1]);
			 visitorData.put("visitorContactNo", (String)values[2]);
			 visitorData.put("visitorFrom", (String)values[3]);
			 visitorData.put("gender", (String)values[4]);			 
			 visitorData.put("parkingRequired", (String)values[5]);
			 visitorData.put("preRegistereduser", (String)values[6]);
			 visitorData.put("status", (String)values[7]);
			 visitorData.put("propertyId", (Integer)values[8]);
			 visitorData.put("property_No", (String)values[9]);			 
			 visitorData.put("blockId", (Integer)values[10]);
			 visitorData.put("blockName", (String)values[11]);
			 visitorData.put("vehicleNo", (String)values[12]);
			 visitorData.put("fromTime", (String)values[13]);
			 visitorData.put("toTime", (String)values[14]);
			 visitorData.put("noOfVisitors", (Integer)values[15]);
			 visitorData.put("visitorPassword", (String)values[16]);
			 visitorData.put("createdBy", (String)values[17]);
			 
			 java.util.Date visitingDtUtil =  (Date)values[18];
			 java.sql.Date visitingDtSql = new java.sql.Date(visitingDtUtil.getTime());		
			 visitorData.put("visitingDate", visitingDtSql);
			 
			 result.add(visitorData);
		 }
		 return result;		
	}

	@Override
	public List<?> searchPreRegisteredVisitorBasedOnContactNo(String visitorContactNo)
	{
		return (List<?>) entityManager.createNamedQuery("SearchPreRegisteredVisitorBasedOnContactNo").setParameter("visitorContactNo", visitorContactNo).setMaxResults(1).getSingleResult();
	}

	@Override
	public int getPropertyIdBasedOnPropertyNo(String propertyNo) 
	{	
		return (int) entityManager.createNamedQuery("Visitor.getPropertyIdBasedOnPropertyNo").setParameter("propertyNo", propertyNo).getSingleResult();
	}

	@Override
	public int getParkinSlotIdBasedOnPsSlotNo(String psSlotNo) 
	{
		return (int) entityManager.createNamedQuery("Visitor.getParkinSlotIdBasedOnPsSlotNo").setParameter("psSlotNo", psSlotNo).getSingleResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getReRegisteredImageForWizardView(String visitorContactNo)
	{
		return (Blob) entityManager.createNamedQuery("PreVisitor.getReRegisteredImageForWizardView", Blob.class).setParameter("visitorContactNo", visitorContactNo).getSingleResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getImageForPreRegisteredView(int viId) 
	{
		return (Blob) entityManager.createNamedQuery("PreVisitor.getImageForPreRegisteredView", Blob.class).setParameter("viId", viId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getVisitorDetails() {
		
		return entityManager.createNamedQuery("PreRegisteredVisitors.getVisitorsRequiredDetails").getResultList();
	}
}