package com.bcits.bfm.service.billingWizard;

import java.util.Locale;

import com.bcits.bfm.model.Account;


public interface BillingTemplateService  {
	
	public String consolidatedBillMailTemplate(int accountId, String billMonth,Locale locale);

	public void allConsolidatedBillMailTemplate(int elBillId,Locale locale);

	public String allConsolidatedCAMBillMailTemplate(int elBillId, Locale locale);

	public void consolidateSingleBillSendMail(Account account,String billMonth,Locale locale);

}
