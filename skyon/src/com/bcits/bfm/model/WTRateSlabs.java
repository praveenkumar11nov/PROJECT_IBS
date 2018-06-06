package com.bcits.bfm.model;

import java.sql.Timestamp;

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
import javax.persistence.Transient;

import com.bcits.bfm.util.SessionData;


@NamedQueries({
	@NamedQuery(name = "WTRateSlabs.rateSlabListByParentID", query = "SELECT el FROM WTRateSlabs el WHERE el.wtrmid = :wtrmid  ORDER BY el.wtSlabNo"),
	@NamedQuery(name = "WTRateSlabs.updateRateSlabStatusFromInnerGrid", query = "UPDATE WTRateSlabs el SET el.wtRateSlabStatus = :wtRateSlabStatus WHERE el.wtrsId = :wtrsId"),
	@NamedQuery(name = "WTRateSlabs.findRateSlabByPrimayID", query = "SELECT el FROM WTRateSlabs el WHERE el.wtrsId = :wtrsId"),
	@NamedQuery(name = "WTRateSlabs.getWTRateSlabGreaterThanUpdateSlabNoEq", query = "SELECT el FROM WTRateSlabs el WHERE el.wtrsId = :wtrsId and el.wtSlabNo >= :wtSlabNo"),
	@NamedQuery(name = "WTRateSlabs.getWTRateSlabGreaterThanUpdateSlabNo", query = "SELECT el FROM WTRateSlabs el WHERE el.wtrmid = :wtrmid and el.wtSlabNo > :wtSlabNo"),
	@NamedQuery(name = "WTRateSlabs.getWTRateSlabListByRateMasterId", query = "SELECT wt FROM WTRateSlabs wt WHERE wt.wtrmid = :wtrmid ORDER BY wt.wtSlabNo"),
})
@Entity
@Table(name = "WT_RATE_SLAB")
public class WTRateSlabs 
{
	@Id
	@SequenceGenerator(name = "wtrateslabs_seq", sequenceName = "WT_RATESLABS_SEQ")
	@GeneratedValue(generator = "wtrateslabs_seq")
	@Column(name="WT_RS_ID")
	private int wtrsId;
	
	@Column(name="WT_RM_ID")
	private int wtrmid;
	
	@Column(name="WT_SLAB_NO")
	private int wtSlabNo;
	
	@Column(name="WT_SLAB_FROM")
	private Float wtSlabFrom =0.0f;
	
	@Column(name="WT_SLAB_TO")
	private Float wtSlabTo=0.0f;
	
	@Column(name="WT_RATE")
	private float wtRate=0.0f;
	
	@Column(name="WT_RATESLAB_STATUS")
	private  String wtRateSlabStatus;
	
	@Column(name="WT_RATESLAB_CREATED_BY")
	private String wtRateSlabCreatedBy;
	
	@Column(name="WT_RATESLAB_LAST_UPDATED_BY")
	private String wtRateSlabLastUpdatedBy;
	
	@Column(name="WT_RATESLAB_LAST_UPDATED_DT")
	private Timestamp wtRateSlabLastUpdatedDT;
	
	@Column(name="WT_SLAB_TYPE")
	private String wtSlabType;
	
	@Column(name="WT_SLAB_RATE_TYPE")
	private String wtSlabRateType;
	
	@Transient
	private String wtDummySlabFrom;
	
	@Transient
	private String wtDummySlabTo;
	
	public int getWtrsId() {
		return wtrsId;
	}

	public void setWtrsId(int wtrsId) {
		this.wtrsId = wtrsId;
	}

	public int getWtrmid() {
		return wtrmid;
	}

	public void setWtrmid(int wtrmid) {
		this.wtrmid = wtrmid;
	}

	public int getWtSlabNo() {
		return wtSlabNo;
	}

	public void setWtSlabNo(int wtSlabNo) {
		this.wtSlabNo = wtSlabNo;
	}

	public Timestamp getWtRateSlabLastUpdatedDT() {
		return wtRateSlabLastUpdatedDT;
	}

	public void setWtRateSlabLastUpdatedDT(Timestamp wtRateSlabLastUpdatedDT) {
		this.wtRateSlabLastUpdatedDT = wtRateSlabLastUpdatedDT;
	}

	public Float getWtSlabFrom() {
		return wtSlabFrom;
	}

	public void setWtSlabFrom(Float wtSlabFrom) {
		this.wtSlabFrom = wtSlabFrom;
	}

	public Float getWtSlabTo() {
		return wtSlabTo;
	}

	public void setWtSlabTo(Float wtSlabTo) {
		this.wtSlabTo = wtSlabTo;
	}

	public float getWtRate() {
		return wtRate;
	}

	public void setWtRate(float wtRate) {
		this.wtRate = wtRate;
	}

	public String getWtRateSlabStatus() {
		return wtRateSlabStatus;
	}

	public void setWtRateSlabStatus(String wtRateSlabStatus) {
		this.wtRateSlabStatus = wtRateSlabStatus;
	}

	public String getWtRateSlabCreatedBy() {
		return wtRateSlabCreatedBy;
	}

	public void setWtRateSlabCreatedBy(String wtRateSlabCreatedBy) {
		this.wtRateSlabCreatedBy = wtRateSlabCreatedBy;
	}

	public String getWtRateSlabLastUpdatedBy() {
		return wtRateSlabLastUpdatedBy;
	}

	public void setWtRateSlabLastUpdatedBy(String wtRateSlabLastUpdatedBy) {
		this.wtRateSlabLastUpdatedBy = wtRateSlabLastUpdatedBy;
	}

	public String getWtSlabType() {
		return wtSlabType;
	}

	public void setWtSlabType(String wtSlabType) {
		this.wtSlabType = wtSlabType;
	}

	public String getWtSlabRateType() {
		return wtSlabRateType;
	}

	public void setWtSlabRateType(String wtSlabRateType) {
		this.wtSlabRateType = wtSlabRateType;
	}

	public String getWtDummySlabFrom() {
		return wtDummySlabFrom;
	}

	public void setWtDummySlabFrom(String wtDummySlabFrom) {
		this.wtDummySlabFrom = wtDummySlabFrom;
	}

	public String getWtDummySlabTo() {
		return wtDummySlabTo;
	}

	public void setWtDummySlabTo(String wtDummySlabTo) {
		this.wtDummySlabTo = wtDummySlabTo;
	}
	
	@PrePersist
	 protected void onCreate() {
	  wtRateSlabLastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  wtRateSlabCreatedBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate()
	 {
	  wtRateSlabLastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }

}
