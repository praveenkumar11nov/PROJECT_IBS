package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StaffAttendanceSummary;
import com.bcits.bfm.service.facilityManagement.StaffAttendanceSummaryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StaffAttendanceSummaryServiceImpl extends GenericServiceImpl<StaffAttendanceSummary> implements StaffAttendanceSummaryService{

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StaffAttendanceSummary> findAll() {
		
		return entityManager.createNamedQuery("StaffAttendanceSummary.findAll").getResultList();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StaffAttendanceSummary> findAllStaffDetails() {
		
		return entityManager.createNamedQuery("StaffAttendanceSummary.findAllStaffDetails").getResultList();
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getInTimeList() {
		
		return entityManager.createNamedQuery("StaffAttendanceSummary.getInTimeList").getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getOutTimeList() {
		
		return entityManager.createNamedQuery("StaffAttendanceSummary.getOutTimeList").getResultList();
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getDistinctDepartmentId() {
		return (List<Integer>) entityManager.createNamedQuery("StaffAttendanceSummary.getDistinctDepartmentId").getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getDistinctDesignationId() {
		return (List<Integer>) entityManager.createNamedQuery("StaffAttendanceSummary.getDistinctDesignationId").getResultList();
	}
	
}
