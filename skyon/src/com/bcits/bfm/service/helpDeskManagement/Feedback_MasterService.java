package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.FeedbackChild;
import com.bcits.bfm.model.FeedbackMaster;
import com.bcits.bfm.service.GenericService;


public interface Feedback_MasterService extends GenericService<FeedbackMaster>{

	public List<Integer> getAllMasterIdList();
	public List<FeedbackMaster> getMasterByTicketId(int id);
}
