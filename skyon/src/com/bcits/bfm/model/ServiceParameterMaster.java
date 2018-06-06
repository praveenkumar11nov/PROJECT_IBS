package com.bcits.bfm.model;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.BatchSize;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="SERVICE_PARAMETER_MASTER")
@BatchSize(size = 10)
@NamedQueries({
	@NamedQuery(name = "ServiceParameterMaster.UpdateStatus",query="UPDATE ServiceParameterMaster el SET el.status = :status WHERE el.spmId = :spmId"),
	@NamedQuery(name="ServiceParameterMaster.findAll",query="SELECT spm from ServiceParameterMaster spm ORDER BY spm.serviceType,spm.spmSequence ASC"),
	//@NamedQuery(name="ServiceParameterMaster.getSpmName",query="SELECT spmName from ServiceParameterMaster spm WHERE spm.serviceType=:serviceType"),
	@NamedQuery(name="ServiceParametersEntity.getNameAndValue",query="select spm.spmName,sp.serviceParameterValue  from ServiceParameterMaster spm,ServiceParametersEntity sp where sp.spmId=spm.spmId and sp.serviceMastersEntity.serviceMasterId=:serviceMasterId"),
	@NamedQuery(name="ServiceParameterMaster.getSpmId",query="SELECT spmId from ServiceParameterMaster spm WHERE spm.spmName=:spmName"),
	@NamedQuery(name="ServiceParameterMaster.getSpmName",query="SELECT spm.spmName from ServiceParameterMaster spm WHERE spm.serviceType=:serviceType AND spm.spmName NOT IN ('DG Applicable','Solar Rebate','Handicapped Rebate','Sewage charges','Solar charges','RO charges','OT Fixed Charge','OT OTHERS','Maintenance Charges') ORDER BY spm.spmSequence")
})
public class ServiceParameterMaster implements java.io.Serializable{

	/**
	 * 
	 */

	@Id
	@SequenceGenerator(name="seqServiceParameterMaster", sequenceName="SERVICE_PARAMETER_MASTER_SEQ")
	@GeneratedValue(generator="seqServiceParameterMaster")
	@Column(name="SPM_ID", unique=true)
	private int spmId;
	
	@Column(name="STATUS", length = 20)
	private String status;
	
	@Column(name="SPM_SEQUENCE")
	private int spmSequence;
	
	
	@Column(name="LAST_UPDATED_DT",nullable = false)
	private Timestamp lastupdatedDt;
	
	@Column(name="SERVICE_TYPE")
	private String serviceType;
	
	@Column(name="SPM_NAME")
	private String spmName;
	
	@Column(name="SPM_DATA_TYPE")
	private String spmdataType;
	
	@Column(name="SPM_DESCRIPTION")
	private String spmDescription;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastupdatedBy;
	
	private static final long serialVersionUID = 1L;
	public ServiceParameterMaster(){
		
	}
	
	public int getSpmId() {
		return spmId;
	}
	public void setSpmId(int spmId) {
		this.spmId = spmId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSpmName() {
		return spmName;
	}
	public void setSpmName(String spmName) {
		this.spmName = spmName;
	}
	public String getSpmdataType() {
		return spmdataType;
	}
	public void setSpmdataType(String spmdataType) {
		this.spmdataType = spmdataType;
	}
	public String getSpmDescription() {
		return spmDescription;
	}
	public void setSpmDescription(String spmDescription) {
		this.spmDescription = spmDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSpmSequence() {
		return spmSequence;
	}
	public void setSpmSequence(int spmSequence) {
		this.spmSequence = spmSequence;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastupdatedBy() {
		return lastupdatedBy;
	}
	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}
	public Timestamp getLastupdatedDt() {
		return lastupdatedDt;
	}
	public void setLastupdatedDt(Timestamp lastupdatedDt) {
	
			this.lastupdatedDt =lastupdatedDt;
				
	}
	
	@PrePersist
	protected void onCreate() 
	{
		lastupdatedDt = new Timestamp(new Date().getTime());
		lastupdatedBy=(String) SessionData.getUserDetails().get("userID");
		createdBy=(String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastupdatedDt = new Timestamp(new Date().getTime());
		lastupdatedBy=(String) SessionData.getUserDetails().get("userID");
	}

	
}
