package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.Owner;
import com.bcits.bfm.service.GenericService;

public interface OwnerService extends GenericService<Owner> {

	public abstract List<Owner> findAll();
	
	public int getOwnerId(int personId);
	
	public  List<Integer> getPropertyIdBasedOnownerId(int ownerId);

	public  Integer findBaseOnOwnerId(Integer object);

	public  List<Integer> getALLPersonIds();
	
	
}