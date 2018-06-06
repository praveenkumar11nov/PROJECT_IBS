package com.bcits.bfm.service.electricityMetersManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.OtherInstallation;
import com.bcits.bfm.service.GenericService;

public interface UnmeteredPointService extends GenericService<OtherInstallation> {

	List<?> findAll();
	public abstract void setParameterStatus(int id, String operation, HttpServletResponse response);

	
}
