package com.bcits.bfm.service.bankPayments;

import java.util.List;
import com.bcits.bfm.model.BankRemittance;
import com.bcits.bfm.service.GenericService;

public interface BankRemittanceService extends GenericService<BankRemittance> 
{
	public List<BankRemittance> findAll();
	public List<?> setResponse();
}
