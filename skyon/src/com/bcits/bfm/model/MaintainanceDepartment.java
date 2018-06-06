package com.bcits.bfm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * MaintainanceDepartment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MAINTAINANCE_DEPARTMENT")
@NamedQueries({
	@NamedQuery(name="Maintenance.getDepartments",query = "select obj.mtDpIt,obj.department.dept_Name FROM MaintainanceDepartment obj"),
	@NamedQuery(name="MaintainanceDepartment.findAll",query = "SELECT u.urLoginName,d.dept_Name,md.jobType,d.dept_Id FROM MaintainanceDepartment md INNER JOIN md.users u INNER JOIN md.department d ORDER BY md.mtDpIt DESC"),
	@NamedQuery(name="MaintainanceDepartment.getAllUsers",query = "SELECT model.urLoginName,model.urId FROM Users model WHERE model.deptId=:deptId"),
	@NamedQuery(name="MaintainanceDepartment.getExistingUsersByDepartment",query = "SELECT u.urLoginName,u.urId FROM Users u, MaintainanceDepartment md where u.urId = md.users AND md.department = :department"),
	@NamedQuery(name="MaintainanceDepartment.findByUsersDepartment",query = "SELECT model FROM MaintainanceDepartment model WHERE model.users=:users"),
	@NamedQuery(name="MaintainanceDepartment.remove",query = "DELETE FROM MaintainanceDepartment md WHERE md.mtDpIt=:mtDpIt"),
	@NamedQuery(name="MaintainanceDepartment.getMPerson",query = "SELECT p.firstName,p.lastName,p.personId,p.personType,d.dept_Id FROM MaintainanceDepartment md INNER JOIN md.users u INNER JOIN md.department d INNER JOIN u.person p"),

})
public class MaintainanceDepartment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private int mtDpIt;
	private Users users;
	private Department department;
	private String status;	
	private String jobType;	
	

	// Property accessors
	@Id
	@Column(name = "MT_DP_IT", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "MAINTAINANCE_DEPARTMENT_SEQ", sequenceName = "MAINTAINANCE_DEPARTMENT_SEQ")
	@GeneratedValue(generator = "MAINTAINANCE_DEPARTMENT_SEQ")
	public int getMtDpIt() {
		return this.mtDpIt;
	}

	public void setMtDpIt(int mtDpIt) {
		this.mtDpIt = mtDpIt;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPT_ID", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "STATUS", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "JOBTYPE", nullable = false, length = 45)
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}	
	
	

}