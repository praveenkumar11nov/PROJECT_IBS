package com.bcits.bfm.service;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.model.Users;

public interface SendMailBillService extends GenericService<PrepaidBillDetails>

{
	
	Blob getBlog(String billNo);
	
	List<?> fetchBillsDataBasedOnMonthAndServiceType(int actualmonth,int year, int propId);
	
	List<String> getContactDetailsForMail(int personId);
	
	public String getTenantStatusByPropertyId1(int propertyId);
	
	public List<String> getContactDetailsByPersonId1(int propertyId);
	
	public String getTenantFirstNameByPropertyId1(int propertyId);
	
	Object[] getPersonNameBasedOnPersonId1(int personId);
	
	//void updateMailSent_Status(int elBillId,String serviceType,String status);
	
	
	public Users getUserInstanceByLoginName1(String userLoginName);

	
	int getUserIdBasedOnPersonId1(int personId);
	
	
	Object[] getPropertyDataBasedOnPropertyId1(int propertyId);

	
}
