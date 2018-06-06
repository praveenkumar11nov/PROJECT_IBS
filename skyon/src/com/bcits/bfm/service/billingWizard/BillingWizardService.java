package com.bcits.bfm.service.billingWizard;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BillingWizardEntity;
import com.bcits.bfm.service.GenericService;

public interface BillingWizardService extends  GenericService<BillingWizardEntity> {

	List<?> findALL();
	
	List<?> findMeterNumbers(String serviceType);

	void approvedAccountNumber(int wizardId, HttpServletResponse response);

	List<?> findServiceSubRouteNames();

	List<?> getServiceRouteNames();

	Set<String> commonFilterForAccountNumbersUrl();

	Set<String> commonFilterForPropertyNoUrl();

	List<Integer> accountCheck(String service, String accountNumber);

	List<?> findPersonForFilters();

	Date getPossessionDate(int wizardId);
}
