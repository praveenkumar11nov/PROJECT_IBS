package com.bcits.bfm.service.facilityManagement;


import java.sql.Blob;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.WrongParking;
import com.bcits.bfm.service.GenericService;

/**
 * @author Manjunath Kotagi
 *
 */
public interface WrongParkingService extends GenericService<WrongParking> {

	/**
	 * @return Wrong Parking Details
	 */
	public abstract List<WrongParking> findAll();

	/**
	 * @param str : Registration number
	 * @return Tag number
	 */
	public abstract String getTagNumbers(String str);

	/**
	 * @param find : Wrong Parking Object
	 */
	public abstract void freeAllocation(WrongParking find);

	/**
	 * @param wpId : Wrong Parking ID
	 * @param operation : Wrong Parking Status
	 * @param response : none
	 * @param locale : System Locale
	 */
	public abstract void setWrongParkingStatus(int wpId, String operation,HttpServletResponse response, Locale locale);

	/**
	 * @param map : Wrong Parking Details
	 * @return Wrong Parking Details
	 */
	public abstract WrongParking getBeanObject(Map<String, Object> map);

	//public abstract void uploadImage(int wpId, Blob blob, String docType);
	public void uploadImage(int wpId, Blob blob);

	public abstract List<?> findAllList();

}