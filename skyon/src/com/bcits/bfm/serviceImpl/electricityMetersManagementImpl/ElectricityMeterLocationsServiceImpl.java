package com.bcits.bfm.serviceImpl.electricityMetersManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ElectricityMeterLocationEntity;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.model.ServiceMastersEntity;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMeterLocationsService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ElectricityMeterLocationsServiceImpl extends GenericServiceImpl<ElectricityMeterLocationEntity> implements ElectricityMeterLocationsService  {

	@Autowired
	private ElectricityMetersService electricityMetersService;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityMeterLocationEntity> findALL() {
		return entityManager.createNamedQuery("ElectricityMeterLocationEntity.findAll").getResultList();
	}

	@Override
	public void setLocationStatus(int elMeterLocationId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricityMeterLocationEntity.setLocationStatus").setParameter("locationStatus", "Active").setParameter("elMeterLocationId", elMeterLocationId).executeUpdate();
				out.write("Meter location is activated");
			}
			else
			{
				entityManager.createNamedQuery("ElectricityMeterLocationEntity.setLocationStatus").setParameter("locationStatus", "Inactive").setParameter("elMeterLocationId", elMeterLocationId).executeUpdate();
				out.write("Meter location de-activated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ElectricityMeterLocationEntity> findAllById(int elMeterId) {
		return getAllDetailsList(entityManager.createNamedQuery("ElectricityMeterLocationEntity.findAllById").setParameter("elMeterId", elMeterId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetailsList(List<?> meterLocationEntities) {
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> locationMap = null; 

		for (Iterator<?> iterator = meterLocationEntities.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			locationMap = new HashMap<String, Object>();	
			
			locationMap.put("elMeterLocationId", (Integer)values[0]);	
			//locationMap.put("servicePointId",(Integer)values[6]);
			locationMap.put("meterFixedDate",(Date)values[1]);
			locationMap.put("meterFixedBy",(String)values[2]);
			locationMap.put("intialReading",(Double)values[3]);
			//locationMap.put("typeOfService", (String)values[7]);
			locationMap.put("finalReading", (Double)values[4]);
			locationMap.put("locationStatus", (String)values[5]);
			if(values[6]!=null){
				locationMap.put("accountId", (Integer)values[6]);
				locationMap.put("accountNo", (String)values[7]);
			}
			
			locationMap.put("meterReleaseDate", (Date)values[8]);
			locationMap.put("dgFinalReading", (Double)values[9]);
			locationMap.put("dgIntitalReading", (Double)values[10]);
			
			result.add(locationMap);
		}
		return result;
	}

	/*private List<ElectricityMeterLocationEntity> selectedList(List<ElectricityMeterLocationEntity> list) 
	{
		List<ElectricityMeterLocationEntity> listNew = new ArrayList<ElectricityMeterLocationEntity>();
		for (Iterator<ElectricityMeterLocationEntity> iterator = list.iterator(); iterator.hasNext();) {
			ElectricityMeterLocationEntity meterLocationEntity = (ElectricityMeterLocationEntity) iterator.next();
			meterLocationEntity.setElectricityMetersEntity(null);
			meterLocationEntity.setServicePointEntity(null);
			listNew.add(meterLocationEntity);
		}
		return listNew;
	}*/
	
	    @Override
		@SuppressWarnings("unchecked")
		@Transactional(propagation=Propagation.SUPPORTS)
		public List<String> getAllServiceTypes()
		{
			return entityManager.createNamedQuery("ElectricityMeterLocationEntity.getAllServiceTypes").getResultList();
		}

		@Override
		public void releaseMeterStatusClick(int elMeterLocationId,Date releaseDate,double finalReading,HttpServletResponse response) {
			
			try
			{
				PrintWriter out = response.getWriter();
				ElectricityMeterLocationEntity meterLocationEntity = find(elMeterLocationId);
				ElectricityMetersEntity electricityMetersEntity = meterLocationEntity.getElectricityMetersEntity();
				if(electricityMetersEntity.getMeterStatus().equalsIgnoreCase("In Repair")){
					out.write("Already released this meter");
				}else {
					
					System.out.println("relese meter:::::::::"+releaseDate+"::::::::::::::"+finalReading);
					electricityMetersEntity.setMeterStatus("In Repair");
					meterLocationEntity.setFinalReading(finalReading);
					meterLocationEntity.setMeterReleaseDate(releaseDate);
					electricityMetersEntity.setAccount(null);
					//electricityMetersEntity.setPointEntity(null);
					update(meterLocationEntity);
					electricityMetersService.update(electricityMetersEntity);
					out.write("Meter released succefully");
				}
				
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public List<?> getAllAccuntNumbers() {
			return getAccountNumbersData(entityManager.createNamedQuery("ElectricityMeterLocationEntity.getAllAccuntNumbers").getResultList());
		}
		
		@SuppressWarnings("rawtypes")
		public List getAccountNumbersData(List<?> accountNumbers){
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			 Map<String, Object> accountNumberMap = null;
			 for (Iterator<?> iterator = accountNumbers.iterator(); iterator.hasNext();)
				{
					final Object[] values = (Object[]) iterator.next();
					accountNumberMap = new HashMap<String, Object>();
					
					accountNumberMap.put("accountId", (Integer)values[0]);
					accountNumberMap.put("accountNo", (String)values[1]);
					
					String personName = "";
					personName = personName.concat((String)values[3]);
					if(((String)values[4]) != null)
					{
						personName = personName.concat(" ");
						personName = personName.concat((String)values[4]);
					}
					
					accountNumberMap.put("personId", (Integer)values[2]);
					accountNumberMap.put("personName", personName);
					accountNumberMap.put("personType", (String)values[5]);
					accountNumberMap.put("personStyle", (String)values[6]);
				
				result.add(accountNumberMap);
		     }
	     return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ServiceMastersEntity> getServiceMasterObj(int accountId,String typeOfService) {
			return entityManager.createNamedQuery("ElectricityMeterLocationEntity.getServiceMasterObj").setParameter("accountId", accountId).setParameter("typeOfService", typeOfService).getResultList();
		}
}
