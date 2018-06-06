package com.bcits.bfm.service.helpDeskManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.Lostandfound;
import com.bcits.bfm.service.GenericService;

public interface LostandfoundService extends GenericService<Lostandfound>{

	List<Lostandfound> findAll();

	Lostandfound setParameters(Map<String, Object> model,Lostandfound lostandfound,String userName);

	Blob getImage(int lostandfoundId);

	void uploadImageOnId(int lostandfoundId, Blob blob);

}
