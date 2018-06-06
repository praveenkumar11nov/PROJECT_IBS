package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.service.facilityManagement.AssetOwnerShipService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetOwnerShipServiceImpl extends GenericServiceImpl<AssetOwnerShip> implements AssetOwnerShipService
{
	private static final Log logger = LogFactory.getLog(AssetOwnerShipServiceImpl.class);

	/*@Autowired
	private AssetOwnerShipService assetOwnerShipService;*/

	/*@Autowired
	private GenericService<AssetOwnerShip> genericService;*/

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getByAllField() {
		return entityManager.createNamedQuery("AssetOwnerShip.getByAllField").getResultList();

	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() {
		List<AssetOwnerShip> ownerShipList =  entityManager.createNamedQuery("AssetOwnerShip.findAllList").getResultList();		
		List<Map<String, Object>> selectedFieldResponse = new ArrayList<Map<String, Object>>();   
		for (final Iterator<?> i = ownerShipList.iterator(); i.hasNext();) {
				{
			  selectedFieldResponse.add(new HashMap<String, Object>() {{
				final Object[] values = (Object[])i.next();
				put("aoId", (Integer)values[0]);
				put("assetId", (Integer)values[1]);
				put("assetName", (String)values[2]);
				put("ownerShipName", (String)values[3]+" "+(String)values[4]);
				put("ownerShip", (Integer)values[5]);
				put("maintainanceOwner", (Integer)values[6]);
				put("maintainanceOwnerName", (String)values[7]+" "+(String)values[8]);
				
				if((Date)values[9]!=null){
					java.util.Date aoStartDateUtil = (Date)values[9];
					java.sql.Date aoStartDateSql = new java.sql.Date(aoStartDateUtil.getTime());
					put("aoStartDate",aoStartDateSql);
				}else{
					logger.info("Ownership date not exists");
					put("aoStartDate","");
				}
				
				if((Date)values[10]!=null){
					java.util.Date aoEndDateUtil = (Date)values[10];
					java.sql.Date aoEndDateSql = new java.sql.Date(aoEndDateUtil.getTime());
					put("aoEndDate",aoEndDateSql);
				}else{
					put("aoEndDate","");
				}
				
				put("createdBy", (String)values[11]);
				put("lastUpdatedBy", (String)values[12]);
				
				java.util.Date lastUpdatedDtUtil = ((Date)values[13]);
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
	}
		return selectedFieldResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllOwnership() {
	
		return entityManager.createNamedQuery("AssetOwnerShip.findAllList").getResultList();
	}
}
