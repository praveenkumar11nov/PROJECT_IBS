package com.bcits.bfm.model;

import java.util.Date;
import java.sql.Timestamp;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name="ACCESS_PERMISSIONS")
@NamedQueries({
	@NamedQuery(name="AccessCardsPermissions.findAll",query="SELECT a FROM AccessCardsPermission a"),
	@NamedQuery(name="AccessCardsPermissions.findOnacId",query="SELECT a FROM AccessCardsPermission a WHERE a.acId = :acId")
})
public class AccessCardsPermission 
{
	@Id
	@SequenceGenerator(name = "accessCardPerSeq", sequenceName = "ACCESS_CARD_PERMIS_SEQ")
	@GeneratedValue(generator = "accessCardPerSeq")
	@Column(name="ACP_ID")
	private int acpId;
	
	//@Min(value=1 ,message="Access Card Not Selected")
	@Column(name="AC_ID")
	private int acId;
	
	@Min(value=1 ,message="Access Repository Not Selected")
	@Column(name="ACCESS_POINT_ID")
	private int arId;
	
	@Column(name="ACP_START_DATE")
	private Date acpStartDate;
	
	@Column(name="ACP_END_DATE")
	private Date acpEndDate;
	
	/*@Column(name="AC_POINT_NAME")
	private String acPointName;*/
	
	@Size(min = 1, message = "Status is not selected")
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdateDt;

	public int getAcpId() {
		return acpId;
	}

	public void setAcpId(int acpId) {
		this.acpId = acpId;
	}

	public int getAcId() {
		return acId;
	}

	public void setAcId(int acId) {
		this.acId = acId;
	}

	public int getArId() {
		return arId;
	}

	public void setArId(int arId) {
		this.arId = arId;
	}

	public Date getAcpStartDate() {
		return acpStartDate;
	}

	public void setAcpStartDate(Date acpStartDate) {
		this.acpStartDate = acpStartDate;
	}

	public Date getAcpEndDate() {
		return acpEndDate;
	}

	public void setAcpEndDate(Date acpEndDate) {
		this.acpEndDate = acpEndDate;
	}

	/*public String getAcPointName() {
		return acPointName;
	}

	public void setAcPointName(String acPointName) {
		this.acPointName = acPointName;
	}*/

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

	public Timestamp getLastUpdateDt() {
		return lastUpdateDt;
	}

	public void setLastUpdateDt(Timestamp lastUpdateDt) {
		this.lastUpdateDt = lastUpdateDt;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACCESS_POINT_ID", insertable = false, updatable = false, nullable = false)
	private AccessPoints ar;


	public AccessPoints getAr() {
		return ar;
	}


	public void setAr(AccessPoints ar) {
		this.ar = ar;
	}
	 
	@PrePersist
	protected void onCreate() 
	{
	     lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	     createdBy = (String) SessionData.getUserDetails().get("userID");
	 }

	@PreUpdate
	protected void onUpdate() 
	{
	   lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	}

}
