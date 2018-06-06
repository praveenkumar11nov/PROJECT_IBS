package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.FeedbackChild;
import com.bcits.bfm.service.helpDeskManagement.Feedback_ChildService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class Feedback_ChildServiceImpl extends GenericServiceImpl<FeedbackChild> implements Feedback_ChildService {

	@Override
	public List<Integer> getAllChildIdList() {
		return entityManager.createNamedQuery("FeedbackChild.getAllIds",Integer.class).getResultList();
	}

	@Override
	public List<FeedbackChild> getChildByMasterId(int fMaster_d) {
		return (List<FeedbackChild>) entityManager.createNamedQuery("FeedbackChild.getChildByMasterId").setParameter("fMaster_d",fMaster_d).getResultList();	
		}

	

}
