package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.StaffAttendanceGateOutAlert;
import com.bcits.bfm.service.facilityManagement.StaffAttendanceGateOutAlertService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class StaffAttendanceGateOutAlertServiceImpl extends GenericServiceImpl<StaffAttendanceGateOutAlert> implements StaffAttendanceGateOutAlertService {


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StaffAttendanceGateOutAlert> findAll() {
		
		return entityManager.createNamedQuery("StaffAttendanceGateOutAlert.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonAccessCards> getPersonAccessCardsByAcid(int personId) {
	
		return entityManager.createNamedQuery("PersonAccess.Size").setParameter("acId", personId).getResultList();
	}

}
