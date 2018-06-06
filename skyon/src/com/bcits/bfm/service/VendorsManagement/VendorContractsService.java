package com.bcits.bfm.service.VendorsManagement;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.service.GenericService;

public interface VendorContractsService extends GenericService<VendorContracts>
{
	public List<VendorContracts> findAll();
	public List<?> setResponse();
	public void setVendorContractStatus(int vcId, String operation,HttpServletResponse response, MessageSource messageSource,Locale locale);
	public List<?> getVendorNamesAutoUrl();
	public List<?> checkForApprovedRequisitions();
	List<VendorContracts> getAllRequiredVendorContractsRecord();
	public List<Person> readPersonIdBasedOnVendorId(int vendorId);
	public List<VendorContracts> readDataForCronScheduler();
	public Long readBudgetUom(int reqIdInChild);
}
