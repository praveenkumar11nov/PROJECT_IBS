package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetServiceHistory;
import com.bcits.bfm.service.facilityManagement.AssetServiceHistoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetServiceHistoryServiceImpl extends GenericServiceImpl<AssetServiceHistory> implements AssetServiceHistoryService
{

	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() {
		
		List<AssetServiceHistory> serviceHistoryList =  entityManager.createNamedQuery("AssetServiceHistory.findAllList").getResultList();				
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Iterator<?> i = serviceHistoryList.iterator(); i.hasNext();) {
			
			response.add(new HashMap<String, Object>() {{
				final Object[] values = (Object[])i.next();

				put("ashId", (Integer)values[0]);
				put("assetId", (Integer)values[1]);
				put("assetName", (String)values[2]);
				if((Date)values[3]!=null){
					java.util.Date apsmDateUtil = (Date)values[3];
					java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
					put("ashDate", apsmDateSql);
					}else{
						put("ashDate", "");
					}
				
				put("problemDesc", (String)values[4]);
				put("serviceDesc",(String)values[5]);
				put("serviceType",(String)values[6]);
				put("servicedUnder",(String)values[7]);
				put("servicedBy",(String)values[8]);
				put("servicedByName",(String)values[9]+" "+(String)values[10]);
				put("createdBy", (String)values[11]);
				put("lastUpdatedBy", (String)values[12]);
				java.util.Date lastUpdatedDtUtil = (Date)values[13];
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
				put("amcId", (Integer)values[14]);
				put("costIncurred", (Integer)values[15]);
				
			}});
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAll() {
	
		return entityManager.createNamedQuery("AssetServiceHistory.findAllList").getResultList();
	}
	
	


}
