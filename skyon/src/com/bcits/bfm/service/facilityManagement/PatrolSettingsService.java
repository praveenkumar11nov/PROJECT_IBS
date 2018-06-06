package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.PatrolSettings;
import com.bcits.bfm.model.PatrolTracks;
import com.bcits.bfm.service.GenericService;

public interface PatrolSettingsService extends GenericService<PatrolSettings>{
	
	public List<Integer> getAllRoleIds();
	
	public List<PatrolSettings> findAll();
	
	public void setPatrolRoleSettingStatus(int psId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);

}
