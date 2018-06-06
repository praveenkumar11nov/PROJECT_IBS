package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerPreviousOrder;
import com.bcits.bfm.service.GenericService;

public interface CustomerPreviousOrderService extends GenericService<CustomerPreviousOrder> {

	public List<CustomerEntity> findAllData(int personId);

}
