package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;



import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.GenericService;

public interface PropertyService extends GenericService<Property>{

	public abstract List<Property> findAll();

	List<Property> getAll();
	
	public List<Integer> getPropertyId(String propertyName); 
	
	public String getProprtyNoBasedOnPropertyId(int propertyId); 
	public int getIDProperty(String propertyName);
	public List<Property> getPropertyNo();
	
	public List<Property> getPropertyNoBasedOnTenant();
	public List<Property> getPropertyNameForFilter();

	public abstract Property getBeanObject(Property property, String string,
			Map<String, Object> map);

	List<Property> findAllforParking();

	public abstract List<Property> getAllPropetyList(List<Property> list);

	public abstract List getFamilyMembersBasedOnPersonId(int personId);
	
	String getPropertyNameBasedOnPropertyId( int propertyId );
	
	int getBlockIdBasedOnPropertyId( int propertyId );

	public void uploadPropertyOnId(int propertyId, Blob blob,String docType);

	public abstract List<Property> getPropertyListBasedOnPropertyNo(String propertyNo);

	public abstract List<?> getPossesionDate(int propertyId);

	public  abstract List<Property>  findAllNotification();
	
	
	public Property getPropertyEntityBasedOnProID(int propertyId);

	
}