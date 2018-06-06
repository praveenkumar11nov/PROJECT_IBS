package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.AboutUs;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.CustomerCareNotification;
import com.bcits.bfm.model.Faq;
import com.bcits.bfm.model.Feedback;
import com.bcits.bfm.model.FeedbackChild;
import com.bcits.bfm.model.FeedbackMaster;
import com.bcits.bfm.model.Handbook;
import com.bcits.bfm.model.HelpDeskSettingsEntity;
import com.bcits.bfm.model.HelpTopicEntity;
import com.bcits.bfm.model.Lostandfound;
import com.bcits.bfm.model.MailMaster;
import com.bcits.bfm.model.MeetingRequest;
import com.bcits.bfm.model.Messages;
import com.bcits.bfm.model.OpenNewTicketEntity;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.model.TicketAssignEntity;
import com.bcits.bfm.model.TicketDeptTransferEntity;
import com.bcits.bfm.model.TicketPostInternalNoteEntity;
import com.bcits.bfm.model.TicketPostReplyEntity;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.MailConfigService;
import com.bcits.bfm.service.MeetingRequestService;
import com.bcits.bfm.service.commonAreaMaintenance.CamConsolidationService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantPropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.helpDeskManagement.AboutUsService;
import com.bcits.bfm.service.helpDeskManagement.CustomerCareNotificationService;
import com.bcits.bfm.service.helpDeskManagement.FaqService;
import com.bcits.bfm.service.helpDeskManagement.FeedbackService;
import com.bcits.bfm.service.helpDeskManagement.Feedback_ChildService;
import com.bcits.bfm.service.helpDeskManagement.Feedback_MasterService;
import com.bcits.bfm.service.helpDeskManagement.HandbookService;
import com.bcits.bfm.service.helpDeskManagement.HelpDeskAccessSettingsService;
import com.bcits.bfm.service.helpDeskManagement.HelpTopicService;
import com.bcits.bfm.service.helpDeskManagement.LostandfoundService;
import com.bcits.bfm.service.helpDeskManagement.OpenNewTicketService;
import com.bcits.bfm.service.helpDeskManagement.PostInternalNoteService;
import com.bcits.bfm.service.helpDeskManagement.TicketAssignService;
import com.bcits.bfm.service.helpDeskManagement.TicketDeptTransferService;
import com.bcits.bfm.service.helpDeskManagement.TicketPostReplyService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.MessagesService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.HelpDeskMailSender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.Link;
import com.bcits.bfm.util.SendTicketInfoSMS;
import com.bcits.bfm.util.SendTicketSMS;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.novell.ldap.LDAPException;


/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module:  Customer Care Management
 * Use Case : Help Desk Management
 * 
 * @author Ravi Shankar
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class HelpDeskManagementController extends FilterUnit {

	static Logger logger = LoggerFactory.getLogger(HelpDeskManagementController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private LdapBusinessModel ldapBusinessModel;

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private OpenNewTicketService openNewTicketService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PersonService personService;
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	MessagesService messagesService;
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private TicketPostReplyService ticketPostReplyService;
	
	@Autowired
	private PostInternalNoteService postInternalNoteService;	
	
	@Autowired
	private TicketDeptTransferService ticketDeptTransferService;
	
	@Autowired
	private TicketAssignService ticketAssignService;
	
	@Autowired
	private HelpTopicService helpTopicService;
	
	@Autowired
	HelpDeskAccessSettingsService helpDeskAccessSettingsService;
	
	@Resource
	private JsonResponse errorResponse;
	
	@Resource
	private ValidationUtil validationUtil;
	
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private PropertyService propertyService;
			
	@Autowired
	private Validator validator;
	
	@Autowired
	private CustomerCareNotificationService customerCareNotificationService;
	
	@Autowired
	private FaqService faqService;
	
	@Autowired	
	private HandbookService HandbookService;
	
	@Autowired 
	private LostandfoundService lostandfoundService;
	
	@Autowired
	private AboutUsService aboutUsService;
	
	@Autowired
	private Feedback_ChildService feedback_ChildService;
	
	@Autowired
	private Feedback_MasterService feedback_MasterService;
	
	@Autowired
	private CamConsolidationService camConsolidationService;
	
	@Autowired
	private MeetingRequestService meetingRequestService;
	
	@Autowired
	private MailConfigService mailConfigService;
	
	@RequestMapping(value = "/openNewTickets", method = RequestMethod.GET)
	public String openNewTickets(ModelMap model, HttpServletRequest request) {
		logger.info("In open new tickets method");
		model.addAttribute("ViewName", "Open New Tickets");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Open New Tickets", 1, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "helpDesk/openNewTickets";
	}
	
	@Link(label="Open New Ticket",family="HelpDeskManagementController",parent="Home")
	@RequestMapping("/openNewTicket")
	public String openNewTicket(HttpServletRequest request, Model model) {
		logger.info("In open new ticket method");
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Open New Ticket", 2, request);
		return "helpDesk/openNewTicket";
	}
	
	@RequestMapping(value = "/respondTickets", method = RequestMethod.GET)
	@Link(label="Respond Tickets",family="HelpDeskManagementController",parent="Home")
	public String respondTickets(ModelMap model, HttpServletRequest request) {
		logger.info("In respond tickets Method");
		model.addAttribute("ViewName", "Respond Tickets");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Respond Tickets", 1, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "helpDesk/respondTickets";
	}
	
	@RequestMapping("/respondTicket")
	@Link(label="Respond Ticket",family="HelpDeskManagementController",parent="Open New Ticket")
	public String respondTicket(HttpServletRequest request, Model model) {
		logger.info("In respond ticket method");
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Respond Ticket", 2, request);
		return "helpDesk/respondTicket";
	}
	
	@RequestMapping("/deptTransferedTickets")
	public String deptTransferedTickets(HttpServletRequest request, Model model) {
		logger.info("In deptTransfered tickets method");
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Transfered Tickets", 2, request);
		return "helpDesk/deptTransferedTickets";
	}
	
	@RequestMapping("/escalatedTickets")
	public String escalatedTickets(HttpServletRequest request, Model model) {
		logger.info("In escalated tickets method");
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Escalated Tickets", 2, request);
		return "helpDesk/escalatedTickets";
	}
	
	@RequestMapping("/helpTopic")
	public String helpTopicTickets(HttpServletRequest request, Model model) {
		logger.info("In help topic method");
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Help Topic", 2, request);
		return "helpDesk/helpTopic";
	}
	
	@RequestMapping("/departmentAccessSettings")
	public String departmentAccessSettings(HttpServletRequest request, Model model) {
		logger.info("In department access settings method");
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Department Access Settings", 2, request);
		return "helpDesk/departmentAccessSettings";
	}
		
	@RequestMapping("/createTickets")
	public String createTickets(@ModelAttribute("openNewTicketEntity")OpenNewTicketEntity openNewTicketEntity, BindingResult bindingResult,HttpServletRequest request, Model model) {
		logger.info("In create ticket method");
		model.addAttribute("ViewName", "Create Tickets");
		model.addAttribute("helpTopics", commonController.populateCategories("topicId", "topicName", "HelpTopicEntity"));
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Create Tickets", 1, request);
		return "helpDesk/ticketAdd";
	}
	
	@RequestMapping("/readTickets")
	public String readTickets(@ModelAttribute("openNewTicketEntity")OpenNewTicketEntity openNewTicketEntity, BindingResult bindingResult,HttpServletRequest request, Model model) {
		logger.info("In read ticket method");
		model.addAttribute("ViewName", "Read Ticket Details");
		model.addAttribute("helpTopics", commonController.populateCategories("topicId", "topicName", "HelpTopicEntity"));
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Read Ticket Details", 1, request);
		return "helpDesk/readTickets";
	}
	
	@RequestMapping(value="/createTicket",method=RequestMethod.POST)
	public void createTicket(@ModelAttribute("openNewTicketEntity")OpenNewTicketEntity openNewTicketEntity, BindingResult bindingResult,ModelMap model,HttpServletRequest req,HttpServletResponse res, SessionStatus sessionStatus, final Locale locale) throws Exception{
		logger.info("In create ticket method");
		openNewTicketEntity.setTicketStatus("Open");
		openNewTicketEntity.setIpAddress(req.getLocalAddr());
		openNewTicketEntity.setTicketNumber(genrateTicketNumber());
		openNewTicketEntity.setDept_Id(1321);
		
		openNewTicketService.save(openNewTicketEntity);
		
		res.sendRedirect("home?page=createTickets");
	}
	
	// ****************************** Open New Ticket Read,Create,Update,Delete methods ********************************//

		@RequestMapping(value = "/openNewTickets/openTicketsRead", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object readOpenTicketsData(final Locale locale) {
			
			logger.info("In read open tickets data method");
			try{
				
			List<OpenNewTicketEntity> ticketEntities = openNewTicketService.findALL();
			return ticketEntities;
			
			}catch (Exception e) {
				e.printStackTrace();
				return errorResponse.throwException(locale);
			}
		}
		
		
		@RequestMapping(value = "/openNewTickets/openTicketsReadOnlyPerson", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object readOpenTicketsDataPerson(final Locale locale,HttpServletRequest req) {
		  int personId=Integer.parseInt(req.getParameter("personId"));
			logger.info("In read open tickets data single method");
			try{
				
			List<OpenNewTicketEntity> ticketEntities = openNewTicketService.findALLForPerson(personId);
			return ticketEntities;
			
			}catch (Exception e) {
				e.printStackTrace();
				return errorResponse.throwException(locale);
			}
		}
		
		@SuppressWarnings("deprecation")
		@RequestMapping(value = "/openNewTickets/openTicketCreate", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object saveOpenTicket(@ModelAttribute("newTicketEntity") OpenNewTicketEntity newTicketEntity,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws Exception {

			logger.info("In open ticket create method");
			
			EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
			SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
			
			String mobstatus=req.getParameter("mobstatus");
			System.out.println("mobstatusmobstatusmobstatusmobstatusmobstatus"+mobstatus);
			if(mobstatus!=null){
				
				String createby=req.getParameter("createdBy");
				String updateby=req.getParameter("updatedBy");			
				
				newTicketEntity.setCreatedBy(createby);
				newTicketEntity.setLastUpdatedBy(updateby);
			
				Timestamp ticketCreatedDate = new Timestamp(new java.util.Date().getTime());
				newTicketEntity.setTicketCreatedDate(ticketCreatedDate);
			
				newTicketEntity.setTicketStatus("Open");
				newTicketEntity.setIpAddress(req.getLocalAddr());
				newTicketEntity.setTicketNumber(genrateTicketNumber());
				newTicketEntity.setPropertyId(Integer.parseInt(req.getParameter("propertyId")));
						
				int topicId = Integer.parseInt(req.getParameter("topicId"));
				int personId = Integer.parseInt(req.getParameter("personId"));
				int deptId=helpTopicService.find(topicId).getDept_Id();
				newTicketEntity.setDept_Id(deptId);
				newTicketEntity.setPersonId(personId);
				Person p = openNewTicketService.getPersons(personId);
				
				String property_No = propertyService.getProprtyNoBasedOnPropertyId(Integer.parseInt(req.getParameter("propertyId")));
						
				String toAddressEmail = "";
				String toAddressMobile = "";
				for (Contact contact : p.getContacts()) {
					if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
						toAddressEmail = contact.getContactContent();
					}
					if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
							toAddressMobile = contact.getContactContent();
						}
					}
						
				List<Integer> personIdList= openNewTicketService.getPersonsList(deptId);
				String personName = p.getFirstName();
					
				String ticketNumber = newTicketEntity.getTicketNumber();
				String deptName = openNewTicketService.getDepartements(newTicketEntity.getDept_Id());				
				String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(newTicketEntity.getTopicId());
							
				/*String messagePerson = "help desk.This sms is to inform you that your ticket "+ticketNumber+" created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" is in "+deptName+" department."
									+"Thank you very much contact to our service";*/
				String messagePerson="A complaint with ticket no. "+ticketNumber+" has been logged on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+" and assign to FM-Services. We will confirm you after completion of complaint or reason for unresolved shortly. RWA Services .";
				
				String ticketMailSubj = "New Ticket : Apartment No = "+property_No;
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
										+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services.</h2> <br /> Dear "+personName+", <br/> <br/> "
										+"A complaint with ticket no. "+ticketNumber+"  has been logged on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"  and assign to FM-Services. We will confirm you after completion of<br>"
										+"<br>complaint or reason for unresolved shortly. RWA Services .<br>"
										/*+" (ii) - desired material is not available. Help desk will contact you shortly. RWA Services .<br>"
										+" (iii) - the other flat is closed and after getting the confirmation, will close the complaint. RWA Services .<br>"*/
										+"<br>Thank you very much contact to our service and have a nice day.<br/><br/>"
										
										+ "<br/>Sincerely,<br/><br/>"
										+ "Manager,<br/>"
										+"HelpDesk Services,<br/>"
										+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
										+"</body></html>";
						
						String messageDepartment = "help desk.This sms is to inform you that help desk guy created ticket and assigned to your department.Ticket Details are Ticket Number :"+ticketNumber+", Created Date :"+new Timestamp(new java.util.Date().getTime()).toLocaleString()+", Assigned Department :"+deptName+"."
								+"Thank you very much contact to our service";			
						
						
						List<Person> personList=new ArrayList<>();
						for (Integer integer : personIdList) {
							personList.add(personService.find(integer));
						}
						for (Person person : personList) {
							
							for (Contact contact : person.getContacts()) {
								
								if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
									if(contact.getContactContent() == null || contact.getContactContent().equals("")){
										logger.info("Mobile number is not there for this deparment person");
									}else{	
										
										smsCredentialsDetailsBean.setNumber(contact.getContactContent());
										smsCredentialsDetailsBean.setUserName(person.getFirstName());
										smsCredentialsDetailsBean.setMessage(messagePerson);
										
										new Thread(new SendTicketInfoSMS(smsCredentialsDetailsBean)).start();
									}
								}

								if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
									if(contact.getContactContent() == null || contact.getContactContent().equals("")){
										logger.info("Email id is not there for this deparment person");
									}else{
										
										String messageContentForDepartment = "<html>"						   
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
												+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear "+person.getFirstName()+", <br/> <br/> "
												+"A complaint with ticket no. "+ticketNumber+"  has been logged on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"  and assign to FM-Services. We will confirm you after completion of<br>"
												+"<br>complaint or reason for unresolved shortly. RWA Services .<br>"
												+"Thank you very much, have a nice day.<br/><br/>"
												
												+ "<br/>Sincerely,<br/><br/>"
												+ "Manager,<br/>"
												+"HelpDesk Services,<br/>"
												+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
												+"</body></html>";
										
										emailCredentialsDetailsBean.setToAddressEmail(contact.getContactContent());
										emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
										emailCredentialsDetailsBean.setMessageContent(messageContentForDepartment);
										
									new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
									}
								}					
						  }
						}
						validator.validate(newTicketEntity, bindingResult);			
						if(toAddressEmail == null || toAddressEmail.equals("")){				
								openNewTicketService.save(newTicketEntity);
						}else {	
							
							emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
							emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
							emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
							
							smsCredentialsDetailsBean.setNumber(toAddressMobile);
							smsCredentialsDetailsBean.setUserName(personName);
							smsCredentialsDetailsBean.setMessage(messagePerson);
							System.out.println("message person+++++++++++++++++++"+messagePerson);
							
							new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
							new Thread(new SendTicketInfoSMS(smsCredentialsDetailsBean)).start();				
							
						openNewTicketService.save(newTicketEntity);
						}
						return newTicketEntity; 
			}else{
				
				if(newTicketEntity.getTypeOfTicket().trim().equalsIgnoreCase("Common Area")){
					
					HttpSession session = req.getSession(true);
					String userName = (String) session.getAttribute("userId");
					
					newTicketEntity.setCreatedBy(userName);
					newTicketEntity.setLastUpdatedBy(userName);
				
					Timestamp ticketCreatedDate = new Timestamp(new java.util.Date().getTime());
					newTicketEntity.setTicketCreatedDate(ticketCreatedDate);			
								
					newTicketEntity.setTicketStatus("Open");
					newTicketEntity.setIpAddress(req.getLocalAddr());
					newTicketEntity.setTicketNumber(genrateTicketNumber());
					newTicketEntity.setPersonId(0);
					newTicketEntity.setPropertyId(0);
						
					int topicId = Integer.parseInt(req.getParameter("topicId"));
					int deptId = helpTopicService.find(topicId).getDept_Id();
					newTicketEntity.setDept_Id(deptId);
					String deptName = openNewTicketService.getDepartements(newTicketEntity.getDept_Id());
				    validator.validate(newTicketEntity, bindingResult);	
						
					openNewTicketService.save(newTicketEntity);
					String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(newTicketEntity.getTopicId());
					sendMailAndSmsCodeStaff(newTicketEntity,emailCredentialsDetailsBean,smsCredentialsDetailsBean,"Common Area","Common Area",helpTopicName,deptName);
					
				}else{
					HttpSession session = req.getSession(true);
					String userName = (String) session.getAttribute("userId");
					
					newTicketEntity.setCreatedBy(userName);
					newTicketEntity.setLastUpdatedBy(userName);
				
					newTicketEntity.setTicketCreatedDate(new Timestamp(new java.util.Date().getTime()));		
					newTicketEntity.setTicketStatus("Open");
					newTicketEntity.setIpAddress(req.getLocalAddr());
					newTicketEntity.setTicketNumber(genrateTicketNumber());
					newTicketEntity.setPropertyId(Integer.parseInt(req.getParameter("propertyId")));
						
					int topicId = Integer.parseInt(req.getParameter("topicId"));
					int personId = Integer.parseInt(req.getParameter("personId"));
					int deptId = helpTopicService.find(topicId).getDept_Id();
					newTicketEntity.setDept_Id(deptId);
					newTicketEntity.setPersonId(personId);
					
					openNewTicketService.save(newTicketEntity);
					
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
					String personName = "";			
					if(personNameData!=null){
						personName = (String)personNameData[0];
					}
					String property_No = propertyService.getProprtyNoBasedOnPropertyId(newTicketEntity.getPropertyId());
					String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(newTicketEntity.getTopicId());
					String deptName = openNewTicketService.getDepartements(newTicketEntity.getDept_Id());
					
					sendMailAndSmsCodeStaff(newTicketEntity,emailCredentialsDetailsBean,smsCredentialsDetailsBean,personName,property_No,helpTopicName,deptName);
					sendMailAndSmsCodeConsumer(newTicketEntity,emailCredentialsDetailsBean,smsCredentialsDetailsBean,personName,property_No,helpTopicName,deptName);
				}
			}	
			
		   return newTicketEntity;
		}
		
		@SuppressWarnings("deprecation")
		@Async
		private void sendMailAndSmsCodeStaff(OpenNewTicketEntity newTicketEntity,EmailCredentialsDetailsBean emailCredentialsDetailsBean,SmsCredentialsDetailsBean smsCredentialsDetailsBean,String personName,String property_No,String helpTopicName,String deptName){
			
			List<Integer> personIdList = openNewTicketService.getPersonsList(newTicketEntity.getDept_Id());
			String ticketNumber = newTicketEntity.getTicketNumber();
			String messageDepartment = "help desk.This sms is to inform you that help desk guy created ticket and assigned to your department.Ticket Details are Ticket Number :"+ticketNumber+", Created Date :"+new Timestamp(new java.util.Date().getTime()).toLocaleString()+", Assigned Department :"+deptName+"."
					+"Thank you very much contact to our service";
			int userId = 0;
			String userName = (String)SessionData.getUserDetails().get("userID");
			if(!userName.equalsIgnoreCase("bcitsadmin")){
				userId = usersService.getUserInstanceByLoginName(userName).getUrId();
			}
			for (Integer personId : personIdList) {		
			      if(userId!=0){			    	  
					  Messages messages3 = new Messages();
					  messages3.setUsr_id(openNewTicketService.getUserIdBasedOnPersonId(personId)+"");
					  messages3.setFromUser(userId+"");
					  messages3.setToUser("");
					  messages3.setSubject("New ticket is created");
					  messages3.setMessage("This notification is to inform you that help desk created ticket and assigned to your department. Ticket Details are, Ticket Number :"+ticketNumber+", Created Date :"+new Timestamp(new java.util.Date().getTime()).toLocaleString()+", Assigned Department :"+deptName+"");
					  messages3.setMsg_status("INBOX");
					  messages3.setRead_status(0);    
					  messages3.setNotificationType("MESSAGE");
					     
					  messagesService.save(messages3);
			      }
			    Object[] userPersonNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
				String userPersonName = "";			
				if(userPersonNameData!=null){
					userPersonName = (String)userPersonNameData[0];
				}  
			    List<String> userContactDetailsList = camConsolidationService.getContactDetailsForMail(personId);  
				for (Iterator<?> iterator = userContactDetailsList.iterator(); iterator.hasNext();) {
					
					final Object[] values = (Object[]) iterator.next();
					if(((String)values[0]).equals("Email")){
						String ticketMailSubj = "New Ticket : Apartment No = "+property_No;						
						String messageContentForDepartment = "<html>"						   
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear "+userPersonName+", <br/> <br/> "
								+"Good day. This email is to inform you that following ticket is created and assigned to your department.<br><br>Ticket Details are,<br><br>Apartment No. : "+property_No+"<br>Owner Name : "+personName+"<br>Ticket Number : "+ticketNumber+"<br>Created Date : "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"<br>Help Topic : "+helpTopicName+"<br>Issue Subject : "+newTicketEntity.getIssueSubject()+"<br>Issue Details : "+newTicketEntity.getIssueDetails()+".<br>Assigned Department : "+deptName+"</b><br/><br>For more details, please <a href=http://"+emailCredentialsDetailsBean.getWebsiteUrl()+"/respondTicket >click here</a>.<br><br/>"
								+"Respond as early as possible.<br>"
								+"Thank you very much, have a nice day.<br/><br/>"
								
								+ "<br/>Sincerely,<br/><br/>"
								+ "Manager,<br/>"
								+"HelpDesk Services,<br/>"
								+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
								+"</body></html>";
						emailCredentialsDetailsBean.setToAddressEmail((String)values[1]);
						emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
						emailCredentialsDetailsBean.setMessageContent(messageContentForDepartment);
						
						new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
						
					}else if(((String)values[0]).equals("Mobile")){

						smsCredentialsDetailsBean.setNumber((String)values[1]);
						smsCredentialsDetailsBean.setUserName(userPersonName);
						smsCredentialsDetailsBean.setMessage(messageDepartment);
						
						new Thread(new SendTicketInfoSMS(smsCredentialsDetailsBean)).start();
					}					
			    }
			}
		}
		
		@SuppressWarnings("deprecation")
		@Async
		private void sendMailAndSmsCodeConsumer(OpenNewTicketEntity newTicketEntity,EmailCredentialsDetailsBean emailCredentialsDetailsBean,SmsCredentialsDetailsBean smsCredentialsDetailsBean,String personName,String property_No,String helpTopicName,String deptName){
			
			List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(newTicketEntity.getPersonId());
					
			String toAddressEmail = "";
			String toAddressMobile = "";
			for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();){
				
				final Object[] values = (Object[]) iterator.next();
				if(((String)values[0]).equals("Email")){
					toAddressEmail = (String)values[1];
				}else if(((String)values[0]).equals("Mobile")){
					toAddressMobile = (String)values[1];
				}
			}
			String ticketNumber = newTicketEntity.getTicketNumber();							
			String messagePerson = "A complaint with ticket no. "+ticketNumber+" has been logged on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+" and assign to FM-Services. We will confirm you after completion of complaint or reason for unresolved shortly. RWA Services .";			
			
			String ticketMailSubj = "New Ticket : Apartment No = "+property_No;
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear "+personName+", <br/> <br/> "
								+"A complaint with ticket no. "+ticketNumber+"  has been logged on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"  and assign to FM-Services. We will confirm you after completion of<br>"
								+"<br>complaint or reason for unresolved shortly. RWA Services .<br>"
								
								+ "<br/>Sincerely,<br/><br/>"
								+ "Manager,<br/>"
								+"HelpDesk Services,<br/>"
								+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
								+"</body></html>";
						
				if(toAddressEmail == null || toAddressEmail.equals("")){				
						
				}else {	
					
					emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
					emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
					emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
					
					smsCredentialsDetailsBean.setNumber(toAddressMobile);
					smsCredentialsDetailsBean.setUserName(personName);
					smsCredentialsDetailsBean.setMessage(messagePerson);
					
					new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
					new Thread(new SendTicketSMS(smsCredentialsDetailsBean)).start();
				}
		}
		
		public @ResponseBody Object editOpenTickets(@ModelAttribute("openNewTicketEntity") OpenNewTicketEntity openNewTicketEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

			logger.info("In open ticket update method");
			
			OpenNewTicketEntity temp=openNewTicketService.find(openNewTicketEntity.getTicketId());
			
			openNewTicketEntity.setTicketCreatedDate(temp.getTicketCreatedDate());
			openNewTicketEntity.setTicketUpdateDate(new Timestamp(new java.util.Date().getTime()));
			openNewTicketEntity.setTicketReopenDate(temp.getTicketReopenDate());
			openNewTicketEntity.setDept_Id(helpTopicService.find(openNewTicketEntity.getTopicId()).getDept_Id());
			if(openNewTicketEntity.getDept_Id()== openNewTicketService.find(openNewTicketEntity.getTicketId()).getDept_Id()){
				
			}else{
			openNewTicketEntity.setDeptAcceptanceStatus(null);
			}
			validator.validate(openNewTicketEntity.getIssueSubject(), bindingResult);
			
			openNewTicketService.update(openNewTicketEntity);
			return openNewTicketEntity;
		}

		@RequestMapping(value = "/openNewTickets/openTicketDestroy", method = RequestMethod.GET)
		public @ResponseBody Object deleteOpenTicket(@ModelAttribute("openNewTicketEntity") OpenNewTicketEntity openNewTicketEntity,	BindingResult bindingResult, ModelMap model, SessionStatus ss) {

			logger.info("In open ticket destroy method");
			JsonResponse errorResponse = new JsonResponse();
			final Locale locale = null;
			if(! openNewTicketEntity.getTicketStatus().equalsIgnoreCase("Closed") )
			{
				HashMap<String, String> errorMapResponse = new HashMap<String, String>()
				{
					private static final long serialVersionUID = 1L;

				{
					put("ClosedTicketDestroyError", messageSource.getMessage("OpenNewTicketEntity.ClosedTicketDestroyError", null, locale));
				  }
				};
				errorResponse.setStatus("ClosedTicketDestroyError");
				errorResponse.setResult(errorMapResponse);
				
				return errorResponse;				
			}else{
			try {
				 List <Integer> postReplyIdList = ticketPostReplyService.findAllIds("TicketPostReplyEntity","postReplyId",openNewTicketEntity.getTicketId());
				 for (Integer replyId : postReplyIdList) {
					ticketPostReplyService.delete(replyId);
				}
				 
				 List <Integer> internalNoteIDList = ticketPostReplyService.findAllIds("TicketPostInternalNoteEntity","internalNoteID",openNewTicketEntity.getTicketId());
				 for (Integer internalNoteID : internalNoteIDList) {
					postInternalNoteService.delete(internalNoteID);
				}
				 
				 List <Integer> deptTransIdList = ticketPostReplyService.findAllIds("TicketDeptTransferEntity","deptTransId",openNewTicketEntity.getTicketId());
				 for (Integer deptTransId : deptTransIdList) {
					ticketDeptTransferService.delete(deptTransId);
				}
				 
				 List <Integer> assignIdList = ticketPostReplyService.findAllIds("TicketAssignEntity","assignId",openNewTicketEntity.getTicketId());
				 for (Integer assignId : assignIdList) {
					ticketAssignService.delete(assignId);
				}
				 
				OpenNewTicketEntity findTicketId = openNewTicketService.find(openNewTicketEntity.getTicketId());
				openNewTicketService.delete(findTicketId.getTicketId());
				ss.setComplete();
				return openNewTicketService.findALL();
			} catch (Exception e) {
				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			}
		}
		
		@RequestMapping(value = "/openNewTickets/ticketStatusUpdateFromInnerGrid/{ticketId}", method = { RequestMethod.GET, RequestMethod.POST })
		public void ticketStatusUpdateFromInnerGrid(@PathVariable int ticketId, HttpServletResponse response)
		{	
			logger.info("In ticket status update as closed method");
			openNewTicketService.ticketStatusUpdateFromInnerGrid(ticketId, response);
		}
		
		@RequestMapping(value = "/openNewTickets/departementTicketAcceptanceStatusAsAccept/{ticketId}", method = { RequestMethod.GET, RequestMethod.POST })
		public void departementTicketAcceptanceStatusAsAccept(@PathVariable int ticketId, HttpServletResponse response)
		{	
			logger.info("In department acceptance status update As accepted method");
			openNewTicketService.departementTicketAcceptanceStatusAsAccept(ticketId, response);
		}
		
		@RequestMapping(value = "/openNewTickets/departementTicketAcceptanceStatusAsReject/{ticketId}", method = { RequestMethod.GET, RequestMethod.POST })
		public void departementTicketAcceptanceStatusAsReject(@PathVariable int ticketId, HttpServletResponse response)
		{	
			logger.info("In department acceptance status update as rejected method");
			openNewTicketService.departementTicketAcceptanceStatusAsReject(ticketId, response);
		}
		
		@RequestMapping(value = "/openNewTickets/readTowerNames", method = RequestMethod.GET)
		public @ResponseBody List<?> readTowerNames() {		
			 return openNewTicketService.getTowerNames();
		}		
		
		@RequestMapping(value = "/openNewTickets/readPropertyNumbers", method = RequestMethod.GET)
		public @ResponseBody List<?> readPropertyNumber() {		
			return openNewTicketService.findPropertyNames();
		}	
		
				
		// ****************************** Open New Ticket Filters Data methods ********************************/

		@RequestMapping(value = "/openNewTickets/filter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> getOpenTicketContentsForFilter(@PathVariable String feild) {
			logger.info("In  Open New Ticket Use case filters Method");
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("OpenNewTicketEntity",attributeList, null));
			
			return set;
		}
		
		@RequestMapping(value = "/openNewTickets/getBlockNamesList", method = RequestMethod.GET)
		public @ResponseBody
		List<String> getBlockNames() {
			return openNewTicketService.getAllBlockNames();
		}
		
		@RequestMapping(value = "/openNewTickets/getPersonListBasedOnPropertyNumbers", method = RequestMethod.GET)
		public @ResponseBody List<?> readPersons() {
			logger.info("In get the all owners and tenants method");
			List<OwnerProperty> propertyPersonOwnerList = openNewTicketService.findAllPropertyPersonOwnerList();
			List<TenantProperty> propertyPersonTenantList = openNewTicketService.findAllPropertyPersonTenantList();
			
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> propertyOwnerMap = null;
			Map<String, Object> propertyTenantMap = null;
			for (Iterator<?> iterator = propertyPersonOwnerList.iterator(); iterator.hasNext();) {
				
				final Object[] values = (Object[]) iterator.next();
				propertyOwnerMap = new HashMap<String, Object>();
				
				String str="";
				if((String)values[2]!=null){
					str=(String)values[2];
				}
								
				propertyOwnerMap.put("personName", (String)values[1]+" "+str);	
				propertyOwnerMap.put("propertyId",(Integer)values[0]);
				propertyOwnerMap.put("personId", (Integer)values[3]);	
				propertyOwnerMap.put("personType",(String)values[4]);
				propertyOwnerMap.put("personStyle", (String)values[5]);
			
			    result.add(propertyOwnerMap);				
			}
           for (Iterator<?> iterator = propertyPersonTenantList.iterator(); iterator.hasNext();) {				
				final Object[] values = (Object[]) iterator.next();
				propertyTenantMap = new HashMap<String, Object>();
				
				String str="";
				if((String)values[2]!=null){
					str=(String)values[2];
				}
								
				propertyTenantMap.put("personName", (String)values[1]+" "+str);	
				propertyTenantMap.put("propertyId",(Integer)values[0]);
				propertyTenantMap.put("personId", (Integer)values[3]);	
				propertyTenantMap.put("personType",(String)values[4]);
				propertyTenantMap.put("personStyle", (String)values[5]);	
			
			result.add(propertyTenantMap);
		    }
           return result;
		}    
		
		// Respond Tickets Use case starts from here
				
		@RequestMapping(value = "/respondTickets/respondTicketsRead", method = {RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody List<OpenNewTicketEntity> readRespondTicketsData(HttpServletRequest request) {
			
			logger.info("In read respond tickets data method");
			List<OpenNewTicketEntity> ticketEntities = null;
			HttpSession session = request.getSession(false);
			if(((String)session.getAttribute("userId")).equals("bcitsadmin")){
				ticketEntities = openNewTicketService.findALL();
			}else{
			Users user = usersService.getUserInstanceByLoginName((String)session.getAttribute("userId"));
			List<String> roleName = openNewTicketService.getRoleName(user.getUrId());
			
			if(roleName != null && !roleName.isEmpty()){
				System.out.println("hi");
				ticketEntities = openNewTicketService.findAllTicketsBasedOnDept(user.getDeptId(),user.getUrId());
			}else {
				System.out.println("hi");
				ticketEntities = openNewTicketService.findAllTicketsBasedOnUrId(user.getUrId());
			}
			}
			return ticketEntities;
		}
				
		// ****************************** Post Reply Read,Create,Update,Delete methods ********************************//

		@RequestMapping(value = "/respondTickets/postReplyRead/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<TicketPostReplyEntity> readPostReplyMessages(	@PathVariable int ticketId) {
			logger.info("In read post reply messages method");
			List<TicketPostReplyEntity> replyEntities = ticketPostReplyService.findAllById(ticketId);		
			return replyEntities;
		}

		@RequestMapping(value = "/respondTickets/postReplyCreate/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object savePostReply(@ModelAttribute("replyEntity") TicketPostReplyEntity replyEntity,BindingResult bindingResult, @PathVariable int ticketId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

			logger.info("In save postReply method");
			replyEntity.setTicketId(ticketId);
			//replyEntity.setTicketStatus(openNewTicketService.find(ticketId).getTicketStatus());
            
			validator.validate(replyEntity, bindingResult);
			ticketPostReplyService.save(replyEntity);
			openNewTicketService.lastResponseDateUpdate(ticketId);
			sessionStatus.setComplete();
			return replyEntity;
		}

		@RequestMapping(value = "/respondTickets/postReplyUpdate", method = RequestMethod.GET)
		public @ResponseBody Object editPostReply(@ModelAttribute("replyEntity") TicketPostReplyEntity replyEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

			logger.info("In edit postReply method");			
			TicketPostReplyEntity temp=ticketPostReplyService.find(replyEntity.getPostReplyId());			
			replyEntity.setResponseDate(temp.getResponseDate());
			
			validator.validate(replyEntity, bindingResult);
			ticketPostReplyService.update(replyEntity);
			
			return replyEntity;
		}

		@RequestMapping(value = "/respondTickets/postReplyDestroy", method = RequestMethod.GET)
		public @ResponseBody Object deletePostReply(@ModelAttribute("replyEntity") TicketPostReplyEntity replyEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

			logger.info("In delete postReply method");
			try {
				ticketPostReplyService.delete(replyEntity.getPostReplyId());
				ss.setComplete();
				return replyEntity;
			} catch (Exception e) {
				JsonResponse errorResponse = new JsonResponse();
				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
		}
		
		@RequestMapping(value = "/respondTickets/postReplyFilter/{feild}", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPostReplyContentsForFilter(@PathVariable String feild) {
			logger.info("In post reply use case filters method");
			List<String> attributeList = new ArrayList<String>();
			attributeList.add(feild);
			HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("TicketPostReplyEntity",attributeList, null));
			
			return set;
		}
		
		@RequestMapping(value = "/respondTickets/ticketStatusUpdate/{postReplyId}", method = { RequestMethod.GET, RequestMethod.POST })
		public void ticketStatusUpdate(@PathVariable int postReplyId, HttpServletResponse response)
		{	
			logger.info("In change ticket status as closed method");
			ticketPostReplyService.ticketStatusUpdate(postReplyId, response);
		}
		
		// ****************************** Post Internal Note Read,Create,Update,Delete methods ********************************//

				@RequestMapping(value = "/respondTickets/postInternalNoteRead/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<TicketPostInternalNoteEntity> readPostInternalNotes(	@PathVariable int ticketId) {
					logger.info("In read post internal notes method");
					List<TicketPostInternalNoteEntity> postInternalNoteEntities = postInternalNoteService.findAllById(ticketId);		
					return postInternalNoteEntities;
				}

				@RequestMapping(value = "/respondTickets/postInternalNoteCreate/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object savePostinternalNote(@ModelAttribute("postInternalNoteEntity") TicketPostInternalNoteEntity postInternalNoteEntity,BindingResult bindingResult, @PathVariable int ticketId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws Exception {

					logger.info("In save post internal note method");
					postInternalNoteEntity.setTicketId(ticketId);
					Timestamp internalNoteCreatedDate = new Timestamp(new java.util.Date().getTime());
					postInternalNoteEntity.setInternalNoteCreatedDate(internalNoteCreatedDate);
					//postInternalNoteEntity.setTicketStatus(openNewTicketService.find(ticketId).getTicketStatus());

					validator.validate(postInternalNoteEntity, bindingResult);
					postInternalNoteService.save(postInternalNoteEntity);
					openNewTicketService.lastResponseDateUpdate(ticketId);
					postInternalNoteCode(postInternalNoteEntity);
					sessionStatus.setComplete();
					return postInternalNoteEntity;
				}
				
				@SuppressWarnings("deprecation")
				@Async
				private void postInternalNoteCode(TicketPostInternalNoteEntity postInternalNoteEntity) throws Exception{
					
					
					OpenNewTicketEntity newTicketEntity = postInternalNoteService.getPersonIdBasedOnTicketId(postInternalNoteEntity.getTicketId());
					if(newTicketEntity!=null){
						
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						
						Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(newTicketEntity.getPersonId());
						String personName = "";			
						if(personNameData!=null){
							personName = (String)personNameData[0];
						}
						
						List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(newTicketEntity.getPersonId());
						
						String toAddressEmail = "";
						String toAddressMobile = "";
						for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();){
							
							final Object[] values = (Object[]) iterator.next();
							if(((String)values[0]).equals("Email")){
								toAddressEmail = (String)values[1];
							}else if(((String)values[0]).equals("Mobile")){
								toAddressMobile = (String)values[1];
							}
						}
						
						String ticketNumber = newTicketEntity.getTicketNumber();	
						String property_No = propertyService.getProprtyNoBasedOnPropertyId(newTicketEntity.getPropertyId());
						String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(newTicketEntity.getTopicId());
						String deptName = openNewTicketService.getDepartements(newTicketEntity.getDept_Id());
						
						/*String messagePerson = "help desk. This sms is to inform you that your ticket "+ticketNumber+" created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" is in "+deptName+" department."
								+" Thank you very much contact to our service";	*/
						/*String messagePerson = "A complaint with ticket no. "+ticketNumber+" on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+" has been parked due to (i) - your flat found locked, please contact help desk. (ii) - desired material is not available. Help desk will contact you shortly. (iii) - the other flat is closed and after getting the confirmation, will close the complaint.";*/	
						
						
						String option=null;
						if(postInternalNoteEntity.getInternalNoteTitle().equalsIgnoreCase("Material is not available"))
						{
							option="Desired material is not available. Help desk will contact you shortly. RWA Services .";
						}
						else if(postInternalNoteEntity.getInternalNoteTitle().equalsIgnoreCase("Flat found Locked"))
						{
							option="Your flat found locked, please contact help desk. RWA Services .";
						}
						else if(postInternalNoteEntity.getInternalNoteTitle().equalsIgnoreCase("Other Flat found Locked"))
						{
							option="The other flat is closed and after getting the confirmation, will close the complaint. RWA Services .";
						}
						else if(postInternalNoteEntity.getInternalNoteTitle().equalsIgnoreCase("Problem Onhold"))
						{
							option="Problem Onhold";
						}
						String messagePerson = "A complaint with ticket no. "+ticketNumber+" on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+" has been parked due to (i) - "+option+". Description : "+postInternalNoteEntity.getInternalNoteDetails()+" .";
						String ticketMailSubj = "Unresolved Ticket : Apartment No = "+property_No;
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
											+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear "+personName+", <br/> <br/> "
											+"A complaint with ticket no. "+ticketNumber+" on "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+" has been parked due to<br>"
											/*+"<br>(i) - your flat found locked, please contact help desk. RWA Services .<br>"
											+" (ii) - desired material is not available. Help desk will contact you shortly. RWA Services .<br>"
											+" (iii) - the other flat is closed and after getting the confirmation, will close the complaint. RWA Services .<br>"*/
											+" <br>(i) - "+option
											+"<br>Description:"
											+postInternalNoteEntity.getInternalNoteDetails()
											+"<br><br>Thank you very much contact to our service and have a nice day.<br/><br/>"
											
											+ "<br/>Sincerely,<br/><br/>"
											+ "Manager,<br/>"
											+"HelpDesk Services,<br/>"
											+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
											+"</body></html>";
									
							if(toAddressEmail == null || toAddressEmail.equals("")){				
									
							}else {	
								
								emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
								emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
								emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
								
								smsCredentialsDetailsBean.setNumber(toAddressMobile);
								smsCredentialsDetailsBean.setUserName(personName);
								smsCredentialsDetailsBean.setMessage(messagePerson);
								System.out.println("sms content+++++++++++++++++++++++++++"+messagePerson);
								
								new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
								new Thread(new SendTicketSMS(smsCredentialsDetailsBean)).start();
							}
					}
					
				}

				@RequestMapping(value = "/respondTickets/postInternalNoteUpdate", method = RequestMethod.GET)
				public @ResponseBody Object editPostInternalNote(@ModelAttribute("postInternalNoteEntity") TicketPostInternalNoteEntity postInternalNoteEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

					logger.info("In edit post internal note method");
					TicketPostInternalNoteEntity temp=postInternalNoteService.find(postInternalNoteEntity.getInternalNoteID());			
					postInternalNoteEntity.setInternalNoteCreatedDate(temp.getInternalNoteCreatedDate());
					
					validator.validate(postInternalNoteEntity, bindingResult);
					postInternalNoteService.update(postInternalNoteEntity);
					
					return postInternalNoteEntity;
				}

				@RequestMapping(value = "/respondTickets/postInternalNoteDestroy", method = RequestMethod.GET)
				public @ResponseBody Object deletePostInternalNote(@ModelAttribute("postInternalNoteEntity") TicketPostInternalNoteEntity postInternalNoteEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

					logger.info("In delete post internal note method");
					try {
						postInternalNoteService.delete(postInternalNoteEntity.getInternalNoteID());
						ss.setComplete();
						return postInternalNoteEntity;
					} catch (Exception e) {
						JsonResponse errorResponse = new JsonResponse();
						errorResponse.setStatus("CHILD");
						return errorResponse;
					}
				}
				
				@RequestMapping(value = "/respondTickets/internalNoteFilter/{feild}", method = RequestMethod.GET)
				public @ResponseBody Set<?> getInternalNoteContentsForFilter(@PathVariable String feild, HttpServletRequest req) {
					logger.info("In internal note use case filter method");
					List<String> attributeList = new ArrayList<String>();
					attributeList.add(feild);
					HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("TicketPostInternalNoteEntity",attributeList, null));
					
					return set;
				}
				
				@RequestMapping(value = "/respondTickets/ticketStatusUpdateOnPostInternalNote/{ticketId}", method = { RequestMethod.GET, RequestMethod.POST })
				@SuppressWarnings("deprecation")
				public void ticketStatusUpdateOnPostInternalNote(@PathVariable int ticketId, HttpServletResponse response,final Locale locale) throws Exception
				{		
					logger.info("In change ticket status as closed method");
					String statusOperation=openNewTicketService.ticketStatusUpdateAsClose(ticketId, response);
					
					/*if(openNewTicketService.find(ticketId).getTicketStatus().equals("Closed")){*/
					if(statusOperation.equals("Success")){
						
						OpenNewTicketEntity  newTicketEntity = openNewTicketService.find(ticketId);
						
						if(newTicketEntity.getTypeOfTicket().trim().equalsIgnoreCase("Common Area")){
							
						}else{
							EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
							SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
							
							Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(newTicketEntity.getPersonId());
							String personName = "";			
							if(personNameData!=null){
								personName = (String)personNameData[0];
							}
							String property_No = propertyService.getProprtyNoBasedOnPropertyId(newTicketEntity.getPropertyId());
							
							List<String> contactDetailsList = camConsolidationService.getContactDetailsForMail(newTicketEntity.getPersonId());
							
							String toAddressEmail = "";
							String toAddressMobile = "";
							for (Iterator<?> iterator = contactDetailsList.iterator(); iterator.hasNext();){
								
								final Object[] values = (Object[]) iterator.next();
								if(((String)values[0]).equals("Email")){
									toAddressEmail = (String)values[1];
								}else if(((String)values[0]).equals("Mobile")){
									toAddressMobile = (String)values[1];
								}
							}
							String ticketNumber = newTicketEntity.getTicketNumber();
							String deptName = openNewTicketService.getDepartements(newTicketEntity.getDept_Id());
							String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(newTicketEntity.getTopicId());
							
							/*String messagePerson = "help desk. This sms is to inform you that your ticket "+ticketNumber+" closed on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" ,and closed by "+deptName+" department."
											+"Thank you very much contact to our service";*/
							String messagePerson ="A complaint with ticket no. "+ticketNumber+" logged on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" has been closed by FM-Services. In case of any unresolved issue, please contact to Help Desk. RWA Services .";
							String ticketMailSubj = "Closed Ticket : Apartment No = "+property_No;
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
									+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear "+personName+", <br/> <br/> "
									+"A complaint with ticket no. "+ticketNumber+" logged on "+new Timestamp(newTicketEntity.getTicketCreatedDate().getTime()).toLocaleString()+" has been closed by FM-Services. <br>"
									+"<br>In case of any unresolved issue, please contact to Help Desk. RWA Services .<br>"
									
									+ "<br/>Sincerely,<br/><br/>"
									+ "Manager,<br/>"
									+"HelpDesk Services,<br/>"
									+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
									+"</body></html>";
							
						if(toAddressEmail == null || toAddressEmail.equals("")){
							
						}else {	
							
							emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
							emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
							emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
							
							smsCredentialsDetailsBean.setNumber(toAddressMobile);
							smsCredentialsDetailsBean.setUserName(personName);
							smsCredentialsDetailsBean.setMessage(messagePerson);
							
							new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
							new Thread(new SendTicketSMS(smsCredentialsDetailsBean)).start();
						}
						}
						
					}					
				}
				
				// ****************************** Department Transfer  Read,Create,Update,Delete methods ********************************//

				@RequestMapping(value = "/respondTickets/departmentTransferRead/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<TicketDeptTransferEntity> readDepartmentTransfer(	@PathVariable int ticketId) {
					logger.info("In read department transfer method");
					List<TicketDeptTransferEntity> ticketDeptTransferEntities = ticketDeptTransferService.findAllById(ticketId);		
					return ticketDeptTransferEntities;
				}

				@RequestMapping(value = "/respondTickets/departmentTransferCreate/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object saveDepartmentTransfer(@ModelAttribute("ticketDeptTransferEntity") TicketDeptTransferEntity ticketDeptTransferEntity,BindingResult bindingResult, @PathVariable int ticketId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

					logger.info("In save department transfer method");
					ticketDeptTransferEntity.setTicketId(ticketId);
					Timestamp internalNoteCreatedDate = new Timestamp(new java.util.Date().getTime());
					ticketDeptTransferEntity.setTransferDate(internalNoteCreatedDate);
					
					ticketDeptTransferEntity.setPrevDeptId(openNewTicketService.find(ticketDeptTransferEntity.getTicketId()).getDept_Id());

					validator.validate(ticketDeptTransferEntity, bindingResult);
					ticketDeptTransferService.save(ticketDeptTransferEntity);
					
					ticketDeptTransferService.deptIdUpdate(ticketId,ticketDeptTransferEntity.getDept_Id());
					ticketDeptTransferService.helpTopicUpdate(ticketId,ticketDeptTransferEntity.getTopicId());
					ticketDeptTransferService.updateTicketDeptAcceptanceStatus(ticketId);
					ticketDeptTransferService.updateTicketStatus(ticketId);
					ticketDeptTransferService.updateTicketLastResponse(ticketId);
					
					sessionStatus.setComplete();
					return ticketDeptTransferEntity;
				}

				@RequestMapping(value = "/respondTickets/departmentTransferUpdate", method = RequestMethod.GET)
				public @ResponseBody Object editDepartmentTransfer(@ModelAttribute("ticketDeptTransferEntity") TicketDeptTransferEntity ticketDeptTransferEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

					logger.info("In edit department transfer method");
					TicketDeptTransferEntity temp=ticketDeptTransferService.find(ticketDeptTransferEntity.getDeptTransId());			
					ticketDeptTransferEntity.setTransferDate(temp.getTransferDate());
					
					ticketDeptTransferEntity.setPrevDeptId(openNewTicketService.find(ticketDeptTransferEntity.getTicketId()).getDept_Id());
					
					validator.validate(ticketDeptTransferEntity, bindingResult);
					ticketDeptTransferService.update(ticketDeptTransferEntity);
					
					return ticketDeptTransferEntity;
				}

				@RequestMapping(value = "/respondTickets/departmentTransferDestroy", method = RequestMethod.GET)
				public @ResponseBody Object deleteDepartmentTransfer(@ModelAttribute("ticketDeptTransferEntity") TicketDeptTransferEntity ticketDeptTransferEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

					logger.info("In delete department transfer method");
					JsonResponse errorResponse = new JsonResponse();
					final Locale locale = null;
					if(!openNewTicketService.find(ticketDeptTransferEntity.getTicketId()).getTicketStatus().equalsIgnoreCase("Closed") )
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>()
						{
						private static final long serialVersionUID = 1L;

						{
							put("ClosedTicketDestroyError", messageSource.getMessage("TicketDeptTransferEntity.ClosedTicketDestroyError", null, locale));
						  }
						};
						errorResponse.setStatus("ClosedTicketDestroyError");
						errorResponse.setResult(errorMapResponse);
						
						return errorResponse;				
					}else{
					try {
						ticketDeptTransferService.delete(ticketDeptTransferEntity.getDeptTransId());
						ss.setComplete();
						return ticketDeptTransferEntity;
					} catch (Exception e) {
						errorResponse.setStatus("CHILD");
						return errorResponse;
					}
					}
				}
				
				@RequestMapping(value = "/respondTickets/departmentTransferFilter/{feild}", method = RequestMethod.GET)
				public @ResponseBody Set<?> getDepartmentTransferContentsForFilter(@PathVariable String feild) {
					logger.info("In department transfered ticket use case filters method");
					List<String> attributeList = new ArrayList<String>();
					attributeList.add(feild);
					HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("TicketDeptTransferEntity",attributeList, null));
					
					return set;
				}
				
				
				// ****************************** Ticket Assign Read,Create,Update,Delete methods ********************************//

				@RequestMapping(value = "/respondTickets/ticketAssignRead/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<TicketAssignEntity> readTicketAssign(	@PathVariable int ticketId) {
					logger.info("In read ticket assign method");
					List<TicketAssignEntity> ticketAssignEntities = ticketAssignService.findAllById(ticketId);		
					return ticketAssignEntities;
				}

				@SuppressWarnings("deprecation")
				@RequestMapping(value = "/respondTickets/ticketAssignCreate/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object saveTicketAssign(@ModelAttribute("ticketAssignEntity") TicketAssignEntity ticketAssignEntity,BindingResult bindingResult, @PathVariable int ticketId,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws Exception {

					logger.info("In save ticket assign method");
					ticketAssignEntity.setTicketId(ticketId);
					Timestamp assignDate = new Timestamp(new java.util.Date().getTime());
					ticketAssignEntity.setAssignDate(assignDate);
					ticketAssignEntity.setCreatedBy((String)SessionData.getUserDetails().get("userID"));
					
					Person p = usersService.find(ticketAssignEntity.getUrId()).getPerson();
					String toAddressEmail = "";
					String toAddressMobile = "";
					for (Contact contact : p.getContacts()) {
						if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
							toAddressEmail = contact.getContactContent();
						}
						if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
							toAddressMobile = contact.getContactContent();
						}
					}
					validator.validate(ticketAssignEntity, bindingResult);
					if(toAddressEmail == null || toAddressEmail.equals("")){
						ticketAssignService.save(ticketAssignEntity);
					}else {
						
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						
						String personName = p.getFirstName();
						OpenNewTicketEntity ont = openNewTicketService.find(ticketAssignEntity.getTicketId());
						String ticketNumber = ont.getTicketNumber();
						String deptName = openNewTicketService.getDepartements(ont.getDept_Id());
						
						String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(ont.getTopicId());
						
						String property_No = propertyService.getProprtyNoBasedOnPropertyId(ont.getPropertyId());
						String ticketMailSubj = "Assigned Ticket : Apartment No = "+property_No;						
						String messageContent = "<html>"						   
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear "+personName+", <br/> <br/> "
								+"Good day. This email is to inform you that following ticket is created and assigned to you.<br><br>Ticket Details are,<br><br>Apartment No. : "+property_No+"<br>Owner Name : "+personName+"<br>Ticket Number : "+ticketNumber+"<br>Created Date : "+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"<br>Help Topic : "+helpTopicName+"<br>Issue Subject : "+ont.getIssueSubject()+"<br>Issue Details : "+ont.getIssueDetails()+".<br>Assigned Department : "+deptName+"</b><br/><br>For more details, please <a href=http://"+emailCredentialsDetailsBean.getWebsiteUrl()+"/respondTicket >click here</a>.<br/><br>"
								+"Thank you very much contact to our service and have a nice day.<br/><br/>"
								
								+ "<br/>Sincerely,<br/><br/>"
								+ "Manager,<br/>"
								+"HelpDesk Services,<br/>"
								+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
								+"</body></html>";
						
						emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
						emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
						emailCredentialsDetailsBean.setMessageContent(messageContent);
						
						new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
						
						String message = "help desk.This sms is to inform you that one ticket assigned to you, ticket number "+ticketNumber+" created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" and "+ deptName+" department."
										+"Thank you very much contact to our service";
						
						smsCredentialsDetailsBean.setNumber(toAddressMobile);
						smsCredentialsDetailsBean.setUserName(personName);
						smsCredentialsDetailsBean.setMessage(message);
						
						new Thread(new SendTicketInfoSMS(smsCredentialsDetailsBean)).start();						
					
						 ticketAssignService.save(ticketAssignEntity);
					}
					
					openNewTicketService.updateAssignTicketDate(ticketId);
					openNewTicketService.updateTicketStatusAsAssigned(ticketId);
					sessionStatus.setComplete();
					return ticketAssignEntity;
				}

				@RequestMapping(value = "/respondTickets/ticketAssignUpdate", method = RequestMethod.GET)
				public @ResponseBody Object editTicketAssign(@ModelAttribute("ticketAssignEntity") TicketAssignEntity ticketAssignEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

					logger.info("In edit ticket assign method");
					TicketAssignEntity temp=ticketAssignService.find(ticketAssignEntity.getAssignId());			
					ticketAssignEntity.setAssignDate(temp.getAssignDate());
					
					validator.validate(ticketAssignEntity, bindingResult);
					ticketAssignService.update(ticketAssignEntity);
					
					return ticketAssignEntity;
				}

				@RequestMapping(value = "/respondTickets/ticketAssignDestroy", method = RequestMethod.GET)
				public @ResponseBody Object deleteTicketAssign(@ModelAttribute("ticketAssignEntity") TicketAssignEntity ticketAssignEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

					logger.info("In delete ticket assign method");
					JsonResponse errorResponse = new JsonResponse();
					final Locale locale = null;
					if(!openNewTicketService.find(ticketAssignEntity.getTicketId()).getTicketStatus().equalsIgnoreCase("Closed") )
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>()
						{
						  private static final long serialVersionUID = 1L;
						{
							put("ClosedTicketDestroyError", messageSource.getMessage("TicketAssignEntity.ClosedTicketDestroyError", null, locale));
						  }
						};
						errorResponse.setStatus("ClosedTicketDestroyError");
						errorResponse.setResult(errorMapResponse);
						
						return errorResponse;				
					}else{
					try {
						ticketAssignService.delete(ticketAssignEntity.getAssignId());
						ss.setComplete();
						return ticketAssignEntity;
					} catch (Exception e) {
						errorResponse.setStatus("CHILD");
						return errorResponse;
					}
					}
				}
				
				@RequestMapping(value = "/escalatedTickets/escalatedTicketRead", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<TicketAssignEntity> escalatedTicketRead() {
					
					logger.info("In escalated ticket read data method");
					List<TicketAssignEntity> ticketEscalatedEntities = ticketAssignService.findAllData();
					return ticketEscalatedEntities;
				}
				
				@RequestMapping(value = "/respondTickets/ticketAssignFilter/{feild}", method = RequestMethod.GET)
				public @ResponseBody Set<?> getTicketAssignContentsForFilter(@PathVariable String feild) {
					logger.info("In escalated ticket use case filters method");
					List<String> attributeList = new ArrayList<String>();
					attributeList.add(feild);
					HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("TicketAssignEntity",attributeList, null));
					
					return set;
				}
				
				
				@RequestMapping(value = "/respondTickets/readUserLogins/{dept_Id}", method = RequestMethod.GET)
				public @ResponseBody List<?> readUserLogins(@PathVariable int dept_Id)
				{
					logger.info("In retrieve all the users based on department id method");
				    return ticketAssignService.findUsers(dept_Id);
				}
				
				@RequestMapping(value = "/deptTransferedTickets/transferDeptRead", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<TicketDeptTransferEntity> transferDeptReadData() {
					
					logger.info("In transfer department read data method");
					List<TicketDeptTransferEntity> ticketDeptTransferEntities = ticketDeptTransferService.findAllData();
					return ticketDeptTransferEntities;
				}
				
				@RequestMapping(value = "/openNewTickets/displayConversation/{ticketId}", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<?> displayConversation(@PathVariable int ticketId) {
					
					logger.info("In display conversation of the selected ticket method");
					
					List<TicketPostReplyEntity> replyEntities = ticketPostReplyService.findAllByAscOrder(ticketId);					
					List<TicketPostInternalNoteEntity> postInternalNoteEntities = postInternalNoteService.findAllByAscOrder(ticketId);
					
					List<TicketDeptTransferEntity> deptTransferEntities = ticketDeptTransferService.findAllByAscOrder(ticketId);					
					List<TicketAssignEntity> ticketAssignEntities = ticketAssignService.findAllByAscOrder(ticketId);
					
					Map<Timestamp, Object> result = new HashMap<Timestamp, Object>();
					
					for (Iterator<TicketPostReplyEntity> iterator = replyEntities.iterator(); iterator.hasNext();) {
						
						TicketPostReplyEntity ticketPostReplyEntity = (TicketPostReplyEntity) iterator.next();
						result.put(ticketPostReplyEntity.getResponseDate(), ticketPostReplyEntity);
					}

					for (Iterator<TicketPostInternalNoteEntity> iterator = postInternalNoteEntities.iterator(); iterator.hasNext();) {
						
						TicketPostInternalNoteEntity ticketPostInternalNoteEntity = (TicketPostInternalNoteEntity) iterator.next();
						result.put(ticketPostInternalNoteEntity.getInternalNoteCreatedDate(), ticketPostInternalNoteEntity);						
					}
					
					for (Iterator<TicketDeptTransferEntity> iterator = deptTransferEntities.iterator(); iterator.hasNext();) {
						
						TicketDeptTransferEntity ticketDeptTransferEntity = (TicketDeptTransferEntity) iterator.next();
						ticketDeptTransferEntity.setDepartmentObj(departmentService.find(ticketDeptTransferEntity.getDept_Id()));
						ticketDeptTransferEntity.setPrevDepartmentObj(departmentService.find(ticketDeptTransferEntity.getPrevDeptId()));
						result.put(ticketDeptTransferEntity.getTransferDate(), ticketDeptTransferEntity);
					}

					for (Iterator<TicketAssignEntity> iterator = ticketAssignEntities.iterator(); iterator.hasNext();) {
						
						TicketAssignEntity ticketAssignEntity = (TicketAssignEntity) iterator.next();
						ticketAssignEntity.setUrLoginName(usersService.find(ticketAssignEntity.getUrId()).getUrLoginName());
						result.put(ticketAssignEntity.getAssignDate(), ticketAssignEntity);			
					}
					  
					  Set<Timestamp> timeSet = result.keySet();
					  List<Timestamp> timeList = new ArrayList<Timestamp>(timeSet);
					  Collections.sort(timeList);				  
					  
					  List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
					  
					  Map<String,Object> finalMap = null;
					  for (Iterator<Timestamp> iterator2 = timeList.iterator(); iterator2.hasNext();)
					  {
						   Timestamp timestamp = (Timestamp) iterator2.next();
						   Object obj  =  result.get(timestamp);
						   finalMap = new HashMap<String, Object>();
						   
						   if (obj instanceof TicketPostReplyEntity) {
							   finalMap.put("TicketPostReplyEntity", obj);
						   } else if (obj instanceof TicketPostInternalNoteEntity){
							   finalMap.put("TicketPostInternalNoteEntity", obj);
						   } else if (obj instanceof TicketDeptTransferEntity){
							   finalMap.put("TicketDeptTransferEntity", obj);
						   } else{
							   finalMap.put("TicketAssignEntity", obj);
						   }
						   objList.add(finalMap);
					  }

					  return objList;
				}
				
				// ****************************** Help Topic Read,Create,Update,Delete methods ********************************//

				@RequestMapping(value = "/helpTopics/helpTopicRead", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<HelpTopicEntity> readHelpTopic() {
					logger.info("In read help topic method");
					List<HelpTopicEntity> topicEntities = helpTopicService.findAll();
					return topicEntities;
				}

				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/helpTopics/helpTopicCreate", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object saveHelpTopic(@RequestBody Map<String, Object> map,@ModelAttribute("helpTopicEntity") HelpTopicEntity helpTopicEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

					logger.info("In save help topic method");	
					String level1NotifiedUsers = "";
					if(map.get("level1NotifiedUsers")!=null){
						if (map.get("level1NotifiedUsers") instanceof List) {
							List<?> projects = (ArrayList<?>)map.get("level1NotifiedUsers");
							for (Iterator<?> iterator = projects.iterator(); iterator.hasNext();){
								final Map<String, Object> values = (Map<String, Object>)iterator.next();
								level1NotifiedUsers+=values.get("urId")+",";
							}
						}
					}
					
					String level2NotifiedUsers = "";
					if(map.get("level2NotifiedUsers")!=null){
						if (map.get("level2NotifiedUsers") instanceof List) {
							List<?> projects = (ArrayList<?>)map.get("level2NotifiedUsers");
							for (Iterator<?> iterator = projects.iterator(); iterator.hasNext();){
								final Map<String, Object> values = (Map<String, Object>)iterator.next();
								level2NotifiedUsers+=values.get("urId")+",";
							}
						}
					}
					
					String level3NotifiedUsers = "";
					if(map.get("level3NotifiedUsers")!=null){
						if (map.get("level3NotifiedUsers") instanceof List) {
							List<?> projects = (ArrayList<?>)map.get("level3NotifiedUsers");
							for (Iterator<?> iterator = projects.iterator(); iterator.hasNext();){
								final Map<String, Object> values = (Map<String, Object>)iterator.next();
								level3NotifiedUsers+=values.get("urId")+",";
							}
						}
					}
					
					helpTopicEntity.setTopicName((String)map.get("topicName"));
					helpTopicEntity.setTopicDesc((String)map.get("topicDesc"));
					helpTopicEntity.setDept_Id((Integer)map.get("dept_Id"));
					helpTopicEntity.setNormalSLA((Integer)map.get("normalSLA"));
					
					helpTopicEntity.setLevel1SLA((Integer)map.get("level1SLA"));
					helpTopicEntity.setLevel1User((Integer)map.get("level1User"));
					helpTopicEntity.setLevel1NotifiedUsers(level1NotifiedUsers);
					
					helpTopicEntity.setLevel2SLA((Integer)map.get("level2SLA"));
					helpTopicEntity.setLevel2User((Integer)map.get("level2User"));
					helpTopicEntity.setLevel2NotifiedUsers(level2NotifiedUsers);
					
					helpTopicEntity.setLevel3SLA((Integer)map.get("level3SLA"));
					helpTopicEntity.setLevel3User((Integer)map.get("level3User"));
					helpTopicEntity.setLevel3NotifiedUsers(level3NotifiedUsers);
					
					helpTopicEntity.setStatus("Inactive");
					Timestamp createdDate = new Timestamp(new java.util.Date().getTime());
					helpTopicEntity.setCreatedDate(createdDate);
					
					validator.validate(helpTopicEntity, bindingResult);
					helpTopicService.save(helpTopicEntity);
					sessionStatus.setComplete();
					return helpTopicEntity;
				}

				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/helpTopics/helpTopicUpdate", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object editHelpTopic(@RequestBody Map<String, Object> map,@ModelAttribute("helpTopicEntity") HelpTopicEntity helpTopicEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

					logger.info("In edit help topic method");
					HelpTopicEntity temp = helpTopicService.find((Integer)map.get("topicId"));
					helpTopicEntity.setTopicId((Integer)map.get("topicId"));
					helpTopicEntity.setCreatedDate(temp.getCreatedDate());
					helpTopicEntity.setStatus(temp.getStatus());
					helpTopicEntity.setCreatedBy(temp.getCreatedBy());
					helpTopicEntity.setLastUpdatedBy(temp.getLastUpdatedBy());
					helpTopicEntity.setLastUpdatedDT(temp.getLastUpdatedDT());
					
					String level1NotifiedUsers = "";
					if(map.get("level1NotifiedUsers")!=null){
						if (map.get("level1NotifiedUsers") instanceof List) {
							List<?> projects = (ArrayList<?>)map.get("level1NotifiedUsers");
							for (Iterator<?> iterator = projects.iterator(); iterator.hasNext();){
								final Map<String, Object> values = (Map<String, Object>)iterator.next();
								level1NotifiedUsers+=values.get("urId")+",";
							}
						}
					}
					
					String level2NotifiedUsers = "";
					if(map.get("level2NotifiedUsers")!=null){
						if (map.get("level2NotifiedUsers") instanceof List) {
							List<?> projects = (ArrayList<?>)map.get("level2NotifiedUsers");
							for (Iterator<?> iterator = projects.iterator(); iterator.hasNext();){
								final Map<String, Object> values = (Map<String, Object>)iterator.next();
								level2NotifiedUsers+=values.get("urId")+",";
							}
						}
					}
					
					String level3NotifiedUsers = "";
					if(map.get("level3NotifiedUsers")!=null){
						if (map.get("level3NotifiedUsers") instanceof List) {
							List<?> projects = (ArrayList<?>)map.get("level3NotifiedUsers");
							for (Iterator<?> iterator = projects.iterator(); iterator.hasNext();){
								final Map<String, Object> values = (Map<String, Object>)iterator.next();
								level3NotifiedUsers+=values.get("urId")+",";
							}
						}
					}
					
					helpTopicEntity.setTopicName((String)map.get("topicName"));
					helpTopicEntity.setTopicDesc((String)map.get("topicDesc"));
					helpTopicEntity.setDept_Id((Integer)map.get("dept_Id"));
					helpTopicEntity.setNormalSLA((Integer)map.get("normalSLA"));
					
					helpTopicEntity.setLevel1SLA((Integer)map.get("level1SLA"));
					helpTopicEntity.setLevel1User((Integer)map.get("level1User"));
					helpTopicEntity.setLevel1NotifiedUsers(level1NotifiedUsers);
					
					helpTopicEntity.setLevel2SLA((Integer)map.get("level2SLA"));
					helpTopicEntity.setLevel2User((Integer)map.get("level2User"));
					helpTopicEntity.setLevel2NotifiedUsers(level2NotifiedUsers);
					
					helpTopicEntity.setLevel3SLA((Integer)map.get("level3SLA"));
					helpTopicEntity.setLevel3User((Integer)map.get("level3User"));
					helpTopicEntity.setLevel3NotifiedUsers(level3NotifiedUsers);
					
					validator.validate(helpTopicEntity, bindingResult);
					helpTopicService.update(helpTopicEntity);
					
					return helpTopicEntity;
				}

				@RequestMapping(value = "/helpTopics/helpTopicDestroy", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object deleteHelpTopic(@RequestBody Map<String, Object> map,@ModelAttribute("helpTopicEntity") HelpTopicEntity helpTopicEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

					logger.info("In delete help topic method");
					JsonResponse errorResponse = new JsonResponse();
					final Locale locale = null;
					if(((String)map.get("status")).equalsIgnoreCase("Active") )
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>()
						{
							private static final long serialVersionUID = 1L;
						{
							put("AciveHelpTopicDestroyError", messageSource.getMessage("HelpTopicEntity.AciveHelpTopicDestroyError", null, locale));
						  }
						};
						errorResponse.setStatus("AciveHelpTopicDestroyError");
						errorResponse.setResult(errorMapResponse);
						
						return errorResponse;				
					}else{
					try {
						helpTopicService.delete((Integer)map.get("topicId"));
						ss.setComplete();
						return readHelpTopic();
					} catch (Exception e) {
						errorResponse.setStatus("CHILD");
						return errorResponse;
					}
					}
				}
				
				@RequestMapping(value = "/helpTopics/helpTopicStatus/{topicId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
				public void helpTopicStatus(@PathVariable int topicId,@PathVariable String operation, HttpServletResponse response) {
					
					logger.info("In help topic status change method");
					if (operation.equalsIgnoreCase("activate"))
						helpTopicService.setHelpTopicStatus(topicId,operation, response);
					else
						helpTopicService.setHelpTopicStatus(topicId,operation, response);
				}
				
				// ****************************** Help Topic Filter method ********************************//
				
				@RequestMapping(value = "/helpTopics/helpTopicFilter/{feild}", method = RequestMethod.GET)
				public @ResponseBody Set<?> getHelpTopicContentsForFilter(@PathVariable String feild) {
					logger.info("In help topic use case filters method");
					List<String> attributeList = new ArrayList<String>();
					attributeList.add(feild);
					HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("HelpTopicEntity",attributeList, null));
					
					return set;
				}
				
				@RequestMapping(value = "/helpTopics/readUserLogins", method = RequestMethod.GET)
				public @ResponseBody List<?> readUserLoginsHelpTopics()
				{		
					 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
					 Map<String, Object> deptMap = null;
					 for (Iterator<?> iterator = helpTopicService.findUsers().iterator(); iterator.hasNext();)
						{
							final Object[] values = (Object[]) iterator.next();
							deptMap = new HashMap<String, Object>();
							String str="";
							if((String)values[6]!=null){
								str=(String)values[6];
							}							
							deptMap.put("dept_Id", (Integer)values[2]);
							deptMap.put("personId", (Integer)values[4]);
							deptMap.put("urId", (Integer)values[0]);
							deptMap.put("urLoginName", (String)values[1]);
							deptMap.put("userName", (String)values[5]+" "+str);
							deptMap.put("designation",(String)values[3]);
							deptMap.put("dept_Name",(String)values[7]);													
				 	       	result.add(deptMap);
				 	     }
				        return result;
				}
				
				@RequestMapping(value = "/helpTopics/readNotifiedUserLogins", method = RequestMethod.GET)
				public @ResponseBody List<?> readNotifiedUserLogins(){		
					
					 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
					 Map<String, Object> deptMap = null;
					 for (Iterator<?> iterator = helpTopicService.findUsers().iterator(); iterator.hasNext();)
						{
							final Object[] values = (Object[]) iterator.next();
							deptMap = new HashMap<String, Object>();
							String str="";
							if((String)values[6]!=null){
								str=(String)values[6];
							}							
							deptMap.put("urId", (Integer)values[0]);
							deptMap.put("notifiedUser",(String)values[5]+" "+str+"-"+(String)values[7]+"-"+(String)values[3]);
						
				 	       	result.add(deptMap);
				 	     }
				        return result;
				}
			
				@Scheduled(cron = "${scheduling.job.helpDeskEscalationCron}")
				public void escalationSheduler() throws Exception{
			    	
			    	logger.info("In escalation method cron sheduler code");
					for (Iterator<?> iterator = openNewTicketService.findAllTicketsBasedOnResponseTime().iterator(); iterator.hasNext();) {
						
						    final Object[] values = (Object[]) iterator.next();				
							//Hours h = Hours.hoursBetween(new DateTime((Timestamp)values[3]),new DateTime(new Timestamp(new java.util.Date().getTime())));
							Days days = Days.daysBetween(new DateTime((Timestamp)values[3]),new DateTime(new Timestamp(new java.util.Date().getTime())));
							
							if(((Integer)values[4]) == (days.getDays())){
								TicketAssignEntity ticketAssignEntity =new TicketAssignEntity();
								ticketAssignEntity.setLevelOFSLA((Integer)values[4]);
								ticketAssignEntity.setEscalated("Yes");
								ticketAssignEntity.setUrId((Integer)values[7]);
								ticketAssignEntity.setTicketId((Integer)values[0]);
								ticketAssignEntity.setAssignDate(new Timestamp(new java.util.Date().getTime()));
								ticketAssignEntity.setAssignComments("This Ticket "+(String)values[2]+" escalated to "+(String)values[10]+".");
								ticketAssignEntity.setCreatedBy("bcitsadmin");
								
								ticketAssignService.save(ticketAssignEntity);
								
								if(values[13]!=null){
									String[] notifiedUserIdArray = ((String)values[13]).split(",");
									List<String> list = Arrays.asList(notifiedUserIdArray);
									for (String str : list) {
										saveEscalatedData(Integer.parseInt(str), ticketAssignEntity);
									}
								}
								saveEscalatedData((Integer)values[7], ticketAssignEntity);
								
							} else if(((Integer)values[5]) == (days.getDays())){
								TicketAssignEntity ticketAssignEntity =new TicketAssignEntity();
								ticketAssignEntity.setLevelOFSLA((Integer)values[5]);
								ticketAssignEntity.setEscalated("Yes");
								ticketAssignEntity.setUrId((Integer)values[8]);
								ticketAssignEntity.setTicketId((Integer)values[0]);
								ticketAssignEntity.setAssignDate(new Timestamp(new java.util.Date().getTime()));
								ticketAssignEntity.setAssignComments("This Ticket "+(String)values[2]+" escalated to "+(String)values[11]+".");
								ticketAssignEntity.setCreatedBy("bcitsadmin");
								
								ticketAssignService.save(ticketAssignEntity);
								
								if(values[14]!=null){
									String[] notifiedUserIdArray = ((String)values[14]).split(",");
									List<String> list = Arrays.asList(notifiedUserIdArray);
									for (String str : list) {
										saveEscalatedData(Integer.parseInt(str), ticketAssignEntity);
									}
								}
								
								saveEscalatedData((Integer)values[8], ticketAssignEntity);
							}else if (((Integer)values[6]) == (days.getDays())) {
								TicketAssignEntity ticketAssignEntity =new TicketAssignEntity();
								ticketAssignEntity.setLevelOFSLA((Integer)values[6]);
								ticketAssignEntity.setEscalated("Yes");
								ticketAssignEntity.setUrId((Integer)values[9]);
								ticketAssignEntity.setTicketId((Integer)values[0]);
								ticketAssignEntity.setAssignDate(new Timestamp(new java.util.Date().getTime()));
								ticketAssignEntity.setAssignComments("This Ticket "+(String)values[2]+" escalated to "+(String)values[12]+".");
								ticketAssignEntity.setCreatedBy("bcitsadmin");
								
								ticketAssignService.save(ticketAssignEntity);
								
								if(values[15]!=null){
									String[] notifiedUserIdArray = ((String)values[15]).split(",");
									List<String> list = Arrays.asList(notifiedUserIdArray);
									for (String str : list) {
										saveEscalatedData(Integer.parseInt(str), ticketAssignEntity);
									}
								}
								saveEscalatedData((Integer)values[9], ticketAssignEntity);
							}
							
							/*if(((Integer)values[4])*24 == (h.getHours())+1){
								ticketAssignEntity.setLevelOFSLA((Integer)values[4]);
								ticketAssignEntity.setEscalated("Yes");
								ticketAssignEntity.setUrId((Integer)values[7]);
								ticketAssignEntity.setTicketId((Integer)values[0]);
								ticketAssignEntity.setAssignDate(new Timestamp(new java.util.Date().getTime()));
								ticketAssignEntity.setAssignComments("This Ticket "+(String)values[2]+" escalated to "+(String)values[10]+".");
								ticketAssignEntity.setCreatedBy("bcitsadmin");
								
								saveEscalatedData((Integer)values[7], ticketAssignEntity);
								
							} else if(((Integer)values[5])*24 == (h.getHours())+1){
								ticketAssignEntity.setLevelOFSLA((Integer)values[5]);
								ticketAssignEntity.setEscalated("Yes");
								ticketAssignEntity.setUrId((Integer)values[8]);
								ticketAssignEntity.setTicketId((Integer)values[0]);
								ticketAssignEntity.setAssignDate(new Timestamp(new java.util.Date().getTime()));
								ticketAssignEntity.setAssignComments("This Ticket "+(String)values[2]+" escalated to "+(String)values[11]+".");
								ticketAssignEntity.setCreatedBy("bcitsadmin");
								
								saveEscalatedData((Integer)values[8], ticketAssignEntity);
							}else if (((Integer)values[6])*24 == (h.getHours())+1) {
								ticketAssignEntity.setLevelOFSLA((Integer)values[6]);
								ticketAssignEntity.setEscalated("Yes");
								ticketAssignEntity.setUrId((Integer)values[9]);
								ticketAssignEntity.setTicketId((Integer)values[0]);
								ticketAssignEntity.setAssignDate(new Timestamp(new java.util.Date().getTime()));
								ticketAssignEntity.setAssignComments("This Ticket "+(String)values[2]+" escalated to "+(String)values[12]+".");
								ticketAssignEntity.setCreatedBy("bcitsadmin");
								
								saveEscalatedData((Integer)values[9], ticketAssignEntity);
							}*/
							
						}
				}
			    
				@SuppressWarnings("deprecation")
				@Async
			    private void saveEscalatedData(int urId, TicketAssignEntity ticketAssignEntity) throws Exception{
			    	
			    	Person p = usersService.find(urId).getPerson();
					String toAddressEmail = "";
					String toAddressMobile = "";
					for (Contact contact : p.getContacts()) {
						if(contact.getContactType().equals("Email")&& contact.getContactPrimary().equals("Yes")){
							toAddressEmail = contact.getContactContent();
						}
						if(contact.getContactType().equals("Mobile") && contact.getContactPrimary().equals("Yes")){
							toAddressMobile = contact.getContactContent();
						}
					}
					
					if(toAddressEmail == null || toAddressEmail.equals("")){
						
					}else {
						
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
						OpenNewTicketEntity ont = openNewTicketService.find(ticketAssignEntity.getTicketId());
						
						Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(ont.getPersonId());
						 
						 String personName = "";
						 personName = personName.concat((String)personNameData[0]);
						 if(personNameData[1]!= null){
						 	personName = personName.concat(" ");
							personName = personName.concat((String)personNameData[1]);
						 }
						
						String userName = "";
						userName = userName.concat(p.getFirstName());
						 if(p.getLastName() != null)
						 {
							 userName = userName.concat(" ");
							 userName = userName.concat(p.getLastName());
						 }
						 
						EmailCredentialsDetailsBean credentialsDetailsBean =  new EmailCredentialsDetailsBean();
						
						String helpTopicName = openNewTicketService.getTopicNameBasedOnTopicId(ont.getTopicId());
						String ticketNumber = ont.getTicketNumber();
						String deptName = openNewTicketService.getDepartements(ont.getDept_Id());			
						String property_No = propertyService.getProprtyNoBasedOnPropertyId(ont.getPropertyId());
						String ticketMailSubj = "Escalated Ticket : Apartment No = "+property_No;
						String messageContent = "<html>"						   
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Help Desk Services</h2> <br /> Dear Staff, <br/> <br/> "
								+"Good day. This email is to inform you that following ticket is escalated to you.<br><br>Ticket Details are,<br><br>Apartment No. : "+property_No+"<br>Owner Name : "+personName+"<br>Ticket Number : "+ticketNumber+"<br>Created Date :"+new Timestamp(new java.util.Date().getTime()).toLocaleString()+"<br>Help Topic : "+helpTopicName+"<br>Issue Subject : "+ont.getIssueSubject()+"<br>Issue Details : "+ont.getIssueDetails()+".<br>Assigned Department :"+deptName+"<br>Assigned complaint to : "+userName+"</b><br/><br>For more details, please <a href=http://"+credentialsDetailsBean.getWebsiteUrl()+"/escalatedTickets >click here</a>.<br/><br>"
								+"Thank you very much contact to our service and have a nice day.<br/><br/>"
								
								+ "<br/>Sincerely,<br/><br/>"
								+ "Manager,<br/>"
								+"HelpDesk Services,<br/>"
								+"Skyon<br/><br/> This is an auto generated Email.Please dont revert back"
								+"</body></html>";
						
						emailCredentialsDetailsBean.setToAddressEmail(toAddressEmail);
						emailCredentialsDetailsBean.setMailSubject(ticketMailSubj);
						emailCredentialsDetailsBean.setMessageContent(messageContent);
						
						new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
						
						String message = "help desk.This sms is to inform you that one ticket escalated to you, ticket details are ticket number "+ticketNumber+" created on "+ new Timestamp(new java.util.Date().getTime()).toLocaleString()+" and "+ deptName+" department."
										+"Thank you very much contact to our service";
						
						smsCredentialsDetailsBean.setNumber(toAddressMobile);
						smsCredentialsDetailsBean.setUserName(userName);
						smsCredentialsDetailsBean.setMessage(message);
						
						new Thread(new SendTicketInfoSMS(smsCredentialsDetailsBean)).start();					
					}
			    	
			    }
			    
			    @RequestMapping(value = "/openTickets/readHelpTopics", method = RequestMethod.GET)
				public @ResponseBody List<?> getHelpTopics() {
			    	logger.info("In retrive all the help topics method");
					return helpTopicService.findAll();
				}
			    
			    @RequestMapping(value = "/openTickets/getPersonListForFileter", method = RequestMethod.GET)
				public @ResponseBody List<?> readPersonNames() 
				{
			    	logger.info("In person data filter method");
					List<?> accountPersonList = openNewTicketService.findPersonForFilters();
					List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = null;
					for (Iterator<?> i = accountPersonList.iterator(); i.hasNext();)
					{
						final Object[] values = (Object[]) i.next();
						String personName = "";
						personName = personName.concat((String)values[1]);
						if(((String)values[2]) != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)values[2]);
						}

						map = new HashMap<String, Object>();
						map.put("personId", (Integer)values[0]);
						map.put("personName", personName);
						map.put("personType", (String)values[3]);
						map.put("personStyle", (String)values[4]);

						result.add(map);
					}

					return result;
				}
			    
			    @RequestMapping(value = "/common/relationshipIds/getFilterAutoCompleteValues/{className}/{relationObject}/{attribute}", method ={ RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody List<?> getFilterAutoCompleteValues(@PathVariable String className,@PathVariable String relationObject, @PathVariable String attribute) 
				{
					return (List<?>) openNewTicketService.selectDistinctQuery(className, relationObject, attribute);
				}
			    
			    @RequestMapping(value = "/common/relationshipIds/getFilterAutoCompleteValuesForUsers/{className}/{relationObject}", method ={ RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody List<?> getFilterAutoCompleteValuesForUsers(@PathVariable String className,@PathVariable String relationObject) 
				{			    	
			      return (List<?>)helpTopicService.selectDistinctQueryForUsers(className,relationObject);
				}
			    
			    @RequestMapping(value = "/openNewTickets/getHelpTopicData", method = RequestMethod.GET)
				public @ResponseBody List<?> getHelpTopicData() {
					List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
					List<?> deptList = helpTopicService.getDistinctHelpTopics();
					Map<String, Object> helpTopicMap = null;
					for (Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
					{
						final Object[] values = (Object[]) iterator.next();
						helpTopicMap = new HashMap<String, Object>();
						helpTopicMap.put("topicId", (Integer)values[0]);
						helpTopicMap.put("topicName", (String)values[1]);
						helpTopicMap.put("dept_Id", (Integer)values[2]);
			 	       	result.add(helpTopicMap);
			 	     }
			        return result;
				}
			    
			 // ****************************** Department Access Role Read,Create,Delete methods ********************************//

				@RequestMapping(value = "/HelpDeskSettings/departmentAccessSettingsRead", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody List<HelpDeskSettingsEntity> readDepartmentAccessSettings() {
					logger.info("In read department access role method");
					List<HelpDeskSettingsEntity> settingsEntities = helpDeskAccessSettingsService.findAll();
					return settingsEntities;
				}

				@RequestMapping(value = "/HelpDeskSettings/departmentAccessSettingsCreate", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object saveDepartmentAccessSettingsRead(@Valid @ModelAttribute("helpDeskSettingsEntity") HelpDeskSettingsEntity helpDeskSettingsEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

					logger.info("In save department access role method");
					JsonResponse errorResponse = new JsonResponse();
					int rlId = helpDeskSettingsEntity.getRlId();
					int count=0;
					List<Integer> dbRlIds = helpDeskAccessSettingsService.getAllRoleIds();
					for (Iterator<Integer> iterator = dbRlIds.iterator(); iterator.hasNext();) {
						int rlIds = (Integer) iterator.next();
						if( rlIds == rlId ){
							count = 1;
							break;
						}
					}
					if( count == 1 ){
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							put("ExitRole", messageSource.getMessage("HelpDeskSettings.ExitRole", null, locale));
						}
						};
						errorResponse.setStatus("ExitRole");
						errorResponse.setResult(errorMapResponse);

						return errorResponse;
					}else
						helpDeskSettingsEntity.setRlId(rlId);
				validator.validate(helpDeskSettingsEntity, bindingResult);
				if (bindingResult.hasErrors()) {
					errorResponse.setStatus("FAIL");
					errorResponse.setResult(bindingResult.getAllErrors());
					return errorResponse;
				}
				else{
					helpDeskAccessSettingsService.save(helpDeskSettingsEntity);
					return helpDeskSettingsEntity;
				    }
				}

				@RequestMapping(value = "/HelpDeskSettings/departmentAccessSettingsDestroy", method = RequestMethod.GET)
				public @ResponseBody Object deleteDepartmentAccessSettingsRead(@ModelAttribute("helpDeskSettingsEntity") HelpDeskSettingsEntity helpDeskSettingsEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
					logger.info("In delete department access role method");
					JsonResponse errorResponse = new JsonResponse();
					
					try {
						helpDeskAccessSettingsService.delete(helpDeskSettingsEntity.getSettingId());
					} catch (Exception e) {
						errorResponse.setStatus("CHILD");
						return errorResponse;
					}
					ss.setComplete();
					return helpDeskSettingsEntity;
				}			
				
				//@method fetching role details for Drop down Editors
				
				@RequestMapping(value="/HelpDeskSettings/getRolenames",method= RequestMethod.GET)
				public @ResponseBody List<?> getRole() {
					return helpDeskAccessSettingsService.getAllActiveRoles();
				}
				
				 // ****************************** CUSTOMER CARE NOTIFICATION METHODS ********************************//
				
				@RequestMapping(value="/customerCareNotification")
				public String notificationIndex(HttpServletRequest request,ModelMap model)
				{
					model.addAttribute("ViewName", "Customer Care");
					breadCrumbService.addNode("nodeID", 0, request);
					breadCrumbService.addNode("Customer Care", 1, request);
					breadCrumbService.addNode("Manage Notification", 2, request);
					return "helpDesk/customerCareNotification";
				}
				@RequestMapping(value = "/customerCareNotification/read", method = RequestMethod.POST)
				public @ResponseBody List<?> readBookedServices()
				{
					return customerCareNotificationService.getNotificationObject();

				}
				@RequestMapping(value="/customerCareNotification/add", method=RequestMethod.POST)
				public @ResponseBody Object addNotification(@RequestBody Map<String, Object> map,@ModelAttribute("customerCareNotification") CustomerCareNotification customerCareNotification , BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws Exception
				{
					JsonResponse errorResponse = new JsonResponse();
					
					customerCareNotification = customerCareNotificationService.getObject(map, "save",customerCareNotification,request);
					
					validator.validate(customerCareNotification, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("CREATEFAIL");
						errorResponse.setResult(bindingResult.getAllErrors());

						return errorResponse;
					}

					if(customerCareNotification.getBlockId().equalsIgnoreCase("All Blocks"))
					{
						if(customerCareNotification.getPropertyId().equalsIgnoreCase("All Properties Of This Block"))
						{
							List<Blocks> blocks=meetingRequestService.getAllBlock();
							for (Blocks blocks2 : blocks) {
								List<Property> properties=meetingRequestService.getPropertiesByBlockId(blocks2.getBlockId());
								int property_Count=0;
								for (Property property : properties) {
									List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
									for (OwnerProperty ownerProperty : ownerProperies) {
										int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
										List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
										for (Contact contact : contacts) {
											String mail="";
											String mobile="";
											if(contact.getContactType().equalsIgnoreCase("Email"))
											{
												mail=contact.getContactContent();
												sendMailForNotification(personId, mail,customerCareNotification);
											}
											else if(contact.getContactType().equalsIgnoreCase("Mobile"))
											{
												mobile=contact.getContactContent();
												sendSMSForNotification(personId, mobile,customerCareNotification);
											}
										}
									}
									property_Count++;
								}   System.out.println("for Block --> "+blocks2.getBlockName()+",Notification sent to <"+property_Count+">properties"); 
								
							}
						}
						
					}
					else
					{
							if(customerCareNotification.getPropertyId().equalsIgnoreCase("All Properties Of This Block"))
							{
								String block=customerCareNotification.getBlockId();
								String blocks[]=block.split(",");
								
								for(String bl:blocks)
								{
									List<Property> properties=meetingRequestService.getPropertiesByBlockId(Integer.parseInt(bl));
									
									int property_Count=0;
									for (Property property : properties) {
										List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
										for (OwnerProperty ownerProperty : ownerProperies) {
											int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
											List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
											for (Contact contact : contacts) {
												String mail="";
												String mobile="";
												if(contact.getContactType().equalsIgnoreCase("Email"))
												{
													mail=contact.getContactContent();
													sendMailForNotification(personId, mail,customerCareNotification);
												}
												else if(contact.getContactType().equalsIgnoreCase("Mobile"))
												{
													mobile=contact.getContactContent();
													sendSMSForNotification(personId, mobile,customerCareNotification);
												}
											
											}
										}property_Count++;
									} System.out.println("for Block --> "+bl+",Notification sent to <"+property_Count+">properties");
								}
								
							}
							else
							{
								System.out.println("*************************RadioButton='property'************************************");
								String propertie=customerCareNotification.getPropertyId();
								String pro[]=propertie.split(",");
								for (String property : pro) {
										List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(Integer.parseInt(property));
										for (OwnerProperty ownerProperty : ownerProperies) {
											int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
											Person person=meetingRequestService.getPersonStatus(personId);
											if(person.getPersonStatus().equalsIgnoreCase("Active"))
											{
												List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
												for (Contact contact : contacts) {
													String mail="";
													String mobile="";
													if(contact.getContactType().equalsIgnoreCase("Email"))
													{
														mail=contact.getContactContent();
														sendMailForNotification(personId, mail,customerCareNotification);
													}
													else if(contact.getContactType().equalsIgnoreCase("Mobile"))
													{
														mobile=contact.getContactContent();
														sendSMSForNotification(personId, mobile,customerCareNotification);
													}
													
													}

												}
											}
											
										}
								}
							}
					
					customerCareNotificationService.save(customerCareNotification);
					return customerCareNotification;
					
				}
				@RequestMapping(value = "/customerCareNotification/delete", method = RequestMethod.POST)
				public @ResponseBody
				Object deleteVendorServices(@RequestBody Map<String, Object> map,@ModelAttribute("customerCareNotification") CustomerCareNotification customerCareNotification, BindingResult bindingResult,
						ModelMap model,final Locale locale) {

					int cnId = (Integer)map.get("cnId");
					
						customerCareNotificationService.delete(cnId);
						return customerCareNotification;

				}
				
				@RequestMapping(value = "/customerCareNotification/update", method = RequestMethod.POST)
				public @ResponseBody Object updateNotification(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute CustomerCareNotification customerCareNotification,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					System.out.println("coming into update method+++++++++++++++++");
					//String userName = (String) session.getAttribute("userId");
					JsonResponse errorResponse = new JsonResponse();
					customerCareNotification=customerCareNotificationService.getObject(model, "save", customerCareNotification, request);
					validator.validate(customerCareNotification, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						
						customerCareNotification.setCnId(Integer.parseInt(model.get("cnId").toString()));
						customerCareNotificationService.update(customerCareNotification);
						//return readlostandfound();
						return customerCareNotification;
					}
				}
				@SuppressWarnings("serial")
				@RequestMapping(value = "/customerCareNotification/getBlockNames", method = RequestMethod.GET)
				public @ResponseBody Set<?> getBlockName()
				{
					Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();      
					for (final Blocks record : blocksService.findAll()) {
						result.add(new HashMap<String, Object>() 
								{{	    
									put("blockName", record.getBlockName());
									put("blockId", record.getBlockId());

								}});
					}
					return result;
				}
				@SuppressWarnings("serial")
				@RequestMapping(value = "/customerCareNotification/getProperties", method = RequestMethod.GET)
				public @ResponseBody Set<?> getProperties()
				{
					Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();      
					for (final Property record : propertyService.findAllNotification()) {
						result.add(new HashMap<String, Object>() 
								{{	    
									put("propertyNo", record.getProperty_No());
									put("propertyId", record.getPropertyId());

								}});
					}
					return result;
				}
/*----------------------------------------------- FAQ Section ------------------------------------------------------*/
				@RequestMapping("/faqsection")
				public String parkingSlotsIndex(ModelMap model, HttpServletRequest request) {
					HttpSession session = request.getSession(true);
					if(session.getAttribute("XLERRORData")!=null){
						model.addAttribute("XLERRORData", session.getAttribute("XLERRORData"));
					}
					model.addAttribute("ViewName", "Customer Care");
					breadCrumbService.addNode("nodeID", 0, request);
					breadCrumbService.addNode("Customer Care", 1, request);
					breadCrumbService.addNode("Manage FAQ Master", 2, request);
					session.removeAttribute("XLERRORData");
					return "helpDesk/faqsection";
				}
				
				@SuppressWarnings("serial")	
				@RequestMapping(value = "/faq/read", method = RequestMethod.POST)
				public @ResponseBody
				List<?> readFAQ() {
					List<Map<String, Object>> faqlist = new ArrayList<Map<String, Object>>();
					for (final Faq record : faqService.findAll()) {
						faqlist.add(new HashMap<String, Object>() {
							{
								put("faqId", record.getFaqId());
								put("faqtype", record.getFaqType());
								put("faqsubject", record.getFaqSubject());
								put("faqcontent", record.getFaqContent());
								put("createdby", record.getCreatedBy());
							}
						});
					}
					return faqlist;
				}
				
				@RequestMapping(value = "/faq/create", method = RequestMethod.POST)
				public @ResponseBody Object createFAQ(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute Faq faq,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					String userName = (String) session.getAttribute("userId");
					JsonResponse errorResponse = new JsonResponse();
					faq.setFaqType((String) model.get("faqtype"));
					faq.setFaqSubject((String) model.get("faqsubject"));
					faq.setFaqContent((String) model.get("faqcontent"));
					faq.setCreatedBy(userName);
					faq.setLastUpdatedBy(userName);
					faq.setLastUpdatedDate(new Timestamp(new Date().getTime()));
					
					validator.validate(faq, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						faqService.save(faq);
						return readFAQ();
					}
				}
				
				@RequestMapping(value = "/faq/update", method = RequestMethod.POST)
				public @ResponseBody Object updateFAQ(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute Faq faq,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					String userName = (String) session.getAttribute("userId");
					JsonResponse errorResponse = new JsonResponse();
					int id=Integer.parseInt(model.get("faqId").toString());
					System.out.println("=================id"+id);
					Faq old=faqService.find(Integer.parseInt(model.get("faqId").toString()));
					faq.setFaqId(old.getFaqId());
					faq.setFaqType((String) model.get("faqtype"));
					faq.setFaqSubject((String) model.get("faqsubject"));
					faq.setFaqContent((String) model.get("faqcontent"));
					faq.setCreatedBy(old.getCreatedBy());
					faq.setLastUpdatedBy(userName);
					faq.setLastUpdatedDate(new Timestamp(new Date().getTime()));
					
					validator.validate(faq, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						faqService.update(faq);
						return readFAQ();
					}
				}
				
				@RequestMapping(value = "/faq/delete", method = RequestMethod.POST)
				public @ResponseBody Object deleteFAQ(@RequestBody Map<String, Object> model) {
					faqService.delete(Integer.parseInt(model.get("faqId").toString()));
					return readFAQ();
				}
				
				@SuppressWarnings({ "deprecation" })
				@RequestMapping(value = "/faq/uploadhanbook", method={RequestMethod.POST,RequestMethod.GET})
				public void uploadhanbook(@RequestParam List<MultipartFile> file,HttpServletRequest request) throws Exception {

					HttpSession session = request.getSession(true);
					Blob blob=null;
					for(MultipartFile filess:file)
					{
					blob = Hibernate.createBlob(filess.getInputStream());
					//List<Handbook> list = HandbookService.executeSimpleQuery("select o from Handbook o");
					
						Handbook book=new Handbook();
						/*	book.setHandbookId(3);*/
						book.setHandbookType("Rules and Regulations");
						book.setHandbookContent(blob);
						
						HandbookService.save(book);
						session.setAttribute("XLERRORData", "HANDBOOK Uploaded Succesfully");
					}
				
				}
				
				@SuppressWarnings({ "deprecation" })
				@RequestMapping(value = "/faq/upload", method={RequestMethod.POST,RequestMethod.GET})
				public void uploadFaqDoc(@RequestParam MultipartFile files,HttpServletRequest request) throws Exception {

					HttpSession session = request.getSession(true);
					int faqId = Integer.parseInt(request.getParameter("faqId"));
					//MultipartFile filess=files.get(0);
					List<Faq> documentList=faqService.executeSimpleQuery("SELECT f FROM Faq f WHERE f.faqId="+faqId);
					System.out.println("===documentList=="+documentList);
					Blob blob=null;
					try
					{
					blob = Hibernate.createBlob(files.getInputStream());
					faqService.updateDocument(faqId,blob);
					session.setAttribute("XLERRORData", "Uploaded Succesfully");
					}
					catch(Exception e)
					{
						session.setAttribute("XLERRORData", "Some error occured");
					}
				
					
					
					/*	if(documentList.size()>0)
					{
						System.out.println("===inside if=="+documentList);
						FAQDocument faqDocument=new FAQDocument();
						faqDocument.setFaqId(faqId);
						faqDocument.setDocDescription(blob);
						System.out.println("===befor update =="+blob);
						faqdocumentService.update(faqDocument);
						System.out.println("===Afetr  update =="+blob);
						session.setAttribute("XLERRORData", "Uploaded Succesfully");
					}
					else
					{
						FAQDocument faqDocument=new FAQDocument();
						faqDocument.setFaqId(faqId);
						faqDocument.setDocDescription(blob);
						
					     faqdocumentService.save(faqDocument);
						session.setAttribute("XLERRORData", "Uploaded Succesfully");
					}*/
					
				
				}
				
			
				
				@RequestMapping("/faq/download/{faqId}")
				public String downloadDocument(@PathVariable Integer faqId,HttpServletResponse response) throws Exception {

					List<Faq> documentList = faqService.executeSimpleQuery("select o from Faq o where o.faqId="+faqId);
					if(!documentList.isEmpty()){
						Faq document = documentList.get(0);
					if (document.getFaqDocument() != null) {
						try {
							response.setHeader("Content-Disposition","inline;filename=\"");
							OutputStream out = response.getOutputStream();
								//response.setContentType(document.getDocumentType());
							IOUtils.copy(document.getFaqDocument().getBinaryStream(), out);
							out.flush();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return null;
					} else {
						
						OutputStream out = response.getOutputStream();
						IOUtils.write("File Not Found", out);
						out.flush();
						out.close();
						return null;
						//return "backend/filenotfound";
					}
					}
					else{
						
						OutputStream out = response.getOutputStream();
						IOUtils.write("File Not Found", out);
						out.flush();
						out.close();
						return null;
						
						//return "backend/filenotfound";
					}
				}
				
				
				//filter
				
				@RequestMapping(value = "/faq/Filter", method = {RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Set<?> filter()
				{
					Set<String> data=new HashSet<String>();
					
					for(Faq f:faqService.findAll())
					{
						data.add(f.getFaqType());
						
					}
					
					return data;
					
				
				}
				
				
				
				

				/*--------------------------LOST AND FOUND-------------------------------------*/
				
				@RequestMapping("/lostandfound")
				public String lostandfoundIndex(ModelMap model, HttpServletRequest request) {
					model.addAttribute("ViewName", "Customer Care");
					breadCrumbService.addNode("nodeID", 0, request);
					breadCrumbService.addNode("Customer Care", 1, request);
					breadCrumbService.addNode("Manage Lost & Found", 2, request);
					return "helpDesk/lostandfound";
				}
				
				@SuppressWarnings("serial")	
				@RequestMapping(value = "/lostandfound/read", method = RequestMethod.POST)
				public @ResponseBody
				List<?> readlostandfound() {
					List<Map<String, Object>> lostandfoundlist = new ArrayList<Map<String, Object>>();
					for (final Lostandfound record : lostandfoundService.findAll()) {
						lostandfoundlist.add(new HashMap<String, Object>() {
							{
								put("lostandfoundId", record.getId());
								put("lostandfoundtype", record.getType());
								put("lostandfoundsubject", record.getSubject());
								put("datelost",ConvertDate.TimeStampString(record.getDate()));
								if(record.getBlockId()!=0){
									Blocks block=blocksService.find(record.getBlockId());
									put("block", block.getBlockName());
									put("blockId", record.getBlockId());
									Property property=propertyService.find(record.getPropertyId());
									put("propertyNo", property.getProperty_No());
									put("propertyId", record.getPropertyId());
									Person person=personService.find(record.getPersonId());
									put("personName", person.getFirstName()+" "+person.getLastName());
									put("personId", record.getPersonId());
								}
								put("lostandfoundcontent", record.getDescription());
								put("createdBy", record.getCreatedBy());
								put("status", record.getStatus());
							}
						});
					}
					return lostandfoundlist;
				}
				
				@RequestMapping(value = "/lostandfound/create", method = RequestMethod.POST)
				public @ResponseBody Object createlostandfound(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute Lostandfound lostandfound,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					String userName = (String) session.getAttribute("userId");
					JsonResponse errorResponse = new JsonResponse();
					lostandfound=lostandfoundService.setParameters(model,lostandfound,userName);
					validator.validate(lostandfound, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						lostandfoundService.save(lostandfound);
						return readlostandfound();
					}
				}
				
				@RequestMapping(value = "/lostandfound/update", method = RequestMethod.POST)
				public @ResponseBody Object updatelostandfound(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute Lostandfound lostandfound,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					String userName = (String) session.getAttribute("userId");
					JsonResponse errorResponse = new JsonResponse();
					lostandfound=lostandfoundService.setParameters(model,lostandfound,userName);
					validator.validate(lostandfound, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						
						lostandfound.setId(Integer.parseInt(model.get("lostandfoundId").toString()));
						lostandfoundService.update(lostandfound);
						return readlostandfound();
					}
				}
				
				@RequestMapping(value = "/lostandfound/delete", method = RequestMethod.POST)
				public @ResponseBody Object deletelostandfound(@RequestBody Map<String, Object> model) {
					lostandfoundService.delete(Integer.parseInt(model.get("lostandfoundId").toString()));
					return readlostandfound();
				}
				
				//lost and found Filter
				@RequestMapping(value = "/lostandfound/filter1", method = {RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Set<?> filter1()
				{
					Set<String> data=new HashSet<String>();
					
					for(Lostandfound f:lostandfoundService.findAll())
					{
						data.add(f.getType());
						
					}
					
					return data;
					
				
				}
				@RequestMapping(value = "/lostandfound/filter2", method = {RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Set<?> filter2()
				{
					Set<String> data=new HashSet<String>();
					
					for(Lostandfound f:lostandfoundService.findAll())
					{
						Blocks block=blocksService.find(f.getBlockId());
						if(block!=null)
						{
						data.add(block.getBlockName());
						}
					}
					
					return data;
					
				
				}
				@RequestMapping(value = "/lostandfound/filter3", method ={RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Set<?> filter3()
				{
					Set<String> data=new HashSet<String>();
					
					for(Lostandfound f:lostandfoundService.findAll())
					{
						Property property=propertyService.find(f.getPropertyId());
						if(property!=null)
						{
						data.add(property.getProperty_No());
						}
					}
					
					return data;
					
				
				}
				@RequestMapping(value = "/lostandfound/filter4", method = {RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Set<?> filter4()
				{
					Set<String> data=new HashSet<String>();
					
					for(Lostandfound f:lostandfoundService.findAll())
					{
						Person person=personService.find(f.getPersonId());
						if(person!=null)
						{
						data.add(person.getFirstName()+" "+person.getLastName());
						}
					}
					
					return data;
					
				
				}
				@RequestMapping(value = "/lostandfound/filter5", method ={RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Set<?> filter5()
				{
					Set<String> data=new HashSet<String>();
					
					for(Lostandfound f:lostandfoundService.findAll())
					{
						
						data.add(f.getStatus());
						
					}
					
					return data;
					
				
				}
				
				
				@RequestMapping("/lostandfound/getlostandfoundimage/{lostandfoundId}")
				public String getImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int lostandfoundId)	throws LDAPException, Exception {

					response.setContentType("image/jpeg");
					Blob blobImage = lostandfoundService.getImage(lostandfoundId);
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
				
				@SuppressWarnings("deprecation")
				@RequestMapping(value = "/lostandfound/upload/lostandfoundImage", method = RequestMethod.POST)
				public @ResponseBody
				void save(@RequestParam MultipartFile files, HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
		
					int lostandfoundId = Integer.parseInt(request.getParameter("lostandfoundId"));
					Blob blob;
					blob = Hibernate.createBlob(files.getInputStream());
					lostandfoundService.uploadImageOnId(lostandfoundId, blob);			
					
				}
				
				

				@RequestMapping(value="/helpdesk/setFeedback",method={RequestMethod.POST,RequestMethod.GET})
				
				public @ResponseBody Object  postDetails(@ModelAttribute("feedbackEntity") Feedback feedbackEntity,HttpServletRequest req){
					logger.info("method:Inside new save Details methodfedback ------------------------------------ ");

					String name=req.getParameter("name");
					String ticketId=req.getParameter("ticketId");
					String feedback=req.getParameter("feedback");
					
					
					
					feedbackEntity.setName(name);
					feedbackEntity.setTicketId(Integer.parseInt(ticketId));
					feedbackEntity.setFeedback(feedback);
					feedbackEntity.setFeedbackId(0);
					//groceryEntity.setInvoiceId(0);
				
					logger.info("method:Inside new save method");
					feedbackService.save(feedbackEntity);
					return feedbackEntity;
				
				}
				
				@RequestMapping(value = "/openNewTicketTemplate/openNewTicketTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
				public String exportFileOpenTicket(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
					
					
			 
					String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			        
			       
					List<OpenNewTicketEntity> ticketEntities = openNewTicketService.findAllData();	
				
			               
			        String sheetName = "Templates";//name of sheet

			    	XSSFWorkbook wb = new XSSFWorkbook();
			    	XSSFSheet sheet = wb.createSheet(sheetName) ;
			    	
			    	XSSFRow header = sheet.createRow(0);    	
			    	
			    	XSSFCellStyle style = wb.createCellStyle();
			    	style.setWrapText(true);
			    	XSSFFont font = wb.createFont();
			    	font.setFontName(HSSFFont.FONT_ARIAL);
			    	font.setFontHeightInPoints((short)10);
			    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			    	style.setFont(font);
			    	
			    	header.createCell(0).setCellValue("Created Date");
			        header.createCell(1).setCellValue("Ticket Number");
			        header.createCell(2).setCellValue("Block Name");
			        header.createCell(3).setCellValue("Property Number");
			        header.createCell(4).setCellValue("Person Name");
			        header.createCell(5).setCellValue("Priority");
			        header.createCell(6).setCellValue("Help Topic");
			        header.createCell(7).setCellValue("Issue Subject");
			        header.createCell(8).setCellValue("Issue Deatils");
			        header.createCell(9).setCellValue("Department");
			        header.createCell(10).setCellValue("Ticket Status");
			        header.createCell(11).setCellValue("Dept Acceptance Status");
			        header.createCell(12).setCellValue("Dept Accepted Date");
			        header.createCell(13).setCellValue("Last Response Date");
			        header.createCell(14).setCellValue("Ticket Updated Date");
			        header.createCell(15).setCellValue("Closed Date");
			        header.createCell(16).setCellValue("Reopen Date");
			        header.createCell(17).setCellValue("Assign Date");
			        header.createCell(18).setCellValue("Created By");
			        
			     
			        for(int j = 0; j<=18; j++){
			    		header.getCell(j).setCellStyle(style);
			            sheet.setColumnWidth(j, 8000); 
			            sheet.setAutoFilter(CellRangeAddress.valueOf("K1:U200"));
			        }
			        
			        int count = 1;
			       
			    	 for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();) {
			    		
			    		XSSFRow row = sheet.createRow(count);
			    		final Object[] values = (Object[]) iterator.next();
			    		String str="";
			    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			    		if((Timestamp)values[9]!=null){
							str=sdf.format((Timestamp)values[9]);
						}
			            else{
			            	str="";
			            }
			    		row.createCell(0).setCellValue(str);
			    		
			    		
			    		if(values[22]!=null && ((String)values[22]).trim().equalsIgnoreCase("Common Area")){
			    			row.createCell(4).setCellValue("");					
							row.createCell(3).setCellValue("");
							row.createCell(2).setCellValue("");

						}
			    		else
						{
							Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId

									((Integer)values[23]);
							 
							 String personName = "";
							 personName = personName.concat((String)personNameData[0]);
							 if(personNameData[1]!= null){
							 	personName = personName.concat(" ");
								personName = personName.concat((String)personNameData[1]);
							 }
							 row.createCell(4).setCellValue(personName);					
								Object propertyData[] = 

								openNewTicketService.getPropertyDataBasedOnPropertyId((Integer)values[24]);
								row.createCell(3).setCellValue((String)propertyData[0]);
								row.createCell(2).setCellValue((String)propertyData[2]);
						} 
							 
			    		if((String)values[1]!=null){
							str=(String)values[1];
						}
			            else{
			            	str="";
			            }
			    		
					    row.createCell(1).setCellValue(str);
					    
			    		
			    		if((String)values[7]!=null){
							str=(String)values[7];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(5).setCellValue(str);
			    	    
			    	    if((String)values[20]!=null){
							str=(String)values[20];
						}
			            else{
			            	str="";
			            }
			    		row.createCell(6).setCellValue(str);
			    		
			    		
			    		if((String)values[5]!=null){
							str=(String)values[5];
						}
			            else{
			            	str="";
			            }
			    		
					    row.createCell(7).setCellValue(str);
					    
			    		if((String)values[6]!=null){
							str=(String)values[6];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(8).setCellValue(str);
			    	    
			    	    if((String)values[19]!=null){
							str=(String)values[19];
						}
			            else{
			            	str="";
			            }
			    		row.createCell(9).setCellValue(str);
			    		
			    		if((String)values[14]!=null){
							str=(String)values[14];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(10).setCellValue(str);
			    	    
			    	    if((String)values[15]!=null){
							str=(String)values[15];
						}
			            else{
			            	str="";
			            }
			    		row.createCell(11).setCellValue(str);
			    		
			    		if((Timestamp)values[21]!=null){
							str=sdf.format((Timestamp)values[21]);
						}
			            else{
			            	str="";
			            }


			    		row.createCell(12).setCellValue(str);
			    		
			    		if((Timestamp)values[8]!=null){
							str=sdf.format((Timestamp)values[8]);
						}
			            else{
			            	str="";
			            }


			    		row.createCell(13).setCellValue(str);
			    		
			    		if((Timestamp)values[13]!=null){
							str=sdf.format((Timestamp)values[13]);
						}
			            else{
			            	str="";
			            }


			    		row.createCell(14).setCellValue(str);
			    		
			    		if((Timestamp)values[10]!=null){
							str=sdf.format((Timestamp)values[10]);
						}
			            else{
			            	str="";
			            }

			    		row.createCell(15).setCellValue(str);
			    		
			    		if((Timestamp)values[11]!=null){
							str=sdf.format((Timestamp)values[11]);
						}
			            else{
			            	str="";
			            }


			    		row.createCell(16).setCellValue(str);
			    		
			    		if((Timestamp)values[12]!=null){
							str=sdf.format((Timestamp)values[12]);
						}
			            else{
			            	str="";
			            }


			    		row.createCell(17).setCellValue(str);
			    		
			    		if((String)values[16]!=null){
							str=(String)values[16];
						}
			            else{
			            	str="";
			            }
			    		
					    row.createCell(18).setCellValue(str);
			    		
			    		count ++;
					}
			    	
			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	wb.write(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
			        
			        ServletOutputStream servletOutputStream;

					servletOutputStream = response.getOutputStream();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "inline;filename=\"OpenNewTicketTemplates.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
					
					return null;
				}
				
				@RequestMapping(value = "/openNewTicketPdfTemplate/openNewTicketPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
				public String exportPdfOpenTicket(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
					
					
			 
					String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
			        
			       
					List<OpenNewTicketEntity> ticketEntities = openNewTicketService.findAllData();	
				
			              
			      
			        Document document = new Document();
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        PdfWriter.getInstance(document, baos);
			        document.open();
			        
			       
			        
			        PdfPTable table = new PdfPTable(19);
			        
			        Font font1 = new Font(Font.FontFamily.HELVETICA  , 7, Font.BOLD);
			        Font font3 = new Font(Font.FontFamily.HELVETICA, 7);
			       
			        table.addCell(new Paragraph("Created Date",font1));
			        table.addCell(new Paragraph("Ticket Number",font1));
			        table.addCell(new Paragraph("Block Name",font1));
			        table.addCell(new Paragraph("Property Number",font1));
			        table.addCell(new Paragraph("Person Name",font1));
			        table.addCell(new Paragraph("Priority",font1));
			        table.addCell(new Paragraph("Help Topic",font1));
			        table.addCell(new Paragraph("Issue Subject",font1));
			        table.addCell(new Paragraph("Issue Deatils",font1));
			        table.addCell(new Paragraph("Department",font1));
			        table.addCell(new Paragraph("Ticket Status",font1));
			        table.addCell(new Paragraph("Dept Acceptance Status",font1));
			        table.addCell(new Paragraph("Dept Accepted Date",font1));
			        table.addCell(new Paragraph("Last Response Date",font1));
			        table.addCell(new Paragraph("Ticket Updated Date",font1));
			        table.addCell(new Paragraph("Closed Date",font1));
			        table.addCell(new Paragraph("Reopen Date",font1));
			        table.addCell(new Paragraph("Assign Date",font1));
			        table.addCell(new Paragraph("Created By",font1));
			        
			        
			        
			      
			    	for (Iterator<?> iterator = ticketEntities.iterator(); iterator.hasNext();) {
			    		
			    		final Object[] values = (Object[]) iterator.next();
			    		String str="";
			    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			    		if((Timestamp)values[9]!=null){
							str=sdf.format((Timestamp)values[9]);
						}
			            else{
			            	str="";
			            }

			    		PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
			    		PdfPCell cell3 = new PdfPCell();
			    		PdfPCell cell4 = new PdfPCell();
			    		PdfPCell cell5 = new PdfPCell();				
			    		if(values[22]!=null && ((String)values[22]).trim().equalsIgnoreCase("Common Area")){
			    			//PdfPCell cell5 = new PdfPCell(new Paragraph("",font3));	
			    			cell5.addElement(new Paragraph("",font3));
			    			cell4.addElement(new Paragraph("",font3));
			    			cell3.addElement(new Paragraph("",font3));
			    			//PdfPCell cell4 = new PdfPCell(new Paragraph("",font3));	
			    			//PdfPCell cell3 = new PdfPCell(new Paragraph("",font3));	
							/*row.createCell(3).setCellValue("");
							row.createCell(2).setCellValue("");
*/
						}
			    		else
						{
							Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId

									((Integer)values[23]);
							 
							 String personName = "";
							 personName = personName.concat((String)personNameData[0]);
							 if(personNameData[1]!= null){
							 	personName = personName.concat(" ");
								personName = personName.concat((String)personNameData[1]);
							 }
							 cell5.addElement(new Paragraph(personName,font3));
							 //PdfPCell cell5 = new PdfPCell(new Paragraph(personName,font3));	
							 //row.createCell(4).setCellValue(personName);					
								Object propertyData[] = 

								openNewTicketService.getPropertyDataBasedOnPropertyId((Integer)values[24]);
								/*row.createCell(3).setCellValue((String)propertyData[0]);
								row.createCell(2).setCellValue((String)propertyData[2]);*/
								//PdfPCell cell4 = new PdfPCell(new Paragraph((String)propertyData[0],font3));
								//PdfPCell cell3 = new PdfPCell(new Paragraph((String)propertyData[2],font3));
								 cell4.addElement(new Paragraph((String)propertyData[0],font3));
								 cell3.addElement(new Paragraph((String)propertyData[2],font3));
						} 
						
			    		
			    		if((String)values[1]!=null){
							str=(String)values[1];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			    		
			    		/*if((String)openTicket[23]!=null){
							str=(String)openTicket[23];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
			    	    if((String)openTicket[21]!=null){
							str=(String)openTicket[21];
						}
			            else{
			            	str="";
			            }
			    	    PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			    		String personName = "";
						personName = personName.concat((String)openTicket[26]);
						if((String)openTicket[27] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)openTicket[27]);
						}
			    		
						PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
			    		*/
			    		if((String)values[7]!=null){
							str=(String)values[7];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
			    		 if((String)values[20]!=null){
								str=(String)values[20];
							}
				            else{
				            	str="";
				            }
			    	    PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			    	    if((String)values[5]!=null){
							str=(String)values[5];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			    		if((String)values[6]!=null){
							str=(String)values[6];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));
			    		if((String)values[19]!=null){
							str=(String)values[19];
						}
			            else{
			            	str="";
			            }
			    	    PdfPCell cell10 = new PdfPCell(new Paragraph(str,font3));
			    		
			    		if((String)values[14]!=null){
							str=(String)values[14];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell11 = new PdfPCell(new Paragraph(str,font3));
			    		if((String)values[15]!=null){
							str=(String)values[15];
						}
			            else{
			            	str="";
			            }
			    	    PdfPCell cell12 = new PdfPCell(new Paragraph(str,font3));
			    	    if((Timestamp)values[21]!=null){
							str=sdf.format((Timestamp)values[21]);
						}
			            else{
			            	str="";
			            }


			    		PdfPCell cell13 = new PdfPCell(new Paragraph(str,font3));
			    		if((Timestamp)values[8]!=null){
							str=sdf.format((Timestamp)values[8]);
						}
			            else{
			            	str="";
			            }


			    		PdfPCell cell14 = new PdfPCell(new Paragraph(str,font3));
			    		if((Timestamp)values[13]!=null){
							str=sdf.format((Timestamp)values[13]);
						}
			            else{
			            	str="";
			            }


			    		PdfPCell cell15 = new PdfPCell(new Paragraph(str,font3));
			    		if((Timestamp)values[10]!=null){
							str=sdf.format((Timestamp)values[10]);
						}
			            else{
			            	str="";
			            }

			    		PdfPCell cell16 = new PdfPCell(new Paragraph(str,font3));
			    		if((Timestamp)values[11]!=null){
							str=sdf.format((Timestamp)values[11]);
						}
			            else{
			            	str="";
			            }


			    		PdfPCell cell17 = new PdfPCell(new Paragraph(str,font3));
			    		if((Timestamp)values[12]!=null){
							str=sdf.format((Timestamp)values[12]);
						}
			            else{
			            	str="";
			            }

			    		PdfPCell cell18 = new PdfPCell(new Paragraph(str,font3));
			    		

			    		if((String)values[16]!=null){
							str=(String)values[16];
						}
			            else{
			            	str="";
			            }
			    		
			    		PdfPCell cell19 = new PdfPCell(new Paragraph(str,font3));
			    		
			      
			      
			       

			        table.addCell(cell1);
			        table.addCell(cell2);
			        table.addCell(cell3);
			        table.addCell(cell4);
			        table.addCell(cell5);
			        table.addCell(cell6);
			        table.addCell(cell7);
			        table.addCell(cell8);
			        table.addCell(cell9);
			        table.addCell(cell10);
			        table.addCell(cell11);
			        table.addCell(cell12);
			        table.addCell(cell13);
			        table.addCell(cell14);
			        table.addCell(cell15);
			        table.addCell(cell16);
			        table.addCell(cell17);
			        table.addCell(cell18);
			        table.addCell(cell19);
			        table.setWidthPercentage(100);
			      /*  float[] columnWidths = {8f, 10f, 8f, 10f, 10f, 10f, 14f, 10f, 8f};

			        table.setWidths(columnWidths);*/
			    		
					}
			        
			         document.add(table);
			        document.close();

			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	baos.writeTo(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
			        
			        ServletOutputStream servletOutputStream;

					servletOutputStream = response.getOutputStream();
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "inline;filename=\"OpenNewTicketTemplates.pdf"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
					
					return null;
				}
				
				
				@RequestMapping(value = "/escalatedTicketTemplate/escalatedTicketTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
				public String exportFileEscalatedTicket(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
					
					
			 
					String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			        
			       
					List<Object[]> ticketEscalatedEntities = ticketAssignService.findAll();
				
			               
			        String sheetName = "Templates";//name of sheet

			    	XSSFWorkbook wb = new XSSFWorkbook();
			    	XSSFSheet sheet = wb.createSheet(sheetName) ;
			    	
			    	XSSFRow header = sheet.createRow(0);    	
			    	
			    	XSSFCellStyle style = wb.createCellStyle();
			    	style.setWrapText(true);
			    	XSSFFont font = wb.createFont();
			    	font.setFontName(HSSFFont.FONT_ARIAL);
			    	font.setFontHeightInPoints((short)10);
			    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			    	style.setFont(font);
			    	
			    	header.createCell(0).setCellValue("Escalated Date");
			        header.createCell(1).setCellValue("Escalated To");
			        header.createCell(2).setCellValue("Escalated Comments");
			        header.createCell(3).setCellValue("SLA Level (in Days)");
			        header.createCell(4).setCellValue("Ticket Number");
			        header.createCell(5).setCellValue("Person Name");
			        header.createCell(6).setCellValue("Priority");
			        header.createCell(7).setCellValue("Issue Subject");
			        header.createCell(8).setCellValue("Issue Deatils");
			        header.createCell(9).setCellValue("Ticket Status");
			        header.createCell(10).setCellValue("Ticket Created Date");
			        
			        for(int j = 0; j<=10; j++){
			    		header.getCell(j).setCellStyle(style);
			            sheet.setColumnWidth(j, 8000); 
			            sheet.setAutoFilter(CellRangeAddress.valueOf("K1:K200"));
			        }
			        
			        int count = 1;
			       
			    	for (Object[] escalatedTicket :ticketEscalatedEntities ) {
			    		
			    		XSSFRow row = sheet.createRow(count);

			    		String str="";
			    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			    		if((Timestamp)escalatedTicket[4]!=null){
							str=sdf.format((Timestamp)escalatedTicket[4]);
						}
			            else{
			            	str="";
			            }

                        row.createCell(0).setCellValue(str);
                        String userName = "";
            			userName = userName.concat((String)escalatedTicket[8]);
            			if((String)escalatedTicket[9] != null)
            			{
            				userName = userName.concat(" ");
            				userName = userName.concat((String)escalatedTicket[9]);
            			}	
			    		
					    row.createCell(1).setCellValue(userName);
			    		if((String)escalatedTicket[3]!=null){
							str=(String)escalatedTicket[3];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(2).setCellValue(str);
			    	    if((Integer)escalatedTicket[19]!=null){
							str=Integer.toString((Integer)escalatedTicket[19]);
						}
			            else{
			            	str="";
			            }
			    		row.createCell(3).setCellValue(str);
			    		if((String)escalatedTicket[11]!=null){
							str=(String)escalatedTicket[11];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(4).setCellValue(str);
			    		String personName = "";
						personName = personName.concat((String)escalatedTicket[12]);
						if((String)escalatedTicket[13] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)escalatedTicket[13]);
						}
			    		
			    		row.createCell(5).setCellValue(personName);
			    		
			    		if((String)escalatedTicket[14]!=null){
							str=(String)escalatedTicket[14];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(6).setCellValue(str);
			    	    if((String)escalatedTicket[15]!=null){
							str=(String)escalatedTicket[15];
						}
			            else{
			            	str="";
			            }
			    		row.createCell(7).setCellValue(str);
			    		if((String)escalatedTicket[16]!=null){
							str=(String)escalatedTicket[16];
						}
			            else{
			            	str="";
			            }
			    		
					    row.createCell(8).setCellValue(str);
			    		if((String)escalatedTicket[17]!=null){
							str=(String)escalatedTicket[17];
						}
			            else{
			            	str="";
			            }
			    	    row.createCell(9).setCellValue(str);
			    	    
			    		if((Timestamp)escalatedTicket[18]!=null){
							str=sdf.format((Timestamp)escalatedTicket[18]);
						}
			            else{
			            	str="";
			            }


			    		row.createCell(10).setCellValue(str);
			    		
			    		count ++;
					}
			    	
			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	wb.write(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
			        
			        ServletOutputStream servletOutputStream;

					servletOutputStream = response.getOutputStream();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "inline;filename=\"EscalatedTicketTemplates.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
					
					return null;
				}
				
				@RequestMapping(value = "/escalatedTicketPdfTemplate/escalatedTicketPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
				public String exportPdfEscalatedTicket(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
					
					
			 
					String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
			        
			       
					List<Object[]> ticketEscalatedEntities = ticketAssignService.findAll();
				
			              
			      
			        Document document = new Document();
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        PdfWriter.getInstance(document, baos);
			        document.open();
			        
			       
			        
			        PdfPTable table = new PdfPTable(11);
			        
			        Font font1 = new Font(Font.FontFamily.HELVETICA  , 7, Font.BOLD);
			        Font font3 = new Font(Font.FontFamily.HELVETICA, 7);
			        

			    	
			       
			        table.addCell(new Paragraph("Escalated Date",font1));
			        table.addCell(new Paragraph("Escalated To",font1));
			        table.addCell(new Paragraph("Escalated Comments",font1));
			        table.addCell(new Paragraph("SLA Level (in Days)",font1));
			        table.addCell(new Paragraph("Ticket Number",font1));
			        table.addCell(new Paragraph("Person Name",font1));
			        table.addCell(new Paragraph("Priority",font1));
			        table.addCell(new Paragraph("Issue Subject",font1));
			        table.addCell(new Paragraph("Issue Deatils",font1));
			        table.addCell(new Paragraph("Ticket Status",font1));
			        table.addCell(new Paragraph("Ticket Created Date",font1));
			        
			    	for (Object[] escalatedTicket :ticketEscalatedEntities) {
			    		
			    		String str="";
			    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			    		if((Timestamp)escalatedTicket[4]!=null){
							str=sdf.format((Timestamp)escalatedTicket[4]);
						}
			            else{
			            	str="";
			            }

			    		PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
                        String userName = "";
            			userName = userName.concat((String)escalatedTicket[8]);
            			if((String)escalatedTicket[9] != null)
            			{
            				userName = userName.concat(" ");
            				userName = userName.concat((String)escalatedTicket[9]);
            			}	
			    		
            			PdfPCell cell2 = new PdfPCell(new Paragraph(userName,font3));
			    		if((String)escalatedTicket[3]!=null){
							str=(String)escalatedTicket[3];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
			    	    if((Integer)escalatedTicket[19]!=null){
							str=Integer.toString((Integer)escalatedTicket[19]);
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			    		if((String)escalatedTicket[11]!=null){
							str=(String)escalatedTicket[11];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
			    		String personName = "";
						personName = personName.concat((String)escalatedTicket[12]);
						if((String)escalatedTicket[13] != null)
						{
							personName = personName.concat(" ");
							personName = personName.concat((String)escalatedTicket[13]);
						}
			    		
						PdfPCell cell6 = new PdfPCell(new Paragraph(personName,font3));
			    		
			    		if((String)escalatedTicket[14]!=null){
							str=(String)escalatedTicket[14];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			    	    if((String)escalatedTicket[15]!=null){
							str=(String)escalatedTicket[15];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			    		if((String)escalatedTicket[16]!=null){
							str=(String)escalatedTicket[16];
						}
			            else{
			            	str="";
			            }
			    		
			    		PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));
			    		if((String)escalatedTicket[17]!=null){
							str=(String)escalatedTicket[17];
						}
			            else{
			            	str="";
			            }
			    		PdfPCell cell10 = new PdfPCell(new Paragraph(str,font3));
			    	    
			    		if((Timestamp)escalatedTicket[18]!=null){
							str=sdf.format((Timestamp)escalatedTicket[18]);
						}
			            else{
			            	str="";
			            }
			    		
			    		PdfPCell cell11 = new PdfPCell(new Paragraph(str,font3));
			    		
			      
			       

			        table.addCell(cell1);
			        table.addCell(cell2);
			        table.addCell(cell3);
			        table.addCell(cell4);
			        table.addCell(cell5);
			        table.addCell(cell6);
			        table.addCell(cell7);
			        table.addCell(cell8);
			        table.addCell(cell9);
			        table.addCell(cell10);
			        table.addCell(cell11);
			        
			        table.setWidthPercentage(100);
			      /*  float[] columnWidths = {8f, 10f, 8f, 10f, 10f, 10f, 14f, 10f, 8f};

			        table.setWidths(columnWidths);*/
			    		
					}
			        
			         document.add(table);
			        document.close();

			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	baos.writeTo(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
			        
			        ServletOutputStream servletOutputStream;

					servletOutputStream = response.getOutputStream();
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "inline;filename=\"EscalatedTicketTemplates.pdf"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
					
					return null;
				}
				
/*---------------------------About Us-----------------*/
				
				@RequestMapping(value="/aboutUs")
				private String indexAboutUs(HttpServletRequest request, Model model)
				{
					logger.info("In inbox about Details method");
					model.addAttribute("ViewName", "About Us");
					return "helpDesk/aboutus";
				}
				
				@RequestMapping(value="/helpDesk/aboutus",method={RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody List<?> aboutUS()
				{
				
					logger.info("In read about Details method");
					List<?> aboutusdata=aboutUsService.getAll();
					List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
					 Map<String, Object> aboutMap = null;
					 for (Iterator<?> iterator = aboutusdata.iterator(); iterator.hasNext();)
						{
							Object[] aboutus = (Object[]) iterator.next();
							aboutMap = new HashMap<String, Object>();
							/*aboutMap.put("image", aboutUsService.getImage((Integer)aboutus[0]));*/
							aboutMap.put("about_id",(Integer)aboutus[0]);
							aboutMap.put("about_name",(String)aboutus[1]);
							aboutMap.put("about_designation",(String)aboutus[2]);
							aboutMap.put("about_description",(String)aboutus[3] );
							result.add(aboutMap);
						}
					 
					return result;
				}
				
				@RequestMapping(value = "/helpDesk/AboutCreateUrl", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object saveContactDetails(@ModelAttribute("aboutUs") AboutUs aboutus,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws LDAPException,Exception {

					logger.info(" In save aboutus method ");
					aboutUsService.save(aboutus);
					
					 return aboutus;	
				}
				
				@RequestMapping(value = "/helpDesk/AboutDestroyUrl", method = RequestMethod.GET)
				public @ResponseBody Object deleteAboutDetails(@ModelAttribute("aboutUs") AboutUs aboutus,BindingResult bindingResult, ModelMap model, SessionStatus ss) {

					logger.info("In delete aboutus details method");
					JsonResponse errorResponse = new JsonResponse();		
					try {
						aboutUsService.delete(aboutus.getAbout_id());
						ss.setComplete();
						return aboutUS();
					} catch (Exception e) {
						
						return errorResponse;
					}
				}
				
				@RequestMapping(value = "/helpDesk/AboutUpdateUrl", method = {RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody Object editAboutDetails(@ModelAttribute("aboutUs") AboutUs aboutus,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception {

					logger.info(" In edit about us method ");
					aboutUsService.update(aboutus);		
					 return aboutus;	
				}
				@SuppressWarnings("deprecation")
				@RequestMapping(value = "/helpDesk/aboutImage", method = RequestMethod.POST)
				public @ResponseBody
				void saveImageForAboutUs(@RequestParam MultipartFile files, HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {

					int personId = Integer.parseInt(request.getParameter("about_id"));
					Blob blob;
					blob = Hibernate.createBlob(files.getInputStream());
					aboutUsService.uploadImageOnId(personId, blob);
				}
				@RequestMapping("/about/getpersonimage/{personId}")
				public String getAboutUsImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int personId)	throws LDAPException, Exception {

					logger.info("Image..............................");
					System.out.println("ID++++++++++++++++++++"+personId);
					response.setContentType("image/jpeg");
					Blob blobImage = aboutUsService.getImage(personId);
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("about_id", personId);
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
					}
					return null;
				}
				
				@SuppressWarnings("serial")
				@RequestMapping(value="/helpDesk/feedbackData",method={RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody List<?> readAllFeedbackData()
				{
					System.out.println("feedback data");
					List<Map<String, Object>> allComData=new ArrayList<>(); 
					List<Integer> allMasterId=feedback_MasterService.getAllMasterIdList();
						for (final Integer masterId :allMasterId ) {
							allComData.add(new HashMap<String,Object>(){
								{
									FeedbackMaster master=feedback_MasterService.find(masterId);
									
									put("contactedbymgtt",master.getLike_Contacted());
									put("additionalComments",master.getAdditional_Comments());
									List<FeedbackChild> childs=feedback_ChildService.getChildByMasterId(master.getfMaster_d());
								
									for (FeedbackChild child : childs) {
										if(child.getValue().equalsIgnoreCase("Professionalism"))
										{
										put("professionalism",child.getRate());
										}
										if(child.getValue().equalsIgnoreCase("Timeliness"))
										{
										put("timeliness",child.getRate());
										}
										if(child.getValue().equalsIgnoreCase("Completion Speed"))
										{
										put("completionSpeed",child.getRate());
										}
										if(child.getValue().equalsIgnoreCase("Cleanliness"))
										{
										put("cleanliness",child.getRate());
										}
										if(child.getValue().equalsIgnoreCase("Communication"))
										{
										put("communication",child.getRate());
										}
										if(child.getValue().equalsIgnoreCase("Quality"))
										{
										put("quality",child.getRate());
										}
									}

									Person person=personService.find(master.getPersonId());
									put("customerName",person.getFirstName()+" "+person.getLastName());
									OpenNewTicketEntity ticket=openNewTicketService.find(master.getTicketId());
									put("ticketNo",ticket.getTicketNumber());
									put("ticketDescription",ticket.getIssueDetails());
									Property property=propertyService.find(ticket.getPropertyId());
									put("apartmentNo",property.getProperty_No());
									
								}
							
							});
						}
						return allComData;
				}
				
				@SuppressWarnings({ "unchecked", "unused" })
				@RequestMapping(value = "/helpDesk/feedbackDataExport", method = {RequestMethod.POST,RequestMethod.GET})
				public Object getFeedbackExport(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException
				{	
					ServletOutputStream servletOutputStream=null;
					logger.info("*********feedback Report three**********");
					String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Feedback Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
					
			        String sheetName = "Templates";//name of sheet

			    	XSSFWorkbook wb = new XSSFWorkbook();
			    	XSSFSheet sheet = wb.createSheet(sheetName) ;
			    	
			    	XSSFRow header = sheet.createRow(0);    	
			    	
			    	XSSFCellStyle style = wb.createCellStyle();
			    	style.setWrapText(true);
			    	XSSFFont font = wb.createFont();
			    	font.setFontName(HSSFFont.FONT_ARIAL);
			    	font.setFontHeightInPoints((short)10);
			    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			    	style.setFont(font);
			    	header.createCell(0).setCellValue("Apartment No");
			        header.createCell(1).setCellValue("Customer Name");
			        header.createCell(2).setCellValue("Ticket No");
			        header.createCell(3).setCellValue("Ticket Description");
			        header.createCell(4).setCellValue("Professionalism"); 
			        header.createCell(5).setCellValue("Timeliness"); 
			        header.createCell(6).setCellValue("Completion Speed");
			        header.createCell(7).setCellValue("Cleanliness");
			        header.createCell(8).setCellValue("Communication");				        
			        header.createCell(9).setCellValue("Quality");	
			        header.createCell(10).setCellValue("Additional Comments");	
			        header.createCell(11).setCellValue("Contacted by mgt.");
			        for(int j = 0; j<=11; j++){
			    		header.getCell(j).setCellStyle(style);
			            sheet.setColumnWidth(j, 8000); 
			            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			        }
			        List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findAllTicketReports();
			       int count = 1 ;
			        List<Map<String, Object>> allComData=new ArrayList<>(); 
					List<Integer> allMasterId=feedback_MasterService.getAllMasterIdList();
					
					
			        for (Integer masterId :allMasterId ) {
								FeedbackMaster master=feedback_MasterService.find(masterId);
								OpenNewTicketEntity ticket=openNewTicketService.find(master.getTicketId());
								Property property=propertyService.find(ticket.getPropertyId());
								Person person=personService.find(master.getPersonId());
								XSSFRow row = sheet.createRow(count);
								row.createCell(0).setCellValue((String)property.getProperty_No());
								row.createCell(1).setCellValue((String)person.getFirstName()+" "+(String)person.getLastName());
								row.createCell(2).setCellValue((String)ticket.getTicketNumber());
								row.createCell(3).setCellValue((String)ticket.getIssueDetails());
								List<FeedbackChild> childs=feedback_ChildService.getChildByMasterId(master.getfMaster_d());
								
								for (FeedbackChild child : childs) {
									
									if(child.getValue().equalsIgnoreCase("Professionalism"))
									{
										System.out.println("child.getRate()"+child.getRate());
										row.createCell(4).setCellValue((Integer)child.getRate());
									}
									if(child.getValue().equalsIgnoreCase("Timeliness"))
									{
										row.createCell(5).setCellValue((Integer)child.getRate());
									}
									if(child.getValue().equalsIgnoreCase("Completion Speed"))
									{
										row.createCell(6).setCellValue((Integer)child.getRate());
									}
									if(child.getValue().equalsIgnoreCase("Cleanliness"))
									{
										row.createCell(7).setCellValue((Integer)child.getRate());
									}
									if(child.getValue().equalsIgnoreCase("Communication"))
									{
										row.createCell(8).setCellValue((Integer)child.getRate());
									}
									if(child.getValue().equalsIgnoreCase("Quality"))
									{
										row.createCell(9).setCellValue((Integer)child.getRate());
									}
								}
								row.createCell(10).setCellValue((String)master.getAdditional_Comments());
								row.createCell(11).setCellValue((String)master.getLike_Contacted());
					count++;
			        }
			
			    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
			    	wb.write(fileOut);
			    	fileOut.flush();
			    	fileOut.close();
					servletOutputStream = response.getOutputStream();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "inline;filename=\"Feedback_Report.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					//servletOutputStream.w
					servletOutputStream.flush();
					servletOutputStream.close();
					return null;
					
				}
				
				public String genrateTicketNumber() throws Exception {  
					/*Random generator = new Random();  
					generator.setSeed(System.currentTimeMillis());  
					   
					int num = generator.nextInt(99999) + 99999;  
					if (num < 100000 || num > 999999) {  
					num = generator.nextInt(99999) + 99999;  
					if (num < 100000 || num > 999999) {  
					throw new Exception("Unable to generate PIN at this time..");  
					}  
					} */ 
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					String year = sdf.format(new Date());
					int nextSeqVal = openNewTicketService.autoGeneratedTicketNumber();	 
					
					return "#"+year+nextSeqVal;  
				}		
				
				@RequestMapping(value = "/helpDesk/getOpenTicketSearchByMonth", method = {RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Object getOpenTicketSearchByMonth(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException
				{
					String fromDate="";
					String toDate="";
					if(request.getParameter("fromDate")!=null)
					{
						fromDate=request.getParameter("fromDate");
					}
					if(request.getParameter("toDate")!=null)
					{
						toDate=request.getParameter("toDate");
					}
					List<OpenNewTicketEntity> openTickets= new ArrayList<>();
					try
					{
						Date fromDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
						Date toDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
						openTickets=openNewTicketService.getOpenNewTicketSearchByMonth(fromDateVal, toDateVal);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return openTickets;
					
					
				}
				@RequestMapping(value = "/helpDesk/getDeptTransferSearchByMonth", method = {RequestMethod.POST,RequestMethod.GET})
				public @ResponseBody Object getDeptTransferSearchByMonth(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException
				{
					String fromDate="";
					String toDate="";
					if(request.getParameter("fromDate")!=null)
					{
						fromDate=request.getParameter("fromDate");
					}
					if(request.getParameter("toDate")!=null)
					{
						toDate=request.getParameter("toDate");
					}
					List<TicketDeptTransferEntity> deptTransfer= new ArrayList<>();
					try
					{
						Date fromDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
						Date toDateVal = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
						deptTransfer=ticketDeptTransferService.getDeptTransferSearchByMonth(fromDateVal, toDateVal);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return deptTransfer;
					
					
				}
				
				@RequestMapping(value = "/helpDesk/getPostInternalNote", method = RequestMethod.GET)
				public @ResponseBody List<Map<String, String>> getPostInternalNote(final Locale locale) 
				{
					String[] noteType = ResourceBundle.getBundle("application").getString("internalNoteTitle").split(",");
					List<Map<String, String>> result = new ArrayList<Map<String, String>>();

					Map<String, String> map = null;

					for (int i = 0; i < noteType.length; i++)
					{
						map = new HashMap<String, String>();
						map.put("value", noteType[i]);
						map.put("name", noteType[i]);
						result.add(map);
					}
					return result;
				}
				
				@RequestMapping(value="/meetingRequest")
				public String meetingRequestIndex(HttpServletRequest request,ModelMap model)
				{
					model.addAttribute("ViewName", "Meeting Request");
					breadCrumbService.addNode("nodeID", 0, request);
					breadCrumbService.addNode("Customer Care", 1, request);
					breadCrumbService.addNode("Meeting Request", 2, request);
					return "helpDesk/meetingRequest";
				}
				@RequestMapping(value="/meetingRequest/add", method=RequestMethod.POST)
				public @ResponseBody Object addMeetingRequest(@RequestBody Map<String, Object> map,@ModelAttribute("meetingRequest") MeetingRequest meetingRequest , BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws Exception
				{
					JsonResponse errorResponse = new JsonResponse();
					meetingRequest = meetingRequestService.getObject(map,"save",meetingRequest,request);
					validator.validate(meetingRequest, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("CREATEFAIL");
						errorResponse.setResult(bindingResult.getAllErrors());

						return errorResponse;
					}
					if(meetingRequest.getBlockId().equalsIgnoreCase("All Blocks"))
					{
						if(meetingRequest.getPropertyId().equalsIgnoreCase("All Properties Of This Block"))
						{
							List<Blocks> blocks=meetingRequestService.getAllBlock();
							for (Blocks blocks2 : blocks) {
								List<Property> properties=meetingRequestService.getPropertiesByBlockId(blocks2.getBlockId());
								for (Property property : properties) {
									List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
									for (OwnerProperty ownerProperty : ownerProperies) {
										int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
										List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
										for (Contact contact : contacts) {
											String mail="";
											String mobile="";
											if(contact.getContactType().equalsIgnoreCase("Email"))
											{
												mail=contact.getContactContent();
												sendMail(personId, mail,meetingRequest);
											}
											else if(contact.getContactType().equalsIgnoreCase("Mobile"))
											{
												mobile=contact.getContactContent();
												sendSMS(personId, mobile,meetingRequest);
											}
										}
									}
								}
								
							}
						}
						
					}
					else
					{
						List<Property> properties=meetingRequestService.getPropertiesByBlockId(Integer.parseInt(meetingRequest.getBlockId()));
					
							if(meetingRequest.getPropertyId().equalsIgnoreCase("All Properties Of This Block"))
							{
								for (Property property : properties) {
									List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
									for (OwnerProperty ownerProperty : ownerProperies) {
										int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
										List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
										for (Contact contact : contacts) {
											String mail="";
											String mobile="";
											if(contact.getContactType().equalsIgnoreCase("Email"))
											{
												mail=contact.getContactContent();
												sendMail(personId, mail,meetingRequest);
											}
											else if(contact.getContactType().equalsIgnoreCase("Mobile"))
											{
												mobile=contact.getContactContent();
												sendSMS(personId, mobile,meetingRequest);
											}
										
										}
									}
								}
							}
							else
							{
								for (Property property : properties) {
									int propertyId=Integer.parseInt(meetingRequest.getPropertyId());
									if(propertyId==property.getPropertyId())
									{
										List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
										for (OwnerProperty ownerProperty : ownerProperies) {
											int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
											Person person=meetingRequestService.getPersonStatus(personId);
											if(person.getPersonStatus().equalsIgnoreCase("Active"))
											{
												List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
												for (Contact contact : contacts) {
													String mail="";
													String mobile="";
													if(contact.getContactType().equalsIgnoreCase("Email"))
													{
														mail=contact.getContactContent();
														sendMail(personId, mail,meetingRequest);
													}
													else if(contact.getContactType().equalsIgnoreCase("Mobile"))
													{
														mobile=contact.getContactContent();
														sendSMS(personId, mobile,meetingRequest);
													}
													
													}

												}
											}
											
										}
									}
								}
							}
					
					meetingRequestService.save(meetingRequest);
					return meetingRequest;
					
				}
				@RequestMapping(value = "/meetingRequest/delete", method = RequestMethod.POST)
				public @ResponseBody
				Object deleteMeetingRequest(@RequestBody Map<String, Object> map,@ModelAttribute("meetingRequest") MeetingRequest meetingRequest, BindingResult bindingResult,
						ModelMap model,final Locale locale) {
						int meeting_id = (Integer)map.get("meeting_id");
						meetingRequestService.delete(meeting_id);
						return meetingRequest;
				}
				@RequestMapping(value = "/meetingRequest/update", method = RequestMethod.POST)
				public @ResponseBody Object updatemeetingRequest(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute MeetingRequest meetingRequest,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					JsonResponse errorResponse = new JsonResponse();
					meetingRequest=meetingRequestService.getObject(model,"save",meetingRequest,request);
					validator.validate(meetingRequest, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						meetingRequest.setMeeting_id(Integer.parseInt(model.get("meeting_id").toString()));
						meetingRequestService.update(meetingRequest);
						return meetingRequest;
					}
				}
				public void sendSMS(int personId,String mobile,MeetingRequest meetingRequest)
				{
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
					String personName = "";		
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
					 Date date = new Date(meetingRequest.getStartTime().getTime());
					if(personNameData!=null){
						personName = (String)personNameData[0];
					}
					if(mobile!="" && mobile!=null)
					{
					String messagePerson ="Dear Resident , A meeting is scheduled on dated "+dateFormat.format(meetingRequest.getStartTime())+" at "+timeFormat.format(date)+" time to discuss the "+meetingRequest.getMeetingSubject()+" subject matter at "+meetingRequest.getLocation()+" venue.<br/> You are requested to be present as per time given.";
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
					smsCredentialsDetailsBean.setNumber(mobile);
					smsCredentialsDetailsBean.setUserName(personName);
					smsCredentialsDetailsBean.setMessage(messagePerson);
					new Thread(new SendTicketSMS(smsCredentialsDetailsBean)).start();
					}
				}
				public void sendMail(int personId,String mailAddress,MeetingRequest meetingRequest) throws Exception
				{
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
					String personName = "";		
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
					 Date date = new Date(meetingRequest.getStartTime().getTime());
					if(personNameData!=null){
						personName = (String)personNameData[0];
					}
					if(mailAddress!="" && mailAddress!=null)
					{
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
								+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Meeting Request</h2> <br/> Dear "+personName+", <br/> <br/> "
								+ "Date :- <b>" + dateFormat.format(meetingRequest.getStartTime())+"</b><br/>"
								+ "Time :- <b>" +timeFormat.format(date)+"</b><br/>"
								+ "Subject :- <b>" +meetingRequest.getMeetingSubject()+"</b><br/>"
								+ "Venue :- <b>" +meetingRequest.getLocation()+"</b><br/>"
								+ "Dear Resident / RWA Member<br/>"
								+ "A meeting is scheduled to discuss the subject matter.<br/>"
								+ "You are requested to be present as per time given.<br/>"
								
								
								+ "<br/>From<br/>"
								+ "RWA<br/>"
								+"SCOWARWA<br/>"
								+"<br/> This is an auto generated Email.Please dont revert back"
								+"</html>";
						String mailSubject = "Meeting Request for "+meetingRequest.getMeetingSubject();
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						emailCredentialsDetailsBean.setToAddressEmail(mailAddress);
						emailCredentialsDetailsBean.setMailSubject(mailSubject);
						emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
						new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
					}
				}
				@RequestMapping(value = "/meetingRequest/read", method = RequestMethod.POST)
				public @ResponseBody List<?> readMeetingServices()
				{
					return meetingRequestService.getMeetingObject();
				}
				
				
				@Scheduled(cron = "0 0 09 * * *")
				public void meetingSheduler() throws Exception{
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					 String currentDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					 String incDate=change(currentDate,+1);
					 String decrDate=change(currentDate,-1);
					 System.out.println("coming to scheduler "+currentDate);
					 List<MeetingRequest> meetingRequests=meetingRequestService.getAllRequestByCurrentDate(currentDate,incDate);
				 for (MeetingRequest meetingRequest : meetingRequests) {
					 if(meetingRequest.getBlockId().equalsIgnoreCase("All Blocks"))
						{
							if(meetingRequest.getPropertyId().equalsIgnoreCase("All Properties Of This Block"))
							{
								List<Blocks> blocks=meetingRequestService.getAllBlock();
								for (Blocks blocks2 : blocks) {
									List<Property> properties=meetingRequestService.getPropertiesByBlockId(blocks2.getBlockId());
									for (Property property : properties) {
										List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
										for (OwnerProperty ownerProperty : ownerProperies) {
											int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
											List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
											for (Contact contact : contacts) {
												String mail="";
												String mobile="";
												if(contact.getContactType().equalsIgnoreCase("Email"))
												{
													mail=contact.getContactContent();
												}
												else if(contact.getContactType().equalsIgnoreCase("Mobile"))
												{
													mobile=contact.getContactContent();
													sendSMSForScheduler(personId, mobile,meetingRequest);
												}
											}
										}
									}
									
								}
							}
							
						}
						else
						{
							List<Property> properties=meetingRequestService.getPropertiesByBlockId(Integer.parseInt(meetingRequest.getBlockId()));
								if(meetingRequest.getPropertyId().equalsIgnoreCase("All Properties Of This Block")){
									for (Property property : properties) {
										List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
										for (OwnerProperty ownerProperty : ownerProperies) {
											int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
											List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
											for (Contact contact : contacts) {
												String mail="";
												String mobile="";
												if(contact.getContactType().equalsIgnoreCase("Email"))
												{
													mail=contact.getContactContent();
												}
												else if(contact.getContactType().equalsIgnoreCase("Mobile"))
												{
													mobile=contact.getContactContent();
													sendSMSForScheduler(personId, mobile,meetingRequest);
												}
											}
										}
									}
								}
								else
								{
									for (Property property : properties) {
										int propertyId=Integer.parseInt(meetingRequest.getPropertyId());
										if(propertyId==property.getPropertyId())
										{
											List<OwnerProperty> ownerProperies=meetingRequestService.getOwnerPropertyBasedOnPropertyId(property.getPropertyId());
											for (OwnerProperty ownerProperty : ownerProperies) {
												int personId=meetingRequestService.getPersonIdByOwnerId(ownerProperty.getOwnerId());
												Person person=meetingRequestService.getPersonStatus(personId);
												if(person.getPersonStatus().equalsIgnoreCase("Active"))
												{
													List<Contact> contacts=meetingRequestService.getContactsByPersonId(personId);
													for (Contact contact : contacts) {
														String mail="";
														String mobile="";
														if(contact.getContactType().equalsIgnoreCase("Email"))
														{
															mail=contact.getContactContent();
														}
														else if(contact.getContactType().equalsIgnoreCase("Mobile"))
														{
															mobile=contact.getContactContent();
															sendSMSForScheduler(personId, mobile,meetingRequest);
														}
														}

													}
												}
												
											}
										}
									}
								}
					
				}
			 
			 }
				public static String change(String date, int days) throws Exception {
					System.out.println("date coming "+date);
				    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				    Date d = df.parse(date);
				    Calendar c = Calendar.getInstance(); 
				    c.setTime(d); 
				    c.add(Calendar.DATE, days);
				    d = c.getTime();
				    return df.format(d);
				}
				
				public void sendSMSForScheduler(int personId,String mobile,MeetingRequest meetingRequest)
				{
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
					String personName = "";		
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
					 Date date = new Date(meetingRequest.getStartTime().getTime());
					if(personNameData!=null){
						personName = (String)personNameData[0];
					}
					if(mobile!="" && mobile!=null)
					{
					String messagePerson ="Dear Resident / RWA Member, This is reminder SMS that meeting is scheduled on dated "+dateFormat.format(meetingRequest.getStartTime())+" at "+timeFormat.format(date)+" time to discuss the "+meetingRequest.getMeetingSubject()+" subject matter at "+meetingRequest.getLocation()+" venue.<br/> You are requested to be present as per time given.";
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
					smsCredentialsDetailsBean.setNumber(mobile);
					smsCredentialsDetailsBean.setUserName(personName);
					smsCredentialsDetailsBean.setMessage(messagePerson);
					new Thread(new SendTicketSMS(smsCredentialsDetailsBean)).start();
					}
				}
				
				@RequestMapping(value="/mailConfig")
				public String mailConfigIndex(HttpServletRequest request,ModelMap model)
				{
					model.addAttribute("ViewName", "Mail Master");
					breadCrumbService.addNode("nodeID", 0, request);
					breadCrumbService.addNode("Master", 1, request);
					breadCrumbService.addNode("Mail Master", 2, request);
					return "helpDesk/mailConfig";
				}
				
				
				
				@RequestMapping(value = "/mailConfig/add", method = RequestMethod.POST)
				public @ResponseBody Object mailConfigAdd(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute MailMaster mailMaster,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					String userName = (String) session.getAttribute("userId");
					JsonResponse errorResponse = new JsonResponse();
					
					mailMaster.setMailMessage((String)model.get("mailMessage"));
					mailMaster.setMailSubject((String)model.get("mailSubject"));
					mailMaster.setMailServiceType((String)model.get("mailServiceType"));
					validator.validate(mailMaster, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						mailConfigService.save(mailMaster);
						return readMailMaster();
					}
				}
				
				@SuppressWarnings("serial")	
				@RequestMapping(value = "/mailConfig/read", method = RequestMethod.POST)
				public @ResponseBody
				List<?> readMailMaster() {
					List<Map<String, Object>> mailMasterList = new ArrayList<Map<String, Object>>();
					for (final Object record : mailConfigService.findAll()) {
						mailMasterList.add(new HashMap<String, Object>() {
							{
								put("mailMasterId", ((MailMaster) record).getMailMasterId());
								put("mailSubject", ((MailMaster) record).getMailSubject());
								put("mailMessage", ((MailMaster) record).getMailMessage());
								put("mailServiceType", ((MailMaster) record).getMailServiceType());
								
							}
						});
					}
					return mailMasterList;
				}
				
				@RequestMapping(value = "/mailConfig/delete", method = RequestMethod.POST)
				public @ResponseBody
				Object deleteMailConfig(@RequestBody Map<String, Object> map,@ModelAttribute("mailMaster") MailMaster mailMaster, BindingResult bindingResult,
						ModelMap model,final Locale locale) {
						int meeting_id = (Integer)map.get("mailMasterId");
						mailConfigService.delete(meeting_id);
						return mailMaster;
				}
				
				@RequestMapping(value = "/mailConfig/update", method = RequestMethod.POST)
				public @ResponseBody Object updateMailMaster(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute MailMaster mailMaster,
						BindingResult bindingResult) {
					HttpSession session = request.getSession(false);
					JsonResponse errorResponse = new JsonResponse();
					validator.validate(mailMaster, bindingResult);
					if (bindingResult.hasErrors()) {
						errorResponse.setStatus("FAIL");
						errorResponse.setResult(bindingResult.getAllErrors());
						return errorResponse;
					} else {
						mailMaster.setMailMasterid(Integer.parseInt(model.get("mailMasterId").toString()));
						mailMaster.setMailMessage((String)model.get("mailMessage"));
						mailMaster.setMailSubject((String)model.get("mailSubject"));
						mailMaster.setMailServiceType((String)model.get("mailServiceType"));
						mailConfigService.update(mailMaster);
						return mailMaster;
					}
				}
				@RequestMapping(value = "/mailConfig/getService", method = RequestMethod.GET)
				public @ResponseBody List<Map<String, String>> getService(final Locale locale) 
				{
					System.out.println("comingg ");
					String[] noteType = ResourceBundle.getBundle("application").getString("mailMasterServiceType").split(",");
					List<Map<String, String>> result = new ArrayList<Map<String, String>>();
					Map<String, String> map = null;
					for (int i = 0; i < noteType.length; i++)
					{
						map = new HashMap<String, String>();
						map.put("value", noteType[i]);
						map.put("name", noteType[i]);
						result.add(map);
					}
					return result;
				}
				
				@RequestMapping(value="/getService/{value}",method=RequestMethod.POST)
				public @ResponseBody int getPropertyOwnerShipStatus(@PathVariable String value)
				{
					 int count = mailConfigService.getServiceStatus(value);
					 return count;
				}
				
				public void sendMailForNotification(int personId,String mailAddress,CustomerCareNotification customerCareNotification) throws Exception
				{
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
					String personName = "";		
					if(personNameData!=null){
						personName = (String)personNameData[0];
					}
					if(mailAddress!="" && mailAddress!=null)
					{
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
									+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">Skyon Notification</h2> <br /> "
									
								
								
								+ "Dear Resident ,<br/><br/>"
								+ "Subject :- " +customerCareNotification.getSubject()+"<br/><br/>"
								+customerCareNotification.getMessage()
								
								
								+ "<br/><br/>Regards<br/>"
								+ "Resident Services.<br/>"
								+ "</html>";
						String mailSubject = "Notification for "+customerCareNotification.getSubject();
						EmailCredentialsDetailsBean emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
						emailCredentialsDetailsBean.setToAddressEmail(mailAddress);
						emailCredentialsDetailsBean.setMailSubject(mailSubject);
						emailCredentialsDetailsBean.setMessageContent(messageContentForPerson);
						new Thread(new HelpDeskMailSender(emailCredentialsDetailsBean)).start();
					}
				}
				public void sendSMSForNotification(int personId,String mobile,CustomerCareNotification customerCareNotification)
				{
					Object[] personNameData = openNewTicketService.getPersonNameBasedOnPersonId(personId);
					String personName = "";		
					if(personNameData!=null){
						personName = (String)personNameData[0];
					}
					if(mobile!="" && mobile!=null)
					{
					String messagePerson ="Dear Resident ,"+customerCareNotification.getMessage()+" Regards- Resident Services";
						SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
					smsCredentialsDetailsBean.setNumber(mobile);
					smsCredentialsDetailsBean.setUserName(personName);
					smsCredentialsDetailsBean.setMessage(messagePerson);
					new Thread(new SendTicketSMS(smsCredentialsDetailsBean)).start();
					}
				}
}
