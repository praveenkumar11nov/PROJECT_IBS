package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.AccessRepository;
import com.bcits.bfm.service.GenericService;

public interface AccessRepositoryService extends GenericService<AccessRepository> {
	
	public List<AccessRepository> findAll();
	
	public int getAccessRepositoryIdBasedOnName(String arName);
	
	public List<String> getRepositoryName();
	
	public List<AccessRepository> getRepositoryName(int arName);

}
