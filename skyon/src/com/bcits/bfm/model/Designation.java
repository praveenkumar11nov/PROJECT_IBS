package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "DESIGNATION")
@NamedQueries({
	@NamedQuery(name = "Designation.setAllPrevDepartmentsBasedOnDeptId", query = "UPDATE Designation dn SET dn.dept_Id = (SELECT d.dept_Id FROM Department d WHERE d.dept_Name = 'Invalid') WHERE dn.dept_Id = :deptId"),
	
		@NamedQuery(name = "Designation.getDesignationIdBasedOnDesignationName", query = "SELECT d.dn_Id FROM Designation d WHERE d.dn_Name=:designationName"),
		@NamedQuery(name = "designationDetails", query = "SELECT D FROM Designation D  where  D.dn_Id=:DesignationId"),
		@NamedQuery(name = "Designation.findAll", query = "SELECT d FROM Designation d WHERE d.dn_Id!=1 ORDER BY d.dn_Id desc"),
		@NamedQuery(name = "Designation.Delete", query = "DELETE FROM Designation WHERE dn_Id= :id"),
		@NamedQuery(name = "Designation.UpdateStatus",query="UPDATE Designation d SET  d.dr_Status = :dr_Status WHERE d.dn_Id = :dnId"),
		@NamedQuery(name="desigantionName",query="select d.dn_Name from Designation d"),
		@NamedQuery(name = "Designation.CheckGroup", query = "select d.dn_Name from Designation d where d.dn_Name=:dn_Name"),
		@NamedQuery(name = "Designation.checkNameExistence", query = "SELECT d FROM Designation d WHERE d.dn_Name= :dn_Name"),
		@NamedQuery(name="Desig_department",query="select d.dept_Id from Designation d"),
		@NamedQuery(name="Designation.findAllfields",query="select d.dn_Id,d.dn_Name,d.dr_Status,d.dn_Description,d.created_By,d.last_Updated_By,dp.dept_Id,dp.dept_Name "
				+ "from Designation d join d.department dp order by d.dn_Id desc"),
				
		@NamedQuery(name = "Designation.getDistinctDepartments", query = "SELECT DISTINCT dn.dept_Id, dt.dept_Name, dt.dept_Status from Designation dn, Department dt where dn.dept_Id = dt.dept_Id AND dt.dept_Status = 'Active' AND dn.dr_Status = 'Active'"),
		@NamedQuery(name = "Designation.getAllActiveDesignations",query="SELECT d from Designation d WHERE d.dr_Status = 'Active'"),
		@NamedQuery(name="Designation.getDesignationNameBasedOnId",query="select d.dn_Name from Designation d WHERE d.dn_Id=:id"),
		@NamedQuery(name="Designation.findAllDesigNames",query="select d from Designation d "),
	
})
public class Designation {
	@Id
	@SequenceGenerator(name = "designation_seq", sequenceName = "DESIGNATION_SEQ")
	@GeneratedValue(generator = "designation_seq")
	@Column(name = "DN_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int dn_Id;
	
	@Column(name = "DN_NAME", length = 1020)
	@Pattern(regexp = "^[a-zA-Z']+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$",
    message = "Designation name field can not allow special symbols except(_ .) ")
	private String dn_Name;
	
	
	@Size(min=1,max=200,message="Description  Cannot be empty")
	@Column(name = "DN_DESCRIPTION", length = 1020)
	private String dn_Description;
	
	@Column(name = "DR_STATUS", length = 1020)
	private String dr_Status;
	
	@Min(value = 1, message = "'Department' is not selected")
	@Column(name = "DEPT_ID", precision = 11, scale = 0)
	private int dept_Id;
	
	@Column(name = "CREATED_BY", length = 1020)
	private String created_By;
	
	@Column(name = "LAST_UPDATED_BY", length = 1020)
	private String last_Updated_By;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp  lastUpdatedDt;

	
	
	@OneToOne
	@JoinColumn(name = "DEPT_ID", nullable = false, insertable = false, updatable = false)
	private Department department;

	public Designation() {
	}
	
	
	public Designation(int dn_Id, String dn_Name, String dn_Description,
			String dr_Status, int dept_Id, String created_By,
			String last_Updated_By) {
		super();
		this.dn_Id = dn_Id;
		this.dn_Name = dn_Name;
		this.dn_Description = dn_Description;
		this.dr_Status = dr_Status;
		this.dept_Id = dept_Id;
		this.created_By = created_By;
		this.last_Updated_By = last_Updated_By;
		//this.last_Updated_Dt = last_Updated_Dt;
		//this.department = department;
	}


	public int getDn_Id() {
		return dn_Id;
	}

	public void setDn_Id(int dn_Id) {
		this.dn_Id = dn_Id;
	}

	

	public Timestamp getLastUpdatedDt() {
		return lastUpdatedDt;
	}


	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}


	public String getDn_Name() {
		return dn_Name;
	}

	public void setDn_Name(String dn_Name) {
		this.dn_Name = dn_Name;
	}

	public String getDn_Description() {
		return dn_Description;
	}

	public void setDn_Description(String dn_Description) {
		this.dn_Description = dn_Description;
	}

	public String getDr_Status() {
		return dr_Status;
	}

	public void setDr_Status(String dr_Status) {
		this.dr_Status = dr_Status;
	}

	public int getDept_Id() {
		return dept_Id;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getCreated_By() {
		return created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}

	public String getLast_Updated_By() {
		return last_Updated_By;
	}

	public void setLast_Updated_By(String last_Updated_By) {
		this.last_Updated_By = last_Updated_By;
	}

}
