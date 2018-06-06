package com.bcits.bfm.service;

import java.util.List;

import com.bcits.bfm.model.OwnerAuditTrail;
import com.bcits.bfm.model.Person;

public interface OwnerAuditTrailService extends GenericService<OwnerAuditTrail>{

	Person getPersonObjectBasedOnID(int personId);

	List<?> getAllData();

	List<OwnerAuditTrail> getFiltterdata();

}
