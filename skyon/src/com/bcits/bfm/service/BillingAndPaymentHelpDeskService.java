package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.BillingAndPaymentHelpDesk;
public interface BillingAndPaymentHelpDeskService extends GenericService<BillingAndPaymentHelpDesk>{

public	List<?> getAllData();
public List<Object[]> fetchData();

}
