package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class UomServiceImpl extends GenericServiceImpl<UnitOfMeasurement> implements UomService
{
	static Logger logger = LoggerFactory.getLogger(UomServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<UnitOfMeasurement> findAll() 
	{
		List<UnitOfMeasurement> uom = entityManager.createNamedQuery("UOM.findAll").getResultList();
		return uom;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<UnitOfMeasurement> findUomBasedOnItemId(int imid) 
	{
		return entityManager.createNamedQuery("Uom.findUomBasedOnItemId").setParameter("imid",imid).getResultList();
	}

	@Override
	public UnitOfMeasurement getBeanObject(Map<String, Object> map,String type, UnitOfMeasurement uom,int id)
	{
		@SuppressWarnings("unused")
		String username = (String) SessionData.getUserDetails().get("userID");
		if(type.equals("save"))
		{
			if(map.get("uomConversion") instanceof Integer)
			{
				Integer i = (Integer)map.get("uomConversion");
				Double d = i.doubleValue();
				uom.setUomConversion(d);
			}
			else if(map.get("uomConversion") instanceof Double)
			{
				Double i = (Double)map.get("uomConversion");
				uom.setUomConversion(i);
			}
			uom.setImId(id);
			uom.setUom((String)map.get("uom"));
			uom.setCode((String)map.get("code"));
			uom.setBaseUom((String)map.get("baseUom"));		
			uom.setStatus("Created");
			logger.info("Values Saved");
		}
		else if(type.equals("update"))
		{
			logger.info("Inside Update() in Impl");
			uom.setUomId((int)map.get("uomId"));
			uom.setImId(id);
			uom.setUom((String)map.get("uom"));
			uom.setCode((String)map.get("code"));
			uom.setBaseUom((String)map.get("baseUom"));
			uom.setStatus((String)map.get("status"));
			uom.setUomConversion((Double.parseDouble(map.get("uomConversion").toString())));
			logger.info("Values Update");
		}
		return uom;
	}

	

	@Override
	public List<?> getUom(int itemId) {
		return entityManager.createNamedQuery("UOM.getAllUom").setParameter("itemId", itemId).getResultList();
	}
	
	@Override
	public List<?> getCode(int itemId) {
		return entityManager.createNamedQuery("UOM.getAllCode").setParameter("itemId", itemId).getResultList();
	}

	@Override
	public String getBaseUomBasedOnId(int UomId) 
	{
		return entityManager.createNamedQuery("UOM.getBaseUomBasedOnId",String.class).setParameter("UomId", UomId).getSingleResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateUomStatus(int uomid, String uomStatus) 
	{
		return entityManager.createNamedQuery("UOM.updateUomStatus").setParameter("uomid", uomid).setParameter("uomStatus", uomStatus).executeUpdate();
	}

}
