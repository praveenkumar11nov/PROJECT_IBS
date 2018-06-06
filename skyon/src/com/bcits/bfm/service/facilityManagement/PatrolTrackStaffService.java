package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.PatrolTrackStaff;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.GenericService;

public interface PatrolTrackStaffService extends GenericService<PatrolTrackStaff>{
	
	public List<PatrolTrackStaff> findAll();
	
	public PatrolTrackStaff getPatrolTrackStaffInstanceById(int ptsId);
	
	public List<PatrolTrackStaff> findAllBasedOnPtId(int ptId);
	
	public void setPatrolTracktStaffStatus(int ptsId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale);
	
	public List<PatrolTrackStaff> findStaffRecord(int ptId,int acId,int staffId);

	public String getStatusBasedOnId(int ptsId);

	public Person getPersonBasedOnId(Integer integer);

	public List<?> getPatrolTrackStaffData();
	
	
}
