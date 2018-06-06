package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Blob;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.controller.CAMBillsController;
import com.bcits.bfm.controller.CustomerOccupancyManagementController;
import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.model.MailMaster;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Pets;
import com.bcits.bfm.model.Requisition;
import com.bcits.bfm.model.Role;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.model.Vendors;
import com.bcits.bfm.service.MailConfigService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.CheckChildEntries;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.EmailCredentialsDetailsBean;
import com.bcits.bfm.util.RandomPasswordGenerator;
import com.bcits.bfm.util.SendMailForOwnersDetails;
import com.bcits.bfm.util.SendSMSForStatus;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.SmsCredentialsDetailsBean;

/**
 * A data access object (DAO) providing persistence and search support for
 * Person entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 */
@Repository
public class PersonServiceImpl extends GenericServiceImpl<Person> implements PersonService{
	
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	
	@Autowired
	private DateTimeCalender dateTimeCalender; 
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private CheckChildEntries checkChildEntries;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private OwnerPropertyService ownerPropertyService;
	
	@Autowired
	private OwnerService ownerService;
	 
	@Value("${Unique.conf.serverIp}")
	private String hostIp;
	

	@Value("${Unique.conf.Serverport}")
	private String portNo;
	
	@Value("${Unique.conf.AppVersion}")
	private String version;
	
	
	@Value("${Unique.conf.DeployVersion}")
	private String DeployVersion;
	
	@Autowired
	private MailConfigService mailConfigService;


	@Value("${Unique.server.link}")
	private String link;
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.PersonService#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findAllStaff() {		
		BfmLogger.logger.info("finding all Person instances");
		try {
			return entityManager.createNamedQuery("Person.findAllStaff").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findAllStaff1() {		
		BfmLogger.logger.info("finding all Person instances");
		try {
			return entityManager.createNamedQuery("Person.findAllStaff1").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> findDistinct() {		
		BfmLogger.logger.info("finding only Person who are not thre in manpower");
		try {
			return entityManager.createNamedQuery("Person.findDistinctId").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getPersonIdBasedOnCreatedByAndLastUpdatedDt(String createdBy, Timestamp lastUpdatedDt)
	{
		return entityManager.createNamedQuery("Person.getPersonIdBasedOnCreatedByAndLastUpdatedDt", Integer.class).setParameter("createdBy", createdBy).setParameter("lastUpdatedDt", lastUpdatedDt).getSingleResult();		
	}

	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.PersonService#getPersonObject(java.util.Map, java.lang.String, com.bcits.bfm.model.Person)
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Person getPersonObject(Map<String, Object> map, String type, Person person)
	{
		String title=(String) map.get("title");
		String personStyle = (String) map.get("personStyle");
		
		person.setPersonStyle(personStyle);	
		person.setTitle(title);
		person.setFirstName(WordUtils.capitalizeFully((String) map.get("firstName")));
		person.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		person.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		
		if(type == "update"){
			person.setPersonId((Integer) map.get("personId"));
			person.setDrGroupId((Integer)map.get("drGroupId"));
			person.setCreatedBy((String) map.get("createdBy"));
			person.setKycCompliant((String) map.get("kycCompliant"));
			person.setPersonImages(getImage(person.getPersonId()));
			person.setPersonType((String) map.get("personType"));
			person.setPersonStatus(((String)map.get("personStatus")));
		}
		else if (type == "save")
		{
			person.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			//person.setKycCompliant("No");
			person.setPersonStatus("Inactive");
			
			try
			{
				if((personStyle.equalsIgnoreCase("Individual")) && ((title != null) && ((title.length() > 0))))
				{
					if(title.equalsIgnoreCase("Mr"))
					{
						byte[] b=ldapBusinessModel.getImage("bcitsadmin");
						Blob blob = Hibernate.createBlob(b);
						person.setPersonImages(blob);
					}
					else
					{
						byte[] b=ldapBusinessModel.getImageForFemale("bcitsadmin");
						Blob blob = Hibernate.createBlob(b);
						person.setPersonImages(blob);
					}		
				}
				else
				{
					byte[] b=ldapBusinessModel.getImage("bcitsadmin");
					Blob blob = Hibernate.createBlob(b);
					person.setPersonImages(blob);
				}
			}
		
			catch(Exception e)
			{
				e.getMessage();
			}
		}
		
		if(!(((String) map.get("personStyle")).equalsIgnoreCase("Individual")))
		{
			person.setTitle("M/S");
			person.setFatherName(null);
			person.setLanguagesKnown("None");
			person.setLastName(null);
			person.setOccupation("None");
			person.setMiddleName(null);
			person.setSpouseName(null);
			person.setDob(null);
			
			person.setMaritalStatus("None");
			person.setSex("None");
			person.setNationality("None");
			person.setBloodGroup("None");
			person.setWorkNature("None");
			
		}
		else
		{
			String langaugeSelected = "";
			if(map.get("languagesKnown") instanceof List)
			{
				@SuppressWarnings("unchecked")
				List<Map<String,String>> languagesList = (List<Map<String, String>>) map.get("languagesKnown");
				if(!(languagesList.isEmpty()))
				{
					for (Iterator<Map<String, String>> iterator = languagesList.iterator(); iterator.hasNext();) 
					{
						Map<String, String> map2 = (Map<String, String>) iterator.next();
						langaugeSelected = langaugeSelected +((String)map2.get("value")) +",";
					}
					langaugeSelected = langaugeSelected.substring(0,langaugeSelected.length()-1);
				}
			}
			else if((map.get("languagesKnown") == null) || (((String)map.get("languagesKnown")).trim().length() == 0)){
				langaugeSelected = "None";	
			}
			else{
				langaugeSelected = (String)map.get("languagesKnown");	
				if (langaugeSelected.endsWith(",")) {
					langaugeSelected = langaugeSelected.substring(0, langaugeSelected.length() - 1);
				}
			}
			if(((String) map.get("dob") != null) && (((String) map.get("dob")).length() > 0))
			{
				try {
					person.setDob(dateTimeCalender.kendoDateIssue((String) map.get("dob")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			
			person.setFatherName(WordUtils.capitalizeFully((String) map.get("fatherName")));
			person.setLanguagesKnown(langaugeSelected);
			person.setLastName(WordUtils.capitalizeFully((String) map.get("lastName")));
			String occupation = (String) map.get("occupation");
			if((occupation != null) && (occupation.trim().length() == 0))
				person.setOccupation("None");
			else
				person.setOccupation(occupation);
			person.setMiddleName(WordUtils.capitalizeFully((String) map.get("middleName")));
			if(!StringUtils.equalsIgnoreCase((String) map.get("maritalStatus"), "Single") || !StringUtils.equalsIgnoreCase((String) map.get("maritalStatus"), "None"))
			person.setSpouseName(WordUtils.capitalizeFully((String) map.get("spouseName")));
			person.setMaritalStatus((String) map.get("maritalStatus"));
			person.setSex((String) map.get("sex"));
			person.setNationality((String) map.get("nationality"));
			person.setBloodGroup((String) map.get("bloodGroup"));
			person.setWorkNature((String) map.get("workNature"));
			/*int transId=entityManager.createNamedQuery("TransactionManager.getTransactionId",Integer.class).setParameter("process","Manpower process").getSingleResult();
			person.setTransId(transId);
			int level=entityManager.createNamedQuery("TransactionManager.getTransactionLevel",Integer.class).setParameter("transId", transId).getSingleResult();
			if(level==0){
				person.setReqInLevel(0);
			}else{
				person.setReqInLevel(1);
			}*/
		}

		return person;
	}


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getPersonIdBasedOnPersonName(String personName){

		return entityManager.createNamedQuery("Person.getPersonIdBasedOnPersonName",
				Integer.class)
				.setParameter("personName", personName)
				.getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getAllPersonsRequiredFilds(String personType) {

		List<?> personList = null;
		
		switch (personType)
		{
			case "Owner":
				personList = entityManager.createNamedQuery("Owner.getAllPersonsRequiredFilds").getResultList();
				break;
			
			case "Tenant":
				personList = entityManager.createNamedQuery("Tenant.getAllPersonsRequiredFilds").getResultList();
				break;
				
			case "Family":
				personList = entityManager.createNamedQuery("Family.getAllPersonsRequiredFilds").getResultList();
				break;
				
			case "DomesticHelp":
				personList = entityManager.createNamedQuery("Domestic.getAllPersonsRequiredFilds").getResultList();
				break;
				
			case "Vendor":
				personList = entityManager.createNamedQuery("Vendors.getAllPersonsRequiredFilds").getResultList();
				break;
				
			case "ConciergeVendor":
				personList = entityManager.createNamedQuery("ConciergeVendors.getAllPersonsRequiredFilds").getResultList();
				break;	
				
			default :
				personList = entityManager.createNamedQuery("Person.getAllPersonsRequiredFilds").setParameter("personType", "%"+personType+"%").getResultList();
				break;	
		}

		return getAllPersonList(personList);

	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List getAllPersonList(List personList)
	{
		List<Map<String, Object>> allPersonDetailsList = new ArrayList<Map<String, Object>>();

		Map<String, Object> personMap = null;

		for (Iterator i = personList.iterator(); i.hasNext();) 
		{
			personMap = new HashMap<String, Object>();

			final Object[] values = (Object[]) i.next();

			personMap.put("personId", (Integer)values[0]);
			personMap.put("personType", (String)values[1]);
			personMap.put("personStyle", (String)values[2]);
			personMap.put("title", (String)values[3]);
			personMap.put("firstName", (String)values[4]);
			personMap.put("middleName", (String)values[5]);
			personMap.put("lastName", (String)values[6]);
			if((String)values[7]!=null){
			personMap.put("fatherName", (String)values[7]);}
			else{
				personMap.put("fatherName", "N/A");}
			personMap.put("spouseName", (String)values[8]);
			personMap.put("dob", (Date)values[9]);
			personMap.put("occupation", (String)values[10]);

			String langTemp  = (String) values[11];
			List<Map<String,String>> languageList = new ArrayList<Map<String,String>>();
			
			if(langTemp != null)
			{
				String[] tempArray = langTemp.split(",");
				
				Map<String,String> mapobject = null;
				for (String value : tempArray) {
					mapobject = new HashMap<String,String>();
					mapobject.put("text", value);
					mapobject.put("value", value);
					languageList.add(mapobject);
				}
			}

			//personMap.put("languagesKnown", languageList);
			
			personMap.put("languagesKnown", (String) values[11]);
			
			personMap.put("languagesKnownDummy", (String) values[11]);
			
			personMap.put("drGroupId", (Integer)values[12]);
			personMap.put("kycCompliant", (String)values[13]);
			personMap.put("createdBy", (String)values[14]);
			personMap.put("lastUpdatedBy", (String)values[15]);
			personMap.put("lastUpdatedDt", (Timestamp)values[16]);
			
			String personName = "";
			personName = personName.concat((String)values[4]);
			
			if(((String)values[5]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[5]);
			}
			
			if(((String)values[6]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[6]);
			}

			personMap.put("personName", personName);
			
			personMap.put("maritalStatus", (String)values[17]);
			personMap.put("sex", (String)values[18]);
			personMap.put("nationality", (String)values[19]);
			personMap.put("bloodGroup", (String)values[20]);
			personMap.put("workNature", (String)values[21]);
			personMap.put("personStatus", (String)values[22]);
			
			if((personMap.get("dob")) != null)
				personMap.put("age", dateTimeCalender.getAgeFromDob((Date)personMap.get("dob")));
			
			if(personMap.get("dob") != null)
				personMap.put("dob", dateTimeCalender.getDateFromString(personMap.get("dob").toString()));
			
			allPersonDetailsList.add(personMap);

		}
		return allPersonDetailsList;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Person> getStaffNames(String type) {

		return entityManager.createNamedQuery("Person.getStaffNames",
				Person.class)
				.setParameter("type", "%"+type+"%")
				.setParameter("status", "Active")
				.getResultList();
	}


	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getPersonIdBasedOnPersonFullName(String fstName,String lstName) {
		return entityManager.createNamedQuery("Person.getPersonIdBasedOnPersonFullNameName",
				Integer.class)
				.setParameter("fstName", fstName)
				.setParameter("lstName", lstName)
				.getResultList();
	}


	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Person getPersonInstanceBasedOnCreatedByAndLastUpdatedDt(
			String createdBy)
	{
		return entityManager.createNamedQuery("Person.getPersonInstanceBasedOnCreatedByAndLastUpdatedDt",
				Person.class)
				.setParameter("createdBy", createdBy)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> getPersonFullDetails(Person person) {

		List<Map<String, Object>> personList = new ArrayList<Map<String, Object>>();

		Map<String, Object> personMap = new HashMap<String, Object>();

		personMap.put("lastUpdatedDt", person.getLastUpdatedDt());
		personMap.put("middleName", person.getMiddleName());
		personMap.put("lastUpdatedBy", person.getLastUpdatedBy());
		personMap.put("title", person.getTitle());
		personMap.put("createdBy", person.getCreatedBy());
		personMap.put("lastName", person.getLastName());
		personMap.put("firstName", person.getFirstName());
		personMap.put("personId", person.getPersonId());
		personMap.put("dob", person.getDob());
		personMap.put("personType", person.getPersonType());
		personMap.put("personStyle", person.getPersonStyle());
		personMap.put("fatherName", person.getFatherName());
		personMap.put("spouseName", person.getSpouseName());
		personMap.put("occupation", person.getOccupation());
		personMap.put("kycCompliant", person.getKycCompliant());

		String langTemp  =  person.getLanguagesKnown();
		List<Map<String,String>> languageList = new ArrayList<Map<String,String>>();
		
		if(langTemp != null)
		{
			String[] tempArray = langTemp.split(",");
			
			Map<String,String> mapobject = null;
			for (String value : tempArray) {
				mapobject = new HashMap<String,String>();
				mapobject.put("text", value);
				mapobject.put("value", value);
				languageList.add(mapobject);
			}
		}

		personMap.put("languagesKnown", languageList);
		personMap.put("personId", person.getPersonId());
		personMap.put("drGroupId", person.getDrGroupId());
		
		String personName = "";
		personName = personName.concat(person.getFirstName());
		
		if(person.getMiddleName() != null)
		{
			personName = personName.concat(" ");
			personName = personName.concat(person.getMiddleName());
		}
		
		if((person.getLastName()) != null)
		{
			personName = personName.concat(" ");
			personName = personName.concat(person.getLastName());
		}

		personMap.put("personName", personName.trim());
		
		personMap.put("maritalStatus", person.getMaritalStatus());
		personMap.put("sex", person.getSex());
		personMap.put("nationality", person.getNationality());
		personMap.put("bloodGroup", person.getBloodGroup());
		personMap.put("workNature", person.getWorkNature());
		personMap.put("personStatus", person.getPersonStatus());
		
		if((personMap.get("dob")) != null)
			personMap.put("age", dateTimeCalender.getAgeFromDob((Date)personMap.get("dob")));
		
		personList.add(personMap);

		return personList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object> getAllPersonNamesList(String personType)
	{
		return entityManager.createNamedQuery("Person.getAllPersonNamesList").setParameter("personType", "%"+personType+"%").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object> getAllPersonFullNamesList(String personType)
	{
		return entityManager.createNamedQuery("Person.getAllPersonFullNamesList").setParameter("personType", "%"+personType+"%").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllAttributeValues(String personType, String attribute)
	{
		String queryKey = "";
		
		switch (attribute)
		{
			case "fatherName":
				queryKey = "Person.getAllFatherName";
				break;

			case "spouseName":
				queryKey = "Person.getAllSpouseName";
				break;
			
			case "occupation":
				queryKey = "Person.getAllOccupation";
				break;
		}
		
		return entityManager.createNamedQuery(queryKey).setParameter("personType", "%"+personType+"%").getResultList();
		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Person> getPersonDetails()
	{
		return entityManager.createNamedQuery("Person.getAllPersonDetails").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object> getAllVendorNamesList()
	{
		return entityManager.createNamedQuery("Person.getAllVendorNamesList").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Person> getAllFamilyDetails() {

		return entityManager.createNamedQuery("Person.getAllFamilyDetails")
				.setParameter("personType", "%FamilyMember%")
				.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllOwnerRecord() {

		return entityManager.createNamedQuery("Person.getAllOwnerRecord")
				.setParameter("personType", "Owner")
				.getResultList();
	}

	/** Read all the staff records for the manpower
	 * @return List of Staff
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> getUserPerson() {
		List<Object> plist =  entityManager.createNamedQuery("Person.getUserPerson")
				.getResultList();
		return getAllUserPersonList(plist);
	}

	/**  Support method for the getUserPerson to fetch all the Staff
	 * @param plist List of Staffs
	 * @return List of Staffs
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private List<Map<String, Object>> getAllUserPersonList(List<Object> plist) {

		Map<Integer, Users> userMap = new HashMap<Integer, Users>();

		for (Iterator<Object> i = plist.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();

			if(!(userMap.containsKey((Integer)values[0])))
			{		
				Users userObj = new Users();
				userObj.setUrId((Integer)values[0]);
				userObj.setUrLoginName((String)values[1]);

				Person person = new Person();
				person.setPersonId((Integer)values[19]);
				person.setPersonType((String)values[20]);
				person.setPersonStyle((String)values[21]);
				person.setTitle((String)values[22]);
				person.setFirstName((String)values[23]);
				person.setMiddleName((String)values[24]);
				person.setLastName((String)values[25]);
				person.setFatherName((String)values[26]);
				if((String)values[27]!=null){
				person.setSpouseName((String)values[27]);
				}
				else{
					person.setSpouseName("NA");
				}
				person.setDob((java.sql.Timestamp)values[28]);
				person.setOccupation((String)values[29]);
				
				person.setLanguagesKnown((String) values[30]);
				
				person.setDrGroupId((Integer)values[31]);
				person.setKycCompliant((String)values[32]);
				person.setCreatedBy((String)values[33]);
				person.setLastUpdatedBy((String)values[34]);
				person.setLastUpdatedDt((Timestamp)values[35]);
				person.setPersonImages((Blob)values[40]);
				person.setSex((String)values[41]);
				person.setMaritalStatus((String)values[42]);
				person.setNationality((String)values[43]);
				person.setBloodGroup((String)values[44]);
				person.setPersonStatus((String)values[45]);
				person.setWorkNature((String)values[46]);
				person.setOccupation((String)values[47]);

				Requisition requisition = new Requisition();
				requisition.setReqId((Integer)values[48]);
				requisition.setReqName((String)values[49]);
				
				userObj.setRequisition(requisition);
				
				userObj.setPerson(person);

				userObj.setCreatedBy((String)values[2]);
				
				
				userObj.setLastUpdatedBy((String)values[3]);
				userObj.setLastUpdatedDt((Timestamp)values[4]);
				userObj.setStatus((String)values[5]);

				Designation designation = new Designation();
				designation.setDn_Id((Integer)values[6]);
				//designation.setDn_Name((String)values[7]);
				
				designation.setDn_Name((String)values[7]);

				userObj.setDesignation(designation);

				Department department = new Department();
				department.setDept_Id((Integer)values[8]);
				//department.setDept_Name((String)values[9]);
				
				department.setDept_Name((String)values[9]);
				
				userObj.setDepartment(department);

				Set<Role> roleSet = new HashSet<Role>();
				Role role = new Role();
				role.setRlId((Integer)values[10]);
				//role.setRlName((String)values[ 11]);
				
				if(((String)values[36]).equalsIgnoreCase("Active"))
					role.setRlName(((String) values[11]).concat("  {Active}"));
				else
					role.setRlName(((String) values[11]).concat("  {Inactive}"));
				
				roleSet.add(role);

				userObj.setRoles(roleSet);

				Set<Groups> groupSet = new HashSet<Groups>();
				Groups groups = new Groups();
				groups.setGr_id((Integer)values[12]);
				//groups.setGr_name((String)values[13]);
				
				String groupStatus = (String)values[37];
				if(groupStatus.equalsIgnoreCase("Active"))					
					groups.setGr_name(((String) values[13]).concat("  {Active}"));
				else
					groups.setGr_name(((String) values[13]).concat("  {Inactive}"));	
				
				groupSet.add(groups);

				userObj.setGroups(groupSet);

				userObj.setStaffType((String)values[14]);

				userObj.setVendorId((Integer)values[15]);

				Vendors vendors = new Vendors();

				vendors.setVendorId((Integer)values[15]);
				Person personVendor = new Person();

				personVendor.setPersonId((Integer)values[16]);
				personVendor.setFirstName((String)values[17]);
				personVendor.setLastName((String)values[18]);
				vendors.setPerson(personVendor);

				userObj.setVendors(vendors);

				userMap.put((Integer)values[0], userObj);
			}

			else
			{
				Users userObj = userMap.get((Integer)values[0]);

				Set<Role> roleSet = userObj.getRoles();

				Role role = new Role();
				role.setRlId((Integer)values[10]);
				//role.setRlName((String)values[11]);
				
				if(((String)values[36]).equalsIgnoreCase("Active"))
					role.setRlName(((String) values[11]).concat("  {Active}"));
				else
					role.setRlName(((String) values[11]).concat("  {Inactive}"));

				if(!(roleSet.contains(role)))
				{
					roleSet.add(role);
				}	

				userObj.setRoles(roleSet);

				Set<Groups> groupSet = userObj.getGroups();

				Groups groups = new Groups();
				groups.setGr_id((Integer)values[12]);
				//groups.setGr_name((String)values[13]);
				String groupStatus = (String)values[37];
				if(groupStatus.equalsIgnoreCase("Active"))					
					groups.setGr_name(((String) values[13]).concat("  {Active}"));
				else
					groups.setGr_name(((String) values[13]).concat("  {Inactive}"));

				if(!(groupSet.contains(groups)))
				{
					groupSet.add(groups);
				}
				userObj.setGroups(groupSet);
			}		
		}

		List<Map<String, Object>> userPersons =  new ArrayList<Map<String, Object>>();

		Collection<Users> col = userMap.values();
		for (Iterator<Users> iterator = col.iterator(); iterator.hasNext();)
		{
			Map<String, Object> usersMap = new HashMap<String, Object>();

			Users userInstance = (Users) iterator.next();	

			usersMap.put("personId", userInstance.getPerson().getPersonId());
			usersMap.put("personType", userInstance.getPerson().getPersonType());
			usersMap.put("personStyle", userInstance.getPerson().getPersonStyle());
			usersMap.put("title", userInstance.getPerson().getTitle());
			usersMap.put("firstName", userInstance.getPerson().getFirstName());
			usersMap.put("middleName", userInstance.getPerson().getMiddleName());
			usersMap.put("lastName", userInstance.getPerson().getLastName());
			usersMap.put("fatherName", userInstance.getPerson().getFatherName());
			usersMap.put("spouseName", userInstance.getPerson().getSpouseName());
			usersMap.put("kycCompliant", userInstance.getPerson().getKycCompliant());
			
			if(userInstance.getPerson().getDob()!=null){
				java.util.Date dobUtil =userInstance.getPerson().getDob();
				java.sql.Date dobSql = new java.sql.Date(dobUtil.getTime());
				usersMap.put("dob",dobSql);
			}else{
				usersMap.put("dob","");
			}
			
			usersMap.put("languagesKnown", userInstance.getPerson().getLanguagesKnown());
			usersMap.put("occupation", userInstance.getPerson().getOccupation());

			usersMap.put("sex", userInstance.getPerson().getSex());
			usersMap.put("maritalStatus", userInstance.getPerson().getMaritalStatus());
			usersMap.put("nationality", userInstance.getPerson().getNationality());
			usersMap.put("bloodGroup", userInstance.getPerson().getBloodGroup());
			usersMap.put("personStatus", userInstance.getPerson().getPersonStatus());
			usersMap.put("workNature", userInstance.getPerson().getWorkNature());
			usersMap.put("occupation",userInstance.getPerson().getOccupation());

			usersMap.put("urId", userInstance.getUrId());
			usersMap.put("urLoginName", userInstance.getUrLoginName());
			//Users users = usersService.find(userInstance.getUrId());
			//if(users!=null){
				usersMap.put("requisition", userInstance.getRequisition().getReqName());
				usersMap.put("reqId", userInstance.getRequisition().getReqId());
			//}
			String personName = "";
			personName = personName.concat(userInstance.getPerson().getFirstName());
			
			if((userInstance.getPerson().getMiddleName()) != null){
				personName = personName.concat(" ");
				personName = personName.concat(userInstance.getPerson().getMiddleName());
			}
			
			if((userInstance.getPerson().getLastName()) != null){
				personName = personName.concat(" ");
				personName = personName.concat(userInstance.getPerson().getLastName());
			}

			usersMap.put("personName", personName.trim());
			String personVendorName = "";
			personVendorName = personVendorName.concat(userInstance.getVendors().getPerson().getFirstName());
			personVendorName = personVendorName.concat(" ");
			
			if(userInstance.getVendors().getPerson().getLastName()!=null)			
				personVendorName = personVendorName.concat(userInstance.getVendors().getPerson().getLastName());
			usersMap.put("vendorName", personVendorName.trim());

			usersMap.put("staffType", userInstance.getStaffType());
			usersMap.put("createdBy", userInstance.getCreatedBy());
			usersMap.put("lastUpdatedBy", userInstance.getLastUpdatedBy());
			usersMap.put("lastUpdatedDt", userInstance.getLastUpdatedDt());	   
			usersMap.put("status", userInstance.getStatus());

			usersMap.put("dn_Id", userInstance.getDesignation().getDn_Id());
			usersMap.put("dn_Name", userInstance.getDesignation().getDn_Name());

			usersMap.put("dept_Id", userInstance.getDepartment().getDept_Id());
			usersMap.put("dept_Name", userInstance.getDepartment().getDept_Name());	

			usersMap.put("roles", userInstance.getRoles());
			usersMap.put("rolesDummy", userInstance.getRoles());
			usersMap.put("groups", userInstance.getGroups());
			usersMap.put("groupsDummy", userInstance.getGroups());

			usersMap.put("vendorId", userInstance.getVendors().getVendorId());

			usersMap.put("drGroupId", userInstance.getPerson().getDrGroupId());
			
			if(userInstance.getPerson().getDob() != null )
				usersMap.put("age", dateTimeCalender.getAgeFromDob(userInstance.getPerson().getDob()));
			userPersons.add(usersMap);
		}
		return userPersons;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getPersonDetailsBasedOnTypes() {
		
		return entityManager.createNamedQuery("Person.getPersonDetailsBasedOnTypes")
				.setParameter("owner", "Owner") .setParameter("tenant", "Tenant")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllPersonDetailsBasedOnPersonType(String personType) {		
		return entityManager.createNamedQuery("Person.getAllPersonDetailsBasedOnPersonType")
				.setParameter("personType", "%"+personType+"%")
				.getResultList();

	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllPersonRequiredDetailsBasedOnPersonType(String personType) 
	{		
		switch (personType)
		{
			case "Owner":
			{
				return entityManager.createNamedQuery("Owner.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
			
			case "Tenant":
			{
				return entityManager.createNamedQuery("Tenant.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
			
			case "Family":
			{
				return entityManager.createNamedQuery("Family.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
			
			case "DomesticHelp":
			{
				return entityManager.createNamedQuery("Domestic.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
			
			case "Staff":
			{
				return entityManager.createNamedQuery("Users.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
			
			case "ConciergeVendor":
			{
				return entityManager.createNamedQuery("ConciergeVendors.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
			
			case "Vendor":
			{
				return entityManager.createNamedQuery("Vendors.getAllPersonRequiredDetailsBasedOnPersonType").getResultList();
			}
				
			default:
			{
				return entityManager.createNamedQuery("Person.getAllPersonDetailsBasedOnPersonType")
						.setParameter("personType", "%"+personType+"%")
						.getResultList();
			}
		}

	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllAccountPersonDetailsBasedOnPersonType(String personType) {		
		
		if(personType.equalsIgnoreCase("Owner"))
			return entityManager.createNamedQuery("Person.getAllAccountPersonDetailsBasedOnPersonTypeAsOwner").getResultList();
		else
			return entityManager.createNamedQuery("Person.getAllAccountPersonDetailsBasedOnPersonTypeAsTenant").getResultList();
	}

	@Override
	public void updatePersonType(Person person, String personType) {
		personType = personType + "," + person.getPersonType();
		entityManager.createNamedQuery("Person.updatePersonType")
		.setParameter("personType", personType)
		.setParameter("personId", person.getPersonId())
		.executeUpdate();
		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonStyle() {
		return entityManager.createNamedQuery("Person.getPersonStyle")
				.setParameter("personType", "%Vendor%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonTitleList() {
		return entityManager.createNamedQuery("Person.getPersonTitle")
				.setParameter("personType", "%Vendor%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonFirstName() {
		return entityManager.createNamedQuery("Person.getPersonFirstName")
				.setParameter("personType", "%Vendor%")
				.getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonMiddleName() {
		return entityManager.createNamedQuery("Person.getPersonMiddleName")
				.setParameter("personType", "%Vendor%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPersonLastName() {
		return entityManager.createNamedQuery("Person.getPersonLastName")
				.setParameter("personType", "%Vendor%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getLanguage() {
		return entityManager.createNamedQuery("Person.getLanguage")
				.setParameter("personType", "%Vendor%")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllVendorDetails() {
		return entityManager.createNamedQuery("Person.getAllVendorDetails")
				.setParameter("personType", "Vendor")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadImageOnId(int personId,Blob personImage) {
		entityManager.createNamedQuery("Person.uploadImageOnId")
				.setParameter("personId", personId)
				.setParameter("personImage", personImage)
				.setParameter("lastUpdatedDate", (new Timestamp(new Date().getTime())))
				.executeUpdate();
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Blob getImage(int personId) {
		
		return (Blob) entityManager.createNamedQuery("Person.getImage", Blob.class)
				.setParameter("personId", personId)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getAllOwnersUnderProperty(String personType, int propertyId) {
		List<?> personList = entityManager.createNamedQuery("Person.getAllOwnersUnderProperty")
				.setParameter("personType", "%"+personType+"%")
				.setParameter("propertyId", propertyId)
				.getResultList();
		
		return getAllPersonDetailsBasedOnPropertyIdAndPersonType(personList,personType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation = Propagation.SUPPORTS)
	private List getAllPersonDetailsBasedOnPropertyIdAndPersonType(List<?> personList,String personType)
	{
		List personNewList = new ArrayList();
		for (Iterator<Object> i = (Iterator<Object>) personList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			Map<String, Object> personMap = new HashMap<String, Object>();
			
			if(personType.equalsIgnoreCase("Owner"))
			{
				personMap.put("Resident",(String)values[8]);
			}
			if(personType.equalsIgnoreCase("DomesticHelp"))
			{
				personMap.put("workNature",(String)values[8]);
			}
			personMap.put("personId", (Integer)values[0]);
			personName = (String)values[1];
			if(((String)values[3]) != null)
			   {
			    personName = personName.concat(" ");
			    personName = personName.concat((String)values[3]);
			   }
			  if(((String)values[2]) != null)
			   {
			    personName = personName.concat(" ");
			    personName = personName.concat((String)values[2]);
			   }
			 
			personMap.put("personName", personName);
			personMap.put("status", (String)values[4]);
		
			if(values[5] != null)
				try {
					personMap.put("age", dateTimeCalender.getAgeFromDob((Date)values[5]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			personMap.put("sex", (String)values[6]);
			personMap.put("bloodGroup", (String)values[7]);
			
			String mobileNumber = "";
			List<Contact> contactList  = entityManager.createNamedQuery("Property.getPersonContactDetails").setParameter("personId", (Integer)values[0]).getResultList();
			if(contactList.size() > 0 && StringUtils.equalsIgnoreCase(contactList.get(0).getContactPrimary(), "Yes"))
			{
				for(Contact c :contactList)
				{
					mobileNumber = mobileNumber +"/"+ c.getContactContent();
				}
				
			}
			mobileNumber = mobileNumber.startsWith("/") ? mobileNumber.substring(1) : mobileNumber;
			if(mobileNumber == "")
			{
				mobileNumber = "No contact details";
			}
			personMap.put("contactContent", mobileNumber);
			
			personNewList.add(personMap);
		}
		
		return personNewList;
	}

	@Override
	public List<?> getAllFamilyUnderProperty(String personType, int propertyId) {
		List<?> personList = entityManager.createNamedQuery("Person.getAllFamilyUnderProperty")
				.setParameter("personType", "%"+personType+"%")
				.setParameter("propertyId", propertyId)
				.getResultList();
		return getAllPersonDetailsBasedOnPropertyIdAndPersonType(personList,personType);
	}

	@Override
	public List<?> getAllTenantUnderProperty(String personType, int propertyId) {
		List<?> personList = entityManager.createNamedQuery("Person.getAllTenantUnderProperty")
				.setParameter("personType", "%"+personType+"%")
				.setParameter("propertyId", propertyId)
				.getResultList();
		return getAllPersonDetailsBasedOnPropertyIdAndPersonType(personList,personType);
	}

	@Override
	public List<?> getAllDomesticHelpUnderProperty(String personType,
			int propertyId) {
		List<?> personList = entityManager.createNamedQuery("Person.getAllDomesticHelpUnderProperty")
				.setParameter("personType", "%"+personType+"%")
				.setParameter("propertyId", propertyId)
				.getResultList();
		return getAllPersonDetailsBasedOnPropertyIdAndPersonType(personList,personType);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getStaffNames(int personId) {
		
		return  entityManager.createNamedQuery("Person.getStaffName")
				.setParameter("personId", "personId")
				.getResultList();
	}

	@Override
	public Object getSinglePerson(int personId)
	{
		Object personObj = entityManager.createNamedQuery("Person.getSinglePersonDetails").setParameter("personId", personId).getSingleResult();		

		return getSinglePersonDetails(personObj);		

	}

	private Object getSinglePersonDetails(Object personObj)
	{
		Map<String, Object> personMap = new HashMap<String, Object>();
		
		final Object[] values = (Object[]) personObj;
		
		personMap.put("personId", (Integer)values[0]);
		personMap.put("personType", (String)values[2]);
		personMap.put("fatherName", (String)values[3]);
		personMap.put("spouseName", (String)values[4]);
		personMap.put("dob", (Date)values[5]);
		personMap.put("occupation", (String)values[6]);
		personMap.put("languagesKnown", (String)values[7]);
		
		personMap.put("maritalStatus", (String)values[8]);
		personMap.put("sex", (String)values[9]);
		personMap.put("nationality", (String)values[10]);
		personMap.put("bloodGroup", (String)values[11]);
		personMap.put("workNature", (String)values[12]);
		
		if((personMap.get("dob")) != null)
			personMap.put("age", dateTimeCalender.getAgeFromDob((Date)personMap.get("dob")));
		
		return personMap;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Person getPersonBasedOnId(int personId) {
		 return  entityManager.createNamedQuery("Person.getPersonBasedOnId",Person.class).setParameter("personId", personId).getSingleResult();		
	}


	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	
	public String setPersonStatus(int personId, String operation, String personType)
	{
		String gateWayUserName=ResourceBundle.getBundle("application").getString("SMS.users.username");
		String gateWayPassword=ResourceBundle.getBundle("application").getString("SMS.users.password");
		Person person = find(personId);
		
		int ownerId=ownerService.getOwnerId(personId);
		
		List<Integer> size=ownerService.getPropertyIdBasedOnownerId(ownerId);
		List<Contact> contacts1 = contactService.executeSimpleQuery("select o from Contact o where o.personId="+personId);
		
		String email1 = null;
		String mobile1 = null;
		for (Contact contact : contacts1) {
			if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
				
				email1=contact.getContactContent();
				
				
				
			}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
				mobile1= contact.getContactContent();
               
             }

		}
		
	
		
		if((email1==null||mobile1==null)&&(personType.equalsIgnoreCase("Owner")||person.getPersonType().equalsIgnoreCase("Tenant"))&&operation.equalsIgnoreCase("activate"))
		{
			
			return "Cannot Activate Without Email and Mobile Number of an Person";
		}
		
		else if(((operation.equalsIgnoreCase("activate"))) && ((person.getKycCompliant() == null) || (!(person.getKycCompliant().contains(personType)))))
		{
			return "Cannot activate since all the required documents are not submitted";
		}
		
		else if(size.size()<=0&&personType.equalsIgnoreCase("Owner")&&operation.equalsIgnoreCase("activate"))
		{
			return "Cannot activate Person without a Property Number";
		}
		else
		{
			String[] types = person.getPersonType().split(",");
			List<String> list = Arrays.asList(types);
			int flag = types.length;

			int statusPersonActive = 0;
			
		/*	List<Contact> contacts1 = contactService.executeSimpleQuery("select o from Contact o where o.personId="+personId);
			
		

			PasswordGenerator Generator = new PasswordGenerator();
			String pwd=Generator.getKeys();
			String email1 = "";
			String mobile1 = "";
			for (Contact contact : contacts1) {
				if(contact.getContactType().equalsIgnoreCase("Email")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
					
					email1=contact.getContactContent();
					
					
					
				}else if(contact.getContactType().equalsIgnoreCase("Mobile")&&contact.getContactPrimary().equalsIgnoreCase("Yes")){
					mobile1= contact.getContactContent();
                   
                 }

			}*/

			
			
			if((list.contains("Owner")) && (person.getOwner().getOwnerStatus().equalsIgnoreCase("InActive"))){
				CAMBillsController.writeToFile("1340. OwnerStatus="+person.getOwner().getOwnerStatus());
				
				statusPersonActive++;
				
				String customerServiceNumber=ResourceBundle.getBundle("application").getString("person.customerServiceNumber");
				String personPassword = RandomPasswordGenerator.generateRandomPassword();
				
				entityManager.createNamedQuery("Person.setPersonStatus")
				.setParameter("personStatus", "Active")
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
				
				
				EmailCredentialsDetailsBean emailCredentialsDetailsBean=null;
				  try {
				  emailCredentialsDetailsBean = new EmailCredentialsDetailsBean();
				} catch (Exception e) {
					e.printStackTrace();
				}
				SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
				MailMaster mailMaster=mailConfigService.getMailMasterByService("Customer Occupancy");
				String mailMessage=mailMaster.getMailMessage();
				String mailSubject=mailMaster.getMailSubject();
				String messageContent="";
		/*		if(CustomerOccupancyManagementController.msgcode==1){
					//messageContent="Dear "+person.getFirstName()+",Your newly generated password id "+personPassword+" - Skyon RWA  Administration services";
					messageContent="<html>"
								 + "<head></head>"
								 + "<body><table><tr><td>"
								 + "Dear "+person.getFirstName()+"</td></tr><tr><td>"
								 + "Your Newly generated password is "+personPassword
								 + "</td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>"
								 + "Regards</td></tr><tr><td>"
								 + "Skyon RWA  Administration services</td></tr></table></body></html>";
				}else{	*/
				messageContent = "<html>"
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
							+ "<h2  align=\"center\"  style=\"background-color:#88ab74;\">WELCOME.</h2> <br /> Dear "+ person.getFirstName() +",<br><br> <p>"+mailMessage+"<br/> <br/>"
							/*Greetings from the Resident Services Team at The Skyon! As a proud owner of an apartment in the Skyon,you can always depend on our team for all matters relating to maintenance and upkeep of condominium facilities and services.Each member of our staff is here to make sure that community life at the Skyon is the very best it can be.</p><br/>Once again wishing you and your family a very warm welcome to The Skyon.*/ 
							
							+ "<table class=\"hovertable\" >" + "<tr>"
							+ "<td colspan='2'></td>"
							+"</tr>" + "<tr>" 
							+"</tr>" + "<tr>" + "<td> Customer Service Portal   :</td>"
							+"<td>"+ "<a href=http://"+ link
							+ ">www.skyon-rwa.com</a></td>"
							+ "</tr>" + "<tr>" + "<td> User Id :</td>"
							+ "<td>" + email1 + "</td>"
							+ "</tr>" + "<tr>" + "<td> Password :</td>" + "<td>"
							+ personPassword + "</td>" + "</tr>" + "</table>"
							+ "</body></html>"
							+ "<br/><br/>"
							+ "<br/>Yours sincerely,<br/>"
							+ "Nitya Mohan <br/>Head- Resident Services. <br/>"
							+"<p><font size=1>This is an auto generated Email. Please don't revert back.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Serviced by Newage Living Solutions</font></p>"
							+"</body></html>";		
			//		}
				
				String personMessage="";
/*				if(CustomerOccupancyManagementController.msgcode==1){
					personMessage="Dear "+person.getFirstName()+",Your newly generated password is "+personPassword+" - Skyon RWA  Administration services";
				}
				else{
					*/
				   personMessage = "Dear "+person.getFirstName()+", Your accounts has been "+"Activated"+". Your account details are username : "+email1+" & password : "+personPassword+" - Skyon RWA  Administration services.";	
	//			}
				
				
				    emailCredentialsDetailsBean.setMailSubject(mailSubject);
				    emailCredentialsDetailsBean.setToAddressEmail(email1);
					emailCredentialsDetailsBean.setMessageContent(messageContent);
				
					new Thread(new SendMailForOwnersDetails(emailCredentialsDetailsBean)).start();
				   

				   
				   

					smsCredentialsDetailsBean.setNumber(mobile1);
					smsCredentialsDetailsBean.setUserName(person.getFirstName());
					smsCredentialsDetailsBean.setMessage(personMessage);
					
					new Thread(new SendSMSForStatus(smsCredentialsDetailsBean)).start();
					
				
				Set set= new HashSet<>();
				
				ldapBusinessModel.addCustomer(email1, personPassword,
						email1, mobile1, set, set);
				
			}
			if((list.contains("Owner")) && (person.getOwner().getOwnerStatus().equalsIgnoreCase("Active")))
			{
				CAMBillsController.writeToFile("1439. OwnerStatus="+person.getOwner().getOwnerStatus());
				entityManager.createNamedQuery("Person.setPersonStatus")
				.setParameter("personStatus", "InActive")
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
				
				Set set= new HashSet<>();
				ldapBusinessModel.deleteCustomer(email1, set, set);
				
				
			}
			
			if((list.contains("Tenant")) && (person.getTenant().getTenantStatus().equalsIgnoreCase("InActive")))
			{
				System.out.println("type----"+list);
				List<Contact> contacts = contactService.executeSimpleQuery("select o from Contact o where o.personId="+personId);
				String email = "";
				String mobile = "";
				for (Contact contact : contacts) {
					if(contact.getContactType().equalsIgnoreCase("Email")){
						email=contact.getContactContent();
					}else if(contact.getContactType().equalsIgnoreCase("Mobile")){
						mobile= contact.getContactContent();
					}
				}
				Set set= new HashSet<>();
				ldapBusinessModel.addCustomer(email, "",
						email, mobile, set, set);
				
				statusPersonActive++;
			}
				
				
				
				
				
				
			if((list.contains("Family")) && (person.getFamily().getFamilyStatus().equalsIgnoreCase("Active")))
				statusPersonActive++;
			if((list.contains("DomesticHelp")) && (person.getDomesticHelp().getDomesticHelpStatus().equalsIgnoreCase("Active")))
				statusPersonActive++;
			
			if(list.contains("Staff"))
			{
				Users users = usersService.getUserInstanceBasedOnPersonId(personId);
				if(users.getStatus().equalsIgnoreCase("Active"))
					statusPersonActive++;
			}
			
			if((list.contains("ConciergeVendor")) && (person.getConciergeVendors().getStatus().equalsIgnoreCase("Active")))
				statusPersonActive++;
			
			if((list.contains("Vendor")) && (person.getVendors().getVendorPersonStatus().equalsIgnoreCase("Active")))
				statusPersonActive++;
			
			/*if((list.contains("Vendor")) && (person.getOwner().getOwnerStatus().equalsIgnoreCase("Active"))){*/
			
			if((list.contains("Vendor")) && (person.getVendors().getVendorPersonStatus().equalsIgnoreCase("Active"))){
			
				statusPersonActive++;
				List<Contact> contacts = contactService.executeSimpleQuery("select o from Contact o where o.personId="+personId);
				String email = "";
				String mobile = "";
				for (Contact contact : contacts) {
					if(contact.getContactType().equalsIgnoreCase("Email")){
						email=contact.getContactContent();
					}else if(contact.getContactType().equalsIgnoreCase("Mobile")){
						mobile= contact.getContactContent();
					}
				}
				Set set= new HashSet<>();
				ldapBusinessModel.addCustomer(email, "",
						email, mobile, set, set);
			}
			
			
			if(operation.equalsIgnoreCase("activate"))
			{
				if((flag - 1) == statusPersonActive)
				{
					entityManager.createNamedQuery("Person.setPersonStatus")
						.setParameter("personStatus", "Active")
						.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
						.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
						.setParameter("personId", personId)
						.executeUpdate();
					return setChildStatus(personType, personId, "Active", "Activated");
				}
				else
					return setChildStatus(personType, personId, "Active", "Activated");
			}
			else
			{
				/*
				 String parentClasses = "OWNER,TENANT,FAMILY,DOMESTIC_HELP,STAFF,VENDORS,CS_VENDORS";
				 Set<String> entrySet = checkChildEntries.getChildEntries("PERSON_ID", personId, parentClasses);
				if(entrySet.size() > 0)
				{
					String classNames = "";
					for (Iterator<String> iterator = entrySet.iterator(); iterator
							.hasNext();)
					{
						String string = (String) iterator.next();
						classNames = classNames.concat(string);
						classNames = classNames.concat(", ");
					}
					classNames = classNames.substring(0, classNames.length() - 2);
					return "Cannot deactivate "+personType+" since this "+personType+" already exists";
				}
				else*/
				{
					entityManager.createNamedQuery("Person.setPersonStatus")
					.setParameter("personStatus", "Inactive")
					.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
					.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
					.setParameter("personId", personId)
					.executeUpdate();
					return setChildStatus(personType, personId, "Inactive", "De-Activated");
				}
			}
		}
	}

	private String setChildStatus(String personType, int personId, String status, String message)
	{
		if(personType.equalsIgnoreCase("Owner"))
		{
			entityManager.createNamedQuery("Owner.setOwnerStatus")
				.setParameter("ownerStatus", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			/******************************************Update the Owner_Property staus as Active****************************/
				entityManager.createNativeQuery("UPDATE OWNER_PROPERTY SET STATUS='Active' WHERE OWNER_ID="
											+ "(SELECT OWNER_ID FROM OWNER WHERE PERSON_ID='"+personId+"')").executeUpdate();
			/***************************************************************************************************************/
			
			return personType+" "+message;
		}
		else if(personType.equalsIgnoreCase("Tenant"))
		{
			entityManager.createNamedQuery("Tenant.setTenantStatus")
				.setParameter("tenantStatus", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			return personType+" "+message;
		}
		else if(personType.equalsIgnoreCase("Family"))
		{
			entityManager.createNamedQuery("Family.setFamilyStatusFromPerson")
				.setParameter("familyStatus", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			return personType+" "+message;
		}
		else if(personType.equalsIgnoreCase("DomesticHelp"))
		{
			entityManager.createNamedQuery("Domestic.setDomesticHelpStatus")
				.setParameter("domesticHelpStatus", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			return "Domestic Help "+message;
		}
		else if(personType.equalsIgnoreCase("Staff"))
		{
			entityManager.createNamedQuery("Users.setUserStatus")
				.setParameter("status", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			return personType+" "+message;
		}
		else if(personType.equalsIgnoreCase("ConciergeVendor"))
		{
			entityManager.createNamedQuery("ConciergeVendors.setConciergeVendorStatus")
				.setParameter("status", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			return "Concierge Vendor "+message;
		}
		else if(personType.equalsIgnoreCase("Vendor"))
		{
			entityManager.createNamedQuery("Vendors.setVendorPersonStatus")
				.setParameter("vendorPersonStatus", status)
				.setParameter("lastUpdatedBy", (String) SessionData.getUserDetails().get("userID"))
				.setParameter("lastUpdatedDt", new Timestamp(new Date().getTime()))
				.setParameter("personId", personId)
				.executeUpdate();
			return "Vendor "+message;
		}else
		{
			return "Error: Please contact your Admin";
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllOwnersOnPropetyId(int propertyId) {
		
		return  entityManager.createNamedQuery("Person.getAllOwnersOnPropetyId",Person.class)
				.setParameter("propertyId", propertyId)
				.getResultList();		
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllTenantOnPropetyId(int propertyId) {
		return  entityManager.createNamedQuery("Person.getAllTenantOnPropetyId",Person.class)
				.setParameter("propertyId", propertyId)
				.getResultList();		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getAllDomesticOnPropetyId(int propertyId) {
		return  entityManager.createNamedQuery("Person.getAllDomesticOnPropetyId",Person.class)
				.setParameter("propertyId", propertyId)
				.getResultList();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object> getAllPersonRequiredFieldsBasedOnPersonStyle(String personStyle) {
		return entityManager.createNamedQuery("Person.getAllPersonRequiredFieldsBasedOnPersonStyle").setParameter("personStyle", personStyle).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getAllPetsOnProperytId(String petType, int propertyId) {
		return  readAllPetsOnProperytId(entityManager.createNamedQuery("Pets.getAllPetsBasedOnPropetyId")
				.setParameter("propertyId", propertyId)
				.getResultList());
	}

	@SuppressWarnings("serial")
	private List<?> readAllPetsOnProperytId(List<Pets> resultList) {
		
		List<Map<String, Object>> pets = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = resultList.iterator(); i.hasNext();) {
			pets.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("petId", (Integer)values[0]);
					put("petName", (String)values[1]);
					put("breedName", (String)values[2]);
					put("petSize", (String)values[3]);
					put("petColor", (String)values[4]);
					put("petSex", (String)values[5]);
					put("petAge", (Integer)values[6]);
					put("emergencyContact", (String)values[7]);
					put("petStatus", (String)values[8]);
					
				}
			});
		}
		return pets;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getCsVendorNames(String type) {
		return entityManager.createNamedQuery("Person.getCsVendorNames",
				Person.class)
				.setParameter("type", "%"+type+"%")
				.setParameter("status", "Active")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Person> getOwnersBasedOnType(String type) {
		return entityManager.createNamedQuery("Person.getOwnersBasedOnType",
				Person.class)
				.setParameter("type", "%"+type+"%")
				.setParameter("status", "Active")
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getOwnersBasedOnBlock(){
		BfmLogger.logger.info("finding all Person instances");
		try {
			return entityManager.createNamedQuery("Person.getOwnersBasedOnBlock")
					.getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Person getPersonBasedOnOwnerId(int ownerId) {
		try{
		return entityManager.createNamedQuery("Person.getPersonBasedOnOwnerId",
				Person.class)
				.setParameter("ownerId", ownerId)
				.getSingleResult();
		}
		catch(Exception e){
			//e.printStackTrace();
		 }
		 return null;
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Person getPersonBasedOnTenantId(int tenantId) {
		try{
		return entityManager.createNamedQuery("Person.getPersonBasedOnTenantId",
				Person.class)
				.setParameter("tenantId", tenantId)
				.getSingleResult();
		}
		catch(Exception e){
			//e.printStackTrace();
		 }
		 return null;
	}

	/** Get Staff and Vendor
	 * @return List of Objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getStaffAndVendorName() {
		return entityManager.createNamedQuery("Person.getStaffAndVendorName").getResultList();
	}

	/** Get Staff or Vendor
	 * @param personType 			Person Type
	 * @return List of Objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getStaffOrVendorName(String personType) {
		return entityManager.createNamedQuery("Person.getStaffOrVendorName").setParameter("personType", "%"+personType+"%").getResultList();
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Person getPersonNewObject(Person person, String operation)
	{
		String title = person.getTitle();
		String personStyle = person.getPersonStyle();

		person.setFirstName(WordUtils.capitalizeFully(person.getFirstName()));
		
		person.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		person.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		
		if(operation.equalsIgnoreCase("update"))
		{
			person.setPersonImages(getImage(person.getPersonId()));
		}
		else if (operation.equalsIgnoreCase("create"))
		{
			person.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			
			//person.setKycCompliant("No");
			person.setPersonStatus("Inactive");
			
			try
			{
				if((title.equalsIgnoreCase("Mr")) || (title.equalsIgnoreCase("M/S")))
				{
						byte[] b=ldapBusinessModel.getImage("bcitsadmin");
						Blob blob = Hibernate.createBlob(b);
						person.setPersonImages(blob);

				}
				else
				{
					byte[] b=ldapBusinessModel.getImageForFemale("bcitsadmin");
					Blob blob = Hibernate.createBlob(b);
					person.setPersonImages(blob);
				}
			}
		
			catch(Exception e)
			{
				e.getMessage();
			}
		}
		
		if(!((personStyle).equalsIgnoreCase("Individual")))
		{
			person.setTitle("M/S");
			person.setFatherName(null);
			person.setLanguagesKnown("None");
			person.setLastName(null);
			person.setOccupation("None");
			person.setMiddleName(null);
			person.setSpouseName(null);
			person.setDob(null);
			
			person.setMaritalStatus("None");
			person.setSex("None");
			person.setNationality("None");
			person.setBloodGroup("None");
			person.setWorkNature("None");
			
		}
		else
		{
			String languageSelected = "";
			
			if(!(person.getLanguagesKnown() instanceof String))
			{
				@SuppressWarnings("unchecked")
				List<Map<String,String>> languagesList = (List<Map<String, String>>) (Object) person.getLanguagesKnown();
				if(!(languagesList.isEmpty()))
				{
					for (Iterator<Map<String, String>> iterator = languagesList.iterator(); iterator.hasNext();) 
					{
						Map<String, String> map2 = (Map<String, String>) iterator.next();
						languageSelected = languageSelected +((String)map2.get("value")) +",";
					}
					languageSelected = languageSelected.substring(0,languageSelected.length()-1);
				}
			}
			else if((person.getLanguagesKnown() == null) || ((person.getLanguagesKnown()).trim().length() == 0)){
				languageSelected = "None";	
			}
			else{
				languageSelected = person.getLanguagesKnown();	
				if (languageSelected.endsWith(",")) {
					languageSelected = languageSelected.substring(0, languageSelected.length() - 1);
				}
			}
			
			person.setLanguagesKnown(languageSelected);
			
			person.setLastName(WordUtils.capitalizeFully(person.getLastName()));
			person.setFatherName(WordUtils.capitalizeFully(person.getFatherName()));
			
			String occupation = person.getOccupation();
			if((occupation != null) && (occupation.trim().length() == 0))
				person.setOccupation("None");

			person.setMiddleName(WordUtils.capitalizeFully(person.getMiddleName()));
			
			if(!StringUtils.equalsIgnoreCase(person.getMaritalStatus(), "Single") || !StringUtils.equalsIgnoreCase(person.getMaritalStatus(), "None"))
			person.setSpouseName(WordUtils.capitalizeFully(person.getSpouseName()));
		}

		return person;
	}

	@Override
	public String updateKycCompliant(String kycCompliant, int personId) 
	{
		final String queryString = "UPDATE Person model SET model.kycCompliant=:kycCompliant WHERE model.personId=:personId";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("kycCompliant", kycCompliant);
		query.setParameter("personId", personId);
		query.executeUpdate();
		return "Documents Approved Successfully";
	}

	@Override
	public int getGroupIdBasedOnPersonId(int personId) {
		List<Integer> groupId =  entityManager.createNamedQuery("Person.getGroupIdBasedOnPersonId",
				Integer.class)
				.setParameter("personId", personId)
				.getResultList();
		Iterator<Integer> it=groupId.iterator();
		while(it.hasNext()){
			return (int) it.next();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTenantsBasedOnBlock() {
		BfmLogger.logger.info("finding all Person instances");
		try {
			return entityManager.createNamedQuery("Person.getTenantsBasedOnBlock")
					.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPersonLanguages() {
		return entityManager.createNamedQuery("Person.getPersonLanguages")
				.setParameter("personType", "%ConciergeVendor%")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getContactNameBasedOnPersonId(int personId) {
		return entityManager.createNamedQuery("Contact.getContactNameBasedOnPersonId")
				.setParameter("personId", personId)
				.getResultList();
	}
	

	@Override
	public Integer findMaxId() {
		return	 entityManager.createNamedQuery("Person.getMaximunId",Integer.class).getSingleResult();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<String> getFirstAndLastName(int personId) {
		 List name=entityManager.createNamedQuery("Person.getFirstAndLastNames").setParameter("personId", personId).getResultList();
		 Iterator<?> itr=name.iterator();
		 List<String> names=new ArrayList<String>();
		 while(itr.hasNext()){
			 Object[] obj=(Object[]) itr.next();
			 for(Object str:obj){
			 names.add((String)str);
			 }
		 }
		 return names;
	}

	@Override
	public int updatePerson(int transId, int levels,int personid) {
		return	 entityManager.createNamedQuery("Person.Update").setParameter("transactionId", transId).setParameter("levels", levels).setParameter("personid", personid).executeUpdate();
		
	}

	@Override
	public Object getSinglePersonId(int name) {
		return	 entityManager.createNamedQuery("Persons.getNameBasedOn PersonId").setParameter("personId", name).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLanguageNew() {
		return entityManager.createNamedQuery("Person.findDistinctIdNew").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetOwnerShip> getOwnerShipNameFilterUrl() {
		return entityManager.createNamedQuery("AssetOwnerShip.findAllList").getResultList();	
	}

	@Override
	public Object[] findBaseOnPersonId(int personId) {
		return (Object[])entityManager.createNamedQuery("PersonPersonID.basedOwnerId").setParameter("personId", personId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllPerson() {
		return entityManager.createNamedQuery("Owner.getAllPersonsRequiredFilds").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllTenant() {
		return entityManager.createNamedQuery("Tenant.getAllPersonsRequiredFilds").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllFamily() {
		return entityManager.createNamedQuery("Family.getAllPersonsRequiredFilds").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllDomestic() {
		return entityManager.createNamedQuery("Domestic.getAllPersonsRequiredFilds").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllUserPerson() {
		
		return entityManager.createNamedQuery("Person.getUserPerson").getResultList();
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllVendor() {
		
		return entityManager.createNamedQuery("Vendors.getAllPersonsRequiredFilds").getResultList();
	}

	@Override
	public List<Integer> getAllPersonIdList() {
		return entityManager.createNamedQuery("Person.personIdOfOwnerTenant",Integer.class).getResultList();
	}

	@Override
	public String getPropertyNoBasedOnPersonId(int personId) {
		return (String)entityManager.createNamedQuery("Property.getPropertyNoBasedOnPersonId").setParameter("personId", personId).getSingleResult();
	}


}