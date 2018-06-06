package com.bcits.bfm.service.serviceMasterManagement;

import java.util.List;
import java.util.Set;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.FRChange;
import com.bcits.bfm.service.GenericService;

public interface FRChangeService extends GenericService<FRChange> {

	List<?> findAll();
	List<Account> findAccountNumbers();
	List<?> findServiceType();
    int findBillDateAndPreReading(int acId,String serviceType);
	String getPresentReading(int elBillId);
	Set<String> commonFilterForAccountNumbersUrl();
	List<?> findPersonForFilters();
	String getDGPresentReading(int elBillId);
}
