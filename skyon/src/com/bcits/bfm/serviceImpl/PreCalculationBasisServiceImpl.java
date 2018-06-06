package com.bcits.bfm.serviceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.PrepaidCalculationBasisEntity;
import com.bcits.bfm.model.PrepaidTxnChargesEntity;
import com.bcits.bfm.service.PreCalculationBasisService;

@Repository
public class PreCalculationBasisServiceImpl extends GenericServiceImpl<PrepaidCalculationBasisEntity> implements PreCalculationBasisService{

	@Override
	public List<PrepaidCalculationBasisEntity> readData(int serviceId) {
	try{
		return getData(entityManager.createNamedQuery("PrepaidCalculationBasisEntity.readALL").setParameter("serviceId", serviceId).getResultList());
	}catch(Exception e){
		e.printStackTrace();
	}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getData(List<PrepaidCalculationBasisEntity> prEntities){
		List<Map<String, Object>> resultList=new ArrayList<>();
		Map<String, Object> mapList=null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		for(PrepaidCalculationBasisEntity pCalculationBasisEntity:prEntities){
			mapList=new HashMap<>();
			mapList.put("cid", pCalculationBasisEntity.getCid());
			mapList.put("cbName", pCalculationBasisEntity.getCbName());
			mapList.put("createdBy", pCalculationBasisEntity.getCreatedBy());
			mapList.put("lastUpdatedBy", pCalculationBasisEntity.getLastUpdatedBy());
			mapList.put("lastUpdated_DT",dateFormat.format((Timestamp)pCalculationBasisEntity.getLastUpdatedDT()));
			//mapList.put("serviceId", pCalculationBasisEntity.getsId());
			resultList.add(mapList);
		}
		//System.out.println("resulitList "+resultList);
		return resultList;
	}

	@Override
	public List<?> getData(int serviceID) {
	try{
		return entityManager.createNamedQuery("PrepaidCalculationBasisEntity.readALL").setParameter("serviceId", serviceID).getResultList();
	}catch(Exception e){
		e.printStackTrace();
	}
		return null;
	}
}
