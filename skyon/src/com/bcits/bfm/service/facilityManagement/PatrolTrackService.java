package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.PatrolTracks;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;

public interface PatrolTrackService extends GenericService<PatrolTracks> {
	
	public List<PatrolTracks> findAll();
	
	public PatrolTracks getPatrolTrackInstanceById(int ptId);
	
	public List<String> getAllPatrolTrackNames(); 
	
	public int getPatrolTrackIdBasedOnName(String ptName);
	
	public void setPatrolTracktStatus(int ptId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	
	public List<String> getStatusList(); 
	
	public List<String> getAllPatrolTrackNames(String ptName);
	
	public PatrolTracks getTrackObject(Map<String, Object> map, String type,
			PatrolTracks patrolTracks);
	
	public List<PatrolTracks> findActiveTracks();
	
	public String getStatusBasedOnId(int ptId);

	List<Integer> getAllPatrolRoles();
	
	

	

}
