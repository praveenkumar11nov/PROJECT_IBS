package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DEPARTMENT")
@NamedQueries({  
	@NamedQuery(name = "Department.findAllWithoutInvalidDefault", query = "SELECT d FROM Department d WHERE d.dept_Name != 'Invalid' ORDER BY d.dept_Id DESC"),	
	@NamedQuery(name = "Department.getAllActiveDepartments",query="SELECT d from Department d WHERE d.dept_Status = 'Active'"),
		@NamedQuery(name = "Department.getDepartmentIdBasedOnDepartmentName", query = "SELECT d.dept_Id FROM Department d where d.dept_Name=:departmentName"),
		@NamedQuery(name = "departmentDetails", query = "SELECT D FROM Department D  where  D.dept_Id=:DeptId"),
		@NamedQuery(name = "Department.findAll", query = "SELECT D FROM Department D ORDER BY D.dept_Id desc "),
		@NamedQuery(name = "Department.Delete", query = "DELETE FROM Department WHERE dept_Id= :id"),
		@NamedQuery(name = "Department.CheckGroup", query = "select d.dept_Name from Department d where d.dept_Name=:dept_Name"),
		@NamedQuery(name = "Department.checkNameExistence", query = "SELECT d FROM Department d WHERE d.dept_Name= :dept_Name"),
		@NamedQuery(name = "Department.UpdateStatus",query="UPDATE Department d SET  d.dept_Status = :dept_Status WHERE d.dept_Id = :depId"),
		@NamedQuery(name="departmentName",query="select d.dept_Name from Department d"),
		@NamedQuery(name = "Department.getDepartmentIdBasedOnName", query = "SELECT dp.dept_Id FROM Department dp WHERE dp.dept_Name=:dept_Name"),
		@NamedQuery(name="department.getDptName",query="select d.dept_Name from Department d WHERE d.dept_Id=:id"),
		@NamedQuery(name="Department.findAllDeptNames",query="select d.dept_Id,d.dept_Name from Department d "),
})
public class Department {
	@Id
	@SequenceGenerator(name = "DEPARTMENT_SEQ", sequenceName = "DEPARTMENT_SEQ")
	@GeneratedValue(generator = "DEPARTMENT_SEQ")
	@Column(name = "DEPT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int dept_Id;

	@Column(name = "DEPT_NAME", length = 1020)
	@Size(min = 2)
	@Pattern(regexp = "^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$",
	             message = "Department name field can not allow special symbols except(_ .) ")
	private String dept_Name;

	@Size(min=1,max=200,message="Department Description  Cannot be empty")
	@Column(name = "DEPT_DESC", length = 1020)
	private String dept_Desc;

	@Column(name = "DEPT_STATUS", length = 1020)
	private String dept_Status;

	@Column(name = "CREATED_BY", precision = 11, scale = 0)
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY", precision = 11, scale = 0)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public Department() {
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Department(int dept_Id, String dept_Name, String dept_Desc,
			String dept_Status, String createdBy, String lastUpdatedBy) {
		super();
		this.dept_Id = dept_Id;
		this.dept_Name = dept_Name;
		this.dept_Desc = dept_Desc;
		this.dept_Status = dept_Status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public int getDept_Id() {
		return dept_Id;
	}

	public String getDept_Name() {
		return dept_Name;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}

	public String getDept_Desc() {
		return dept_Desc;
	}

	public String getDept_Status() {
		return dept_Status;
	}

	public void setDept_Desc(String dept_Desc) {
		this.dept_Desc = dept_Desc;
	}

	public void setDept_Status(String dept_Status) {
		this.dept_Status = dept_Status;
	}

	
}
