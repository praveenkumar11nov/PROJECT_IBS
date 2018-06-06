package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.FeedbackChild;
import com.bcits.bfm.model.FeedbackMaster;
import com.bcits.bfm.service.helpDeskManagement.Feedback_MasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class Feedback_MasterServiceImpl extends GenericServiceImpl<FeedbackMaster> implements Feedback_MasterService {

	@Override
	public List<Integer> getAllMasterIdList() {
		return entityManager.createNamedQuery("FeedbackMaster.getAllIds",Integer.class).getResultList();
	}

	

	@Override
	public List<FeedbackMaster> getMasterByTicketId(int ticketId) {
		return (List<FeedbackMaster>) entityManager.createNamedQuery("FeedbackMaster.getMasterByTicketId").setParameter("ticketId",ticketId).getResultList();	
	}

	
}
