package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.PatrolTrackPoints;
import com.bcits.bfm.model.PatrolTracks;
import com.bcits.bfm.service.GenericService;

public interface PatrolTrackPointService extends GenericService<PatrolTrackPoints> {
	
	public List<PatrolTrackPoints> findAll();
	
	public List<PatrolTrackPoints> findPatrolPointExceptId( int ptpId );
	
	public PatrolTrackPoints getPatrolTrackPointInstanceById(int ptpId);

	public List<PatrolTrackPoints> findAllBasedOnPtId(int ptId);
	
	public void setPatrolTracktPointStatus(int ptpId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	
	public List<Integer> getSequences(); 
	
	public List<String> getTimeIntervals(); 
	
	public List<String> getStatus(); 
	
	public List<Integer> getPtId(int ptId);
	
	public String getStatusBasedOnId(int ptpId);
	
	
}
