package com.bcits.bfm.service.electricityBillsManagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.BillingConfiguration;
import com.bcits.bfm.service.GenericService;

/**
 * @author user51
 *
 */
public interface BillingConfigurationService extends GenericService<BillingConfiguration> {

	/**
	 */
	List<?> findALL();
	/**
	 */
	void billingSettingsStatus(final int id, final String operation,final HttpServletResponse response);
	/**
	 */
	String checkForDuplicate(String configName);

}
