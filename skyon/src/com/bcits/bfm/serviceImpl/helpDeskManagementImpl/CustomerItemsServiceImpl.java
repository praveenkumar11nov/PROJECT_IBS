package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerItemsEntity;
import com.bcits.bfm.service.helpDeskManagement.CustomerItemsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;




@Repository
public class CustomerItemsServiceImpl extends GenericServiceImpl<CustomerItemsEntity> implements CustomerItemsService {


	@Override
	public List<?> readAllCustomerItems(int cid) {
		
		
			return readItems(entityManager.createNamedQuery("CustomerItems.readAllCustomerItems").setParameter("cid", cid).getResultList());
		}
		
		@SuppressWarnings("rawtypes")
	private List readItems(List<?> department)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> departmentData = null;
		for (Iterator<?> iterator = department.iterator(); iterator.hasNext();)
		{
			 final CustomerItemsEntity values = (CustomerItemsEntity) iterator.next();
			 departmentData = new HashMap<String, Object>();
			 departmentData.put("ccid",values.getCcid());
			 departmentData.put("cid", values.getCid());
				
			 departmentData.put("itemName",values.getItemName());
			 departmentData.put("itemQuantity", values.getItemQuantity());
			 departmentData.put("itemPrice",values.getItemPrice());
			 departmentData.put("itemTotalPrice", values.getItemTotalPrice());
			 departmentData.put("uom", values.getUom());
		//	 departmentData.put("itemsList", values.getCustomerEntity().getItemsList());
		     result.add(departmentData); 
		 }
		 return result;
	}

		@SuppressWarnings("unchecked")
		@Override
		public List<CustomerEntity> findEmail(String custEmail) {
			return entityManager.createNamedQuery("Customer.findEmail").setParameter("custEmail", custEmail).getResultList();
		}

		
		

}

