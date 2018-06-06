package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="BILL_PARAMETERS")  
@NamedQueries({
	  @NamedQuery(name = "ElectricityBillParametersEntity.findByBillId", query = "SELECT ebp FROM ElectricityBillParametersEntity ebp INNER JOIN ebp.billParameterMasterEntity bpm WHERE ebp.electricityBillEntity.elBillId = :elBillId ORDER BY ebp.elBillParameterId ASC"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.findAllById", query = "SELECT ebp.elBillParameterId,ebp.elBillParameterDataType,ebp.elBillParameterValue,ebp.notes,bpm.bvmName FROM ElectricityBillParametersEntity ebp INNER JOIN ebp.billParameterMasterEntity bpm WHERE ebp.electricityBillEntity.elBillId = :elBillId ORDER BY ebp.elBillParameterId ASC"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.findAll", query = "SELECT ebp FROM ElectricityBillParametersEntity ebp ORDER BY ebp.elBillParameterId ASC"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.setELRateMasterStatus", query = "UPDATE ElectricityBillParametersEntity ebp SET ebp.status = :status WHERE ebp.elBillParameterId = :elBillParameterId"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.getAverageUnits", query = "SELECT ebp.elBillParameterValue FROM ElectricityBillParametersEntity ebp INNER JOIN ebp.electricityBillEntity bpm where ebp.electricityBillEntity.elBillId =:elBillId and ebp.billParameterMasterEntity.bvmId =:bvmId and EXTRACT(year FROM ebp.lastUpdatedDT) between EXTRACT(year FROM :lastYear) and EXTRACT(year FROM :presentYear) and ebp.electricityBillEntity.billType='Normal' and ebp.electricityBillEntity.postType='Bill' and ebp.electricityBillEntity.status!='Cancelled'"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.getCamParameter", query = "SELECT bp.bvmId FROM BillParameterMasterEntity bp WHERE bp.serviceType='CAM' AND bp.bvmName=:bvmName"),
	  @NamedQuery(name = "BillLineItem.getPreviousreading",query="SELECT MAX(e.elBillParameterValue) FROM ElectricityBillParametersEntity e WHERE e.electricityBillEntity.accountId=:accountId AND e.electricityBillEntity.typeOfService=:typeOfService AND  e.billParameterMasterEntity.bvmName='Present reading'"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.getAverageAmountValue", query = "SELECT bp.elBillParameterValue FROM ElectricityBillParametersEntity bp WHERE bp.electricityBillEntity.elBillId=:elBillId AND bp.billParameterMasterEntity.bvmId=:bpmId"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.getParameterValue", query = "select bp.elBillParameterValue from ElectricityBillParametersEntity bp inner join bp.billParameterMasterEntity where bp.billParameterMasterEntity.serviceType=:typeOfService and bp.billParameterMasterEntity.bvmName=:paraMeterName and bp.electricityBillEntity.elBillId=:elBillId"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.getBillParametersByBillId", query = "SELECT bp.elBillParameterId FROM ElectricityBillParametersEntity bp WHERE bp.electricityBillEntity.elBillId=:elBillId"),
	  @NamedQuery(name = "ElectricityBillParametersEntity.getMasterObjBasedOnName", query = "SELECT bpm FROM BillParameterMasterEntity bpm WHERE bpm.bvmName=:bvmName AND bpm.serviceType=:serviceType"),
})	
public class ElectricityBillParametersEntity {

	@Id
	@Column(name="ELBP_ID")
	@SequenceGenerator(name = "elBill_Parameter_seq", sequenceName = "ELBILL_PARAMETER_SEQ") 
	@GeneratedValue(generator = "elBill_Parameter_seq")
	private int elBillParameterId;
	
	/*@Column(name="ELB_ID")
	private int elBillId;*/
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ELB_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ElectricityBillEntity electricityBillEntity;
	
	@Column(name="ELBP_DATA_TYPE")
	private String elBillParameterDataType;
	
	@Column(name="ELBP_VALUE")
	private String elBillParameterValue;
	
	@Column(name="NOTES")
	private String notes;
	
	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	@Transient
	private int bvmId;
	
	@ManyToOne(fetch=FetchType.LAZY)	 
    @JoinColumn(name = "BVM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private BillParameterMasterEntity billParameterMasterEntity;

	public int getElBillParameterId() {
		return elBillParameterId;
	}

	public void setElBillParameterId(int elBillParameterId) {
		this.elBillParameterId = elBillParameterId;
	}

	/*public int getElBillId() {
		return elBillId;
	}

	public void setElBillId(int elBillId) {
		this.elBillId = elBillId;
	}*/

	/*public int getElbpBvmId() {
		return elbpBvmId;
	}

	public void setElbpBvmId(int elbpBvmId) {
		this.elbpBvmId = elbpBvmId;
	}*/

	public String getElBillParameterDataType() {
		return elBillParameterDataType;
	}

	public void setElBillParameterDataType(String elBillParameterDataType) {
		this.elBillParameterDataType = elBillParameterDataType;
	}

	public String getElBillParameterValue() {
		return elBillParameterValue;
	}

	public void setElBillParameterValue(String elBillParameterValue) {
		this.elBillParameterValue = elBillParameterValue;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public ElectricityBillEntity getElectricityBillEntity() {
		return electricityBillEntity;
	}

	public void setElectricityBillEntity(ElectricityBillEntity electricityBillEntity) {
		this.electricityBillEntity = electricityBillEntity;
	}
	
	public BillParameterMasterEntity getBillParameterMasterEntity() {
		return billParameterMasterEntity;
	}

	public void setBillParameterMasterEntity(
			BillParameterMasterEntity billParameterMasterEntity) {
		this.billParameterMasterEntity = billParameterMasterEntity;
	}

	public ElectricityBillParametersEntity() {
		super();
	}

	public ElectricityBillParametersEntity(String elBillParameterDataType,String elBillParameterValue,String status) {
		super();
		this.elBillParameterDataType = elBillParameterDataType;
		this.elBillParameterValue = elBillParameterValue;
		this.status = status;
	}
	public ElectricityBillParametersEntity(String elBillParameterDataType,String elBillParameterValue,String status,String notes ) {
		super();
		this.elBillParameterDataType = elBillParameterDataType;
		this.elBillParameterValue = elBillParameterValue;
		this.status = status;
		this.notes=notes;
	} 
	public int getBvmId() {
		return bvmId;
	}

	public void setBvmId(int bvmId) {
		this.bvmId = bvmId;
	}
}
