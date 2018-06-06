package com.bcits.bfm.controller;

import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.AdvanceBill;
import com.bcits.bfm.model.Arms;
import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.AssetMaintenanceCost;
import com.bcits.bfm.model.AssetSpares;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.City;
import com.bcits.bfm.model.ConciergeVendors;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Country;
import com.bcits.bfm.model.DocumentDefiner;
import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.model.Domestic;
import com.bcits.bfm.model.DomesticProperty;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.Family;
import com.bcits.bfm.model.FamilyProperty;
import com.bcits.bfm.model.FeedbackChild;
import com.bcits.bfm.model.FeedbackMaster;
import com.bcits.bfm.model.MedicalEmergencyDisability;
import com.bcits.bfm.model.OpenNewTicketEntity;
import com.bcits.bfm.model.Owner;
import com.bcits.bfm.model.OwnerAuditTrail;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.Personnel;
import com.bcits.bfm.model.Pets;
import com.bcits.bfm.model.Project;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.State;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.OwnerAuditTrailService;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.advanceBilling.AdvanceBillingService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ArmsService;
import com.bcits.bfm.service.customerOccupancyManagement.CityService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.CountryService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentDefinerService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentRepositoryService;
import com.bcits.bfm.service.customerOccupancyManagement.DomesticPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.DomesticService;
import com.bcits.bfm.service.customerOccupancyManagement.FamilyPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.FamilyService;
import com.bcits.bfm.service.customerOccupancyManagement.MedicalEmergencyDisabilityService;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonAccessCardService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PetsService;
import com.bcits.bfm.service.customerOccupancyManagement.ProjectLocationService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.StateService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantPropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantSevice;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceCostService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceService;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.service.facilityManagement.AssetSparesService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.ConciergeVendorsService;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.facilityManagement.VehicleService;
import com.bcits.bfm.service.facilityManagement.VisitorVisitsService;
import com.bcits.bfm.service.helpDeskManagement.Feedback_ChildService;
import com.bcits.bfm.service.helpDeskManagement.Feedback_MasterService;
import com.bcits.bfm.service.helpDeskManagement.OpenNewTicketService;
import com.bcits.bfm.service.helpDeskManagement.TicketAssignService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.GroupsService;
import com.bcits.bfm.service.userManagement.RoleService;
import com.bcits.bfm.service.userManagement.UserGroupService;
import com.bcits.bfm.service.userManagement.UserRoleService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.customerOccupancyManagement.ProjectServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * Controller which includes all the business logic concerned to this module
 * Module: Customer Occupancy Management
 * 
 * @author Manjunath Krishnappa, Farooq, Ravi Shankar, Syed, Pooja K, Shashi
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class CustomerOccupancyManagementController
{

	@PersistenceContext(unitName = "bfm")
	protected EntityManager entityManager;

	private static final Log logger = LogFactory.getLog(CustomerOccupancyManagementController.class);

	@Autowired
	private CommonController commonController;
	@Autowired
	private JsonResponse errorResponse;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	@Autowired
	private DrGroupIdService drGroupIdService;
	@Autowired
	private GroupsService groupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private DesignationService designationService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PersonService personService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private MedicalEmergencyDisabilityService medicalEmergencyDisabilityService;
	@Autowired
	private ArmsService armsService;
	@Autowired
	private ProjectServiceImpl projectService;
	@Autowired
	private Validator validator;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	private PropertyService propertyImpl;
	@Autowired
	private CountryService countryService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private TenantSevice tenantSevice;
	@Autowired
	private FamilyService familySevice;
	@Autowired
	private DomesticService domesticService;
	@Autowired
	private VendorsService vendorsService;
	@Autowired
	private PetsService petsService;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private MailRoomService mailService;
	@Autowired
	private DateTimeCalender dateTimeCalender;
	@Autowired
	private ProjectLocationService projectLocationService;
	@Autowired
	private BlocksService blocksService;
	@Autowired
	private ConciergeVendorsService conciergeVendorsService;
	@Autowired
	private ValidationUtil validationUtil;
	@Autowired
	private DocumentDefinerService documentDefinerService;
	@Autowired
	private OwnerPropertyService ownerPropertyService;;
	@Autowired
	private TenantPropertyService tenantPropertyService;
	@Autowired
	private TicketAssignService ticketAssignService;
	@Autowired
	private DocumentRepositoryService documentRepoService;
	@Autowired
	private FamilyPropertyService familyProperty;
	@Autowired
	private OwnerAuditTrailService ownerAuditTrailService;
	@Autowired
	private DomesticPropertyService domesticPropertyServicel;
	@Autowired
	private ParkingSlotsAllotmentService parkingSlotsAllotmentService;
	@Autowired
	private PersonAccessCardService personAccessCardService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private AssetService assetService;
	@Autowired
	private VisitorVisitsService visitorVisitsService;
	@Autowired
	private ElectricityBillsService billsService;
	@Autowired
	private OpenNewTicketService openNewTicketService;
	@PersistenceContext(unitName="MSSQLDataSourceAccessCards")
	private EntityManager msSQLentityManager;
	@Autowired
	private AdvanceBillingService advanceBillingService;
	@Autowired
	AssetSparesService assetSparesService;
	@Autowired
	AssetMaintenanceCostService assetMaintenanceCostService;
	@Autowired
	private Feedback_MasterService feedback_MasterService;
	@Autowired
	private Feedback_ChildService feedback_ChildService;
	
	/* ============================= Consolidated Report ============================================ */
	//String qry ="select l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||''||pr.LAST_NAME as Customer_name,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Email' and contact_location='Home') as Email,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Mobile' and contact_location='Home') as Mobile from ledger l,account a,property p,person pr where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID and pr.PERSON_TYPE = 'Owner' order by account_id";
	String CONSOLIDATEEDreport="select l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||''||pr.LAST_NAME as Customer_name,X.CONTACT_CONTENT as Email,Y.CONTACT_CONTENT as Mobile\n" +
			"from ledger l,account a,property p,person pr ,\n" +
			"(\n" +
			"SELECT person_id,CONTACT_CONTENT FROM contact where PERSON_ID IN(\n" +
			"SELECT Q.person FROM\n" +
			"(\n" +
			"select pr.person_id as person,l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||''||pr.LAST_NAME as Customer_name\n" +
			"from ledger l,account a,property p,person pr \n" +
			"where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and \n" +
			"l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID and pr.PERSON_TYPE = 'Owner' order by account_id\n" +
			")Q\n" +
			") AND contact_type='Email' and contact_location='Home'\n" +
			")X, \n" +
			"(\n" +
			"SELECT person_id,CONTACT_CONTENT FROM contact where PERSON_ID IN(\n" +
			"SELECT Q.person FROM\n" +
			"(\n" +
			"select pr.person_id as person,l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||''||pr.LAST_NAME as Customer_name\n" +
			"from ledger l,account a,property p,person pr \n" +
			"where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and \n" +
			"l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID and pr.PERSON_TYPE = 'Owner' order by account_id\n" +
			")Q\n" +
			") AND contact_type='Mobile' and contact_location='Home'\n" +
			")Y \n" +
			"where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and \n" +
			"l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID and pr.PERSON_TYPE = 'Owner' and PR.PERSON_ID=X.PERSON_ID and\n" +
			" X.PERSON_ID=Y.PERSON_ID order by account_id";
	

	@RequestMapping("/comgeneral")
	public String comGeneral(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		model.addAttribute("ViewName", "Customer Occupancy");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("General", 1, request);
		return "com/general";
	}


	/**
	 * Use Case: ----------------------------------------- Manage Persons --------------------------------------------
	 */

	/**
	 * All the person instances will be send to the view
	 * 
	 * @param model
	 *            This will set message inside view
	 * @param request
	 *            Input from the view
	 * @return Control goes to respective view
	 * @since 0.1
	 */
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public String indexPerson(ModelMap model, HttpServletRequest request)
	{
		model.addAttribute("ViewName", "Person");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manage persons", 1, request);
		model.addAttribute("person", new Person());
		return "com/person";
	}

	/**
	 * Fetching all the values from the Person table and its children
	 * 
	 * @return All person instances
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/read/{personType}", method = RequestMethod.POST)
	public @ResponseBody Object readPerson(@PathVariable String personType, final Locale locale) 
	{
		try
		{
			return personService.getAllPersonsRequiredFilds(personType);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	/**
	 * Modifying person details 
	 * 
	 * @param operation
	 *            Operation - Create/Update based on user operation
	 * @param personType
	 *            personType - Owner/Tenant/Family/DomesticHelp/Vendor/ConciergeVendor
	 * @param person
	 *            person instance to be modified
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param model
	 *            Required to be there along with binding result for validation purpose         
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the status name based on locale from properties file
	 * @param request
	 *            Field values from the input jsp in the form of request
	 * @return Modified person instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/modify/{operation}/{personType}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyPerson(@PathVariable String operation, @PathVariable String personType, @ModelAttribute("person") Person person, 
			BindingResult bindingResult, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest request)
	{
		try
		{
			person = personService.getPersonNewObject(person, operation);

			if(!((person.getPersonStyle()).equalsIgnoreCase("Individual")))
			{
				if((validationUtil.customValidateModelObject(person, "NonIndividual", locale)) != null)
					return validationUtil.customValidateModelObject(person, "NonIndividual", locale);
			}
			else
			{
				if((validationUtil.customValidateModelObject(person, "Individual", locale)) != null)
					return validationUtil.customValidateModelObject(person, "Individual", locale);
			}

			if(operation.equalsIgnoreCase("create"))
			{


				drGroupIdService.save(new DrGroupId(person.getCreatedBy(), person.getLastUpdatedDt()));
				person.setDrGroupId(drGroupIdService.getNextVal(person.getCreatedBy(), person.getLastUpdatedDt()));
				person = setChildSaveDetails(person, person.getPersonType(), request);
				personService.save(person);	

			}
			else

			{

				person = setChildUpdateDetails(person, personType, request);
				personService.update(person);




			}
			person.setPersonImages(null);
			sessionStatus.setComplete();
			return personService.getPersonFullDetails(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}





	/**
	 * Setting the child values while creation
	 *
	 * @param personType
	 *            personType - Owner/Tenant/Family/DomesticHelp/Vendor/ConciergeVendor
	 * @param request
	 *            Field values from the input jsp in the form of request
	 * @return Person instance
	 * @since 0.1
	 */
	private Person setChildSaveDetails(Person person, String personType, HttpServletRequest request)
	{
		switch (personType)
		{
		case "Owner":
		{

			Owner owner = new Owner();
			owner.setCreatedBy(person.getLastUpdatedBy());
			owner.setLastUpdatedBy(person.getLastUpdatedBy());
			owner.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			owner.setOwnerStatus("Inactive");
			owner.setPerson(person);

			person.setOwner(owner);
			break;
		}

		case "Tenant":
		{
			Tenant tenant = new Tenant();
			tenant.setCreatedBy(person.getCreatedBy());
			tenant.setLastUpdatedBy(person.getLastUpdatedBy());
			tenant.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			tenant.setTenantStatus("Inactive");
			tenant.setPerson(person);
			person.setTenant(tenant);
			break;
		}

		case "Family":
		{
			Family family = new Family();
			family.setCreatedBy(person.getCreatedBy());
			family.setLastUpdatedBy(person.getLastUpdatedBy());
			family.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			family.setFamilyStatus("Inactive");
			family.setPerson(person);
			person.setFamily(family);
			break;
		}

		case "DomesticHelp":
		{
			Domestic domesticHelp = new Domestic();
			domesticHelp.setCreatedBy(person.getCreatedBy());
			domesticHelp.setLastUpdatedBy(person.getLastUpdatedBy());
			domesticHelp.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			domesticHelp.setDomesticHelpStatus("Inactive");
			domesticHelp.setPerson(person);
			person.setDomesticHelp(domesticHelp);
			break;
		}

		case "Vendor":
		{				
			Vendors vendor = new Vendors();
			vendor.setPerson(person);
			vendor.setPanNo(request.getParameter("panNo"));
			vendor.setCstNo(request.getParameter("cstNo"));
			vendor.setStateTaxNo(request.getParameter("stateTaxNo"));
			vendor.setServiceTaxNo(request.getParameter("serviceTaxNo"));
			vendor.setStatus("Created");
			vendor.setVendorPersonStatus("Inactive");
			vendor.setCreatedBy(person.getLastUpdatedBy());
			vendor.setLastUpdatedBy(person.getLastUpdatedBy());
			vendor.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			vendor.setPerson(person);
			person.setVendors(vendor);
			break;				
		}

		case "ConciergeVendor":
		{
			ConciergeVendors conciergeVendors = new ConciergeVendors();
			conciergeVendors.setPerson(person);
			conciergeVendors.setCstNo(request.getParameter("cstNo"));
			conciergeVendors.setStateTaxNo(request.getParameter("stateTaxNo"));
			conciergeVendors.setServiceTaxNo(request.getParameter("serviceTaxNo"));
			conciergeVendors.setStatus(request.getParameter("status"));
			conciergeVendors.setCreatedBy(person.getLastUpdatedBy());
			conciergeVendors.setLastUpdatedBy(person.getLastUpdatedBy());
			conciergeVendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			conciergeVendors.setPerson(person);
			person.setConciergeVendors(conciergeVendors);
			break;
		}		
		}
		return person;
	}



	@RequestMapping(value = "/getAllOutstandingReports")
	public String oustandingQuery(Model model, HttpServletRequest request) {
		logger.info("******Inside a getAllOustandingReports Method***************"
				+ request.getParameter("jspId"));

		model.addAttribute("data", request.getParameter("jspId"));

		return "com/oustandingQueryReport";
	}
	
	@RequestMapping(value = "/getCAMreports")
	public String getCAMreports(Model model, HttpServletRequest request) {
		logger.info("******Inside a getCAMreports Method***************" + request.getParameter("jspId"));
		
		model.addAttribute("data", request.getParameter("jspId"));
		
		return "com/camreports";
	}
	
	@RequestMapping(value = "/com/readCamData")
	public @ResponseBody List<Map<String,Object>> readCamData() {
		logger.info("******Inside a readCamData Method***************");
		List<Map<String,Object>> reportlist=new ArrayList<>();
		Map<String,Object> map=null;
		
		List<?> camReport = entityManager.createNativeQuery("SELECT * FROM CAM_REPORT_VIEW").getResultList();
		for (Iterator iterator = camReport.iterator(); iterator.hasNext();) {
			Object[] report = (Object[]) iterator.next();
			map=new HashMap<>();
			map.put("accid",report[0]);
			map.put("elbid",report[1]);
			map.put("propertyno",report[2]);
			map.put("accnumber",report[3]);
			map.put("billmonth",report[4]);
			map.put("camamount",report[5]);
			map.put("cgstamt",report[6]);
			map.put("sgstamt",report[7]);
			map.put("cgstltpay",report[8]);
			map.put("sgstltpay",report[9]);
			map.put("pldamt",report[10]);
			map.put("gstonpld",report[11]);
			map.put("arrearamt",report[12]);
			map.put("billamt",report[13]);
			map.put("netamt",report[14]);
			reportlist.add(map);
		}
		return reportlist;
	}
	
	@RequestMapping(value = "/ExportCamReportToExcel", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCamReportToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException
	{
		System.err.println("************* Inside exportPaymentsToExcel() Method *************");
		/*String resp = request.getParameter("date");
		String[] date = resp.split("/");*/
		
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
		/*String payments = "select p.CP_RECEIPT_NO,a.ACCOUNT_NO,(PR.FIRST_NAME||PR.LAST_NAME)AS NAME,pro.PROPERTY_NO,p.CP_RECEIPT_DATE,p.CP_AMOUNT,p.INSTRUMENT_NO"
						+ " FROM PAYMENTS p,ACCOUNT a,PROPERTY pro,PERSON pr WHERE p.ACCOUNT_ID=a.ACCOUNT_ID AND a.PROPERTYID=pro.PROPERTY_ID"
						+ " AND a.PERSON_ID=PR.PERSON_ID AND EXTRACT(month FROM p.CP_RECEIPT_DATE) IN("+date[0]+") AND "
						+ " EXTRACT(year FROM p.CP_RECEIPT_DATE)="+date[1]+" AND p.STATUS NOT IN 'Cancelled' ORDER BY p.CP_RECEIPT_DATE DESC";
		*/
		List<Object[]> camReport = entityManager.createNativeQuery("SELECT * FROM CAM_REPORT_VIEW").getResultList();
		
		//List<Object[]> paymentRecords = entityManager.createNativeQuery(payments).getResultList();
		//System.err.println("paymentRecords="+paymentRecords); 
		
		
        String sheetName = "Templates";//name of sheet

    	XSSFWorkbook wb = new XSSFWorkbook();
    	XSSFSheet sheet = wb.createSheet(sheetName) ;
    	XSSFRow header  = sheet.createRow(0);    	
    	
    	XSSFCellStyle style = wb.createCellStyle();
    	style.setWrapText(true);
    	XSSFFont font = wb.createFont();
    	font.setFontName(HSSFFont.FONT_ARIAL);
    	font.setFontHeightInPoints((short)10);
    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    	style.setFont(font);
    	
    	header.createCell(0).setCellValue("PROPERTY_NO");
        header.createCell(1).setCellValue("BILL_MONTH");
        header.createCell(2).setCellValue("CAM_AMOUNT");
        header.createCell(3).setCellValue("CGST");
        header.createCell(4).setCellValue("SGST");
        header.createCell(5).setCellValue("CGST_LATE_PAY");
        header.createCell(6).setCellValue("SGST_LATE_PAY");
        header.createCell(7).setCellValue("PREVIOUS_LATE_PAY");
        header.createCell(8).setCellValue("GST_ON_PLD");
        header.createCell(9).setCellValue("ARREAR");
        header.createCell(10).setCellValue("BILL_AMOUNT");
        header.createCell(11).setCellValue("NET_AMOUNT");
    
        for(int j = 0; j<7; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I200"));
        }
        
        int count = 1;
    	for (Object[] paymentRecord :camReport ) 
    	{
    		/*String str = "";
    		if((String)paymentRecord[6]!=null){
    			str=(String)paymentRecord[6];
    		}else{
    			str="NA";
    		}*/
    		
    		XSSFRow row = sheet.createRow(count);
    		row.createCell(0).setCellValue(paymentRecord[2].toString());
    		row.createCell(1).setCellValue(paymentRecord[4].toString());
    		row.createCell(2).setCellValue(paymentRecord[5].toString());
    		
    		row.createCell(3).setCellValue(paymentRecord[6].toString());
    		row.createCell(4).setCellValue(paymentRecord[7].toString());
    		row.createCell(5).setCellValue(paymentRecord[8].toString());
    		row.createCell(6).setCellValue(paymentRecord[9].toString());
    		row.createCell(7).setCellValue(paymentRecord[10].toString());
    		row.createCell(8).setCellValue(paymentRecord[11].toString());
    		row.createCell(9).setCellValue(paymentRecord[12].toString());
    		row.createCell(10).setCellValue(paymentRecord[13].toString());
    		row.createCell(11).setCellValue(paymentRecord[14].toString());
    		count ++;
    	}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"CAM_REPORT.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}



	/**
	 * Setting the child values while updation
	 *
	 * @param person
	 *            person instance to be modified
	 * @param personType
	 *            personType - Owner/Tenant/Family/DomesticHelp/Vendor/ConciergeVendor
	 * @param request
	 *            Field values from the input jsp in the form of request
	 * @return Person instance
	 * @since 0.1
	 */
	private Person setChildUpdateDetails(Person person, String personType, HttpServletRequest request)
	{
		switch (personType)
		{
		case "Owner":
		{


			Map<String, Object> params = new HashMap<String, Object>();
			params.put("personId", person.getPersonId());

			Owner owner = (Owner) (ownerService.selectObjectQuery(params)).get(0);


			owner.setLastUpdatedBy(person.getLastUpdatedBy());
			owner.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setOwner(owner);

			//audit.setCurrent(person);

			Person oldPerson=ownerAuditTrailService.getPersonObjectBasedOnID(person.getPersonId());


			logger.info("finding the person stle while updating -----"+oldPerson.getPersonStyle()+"-new style---"+person.getPersonStyle());



			//THIs is for AUDITINg a EDIT fields in OWNER 
			if(oldPerson.getPersonStyle().equalsIgnoreCase("Individual"))
			{

				if(!person.getTitle().equalsIgnoreCase(oldPerson.getTitle()))
				{

					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getTitle());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("TITLE");
					ownerAuditTrail.setOwner_current(person.getTitle());
					ownerAuditTrailService.save(ownerAuditTrail);
				}



				if(!person.getFirstName().equalsIgnoreCase(oldPerson.getFirstName()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());

					ownerAuditTrail.setOwner_previous(oldPerson.getFirstName());
					ownerAuditTrail.setUpdated_field("FIRST_NAME");
					ownerAuditTrail.setOwner_current(person.getFirstName());
					ownerAuditTrailService.save(ownerAuditTrail);
				}

				if(!person.getMiddleName().equals("")||oldPerson.getMiddleName()!=null)
				{

					if(!person.getMiddleName().equalsIgnoreCase(oldPerson.getMiddleName()))
					{
						OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
						ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
						ownerAuditTrail.setOwner_previous(oldPerson.getMiddleName());
						ownerAuditTrail.setUpdated_field("MIDDLE_NAME");

						ownerAuditTrail.setOwner_current(person.getMiddleName());
						ownerAuditTrailService.save(ownerAuditTrail);


					}
				}

				if(!person.getLastName().equalsIgnoreCase(oldPerson.getLastName()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getLastName());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("LAST_NAME");
					ownerAuditTrail.setOwner_current(person.getLastName());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				if(!person.getFatherName().equalsIgnoreCase(oldPerson.getFatherName()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getFatherName());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("FATHER_NAME");
					ownerAuditTrail.setOwner_current(person.getFatherName());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				if(!person.getMaritalStatus().equalsIgnoreCase(oldPerson.getMaritalStatus()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getMaritalStatus());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("MARITAL_STATUS");
					ownerAuditTrail.setOwner_current(person.getMaritalStatus());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				if(!person.getOccupation().equalsIgnoreCase(oldPerson.getOccupation()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getOccupation());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("OCCUPATION");
					ownerAuditTrail.setOwner_current(person.getOccupation());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				if(!person.getNationality().equalsIgnoreCase(oldPerson.getNationality()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getNationality());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("NATIONALITY");
					ownerAuditTrail.setOwner_current(person.getNationality());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				if(!person.getBloodGroup().equalsIgnoreCase(oldPerson.getBloodGroup()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getBloodGroup());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("BLOOD_GROUP");
					ownerAuditTrail.setOwner_current(person.getBloodGroup());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				/*if(!person.getSpouseName().equalsIgnoreCase(oldPerson.getSpouseName()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getSpouseName());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("SPOUSE_NAME");
					ownerAuditTrail.setOwner_current(person.getSpouseName());
					ownerAuditTrailService.save(ownerAuditTrail);

				}*/
				System.out.println("date       "+person.getDob());
				//if(!person.getDob().equals(oldPerson.getDob()) || oldPerson.getDob()!=null )
				if( oldPerson.getDob()!=null && person.getDob()!=null && !person.getDob().equals(oldPerson.getDob()) )
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getDob().toString());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("DATE_OF_BIRTH");
					ownerAuditTrail.setOwner_current(person.getDob().toString());
					ownerAuditTrailService.save(ownerAuditTrail);

				}
				else
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous("");
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("DATE_OF_BIRTH");
					ownerAuditTrail.setOwner_current("");
					ownerAuditTrailService.save(ownerAuditTrail);
				}


				if(!person.getLanguagesKnown().equalsIgnoreCase(oldPerson.getLanguagesKnown()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getLanguagesKnown());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("LANGUAGE_KNOWN");
					ownerAuditTrail.setOwner_current(person.getLanguagesKnown());
					ownerAuditTrailService.save(ownerAuditTrail);

				}

				if(!person.getWorkNature().equalsIgnoreCase(oldPerson.getWorkNature()))
				{
					OwnerAuditTrail ownerAuditTrail=new OwnerAuditTrail();
					ownerAuditTrail.setOwnerName(person.getFirstName()+" "+person.getMiddleName()+" "+person.getLastName());
					ownerAuditTrail.setOwner_previous(oldPerson.getWorkNature());
					ownerAuditTrail.setOwnerId(person.getPersonId());
					ownerAuditTrail.setUpdated_field("WORK_NATURE");
					ownerAuditTrail.setOwner_current(person.getWorkNature());
					ownerAuditTrailService.save(ownerAuditTrail);

				}



			}


			break;
		}

		case "Tenant":
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("personId", person.getPersonId());

			Tenant tenant = (Tenant) (tenantSevice.selectObjectQuery(params)).get(0);
			tenant.setLastUpdatedBy(person.getLastUpdatedBy());
			tenant.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setTenant(tenant);
			break;
		}

		case "Family":
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("personId", person.getPersonId());

			Family family = (Family) (familySevice.selectObjectQuery(params)).get(0);
			family.setLastUpdatedBy(person.getLastUpdatedBy());
			family.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setFamily(family);
			break;
		}

		case "DomesticHelp":
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("personId", person.getPersonId());

			Domestic domesticHelp = (Domestic) (domesticService.selectObjectQuery(params)).get(0);
			domesticHelp.setLastUpdatedBy(person.getLastUpdatedBy());
			domesticHelp.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setDomesticHelp(domesticHelp);
			break;
		}

		case "Vendor":
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("personId", person.getPersonId());

			Vendors vendors = (Vendors) (vendorsService.selectObjectQuery(params)).get(0);
			vendors.setLastUpdatedBy(person.getLastUpdatedBy());
			vendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setVendors(vendors);
			break;
		}

		case "ConciergeVendor":
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("personId", person.getPersonId());

			ConciergeVendors conciergeVendors = (ConciergeVendors) (conciergeVendorsService.selectObjectQuery(params)).get(0);
			conciergeVendors.setLastUpdatedBy(person.getLastUpdatedBy());
			conciergeVendors.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setConciergeVendors(conciergeVendors);
			break;
		}		
		}
		return person;
	}

	@RequestMapping(value="/Persons/languageFilter",method=RequestMethod.GET)
	public @ResponseBody Set<?> readLanguageFilter()
	{
		Set<String> data=new HashSet<String>();
		Iterator<?> iterator=personService.getLanguageNew().iterator();
		while(iterator.hasNext())
		{

			data.add((String)iterator.next());
		}
		return data;
	}

	/**
	 * Setting the status of the person as well as updating the child status
	 * 
	 * @param personId
	 *            This person id to change the status.
	 * @param operation
	 *            operation - Either Activate or De-Activate the child
	 * @param personType
	 *            personType - Owner/Tenant/Family/DomesticHelp/Vendor/ConciergeVendor
	 * @param locale
	 *            Is required to fetch the status name based on locale from properties file          
	 * @return the status as a string after activation/de-activation for display purpose
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/personStatus/{personId}/{operation}/{personType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String personStatus(@PathVariable int personId, @PathVariable String operation, @PathVariable String personType)
	{		
		return personService.setPersonStatus(personId, operation, personType);
	}

	/**
	 * Getting all field values with respect to person for filtering purpose based on person type
	 *   
	 * @param personType
	 *            personType - Owner/Tenant/Family/DomesticHelp/Vendor/ConciergeVendor
	 * @param attribute
	 *            attribute - field name          
	 * @return All field values with respect to person for filtering purpose based on person type
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/getAllAttributeValues/{personType}/{attribute}", method = RequestMethod.GET)
	public @ResponseBody List<String> getAllAttributeValues(@PathVariable String personType, @PathVariable String attribute) 
	{
		return 	personService.getAllAttributeValues(personType, attribute);
	}

	/**
	 * Getting required field values with respect to person based on person style
	 *   
	 * @param personStyle
	 *            personStyle - Individual,Company,Proprietor,LLP,Partnership
	 *            
	 * @return All field values with respect to person for filtering purpose based on person type
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/getPersonList/{personStyle}", method = RequestMethod.GET)
	public @ResponseBody List<?> getPersonIndividualsList(@PathVariable String personStyle) 
	{
		List<Object> personNamesList = personService.getAllPersonRequiredFieldsBasedOnPersonStyle(personStyle);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<Object> i = personNamesList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String)values[0]);
			if(((String)values[1]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[1]);
			}
			map = new HashMap<String, Object>();
			map.put("personId", (Integer)values[2]);
			map.put("name", personName);
			map.put("value", personName);
			map.put("personType", (String)values[3]);
			map.put("personStyle", (String)values[4]);
			result.add(map);
		}
		return result;
	} 

	/**
	 * Getting person types for toolbar template to add person based on selection of the category
	 *   
	 * @param locale
	 *            Is required to fetch the person style based on locale from properties file
	 * @return All field values with respect to person for filtering purpose based on person type
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/categories", method = RequestMethod.GET)
	public @ResponseBody List<?> categories(final Locale locale) 
	{
		String[] str = messageSource.getMessage("personStyle", null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
		List<Map<String, String>> categoriesList = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			map = new HashMap<String, String>();
			map.put("personStyle", string);
			categoriesList.add(map);
		}
		return categoriesList;
	}

	/**
	 * --------------------------------------------------- End --------------------------------------------
	 */

	/**
	 * Use Case: ----------------------------------------- Manage Medical Emergency Disability --------------------------------------------
	 */

	/**
	 * Fetching all the values from the Medical Emergency Disability table
	 *
	 * @param personId
	 *            Person id of the person who is having this disability
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return All medical emergency disability instances
	 * @since 0.1
	 */
	@RequestMapping(value = "/medicalEmergencyDisability/read/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readMedicalEmergencyDisability(@PathVariable int personId, final Locale locale) 
	{
		try
		{
			return medicalEmergencyDisabilityService.findAllMedicalEmergencyDisabilityBasedOnPersonID(personId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	/**
	 * Creation of new Medical Emergency Disability record 
	 * 
	 * @param map
	 *            Input from view includes Medical Emergency Disability details
	 * @param medicalEmergencyDisability
	 *            Medical Emergency Disability instance to be created
	 * @param personId
	 *            Person id of the person who is having this disability
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param model
	 *            Required to be there along with binding result for validation purpose         
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return created medical emergency disability instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = "/medicalEmergencyDisability/create/{personId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createMedicalEmergencyDisability(@PathVariable int personId, @RequestBody Map<String, Object> map,
			@ModelAttribute("medicalEmergencyDisability") MedicalEmergencyDisability medicalEmergencyDisability, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) 
	{
		Person person = null;
		try
		{
			medicalEmergencyDisability = medicalEmergencyDisabilityService.getMedicalEmergencyDisabilityObject(map, "save", medicalEmergencyDisability);
			JsonResponse response = validationUtil.validateModelObject(medicalEmergencyDisability, bindingResult);
			if(response.getStatus().equalsIgnoreCase("FAIL"))
				return response;
			else 
			{
				person = new Person();
				person = personService.find(personId);
				Set<MedicalEmergencyDisability> medicalEmergencyDisabilitySet = person.getMedicalEmergencyDisabilities();
				medicalEmergencyDisabilitySet.add(medicalEmergencyDisability);
				person.setMedicalEmergencyDisabilities(medicalEmergencyDisabilitySet);
				personService.update(person);
				sessionStatus.setComplete();
				medicalEmergencyDisability.setMeId(medicalEmergencyDisabilityService.getMedicalEmergencyDisabilityIdBasedOnCreatedByAndLastUpdatedDt(medicalEmergencyDisability.getCreatedBy(), medicalEmergencyDisability.getLastUpdatedDt()));
				return medicalEmergencyDisability;			
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	/**
	 * Updating Medical Emergency Disability record 
	 * 
	 * @param map
	 *            Input from view includes Medical Emergency Disability details
	 * @param medicalEmergencyDisability
	 *            Medical Emergency Disability instance to be updated
	 * @param personId
	 *            Person id of the person who is having this disability
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param model
	 *            Required to be there along with binding result for validation purpose         
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return updated medical emergency disability instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = "/medicalEmergencyDisability/update/{personId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object updateMedicalEmergencyDisability(@RequestBody Map<String, Object> map,
			@ModelAttribute("medicalEmergencyDisability") MedicalEmergencyDisability medicalEmergencyDisability, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) throws Exception 
			{
		try
		{
			medicalEmergencyDisability = medicalEmergencyDisabilityService.getMedicalEmergencyDisabilityObject(map, "update", medicalEmergencyDisability);
			JsonResponse response = validationUtil.validateModelObject(medicalEmergencyDisability, bindingResult);
			if(response.getStatus().equalsIgnoreCase("FAIL"))
				return response;
			else 
			{
				medicalEmergencyDisability = medicalEmergencyDisabilityService.update(medicalEmergencyDisability);
				sessionStatus.setComplete();
				return medicalEmergencyDisability;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
			}

	/**
	 * Deleting Medical Emergency Disability record 
	 * 
	 * @param map
	 *            Input from view includes Medical Emergency Disability details
	 * @param medicalEmergencyDisability
	 *            Medical Emergency Disability instance to be deleted
	 * @param personId
	 *            Person id of the person who is having this disability
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return updated medical emergency disability instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = {"/manpower/medicalEmergencyDisability/delete","/owner/medicalEmergencyDisability/delete","/tenant/medicalEmergencyDisability/delete","/dom/medicalEmergencyDisability/delete","/family/medicalEmergencyDisability/delete"}, method = RequestMethod.POST)
	public @ResponseBody Object deleteMedicalEmergencyDisability(@RequestBody Map<String, Object> map, @ModelAttribute("medicalEmergencyDisability") MedicalEmergencyDisability medicalEmergencyDisability, 
			BindingResult bindingResult, SessionStatus sessionStatus, final Locale locale) 
	{
		try
		{
			medicalEmergencyDisabilityService.delete((int)map.get("meId"));
			sessionStatus.setComplete();
			return medicalEmergencyDisability;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}


	/**
	 * --------------------------------------------------- End --------------------------------------------
	 */

	/**
	 * Use Case: ----------------------------------------- Manage Arms --------------------------------------------
	 */

	/**
	 * Fetching all the values from the Arms table
	 *
	 * @param personId
	 *            Person id of the person who is having this arms
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return All medical emergency disability instances
	 * @since 0.1
	 */
	@RequestMapping(value = "/arms/read/{personId}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object readArms(@PathVariable int personId, final Locale locale) 
	{
		try
		{
			return armsService.findAllArmsBasedOnPersonID(personId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	/**
	 * Creation of new Arms record 
	 * 
	 * @param map
	 *            Input from view includes Arms details
	 * @param arms
	 *            Arms instance to be created
	 * @param personId
	 *            Person id of the person who is having this disability
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param model
	 *            Required to be there along with binding result for validation purpose         
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return created medical emergency disability instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = "/arms/create/{personId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createArms(@PathVariable int personId,@RequestBody Map<String, Object> map,
			@ModelAttribute("arms") Arms arms, BindingResult bindingResult, ModelMap model, SessionStatus sessionStatus, final Locale locale) 
	{
		Person person = null;
		try
		{
			arms = armsService.getArmsObject(map, "save", arms);
			drGroupIdService.save(new DrGroupId(arms.getCreatedBy(), arms.getLastUpdatedDt()));
			arms.setDrGroupId(drGroupIdService.getNextVal(arms.getCreatedBy(), arms.getLastUpdatedDt()));
			JsonResponse response = validationUtil.validateModelObject(arms, bindingResult);
			if(response.getStatus().equalsIgnoreCase("FAIL"))
				return response;
			else 
			{
				person = new Person();
				person = personService.find(personId);
				Set<Arms> armses = person.getArms();
				armses.add(arms);
				person.setArms(armses);
				personService.update(person);
				sessionStatus.setComplete();
				arms.setArmsId(armsService.getArmsIdBasedOnCreatedByAndLastUpdatedDt(arms.getCreatedBy(), arms.getLastUpdatedDt()));
				return arms;					
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	/**
	 * Updating Arms record 
	 * 
	 * @param map
	 *            Input from view includes Arms details
	 * @param arms
	 *            Medical Emergency Disability instance to be updated
	 * @param personId
	 *            Person id of the person who is having this arms
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param model
	 *            Required to be there along with binding result for validation purpose         
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return updated medical emergency disability instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = "/arms/update/{personId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object updateArms(@RequestBody Map<String, Object> map,
			@ModelAttribute("arms") Arms arms, BindingResult bindingResult, ModelMap model, SessionStatus sessionStatus, final Locale locale) throws Exception 
			{
		try
		{
			arms = armsService.getArmsObject(map, "update", arms);
			JsonResponse response = validationUtil.validateModelObject(arms, bindingResult);
			if(response.getStatus().equalsIgnoreCase("FAIL"))
				return response;
			else 
			{
				arms = armsService.update(arms);
				sessionStatus.setComplete();
				return arms;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
			}

	/**
	 * Deleting Arms record 
	 * 
	 * @param map
	 *            Input from view includes Arms details
	 * @param arms
	 *            Arms instance to be deleted
	 * @param personId
	 *            Person id of the person who is having this arms
	 * @param bindingResult
	 *            Used to set the validation errors data
	 * @param sessionStatus
	 *            Used to clear the session
	 * @param locale
	 *            Is required to fetch the error message based on locale from properties file
	 * @return updated medical emergency disability instance
	 * @throws Exception 
	 * @since 0.1
	 */
	@RequestMapping(value = {"/manpower/arms/delete","/owner/arms/delete","/tenant/arms/delete","/dom/arms/delete","/family/arms/delete"}, method = RequestMethod.POST)
	public @ResponseBody Object deleteArms(@RequestBody Map<String, Object> map, @ModelAttribute("arms") Arms arms, BindingResult bindingResult, SessionStatus sessionStatus, final Locale locale)
	{
		try
		{
			armsService.delete((int)map.get("armsId"));
			sessionStatus.setComplete();
			return arms;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	/**
	 * --------------------------------------------------- End --------------------------------------------
	 */


	@RequestMapping("/persons")
	public String personsIndex(HttpServletRequest request,HttpServletResponse response,ModelMap model, final Locale locale)
	{
		model.addAttribute("ViewName", "Persons");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manage Persons", 1, request);

		model.addAttribute("personStyle", commonController.populateCategories("personStyle", locale));
		model.addAttribute("title", commonController.populateCategories("title", locale));
		model.addAttribute("maritalStatus", commonController.populateCategories("maritalStatus", locale));
		model.addAttribute("sex", commonController.populateCategories("sex", locale));
		model.addAttribute("nationality", commonController.populateCategories("nationality", locale));
		model.addAttribute("bloodGroup", commonController.populateCategories("bloodGroup", locale));
		model.addAttribute("workNature", commonController.populateCategories("workNature", locale));

		model.addAttribute("meCategory", commonController.populateCategories("meCategory", locale));

		return "com/personsIndex";
	}

	@RequestMapping(value="/property/getProjectName",method= RequestMethod.GET)
	public @ResponseBody List<Project> getProjectName()
	{
		List<Project> project_list = new ArrayList<Project>();
		Project project = null;
		for (final Project record : projectService.findAll()) 
		{
			project =  new Project();
			project.setProjectId(record.getProjectId());
			project.setProjectName(record.getProjectName());
			project_list.add(project);
		}
		return project_list;
	}



	/**
	 * Upload Asset related documents
	 * 
	 * @param files			Multipart File
	 * @return          	none
	 * @since           	1.0
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/property/upload", method = RequestMethod.POST)
	public @ResponseBody
	void uploadPropertyDocument(@RequestParam MultipartFile files,	HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {

		int propertyId = Integer.parseInt(request.getParameter("propertyId"));
		String docType = request.getParameter("type");
		Blob blob;
		blob = Hibernate.createBlob(files.getInputStream());
		propertyService.uploadPropertyOnId(propertyId, blob , docType);
	}

	/**
	 * Download notice document of the staff
	 * 
	 * @param snId			Staff Notice Id
	 * @return          	document or get redirect to file-not-found error page 
	 * @since           	1.0
	 */
	@RequestMapping("/property/download/{propertyId}")
	public String downloadProperty(@PathVariable("propertyId") Integer propertyId,HttpServletResponse response) throws Exception {

		Property property = propertyService.find(propertyId);
		if (property.getPropertyDocument() != null) {
			try {
				response.setHeader("Content-Disposition","inline;filename=\"" + property.getProperty_No());
				OutputStream out = response.getOutputStream();
				if(StringUtils.equalsIgnoreCase(".doc", property.getPropertyDocumentType()) || StringUtils.equalsIgnoreCase(".docx", property.getPropertyDocumentType()))
					response.setContentType("application/msword");
				else if(StringUtils.equalsIgnoreCase(".xls", property.getPropertyDocumentType()) || StringUtils.equalsIgnoreCase(".xlsx", property.getPropertyDocumentType()))
					response.setContentType("application/vnd.ms-excel");
				else
					response.setContentType(property.getPropertyDocumentType());
				IOUtils.copy(property.getPropertyDocument().getBinaryStream(), out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return "filenotfound";
		}
	}


	@RequestMapping(value = "/comsettings", method = RequestMethod.GET)
	public String settingsIndex(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Settings");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manage settings", 1, request);
		return "com/settings";
	}

	/**
	 * Fetching person details for first name autocomplete field
	 * 
	 * @param personId
	 *            Person id of the person who is having this arms
	 * @param personStyle
	 *            personStyle - Individual,Company,Proprietor,LLP,Partnership
	 * @return updated medical emergency disability instance
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/getPersonDetails/{personId}/{personStyle}",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getPersonDetails(@PathVariable int personId, @PathVariable String personStyle) 
	{
		Person person = personService.find(personId);
		String table = "";
		String title = "";
		String lastName = "";
		if((person.getTitle() != null) && (person.getTitle().length() > 0))
			title = person.getTitle();
		if((person.getLastName() != null) && (person.getLastName().length() > 0))
			lastName = person.getLastName();
		String name = "";
		if(title.length() > 0)
			name = title.concat(" ");
		if(lastName.length() > 0)
			name = name.concat(lastName);
		else
			name = name.concat(person.getFirstName());
		if(personStyle.equalsIgnoreCase("Individual"))
		{
			table = "<table id=employeesTable>"
					+ " <colgroup><col class=photo /><col class=details/><col/></colgroup>"
					+ " <tbody>"
					+ "<tr>"
					+ "<td class=photo>"
					+ "<img src=./person/getpersonimage/"+person.getPersonId()+" width=150 height=200 alt=\"No Image to Display\" />"
					+ "</td>"
					+ "<td class=details>"
					+ "<span class=title>"+name+"</span>"
					+ "<span class=description><b>Category &nbsp;:&nbsp;</b><i>"+person.getPersonStyle()+"</i></span>"
					+ "<span class=description><b>Type &nbsp;:&nbsp;</b>"+person.getPersonType()+"</span>"
					+ "<span class=description><b>Date Of Birth &nbsp;&nbsp;:&nbsp;</b>"+dateTimeCalender.getDate3(person.getDob())+"</span>"
					+ "<span class=details><input type=button value=Save onclick=saveExistingId('"+person.getPersonId()+"') class=k-button style=\"height: 40px; width: 100px; margin-left: 158px; margin-top: 29px;\"/></span>"
					+ "</td>"
					+ "</tr>"
					+ "</table>";
		}
		else
		{
			table = "<table id=employeesTable>"
					+ " <colgroup><col class=photo /><col class=details/><col/></colgroup>"
					+ " <tbody>"
					+ "<tr>"
					+ "<td class=photo>"
					+ "<img src=./person/getpersonimage/"+person.getPersonId()+" width=150 height=200 alt=\"No Image to Display\" />"
					+ "</td>"
					+ "<td class=details>"
					+ "<span class=title>"+name+"</span>"
					+ "<span class=description><b>Category &nbsp;:&nbsp;</b><i>"+person.getPersonStyle()+"</i></span>"
					+ "<span class=description><b>Type &nbsp;:&nbsp;</b>"+person.getPersonType()+"</span>"
					+ "<span class=details><input type=button value=Save onclick=saveExistingId('"+person.getPersonId()+"') class=k-button style=\"height: 40px; width: 100px; margin-left: 158px; margin-top: 29px;\"/></span>"
					+ "</td>"
					+ "</tr>"
					+ "</table>";
		}
		return table;
	}

	/**
	 * Updating existing person with other person type and creating that child entry when selected from first name auto complete
	 * 
	 * @param request
	 *            Request will hold the fields data
	 * @return updated medical emergency disability instance
	 * @throws IOException 
	 * @since 0.1
	 */
	@RequestMapping(value = "/person/updateExistingPerson",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String updateExistingPerson(HttpServletRequest request) throws IOException 
	{
		String personType = request.getParameter("personType");
		int personId = Integer.parseInt(request.getParameter("personId"));
		Person person = personService.find(personId);
		String title = "";
		String lastName = "";
		if((person.getTitle() != null) && (person.getTitle().length() > 0))
			title = person.getTitle();
		if((person.getLastName() != null) && (person.getLastName().length() > 0))
			lastName = person.getLastName();
		String name = "";
		if(title.length() > 0)
			name = title.concat(" ");
		if(lastName.length() > 0)
		{
			name = name.concat(person.getFirstName());
			name = name.concat(" ");
			name = name.concat(lastName);
		}
		else
			name = name.concat(person.getFirstName());

		String[] strArr = person.getPersonType().split(",");
		List<String> list = Arrays.asList(strArr);
		if(list.contains(personType))
		{
			if(personType.equalsIgnoreCase("DomecticHelp"))
				return (name.trim()+" is already added as Domestic help");
			else
				return (name.trim()+" is already added as "+personType);
		}
		else 
		{
			if(personType.equalsIgnoreCase("Owner"))
			{
				Owner owner = new Owner();
				owner.setPersonId(personId);
				owner.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				owner.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				owner.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				owner.setOwnerStatus("Inactive");
				owner.setPerson(person);
				person.setOwner(owner);
			}
			else if(personType.equalsIgnoreCase("Tenant"))
			{
				Tenant tenant = new Tenant();
				tenant.setPersonId(personId);
				tenant.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				tenant.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				tenant.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				tenant.setTenantStatus("Inactive");
				tenant.setPerson(person);
				person.setTenant(tenant);
			}
			else if(personType.equalsIgnoreCase("Family"))
			{
				Family family = new Family();
				family.setPersonId(personId);
				family.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				family.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				family.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				family.setFamilyStatus("Inactive");
				family.setPerson(person);
				person.setFamily(family);
			}
			else if(personType.equalsIgnoreCase("DomesticHelp"))
			{
				Domestic domesticHelp = new Domestic();
				domesticHelp.setPersonId(personId);
				domesticHelp.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				domesticHelp.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
				domesticHelp.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				domesticHelp.setDomesticHelpStatus("Inactive");
				domesticHelp.setPerson(person);
				person.setDomesticHelp(domesticHelp);
			}
			person.setPersonType(person.getPersonType().concat(",").concat(personType));
			person.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
			person.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			person.setPersonStatus("Inactive");
			if(person.getWorkNature() == null)
				person.setWorkNature("None");
			if(person.getOccupation() == null)
				person.setOccupation("None");

			personService.update(person);

			if(personType.equalsIgnoreCase("DomecticHelp"))
				return (name.trim()+" is succeccfully added as Domestic help and now you can go and edit the fields if you want");
			else
				return (name.trim()+" is succeccfully added as "+personType+" and now you can go and edit the fields if you want");
		}
	}

	/**
	 * Use Case: ----------------------------------------- Manage Pets --------------------------------------------
	 */

	@RequestMapping(value = "/compets", method = RequestMethod.GET)
	public String indexPets(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Customer Occupancy");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage pets", 2, request);
		return "com/pets";
	}

	@RequestMapping(value = "/pets/read", method = RequestMethod.POST)
	public @ResponseBody Object readPets(final Locale locale,Model model) 
	{
		List<?> petsList = null;

		JsonResponse errorResponse = new JsonResponse();

		try
		{
			petsList = petsService.findAllPetsRequiredFields();
			return petsList;
		}

		catch (Exception e)
		{
			BfmLogger.logger.error("COM management module - pets use case - COM managent controller - read method");
			e.printStackTrace();

			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("readException", messageSource.getMessage(
							"exception.project.exception", null, locale));
				}
			};

			errorResponse.setStatus("READ_EXCEPTION");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		}

	}

	@RequestMapping(value = "/pets/create", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object createPets(@RequestBody Map<String, Object> map,
			@ModelAttribute("pets") Pets pets, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale)
	{
		JsonResponse errorResponse = new JsonResponse();

		try
		{

			pets = petsService.getPetObject(map, "save", pets);

			/*drGroupIdService.save(new DrGroupId(pets.getCreatedBy(), pets.getLastUpdatedDt()));
			pets.setDrGroupId(drGroupIdService.getNextVal(pets.getCreatedBy(), pets.getLastUpdatedDt()));*/

			validator.validate(pets, bindingResult);

		}

		catch (Exception e)
		{		
			BfmLogger.logger.error("COM management module - pets use case - COM managent controller - create method - loading pet object");
			e.printStackTrace();

			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("saveOrUpdateException", messageSource.getMessage(
							"exception.project.exception", null, locale));
				}
			};

			errorResponse.setStatus("SAVE_OR_UPDATE_EXCEPTION");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		}


		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: "+bindingResult);

			return errorResponse;
		}

		else 
		{
			try
			{
				petsService.save(pets);		
			}

			catch (Exception e)
			{
				BfmLogger.logger.error("COM management module - pets use case - COM managent controller - create method - saving pet object");
				e.printStackTrace();

				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("saveOrUpdateException", messageSource.getMessage(
								"exception.project.exception", null, locale));
					}
				};

				errorResponse.setStatus("SAVE_OR_UPDATE_EXCEPTION");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}

			try
			{
				pets.setPetId(petsService.getPetIdBasedOnCreatedByAndLastUpdatedDt(pets.getCreatedBy(), pets.getLastUpdatedDt()));

				sessionStatus.setComplete();
				return pets;
			}

			catch (Exception e)
			{		
				BfmLogger.logger.error("COM management module - pets use case - COM managent controller - create method - loading saved pet object");
				e.printStackTrace();

				errorResponse.setStatus("LOAD_EXCEPTION");
				return errorResponse;
			}

		}

	}

	@RequestMapping(value = "/pets/update", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object updatePets(@RequestBody Map<String, Object> map,
			@ModelAttribute("pets") Pets pets, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) throws Exception 
			{
		JsonResponse errorResponse = new JsonResponse();

		try
		{
			pets = petsService.getPetObject(map, "update", pets);
			validator.validate(pets, bindingResult);
		}

		catch (Exception e)
		{		
			BfmLogger.logger.error("COM management module - pets use case - COM managent controller - update method - loading pet object");
			e.printStackTrace();

			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("saveOrUpdateException", messageSource.getMessage(
							"exception.project.exception", null, locale));
				}
			};

			errorResponse.setStatus("SAVE_OR_UPDATE_EXCEPTION");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		}

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: "+bindingResult);

			return errorResponse;
		}

		else 
		{
			try
			{
				petsService.update(pets);	
				sessionStatus.setComplete();
				return pets;
			}

			catch (Exception e)
			{
				BfmLogger.logger.error("COM management module - pets use case - COM managent controller - update method - updating pet object");
				e.printStackTrace();

				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("saveOrUpdateException", messageSource.getMessage(
								"exception.project.exception", null, locale));
					}
				};

				errorResponse.setStatus("SAVE_OR_UPDATE_EXCEPTION");
				errorResponse.setResult(errorMapResponse);

				return errorResponse;
			}

		}
			}

	@RequestMapping(value = "/pets/getPersonListBasedOnPropertyNumbers", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersons() {
		List<OwnerProperty> propertyPersonOwnerList = petsService.findAllPropertyPersonOwnerList();
		List<TenantProperty> propertyPersonTenantList = petsService.findAllPropertyPersonTenantList();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (final OwnerProperty record : propertyPersonOwnerList) {
			result.add(new HashMap<String, Object>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					String str="";
					if(record.getOwner().getPerson().getLastName()!=null){
						str=record.getOwner().getPerson().getLastName();
					}
					put("personName", record.getOwner().getPerson().getFirstName()+ " " + str);
					put("propertyId", record.getProperty().getPropertyId());
					put("personId", record.getOwner().getPerson().getPersonId());
					put("persontType", record.getOwner().getPerson().getPersonType());
					put("persontStyle", record.getOwner().getPerson().getPersonStyle());
					put("drGroupId", record.getOwner().getPerson().getDrGroupId());

				}
			});
		}
		for (final TenantProperty record : propertyPersonTenantList) {
			result.add(new HashMap<String, Object>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					String str="";
					if(record.getTenantId().getPerson().getLastName()!=null){
						str=record.getTenantId().getPerson().getLastName();
					}
					put("personName", record.getTenantId().getPerson().getFirstName()+ " " + str);						
					put("propertyId", record.getProperty().getPropertyId());
					put("personId", record.getTenantId().getPerson().getPersonId());
					put("persontType", record.getTenantId().getPerson().getPersonType());
					put("persontStyle", record.getTenantId().getPerson().getPersonStyle());
					put("drGroupId", record.getTenantId().getPerson().getDrGroupId());
				}
			});
		}
		return result;	
	}

	@RequestMapping(value = "/pets/getPersonListForFileter", method = RequestMethod.GET)
	public @ResponseBody List<?> readPersonNames() 
	{

		List<?> accountPersonList = petsService.getPersonListForFileter();

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

	@RequestMapping(value = "/pets/petTypeChecks", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getPetType(final Locale locale) 
	{

		String[] petType = messageSource.getMessage("values.pets.petType", null, locale).split(",");

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;

		for (int i = 0; i < petType.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", petType[i]);
			map.put("name", petType[i]);

			result.add(map);
		}

		return result;

	}

	@RequestMapping(value = "/pets/petSizeChecks", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getPetSize(final Locale locale) 
	{

		String[] petSize = messageSource.getMessage("values.pets.petSize", null, locale).split(",");

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;

		for (int i = 0; i < petSize.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", petSize[i]);
			map.put("name", petSize[i]);

			result.add(map);
		}

		return result;

	}

	@RequestMapping(value = "/pets/petSexChecks", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getPetSex(final Locale locale) 
	{

		String[] petSex = messageSource.getMessage("values.pets.petSex", null, locale).split(",");

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;

		for (int i = 0; i < petSex.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", petSex[i]);
			map.put("name", petSex[i]);

			result.add(map);
		}

		return result;

	}

	@RequestMapping(value = "/pets/petStatus/{petId}/{operation}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void petStatus(@PathVariable int petId, @PathVariable String operation, HttpServletResponse response)
	{		
		if(operation.equalsIgnoreCase("activate"))
			petsService.setPetStatus(petId, operation, response);

		else
			petsService.setPetStatus(petId, operation, response);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pets/getPropertyNoList", method = RequestMethod.GET)
	public @ResponseBody List<String> getPropertyNoList() {

		Collection<Integer> intList = petsService.getPropertyNo();
		Collection<String> stringList = CollectionUtils.collect(intList, new Transformer() {  
			public Object transform(Object o) {  
				return String.valueOf((Integer) o);  
			}  
		});  

		return (List<String>) stringList;
	}

	// ****************************** Pets Filter Data methods ********************************/

	/*@RequestMapping(value = "/pets/getPetNameList", method = RequestMethod.GET)
	public @ResponseBody Set<String> getPetName() {
		return 	petsService.getAllPetName();
	}*/

	/*@RequestMapping(value = "/pets/getPetTypeList", method = RequestMethod.GET)
	public @ResponseBody List<String> getPetType() {
		return 	petsService.getAllPetType();
	}*/

	/*@RequestMapping(value = "/pets/getPetSizeList", method = RequestMethod.GET)
	public @ResponseBody List<String> getPetSize() {
		return 	petsService.getAllPetSize();
	}

	@RequestMapping(value = "/pets/getPetBreedList", method = RequestMethod.GET)
	public @ResponseBody Set<String> getPetBreed() {
		return 	petsService.getAllPetBreed();
	}

	@RequestMapping(value = "/pets/getPetColorList", method = RequestMethod.GET)
	public @ResponseBody Set<String> getPetColor() {
		return 	petsService.getAllPetColor();
	}

	@RequestMapping(value = "/pets/getPetAgeList", method = RequestMethod.GET)
	public @ResponseBody List<Integer> getPetAge() {
		return 	petsService.getAllPetAge();
	}

	@RequestMapping(value = "/pets/getPetSexList", method = RequestMethod.GET)
	public @ResponseBody List<String> getPetSex() {
		return 	petsService.getAllPetSex();
	}

	@RequestMapping(value = "/pets/getlastUpdatedBy", method = RequestMethod.GET)
	public @ResponseBody List<String> getLastUpdatedBy() {
		return 	petsService.getAllUpdatedByNames();
	}

	@RequestMapping(value = "/pets/getCreatedBy", method = RequestMethod.GET)
	public @ResponseBody List<String> getCreatedBy() {
		return 	petsService.getAllCreatedByNames(); 
	}

	@RequestMapping(value = "/pets/getVeterianNameList", method = RequestMethod.GET)
	public @ResponseBody Set<String> getVeterianName() {  
		return 	petsService.getAllVeterianame();
	}*/

	@RequestMapping(value = "/pets/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getPetsContentsForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("Pets", attributeList, null));

		return set;
	}

	@RequestMapping(value = "/pets/getBlockNames", method = RequestMethod.GET)
	public @ResponseBody Set<String> getBlockNames() {
		return 	petsService.getAllBlockNames();
	}

	@RequestMapping(value = "/pets/getPropertyNumList", method = RequestMethod.GET)
	public @ResponseBody List<String> getPropertyNumber() {
		return 	petsService.getAllPropertyNumbers();
	}

	@RequestMapping(value = "/pets/getEmergencyContact", method = RequestMethod.GET)
	public @ResponseBody Set<String> getEmergencyContact() {
		return 	petsService.getAllEmergencyContact();
	}


	/**
	 * --------------------------------------------------- End --------------------------------------------
	 */

	/**
	 * --------------------------------------------------- Manage property --------------------------------------------
	 */

	@RequestMapping(value = "/person/readTowerNames", method = RequestMethod.GET)
	public @ResponseBody Set<?> getTowerNames()
	{

		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>(); 

		List<Property> record = mailService.findPropertyNames();

		Map<String, Object> map = null;

		for (Iterator<Property> iterator = record.iterator(); iterator.hasNext();)
		{
			Property property = (Property) iterator.next();
			map = new HashMap<String, Object>();
			map.put("propertyId", property.getPropertyId());

			map.put("propertyNo", property.getProperty_No());

			//map.put("towerName", property.getTowerName());

			result.add(map);

		}
		return result;
	}

	@RequestMapping(value = "/person/getPropertyNo", method = RequestMethod.GET)
	public @ResponseBody Set<?> getPropertyNo()
	{

		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>(); 

		List<Property> record = mailService.findPropertyNames();

		Map<String, Object> map = null;

		for (Iterator<Property> iterator = record.iterator(); iterator.hasNext();)
		{
			Property property = (Property) iterator.next();
			map = new HashMap<String, Object>();
			map.put("propertyId", property.getPropertyId());

			map.put("propertyNo", property.getProperty_No());
			map.put("blockId", property.getBlockId());

			//map.put("towerName", property.getTowerName());

			result.add(map);

		}
		return result;
	}

	/**
	 * --------------------------------------------------- End --------------------------------------------
	 */

	@RequestMapping(value="/createPersonDashboard",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String createPerson(@ModelAttribute("personObject") Person person,BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale)
	{
		if(bindingResult.hasErrors())
		{
		}
		return null;
	}

	/**
	 * Method to approve the documents of Owner,Tenant,DomesticHelp,Staff,Family
	 * @param personId
	 * @param personType
	 * @param kycCompliant
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/documents/approve/{personId}/{personType}/{kycCompliant}")
	@ResponseBody
	public String acceptOrReject(@PathVariable int personId,@PathVariable String personType,@PathVariable String kycCompliant,HttpServletRequest req,HttpServletResponse res)
	{
		if(kycCompliant.equalsIgnoreCase("null"))
		{
			kycCompliant = personType;
		}
		else
		{
			kycCompliant = kycCompliant +","+personType;
		}
		String status = personService.updateKycCompliant(kycCompliant,personId);
		int groupId = personService.getGroupIdBasedOnPersonId(personId);
		documentRepoService.updateApprovedStatus(groupId);
		return status;
	}

	@RequestMapping(value="/documents/checkFile/{groupId}/{personType}")
	@ResponseBody
	public void checkFile(@PathVariable int groupId,@PathVariable String personType,HttpServletRequest req,HttpServletResponse res)
	{
		PrintWriter out;
		boolean fileExist = true;
		//DocumentRepository documentRepository = documentRepoService.getDocumentRepositoryObjectOnGroupId(groupId);
		for (DocumentRepository record: documentRepoService.getDocumentRepositoryObjectOnGroupId(groupId ,personType ))
		{
			if( record.getDocumentFile() == null ){
				fileExist = false;
				break;
			}
			if( record.getDocumentFile() != null ){
				fileExist = true;
			}
		}
		if( fileExist == false ){
			try {
				out = res.getWriter();
				out.write("FILEENOTEXISTS");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	@SuppressWarnings("unused")
	@RequestMapping(value="/documents/checkAllDocument/{groupId}/{personType}")
	@ResponseBody
	public void checkAllDocument(@PathVariable int groupId,@PathVariable String personType,HttpServletRequest req,HttpServletResponse res)
	{
		PrintWriter out;
		boolean fileExist = false;
		List<DocumentRepository> documentRepository = documentRepoService.getDocumentRepositoryObjectOnGroupId(groupId ,personType );
		List<DocumentDefiner> documentDefiner = documentDefinerService.getAllBasedOnTypeAndCondition(personType,"No");

		if( documentRepository.size() != documentDefiner.size() ){
			try {
				out = res.getWriter();
				out.write("NOTAPPROVED");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/documents/documentCheckForArms/{groupId}/{personType}")
	@ResponseBody
	public void documentCheckForArms(@PathVariable int groupId,@PathVariable String personType,HttpServletRequest req,HttpServletResponse res)
	{

		PrintWriter out;
		boolean status = true;

		final String queryString = "SELECT dr FROM DocumentRepository dr WHERE dr.drGroupId = "+groupId+" AND dr.documentType='"+personType+"' AND dr.documentName='Arms Approval Document'";
		DocumentRepository record = documentRepoService.getSingleResult(queryString);
		if( record == null ){
			try {
				out = res.getWriter();
				out.write("ARMSDOCUMENTNOTFOUND");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			List<DocumentRepository> documentRepository = documentRepoService.getDocumentRepositoryObject(groupId ,personType );
			for (Iterator iterator = documentRepository.iterator(); iterator
					.hasNext();) {
				DocumentRepository documentRepository2 = (DocumentRepository) iterator
						.next();
				if( documentRepository2.getDocumentType().equalsIgnoreCase(personType) && documentRepository2.getApproved().equalsIgnoreCase("No") && documentRepository2.getDocumentName().equalsIgnoreCase("Arms Approval Document")){
					status = false;
					break;
				}
				else
					status = true;
			}
			if( status == false){
				try {
					out = res.getWriter();
					out.write("ARMSNOTAPPROVED");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}


	@SuppressWarnings({ "rawtypes", "unused", "serial" })
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/ownerPersonAdderssContact/uploadExcelData", method ={ RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object importExcelDataForOwners(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException, java.text.ParseException {	

		JsonResponse errorResponse = new JsonResponse();
		logger.info("Inside Excel Upload Data");
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userId");
		String fileExtention="";
		logger.info("************File Name**************"+files.getOriginalFilename());

		if(files.getOriginalFilename().lastIndexOf(".") != -1 && files.getOriginalFilename().lastIndexOf(".") != 0){

			logger.info("**********File extention********"+files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".")+1));
			fileExtention=files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".")+1);

			if(!("xls".equalsIgnoreCase(fileExtention)) && !("xlsx".equalsIgnoreCase(fileExtention))){
				throw new Error("File is not valid");
			}
		}

		int i=0;		
		int j=0;
		int k=0;

		List<OwnerProperty> ownerPropertyList = new ArrayList<OwnerProperty>();		
		List<Person> personList = new ArrayList<Person>();		
		List<Address> addressList = new ArrayList<Address>();		
		List<Contact> contactList1 = new ArrayList<Contact>();		
		List<Owner> ownerList = new ArrayList<Owner>();
		List<Contact> contactList2 = new ArrayList<Contact>();		
		List<Country> countryLists = new ArrayList<Country>();		
		List<State> stateLists = new ArrayList<State>();		
		List<City> cityLists = new ArrayList<City>();		

		if("xls".equalsIgnoreCase(fileExtention)){

			HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream());
			HSSFSheet  sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();			


			while(rows.hasNext())
			{
				final HSSFRow  row = ((HSSFRow ) rows.next());

				logger.info("*****ROW COUNT*****"+row.getRowNum());
				Iterator cells = row.cellIterator();
				Person person=new Person();
				OwnerProperty ownerProperty = new OwnerProperty();
				Address address=new  Address();
				Contact contact1=new Contact();
				Contact contact2=new Contact();
				Country country=new Country();
				State state=new State();
				City city=new City();

				if(row.getRowNum()==0){
					continue; //just skip the rows if row number is 0 or 1
				}


				if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
					logger.info("*****Block Name*******"+row.getCell(0).getStringCellValue());
					String blockName=row.getCell(0).getStringCellValue();
					List<Blocks> blockList = blocksService.executeSimpleQuery("SELECT b FROM Blocks b WHERE b.blockName = '"+blockName+"'");

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Block Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 1");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}	   

				if(!StringUtils.isEmpty(row.getCell(1).getStringCellValue())){

					final String propertyNo=row.getCell(1).getStringCellValue();

					List<Property> proList = propertyService.getPropertyListBasedOnPropertyNo(propertyNo.trim());

					if(proList.isEmpty()){

						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Given Property No:"+propertyNo+" does n't exist at Excel Row No :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
					else{
						logger.info("***********Property Id*************"+proList.get(0).getPropertyId());
						int propertyId=proList.get(0).getPropertyId();
						ownerProperty.setPropertyId(propertyId);
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Property Number cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 2");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					logger.info("***value Of i****"+i);
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(2).getStringCellValue())){
					logger.info("*****Primary Owner*******"+row.getCell(2).getStringCellValue());


					if("Yes".equalsIgnoreCase(row.getCell(2).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(2).getStringCellValue())) {

						//ownerProperty.setPrimaryOwner(row.getCell(2).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Primary Owner Type is not matched(Allowable Values are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 3");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Primary Owner Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 3");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(3).getStringCellValue())){
					logger.info("*****Occupied*******"+row.getCell(3).getStringCellValue());

					if("Yes".equalsIgnoreCase(row.getCell(3).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(3).getStringCellValue())) {

						ownerProperty.setResidential(row.getCell(3).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Occupied Colum Type is not matched(Allowable Values are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 4");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;


					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Occupied Column cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 4");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				String dateVal = String.valueOf(row.getCell(4).getDateCellValue());
				logger.info("*****date Value ****"+dateVal);
				if(!("null".equalsIgnoreCase(dateVal.trim())) && !("".equalsIgnoreCase(dateVal.trim()))){

					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date date1 = (Date)formatter.parse(dateVal);

					java.sql.Date propertyAcquiredDate=new java.sql.Date(date1.getTime());
					ownerProperty.setPropertyAquiredDate(propertyAcquiredDate);

				}

				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Property Acquired Date cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 5");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/
				if(!StringUtils.isEmpty(row.getCell(5).getStringCellValue())){
					logger.info("*****Visitor Registration Required*******"+row.getCell(5).getStringCellValue());
					if("Yes".equalsIgnoreCase(row.getCell(5).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(5).getStringCellValue())) {

						ownerProperty.setVisitorRegReq(row.getCell(5).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Visitor Registration Column Type is not matched(Allowable Values are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 6");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Visitor Registration cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 6");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}


				if(!StringUtils.isEmpty(row.getCell(6).getStringCellValue())){
					logger.info("*****Owner Type*******"+row.getCell(6).getStringCellValue());
					if("Individual".equalsIgnoreCase(row.getCell(6).getStringCellValue()) || "Company".equalsIgnoreCase(row.getCell(6).getStringCellValue())) {

						person.setPersonType("Owner");
						person.setPersonStyle(row.getCell(6).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Owner Type is not matched(Allowable Values are: Individual/Company) at Excel Row No :"+(row.getRowNum()) +"& Column No 7");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Owner Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 7");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(7).getStringCellValue())){
					logger.info("*****Title*******"+row.getCell(7).getStringCellValue());
					if("Mr".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "M/S".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "Mrs".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "Ms".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "Miss".equalsIgnoreCase(row.getCell(7).getStringCellValue())) {

						person.setTitle(row.getCell(7).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Title Type is not matched(Allowable Values are: Mr,M/S,Mrs,Ms and Miss) at Excel Row No :"+(row.getRowNum()) +"& Column No 8");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Title cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 8");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(8).getStringCellValue())){
					logger.info("*****First Name*******"+row.getCell(8).getStringCellValue());


					if(row.getCell(8).getStringCellValue().matches("[a-zA-Z ]{3,30}$")) {
						person.setFirstName(row.getCell(8).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "First Name(it Contains only Characters & not allows spaces) is mismatched at Excel Row No :"+(row.getRowNum()) +"& Column No 9");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
					java.util.Date date= new java.util.Date();

					logger.info("*****Created By Name*******"+userName);
					logger.info("*****Updated Date*******"+new Timestamp(date.getTime()));

					drGroupIdService.save(new DrGroupId(userName, new Timestamp(date.getTime())));
					person.setDrGroupId(drGroupIdService.getNextVal(userName, new Timestamp(date.getTime())));
					person.setCreatedBy(userName);
					person.setLastUpdatedBy(userName);
					person.setLastUpdatedDt(new Timestamp(date.getTime()));

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:First Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 9");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}
				logger.info("*************Middle Name*************"+row.getCell(9));
				if(!StringUtils.isEmpty(row.getCell(9).getStringCellValue())){
					logger.info("*****Middle Name*******"+row.getCell(9).getStringCellValue());
					person.setMiddleName(row.getCell(9).getStringCellValue().trim());
				}


				if(!StringUtils.isEmpty(row.getCell(10).getStringCellValue())){
					logger.info("*****Last Name*******"+row.getCell(10).getStringCellValue());


					if(row.getCell(10).getStringCellValue().matches("[a-zA-Z ]{1,30}$")) {

						person.setLastName(row.getCell(10).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Last Name Column(it Contains only Characters & not allows spaces) is mismatched at Excel Row No :"+(row.getRowNum()) +"& Column No 11");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Last Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 11");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(11).getStringCellValue())){
					logger.info("*****Father Name*******"+row.getCell(11).getStringCellValue());


					if(row.getCell(11).getStringCellValue().matches("[a-zA-Z& ]{3,55}$")) {
						person.setFatherName(row.getCell(11).getStringCellValue());

					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Father Name Column(it Contains only Characters min 3 & it allows spaces) is mismatched at Excel Row No :"+(row.getRowNum()) +"& Column No 12");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Father Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 12");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(12).getStringCellValue())){
					logger.info("*****Maritial Status*******"+row.getCell(12).getStringCellValue());
					if("Single".equalsIgnoreCase(row.getCell(12).getStringCellValue()) || "Married".equalsIgnoreCase(row.getCell(12).getStringCellValue()) || "Divorcee".equalsIgnoreCase(row.getCell(12).getStringCellValue()) || "Widow".equalsIgnoreCase(row.getCell(12).getStringCellValue())) {

						person.setMaritalStatus(row.getCell(12).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Maritial Status Column Type is not matched(Allowable Values are: Single/Married/Divorcee/Widow) at Excel Row No :"+(row.getRowNum()) +"& Column No 13");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Maritial Status cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 13");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}
				if(!StringUtils.isEmpty(row.getCell(13).getStringCellValue())){
					logger.info("*****Spouse Name*******"+row.getCell(13).getStringCellValue());
					person.setSpouseName(row.getCell(13).getStringCellValue().trim());
				}

				if(!StringUtils.isEmpty(row.getCell(14).getStringCellValue())){
					logger.info("***** SEX *******"+row.getCell(14).getStringCellValue());
					if("Male".equalsIgnoreCase(row.getCell(14).getStringCellValue()) || "Female".equalsIgnoreCase(row.getCell(14).getStringCellValue())) {

						person.setSex(row.getCell(14).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "SEX Column Type is not matched(Allowable Values are: Male/Female) at Excel Row No :"+(row.getRowNum()) +"& Column No 15");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:SEX Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 15");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				String dateOfBirth = String.valueOf(row.getCell(15).getDateCellValue());
				logger.info("*****dateOfBirth Value ****"+dateOfBirth);
				if(!("null".equalsIgnoreCase(dateOfBirth.trim())) && !("".equalsIgnoreCase(dateOfBirth.trim()))){

					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date dob = (Date)formatter.parse(dateOfBirth);

					logger.info("*****Date Of Birth*******"+row.getCell(15).getDateCellValue().toString());
					person.setDob(dob);
				}

				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Date of Birth cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 16");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/
				if(!StringUtils.isEmpty(row.getCell(16).getStringCellValue())){
					logger.info("***** Nationality *******"+row.getCell(16).getStringCellValue());


					if(row.getCell(16).getStringCellValue().matches("[a-zA-Z& ]{3,55}$")) {

						person.setNationality(row.getCell(16).getStringCellValue().trim().toUpperCase());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Nationalty Column is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 17,it allows spaces & min 3 characters");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break; 
					}

				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Nationality cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 17");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/
				if(!StringUtils.isEmpty(row.getCell(17).getStringCellValue())){
					logger.info("***** Blood Group *******"+row.getCell(17).getStringCellValue());
					person.setBloodGroup(row.getCell(17).getStringCellValue().trim());
				}


				if(!StringUtils.isEmpty(row.getCell(18).getStringCellValue())){
					logger.info("***** Occupation *******"+row.getCell(18).getStringCellValue());


					if(row.getCell(18).getStringCellValue().matches("[a-zA-Z& ]{3,55}$")) {

						person.setOccupation(row.getCell(18).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Occupation Colum Type is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 19,it allows spaces only");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Occupation cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 19");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/

				if(!StringUtils.isEmpty(row.getCell(19).getStringCellValue())){
					logger.info("***** Work Nature *******"+row.getCell(19).getStringCellValue());
					if("Private".equalsIgnoreCase(row.getCell(19).getStringCellValue()) || "Government".equalsIgnoreCase(row.getCell(19).getStringCellValue()) || "Semi-Government".equalsIgnoreCase(row.getCell(19).getStringCellValue()) || "PSU".equalsIgnoreCase(row.getCell(19).getStringCellValue())) {

						person.setWorkNature(row.getCell(19).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "WorkNature Colum Type is not matched(Allowable Values are:Private/Government/Semi-Government/PSU) at Excel Row No :"+(row.getRowNum()) +"& Column No 20");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Work Nature cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 20");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/

				if(!StringUtils.isEmpty(row.getCell(20).getStringCellValue())){
					logger.info("***** Languages Known *******"+row.getCell(20).getStringCellValue());
					person.setLanguagesKnown(row.getCell(20).getStringCellValue().trim());
				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Languages Column cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 21");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/


				if(!StringUtils.isEmpty(row.getCell(21).getStringCellValue())){
					logger.info("***** Address Type *******"+row.getCell(21).getStringCellValue());
					if("Home".equalsIgnoreCase(row.getCell(21).getStringCellValue()) || "Office".equalsIgnoreCase(row.getCell(21).getStringCellValue())) {

						address.setAddressLocation(row.getCell(21).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Address Type Colum is not matched(Allowable Values Are: Home/Office) at Excel Row No :"+(row.getRowNum()) +"& Column No 22");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 22");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(22).getStringCellValue())){
					logger.info("***** Address Primary *******"+row.getCell(22).getStringCellValue());
					if("Yes".equalsIgnoreCase(row.getCell(22).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(22).getStringCellValue())) {

						address.setAddressPrimary(row.getCell(22).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Address Primary Colum Type is not matched(Allowable Values Are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 23");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address Primary cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 23");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(23).getStringCellValue())){
					logger.info("***** Address No*******"+row.getCell(23).getStringCellValue());
					address.setAddressNo(row.getCell(23).getStringCellValue());
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address No cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 24");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(24).getStringCellValue())){
					logger.info("***** Address1 *******"+row.getCell(24).getStringCellValue());
					address.setAddress1(row.getCell(24).getStringCellValue());
					address.setCreatedBy(userName);

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address1 cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 25");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(25).getStringCellValue())){
					logger.info("***** Address2 *******"+row.getCell(25).getStringCellValue());
					address.setAddress2(row.getCell(25).getStringCellValue());
				}


				if(!StringUtils.isEmpty(row.getCell(26).getStringCellValue())){
					logger.info("***** Address3 *******"+row.getCell(26).getStringCellValue());
					address.setAddress3(row.getCell(26).getStringCellValue());
				}


				if(!StringUtils.isEmpty(row.getCell(27).getStringCellValue())){
					logger.info("***** Country Name*******"+WordUtils.capitalizeFully(row.getCell(27).getStringCellValue()));
					country.setCountryName(WordUtils.capitalizeFully(row.getCell(27).getStringCellValue()));
				}
				else{

					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Country Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 28");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}

				if(!StringUtils.isEmpty(row.getCell(28).getStringCellValue())){
					logger.info("***** State Name*******"+WordUtils.capitalizeFully(row.getCell(28).getStringCellValue()));
					state.setStateName(WordUtils.capitalizeFully(row.getCell(28).getStringCellValue()));
				}
				else{

					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:State Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 29");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}

				if(!StringUtils.isEmpty(row.getCell(29).getStringCellValue())){
					logger.info("***** City *******"+WordUtils.capitalizeFully(row.getCell(29).getStringCellValue()));
					String cityName=WordUtils.capitalizeFully(row.getCell(29).getStringCellValue());

					/*List<City> cityList = cityService.executeSimpleQuery("SELECT c FROM City c WHERE c.cityName = '"+cityName+"'");
				    		    	int stateId=cityList.get(0).getStateId();
				                	logger.info("***********State Id**********"+stateId);

				    		    	List<State> stateList = stateService.executeSimpleQuery("SELECT s FROM State s WHERE s.stateId = '"+stateId+"'");
				    		    	int countryId=stateList.get(0).getCountryId();
				                	logger.info("***********Country Id**********"+countryId);
				    		    	address.setCityId(cityList.get(0).getCityId());*/
					city.setCityName(cityName);



				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:City Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 30");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}

				if(!(String.valueOf(row.getCell(30)).equalsIgnoreCase(null)) && !(String.valueOf(row.getCell(30)).equalsIgnoreCase(""))){

					if(String.valueOf(row.getCell(30)).length()>3){
						logger.info("***** Pincode Before *******"+row.getCell(30));
						String pinCodeVal=String.valueOf(row.getCell(30));
						/*logger.info("***** Pincode After*******"+pinCodeVal.substring(0, (pinCodeVal.length()-2)));*/

						/*logger.info("***** Pincode Checking*******"+pinCodeVal.matches("[0-9]{6}$"));*/
						if((pinCodeVal.length()>3)){
							BigDecimal b1;
							b1 = new BigDecimal(pinCodeVal);
							int pincode = b1.intValue();
							address.setPincode(pincode);

						}
						else{

							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Pincode Column Type(allows 6 Numbers) is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 31");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							j=1;
							break;	
						}
					}
					else
					{

						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Pincode is not Valid");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;	

					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Pincode cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 31");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}


				if(!StringUtils.isEmpty(row.getCell(31).getStringCellValue())){
					logger.info("***** Email *******"+row.getCell(31).getStringCellValue());

					if(row.getCell(31).getStringCellValue().contains("@")){
						contact1.setContactContent(row.getCell(31).getStringCellValue());
						contact1.setContactType("Email");
						contact1.setContactPrimary("Yes");
					}
					else
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Email Column Type is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 32");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Email Address cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 32");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}

				if(!(String.valueOf(row.getCell(32)).equalsIgnoreCase(null)) && !(String.valueOf(row.getCell(32)).equalsIgnoreCase(""))){

					BigDecimal b1;

					if((String.valueOf(row.getCell(32)).trim().length()>20)){
						logger.info("***** Mobile No Length *******"+String.valueOf(row.getCell(32)).length());
						logger.info("***** Mobile No  After*******"+(String.valueOf(row.getCell(32)).trim().substring(0, String.valueOf(row.getCell(32)).length()-2)));


						/*logger.info("***** MobileNo Checking*******"+String.valueOf(row.getCell(32)).trim().substring(0, (String.valueOf(row.getCell(32)).length()-2)).replace(".", "").matches("^[0-9]{10}$"));*/
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Mobile No Column(allows upto 15 Numbers) is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 33");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;	
					}

					else{
						b1 = new BigDecimal(row.getCell(32).getNumericCellValue());
						contact2.setContactContent(b1.toString());
						contact2.setContactType("Mobile");
						contact2.setContactPrimary("Yes");
					}

				}

				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Mobile Number cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 33");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}



				if(!StringUtils.isEmpty(row.getCell(33).getStringCellValue())){
					logger.info("***** Seasonal *******"+row.getCell(33).getStringCellValue());
					if("Yes".equalsIgnoreCase(row.getCell(33).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(33).getStringCellValue())) {

						address.setAddressSeason((row.getCell(33).getStringCellValue()));
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Seasonal Colum Type is not matched(Allowable Values are:Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 34");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Seasonal Column cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 34");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}

				String seasonFrom = String.valueOf(row.getCell(34).getDateCellValue());
				logger.info("*****seasonFrom Value ****"+seasonFrom);
				if(!("null".equalsIgnoreCase(seasonFrom.trim())) && !("".equalsIgnoreCase(seasonFrom.trim()))){


					logger.info("***** Seasonal From*******"+row.getCell(34).getDateCellValue().toString());
					address.setAddressSeasonFrom((ConvertDate.formattedDate(seasonFrom)));
				}


				String seasonTo = String.valueOf(row.getCell(35).getDateCellValue());
				logger.info("*****seasonTo Value ****"+seasonTo);
				if(!("null".equalsIgnoreCase(seasonTo.trim())) && !("".equalsIgnoreCase(seasonTo.trim()))){

					logger.info("***** Seasonal To*******"+row.getCell(35).getDateCellValue().toString());
					address.setAddressSeasonTo((ConvertDate.formattedDate(seasonTo)));
				}

				ownerPropertyList.add(ownerProperty);
				personList.add(person);
				addressList.add(address);
				contactList1.add(contact1);
				contactList2.add(contact2);
				countryLists.add(country);
				stateLists.add(state);
				cityLists.add(city);


				logger.info("********Value of i********"+i);
			}
			logger.info("********Value of i********"+i);

		}	

		if("xlsx".equalsIgnoreCase(fileExtention)){

			XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();			


			while(rows.hasNext())
			{
				final XSSFRow row = ((XSSFRow) rows.next());

				logger.info("*****ROW COUNT*****"+row.getRowNum());
				Iterator cells = row.cellIterator();
				Person person=new Person();
				OwnerProperty ownerProperty = new OwnerProperty();
				Address address=new  Address();
				Contact contact1=new Contact();
				Contact contact2=new Contact();
				Country country=new Country();
				State state=new State();
				City city=new City();

				if(row.getRowNum()==0){
					continue; //just skip the rows if row number is 0 or 1
				}


				if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
					logger.info("*****Block Name*******"+row.getCell(0).getStringCellValue());
					String blockName=row.getCell(0).getStringCellValue();
					List<Blocks> blockList = blocksService.executeSimpleQuery("SELECT b FROM Blocks b WHERE b.blockName = '"+blockName+"'");

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Block Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 1");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}	   

				if(!StringUtils.isEmpty(row.getCell(1).getStringCellValue())){

					final String propertyNo=row.getCell(1).getStringCellValue();

					List<Property> proList = propertyService.getPropertyListBasedOnPropertyNo(propertyNo.trim());

					if(proList.isEmpty()){

						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Given Property No:"+propertyNo+" does n't exist at Excel Row No :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
					else{
						logger.info("***********Property Id*************"+proList.get(0).getPropertyId());
						int propertyId=proList.get(0).getPropertyId();
						ownerProperty.setPropertyId(propertyId);
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Property Number cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 2");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					logger.info("***value Of i****"+i);
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(2).getStringCellValue())){
					logger.info("*****Primary Owner*******"+row.getCell(2).getStringCellValue());


					if("Yes".equalsIgnoreCase(row.getCell(2).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(2).getStringCellValue())) {

						//ownerProperty.setPrimaryOwner(row.getCell(2).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Primary Owner Type is not matched(Allowable Values are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 3");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Primary Owner Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 3");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(3).getStringCellValue())){
					logger.info("*****Occupied*******"+row.getCell(3).getStringCellValue());

					if("Yes".equalsIgnoreCase(row.getCell(3).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(3).getStringCellValue())) {

						ownerProperty.setResidential(row.getCell(3).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Occupied Colum Type is not matched(Allowable Values are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 4");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;


					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Occupied Column cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 4");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				String dateVal = String.valueOf(row.getCell(4).getDateCellValue());
				logger.info("*****date Value ****"+dateVal);
				if(!("null".equalsIgnoreCase(dateVal.trim())) && !("".equalsIgnoreCase(dateVal.trim()))){

					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date date1 = (Date)formatter.parse(dateVal);

					java.sql.Date propertyAcquiredDate=new java.sql.Date(date1.getTime());
					ownerProperty.setPropertyAquiredDate(propertyAcquiredDate);

				}

				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Property Acquired Date cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 5");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/
				if(!StringUtils.isEmpty(row.getCell(5).getStringCellValue())){
					logger.info("*****Visitor Registration Required*******"+row.getCell(5).getStringCellValue());
					if("Yes".equalsIgnoreCase(row.getCell(5).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(5).getStringCellValue())) {

						ownerProperty.setVisitorRegReq(row.getCell(5).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Visitor Registration Column Type is not matched(Allowable Values are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 6");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Visitor Registration cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 6");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}


				if(!StringUtils.isEmpty(row.getCell(6).getStringCellValue())){
					logger.info("*****Owner Type*******"+row.getCell(6).getStringCellValue());
					if("Individual".equalsIgnoreCase(row.getCell(6).getStringCellValue()) || "Company".equalsIgnoreCase(row.getCell(6).getStringCellValue())) {

						person.setPersonType("Owner");
						person.setPersonStyle(row.getCell(6).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Owner Type is not matched(Allowable Values are: Individual/Company) at Excel Row No :"+(row.getRowNum()) +"& Column No 7");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Owner Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 7");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(7).getStringCellValue())){
					logger.info("*****Title*******"+row.getCell(7).getStringCellValue());
					if("Mr".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "M/S".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "Mrs".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "Ms".equalsIgnoreCase(row.getCell(7).getStringCellValue()) || "Miss".equalsIgnoreCase(row.getCell(7).getStringCellValue())) {

						person.setTitle(row.getCell(7).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Title Type is not matched(Allowable Values are: Mr,M/S,Mrs,Ms and Miss) at Excel Row No :"+(row.getRowNum()) +"& Column No 8");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Title cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 8");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(8).getStringCellValue())){
					logger.info("*****First Name*******"+row.getCell(8).getStringCellValue());


					if(row.getCell(8).getStringCellValue().matches("[a-zA-Z ]{3,30}$")) {
						person.setFirstName(row.getCell(8).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "First Name(it Contains only Characters & not allows spaces) is mismatched at Excel Row No :"+(row.getRowNum()) +"& Column No 9");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
					java.util.Date date= new java.util.Date();

					logger.info("*****Created By Name*******"+userName);
					logger.info("*****Updated Date*******"+new Timestamp(date.getTime()));

					drGroupIdService.save(new DrGroupId(userName, new Timestamp(date.getTime())));
					person.setDrGroupId(drGroupIdService.getNextVal(userName, new Timestamp(date.getTime())));
					person.setCreatedBy(userName);
					person.setLastUpdatedBy(userName);
					person.setLastUpdatedDt(new Timestamp(date.getTime()));

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:First Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 9");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}
				logger.info("*************Middle Name*************"+row.getCell(9));
				if(!StringUtils.isEmpty(row.getCell(9).getStringCellValue())){
					logger.info("*****Middle Name*******"+row.getCell(9).getStringCellValue());
					person.setMiddleName(row.getCell(9).getStringCellValue().trim());
				}


				if(!StringUtils.isEmpty(row.getCell(10).getStringCellValue())){
					logger.info("*****Last Name*******"+row.getCell(10).getStringCellValue());


					if(row.getCell(10).getStringCellValue().matches("[a-zA-Z ]{1,30}$")) {

						person.setLastName(row.getCell(10).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Last Name Column(it Contains only Characters & not allows spaces) is mismatched at Excel Row No :"+(row.getRowNum()) +"& Column No 11");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Last Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 11");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(11).getStringCellValue())){
					logger.info("*****Father Name*******"+row.getCell(11).getStringCellValue());


					if(row.getCell(11).getStringCellValue().matches("[a-zA-Z& ]{3,55}$")) {
						person.setFatherName(row.getCell(11).getStringCellValue());

					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Father Name Column(it Contains only Characters min 3 & it allows spaces) is mismatched at Excel Row No :"+(row.getRowNum()) +"& Column No 12");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Father Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 12");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(12).getStringCellValue())){
					logger.info("*****Maritial Status*******"+row.getCell(12).getStringCellValue());
					if("Single".equalsIgnoreCase(row.getCell(12).getStringCellValue()) || "Married".equalsIgnoreCase(row.getCell(12).getStringCellValue()) || "Divorcee".equalsIgnoreCase(row.getCell(12).getStringCellValue()) || "Widow".equalsIgnoreCase(row.getCell(12).getStringCellValue())) {

						person.setMaritalStatus(row.getCell(12).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Maritial Status Column Type is not matched(Allowable Values are: Single/Married/Divorcee/Widow) at Excel Row No :"+(row.getRowNum()) +"& Column No 13");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Maritial Status cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 13");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}
				if(!StringUtils.isEmpty(row.getCell(13).getStringCellValue())){
					logger.info("*****Spouse Name*******"+row.getCell(13).getStringCellValue());
					person.setSpouseName(row.getCell(13).getStringCellValue().trim());
				}

				if(!StringUtils.isEmpty(row.getCell(14).getStringCellValue())){
					logger.info("***** SEX *******"+row.getCell(14).getStringCellValue());
					if("Male".equalsIgnoreCase(row.getCell(14).getStringCellValue()) || "Female".equalsIgnoreCase(row.getCell(14).getStringCellValue())) {

						person.setSex(row.getCell(14).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "SEX Column Type is not matched(Allowable Values are: Male/Female) at Excel Row No :"+(row.getRowNum()) +"& Column No 15");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:SEX Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 15");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				String dateOfBirth = String.valueOf(row.getCell(15).getDateCellValue());
				logger.info("*****dateOfBirth Value ****"+dateOfBirth);
				if(!("null".equalsIgnoreCase(dateOfBirth.trim())) && !("".equalsIgnoreCase(dateOfBirth.trim()))){

					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date dob = (Date)formatter.parse(dateOfBirth);

					logger.info("*****Date Of Birth*******"+row.getCell(15).getDateCellValue().toString());
					person.setDob(dob);
				}

				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Date of Birth cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 16");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/
				if(!StringUtils.isEmpty(row.getCell(16).getStringCellValue())){
					logger.info("***** Nationality *******"+row.getCell(16).getStringCellValue());


					if(row.getCell(16).getStringCellValue().matches("[a-zA-Z& ]{3,55}$")) {

						person.setNationality(row.getCell(16).getStringCellValue().trim().toUpperCase());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Nationalty Column is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 17,it allows spaces & min 3 characters");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break; 
					}

				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Nationality cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 17");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/
				if(!StringUtils.isEmpty(row.getCell(17).getStringCellValue())){
					logger.info("***** Blood Group *******"+row.getCell(17).getStringCellValue());
					person.setBloodGroup(row.getCell(17).getStringCellValue().trim());
				}


				if(!StringUtils.isEmpty(row.getCell(18).getStringCellValue())){
					logger.info("***** Occupation *******"+row.getCell(18).getStringCellValue());


					if(row.getCell(18).getStringCellValue().matches("[a-zA-Z& ]{3,55}$")) {

						person.setOccupation(row.getCell(18).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Occupation Colum Type is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 19,it allows spaces only");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Occupation cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 19");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/

				if(!StringUtils.isEmpty(row.getCell(19).getStringCellValue())){
					logger.info("***** Work Nature *******"+row.getCell(19).getStringCellValue());
					if("Private".equalsIgnoreCase(row.getCell(19).getStringCellValue()) || "Government".equalsIgnoreCase(row.getCell(19).getStringCellValue()) || "Semi-Government".equalsIgnoreCase(row.getCell(19).getStringCellValue()) || "PSU".equalsIgnoreCase(row.getCell(19).getStringCellValue())) {

						person.setWorkNature(row.getCell(19).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "WorkNature Colum Type is not matched(Allowable Values are:Private/Government/Semi-Government/PSU) at Excel Row No :"+(row.getRowNum()) +"& Column No 20");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Work Nature cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 20");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/

				if(!StringUtils.isEmpty(row.getCell(20).getStringCellValue())){
					logger.info("***** Languages Known *******"+row.getCell(20).getStringCellValue());
					person.setLanguagesKnown(row.getCell(20).getStringCellValue().trim());
				}
				/*else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Languages Column cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 21");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}*/


				if(!StringUtils.isEmpty(row.getCell(21).getStringCellValue())){
					logger.info("***** Address Type *******"+row.getCell(21).getStringCellValue());
					if("Home".equalsIgnoreCase(row.getCell(21).getStringCellValue()) || "Office".equalsIgnoreCase(row.getCell(21).getStringCellValue())) {

						address.setAddressLocation(row.getCell(21).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Address Type Colum is not matched(Allowable Values Are: Home/Office) at Excel Row No :"+(row.getRowNum()) +"& Column No 22");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address Type cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 22");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(22).getStringCellValue())){
					logger.info("***** Address Primary *******"+row.getCell(22).getStringCellValue());
					if("Yes".equalsIgnoreCase(row.getCell(22).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(22).getStringCellValue())) {

						address.setAddressPrimary(row.getCell(22).getStringCellValue().trim());
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Address Primary Colum Type is not matched(Allowable Values Are: Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 23");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address Primary cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 23");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(23).getStringCellValue())){
					logger.info("***** Address No*******"+row.getCell(23).getStringCellValue());
					address.setAddressNo(row.getCell(23).getStringCellValue());
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address No cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 24");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(24).getStringCellValue())){
					logger.info("***** Address1 *******"+row.getCell(24).getStringCellValue());
					address.setAddress1(row.getCell(24).getStringCellValue());
					address.setCreatedBy(userName);

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Address1 cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 25");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;

				}

				if(!StringUtils.isEmpty(row.getCell(25).getStringCellValue())){
					logger.info("***** Address2 *******"+row.getCell(25).getStringCellValue());
					address.setAddress2(row.getCell(25).getStringCellValue());
				}


				if(!StringUtils.isEmpty(row.getCell(26).getStringCellValue())){
					logger.info("***** Address3 *******"+row.getCell(26).getStringCellValue());
					address.setAddress3(row.getCell(26).getStringCellValue());
				}


				if(!StringUtils.isEmpty(row.getCell(27).getStringCellValue())){
					logger.info("***** Country Name*******"+WordUtils.capitalizeFully(row.getCell(27).getStringCellValue()));
					country.setCountryName(WordUtils.capitalizeFully(row.getCell(27).getStringCellValue()));
				}
				else{

					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Country Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 28");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}

				if(!StringUtils.isEmpty(row.getCell(28).getStringCellValue())){
					logger.info("***** State Name*******"+WordUtils.capitalizeFully(row.getCell(28).getStringCellValue()));
					state.setStateName(WordUtils.capitalizeFully(row.getCell(28).getStringCellValue()));
				}
				else{

					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:State Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 29");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}

				if(!StringUtils.isEmpty(row.getCell(29).getStringCellValue())){
					logger.info("***** City *******"+WordUtils.capitalizeFully(row.getCell(29).getStringCellValue()));
					String cityName=WordUtils.capitalizeFully(row.getCell(29).getStringCellValue());

					/*List<City> cityList = cityService.executeSimpleQuery("SELECT c FROM City c WHERE c.cityName = '"+cityName+"'");
    		    	int stateId=cityList.get(0).getStateId();
                	logger.info("***********State Id**********"+stateId);

    		    	List<State> stateList = stateService.executeSimpleQuery("SELECT s FROM State s WHERE s.stateId = '"+stateId+"'");
    		    	int countryId=stateList.get(0).getCountryId();
                	logger.info("***********Country Id**********"+countryId);
    		    	address.setCityId(cityList.get(0).getCityId());*/
					city.setCityName(cityName);



				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:City Name cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 30");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}

				if(!(String.valueOf(row.getCell(30)).equalsIgnoreCase(null)) && !(String.valueOf(row.getCell(30)).equalsIgnoreCase(""))){

					if(String.valueOf(row.getCell(30)).length()>3){
						logger.info("***** Pincode Before *******"+row.getCell(30));
						String pinCodeVal=String.valueOf(row.getCell(30));
						logger.info("***** Pincode After*******"+pinCodeVal.substring(0, (pinCodeVal.length()-2)));

						/*logger.info("***** Pincode Checking*******"+pinCodeVal.matches("[0-9]{6}$"));*/
						if((pinCodeVal.length()>3)){
							BigDecimal b1;
							b1 = new BigDecimal(pinCodeVal);
							int pincode = b1.intValue();
							address.setPincode(pincode);

						}
						else{

							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Pincode Column Type(allows 6 Numbers) is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 31");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							j=1;
							break;	
						}
					}
					else
					{

						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Pincode is not Valid");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;	

					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Pincode cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 31");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}


				if(!StringUtils.isEmpty(row.getCell(31).getStringCellValue())){
					logger.info("***** Email *******"+row.getCell(31).getStringCellValue());

					if(row.getCell(31).getStringCellValue().contains("@")){
						contact1.setContactContent(row.getCell(31).getStringCellValue());
						contact1.setContactType("Email");
						contact1.setContactPrimary("Yes");
					}
					else
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Email Column Type is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 32");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Email Address cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 32");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}

				if(!(String.valueOf(row.getCell(32)).equalsIgnoreCase(null)) && !(String.valueOf(row.getCell(32)).equalsIgnoreCase(""))){

					BigDecimal b1;

					if((String.valueOf(row.getCell(32)).trim().length()>20)){
						logger.info("***** Mobile No Length *******"+String.valueOf(row.getCell(32)).length());
						logger.info("***** Mobile No  After*******"+(String.valueOf(row.getCell(32)).trim().substring(0, String.valueOf(row.getCell(32)).length()-2)));
						logger.info("*****Raj*** ");


						/*logger.info("***** MobileNo Checking*******"+String.valueOf(row.getCell(32)).trim().substring(0, (String.valueOf(row.getCell(32)).length()-2)).replace(".", "").matches("^[0-9]{15}$"));*/
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Mobile No Column(allows upto 15 Numbers) is not matched at Excel Row No :"+(row.getRowNum()) +"& Column No 33");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;	
					}

					else{
						Object b2 = row.getCell(32);
						System.err.println("b2="+b2);
						if(b2.toString().contains("."))
						{
							String s2=b2.toString().replace(".", ",");
							String[] ss=s2.toString().split(",");
							String mb=ss[0]+""+ss[1];
							String[] mobile=mb.split("E");
							System.out.println("mobilemobilemobilemobilemobile"+mobile[0]);
							contact2.setContactContent(mobile[0]);
						}else if(b2.toString().contains("E")){
							String[] mobile=b2.toString().split("E");
							System.out.println("b2  "+b2.toString().split(".")+"mobilemobilemobilemobilemobile"+mobile[0]);
							contact2.setContactContent(mobile[0]);
							contact2.setContactContent(mobile[0]);
						}else{
							contact2.setContactContent(b2.toString());
						}

						contact2.setContactType("Mobile");
						contact2.setContactPrimary("Yes");
					}

				}

				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Mobile Number cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 33");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}



				if(!StringUtils.isEmpty(row.getCell(33).getStringCellValue())){
					logger.info("***** Seasonal *******"+row.getCell(33).getStringCellValue());
					if("Yes".equalsIgnoreCase(row.getCell(33).getStringCellValue()) || "No".equalsIgnoreCase(row.getCell(33).getStringCellValue())) {

						address.setAddressSeason((row.getCell(33).getStringCellValue()));
					}
					else{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Seasonal Colum Type is not matched(Allowable Values are:Yes/No) at Excel Row No :"+(row.getRowNum()) +"& Column No 34");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						j=1;
						break;
					}
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Importing Failed:Seasonal Column cannot be empty at Excel Row No :"+(row.getRowNum()) +"& Column No 34");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;


				}


				String seasonFrom = String.valueOf(row.getCell(34).getDateCellValue());
				logger.info("*****seasonFrom Value ****"+seasonFrom);
				if(!("null".equalsIgnoreCase(seasonFrom.trim())) && !("".equalsIgnoreCase(seasonFrom.trim()))){

					logger.info("***** Seasonal From*******"+row.getCell(34).getDateCellValue().toString());
					address.setAddressSeasonFrom((ConvertDate.formattedDate(seasonFrom)));
				}




				String seasonTo = String.valueOf(row.getCell(35).getDateCellValue());
				logger.info("*****seasonTo Value ****"+seasonTo);
				if(!("null".equalsIgnoreCase(seasonTo.trim())) && !("".equalsIgnoreCase(seasonTo.trim()))){


					logger.info("***** Seasonal To*******"+row.getCell(35).getDateCellValue().toString());
					address.setAddressSeasonTo((ConvertDate.formattedDate(seasonTo)));
				}

				ownerPropertyList.add(ownerProperty);
				personList.add(person);
				addressList.add(address);
				contactList1.add(contact1);
				contactList2.add(contact2);
				countryLists.add(country);
				stateLists.add(state);
				cityLists.add(city);


				logger.info("********Value of i********"+i);
			}

		}


		if(j==1){

			return errorResponse;
		}

		if(i==0)
		{		

			logger.info("********Inside if codition Value of i********"+i);


			Iterator<Person>  actualPerson=personList.iterator();
			Iterator<Address> actualAddress=addressList.iterator();
			Iterator<OwnerProperty> actualOwnerProperty=ownerPropertyList.iterator();
			Iterator<Contact> actualContact1=contactList1.iterator();
			Iterator<Contact> actualContact2=contactList2.iterator();

			Iterator<Country> actualCountry=countryLists.iterator();
			Iterator<State> actualState=stateLists.iterator();
			Iterator<City> actualCity=cityLists.iterator();


			logger.info("Person List"+personList.size());
			logger.info("Addrees List"+addressList.size());
			logger.info("OwnerPropertyList"+ownerPropertyList.size());
			logger.info("Contact List"+contactList1.size());
			logger.info("Contact List"+contactList2.size());

			logger.info("Country List"+countryLists.size());
			logger.info("State List"+stateLists.size());
			logger.info("City List"+cityLists.size());

			if(personList.size()==0 && addressList.size()==0 && ownerPropertyList.size()==0 && contactList1.size()==0 && contactList2.size()==0 && countryLists.size()==0 && stateLists.size()==0 && cityLists.size()==0){
				logger.info("Empty File Checking");
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "File is Empty,Please Upload Valid File");
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
				k=1;


			}



			if(personList.size()==addressList.size() && addressList.size()==ownerPropertyList.size()){

				while(actualPerson.hasNext() && actualAddress.hasNext() && actualOwnerProperty.hasNext() && actualContact1.hasNext() && actualContact2.hasNext() && i!=1){

					logger.info("********Value of i inside While Condition********"+i);

					Person per=actualPerson.next();
					Address addr=actualAddress.next();
					Contact contacts1=actualContact1.next();
					Contact contacts2=actualContact2.next();


					Country countries=actualCountry.next();
					State states=actualState.next();
					City cities=actualCity.next();

					List<Country> countryListAfterPassingCountryName=countryService.findIdbyName(countries.getCountryName());

					if(countryListAfterPassingCountryName.isEmpty()){
						countryService.save(countries);
					}

					int stateIds=0;
					/*List<State> stateListAfterPassingStateName=stateService.getStateListAfterPassingStateName(states.getStateName(),countries.getCountryId());

							if(stateListAfterPassingStateName.isEmpty()){
								states.setCountryId(countries.getCountryId());
								stateService.save(states);
							}*/

					if(countryListAfterPassingCountryName.isEmpty()){

						logger.info("********Country Id********"+countries.getCountryId());
						List<State> stateListAfterPassingStateName=stateService.getStateListAfterPassingStateName(states.getStateName(),countries.getCountryId());

						if(stateListAfterPassingStateName.isEmpty()){
							states.setCountryId(countries.getCountryId());
							stateService.save(states);
							stateIds=states.getStateId();

						}

					}

					else{
						logger.info("********Country Id********"+countryListAfterPassingCountryName.get(0).getCountryId());

						List<State> stateListAfterPassingStateName=stateService.getStateListAfterPassingStateName(states.getStateName(),countryListAfterPassingCountryName.get(0).getCountryId());

						if(stateListAfterPassingStateName.size()>0){
							logger.info("state List Size"+stateListAfterPassingStateName.size());
							stateIds=stateListAfterPassingStateName.get(0).getStateId();
						}
						logger.info("state Id When hit to db"+stateListAfterPassingStateName.get(0).getStateId());
						if(stateListAfterPassingStateName.isEmpty()){
							states.setCountryId(countryListAfterPassingCountryName.get(0).getCountryId());
							stateService.save(states);
							stateIds=states.getStateId();
							logger.info("********State Id If does not exist********"+states.getStateId());
						}

					}


					logger.info("********State Id********"+states.getStateId());
					List<City> cityListAfterPassingCityNameAndStId=cityService.cityListAfterPassingCityNameAndStId(cities.getCityName(),stateIds);

					if(cityListAfterPassingCityNameAndStId.isEmpty()){

						logger.info("state Id setting in city Table"+stateIds);
						cities.setStateId(stateIds);
						cityService.save(cities);

					}

					Set<Address> address1=new HashSet<Address>();

					Set<Contact> contactAdd=new HashSet<Contact>();
					if(cityListAfterPassingCityNameAndStId.isEmpty()){
						logger.info("city Id in Empty Condition"+cities.getCityId());
						addr.setCityId(cities.getCityId());
					}
					else
					{
						logger.info("city Id in Not Empty Condition"+cityListAfterPassingCityNameAndStId.get(0).getCityId());

						addr.setCityId(cityListAfterPassingCityNameAndStId.get(0).getCityId());

					}

					address1.add(addr);
					per.setAddresses(address1);

					Owner owner = new Owner();
					owner.setCreatedBy(per.getLastUpdatedBy());
					owner.setLastUpdatedBy(per.getLastUpdatedBy());
					owner.setLastUpdatedDt(new Timestamp(new Date().getTime()));
					owner.setOwnerStatus("Inactive");
					owner.setPerson(per);
					per.setOwner(owner);


					contacts1.setAddressId(addr.getAddressId());
					contacts1.setContactLocation(addr.getAddressLocation());
					contacts1.setCreatedBy(per.getLastUpdatedBy());
					contacts1.setLastUpdatedBy(per.getLastUpdatedBy());
					contacts1.setLastUpdatedDt(new Timestamp(new Date().getTime()));

					List<Contact> contactListAfterMailId=contactService.getContactListBasedOnContactContent(contacts1.getContactContent()); 

					final String mailContent=contacts1.getContactContent();
					if(contactListAfterMailId.isEmpty()){
						contactAdd.add(contacts1);
						per.setContacts(contactAdd);
					}
					else
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Email Id :"+mailContent+" Already Exists");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						k=1;
						break;
					}
					contacts2.setAddressId(addr.getAddressId());
					contacts2.setContactLocation(addr.getAddressLocation());
					contacts2.setCreatedBy(per.getLastUpdatedBy());
					contacts2.setLastUpdatedBy(per.getLastUpdatedBy());
					contacts2.setLastUpdatedDt(new Timestamp(new Date().getTime()));

					List<Contact> contactListAfterMobileNo=contactService.getContactListBasedOnContactContent(contacts2.getContactContent()); 
					final String mobileContent=contacts2.getContactContent();
					if(contactListAfterMobileNo.isEmpty()){
						contactAdd.add(contacts2);
						per.setContacts(contactAdd);
					}
					else{

						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Mobile No :"+mobileContent+" Already Exists");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						k=1;
						break;
					}


					per.setPersonStatus("Inactive");
					personService.save(per);
					logger.info("Person Id"+per.getPersonId());

					long drGroupId=per.getDrGroupId();
					int ownerId=owner.getOwnerId();

					OwnerProperty ownerProp=actualOwnerProperty.next();
					ownerProp.setDrGroupId(drGroupId);
					ownerProp.setOwnerId(ownerId);
					ownerProp.setCreatedBy(per.getLastUpdatedBy());
					ownerProp.setLastUpdatedBy(per.getLastUpdatedBy());
					ownerProp.setLastUpdatedDt(new Timestamp(new Date().getTime()));
					ownerProp.setStatus("Inactive");

					List<OwnerProperty> ownerPropertyBasedOnPropertyIdAndOwnerId=ownerPropertyService.getOwnerPropertyBasedOnPropertyIdAndOwnerId(ownerProp.getPropertyId()); 
					final String propertyListBasedOnPropertyId=propertyService.getPropertyNameBasedOnPropertyId(ownerProp.getPropertyId());

					if(ownerPropertyBasedOnPropertyIdAndOwnerId.isEmpty()){
						ownerProp.setPrimaryOwner("Yes");	
						ownerPropertyService.save(ownerProp);
					}
					else{
						ownerProp.setPrimaryOwner("No");	
						ownerPropertyService.save(ownerProp);

					}

					/*else{

		    		    		HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
		    						{
		    							put("cannotImport", "Property No:"+propertyListBasedOnPropertyId+"Already Assigned to the Same Owner");
		    						}
		    					};
		    					errorResponse.setStatus("cannotImport");
		    					errorResponse.setResult(errorMapResponse);

		    		    	}*/

					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "File Imported Successfully");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);

				}
			}


		}
		if(i==1){
			logger.info("value of i Inside if condition"+i);
			return errorResponse;
		}

		if(k==1){
			return errorResponse;	
		}

		return errorResponse;

	}

	@RequestMapping(value="/property/getAllproperty",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Map<String, Object>> readAllproperty()
	{


		List<Object[]> ownerprop=ownerPropertyService.getData();
		logger.info("inside all property details--"+ownerprop.size());
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

		for(final Object[] prop:ownerprop)
		{
			data.add(new HashMap<String,Object>(){

				{
					put("property_No", propertyService.find(prop[1]).getProperty_No());
					logger.info("********Owner ID*****************"+(int)prop[0]);
					Integer owner=ownerService.findBaseOnOwnerId((Integer)prop[0]);
					Object[] p=personService.findBaseOnPersonId(owner);
					if(p[1]==null)
						put("personName",p[0]+" "+p[2]);
					else
						put("personName", p[0]+" "+p[1]+" "+p[2]); 



				}
			});

		}

		return data;

	}

	@RequestMapping(value="/property/getAllpropertyTenant",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Map<String, Object>> readAllpropertyTenat()
	{


		List<TenantProperty> tenantProp=tenantPropertyService.findAll();
		logger.info("inside all property details--"+tenantProp.size());
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

		for(final TenantProperty prop:tenantProp)
		{
			data.add(new HashMap<String,Object>(){

				{
					put("property_No", propertyService.find(prop.getPropertyId()).getProperty_No());
					logger.info("********Tenant ID*****************"+prop.getTenantId());

					Object[] p=personService.findBaseOnPersonId(tenantSevice.getData(prop.getTenantId().getTenantId()));
					if(p[1]==null)
						put("personName",p[0]+" "+p[2]);
					else
						put("personName", p[0]+" "+p[1]+" "+p[2]); 



				}
			});

		}

		return data;

	}


	@RequestMapping(value="/property/getAllpropertyFamily",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Map<String, Object>> readAllpropertyFamily()
	{


		List<FamilyProperty> familyProp=familyProperty.executeSimpleQuery("select f from FamilyProperty f");
		logger.info("inside all property details--"+familyProp.size());
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

		for(final FamilyProperty prop:familyProp)
		{
			data.add(new HashMap<String,Object>(){

				{
					put("property_No", propertyService.find(prop.getPropertyId()).getProperty_No());
					logger.info("********Tenant ID*****************"+prop.getFamilyId());



					Object[] p=personService.findBaseOnPersonId(familySevice.find(prop.getFamilyId()).getPersonId());
					if(p[1]==null)
						put("personName",p[0]+" "+p[2]);
					else
						put("personName", p[0]+" "+p[1]+" "+p[2]); 



				}
			});

		}

		return data;

	}




	@RequestMapping(value="/property/getAllpropertyDomestic",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Map<String, Object>> readAllpropertyDomestic()
	{


		List<DomesticProperty> domProp=domesticPropertyServicel.executeSimpleQuery("select f from DomesticProperty f");
		logger.info("inside all property details--"+domProp.size());
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

		for(final DomesticProperty prop:domProp)
		{
			data.add(new HashMap<String,Object>(){

				{
					put("property_No", propertyService.find(prop.getPropertyId()).getProperty_No());
					logger.info("********Tenant ID*****************"+prop.getDomasticId());



					Object[] p=personService.findBaseOnPersonId(domesticService.find(prop.getDomasticId()).getPersonId());
					if(p[1]==null)
						put("personName",p[0]+" "+p[2]);
					else
						put("personName", p[0]+" "+p[1]+" "+p[2]); 



				}
			});

		}

		return data;

	}

	@RequestMapping(value="/person/getAllParkingSlotsBasedOnproperty/{personId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Map<String, Object>> readAllpropertyBaseonParkin(@PathVariable int personId)
	{

		logger.info("*******inside a parking slot master ***********");
		int ownerId=ownerService.getOwnerId(personId);

		List<OwnerProperty> propertyIds=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+ownerId);
		logger.info("****owner Property Size************"+propertyIds.size());
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		for(final OwnerProperty property:propertyIds)
		{

			List<ParkingSlotsAllotment> parkingSlots=	parkingSlotsAllotmentService.executeSimpleQuery("select p from ParkingSlotsAllotment p INNER JOIN p.property p1 where p1.propertyId="+property.getPropertyId());

			logger.info("**********parking slots Size*******"+parkingSlots.size());
			for(final ParkingSlotsAllotment parkin:parkingSlots)
			{

				data.add(new HashMap<String,Object>(){

					{
						put("propertyNo", parkin.getProperty().getProperty_No());
						put("slotNumber",parkin.getParkingSlots().getPsSlotNo());
						put("parkingMethod",parkin.getParkingSlots().getPsParkingMethod());
						put("ownerShipType", parkin.getParkingSlots().getPsOwnership());

						put("activationDate",parkin.getParkingSlots().getPsActiveDate());



					}
				});

			}


		}


		logger.info("**********Map Size*******"+data.size());


		return data;
	}


	@RequestMapping(value = "/petsTemplate/petsTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


		List<Object[]> salaryTemplateEntities = petsService.findAllPets();
		System.out.println(salaryTemplateEntities);

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

		header.createCell(0).setCellValue("Block Name");
		header.createCell(1).setCellValue("Property Number");
		header.createCell(2).setCellValue("Person Name");
		header.createCell(3).setCellValue("Pet Type");
		header.createCell(4).setCellValue("Pet Name");        
		header.createCell(5).setCellValue("Vaccinations Type");
		header.createCell(6).setCellValue("Emergency Contact");
		header.createCell(7).setCellValue("Pet Status");

		for(int j = 0; j<=7; j++){
			header.getCell(j).setCellStyle(style);
			sheet.setColumnWidth(j, 8000); 
			sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		}

		int count = 1;
		XSSFCell cell = null;
		for (Object[] person :salaryTemplateEntities ) {

			XSSFRow row = sheet.createRow(count);

			int value = count+1;

			row.createCell(0).setCellValue((String)person[11]);
			row.createCell(1).setCellValue((String)person[9]);
			String str="";
			if((String)person[23]!=null){
				str=(String)person[23];
			}

			row.createCell(2).setCellValue((String)person[22]+" "+str);

			row.createCell(3).setCellValue((String)person[4]);
			row.createCell(4).setCellValue((String)person[1]);
			row.createCell(5).setCellValue((String)person[17]);
			row.createCell(6).setCellValue((String)person[21]);
			row.createCell(7).setCellValue((String)person[5]);

			count ++;
		}

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"PetTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}





	@RequestMapping(value = "/ownerTemplate/ownerTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


		List<Object[]> ownerTemplateEntities =personService.getAllPerson();


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

		header.createCell(0).setCellValue("Title");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Father Name");
		header.createCell(3).setCellValue("Marital Status");
		header.createCell(4).setCellValue("Spouse Name");
		header.createCell(5).setCellValue("Nationality");        
		header.createCell(6).setCellValue("Language");
		header.createCell(7).setCellValue("Status");

		for(int j = 0; j<=7; j++){
			header.getCell(j).setCellStyle(style);
			sheet.setColumnWidth(j, 9000); 
			sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		}

		int count = 1;
		//XSSFCell cell = null;
		for (Object[] owner :ownerTemplateEntities ) {

			XSSFRow row = sheet.createRow(count);

			//int value = count+1;
			String str="";
			if((String)owner[3]!=null){
				str=(String)owner[3];
			}
			else{
				str="";
			}
			row.createCell(0).setCellValue(str);
			String str1="";
			String str2="";
			if((String)owner[5]!=null){
				str=(String)owner[5];
			}
			else{
				str="";
			}
			if((String)owner[4]!=null){
				str1=(String)owner[4];
			}
			else{
				str1="";
			}
			if((String)owner[6]!=null){
				str2=(String)owner[6];
			}
			else{
				str2="";
			}


			row.createCell(1).setCellValue(str1+" "+str+" "+str2);
			if((String)owner[7]!=null){
				str=(String)owner[7];
			}
			else{
				str="N/A";
			}

			row.createCell(2).setCellValue(str);
			if((String)owner[17]!=null){
				str=(String)owner[17];
			}
			else{
				str="";
			}


			row.createCell(3).setCellValue(str);
			if((String)owner[8]!=null){
				str=(String)owner[8];
			}
			else{
				str="";
			}

			row.createCell(4).setCellValue(str);
			if((String)owner[19]!=null){
				str=(String)owner[19];
			}
			else{
				str="";
			}

			row.createCell(5).setCellValue(str);
			if((String)owner[11]!=null){
				str=(String)owner[11];
			}
			else{
				str="";
			}

			row.createCell(6).setCellValue(str);
			if((String)owner[22]!=null){
				str=(String)owner[22];
			}
			else{
				str="";
			}

			row.createCell(7).setCellValue(str);

			count ++;
		}

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}

	@RequestMapping(value = "/tenantTemplate/tenantTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileTenant(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


		List<Object[]> tenantTemplateEntities =personService.getAllTenant();


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

		header.createCell(0).setCellValue("Title");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Father Name");
		header.createCell(3).setCellValue("Marital Status");
		header.createCell(4).setCellValue("Spouse Name");
		header.createCell(5).setCellValue("Nationality");        
		header.createCell(6).setCellValue("Language");
		header.createCell(7).setCellValue("Status");

		for(int j = 0; j<=7; j++){
			header.getCell(j).setCellStyle(style);
			sheet.setColumnWidth(j, 8000); 
			sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		}

		int count = 1;
		//XSSFCell cell = null;
		for (Object[] owner :tenantTemplateEntities ) {

			XSSFRow row = sheet.createRow(count);

			String str="";
			String str1="";
			String str2="";
			if((String)owner[3]!=null){
				str=(String)owner[3];
			}
			else{
				str="";
			}

			row.createCell(0).setCellValue(str);
			if((String)owner[4]!=null){
				str=(String)owner[4];
			}
			else{
				str="";
			}
			if((String)owner[5]!=null){
				str1=(String)owner[5];
			}
			else{
				str1="";
			}
			if((String)owner[6]!=null){
				str2=(String)owner[6];
			}
			else{
				str2="";
			}


			row.createCell(1).setCellValue(str+" "+str1+" "+str2);
			if((String)owner[7]!=null){
				str=(String)owner[7];
			}
			else{
				str="";
			}


			row.createCell(2).setCellValue(str);
			if((String)owner[17]!=null){
				str=(String)owner[17];
			}
			else{
				str="";
			}

			row.createCell(3).setCellValue(str);
			if((String)owner[8]!=null){
				str=(String)owner[8];
			}
			else{
				str="";
			}

			row.createCell(4).setCellValue(str);
			if((String)owner[19]!=null){
				str=(String)owner[19];
			}
			else{
				str="";
			}

			row.createCell(5).setCellValue(str);
			if((String)owner[11]!=null){
				str=(String)owner[11];
			}
			else{
				str="";
			}

			row.createCell(6).setCellValue(str);
			if((String)owner[22]!=null){
				str=(String)owner[22];
			}
			else{
				str="";
			}

			row.createCell(7).setCellValue(str);

			count ++;
		}

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"TenantTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}

	@RequestMapping(value = "/familyTemplate/familyTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileFamily(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


		List<Object[]> familyTemplateEntities =personService.getAllFamily();


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

		header.createCell(0).setCellValue("Title");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Father Name");
		header.createCell(3).setCellValue("Sex");
		header.createCell(4).setCellValue("Date of Birth");
		header.createCell(5).setCellValue("Age");        
		header.createCell(6).setCellValue("Blood Group");
		header.createCell(7).setCellValue("Status");
		header.createCell(8).setCellValue("Nationality");
		header.createCell(9).setCellValue("RelationShip");
		header.createCell(10).setCellValue("Primary Mobile");
		header.createCell(11).setCellValue("Primary Email");

		for(int j = 0; j<=11; j++){
			header.getCell(j).setCellStyle(style);
			sheet.setColumnWidth(j, 8000); 
			sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		}

		int count = 1;

		for (Object[] owner :familyTemplateEntities ) {

			XSSFRow row = sheet.createRow(count);

			String str="";
			String str1="";
			String str2="";

			if((String)owner[3]!=null){
				str=(String)owner[3];
			}
			else{
				str="";
			}


			row.createCell(0).setCellValue(str);
			if((String)owner[4]!=null){
				str=(String)owner[4];
			}
			else{
				str="";
			}
			if((String)owner[5]!=null){
				str1=(String)owner[5];
			}
			else{
				str1="";
			}
			if((String)owner[6]!=null){
				str2=(String)owner[6];
			}
			else{
				str2="";
			}



			row.createCell(1).setCellValue(str+" "+str1+" "+str2);
			if((String)owner[7]!=null){
				str=(String)owner[7];
			}
			else{
				str="";
			}
			row.createCell(2).setCellValue(str);
			if((String)owner[18]!=null){
				str=(String)owner[18];
			}
			else{
				str="";
			}
			row.createCell(3).setCellValue(str);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if((Date)owner[9] != null){
				str=sdf.format((Date)owner[9]);
			}
			else{
				str="";
			}
			row.createCell(4).setCellValue(str);
			if((Date)owner[9] != null){
				str=Integer.toString(dateTimeCalender.getAgeFromDob((Date)owner[9]));
			}
			else{
				str="";
			}

			row.createCell(5).setCellValue(str);
			if((String)owner[20]!=null){
				str=(String)owner[20];
			}
			else{
				str="";
			}
			row.createCell(6).setCellValue(str);
			if((String)owner[22]!=null){
				str=(String)owner[22];
			}
			else{
				str="";
			}
			row.createCell(7).setCellValue(str);
			row.createCell(8).setCellValue((String)owner[19]);
			List<FamilyProperty> famiProp=familyProperty.executeSimpleQuery("select f from FamilyProperty f where f.familyId="+(Integer)owner[23]);
			if(!(famiProp.isEmpty())){
				row.createCell(9).setCellValue(famiProp.get(0).getFpRelationship());
			}
			List<Person> personContact=personService.getContactNameBasedOnPersonId((Integer)owner[0]);

			if(!(personContact).isEmpty()){


				for(Contact contact:personContact.get(0).getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(10).setCellValue(contact.getContactContent());



					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(11).setCellValue(contact.getContactContent());

					}


				}
			}
			count ++;
		}

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"FamilyTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}

	@RequestMapping(value = "/domesticTemplate/domesticTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileDomestic(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


		List<Object[]> domesticTemplateEntities =personService.getAllDomestic();


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

		header.createCell(0).setCellValue("Title");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Father Name");
		header.createCell(3).setCellValue("Sex");
		header.createCell(4).setCellValue("Date of Birth");
		header.createCell(5).setCellValue("Age");        
		header.createCell(6).setCellValue("Blood Group");
		header.createCell(7).setCellValue("Status");

		for(int j = 0; j<=7; j++){
			header.getCell(j).setCellStyle(style);
			sheet.setColumnWidth(j, 8000); 
			sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		}

		int count = 1;
		//XSSFCell cell = null;
		for (Object[] owner :domesticTemplateEntities ) {

			XSSFRow row = sheet.createRow(count);


			String str="";
			String str1="";
			String str2="";

			if((String)owner[3]!=null){
				str=(String)owner[3];
			}
			else{
				str="";
			}

			row.createCell(0).setCellValue(str);
			if((String)owner[4]!=null){
				str=(String)owner[4];
			}
			else{
				str="";
			}
			if((String)owner[5]!=null){
				str1=(String)owner[5];
			}
			else{
				str1="";
			}
			if((String)owner[6]!=null){
				str2=(String)owner[6];
			}
			else{
				str2="";
			}


			row.createCell(1).setCellValue(str+" "+str1+" "+str2);
			if((String)owner[7]!=null){
				str=(String)owner[7];
			}
			else{
				str="";
			}


			row.createCell(2).setCellValue(str);
			if((String)owner[18]!=null){
				str=(String)owner[18];
			}
			else{
				str="";
			}


			row.createCell(3).setCellValue(str);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if((Timestamp)owner[9]!=null){
				str=sdf.format((Timestamp)owner[9]);
			}
			else{
				str="";
			}

			row.createCell(4).setCellValue(str);
			if((Timestamp)owner[9] != null){
				str=Integer.toString(dateTimeCalender.getAgeFromDob((Timestamp)owner[9]));
			}
			else{
				str="";
			}
			row.createCell(5).setCellValue(str);
			if((String)owner[20]!=null){
				str=(String)owner[20];
			}
			else{
				str="";
			}

			row.createCell(6).setCellValue(str);
			if((String)owner[22]!=null){
				str=(String)owner[22];
			}
			else{
				str="";
			}

			row.createCell(7).setCellValue(str);

			count ++;
		}

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"DomesticTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}

	@RequestMapping(value = "/vendorTemplate/vendorTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileVendor(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


		List<Object[]> vendorTemplateEntities =personService.getAllVendor();


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



		header.createCell(0).setCellValue("Category");
		header.createCell(1).setCellValue("Title");
		header.createCell(2).setCellValue("Name");
		header.createCell(3).setCellValue("Date of Birth");
		header.createCell(4).setCellValue("Age");        
		header.createCell(5).setCellValue("Nature Of Work");
		header.createCell(6).setCellValue("Languages");
		header.createCell(7).setCellValue("Status");

		for(int j = 0; j<=7; j++){
			header.getCell(j).setCellStyle(style);
			sheet.setColumnWidth(j, 8000); 
			sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		}

		int count = 1;
		//XSSFCell cell = null;
		for (Object[] owner :vendorTemplateEntities ) {

			XSSFRow row = sheet.createRow(count);


			String str="";
			String str1="";
			String str2="";

			if((String)owner[2]!=null){
				str=(String)owner[2];
			}
			else{
				str="";
			}
			row.createCell(0).setCellValue(str);

			if((String)owner[3]!=null){
				str=(String)owner[3];
			}
			else{
				str="";
			}
			row.createCell(1).setCellValue(str);
			if((String)owner[4]!=null){
				str=(String)owner[4];
			}
			else{
				str="";
			}
			if((String)owner[5]!=null){
				str1=(String)owner[5];
			}
			else{
				str1="";
			}
			if((String)owner[6]!=null){
				str2=(String)owner[6];
			}
			else{
				str2="";
			}

			row.createCell(2).setCellValue(str+" "+str1+" "+str2);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if((Date)owner[9] != null){
				str=sdf.format((Date)owner[9]);	
			}
			else{
				str="";
			}

			row.createCell(3).setCellValue(str);

			Date d=null;
			if((Date)owner[9] != null)
			{
				d=(Date)owner[9];
				str=Integer.toString(dateTimeCalender.getAgeFromDob(d));
			}
			else{
				str="";
			}

			row.createCell(4).setCellValue(str);
			if((String)owner[21]!=null){
				str=(String)owner[21];
			}
			else{
				str="";
			}
			row.createCell(5).setCellValue(str);
			if((String)owner[11]!=null){
				str=(String)owner[11];
			}
			else{
				str="";
			}
			row.createCell(6).setCellValue(str);
			if((String)owner[22]!=null){
				str=(String)owner[22];
			}
			else{
				str="";
			}
			row.createCell(7).setCellValue(str);

			count ++;
		}

		FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();

		ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"VendorTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}




	//************************Pdf**************************************

	@RequestMapping(value = "/ownerPdfTemplate/ownerPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfOwner(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";


		List<Object[]> ownerTemplateEntities =personService.getAllPerson();



		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();



		PdfPTable table = new PdfPTable(9);

		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);

		table.addCell(new Paragraph("Image",font1));
		table.addCell(new Paragraph("Title",font1));
		table.addCell(new Paragraph("Name",font1));
		table.addCell(new Paragraph("Father Name",font1));
		table.addCell(new Paragraph("Marital Status",font1));
		table.addCell(new Paragraph("Spouse Name",font1));
		table.addCell(new Paragraph("Nationality",font1));
		table.addCell(new Paragraph("Language",font1));
		table.addCell(new Paragraph("Status",font1));

		for (Object[] owner :ownerTemplateEntities) {

			Blob blobImage = personService.getImage((Integer)owner[0]);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			Image image2 = Image.getInstance(blobAsBytes);
			PdfPCell cell1 = new PdfPCell(image2,true);
			String str="";
			String str1="";
			String str2="";
			if((String)owner[3]!=null){
				str=(String)owner[3];
			}
			else{
				str="";
			}
			PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			if((String)owner[4]!=null){
				str=(String)owner[4];
			}
			else{
				str="";
			}
			if((String)owner[5]!=null){
				str1=(String)owner[5];
			}
			else{
				str1="";
			}
			if((String)owner[6]!=null){
				str2=(String)owner[6];
			}
			else{
				str2="";
			}


			PdfPCell cell3 = new PdfPCell(new Paragraph(str+" "+str1+" "+str2,font3));
			if((String)owner[7]!=null){
				str=(String)owner[7];
			}
			else{
				str="N/A";
			}

			PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			if((String)owner[17]!=null){
				str=(String)owner[17];
			}
			else{
				str="";
			}

			PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
			if((String)owner[8]!=null){
				str=(String)owner[8];
			}
			else{
				str="";
			}

			PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
			if((String)owner[19]!=null){
				str=(String)owner[19];
			}
			else{
				str="";
			}

			PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			if((String)owner[11]!=null){
				str=(String)owner[11];
			}
			else{
				str="";
			}

			PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			if((String)owner[22]!=null){
				str=(String)owner[22];
			}
			else{
				str="";
			}

			PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));


			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.setWidthPercentage(100);
			float[] columnWidths = {8f, 5f, 10f, 10f, 10f, 10f, 10f, 10f, 5f};

			table.setWidths(columnWidths);

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
		response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);

		//servletOutputStream.w
		/*PdfReader reader = new PdfReader(fileName);
	        PrintWriter out = new PrintWriter(new FileOutputStream(ResourceBundle.getBundle("application").getString("bfm.exportCsvFile")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf"));
	        Rectangle rect = new Rectangle(70, 80, 490, 580);
	        RenderFilter filter = new RegionTextRenderFilter(rect);
	        TextExtractionStrategy strategy;
	        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	            out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
	        }
	        out.flush();
	        out.close();
	        reader.close();*/
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}


	@RequestMapping(value = "/tenantPdfTemplate/tenantPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfTenant(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";


		List<Object[]> tenantTemplateEntities =personService.getAllTenant();



		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();



		PdfPTable table = new PdfPTable(9);

		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);



		table.addCell(new Paragraph("Image",font1));
		table.addCell(new Paragraph("Title",font1));
		table.addCell(new Paragraph("Name",font1));
		table.addCell(new Paragraph("Father Name",font1));
		table.addCell(new Paragraph("Marital Status",font1));
		table.addCell(new Paragraph("Spouse Name",font1));
		table.addCell(new Paragraph("Nationality",font1));
		table.addCell(new Paragraph("Language",font1));
		table.addCell(new Paragraph("Status",font1));

		for (Object[] tenant :tenantTemplateEntities) {

			Blob blobImage = personService.getImage((Integer)tenant[0]);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			Image image2 = Image.getInstance(blobAsBytes);
			PdfPCell cell1 = new PdfPCell(image2,true);
			String str="";
			String str1="";
			String str2="";
			if((String)tenant[3]!=null){
				str=(String)tenant[3];
			}
			else{
				str="";
			}
			PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			if((String)tenant[4]!=null){
				str=(String)tenant[4];
			}
			else{
				str="";
			}
			if((String)tenant[5]!=null){
				str1=(String)tenant[5];
			}
			else{
				str1="";
			}
			if((String)tenant[6]!=null){
				str2=(String)tenant[6];
			}
			else{
				str2="";
			}


			PdfPCell cell3 = new PdfPCell(new Paragraph(str+" "+str1+" "+str2,font3));
			if((String)tenant[7]!=null){
				str=(String)tenant[7];
			}
			else{
				str="";
			}
			PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			if((String)tenant[17]!=null){
				str=(String)tenant[17];
			}
			else{
				str="";
			}

			PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
			if((String)tenant[8]!=null){
				str=(String)tenant[8];
			}
			else{
				str="";
			}

			PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
			if((String)tenant[19]!=null){
				str=(String)tenant[19];
			}
			else{
				str="";
			}

			PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			if((String)tenant[11]!=null){
				str=(String)tenant[11];
			}
			else{
				str="";
			}

			PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			if((String)tenant[22]!=null){
				str=(String)tenant[22];
			}
			else{
				str="";
			}

			PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));


			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.setWidthPercentage(100);
			float[] columnWidths = {8f, 5f, 10f, 10f, 10f, 10f, 10f, 10f, 5f};

			table.setWidths(columnWidths);

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
		response.setHeader("Content-Disposition", "inline;filename=\"TenantTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}


	@RequestMapping(value = "/familyPdfTemplate/familyPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfFamily(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";


		List<Object[]> familyTemplateEntities =personService.getAllFamily();



		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();



		PdfPTable table = new PdfPTable(9);

		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);



		table.addCell(new Paragraph("Image",font1));
		table.addCell(new Paragraph("Title",font1));
		table.addCell(new Paragraph("Name",font1));
		table.addCell(new Paragraph("Father Name",font1));
		table.addCell(new Paragraph("Sex",font1));
		table.addCell(new Paragraph("Date of Birth",font1));
		table.addCell(new Paragraph("Age",font1));
		table.addCell(new Paragraph("Blood Group",font1));
		table.addCell(new Paragraph("Status",font1));

		for (Object[] family :familyTemplateEntities) {

			Blob blobImage = personService.getImage((Integer)family[0]);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			Image image2 = Image.getInstance(blobAsBytes);
			PdfPCell cell1 = new PdfPCell(image2,true);
			String str="";
			String str1="";
			String str2="";
			if((String)family[3]!=null){
				str=(String)family[3];
			}
			else{
				str="";
			}
			PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[4]!=null){
				str=(String)family[4];
			}
			else{
				str="";
			}
			if((String)family[5]!=null){
				str1=(String)family[5];
			}
			else{
				str1="";
			}
			if((String)family[6]!=null){
				str2=(String)family[6];
			}
			else{
				str2="";
			}


			PdfPCell cell3 = new PdfPCell(new Paragraph(str+" "+str1+" "+str2,font3));
			if((String)family[7]!=null){
				str=(String)family[7];
			}
			else{
				str="";
			}
			PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[18]!=null){
				str=(String)family[18];
			}
			else{
				str="";
			}

			PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));


			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if((Date)family[9] != null){
				str=sdf.format((Date)family[9]);
			}
			else{
				str="";
			}

			PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));

			Date d=null;
			if((Date)family[9] != null)
			{
				d=(Date)family[9];
				str=Integer.toString(dateTimeCalender.getAgeFromDob(d));

			}
			else{
				str="";
			}

			PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[20]!=null){
				str=(String)family[20];
			}
			else{
				str="";
			}

			PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[22]!=null){
				str=(String)family[22];
			}
			else{
				str="";
			}

			PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));


			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.setWidthPercentage(100);
			float[] columnWidths = {8f, 5f, 10f, 10f, 10f, 10f, 10f, 10f, 5f};

			table.setWidths(columnWidths);

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
		response.setHeader("Content-Disposition", "inline;filename=\"FamilyTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}



	@RequestMapping(value = "/domesticPdfTemplate/domesticPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfDomesticHelp(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";


		List<Object[]> domesticTemplateEntities =personService.getAllDomestic();



		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();



		PdfPTable table = new PdfPTable(9);

		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);


		table.addCell(new Paragraph("Image",font1));
		table.addCell(new Paragraph("Title",font1));
		table.addCell(new Paragraph("Name",font1));
		table.addCell(new Paragraph("Father Name",font1));
		table.addCell(new Paragraph("Sex",font1));
		table.addCell(new Paragraph("Date of Birth",font1));
		table.addCell(new Paragraph("Age",font1));
		table.addCell(new Paragraph("Blood Group",font1));
		table.addCell(new Paragraph("Status",font1));

		for (Object[] domestic :domesticTemplateEntities) {

			Blob blobImage = personService.getImage((Integer)domestic[0]);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			Image image2 = Image.getInstance(blobAsBytes);
			PdfPCell cell1 = new PdfPCell(image2,true);
			String str="";
			String str1="";
			String str2="";
			if((String)domestic[3]!=null){
				str=(String)domestic[3];
			}
			else{
				str="";
			}
			PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			if((String)domestic[4]!=null){
				str=(String)domestic[4];
			}
			else{
				str="";
			}
			if((String)domestic[5]!=null){
				str1=(String)domestic[5];
			}
			else{
				str1="";
			}
			if((String)domestic[6]!=null){
				str2=(String)domestic[6];
			}
			else{
				str2="";
			}

			PdfPCell cell3 = new PdfPCell(new Paragraph(str+" "+str1+" "+str2,font3));
			if((String)domestic[7]!=null){
				str=(String)domestic[7];
			}
			else{
				str="";
			}

			PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			if((String)domestic[18]!=null){
				str=(String)domestic[18];
			}
			else{
				str="";
			}

			PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if((Date)domestic[9] != null){
				str=sdf.format((Date)domestic[9]);
			}
			else{
				str="";
			}


			PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));

			Date d=null;
			if((Date)domestic[9] != null)
			{
				d=(Date)domestic[9];
				str=Integer.toString(dateTimeCalender.getAgeFromDob(d));
			}
			else{
				str="";
			}

			PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			if((String)domestic[20]!=null){
				str=(String)domestic[20];
			}
			else{
				str="";
			}

			PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			if((String)domestic[22]!=null){
				str=(String)domestic[22];
			}
			else{
				str="";
			}

			PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));


			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.setWidthPercentage(100);
			float[] columnWidths = {8f, 5f, 10f, 10f, 10f, 10f, 10f, 10f, 5f};

			table.setWidths(columnWidths);

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
		response.setHeader("Content-Disposition", "inline;filename=\"DomesticTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}


	@RequestMapping(value = "/petsPdfTemplate/petsPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfForPets(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";


		List<Object[]> petsTemplateEntities = petsService.findAllPets();



		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();



		PdfPTable table = new PdfPTable(8);

		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);



		table.addCell(new Paragraph("Block Name",font1));
		table.addCell(new Paragraph("Property Number",font1));
		table.addCell(new Paragraph("Person Name",font1));
		table.addCell(new Paragraph("Pet Type",font1));
		table.addCell(new Paragraph("Pet Name",font1));
		table.addCell(new Paragraph("Vaccinations Type",font1));
		table.addCell(new Paragraph("Emergency Contact",font1));
		table.addCell(new Paragraph("Pet Status",font1));

		for (Object[] pets : petsTemplateEntities ) {


			String str="";
			String str1="";

			if((String)pets[11]!=null){
				str=(String)pets[11];
			}
			else{
				str="";
			}
			PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
			if((String)pets[9]!=null){
				str=(String)pets[9];
			}
			else{
				str="";
			}

			PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			if((String)pets[22]!=null){
				str=(String)pets[22];
			}
			else{
				str="";
			}
			if((String)pets[23]!=null){
				str1=(String)pets[23];
			}
			else{
				str1="";
			}


			PdfPCell cell3 = new PdfPCell(new Paragraph(str+" "+str1,font3));
			if((String)pets[4]!=null){
				str=(String)pets[4];
			}
			else{
				str="";
			}

			PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
			if((String)pets[1]!=null){
				str=(String)pets[1];
			}
			else{
				str="";
			}


			PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
			if((String)pets[17]!=null){
				str=(String)pets[17];
			}
			else{
				str="";
			}


			PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
			if((String)pets[21]!=null){
				str=(String)pets[21];
			}
			else{
				str="";
			}

			PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			if((String)pets[5]!=null){
				str=(String)pets[5];
			}
			else{
				str="";
			}

			PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));


			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);

			table.setWidthPercentage(100);
			float[] columnWidths = {12f, 15f, 8f, 8f, 10f, 18f, 18f, 10f};

			table.setWidths(columnWidths);

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
		response.setHeader("Content-Disposition", "inline;filename=\"PetTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}

	@RequestMapping(value = "/vendorPdfTemplate/vendorPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfVendor(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{



		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";


		List<Object[]> vendorTemplateEntities =personService.getAllVendor();



		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();



		PdfPTable table = new PdfPTable(9);

		Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8);



		table.addCell(new Paragraph("Image",font1));
		table.addCell(new Paragraph("Category",font1));
		table.addCell(new Paragraph("Title",font1));
		table.addCell(new Paragraph("Name",font1));
		table.addCell(new Paragraph("Date of Birth",font1));
		table.addCell(new Paragraph("Age",font1));
		table.addCell(new Paragraph("Nature Of Work",font1));
		table.addCell(new Paragraph("Languages",font1));
		table.addCell(new Paragraph("Status",font1));

		for (Object[] family :vendorTemplateEntities) {

			Blob blobImage = personService.getImage((Integer)family[0]);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			Image image2 = Image.getInstance(blobAsBytes);
			PdfPCell cell1 = new PdfPCell(image2,true);

			String str="";
			String str1="";
			String str2="";
			if((String)family[2]!=null){
				str=(String)family[2];
			}
			else{
				str="";
			}

			PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[3]!=null){
				str=(String)family[3];
			}
			else{
				str="";
			}

			PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[4]!=null){
				str=(String)family[4];
			}
			else{
				str="";
			}
			if((String)family[5]!=null){
				str1=(String)family[5];
			}
			else{
				str1="";
			}
			if((String)family[6]!=null){
				str2=(String)family[6];
			}
			else{
				str2="";
			}


			PdfPCell cell4 = new PdfPCell(new Paragraph(str+" "+str1+" "+str2,font3));

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if((Date)family[9] != null){
				str=sdf.format((Date)family[9]);
			}
			else{
				str="";
			}
			PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));

			Date d=null;
			if((Date)family[9] != null)
			{
				d=(Date)family[9];
				str=Integer.toString(dateTimeCalender.getAgeFromDob(d));
			}
			else{
				str="";
			}

			PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));

			if((String)family[21]!=null){
				str=(String)family[21];
			}
			else{
				str="";
			}

			PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[11]!=null){
				str=(String)family[11];
			}
			else{
				str="";
			}

			PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
			if((String)family[22]!=null){
				str=(String)family[22];
			}
			else{
				str="";
			}


			PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));


			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.setWidthPercentage(100);
			float[] columnWidths = {8f, 10f, 8f, 10f, 10f, 10f, 14f, 10f, 8f};

			table.setWidths(columnWidths);

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
		response.setHeader("Content-Disposition", "inline;filename=\"VendorTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();

		return null;
	}


	/*
	 *MIS REPORTS Excel Sheets 
	 *
	 * of Customer Occupancy Module
	 * ,Assests,Vendors, parking ,Staff
	 * 
	 * 
	 * Jsp View Reports Id
	 * 
	 * Users=1
	 * Admin=2
	 * assets=3
	 * vendors=4
	 * staff=5
	 * visitors=6
	 * 
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value="/comReports")
	public String indexReports(Model map,HttpServletRequest request,ModelMap model)
	{


		logger.info("*****all reports Id********"+request.getParameter("unqId"));

		String reprtId=request.getParameter("unqId");
		map.addAttribute("unqId", request.getParameter("unqId"));
		if(reprtId!=null)
		{
			if(reprtId.equals("1"))
			{
				model.addAttribute("ViewName", "Customer Occupancy");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Customer Occupancy", 1, request);
				breadCrumbService.addNode("Customers Reports", 2, request);

				map.addAttribute("reportName", "Users MIS Reports");
				return "com/reports";
			}
			if(reprtId.equals("2"))
			{
				model.addAttribute("ViewName", "System Security");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("System Security", 1, request);
				breadCrumbService.addNode("Admin Reports", 2, request);


				map.addAttribute("reportName", "Admin MIS Reports");
				return "com/reports";

			}
			if(reprtId.equals("3"))
			{

				model.addAttribute("ViewName", "Asset");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Asset", 1, request);
				breadCrumbService.addNode("Asset Reports", 2, request);

				map.addAttribute("reportName", "Asset & Inventory Tracker MIS Reports");
				return "com/reports";

			}
			if(reprtId.equals("4"))
			{

				model.addAttribute("ViewName", "Vendors");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Vendors", 1, request);
				breadCrumbService.addNode("Vendors Reports", 2, request);

				map.addAttribute("reportName", "Vendors Contracts MIS Reports");
				return "com/reports";

			}
			if(reprtId.equals("5"))
			{

				model.addAttribute("ViewName", "ManPower");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Manpower", 1, request);
				breadCrumbService.addNode("Staff Reports", 2, request);

				map.addAttribute("reportName", "Staff MIS Reports");
				return "com/reports";

			}
			if(reprtId.equals("6"))
			{

				model.addAttribute("ViewName", "Visitors");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Visitors", 1, request);
				breadCrumbService.addNode("Vistors Reports", 2, request);

				map.addAttribute("reportName", "Visitors MIS Reports");
				return "com/reports";

			}
			if(reprtId.equals("8"))
			{
				model.addAttribute("ViewName", "Billing");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Billing", 1, request);
				breadCrumbService.addNode("Billing Reports", 2, request);

				map.addAttribute("reportName", "Billing MIS Reports");
				return "com/reports";
			}
			if(reprtId.equals("9"))
			{
				model.addAttribute("ViewName", "Help Desk");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Help Desk", 1, request);
				breadCrumbService.addNode("Help Desk Reports", 2, request);

				map.addAttribute("reportName", "Help Desk MIS Reports");
				return "com/reports";
			}
			if(reprtId.equals("10"))
			{
				model.addAttribute("ViewName", "Income Tracker");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("Income Tracker", 1, request);
				breadCrumbService.addNode("Income Tracker", 2, request);

				map.addAttribute("reportName", "Income Tracker MIS Reports");
				return "com/reports";
			}
			if(reprtId.equals("11"))
			{
				model.addAttribute("ViewName", "General Ledger");
				breadCrumbService.addNode("nodeID", 0, request);
				breadCrumbService.addNode("General Ledger", 1, request);
				breadCrumbService.addNode("General Ledger Reports", 2, request);

				map.addAttribute("reportName", "General Ledger MIS Reports");
				return "com/reports";
			}
		}

		return "com/reports";

	}

	@RequestMapping(value="/reports/read/{unqId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> readAllreports(@PathVariable int unqId)
	{
		List<Map<String,Object>> data=new ArrayList<>();


		logger.info("********after calling Jsp Json path variable Id*****"+unqId);
		if(unqId==1)//****Users Reports name
		{

			String[] arr={"(MODERATOR ONLY) Residents Database","Allotted Parking Slots","Alternate Address Report - Owners","Alternate Address Report - Tenants","Alternate Address Report"," Deactivated Members"
					,"Family Information","Primary Contacts","Residents Database"," Signature List - All Owners who live here"," Signature List - All Owners","Signature List - All Users who live here"
					,"Signature List - All Users","Signature List - Primary Contacts","Signature List - Tenants","Signature List With Photo - All Users","Un Allotted Parking Slots"
					,"Vehicle Information","Multi Flat Users"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);

			}



			return data;

		}
		if(unqId==2)//********Admin Reports Name
		{
			String[] arr={"Flat Specific Documents"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);
			}

			return data;

		}

		if(unqId==3)//********Assets and Inventory Reports Name
		{
			String[] arr={"Active Assets","Active Assets with SubComponents"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);
			}

			return data;

		}
		if(unqId==4)//********Assets and Inventory Reports Name
		{
			String[] arr={"Active Association Vendors","In-Active Association Vendors"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);
			}

			return data;

		}
		if(unqId==5)//********Staffs  Reports Name
		{
			String[] arr={"List of Drivers by Flats","List of Drivers","List of Staff","List of Staff Active","List of Staff In-Active","Staff Database","Staff Biometric Logs"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);
			}

			return data;

		}
		if(unqId==6)//********Vistors  Reports Name
		{
			String[] arr={"Visitors Currently inside the Complex","Visitors Checked Out History"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);
			}

			return data;

		}
		if(unqId==8)//********Billing Report
		{
			String[] arr={"Monthwise Billing Report","Monthwise Consumption Report","Customerwise Monthly Detailed Billing Report","AMR Data Billing ReportAMR Data Billing Report","Billing and Payment Collection Report"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);

			}

			return data;

		}

		if(unqId==9)//********Helep Desk Report
		{
			String[] arr={"All Ticket","All Closed Community Ticket","All Open Community Ticket","All Closed On-Behalf of Tickets","All Open On-Behalf of Tickets","All Open Personal Tickets","All Closed Personal  Tickets","All Escalted Ticket","Summary Of Ticket","Feedback Reports"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);

			}

			return data;

		}

		if(unqId==10)//********Helep Desk Report
		{
			String[] arr={"Payment Gateway NEFT Txn","Accured Interest Till Date","Payment Gateway Txn","Flat Information For Billing","Member Income Due List","Member Recivable By Account","Resident Payment Posting MonthWise","Refundable Deposit","Miscellaneous Income","Open Resident Invoice","Resident Due","Resident Receipt","Payment Statment Report Account Wise"};
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);

			}

			return data;

		}

		if(unqId==11)//********Helep Desk Report
		{
			String[] arr={"Audit Report:Account Statement by Flat",
					"Audit-Report:Advance Members","OutStanding Final Query","CAM-Report" };
			Map<String,Object> dataaa=null;

			for(int  i=0;i<arr.length;i++)
			{
				dataaa=new HashMap<>();;
				dataaa.put("report", arr[i]);
				dataaa.put("slNo", i+1);
				data.add(dataaa);

			}

			return data;

		}
		return null;

	}



	@RequestMapping(value="/getAll")
	public String allCom(Model model,HttpServletRequest request)
	{
		logger.info("******Inside a Request Method***************"+request.getParameter("jspId"));

		model.addAttribute("data", request.getParameter("jspId"));

		return "com/comAllRecords";
	}

	@RequestMapping(value="/getAllStaffLog")
	public String getAllStaffLog(Model model,HttpServletRequest request){
		logger.info("******Inside a Request Method***************"+request.getParameter("jspId"));

		model.addAttribute("data", request.getParameter("jspId"));

		return "billingPayments/interestAccured";
	}


	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value="/com/readAllData/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readAllComData(@PathVariable int data)
	{
		logger.info("**************data*************************"+data);
		List<Map<String, Object>> allComData=new ArrayList<>();      
		if(data==8)//**********primary Contacts of owners and Tenants
		{
			logger.info("********inside a Primary Contact Of Person*****************");

			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("**********person Size********"+allPersonIds.size());

			for (final Integer personId :allPersonIds ) {



				//int value = count+1;

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(personId);
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;
						if(p.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

						}
						if(p.getPersonType().equalsIgnoreCase("Tenant"))
						{
							tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

						}

						if(tenantId!=0)
						{
							tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
						}

						if(ownerProp!=null&&ownerProp.size()>0)
						{
							put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

						}
						if(tenatnProp!=null&&tenatnProp.size()>0)
						{
							put("flatNo",tenatnProp.get(0).getProperty().getProperty_No());

						}


						for(Contact contact:p.getContacts())
						{

							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email", contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("mobile", contact.getContactContent());

							}


						}


					}
				});



			}
			return allComData;

		}
		if(data==14)//**********signature  primary Contacts of owners and Tenants
		{
			logger.info("********inside a Primary Contact Of Person*****************");

			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("**********person Size********"+allPersonIds.size());

			for (final Integer personId :allPersonIds ) {



				//int value = count+1;

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(personId);
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;
						if(p.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

						}
						if(p.getPersonType().equalsIgnoreCase("Tenant"))
						{
							tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

						}

						if(tenantId!=0)
						{
							tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
						}

						if(ownerProp!=null&&ownerProp.size()>0)
						{
							put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

						}
						if(tenatnProp!=null&&tenatnProp.size()>0)
						{
							put("flatNo",tenatnProp.get(0).getProperty().getProperty_No());

						}


						for(Contact contact:p.getContacts())
						{

							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email", contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("mobile", contact.getContactContent());

							}


						}


					}
				});



			}
			return allComData;

		}


		if(data==3)//**********Owner Alternate Address**********
		{
			List<Integer> allPersonIds=personService.getAllPersonIdList();

			for(final Integer personId:allPersonIds)
			{
				final Person p=personService.find(personId);
				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					allComData.add(new HashMap<String,Object>(){

						{

							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}

							put("title", p.getTitle());
							put("name", p.getFirstName()+str+p.getLastName());
							put("pType",p.getPersonType());
							List<?> ownerProp=null;
							if(p.getPersonType().equalsIgnoreCase("Owner"))
							{
								ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

							}

							if(ownerProp!=null&&ownerProp.size()>0)
							{
								put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

							}
							for(Address address:p.getAddresses())
							{

								if(address.getAddressPrimary().equalsIgnoreCase("yes"))
								{
									String s="";
									String cityName=cityService.find(address.getCityId()).getCityName();
									String stateName=stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName();
									String countryName=countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName();
									if(address.getAddress2()!=null)
									{
										s=address.getAddress2();
									}
									put("address1",address.getAddressNo()+"\n"+address.getAddress1()+"\n"+s+"\n"+cityName+"\n"+stateName+"\n"+countryName);
									put("city",cityName);
									put("state",stateName);
									put("country",countryName);

								}
								else
								{
									put("address2","N/A");
									if(address.getAddress2()!=null)
									{
										String s="";
										if(address.getAddress2()!=null)
										{
											s=address.getAddress2();
										}
										put("address2",address.getAddressNo()+"\n"+address.getAddress1()+"\n"+s+"\n"+cityService.find(address.getCityId()).getCityName()+"\n"+stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName()+"\n"+countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName());		
									}
									else
									{
										put("address2","N/A");
									}
								}
							}



						}
					});


				}


			}
			return allComData;



		}






		if(data==5)//**********Owner/Tenant Alternate Address**********
		{
			List<Integer> allPersonIds=personService.getAllPersonIdList();

			for(final Integer personId:allPersonIds)
			{
				final Person p=personService.find(personId);
				if(p.getPersonType().equalsIgnoreCase("Owner")||p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					allComData.add(new HashMap<String,Object>(){

						{

							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}

							put("title", p.getTitle());
							put("name", p.getFirstName()+str+p.getLastName());
							put("pType",p.getPersonType());
							List<?> ownerProp=null;
							if(p.getPersonType().equalsIgnoreCase("Owner"))
							{
								ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

							}

							if(ownerProp!=null&&ownerProp.size()>0)
							{
								put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

							}

							List<TenantProperty> tenProp=null;
							if(p.getPersonType().equalsIgnoreCase("Tenant"))
							{
								tenProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+p.getTenant().getTenantId());

							}

							if(tenProp!=null&&tenProp.size()>0)
							{
								put("flatNo",tenProp.get(0).getProperty().getProperty_No());

							}
							for(Address address:p.getAddresses())
							{

								if(address.getAddressPrimary().equalsIgnoreCase("yes"))
								{
									String s="";
									String cityName=cityService.find(address.getCityId()).getCityName();
									String stateName=stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName();
									String countryName=countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName();
									if(address.getAddress2()!=null)
									{
										s=address.getAddress2();
									}
									put("address1",address.getAddressNo()+"\n"+address.getAddress1()+"\n"+s+"\n"+cityName+"\n"+stateName+"\n"+countryName);
									put("city",cityName);
									put("state",stateName);
									put("country",countryName);

								}
								else
								{
									put("address2","N/A");
									if(address.getAddress2()!=null)
									{
										String s="";
										if(address.getAddress2()!=null)
										{
											s=address.getAddress2();
										}
										put("address2",address.getAddressNo()+"\n"+address.getAddress1()+"\n"+s+"\n"+cityService.find(address.getCityId()).getCityName()+"\n"+stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName()+"\n"+countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName());		
									}
									else
									{
										put("address2","N/A");
									}
								}
							}



						}
					});


				}


			}
			return allComData;



		}






		if(data==4)//*********Tenant Alternate Address
		{
			List<Integer> allPersonIds=personService.getAllPersonIdList();

			for(final Integer personId:allPersonIds)
			{
				final Person p=personService.find(personId);
				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					allComData.add(new HashMap<String,Object>(){

						{

							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}

							put("title", p.getTitle());
							put("name", p.getFirstName()+str+p.getLastName());
							put("pType",p.getPersonType());
							List<TenantProperty> ownerProp=null;
							if(p.getPersonType().equalsIgnoreCase("Tenant"))
							{
								ownerProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+p.getTenant().getTenantId());

							}

							if(ownerProp!=null&&ownerProp.size()>0)
							{
								put("flatNo",ownerProp.get(0).getProperty().getProperty_No());

							}
							for(Address address:p.getAddresses())
							{

								if(address.getAddressPrimary().equalsIgnoreCase("yes"))
								{
									String s="";
									String cityName=cityService.find(address.getCityId()).getCityName();
									String stateName=stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName();
									String countryName=countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName();
									if(address.getAddress2()!=null)
									{
										s=address.getAddress2();
									}
									put("address1",address.getAddressNo()+"\n"+address.getAddress1()+"\n"+s+"\n"+cityName+"\n"+stateName+"\n"+countryName);
									put("city",cityName);
									put("state",stateName);
									put("country",countryName);

								}
								else
								{
									put("address2","N/A");
									if(address.getAddress2()!=null)
									{
										String s="";
										if(address.getAddress2()!=null)
										{
											s=address.getAddress2();
										}
										put("address2",address.getAddressNo()+"\n"+address.getAddress1()+"\n"+s+"\n"+cityService.find(address.getCityId()).getCityName()+"\n"+stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName()+"\n"+countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName());		
									}
									else
									{
										put("address2","N/A");
									}
								}
							}



						}
					});


				}


			}
			return allComData;



		}

		if(data==19)//***********vehicles Information 
		{
			logger.info("*******inside Vehicle Information**********");
			List<Object[]> allVehclies=(List<Object[]>)vehicleService.findAllVehicals();

			for(final Object[] vehicles:allVehclies)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find((int)vehicles[3]);
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;
						if(p.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

						}
						if(p.getPersonType().equalsIgnoreCase("Tenant"))
						{
							tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

						}

						if(tenantId!=0)
						{
							tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
						}

						if(ownerProp!=null&&ownerProp.size()>0)
						{
							put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

						}
						if(tenatnProp!=null&&tenatnProp.size()>0)
						{
							put("flatNo",tenatnProp.get(0).getProperty().getProperty_No());

						}

						put("rNo", (String)vehicles[4]);
						put("vMk", (String)vehicles[5]);
						put("vModel", (String)vehicles[6]);
						put("vTag", (String)vehicles[7]);
						put("vSlot", (String)vehicles[10]);
						put("vStd", (Timestamp)vehicles[8]);
						put("vEnd", (Timestamp)vehicles[9]);



					}
				});


			}

			return allComData;
		}
		if(data==2)//***********Allotted Parking Slots
		{

			List<?> requestMaster=parkingSlotsAllotmentService.findAllParkingSlot();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> parkingSlotsAllotmentData = null;
			for (Iterator<?> iterator = requestMaster.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				parkingSlotsAllotmentData = new HashMap<String, Object>();
				if("true".equalsIgnoreCase((String)values[12]))
				{
					parkingSlotsAllotmentData.put("psaId", (Integer)values[0]);
					parkingSlotsAllotmentData.put("parkingSlotsNo", (String)values[1]);
					parkingSlotsAllotmentData.put("psId", (Integer)values[2]);
					parkingSlotsAllotmentData.put("property", (String)values[3]);
					parkingSlotsAllotmentData.put("propertyId", (Integer)values[4]);
					parkingSlotsAllotmentData.put("allotmentDateFrom",ConvertDate.TimeStampString((Timestamp)values[5]));	
					if(values[6]!=null){
						parkingSlotsAllotmentData.put("allotmentDateTo",ConvertDate.TimeStampString((Timestamp)values[6]));
					}
					parkingSlotsAllotmentData.put("psRent",(Integer)values[7]);
					if (values[8] != null) {
						parkingSlotsAllotmentData.put("psRentLastRevised", ConvertDate
								.TimeStampString((Timestamp)values[8]));
					}
					if (values[9] != null) {
						parkingSlotsAllotmentData.put("psRentLastRaised", ConvertDate
								.TimeStampString((Timestamp)values[9]));
					}
					parkingSlotsAllotmentData.put("createdBy",(String)values[10]);
					parkingSlotsAllotmentData.put("lastUpdatedBy",(String)values[11]);
					parkingSlotsAllotmentData.put("status",(String)values[12]);

					parkingSlotsAllotmentData.put("block",(String)values[13]);
					parkingSlotsAllotmentData.put("blockId",(Integer)values[14]);
					result.add(parkingSlotsAllotmentData); 
				}
			}
			return result;

		}

		if(data==17)//******Un Alloted Parking  Slots
		{

			List<?> requestMaster=parkingSlotsAllotmentService.findAllParkingSlot();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Map<String, Object> parkingSlotsAllotmentData = null;
			for (Iterator<?> iterator = requestMaster.iterator(); iterator.hasNext();)
			{
				final Object[] values = (Object[]) iterator.next();
				parkingSlotsAllotmentData = new HashMap<String, Object>();
				if("false".equalsIgnoreCase((String)values[12]))
				{
					parkingSlotsAllotmentData.put("psaId", (Integer)values[0]);
					parkingSlotsAllotmentData.put("parkingSlotsNo", (String)values[1]);
					parkingSlotsAllotmentData.put("psId", (Integer)values[2]);
					parkingSlotsAllotmentData.put("property", (String)values[3]);
					parkingSlotsAllotmentData.put("propertyId", (Integer)values[4]);
					parkingSlotsAllotmentData.put("allotmentDateFrom",ConvertDate.TimeStampString((Timestamp)values[5]));	
					if(values[6]!=null){
						parkingSlotsAllotmentData.put("allotmentDateTo",ConvertDate.TimeStampString((Timestamp)values[6]));
					}
					parkingSlotsAllotmentData.put("psRent",(Integer)values[7]);
					if (values[8] != null) {
						parkingSlotsAllotmentData.put("psRentLastRevised", ConvertDate
								.TimeStampString((Timestamp)values[8]));
					}
					if (values[9] != null) {
						parkingSlotsAllotmentData.put("psRentLastRaised", ConvertDate
								.TimeStampString((Timestamp)values[9]));
					}
					parkingSlotsAllotmentData.put("createdBy",(String)values[10]);
					parkingSlotsAllotmentData.put("lastUpdatedBy",(String)values[11]);
					parkingSlotsAllotmentData.put("status",(String)values[12]);

					parkingSlotsAllotmentData.put("block",(String)values[13]);
					parkingSlotsAllotmentData.put("blockId",(Integer)values[14]);
					result.add(parkingSlotsAllotmentData); 
				}
			}
			return result;

		}
		if(data==6)//***********De acticated Memmbers Of Owners and Tenants
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where (p.personType LIKE 'Owner' OR p.personType LIKE 'Tenant') and p.personStatus LIKE 'Inactive'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;
						if(p.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

						}
						if(p.getPersonType().equalsIgnoreCase("Tenant"))
						{
							tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

						}

						if(tenantId!=0)
						{
							tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
						}

						if(ownerProp!=null&&ownerProp.size()>0)
						{
							put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

						}
						if(tenatnProp!=null&&tenatnProp.size()>0)
						{
							put("flatNo",tenatnProp.get(0).getProperty().getProperty_No());

						}
						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());
						put("status", "Inactive");

					}
				});




			}


			return allComData;


		}

		//Flat Information for Billing
		if(data==44){

			List<Map<String, Object>> propertyMap =  new ArrayList<Map<String, Object>>();    

			List<Account> accountList=accountService.executeSimpleQuery("SELECT a FROM Account a");


			for (final Account accountForProperty : accountList) 
			{
				propertyMap.add(new HashMap<String, Object>() {{    

					List<Property> propertyIds=propertyService.executeSimpleQuery("SELECT p FROM Property p where p.propertyId="+accountForProperty.getPropertyId());
					if(!(propertyIds.isEmpty())){
						put("blockName", propertyIds.get(0).getBlocks().getBlockName());
						put("flatNo", propertyIds.get(0).getProperty_No());
						put("parkingSlotsNo",propertyIds.get(0).getNo_of_ParkingSlots());
						put("propertyType", propertyIds.get(0).getPropertyType());
						put("areaType", propertyIds.get(0).getAreaType());
						put("area", propertyIds.get(0).getArea());
						put("property_Floor", propertyIds.get(0).getProperty_Floor());
						put("status",propertyIds.get(0).getStatus());
						put("tenancyHandoverDate",propertyIds.get(0).getTenancyHandoverDate());
						put("propertyBillable",propertyIds.get(0).getPropertyBillable());

					}


					if(accountForProperty.getPerson().getLastName()!=null){
						put("name", accountForProperty.getPerson().getFirstName()+" "+accountForProperty.getPerson().getLastName());
					}


					else{
						put("name", accountForProperty.getPerson().getFirstName());
					}
					put("pType", accountForProperty.getPerson().getPersonType());

				}});

			}


			return propertyMap;

		}


		//Audit-Report:Advance Members
		if(data==46){

			logger.info("In Advance Bill Read Method");
			List<AdvanceBill> billEntities = advanceBillingService.findAll();
			return billEntities;

		}

		if (data == 48) {
			logger.info("In side Outstanding Query Methode");
			//String qry = "select l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||' '||pr.LAST_NAME as Customer_name,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Email') as Email,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Mobile') as Mobile from ledger l,account a,property p,person pr where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID order by account_id";
			//String qry ="select l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||''||pr.LAST_NAME as Customer_name,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Email' and contact_location='Home') as Email,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Mobile' and contact_location='Home') as Mobile from ledger l,account a,property p,person pr where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID and pr.PERSON_TYPE = 'Owner' order by account_id";
		
			
			@SuppressWarnings("unchecked")
			List<Object> queryDetails = entityManager.createNativeQuery(CONSOLIDATEEDreport)
			.getResultList();
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> list = null;
			for (Iterator<?> iterator = queryDetails.iterator(); iterator
					.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				list = new HashMap<>();
				if (values[0] != null) {
					list.put("account_Id", values[0]);
				}
				if (values[3] != null) {
					list.put("account_No", values[3]);
				}
				if (values[4] != null) {
					list.put("property_No", values[4]);
				}
				if (values[5] != null) {
					list.put("person_Name", values[5]);
				}
				if (values[1] != null) {
					list.put("balance", values[1]);
				}
				if (values[2] != null) {
					list.put("ledger_Type", values[2]);
				}
				if (values[6] != null) {
					list.put("email", values[6]);
				}
				if (values[7] != null) {
					list.put("mobile", values[7]);
				}
				resultList.add(list);
			}
			return resultList;
		}

		if(data==1)//******Modearator Resdianial Database
		{

			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("**********person Size********"+allPersonIds.size());
			final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			for (final Integer personId :allPersonIds ) {
				Person p1=personService.find(personId);

				List<OwnerProperty> ownerpropNew=null;

				if(p1.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
				}

				//int value = count+1;
				if(ownerpropNew!=null&&ownerpropNew.size()>0)
				{

					allComData.add(new HashMap<String,Object>(){
						{

							Person p1=personService.find(personId);
							if(p1.getPersonType().equalsIgnoreCase("Owner"))
							{
								List<OwnerProperty> ownerpropNew=null;


								Person p=null;
								if(p1.getPersonType().equalsIgnoreCase("Owner"))
								{
									ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
									if(ownerpropNew.size()>0&&!ownerpropNew.isEmpty())
									{
										p=ownerpropNew.get(0).getOwner().getPerson();
										Date d=ownerpropNew.get(0).getPropertyAquiredDate();
										java.util.Date d2=new Date(d.getTime());
										put("aquiredDate",sdfDate.format(d));
									}
								}

								if(p!=null)
								{

									String str="";
									if(p.getMiddleName()!=null){
										str=p.getMiddleName();
									}

									put("title", p.getTitle());
									put("name", p.getFirstName()+str+p.getLastName());
									put("pType",p.getPersonType());
									put("nationality",p.getNationality());


									int ownerId=0;
									int tenantId=0;
									List<?> ownerProp=null;

									if(p.getPersonType().equalsIgnoreCase("Owner"))
									{
										ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

									}




									if(ownerProp!=null&&ownerProp.size()>0)
									{
										put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());
										put("blockName",propertyService.find(ownerProp.get(0)).getBlocks().getBlockName());
										put("possessionDate", sdfDate.format(propertyService.find(ownerProp.get(0)).getTenancyHandoverDate()));

									}


									for(Contact contact:p.getContacts())
									{

										if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

											put("email", contact.getContactContent());




										}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

											put("mobile", contact.getContactContent());

										}


									}


								}
							}


						}
					});


				}

			}
			return allComData;	


		}





		if(data==30)//Multi Flat Users
		{

			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("**********person Size********"+allPersonIds.size());

			for (final Integer personId :allPersonIds ) {
				Person p1=personService.find(personId);
				List<OwnerProperty> ownerpropNew=null;

				if(p1.getPersonType().equalsIgnoreCase("Owner")){
					ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
				}

				if(ownerpropNew!=null&&ownerpropNew.size()>0){

					allComData.add(new HashMap<String,Object>(){{

						Person p1=personService.find(personId);
						if(p1.getPersonType().equalsIgnoreCase("Owner")){
							List<OwnerProperty> ownerpropNew=null;


							Person p=null;
							if(p1.getPersonType().equalsIgnoreCase("Owner")){
								ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
								if(ownerpropNew.size()>0&&!ownerpropNew.isEmpty())
								{
									p=ownerpropNew.get(0).getOwner().getPerson();
									Date d=ownerpropNew.get(0).getPropertyAquiredDate();
									put("aquiredDate",d);
								}
							}

							if(p!=null){

								String str="";
								if(p.getMiddleName()!=null){
									str=p.getMiddleName();
								}

								put("title", p.getTitle());
								put("name", p.getFirstName()+str+p.getLastName());
								put("pType",p.getPersonType());
								put("pStatus", p.getPersonStatus());




								List<?> ownerProp=null;

								if(p.getPersonType().equalsIgnoreCase("Owner")){
									ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

								}

								if(ownerProp!=null&&ownerProp.size()>0){
									String propertyNo="";
									String blockIds="";
									String blockNames="";
									for (Iterator iterator = ownerProp.iterator(); iterator.hasNext();) {
										int propertyId=(Integer)iterator.next();
										String propertyNames=propertyService.getPropertyNameBasedOnPropertyId(propertyId);
										propertyNo = propertyNo +propertyNames +",";
										int blockId=propertyService.getBlockIdBasedOnPropertyId(propertyId);
										blockIds = blockIds +blockId +",";
									}
									String[] blockIdsArray = blockIds.split(",");
									for(int  i=0;i<blockIdsArray.length;i++)
									{
										if(blockIdsArray[i]!=null && !(blockIdsArray[i].equalsIgnoreCase(""))){
											Blocks block=blocksService.getBlockNameByBlockId(Integer.parseInt(blockIdsArray[i]));
											blockNames=blockNames +block.getBlockName()+",";
										}
									}
									put("flatNo", propertyNo);
									put("blockName",blockNames);

								}


								for(Contact contact:p.getContacts()){

									if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

										put("email", contact.getContactContent());

									}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

										put("mobile", contact.getContactContent());

									}

								}

							}
						}

					}
					});

				}

			}
			return allComData;	


		}

		if(data==10)//***********Owner Who live
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Owner'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;

						if(p.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());


						}


						if(ownerProp!=null&&ownerProp.size()>0)
						{
							put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

							put("blockName",propertyService.find(ownerProp.get(0)).getBlocks().getBlockName());

						}



						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getPersonStatus());


					}
				});




			}


			return allComData;


		}



		if(data==12)//***********All users Owner Who live
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Owner' OR p.personType LIKE 'Tenant'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;

						if(p.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());


						}


						if(ownerProp!=null&&ownerProp.size()>0)
						{
							put("flatNo", propertyService.find(ownerProp.get(0)).getProperty_No());

							put("blockName",propertyService.find(ownerProp.get(0)).getBlocks().getBlockName());

						}


						if(p.getPersonType().equalsIgnoreCase("Tenant"))
						{
							tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

						}

						if(tenantId!=0)
						{
							tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
						}


						if(tenatnProp!=null&&tenatnProp.size()>0)
						{
							put("flatNo",tenatnProp.get(0).getProperty().getProperty_No());

							put("blockName",propertyService.find(tenatnProp.get(0).getPropertyId()).getBlocks().getBlockName());

						}





						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getPersonStatus());


					}
				});




			}


			return allComData;


		}




		if(data==15)//***********Tenant Who live
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Tenant'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());

						int ownerId=0;
						int tenantId=0;
						List<?> ownerProp=null;
						List<TenantProperty> tenatnProp=null;
						if(p.getPersonType().equalsIgnoreCase("Tenant"))
						{
							tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

						}

						if(tenantId!=0)
						{
							tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
						}


						if(tenatnProp!=null&&tenatnProp.size()>0)
						{
							put("flatNo",tenatnProp.get(0).getProperty().getProperty_No());

							put("blockName",propertyService.find(tenatnProp.get(0).getPropertyId()).getBlocks().getBlockName());

						}

						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getPersonStatus());


					}
				});




			}


			return allComData;


		}


		//***Assets Read Active

		if(data==21)
		{
			List<Asset> readAssets=assetService.executeSimpleQuery("select a from Asset a where a.assetStatus LIKE 'Approved'");

			for (final Asset asset:readAssets) {

				allComData.add(new HashMap<String, Object>() {{

					put("assetName", asset.getAssetName());
					put("assetCatHierarchy", asset.getAssetCategoryTree().getTreeHierarchy());
					put("assetType", asset.getAssetType());
					put("ownerShip", asset.getOwnerShip());
					put("assetLocHierarchy", asset.getAssetLocationTree().getAssetlocText());
					put("assetTag", asset.getAssetTag());
					put("assetStatus", asset.getAssetStatus());
					put("vendorName", asset.getVendor().getPerson().getFirstName()+" "+asset.getVendor().getPerson().getLastName());

					if(asset.getPurchaseDate()!=null){
						java.util.Date purchaseDateUtil = asset.getPurchaseDate();
						java.sql.Date purchaseDateSql = new java.sql.Date(purchaseDateUtil.getTime());
						put("purchaseDate", purchaseDateSql);
					}else{
						put("purchaseDate", "");
					}

					if(asset.getWarrantyTill()!=null){
						java.util.Date warrantyTillUtil = asset.getWarrantyTill();
						java.sql.Date warrantyTillSql = new java.sql.Date(warrantyTillUtil.getTime());
						put("warrantyTill", warrantyTillSql);
					}else{
						put("warrantyTill", "");
					}

					if(asset.getAssetLifeExpiry()!=null){
						java.util.Date assetLifeExpiryUtil = asset.getAssetLifeExpiry();
						java.sql.Date  assetLifeExpirySql = new java.sql.Date( assetLifeExpiryUtil.getTime());
						put("assetLifeExpiry", assetLifeExpirySql);
					}else{
						put("assetLifeExpiry", "");
					}


				}});
			}
			return allComData;

		}

		if(data==51){

			List<Asset> list=assetService.getDataForViewReport();
			final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {
				response.add(new HashMap<String, Object>() {{
					final Object[] values = (Object[])i.next();

					put("assetName", (String)values[1]);
					put("assetCatHierarchy", (String)values[5]);
					put("assetType", (String)values[6]);
					put("assetLocHierarchy", (String)values[9]);
					put("assetGeoTag", (String)values[10]);
					put("assetManufacturer", (String)values[14]);
					put("assetModelNo", (String)values[21]);
					put("assetTag", (String)values[23]);
					put("vendorName", (String)values[27]+" "+(String)values[28]);

					if((Date)values[31]!=null){

						put("purchaseDate", sdfDate.format((Date)values[31]));
					}else{
						put("purchaseDate", "");
					}

					List<AssetSpares> assetSpares=assetSparesService.executeSimpleQuery("SELECT a FROM AssetSpares a WHERE a.assetId ="+(Integer)values[0]);
					if(!(assetSpares.isEmpty())){
						put("assetSpare",assetSpares.get(0).getItemMaster().getImName());
						put("partModelNumber", assetSpares.get(0).getPartModelNumber());
					}
					List<AssetMaintenanceCost> assetMaintenanceCost=assetMaintenanceCostService.executeSimpleQuery("SELECT mc FROM AssetMaintenanceCost mc WHERE mc.assetId="+(Integer)values[0]);
					if(!(assetMaintenanceCost.isEmpty())){
						put("assetMainCost", assetMaintenanceCost.get(0).getCostIncurred());	
					}

				}});
			}
			return response;

		}

		// Active Vendor Associaton


		if(data==22)//***********Owner Who live
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Vendor' and p.personStatus LIKE 'Active'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());



						for(Contact contact:p.getContacts())
						{

							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email", contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("mobile", contact.getContactContent());

							}


						}
						put("panNo",p.getVendors().getPanNo());
						put("serviceTax",p.getVendors().getServiceTaxNo());


						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getPersonStatus());


					}
				});




			}


			return allComData;


		}


		if(data==29)//visitor information who are all in Property
		{

			List<VisitorVisits> visitorVisits=visitorVisitsService.findAll();

			for(final VisitorVisits vists:visitorVisits)
			{
				if (vists.getVvstatus().equalsIgnoreCase("IN")) {

					allComData.add(new HashMap<String,Object>(){
						{
							put("visitorName",vists.getVisitor().getVmName());

							put("visitorContact",vists.getVisitor().getVmContactNo());

							put("visitorFrom",vists.getVisitor().getVmFrom());

							put("visitorPurpose",vists.getVPurpose());

							put("vistorProperty",vists.getProperty().getProperty_No());

							put("visitorInDate",vists.getVInDt());
							put("visitorStatus","IN");

						}
					});


				}
			}

			return allComData;
		}



		//**vendor In active members

		if(data==23)//***********
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Vendor' and p.personStatus LIKE 'Inactive'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());



						for(Contact contact:p.getContacts())
						{

							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email", contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("mobile", contact.getContactContent());

							}


						}
						put("panNo",p.getVendors().getPanNo());
						put("serviceTax",p.getVendors().getServiceTaxNo());


						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getPersonStatus());


					}
				});




			}


			return allComData;


		}




		if(data==27)//***********Staff Active Details**************
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{


				Person p=personService.find(person2.getPersonId());
				if(p.getUsers().getStatus().equalsIgnoreCase("Active"))
				{

					allComData.add(new HashMap<String,Object>(){
						{
							Person p=personService.find(person2.getPersonId());

							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}

							put("title", p.getTitle());
							put("name", p.getFirstName()+str+p.getLastName());
							put("pType",p.getPersonType());



							for(Contact contact:p.getContacts())
							{

								if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

									put("email", contact.getContactContent());




								}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

									put("mobile", contact.getContactContent());

								}


							}

							put("dept",p.getUsers().getDepartment().getDept_Name());
							put("desg",p.getUsers().getDesignation().getDn_Name());

							String dRo="";
							for(Role role:p.getUsers().getRoles())
							{
								dRo=dRo+role.getRlName()+" ,";

							}

							put("role",dRo.substring(0,dRo.length()-1));


							put("fatherName",p.getFatherName());
							put("nationality",p.getNationality());

							put("status",p.getUsers().getStatus());


						}
					});
				}




			}


			return allComData;


		}




		if(data==28)//***********Staff  In Active Details**************
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{

				Person p=personService.find(person2.getPersonId());
				if(!p.getUsers().getStatus().equalsIgnoreCase("Active"))
				{
					allComData.add(new HashMap<String,Object>(){
						{

							Person p=personService.find(person2.getPersonId());
							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}

							put("title", p.getTitle());
							put("name", p.getFirstName()+str+p.getLastName());
							put("pType",p.getPersonType());



							for(Contact contact:p.getContacts())
							{

								if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

									put("email", contact.getContactContent());




								}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

									put("mobile", contact.getContactContent());

								}


							}

							put("dept",p.getUsers().getDepartment().getDept_Name());
							put("desg",p.getUsers().getDesignation().getDn_Name());

							String dRo="";
							for(Role role:p.getUsers().getRoles())
							{
								dRo=dRo+role.getRlName()+" ,";

							}

							put("role",dRo.substring(0,dRo.length()-1));


							put("fatherName",p.getFatherName());
							put("nationality",p.getNationality());

							put("status",p.getUsers().getStatus());


						}
					});
				}




			}


			return allComData;


		}

		//List Of Driver By Flat

		if(data==24)
		{


			List<DomesticProperty> readAllByDrivers=domesticPropertyServicel.executeSimpleQuery("select d from DomesticProperty d where d.workNature LIKE 'Driver'");

			for(final DomesticProperty d:readAllByDrivers)
			{

				allComData.add(new HashMap<String,Object>(){
					{
						put("driverName",domesticService.find(d.getDomasticId()).getPerson().getFirstName());
						put("workNature",d.getWorkNature());
						put("vStd",d.getStartDate());
						put("vEnd",d.getEndDate());
						put("flatNo",d.getProperty().getProperty_No());
						logger.info("*****property No**********"+d.getPropertyNo());
						List<OwnerProperty> ownerId=ownerPropertyService.getOwnerPropertyBasedOnPropertyIdAndOwnerId(d.getPropertyId());

						if(ownerId==null||ownerId.isEmpty()||ownerId.size()<=0)
						{
							TenantProperty t	=tenantPropertyService.getTenantPropertyBasedOnId(d.getPropertyId());
							put("name",t.getTenantId().getPerson().getFirstName()+" "+t.getTenantId().getPerson().getLastName());
							put("pType",t.getTenantId().getPerson().getPersonType());
							put("title",t.getTenantId().getPerson().getTitle());
						}
						else
						{
							put("name",ownerId.get(0).getOwner().getPerson().getFirstName()+" "+ownerId.get(0).getOwner().getPerson().getLastName());
							put("pType",ownerId.get(0).getOwner().getPerson().getPersonType());
							put("title",ownerId.get(0).getOwner().getPerson().getTitle());
						}


						Person p=domesticService.find(d.getDomasticId()).getPerson();

						for(Contact contact:p.getContacts())
						{
							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email",contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
								put("Mobile",contact.getContactContent());

							}


						}



					}
				});
			}

			return allComData;

		}









		if(data==26)//***********Staff All Details**************
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff'");
			logger.info("****inside a deactivated members of owenrs and Tenants*********"+person.size());

			for(final Person person2:person)
			{



				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());



						for(Contact contact:p.getContacts())
						{

							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email", contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("mobile", contact.getContactContent());

							}


						}

						put("dept",p.getUsers().getDepartment().getDept_Name());
						put("desg",p.getUsers().getDesignation().getDn_Name());

						String dRo="";
						for(Role role:p.getUsers().getRoles())
						{
							dRo=dRo+role.getRlName()+" ,";

						}

						put("role",dRo.substring(0,dRo.length()-1));


						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getUsers().getStatus());


					}
				});





			}


			return allComData;


		}



		//*****List OF Driver Only


		if(data==25)
		{


			List<DomesticProperty> readAllByDrivers=domesticPropertyServicel.executeSimpleQuery("select d from DomesticProperty d where d.workNature LIKE 'Driver'");

			for(final DomesticProperty d:readAllByDrivers)
			{

				allComData.add(new HashMap<String,Object>(){
					{
						put("driverName",domesticService.find(d.getDomasticId()).getPerson().getFirstName());
						put("title",domesticService.find(d.getDomasticId()).getPerson().getTitle());
						put("pType",domesticService.find(d.getDomasticId()).getPerson().getPersonType());
						put("workNature",d.getWorkNature());
						put("vStd",d.getStartDate());
						put("vEnd",d.getEndDate());

						logger.info("*****property No**********"+d.getPropertyNo());

						Person p=domesticService.find(d.getDomasticId()).getPerson();

						for(Contact contact:p.getContacts())
						{
							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email",contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
								put("mobile",contact.getContactContent());

							}


						}



					}
				});
			}

			return allComData;

		}

		//flat specific document

		if(data==20)
		{

			List<DocumentRepository> doc=documentRepoService.executeSimpleQuery("select d from DocumentRepository d where d.documentType LIKE 'Property'");

			for(final DocumentRepository d:doc)
			{
				final int drGrp=d.getDrGroupId();

				allComData.add(new HashMap<String,Object>(){

					{
						List<Property> prop=null;
						if(drGrp!=0)
						{
							prop=propertyService.executeSimpleQuery("select p from Property p where p.drGroupId="+drGrp);
						}
						if(prop!=null)
						{
							put("blockName",prop.get(0).getBlocks().getBlockName());
							put("flatNo",prop.get(0).getProperty_No());
						}
						else

						{
							put("blockName","N/A");
							put("flatNo","N/A");
						}
						put("docName",d.getDocumentName());
						put("docNum",d.getDocumentDescription());
						put("docType",d.getDocumentNumber());


					}
				});

			}
			return allComData;


		}

		//Visitor Checked Out History

		if(data==42)
		{
			final SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
			sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();   
			for(final VisitorVisits record:visitorVisitsService.findVisitorIn()){

				result.add(new HashMap<String,Object>(){

					{
						put("visitorName", record.getVisitor().getVmName());
						put("vistorProperty", record.getProperty().getProperty_No());
						put("visitorPurpose", record.getVPurpose());

						final String timeformat = "dd/MM/yyyy";
						SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
						Timestamp intime =record.getVInDt();
						if(intime!=null){
							put("visitorInDate", sdf.format(intime));
							put("inTime", sdfTime.format(intime));
						}
						else{
						}
						Timestamp outtime = record.getVOutDt();
						if(outtime!=null){
							put("visitorOutDate", sdf.format(outtime));
							put("outTime", sdfTime.format(outtime));
						}

						put("visitorStatus",record.getVvstatus());

						put("visitorContact", record.getVisitor().getVmContactNo());




					}
				});
			}
			return result;


		}
		if(data==40)//***********Staff DataBase
		{
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff' OR p.personType LIKE 'Staff,Owner'");
			final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));

			for(final Person person2:person)
			{



				allComData.add(new HashMap<String,Object>(){
					{

						Person p=personService.find(person2.getPersonId());
						String str="";
						if(p.getMiddleName()!=null){
							str=p.getMiddleName();
						}

						put("title", p.getTitle());
						put("name", p.getFirstName()+str+p.getLastName());
						put("pType",p.getPersonType());



						for(Contact contact:p.getContacts())
						{

							if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("email", contact.getContactContent());




							}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

								put("mobile", contact.getContactContent());

							}


						}

						for(Address address:p.getAddresses()){
							if(address.getAddressPrimary().equalsIgnoreCase("Yes")){

								put("address", address.getAddress1());

							}else{
								put("address", address.getAddress2());

							}
						}
						put("dept",p.getUsers().getDepartment().getDept_Name());
						put("desg",p.getUsers().getDesignation().getDn_Name());

						String dRo="";
						for(Role role:p.getUsers().getRoles())
						{
							dRo=dRo+role.getRlName()+" ,";

						}

						put("role",dRo.substring(0,dRo.length()-1));


						put("fatherName",p.getFatherName());
						put("nationality",p.getNationality());

						put("status",p.getUsers().getStatus());

						put("languages",p.getLanguagesKnown());
						put("dob",sdfDate.format(p.getDob()));
						put("gender",p.getSex());
						List<PersonAccessCards> list = personAccessCardService.getPersonAccessCardBasedOnPersonId(p.getPersonId());
						if(!(list.isEmpty())){
							Personnel per=msSQLentityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo", list.get(0).getAcId()).getSingleResult();
							put("accessCardNo",per.getNonAbacardNumber());

						}

					}
				});

			}

			return allComData;
		}

		return allComData;
	}



	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/comAll/exportExcel/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	public String getPrimaryContactReport(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws IOException
	{

		ServletOutputStream servletOutputStream=null;
		logger.info("************************ID inside Excel Sheet**********************************"+id);
		if(id==8)//**********primary Contact
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Primary Contact "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Primary Mobile");
			header.createCell(5).setCellValue("Mobile Address Type");
			header.createCell(6).setCellValue("Primary Email");
			header.createCell(7).setCellValue("Email Address Type");


			for(int j = 0; j<=7; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;
			//XSSFCell cell = null;
			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(personId);
				//int value = count+1;

				row.createCell(0).setCellValue(p.getTitle());
				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;
				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

				}
				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

				}

				if(tenantId!=0)
				{
					tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
				}

				if(ownerProp!=null&&ownerProp.size()>0)
				{
					row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());

				}
				if(tenatnProp!=null&&tenatnProp.size()>0)
				{
					logger.info("***********inside a Tenant property************"+tenantId+"**********"+tenatnProp);
					row.createCell(3).setCellValue(tenatnProp.get(0).getProperty().getProperty_No());

				}




				row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(6).setCellValue(contact.getContactContent());

						row.createCell(7).setCellValue(contact.getContactLocation());


					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(4).setCellValue(contact.getContactContent());

						row.createCell(5).setCellValue(contact.getContactLocation());
					}


				}




				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"CustomerTemplate(Primary Contact).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}

		if(id==46)//Audit-Report:Advance Members
		{
			logger.info("-----------Audit-Report:Advance Members--------------------");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Audit-Report:Advance Members"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			String sheetName = "Audit Report Advance Members";//name of sheet

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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Block Name");
			header.createCell(2).setCellValue("Property No");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Person Name"); 
			header.createCell(5).setCellValue("Bill No"); 
			header.createCell(6).setCellValue("Advance Amount");
			header.createCell(7).setCellValue("Advance Bill Date");


			for(int j = 0; j<=7; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			List<Map<String, Object>> result=(List<Map<String, Object>>)advanceBillingService.findAdvanceBillData();
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			int count = 1;
			for (Map<String, Object> map : result) {

				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));
				row.createCell(1).setCellValue((String) map.get("blockName"));
				row.createCell(2).setCellValue((String) map.get("property_No"));
				row.createCell(3).setCellValue((String) map.get("accountNo"));				
				row.createCell(4).setCellValue((String) map.get("personName"));
				row.createCell(5).setCellValue((String) map.get("abBillNo"));
				row.createCell(6).setCellValue((Double) map.get("abBillAmount"));
				if((Date) map.get("abBillDate")!=null){
					row.createCell(7).setCellValue(sdfDate.format((Date) map.get("abBillDate")));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Audit-Report:Advance Members.xlsx"+"\"");

			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();



		}



		if (id == 48) {
			logger.info("-----------Export to Excel FrOutstanding Final Query--------------------");
			String fileName = ResourceBundle.getBundle("application")
					.getString("bfm.exportCsvFilePath")
					+ "Outstanding Final Query"
					+ DateFormatUtils.format(new Date(), "MMM yyyy") + ".xlsx";
			String sheetName = "Outstanding Final Report";// name of sheet

			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(sheetName);

			XSSFRow header = sheet.createRow(0);

			XSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);
			XSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 10);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			header.createCell(0).setCellValue("Account ID");
			header.createCell(1).setCellValue("Account Number");
			header.createCell(2).setCellValue("Property Number");
			header.createCell(3).setCellValue("Person Name");
			header.createCell(4).setCellValue("Balance");
			header.createCell(5).setCellValue("Ledger Type");
			header.createCell(6).setCellValue("Email Id");
			header.createCell(7).setCellValue("Mobile Number");

			for (int j = 0; j <= 7; j++) {
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000);
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			//String qry = "select l.account_id,l.balance,l.ledger_type,a.ACCOUNT_NO,p.PROPERTY_NO,pr.FIRST_NAME||' '||pr.LAST_NAME as Customer_name,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Email') as Email,(select contact_content from contact where person_id=a.PERSON_ID and contact_type='Mobile') as Mobile from ledger l,account a,property p,person pr where l.ell_id in(select max(ell_id) from ledger group by account_id,ledger_type ) and l.account_id=a.account_id and a.PROPERTYID=p.PROPERTY_ID and a.PERSON_ID=pr.PERSON_ID order by account_id";
			
			
			@SuppressWarnings("unchecked")
			List<Object> resultList = entityManager.createNativeQuery(CONSOLIDATEEDreport)
			.getResultList();

			int count = 1;
			for (Iterator<?> iterator = resultList.iterator(); iterator
					.hasNext();) {
				final Object[] value = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				int accid = ((BigDecimal) value[0]).intValue();
				row.createCell(0).setCellValue(accid);
				row.createCell(1).setCellValue((String) value[3]);
				row.createCell(2).setCellValue((String) value[4]);
				row.createCell(3).setCellValue((String) value[5]);
				if (value[1] != null) {
					int bal = ((BigDecimal) value[1]).intValue();
					row.createCell(4).setCellValue(bal);
				}
				row.createCell(5).setCellValue((String) value[2]);
				row.createCell(6).setCellValue((String) value[6]);
				if ((value[7]) != null) {
					try {
						int mob = ((BigDecimal) value[7]).intValue();
						row.createCell(7).setCellValue(mob);
					} catch (Exception e) {
						row.createCell(7).setCellValue((String) value[7]);
					}
				}
				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"inline;filename=\"Outstanding Final Report.xlsx" + "\"");

			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}




		if(id==14)//**********Signature primary Contact
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Signature Primary Contact "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Primary Mobile");
			header.createCell(5).setCellValue("Mobile Address Type");
			header.createCell(6).setCellValue("Primary Email");
			header.createCell(7).setCellValue("Email Address Type");
			header.createCell(8).setCellValue("Signature");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;
			//XSSFCell cell = null;
			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(personId);
				//int value = count+1;

				row.createCell(0).setCellValue(p.getTitle());
				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;
				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

				}
				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

				}

				if(tenantId!=0)
				{
					tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
				}

				if(ownerProp!=null&&ownerProp.size()>0)
				{
					row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());

				}
				if(tenatnProp!=null&&tenatnProp.size()>0)
				{
					logger.info("***********inside a Tenant property************"+tenantId+"**********"+tenatnProp);
					row.createCell(3).setCellValue(tenatnProp.get(0).getProperty().getProperty_No());

				}

				row.createCell(8).setCellValue("");


				row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(6).setCellValue(contact.getContactContent());

						row.createCell(7).setCellValue(contact.getContactLocation());


					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(4).setCellValue(contact.getContactContent());

						row.createCell(5).setCellValue(contact.getContactLocation());
					}


				}




				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"CustomerTemplate(Signature Primary Contact).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}


		if(id==3)//Address Export 
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Alternate Address Report - Owners "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Address 1");
			header.createCell(5).setCellValue("Address 2");
			header.createCell(6).setCellValue("City");
			header.createCell(7).setCellValue("State");
			header.createCell(8).setCellValue("Country");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			int count = 1;
			//XSSFCell cell = null;
			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(personId);
				//int value = count+1;

				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					row.createCell(0).setCellValue(p.getTitle());
					String str="";
					if(p.getMiddleName()!=null){
						str=p.getMiddleName();
					}



					List<?> ownerProp=null;

					if(p.getPersonType().equalsIgnoreCase("Owner"))
					{
						ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

					}

					if(ownerProp!=null&&ownerProp.size()>0)
					{
						row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());

					}

					row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

					row.createCell(2).setCellValue(p.getPersonType());

					for(Address address:p.getAddresses())
					{

						if(address.getAddressPrimary().equalsIgnoreCase("yes"))
						{

							String s="";
							String cityName=cityService.find(address.getCityId()).getCityName();
							String stateName=stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName();
							String countryName=countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName();
							if(address.getAddress2()!=null)
							{
								s=address.getAddress2();
							}
							row.createCell(4).setCellValue((address.getAddressNo()+","+address.getAddress1()));

							row.createCell(6).setCellValue(cityName);
							row.createCell(7).setCellValue(stateName);
							row.createCell(8).setCellValue(countryName);
						}
						else
						{
							String s="";
							if(address.getAddress2()!=null)
							{
								s=address.getAddress2();
							}
							if(address.getAddress2()!=null)
								row.createCell(5).setCellValue(address.getAddressNo()+","+address.getAddress1()+","+s);
							else
								row.createCell(5).setCellValue("N/A");
						}
					}

					count ++;
				}

			}
			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Alternate Address Report - Owners).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}





		if(id==5)// Owner/Tenant Address Export 
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Alternate Address Report "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type(Owner/Tenant)");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Address 1");
			header.createCell(5).setCellValue("Address 2");
			header.createCell(6).setCellValue("City");
			header.createCell(7).setCellValue("State");
			header.createCell(8).setCellValue("Country");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			int count = 1;
			//XSSFCell cell = null;
			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(personId);
				//int value = count+1;

				if(p.getPersonType().equalsIgnoreCase("Owner")||p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					row.createCell(0).setCellValue(p.getTitle());
					String str="";
					if(p.getMiddleName()!=null){
						str=p.getMiddleName();
					}

					List<TenantProperty> tenProp=null;
					if(p.getPersonType().equalsIgnoreCase("Tenant"))
					{
						tenProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+p.getTenant().getTenantId());

					}

					if(tenProp!=null&&tenProp.size()>0)
					{
						row.createCell(3).setCellValue(tenProp.get(0).getProperty().getProperty_No());

					}

					List<?> ownerProp=null;

					if(p.getPersonType().equalsIgnoreCase("Owner"))
					{
						ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

					}

					if(ownerProp!=null&&ownerProp.size()>0)
					{
						row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());

					}

					row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

					row.createCell(2).setCellValue(p.getPersonType());

					for(Address address:p.getAddresses())
					{

						if(address.getAddressPrimary().equalsIgnoreCase("yes"))
						{

							String s="";
							String cityName=cityService.find(address.getCityId()).getCityName();
							String stateName=stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName();
							String countryName=countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName();
							if(address.getAddress2()!=null)
							{
								s=address.getAddress2();
							}
							row.createCell(4).setCellValue((address.getAddressNo()+","+address.getAddress1()));

							row.createCell(6).setCellValue(cityName);
							row.createCell(7).setCellValue(stateName);
							row.createCell(8).setCellValue(countryName);
						}
						else
						{
							String s="";
							if(address.getAddress2()!=null)
							{
								s=address.getAddress2();
							}
							if(address.getAddress2()!=null)
								row.createCell(5).setCellValue(address.getAddressNo()+","+address.getAddress1()+","+s);
							else
								row.createCell(5).setCellValue("N/A");
						}
					}

					count ++;
				}

			}
			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Alternate Address Report).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}




		if(id==4)//Address Export for tenant 
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Alternate Address Report - Tenants "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Address 1");
			header.createCell(5).setCellValue("Address 2");
			header.createCell(6).setCellValue("City");
			header.createCell(7).setCellValue("State");
			header.createCell(8).setCellValue("Country");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			int count = 1;
			//XSSFCell cell = null;
			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(personId);
				//int value = count+1;

				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					row.createCell(0).setCellValue(p.getTitle());
					String str="";
					if(p.getMiddleName()!=null){
						str=p.getMiddleName();
					}


					List<TenantProperty> tenProp=null;
					if(p.getPersonType().equalsIgnoreCase("Tenant"))
					{
						tenProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+p.getTenant().getTenantId());

					}

					if(tenProp!=null&&tenProp.size()>0)
					{
						row.createCell(3).setCellValue(tenProp.get(0).getProperty().getProperty_No());

					}

					row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

					row.createCell(2).setCellValue(p.getPersonType());

					for(Address address:p.getAddresses())
					{

						if(address.getAddressPrimary().equalsIgnoreCase("yes"))
						{

							String s="";
							String cityName=cityService.find(address.getCityId()).getCityName();
							String stateName=stateService.find(cityService.find(address.getCityId()).getStateId()).getStateName();
							String countryName=countryService.find(stateService.find(cityService.find(address.getCityId()).getStateId()).getCountryId()).getCountryName();
							if(address.getAddress2()!=null)
							{
								s=address.getAddress2();
							}
							row.createCell(4).setCellValue((address.getAddressNo()+"\n"+address.getAddress1()));

							row.createCell(6).setCellValue(cityName);
							row.createCell(7).setCellValue(stateName);
							row.createCell(8).setCellValue(countryName);
						}
						else
						{
							String s="";
							if(address.getAddress2()!=null)
							{
								s=address.getAddress2();
							}
							if(address.getAddress2()!=null)
								row.createCell(5).setCellValue(address.getAddressNo()+","+address.getAddress1()+","+s);
							else
								row.createCell(5).setCellValue("N/A");
						}
					}

					count ++;
				}

			}
			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Alternate Address Report - Tenants).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}



		if(id==19)//Export for Vehicle Information 
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Vehicle Information "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Register Number");
			header.createCell(5).setCellValue("Vehicle Make");
			header.createCell(6).setCellValue("vehicle Model");
			header.createCell(7).setCellValue("vehicle Slot NO");
			header.createCell(8).setCellValue("vehicle Tag No");
			header.createCell(9).setCellValue("Vehicle Start Date");
			header.createCell(10).setCellValue("Vehicle End Date");


			for(int j = 0; j<=10; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			int count = 1;
			//XSSFCell cell = null;

			for (Object[] personId :(List<Object[]>)vehicleService.findAllVehicals() ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find((Integer)personId[3]);
				//int value = count+1;

				row.createCell(0).setCellValue(p.getTitle());
				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;
				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

				}
				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

				}

				if(tenantId!=0)
				{
					tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
				}

				if(ownerProp!=null&&ownerProp.size()>0)
				{
					row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());

				}
				if(tenatnProp!=null&&tenatnProp.size()>0)
				{
					logger.info("***********inside a Tenant property************"+tenantId+"**********"+tenatnProp);
					row.createCell(3).setCellValue(tenatnProp.get(0).getProperty().getProperty_No());

				}




				row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());


				row.createCell(4).setCellValue((String)personId[4]);
				row.createCell(5).setCellValue((String)personId[5]);
				row.createCell(6).setCellValue((String)personId[6]);
				row.createCell(8).setCellValue((String)personId[7]);
				row.createCell(7).setCellValue((String)personId[10]);
				row.createCell(9).setCellValue(ConvertDate.TimeStampString((Timestamp)personId[8]));
				row.createCell(10).setCellValue(ConvertDate.TimeStampString((Timestamp)personId[9]));






				count ++;
			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Vehicle Information).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}


		if(id==2)//**********Parking Slots Allotment
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Allotted Parking Slots "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Block Name");
			header.createCell(1).setCellValue("Parking Slot");
			header.createCell(2).setCellValue("Property Number");
			header.createCell(3).setCellValue("Allotment Date From"); 
			header.createCell(4).setCellValue("Allotment Date To");
			header.createCell(5).setCellValue("Rent Rate");
			header.createCell(6).setCellValue("Status");


			for(int j = 0; j<=6; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			int count = 1;
			//XSSFCell cell = null;

			List<?> requestMaster=parkingSlotsAllotmentService.findAllParkingSlot();

			for (Iterator<?> iterator = requestMaster.iterator(); iterator.hasNext();) {



				XSSFRow row = sheet.createRow(count);

				final Object[] values = (Object[]) iterator.next();

				if("true".equalsIgnoreCase((String)values[12]))
				{

					row.createCell(1).setCellValue((String)values[1]);

					row.createCell(2).setCellValue((String)values[3]);

					row.createCell(3).setCellValue(ConvertDate.TimeStampString((Timestamp)values[5]));	
					if(values[6]!=null){
						row.createCell(4).setCellValue(ConvertDate.TimeStampString((Timestamp)values[6]));
					}
					row.createCell(5).setCellValue((Integer)values[7]);
					/* if (values[8] != null) {
					 parkingSlotsAllotmentData.put("psRentLastRevised", ConvertDate
							 .TimeStampString((Timestamp)values[8]));
				 }
				 if (values[9] != null) {
					 parkingSlotsAllotmentData.put("psRentLastRaised", ConvertDate
							 .TimeStampString((Timestamp)values[9]));
				 }*/

					row.createCell(6).setCellValue("Allocated");

					row.createCell(0).setCellValue((String)values[13]);


				}

				count ++;
			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Allotted Parking Slots).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}

		if(id==17)//****************Parking Un allotted Slots
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Un Allotted Parking Slots "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Block Name");
			header.createCell(1).setCellValue("Parking Slot");
			header.createCell(2).setCellValue("Property Number");
			header.createCell(3).setCellValue("Allotment Date From"); 
			header.createCell(4).setCellValue("Allotment Date To");
			header.createCell(5).setCellValue("Rent Rate");
			header.createCell(6).setCellValue("Status");


			for(int j = 0; j<=6; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			int count = 1;
			//XSSFCell cell = null;

			List<?> requestMaster=parkingSlotsAllotmentService.findAllParkingSlot();

			for (Iterator<?> iterator = requestMaster.iterator(); iterator.hasNext();) {



				XSSFRow row = sheet.createRow(count);

				final Object[] values = (Object[]) iterator.next();

				if("true".equalsIgnoreCase((String)values[12]))
				{

					row.createCell(1).setCellValue((String)values[1]);

					row.createCell(2).setCellValue((String)values[3]);

					row.createCell(3).setCellValue(ConvertDate.TimeStampString((Timestamp)values[5]));	
					if(values[6]!=null){
						row.createCell(4).setCellValue(ConvertDate.TimeStampString((Timestamp)values[6]));
					}
					row.createCell(5).setCellValue((Integer)values[7]);
					/* if (values[8] != null) {
						 parkingSlotsAllotmentData.put("psRentLastRevised", ConvertDate
								 .TimeStampString((Timestamp)values[8]));
					 }
					 if (values[9] != null) {
						 parkingSlotsAllotmentData.put("psRentLastRaised", ConvertDate
								 .TimeStampString((Timestamp)values[9]));
					 }*/

					row.createCell(6).setCellValue("De-Allocated");

					row.createCell(0).setCellValue((String)values[13]);


				}

				count ++;
			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Un Allotted Parking Slots).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();


		}



		if(id==6)//***************Deactivate
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Deactivated Members "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type(Owner/Tenant)");
			header.createCell(3).setCellValue("Flat No"); 
			header.createCell(4).setCellValue("Father Name"); 
			header.createCell(5).setCellValue("Nationality");
			header.createCell(6).setCellValue("Status");


			for(int j = 0; j<=6; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where (p.personType LIKE 'Owner' OR p.personType LIKE 'Tenant') and p.personStatus LIKE 'Inactive'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;

				row.createCell(0).setCellValue(p.getTitle());
				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;
				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

				}
				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

				}

				if(tenantId!=0)
				{
					tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
				}

				if(ownerProp!=null&&ownerProp.size()>0)
				{
					row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());

				}
				if(tenatnProp!=null&&tenatnProp.size()>0)
				{
					logger.info("***********inside a Tenant property************"+tenantId+"**********"+tenatnProp);
					row.createCell(3).setCellValue(tenatnProp.get(0).getProperty().getProperty_No());

				}




				row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				row.createCell(4).setCellValue(p.getFatherName());
				row.createCell(5).setCellValue(p.getNationality());
				row.createCell(6).setCellValue("Inactive");




				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Deactivated Members).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}




		if(id==1)//***********Resdintial Database*********************
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Residents Database "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Block Name");    
			header.createCell(3).setCellValue("Person Type");
			header.createCell(4).setCellValue("Flat No"); 
			header.createCell(5).setCellValue("Primary Mobile");
			header.createCell(6).setCellValue("Primary Email");
			header.createCell(7).setCellValue("Acquired Date");
			header.createCell(8).setCellValue("Possession Date");



			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);
				Person p1=personService.find(personId);

				List<OwnerProperty> ownerpropNew=null;

				if(p1.getPersonType().equalsIgnoreCase("Owner")){
					ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
				}

				if(ownerpropNew!=null&&ownerpropNew.size()>0){
					if(p1.getPersonType().equalsIgnoreCase("Owner")){

						Person p=null;
						if(p1.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
							if(ownerpropNew.size()>0&&!ownerpropNew.isEmpty())
							{
								p=ownerpropNew.get(0).getOwner().getPerson();
								Date d=ownerpropNew.get(0).getPropertyAquiredDate();

								if(d!=null){
									row.createCell(7).setCellValue(sdfDate.format(d));
								}
							}
						}

						if(p!=null)
						{


							row.createCell(0).setCellValue(p.getTitle());
							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}

							int ownerId=0;
							int tenantId=0;
							List<?> ownerProp=null;
							List<TenantProperty> tenatnProp=null;
							if(p.getPersonType().equalsIgnoreCase("Owner"))
							{
								ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

							}


							if(ownerProp!=null&&ownerProp.size()>0)
							{
								row.createCell(4).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());
								row.createCell(2).setCellValue(propertyService.find(ownerProp.get(0)).getBlocks().getBlockName());
								row.createCell(8).setCellValue(sdfDate.format(propertyService.find(ownerProp.get(0)).getTenancyHandoverDate()));

							}





							row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

							row.createCell(3).setCellValue(p.getPersonType());
							for(Contact contact:p.getContacts())
							{
								if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

									row.createCell(6).setCellValue(contact.getContactContent());




								}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
									row.createCell(5).setCellValue(contact.getContactContent());


								}


							}
							count ++;
						}
					}
				}



			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Residents Database).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}

		if(id==30)//***********Multi Flat Users*********************
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Multi Flat Users"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
			String sheetName = "Multi Flat Users";//name of sheet

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

			header.createCell(0).setCellValue("Title");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Block Name");    
			header.createCell(3).setCellValue("Person Type");
			header.createCell(4).setCellValue("Flat No"); 
			header.createCell(5).setCellValue("Primary Mobile");
			header.createCell(6).setCellValue("Primary Email");
			header.createCell(7).setCellValue("Status");
			/*header.createCell(7).setCellValue("Acquired Date");*/



			for(int j = 0; j<=7; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;

			for (Integer personId :allPersonIds ) {

				XSSFRow row = sheet.createRow(count);
				Person p1=personService.find(personId);

				List<OwnerProperty> ownerpropNew=null;

				if(p1.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
				}

				if(ownerpropNew!=null&&ownerpropNew.size()>0)
				{

					if(p1.getPersonType().equalsIgnoreCase("Owner"))
					{


						Person p=null;
						if(p1.getPersonType().equalsIgnoreCase("Owner"))
						{
							ownerpropNew=ownerPropertyService.executeSimpleQuery("select o from OwnerProperty o where o.ownerId="+p1.getOwner().getOwnerId()+" and o.residential LIKE 'Yes'");
							if(ownerpropNew.size()>0&&!ownerpropNew.isEmpty())
							{
								p=ownerpropNew.get(0).getOwner().getPerson();
								Date d=ownerpropNew.get(0).getPropertyAquiredDate();
								java.util.Date d2=new Date(d.getTime());
								logger.info("**********Date**********"+d.toString()+"******da******"+d);
								/*row.createCell(7).setCellValue(d2.toString());*/
							}
						}

						if(p!=null)
						{

							row.createCell(7).setCellValue(p.getPersonStatus());
							row.createCell(0).setCellValue(p.getTitle());
							String str="";
							if(p.getMiddleName()!=null){
								str=p.getMiddleName();
							}


							List<?> ownerProp=null;
							if(p.getPersonType().equalsIgnoreCase("Owner"))
							{
								ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

							}


							if(ownerProp!=null&&ownerProp.size()>0)
							{

								String propertyNo="";
								String blockIds="";
								String blockNames="";
								for (Iterator iterator = ownerProp.iterator(); iterator.hasNext();) {
									int propertyId=(Integer)iterator.next();
									String propertyNames=propertyService.getPropertyNameBasedOnPropertyId(propertyId);
									propertyNo = propertyNo +propertyNames +",";
									int blockId=propertyService.getBlockIdBasedOnPropertyId(propertyId);
									blockIds = blockIds +blockId +",";
								}
								String[] blockIdsArray = blockIds.split(",");
								for(int  i=0;i<blockIdsArray.length;i++)
								{
									if(blockIdsArray[i]!=null && !(blockIdsArray[i].equalsIgnoreCase(""))){
										Blocks block=blocksService.getBlockNameByBlockId(Integer.parseInt(blockIdsArray[i]));
										blockNames=blockNames +block.getBlockName()+",";
									}
								}


								row.createCell(4).setCellValue(propertyNo);

								row.createCell(2).setCellValue(blockNames);

							}





							row.createCell(1).setCellValue(p.getFirstName()+" "+str+" "+p.getLastName());

							row.createCell(3).setCellValue(p.getPersonType());
							for(Contact contact:p.getContacts())
							{
								if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

									row.createCell(6).setCellValue(contact.getContactContent());




								}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
									row.createCell(5).setCellValue(contact.getContactContent());


								}


							}
							count ++;
						}
					}
				}



			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Multi Flat Users.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}


		if(id==10)//***************All Owner Who Live
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Signature List - All Owners who live here "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Block Name");
			header.createCell(4).setCellValue("Flat No"); 
			header.createCell(5).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("Status");
			header.createCell(8).setCellValue("Signature");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Owner'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;


				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;
				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

				}

				if(ownerProp!=null&&ownerProp.size()>0)
				{
					row.createCell(4).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());
					row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getBlocks().getBlockName());

				}



				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				row.createCell(5).setCellValue(p.getFatherName());
				row.createCell(6).setCellValue(p.getNationality());
				row.createCell(7).setCellValue(p.getPersonStatus());
				row.createCell(8).setCellValue("");




				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Signature List - All Owners who live here).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}





		if(id==15)//***************All Tenant Who Live
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Signature List - Tenants "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Block Name");
			header.createCell(4).setCellValue("Flat No"); 
			header.createCell(5).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("Status");
			header.createCell(8).setCellValue("Signature");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Tenant'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;


				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;

				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

				}

				if(tenantId!=0)
				{
					tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
				}


				if(tenatnProp!=null&&tenatnProp.size()>0)
				{
					row.createCell(4).setCellValue(tenatnProp.get(0).getProperty().getProperty_No());

					row.createCell(3).setCellValue(propertyService.find(tenatnProp.get(0).getPropertyId()).getBlocks().getBlockName());

				}



				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				row.createCell(5).setCellValue(p.getFatherName());
				row.createCell(6).setCellValue(p.getNationality());
				row.createCell(7).setCellValue(p.getPersonStatus());
				row.createCell(8).setCellValue("");




				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Signature List - Tenants).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}


		if(id==44)//Flat Information for Billing
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Flat Information for Billing "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




			String sheetName = "Flat Billing";//name of sheet

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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Block");
			header.createCell(2).setCellValue("Property No");
			header.createCell(3).setCellValue("Property Type"); 
			header.createCell(4).setCellValue("Property floor");
			header.createCell(5).setCellValue("Area");
			header.createCell(6).setCellValue("Area Type"); 
			header.createCell(7).setCellValue("Parking Slots");
			header.createCell(8).setCellValue("Property Billable");
			header.createCell(9).setCellValue("Person Name");
			header.createCell(10).setCellValue("Person Type");



			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;


			List<Account> accountList=accountService.executeSimpleQuery("SELECT a FROM Account a");


			for (final Account accountForProperty : accountList) 
			{


				List<Property> propertyIds=propertyService.executeSimpleQuery("SELECT p FROM Property p where p.propertyId="+accountForProperty.getPropertyId());

				XSSFRow row = sheet.createRow(count);

				row.createCell(0).setCellValue(String.valueOf(count));
				row.createCell(1).setCellValue(propertyIds.get(0).getBlocks().getBlockName());
				row.createCell(2).setCellValue(propertyIds.get(0).getProperty_No());
				row.createCell(3).setCellValue(propertyIds.get(0).getPropertyType());
				row.createCell(4).setCellValue(propertyIds.get(0).getProperty_Floor());
				row.createCell(5).setCellValue(propertyIds.get(0).getArea());
				row.createCell(6).setCellValue(propertyIds.get(0).getAreaType());
				row.createCell(7).setCellValue(propertyIds.get(0).getNo_of_ParkingSlots());
				row.createCell(8).setCellValue(propertyIds.get(0).getPropertyBillable());

				if(accountForProperty.getPerson().getLastName()!=null){
					row.createCell(9).setCellValue(accountForProperty.getPerson().getFirstName()+" "+accountForProperty.getPerson().getLastName());

				}
				else{
					row.createCell(9).setCellValue(accountForProperty.getPerson().getFirstName());

				}
				row.createCell(10).setCellValue(accountForProperty.getPerson().getPersonType());


				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Flat Information for Billing.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}







		if(id==12)//***************All Users  Who Live
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Signature List - All Users who live here "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Block Name");
			header.createCell(4).setCellValue("Flat No"); 
			header.createCell(5).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("Status");
			header.createCell(8).setCellValue("Signature");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Tenant' OR p.personType LIKE 'Owner'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;


				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				int ownerId=0;
				int tenantId=0;
				List<?> ownerProp=null;
				List<TenantProperty> tenatnProp=null;

				if(p.getPersonType().equalsIgnoreCase("Owner"))
				{
					ownerProp=ownerService.getPropertyIdBasedOnownerId(p.getOwner().getOwnerId());

				}

				if(ownerProp!=null&&ownerProp.size()>0)
				{
					row.createCell(4).setCellValue(propertyService.find(ownerProp.get(0)).getProperty_No());
					row.createCell(3).setCellValue(propertyService.find(ownerProp.get(0)).getBlocks().getBlockName());

				}

				if(p.getPersonType().equalsIgnoreCase("Tenant"))
				{
					tenantId=tenantSevice.getTenantIdByInstanceOfPersonId(p.getPersonId());

				}

				if(tenantId!=0)
				{
					tenatnProp=tenantPropertyService.executeSimpleQuery("select p from TenantProperty p where p.tenantId="+tenantId);
				}


				if(tenatnProp!=null&&tenatnProp.size()>0)
				{
					row.createCell(4).setCellValue(tenatnProp.get(0).getProperty().getProperty_No());

					row.createCell(3).setCellValue(propertyService.find(tenatnProp.get(0).getPropertyId()).getBlocks().getBlockName());

				}



				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				row.createCell(5).setCellValue(p.getFatherName());
				row.createCell(6).setCellValue(p.getNationality());
				row.createCell(7).setCellValue(p.getPersonStatus());
				row.createCell(8).setCellValue("");




				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"OwnerTemplates(Signature List - All Users who live here).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}


		if(id==21)//**********assets and Inventory
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Assets "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";


			List<Integer> allPersonIds=personService.getAllPersonIdList();
			logger.info("*****person Size in Primary Owner Contact Type***********"+allPersonIds.size());
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

			header.createCell(0).setCellValue("Asset Name");
			header.createCell(1).setCellValue("Asset category");
			header.createCell(2).setCellValue("Asset Type");
			header.createCell(3).setCellValue("Ownership"); 
			header.createCell(4).setCellValue("Asset Location");
			header.createCell(5).setCellValue("Asset Tag");
			header.createCell(6).setCellValue("Vendor Name");
			header.createCell(7).setCellValue("Purchase Date");
			header.createCell(8).setCellValue("Status");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;




			List<Asset> readAssets=assetService.executeSimpleQuery("select a from Asset a where a.assetStatus LIKE 'Approved'");

			for (final Asset asset:readAssets) {

				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue("");

				row.createCell(0).setCellValue(asset.getAssetName());
				row.createCell(1).setCellValue(asset.getAssetCategoryTree().getTreeHierarchy());
				row.createCell(2).setCellValue(asset.getAssetType());
				row.createCell(3).setCellValue(asset.getOwnerShip());
				row.createCell(4).setCellValue(asset.getAssetLocationTree().getAssetlocText());
				row.createCell(5).setCellValue(asset.getAssetTag());
				row.createCell(8).setCellValue(asset.getAssetStatus());
				row.createCell(6).setCellValue(asset.getVendor().getPerson().getFirstName()+" "+asset.getVendor().getPerson().getLastName());

				if(asset.getPurchaseDate()!=null){
					java.util.Date purchaseDateUtil = asset.getPurchaseDate();
					java.sql.Date purchaseDateSql = new java.sql.Date(purchaseDateUtil.getTime());
					row.createCell(7).setCellValue(purchaseDateSql.toString());
				}else{
					row.createCell(7).setCellValue("");
				}


				count ++;

			}


			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"CustomerTemplate(Assets Active List).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}

		if(id==51)//**********assets and Inventory
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Active Assets with SubComponents"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			String sheetName = "Assets with SubComponents";//name of sheet

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

			header.createCell(0).setCellValue("Asset Name");
			header.createCell(1).setCellValue("Asset category");
			header.createCell(2).setCellValue("Asset Type");
			header.createCell(3).setCellValue("Ownership"); 
			header.createCell(4).setCellValue("Asset Location");
			header.createCell(5).setCellValue("Asset Tag");
			header.createCell(6).setCellValue("Vendor Name");
			header.createCell(7).setCellValue("Purchase Date");
			header.createCell(8).setCellValue("Asset Geo Tag");
			header.createCell(9).setCellValue("Asset Manufacture");
			header.createCell(10).setCellValue("Asset Model No");
			header.createCell(11).setCellValue("Asset Spare");
			header.createCell(12).setCellValue("Spare Model No");
			header.createCell(13).setCellValue("Maintenance Cost");



			for(int j = 0; j<=13; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;
			List<Asset> list=assetService.getDataForViewReport();
			final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {

				final Object[] values = (Object[])i.next();

				XSSFRow row = sheet.createRow(count);

				row.createCell(0).setCellValue((String)values[1]);
				row.createCell(1).setCellValue((String)values[5]);
				row.createCell(2).setCellValue((String)values[6]);
				row.createCell(3).setCellValue((String)values[7]);
				row.createCell(4).setCellValue((String)values[9]);
				row.createCell(5).setCellValue((String)values[23]);
				row.createCell(6).setCellValue((String)values[27]+" "+(String)values[28]);
				if((Date)values[31]!=null){
					row.createCell(7).setCellValue(sdfDate.format((Date)values[31]));

				}
				row.createCell(8).setCellValue((String)values[10]);
				row.createCell(9).setCellValue((String)values[14]);
				row.createCell(10).setCellValue((String)values[21]);
				List<AssetSpares> assetSpares=assetSparesService.executeSimpleQuery("SELECT a FROM AssetSpares a WHERE a.assetId ="+(Integer)values[0]);
				if(!(assetSpares.isEmpty())){
					row.createCell(11).setCellValue(assetSpares.get(0).getItemMaster().getImName());
					row.createCell(12).setCellValue(assetSpares.get(0).getPartModelNumber());
				}
				List<AssetMaintenanceCost> assetMaintenanceCost=assetMaintenanceCostService.executeSimpleQuery("SELECT mc FROM AssetMaintenanceCost mc WHERE mc.assetId="+(Integer)values[0]);
				if(!(assetMaintenanceCost.isEmpty())){
					row.createCell(13).setCellValue(assetMaintenanceCost.get(0).getCostIncurred());

				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Active Assets with SubComponents.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}

		//******vendor Active Export


		if(id==22)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Vendor Active"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Mobile");
			header.createCell(4).setCellValue("Email"); 
			/*header.createCell(5).setCellValue("Father Name");*/ 
			header.createCell(5).setCellValue("Nationality");
			header.createCell(6).setCellValue("Pan Number");
			header.createCell(7).setCellValue("Service Tax Number");

			header.createCell(8).setCellValue("Status");


			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Vendor' and p.personStatus LIKE 'Active'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;


				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(4).setCellValue(contact.getContactContent());




					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(3).setCellValue(contact.getContactContent());

					}


				}



				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				/*row.createCell(5).setCellValue(p.getFatherName());*/
				row.createCell(5).setCellValue(p.getNationality());
				row.createCell(6).setCellValue(p.getVendors().getPanNo());
				row.createCell(7).setCellValue(p.getVendors().getServiceTaxNo());
				row.createCell(8).setCellValue(p.getPersonStatus());





				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Vendors(Vendors Active).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}



		//********vendors Deactivate

		if(id==23)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Vendor In Active"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Mobile");
			header.createCell(4).setCellValue("Email"); 
			header.createCell(5).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("Pan Number");
			header.createCell(8).setCellValue("Service Tax Number");

			header.createCell(9).setCellValue("Status");


			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Vendor' and p.personStatus LIKE 'Inactive'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;


				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(4).setCellValue(contact.getContactContent());




					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(3).setCellValue(contact.getContactContent());

					}


				}



				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				row.createCell(5).setCellValue(p.getFatherName());
				row.createCell(6).setCellValue(p.getNationality());
				row.createCell(7).setCellValue(p.getVendors().getPanNo());
				row.createCell(8).setCellValue(p.getVendors().getServiceTaxNo());
				row.createCell(9).setCellValue(p.getPersonStatus());





				count ++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Vendors(Vendors In Active).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}	



		if(id==29)//*********visior Informationm in**********
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Visitors Currently Inside the Complex"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Visitor Name");
			header.createCell(2).setCellValue("Visior Contact");
			header.createCell(3).setCellValue("Visitor From");
			header.createCell(4).setCellValue("Visitor Purpose"); 
			header.createCell(5).setCellValue("Visiting Property"); 
			header.createCell(6).setCellValue("Visitor In Date");
			header.createCell(7).setCellValue("Status");




			for(int j = 0; j<=7; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			List<VisitorVisits> visitorVisits=visitorVisitsService.findAll();
			int count = 1;
			//XSSFCell cell = null;
			for(final VisitorVisits vists:visitorVisits)
			{

				XSSFRow row = sheet.createRow(count);
				if (vists.getVvstatus().equalsIgnoreCase("IN")) {


					row.createCell(0).setCellValue(count);
					row.createCell(1).setCellValue(vists.getVisitor().getVmName());

					row.createCell(2).setCellValue(vists.getVisitor().getVmContactNo());
					row.createCell(3).setCellValue(vists.getVisitor().getVmFrom());

					row.createCell(4).setCellValue(vists.getVPurpose());

					row.createCell(5).setCellValue(vists.getProperty().getProperty_No());

					row.createCell(6).setCellValue(ConvertDate.TimeStampString(vists.getVInDt()));

					row.createCell(7).setCellValue("IN");


					count ++;
				}
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Visitors(Visitors Currently Inside the Complex).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}


		if(id==42)//*********visior Checked Out History**********
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Visitors Checked Out History"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			String sheetName = "Visitors History";//name of sheet

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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Visitor Name");
			header.createCell(2).setCellValue("Visiting Flats"); 
			header.createCell(3).setCellValue("Purpose Of Visit"); 
			header.createCell(4).setCellValue("Visitor In Date");
			header.createCell(5).setCellValue("Visitor Out Date");
			header.createCell(6).setCellValue("In Time");
			header.createCell(7).setCellValue("Visitor Contact");
			header.createCell(8).setCellValue("Visitor Status");
			header.createCell(9).setCellValue("Out Time");




			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}



			List<VisitorVisits> visitorVisits=visitorVisitsService.findAll();
			int count = 1;

			for(final VisitorVisits vists:visitorVisits)
			{

				final String timeformat = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
				sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));

				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(count);
				row.createCell(1).setCellValue(vists.getVisitor().getVmName());
				row.createCell(2).setCellValue(vists.getProperty().getProperty_No());
				row.createCell(3).setCellValue(vists.getVPurpose());
				if(vists.getVInDt()!=null){
					row.createCell(4).setCellValue(sdf.format(vists.getVInDt()));
					row.createCell(6).setCellValue(sdfTime.format(vists.getVInDt()));
				}
				if(vists.getVOutDt()!=null){
					row.createCell(5).setCellValue(sdf.format(vists.getVOutDt()));
					row.createCell(9).setCellValue(sdfTime.format(vists.getVOutDt()));
				}
				row.createCell(7).setCellValue(vists.getVisitor().getVmContactNo());
				row.createCell(8).setCellValue(vists.getVvstatus());

				count ++;

			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Visitors Checked Out History.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		//***********User Active


		if(id==27)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Staff Active Members"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Mobile");
			header.createCell(4).setCellValue("Email"); 
			header.createCell(5).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("DepartMent");
			header.createCell(8).setCellValue("Designation");

			header.createCell(9).setCellValue("Roles");

			header.createCell(10).setCellValue("Status");


			for(int j = 0; j<=10; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);


				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;

				if(p.getUsers().getStatus().equalsIgnoreCase("Active"))
				{

					String str="";
					if(p.getMiddleName()!=null){
						str=p.getMiddleName();
					}

					for(Contact contact:p.getContacts())
					{
						if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

							row.createCell(4).setCellValue(contact.getContactContent());




						}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
							row.createCell(3).setCellValue(contact.getContactContent());

						}


					}



					row.createCell(0).setCellValue(String.valueOf(count));

					row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

					row.createCell(2).setCellValue(p.getPersonType());
					row.createCell(5).setCellValue(p.getFatherName());
					row.createCell(6).setCellValue(p.getNationality());
					row.createCell(7).setCellValue(p.getUsers().getDepartment().getDept_Name());
					row.createCell(8).setCellValue(p.getUsers().getDesignation().getDn_Name());

					String s="";
					for(Role r:p.getUsers().getRoles())
					{
						s=s+r.getRlName()+",";
					}

					row.createCell(9).setCellValue(s.substring(0, s.length()-1));
					row.createCell(10).setCellValue(p.getUsers().getStatus());





					count ++;
				}

			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Staff(Staff Active).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}	


		if(id==28)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Staff IN-Active Members"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Mobile");
			header.createCell(4).setCellValue("Email"); 
			header.createCell(5).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("DepartMent");
			header.createCell(8).setCellValue("Designation");

			header.createCell(9).setCellValue("Roles");

			header.createCell(10).setCellValue("Status");


			for(int j = 0; j<=10; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff'");
			int count = 1;
			//XSSFCell cell = null;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());
				//int value = count+1;

				if(!p.getUsers().getStatus().equalsIgnoreCase("Active"))
				{

					String str="";
					if(p.getMiddleName()!=null){
						str=p.getMiddleName();
					}

					for(Contact contact:p.getContacts())
					{
						if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

							row.createCell(4).setCellValue(contact.getContactContent());




						}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
							row.createCell(3).setCellValue(contact.getContactContent());

						}


					}



					row.createCell(0).setCellValue(String.valueOf(count));

					row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

					row.createCell(2).setCellValue(p.getPersonType());
					row.createCell(5).setCellValue(p.getFatherName());
					row.createCell(6).setCellValue(p.getNationality());
					row.createCell(7).setCellValue(p.getUsers().getDepartment().getDept_Name());
					row.createCell(8).setCellValue(p.getUsers().getDesignation().getDn_Name());

					String s="";
					for(Role r:p.getUsers().getRoles())
					{
						s=s+r.getRlName()+",";
					}

					row.createCell(9).setCellValue(s.substring(0, s.length()-1));
					row.createCell(10).setCellValue(p.getUsers().getStatus());





					count ++;
				}
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Staff(Staff Active).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}	

		//*****Domestic Help

		if(id==24)
		{



			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"List Of Driver By Flat"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Flat Number");
			header.createCell(4).setCellValue("Driver Name");
			header.createCell(5).setCellValue("Work nature");
			header.createCell(6).setCellValue("Start Date"); 
			header.createCell(7).setCellValue("End Date");
			header.createCell(8).setCellValue("Email");
			header.createCell(9).setCellValue("Mobile");






			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<DomesticProperty> readAllByDrivers=domesticPropertyServicel.executeSimpleQuery("select d from DomesticProperty d where d.workNature LIKE 'Driver'");
			int count = 1;
			//XSSFCell cell = null;

			for(final DomesticProperty d:readAllByDrivers)
			{
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(count);

				row.createCell(4).setCellValue(domesticService.find(d.getDomasticId()).getPerson().getFirstName());
				row.createCell(5).setCellValue(d.getWorkNature());
				row.createCell(6).setCellValue(ConvertDate.TimeStampString(d.getStartDate()));
				row.createCell(7).setCellValue(ConvertDate.TimeStampString(d.getEndDate()));
				row.createCell(3).setCellValue(d.getProperty().getProperty_No());
				logger.info("*****property No**********"+d.getPropertyNo());
				List<OwnerProperty> ownerId=ownerPropertyService.getOwnerPropertyBasedOnPropertyIdAndOwnerId(d.getPropertyId());

				if(ownerId==null||ownerId.isEmpty()||ownerId.size()<=0)
				{
					TenantProperty t	=tenantPropertyService.getTenantPropertyBasedOnId(d.getPropertyId());
					row.createCell(1).setCellValue(t.getTenantId().getPerson().getFirstName()+" "+t.getTenantId().getPerson().getLastName());
					row.createCell(2).setCellValue(t.getTenantId().getPerson().getPersonType());

				}
				else
				{
					row.createCell(1).setCellValue(ownerId.get(0).getOwner().getPerson().getFirstName()+" "+ownerId.get(0).getOwner().getPerson().getLastName());
					row.createCell(2).setCellValue(ownerId.get(0).getOwner().getPerson().getPersonType());

				}

				Person p=domesticService.find(d.getDomasticId()).getPerson();

				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(8).setCellValue(contact.getContactContent());




					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(9).setCellValue(contact.getContactContent());

					}


				} 
				count ++;




			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Domestic(List of Drivers By Flat).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();




		}



		//*********LISt OF Drivers*************


		if(id==25)
		{



			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"List Of Drivers"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Mobile");
			header.createCell(3).setCellValue("Email");
			header.createCell(4).setCellValue("Father Name");
			header.createCell(5).setCellValue("Work nature");
			header.createCell(6).setCellValue("Start Date"); 
			header.createCell(7).setCellValue("End Date");






			for(int j = 0; j<=7; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<DomesticProperty> readAllByDrivers=domesticPropertyServicel.executeSimpleQuery("select d from DomesticProperty d where d.workNature LIKE 'Driver'");
			int count = 1;
			//XSSFCell cell = null;

			for(final DomesticProperty d:readAllByDrivers)
			{
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(count);

				row.createCell(1).setCellValue(domesticService.find(d.getDomasticId()).getPerson().getFirstName());
				row.createCell(5).setCellValue(d.getWorkNature());
				row.createCell(6).setCellValue(ConvertDate.TimeStampString(d.getStartDate()));
				row.createCell(7).setCellValue(ConvertDate.TimeStampString(d.getEndDate()));
				row.createCell(4).setCellValue(domesticService.find(d.getDomasticId()).getPerson().getFatherName());
				logger.info("*****property No**********"+d.getPropertyNo());

				Person p=domesticService.find(d.getDomasticId()).getPerson();

				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(2).setCellValue(contact.getContactContent());




					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(3).setCellValue(contact.getContactContent());

					}


				}

				count ++;




			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Domestic(List of Drivers ).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();




		}

		//list of all staffs



		if(id==26)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Staff All"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Mobile");
			header.createCell(4).setCellValue("Email"); 
			/*header.createCell(5).setCellValue("Father Name");*/ 
			header.createCell(5).setCellValue("Nationality");
			header.createCell(6).setCellValue("DepartMent");
			header.createCell(7).setCellValue("Designation");

			header.createCell(8).setCellValue("Roles");

			header.createCell(9).setCellValue("Status");


			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}


			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff'");
			int count = 1;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());

				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				for(Contact contact:p.getContacts())
				{
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(4).setCellValue(contact.getContactContent());




					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(3).setCellValue(contact.getContactContent());

					}


				}



				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				/*row.createCell(5).setCellValue(p.getFatherName());*/
				row.createCell(5).setCellValue(p.getNationality());
				row.createCell(6).setCellValue(p.getUsers().getDepartment().getDept_Name());
				row.createCell(7).setCellValue(p.getUsers().getDesignation().getDn_Name());

				String s="";
				for(Role r:p.getUsers().getRoles())
				{
					s=s+r.getRlName()+",";
				}

				row.createCell(8).setCellValue(s.substring(0, s.length()-1));
				row.createCell(9).setCellValue(p.getUsers().getStatus());





				count ++;

			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Staff(Staff All).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}	

		if(id==40)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Staff dataBase"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
			String sheetName = "Staff dataBase";//name of sheet

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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Staff Name");
			header.createCell(2).setCellValue("Person Type");
			header.createCell(3).setCellValue("Mobile");
			header.createCell(4).setCellValue("Email"); 
			header.createCell(13).setCellValue("Father Name"); 
			header.createCell(6).setCellValue("Nationality");
			header.createCell(7).setCellValue("DepartMent");
			header.createCell(8).setCellValue("Designation");

			header.createCell(9).setCellValue("Roles");

			header.createCell(10).setCellValue("Status");
			header.createCell(11).setCellValue("Languages");
			header.createCell(12).setCellValue("Address");
			header.createCell(5).setCellValue("Access Card No");
			header.createCell(14).setCellValue("Gender");
			header.createCell(15).setCellValue("DOB");


			for(int j = 0; j<=15; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			List<Person> person=personService.executeSimpleQuery("select p from Person p where p.personType LIKE 'Staff' OR p.personType LIKE 'Staff,Owner'");
			int count = 1;

			for (Person perso2 :person ) {

				XSSFRow row = sheet.createRow(count);

				Person p=personService.find(perso2.getPersonId());

				String str="";
				if(p.getMiddleName()!=null){
					str=p.getMiddleName();
				}

				for(Contact contact:p.getContacts()){
					if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){

						row.createCell(4).setCellValue(contact.getContactContent());
					}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
						row.createCell(3).setCellValue(contact.getContactContent());

					}
				}



				for(Address address:p.getAddresses()){
					if(address.getAddressPrimary().equalsIgnoreCase("Yes")){

						row.createCell(12).setCellValue(address.getAddress1());

					}else{
						row.createCell(12).setCellValue(address.getAddress2());

					}
				}
				row.createCell(0).setCellValue(String.valueOf(count));

				row.createCell(1).setCellValue(p.getTitle()+"."+p.getFirstName()+" "+str+" "+p.getLastName());

				row.createCell(2).setCellValue(p.getPersonType());
				row.createCell(13).setCellValue(p.getFatherName());
				row.createCell(6).setCellValue(p.getNationality());
				row.createCell(7).setCellValue(p.getUsers().getDepartment().getDept_Name());
				row.createCell(8).setCellValue(p.getUsers().getDesignation().getDn_Name());

				row.createCell(11).setCellValue(p.getLanguagesKnown());
				row.createCell(14).setCellValue(p.getSex());

				row.createCell(15).setCellValue(sdfDate.format(p.getDob()));

				List<PersonAccessCards> list = personAccessCardService.getPersonAccessCardBasedOnPersonId(p.getPersonId());
				if(!(list.isEmpty())){
					Personnel per=msSQLentityManager.createNamedQuery("Personnel.findByObjectIdLo",Personnel.class).setParameter("objectIdLo", list.get(0).getAcId()).getSingleResult();
					row.createCell(5).setCellValue(per.getNonAbacardNumber()); 
				}
				String s="";
				for(Role r:p.getUsers().getRoles())
				{
					s=s+r.getRlName()+",";
				}

				row.createCell(9).setCellValue(s.substring(0, s.length()-1));
				row.createCell(10).setCellValue(p.getUsers().getStatus());


				count ++;

			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Staff dataBase.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}


		///Property Docs********



		if(id==20)//***************
		{

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Flat By Document"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Block Name");
			header.createCell(2).setCellValue("Flat No");
			header.createCell(3).setCellValue("Document Name");
			header.createCell(4).setCellValue("Document Number"); 
			header.createCell(5).setCellValue("Document Description"); 
			header.createCell(6).setCellValue("Document Type");




			for(int j = 0; j<=6; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}





			int count = 1;
			//XSSFCell cell = null;



			List<DocumentRepository> doc=documentRepoService.executeSimpleQuery("select d from DocumentRepository d where d.documentType LIKE 'Property'");

			for(final DocumentRepository d:doc)
			{
				XSSFRow row = sheet.createRow(count);


				int drGrp=d.getDrGroupId();
				row.createCell(0).setCellValue(count);
				List<Property> prop=null;
				if(drGrp!=0)
				{
					prop=propertyService.executeSimpleQuery("select p from Property p where p.drGroupId="+drGrp);
				}
				if(prop!=null)
				{
					row.createCell(1).setCellValue(prop.get(0).getBlocks().getBlockName());
					row.createCell(2).setCellValue(prop.get(0).getProperty_No());
				}
				else

				{
					row.createCell(1).setCellValue("N/A");
					row.createCell(2).setCellValue("N/A");
				}
				row.createCell(3).setCellValue(d.getDocumentName());
				row.createCell(5).setCellValue(d.getDocumentDescription());
				row.createCell(4).setCellValue(d.getDocumentNumber());
				row.createCell(6).setCellValue("Property");

				count ++;

			}


			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Document(Flat By Document).xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}	





		logger.info("********response*********"+response);
		PrintWriter out=response.getWriter();


		out.println("<html><b>No Record Found</b></html>");

		return null;
	}



	@RequestMapping(value="/getAllBillingRepots")
	public String allbillreports(Model model,HttpServletRequest request)
	{
		logger.info("******Inside a Request Method***************"+request.getParameter("jspId"));

		model.addAttribute("data", request.getParameter("jspId"));
		logger.info("::::::::jspid::::::"+request.getParameter("jspId"));
		int id=Integer.parseInt(request.getParameter("jspId"));
		if(id==1)
		{
			model.addAttribute("ViewName", "Billing");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Billing Reports", 2, request);
			breadCrumbService.addNode("Month Wise Billing Report", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "electricityBills/billingAllRecords2";
		}
		if(id==2)
		{
			model.addAttribute("ViewName", "Billing");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Billing Reports", 2, request);
			breadCrumbService.addNode("Month Wise Consumption Report", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "electricityBills/billingAllRecords2";
		}
		if(id==3)
		{
			model.addAttribute("ViewName", "Billing");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Billing Reports", 2, request);
			breadCrumbService.addNode("Customerwise Detailed Billing Report", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "electricityBills/billingAllRecords2";
		}
		if(id==4)
		{
			model.addAttribute("ViewName", "Billing");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Billing Reports", 2, request);
			breadCrumbService.addNode("AMR Data Billing Report", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "electricityBills/billingAllRecords";
		}
		if(id==5)
		{
			model.addAttribute("ViewName", "Billing");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Billing Reports", 2, request);
			breadCrumbService.addNode("Billing And Collection Payment Report", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "electricityBills/billingAllRecords";
		}

		return "electricityBills/billingAllRecords";
	}




	@RequestMapping(value="/bill/readAllBillData/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readBillAllData(@PathVariable int data)throws java.text.ParseException
	{


		logger.info("****************Bill Reports Inside Read**************"+data);

		List<Map<String, Object>> allComData=new ArrayList<>();   
		if(data==1)
		{
			/*Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthToShow);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);*/
			logger.info("*********Billing Report One**********");

			logger.info("********inside a Primary Contact Of Person*****************");

			List<?> allbilldetails=billsService.getAllBillDetailsMIS();
			logger.info("**********Bill Detail size********"+allbilldetails.size());


			return allbilldetails;
		}
		if(data==2)
		{
			logger.info("********second REport*********");
			List<?> allbilldetails=billsService.getAllUnitDetailsMIS();
			return allbilldetails;
		}
		if(data==3)
		{
			logger.info("********third REport*********");
			List<?> allbilldetails=billsService.getAllBillLinItemDetails();
			return allbilldetails;

		}
		if(data==4)
		{
			logger.info("********fourth REport*********");
			List<?> allbilldetails=billsService.getAllAmrDateDetails();
			return allbilldetails;
		}
		if(data==5)
		{
			logger.info("********fifth REport*********");
			List<?> allbilldetails=billsService.getAllBillPaymentDetails();
			return allbilldetails;
		}
		if(data==6)
		{
			logger.info("********sixth REport*********");

		}
		if(data==7)
		{
			logger.info("********seventh REport*********");

		}

		return null;

	}
	@RequestMapping(value="/bill/readAllBillData2/{month}/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readBillAllData2(@PathVariable String month,@PathVariable int data)throws java.text.ParseException
	{

		if(data==1)
		{
			List<?> result=result=billsService.getAllBillDetails2(month);
			return result;
		}
		if(data==2)
		{
			List<?> allbilldetails=billsService.getAllUnitDetails2(month);
			return allbilldetails;
		}
		if(data==3)
		{
			List<?> allbilldetails=billsService.getAllBillLinItemDetails2(month);
			return allbilldetails;
		}
		return null;

	}

	@RequestMapping(value = "/billAll/exportExcel/{month}/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	public String getPrimaryBillingReport(HttpServletRequest request,HttpServletResponse response,@PathVariable String month,@PathVariable int id) throws IOException, java.text.ParseException
	{

		ServletOutputStream servletOutputStream=null;
		System.out.println("monthhhhhhhhhhhhhhhhhhhhhhhhhhh"+month);

		if(id==1)
		{

			logger.info("*********Billing Report One**********");

			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Service Type");
			header.createCell(5).setCellValue("Billed Amount");
			/*header.createCell(5).setCellValue("Jan-15"); 
				        header.createCell(6).setCellValue("Feb-15"); 
				        header.createCell(7).setCellValue("March-15");
				        header.createCell(8).setCellValue("April-15");
				        header.createCell(9).setCellValue("May-15");				        
				        header.createCell(10).setCellValue("June-15");				        
				        header.createCell(11).setCellValue("July-15");
				        header.createCell(12).setCellValue("August-15");
				        header.createCell(13).setCellValue("Sepetember-15");
				        header.createCell(14).setCellValue("October-15");
				        header.createCell(15).setCellValue("November-15");
				        header.createCell(16).setCellValue("December-15");*/


			for(int j = 0; j<=5; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllBillDetails2(month);
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				if(map.get("serviceType")!=null)
				{
					row.createCell(4).setCellValue((String) map.get("serviceType"));
				}else
				{
					row.createCell(4).setCellValue("Electricity");
				}
				if(map.get("billAmount")!=null)
				{
					row.createCell(5).setCellValue((Double) map.get("billAmount"));
				}
				else
				{
					row.createCell(5).setCellValue(0.0);
				}
				/*if(map.containsKey("jan")){
									row.createCell(5).setCellValue((Double) map.get("jan"));
									}else{
										row.createCell(5).setCellValue(0);	
									}
								if(map.containsKey("feb")){
									row.createCell(6).setCellValue((Double) map.get("feb"));
									}else{
										row.createCell(6).setCellValue(0);	
									}
								if(map.containsKey("march")){
									row.createCell(7).setCellValue((Double) map.get("march"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								if(map.containsKey("april")){
									row.createCell(8).setCellValue((Double) map.get("april"));
									}else{
										row.createCell(8).setCellValue(0);	
									}
								if(map.containsKey("may")){
									row.createCell(9).setCellValue((Double) map.get("may"));
									}else{
										row.createCell(9).setCellValue(0);	
									}
								if(map.containsKey("june")){
									row.createCell(10).setCellValue((Double) map.get("june"));
									}else{
										row.createCell(10).setCellValue(0);	
									}
								if(map.containsKey("july")){
									row.createCell(11).setCellValue((Double) map.get("july"));
									}else{
										row.createCell(11).setCellValue(0);	
									}
								if(map.containsKey("august")){
									row.createCell(12).setCellValue((Double) map.get("august"));
									}else{
										row.createCell(12).setCellValue(0);	
									}
								if(map.containsKey("sepetembre")){
									row.createCell(13).setCellValue((Double) map.get("sepetember"));
									}else{
										row.createCell(13).setCellValue(0);	
									}
								if(map.containsKey("october")){
									row.createCell(14).setCellValue((Double) map.get("october"));
									}else{
										row.createCell(14).setCellValue(0);	
									}

								if(map.containsKey("november")){
									row.createCell(15).setCellValue((Double) map.get("november"));
									}else{
										row.createCell(15).setCellValue(0);	
									}
								if(map.containsKey("december")){
									row.createCell(16).setCellValue((Double) map.get("december"));
									}else{
										row.createCell(16).setCellValue(0);	
									}*/


				count++;
			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Billing Month Wise Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
		}

		if(id==2)
		{
			logger.info("*********Billing Report two**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Service Type");
			header.createCell(5).setCellValue("Unit Consumption"); 
			/*header.createCell(6).setCellValue("Feb-15"); 
				        header.createCell(7).setCellValue("March-15");
				        header.createCell(8).setCellValue("April-15");
				        header.createCell(9).setCellValue("May-15");				        
				        header.createCell(10).setCellValue("June-15");				        
				        header.createCell(11).setCellValue("July-15");
				        header.createCell(12).setCellValue("August-15");
				        header.createCell(13).setCellValue("Sepetember-15");
				        header.createCell(14).setCellValue("October-15");
				        header.createCell(15).setCellValue("November-15");
				        header.createCell(16).setCellValue("December-15");*/



			for(int j = 0; j<=5; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllUnitDetails2(month);
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				/*row.createCell(4).setCellValue((String) map.get("serviceType"));*/
				if(map.get("serviceType")!=null)
				{
					row.createCell(4).setCellValue((String) map.get("serviceType"));
				}else
				{
					row.createCell(4).setCellValue("Electricity");
				}
				if(map.get("consumption")!=null)
				{
					row.createCell(5).setCellValue((Float) map.get("consumption"));
				}
				else
				{
					row.createCell(5).setCellValue(0.0);
				}
				/*if(map.containsKey("jan") && map.get("jan")!=null ){
									row.createCell(5).setCellValue((Float) map.get("jan"));
									}else{
										row.createCell(5).setCellValue(0);	
									}
								if(map.containsKey("feb")  && map.get("feb")!=null){
									row.createCell(6).setCellValue((Float) map.get("feb"));
									}else{
										row.createCell(6).setCellValue(0);	
									}
								if(map.containsKey("march")  && map.get("march")!=null){
									row.createCell(7).setCellValue((Float) map.get("march"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								if(map.containsKey("april")  && map.get("april")!=null){
									row.createCell(8).setCellValue((Float) map.get("april"));
									}else{
										row.createCell(7).setCellValue(0);	
									}
								if(map.containsKey("may")  && map.get("may")!=null){
									row.createCell(9).setCellValue((Float) map.get("may"));
									}else{
										row.createCell(9).setCellValue(0);	
									}
								if(map.containsKey("june")  && map.get("june")!=null){
									row.createCell(10).setCellValue((Float) map.get("june"));
									}else{
										row.createCell(10).setCellValue(0);	
									}
								if(map.containsKey("july")  && map.get("july")!=null){
									row.createCell(11).setCellValue((Float) map.get("july"));
									}else{
										row.createCell(11).setCellValue(0);	
									}
								if(map.containsKey("august")  && map.get("august")!=null){
									row.createCell(12).setCellValue((Float) map.get("august"));
									}else{
										row.createCell(12).setCellValue(0);	
									}
								if(map.containsKey("sepetembre")  && map.get("sepetember")!=null){
									row.createCell(13).setCellValue((Float) map.get("sepetember"));
									}else{
										row.createCell(13).setCellValue(0);	
									}
								if(map.containsKey("october") && map.get("october")!=null){
									row.createCell(13).setCellValue((Float) map.get("october"));
									}else{
										row.createCell(13).setCellValue(0);	
									}
								if(map.containsKey("november") && map.get("november")!=null){
									row.createCell(14).setCellValue((Float) map.get("november"));
									}else{
										row.createCell(14).setCellValue(0);	
									}
								if(map.containsKey("december") && map.get("decembers")!=null){
									row.createCell(15).setCellValue((Float) map.get("december"));
									}else{
										row.createCell(15).setCellValue(0);	
									}*/



				count++;
			}



			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Month Wise Consumption Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}

		if(id==3)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";



			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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

			/*billsMap.put("month", electricityBillEntity.getFromDate());
			Double energycharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_EC");
			System.out.println("::::::::::energycharge::::::"+energycharge);
			Double roundoff=getBillLineItem(electricityBillEntity.getElBillId(), "EL_ROF");
			billsMap.put("head1",energycharge);
			billsMap.put("head5", roundoff);
			System.out.println("::::::::::roundoff::::::"+roundoff);
			Double dgcharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_DG");
			Double arrear=getBillLineItem(electricityBillEntity.getElBillId(), "BILL_ARR");
			Double latepaymentcharge=getBillLineItem(electricityBillEntity.getElBillId(), "EL_LPC");
			billsMap.put("head2",dgcharge);
			billsMap.put("head4",arrear);
			billsMap.put("head3",latepaymentcharge);*/

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Month"); 
			header.createCell(5).setCellValue("Energy Charge"); 
			header.createCell(6).setCellValue("DG Charge");
			header.createCell(7).setCellValue("Late Payment Charge");
			header.createCell(8).setCellValue("Arrears");				        
			header.createCell(9).setCellValue("Round Off");				        




			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllBillLinItemDetails2(month);
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				if(map.containsKey("month")  && map.get("month")!=null){
					String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("month"));
					row.createCell(4).setCellValue(date);
				}

				if(map.containsKey("head1") && map.get("head1")!=null ){
					row.createCell(5).setCellValue((Double) map.get("head1"));
				}else{
					row.createCell(5).setCellValue(0);	
				}
				if(map.containsKey("head2")  && map.get("head2")!=null){
					row.createCell(6).setCellValue((Double) map.get("head2"));
				}else{
					row.createCell(6).setCellValue(0);	
				}
				if(map.containsKey("head3")  && map.get("head3")!=null){
					row.createCell(7).setCellValue((Double) map.get("head3"));
				}else{
					row.createCell(7).setCellValue(0);	
				}
				if(map.containsKey("head4")  && map.get("head4")!=null){
					row.createCell(8).setCellValue((Double) map.get("head4"));
				}else{
					row.createCell(8).setCellValue(0);	
				}
				if(map.containsKey("head5")  && map.get("head5")!=null){
					row.createCell(9).setCellValue((Double) map.get("head5"));
				}else{
					row.createCell(9).setCellValue(0);	
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Customerwise Detailed Monthly Billing Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		if(id==4)
		{
			logger.info("********fourth REport*********");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Meter Sr No"); 
			header.createCell(5).setCellValue("Previous Billed Utility Unit"); 
			header.createCell(6).setCellValue("Present Billed Utility Unit");
			header.createCell(7).setCellValue("Previous Billed DG Unit"); 
			header.createCell(8).setCellValue("Present Billed DG Unit");
			header.createCell(9).setCellValue("Previous Bill Date");
			header.createCell(10).setCellValue("Present Bill Date");
			for(int j = 0; j<=10; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllAmrDateDetails();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				if(map.containsKey("meterSrNo")  && map.get("meterSrNo")!=null){
					//String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("month"));
					row.createCell(4).setCellValue((String)map.get("meterSrNo"));
				}

				if(map.containsKey("prevBillUnit") && map.get("prevBillUnit")!=null ){
					row.createCell(5).setCellValue((Float) map.get("prevBillUnit"));
				}else{
					row.createCell(5).setCellValue(0);	
				}
				if(map.containsKey("presentBillUnit")  && map.get("presentBillUnit")!=null){
					row.createCell(6).setCellValue((Float) map.get("presentBillUnit"));
				}else{
					row.createCell(6).setCellValue(0);	
				}
				if(map.containsKey("prevBilldgUnit")  && map.get("prevBilldgUnit")!=null){
					row.createCell(7).setCellValue((Float) map.get("prevBilldgUnit"));
				}else{
					row.createCell(7).setCellValue(0);	
				}
				if(map.containsKey("presentBilldgUnit")  && map.get("presentBilldgUnit")!=null){
					row.createCell(8).setCellValue((Float) map.get("presentBilldgUnit"));
				}else{
					row.createCell(8).setCellValue(0);	
				}
				if(map.containsKey("prevBillDate")  && map.get("prevBillDate")!=null){
					String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("prevBillDate"));
					row.createCell(9).setCellValue(date);
				}
				if(map.containsKey("presentBillDate")  && map.get("presentBillDate")!=null){
					String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("presentBillDate"));
					row.createCell(10).setCellValue(date);
				}


				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"AMR Data Billing Report Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}
		if(id==5)
		{

			logger.info("********fourth REport*********");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			logger.info("*****Billing Collection Report***********");
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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Month"); 
			header.createCell(5).setCellValue("Billed Amount"); 
			header.createCell(6).setCellValue("Collection Amount");
			header.createCell(7).setCellValue("Interest");
			header.createCell(8).setCellValue("Arrears");
			header.createCell(9).setCellValue("Service Tax");

			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllBillPaymentDetails();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				if(map.containsKey("month")  && map.get("month")!=null){

					row.createCell(4).setCellValue((String)map.get("month"));
				}

				if(map.containsKey("monthBilledAmount") && map.get("monthBilledAmount")!=null ){
					row.createCell(5).setCellValue((Double) map.get("monthBilledAmount"));
				}else{
					row.createCell(5).setCellValue(0);	
				}
				if(map.containsKey("collectionAmount")  && map.get("collectionAmount")!=null){
					row.createCell(6).setCellValue((Double) map.get("collectionAmount"));
				}else{
					row.createCell(6).setCellValue(0);	
				}
				if(map.containsKey("interest")  && map.get("interest")!=null){

					row.createCell(7).setCellValue((Double) map.get("interest"));
				}else{
					row.createCell(7).setCellValue(0);	
				}
				if(map.containsKey("arrearsTax")  && map.get("arrearsTax")!=null){

					row.createCell(8).setCellValue((Double) map.get("arrearsTax"));
				}else{
					row.createCell(8).setCellValue(0);	
				}

				if(map.containsKey("vat")  && map.get("vat")!=null){

					row.createCell(9).setCellValue((Double) map.get("vat"));
				}else{
					row.createCell(9).setCellValue(0);	
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Billing Payment Collection Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();


		}
		if(id==6)
		{
			logger.info("********sixth REport*********");

		}
		if(id==7)
		{
			logger.info("********seventh REport*********");

		}

		return null;
	}
	@RequestMapping(value = "/billAll/exportExcel2/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	public String getPrimaryBillingReport2(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws IOException 
	{

		ServletOutputStream servletOutputStream=null;

		if(id==4)
		{
			logger.info("********fourth REport*********");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			logger.info("*****person Size in Vehicle Owner Contact Type***********");
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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Meter Sr No"); 
			header.createCell(5).setCellValue("Previous Billed Utility Unit"); 
			header.createCell(6).setCellValue("Present Billed Utility Unit");
			header.createCell(7).setCellValue("Previous Billed DG Unit"); 
			header.createCell(8).setCellValue("Present Billed DG Unit");
			header.createCell(9).setCellValue("Previous Bill Date");
			header.createCell(10).setCellValue("Present Bill Date");
			for(int j = 0; j<=10; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllAmrDateDetails();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				if(map.containsKey("meterSrNo")  && map.get("meterSrNo")!=null){
					//String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("month"));
					row.createCell(4).setCellValue((String)map.get("meterSrNo"));
				}

				if(map.containsKey("prevBillUnit") && map.get("prevBillUnit")!=null ){
					row.createCell(5).setCellValue((Float) map.get("prevBillUnit"));
				}else{
					row.createCell(5).setCellValue(0);	
				}
				if(map.containsKey("presentBillUnit")  && map.get("presentBillUnit")!=null){
					row.createCell(6).setCellValue((Float) map.get("presentBillUnit"));
				}else{
					row.createCell(6).setCellValue(0);	
				}
				if(map.containsKey("prevBilldgUnit")  && map.get("prevBilldgUnit")!=null){
					row.createCell(7).setCellValue((Float) map.get("prevBilldgUnit"));
				}else{
					row.createCell(7).setCellValue(0);	
				}
				if(map.containsKey("presentBilldgUnit")  && map.get("presentBilldgUnit")!=null){
					row.createCell(8).setCellValue((Float) map.get("presentBilldgUnit"));
				}else{
					row.createCell(8).setCellValue(0);	
				}
				if(map.containsKey("prevBillDate")  && map.get("prevBillDate")!=null){
					String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("prevBillDate"));
					row.createCell(9).setCellValue(date);
				}
				if(map.containsKey("presentBillDate")  && map.get("presentBillDate")!=null){
					String date = new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("presentBillDate"));
					row.createCell(10).setCellValue(date);
				}


				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"AMR Data Billing Report Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();

		}
		if(id==5)
		{

			logger.info("********fourth REport*********");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			logger.info("*****Billing Collection Report***********");
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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Property No");
			header.createCell(2).setCellValue("Person Name");
			header.createCell(3).setCellValue("Account No");
			header.createCell(4).setCellValue("Month"); 
			header.createCell(5).setCellValue("Billed Amount"); 
			header.createCell(6).setCellValue("Collection Amount");
			header.createCell(7).setCellValue("Interest");
			header.createCell(8).setCellValue("Arrears");
			header.createCell(9).setCellValue("Service Tax");

			for(int j = 0; j<=9; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) billsService.getAllBillPaymentDetails();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("propertyno"));						

				row.createCell(2).setCellValue((String) map.get("personName"));				

				row.createCell(3).setCellValue((String) map.get("accountNo"));
				if(map.containsKey("month")  && map.get("month")!=null){

					row.createCell(4).setCellValue((String)map.get("month"));
				}

				if(map.containsKey("monthBilledAmount") && map.get("monthBilledAmount")!=null ){
					row.createCell(5).setCellValue((Double) map.get("monthBilledAmount"));
				}else{
					row.createCell(5).setCellValue(0);	
				}
				if(map.containsKey("collectionAmount")  && map.get("collectionAmount")!=null){
					row.createCell(6).setCellValue((Double) map.get("collectionAmount"));
				}else{
					row.createCell(6).setCellValue(0);	
				}
				if(map.containsKey("interest")  && map.get("interest")!=null){

					row.createCell(7).setCellValue((Double) map.get("interest"));
				}else{
					row.createCell(7).setCellValue(0);	
				}
				if(map.containsKey("arrearsTax")  && map.get("arrearsTax")!=null){

					row.createCell(8).setCellValue((Double) map.get("arrearsTax"));
				}else{
					row.createCell(8).setCellValue(0);	
				}

				if(map.containsKey("vat")  && map.get("vat")!=null){

					row.createCell(9).setCellValue((Double) map.get("vat"));
				}else{
					row.createCell(9).setCellValue(0);	
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Billing Payment Collection Report.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();


		}
		return null;
	}

	@RequestMapping(value="/getAllhelpDeskReport")
	public String allhelpDeskReport(Model model,HttpServletRequest request)
	{
		logger.info("******Inside a Request Method***************"+request.getParameter("jspId"));

		model.addAttribute("data", request.getParameter("jspId"));
		logger.info("::::::::jspid::::::"+request.getParameter("jspId"));
		int id=Integer.parseInt(request.getParameter("jspId"));
		if(id==1)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Help Desk", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Reports", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "helpDesk/helpDeskReport";
		}
		if(id==2)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Closed Community Ticket", 3, request);
			//model.addAttribute("reportName", "Month Wise Billing Report");
			return "helpDesk/helpDeskReport";
		}
		if(id==3)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Open Community Ticket", 3, request);			
			return "helpDesk/helpDeskReport";
		}
		if(id==4)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Closed On-Behalf of Ticket", 3, request);			
			return "helpDesk/helpDeskReport";
		}
		if(id==5)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Open On-Behalf of Ticket", 3, request);			
			return "helpDesk/helpDeskReport";

		}
		if(id==6)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Open Personal  Ticket", 3, request);			
			return "helpDesk/helpDeskReport";

		}
		if(id==7)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Closed Personal  Ticket", 3, request);			
			return "helpDesk/helpDeskReport";
		}
		if(id==8)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("All Escalted  Ticket", 3, request);			
			return "helpDesk/helpDeskReport";
		}
		if(id==9)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("Summary Of Ticket", 3, request);			
			return "helpDesk/helpDeskReport";
		}
		if(id==10)
		{
			model.addAttribute("ViewName", "Help Desk");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Billing", 1, request);
			breadCrumbService.addNode("Help Desk Reports", 2, request);
			breadCrumbService.addNode("Feedback Reports", 3, request);			
			return "helpDesk/feedback";
		}


		return "helpDesk/helpDeskReport";
	}

	@RequestMapping(value="/helpDesk/readAllHelpDeskData/{data}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> readAllHelpDeskData(@PathVariable int data)
	{


		logger.info("****************Bill Reports Inside Read**************"+data);

		List<Map<String, Object>> allComData=new ArrayList<>();   
		if(data==1)
		{

			logger.info("*********Billing Report One**********");

			logger.info("********inside a Primary Contact Of Person*****************");

			List<?> helpdeskData=openNewTicketService.findAllTicketReports();
			//	logger.info("**********Bill Detail size********"+allbilldetails.size());


			return helpdeskData;
		}
		if(data==2)
		{
			logger.info("********second REport*********");
			List<?> allclosedreport=openNewTicketService.findCloseTicketReports();

			return allclosedreport;
		}
		if(data==3)
		{
			logger.info("********third REport*********");
			List<?> allopenReports=openNewTicketService.findOpenTicketReports();

			return allopenReports;

		}
		if(data==4)
		{
			logger.info("********fourth REport*********");
			List<?> allclosedOnbehalf=openNewTicketService.findClosedOnBehalf();

			return allclosedOnbehalf;
		}
		if(data==5)
		{
			logger.info("********fifth REport*********");
			List<?> allOpenOnbehalf=openNewTicketService.findOpenOnBehalf();

			return allOpenOnbehalf;
		}
		if(data==6)
		{
			logger.info("********sixth REport*********");
			List<?> allOpenPersonalTicket=openNewTicketService.findAllPersonalOpenTicket();

			return allOpenPersonalTicket;
		}
		if(data==7)
		{
			logger.info("********seventh REport*********");

			List<?> allClosedPersonalTicket=openNewTicketService.findAllClosedPersonalTicket();

			return allClosedPersonalTicket;

		}
		if(data==8)
		{
			logger.info("********eight REport*********");
			List<?> allClosedPersonalTicket=ticketAssignService.findAllData();
			return allClosedPersonalTicket;
		}

		if(data==9)
		{
			logger.info("********Ninth REport*********");
			List<?> ticketSummary=openNewTicketService.findTicketSummary();
			return ticketSummary;
		}

		return null;

	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/helpDeskReport/exportExcel/{id}", method = {RequestMethod.POST,RequestMethod.GET})
	public Object gethelpdeskReport(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws IOException, DocumentException
	{

		ServletOutputStream servletOutputStream=null;

		if(id==1)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("Category");
			header.createCell(3).setCellValue("Sub Category");
			header.createCell(4).setCellValue("Property No"); 
			header.createCell(5).setCellValue("Lodged By"); 
			header.createCell(6).setCellValue("Opened On");
			header.createCell(7).setCellValue("Last Update");
			header.createCell(8).setCellValue("Status");				        
			header.createCell(9).setCellValue("Driven By");	
			header.createCell(10).setCellValue("Days Issue");	
			header.createCell(11).setCellValue("Last Note");	




			for(int j = 0; j<=11; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findAllTicketReports();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));						

				row.createCell(2).setCellValue((String) map.get("category"));				

				row.createCell(3).setCellValue((String) map.get("subcategory"));
				row.createCell(4).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(5).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(6).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(7).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("Status")  && map.get("Status")!=null){
					row.createCell(8).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(10).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(11).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		if(id==2)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("Category");
			header.createCell(3).setCellValue("Sub Category");
			header.createCell(4).setCellValue("Property No"); 
			header.createCell(5).setCellValue("Lodged By"); 
			header.createCell(6).setCellValue("Opened On");
			header.createCell(7).setCellValue("Last Update");
			header.createCell(8).setCellValue("Status");				        
			header.createCell(9).setCellValue("Driven By");	
			header.createCell(10).setCellValue("Days Issue");	
			header.createCell(11).setCellValue("Last Note");	




			for(int j = 0; j<=11; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findCloseTicketReports();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));						

				row.createCell(2).setCellValue((String) map.get("category"));				

				row.createCell(3).setCellValue((String) map.get("subcategory"));
				row.createCell(4).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(5).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(6).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(7).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(8).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(10).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(11).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Closed Community Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}

		if(id==3)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("Category");
			header.createCell(3).setCellValue("Sub Category");
			header.createCell(4).setCellValue("Property No"); 
			header.createCell(5).setCellValue("Lodged By"); 
			header.createCell(6).setCellValue("Opened On");
			header.createCell(7).setCellValue("Last Update");
			header.createCell(8).setCellValue("Status");				        
			header.createCell(9).setCellValue("Driven By");	
			header.createCell(10).setCellValue("Days Issue");	
			header.createCell(11).setCellValue("Last Note");	




			for(int j = 0; j<=11; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findOpenTicketReports();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));						

				row.createCell(2).setCellValue((String) map.get("category"));				

				row.createCell(3).setCellValue((String) map.get("subcategory"));
				row.createCell(4).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(5).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(6).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(7).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(8).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(10).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(11).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Open Community Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		if(id==4)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("On Be Half Of");
			header.createCell(3).setCellValue("Category");
			header.createCell(4).setCellValue("Sub Category");
			header.createCell(5).setCellValue("Property No"); 
			header.createCell(6).setCellValue("Lodged By"); 
			header.createCell(7).setCellValue("Opened On");
			header.createCell(8).setCellValue("Last Update");
			header.createCell(9).setCellValue("Status");				        
			header.createCell(10).setCellValue("Driven By");	
			header.createCell(11).setCellValue("Days Issue");	
			header.createCell(12).setCellValue("Last Note");	




			for(int j = 0; j<=12; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findClosedOnBehalf();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));
				row.createCell(2).setCellValue((String) map.get("onBeHalfOf"));

				row.createCell(3).setCellValue((String) map.get("category"));				

				row.createCell(4).setCellValue((String) map.get("subcategory"));
				row.createCell(5).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(6).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(7).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(8).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(9).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(10).setCellValue((String) map.get("drivenBy"));
				}

				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(11).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(12).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Closed On Be Half Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		if(id==5)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("ON Be Half Of");
			header.createCell(3).setCellValue("Category");
			header.createCell(4).setCellValue("Sub Category");
			header.createCell(5).setCellValue("Property No"); 
			header.createCell(6).setCellValue("Lodged By"); 
			header.createCell(7).setCellValue("Opened On");
			header.createCell(8).setCellValue("Last Update");
			header.createCell(9).setCellValue("Status");				        
			header.createCell(10).setCellValue("Driven By");	
			header.createCell(11).setCellValue("Days Issue");	
			header.createCell(12).setCellValue("Last Note");	




			for(int j = 0; j<=12; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findOpenOnBehalf();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));
				row.createCell(2).setCellValue((String) map.get("onBeHalfOf"));

				row.createCell(3).setCellValue((String) map.get("category"));				

				row.createCell(4).setCellValue((String) map.get("subcategory"));
				row.createCell(5).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(6).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(7).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(8).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(9).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(10).setCellValue((String) map.get("drivenBy"));
				}

				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(11).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(12).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Open On Be Half Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}

		if(id==6)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("Category");
			header.createCell(3).setCellValue("Sub Category");
			header.createCell(4).setCellValue("Property No"); 
			header.createCell(5).setCellValue("Lodged By"); 
			header.createCell(6).setCellValue("Opened On");
			header.createCell(7).setCellValue("Last Update");
			header.createCell(8).setCellValue("Status");				        
			header.createCell(9).setCellValue("Driven By");	
			header.createCell(10).setCellValue("Days Issue");	
			header.createCell(11).setCellValue("Last Note");	




			for(int j = 0; j<=11; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findAllPersonalOpenTicket();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));						

				row.createCell(2).setCellValue((String) map.get("category"));				

				row.createCell(3).setCellValue((String) map.get("subcategory"));
				row.createCell(4).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(5).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(6).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(7).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(8).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(10).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(11).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Open Personal Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		if(id==7)
		{
			logger.info("*********Billing Report three**********");


			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Monthwise Billing Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";




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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("Category");
			header.createCell(3).setCellValue("Sub Category");
			header.createCell(4).setCellValue("Property No"); 
			header.createCell(5).setCellValue("Lodged By"); 
			header.createCell(6).setCellValue("Opened On");
			header.createCell(7).setCellValue("Last Update");
			header.createCell(8).setCellValue("Status");				        
			header.createCell(9).setCellValue("Driven By");	
			header.createCell(10).setCellValue("Days Issue");	
			header.createCell(11).setCellValue("Last Note");	




			for(int j = 0; j<=11; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findAllClosedPersonalTicket();
			int count = 1;
			for (Map<String, Object> map : result) {
				System.out.println(":::::::::::::::::::000"+map.get("propertyno"));
				System.out.println(map.get("accountNo"));
				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));						

				row.createCell(2).setCellValue((String) map.get("category"));				

				row.createCell(3).setCellValue((String) map.get("subcategory"));
				row.createCell(4).setCellValue((String) map.get("propertyNo"));


				if(map.containsKey("createdBy") && map.get("createdBy")!=null ){
					row.createCell(5).setCellValue((String) map.get("createdBy"));
				}
				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(6).setCellValue((String) map.get("ticketCreatedDate"));
				}
				if(map.containsKey("lastUpdatedDT")  && map.get("lastUpdatedDT")!=null){
					row.createCell(7).setCellValue((String) map.get("lastUpdatedDT"));
				}

				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(8).setCellValue((String) map.get("Status"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("drivenBy")  && map.get("drivenBy")!=null){
					row.createCell(9).setCellValue((String) map.get("drivenBy"));
				}
				if(map.containsKey("dayIssue")  && map.get("dayIssue")!=null){
					row.createCell(10).setCellValue((Integer) map.get("dayIssue"));
				}
				if(map.containsKey("lastnotes")  && map.get("lastnotes")!=null){
					row.createCell(11).setCellValue((String) map.get("lastnotes"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Open Closed Personal Tickets.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}
		if(id==8)
		{
			logger.info("*********Escalated Reports**********");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"All Escalted Ticket"+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
			String sheetName = "Escalated Report";//name of sheet

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
			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Ticket No");
			header.createCell(2).setCellValue("Escalated Date");
			header.createCell(3).setCellValue("Escalated To");
			header.createCell(4).setCellValue("Escalated Comments"); 
			header.createCell(5).setCellValue("SLA Level(in days)"); 
			header.createCell(6).setCellValue("Person Name");
			header.createCell(7).setCellValue("Priority");
			header.createCell(8).setCellValue("Issue Subject");				        
			header.createCell(9).setCellValue("Issue Details");	
			header.createCell(10).setCellValue("Ticket Status");	
			header.createCell(11).setCellValue("Ticket Created Date");	




			for(int j = 0; j<=11; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}
			List<Map<String, Object>> result=(List<Map<String, Object>>) ticketAssignService.findAllEscalatedReports();
			int count = 1;
			for (Map<String, Object> map : result) {

				XSSFRow row = sheet.createRow(count);
				row.createCell(0).setCellValue(String.valueOf(count));


				row.createCell(1).setCellValue((String) map.get("ticketNumber"));

				if(map.containsKey("assignDate") && map.get("assignDate")!=null ){
					row.createCell(2).setCellValue((Timestamp) map.get("assignDate"));
				}

				row.createCell(3).setCellValue((String) map.get("userName"));				

				row.createCell(4).setCellValue((String) map.get("assignComments"));

				row.createCell(5).setCellValue((Integer) map.get("levelOFSLA"));

				row.createCell(6).setCellValue((String) map.get("personName"));



				if(map.containsKey("priorityLevel") && map.get("priorityLevel")!=null ){
					row.createCell(7).setCellValue((String) map.get("priorityLevel"));
				}
				if(map.containsKey("issueSubject")  && map.get("issueSubject")!=null){
					row.createCell(8).setCellValue((String) map.get("issueSubject"));
				}
				if(map.containsKey("issueDetails")  && map.get("issueDetails")!=null){
					row.createCell(9).setCellValue((String) map.get("issueDetails"));
				}
				if(map.containsKey("ticketStatus")  && map.get("ticketStatus")!=null){
					row.createCell(10).setCellValue((String) map.get("ticketStatus"));
				}

				if(map.containsKey("ticketCreatedDate")  && map.get("ticketCreatedDate")!=null){
					row.createCell(11).setCellValue((Timestamp) map.get("ticketCreatedDate"));
				}

				count++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"All Escalted Ticket.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();



		}


		if(id==9)
		{
			logger.info("*********Ticket Summary Report**********");
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Ticket Summary Report"+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";

			DefaultPieDataset dataSet = new DefaultPieDataset();
			String category="";
			String categoryOwner="";
			long openCount = 0;
			long closedCount = 0;

			PdfPTable table = new PdfPTable(5);
			float[] columnWidths = new float[] {10f,22f, 25f, 25f,25f};
			table.setWidths(columnWidths);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Sl No"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Category"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Category Owner"));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Open Tickets"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Closed Tickets"));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);

			List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findTicketSummary();
			int count = 1;
			for (Map<String, Object> map : result) {

				if(map.containsKey("category") && map.get("category")!=null ){
					category=(String) map.get("category");
				}
				if(map.containsKey("categoryOwner") && map.get("categoryOwner")!=null ){
					categoryOwner="Helpdesk GA";
				}
				if(map.containsKey("openCount") && map.get("openCount")!=null ){
					openCount=(Long.parseLong(map.get("openCount").toString()));
				}
				if(map.containsKey("closedCount") && map.get("closedCount")!=null ){
					closedCount=(Long.parseLong(map.get("closedCount").toString()));
				}


				table.addCell(String.valueOf(count));
				table.addCell(category);
				table.addCell(categoryOwner);
				table.addCell(String.valueOf(openCount));
				table.addCell(String.valueOf(closedCount));

				dataSet.setValue(category, (openCount+closedCount));

				count++;
			}

			JFreeChart chart=ChartFactory.createPieChart("Ticket Summary", dataSet, true, true, false);

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			document.add(table);

			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(500, 500);new DefaultFontMapper();
			Graphics2D graphics2d = template.createGraphics(500, 500,new DefaultFontMapper());      
			java.awt.geom.Rectangle2D.Double rectangle2d = new java.awt.geom.Rectangle2D.Double(130, 0, 300,200);

			chart.draw(graphics2d, rectangle2d);
			graphics2d.dispose();
			contentByte.addTemplate(template, 0, 0);
			document.close();

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=\"Ticket Summary.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		}
		if(id==10)
		{
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
			//List<Map<String, Object>> result=(List<Map<String, Object>>) openNewTicketService.findAllTicketReports();
			int count = 1 ;
			//List<Map<String, Object>> allComData=new ArrayList<>(); 
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
		}

		return null;
	}


	@SuppressWarnings({ "unchecked", "static-access", "serial" })
	@RequestMapping(value ="/staff/satffBiometricLog/{month}/{data}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> searcchByMonth(@PathVariable String month,@PathVariable int data, HttpServletRequest req,Model model) throws java.text.ParseException {
		model.addAttribute("selectByMonth",month);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(data==45){
			List<?> StaffData = new ArrayList<>();
			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthToShow);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);
			logger.info("montOne" + montOne);
			logger.info("year" + year);
			StaffData =msSQLentityManager.createNamedQuery("AccessEvent.readAttendanceMonthWise").setParameter("month", montOne).setParameter("year", year).getResultList();
			for (final Iterator<?> i = StaffData.iterator(); i.hasNext();) {
				final int personId = (Integer) i.next();
				List<?>	attendanceDetails = msSQLentityManager.createQuery("SELECT MIN(ac.loginLogOut),MAX(ac.loginLogOut) FROM AccessEvent ac WHERE ac.personIdLo = "+ personId+ " and EXTRACT(month FROM ac.loginLogOut) ="+ montOne+ " and EXTRACT(year FROM ac.loginLogOut) ="+ year+ " GROUP BY datediff(day,0, ac.loginLogOut)").getResultList();
				for (final Iterator<?> i1 = attendanceDetails.iterator(); i1.hasNext();) {
					result.add(new HashMap<String, Object>() {
						{
							final Object[] values = (Object[]) i1.next();
							Date timeIn = (Timestamp) values[0];
							Date timeOut = (Timestamp) values[1];
							long diff = timeOut.getTime() - timeIn.getTime();

							final Double diffInHours = diff / ((double) 1000 * 60 * 60);
							String s = String.format("%.2f", diffInHours);
							final double diffInHours1 = (double) diffInHours.parseDouble(s);

							SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
							sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
							List<Personnel> personState = msSQLentityManager.createNamedQuery("Personnel.findByObjectId").setParameter("objectIdLo", personId).getResultList();
							if(!(personState.isEmpty())){
								put("accessCardNo", personState.get(0).getNonAbacardNumber());
								put("sasDateput", sdf1.format(values[0]));
								put("timeIn", sdfTime.format(values[0]));
								put("timeOut", sdfTime.format(values[1]));
								put("timeSpent", diffInHours1);

								List<PersonAccessCards> pac=personAccessCardService.getPersonBasedOnAccessCard(personState.get(0).getObjectIdLo());
								if(!(pac.isEmpty())){

									List<Users> userForDesignation=	usersService.getDesigBasedOnPersonId(pac.get(0).getPersonId());
									if(!(userForDesignation.isEmpty())){

										put("desigName", userForDesignation.get(0).getDesignation().getDn_Name());
										put("departmentName", userForDesignation.get(0).getDepartment().getDept_Name());
										if (userForDesignation.get(0).getPerson().getLastName()!= null) {
											put("staffName", userForDesignation.get(0).getPerson().getFirstName()+ " " + userForDesignation.get(0).getPerson().getLastName());
										} else {
											put("staffName", userForDesignation.get(0).getPerson().getFirstName());
										}
										put("staffCategory", userForDesignation.get(0).getStaffType());
									}
									else{
										put("desigName", "Not Available");
										if (personState.get(0).getLastName() != null) {
											put("staffName", personState.get(0).getFirstName()+ " " + personState.get(0).getLastName());
										} else {
											put("staffName", personState.get(0).getFirstName());
										}
										put("departmentName", personState.get(0).getDepartment());
										put("staffCategory", "Not Available");
									}
								}
								else{
									put("desigName", "Not Available");
									if (personState.get(0).getLastName() != null) {
										put("staffName", personState.get(0).getFirstName()+ " " + personState.get(0).getLastName());
									} else {
										put("staffName", personState.get(0).getFirstName());
									}
									put("departmentName", personState.get(0).getDepartment());
									put("staffCategory", "Not Available");
								}
							}

						}
					});

				}
			}


		}
		return result;
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/staffBioLog/exportExcel/{id}/{month}", method = {RequestMethod.POST,RequestMethod.GET})
	public String staffBioLog(HttpServletRequest request,HttpServletResponse response,@PathVariable int id,@PathVariable String month) throws IOException, java.text.ParseException
	{

		ServletOutputStream servletOutputStream=null;
		if(id==45)//**********primary Contact
		{
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+"Staff Biometric Log "+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";

			String sheetName = "Staff Biometric Log";//name of sheet

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

			header.createCell(0).setCellValue("Sl NO");
			header.createCell(1).setCellValue("Access card No");
			header.createCell(2).setCellValue("Staff Category");
			header.createCell(3).setCellValue("Staff Name");
			header.createCell(4).setCellValue("Attendance Date"); 
			header.createCell(5).setCellValue("In Time"); 
			header.createCell(6).setCellValue("Out Time");
			header.createCell(7).setCellValue("Department");
			header.createCell(8).setCellValue("Designation");				        




			for(int j = 0; j<=8; j++){
				header.getCell(j).setCellStyle(style);
				sheet.setColumnWidth(j, 8000); 
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
			}

			int count = 1;

			List<?> StaffData = new ArrayList<>();

			Date monthToShow = new SimpleDateFormat("MMMM yyyy").parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthToShow);
			int month1 = cal.get(Calendar.MONTH);
			int montOne = month1 + 1;
			int year = cal.get(Calendar.YEAR);
			logger.info("montOne" + montOne);
			logger.info("year" + year);
			StaffData =msSQLentityManager.createNamedQuery("AccessEvent.readAttendanceMonthWise").setParameter("month", montOne).setParameter("year", year).getResultList();


			for (final Iterator<?> i = StaffData.iterator(); i.hasNext();) {

				final int personId = (Integer) i.next();

				List<?>	attendanceDetails = msSQLentityManager.createQuery("SELECT MIN(ac.loginLogOut),MAX(ac.loginLogOut) FROM AccessEvent ac WHERE ac.personIdLo = "+ personId+ " and EXTRACT(month FROM ac.loginLogOut) ="+ montOne+ " and EXTRACT(year FROM ac.loginLogOut) ="+ year+ " GROUP BY datediff(day,0, ac.loginLogOut)").getResultList();



				for (final Iterator<?> i1 = attendanceDetails.iterator(); i1.hasNext();) {
					XSSFRow row = sheet.createRow(count);
					row.createCell(0).setCellValue(String.valueOf(count));
					final Object[] values = (Object[]) i1.next();

					Date timeIn = (Timestamp) values[0];
					Date timeOut = (Timestamp) values[1];

					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
					sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
					List<Personnel> personState = msSQLentityManager.createNamedQuery("Personnel.findByObjectId").setParameter("objectIdLo", personId).getResultList();
					if(!(personState.isEmpty())){
						row.createCell(4).setCellValue(sdf1.format(values[0]));
						row.createCell(5).setCellValue(sdfTime.format(timeIn));
						row.createCell(6).setCellValue(sdfTime.format(timeOut));


						row.createCell(1).setCellValue(personState.get(0).getNonAbacardNumber());
						List<PersonAccessCards> pac=personAccessCardService.getPersonBasedOnAccessCard(personState.get(0).getObjectIdLo());
						if(!(pac.isEmpty())){

							List<Users> userForDesignation=	usersService.getDesigBasedOnPersonId(pac.get(0).getPersonId());
							if(!(userForDesignation.isEmpty())){
								row.createCell(7).setCellValue(userForDesignation.get(0).getDepartment().getDept_Name());
								row.createCell(8).setCellValue(userForDesignation.get(0).getDesignation().getDn_Name());

								if (userForDesignation.get(0).getPerson().getLastName()!= null) {
									row.createCell(3).setCellValue(userForDesignation.get(0).getPerson().getFirstName()+ " " + userForDesignation.get(0).getPerson().getLastName());
								} else {
									row.createCell(3).setCellValue(userForDesignation.get(0).getPerson().getFirstName());

								}
								row.createCell(2).setCellValue(userForDesignation.get(0).getStaffType());

							}
							else{
								row.createCell(8).setCellValue("Not Available");
								if (personState.get(0).getLastName() != null) {
									row.createCell(3).setCellValue(personState.get(0).getFirstName()+ " " + personState.get(0).getLastName());

								} else {
									row.createCell(3).setCellValue(personState.get(0).getFirstName());

								}
								row.createCell(7).setCellValue(personState.get(0).getDepartment());
								row.createCell(2).setCellValue("Not Available");
							}
						}
						else{
							row.createCell(8).setCellValue("Not Available");
							if (personState.get(0).getLastName() != null) {
								row.createCell(3).setCellValue(personState.get(0).getFirstName()+ " " + personState.get(0).getLastName());

							} else {
								row.createCell(3).setCellValue(personState.get(0).getFirstName());

							}
							row.createCell(7).setCellValue(personState.get(0).getDepartment());
							row.createCell(2).setCellValue("Not Available");
						}
					}

					count++;
				}
			}
			FileOutputStream fileOut = new FileOutputStream(fileName);    	
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();



			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"Staff Biometric Log("+month+").xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();



		}

		return null;
	}

	/*************************************************** Reset Password for portal **************************************************************/
	public static int msgcode=0;
	@RequestMapping(value = "/resetPasswordForPortal", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String resetPasswordForPortal(HttpServletRequest request)
	{		
		String msg 		  = "";
		String operation  = "";
		String personType = "Owner";
		BigDecimal personId   = 
				(BigDecimal) entityManager.createNativeQuery("SELECT PERSON_ID FROM CONTACT WHERE CONTACT_CONTENT='"+request.getParameter("email")+"'").getSingleResult();

		Object[] status=(Object[]) entityManager.createNativeQuery("SELECT o.STATUS,p.STATUS FROM OWNER o,PERSON p WHERE o.PERSON_ID=p.PERSON_ID AND p.PERSON_ID='"+personId.intValue()+"'").getSingleResult();
		if(status[0].toString().equalsIgnoreCase("Inactive")||status[1].toString().equalsIgnoreCase("Inactive")){
			msg = "Person is InActive";
		}else{
			msgcode=1;

			operation = "deactivate";
			msg = personService.setPersonStatus(personId.intValue(), operation, personType);

			operation = "activate";
			msg = personService.setPersonStatus(personId.intValue(), operation, personType);
		}

		return msg;//"Password Reset and Sent To Mail and Sms";

	}

}