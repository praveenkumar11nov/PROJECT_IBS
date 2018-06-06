package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerPreviousOrder;
import com.bcits.bfm.service.helpDeskManagement.CustomerPreviousOrderService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CustomerPreviousOrderServiceImpl extends GenericServiceImpl<CustomerPreviousOrder> implements CustomerPreviousOrderService {

	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> findAllData(int personId) {
		return readItems(entityManager.createNamedQuery("CustomerPreOrder.findEmail").setParameter("personId", personId).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List readItems(List<?> department)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> departmentData = null;
		for (Iterator<?> iterator = department.iterator(); iterator.hasNext();)
		{
			 final CustomerPreviousOrder values = (CustomerPreviousOrder) iterator.next();
			 departmentData = new HashMap<String, Object>();
			
			 departmentData.put("cpid", values.getCpid());
			 departmentData.put("cid", values.getCid());
			 departmentData.put("cusName", values.getCusName());
			 departmentData.put("cusNum", values.getCusNum());
			 departmentData.put("storeId", values.getStoreId());
			 departmentData.put("storeName", values.getStoreName());
			 java.util.Date d=values.getCreated_date();
			 
			 departmentData.put("createdDate", new java.sql.Date(d.getTime()));
			 departmentData.put("orderType", values.getOrderType());
			 departmentData.put("orderstatus", values.getOrderstatus());
			
		     result.add(departmentData); 
		 }
		 return result;
	}
}
