package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.DomesticProperty;
import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.PatrolTracks;
import com.bcits.bfm.service.GenericService;

public interface DomesticPropertyService extends GenericService<DomesticProperty> {
	
	public List<?> findAllDomesticPropertyBasedOnPersonID(int personId);
	
	public DomesticProperty getDomesticPropertyObject(Map<String, Object> map, String type,
			DomesticProperty domesticProperty, int personId);
	

	public int getDometsicIdBasedOnPersonId(int personId);
	
	public int getProprtyIdBasedOnPropertyNo(int propertyNo); 
	
	public DomesticProperty getDomesticPropertyInstanceById(int domesticId);
	
	public int getDomesticIdBasedOnPersonId(int personId);
	
	public List<DomesticProperty> findAll();
	
	public int getProprtyIdBasedOnDomesticPropertyId(int domesticPropertyId);

}
