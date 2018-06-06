package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerItemsEntity;
import com.bcits.bfm.service.GenericService;

public interface CustomerItemsService extends GenericService<CustomerItemsEntity> {

	public List<?> readAllCustomerItems(int cid);

	public List<CustomerEntity> findEmail(String custEmail);
}
