

package com.bcits.bfm.controller;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.CustomerEntity;
import com.bcits.bfm.model.CustomerFeedback;
import com.bcits.bfm.model.CustomerItemsEntity;
import com.bcits.bfm.model.CustomerPreviousOrder;
import com.bcits.bfm.model.CustomerTemplate;
import com.bcits.bfm.model.ItemMasterEntity;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.helpDeskManagement.CustomerFeedbackService;
import com.bcits.bfm.service.helpDeskManagement.CustomerItemsService;
import com.bcits.bfm.service.helpDeskManagement.CustomerOrderService;
import com.bcits.bfm.service.helpDeskManagement.CustomerPreviousOrderService;
import com.bcits.bfm.service.helpDeskManagement.CustomerTemplateService;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.GrocerySendMail;
import com.bcits.bfm.util.GrocerySendSMS;
import com.bcits.bfm.util.HelpDeskMailSender;
import com.bcits.bfm.util.SendTicketInfoSMS;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.util.StoreOwnerSMS;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.novell.ldap.LDAPException;





@Controller
public class CustomerOrderController {
	
	@Autowired
	private CustomerFeedbackService customerFeedbackService;
	
	@Autowired
	private CustomerTemplateService customerTemplateService;
	
	@Autowired
	private CustomerOrderService customerOrderService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private CustomerItemsService customerItemsService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CustomerPreviousOrderService customerPreviousOrderService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private ContactService contactService;
	@RequestMapping("/customerorder")
	public String readIndex(Model model,HttpServletRequest request)
	{
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Customer Order ", 2, request);
		return "/helpDesk/customerorder";
	}	
	 
	
	
	@RequestMapping(value = "/customerorder/createitemmaster", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Object createDepartment(@ModelAttribute("customerEntity")CustomerEntity customerEntity, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception
	{String edit="";
		
		
	String order=req.getParameter("ordertype");
	String template=req.getParameter("templatestatus");
	String address=req.getParameter("address");

		 int storeid=Integer.parseInt(req.getParameter(("storeid")));
		 customerEntity.setStoreid(storeid);
		 
		 String storenames=req.getParameter("storename");
		 String storemobile=req.getParameter("storemobile");
		 customerEntity.setStorename(storenames);
		 customerEntity.setTemplate(template);
		 customerEntity.setOrderAddress(address);
		 String username=req.getParameter("username");
	
			List<Contact> contacts = customerOrderService.findEmail(username);
			int id=contacts.get(0).getPersonId();
		
			
			
			List<Contact> contact=contactService.getContactEmailBasedOnPersonId(id);
			String 	emailu=contact.get(0).getContactContent();
			
		
				
			List<Contact> contact1=contactService.getContactPhoneBasedOnPersonId(id);
				String Phone=contact1.get(0).getContactContent();
				
			 List<Person> persons=personService.getContactNameBasedOnPersonId(id);
				 String firstname=persons.get(0).getFirstName();
				 String lastname=persons.get(0).getLastName();
				 String cname=firstname+""+lastname;
				
			
				
				
				customerEntity.setOrdertype(order);
				customerEntity.setPersonId(id);
				customerEntity.setCustEmail(emailu);
				customerEntity.setCustNum(Phone);
				customerEntity.setCustName(cname);
				

				
				
				java.sql.Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		        java.sql.Date date = new java.sql.Date(timeStamp.getTime());
		        
		        
		        customerEntity.setLastUpdatedDate(date);;
			
		  customerOrderService.save(customerEntity);
		  CustomerPreviousOrder customerPreviousOrder=new CustomerPreviousOrder();
			
		  customerPreviousOrder.setCid(customerEntity.getCid());
			customerPreviousOrder.setPersonId(customerEntity.getPersonId());
			customerPreviousOrder.setCusName(customerEntity.getCustName());
			customerPreviousOrder.setCusNum(customerEntity.getCustNum());
			customerPreviousOrder.setCreated_date(customerEntity.getCreatedDate());
			customerPreviousOrder.setStoreId(customerEntity.getStoreid());
			customerPreviousOrder.setStoreName(customerEntity.getStorename());
			customerPreviousOrder.setOrderType(customerEntity.getOrdertype());
			customerPreviousOrder.setOrderstatus(customerEntity.getCustomerStatus());
			customerPreviousOrderService.save(customerPreviousOrder);
		 	
			String content=req.getParameter("itemsList");
		    
			String storename=req.getParameter("storename");
			
		JSONArray arr=new JSONArray(content);
		   
	       for (int i = 0; i < arr.length(); i++)  
	       {
	    	   
	    	   String row="";
	    JSONObject jsonObject = arr.getJSONObject(i);
	    
	      String fname =  jsonObject.getString("name") ;
	      String fprice =  jsonObject.getString("price") ;
	      String ftotalprice =  jsonObject.getString("totalprice") ;      
	      String fquantity =  jsonObject.getString("quantity") ;
	      String uom =  jsonObject.getString("uom") ;
	      
	      
	    CustomerItemsEntity customerItemsEntity=new CustomerItemsEntity(); 
	      
	       customerItemsEntity.setItemName(fname);
	       customerItemsEntity.setItemPrice(Float.parseFloat(fprice));
	       customerItemsEntity.setItemTotalPrice(Float.parseFloat(ftotalprice));
	       customerItemsEntity.setItemQuantity(Integer.parseInt(fquantity));
	       customerItemsEntity.setUom(uom);
	     customerItemsEntity.setCid(customerEntity.getCid());
	    customerItemsService.save(customerItemsEntity); 
		       }
		
	
	  
	   	EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
	       
	       
	       
			  
			  String messageContentForPerson = "<html>"         
			    + "<style type=\"text/css\">"
			    + "table.hovertable {"
			    + "font-family: verdana,arial,sans-serif;"
			    + "font-size:11px;"
			    + "color:#333333;"
			    + "border-width: 1px;"
			    + "border-color: #999999;"
			    + "border-collapse: collapse;"
			    + "}"
			    + "table.hovertable th {"
			    + "background-color:#c3dde0;"
			    + "border-width: 1px;"
			    + "padding: 8px;"
			    + "border-style: solid;"
			    + "border-color: #394c2e;"
			    + "}"
			    + "table.hovertable tr {"
			    + "background-color:#88ab74;"
			    + "}"
			    + "table.hovertable td {"
			    + "border-width: 1px;"
			    + "padding: 8px;"
			    + "border-style: solid;"
			    + "border-color: #394c2e;"
			    + "}"
			    + "</style>"
			    + "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA GROCERY SERVICES.</h2> <br /> Dear "+cname+", <br/> <br/> "
			    +"Good day. This email is to inform you that, A Grocery Order  is created on your request .<br> <br><br>Date of Order Created :"+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"<br/><br/>"
			    +"Thank you  and have a nice day.<br/><br/>"
			    
			    + "<br/>Sincerely,<br/><br/>"
			    + "Manager,<br/>"
			    +"Grocery Services,<br/>"
			    +"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
			    +"</body></html>";
			  
			  String messagePerson = "grocery services from Skyon RWA.This sms is to inform you that your Grocery Order is placed successfully with your contact no as"+Phone+" created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()
			    +"Thank you and Have a nice day"; 
			  
			  String StorePerson = " Skyon RWA.This sms is to inform you that new Order has come , created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()
					    +"Thank you and Have a nice day"; ;
			
			
					    
						
						smsCredentialsDetailsBean.setNumber(Phone);
						smsCredentialsDetailsBean.setUserName(cname);
						smsCredentialsDetailsBean.setMessage(messagePerson);
						new Thread(new GrocerySendSMS(smsCredentialsDetailsBean)).start();	
						
						smsCredentialsDetailsBean.setNumber(storemobile);
						smsCredentialsDetailsBean.setUserName(storename);
						smsCredentialsDetailsBean.setMessage( StorePerson);
						new Thread(new StoreOwnerSMS(smsCredentialsDetailsBean)).start();
						
						emailCredentialsDetailsBean.setToAddressEmail(emailu);
						emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
					    new Thread(new GrocerySendMail(emailCredentialsDetailsBean)).start();
						
			   
		return customerEntity;
		
	     
			}
	
	     
			
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/customerorder/readitemmaster", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> ItemsRead(@ModelAttribute("customerEntity")CustomerEntity customerEntity,HttpServletRequest req) {
		List<CustomerEntity> customerEntities = customerOrderService.findAllData();
		
	
		return customerEntities;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/customerorder/destroyitemmaster", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> ItemsDestroy(@ModelAttribute("customerEntity")CustomerEntity customerEntity,HttpServletRequest req) {
		
		customerOrderService.getDelete(customerEntity.getCid());
		
		List<CustomerEntity> customerEntities = customerOrderService.findAllData();
		
	
		return customerEntities;
	}
	
	
	
	
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/customer/order/{status}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> OrdersRead(@ModelAttribute("customerEntity")CustomerEntity customerEntity,@PathVariable String status,HttpServletRequest req) {
	//	System.out.println(status);
		List<CustomerEntity> customerEntities = customerOrderService.findAllOrders(status);
		
	
		return customerEntities;
	}
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/customer/enquiry/{status}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> EnquirysRead(@ModelAttribute("customerEntity")CustomerEntity customerEntity,@PathVariable String status,HttpServletRequest req) {
	//	System.out.println(status);
		List<CustomerEntity> customerEntities = customerOrderService.findAllEnquirys(status);
		
	
		return customerEntities;
	}
	
	
	
	
	
	
	@RequestMapping(value="/customerorder/updateitemmaster",method = RequestMethod.GET)	
	public @ResponseBody Object updateDepartment(@ModelAttribute("customerEntity") CustomerEntity customerEntity,HttpServletRequest req) throws InterruptedException
	{
	 return customerOrderService.update(customerEntity);
	}
	
	@RequestMapping(value = "/customer/Status/{cid}/{operation}", method = RequestMethod.POST)
	public String updateStatus(@PathVariable int cid,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{
	
		customerOrderService.setstoreStatus(cid, operation, response, messageSource, locale);
		return null;
	}
	
	
	////////////////////child starts here//////////////////////////////
	
	
	
	@RequestMapping(value = "/customeritems/createitemmaster/{cid}", method = RequestMethod.GET)
	public @ResponseBody Object createDepartment(@ModelAttribute("customerItemsEntity") CustomerItemsEntity customerItemsEntity,BindingResult bindingResult,@PathVariable int cid, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws InterruptedException 
	{
		customerItemsEntity.setCid(cid);
		
		double quantity=Double.parseDouble(req.getParameter("itemQuantity"));
		double price=Double.parseDouble(req.getParameter("itemPrice"));
		long total=(long) (quantity*price);
		customerItemsEntity.setItemTotalPrice(total);
		customerItemsEntity=customerItemsService.save(customerItemsEntity);
		
		double q= customerOrderService.findQuantityBasedonId(cid);
		double q1=customerOrderService.findQuantityBasedonItemId(cid);
		double totalQuantity=q+q1;
		
		CustomerEntity customerEntity=new CustomerEntity();
		//customerEntity.setTotalQuantity(totalQuantity);
		customerOrderService.update(customerEntity,cid,totalQuantity);
		
		sessionStatus.setComplete();
		return customerItemsEntity;   	
	}
	

	@RequestMapping(value = "/customeritems/readitemmaster/{cid}", method = RequestMethod.GET)
	public @ResponseBody List<?> templateItemsRead(@PathVariable int cid) {
		//logger.info("In side the read template line item method");
		return customerItemsService.readAllCustomerItems(cid);
	}
	
	
	
	
	
	
	@RequestMapping("/customer/getCustomerimage/{cid}")
	 public String getImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int cid) throws LDAPException, Exception {

	  response.setContentType("image/jpeg");
	  Blob blobImage = customerOrderService.getImage(cid);
	  int blobLength = 0;
	  byte[] blobAsBytes = null;
	  if (blobImage != null) {
	   blobLength = (int) blobImage.length();
	   blobAsBytes = blobImage.getBytes(1, blobLength);
	  }
	  OutputStream ot = response.getOutputStream();
	  try {
	   ot.write(blobAsBytes);
	   ot.close();
	  } catch (Exception e) {
	   //e.printStackTrace();
	  }
	  return null;
	 }
	
	@RequestMapping(value = "/customer/updatestatus/{cid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Asset assetStatus(@PathVariable int cid, @PathVariable String operation,HttpServletResponse response){		

		customerOrderService.setcustomerStatus(cid, operation, response);
		return null;
	}
	
	@RequestMapping(value = "/customer/upload/itemImage/{cid}/{email}/{mobile}", method ={RequestMethod.POST,RequestMethod.GET})
	 public @ResponseBody
	 String save(@RequestParam("file2") MultipartFile files,HttpServletRequest request,@PathVariable int cid,@PathVariable String email,@PathVariable String mobile, final Locale locale ) throws Exception{

	  // Save the files
	 
	  CustomerEntity visitorObj = customerOrderService.find(cid);
	  //for (MultipartFile file : files) {

	  MultipartFile file = files;
	    String name=file.getOriginalFilename().toString();
	    String str[] =  StringUtils.split(name, ".");
	   Blob blob;
	   try {
	    blob = Hibernate.createBlob(file.getInputStream());
	  //  logger.info("blob is -"+blob);
	    visitorObj.setCid(cid);
	  
	    visitorObj.setCustomerImage(blob);
	
	    customerOrderService.updateVisitorImage(cid, blob);
	   

	   } catch (IOException e) {

	 
	    e.printStackTrace();
	   }

	   EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		  
		  String messageContentForPerson = "<html>"         
		    + "<style type=\"text/css\">"
		    + "table.hovertable {"
		    + "font-family: verdana,arial,sans-serif;"
		    + "font-size:11px;"
		    + "color:#333333;"
		    + "border-width: 1px;"
		    + "border-color: #999999;"
		    + "border-collapse: collapse;"
		    + "}"
		    + "table.hovertable th {"
		    + "background-color:#c3dde0;"
		    + "border-width: 1px;"
		    + "padding: 8px;"
		    + "border-style: solid;"
		    + "border-color: #394c2e;"
		    + "}"
		    + "table.hovertable tr {"
		    + "background-color:#88ab74;"
		    + "}"
		    + "table.hovertable td {"
		    + "border-width: 1px;"
		    + "padding: 8px;"
		    + "border-style: solid;"
		    + "border-color: #394c2e;"
		    + "}"
		    + "</style>"
		    + "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA GROCERY SERVICES.</h2> <br /> Dear customer, <br/> <br/> "
		    +"Good day. This email is to inform you that, A Grocery Enquiry list is created on your request for enquiry and uploaded.Please check by login into application and verify.<br> <br><br>Date of response :"+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"<br/><br/>"
		    +"Thank you  and have a nice day.<br/><br/>"
		    
		    + "<br/>Sincerely,<br/><br/>"
		    + "Manager,<br/>"
		    +"Grocery Services,<br/>"
		    +"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
		    +"</body></html>";
		  
		  String messagePerson = "grocery services from Skyon RWA.This sms is to inform you that your Grocery enquiry is  responded and list is uploaded.Please login into application and verify.Response date "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()
		    +"Thank you and Have a nice day"; 
	
		  
		    smsCredentialsDetailsBean.setNumber(mobile);
			smsCredentialsDetailsBean.setUserName("customer");
			smsCredentialsDetailsBean.setMessage(messagePerson);
			new Thread(new GrocerySendSMS(smsCredentialsDetailsBean)).start();

			emailCredentialsDetailsBean.setToAddressEmail(email);
			emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
		   new Thread(new GrocerySendMail(emailCredentialsDetailsBean)).start();
		  
		  
		  
		  
	
	  return "";
	 }
	 
	@RequestMapping("/customerorder/getItemimage/{cid}")
	 public String getCustomerImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int cid) throws LDAPException, Exception {

	  response.setContentType("image/jpeg");
	  Blob blobImage = customerOrderService.getImage(cid);
	  int blobLength = 0;
	  byte[] blobAsBytes = null;
	  if (blobImage != null) {
	   blobLength = (int) blobImage.length();
	   blobAsBytes = blobImage.getBytes(1, blobLength);
	  }
	  OutputStream ot = response.getOutputStream();
	  try {
	   ot.write(blobAsBytes);
	   ot.close();
	  } catch (Exception e) {
	   //e.printStackTrace();
	  }
	  return null;
	 }
	
	@RequestMapping("/customerorder/download/{cid}")
	 public String downloadJobDocument(@PathVariable("cid") int cid, HttpServletResponse response) throws Exception{
	  
	  
	
	   CustomerEntity customerEntity = customerOrderService.find(cid);
	    try {
	            
	              if(customerEntity.getCustomerImage() != null){
	               response.setHeader("Content-Disposition", "inline;filename=\"" +customerEntity.getCustName()+ "\"");
	               OutputStream out = response.getOutputStream();
	               response.setContentType("image/jpeg");
	               IOUtils.copy(customerEntity.getCustomerImage().getBinaryStream(), out);
	               out.flush();

	              out.close();
	              }
      
   }
catch (IOException e){
       e.printStackTrace();
   } 
catch (SQLException e){
       e.printStackTrace();
   }
return null;
}
	
	
	////////////////////////////CUSTOMER TEMPLATE STARTS HERE /////////////////////////////////////
	
	
	@RequestMapping(value = "/customertemplate/createitem/{ccid}", method = RequestMethod.GET)
	public @ResponseBody Object createTemplate(@ModelAttribute("customerTemplate") CustomerTemplate customerTemplate,BindingResult bindingResult,@PathVariable int ctid, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws InterruptedException 
	{
		customerTemplate.setCtid(ctid);
		
		
		 return customerTemplate=customerTemplateService.save(customerTemplate);
	
}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/customerorder/readitemmasters", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> ItemRead(@ModelAttribute("customerEntity")CustomerEntity customerEntity,HttpServletRequest req) {
		int id=Integer.parseInt(req.getParameter("storeid").toString());
		//System.out.println(id);
		List<CustomerEntity> customerEntities = customerOrderService.findAllDatas(id);
	
		return customerEntities;
	}
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/customerorder/readitemmastersEnquiry", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> ReadEnquiry(@ModelAttribute("customerEntity")CustomerEntity customerEntity,HttpServletRequest req) {
		int id=Integer.parseInt(req.getParameter("storeid").toString());
		List<CustomerEntity> customerEntities = customerOrderService.findAllEnquiry(id);
	
		return customerEntities;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "customer/ProfileDetails", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerEntity> ReadProfile(@ModelAttribute("customerEntity")CustomerEntity customerEntity,HttpServletRequest req) {
		String user=req.getParameter("username");
	
		
	List<CustomerEntity> customerEntities = customerOrderService.findAllUserDetails(user);
		
		
		return customerEntities;
	}
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "readInformation", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> ReadInformation(@ModelAttribute("customerFeedback")CustomerFeedback customerFeedback,HttpServletRequest req) {
		
		String user=req.getParameter("username");
	List<?> customerFeedbacks = customerOrderService.findAllInformation(user);
	return customerFeedbacks;
	}
	
	
	
	@RequestMapping(value = "/customer/saveFeedback", method = RequestMethod.GET)
	public @ResponseBody Object createFeedback(@ModelAttribute("customerFeedback") CustomerFeedback customerFeedback,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws InterruptedException 
	{
			int id=Integer.parseInt(req.getParameter("storeid"));
			String user=req.getParameter("username");
			customerFeedback.setStoreid(id);
		
			
			String customerfeedback=req.getParameter("custFeedback");
			List<CustomerEntity> customerEntities=customerOrderService.finduserDatas(user);
			
			String uname=customerEntities.get(0).getCustName();
			int customerId=customerEntities.get(0).getCid();
			String sname=customerEntities.get(0).getStorename();
			
			
			customerFeedback.setCustId(customerId);
			customerFeedback.setUsername(uname);
			customerFeedback.setFeedback(customerfeedback);
			customerFeedback.setCustEmail(user);
		return customerFeedbackService.save(customerFeedback);
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "readFeedback", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<CustomerFeedback> ReadProfile(@ModelAttribute("customerFeedback")CustomerFeedback customerFeedback,HttpServletRequest req) {
		
		int id=Integer.parseInt(req.getParameter("storeid"));
	List<CustomerFeedback> customerFeedbacks = customerOrderService.findAllFeedback(id);
	return customerFeedbacks;
	}
	
}
