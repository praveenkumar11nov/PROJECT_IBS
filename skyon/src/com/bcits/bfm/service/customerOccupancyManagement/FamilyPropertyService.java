package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.GenericService;

public interface FamilyPropertyService extends GenericService<FamilyProperty>{
	
	public FamilyProperty getFamilyPropertyObject(Map<String, Object> map, String type,
			FamilyProperty familyProperty, int personId);
	
	public FamilyProperty getFamilyPropertyBasedOnId(int familyPropertyId);
	
	public int getFamilyIdBasedOnPersonId(int personId);
	
	public List<?> findAllFamilyPropertyBasedOnPersonID(int personId);
	
	public int getProprtyIdBasedOnPropertyNo(int propertyNo); 
	
	public List<?> findAll();
	
	List<?> getPropertyNoBasedOnOwners();
	
	int getOwnerIdBasedOnFamPropertyId(int famPropertyId);
	
	public int getProprtyIdBasedOnFamPropertyId(int famPropertyId);
	
	
	


}
