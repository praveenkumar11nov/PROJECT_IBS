package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Vehicles;
import com.bcits.bfm.service.GenericService;

/**
 * @author Manjunath Kotagi
 *
 */
public interface VehicleService extends GenericService<Vehicles> {
	
	/**
	 * @return Vehicle Details
	 */
	public abstract List<Vehicles> findAll();

	/**
	 * @param map : Vehicle Details
	 * @return Vehicle Object
	 */
	public abstract Vehicles getBeanObject(Map<String, Object> map);

	/**
	 * @param vehicles : Vehicle Object
	 * @return Vehicle Details
	 */
	public abstract Vehicles addVehicles(Vehicles vehicles);

	/**
	 * @param vhId : Vehicle id
	 * @param status : Vehicle status
	 * @param response : none
	 */
	public abstract void setvehicleStatus(int vhId, String status,HttpServletResponse response);

	/**
	 * @param vregNumber : Vehicle No
	 * @return Vehicle Details
	 */
	public abstract List<Vehicles> getVehicleId(String vregNumber);

	/**
	 * @param propertyId : proprty id
	 * @return Vehicle Details
	 */
	public abstract List<?> getallVehicles(int propertyId);

	/**
	 * @param personId : person Id
	 * @return contact number
	 */
	public abstract String getContacts(int personId);

	public abstract List<?> findAllVehicals();

}