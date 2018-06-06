package com.bcits.bfm.service.helpDeskManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerFeedback;
import com.bcits.bfm.service.GenericService;

public interface CustomerOrderService extends GenericService<CustomerEntity> {

	List<CustomerEntity> findAllData();
	public void getDelete(int cid);
	
	List<CustomerEntity> findAllDatas(int storeid);

	Double findQuantityBasedonId(int cid);

	public void update(CustomerEntity customerEntity, int cid,double totalQuantity);

	Double findQuantityBasedonItemId(int cid);

	public void setstoreStatus(int sId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);

public 	void uploadImageOnId(int cid, Blob blob);

public Blob getImage(int cid);

public void setcustomerStatus(int cid, String operation, HttpServletResponse response);

List<Contact> findEmail(String username);

public List<CustomerEntity> getPersonId(String custNum);

public void updateVisitorImage(int cid, Blob blob);

List<CustomerEntity> findAllUserDetails(String user);

List<CustomerEntity> getdetails(String custEmail);

List<CustomerEntity> finduserDatas(String user);

List<CustomerFeedback> findAllFeedback(int id);

List<CustomerEntity> findAllEnquiry(int id);

List<?> findAllInformation(String user);

List<CustomerEntity> findAllOrders(String status);

List<CustomerEntity> findAllEnquirys(String status);





//List<CustomerEntity> ItemRead(int id);





/*void setstoreOrderStatus(int cid, String operation,
		HttpServletResponse response, MessageSource messageSource, Locale locale);*/


	

	
	
	
	

}
