package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity which includes attributes and their getters and setters mapping with 'USERS' table.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
	@NamedQuery(name = "Users.checkUserLoginNameUniqueness", query = "SELECT u.urLoginName FROM Users u WHERE u.urLoginName= :userLoginName"),
	@NamedQuery(name = "Users.getAllUsersLoginNames", query = "SELECT u.urLoginName FROM Users u"),
	@NamedQuery(name = "Users.getUserInstanceByLoginName", query = "SELECT u from Users u where u.urLoginName=:userLoginName"),
	@NamedQuery(name = "Users.UpdateStatus", query = "UPDATE Users u SET u.status = :status, u.lastUpdatedBy = :lastUpdatedBy, u.lastUpdatedDt = :lastUpdatedDt WHERE u.urId = :urId"),
	@NamedQuery(name = "Users.UpdateApprovalStatus", query = "UPDATE Users u SET u.status = :status WHERE u.urId = :urId"),
	
	
	@NamedQuery(name = "Users.getAllUserApproval", query = "SELECT u.urId, u.urLoginName, p.personId, p.firstName, p.lastName, u.createdBy, u.lastUpdatedBy, u.lastUpdatedDt, u.status, dn.dn_Id, dn.dn_Name, dt.dept_Id, dt.dept_Name, r.rlId, r.rlName, g.gr_id, g.gr_name, u.staffType, u.vendorId, vp.personId, vp.firstName, vp.lastName, r.rlStatus, g.gr_status, dt.dept_Status, dn.dr_Status, p.kycCompliant,p.reqInLevel,p.transId,t.dnId,t.lNum FROM Users u,TransactionDetail t JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g WHERE p.transId=t.tId AND p.reqInLevel=t.lNum AND u.status=:status1 AND p.personType LIKE '%Staff%' ORDER BY u.urId DESC"),
	@NamedQuery(name = "Users.getDesignationId", query = "SELECT u.dnId FROM Users u WHERE u.urLoginName=:urLoginName"),
	@NamedQuery(name = "Users.getAllUserApprovals", query = "SELECT  u.urId,u.createdBy, u.lastUpdatedDt, u.status,t.dnId FROM Users u,TransactionDetail t JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g WHERE p.transId=t.tId AND p.reqInLevel=t.lNum AND u.status=:status1 AND p.personType LIKE '%Staff%' ORDER BY u.urId DESC"),
	@NamedQuery(name = "Users.getDataForNotification", query = "select t from Users u,TransactionDetail t WHERE u.status='Inprogress'and u.person.transId = t.tId AND u.person.reqInLevel = t.lNum AND t.dnId=:did"),

	
	
	
	
	//@NamedQuery(name = "Users.getAllUsersRequiredFilds", query = "SELECT u.urId, u.urLoginName, p.personId, p.firstName, p.lastName, u.createdBy, u.lastUpdatedBy, u.lastUpdatedDt, u.status, dn.dn_Id, dn.dn_Name, dt.dept_Id, dt.dept_Name, r.rlId, r.rlName, g.gr_id, g.gr_name, u.staffType, u.vendorId, vp.personId, vp.firstName, vp.lastName, r.rlStatus, g.gr_status, dt.dept_Status, dn.dr_Status, p.kycCompliant FROM Users u JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g WHERE p.personType LIKE '%Staff%' ORDER BY u.urId DESC"),
	@NamedQuery(name = "Users.getAllUsersRequiredFilds", query = "SELECT u.urId, u.urLoginName, p.personId, p.firstName, p.lastName, u.createdBy, u.lastUpdatedBy, u.lastUpdatedDt, u.status, dn.dn_Id, dn.dn_Name, dt.dept_Id, dt.dept_Name, r.rlId, r.rlName, g.gr_id, g.gr_name, u.staffType, u.vendorId, vp.personId, vp.firstName, vp.lastName, r.rlStatus, g.gr_status, dt.dept_Status, dn.dr_Status, p.kycCompliant,rq.reqId, rq.reqName FROM Users u JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g Join u.requisition rq WHERE p.personType LIKE '%Staff%' ORDER BY u.urId DESC"),
	@NamedQuery(name = "Users.getAllStaffRecords", query = "SELECT u.urId, u.urLoginName, p.personId, p.firstName, p.lastName, u.createdBy, u.lastUpdatedBy, u.lastUpdatedDt, u.status, dn.dn_Id, dn.dn_Name, dt.dept_Id, dt.dept_Name, r.rlId, r.rlName, g.gr_id, g.gr_name, u.staffType, u.vendorId, vp.personId, vp.firstName, vp.lastName FROM Users u JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g WHERE p.personType='Staff' ORDER BY u.urId DESC"),
	@NamedQuery(name = "Users.getUserInstanceByUserPersonId", query = "SELECT u.urId, u.urLoginName, p.personId, p.firstName, p.lastName, u.createdBy, u.lastUpdatedBy, u.lastUpdatedDt, u.status, dn.dn_Id, dn.dn_Name, dt.dept_Id, dt.dept_Name, r.rlId, r.rlName, g.gr_id, g.gr_name, u.staffType, u.vendorId, vp.personId, vp.firstName, vp.lastName FROM Users u JOIN u.person p JOIN u.vendors v INNER JOIN v.person vp JOIN u.designation dn JOIN u.department dt INNER JOIN u.roles r INNER JOIN u.groups g WHERE u.personId = :personId"),
	@NamedQuery(name = "Users.getAll", query = "SELECT u FROM Users u ORDER BY u.urId DESC"),
	@NamedQuery(name="Users.getPersonId",query="select u.personId from Users u where u.urLoginName=:urLoginName"),
	@NamedQuery(name="Users.getUserInstanceBasedOnPersonId",query="select u from Users u where u.personId = :personId"),
	@NamedQuery(name = "Users.setAllPrevDepartmentsBasedOnDeptId", query = "UPDATE Users u SET u.deptId = (SELECT d.dept_Id FROM Department d WHERE d.dept_Name = 'Invalid') WHERE u.deptId = :deptId"),
	@NamedQuery(name = "Users.setAllPrevDesignationsBasedOnDnId", query = "UPDATE Users u SET u.dnId = (SELECT d.dn_Id FROM Designation d WHERE d.dn_Name = 'Invalid') WHERE u.dnId = :dnId"),
	@NamedQuery(name = "Users.checkDepartmentExistence", query = "SELECT u.urLoginName from Users u WHERE u.deptId = :deptId"),
	@NamedQuery(name = "Users.checkDesignationExistence", query = "SELECT u.urLoginName from Users u WHERE u.dnId = :dnId"),
	@NamedQuery(name = "Users.getLoginNameByPersonId", query= "SELECT u.urLoginName from Users u WHERE u.personId = :personId"),
	@NamedQuery(name = "Users.getAllUserIdsAndPersonNames", query = "SELECT u.urId, p.firstName, p.lastName FROM Users u INNER JOIN u.person p ORDER BY p.firstName"),
	@NamedQuery(name = "Users.getLoginNameBasedOnId",query = "SELECT u.urLoginName FROM Users u WHERE u.urId =:id"),
	@NamedQuery(name = "Users.setUserStatus", query = "UPDATE Users u SET u.status = :status WHERE u.personId = :personId"),
	@NamedQuery(name = "Users.getAllPersonRequiredDetailsBasedOnPersonType", query = "SELECT p.personId, p.personType, p.personStyle, p.title, p.firstName, "
			+ "p.middleName, p.lastName, p.fatherName, p.spouseName, p.dob, p.occupation, "
			+ "p.languagesKnown, p.drGroupId, p.kycCompliant, p.createdBy, p.lastUpdatedBy, p.lastUpdatedDt, u.urId FROM Person p INNER JOIN p.users u WHERE u.status LIKE 'Active'"),
	
	@NamedQuery(name="Users.getAllUsersBasedOnStatus",query="SELECT ur FROM Users ur WHERE ur.status='Approved'"),
	@NamedQuery(name="Users.setStatus",query="UPDATE Users a SET a.status = :status WHERE a.urId = :urId"),
	
	@NamedQuery(name="Users.roleFilter",query="SELECT r.rlName,g.gr_name FROM Users u INNER JOIN u.roles r INNER JOIN u.groups g"),
//	@NamedQuery(name="Users.getAllDetailsOfUser",query="SELECT u.personId,ct.contactContent FROM Users u INNER JOIN CONTACTS ct WHERE u.personId=ct.personId AND u.urLoginName= :user"  ),
	@NamedQuery(name="Users.userDetails",query="select count(*),(select count(*)  from Users u WHERE u.status='Active' AND u.urId!=1),(select count(*)  from Users u WHERE u.status='Inactive' and u.urId!=1) from Users x WHERE x.urId!=1 group by 1"),
	@NamedQuery(name="Users.getPersonListBasedOnDeptDesigId",query="SELECT u from Users u WHERE u.deptId = :deptId AND u.dnId = :desigId"),




	
	
/*	@NamedQuery(name = "Users.getUserInstanceByLoginName1", query = "SELECT u from Users u where u.urLoginName=:userLoginName"),
*/

})
	
public class Users
{
	@Id
	@SequenceGenerator(name = "UsersSeq", sequenceName = "USERS_SEQ")
	@GeneratedValue(generator = "UsersSeq")
	@Column(name = "UR_ID")
	private int urId;

	@NotNull
	@Column(name = "UR_LOGIN_NAME")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z]+[._a-zA-Z0-9._]*[a-zA-Z0-9]$", message = "Login name field can not allow special symbols except(_ .)")
	private String urLoginName;

	@Min(value = 1, message = "'Department' is not selected")
	@Column(name = "DEPT_ID", precision = 10, scale = 0)
	private int deptId;

	@Min(value = 1, message = "'Designation' is not selected")
	@Column(name = "DN_ID", precision = 10, scale = 0)
	private int dnId;

	@Column(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	private int personId;
	
	@OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	private Person person;
	
	@OneToOne
	@JoinColumn(name = "DN_ID", insertable = false, updatable = false, nullable = false)
	private Designation designation;

	@OneToOne
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
	private Department department;

	@NotEmpty(message = "At least one 'Role' should be selected")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "URO_UR_ID")
	private Set<UserRole> userRoles;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "URO_UR_ID", referencedColumnName = "UR_ID") }, inverseJoinColumns = { @JoinColumn(name = "URO_RL_ID", referencedColumnName = "RL_ID") })
	private Set<Role> roles;

	@NotEmpty(message = "At least one 'Group' should be selected")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "UG_UR_ID")
	private Set<UserGroup> userGroups;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_GROUP", joinColumns = { @JoinColumn(name = "UG_UR_ID", referencedColumnName = "UR_ID") }, inverseJoinColumns = { @JoinColumn(name = "UG_GR_ID", referencedColumnName = "GR_ID") })
	private Set<Groups> groups;
	
	@Column(name = "STAFF_TYPE")
	private String staffType;
		
	@Column(name = "VENDOR_ID")
	private int vendorId;
	
	@OneToOne
	@JoinColumn(name = "VENDOR_ID", insertable = false, updatable = false, nullable = false)
	private Vendors vendors;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;

	@Column(name = "STATUS")
	private String status;
	
	
	
	
	/**
	 * Gets the Id of this User.
	 * 
	 * @return This User Id.
	 * @since 0.1
	 */
	public int getUrId() 
	{
		return urId;
	}

	/**
	 * Changes the Id of this User.
	 * 
	 * @param urId
	 *            This User new Id.
	 * @since 0.1
	 */
	public void setUrId(int urId) 
	{
		this.urId = urId;
	}

	/**
	 * Gets the Login Name of this User.
	 * 
	 * @return This User Login Name.
	 * @since 0.1
	 */
	public String getUrLoginName() 
	{
		return urLoginName;
	}

	/**
	 * Changes the Login Name of this User.
	 * 
	 * @param urLoginName
	 *            This User new and unique Login Name.
	 * @since 0.1
	 */
	public void setUrLoginName(String urLoginName) 
	{
		this.urLoginName = urLoginName;
	}

	/**
	 * Gets the Department Id of this User.
	 * 
	 * @return This User Department Id.
	 * @since 0.1
	 */
	public int getDeptId() 
	{
		return deptId;
	}

	/**
	 * Changes the Department Id of this User.
	 * 
	 * @param deptId
	 *            This User new Department Id.
	 * @since 0.1
	 */
	public void setDeptId(int deptId) 
	{
		this.deptId = deptId;
	}

	/**
	 * Gets the Designation Id of this User.
	 * 
	 * @return This User Designation Id.
	 * @since 0.1
	 */
	public int getDnId() 
	{
		return dnId;
	}

	/**
	 * Changes the Designation Id of this User.
	 * 
	 * @param dnId
	 *            This User new Designation Id.
	 * @since 0.1
	 */
	public void setDnId(int dnId) 
	{
		this.dnId = dnId;
	}

	/**
	 * Gets the person Id of this User.
	 * 
	 * @return This User person Id.
	 * @since 0.1
	 */
	public Person getPerson()
	{
		return person;
	}

	/**
	 * Changes the person of this User.
	 * 
	 * @param person
	 *            This User as a new person.
	 * @since 0.1
	 */
	public void setPerson(Person person)
	{
		this.person = person;
	}

	/**
	 * Gets the person id of the User.
	 * 
	 * @return Person id of this User.
	 * @since 0.1
	 */
	public int getPersonId()
	{
		return personId;
	}

	/**
	 * Changes the person Id of this User.
	 * 
	 * @param personId
	 *            This User new person Id.
	 * @since 0.1
	 */
	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	/**
	 * Gets the Staff type of the User.
	 * 
	 * @return Staff type of this User.
	 * @since 0.1
	 */
	public String getStaffType()
	{
		return staffType;
	}

	/**
	 * Set the Staff type of this User.
	 * 
	 * @param staffType
	 *            Staff type of this User.
	 * @since 0.1
	 */
	public void setStaffType(String staffType)
	{
		this.staffType = staffType;
	}

	/**
	 * Gets the vendor id of the User.
	 * 
	 * @return The vendor id of this User.
	 * @since 0.1
	 */
	public int getVendorId()
	{
		return vendorId;
	}

	/**
	 * Set the vendor id of this User.
	 * 
	 * @param vendorId
	 *            Vendor id of this User.
	 * @since 0.1
	 */
	public void setVendorId(int vendorId)
	{
		this.vendorId = vendorId;
	}

	/**
	 * Gets the name of the User who has created this User.
	 * 
	 * @return The name of the User who has created this User.
	 * @since 0.1
	 */
	public String getCreatedBy() 
	{
		return createdBy;
	}

	/**
	 * Set the name of the User who has created this User.
	 * 
	 * @param createdBy
	 *            Name of the User who is creating this User.
	 * @since 0.1
	 */
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets the name of the User who has updated this User previously.
	 * 
	 * @return Name of the User who has updated this User previously.
	 * @since 0.1
	 */
	public String getLastUpdatedBy() 
	{
		return lastUpdatedBy;
	}

	/**
	 * Set the name of the User who has updated this User.
	 * 
	 * @param lastUpdatedBy
	 *            Name of the User who is updating this User.
	 * @since 0.1
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * Gets the date when this user is created/last updated.
	 * 
	 * @return Date when the user is last updated.
	 * @since 0.1
	 */
	public Timestamp getLastUpdatedDt() 
	{
		return lastUpdatedDt;
	}

	/**
	 * Set the Date when this user is created/updated.
	 * 
	 * @param lastUpdatedDt
	 *            This User new last updated date.
	 * @since 0.1
	 */
	public void setLastUpdatedDt(Timestamp lastUpdatedDt) 
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}

	/**
	 * Gets the Designation object of this User.
	 * 
	 * @return Designation object of this User.
	 * @since 0.1
	 */
	public Designation getDesignation() 
	{
		return designation;
	}

	/**
	 * Changes the Designation object of this User.
	 * 
	 * @param designation
	 *            New Designation object of this User.
	 * @since 0.1
	 */
	public void setDesignation(Designation designation) 
	{
		this.designation = designation;
	}

	/**
	 * Gets the Department object of this User.
	 * 
	 * @return Department object of this User.
	 * @since 0.1
	 */

	public Department getDepartment() 
	{
		return department;
	}

	/**
	 * Changes the Department object of this User.
	 * 
	 * @param department
	 *            New Department object of this User.
	 * @since 0.1
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * Get all the Role objects of this User.
	 * 
	 * @return All the Role objects of this User.
	 * @since 0.1
	 */
	public Set<Role> getRoles() 
	{
		return roles;
	}

	/**
	 * Change all the Role objects of this User.
	 * 
	 * @param roles
	 *            All the new Role objects of this User.
	 * @since 0.1
	 */
	public void setRoles(Set<Role> roles) 
	{
		this.roles = roles;
	}

	/**
	 * Get all the Role objects of this User from User role table along with log
	 * details.
	 * 
	 * @return All the Role objects of this User from User role table along with
	 *         log details.
	 * @since 0.1
	 */
	public Set<UserRole> getUserRoles()
	{
		return userRoles;
	}

	/**
	 * Change all the Role objects of this User into User role table along with
	 * log details.
	 * 
	 * @param userRoles
	 *            All the new Role objects of this User along with log details.
	 * @since 0.1
	 */
	public void setUserRoles(Set<UserRole> userRoles) 
	{
		this.userRoles = userRoles;
	}

	/**
	 * Get all the Group objects of this User.
	 * 
	 * @return All the Group objects of this User.
	 * @since 0.1
	 */
	public Set<Groups> getGroups() 
	{
		return groups;
	}

	/**
	 * Change all the Group objects of this User.
	 * 
	 * @param groups
	 *            All the new Group objects of this User.
	 * @since 0.1
	 */
	public void setGroups(Set<Groups> groups) 
	{
		this.groups = groups;
	}

	/**
	 * Get all the Group objects of this User.
	 * 
	 * @return All the Group objects of this User.
	 * @since 0.1
	 */
	public Set<UserGroup> getUserGroups() 
	{
		return userGroups;
	}

	/**
	 * Change all the Group objects of this User into User role table along with
	 * log details.
	 * 
	 * @param userGroups
	 *            All the new Group objects of this User along with log details.
	 * @since 0.1
	 */
	public void setUserGroups(Set<UserGroup> userGroups) 
	{
		this.userGroups = userGroups;
	}

	/**
	 * Get status of this User.
	 * 
	 * @return Status of this User.
	 * @since 0.1
	 */
	public String getStatus() 
	{
		return status;
	}

	/**
	 * Change status of this User. 
	 * @param status
	 *            Status of this User.
	 * @since 0.1
	 */
	public void setStatus(String status) 
	{
		this.status = status;
	}
	
	/**
	 * Gets the vendor of this user.
	 * 
	 * @return This user's vendor.
	 * @since 0.1
	 */
	public Vendors getVendors()
	{
		return vendors;
	}

	/**
	 * Changes the vendor of this User.
	 * 
	 * @param vendors
	 *            This User new vendor.
	 * @since 0.1
	 */
	public void setVendors(Vendors vendors)
	{
		this.vendors = vendors;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="REQ_ID",insertable=false, updatable=false,nullable=true)
	private Requisition requisition;

	public Requisition getRequisition() {
		return requisition;
	}

	public void setRequisition(Requisition requisition) {
		this.requisition = requisition;
	}

	@Column(name = "REQ_ID",nullable=true)
	private int reqId;
	
	public int getReqId() {
		return reqId;
	}

	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	
}






























