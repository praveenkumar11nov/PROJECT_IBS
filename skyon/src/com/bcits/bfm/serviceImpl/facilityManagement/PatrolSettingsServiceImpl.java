package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PatrolSettings;
import com.bcits.bfm.service.facilityManagement.PatrolSettingsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PatrolSettingsServiceImpl extends GenericServiceImpl<PatrolSettings> implements PatrolSettingsService{

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getAllRoleIds() {
		
		return entityManager.createNamedQuery("PatrolSettings.getAllRoleIds").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolSettings> findAll() {
		
		return entityManager.createNamedQuery("PatrolSettings.findAll").getResultList();
	}

	@Override
	public void setPatrolRoleSettingStatus(int psId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{

				entityManager.createNamedQuery("PatrolSettings.UpdateStatus")
				.setParameter("psStatus", "Active")
				.setParameter("psId", psId)
				.executeUpdate();
				out.write("Patrol Role Activated");
			}
			else
			{

				entityManager.createNamedQuery("PatrolSettings.UpdateStatus")
				.setParameter("psStatus", "InActive")
				.setParameter("psId", psId)
				.executeUpdate();
				out.write("Patrol Role Deactivated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		
	}
	

}
