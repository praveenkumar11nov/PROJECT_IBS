package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.CostCenter;
import com.bcits.bfm.service.GenericService;

public interface CostCenterService extends GenericService<CostCenter> 
{

	List<?> setResponse();

}

