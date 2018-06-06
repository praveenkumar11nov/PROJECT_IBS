package com.bcits.bfm.serviceImpl.serviceMasterManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.FRChange;
import com.bcits.bfm.service.serviceMasterManagement.FRChangeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class FRChangeServiceImpl extends GenericServiceImpl<FRChange> implements FRChangeService {

	@Override
	public List<?> findAll() {
		return getAllDetails(entityManager.createNamedQuery("FRChange.findAll").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getAllDetails(List<?> frChangeDetails){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> meterStatusMap = null;
		 for (Iterator<?> iterator = frChangeDetails.iterator(); iterator.hasNext();)
			{
				final FRChange frChange = (FRChange) iterator.next();
				meterStatusMap = new HashMap<String, Object>();
				
				meterStatusMap.put("frId",frChange.getFrId());
				meterStatusMap.put("accountId",frChange.getAccountId());
				meterStatusMap.put("accountNo",frChange.getAccount().getAccountNo() );
				meterStatusMap.put("billDate",frChange.getBillDate() );
				meterStatusMap.put("presentReading",frChange.getPresentReading());
				meterStatusMap.put("finalReading",frChange.getFinalReading());
				meterStatusMap.put("status", frChange.getStatus());
				meterStatusMap.put("typeOfService", frChange.getTypeOfService());
				meterStatusMap.put("description", frChange.getDescription());
				meterStatusMap.put("presentReadingDg", frChange.getPresentReadingDg());
				meterStatusMap.put("finalReadingDg", frChange.getFinalReadingDg());
				String personName = "";
				personName = personName.concat(frChange.getAccount().getPerson().getFirstName());
				if(frChange.getAccount().getPerson().getLastName()!=null && !frChange.getAccount().getPerson().getLastName().equals("")){
					personName = personName.concat(" ");
					personName = personName.concat(frChange.getAccount().getPerson().getLastName());
				}
				meterStatusMap.put("personName", personName);
				
			    result.add(meterStatusMap);
	     }
     return result;
	}

	
	@SuppressWarnings("unchecked")
	public List<Account> findAccountNumbers() {
		List<Account> details = entityManager.createNamedQuery("FRChange.findAccountNumbers").getResultList();
		return details;
	}
	
	public List<?> findServiceType() {
		return getAllServiceTypes(entityManager.createNamedQuery("FRChange.findServiceType").getResultList());
	}

	
	@SuppressWarnings("rawtypes")
	private List<?> getAllServiceTypes(List resultList){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 Map<String, Object> meterStatusMap = null;
		 for (Iterator<?> iterator = resultList.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				meterStatusMap = new HashMap<String, Object>();
				
				meterStatusMap.put("typeOfService", (String)values[0]);
				meterStatusMap.put("accountId", (Integer)values[1]);
				
			result.add(meterStatusMap);
	     }
     return result;
	}

	@Override
	public int findBillDateAndPreReading(int acId, String serviceType) {
		return (Integer)entityManager.createNamedQuery("FRChange.findBillDateAndPreReading").setParameter("acId", acId).setParameter("serviceType", serviceType).getSingleResult();
	}

	@Override
	public String getPresentReading(int elBillId) {
		return (String)entityManager.createNamedQuery("FRChange.getPresentReading").setParameter("elBillId", elBillId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> commonFilterForAccountNumbersUrl() {
		return new HashSet<String>(entityManager.createNamedQuery("FRChange.commonFilterForAccountNumbersUrl").getResultList());
	}

	@Override
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("FRChange.findPersonForFilters").getResultList();
		return details;
	}

	@Override
	public String getDGPresentReading(int elBillId) {
		// TODO Auto-generated method stub
		 return (String)entityManager.createNamedQuery("FRChange.getDGPresentReading").setParameter("elBillId", elBillId).getSingleResult();
	}
	
}
