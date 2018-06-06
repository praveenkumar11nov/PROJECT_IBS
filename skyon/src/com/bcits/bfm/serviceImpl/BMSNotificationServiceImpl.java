package com.bcits.bfm.serviceImpl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BMSNotificationEntity;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.BMSNotificationService;
@Repository
public class BMSNotificationServiceImpl extends  GenericServiceImpl<BMSNotificationEntity>  implements BMSNotificationService {

	@SuppressWarnings("serial")
	@Override
	public List<?> readBMSNotificationData() {
		List<?> bmsNotificationList = entityManager.createNamedQuery("BMSNotificationEntity.findAll").getResultList();
		final SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		List<Map<String, Object>> bmsNotification = new ArrayList<Map<String, Object>>();
		 
		for (final Iterator<?> i = bmsNotificationList.iterator(); i.hasNext();) {
			bmsNotification.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					String personNames = "";
					put("bmsNotifyId", (Integer)values[0]);
					put("dept_Id", (Integer)values[1]);
					put("dept_Name", (String)values[2]);
					put("dn_Id", (Integer)values[3]);
					put("dn_Name", (String)values[4]);
					put("bmsElements", (String)values[5]);
					put("notificationDate",sdf.format(values[6]));
					put("bmsStatus", (String)values[7]);					
					put("smsStatus", (String)values[8]);
					put("mailStatus", (String)values[9]);
					
					String personIds=(String)values[10];
					String[] personIdArray = personIds.split(",");
					for(int  i=0;i<personIdArray.length;i++)
					{
						if(personIdArray[i]!=null && !(personIdArray[i].equalsIgnoreCase(""))){
						Person per=entityManager.createNamedQuery("Person.getPersonBasedOnId",Person.class).setParameter("personId", Integer.parseInt(personIdArray[i])).getSingleResult();
						personNames=personNames +per.getFirstName()+",";
						}
					}
					put("personNames", personNames);
				}
			});
		}
		return bmsNotification;
	
	}
}
