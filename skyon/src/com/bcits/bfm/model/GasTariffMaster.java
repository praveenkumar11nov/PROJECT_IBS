package com.bcits.bfm.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="GAS_TARIFF_MASTER")
@NamedQueries({
	@NamedQuery(name="GasTariffMasterTree.getTariffListbyId",query="SELECT e FROM GasTariffMaster e WHERE e.gasparentId =:parentId ORDER BY e.gasTariffId"),
	@NamedQuery(name="GasTariffMaster.GetNodeDetails",query="SELECT t FROM GasTariffMaster t WHERE t.gasTariffId = :gasTariffId"),
	
    @NamedQuery(name="GasTariffMasterTree.getDetails",query="SELECT e FROM  GasTariffMaster e WHERE e.gasparentId =:gasparentId AND e.gastariffName =:gastariffName"),
    @NamedQuery(name="GasTariffMaster.GETTariffNameBasedOnId",query="SELECT t FROM GasTariffMaster t WHERE t.gasTariffId=:gasTariffId"),
    @NamedQuery(name="GasTariffMasterTree.getTariffListbyIdwoStatus",query="SELECT e FROM GasTariffMaster e WHERE e.gasparentId =:gasparentId ORDER BY e.gasTariffId")

})
public class GasTariffMaster implements  java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="GAS_TARIFF_ID",unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="GAS_TARIFF_ID_SEQ",sequenceName="GAS_TARIFF_ID_SEQ")
	@GeneratedValue(generator="GAS_TARIFF_ID_SEQ")
	private int gasTariffId;
	
	
	@Column(name="GAS_TARIFF_CODE")
	
	private String gastariffCode;
	
	
	@Column(name="GAS_TARIFF_NAME")
	private String gastariffName;
	
	@Column(name="TARIFF_DESCRIPTION",length=1020)

    private String gastariffDescription;
	
	@Column(name="PARENT_ID")
    
    private Integer gasparentId;
	
	@Column(name="TREE_HIERARCHY")
	private String gastreeHierarchy;
	
	
	@Column(name = "TARIFF_NODE_TYPE")
	private String gastariffNodetype;
	
	
	@Size(min=1,message="Status not selected")
	@Column(name="STATUS",length=1020)
    
	private String gasStatus;
	
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
	@JoinColumn(name="PARENT_ID", insertable=false, updatable=false)
	@JsonIgnore
private GasTariffMaster parentTariff;

	@OneToMany(mappedBy="parentTariff", fetch=FetchType.EAGER)
	@JsonIgnore 
private Set<GasTariffMaster> childTariff =new HashSet<GasTariffMaster>();

@JsonIgnore
public GasTariffMaster getParentTariff() {
	return parentTariff;
}

@JsonIgnore
public void setParentTariff(GasTariffMaster parentTariff) {
	this.parentTariff = parentTariff;
}

@JsonIgnore
public Set<GasTariffMaster> getChildTariff() {
	return childTariff;
}
@JsonIgnore
public void setChildTariff(Set<GasTariffMaster> childTariff) {
	this.childTariff = childTariff;
}

@Transient
public Boolean getHasChilds() {
    return !getChildTariff().isEmpty();
}

public int getGasTariffId() {
	return gasTariffId;
}

public void setGasTariffId(int gasTariffId) {
	this.gasTariffId = gasTariffId;
}

public String getGastariffCode() {
	return gastariffCode;
}

public void setGastariffCode(String gastariffCode) {
	this.gastariffCode = gastariffCode;
}

public String getGastariffName() {
	return gastariffName;
}

public void setGastariffName(String gastariffName) {
	this.gastariffName = gastariffName;
}

public String getGastariffDescription() {
	return gastariffDescription;
}

public void setGastariffDescription(String gastariffDescription) {
	this.gastariffDescription = gastariffDescription;
}

public Integer getGasparentId() {
	return gasparentId;
}

public void setGasparentId(Integer gasparentId) {
	this.gasparentId = gasparentId;
}

public String getGastreeHierarchy() {
	return gastreeHierarchy;
}

public void setGastreeHierarchy(String gastreeHierarchy) {
	this.gastreeHierarchy = gastreeHierarchy;
}

public String getGastariffNodetype() {
	return gastariffNodetype;
}

public void setGastariffNodetype(String gastariffNodetype) {
	this.gastariffNodetype = gastariffNodetype;
}

public String getGasStatus() {
	return gasStatus;
}

public void setGasStatus(String gasStatus) {
	this.gasStatus = gasStatus;
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

public GasTariffMaster(){
	
}

public GasTariffMaster(int gasTariffId, String gastariffCode,
		String gastariffName, String gastariffDescription, Integer gasparentId,
		String gastreeHierarchy, String gastariffNodetype, String gasStatus,
		String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDT,
		Date validFrom, Date validTo, GasTariffMaster parentTariff,
		Set<GasTariffMaster> childTariff) {
	super();
	this.gasTariffId = gasTariffId;
	this.gastariffCode = gastariffCode;
	this.gastariffName = gastariffName;
	this.gastariffDescription = gastariffDescription;
	this.gasparentId = gasparentId;
	this.gastreeHierarchy = gastreeHierarchy;
	this.gastariffNodetype = gastariffNodetype;
	this.gasStatus = gasStatus;
	this.createdBy = createdBy;
	this.lastUpdatedBy = lastUpdatedBy;
	this.lastUpdatedDT = lastUpdatedDT;
	this.validFrom = validFrom;
	this.validTo = validTo;
	this.parentTariff = parentTariff;
	this.childTariff = childTariff;
}


   
}
