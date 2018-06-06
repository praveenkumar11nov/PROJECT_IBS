package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.MailRoom;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.MailRoomMessenger;
import com.bcits.bfm.util.MailRoomSMS;
import com.bcits.bfm.util.SendMailForStaff;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module
 * Module: User Management
 * 
 * @author Shashidhar Bemalgi
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class MailRoomController extends FilterUnit
{
	static Logger logger = LoggerFactory.getLogger(MailRoomController.class);
	
	@Autowired
	private MailRoomService mailService;
	
	@Autowired
	private OwnerPropertyService ownerProperty;
	
	@Autowired
	private OwnerService owner;
	
	@Autowired
	private PersonService ps;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ParkingSlotsAllotmentService parkingSlotService;
	
	@Autowired
	private DrGroupIdService drGroupIdService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private Validator validator;
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private BlocksService blockService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private TenantSevice tenantService;
	
    
    
	/**
	 * @param model to set attributes for jsp
	 * @param request mailroom page
	 * @return
	 */
	@RequestMapping(value = "/mailroom")
    public String index(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Mail Room");
		breadCrumbService.addNode("nodeID", 0,request);
		breadCrumbService.addNode("Mail Room", 1,request);	
		breadCrumbService.addNode("Manage MailRoom Details", 2, request);
		return "mailroom/mailroom";
    }	
	
	/**
    * For sending the contents to the jsp
	* @return list of mailroom object
	*/
	@RequestMapping(value = "/mailroom/read", method = RequestMethod.GET)
    public @ResponseBody List<?> read() 
	{	
		
		   return mailService.findAllData();
	
    }
	/***********   END ********************************/
	
	/********************** Reading only Sent Mails ********************************/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/mailroom/readSentMails", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody List<?> readallSentMails() 
	{	
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
        for (final MailRoom record : mailService.readAllSentMails("Out_For_Delivery")) {
            result.add(new HashMap<String, Object>() 
            {{
            	put("mlrId", record.getMlrId());
            	put("blockId", record.getPropertyObj().getBlocks().getBlockId());
            	put("blockName", record.getPropertyObj().getBlocks().getBlockName());
            	put("propertyId", record.getPropertyObj().getPropertyId());
            	put("property_No", record.getPropertyObj().getProperty_No());
            	put("propertyName", record.getPropertyObj().getPropertyName());            	
            	put("addressedTo", record.getAddressedTo());
            	put("addressedFrom", record.getAddressedFrom());
            	put("mailboxDt", ConvertDate.TimeStampString(record.getMailboxDt()));
            	put("status", record.getStatus());            	
            	put("lastUpdatedDt", record.getLastUpdatedDt()); 
            	put("drGroupId", record.getDrGroupId());
            	put("createdBy", record.getCreatedBy());
            	put("lastUpdatedBy", record.getLastUpdatedBy());
            	put("mailRedirectedStatus", record.getMailRedirectedStatus());
            	put("mailReturnedStatus", record.getMailReturnedStatus());
            	put("reasons", record.getReasons());
            	put("consignmentNo", record.getConsignmentNo());
            }});
        }
        logger.info("From Mailrom controoler");
        return result;
    }
	
	/********************** Reading only Sent Mails ********************************/
			
	/**
	* For Reading Property Id & Property Names
	* @return list with property attributes
	*/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/mailroom/readPropertyNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readPropertyNumber()
	{		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Property record : mailService.findPropertyNames()) {
	            result.add(new HashMap<String, Object>() 
	            {{
	            	put("blockId", record.getBlocks().getBlockId());
	            	put("propertyId", record.getPropertyId());
	            	put("property_No", record.getProperty_No());
	            	put("blockName", record.getBlocks().getBlockName());
	            }});
	        }
	        return result;
	}
	/***********  END **********/
	
	
	/**
	* For reading property names
	* @return list of property name attribute 
	*/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/mailroom/readPropertyNames", method = RequestMethod.GET)
	public @ResponseBody List<?> readPropertyObj()
	{		
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Property record : mailService.findPropertyNames()) {
	            result.add(new HashMap<String, Object>() 
	            {{
	            	put("propertyId", record.getPropertyId());
	            	put("propertyName", record.getPropertyName());
	            	put("blockId", record.getBlocks().getBlockId());
	            	put("blockName", record.getBlocks().getBlockName());
	            }});
	        }
	        return result;	        
	}
	/***********  END **********/
	
	/**
	*  For Reading Block Names 
	* @return list of tower names with block object
	*/
	@SuppressWarnings("serial")
	@RequestMapping(value = "/mailroom/readTowerNames", method = RequestMethod.GET)
	public @ResponseBody List<?> getTowerNames()
	{
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final Blocks record : blockService.findAll()) {
	            result.add(new HashMap<String, Object>() 
	            {{	            	
	            	put("blockId", record.getBlockId());
	            	put("blockName", record.getBlockName());
	            	
	            }});
	        }
	        return result;
	}
	/*******  END *******/	
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	/**
	 * For saving/adding mailroom details
	 * @param map Mailroom details
	 * @param mailroom MailRoom entity
	 * @param bindingResult for checking errors
	 * @param model Mailroom object details
	 * @param sessionStatus session object
	 * @param locale System locale
	 * @return mailroom object
	 */
	@RequestMapping(value = "/mailroom/create", method = RequestMethod.POST)
	public @ResponseBody Object create(@RequestBody Map<String, Object> map, @ModelAttribute("mailroom") MailRoom mailroom, BindingResult bindingResult,ModelMap model, SessionStatus sessionStatus, final Locale locale) 
	{			
		    mailroom = mailService.getBeanObject(map,"save",mailroom);
		    drGroupIdService.save(new DrGroupId(mailroom.getCreatedBy(), mailroom.getLastUpdatedDt()));
		    mailroom.setDrGroupId(drGroupIdService.getNextVal(mailroom.getCreatedBy(), mailroom.getLastUpdatedDt()));		    
		    
			validator.validate(mailroom, bindingResult);		
			JsonResponse errorResponse = new JsonResponse();
			if (bindingResult.hasErrors())
			{
				logger.info("Error method() in mailroom Controller");
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				logger.info("Biding Result Value in Save()"+bindingResult);
				return errorResponse;
			}
			else
			{				
				logger.info("Inside Else Part of Save()");				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				logger.info(dateFormat.format(date));
				String pid = (String)map.get("property_No");
				int prId = Integer.parseInt(pid);
				int propertyId = prId;				
				String con_No = mailroom.getConsignmentNo();
				if(con_No != null)
				{
					logger.info("Consignment Number Not Null");
					mailService.save(mailroom);
				}
				else if(con_No == null)
				{
					logger.info("Consignment Number Null");
					mailService.save(mailroom);
					
					List id = mailService.getMaxCount();
					Iterator<Integer> id1 = id.iterator();
					int maxId = 0;
					String cno = "";
					while(id1.hasNext())
					{		
						maxId = id1.next();
					}
					int mailroomId = mailroom.getMlrId();
					String mailId = String.valueOf(mailroomId);
					String consignmentNo = String.valueOf(propertyId).concat("/").concat(mailId).concat("/").concat(dateFormat.format(date));
					mailService.updateMailroomConsignmentNumber(mailroomId, consignmentNo);
					
					List<MailRoom> mr=mailService.findAll();
					String fromAddress="";
					String contactType="";
					String consNo = "";
					String status="Mail_Received";
					
					String tenantContactType = "";
					String tenantEmailPhone = "";						
					String ownerContactType = "";
					String ownerEmailPhone = "";
					
					Iterator<MailRoom> it=mr.iterator();
					while(it.hasNext())
					{
						MailRoom m=it.next();				
						if(m.getMlrId()==mailroomId)
						{
							 fromAddress=m.getAddressedFrom();
							 consNo = m.getConsignmentNo();
							 
							 /*******************  FOR GETTING ALL THE TENANT(PERSON) DETAILS ********************/
							 List<Person> tenantList = personService.getAllTenantOnPropetyId(propertyId);
							 int tenantPersonId = 0;
							 String tenantSalutation = "";
							 String tenantFname = "";
							 String tenantLname = "";
							 for (final Person tenantRecord : tenantList ) 
							 {	   
							 	 tenantPersonId = tenantRecord.getPersonId();
							 	 tenantSalutation = tenantRecord.getTitle();
							  	 tenantFname = tenantRecord.getFirstName();
								 tenantLname = tenantRecord.getLastName();
							 }
							 /************************  END FOR GETTING ALL THE TENANT DETAILS  *************************/
							 
							 /***********************  FOR GETTING ALL THE OWNER(PERSON) DETAILS  ****************************/
							 List<Person> ownerList = personService.getAllOwnersOnPropetyId(propertyId);
							 int ownerPersonId = 0;
							 String ownerSalutation = "";
							 String ownerFname = "";
							 String ownerLname = "";
							 for (final Person ownerRecord : ownerList ) 
							 {	   
							 	ownerPersonId = ownerRecord.getPersonId();
							 	ownerSalutation = ownerRecord.getTitle();
							 	ownerFname = ownerRecord.getFirstName();
							 	ownerLname = ownerRecord.getLastName();
							 }	
							 /**********************  END FOR GETTING ALL THE OWNER DETAILS  ***********************/
							
							 List<Contact> c = contactService.findAll(); //FOR GETTING CONTACT DETAILS OF TENANT & OWNER
							 Iterator<Contact> itc = c.iterator();
							 while(itc.hasNext())
							 {
							 	 /******  CHECKING CONTACT DETAILS FOR TENANT BASED ON PERSONID ***********/
								 Contact c1 = itc.next();
								 if(c1.getPersonId() == tenantPersonId)
								 {
								
									 tenantContactType = c1.getContactType();
									 tenantEmailPhone = c1.getContactContent();
								  	 if(tenantContactType.equalsIgnoreCase("email"))
									 {
								  		 
								  		 EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
										  try {
										  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
										} catch (Exception e) {
											e.printStackTrace();
										}
										  
										  String messageContent="<html>"
												   
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
														+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAILROOM SERVICES.</h2> <br /> Dear "+tenantSalutation+", <br/> <br/> "
														+"Good day. This email is to inform you that the mail consignment received from <b>"+fromAddress+"</b> is sent for delivery addressing you.<br/>"
														+"Thank you very much and have a nice day.<br/><br/>"
														
														+ "<br/>Sincerely,<br/><br/>"
														+ "Manager,<br/>"
														+"MailRoom Services,<br/>"
														+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
														+"</body></html>";
												
										 
										      emailCredentialsDetailsBean.setToAddressEmail(tenantEmailPhone);
											  emailCredentialsDetailsBean.setMessageContent(messageContent);
										
											  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
								  		 
								  		 
								   }
								  	 if(tenantContactType.equalsIgnoreCase("mobile"))
									 {
								  		 String message = "is received addressing you.You will be confirmed once the consignment is dispatched";
										 String userMessage = "Dear "+ownerSalutation+", a mail from "+fromAddress+" "+message+" - Skyon RWA  Administration services.";	

											
										  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
										   
										    smsCredentialsDetailsBean.setNumber(tenantEmailPhone);
											smsCredentialsDetailsBean.setUserName(tenantSalutation);
											smsCredentialsDetailsBean.setMessage(userMessage);
											
											new Thread(new MailRoomSMS(smsCredentialsDetailsBean)).start();
								  		 
								  		 
								  	}
								 }
								 /***********  FOR CHECKING OWNER CONTACT DETAILS  BASED ON PERSONID  *************/
								 if(c1.getPersonId() == ownerPersonId)
								 {
									 ownerContactType = c1.getContactType();
									 ownerEmailPhone = c1.getContactContent();
									 
									 //IF Contact Type Is EMAIL
									 if(ownerContactType.equalsIgnoreCase("email"))
									 {
										 
										 
										 EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
										  try {
										  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
										} catch (Exception e) {
											e.printStackTrace();
										}
										  
										  String messageContent="<html>"
												   
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
														+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAILROOM SERVICES.</h2> <br /> Dear "+ownerSalutation+", <br/> <br/> "
														+"Good day. This email is to inform you that the mail consignment received from <b>"+fromAddress+"</b> is sent for delivery addressing you.<br/>"
														+"Thank you very much and have a nice day.<br/><br/>"
														
														+ "<br/>Sincerely,<br/><br/>"
														+ "Manager,<br/>"
														+"MailRoom Services,<br/>"
														+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
														+"</body></html>";
												
												
										 
										      emailCredentialsDetailsBean.setToAddressEmail(ownerEmailPhone);
											  emailCredentialsDetailsBean.setMessageContent(messageContent);
										
											  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
										 										 
										
									 }
									 if(ownerContactType.equalsIgnoreCase("mobile"))
									 {
										 
										 String message = "is received addressing you.You will be confirmed once the consignment is dispatched";
										 String userMessage = "Dear "+ownerSalutation+", a mail from "+fromAddress+" "+message+" - Skyon RWA  Administration services.";	

											
										  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
										   
										    smsCredentialsDetailsBean.setNumber(ownerEmailPhone);
											smsCredentialsDetailsBean.setUserName(ownerSalutation);
											smsCredentialsDetailsBean.setMessage(userMessage);
											
											new Thread(new MailRoomSMS(smsCredentialsDetailsBean)).start();

										 
									
									 }
								 }
							 }
						}
					}					
				}							
				return mailroom;
			}
	}	
	/****************** End *****************/
	
	/**
	 * For updating saved mailroom details
	 * @param map Mailroom details
	 * @param mailroom MailRoom entity
	 * @param bindingResult for checking errors
	 * @param model Mailroom object details
	 * @param sessionStatus session object
	 * @param locale System locale
	 * @return mailroom object
	 */
	@RequestMapping(value = "/mailroom/update", method = RequestMethod.POST)
	public @ResponseBody Object updateDelivery(@RequestBody Map<String, Object> map, @ModelAttribute("mailroom") MailRoom mailroom, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale)
	{	
		logger.info("Inside Delivery Controller UPdate ()");
		mailroom = mailService.getBeanObject(map,"update",mailroom);
		validator.validate(mailroom, bindingResult);		
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors())
		{
			logger.info("Error method() in MailRoomDelivery Update() Controller");
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			logger.info("Biding Result value in update() of MailRoom DeliveryList"+bindingResult);
			return errorResponse;
		}
		else
		{
			mailService.update(mailroom);
		}
		return mailroom;
	}
	/*************   END  ******************/
	
	/**
	 * For deleting the mailroom records
	 * @param map Mailroom details
	 * @param mailroom MailRoom entity
	 * @param bindingResult for checking errors
	 * @param model Mailroom object details
	 * @param sessionStatus session object
	 * @param locale System locale
	 * @return mailroom object
	 * @throws IOException
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/mailroom/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroy(@RequestBody Map<String, Object> map,@ModelAttribute("mailroom") MailRoom mailroom, 
						BindingResult bindingResult,ModelMap model, 
						SessionStatus sessionStatus, final Locale locale) throws IOException 
	{
		JsonResponse errorResponse = new JsonResponse();
		int id = (int)map.get("mlrId");		
		MailRoom m = mailService.find(id);
		String st = m.getStatus();
		if(st.equals("Out_For_Delivery"))
		{
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("OutForDeliveryError", messageSource.getMessage(
							"MailRoom.deleteOutForDeliveryError", null, locale));
				}
			};
			errorResponse.setStatus("OutForDeliveryError");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
		if(st.equals("Returned"))
		{	
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("ReturnedError", messageSource.getMessage(
							"MailRoom.deleteReturnedError", null, locale));
				}
			};
			errorResponse.setStatus("ReturnedError");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		}
		else
		{
			mailService.delete(id);
		}
		return mailroom;			
	}
	/********  End *********/
	
	/**
	 * @param model Mailroom delivery details
	 * @param request mailDelivery list
	 * @return mailroomDeliveryList object
	 */
	@RequestMapping(value = "/mailroomDelivery")
    public String indexMailDeliveryList(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Mail Room");
		breadCrumbService.addNode("nodeID", 0,request);
		breadCrumbService.addNode("Mail Room", 1,request);
		breadCrumbService.addNode("Manage Mail Delivery List", 2, request);
		logger.info("From MailRoom Delivery Controller Class");
		return "mailroom/mailRoomDelivery";
    }
	
	/**
	 * For reading mailroomDeliveryList details
	 * @return mailroom object
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/mailroomDelivery/read", method = RequestMethod.GET)
    public @ResponseBody List<?> readDeliveryList() 
	{	
		return mailService.findData();
		
		
    }
	/***********   END ********************************/	
	
	/**
	 * @return block Names from existing mailroom object
	 */
	@RequestMapping(value = "/mailroom/getBlockNames", method = RequestMethod.GET)
	public @ResponseBody Set<?> getPropNames()
	{ 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getPropertyObj().getBlocks().getBlockName());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @return property number from existing mailroom object
	*/
	@RequestMapping(value = "/mailroom/getPropertyNo", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getPropNumber() 
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getPropertyObj().getProperty_No());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	
	/**
	* @return mail addressedTo details 
	*/
	@RequestMapping(value = "/mailroom/addTo", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getAddressedTo()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getAddressedTo());		    	
			}
	        return result;
	    }
	/********  End  ********************/
		
	/**
	* @return mail addressedFrom details
	*/
	@RequestMapping(value = "/mailroom/addFrom", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getAddressedFrom()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getAddressedFrom());		    	
			}
	        return result;
	    }
	@RequestMapping(value = "/mailroom/mailboxDate", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getmaildate()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(ConvertDate.TimeStampString(mailroom.getMailboxDt()));		    	
			}
	        return result;
	    }
	
	
	
	/********  End  ********************/
	
	/**
	* @return mailStatus 
	*/
	@RequestMapping(value = "/mailroom/getStatus", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getStatus()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getStatus());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @return mail details createdBy details
	*/
	@RequestMapping(value = "/mailroom/getCreatedBy", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getCreatedBy()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getCreatedBy());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @return mailDetails lastUpdatedBy
	*/
	@RequestMapping(value = "/mailroom/getlastUpdatedBy", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getLastUpdatedBY()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getLastUpdatedBy());		    	
			}
	        return result;
	    }
	/********  End  ********************/
		
	/**
	* @return mail consignment details
	*/
	@RequestMapping(value = "/mailroom/getConsignmentNo", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getConsignmentNo()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getConsignmentNo());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @return mailDeliveredByName details
	*/
	@RequestMapping(value = "/mailroom/getDeliveredByName", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getDeliveredByName()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getNote());		    	
			}
	        return result;
	    }
	/********  End  ********************/	


	/**
	* @return mailReceivedByName Details
	*/
	@RequestMapping(value = "/mailroom/getReceivedByName", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getReceivedByName()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getReceivedBy());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @return mailReturned Address details
	*/
	@RequestMapping(value = "/mailroom/getReasons", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getReasons()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getReasons());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @return mailredirectedAddress details
	*/
	@RequestMapping(value = "/mailroom/getRedirectedAddress", method = RequestMethod.GET)
	 public @ResponseBody Set<?> getRedirectedAddress()
	 { 
		  Set<String> result = new HashSet<String>();
		    for (MailRoom mailroom : mailService.findAll())
			{	
		    	result.add(mailroom.getMailRedirectedStatus());		    	
			}
	        return result;
	    }
	/********  End  ********************/
	
	/**
	* @param id mlrId(MailRoom unique Id)
	* @param response mail sent status
	* @throws IOException
	*/
	@RequestMapping(value = "/mailroom/updateSingleOutForDelivery", method = RequestMethod.POST)
	public void updateStatusForDeliveryForSingle(@RequestParam("id1") String id,HttpServletResponse response) throws IOException
	{	
		String newStatus = "Out_For_Delivery";
		@SuppressWarnings("unused")
		String result="";
	    String tenantContactType = "";
		String tenantEmailPhone = "";
			
		String ownerContactType = "";
		String ownerEmailPhone = "";
			
		String idValues[]=id.split(",");
		for(int i=0;i<idValues.length;i++)
		{
		 	 List<MailRoom> mr=mailService.findAll();
			 String fromAddress="";
			 String consNo = "";
			 int propertyId = 0;
				
			 int ii=Integer.parseInt(idValues[i]);
			 Iterator<MailRoom> it=mr.iterator();
			 while(it.hasNext())
			 {
				 MailRoom m=it.next();				
				 if(m.getMlrId()==ii)
				 {
				 	fromAddress=m.getAddressedFrom();
				 	propertyId=m.getPropertyObj().getPropertyId();
				 	consNo = m.getConsignmentNo();
				 }
			 }
			 mailService.updateStatus(Integer.parseInt(idValues[i]), newStatus);
			 result="success";
			 
			 /*******************  FOR GETTING ALL THE TENANT(PERSON) DETAILS ********************/
			 List<Person> tenantList = personService.getAllTenantOnPropetyId(propertyId);
			 int tenantPersonId = 0;
			 String tenantSalutation = "";
			 String tenantFname = "";
			 String tenantLname = "";
			 for (final Person tenantRecord : tenantList ) 
			 {	   
			 	 tenantPersonId = tenantRecord.getPersonId();
			 	 tenantSalutation = tenantRecord.getTitle();
			  	 tenantFname = tenantRecord.getFirstName();
				 tenantLname = tenantRecord.getLastName();
			 }
			 /************************  END FOR GETTING ALL THE TENANT DETAILS  *************************/
			 
			 /***********************  FOR GETTING ALL THE OWNER(PERSON) DETAILS  ****************************/
			 List<Person> ownerList = personService.getAllOwnersOnPropetyId(propertyId);
			 int ownerPersonId = 0;
			 String ownerSalutation = "";
			 String ownerFname = "";
			 String ownerLname = "";
			 for (final Person ownerRecord : ownerList ) 
			 {	   
			 	ownerPersonId = ownerRecord.getPersonId();
			 	ownerSalutation = ownerRecord.getTitle();
			 	ownerFname = ownerRecord.getFirstName();
			 	ownerLname = ownerRecord.getLastName();
			 }	
			 /**********************  END FOR GETTING ALL THE OWNER DETAILS  ***********************/
			 
			 List<Contact> c = contactService.findAll(); //FOR GETTING CONTACT DETAILS OF TENANT & OWNER
			 Iterator<Contact> itc = c.iterator();
			 while(itc.hasNext())
			 {
			 	 /******  CHECKING CONTACT DETAILS FOR TENANT BASED ON PERSONID ***********/
				 Contact c1 = itc.next();
				 if(c1.getPersonId() == tenantPersonId)
				 {
					 tenantContactType = c1.getContactType();
					 tenantEmailPhone = c1.getContactContent();
				  	 if(tenantContactType.equalsIgnoreCase("email"))
					 {
				  		 
				  		 
				  		 
				  		 
				  		 EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
						  try {
						  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  
						  String messageContent="<html>"
								   
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA MAILROOM SERVICES.</h2> <br /> Dear "+tenantSalutation+"\t"+tenantFname+"\t"+tenantLname+"<br/> <br/> "
										+"Good day. This email is to inform you that the mail consignment received from <b>"+fromAddress+"</b>  with  Consignment number <b> \t"+consNo+"</b> is sent for delivery addressing you.<br/>"
										+"Thank you very much and have a nice day.<br/><br/>"
										
										+ "<br/>Sincerely,<br/><br/>"
										+ "Manager,<br/>"
										+"MailRoom Services,<br/>"
										+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
										+"</body></html>";
								
								
						 
						      emailCredentialsDetailsBean.setToAddressEmail(tenantEmailPhone);
							  emailCredentialsDetailsBean.setMessageContent(messageContent);
						
							  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
				  		 
				  		 
					 }
				  	 if(tenantContactType.equalsIgnoreCase("mobile"))
					 {
						 final Locale locale = null;
						 String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
						 String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);				
						 String message = "";
						 if(newStatus.equalsIgnoreCase("Out_For_Delivery"))
						 {
							 message = "is received and has been dispatched for delivery";
							 String userMessage = "Dear "+ tenantSalutation + " " + tenantFname + " " + tenantLname +", a mail from "+fromAddress+" "+message+" - Skyon RWA  Administration services.";
							  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
							   
							    smsCredentialsDetailsBean.setNumber(ownerEmailPhone);
								smsCredentialsDetailsBean.setUserName(tenantSalutation);
								smsCredentialsDetailsBean.setMessage(userMessage);
								
								new Thread(new MailRoomSMS(smsCredentialsDetailsBean)).start();
							
							 
						 }
					 }
				 }
				 /***********  FOR CHECKING OWNER CONTACT DETAILS  BASED ON PERSONID  *************/
				 if(c1.getPersonId() == ownerPersonId)
				 {
					 ownerContactType = c1.getContactType();
					 ownerEmailPhone = c1.getContactContent();
					 
					 //IF Contact Type Is EMAIL
					 if(ownerContactType.equalsIgnoreCase("email"))
					 {
						 
						 
						 
						 
						 EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
						  try {
						  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  
						  String messageContent="<html>"
								   
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA MAILROOM SERVICES.</h2> <br /> Dear "+ownerSalutation+"\t "+ownerFname+"\t"+ownerLname+"<br/> <br/> "
										+"Good day. This email is to inform you that the mail consignment received from <b>"+fromAddress+"</b> <b>with  Consignment number</b>\t"+consNo+" is sent for delivery addressing you.<br/>"
										+"Thank you very much and have a nice day.<br/><br/>"
										
										+ "<br/>Sincerely,<br/><br/>"
										+ "Manager,<br/>"
										+"MailRoom Services,<br/>"
										+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
										+"</body></html>";
								
								
						 
						      emailCredentialsDetailsBean.setToAddressEmail(ownerEmailPhone);
							  emailCredentialsDetailsBean.setMessageContent(messageContent);
						
							  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
						 
						 
						 
					 }
					 if(ownerContactType.equalsIgnoreCase("mobile"))
					 {
						 final Locale locale = null;
						 String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
						 String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);				
						 String message = "";
						 if(newStatus.equalsIgnoreCase("Out_For_Delivery"))
						 {
							 message = "is received and has been dispatched for delivery";
							 
							 
							 String userMessage = "Dear "+ " " + ownerSalutation +" "+ ownerFname+" "+ ownerLname+ ", a mail from "+fromAddress+" "+message+" - Skyon RWA  Administration services.";	

								
							  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
							   
							    smsCredentialsDetailsBean.setNumber(ownerEmailPhone);
								smsCredentialsDetailsBean.setUserName(ownerSalutation);
								smsCredentialsDetailsBean.setMessage(userMessage);
								
								new Thread(new MailRoomSMS(smsCredentialsDetailsBean)).start();
							 
						 }
						 
					 }
				 }
			 }			 
		 }
		PrintWriter out = response.getWriter();
		out.write("Consignment Sent To Delivery");
	}
	/*****  END FOR SINGLE CHECKBOX *********/
	
	
	/**
	 * @param id mlrId(MailRoom Id)
	 * @param selectedStatus mailStatus(Delivered,Redirected,Returned)
	 * @param selectedDate date for delivered/redirected/returned
	 * @param deliveryBoyName delivery boy name
	 * @param receivedByName mail received name
	 * @param reason for returned mail
	 * @param redirectAddress for redirected mail
	 * @param response none
	 * @throws IOException 
	 * @throws NumberFormatException for Ids
	 * @throws ParseException for dateParse
	 */
	@RequestMapping(value = "/mailroomDelivery/updateSingleMailRoomDelivery", method = RequestMethod.POST)
	public void updateSingleMailRoomDelivery(@RequestParam("id1") String id,@RequestParam("selectedStatus") String selectedStatus,@RequestParam("selectedDate") String selectedDate,@RequestParam("deliveryBoyName") String deliveryBoyName,@RequestParam("receivedByName") String receivedByName,@RequestParam("reason") String reason,@RequestParam("redirectAddress") String redirectAddress,HttpServletResponse response) throws IOException, NumberFormatException, ParseException
	{
		 String tenantContactType = "";	
		 String tenantEmailPhone = "";
			
		 String ownerContactType = "";
		 String ownerEmailPhone = "";
			
		 String idValues[]=id.split(",");
		 for(int i=0;i<idValues.length;i++)
		 {
		 	 List<MailRoom> mr=mailService.findAll();
			 String fromAddress="";
			 @SuppressWarnings("unused")
			String contactType="";
			 String consNo = "";
			 int propertyId = 0;
				
			 int ii=Integer.parseInt(idValues[i]);
			 Iterator<MailRoom> it=mr.iterator();
			 while(it.hasNext())
			 {
				 MailRoom m=it.next();				
				 if(m.getMlrId()==ii)
				 {
				 	fromAddress=m.getAddressedFrom();
				 	propertyId=m.getPropertyObj().getPropertyId();
				 	consNo = m.getConsignmentNo();
				 }
			 }	
			 
			 /*****  FOR UPDATING MAILROOM DETAILS IN DATABASE *******/
			 if(selectedStatus.equals("Delivered"))
			 {
				logger.info("DELIVERED");
				mailService.updateMailRoomDeliveryStatus(Integer.parseInt(idValues[i]),selectedStatus,getDate(selectedDate),null,null,selectedStatus,getDate(selectedDate),deliveryBoyName,reason,redirectAddress,receivedByName);						
			 }
			 else if(selectedStatus.equals("Redirected"))
			 {
				mailService.updateMailRoomDeliveryStatus(Integer.parseInt(idValues[i]),selectedStatus,null,getDate(selectedDate),null,selectedStatus,getDate(selectedDate),deliveryBoyName,reason,redirectAddress,receivedByName);
			 }
			 else if(selectedStatus.equals("Returned"))
			 {
			 	mailService.updateMailRoomDeliveryStatus(Integer.parseInt(idValues[i]),selectedStatus,null,null,getDate(selectedDate),selectedStatus,getDate(selectedDate),deliveryBoyName,reason,redirectAddress,receivedByName);
			 }
			 /*******  END FOR UPDATING MAILROOM DETAILS IN DATABASE  *******/
				
			 /*******************  FOR GETTING ALL THE TENANT(PERSON) DETAILS ********************/
			 List<Person> tenantList = personService.getAllTenantOnPropetyId(propertyId);
			 int tenantPersonId = 0;
			 String tenantSalutation = "";
			 String tenantFname = "";
			 String tenantLname = "";
			 for (final Person tenantRecord : tenantList ) 
			 {	   
			 	 tenantPersonId = tenantRecord.getPersonId();
			 	 tenantSalutation = tenantRecord.getTitle();
			  	 tenantFname = tenantRecord.getFirstName();
				 tenantLname = tenantRecord.getLastName();
			 }
			 /************************  END FOR GETTING ALL THE TENANT DETAILS  *************************/
				
				
			/***********************  FOR GETTING ALL THE OWNER(PERSON) DETAILS  ****************************/
			List<Person> ownerList = personService.getAllOwnersOnPropetyId(propertyId);
			int ownerPersonId = 0;
			String ownerSalutation = "";
			String ownerFname = "";
			String ownerLname = "";
			for (final Person ownerRecord : ownerList ) 
			{	   
				ownerPersonId = ownerRecord.getPersonId();
				ownerSalutation = ownerRecord.getTitle();
				ownerFname = ownerRecord.getFirstName();
				ownerLname = ownerRecord.getLastName();
			}	
			/**********************  END FOR GETTING ALL THE OWNER DETAILS  ***********************/
			
			List<Contact> c = contactService.findAll(); //FOR GETTING CONTACT DETAILS OF TENANT & OWNER
			Iterator<Contact> itc = c.iterator();
			while(itc.hasNext())
			{
				/******  CHECKING CONTACT DETAILS FOR TENANT BASED ON PERSONID ***********/
				Contact c1 = itc.next();
				if(c1.getPersonId() == tenantPersonId)
				{
					tenantContactType = c1.getContactType();
					tenantEmailPhone = c1.getContactContent();
			  	    if(tenantContactType.equalsIgnoreCase("email"))
					{
					   if(selectedStatus.equals("Delivered"))
					   {
						   
						   EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
							  try {
							  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
							} catch (Exception e) {
								e.printStackTrace();
							}
							  
							  String messageContent="<html>" 
									   
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
											+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA MAILROOM SERVICES.</h2> <br /> Dear "+tenantSalutation+"\t"+tenantFname+" "+tenantLname+ "<br/> "
											+"Good day. This email is to inform you that the mail consignment received from <b>"+fromAddress+"</b> with Consignment number \t" +consNo+ " has been successfully delivered to you.<br/>"								
											+"Thank you very much and have a nice day.<br/><br/>"
											
											+ "<br/>Sincerely,<br/><br/>"
											+ "Manager,<br/>"
											+"MailRoom Services,<br/>"
											+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
											+"</body></html>";
							 
							      emailCredentialsDetailsBean.setToAddressEmail(tenantEmailPhone);
								  emailCredentialsDetailsBean.setMessageContent(messageContent);
							
								  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
							   
							
					   }
					   if(selectedStatus.equals("Redirected"))
					   {
						  logger.info("Single Checkbox - REDIRECTED");
						  
						  
						  EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
						  try {
						  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  
						  String messageContent="<html>"
								   
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAILROOM SERVICES.</h2> <br /> Dear "+tenantSalutation+"\t"+tenantFname+" "+tenantLname+"<br/> <br/> "
										+"Good day. This email is to inform you that we have received a mail from <b>"+fromAddress+"</b>"
										+ "Your consignment was dispatched & got <b>Redirected</b>\t"
										+"with Consignment number \t"+consNo
										+ " \t Redirected Address - "+redirectAddress+" <br/>"
										+ "Contact MailRoom Admin <br/><br/>"
										+"Thank you very much and have a nice day.<br/><br/>"
										+ "<br/>Sincerely,<br/><br/>"
										+ "Manager,<br/>"
										+"MailRoom Services,<br/>"
										+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
										+"</body></html>";
								
						 
						      emailCredentialsDetailsBean.setToAddressEmail(tenantEmailPhone);
							  emailCredentialsDetailsBean.setMessageContent(messageContent);
						
							  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
						 
						  
					   }
					   else if(selectedStatus.equals("Returned"))
					   {
						  logger.info("Single Checkbox - RETURNED");
						  
						  
						  EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
						  try {
						  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						} catch (Exception e) {
							e.printStackTrace();
						}
						  
						  String messageContent="<html>"
								   
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAILROOM SERVICES.</h2> <br /> Dear "+tenantSalutation+"\t"+tenantFname+"\t"+tenantLname+" <br/> <br/> "
										+"Good day. This email is to inform you that we have received a mail from <b>"+fromAddress+"</b>"+" \t with Consignment number\t" +consNo
										+ " \t Your consignment was dispatched & got <b>Returned</b><br/>"
										+"<br/><br/>"
										+ "Reason - "+reason+".<br>Contact MailRoom Admin <br/><br/>"
										+"Thank you very much and have a nice day.<br/><br/>"
										+ "<br/>Sincerely,<br/><br/>"
										+ "Manager,<br/>"
										+"MailRoom Services,<br/>"
										+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
										+ "</body></html>";
						      emailCredentialsDetailsBean.setToAddressEmail(tenantEmailPhone);
							  emailCredentialsDetailsBean.setMessageContent(messageContent);
						
							  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
						 
						 	  
					   }
					}
					if(tenantContactType.equalsIgnoreCase("mobile"))
					{
						final Locale locale = null;
						String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
						String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);				
						String message =  "is received and got "+selectedStatus+".Thank you";
						
						 String userMessage = "Dear "+" "+tenantSalutation+" "+tenantFname+" "+tenantLname+", a mail from "+fromAddress+" "+message+" - Skyon RWA  Administration services.";	

							
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						   
						    smsCredentialsDetailsBean.setNumber(tenantEmailPhone);
							smsCredentialsDetailsBean.setUserName(tenantSalutation);
							smsCredentialsDetailsBean.setMessage(userMessage);
							
							new Thread(new MailRoomSMS(smsCredentialsDetailsBean)).start();
						
						
					 }
				 }
				 /**********  END FOR CHECKING TENANT CONTACT DETAILS  ************/
					
				 /***********  FOR CHECKING OWNER CONTACT DETAILS  BASED ON PERSONID  *************/
				 if(c1.getPersonId() == ownerPersonId)
				 {
					  ownerContactType = c1.getContactType();
					  ownerEmailPhone = c1.getContactContent();
					 
					  //IF Contact Type Is EMAIL
					  if(ownerContactType.equalsIgnoreCase("email"))
					  {
						  if(selectedStatus.equals("Delivered"))
						  {
							  
							   EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
								  try {
								  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
								} catch (Exception e) {
									e.printStackTrace();
								}
								  
								  String messageContent="<html>"
										   
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
												+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAILROOM SERVICES.</h2> <br /> Dear "+ownerSalutation+"\t"+ownerFname+"\t"+ownerLname+" <br/> <br/> "
												+"Good day. This email is to inform you that the mail consignment received from <b>"+fromAddress+"</b> with Consignment number \t" +consNo+ " has been successfully delivered to you.<br/>"																
												+"Thank you very much and have a nice day.<br/><br/>"
												
												+ "<br/>Sincerely,<br/><br/>"
												+ "Manager,<br/>"
												+"MailRoom Services,<br/>"
												+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
												+"</body></html>";
								 
								      emailCredentialsDetailsBean.setToAddressEmail(ownerEmailPhone);
									  emailCredentialsDetailsBean.setMessageContent(messageContent);
								
									  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
							  
						  }
						  if(selectedStatus.equals("Redirected"))
						  {
							 logger.info("Single Checkbox - REDIRECTED");
								 
							 EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
							  try {
							  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
							} catch (Exception e) {
								e.printStackTrace();
							}
							  
							  String messageContent="<html>"
									   
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
											+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">SKYON RWA MAILROOM SERVICES.</h2> <br /> Dear "+ownerSalutation+"\t"+ownerFname+"\t"+ownerLname+" <br/> <br/> "
											+"Good day. This email is to inform you that we have received a mail from <b>"+fromAddress+"</b><br/>"
											+ "Your consignment was dispatched & got <b>Redirected</b>\t"
											+"with Consignment number \t"+consNo
											+ " \t Redirected Address - "+redirectAddress+" <br/>"
											+ "Contact MailRoom Admin <br/><br/>"
											+"Thank you very much and have a nice day.<br/><br/>"
											+ "<br/>Sincerely,<br/><br/>"
											+ "Manager,<br/>"
											+"MailRoom Services,<br/>"
											+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
											+"</body></html>";
									
							 
							      emailCredentialsDetailsBean.setToAddressEmail(ownerEmailPhone);
								  emailCredentialsDetailsBean.setMessageContent(messageContent);
							
								  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
							 
							 
							 
							 
							 
						  }
						  else if(selectedStatus.equals("Returned"))
						  {
							 logger.info("Single Checkbox - RETURNED");
							 
							 
							 
							 
							 
							 
							  EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
							  try {
							  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
							} catch (Exception e) {
								e.printStackTrace();
							}
							  
							  String messageContent="<html>"
									   
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
											+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon RWA MAILROOM SERVICES.</h2> <br /> Dear "+ownerSalutation+"\t"+ownerFname+"\t"+ownerLname+" <br/> <br/> "
											+"Good day. This email is to inform you that we have received a mail from <b>"+fromAddress+"</b>"+" \t with Consignment number\t" +consNo
											+ " \t Your consignment was dispatched & got <b>Returned</b><br/>"
											+"<br/><br/>"
											+ "Reason - "+reason+".<br>Contact MailRoom Admin <br/><br/>"
											+"Thank you very much and have a nice day.<br/><br/>"
											
											+ "<br/>Sincerely,<br/><br/>"
											+ "Manager,<br/>"
											+"MailRoom Services,<br/>"
											+"Skyon RWA<br/><br/> This is an auto generated Email.Please dont revert back"
											+ "</body></html>";
							      emailCredentialsDetailsBean.setToAddressEmail(ownerEmailPhone);
								  emailCredentialsDetailsBean.setMessageContent(messageContent);
							
								  new Thread(new MailRoomMessenger(emailCredentialsDetailsBean)).start();
							 
							 
							 
							 
						  }
					  }
					  
					  //If Contact Type Is MOBILE
					  if(ownerContactType.equalsIgnoreCase("mobile"))
					  {
						  final Locale locale = null;
						  String gateWayUserName = messageSource.getMessage("SMS.users.username", null, locale);
						  String gateWayPassword = messageSource.getMessage("SMS.users.password", null, locale);				
						  String message =  "is received and got "+selectedStatus+".Thank you";
						  
						  
						  
						  String userMessage = "Dear "+ownerSalutation+ " " +ownerFname+" "+ownerLname+", a mail from "+fromAddress+" "+message+" - Skyon RWA  Administration services.";	

							
						  SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						   
						    smsCredentialsDetailsBean.setNumber(ownerEmailPhone);
							smsCredentialsDetailsBean.setUserName(tenantSalutation);
							smsCredentialsDetailsBean.setMessage(userMessage);
							
							new Thread(new MailRoomSMS(smsCredentialsDetailsBean)).start();
						  
						  
						  
					  }
			     }
				 
					
				
		    }
	     }
		//********  END FOR TENANT *******//
		
		PrintWriter out = response.getWriter();
		out.write("Consignment "+selectedStatus+"");
	}	
	
	/**
	* @param str dateString
	* @return Date object
	* @throws ParseException dateParseException
	*/
	private java.sql.Date getDate(String str) throws ParseException 
	{  		 
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");		 
		  java.util.Date date = sdf.parse(str);		  
		  logger.info("date parsed "+date);
		  java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		  return sqlDate;		  
	 }
}

