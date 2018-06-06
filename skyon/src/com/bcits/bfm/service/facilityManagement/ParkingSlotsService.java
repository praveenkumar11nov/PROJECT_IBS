package com.bcits.bfm.service.facilityManagement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.service.GenericService;

/**
 * @author Manjunath Kotagi
 *
 */
public interface ParkingSlotsService extends  GenericService<ParkingSlots> {
	/**
	 * @return parking slot master details
	 */
	public abstract List<ParkingSlots> findAll();
	/**
	 * @param ps : parkingslot Object
	 * @return parking slot Object
	 */
	public abstract ParkingSlots addParkingSlots(ParkingSlots ps);
	/** 
	 * @param psId : parkingslot Object
	 * @param operation : parkingslot Status
	 * @param response : none
	 */
	public abstract void setParkingSlotStatus(int psId, String operation, HttpServletResponse response);
	/**
	 * @param map : parkingslot Details
	 * @return parkingslot Object
	 */
	public abstract ParkingSlots getBeanObject(Map<String, Object> map);
	/**
	 * @return parking slot Details for own and rented
	 */
	public abstract List<ParkingSlots> getAll();
	/**
	 * @param paramId : parkingslot Object
	 * @return parking slot allotement details based on parkingslot 
	 */
	//public abstract List<ParkingSlotsAllotment> readNestedReadUrl(int paramId);
	public abstract List<?> readNestedReadUrl(int paramId);
	/**
	 * @param blockId : BlockId
	 * @param response : none
	 * @throws IOException
	 */
	public abstract void getallowedSlots(int blockId,HttpServletResponse response) throws IOException;	
}