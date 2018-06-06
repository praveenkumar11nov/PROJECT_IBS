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

@Table(name = "EL_TARIFF_MASTER")
@NamedQueries({
		//@NamedQuery(name = "abc", query = "SELECT e FROM  ELTariffMaster e WHERE e.parentId =:parentId AND e.tariffName =:tariff_Name"),
		@NamedQuery(name = "EL_Tariff_Master.DELETE", query = "DELETE FROM ELTariffMaster WHERE elTariffID= :EL_Tariff_Id"),
		@NamedQuery(name = "EL_Tariff_Id.GETTariffName", query = "SELECT e.parentId FROM  ELTariffMaster e WHERE e.elTariffID= :EL_Tariff_Id "),
		@NamedQuery(name = "getTariffMasterByTariffId", query = "SELECT e.tariffName FROM  ELTariffMaster e WHERE e.elTariffID= :elTariffID"),
		@NamedQuery(name = "TariffMasterTree.getTariffListbyId", query = "SELECT e FROM ELTariffMaster e WHERE e.parentId =:parentId AND status='Active'   ORDER BY  e.elTariffID ASC"),
		@NamedQuery(name = "TariffMaster.GetNodeDetails", query = "SELECT t FROM ELTariffMaster t WHERE t.elTariffID = :nodeid"),
		@NamedQuery(name = "Tariffmaster.GetAllTariff", query = "SELECT e FROM ELTariffMaster e WHERE e.parentId =:parentId   ORDER BY e.tariffName"),
		@NamedQuery(name = "Tariffmaster.GetTariffName", query = "SELECT e.tariffName FROM ELTariffMaster e WHERE  UPPER (e.tariffName) LIKE upper(:tariff_Name) AND status='Active' "),
		@NamedQuery(name = "ELTariffMaster.getTariffMasterByName",query="SELECT e FROM  ELTariffMaster e WHERE e.tariffName =:tariffName"),
		@NamedQuery(name = "TariffCalc.GETTariffName",query="SELECT e.elTariffID,e.tariffName FROM  ELTariffMaster e WHERE e.elTariffID= e.elTariffID AND e.tariffNodetype='Tariff Rate Node'AND status='Active' AND e.parentId>=2 ORDER BY  e.elTariffID ASC"),
		@NamedQuery(name = "TariffCalc.GETTariffNameTOd",query="SELECT DISTINCT(e1.elTariffID),e.tariffName FROM ELRateMaster e1, ELTariffMaster e WHERE e1.elTariffID = e.elTariffID AND e1.todType NOT LIKE '%None%' AND e.tariffNodetype='Tariff Rate Node'AND e.status='Active' AND e.parentId>=3 ORDER BY  e1.elTariffID ASC"),
		//@NamedQuery(name = "ELTariffMaster.getTariffMasterById", query = "SELECT e FROM  ELTariffMaster e WHERE e.elTariffID= :elTariffID "),
		@NamedQuery(name = "TariffCalc.GETRateUom", query = "SELECT e FROM  ELRateMaster e WHERE e.rateName=:ratename AND e.elTariffID=:elTariffID "),
		@NamedQuery(name = "TariffCalc.GETRateName", query = "SELECT DISTINCT (e.elTariffID) ,e.rateName  FROM  ELRateMaster e WHERE e.elTariffID= :eltariffId  "),
		//@NamedQuery(name = "months_between", query = "SELECT MONTHS_BETWEEN(validTo, validFrom) FROM ELRateMaster"),
		@NamedQuery(name = "ELTariffMaster.getTariffName", query = "SELECT tariffName FROM ELTariffMaster where elTariffID=:elTariffID"),
		@NamedQuery(name = "TariffCalc.GetServiceName",query="SELECT MAX(b.serviceMasterId),b.typeOfService FROM ServiceMastersEntity b WHERE b.accountObj.accountId=:accountId AND b.status='Active' AND b.typeOfService !='CAM' AND b.typeOfService !='Telephone Broadband' GROUP BY b.typeOfService"),
		@NamedQuery(name = "TariffCalc.GetBillStatus",query="SELECT bpm.bvmName,e.elBillParameterValue FROM ElectricityBillParametersEntity e INNER JOIN e.electricityBillEntity eb INNER JOIN e.billParameterMasterEntity bpm WHERE eb.elBillId=:elBillId ORDER BY e.billParameterMasterEntity.bvmSequence ASC"),
        @NamedQuery(name = "TariffCalc.GetBillDate",query="SELECT e.billDate,e.status FROM ElectricityBillEntity e WHERE e.elBillId=:elBillId"),
              
        @NamedQuery(name = "TariffCalc.GetBillOnDate",query="SELECT e.typeOfService, e.accountObj.accountNo,e.billNo,e.billAmount FROM ElectricityBillEntity e WHERE EXTRACT(month FROM e.billDate)=:date and EXTRACT(year FROM e.billDate) =:year and e.typeOfService=:serviceType and e.accountId =:accountId and e.status NOT LIKE 'Cancelled' and e.postType NOT IN('Provisional Bill','Deposit')"), 
        @NamedQuery(name = "TariffCalculation.getAllAccuntNumbers",query="SELECT  DISTINCT eb.accountObj.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM ServiceMastersEntity eb,Account a INNER JOIN a.person p WHERE eb.accountObj.accountId = a.accountId AND eb.status='Active'"),
        @NamedQuery(name = "TariffCalc.GetBillId",query="SELECT MAX(b.elBillId) FROM ElectricityBillEntity b WHERE b.accountId=:accountId AND b.typeOfService=:serviceType AND b.postType='Bill' AND b.status NOT IN('Cancelled')"),
        @NamedQuery(name = "TariffCalc.GetBillInitialDate",query="SELECT e.serviceStartDate FROM ServiceMastersEntity e WHERE e.serviceMasterId=:serviceId"),
        @NamedQuery(name = "TariffCalc.GetMeterId",query="SELECT MAX(b.elMeterId) FROM ElectricityMetersEntity b WHERE b.account.accountId=:accountId AND b.typeOfServiceForMeters=:serviceType AND b.meterType NOT LIKE '%DG Meter%'"),
      
        @NamedQuery(name = "TariffCalc.GetBillInitialReading",query="SELECT mpm.mpmName,e.elMeterParameterValue FROM ElectricityMeterParametersEntity e INNER JOIN e.parameterMasterObj mpm WHERE e.electricityMetersEntity.elMeterId=:meterId ORDER BY e.parameterMasterObj.mpmSequence ASC"),
       // @NamedQuery(name = "TariffCalc.GetDgInitialReading",query="SELECT e.electricityMetersEntity.elMeterId FROM  ElectricityMeterParametersEntity e  WHERE e.electricityMetersEntity.elMeterId=:meterId AND e.parameterMasterObj.mpmName LIKE '%Source Of Meter%' AND e.elMeterParameterValue LIKE '%Dual Source%'"),
	    @NamedQuery(name = "ELTariffMaster.getAllStates", query = "SELECT e.elTariffID,e.tariffName FROM ELTariffMaster e where e.status='Active' and e.tariffNodetype='State Node'"),
	    @NamedQuery(name = "ELTariffMaster.getAllTariffNodes", query = "SELECT e FROM ELTariffMaster e INNER JOIN e.parentTariff p WHERE  e.status='Active' and e.tariffNodetype = 'Tariff Rate Node' and e.stateName=:stateName ORDER BY e.tariffName"),
	    @NamedQuery(name = "ELTariffMaster.getStateName", query = "SELECT e.tariffName FROM ELTariffMaster e where e.status='Active' and e.tariffNodetype='State Node'"),
	    @NamedQuery(name = "ELTariffMaster.getAllTariffMasters", query = "SELECT e.elTariffID,e.tariffName FROM ELTariffMaster e where e.status='Active' and e.tariffNodetype='Tariff Rate Node' ORDER BY e.tariffName ASC"),
        //@NamedQuery(name="TariffCalc.GETallChild", query="SELECT n FROM  ELTariffMaster n left join fetch n.childTariff"),
		@NamedQuery(name="TariffCalc.readPropertyNames",query="SELECT p.blockId,p.propertyId,p.property_No,b.blockName FROM Property p INNER JOIN p.blocks b Where p.blockId=:blockId"),
		@NamedQuery(name="TariffCalc.findAllPropertyPersonOwnerList",query="select DISTINCT(p.personId) from OwnerProperty op INNER JOIN op.owner o INNER JOIN o.person p WHERE p.personStatus = 'Active' AND op.propertyId=:propertyId "),
		@NamedQuery(name="TariffCalc.findAllPropertyPersonTenantList",query="select DISTINCT(p.personId) from TenantProperty tp INNER JOIN tp.tenantId t INNER JOIN t.person p WHERE p.personStatus = 'Active'AND tp.propertyId=:propertyId "),  
		
		//@NamedQuery(name="TariffCalc.findAccountID",query="SELECT  a.accountId  FROM Account a WHERE a.personId =:personId AND a.propertyId=:propertyId"),  
		@NamedQuery(name="Blocks.getAllBlocks",query="SELECT b.blockId FROM Blocks b "),
		@NamedQuery(name="TariffCalc.getPropertyId",query="SELECT p.propertyId FROM Property p INNER JOIN p.blocks b Where p.blockId=:blockId and p.status='Sold'"),
		 @NamedQuery(name = "TariffCalc.GetAdvanceBillOnDate",query="SELECT e.typeOfService, e.accountObj.accountNo,e.abBillNo,e.abBillAmount FROM AdvanceBill e WHERE EXTRACT(month FROM e.abBillDate)=:date and EXTRACT(year FROM e.abBillDate) =:year and e.typeOfService=:serviceType and e.accountId =:accountId )"), 
		 @NamedQuery(name = "ELTariffMaster.getStateNameById", query = "SELECT e.stateName FROM ELTariffMaster e WHERE e.elTariffID= :elTariffID"),
		 @NamedQuery(name="TariffCalc.readPropertyNamesAmr",query="SELECT DISTINCT(pt.propertyId),pt.property_No FROM Property pt,ServiceMastersEntity sm INNER JOIN pt.blocks b INNER JOIN sm.accountObj a WHERE pt.propertyId=a.propertyId AND sm.typeOfService IN ('Electricity','CAM') AND b.blockId=:blockId"),
		 @NamedQuery(name="Blocks.getAllBlocksAmr",query="SELECT b FROM Blocks b "),
		 @NamedQuery(name = "TariffCalc.GetBillInitialReadingName",query="SELECT bpm.bvmName FROM ElectricityBillParametersEntity e INNER JOIN e.electricityBillEntity eb INNER JOIN e.billParameterMasterEntity bpm WHERE eb.elBillId=:elBillId ORDER BY e.billParameterMasterEntity.bvmSequence ASC"),  
		 @NamedQuery(name = "TariffCalc.GetBillInitialReadingNamePresent",query="SELECT bpm.bvmName FROM ElectricityBillParametersEntity e INNER JOIN e.electricityBillEntity eb INNER JOIN e.billParameterMasterEntity bpm WHERE eb.elBillId=:elBillId AND bpm.bvmName='Present  DG reading' ORDER BY e.billParameterMasterEntity.bvmSequence ASC"),  
		 @NamedQuery(name = "BillGeneration.getAllAccuntNumbersBasedOnProperty",query="SELECT DISTINCT a.accountId,a.accountNo,p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM ServiceMastersEntity eb,Property pt INNER JOIN eb.accountObj a INNER JOIN a.person p WHERE pt.propertyId=a.propertyId AND eb.status='Active' AND a.propertyId =:propertyId"),
})

public class ELTariffMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EL_TARIFF_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "EL_TARIFF_ID_SEQ", sequenceName = "EL_TARIFF_ID_SEQ")
	@GeneratedValue(generator = "EL_TARIFF_ID_SEQ")
	private int elTariffID;

	@Column(name = "TARIFF_CODE")
	private String tariffCode;

	@Column(name = "TARIFF_NAME")
	private String tariffName;

	@Column(name = "TARIFF_DESCRIPTION")
	private String tariffDescription;

	@Column(name = "PARENT_ID")
	private Integer parentId;

	@Column(name = "TREE_HIERARCHY")
	private String treeHierarchy;

	@Column(name = "VALID_FROM")
	private Date validFrom;

	@Column(name = "VALID_TO")
	private Date validTo;

	@Column(name = "TARIFF_NODE_TYPE")
	private String tariffNodetype;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());;

	@Column(name="STATE_NAME")
	private String stateName;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date date) {
		this.validFrom = date;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date date) {
		this.validTo = date;
	}

	public String getTreeHierarchy() {
		return treeHierarchy;
	}

	public void setTreeHierarchy(String treeHierarchy) {
		this.treeHierarchy = treeHierarchy;
	}
	public ELTariffMaster() {
	}

	public int getElTariffID() {
		return elTariffID;
	}

	public void setElTariffID(int elTariffID) {
		this.elTariffID = elTariffID;
	}

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getTariffName() {
		return tariffName;
	}

	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
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

	public Timestamp getLastUpdatedDT() {
		return lastUpdatedDT;
	}

	public void setLastUpdatedDT(Timestamp lastUpdatedDT) {
		this.lastUpdatedDT = lastUpdatedDT;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
	@JsonIgnore
	private ELTariffMaster parentTariff;

    
	@OneToMany(mappedBy = "parentTariff", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<ELTariffMaster> childTariff = new HashSet<ELTariffMaster>();

	@JsonIgnore
	public ELTariffMaster getParentTariff() {
		return parentTariff;
	}

	@JsonIgnore
	public void setParentTariff(ELTariffMaster parentTariff) {
		this.parentTariff = parentTariff;
	}

	@JsonIgnore
	public Set<ELTariffMaster> getChildTariff() {
		return childTariff;
	}

	@JsonIgnore
	public void setChildTariff(Set<ELTariffMaster> childTariff) {
		this.childTariff = childTariff;
	}

	@Transient
	public Boolean getHasChilds() {
		return !getChildTariff().isEmpty();
	}
	
	public String getTariffNodetype() {
		return tariffNodetype;
	}

	public void setTariffNodetype(String tariffNodetype) {
		this.tariffNodetype = tariffNodetype;
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
