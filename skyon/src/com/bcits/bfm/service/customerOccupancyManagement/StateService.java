package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ProjectLocation;
import com.bcits.bfm.model.State;
import com.bcits.bfm.service.GenericService;

public interface StateService extends GenericService<State>
{

	public List<State> findAll();

	public Integer getIdBasedOnStateName(String string);

	public List<State> findIdbyName(String string);
	
	public List<State> findNamebyId(int id);

	public List<ProjectLocation> findAllProjectLocation();

	public List<State> getStateListAfterPassingStateName(String stateName,
			int countryId);

}