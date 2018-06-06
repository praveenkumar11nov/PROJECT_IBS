package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.service.GenericService;

public interface TenantPropertyService extends GenericService<TenantProperty>{
	
	public List<?> findAllTenantPropertyBasedOnPersonID(int personId);
	
	public TenantProperty getTenantPropertyObject(Map<String, Object> map,HttpServletRequest request, String type,
			TenantProperty tenantProperty, int personId);
	
	public TenantProperty getTenantPropertyBasedOnId(int tenantPropertyId);
	
	public int getProprtyIdBasedOnPropertyNo(int propertyNo); 
	
	public int getProprtyIdBasedOntenPropertyId(int tenId);
	
	public int getTenantIdBasedOnPersonId(int personId);

	List<TenantProperty> findAll();

	public List<TenantProperty> findTenantPropertyBasedOnProperty(Property record);

}
