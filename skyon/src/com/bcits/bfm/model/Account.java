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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bcits.bfm.util.SessionData;

import net.sf.oval.constraint.MatchPattern;

@Entity
@NamedQueries({
	@NamedQuery(name="Account.findAllPersonAccounts",query="SELECT a.accountId, a.personId, a.accountNo, a.accountType, "
			+ "a.accountStatus, p.firstName, p.lastName, p.personType, p.personStyle FROM Account a INNER JOIN a.person p ORDER BY a.accountId DESC"),
	@NamedQuery(name="Account.findAllAccounts",query="SELECT DISTINCT a FROM Account a WHERE a.personId = :personId ORDER BY a.accountId DESC"),
	@NamedQuery(name="Account.setAccountStatus",query="UPDATE Account a SET a.accountStatus = :accountStatus WHERE a.personId = :personId"),
	@NamedQuery(name="Account.getAccountNumber",query="SELECT acc.accountNo FROM Account acc WHERE acc.personId= :personId AND acc.propertyId=:propertyId"),
	@NamedQuery(name="Account.updateAccountStatusFromInnerGrid",query="UPDATE Account a SET a.accountStatus = :accountStatus WHERE a.accountId = :accountId"),
	@NamedQuery(name="Account.testUniqueAccountMeter",query="SELECT a FROM Account a WHERE a.personId = :personId AND a.propertyId=:propertyId"),
	@NamedQuery(name="Account.testUniqueAccount",query="SELECT a FROM Account a WHERE a.accountNo = :accountNo AND a.propertyId=:propertyId"),
	//@NamedQuery(name="Account.testUniqueAccount",query="SELECT a FROM Account a WHERE a.accountNo = :accountNo"),

	@NamedQuery(name = "Account.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from Account a INNER JOIN a.person p"),
	@NamedQuery(name="Account.getAccountIdByNo",query="SELECT a.accountId FROM Account a WHERE a.accountNo = :accountNo"),
	//@NamedQuery(name="Account.getPersonDetailsByAccountID",query="SELECT p.firstName, p.lastName, a.accountNo, pr.property_no FROM Property pr, Account a INNER JOIN a.person p WHERE a.personid=p.personId and a.propertyId=pr.propertyId and a.accountId=:accountId")
	@NamedQuery(name="Account.getAccountNumberBasedOnPropertyId",query="SELECT a.accountId From Account a WHERE a.propertyId=:propertyId and a.accountStatus='Active'"),
	
	@NamedQuery(name="Account.findUniqueAccountNo",query="SELECT a FROM Account a WHERE a.accountNo = :accountNo AND a.propertyId=:propertyId"),


})

@Table(name = "ACCOUNT")
public class Account
{
	private int accountId;
	private int personId;
	private String accountNo;
	private String accountType;
	private String accountStatus;
	private String createdBy;
	private String lastUpdatedBy;
	@Access(AccessType.FIELD)
	@Column(name = "LAST_UPDATED_DT")
	private Timestamp lastUpdatedDt;
	
	private Person person;
	
	private String personType;
	private String personStyle;
	private String personName;
	
	@Column(name="PROPERTYID", unique=true, nullable=false, precision=11, scale=0)
	private int propertyId; 
	
	private String allAccountNos;
	private int noOfAccounts;
	private String accountStatusAllCheck;

	@Id
	@SequenceGenerator(name="ACCOUNT_SKYON_SEQ",sequenceName="ACCOUNT_SKYON_SEQ")
	@GeneratedValue(generator="ACCOUNT_SKYON_SEQ")
	@Column(name = "ACCOUNT_ID")
	public int getAccountId()
	{
		return accountId;
	}

	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}

	@Column(name = "PERSON_ID")
	@net.sf.oval.constraint.Min(value = 1, message = "Min.Account.personId")
	@net.sf.oval.constraint.NotNull(message = "NotNull.Account.personId")
	public int getPersonId()
	{
		return personId;
	}

	public void setPersonId(int personId)
	{
		this.personId = personId;
	}

	@net.sf.oval.constraint.Size(min = 1, max = 50, message = "Size.Account.accountNo")
	@MatchPattern( pattern = "^[a-zA-Z0-9]{1,50}$", message = "MatchPattern.Account.accountNo")
	@net.sf.oval.constraint.NotNull(message = "NotNull.Account.accountNo")
	@Column(name = "ACCOUNT_NO")
	public String getAccountNo()
	{
		return accountNo;
	}

	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}

	@net.sf.oval.constraint.Size(min = 2, message = "Size.Account.accountType")
	@net.sf.oval.constraint.NotNull(message = "NotNull.Account.accountType")
	@Column(name = "ACCOUNT_TYPE")
	public String getAccountType()
	{
		return accountType;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	@net.sf.oval.constraint.Size(min=1, message = "Size.Account.accountStatus")
	@MatchPattern( pattern = "Active|Inactive", message = "MatchPattern.Account.accountStatus")
	@Column(name = "STATUS")
	public String getAccountStatus()
	{
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus)
	{
		this.accountStatus = accountStatus;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATED_DT")
	public Timestamp getLastUpdatedDt()
	{
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt)
	{
		this.lastUpdatedDt = lastUpdatedDt;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
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

	@Transient
	public String getPersonType()
	{
		return personType;
	}

	public void setPersonType(String personType)
	{
		this.personType = personType;
	}

	@Transient
	public String getPersonStyle()
	{
		return personStyle;
	}

	public void setPersonStyle(String personStyle)
	{
		this.personStyle = personStyle;
	}
	
	@Transient
	public String getPersonName()
	{
		return personName;
	}
	
	public void setPersonName(String personName)
	{
		this.personName = personName;
	}

	@Transient
	public String getAllAccountNos()
	{
		return allAccountNos;
	}

	public void setAllAccountNos(String allAccountNos)
	{
		this.allAccountNos = allAccountNos;
	}

	@Transient
	public int getNoOfAccounts()
	{
		return noOfAccounts;
	}

	public void setNoOfAccounts(int noOfAccounts)
	{
		this.noOfAccounts = noOfAccounts;
	}

	@Transient
	public String getAccountStatusAllCheck()
	{
		return accountStatusAllCheck;
	}

	public void setAccountStatusAllCheck(String accountStatusAllCheck)
	{
		this.accountStatusAllCheck = accountStatusAllCheck;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	
}
