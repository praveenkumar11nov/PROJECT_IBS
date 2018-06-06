package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bcits.bfm.util.SessionData;


@NamedQueries({
	@NamedQuery(name = "BillingConfiguration.findALL",query="SELECT bc.id,bc.configName,bc.configValue,bc.description,bc.status FROM BillingConfiguration bc ORDER BY bc.id DESC "),
	@NamedQuery(name = "BillingConfiguration.billingSettingsStatus", query = "UPDATE BillingConfiguration bc SET bc.status = :status WHERE bc.id = :id"),
	@NamedQuery(name = "BillingConfiguration.getBillingConfigValue", query = "SELECT bc.configValue FROM BillingConfiguration bc WHERE bc.configName =:configName and bc.status =:status",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name = "BillingConfiguration.checkForDuplicate", query = "SELECT bc.configName FROM BillingConfiguration bc WHERE bc.configName =:configName"),
})     
@Entity
@Table(name="BILLING_CONFIGURATION")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="BillingConfiguration")
public class BillingConfiguration {
	/**
	 */
	@Id
	@SequenceGenerator(name = "billingConfiguration_seq", sequenceName = "BILLING_CONFIGURATION_SEQ")
	@GeneratedValue(generator = "billingConfiguration_seq")
	@Column(name="ID")
	private int id;
	/**
	 */
	@Column(name="CONFIG_NAME")
	private String configName;
	/** 
	 */
	@Column(name="CONFIG_VALUE")
	private String configValue;
	/**
	 */
	@Column(name="DESCRIPTION")
	private String description;
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigValue() {
		return configValue;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
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
}
