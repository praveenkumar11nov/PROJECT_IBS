package com.bcits.bfm.model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;





import com.bcits.bfm.patternMasterEntity.TransactionMaster;

import net.sf.oval.constraint.MatchPattern;

/**
 * Entity which includes attributes and their getters and setters mapping with 'PERSON' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "PERSON")
@NamedQueries({
	@NamedQuery(name = "Person.setPersonStatus", query = "UPDATE Person p SET p.personStatus = :personStatus, p.lastUpdatedBy = :lastUpdatedBy, p.lastUpdatedDt = :lastUpdatedDt WHERE p.personId = :personId"),
	@NamedQuery(name="Person.findDistinctIdNew",query="SELECT p.languagesKnown FROM Person p"),
	@NamedQuery(name = "Person.getPersonStyle", query = "SELECT p.personStyle FROM Person p WHERE p.personType LIKE :personType "),
	@NamedQuery(name = "Person.getPersonTitle", query = "SELECT p.title FROM Person p WHERE p.personType LIKE :personType "),
	@NamedQuery(name = "Person.getPersonFirstName", query = "SELECT p.firstName FROM Person p WHERE p.personType LIKE :personType "),
	@NamedQuery(name = "Person.getPersonLanguages", query = "SELECT p.languagesKnown FROM Person p WHERE p.personType LIKE :personType "),
	@NamedQuery(name = "Person.getPersonMiddleName", query = "SELECT p.middleName FROM Person p WHERE p.personType LIKE :personType "),
	@NamedQuery(name = "Person.getPersonLastName", query = "SELECT p.lastName FROM Person p WHERE p.personType LIKE :personType "),
	@NamedQuery(name = "Person.getLanguage", query = "SELECT p.languagesKnown FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.updatePersonType", query = "UPDATE Person p SET p.personType = :personType WHERE p.personId = :personId"),
	@NamedQuery(name = "Person.getAllPersonDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt FROM Person p WHERE p.personStatus = 'Active' AND p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllAccountPersonDetailsBasedOnPersonTypeAsTenant", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
					+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
					+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt FROM Person p INNER JOIN p.tenant t WHERE p.personId NOT IN (SELECT DISTINCT a.personId FROM Account a) AND t.tenantStatus = 'Active' AND p.personType LIKE '%Tenant%'"),
					@NamedQuery(name = "Person.getAllAccountPersonDetailsBasedOnPersonTypeAsOwner", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
							+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
							+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt FROM Person p INNER JOIN p.owner o WHERE p.personId NOT IN (SELECT DISTINCT a.personId FROM Account a) AND o.ownerStatus = 'Active' AND p.personType LIKE '%Owner%'"),
	@NamedQuery(name = "Person.getAllFirstName", query = "SELECT DISTINCT p.firstName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllMiddleName", query = "SELECT DISTINCT p.middleName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllLastName", query = "SELECT DISTINCT p.lastName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllFatherName", query = "SELECT DISTINCT p.fatherName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllSpouseName", query = "SELECT DISTINCT p.spouseName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllOccupation", query = "SELECT DISTINCT p.occupation FROM Person p WHERE p.personType LIKE :personType"),	
	@NamedQuery(name = "Person.getAllVendorNamesList", query = "SELECT DISTINCT(p.firstName), p.lastName FROM Person p where p.personType LIKE '%Vendor%'"),
	@NamedQuery(name = "Person.getAllPersonNamesList", query = "SELECT DISTINCT(p.firstName), p.lastName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllPersonFullNamesList", query = "SELECT DISTINCT(p.firstName), p.lastName, p.middleName FROM Person p WHERE p.personType LIKE :personType"),
	@NamedQuery(name = "Person.getAllPersonRequiredFieldsBasedOnPersonStyle", query = "SELECT p.firstName, p.lastName, p.personId, p.personType, p.personStyle FROM Person p WHERE p.personStyle = :personStyle"),
	@NamedQuery(name = "Person.getStaffName", query = "SELECT p FROM Person p WHERE p.personId=:personId "),
	@NamedQuery(name = "Person.getPersonInstanceBasedOnCreatedByAndLastUpdatedDt", query = "SELECT p FROM Person p WHERE p.lastUpdatedDt = (SELECT MAX(p1.lastUpdatedDt) FROM Person p1 WHERE p1.createdBy = :createdBy) AND p.createdBy = :createdBy"),
	@NamedQuery(name = "Person.getPersonIdBasedOnPersonName", query = "SELECT p.personId FROM Person p WHERE p.lastName=:personName"),
	@NamedQuery(name = "Person.getPersonIdBasedOnCreatedByAndLastUpdatedDt", query = "SELECT p.personId FROM Person p WHERE p.createdBy = :createdBy AND p.lastUpdatedDt = :lastUpdatedDt"),
	@NamedQuery(name = "Person.findAllStaff", query = "SELECT DISTINCT(p.personId),p.firstName, p.lastName,p.dob, p.occupation, p.languagesKnown, p.personType, p.personStyle, p.title,  p.fatherName,  dn.dn_Name, dt.dept_Name, u.staffType, u.urLoginName, u.status, ur.aggreRoleName FROM  Person p, Users u, Designation dn, Department dt,Role r, UserRole ur WHERE p.personId = u.personId AND u.deptId = dt.dept_Id AND u.dnId = dn.dn_Id AND u.urId = ur.uro_Ur_Id AND ur.uro_Rl_Id = r.rlId AND (p.personType LIKE '%Staff%') ORDER BY p.personId DESC"),
	@NamedQuery(name = "Person.findAllStaff1", query = "SELECT p.personId,p.firstName, p.lastName,p.dob, p.occupation, p.languagesKnown, p.personType, p.personStyle, p.title,  p.fatherName,  dn.dn_Name, dt.dept_Name, u.staffType, u.urLoginName, u.status, ur.aggreRoleName FROM  Person p, Users u, Designation dn, Department dt,Role r, UserRole ur WHERE p.personId = u.personId AND u.deptId = dt.dept_Id AND u.dnId = dn.dn_Id AND u.urId = ur.uro_Ur_Id AND ur.uro_Rl_Id = r.rlId AND (p.personType LIKE '%Staff%') ORDER BY p.personId DESC"),
	@NamedQuery(name = "Person.getAllPersonsRequiredFilds", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature, p.personStatus FROM Person p WHERE p.personType LIKE :personType ORDER BY p.personId DESC"),
	@NamedQuery(name = "Person.getStaffNames", query = "SELECT p FROM Person p,Users u,UserRole ur,PatrolSettings ps WHERE p.personType LIKE :type and u.status=:status and p.personId = u.personId and u.urId=ur.uro_Ur_Id and ur.uro_Rl_Id=ps.rlId and ps.status=:status"),
	@NamedQuery(name = "Person.getCsVendorNames", query = "SELECT p FROM Person p WHERE p.personType LIKE :type and p.personStatus=:status"),
	@NamedQuery(name = "Person.getPersonIdBasedOnPersonFullNameName", query = "SELECT p.personId FROM Person p WHERE p.firstName=:fstName and p.lastName=:lstName"),
	@NamedQuery(name = "Person.getAllPersonDetails", query = "SELECT p.firstName,p.personId FROM Person p"),
	@NamedQuery(name = "Person.getAllFamilyDetails", query = "SELECT p FROM Person p WHERE p.personType LIKE :personType ORDER BY p.personId DESC"),
	@NamedQuery(name = "Person.getUserPerson", query="SELECT u.urId, u.urLoginName, u.createdBy, u.lastUpdatedBy, u.lastUpdatedDt, u.status, dn.dn_Id, dn.dn_Name, dt.dept_Id, dt.dept_Name, r.rlId, r.rlName, g.gr_id, g.gr_name, u.staffType, u.vendorId, vp.personId, vp.firstName, vp.lastName, p.personId, p.personType, p.personStyle,p.title, p.firstName, p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, r.rlStatus, g.gr_status, dt.dept_Status, dn.dr_Status, p.personImages, p.sex,p.maritalStatus, p.nationality, p.bloodGroup,p.personStatus,p.workNature,p.occupation,req.reqId, req.reqName FROM Users u JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt JOIN u.requisition req INNER JOIN u.roles r INNER JOIN u.groups g WHERE (p.personType LIKE '%Staff%') ORDER BY u.urId DESC"),
	@NamedQuery(name = "Person.getAllOwnerRecord", query = "SELECT p FROM Person p WHERE p.personType= :personType"),
	@NamedQuery(name = "Person.getPersonDetailsBasedOnTypes", query = "SELECT p FROM Person p WHERE p.personType IN (:owner , :tenant ) " ),
	@NamedQuery(name = "Person.getAllVendorDetails", query = "SELECT p FROM Person p WHERE p.personType=:personType ORDER BY p.personId DESC"),
	@NamedQuery(name = "Person.uploadImageOnId", query = "UPDATE Person p SET p.personImages= :personImage, p.lastUpdatedDt= :lastUpdatedDate WHERE p.personId= :personId" ),
	@NamedQuery(name = "Person.getImage", query = "SELECT p.personImages FROM Person p WHERE p.personId= :personId" ),
	@NamedQuery(name="Person.getAllOwnersUnderProperty", query="SELECT IP.personId, IP.firstName, IP.lastName,IP.middleName,IP.personStatus,IP.dob,IP.sex,IP.bloodGroup,OP.residential FROM Person IP, Owner O, OwnerProperty OP WHERE IP.personType LIKE :personType and IP.personId = O.personId AND O.ownerId = OP.ownerId AND OP.propertyId = :propertyId"),
	@NamedQuery(name="Person.getAllFamilyUnderProperty", query="SELECT IP.personId, IP.firstName, IP.lastName,IP.middleName,IP.personStatus,IP.dob,IP.sex,IP.bloodGroup FROM Person IP, Family O, FamilyProperty OP WHERE IP.personType LIKE :personType and IP.personId = O.personId AND O.familyId = OP.familyId AND OP.propertyId = :propertyId"),
	@NamedQuery(name="Person.getAllTenantUnderProperty",query="SELECT IP.personId, IP.firstName, IP.lastName,IP.middleName,IP.personStatus,IP.dob,IP.sex,IP.bloodGroup FROM Person IP, Tenant O, TenantProperty OP WHERE IP.personType LIKE :personType and IP.personId = O.personId AND O.tenantId = OP.tenantId AND OP.propertyId = :propertyId"),
	@NamedQuery(name="Person.getAllDomesticHelpUnderProperty",query="SELECT IP.personId, IP.firstName, IP.lastName,IP.middleName,IP.personStatus,IP.dob,IP.sex,IP.bloodGroup,OP.workNature FROM Person IP, Domestic O, DomesticProperty OP WHERE IP.personType LIKE :personType and IP.personId = O.personId AND O.domesticId = OP.domasticId AND OP.propertyId = :propertyId"),
	@NamedQuery(name="Person.getDrGroupId",query="SELECT p.drGroupId FROM Person p WHERE p.personId = :personId"),
	@NamedQuery(name="Person.getSinglePersonDetails",query="SELECT p.personId, p.title, p.personType, p.fatherName, p.spouseName, p.dob, p.occupation, p.languagesKnown, p.maritalStatus, p.sex, p.nationality, p.bloodGroup, p.workNature FROM Person p WHERE p.personId = :personId"),
	@NamedQuery(name="Person.getPersonBasedOnId",query="SELECT p FROM Person p WHERE p.personId = :personId"),
	@NamedQuery(name="Person.getAllOwnersOnPropetyId",query="SELECT p FROM Person p WHERE p.personId!=1 AND p.personId IN (SELECT o.personId FROM Owner o WHERE o.ownerId IN ("
			+ "(SELECT op.ownerId FROM OwnerProperty op WHERE op.propertyId=:propertyId)))" ),
	@NamedQuery(name="Person.getAllTenantOnPropetyId",query="SELECT p FROM Person p WHERE p.personId IN (SELECT t.personId FROM Tenant t WHERE t.tenantId IN ("
			+ "(SELECT tp.tenantId FROM TenantProperty tp WHERE tp.propertyId=:propertyId)))" ),
	@NamedQuery(name="Person.getAllDomesticOnPropetyId",query="SELECT p FROM Person p WHERE p.personId IN (SELECT d.personId FROM Domestic d WHERE d.domesticId IN ("
			+ "(SELECT dp.domasticId FROM DomesticProperty dp WHERE dp.propertyId=:propertyId)))" ),
	@NamedQuery(name="Pets.getAllPetsOnPropetyId",query="SELECT p FROM Pets p WHERE p.propertyId = :propertyId "),
	@NamedQuery(name="Person.getStaffAndVendorName",query="SELECT DISTINCT(p.personId), p.firstName, p.lastName,p.personType,c.contactType,c.contactContent FROM Person p,Contact c WHERE (p.personType LIKE '%Staff%' OR p.personType LIKE '%Vendor%') AND p.personId = c.personId AND c.contactPrimary = 'Yes' AND c.contactType = 'Mobile' "),
	@NamedQuery(name="Person.getStaffOrVendorName",query="SELECT DISTINCT(p.personId), p.firstName, p.lastName,p.personType,c.contactType,c.contactContent FROM Person p,Contact c WHERE p.personType LIKE :personType  AND p.personId = c.personId AND c.contactPrimary = 'Yes' AND c.contactType = 'Mobile' "),
	@NamedQuery(name = "Person.getOwnersBasedOnType", query = "SELECT p FROM Person p WHERE p.personType LIKE :type and p.personStatus=:status"),
	@NamedQuery(name = "Person.getPersonBasedOnOwnerId", query = "SELECT p FROM Person p,Owner o WHERE o.ownerId=:ownerId AND o.personId=p.personId"),
	@NamedQuery(name = "Person.getPersonBasedOnTenantId", query = "SELECT p FROM Person p,Tenant t WHERE t.tenantId=:tenantId AND t.personId=p.personId"),
	@NamedQuery(name = "Person.getOwnersBasedOnBlock", query = "SELECT DISTINCT(p.personId),p.personType,p.firstName,p.lastName,pr.property_No,pr.propertyId,b.blockName,o.ownerId FROM Person p,Owner o,OwnerProperty op,Property pr,Blocks b WHERE p.personType='Owner' AND o.ownerStatus='Active' AND p.personId=o.personId AND o.ownerId=op.ownerId AND op.propertyId=pr.propertyId AND pr.blockId=b.blockId "),
	@NamedQuery(name = "Person.getTenantsBasedOnBlock", query = "SELECT DISTINCT(p.personId),p.personType,p.firstName,p.lastName,pr.property_No,pr.propertyId,b.blockName,t.tenantId FROM Person p,Tenant t,TenantProperty tp,Property pr,Blocks b WHERE p.personType='Tenant' AND t.tenantStatus='Active' AND p.personId=t.personId AND t.tenantId=tp.tenantId AND tp.propertyId=pr.propertyId AND pr.blockId=b.blockId "),
	@NamedQuery(name = "Person.getGroupIdBasedOnPersonId", query = "SELECT p.drGroupId FROM Person p WHERE p.personId=:personId"),
    @NamedQuery(name="Person.getPersonObjectBasedOnIDNew",query="SELECT p FROM Person p WHERE p.personId=:personId"),
	
	@NamedQuery(name = "Contact.getContactNameBasedOnPersonId", query = "SELECT p FROM Person p WHERE p.personId=:personId"),
	@NamedQuery(name="Person.getMaximunId",query="SELECT max(dr.personId) FROM Person dr WHERE dr.personType LIKE 'Owner'"),
	@NamedQuery(name="Person.getMaxTenantId",query="SELECT max(dr.personId) FROM Person dr WHERE personType='Tenant'"),
	@NamedQuery(name="Vendors.GetPersonId",query="SELECT max(dr.personId) FROM Person dr WHERE personType='Vendor'"),
	@NamedQuery(name="Family.GetPersonId",query="SELECT max(dr.personId) FROM Person dr WHERE personType='Family'"),

	@NamedQuery(name="Person.getFirstAndLastNames",query="SELECT dr.firstName,dr.lastName FROM Person dr WHERE personId=:personId"),
	@NamedQuery(name="Person.updateTransId",query="UPDATE Person p SET p.reqInLevel = :reqInLevel , p.transId = :transId WHERE p.personId=(SELECT u.personId FROM Users u WHERE u.urId=:urId)"),
	@NamedQuery(name="Persons.getNameBasedOnPersonId",query="SELECT dr.firstName FROM Person dr WHERE personId=:personId"),
	@NamedQuery(name="PersonPersonID.basedOwnerId",query="SELECT dr.firstName,dr.middleName,dr.lastName FROM Person dr WHERE personId=:personId"),

	@NamedQuery(name="Person.personIdOfOwnerTenant",query="select p.personId from Person p where p.personType LIKE 'Owner' OR p.personType LIKE 'Tenant'"),

	@NamedQuery(name="Person.personDetails",query="SELECT count(*),(select count(*)  from Person u WHERE u.personStatus='Active' AND u.personType=:type),(select count(*)  from Person p WHERE p.personStatus='Inactive' AND p.personType=:type) FROM Person dr WHERE personType=:type group by 1"),
	@NamedQuery(name="Person.customerDetails",query="SELECT count(*),(select count(*)  from Person u WHERE u.personStatus='Active' AND (u.personType='Owner')),(select count(*)  from Person p WHERE p.personStatus='Inactive' AND (p.personType='Owner')) FROM Person dr WHERE dr.personType='Owner' group by 1"),

	
})
@Access(AccessType.FIELD)
public class Person implements java.io.Serializable
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7604104222187567994L;

	@Id
	@SequenceGenerator(name = "personSeq", sequenceName = "PERSON_SEQ")
	@GeneratedValue(generator = "personSeq")
	@Column(name = "PERSON_ID",insertable=false,updatable=false)
	private int personId;
	
	@net.sf.oval.constraint.Size(min = 1, message = "Size.Person.personType", profiles = {"Individual", "NonIndividual"})
	@Column(name = "PERSON_TYPE")
	private String personType;
	
	@net.sf.oval.constraint.Size(min = 1, message = "Size.Person.personStyle", profiles = {"Individual", "NonIndividual"})
	@Column(name = "PERSON_STYLE")
	private String personStyle;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Title is not selected", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "Title is not selected", profiles = {"Individual"})*/
	@Column(name = "TITLE")
	private String title;
	
	@net.sf.oval.constraint.Size(min = 1, max = 150, message = "Size.Person.firstName", profiles = {"NonIndividual"})
	@MatchPattern( pattern = "^[a-zA-Z]{1,100}$", message = "MatchPattern.Person.firstName", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.firstName", profiles = {"Individual", "NonIndividual"})
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@net.sf.oval.constraint.Size(min = 0, max = 45, message = "Size.Person.middleName", profiles = {"Individual"})
	@MatchPattern(pattern = "^[a-zA-Z ]{0,45}$", message = "MatchPattern.Person.middleName", profiles = {"Individual"})
	//@net.sf.oval.constraint.NotNull(message = "NotNull.Person.middleName", profiles = {"Individual"})
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@net.sf.oval.constraint.Size(min = 1, max = 45, message = "Size.Person.lastName", profiles = {"Individual"})
	@MatchPattern(pattern = "^[a-zA-Z ]{1,45}$", message = "MatchPattern.Person.lastName", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.lastName", profiles = {"Individual"})
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@net.sf.oval.constraint.Size(min = 1, max = 45, message = "Size.Person.fatherName", profiles = {"Individual"})
	@MatchPattern(pattern = "^[a-zA-Z ]{1,45}$", message = "MatchPattern.Person.fatherName", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.fatherName", profiles = {"Individual"})
	@Column(name = "FATHER_NAME")
	private String fatherName;
	
	@net.sf.oval.constraint.Size(min = 0, max = 45, message = "Size.Person.spouseName", profiles = {"Individual"})
	@MatchPattern(pattern = "^[a-zA-Z ]{0,45}$", message = "MatchPattern.Person.spouseName", profiles = {"Individual"})
	/*@net.sf.oval.constraint.NotNull(message = "NotNull.Person.spouseName", profiles = {"Individual"})*/
	@Column(name = "SPOUSE_NAME")
	private String spouseName;
	
	/*@net.sf.oval.constraint.Past(message = "Past.Person.dob", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.dob", profiles = {"Individual"})*/
	@Column(name = "DOB")
	private java.util.Date dob;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Size.Person.nationality", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.nationality", profiles = {"Individual"})*/
	@Column(name="NATIONALITY")
	private String nationality;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Size.Person.bloodGroup", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.bloodGroup", profiles = {"Individual"})*/
	@Column(name="BLOOD_GROUP")
	private String bloodGroup;
	
	@net.sf.oval.constraint.Size(max = 45, message = "Size.Person.occupation", profiles = {"Individual"})
	@MatchPattern(pattern  = "^[a-zA-Z ]{0,45}$", message = "MatchPattern.Person.occupation", profiles = {"Individual"})
	/*@net.sf.oval.constraint.NotNull(message = "NotNull.Person.occupation", profiles = {"Individual"})*/
	@Column(name = "OCCUPATION")
	private String occupation;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Size.Person.languagesKnown", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "Size.Person.languagesKnown", profiles = {"Individual"})*/
	@Size(min = 0, max = 150, message = "Please select appropriate number of languages")
	@Column(name = "LANGUAGES_KNOWN")
	private String languagesKnown;
	
	@Column(name = "DR_GROUP_ID")
	private int drGroupId;
	
	//@net.sf.oval.constraint.Size(min = 1, message = "Size.Person.kycCompliant", profiles = {"Individual", "NonIndividual"})
	@Column(name = "KYC_COMPLIANT")
	private String kycCompliant;
	
	@Column(name="STATUS")
	private String personStatus;
	
	@net.sf.oval.constraint.Size(min = 1, message = "Size.Person.createdBy", profiles = {"Individual", "NonIndividual"})
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@net.sf.oval.constraint.Size(min = 1, message = "Size.Person.lastUpdatedBy", profiles = {"Individual", "NonIndividual"})
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	@Lob
    @Column(name="PERSON_IMAGE")
	private Blob personImages;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Size.Person.maritalStatus", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.NotNull", profiles = {"Individual"})*/
	@Column(name="MARITAL_STATUS")
	private String maritalStatus;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Size.Person.maritalStatus", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.maritalStatus", profiles = {"Individual"})*/
	@Column(name="SEX")
	private String sex;
	
	/*@net.sf.oval.constraint.Size(min = 2, message = "Size.Person.workNature", profiles = {"Individual"})
	@net.sf.oval.constraint.NotNull(message = "NotNull.Person.workNature", profiles = {"Individual"})*/
	@Column(name="WORK_NATURE")
	private String workNature;
	
	
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Owner owner;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Tenant tenant;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Family family;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Domestic domesticHelp;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Users users;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Vendors vendors;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL, mappedBy = "person")
	@JoinColumn(name = "PERSON_ID")
	@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private ConciergeVendors conciergeVendors;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_ID", nullable = false)
	private Set<Address> addresses = new HashSet<Address>(0);
		
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID", nullable = false)
	private Set<Contact> contacts = new HashSet<Contact>(0);
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID", nullable = false)
	private Set<MedicalEmergencyDisability> medicalEmergencyDisabilities = new HashSet<MedicalEmergencyDisability>(0);

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID", nullable = false)
	private Set<Arms> arms = new HashSet<Arms>(0);

	
	@Column(name = "REQ_IN_LEVEL")
	private Integer reqInLevel;
	
	@Column(name = "TRANS_ID")
	private Integer transId;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "TRANS_ID",insertable=false,updatable=false)
	private TransactionMaster transactionMaster;
	// Property accessors
	
	/**
	 * Gets the Id of this person.
	 * 
	 * @return This person Id.
	 * @since 0.1
	 */
	public int getPersonId()
	{
		return personId;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", personType=" + personType
				+ ", personStyle=" + personStyle + ", title=" + title
				+ ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", fatherName=" + fatherName
				+ ", spouseName=" + spouseName + ", dob=" + dob
				+ ", nationality=" + nationality + ", bloodGroup=" + bloodGroup
				+ ", occupation=" + occupation + ", languagesKnown="
				+ languagesKnown + ", drGroupId=" + drGroupId
				+ ", kycCompliant=" + kycCompliant + ", personStatus="
				+ personStatus + ", createdBy=" + createdBy
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDt="
				+ lastUpdatedDt + ", personImages=" + personImages
				+ ", maritalStatus=" + maritalStatus + ", sex=" + sex
				+ ", workNature=" + workNature + ", owner=" + owner
				+ ", tenant=" + tenant + ", family=" + family
				+ ", domesticHelp=" + domesticHelp + ", users=" + users
				+ ", vendors=" + vendors + ", conciergeVendors="
				+ conciergeVendors + ", addresses=" + addresses + ", contacts="
				+ contacts + ", medicalEmergencyDisabilities="
				+ medicalEmergencyDisabilities + ", arms=" + arms
				+ ", getPersonId()=" + getPersonId() + ", getPersonType()="
				+ getPersonType() + ", getPersonStyle()=" + getPersonStyle()
				+ ", getTitle()=" + getTitle() + ", getFirstName()="
				+ getFirstName() + ", getMiddleName()=" + getMiddleName()
				+ ", getLastName()=" + getLastName() + ", getFatherName()="
				+ getFatherName() + ", getSpouseName()=" + getSpouseName()
				+ ", getDob()=" + getDob() + ", getNationality()="
				+ getNationality() + ", getBloodGroup()=" + getBloodGroup()
				+ ", getOccupation()=" + getOccupation()
				+ ", getLanguagesKnown()=" + getLanguagesKnown()
				+ ", getDrGroupId()=" + getDrGroupId() + ", getKycCompliant()="
				+ getKycCompliant() + ", getPersonStatus()="
				+ getPersonStatus() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getLastUpdatedBy()=" + getLastUpdatedBy()
				+ ", getLastUpdatedDt()=" + getLastUpdatedDt()
				+ ", getPersonImages()=" + getPersonImages()
				+ ", getMaritalStatus()=" + getMaritalStatus() + ", getSex()="
				+ getSex() + ", getWorkNature()=" + getWorkNature()
				+ ", getOwner()=" + getOwner() + ", getTenant()=" + getTenant()
				+ ", getFamily()=" + getFamily() + ", getDomesticHelp()="
				+ getDomesticHelp() + ", getUsers()=" + getUsers()
				+ ", getVendors()=" + getVendors() + ", getConciergeVendors()="
				+ getConciergeVendors() + ", getAddresses()=" + getAddresses()
				+ ", getContacts()=" + getContacts()
				+ ", getMedicalEmergencyDisabilities()="
				+ getMedicalEmergencyDisabilities() + ", getArms()="
				+ getArms() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	/**
	 * Changes the Id of this person.
	 * 
	 * @param personId
	 *            This person new Id.
	 * @since 0.1
	 */

	

	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	/**
	 * Gets the type of this person.
	 * 
	 * @return This person type.
	 * @since 0.1
	 */
	public String getPersonType()
	{
		return personType;
	}

	/**
	 * Changes the type of this person.
	 * 
	 * @param personType
	 *            This person new type.
	 * @since 0.1
	 */
	public void setPersonType(String personType)
	{
		this.personType = personType;
	}

	/**
	 * Gets the style of this person.
	 * 
	 * @return This person style.
	 * @since 0.1
	 */
	public String getPersonStyle()
	{
		return personStyle;
	}

	/**
	 * Changes the style of this person.
	 * 
	 * @param personStyle
	 *            This person new style.
	 * @since 0.1
	 */
	public void setPersonStyle(String personStyle)
	{
		this.personStyle = personStyle;
	}

	/**
	 * Gets the title of this person.
	 * 
	 * @return This person title.
	 * @since 0.1
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Changes the title of this person.
	 * 
	 * @param title
	 *            This person new title.
	 * @since 0.1
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the first name of this person.
	 * 
	 * @return This person first name.
	 * @since 0.1
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Changes the first name of this person.
	 * 
	 * @param firstName
	 *            This person new first name.
	 * @since 0.1
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * Gets the middle name of this person.
	 * 
	 * @return This person middle name.
	 * @since 0.1
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * Changes the middle name of this person.
	 * 
	 * @param middleName
	 *            This person new middle name.
	 * @since 0.1
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * Gets the last name of this person.
	 * 
	 * @return This person last name.
	 * @since 0.1
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Changes the middle last of this person.
	 * 
	 * @param lastName
	 *            This person new last name.
	 * @since 0.1
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * Gets the father name of this person.
	 * 
	 * @return This person father name.
	 * @since 0.1
	 */
	public String getFatherName()
	{
		return fatherName;
	}

	/**
	 * Changes the father last of this person.
	 * 
	 * @param fatherName
	 *            This person new father name.
	 * @since 0.1
	 */
	public void setFatherName(String fatherName)
	{
		this.fatherName = fatherName;
	}

	/**
	 * Gets the spouse name of this person.
	 * 
	 * @return This person spouse name.
	 * @since 0.1
	 */
	public String getSpouseName()
	{
		return spouseName;
	}

	/**
	 * Changes the spouse name of this person.
	 * 
	 * @param spouseName
	 *            This person new spouse name.
	 * @since 0.1
	 */
	public void setSpouseName(String spouseName)
	{
		this.spouseName = spouseName;
	}

	/**
	 * Gets the date of birth of this person.
	 * 
	 * @return This person date of birth.
	 * @since 0.1
	 */
	public java.util.Date getDob()
	{
		return dob;
	}

	/**
	 * Changes the date of birth of this person.
	 * 
	 * @param dob
	 *            This person new date of birth.
	 * @since 0.1
	 */
	public void setDob(java.util.Date dob)
	{
		this.dob = dob;
	}

	/**
	 * Gets the nationality of this person.
	 * 
	 * @return This person nationality.
	 * @since 0.1
	 */
	public String getNationality()
	{
		return nationality;
	}

	/**
	 * Changes the nationality of this person.
	 * 
	 * @param nationality
	 *            This person new nationality.
	 * @since 0.1
	 */
	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	/**
	 * Gets the blood group of this person.
	 * 
	 * @return This person blood group.
	 * @since 0.1
	 */
	public String getBloodGroup()
	{
		return bloodGroup;
	}

	/**
	 * Changes the blood group of this person.
	 * 
	 * @param blood group
	 *            This person new blood group.
	 * @since 0.1
	 */
	public void setBloodGroup(String bloodGroup)
	{
		this.bloodGroup = bloodGroup;
	}

	/**
	 * Gets the occupation of this person.
	 * 
	 * @return This person occupation.
	 * @since 0.1
	 */
	public String getOccupation()
	{
		return occupation;
	}

	/**
	 * Changes the occupation of this person.
	 * 
	 * @param occupation
	 *            This person new occupation.
	 * @since 0.1
	 */
	public void setOccupation(String occupation)
	{
		this.occupation = occupation;
	}

	/**
	 * Gets the languages known of this person.
	 * 
	 * @return This person languages known.
	 * @since 0.1
	 */
	public String getLanguagesKnown()
	{
		return languagesKnown;
	}

	/**
	 * Changes the languages known of this person.
	 * 
	 * @param languagesKnown
	 *            This person new languages known.
	 * @since 0.1
	 */
	public void setLanguagesKnown(String languagesKnown)
	{
		this.languagesKnown = languagesKnown;
	}

	/**
	 * Gets this person group id.
	 * 
	 * @return Group id of this person
	 * @since 0.1
	 */
	public int getDrGroupId()
	{
		return drGroupId;
	}

	/**
	 * Changes the group id this person.
	 * 
	 * @param drGroupId
	 *            New group id of this person.
	 * @since 0.1
	 */
	public void setDrGroupId(int drGroupId)
	{
		this.drGroupId = drGroupId;
	}

	/**
	 * Gets the kyc compliant of this person.
	 * 
	 * @return This person kyc compliant.
	 * @since 0.1
	 */
	public String getKycCompliant()
	{
		return kycCompliant;
	}

	/**
	 * Changes the kyc compliant of this person.
	 * 
	 * @param kycCompliant
	 *            This person new kyc compliant.
	 * @since 0.1
	 */
	public void setKycCompliant(String kycCompliant)
	{
		this.kycCompliant = kycCompliant;
	}

	/**
	 * Gets the status of this person.
	 * 
	 * @return This person status.
	 * @since 0.1
	 */
	public String getPersonStatus()
	{
		return personStatus;
	}

	/**
	 * Changes the status of this person.
	 * 
	 * @param kycCompliant
	 *            This person new status.
	 * @since 0.1
	 */
	public void setPersonStatus(String personStatus)
	{
		this.personStatus = personStatus;
	}

	/**
	 * Gets this person created by user name.
	 * 
	 * @return Created by user name of this person.
	 * @since 0.1
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Changes the user name who created this person.
	 * 
	 * @param createdBy
	 *            New user name who created this person.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets this person last updated by user name.
	 * 
	 * @return Last updated by user name of this person.
	 * @since 0.1
	 */
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}

	/**
	 * Changes the user name who last updated this person.
	 * 
	 * @param lastUpdatedBy
	 *            New user name who last updated this person.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets this person last updated date time.
	 * 
	 * @return Last updated date time of this person.
	 * @since 0.1
	 */
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}

	/**
	 * Changes the last updated date time this person.
	 * 
	 * @param lastUpdatedDt
	 *            New last updated date time of this person.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}

	/**
	 * Gets the image of this person.
	 * 
	 * @return This person image.
	 * @since 0.1
	 */
	public Blob getPersonImages()
	{
		return personImages;
	}

	/**
	 * Changes the image of this person.
	 * 
	 * @param personImages
	 *            This person new image.
	 * @since 0.1
	 */
	public void setPersonImages(Blob personImages)
	{
		this.personImages = personImages;
	}

	/**
	 * Gets the marital status of this person.
	 * 
	 * @return This person marital status.
	 * @since 0.1
	 */
	public String getMaritalStatus()
	{
		return maritalStatus;
	}

	/**
	 * Changes the marital status of this person.
	 * 
	 * @param maritalStatus
	 *            This person new marital status.
	 * @since 0.1
	 */
	public void setMaritalStatus(String maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	/**
	 * Gets the sex of this person.
	 * 
	 * @return This person sex.
	 * @since 0.1
	 */
	public String getSex()
	{
		return sex;
	}

	/**
	 * Changes the sex of this person.
	 * 
	 * @param sex
	 *            This person new sex.
	 * @since 0.1
	 */
	public void setSex(String sex)
	{
		this.sex = sex;
	}

	/**
	 * Gets the work nature of this person.
	 * 
	 * @return This person work nature.
	 * @since 0.1
	 */
	public String getWorkNature()
	{
		return workNature;
	}

	/**
	 * Changes the work nature of this person.
	 * 
	 * @param workNature
	 *            This person new work nature.
	 * @since 0.1
	 */
	public void setWorkNature(String workNature)
	{
		this.workNature = workNature;
	}

	/**
	 * Gets the owner as this person if exists.
	 * 
	 * @return This person as a Owner if exists.
	 * @since 0.1
	 */
	public Owner getOwner()
	{
		return owner;
	}

	/**
	 * Changes the owner as this person if exists.
	 * 
	 * @param owner
	 *            This person as a new owner.
	 * @since 0.1
	 */
	public void setOwner(Owner owner)
	{
		this.owner = owner;
	}

	/**
	 * Gets the Tenant as this person if exists.
	 * 
	 * @return This person as a Tenant if exists.
	 * @since 0.1
	 */
	public Tenant getTenant()
	{
		return tenant;
	}

	/**
	 * Changes the tenant as this person if exists.
	 * 
	 * @param tenant
	 *            This person as a new tenant.
	 * @since 0.1
	 */
	public void setTenant(Tenant tenant)
	{
		this.tenant = tenant;
	}

	/**
	 * Gets the family as this person if exists.
	 * 
	 * @return This person as a family if exists.
	 * @since 0.1
	 */
	public Family getFamily()
	{
		return family;
	}

	/**
	 * Changes the family as this person if exists.
	 * 
	 * @param family
	 *            This person as a new family.
	 * @since 0.1
	 */
	public void setFamily(Family family)
	{
		this.family = family;
	}

	/**
	 * Gets the domestic help as this person if exists.
	 * 
	 * @return This person as a domestic help if exists.
	 * @since 0.1
	 */
	public Domestic getDomesticHelp()
	{
		return domesticHelp;
	}

	/**
	 * Changes the domestic help as this person if exists.
	 * 
	 * @param family
	 *            This person as a new domestic help.
	 * @since 0.1
	 */
	public void setDomesticHelp(Domestic domesticHelp)
	{
		this.domesticHelp = domesticHelp;
	}

	/**
	 * Gets the user as this person if exists.
	 * 
	 * @return This person as a user if exists.
	 * @since 0.1
	 */
	public Users getUsers()
	{
		return users;
	}

	/**
	 * Changes the user as this person if exists.
	 * 
	 * @param users
	 *            This person as a new user.
	 * @since 0.1
	 */
	public void setUsers(Users users)
	{
		this.users = users;
	}

	/**
	 * Gets the vendor as this person if exists.
	 * 
	 * @return This person as a vendor if exists.
	 * @since 0.1
	 */
	public Vendors getVendors()
	{
		return vendors;
	}

	/**
	 * Changes the vendor as this person if exists.
	 * 
	 * @param vendors
	 *            This person as a new vendor.
	 * @since 0.1
	 */
	public void setVendors(Vendors vendors)
	{
		this.vendors = vendors;
	}

	/**
	 * Gets the concierge vendors as this person if exists.
	 * 
	 * @return This person as a concierge vendors if exists.
	 * @since 0.1
	 */
	public ConciergeVendors getConciergeVendors()
	{
		return conciergeVendors;
	}

	/**
	 * Changes the concierge vendors as this person if exists.
	 * 
	 * @param conciergeVendors
	 *            This person as a new concierge vendors.
	 * @since 0.1
	 */
	public void setConciergeVendors(ConciergeVendors conciergeVendors)
	{
		this.conciergeVendors = conciergeVendors;
	}

	/**
	 * Gets the addresses of this person if exists.
	 * 
	 * @return This person's addresses if exists.
	 * @since 0.1
	 */
	public Set<Address> getAddresses()
	{
		return addresses;
	}

	/**
	 * Changes the addresses of this person if exists.
	 * 
	 * @param addresses
	 *            This person's new addresses.
	 * @since 0.1
	 */
	public void setAddresses(Set<Address> addresses)
	{
		this.addresses = addresses;
	}

	/**
	 * Gets the contacts of this person if exists.
	 * 
	 * @return This person's contacts if exists.
	 * @since 0.1
	 */
	public Set<Contact> getContacts()
	{
		return contacts;
	}

	/**
	 * Changes the contacts of this person if exists.
	 * 
	 * @param contacts
	 *            This person's new contacts.
	 * @since 0.1
	 */
	public void setContacts(Set<Contact> contacts)
	{
		this.contacts = contacts;
	}

	/**
	 * Gets the medical emergency disabilities of this person if exists.
	 * 
	 * @return This person's medical emergency disabilities if exists.
	 * @since 0.1
	 */
	public Set<MedicalEmergencyDisability> getMedicalEmergencyDisabilities()
	{
		return medicalEmergencyDisabilities;
	}

	/**
	 * Changes the medical emergency disabilities of this person if exists.
	 * 
	 * @param medicalEmergencyDisabilities
	 *            This person's new medical emergency disabilities.
	 * @since 0.1
	 */
	public void setMedicalEmergencyDisabilities(
			Set<MedicalEmergencyDisability> medicalEmergencyDisabilities)
	{
		this.medicalEmergencyDisabilities = medicalEmergencyDisabilities;
	}

	/**
	 * Gets the arms of this person if exists.
	 * 
	 * @return This person's arms if exists.
	 * @since 0.1
	 */
	public Set<Arms> getArms()
	{
		return arms;
	}
	
	/**
	 * Changes the arms of this person if exists.
	 * 
	 * @param arms
	 *            This person's new arms.
	 * @since 0.1
	 */
	public void setArms(Set<Arms> arms)
	{
		this.arms = arms;
	}

	public int getReqInLevel() {
		return reqInLevel;
	}

	public void setReqInLevel(int reqInLevel) {
		this.reqInLevel = reqInLevel;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}
}