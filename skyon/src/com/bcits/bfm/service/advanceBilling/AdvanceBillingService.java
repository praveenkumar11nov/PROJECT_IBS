package com.bcits.bfm.service.advanceBilling;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.service.GenericService;

public interface AdvanceBillingService extends GenericService<AdvanceBill>{

	List<AdvanceBill> findAll();

	void setApproveBill(int elBillId, String operation,
			HttpServletResponse response);

	List<?> findPersonForFilters();

	List<?> findAdvanceBillData();
	
}
