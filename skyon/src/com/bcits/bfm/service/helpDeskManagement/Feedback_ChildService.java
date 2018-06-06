package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.FeedbackChild;
import com.bcits.bfm.service.GenericService;



public interface Feedback_ChildService extends GenericService<FeedbackChild>{
	
	public List<Integer> getAllChildIdList();
	public List<FeedbackChild> getChildByMasterId(int id);

}
