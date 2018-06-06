package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.bcits.bfm.util.SessionData;

@NamedQueries({
	@NamedQuery(name = "CommonServicesRateSlab.rateSlabListByParentID", query = "SELECT el FROM CommonServicesRateSlab el WHERE el.csRmId = :csRmId  ORDER BY el.csSlabNo"),
	@NamedQuery(name = "CommonServicesRateSlab.updateRateSlabStatusFromInnerGrid", query = "UPDATE CommonServicesRateSlab el SET el.status = :status WHERE el.csRsId = :csRsId"),
	@NamedQuery(name = "CommonServicesRateSlab.findRateSlabByID", query = "SELECT el FROM CommonServicesRateSlab el WHERE el.csRmId = :csRmId  ORDER BY el.csSlabNo"),
	@NamedQuery(name = "CommonServicesRateSlab.findRateSlabByPrimayID", query = "SELECT el FROM CommonServicesRateSlab el WHERE el.csRsId = :csRsId"),
	@NamedQuery(name = "CommonServicesRateSlab.getcommonServiceRateSlabListByRateMasterId", query = "SELECT cs FROM CommonServicesRateSlab cs WHERE cs.csRmId = :csRmId ORDER BY cs.csSlabNo"),
//	@NamedQuery(name = "CommonServicesRateSlab.getGasRateSlabGreaterThanUpdateSlabNoEq", query = "SELECT el FROM SolidWasteRateSlab el WHERE el.solidWasteRmId = :solidWasteRmId and el.solidWasteSlabNo >= :solidWasteSlabNo"),
//	@NamedQuery(name = "CommonServicesRateSlab.getGasRateSlabGreaterThanUpdateSlabNo", query = "SELECT el FROM SolidWasteRateSlab el WHERE el.solidWasteRmId = :solidWasteRmId and el.solidWasteSlabNo > :solidWasteSlabNo"),
})

@Entity
@Table(name="CS_RATE_SLAB")
public class CommonServicesRateSlab 
{
	@Id
	@SequenceGenerator(name = "csRateslab_seq", sequenceName = "CS_RATESLAB_SEQ")
	@GeneratedValue(generator = "csRateslab_seq")
	@Column(name="CS_RS_ID")
	private int csRsId;
	
    @Column(name="CS_RM_ID")
	private int csRmId;

	@Column(name="CS_SLAB_NO")
	private int csSlabNo;
	
	@Column(name="CS_SLAB_FROM")
	private Float csSlabFrom =0.0f;
	
	@Column(name="CS_SLAB_TO")
	private Float csSlabTo=0.0f;

	@Column(name="CS_RATE")
	private Float csRate=0.0f;

	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());;
	
	@ManyToOne
	@JoinColumn(name="CS_RM_ID",insertable = false, updatable = false, nullable = false)
	private  CommonServicesRateMaster commonServicesRateMaster;
	
	@Column(name="CS_SLAB_TYPE")
	private String csSlabType;
	
	@Column(name="CS_SLAB_RATE_TYPE")
	private String csSlabRateType;
	
	@Transient
	private String csDummySlabFrom;
	
	@Transient
	private String csDummySlabTo;

	public int getCsRsId() {
		return csRsId;
	}

	public void setCsRsId(int csRsId) {
		this.csRsId = csRsId;
	}

	public int getCsRmId() {
		return csRmId;
	}

	public void setCsRmId(int csRmId) {
		this.csRmId = csRmId;
	}

	public int getCsSlabNo() {
		return csSlabNo;
	}

	public void setCsSlabNo(int csSlabNo) {
		this.csSlabNo = csSlabNo;
	}

	public Float getCsSlabFrom() {
		return csSlabFrom;
	}

	public void setCsSlabFrom(Float csSlabFrom) {
		this.csSlabFrom = csSlabFrom;
	}

	public Float getCsSlabTo() {
		return csSlabTo;
	}

	public void setCsSlabTo(Float csSlabTo) {
		this.csSlabTo = csSlabTo;
	}

	public Float getCsRate() {
		return csRate;
	}

	public void setCsRate(Float csRate) {
		this.csRate = csRate;
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

	public CommonServicesRateMaster getCommonServicesRateMaster() {
		return commonServicesRateMaster;
	}

	public void setCommonServicesRateMaster(
			CommonServicesRateMaster commonServicesRateMaster) {
		this.commonServicesRateMaster = commonServicesRateMaster;
	}

	public String getCsSlabType() {
		return csSlabType;
	}

	public void setCsSlabType(String csSlabType) {
		this.csSlabType = csSlabType;
	}

	public String getCsSlabRateType() {
		return csSlabRateType;
	}

	public void setCsSlabRateType(String csSlabRateType) {
		this.csSlabRateType = csSlabRateType;
	}

	public String getCsDummySlabFrom() {
		return csDummySlabFrom;
	}

	public void setCsDummySlabFrom(String csDummySlabFrom) {
		this.csDummySlabFrom = csDummySlabFrom;
	}

	public String getCsDummySlabTo() {
		return csDummySlabTo;
	}

	public void setCsDummySlabTo(String csDummySlabTo) {
		this.csDummySlabTo = csDummySlabTo;
	}

	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate()
	 {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  
	 }
}
