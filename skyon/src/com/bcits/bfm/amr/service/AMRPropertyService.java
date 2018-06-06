package com.bcits.bfm.amr.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AMRProperty;
import com.bcits.bfm.service.GenericService;
import com.bcits.bfm.util.DataSourceRequest;
import com.bcits.bfm.util.DataSourceResult;

/**
 * @author user51
 *
 */
public interface AMRPropertyService extends GenericService<AMRProperty> {
	/**
	 */
	List<?> getAllBlocks();
	/**
	 */
	List<?> getAllProperty();
	/**
	 */
	List<?> findALL();
	
	List<Object[]> findALLAmr();
	/**
	 */
	void setAMRStatus(int amrId, String operation,HttpServletResponse response);
	/**
	 */
	void activateAll(HttpServletResponse response);
	/**
	 */
	String getColumnName(int blockId, int propertyId);
	/**
	 */
	String getMeterNumber(Integer integer);
	/**
	 */
	String getPersonName(Integer integer);
	/**
	 */
	String getAccountNumber(Integer integer);
	/**
	 */
	String getMeterNumberByAccountNumber(String accountNumber);
	/**
	 * @param blockId 
	 */
	List<?> getAMRAccountDetails(Integer blockId);
	
	List<Object[]> getAMRDataDetails();
	
	DataSourceResult getList(DataSourceRequest dataSourceRequest);
	
}
