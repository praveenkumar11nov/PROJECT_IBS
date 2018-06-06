package com.bcits.bfm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ManPowerNotification;
import com.bcits.bfm.patternMasterEntity.TransactionDetail;
import com.bcits.bfm.service.ManpowerNotificationService;

@Repository
public class ManpowerNotificationServiceImpl extends GenericServiceImpl<ManPowerNotification> implements ManpowerNotificationService {

	@SuppressWarnings("unchecked")
	@Override
	public List<ManPowerNotification> manpowerNotificationUnread(int designationId) {
		
		return getManPowerNotifyData(entityManager.createNamedQuery("ManPowerNotification.findAllList").setParameter("status1","Inprogress").getResultList(),designationId);

	}

	@SuppressWarnings({ "rawtypes", "serial" })
	private List getManPowerNotifyData(List<?> manPowerNotifyData,int designationId)
	{
		
		List<Map<String, Object>> manPowerData = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = manPowerNotifyData.iterator(); i.hasNext();) {
			final Object[] values = (Object[])i.next();
			if((Integer)values[5]==0 && designationId==(Integer)values[2]){
			manPowerData.add(new HashMap<String, Object>() {
				{
					put("manPowerId", (Integer)values[0]);
					put("urId", (Integer)values[1]);
					put("desigId", (Integer)values[2]);
					put("requestedBy", (String)values[3]);
					java.util.Date lastUpdatedDtUtil = (Date)(values[4]);
					java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
					put("requestedDate", lastUpdatedDtSql);
					put("manPowerStatus", (Integer)values[5]);
					
				}
			});
			}
		}
		return manPowerData;
	}
		
	@Override
	public void updateManPowerNotificationStatus(int manPowerId,int desigId) {
		entityManager.createNamedQuery("ManPowerNotification.updateManPowerNotificationStatus").setParameter("manPowerId", manPowerId).setParameter("desigId",desigId ).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionDetail> getDesignations(int transId) {
		
		return entityManager.createNamedQuery("ManPowerNotification.getDesignations").setParameter("transId", transId).getResultList();
	}

	@Override
	public int getDesignationId(String userId) {
		return (int) entityManager.createNamedQuery(
				"Users.getDesignationId").setParameter("urLoginName",userId).getSingleResult();
	}
	
}
