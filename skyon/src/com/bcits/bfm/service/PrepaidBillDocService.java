package com.bcits.bfm.service;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.model.PrepaidBillDocument;

public interface PrepaidBillDocService extends GenericService<PrepaidBillDocument>{

	Blob getBlog(String billNo);


	List<PrepaidBillDetails> downloadAllBills(java.util.Date month);


	List<PrepaidBillDetails> downloadAllBillsOnPropertyNo(java.util.Date monthDate, int propertyId);

}
