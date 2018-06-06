package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.bcits.bfm.util.SessionData;

@Entity
@Table(name = "ACCESS_CARD")
@NamedQueries({
	/*@NamedQuery(name = "AccessCards.findAll", query = "SELECT ac FROM AccessCards ac ORDER BY ac.acId"),
	@NamedQuery(name = "AccessCards.getAccesCardIdBasedOnNo", query = "SELECT ac.acId FROM AccessCards ac WHERE ac.acNo=:acNo"),
	@NamedQuery(name = "AccessCards.getaccessCradNoBasedOnId", query = "SELECT ac.acNo FROM AccessCards ac WHERE ac.acId=:acId"),
	@NamedQuery(name = "AccessCards.findOnPersonId", query = "SELECT a FROM AccessCards a WHERE a.personId=:personId"),
	@NamedQuery(name = "AccessCards.findOnPersonIdForPermissions", query = "SELECT a FROM AccessCards a WHERE a.personId=:personId AND a.status = 'Active'"),
	@NamedQuery(name = "AccessCards.findAcNames", query = "SELECT a.acNo FROM AccessCards a WHERE a.acNo=:acNo AND  a.personId=:personId")*/
	
	
	
	@NamedQuery(name = "AccessCards.findAll", query = "SELECT ac FROM AccessCards ac ORDER BY ac.acId"),
	@NamedQuery(name = "AccessCards.getAccesCardIdBasedOnNo", query = "SELECT ac.acId FROM AccessCards ac WHERE ac.acNo=:acNo"),
	@NamedQuery(name = "AccessCards.getaccessCradNoBasedOnId", query = "SELECT ac.acNo FROM AccessCards ac WHERE ac.acId=:acId"),
	@NamedQuery(name = "AccessCards.findOnPersonId", query = "SELECT a FROM AccessCards a WHERE a.acNo=:personId"),
	@NamedQuery(name = "AccessCards.findOnPersonIdForPermissions", query = "SELECT p.acId,a.acNo FROM PersonAccessCards p,AccessCards a WHERE p.personId=:personId AND p.acId = a.acId"),
	@NamedQuery(name = "AccessCards.findAcNames", query = "SELECT a FROM AccessCards a,PersonAccessCards pac WHERE a.acNo=:acNo AND a.acId = pac.acId AND pac.personId=:personId "),
	
	@NamedQuery(name = "AccessCards.findOnPersonIdForSystemSecurityDisplay", query = "SELECT a.acType,a.acNo,a.status FROM PersonAccessCards p,AccessCards a WHERE p.personId=:personId AND p.acId = a.acId "),
	@NamedQuery(name="AccessCards.findAllAccessCards",query="SELECT ac FROM AccessCards ac WHERE ac.acId NOT IN(SELECT pac.acId FROM PersonAccessCards pac) AND ac.status='Active'"),
	@NamedQuery(name="AccessCards.UpdateStatus",query="UPDATE AccessCards a SET a.status = :status WHERE a.acId = :acId"),
	@NamedQuery(name="AccessCards.findAllAccessCardNumbers",query="SELECT a.acNo FROM AccessCards a"),
	@NamedQuery(name="AccessCards.getAcNosBasedOnpersonId",query="SELECT a FROM AccessCards a,PersonAccessCards pac WHERE pac.personId=:personId AND pac.acId=a.acId "),
	@NamedQuery(name="AccessCard.getCardAssignedStatusOnId",query="SELECT ac.acId,p.firstName,p.middleName,p.lastName,p.personType,p.personId FROM Person p,PersonAccessCards pa,AccessCards ac WHERE ac.acId = pa.acId AND pa.personId = p.personId AND ac.acId = :acId "),
    @NamedQuery(name ="AccessCards.findAllList", query = "SELECT ac.acId,ac.acType,ac.acNo,ac.status,ac.createdBy,ac.lastUpdatedBy FROM AccessCards ac ORDER BY ac.acId"),

})
public class AccessCards {
	
	@Id
	@SequenceGenerator(name = "accesscard_seq", sequenceName = "ACCESSCARD_SEQ")
	@GeneratedValue(generator = "accesscard_seq")
	@Column(name = "AC_ID")
	private int acId;
	
	/*@Column(name = "PERSON_ID")
	private int personId;*/
	
	@Size(min = 1, message = "Access Type is not selected")
	@Column(name = "AC_TYPE")
	private String acType;
	
	@NotEmpty(message = "Access number cannot be blank")
	@Column(name = "AC_NO")
	private String acNo;
	
	/*@Column(name = "AC_START_DATE")
	private Date acStartDate;
	
	@Column(name = "AC_END_DATE")
	private Date acEndDate;*/
	
	@Size(min = 1, message = "Status is not selected")
	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	 

	
	
	
	public AccessCards() {
		super();
	}
	
	

	public AccessCards(int acId, int personId, String acType, String acNo,
			Date acStartDate, Date acEndDate, String status, String createdBy,
			String lastUpdatedBy, Timestamp lastUpdatedDt) {
		super();
		this.acId = acId;
	//	this.personId = personId;
		this.acType = acType;
		this.acNo = acNo;
	//	this.acStartDate = acStartDate;
	//	this.acEndDate = acEndDate;
		this.status = status;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		//this.lastUpdatedDt = lastUpdatedDt;
	}



	public int getAcId() {
		return acId;
	}

	public void setAcId(int acId) {
		this.acId = acId;
	}

	/*public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}*/

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
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
