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

@Entity
@Table(name="EL_RATE_SLABS")
@NamedQueries({
	@NamedQuery(name = "ELRateSlabs.findAll", query = "SELECT el FROM ELRateSlabs el ORDER BY el.elrsId DESC"),
	@NamedQuery(name = "ELRateSlabs.findRateSlabByID", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId  ORDER BY el.slabsNo"),
	@NamedQuery(name = "ELRateSlabs.getMaxSlabTo", query = "SELECT max(el.slabTo)FROM ELRateSlabs el WHERE el.elrmId = :elrmId"),
	@NamedQuery(name = "ELRateSlabs.findAllGreaterThanId", query = "SELECT el FROM ELRateSlabs el WHERE el.slabsNo >= :slabsNo and el.elrmId=:elrmId"),
	@NamedQuery(name = "ELRateSlabs.updateRateSlabStatusFromInnerGrid", query = "UPDATE ELRateSlabs el SET el.status = :status WHERE el.elrsId = :elrsId"),
	@NamedQuery(name = "ELRateSlabs.getMaxSlabNumber", query = "SELECT MAX(el.slabsNo) FROM ELRateSlabs el WHERE el.elrmId = :elrmId"),
	@NamedQuery(name = "ELRateSlabs.findRateSlabByPrimayID", query = "SELECT el FROM ELRateSlabs el WHERE el.elrsId = :elrsId"),
	@NamedQuery(name = "ELRateSlabs.getElRateSlabGreaterThanUpdateSlabNo", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId and el.slabsNo > :slabsNo order by el.slabsNo asc"),
	@NamedQuery(name = "ELRateSlabs.getElRateSlabGreaterThanUpdateSlabNoEq", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId and el.slabsNo >= :slabsNo order by el.slabsNo asc"),
	@NamedQuery(name = "ELRateSlabs.getELRateSlabListByRateMasterId", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId ORDER BY el.slabsNo"),
	@NamedQuery(name = "ELRateSlabs.getELRateSlabListBetween", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId and :pfFactor BETWEEN el.slabFrom AND el.slabTo"),
	@NamedQuery(name = "ELRateSlabs.getELRateSlabBetween", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId and :demandCharges BETWEEN el.slabFrom AND el.slabTo"),
	@NamedQuery(name = "ELRateSlabs.getELRateSlabListByRateMasterDr", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId and el.slabFrom=:slabFrom"),
	@NamedQuery(name = "ELRateSlabs.findIdAndParentId", query = "SELECT el FROM ELRateSlabs el WHERE el.slabsNo = :slabsNo and el.elrmId=:elrmid"),
	@NamedQuery(name = "ELRateSlabs.findAllLessThanId", query = "SELECT el FROM ELRateSlabs el WHERE el.slabsNo <= :slabsNo and el.elrmId=:elrmId"),
	@NamedQuery(name = "ELRateSlabs.getRateSlabByRateMasterId", query = "SELECT el FROM ELRateSlabs el WHERE el.elrmId = :elrmId and el.status='Active' order by el.elrsId ASC"),
	@NamedQuery(name = "ELRateSlabs.getWtRateSlabByRateMasterId", query = "SELECT wt FROM WTRateSlabs wt WHERE wt.wtrmid = :wtrmId and wt.wtRateSlabStatus='Active' order by wt.wtrsId ASC"),
	@NamedQuery(name = "ELRateSlabs.getGasRateSlabByRateMasterId", query = "SELECT gas FROM GasRateSlab gas WHERE gas.gasrmId = :gasrmId and gas.status='Active' order by gas.gasrsId ASC"),
	@NamedQuery(name = "ELRateSlabs.getSWRateSlabByRateMasterId", query = "SELECT sw FROM SolidWasteRateSlab sw WHERE sw.solidWasteRmId = :swrmId and sw.status='Active' order by sw.solidWasteRsId ASC"),
})
//return entityManager.createNamedQuery("",SolidWasteRateSlab.class).setParameter("swrmId", rateMasterID).getResultList();
public class ELRateSlabs 
{
	@Id
	@SequenceGenerator(name = "elrateslabs_seq", sequenceName = "ELRATESLABS_SEQ")
	@GeneratedValue(generator = "elrateslabs_seq")
	@Column(name="ELRS_ID")
	private int elrsId;
	
    @Column(name="ELRM_ID")
	private int elrmId;

	@Column(name="SLAB_SNO")
	private int slabsNo;
	
	@Column(name="SLAB_FROM")
	private Float slabFrom =0.0f;
	
	@Column(name="SLAB_TO")
	private Float slabTo=0.0f;

	@Column(name="RATE")
	private float rate=0.0f;;

	@Column(name="STATUS")
	private  String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());;
	
	@ManyToOne
	@JoinColumn(name="ELRM_ID",insertable = false, updatable = false, nullable = false)
	private ELRateMaster elRateMaster;
	
	@Column(name="SLAB_TYPE")
	private String slabType;
	
	@Column(name="SLAB_RATE_TYPE")
	private String slabRateType;
	
	@Transient
	private String dummySlabFrom;
	
	@Transient
	private String dummySlabTo;
	
	public ELRateMaster getElRateMaster() {
		return elRateMaster;
	}

	public void setElRateMaster(ELRateMaster elRateMaster) {
		this.elRateMaster = elRateMaster;
	}

	public int getElrsId() {
		return elrsId;
	}

	public void setElrsId(int elrsId) {
		this.elrsId = elrsId;
	}

	public int getElrmId() {
		return elrmId;
	}

	public void setElrmId(int elrmId) {
		this.elrmId = elrmId;
	}

	public int getSlabsNo() {
		return slabsNo;
	}

	public void setSlabsNo(int slabsNo) {
		this.slabsNo = slabsNo;
	}

	public Float getSlabFrom() {
		return slabFrom;
	}

	public void setSlabFrom(Float slabFrom) {
		this.slabFrom = slabFrom;
	}

	public Float getSlabTo() {
		return slabTo;
	}

	public void setSlabTo(Float slabTo) {
		this.slabTo = slabTo;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
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
	 protected void onCreate()
	{
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	 }
	 
	 @PreUpdate
	 protected void onUpdate()
	 {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }

	public String getSlabType() {
		return slabType;
	}

	public void setSlabType(String slabType) {
		this.slabType = slabType;
	}

	public String getSlabRateType() {
		return slabRateType;
	}

	public void setSlabRateType(String slabRateType) {
		this.slabRateType = slabRateType;
	}

	public String getDummySlabFrom() {
		return dummySlabFrom;
	}

	public void setDummySlabFrom(String dummySlabFrom) {
		this.dummySlabFrom = dummySlabFrom;
	}

	public String getDummySlabTo() {
		return dummySlabTo;
	}

	public void setDummySlabTo(String dummySlabTo) {
		this.dummySlabTo = dummySlabTo;
	}

}
