package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.HelpTopicEntity;
import com.bcits.bfm.service.GenericService;

public interface HelpTopicService extends  GenericService<HelpTopicEntity> {

	List<HelpTopicEntity> findAll();

	List<?> findUsers();

	void setHelpTopicStatus(int topicId, String operation, HttpServletResponse response);

	List<?> selectDistinctQueryForUsers(String className,String relationObject);

	List<?> getDistinctHelpTopics();

}
