package com.bcits.bfm.serviceImpl.serviceMasterManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTariffMasterServices;
import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ServiceMasterServiceImpl extends GenericServiceImpl<ServiceMastersEntity> implements ServiceMasterService  {

	@Autowired
	private WTTariffMasterService waterTariffService;
	
	@Autowired
	private ELTariffMasterService elTariffMasterService;
	
	@Autowired
	private GasTariffMasterService gasTariffMasterService;
	
	@Autowired
	private SolidWasteTariffMasterService solidWasteTariffMasterService;
	
	@Autowired
	private CommonServiceTariffMasterServices commonServiceTariffMasterServices;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServiceMastersEntity> findALL() { 
		List<ServiceMastersEntity> list = entityManager.createNamedQuery("ServiceMastersEntity.findAll").getResultList();
		return getAllDetailsList(list);
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> serviceMasterEntities){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceMasterMap = null;
		 for (Iterator<?> iterator = serviceMasterEntities.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceMasterMap = new HashMap<String, Object>();
				
				serviceMasterMap.put("serviceMasterId", (Integer)values[0]);
				serviceMasterMap.put("accountId", (Integer)values[1]);
				serviceMasterMap.put("accountNo", (String)values[2]);
				serviceMasterMap.put("personId", (Integer)values[3]);
				
				String personName = "";
				personName = personName.concat((String)values[4]);
				if((String)values[5] != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String)values[5]);
				}
				serviceMasterMap.put("personName", personName);				
				
				if(((Integer)values[6])!=0 && ((String)values[11]).trim().equals("Electricity")){
					serviceMasterMap.put("elTariffID",(Integer)values[6]);
					serviceMasterMap.put("tariffName",elTariffMasterService.find((Integer)values[6]).getTariffName());
				}
				
				if(((Integer)values[7])!=0 && ((String)values[11]).trim().equals("Gas")){
					serviceMasterMap.put("elTariffID",(Integer)values[7]);
					serviceMasterMap.put("tariffName",gasTariffMasterService.find((Integer)values[7]).getGastariffName());
				}
				
				if(((Integer)values[8])!=0 && ((String)values[11]).trim().equals("Water")){
					serviceMasterMap.put("elTariffID",(Integer)values[8]);
					serviceMasterMap.put("tariffName",waterTariffService.find((Integer)values[8]).getTariffName());
				}
				
				if(((Integer)values[9])!=0 && ((String)values[11]).trim().equals("Solid Waste")){
					serviceMasterMap.put("elTariffID",(Integer)values[9]);
					serviceMasterMap.put("tariffName",solidWasteTariffMasterService.find((Integer)values[9]).getSolidWasteTariffName());
				}
				
				if(((Integer)values[10])!=0 && ((String)values[11]).trim().equals("Others")){
					serviceMasterMap.put("elTariffID",(Integer)values[10]);
					serviceMasterMap.put("tariffName",commonServiceTariffMasterServices.find((Integer)values[10]).getCsTariffName());
				}
				
				serviceMasterMap.put("typeOfService", (String)values[11]);
				serviceMasterMap.put("serviceStartDate", (Date)values[12]);
				serviceMasterMap.put("serviceEndDate", (Date)values[13]);
				serviceMasterMap.put("status", (String)values[14]);
				serviceMasterMap.put("todApplicable", (String)values[15]);
				serviceMasterMap.put("property_No", (String)values[16]);
			
			result.add(serviceMasterMap);
	     }
      return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Account> findAccountNumbers() {
		List<Account> details = entityManager.createNamedQuery("ServiceMastersEntity.findAccountNumbers").getResultList();
		return details;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Set<?> findPersons() {
		Set<?> details = new HashSet<>(entityManager.createNamedQuery("ServiceMastersEntity.findPersons").getResultList());
		return details;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findPersonBasedOnAccountIdForFilters() {
		List<?> details = entityManager.createNamedQuery("ServiceMastersEntity.findPersonBasedOnAccountIdForFilters").getResultList();
		return details;
	}
	
	@Override
	public void setServiceMasterStatus(int serviceMasterId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ServiceMastersEntity.setServiceMasterStatus").setParameter("status", "Active").setParameter("serviceMasterId", serviceMasterId).executeUpdate();
				out.write("Service master is activated");
			}
			else
			{
				entityManager.createNamedQuery("ServiceMastersEntity.setServiceMasterStatus").setParameter("status", "Inactive").setParameter("serviceMasterId", serviceMasterId).executeUpdate();
				out.write("Service master is de-activated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void serviceEndDateUpdate(int serviceMasterId,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("serviceEndDate");

			ServiceMastersEntity serviceParametersEntity = find(serviceMasterId);
			
			if(serviceParametersEntity.getServiceEndDate()==null || serviceParametersEntity.getServiceEndDate().equals(""))
			{
				entityManager.createNamedQuery("ServiceMastersEntity.serviceEndDateUpdate").setParameter("serviceEndDate", new java.util.Date()).setParameter("serviceMasterId", serviceMasterId).executeUpdate();
				out.write("Service Ended");
			}else {
				out.write("Service Already Ended");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getElectricityTariffList(String serviceType,String todtype) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 String query="SELECT DISTINCT rm.elTariffID,el.tariffName FROM ELRateMaster rm INNER JOIN rm.elTariffMaster el WHERE el.elTariffID>2 AND el.tariffNodetype='Tariff Rate Node' AND  ";
		
		 if(todtype.equalsIgnoreCase("Yes"))
		 {			 
			query+="rm.todType IN ('Common','Slab Wise')" ;
			
		 }else{
		 query+="rm.todType='None' "; 
		
		 }
		 
		
		 for (Iterator<?> iterator = entityManager.createQuery(query).getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
     return result;
	}

	@Override
	public List<?> getGasTariffList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 for (Iterator<?> iterator = entityManager.createNamedQuery("ServiceMastersEntity.getGasTariffList").getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
    return result;
	}

	@Override
	public List<?> getWaterTariffList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 for (Iterator<?> iterator = entityManager.createNamedQuery("ServiceMastersEntity.getWaterTariffList").getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
    return result;
	}

	@Override
	public List<?> getSolidWasteTariffList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 for (Iterator<?> iterator = entityManager.createNamedQuery("ServiceMastersEntity.getSolidWasteTariffList").getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
    return result;
	}

	@Override
	public List<?> getOthersTariffList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 for (Iterator<?> iterator = entityManager.createNamedQuery("ServiceMastersEntity.getOthersTariffList").getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
    return result;
	}
	@Override
	public List<?> getBroadBandTariffList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> serviceTypeMap = null;
		 for (Iterator<?> iterator = entityManager.createNamedQuery("ServiceMastersEntity.getBroadBandTariffList").getResultList().iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
    return result;
	
	}
	@Override
	public int getServiceMasterByAccountNumber(int accountID,String typeOfService) {
		return (int) entityManager.createNamedQuery("ServiceMastersEntity.getServiceMasterByAccountNumber").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setMaxResults(1).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getWaterTariffId(int accountID, String typeOfService) {
		List<Integer> wtTariffId =entityManager.createNamedQuery("ServiceMastersEntity.getWaterTariffId").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setMaxResults(1).getResultList();
		int wttariffId=0;
		for (Integer object : wtTariffId) {
			wttariffId=object;
		}
		
		return  wttariffId;
	}

	@Override
	public int getGasTariffId(int accountID, String typeOfService) {
		return (int) entityManager.createNamedQuery("ServiceMastersEntity.getGasTariffId").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setMaxResults(1).getSingleResult();
	}

	@Override
	public int getSolidWasteTariffId(int accountID, String typeOfService) {
		return (int) entityManager.createNamedQuery("ServiceMastersEntity.getSolidWasteTariffId").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setMaxResults(1).getSingleResult();
	}

	@Override
	public int getCommonServicesTariffId(int accountID, String typeOfService) {
		return (int) entityManager.createNamedQuery("ServiceMastersEntity.getCommonServicesTariffId").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setMaxResults(1).getSingleResult();
	}

	@Override
	public ServiceMastersEntity getServiceMasterServicType(Integer accoundId,String typeOfService) {
		try {
			return entityManager.createNamedQuery("ServiceMastersEntity.getServiceMasterServicType",ServiceMastersEntity.class).setParameter("accountId", accoundId).setParameter("typeOfService", typeOfService).setMaxResults(1).getSingleResult();
		} catch (Exception e) 
		{
			System.out.println(" ------------------- in catch block --------------");
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Integer> getWaterTariffIdList(int accountID,
			String typeOfService) {
		List wtTariffId =entityManager.createNamedQuery("ServiceMastersEntity.getGasTariffId").setParameter("accountId", accountID).setParameter("typeOfService", typeOfService).setMaxResults(1).getResultList();
		
		return  wtTariffId;
	}

	@Override
	public List<?> allTariffList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Object> allTariffList = new ArrayList<Object>();
		
		List<?> electricityTariffList = entityManager.createNamedQuery("ServiceMastersEntity.getElectricityTariffList").getResultList();
		List<?> waterTariffList = entityManager.createNamedQuery("ServiceMastersEntity.getWaterTariffList").getResultList();
		List<?> gasTariffList = entityManager.createNamedQuery("ServiceMastersEntity.getGasTariffList").getResultList();
		List<?> solidWasteTariffList = entityManager.createNamedQuery("ServiceMastersEntity.getSolidWasteTariffList").getResultList();
		List<?> othersTariffList = entityManager.createNamedQuery("ServiceMastersEntity.getOthersTariffList").getResultList();
		//List<?> broadBandTariffList = entityManager.createNamedQuery("ServiceMastersEntity.getBroadBandTariffList").getResultList();
		
		for (Object object : electricityTariffList) {
			allTariffList.add(object);
		}
		
		for (Object object : waterTariffList) {
			allTariffList.add(object);
		}
		
		for (Object object : gasTariffList) {
			allTariffList.add(object);
		}
		
		for (Object object : solidWasteTariffList) {
			allTariffList.add(object);
		}
		
		for (Object object : othersTariffList) {
			allTariffList.add(object);
		}
		
		/*for (Object object : broadBandTariffList) {
			allTariffList.add(object);
		}*/
		
		Map<String, Object> serviceTypeMap = null;
		for (Iterator<?> iterator = allTariffList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				serviceTypeMap = new HashMap<String, Object>();	
				
				serviceTypeMap.put("elTariffID", (Integer)values[0]);	
				serviceTypeMap.put("tariffName",(String)values[1]);
			
			result.add(serviceTypeMap);
	     }
    return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getServiceParameterBasedOnMasterId(int serviceMasterId) {
		return entityManager.createNamedQuery("ServiceMastersEntity.getServiceParameterBasedOnMasterId").setParameter("serviceMasterId", serviceMasterId).getResultList();
	}

	@Override
	public List<?> serviceParameterList(String serviceType) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> serviceParameterMap = null;
		for (Iterator<?> iterator = entityManager.createNamedQuery("ServiceMastersEntity.serviceParameterList").setParameter("serviceType", serviceType).getResultList().iterator(); iterator.hasNext();){
				final Object[] values = (Object[]) iterator.next();
				serviceParameterMap = new HashMap<String, Object>();	
				
				serviceParameterMap.put("spmId", (Integer)values[0]);	
				serviceParameterMap.put("spmName",(String)values[1]);
			
			result.add(serviceParameterMap);
	     }
    return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getServiceParameterValueBasedOnMasterId(int serviceID) {
		 return entityManager.createNamedQuery("ServiceMastersEntity.getServiceParameterValueBasedOnMasterId").setParameter("serviceMasterId", serviceID).getResultList();
	}
	
}
