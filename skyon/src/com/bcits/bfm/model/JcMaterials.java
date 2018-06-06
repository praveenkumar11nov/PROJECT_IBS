package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * JcMaterials entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JC_MATERIALS")
@NamedQueries({
	@NamedQuery(name = "JobCards.readJobMaterials", query = "SELECT jobMaterial FROM JcMaterials jobMaterial WHERE jobMaterial.jobCards=:jcId")
})
public class JcMaterials implements java.io.Serializable {

	// Fields
	
	private static final long serialVersionUID = 1L;
	private int jcmId;
	private JobCards jobCards;
	private UnitOfMeasurement unitOfMeasurement;
	private ItemMaster itemMaster;
	private String jcmType;
	private String imQuantity;
	private String jcmMaterial;
	private String jcmPartno;
	private String jcQuantity;
	private String rerunedquantity;
	private int rate;
	private String createdBy;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDt;
	private StoreMaster storeMaster;
	private String status;

	private Set<StoreGoodsReturns> storeGoodsReturnses = new HashSet<StoreGoodsReturns>(0);
	private Set<StoreIssue> storeIssues = new HashSet<StoreIssue>(0);

	// Constructors

	/** default constructor */
	public JcMaterials() {
	}

	// Property accessors
	@Id
	@Column(name = "JCM_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name = "JOB_MATERIAL", sequenceName = "JOB_MATERIAL")
	@GeneratedValue(generator = "JOB_MATERIAL")	
	public int getJcmId() {
		return this.jcmId;
	}

	public void setJcmId(int jcmId) {
		this.jcmId = jcmId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JC_ID", nullable = false)
	@NotNull(message="Job Card is Required")
	public JobCards getJobCards() {
		return this.jobCards;
	}

	public void setJobCards(JobCards jobCards) {
		this.jobCards = jobCards;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IM_UOM", nullable = false)
	@NotNull(message="Unit Of Issue is Required")
	public UnitOfMeasurement getUnitOfMeasurement() {
		return this.unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement UnitOfMeasurement) {
		this.unitOfMeasurement = UnitOfMeasurement;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IM_ID", nullable = false)
	@NotNull(message="Item Name is Required")
	public ItemMaster getItemMaster() {
		return this.itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	@Column(name = "JCM_TYPE", nullable = false, length = 45)
	public String getJcmType() {
		return this.jcmType;
	}

	public void setJcmType(String jcmType) {
		this.jcmType = jcmType;
	}

	@Column(name = "IM_QUANTITY", nullable = false, length = 45)
	@NotEmpty(message="Quantity is Required")
	public String getImQuantity() {
		return this.imQuantity;
	}

	public void setImQuantity(String imQuantity) {
		this.imQuantity = imQuantity;
	}

	@Column(name = "JCM_MATERIAL", length = 500)
	public String getJcmMaterial() {
		return this.jcmMaterial;
	}

	public void setJcmMaterial(String jcmMaterial) {
		this.jcmMaterial = jcmMaterial;
	}

	@Column(name = "JCM_PARTNO", nullable = false, length = 200)
	@NotEmpty(message="Part Number is Required")
	public String getJcmPartno() {
		return this.jcmPartno;
	}

	public void setJcmPartno(String jcmPartno) {
		this.jcmPartno = jcmPartno;
	}

	@Column(name = "JC_QUANTITY", nullable = false, length = 45)
	@NotEmpty(message="Quantity is Required")
	public String getJcQuantity() {
		return this.jcQuantity;
	}

	public void setJcQuantity(String jcQuantity) {
		this.jcQuantity = jcQuantity;
	}
	

	@Column(name = "RETURNED_QUANTITY", nullable = true, length = 45)
	public String getRerunedquantity() {
		return rerunedquantity;
	}

	public void setRerunedquantity(String rerunedquantity) {
		this.rerunedquantity = rerunedquantity;
	}

	@Column(name = "RATE", nullable = false, precision = 10)
	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Column(name = "CREATED_BY", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY", length = 45)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT", length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STORE_ID", nullable = false)
	public StoreMaster getstoreMaster() {
		return this.storeMaster;
	}

	public void setstoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}

	@Column(name = "STATUS", length = 45)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true,mappedBy = "jcMaterials")
	public Set<StoreGoodsReturns> getStoreGoodsReturnses() {
		return this.storeGoodsReturnses;
	}

	public void setStoreGoodsReturnses(
			Set<StoreGoodsReturns> storeGoodsReturnses) {
		this.storeGoodsReturnses = storeGoodsReturnses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true, mappedBy = "jcMaterials")
	public Set<StoreIssue> getStoreIssues() {
		return this.storeIssues;
	}

	public void setStoreIssues(Set<StoreIssue> storeIssues) {
		this.storeIssues = storeIssues;
	}

	
}