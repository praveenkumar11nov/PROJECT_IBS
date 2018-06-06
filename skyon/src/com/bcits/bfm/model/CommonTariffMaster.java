package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.bcits.bfm.util.SessionData;
@Entity
@Table(name="CS_TARIFF_MASTER")
@NamedQueries({
	@NamedQuery(name="CommonTariffMasterTree.GetNodeDetails",query="SELECT t FROM CommonTariffMaster t WHERE t.csTariffID = :csTariffID"),
	@NamedQuery(name="CommonTariffMasterTree.getTariffListbyId",query="SELECT e FROM CommonTariffMaster e WHERE e.csParentId =:csParentId ORDER BY e.csTariffID"),
    @NamedQuery(name="CommonTariffMasterTree.getDetails",query="SELECT e FROM  CommonTariffMaster e WHERE e.csParentId =:csParentId AND e.csTariffName =:csTariffName"),
    @NamedQuery(name="CommonTariffMaster.GETTariffNameBasedOnId",query="SELECT t FROM CommonTariffMaster t WHERE t.csTariffID=:csTariffID"),
    @NamedQuery(name="CommonTariffMasterTree.getTariffListbyIdwoStatus",query="SELECT e FROM CommonTariffMaster e WHERE e.csParentId =:csParentId ORDER BY e.csTariffID")
})
public class CommonTariffMaster{
	@Id
	@Column(name = "CS_TARIFF_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "CS_TARIFF_ID_SEQ", sequenceName = "CS_TARIFF_ID_SEQ")
	@GeneratedValue(generator = "CS_TARIFF_ID_SEQ")
	private int csTariffID;

	@Column(name = "CS_TARIFF_CODE")
	private String csTariffCode;

	@Column(name = "CS_TARIFF_NAME")
	private String csTariffName;

	@Column(name = "CS_TARIFF_DESCRIPTION")
	private String csTariffDescription;

	@Column(name = "CS_PARENT_ID")
	private Integer csParentId;

	@Column(name = "CS_TREE_HIERARCHY")
	private String csTreeHierarchy;

	@Column(name = "VALID_FROM")
	private Date validFrom;

	@Column(name = "VALID_TO")
	private Date validTo;

	@Column(name = "CS_TARIFF_NODE_TYPE")
	private String csTariffNodetype;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());;

	
	@ManyToOne()
	@JoinColumn(name = "CS_PARENT_ID", insertable = false, updatable = false)
	@JsonIgnore
	private CommonTariffMaster parentTariff;

	@OneToMany(mappedBy = "parentTariff", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<CommonTariffMaster> childTariff = new HashSet<CommonTariffMaster>();

	@JsonIgnore
	public CommonTariffMaster getParentTariff() {
		return parentTariff;
	}

	@JsonIgnore
	public void setParentTariff(CommonTariffMaster parentTariff) {
		this.parentTariff = parentTariff;
	}

	@JsonIgnore
	public Set<CommonTariffMaster> getChildTariff() {
		return childTariff;
	}

	@JsonIgnore
	public void setChildTariff(Set<CommonTariffMaster> childTariff) {
		this.childTariff = childTariff;
	}

	@Transient
	public Boolean getHasChilds() {
		return !getChildTariff().isEmpty();
	}

	public CommonTariffMaster(){
		
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

	public CommonTariffMaster(int csTariffID, String csTariffCode,
			String csTariffName, String csTariffDescription,
			Integer csParentId, String csTreeHierarchy, Date validFrom,
			Date validTo, String csTariffNodetype, String status,
			String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDT,
			CommonTariffMaster parentTariff, Set<CommonTariffMaster> childTariff) {
		super();
		this.csTariffID = csTariffID;
		this.csTariffCode = csTariffCode;
		this.csTariffName = csTariffName;
		this.csTariffDescription = csTariffDescription;
		this.csParentId = csParentId;
		this.csTreeHierarchy = csTreeHierarchy;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.csTariffNodetype = csTariffNodetype;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDT = lastUpdatedDT;
		this.parentTariff = parentTariff;
		this.childTariff = childTariff;
	}

	public int getCsTariffID() {
		return csTariffID;
	}

	public void setCsTariffID(int csTariffID) {
		this.csTariffID = csTariffID;
	}

	public String getCsTariffCode() {
		return csTariffCode;
	}

	public void setCsTariffCode(String csTariffCode) {
		this.csTariffCode = csTariffCode;
	}

	public String getCsTariffName() {
		return csTariffName;
	}

	public void setCsTariffName(String csTariffName) {
		this.csTariffName = csTariffName;
	}

	public String getCsTariffDescription() {
		return csTariffDescription;
	}

	public void setCsTariffDescription(String csTariffDescription) {
		this.csTariffDescription = csTariffDescription;
	}

	public Integer getCsParentId() {
		return csParentId;
	}

	public void setCsParentId(Integer csParentId) {
		this.csParentId = csParentId;
	}

	public String getCsTreeHierarchy() {
		return csTreeHierarchy;
	}

	public void setCsTreeHierarchy(String csTreeHierarchy) {
		this.csTreeHierarchy = csTreeHierarchy;
	}

	public String getCsTariffNodetype() {
		return csTariffNodetype;
	}

	public void setCsTariffNodetype(String csTariffNodetype) {
		this.csTariffNodetype = csTariffNodetype;
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
}
