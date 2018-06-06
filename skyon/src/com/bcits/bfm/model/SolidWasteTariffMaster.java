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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="SOLID_WASTE_TARIFF_MASTER")
@NamedQueries({
	@NamedQuery(name="SolidWatsteTariff.getTariffListbyId",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteparentId =:solidWasteTariffId ORDER BY e.solidWasteTariffId"),
   // @NamedQuery(name="SolidWAsteTariffMAster.getDetails",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteparentId =:solidWasteTariffId AND e.solidWastetariffName =:solidWastetariffName"),
	//@NamedQuery(name="SolidWAsteTariffMAster.getDetails",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteparentId =:solidWasteTariffId "),
	@NamedQuery(name="SolidWasteTariff.getNodeDetails",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteTariffId=:solidWasteTariffId"), 
    @NamedQuery(name="SolidWasteTariffMaster.getTAriffNameBasedonID",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteTariffId=:solidWasteTariffId"),
    @NamedQuery(name="SolidWatsteTariff.getTariffListbyIdWoStatus",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteparentId =:solidWasteTariffId ORDER BY e.solidWasteTariffId"),
	@NamedQuery(name="SolidWAsteTariffMAster.getDetails",query="SELECT e FROM SolidWasteTariffMaster e WHERE e.solidWasteparentId =:solidWasteTariffId AND e.solidWasteTariffName =:solidWastetariffName"),
})
public class SolidWasteTariffMaster 
{

	@Id
	@Column(name="SOLID_WASTE_TARIFF_ID",unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="SOLID_WASTE_TARIFF_ID_SEQ",sequenceName="SOLID_WASTE_TARIFF_ID_SEQ")
	@GeneratedValue(generator="SOLID_WASTE_TARIFF_ID_SEQ")
	private int solidWasteTariffId;
	
	@Column(name="SOLID_WASTE_TARIFF_CODE")
	private String solidWasteTariffCode;
	
	@Column(name="SOLID_WASTE_TARIFF_NAME")
	private String solidWasteTariffName;
	
	@Column(name="SOLID_WASTE_TARIFF_DESCRIPTION",length=1020)

    private String solidWastetariffDescription;
	
	@Column(name="SOLID_WASTE_PARENT_ID")
    
    private Integer solidWasteparentId;
	
	@Column(name="SOLID_WASTE_TREE_HIERARCHY")
	private String solidWastetreeHierarchy;
	
	@Column(name = "SOLID_WASTE_TARIFF_NODE_TYPE")
	private String solidWastetariffNodetype;

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

	
	
	
	public String getSolidWasteTariffCode() {
		return solidWasteTariffCode;
	}

	public void setSolidWasteTariffCode(String solidWasteTariffCode) {
		this.solidWasteTariffCode = solidWasteTariffCode;
	}

	public String getSolidWasteTariffName() {
		return solidWasteTariffName;
	}

	public void setSolidWasteTariffName(String solidWasteTariffName) {
		this.solidWasteTariffName = solidWasteTariffName;
	}

	public Integer getSolidWasteparentId() {
		return solidWasteparentId;
	}

	public void setSolidWasteparentId(Integer solidWasteparentId) {
		this.solidWasteparentId = solidWasteparentId;
	}

	public String getSolidWastetreeHierarchy() {
		return solidWastetreeHierarchy;
	}

	public void setSolidWastetreeHierarchy(String solidWastetreeHierarchy) {
		this.solidWastetreeHierarchy = solidWastetreeHierarchy;
	}

	public String getSolidWastetariffNodetype() {
		return solidWastetariffNodetype;
	}

	public void setSolidWastetariffNodetype(String solidWastetariffNodetype) {
		this.solidWastetariffNodetype = solidWastetariffNodetype;
	}

	public String getStatus() {
		return status;
	}

	public SolidWasteTariffMaster(){}
	
	/*public SolidWasteTariffMaster(int solidWasteTariffId,
			String solidWasteTariffCode, String solidWastetariffName,
			String tariffDescription, Integer solidWasteparentId,
			String solidWastetreeHierarchy, String solidWastetariffNodetype,
			String status, String createdBy, String lastUpdatedBy,
			Timestamp lastUpdatedDT, Date validFrom, Date validTo,
			SolidWasteTariffMaster parentTariff,
			Set<SolidWasteTariffMaster> childTariff) {
		super();
		this.solidWasteTariffId = solidWasteTariffId;
		this.solidWasteTariffCode = solidWasteTariffCode;
		this.solidWastetariffName = solidWastetariffName;
		this.solidWastetariffDescription = tariffDescription;
		this.solidWasteparentId = solidWasteparentId;
		this.solidWastetreeHierarchy = solidWastetreeHierarchy;
		this.solidWastetariffNodetype = solidWastetariffNodetype;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDT = lastUpdatedDT;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.parentTariff = parentTariff;
		this.childTariff = childTariff;
	}*/

	public int getSolidWasteTariffId() {
		return solidWasteTariffId;
	}

	public void setSolidWasteTariffId(int solidWasteTariffId) {
		this.solidWasteTariffId = solidWasteTariffId;
	}

	public String getSolidWastetariffDescription() {
		return solidWastetariffDescription;
	}

	public void setSolidWastetariffDescription(String solidWastetariffDescription) {
		this.solidWastetariffDescription = solidWastetariffDescription;
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

	@ManyToOne()
	@JoinColumn(name="SOLID_WASTE_PARENT_ID", insertable=false, updatable=false)
	@JsonIgnore
    private SolidWasteTariffMaster parentTariff;

	



	@OneToMany(mappedBy="parentTariff", fetch=FetchType.EAGER)
	@JsonIgnore 
private Set<SolidWasteTariffMaster> childTariff =new HashSet<SolidWasteTariffMaster>();

@JsonIgnore
public SolidWasteTariffMaster getParentTariff() {
	return parentTariff;
}

@JsonIgnore
public void setParentTariff(SolidWasteTariffMaster parentTariff) {
	this.parentTariff = parentTariff;
}

@JsonIgnore
public Set<SolidWasteTariffMaster> getChildTariff() {
	return childTariff;
}
@JsonIgnore
public void setChildTariff(Set<SolidWasteTariffMaster> childTariff) {
	this.childTariff = childTariff;
}

@Transient
public Boolean getHasChilds() {
    return !getChildTariff().isEmpty();
}



}
