package com.bcits.bfm.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="BROAD_TELE_TARIFF_MASTER")
@NamedQueries({
	@NamedQuery(name="BroadTeleTariffMaster.GetONParentId",query="SELECT e FROM BroadTeleTariffMaster e WHERE e.broadTeleParentId =:broadTeleTariffId ORDER BY e.broadTeleTariffId"),
	@NamedQuery(name="BraodTeleTariffMaster.GetTariffDetails",query="SELECT e FROM  BroadTeleTariffMaster e WHERE e.broadTeleParentId =:broadTeleTariffId AND e.broadTeleTariffName =:broadTeleTariffName"),
    @NamedQuery(name="BroadTeleTariffMaster.GetOnNodeId",query="SELECT e  FROM BroadTeleTariffMaster e WHERE e.broadTeleTariffId=:broadTeleTariffId"), 
    @NamedQuery(name="BroadTeleTariffMaster.GetONParentIdWoStatus",query="SELECT e FROM BroadTeleTariffMaster e WHERE e.broadTeleParentId =:broadTeleTariffId ORDER BY e.broadTeleTariffId"),
    @NamedQuery(name="BroadBand.GETTariffNameBasedOnId",query="SELECT e FROM  BroadTeleTariffMaster e WHERE e.broadTeleTariffId =:broadTeleTariffId") 
})


public class BroadTeleTariffMaster implements  java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="BROAD_TELE_TARIFF_ID",unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="BROAD_TELE_TARIFF_ID_SEQ",sequenceName="BROAD_TELE_TARIFF_ID_SEQ")
	@GeneratedValue(generator="BROAD_TELE_TARIFF_ID_SEQ")
	private int broadTeleTariffId;
	

	
	@Column(name="BROAD_TELE_TARIFF_CODE")
	
	private String broadTeleTariffCode;
	
	
	@Column(name="BROAD_TELE_TARIFF_NAME")
	private String broadTeleTariffName;
	
	@Column(name="BROAD_TELE_TARIFF_DESCRIPTION",length=1020)

    private String broadTeleTariffDescription;
	
	@Column(name="BROAD_TELE_PARENT_ID")
    
    private Integer broadTeleParentId;
	
	@Column(name="BROAD_TELE_TREE_HIERARCHY")
	private String broadTeleTreeHierarchy;
	
	
	@Column(name = "BROAD_TELE_TARIFF_NODE_TYPE")
	private String broadTeleTariffNodetype;
	
	
	@Size(min=1,message="Status not selected")
	@Column(name="STATUS",length=1020)
    
	private String status;
	
   @Column(name="CREATED_BY",precision = 11, scale = 0)
    private String createdBy;
    
    @Column(name="LAST_UPDATED_BY", precision = 11, scale = 0)
    
   
    private  String lastUpdatedBy;
    
    @Column(name="LAST_UPDATED_DT", length = 11)
    
    private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
    
    @Column(name = "VALID_FROM")
	private Date validFrom;

	@Column(name = "VALID_TO")
	private Date validTo;
	
	@ManyToOne()
	@JoinColumn(name="BROAD_TELE_PARENT_ID", insertable=false, updatable=false)
	@JsonIgnore
private BroadTeleTariffMaster parentTariff;

	@OneToMany(mappedBy="parentTariff", fetch=FetchType.EAGER)
	@JsonIgnore 
private Set<BroadTeleTariffMaster> childTariff =new HashSet<BroadTeleTariffMaster>();

@JsonIgnore
public BroadTeleTariffMaster getParentTariff() {
	return parentTariff;
}

@JsonIgnore
public void setParentTariff(BroadTeleTariffMaster parentTariff) {
	this.parentTariff = parentTariff;
}

@JsonIgnore
public Set<BroadTeleTariffMaster> getChildTariff() {
	return childTariff;
}
@JsonIgnore
public void setChildTariff(Set<BroadTeleTariffMaster> childTariff) {
	this.childTariff = childTariff;
}

@Transient
public Boolean getHasChilds() {
    return !getChildTariff().isEmpty();
}


public String getCreatedBy() {
	return createdBy;
}

public int getBroadTeleTariffId() {
	return broadTeleTariffId;
}

public void setBroadTeleTariffId(int broadTeleTariffId) {
	this.broadTeleTariffId = broadTeleTariffId;
}

public String getBroadTeleTariffCode() {
	return broadTeleTariffCode;
}

public void setBroadTeleTariffCode(String broadTeleTariffCode) {
	this.broadTeleTariffCode = broadTeleTariffCode;
}

public String getBroadTeleTariffName() {
	return broadTeleTariffName;
}

public void setBroadTeleTariffName(String broadTeleTariffName) {
	this.broadTeleTariffName = broadTeleTariffName;
}

public String getBroadTeleTariffDescription() {
	return broadTeleTariffDescription;
}

public void setBroadTeleTariffDescription(String broadTeleTariffDescription) {
	this.broadTeleTariffDescription = broadTeleTariffDescription;
}

public Integer getBroadTeleParentId() {
	return broadTeleParentId;
}

public void setBroadTeleParentId(Integer broadTeleParentId) {
	this.broadTeleParentId = broadTeleParentId;
}

public String getBroadTeleTreeHierarchy() {
	return broadTeleTreeHierarchy;
}

public void setBroadTeleTreeHierarchy(String broadTeleTreeHierarchy) {
	this.broadTeleTreeHierarchy = broadTeleTreeHierarchy;
}

public String getBroadTeleTariffNodetype() {
	return broadTeleTariffNodetype;
}

public void setBroadTeleTariffNodetype(String broadTeleTariffNodetype) {
	this.broadTeleTariffNodetype = broadTeleTariffNodetype;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
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

public Date getValidFrom() {
	return validFrom;
}

public void setValidFrom(Date validFrom) {
	this.validFrom = validFrom;
}

public Date getValidTo() {
	return validTo;
}

public void setValidTo(Date validTo) {
	this.validTo = validTo;
}
   
}
