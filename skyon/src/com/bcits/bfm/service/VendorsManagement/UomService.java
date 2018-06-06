package com.bcits.bfm.service.VendorsManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.service.GenericService;

public interface UomService extends GenericService<UnitOfMeasurement> 
{
	public List<UnitOfMeasurement> findAll();
	public List<UnitOfMeasurement> findUomBasedOnItemId(int imid);
	public UnitOfMeasurement getBeanObject(Map<String, Object> map, String string,UnitOfMeasurement uom,int id);
	public List<?> getUom(int itemId);
	public List<?> getCode(int itemId);
	public String getBaseUomBasedOnId(int UomId);
	public int updateUomStatus(int id, String newStatus);
}
