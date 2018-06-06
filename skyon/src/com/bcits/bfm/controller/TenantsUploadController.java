package com.bcits.bfm.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.City;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Country;
import com.bcits.bfm.model.DrGroupId;
//import com.bcits.bfm.model.Owner;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.State;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.model.TenantProperty;
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
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class TenantsUploadController {

	@PersistenceContext(unitName = "bfm")
	protected EntityManager entityManager;
	
	private static final Log logger = LogFactory.getLog(CustomerOccupancyManagementController.class);
	
	@Autowired
	private DrGroupIdService drGroupIdService;
	@Autowired
	private PersonService personService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TenantSevice tenantSevice;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private BlocksService blocksService;
	@Autowired
	private TenantPropertyService tenantPropertyService;

	
	/*------------------------------------------Starts tenants data upload----------------------------------------------*/
	@SuppressWarnings({ "rawtypes", "unused", "serial" })
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/UploadDataTenants/uploadExcelData", method ={ RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object importExcelDataForTenants(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException, java.text.ParseException 
	{
		logger.info("---------------------Inside importExcelDataForTenants---------------------");
		JsonResponse errorResponse = new JsonResponse();
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

		List<TenantProperty> tenantsPropertyList = new ArrayList<TenantProperty>();		
		List<Person> personList    = new ArrayList<Person>();		
		List<Address> addressList  = new ArrayList<Address>();		
		List<Contact> contactList1 = new ArrayList<Contact>();		
		List<Tenant> tenantList    = new ArrayList<Tenant>();
		List<Contact> contactList2 = new ArrayList<Contact>();		
		List<Country> countryLists = new ArrayList<Country>();		
		List<State> stateLists 	   = new ArrayList<State>();		
		List<City> cityLists	   = new ArrayList<City>();		

		if("xls".equalsIgnoreCase(fileExtention)){

			HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream());
			HSSFSheet  sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();			


			while(rows.hasNext())
			{
				final HSSFRow  row = ((HSSFRow ) rows.next());

				logger.info("*****ROW COUNT*****"+row.getRowNum());
				Iterator cells = row.cellIterator();
				Person person	=new Person();
				TenantProperty tenantsProperty = new TenantProperty();
				Address address	= new Address();
				Contact contact1= new Contact();
				Contact contact2= new Contact();
				Country country	= new Country();
				State state		= new State();
				City city		= new City();

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
						tenantsProperty.setPropertyId(propertyId);
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

						//tenantsProperty.setPrimaryOwner(row.getCell(2).getStringCellValue().trim());
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

						tenantsProperty.setResidential(row.getCell(3).getStringCellValue().trim());
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
					java.sql.Timestamp sqltimestamp = new java.sql.Timestamp(date1.getTime());
					tenantsProperty.setStartDate(sqltimestamp);

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

						tenantsProperty.setVisitorRegReq(row.getCell(5).getStringCellValue().trim());
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

				tenantsPropertyList.add(tenantsProperty);
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
				TenantProperty tenantsProperty = new TenantProperty();
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
						tenantsProperty.setPropertyId(propertyId);
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

						//tenantsProperty.setPrimaryOwner(row.getCell(2).getStringCellValue().trim());
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

						tenantsProperty.setResidential(row.getCell(3).getStringCellValue().trim());
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
					java.sql.Timestamp sqltimestamp = new java.sql.Timestamp(date1.getTime());
					tenantsProperty.setStartDate(sqltimestamp);

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

						tenantsProperty.setVisitorRegReq(row.getCell(5).getStringCellValue().trim());
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

				if(!StringUtils.isEmpty(row.getCell(23).getRawValue())){
					logger.info("***** Address No*******"+row.getCell(23).getRawValue());
					address.setAddressNo(row.getCell(23).getRawValue());
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

				tenantsPropertyList.add(tenantsProperty);
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
			Iterator<TenantProperty> actualOwnerProperty=tenantsPropertyList.iterator();
			Iterator<Contact> actualContact1=contactList1.iterator();
			Iterator<Contact> actualContact2=contactList2.iterator();

			Iterator<Country> actualCountry=countryLists.iterator();
			Iterator<State> actualState=stateLists.iterator();
			Iterator<City> actualCity=cityLists.iterator();


			logger.info("Person List=========>"+personList.size());
			logger.info("Addrees List========>"+addressList.size());
			logger.info("OwnerPropertyList===>"+tenantsPropertyList.size());
			logger.info("Contact List========>"+contactList1.size());
			logger.info("Contact List========>"+contactList2.size());
			logger.info("Country List========>"+countryLists.size());
			logger.info("State List==========>"+stateLists.size());
			logger.info("City List===========>"+cityLists.size());

			if(personList.size()==0 && addressList.size()==0 && tenantsPropertyList.size()==0 && contactList1.size()==0 && contactList2.size()==0 && countryLists.size()==0 && stateLists.size()==0 && cityLists.size()==0){
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



			if(personList.size()==addressList.size() && addressList.size()==tenantsPropertyList.size()){

				while(actualPerson.hasNext() && actualAddress.hasNext() && actualOwnerProperty.hasNext() && actualContact1.hasNext() && actualContact2.hasNext() && i!=1){

					logger.info("Value of i inside While Condition======>"+i);

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

					Tenant owner = new Tenant();
					owner.setCreatedBy(per.getLastUpdatedBy());
					owner.setLastUpdatedBy(per.getLastUpdatedBy());
					owner.setLastUpdatedDt(new Timestamp(new Date().getTime()));
					owner.setTenantStatus("Inactive");
					owner.setPerson(per);
					per.setTenant(owner);

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
					
					System.err.println("owner.getTenantId()=======>"+owner.getTenantId());
					       logger.info("Person Id=================>"+per.getPersonId());

					int drGroupId=per.getDrGroupId();
					int tenantId=owner.getTenantId();
					
					TenantProperty ownerProp=actualOwnerProperty.next();
					ownerProp.setGroupId(drGroupId);
					ownerProp.setTenantId(owner);
					ownerProp.setCreatedBy(per.getLastUpdatedBy());
					ownerProp.setLastUpdatedBy(per.getLastUpdatedBy());
					ownerProp.setLastUpdatedDt(new Timestamp(new Date().getTime()));
					ownerProp.setStatus("Inactive");

					Property properTY = propertyService.getPropertyEntityBasedOnProID(ownerProp.getPropertyId());
					
					List<TenantProperty> ownerPropertyBasedOnPropertyIdAndOwnerId=tenantPropertyService.findTenantPropertyBasedOnProperty(properTY); 
					final String propertyListBasedOnPropertyId=propertyService.getPropertyNameBasedOnPropertyId(ownerProp.getPropertyId());

					if(ownerPropertyBasedOnPropertyIdAndOwnerId.isEmpty()){
						ownerProp.setPrimaryOwner("Yes");	
						tenantPropertyService.save(ownerProp);
					}
					else{
						ownerProp.setPrimaryOwner("No");	
						tenantPropertyService.save(ownerProp);
					}

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
}
