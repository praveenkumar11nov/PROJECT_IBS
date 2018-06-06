package com.bcits.bfm.serviceImpl;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BMSSettingsEntity;
import com.bcits.bfm.service.BMSSettingsService;

@Repository
public class BMSSettingsServiceImpl extends  GenericServiceImpl<BMSSettingsEntity> implements BMSSettingsService {
	static Logger logger = LoggerFactory.getLogger(BMSSettingsServiceImpl.class);

	@SuppressWarnings("serial")
	@Override
	public List<?> readBMSSettingsData() {
		
		List<?> bmsSettingsList = entityManager.createNamedQuery("BMSSettingsEntity.findAll").getResultList();

		List<Map<String, Object>> bmsSettings = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = bmsSettingsList.iterator(); i.hasNext();) {
			bmsSettings.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("bmsSettingsId", (Integer)values[0]);
					put("dept_Id", (Integer)values[1]);
					put("dept_Name", (String)values[2]);
					put("dn_Id", (Integer)values[3]);
					put("dn_Name", (String)values[4]);
					put("bmsElements", (String)values[5]);
					put("paramValue", (Integer)values[6]);
					put("createdBy", (String)values[7]);					
					put("lastUpdatedBy", (String)values[8]);
					put("lastUpdatedDT", (Timestamp)values[9]);
					put("status", (String)values[10]);
					
				}
			});
		}
		return bmsSettings;
	}

	@Override
	public void setBMSSettingsStatus(int bmsSettingsId, String operation,
			HttpServletResponse response) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("BMSSettingsEntity.setBMSSettingsStatus").setParameter("status", "Active").setParameter("bmsSettingsId", bmsSettingsId).executeUpdate();
				out.write("BMS Settings Activated");
			}
			else
			{
				entityManager.createNamedQuery("BMSSettingsEntity.setBMSSettingsStatus").setParameter("status", "Inactive").setParameter("bmsSettingsId", bmsSettingsId).executeUpdate();
				out.write("BMS Settings De-Activated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<?> findAllFilter() {
		
		return entityManager.createNamedQuery("BMSSettingsEntity.findAll").getResultList();
	}

	
	@Override
	public List<?> findTrendLogIds() {
		return entityManager.createNamedQuery("BMSSettingsEntity.findTrendLogIds").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BMSSettingsEntity> getHydroPneumaticPumpId() {
		
		return entityManager.createNamedQuery("BMSSettingsEntity.getHydroPneumaticPumpId").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BMSSettingsEntity>  getVentillationId() {
		return entityManager.createNamedQuery("BMSSettingsEntity.getVentillationId").getResultList();
	}

	@Override
	public List<?> readTrendLogNamesUniqe() {
		return entityManager.createNamedQuery("BMSSettingsEntity.readTrendLogNamesUniqe").getResultList();
	}

	@Override
	public List<?> readTrendLogIdsUniqe() {
		return entityManager.createNamedQuery("BMSSettingsEntity.readTrendLogIdsUniqe").getResultList();
	}

	@Override
	public List<?> getSeweragePlantIds() {
		return entityManager.createNamedQuery("BMSSettingsEntity.getSeweragePlantIds").getResultList();
	}

	@Override
	public List<?> getFightFightingIds() {
		return entityManager.createNamedQuery("BMSSettingsEntity.getFightFightingIds").getResultList();
	}

	@Override
	public List<?> getDGSetIds() {
		return entityManager.createNamedQuery("BMSSettingsEntity.getDGSetIds").getResultList();
	}

	@Override
	public List<?> getLiftElevatorIds() {
		return entityManager.createNamedQuery("BMSSettingsEntity.getLiftElevatorIds").getResultList();
	}

	@Override
	public List<?> readTrendLogIdWithDeptDesig() {
		
		return entityManager.createNamedQuery("BMSSettingsEntity.readTrendLogIdWithDeptDesig").getResultList();
	}

	@Override
	public List<?> getYearWisedetails() {
		return entityManager.createNamedQuery("BMSDashboardEntity.getYearWisedetails").getResultList();
	}

	@Override
	public List<?> monthWisedetails() {
		return entityManager.createNamedQuery("BMSDashboardEntity.monthWisedetails").getResultList();
	}

	@Override
	public List<?> getAccountId(int blockId,Date months) {
		  Calendar cal = Calendar.getInstance();
			cal.setTime(months);
			int month = cal.get(Calendar.MONTH);
			int montOne = month +1;
			int year = cal.get(Calendar.YEAR);
			return entityManager.createNamedQuery("BMSDashboardEntity.getUnits").setParameter("month",montOne).setParameter("year",year).setParameter("blockId",blockId).getResultList();
	}


}
