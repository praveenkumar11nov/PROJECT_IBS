package com.bcits.bfm.serviceImpl.electricityMetersManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.electricityMetersManagement.ElectricityMetersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ElectricityMetersServiceImpl extends GenericServiceImpl<ElectricityMetersEntity> implements ElectricityMetersService  {

	@Autowired
	private AccountService accountService;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> findALL() { 
		List<ElectricityMetersEntity> list = entityManager.createNamedQuery("ElectricityMetersEntity.findAll").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		for (final ElectricityMetersEntity record : list) {
			response.add(new HashMap<String, Object>() {
				{

					put("elMeterId", record.getElMeterId());
					put("typeOfServiceForMeters", record.getTypeOfServiceForMeters());
					put("meterSerialNo", record.getMeterSerialNo());
					put("meterType", record.getMeterType());
					put("meterOwnerShip", record.getMeterOwnerShip());
					put("meterStatus", record.getMeterStatus());
					put("createdBy", record.getCreatedBy());
					if(record.getAccount()!=null){
					put("accountId", record.getAccount().getAccountId());
					put("accountNo", record.getAccount().getAccountNo());
					String personName = "";
					personName=personName.concat(record.getAccount().getPerson().getFirstName());
					if(record.getAccount().getPerson().getLastName()!=null){
						personName=personName.concat(" ");
						personName=personName.concat(record.getAccount().getPerson().getLastName());
					}
					put("personName",personName);
					put("property_No",entityManager.createNamedQuery("ElectricityMetersEntity.proPertyNameById").setParameter("propertyId", record.getAccount().getPropertyId()).getResultList());
					
					}
				}
			});

		}
		return response;
		/*List<?> list = entityManager.createNamedQuery("ElectricityMetersEntity.findAll").getResultList();
		Map<String, Object> rateMaster;
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			rateMaster = new HashMap<String, Object>();
			if((String)values[6]!=null && (String)values[5]!=null)
			{

				rateMaster.put("elMeterId", (Integer)values[0]);
				rateMaster.put("typeOfServiceForMeters", (String)values[1]);
				rateMaster.put("meterSerialNo", (String)values[2]);
				rateMaster.put("meterType", (String)values[3]);
				rateMaster.put("meterOwnerShip", (String)values[4]);
				rateMaster.put("meterStatus", (String)values[5]);
				rateMaster.put("createdBy", (String)values[6]);
				rateMaster.put("accountId", (Integer)values[7]);
				rateMaster.put("accountNo", (String)values[8]);
				rateMaster.put("personName", (String)values[9] +" "+(String)values[10]);
				rateMaster.put("property_No", (String)values[11]);
				result.add(rateMaster);
			}
		}
		return result;*/
		
		
	}

	@Override
	public void setMetersStatus(int elMeterId, String operation,HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();
			
			ElectricityMetersEntity electricityMetersEntity = find(elMeterId);

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("ElectricityMetersEntity.setMetersStatus").setParameter("status", "Active").setParameter("elMeterId", elMeterId).executeUpdate();
				out.write("Meter is activated");
			}
			else
			{
				if(electricityMetersEntity.getAccount()!= null && electricityMetersEntity.getAccount().getAccountStatus().equals("Active")){
					out.write("You can't inactive,bcz this meter assigned account status is active");
				}else{
					entityManager.createNamedQuery("ElectricityMetersEntity.setMetersStatus").setParameter("status", "Inactive").setParameter("elMeterId", elMeterId).executeUpdate();
					out.write("Meter is de-activated");
				}
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("ElectricityMetersEntity.findPersonForFilters").getResultList();
		return details;
	}

	@Override
	public ElectricityMetersEntity getMeterByMeterSerialNo(String meterSerialNo) {
		try {
		   return entityManager.createNamedQuery("ElectricityMetersEntity.getMeterByMeterSerialNo",ElectricityMetersEntity.class).setParameter("meterSerialNo", meterSerialNo).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ElectricityMetersEntity getMeter(int accountId, String typeOfService) {
		try {
			   return entityManager.createNamedQuery("ElectricityMetersEntity.getMeter",ElectricityMetersEntity.class).setParameter("accountId", accountId).setParameter("typeOfService", typeOfService).setMaxResults(1).getSingleResult();
			} catch (Exception e) {
				return null;
			}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ElectricityMetersEntity> findALLBillMeters() {
		return entityManager.createNamedQuery("ElectricityMetersEntity.findAll").getResultList();
	}

	@Override
	public List<?> proPertyName() {
		return entityManager.createNamedQuery("ElectricityMetersEntity.proPertyName").getResultList();
	}
}
