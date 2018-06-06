package com.bcits.bfm.patternMasterEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.Designation;




@SuppressWarnings("serial")
@Table(name="PETTY_TRASACTION_DETAILS")
@Entity 
@NamedQueries({
	@NamedQuery(name="TransctionDetail.findAll",query="SELECT t FROM TransactionDetail t where t.tId=:tId ORDER BY t.id ASC"),
	@NamedQuery(name="TransctionDetail.getAllDesignationId",query="SELECT t.dnId from TransactionDetail t where t.tId=:tId ")
	
})

public class TransactionDetail  {

	@Id
	@SequenceGenerator(name = "tn_seq", sequenceName = "TRANSCTIONS_CHILD_SEQ") 
	@GeneratedValue(generator = "tn_seq")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private int id;
	
	@Column(name="TRANSA_ID")
	private int tId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TRANSA_ID",insertable=false,updatable=false)
	private TransactionMaster transactioMaster;
	
	@Column(name="DESGNTION_ID")
	private int dnId;
	
	public int getDnId() {
		return dnId;
	}

	public void setDnId(int dnId) {
		this.dnId = dnId;
	}

	@Column(name="DESGNTION_NAME")
	private String dn_name;
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name="DEPT_ID")
	private int dept_Id;
	
	@Column(name="DEPT_NAME")
	private String dept_Name;
	
	public String getDn_name() {
		return dn_name;
	}

	public void setDn_name(String dn_name) {
		this.dn_name = dn_name;
	}

	

	public int getDept_Id() {
		return dept_Id;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public String getDept_Name() {
		return dept_Name;
	}

	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DESGNTION_ID",insertable=false,updatable=false)
	private Designation designation;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DEPT_ID",insertable=false,updatable=false)
	private Department department;
	
	@Column(name="LEVEL_NUM")
	private Integer lNum;
	
	
	
	public int getId() {
		return id;
	}


	
	public void setId(int id) {
		this.id = id;
	}

	public TransactionMaster getTransactioMaster() {
		return transactioMaster;
	}

	public void setTransactioMaster(TransactionMaster transactioMaster) {
		this.transactioMaster = transactioMaster;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	
	

	public Integer getlNum() {
		return lNum;
	}

	public void setlNum(Integer lNum) {
		this.lNum = lNum;
	}
	
	
}
