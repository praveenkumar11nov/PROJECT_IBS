package com.bcits.bfm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "BMS_SETTINGS")
@NamedQueries({

	@NamedQuery(name="BMSSettingsEntity.findAll", query = "SELECT bms.bmsSettingsId,bms.deptId,dpt.dept_Name,bms.dnId,d.dn_Name,bms.bmsElements,bms.paramValue,bms.createdBy,bms.lastUpdatedBy,bms.lastUpdatedDT,bms.status FROM BMSSettingsEntity bms INNER JOIN bms.designation d INNER JOIN d.department dpt ORDER BY bms.bmsSettingsId DESC"),
	@NamedQuery(name="BMSSettingsEntity.setBMSSettingsStatus", query = "UPDATE BMSSettingsEntity bms SET bms.status = :status WHERE bms.bmsSettingsId = :bmsSettingsId"),
	@NamedQuery(name="BMSSettingsEntity.findAllLevels", query = "SELECT bms FROM BMSSettingsEntity bms"),
	@NamedQuery(name="Users.getNameBasedOnDeptDesigID", query = "SELECT m FROM Users m WHERE m.deptId = :deptId AND m.dnId = :dnId"),
	@NamedQuery(name="BMSSettingsEntity.findTrendLogIds",query="Select bms.paramValue,bms.bmsElements from BMSSettingsEntity bms"),
	@NamedQuery(name="BMSSettingsEntity.getHydroPneumaticPumpId",query="Select bms from BMSSettingsEntity bms WHERE bms.bmsElements LIKE 'Hydro Pneumatic Pump'"),
	@NamedQuery(name="BMSSettingsEntity.getVentillationId",query="Select bms from BMSSettingsEntity bms WHERE bms.bmsElements LIKE 'Fan Status'"),
	@NamedQuery(name="BMSSettingsEntity.readTrendLogNamesUniqe",query="Select bms.bmsElements from BMSSettingsEntity bms"),
	@NamedQuery(name="BMSSettingsEntity.readTrendLogIdsUniqe",query="Select bms.paramValue from BMSSettingsEntity bms"),
	@NamedQuery(name="BMSSettingsEntity.getSeweragePlantIds",query="Select bms.paramValue,bms.bmsElements from BMSSettingsEntity bms WHERE bms.bmsElements LIKE 'Treated Water Tank Level' OR bms.bmsElements LIKE 'Soft Water Tank Level' OR bms.bmsElements LIKE 'Soft Water PH Monitoring' OR bms.bmsElements LIKE 'Soft Water Chlorine Monitoring'"),
	@NamedQuery(name="BMSSettingsEntity.getFightFightingIds",query="Select bms.paramValue,bms.bmsElements from BMSSettingsEntity bms WHERE bms.bmsElements LIKE 'Hydrant Pump Status' OR bms.bmsElements LIKE 'Sprinkler Pump Status' OR bms.bmsElements LIKE 'Jockey Pump Status' OR bms.bmsElements LIKE 'Diesel Pump Status'"),
	@NamedQuery(name="BMSSettingsEntity.getDGSetIds",query="Select bms.paramValue,bms.bmsElements from BMSSettingsEntity bms WHERE bms.bmsElements LIKE 'DG Set Status' OR bms.bmsElements LIKE 'DG Set Trip Alarm' OR bms.bmsElements LIKE 'DG Battery Status'"),
	@NamedQuery(name="BMSSettingsEntity.getLiftElevatorIds",query="Select bms.paramValue,bms.bmsElements from BMSSettingsEntity bms WHERE bms.bmsElements LIKE 'Lift Elevator Status' OR bms.bmsElements LIKE 'Lift Elevator Fault Alarm'"),
	@NamedQuery(name="BMSSettingsEntity.readTrendLogIdWithDeptDesig",query="Select bms.paramValue,bms.deptId,bms.dnId,bms.bmsElements from BMSSettingsEntity bms"),
	
	@NamedQuery(name="BMSDashboardEntity.getYearWisedetails", query = "select sum(ebp.elBillParameterValue),EXTRACT(year FROM ebp.electricityBillEntity.billDate) from ElectricityBillParametersEntity ebp inner join ebp.billParameterMasterEntity bpm where bpm.bvmName='Units' group by EXTRACT(year FROM ebp.electricityBillEntity.billDate)"),
	@NamedQuery(name="BMSDashboardEntity.monthWisedetails", query = "select sum(ebp.elBillParameterValue),EXTRACT(month FROM ebp.electricityBillEntity.billDate),EXTRACT(year FROM ebp.electricityBillEntity.billDate) from ElectricityBillParametersEntity ebp inner join ebp.billParameterMasterEntity bpm where bpm.bvmName='Units' group by EXTRACT(month FROM ebp.electricityBillEntity.billDate),EXTRACT(year FROM ebp.electricityBillEntity.billDate)"),
	@NamedQuery(name="BMSDashboardEntity.getUnits", query = "SELECT sum(el.elBillParameterValue) FROM ElectricityBillParametersEntity el WHERE el.electricityBillEntity.elBillId In(SELECT bi.elBillId from ElectricityBillEntity bi where EXTRACT(month FROM bi.billDate)=:month and EXTRACT(year FROM bi.billDate) =:year AND bi.accountId In(SELECT a.accountId FROM Account a,Property p,Blocks b WHERE b.blockId=p.blockId and p.propertyId=a.propertyId and b.blockId=:blockId)) AND el.billParameterMasterEntity.bvmName='Units'"),

})
public class BMSSettingsEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "bms_settings_seq", sequenceName = "BMS_SETTINGS_SEQ")
	@GeneratedValue(generator = "bms_settings_seq")
	@Column(name = "BMS_SETTINGSID")
	private int bmsSettingsId;
		
	@Column(name = "DEPT_ID", precision = 10, scale = 0)
	private int deptId;

	@Column(name = "DN_ID", precision = 10, scale = 0)
	private int dnId;
	
	@OneToOne
	@JoinColumn(name = "DN_ID", insertable = false, updatable = false, nullable = false)
	private Designation designation;

	@OneToOne
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
	private Department department;
	
	@Column(name="ELEMENT")
	private String bmsElements;
	
	@Column(name="BMS_VALUE")
	private int paramValue;
	
	@Column(name="STATUS")
	private String status;
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Column(name="CREATED_BY", length = 1020)
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY", length = 1020)
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDT;

	
	public int getBmsSettingsId() {
		return bmsSettingsId;
	}


	public void setBmsSettingsId(int bmsSettingsId) {
		this.bmsSettingsId = bmsSettingsId;
	}


	public int getDeptId() {
		return deptId;
	}


	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}


	public int getDnId() {
		return dnId;
	}


	public void setDnId(int dnId) {
		this.dnId = dnId;
	}


	public Designation getDesignation() {
		return designation;
	}


	public void setDesignation(Designation designation) {
		this.designation = designation;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}


	public String getBmsElements() {
		return bmsElements;
	}


	public void setBmsElements(String bmsElements) {
		this.bmsElements = bmsElements;
	}

	public int getParamValue() {
		return paramValue;
	}


	public void setParamValue(int paramValue) {
		this.paramValue = paramValue;
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


	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}


	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}


	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	  status="Inactive";
	 }

	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	
}
