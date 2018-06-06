package com.bcits.bfm.service.bankPayments;

import java.util.List;

import com.bcits.bfm.model.BankStatement;
import com.bcits.bfm.service.GenericService;

public interface BankStatementService extends GenericService<BankStatement> 
{
	public List<BankStatement> findAll();
	public List<?> setResponse();
}
