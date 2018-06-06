package com.bcits.bfm.service.facilityManagement;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.GenericService;

public interface OwnerPropertyService extends GenericService<OwnerProperty>
{
	public abstract List<OwnerProperty> findAll();
	
	public OwnerProperty getOwnerPropertyObject(Map<String, Object> map, String type,
			OwnerProperty ownerProperty, int personId) ;

	Integer getOwnerIdBasedOnPersonId(int personId);

	List<?> findAllOwnerPropertyBasedOnPersonID(int personId);

	int getPropertyOwnerShipStatus(int propertyId);

	int checkPropertyAssigned(int proptertyNumber, int ownerId);

	public abstract List<OwnerProperty> getOwnerType(Property record);

	List<OwnerProperty> getOwnerPropertyBasedOnPropertyIdAndOwnerId(
			int propertyId);

	public  List<Object[]> getData();

}