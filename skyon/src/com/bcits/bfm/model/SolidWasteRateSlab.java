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
	@NamedQuery(name = "SolidWasteRateSlab.rateSlabListByParentID", query = "SELECT el FROM SolidWasteRateSlab el WHERE el.solidWasteRmId = :solidWasteRmId  ORDER BY el.solidWasteSlabNo"),
	@NamedQuery(name = "SolidWasteRateSlab.updateRateSlabStatusFromInnerGrid", query = "UPDATE SolidWasteRateSlab el SET el.status = :status WHERE el.solidWasteRsId = :solidWasteRsId"),
	@NamedQuery(name = "SolidWasteRateSlab.findRateSlabByPrimayID", query = "SELECT el FROM SolidWasteRateSlab el WHERE el.solidWasteRsId = :solidWasteRsId"),
	@NamedQuery(name = "SolidWasteRateSlab.getGasRateSlabGreaterThanUpdateSlabNoEq", query = "SELECT el FROM SolidWasteRateSlab el WHERE el.solidWasteRmId = :solidWasteRmId and el.solidWasteSlabNo >= :solidWasteSlabNo"),
	@NamedQuery(name = "SolidWasteRateSlab.getGasRateSlabGreaterThanUpdateSlabNo", query = "SELECT el FROM SolidWasteRateSlab el WHERE el.solidWasteRmId = :solidWasteRmId and el.solidWasteSlabNo > :solidWasteSlabNo"),
	@NamedQuery(name = "SolidWasteRateSlab.getSolidWasteRateSlabListByRateMasterId", query = "SELECT sw FROM SolidWasteRateSlab sw WHERE sw.solidWasteRmId = :solidWasteRmId ORDER BY sw.solidWasteSlabNo"),
	@NamedQuery(name = "SolidWasteRateSlab.getSolidWasterRateSlabBetween", query = "SELECT sw FROM SolidWasteRateSlab sw WHERE sw.solidWasteRmId = :solidWasteRmId and :uomValue BETWEEN sw.solidWasteSlabFrom AND sw.solidWasteSlabTo"),
})
@Entity
@Table(name="SOLID_WASTE_RATE_SLAB")
public class SolidWasteRateSlab
{
	@Id
	@SequenceGenerator(name = "solidWasteRateslab_seq", sequenceName = "SOLID_WASTE_RATESLAB_SEQ")
	@GeneratedValue(generator = "solidWasteRateslab_seq")
	@Column(name="SOLID_WASTE_RS_ID")
	private int solidWasteRsId;
	
    @Column(name="SOLID_WASTE_RM_ID")
	private int solidWasteRmId;

	@Column(name="SOLID_WASTE_SLAB_NO")
	private int solidWasteSlabNo;
	
	@Column(name="SOLID_WASTE_SLAB_FROM")
	private Float solidWasteSlabFrom =0.0f;
	
	@Column(name="SOLID_WASTE_SLAB_TO")
	private Float solidWasteSlabTo=0.0f;

	@Column(name="SOLID_WASTE_RATE")
	private Float solidWasteRate=0.0f;

	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());;
	
	@ManyToOne
	@JoinColumn(name="SOLID_WASTE_RM_ID",insertable = false, updatable = false, nullable = false)
	private SolidWasteRateMaster solidWasteRateMaster;
	
	@Column(name="SOLID_WASTE_SLAB_TYPE")
	private String solidWasteSlabType;
	
	@Column(name="SOLID_WASTE_SLAB_RATE_TYPE")
	private String solidWasteSlabRateType;
	
	@Transient
	private String solidWasteDummySlabFrom;
	
	@Transient
	private String solidWasteDummySlabTo;

	public int getSolidWasteRsId() {
		return solidWasteRsId;
	}

	public void setSolidWasteRsId(int solidWasteRsId) {
		this.solidWasteRsId = solidWasteRsId;
	}

	public int getSolidWasteRmId() {
		return solidWasteRmId;
	}

	public void setSolidWasteRmId(int solidWasteRmId) {
		this.solidWasteRmId = solidWasteRmId;
	}

	public int getSolidWasteSlabNo() {
		return solidWasteSlabNo;
	}

	public void setSolidWasteSlabNo(int solidWasteSlabNo) {
		this.solidWasteSlabNo = solidWasteSlabNo;
	}

	public Float getSolidWasteSlabFrom() {
		return solidWasteSlabFrom;
	}

	public void setSolidWasteSlabFrom(Float solidWasteSlabFrom) {
		this.solidWasteSlabFrom = solidWasteSlabFrom;
	}

	public Float getSolidWasteSlabTo() {
		return solidWasteSlabTo;
	}

	public void setSolidWasteSlabTo(Float solidWasteSlabTo) {
		this.solidWasteSlabTo = solidWasteSlabTo;
	}

	public Float getSolidWasteRate() {
		return solidWasteRate;
	}

	public void setSolidWasteRate(Float solidWasteRate) {
		this.solidWasteRate = solidWasteRate;
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

	public SolidWasteRateMaster getSolidWasteRateMaster() {
		return solidWasteRateMaster;
	}

	public void setSolidWasteRateMaster(SolidWasteRateMaster solidWasteRateMaster) {
		this.solidWasteRateMaster = solidWasteRateMaster;
	}

	public String getSolidWasteSlabType() {
		return solidWasteSlabType;
	}

	public void setSolidWasteSlabType(String solidWasteSlabType) {
		this.solidWasteSlabType = solidWasteSlabType;
	}

	public String getSolidWasteSlabRateType() {
		return solidWasteSlabRateType;
	}

	public void setSolidWasteSlabRateType(String solidWasteSlabRateType) {
		this.solidWasteSlabRateType = solidWasteSlabRateType;
	}

	public String getSolidWasteDummySlabFrom() {
		return solidWasteDummySlabFrom;
	}

	public void setSolidWasteDummySlabFrom(String solidWasteDummySlabFrom) {
		this.solidWasteDummySlabFrom = solidWasteDummySlabFrom;
	}

	public String getSolidWasteDummySlabTo() {
		return solidWasteDummySlabTo;
	}

	public void setSolidWasteDummySlabTo(String solidWasteDummySlabTo) {
		this.solidWasteDummySlabTo = solidWasteDummySlabTo;
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
