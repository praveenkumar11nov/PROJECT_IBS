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
import javax.persistence.Transient;


import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	@NamedQuery(name = "StoreItemLedgerDetails.getAllStoreItemLedgerDetailsBasedOnSILId", query = "SELECT s FROM StoreItemLedgerDetails s WHERE s.silId = :silId ORDER BY s.sildId DESC")
})
@Table(name = "STORE_ITEM_LEDGER_DETAILS")
public class StoreItemLedgerDetails 
{
	private int sildId;
	private int silId;
	private String transactionType;
	private Double quantity;
	private Double rate;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private int uomId;
	private String quantityPerBaseUom;
	private String status;
	private Date createdDate;

	@Id
	@SequenceGenerator(name = "STORE_ITEM_LEDGER_DETAILS_SEQ", sequenceName = "STORE_ITEM_LEDGER_DETAILS_SEQ")
	@GeneratedValue(generator = "STORE_ITEM_LEDGER_DETAILS_SEQ")
	@Column(name = "SILD_ID")
	public int getSildId()
	{
		return sildId;
	}
	
	public void setSildId(int sildId)
	{
		this.sildId = sildId;
	}
	
	@Column(name = "SIL_ID", insertable = false, updatable = false)
	public int getSilId()
	{
		return silId;
	}
	
	public void setSilId(int silId)
	{
		this.silId = silId;
	}	
	
	
	@Column(name = "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "QUANTITY")
	public Double getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(Double quantity)
	{
		this.quantity = quantity;
	}
	
	@Column(name = "RATE")
	public Double getRate()
	{
		return rate;
	}
	
	public void setRate(Double rate)
	{
		this.rate = rate;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}
	
	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	@PrePersist
	protected void onCreate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
	    lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	    createdBy = (String) SessionData.getUserDetails().get("userID");
	}

	@PreUpdate
	protected void onUpdate() 
	{
		lastUpdatedDt = new Timestamp(new Date().getTime());
		lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}
	
	@Column(name = "UOM_ID")
	public int getUomId()
	{
		return uomId;
	}
	
	public void setUomId(int uomId)
	{
		this.uomId = uomId;
	}
	
	@Transient
	public String getQuantityPerBaseUom()
	{
		return quantityPerBaseUom;
	}
	
	public void setQuantityPerBaseUom(String quantityPerBaseUom)
	{
		this.quantityPerBaseUom = quantityPerBaseUom;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
