package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedNativeQueries;

import com.bcits.bfm.util.SessionData;

/**
 * @author user51
 *
 */
@Entity
@Table(name="AMR_PROPERTY")
@NamedQueries({
	@NamedQuery(name = "AMRProperty.getAllBlocks",query="SELECT b.blockId,b.blockName FROM Blocks b"),
	@NamedQuery(name = "AMRProperty.getAllProperty", query = "select p.propertyId,p.property_No,p.blockId from Property p "),
    @NamedQuery(name = "AMRProperty.findALL",query="SELECT a.amrId,b.blockId,b.blockName,p.propertyId,p.property_No,a.columnName,a.status FROM AMRProperty a INNER JOIN a.blocks b INNER JOIN a.property p ORDER BY a.amrId DESC"),
	@NamedQuery(name = "AMRProperty.setAMRStatus", query = "UPDATE AMRProperty a SET a.status = :status WHERE a.amrId = :amrId"),
	@NamedQuery(name = "AMRProperty.activateAll", query = "UPDATE AMRProperty a SET a.status = :status"),
	@NamedQuery(name = "AMRProperty.getColumnName",query="SELECT a.columnName FROM AMRProperty a where a.blockId=:blockId and a.propertyId=:propertyId and a.status='Active'"),
	@NamedQuery(name = "AMRProperty.getMeterNumber", query = "SELECT em.meterSerialNo FROM ElectricityMetersEntity em WHERE em.account.propertyId=:propertyId and em.typeOfServiceForMeters='Electricity'"),
	@NamedQuery(name = "AMRProperty.getPersonName",query="SELECT ow.owner.person.firstName,ow.owner.person.lastName FROM OwnerProperty ow INNER JOIN ow.property where ow.property.propertyId=:propertyId"),
	@NamedQuery(name = "AMRProperty.getAccountNumber", query = "SELECT a.accountNo FROM Account a WHERE a.propertyId=:propertyId"),
	@NamedQuery(name = "AMRProperty.getMeterNumberByAccountNumber", query = "SELECT em.meterSerialNo FROM ElectricityMetersEntity em WHERE em.account.accountNo=:accountNumber"),
	@NamedQuery(name = "AMRProperty.getAMRAccountDetails", query = "select eme.meterSerialNo,a.accountNo,p.firstName,p.lastName,(select pp.property_No from Property pp where pp.propertyId = a.propertyId),(select pp.blocks.blockName from Property pp where pp.propertyId = a.propertyId),(select amr.columnName from AMRProperty amr where amr.propertyId = a.propertyId) from ElectricityMetersEntity eme inner join eme.account a inner join a.person p"),
	@NamedQuery(name = "AMRProperty.getBlockProperty", query = "select amr.property.property_No,amr.blocks.blockName,amr.columnName from AMRProperty amr where amr.propertyId = (SELECT a.propertyId FROM Account a WHERE a.accountNo=:accountNo)"),
	
}) 

public class AMRProperty{
	
	/**
	 */
	@Id
	@SequenceGenerator(name = "amrproperty_seq", sequenceName = "AMR_PROPERTY_SEQ")
	@GeneratedValue(generator = "amrproperty_seq")
	@Column(name="AMR_ID")
	private int amrId;
	/**
	 */
	@OneToOne
	@JoinColumn(name = "BLOCK_ID",insertable = false, updatable = false, nullable = false)
	private Blocks blocks;
	/**
	 */
	@Column(name="BLOCK_ID")
	private int blockId;
	/**
	 */
	@OneToOne
	@JoinColumn(name = "PROPERTY_ID",insertable = false, updatable = false, nullable = false)
	private Property property;
	/**
	 */
	@Column(name="PROPERTY_ID")
	private int propertyId;
	/**
	 */
	@Column(name="COLUMN_NAME")
	private String columnName;
	/**
	 */
	@Column(name="STATUS")
	private String status;
	/**
	 */
	@Column(name="CREATED_BY")
	private String createdBy;
	/**
	 */
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	/**
	 */
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT;
	/**
	 */
	@Transient
	private String blocksName;
	/**
	 */
	@Transient
	private String propertyName;
	/**
	 */
	@Transient
	private String meterNumber;
	/**
	 */
	
	public String getBlocksName() {
		return blocksName;
	}
	/**
	 */
	public void setBlocksName(final String blocksName) {
		this.blocksName = blocksName;
	}
	/**
	 */
	public String getPropertyName() {
		return propertyName;
	}
	/**
	 */
	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}
	/**
	 */
	public int getAmrId() {
		return amrId;
	}
	/**
	 */
	public void setAmrId(final int amrId) {
		this.amrId = amrId;
	}
	/**
	 */
	public Blocks getBlocks() {
		return blocks;
	}
	/**
	 */
	public void setBlocks(final Blocks blocks) {
		this.blocks = blocks;
	}
	/**
	 */
	public Property getProperty() {
		return property;
	}
	/**
	 */
	public void setProperty(final Property property) {
		this.property = property;
	}
	/**
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 */
	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}
	/**
	 */
	public String getStatus() {
		return status;
	}
	/**
	 */
	public void setStatus(final String status) {
		this.status = status;
	}
	/**
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 */
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	/**
	 */
	public void setLastUpdatedBy(final String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	/**
	 */
	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}
	/**
	 */
	public void setLastUpdatedDT(final Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}
	/**
	 */
	public int getBlockId() {
		return blockId;
	}
	/**
	 */
	public void setBlockId(final int blockId) {
		this.blockId = blockId;
	}
	/**
	 */
	public int getPropertyId() {
		return propertyId;
	}
	/**
	 */
	public void setPropertyId(final int propertyId) {
		this.propertyId = propertyId;
	}
	/**
	 */
	public String getMeterNumber() {
		return meterNumber;
	}
	/**
	 */
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	/**
	 */
	@PrePersist
	 protected void onCreate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	  lastUpdatedDT = new Timestamp(new Date().getTime());
      this.status = "Inactive";
	 }
	/**
	 */
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	  lastUpdatedDT = new Timestamp(new Date().getTime());
	 }
	 /**
		 */
		public AMRProperty(){super();}
		/**
		 */
		public AMRProperty(final int amrId, final Blocks blocks, final int blockId,final Property property,final int propertyId,final String columnName,final String status,final String createdBy,final String lastUpdatedBy,final Timestamp lastUpdatedDT, final String blocksName,final String propertyName) {
			this.amrId = amrId;
			this.blocks = blocks;
			this.blockId = blockId;
			this.property = property;
			this.propertyId = propertyId;
			this.columnName = columnName;
			this.status = status;
			this.createdBy = createdBy;
			this.lastUpdatedBy = lastUpdatedBy;
			this.lastUpdatedDT = lastUpdatedDT;
			this.blocksName = blocksName;
			this.propertyName = propertyName;
		}
}
