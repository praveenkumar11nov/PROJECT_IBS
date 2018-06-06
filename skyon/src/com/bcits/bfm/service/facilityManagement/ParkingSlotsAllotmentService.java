package com.bcits.bfm.service.facilityManagement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.GenericService;

/**
 * @author Manjunath Kotagi
 *
 */
public interface ParkingSlotsAllotmentService extends GenericService<ParkingSlotsAllotment> {

	/**
	 * @return parking slot allotement details
	 */
	public abstract List<ParkingSlotsAllotment> findAll();
	/**
	 * @param pakingSlotAllocation : parking slot Object
	 * @return parking slot allotement details
	 */
	public abstract ParkingSlotsAllotment addParkingSlotsAllocation(ParkingSlotsAllotment pakingSlotAllocation);
	/**
	 * @param psaId : parking slot allotement Id
	 * @param operation : parking slot allotement status
	 * @param response : none
	 */
	public abstract void setParkingSlotAllocationStatus(int psaId,String operation, HttpServletResponse response);	
	/**
	 * @param psa : parking slot allotement object
	 */
	public abstract void freeAllocation(ParkingSlotsAllotment psa);
	/**
	 * @param i : parking slot id
	 * @return parking slot allotement status
	 */
	public abstract String getStatus(int i);
	/**
	 * @param psaId : parking slot allotement Id
	 * @param status : parking slot allotement status
	 */
	public abstract void setAllocationStatusInParkingSlot(int psaId, String status);
	/**
	 * @param psaId : parking slot allotement Id
	 * @param string : parking slot allotement status
	 */
	public abstract void statusAfterTimeout(int psaId, String string);	
	/**
	 * @param psId : parking slot id
	 * @return parking slot allotement status
	 */
	public abstract String getStatusFromParkingSlot(int psId);
	/**
	 * @param i : parking slot id
	 * @param d : parking slot allotement start date
	 * @return
	 */
	public abstract String findBySlotId(int i, Date d);
	/**
	 * @param map : parking slot allotement details
	 * @return parking slot allotement object
	 */
	public abstract ParkingSlotsAllotment getBeanObject(Map<String, Object> map);
	/**
	 * Check parking slot allotement Expiry date
	 */
	public abstract void checkTimeout();
	/**
	 * @return list of Property
	 */
	public abstract List<Property> getPropertyId();
	
	public abstract List<?> findAllParkingSlot();
	
	public abstract List<ParkingSlotsAllotment> findAllParkingAllocationSlot();
	/**
	 * @param pakingSlotAllocation : parking slot required variables
	 */
	public abstract List<ParkingSlotsAllotment> getAllParkingSlots(int propertyId);
}