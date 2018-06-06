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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="SERVICE_PARAMETERS")  
@NamedQueries({
	@NamedQuery(name = "ServiceParametersEntity.findAllById", query = "SELECT spe.serviceParameterId,spe.spmId,spe.serviceParameterDataType,spe.serviceParameterValue,spe.serviceParameterSequence,spe.status,spm.spmName,sm.serviceMasterId FROM ServiceParametersEntity spe INNER JOIN spe.serviceParameterMaster spm INNER JOIN spe.serviceMastersEntity sm WHERE spe.serviceMastersEntity.serviceMasterId = :serviceMasterId ORDER BY spe.serviceParameterSequence ASC"),
	@NamedQuery(name = "ServiceParametersEntity.findAll", query = "SELECT el FROM ServiceParametersEntity el ORDER BY el.serviceParameterSequence ASC"),
	@NamedQuery(name="ServiceParametersEntity.updateParameterStatusFromInnerGrid",query="UPDATE ServiceParametersEntity a SET a.status = :status WHERE a.serviceParameterId = :serviceParameterId"),
	@NamedQuery(name = "ServiceParametersEntity.getServiceDataType", query = "SELECT el FROM ServiceParameterMaster el where el.spmId = :spmId"),
	@NamedQuery(name = "ServiceParametersEntity.findAllByParentId", query = "SELECT spe FROM ServiceParametersEntity spe WHERE spe.serviceMastersEntity.serviceMasterId = :serviceMasterId and spe.status='Active' ORDER BY spe.serviceParameterSequence ASC"),
	@NamedQuery(name = "ServiceParametersEntity.getSequenceForAverageUnits", query = "SELECT MAX(sp.serviceParameterSequence) FROM ServiceParametersEntity sp where sp.serviceMastersEntity.serviceMasterId = :serviceMasterId"),
	@NamedQuery(name="ServiceParametersEntity.getServiceParameterMasterId",query="SELECT spmId from ServiceParameterMaster spm WHERE spm.spmName=:spmName"),
	@NamedQuery(name = "ServiceParametersEntity.findAllByParentIdForDG", query = "SELECT spe FROM ServiceParametersEntity spe inner join spe.serviceParameterMaster spm WHERE spe.serviceMastersEntity.serviceMasterId = :serviceMasterId and spe.status='Active' and spe.serviceParameterMaster.spmName='DG Applicable' ORDER BY spe.serviceParameterSequence ASC"),
})
public class ServiceParametersEntity {

	@Id
	@Column(name="SPARA_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "serviceParameters_seq", sequenceName = "SERVICE_PARAMETERS_SEQ") 
	@GeneratedValue(generator = "serviceParameters_seq")
	private int serviceParameterId;
	
	@Transient
	private int serviceMasterId;
	
	/*@Column(name="SM_ID")
	@NotNull(message = "Service Master Id Should Not Be Empty")
	private int serviceMasterId;*/
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "SM_ID")
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	private ServiceMastersEntity serviceMastersEntity;
	
	@Column(name="SP_SPM_ID", unique=true, nullable=false, precision=11, scale=0)
	private int spmId;
	
	@OneToOne	 
	@JoinColumn(name = "SP_SPM_ID", insertable = false, updatable = false, nullable = false)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private ServiceParameterMaster serviceParameterMaster;
	
	@Column(name="SP_SPM_DATA_TYPE")
	private String serviceParameterDataType;
	
	@Column(name="SP_VALUE")
	private String serviceParameterValue;
	
	@Column(name="SP_SEQUENCE")
	private int serviceParameterSequence;
	
	@Column(name="STATUS")
	@NotEmpty(message = "Parameter Status Sholud Not Be Empty")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	
	public int getServiceParameterId() {
		return serviceParameterId;
	}


	public void setServiceParameterId(int serviceParameterId) {
		this.serviceParameterId = serviceParameterId;
	}


	/*public int getServiceMasterId() {
		return serviceMasterId;
	}


	public void setServiceMasterId(int serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}*/


	public ServiceMastersEntity getServiceMastersEntity() {
		return serviceMastersEntity;
	}


	public void setServiceMastersEntity(ServiceMastersEntity serviceMastersEntity) {
		this.serviceMastersEntity = serviceMastersEntity;
	}


	public int getSpmId() {
		return spmId;
	}


	public void setSpmId(int spmId) {
		this.spmId = spmId;
	}


	public ServiceParameterMaster getServiceParameterMaster() {
		return serviceParameterMaster;
	}


	public void setServiceParameterMaster(
			ServiceParameterMaster serviceParameterMaster) {
		this.serviceParameterMaster = serviceParameterMaster;
	}


	public String getServiceParameterDataType() {
		return serviceParameterDataType;
	}


	public void setServiceParameterDataType(String serviceParameterDataType) {
		this.serviceParameterDataType = serviceParameterDataType;
	}


	public String getServiceParameterValue() {
		return serviceParameterValue;
	}


	public void setServiceParameterValue(String serviceParameterValue) {
		this.serviceParameterValue = serviceParameterValue;
	}


	public int getServiceParameterSequence() {
		return serviceParameterSequence;
	}


	public void setServiceParameterSequence(int serviceParameterSequence) {
		this.serviceParameterSequence = serviceParameterSequence;
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


	public int getServiceMasterId() {
		return serviceMasterId;
	}


	public void setServiceMasterId(int serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}
	
}
