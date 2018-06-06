package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Set;

import com.bcits.bfm.model.StaffAttendanceSummary;
import com.bcits.bfm.service.GenericService;

public interface StaffAttendanceSummaryService extends GenericService<StaffAttendanceSummary> {
	
	public List<StaffAttendanceSummary> findAll();
	
	public List<StaffAttendanceSummary> findAllStaffDetails();
	
	public List<String> getInTimeList();
	
	public List<String> getOutTimeList();
	
	public List<Integer> getDistinctDepartmentId();
	
	public List<Integer> getDistinctDesignationId();
}
