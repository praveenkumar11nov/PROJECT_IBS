package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PatrolTrackAlert;
import com.bcits.bfm.service.facilityManagement.PatrolTrackAlertService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PatrolTrackAlertServiceImpl extends GenericServiceImpl<PatrolTrackAlert> implements PatrolTrackAlertService{


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackAlert> findAll() {
		
		return entityManager.createNamedQuery("PatrolTrackAlert.findAll").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackAlert> findAllBasedOnPtId(int ptId) {
		
		return entityManager.createNamedQuery("PatrolTrackAlert.findAllBasedOnPtId",PatrolTrackAlert.class)
				.setParameter("ptId", ptId)
				.getResultList();
	}

}
