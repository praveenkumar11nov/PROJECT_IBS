package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.BatchBillsEntity;
import com.bcits.bfm.model.PostLedgerEntity;


public interface BatchBillService extends GenericService<BatchBillsEntity> 
{
	public void batcBillSave(List<BatchBillsEntity> l);
	
	
}
