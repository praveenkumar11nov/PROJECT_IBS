package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.oval.constraint.MatchPattern;

import com.bcits.bfm.util.SessionData;

@Entity
@NamedQueries({
	@NamedQuery(name="StoreMaster.getAllStoresRequiredFields",query="SELECT s.storeId, s.storeName, s.storeInchargeStaffId, s.storeLocation, s.storeStatus, s.createdBy, s.lastUpdatedBy, s.lastUpdatedDt, p.firstName, p.lastName FROM StoreMaster s INNER JOIN s.users u INNER JOIN u.person p WHERE s.storeId > 1 ORDER BY s.storeId DESC"),
	@NamedQuery(name="StoreMaster.setStoreStatus",query="UPDATE StoreMaster s SET s.storeStatus = :storeStatus WHERE s.storeId = :storeId"),
	@NamedQuery(name="StoreMaster.getStoresBasedOnContractExistence", query = "SELECT s.storeId, s.storeName, s.storeLocation FROM StoreMaster s WHERE s.storeId IN (SELECT DISTINCT vc.storeId FROM VendorContracts vc, VendorContractLineitems vcl WHERE vcl.vcId = vc.vcId AND vc.status LIKE 'Approved' AND vcl.reqType LIKE 'Item Supply' AND vc.vcId NOT IN (SELECT DISTINCT sgr.vcId FROM StoreGoodsReceipt sgr)) AND s.storeStatus = 'Active' AND s.storeId > 1 ORDER BY s.storeId DESC"),
	@NamedQuery(name="StoreMaster.getStoresBasedOnContractExistenceForAdjustment", query = "SELECT s.storeId, s.storeName, s.storeLocation FROM StoreMaster s WHERE s.storeId IN (SELECT DISTINCT vc.storeId FROM VendorContracts vc, VendorContractLineitems vcl WHERE vcl.vcId = vc.vcId AND vc.status LIKE 'Approved' AND vcl.reqType LIKE 'Item Supply') AND s.storeStatus = 'Active' AND s.storeId > 1 ORDER BY s.storeId DESC"),
	@NamedQuery(name="StoreMaster.getStoreNamesForFilterList", query = "SELECT DISTINCT s.storeName FROM StoreMaster s WHERE s.storeId != 1 ORDER BY s.storeName"),
	@NamedQuery(name="StoreMaster.getstoreInchargeStaffNamesFilterList", query = "SELECT DISTINCT p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM StoreMaster s INNER JOIN s.users u INNER JOIN u.person p WHERE s.storeId != 1 ORDER BY p.firstName"),
	@NamedQuery(name="StoreMaster.getStoreLocationFilterUrl", query = "SELECT DISTINCT s.storeLocation FROM StoreMaster s  ORDER BY s.storeLocation"),
})
@Table(name = "STORE_MASTER")
public class StoreMaster
{
	private int storeId;
	private String storeName;
	private int storeInchargeStaffId;
	private String storeInchargeStaffName;
	private String storeLocation;
	private String storeStatus;
	private String createdBy;
	private String lastUpdatedBy;
	@Access(AccessType.FIELD)
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	private Users users;
	
	@Id
	@SequenceGenerator(name="STORE_MASTER_SEQ",sequenceName="STORE_MASTER_SEQ")
	@GeneratedValue(generator="STORE_MASTER_SEQ")
	@Column(name = "STORE_ID")
	public int getStoreId() 
	{
		return this.storeId;
	}

	public void setStoreId(int storeId) 
	{
		this.storeId = storeId;
	}

	@net.sf.oval.constraint.Size(min = 1, max = 45, message = "Size.StoreMaster.storeName")
	@MatchPattern( pattern = "^[a-zA-Z ]{1,45}$", message = "MatchPattern.StoreMaster.storeName")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreMaster.storeName")
	@Column(name = "STORE_NAME")
	public String getStoreName() 
	{
		return this.storeName;
	}

	public void setStoreName(String storeName) 
	{
		this.storeName = storeName;
	}

	@net.sf.oval.constraint.Min(value = 1, message = "Min.StoreMaster.storeInchargeStaffId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.StoreMaster.storeInchargeStaffId")
	@Column(name = "STORE_INCHARGE_STAFF_ID")
	public int getStoreInchargeStaffId()
	{
		return storeInchargeStaffId;
	}
	
	public void setStoreInchargeStaffId(int storeInchargeStaffId)
	{
		this.storeInchargeStaffId = storeInchargeStaffId;
	}

	@net.sf.oval.constraint.Size(max = 225, message = "Size.StoreMaster.storeLocation")
	@Column(name = "STORE_LOCATION")
	public String getStoreLocation() 
	{
		return this.storeLocation;
	}

	public void setStoreLocation(String storeLocation) 
	{
		this.storeLocation = storeLocation;
	}

	@net.sf.oval.constraint.Size(min=1, message = "Size.StoreMaster.storeStatus")
	@MatchPattern( pattern = "Active|Inactive", message = "MatchPattern.StoreMaster.storeStatus")
	@Column(name = "STATUS")
	public String getStoreStatus()
	{
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus)
	{
		this.storeStatus = storeStatus;
	}


	@Column(name = "CREATED_BY")
	public String getCreatedBy() 
	{
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy() 
	{
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt()
	{
		return this.lastUpdatedDt;
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
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STORE_INCHARGE_STAFF_ID", insertable = false, updatable = false, nullable = false)
	public Users getUsers()
	{
		return users;
	}
	
	public void setUsers(Users users)
	{
		this.users = users;
	}
	
	@Transient
	public String getStoreInchargeStaffName()
	{
		return storeInchargeStaffName;
	}
	
	public void setStoreInchargeStaffName(String storeInchargeStaffName)
	{
		this.storeInchargeStaffName = storeInchargeStaffName;
	}

}