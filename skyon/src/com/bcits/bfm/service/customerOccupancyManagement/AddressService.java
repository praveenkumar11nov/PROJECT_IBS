package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.Address;
import com.bcits.bfm.service.GenericService;

public interface AddressService extends GenericService<Address>
{

	/**
	 * Find all Address entities.
	 * 
	 * @return List<Address> all Address entities
	 */
	public List<?> findAllAddressesBasedOnPersonID(int personId);

	public Address getAddressObject(Map<String, Object> map, String type,
			Address address);

	public Integer getAddressIdBasedOnCreatedByAndLastUpdatedDt(String createdBy, Timestamp lastUpdatedDt);

	public void updateContactId(int addressId, int contactId);

	public List<Address> checkForUniquePrimary(int personId, String addrPrimary);

	public String getAddressPrimary(int addressId);

}