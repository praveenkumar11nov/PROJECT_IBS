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
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="WT_TARIFF_MASTER")
   
@NamedQueries({
	@NamedQuery(name="WTTariffMaster.GetNodeDetails",query="SELECT t FROM WTTariffMaster t WHERE t.wtTariffId = :nodeid"),
	@NamedQuery(name="WTTariffMasterTree.getTariffListbyId",query="SELECT e FROM WTTariffMaster e WHERE e.parentId =:parentId ORDER BY e.wtTariffId"),
    @NamedQuery(name="WTTariffMasterTree.getDetails",query="SELECT e FROM  WTTariffMaster e WHERE e.parentId =:parentId AND e.tariffName =:tariffname"),
    @NamedQuery(name="WTTariffMaster.GETTariffNameBasedOnId",query="SELECT t FROM WTTariffMaster t WHERE t.wtTariffId=:wtTariffId"),
    @NamedQuery(name="WTTariffMasterTree.getTariffListbyIdwoStatus",query="SELECT e FROM WTTariffMaster e WHERE e.parentId =:parentId ORDER BY e.wtTariffId")
})

public class WTTariffMaster implements  java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WT_TARIFF_ID",unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="WT_TARIFF_ID_SEQ",sequenceName="WT_TARIFF_ID_SEQ")
	@GeneratedValue(generator="WT_TARIFF_ID_SEQ")
	private int wtTariffId;
	
	
	@Column(name="WT_TARIFF_CODE")
	
	private String tariffCode;
	
	
	@Column(name="WT_TARIFF_NAME")
	private String tariffName;
	
	@Column(name="TARIFF_DESCRIPTION",length=1020)

    private String tariffDescription;
	
	@Column(name="PARENT_ID")
    
    private Integer parentId;
	
	@Column(name="TREE_HIERARCHY")
	private String treeHierarchy;
	
	
	@Column(name = "TARIFF_NODE_TYPE")
	private String tariffNodetype;
	
	public String getTreeHierarchy() {
		return treeHierarchy;
	}

	public void setTreeHierarchy(String treeHierarchy) {
		this.treeHierarchy = treeHierarchy;
	}

	
	
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

   
    public WTTariffMaster(){}

  
	public String getTariffName() {
		return tariffName;
	}

	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
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

	public String getTariffNodetype() {
		return tariffNodetype;
	}

	public void setTariffNodetype(String tariffNodetype) {
		this.tariffNodetype = tariffNodetype;
	}

	public int getWtTariffId() {
		return wtTariffId;
	}

	public void setWtTariffId(int wtTariffId) {
		this.wtTariffId = wtTariffId;
	}

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getTariffDescription() {
		return tariffDescription;
	}

	public void setTariffDescription(String tariffDescription) {
		this.tariffDescription = tariffDescription;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public WTTariffMaster(int wtTariffId, String tariffCode, String tariffName,
			String tariffDescription, Integer parentId, String treeHierarchy,
			String tariffNodetype, String status, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDT, Date validFrom,
			Date validTo, WTTariffMaster parentTariff,
			Set<WTTariffMaster> childTariff) {
		super();
		this.wtTariffId = wtTariffId;
		this.tariffCode = tariffCode;
		this.tariffName = tariffName;
		this.tariffDescription = tariffDescription;
		this.parentId = parentId;
		this.treeHierarchy = treeHierarchy;
		this.tariffNodetype = tariffNodetype;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDT = lastUpdatedDT;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.parentTariff = parentTariff;
		this.childTariff = childTariff;
	}



	@ManyToOne()
	@JoinColumn(name="PARENT_ID", insertable=false, updatable=false)
	@JsonIgnore
private WTTariffMaster parentTariff;

	



	@OneToMany(mappedBy="parentTariff", fetch=FetchType.EAGER)
	@JsonIgnore 
private Set<WTTariffMaster> childTariff =new HashSet<WTTariffMaster>();

@JsonIgnore
public WTTariffMaster getParentTariff() {
	return parentTariff;
}

@JsonIgnore
public void setParentTariff(WTTariffMaster parentTariff) {
	this.parentTariff = parentTariff;
}

@JsonIgnore
public Set<WTTariffMaster> getChildTariff() {
	return childTariff;
}
@JsonIgnore
public void setChildTariff(Set<WTTariffMaster> childTariff) {
	this.childTariff = childTariff;
}

@Transient
public Boolean getHasChilds() {
    return !getChildTariff().isEmpty();
}



/*@PrePersist
protected void onCreate(String createdBy) {
	lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	createdBy = (String) SessionData.getUserDetails().get("userID");
}

@PreUpdate
protected void onUpdate() {
	lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
}*/





}
