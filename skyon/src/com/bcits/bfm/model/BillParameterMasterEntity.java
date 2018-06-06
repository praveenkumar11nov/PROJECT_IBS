package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="BILL_PARAMETERS_MASTER")  
@NamedQueries({
	@NamedQuery(name ="BillParameterMaster.UpdateStatus",query="UPDATE BillParameterMasterEntity el SET el.status = :status WHERE el.bvmId = :bvmId"),
	@NamedQuery(name="BillParameterMaster.getNameAndValue",query="select bpm.bpmName,bp.elBillParameterValue from ElectricityBillParametersEntity bp, BillingParameterMaster bpm where bp.billParameterMasterEntity = bpm.bpmId and bp.electricityBillEntity.elBillId=:elBillId"),
	@NamedQuery(name="BillParameterMasterEntity.findAll",query="SELECT bpm.bvmId,bpm.serviceType,bpm.createdBy,bpm.lastUpdatedBy,bpm.lastUpdatedDT,bpm.bvmDataType,bpm.bvmDescription,bpm.bvmName,bpm.bvmSequence,bpm.status from BillParameterMasterEntity bpm ORDER BY bpm.serviceType,bpm.bvmSequence ASC"),
	@NamedQuery(name="BillParameterMasterEntity.getBillParameterMasterByServiceType",query="SELECT bpm from BillParameterMasterEntity bpm where bpm.serviceType =:serviceType and bpm.status='Active' ORDER BY bpm.bvmSequence ASC"),
	@NamedQuery(name="BillParameterMasterEntity.getServiceMasterPresentingId",query="SELECT bpm.bvmId from BillParameterMasterEntity bpm where bpm.bvmName=:bvmName"),
	@NamedQuery(name="BillParameterMasterEntity.getServicMasterId",query="SELECT bpm.bvmId from BillParameterMasterEntity bpm where bpm.bvmName=:bvmName and bpm.serviceType=:serviceType"),
	@NamedQuery(name="BillParameterMasterEntity.getParameterName",query="SELECT bpm.bvmName from BillParameterMasterEntity bpm where bpm.serviceType=:serviceType"),
	@NamedQuery(name="BillParameterMasterEntity.getBillParameterMasterById",query="SELECT bpm from BillParameterMasterEntity bpm where bpm.bvmId=:bvmId"),
	@NamedQuery(name="BillParameterMasterEntity.findAllBill",query="SELECT bpm from BillParameterMasterEntity bpm ORDER BY bpm.serviceType,bpm.bvmSequence ASC"),
})
public class BillParameterMasterEntity {
	
	@Id
	@Column(name="BVM_ID")
	@SequenceGenerator(name = "Bill_Parameter_Master_seq", sequenceName = "BILL_PARAMETER_MASTER_SEQ") 
	@GeneratedValue(generator = "Bill_Parameter_Master_seq")
	private int bvmId;
	 
	@Column(name="SERVICE_TYPE")
	private String serviceType;
	
	@Column(name="BVM_NAME")
	private String bvmName;
	
	@Column(name="BVM_DATA_TYPE")
	private String bvmDataType;
	
	@Column(name="BVM_DESCRIPTION")
	private String bvmDescription;
	
	@Column(name="BVM_SEQUENCE")
	private int bvmSequence;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@OneToMany(mappedBy="billParameterMasterEntity",cascade = CascadeType.ALL,fetch =  FetchType.EAGER)
	private List<ElectricityBillParametersEntity> billParametersEntities = new ArrayList<>();
	
	public List<ElectricityBillParametersEntity> getBillParametersEntities() {
		return billParametersEntities;
	}

	public void setBillParametersEntities(
			List<ElectricityBillParametersEntity> billParametersEntities) {
		this.billParametersEntities = billParametersEntities;
	}

	public int getBvmId() {
		return bvmId;
	}

	public void setBvmId(int bvmId) {
		this.bvmId = bvmId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getBvmName() {
		return bvmName;
	}

	public void setBvmName(String bvmName) {
		this.bvmName = bvmName;
	}

	public String getBvmDataType() {
		return bvmDataType;
	}

	public void setBvmDataType(String bvmDataType) {
		this.bvmDataType = bvmDataType;
	}

	public String getBvmDescription() {
		return bvmDescription;
	}

	public void setBvmDescription(String bvmDescription) {
		this.bvmDescription = bvmDescription;
	}

	public int getBvmSequence() {
		return bvmSequence;
	}

	public void setBvmSequence(int bvmSequence) {
		this.bvmSequence = bvmSequence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 

}
