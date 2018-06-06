package com.bcits.bfm.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * MailRepository entity.
 * @author Shashidhar Bemalgi
 */

@SuppressWarnings("serial")
@Entity
@Table(name="MAIL_REPOSITORY")
@NamedQueries({
	
	@NamedQuery(name="MailRoom.findAllData",query=" SELECT m.mlrId,p.blocks.blockId,p.blocks.blockName,p.propertyId,p.property_No,p.propertyName,m.addressedTo, m.addressedFrom, m.mailboxDt,m.status,m.lastUpdatedDt,m.drGroupId,m.createdBy,m.lastUpdatedBy,m.mailRedirectedStatus,m.mailReturnedStatus,m.reasons,m.consignmentNo  FROM MailRoom m INNER JOIN m.propertyObj p ORDER BY m.mlrId DESC"),
	
	@NamedQuery(name="MailRoom.findData",query=" SELECT m.mlrId,p.blocks.blockId,p.blocks.blockName,p.propertyId,p.property_No,p.propertyName,m.addressedTo, m.addressedFrom, m.mailboxDt,m.status,m.lastUpdatedDt,m.drGroupId,m.createdBy,m.lastUpdatedBy,m.mailRedirectedStatus,m.mailReturnedStatus,m.reasons,m.consignmentNo,m.mailNotifiedDt,m.lastUpdatedDt,m.mailRedirectedDt  FROM MailRoom m INNER JOIN m.propertyObj p ORDER BY m.mlrId DESC"),
	
	
	@NamedQuery(name="MailRoom.findAll",query="SELECT m FROM MailRoom m ORDER BY m.mlrId DESC"),	
	@NamedQuery(name="MailRoom.updateStatus",query="UPDATE MailRoom m SET m.status = :newStatus WHERE m.mlrId = :id"),
	@NamedQuery(name="MailRoom.updateMailRoomDeliveryStatus",query="UPDATE MailRoom m SET m.status = :newStatus,m.mailNotifiedDt=:d1,m.mailRedirectedDt=:d2,m.mailReturnedDt=:d3,m.note=:deliveredBy,m.mailRedirectedStatus=:redirectedAddress,m.reasons=:reason,m.receivedBy=:receivedBy WHERE m.mlrId = :id"),
	@NamedQuery(name="Property.readPropertyNames",query="SELECT p FROM Property p ORDER BY p.property_No ASC"),
	@NamedQuery(name="Person.readPersonNames",query="SELECT p1 FROM Person p1"),	
	@NamedQuery(name="Property.getPropName",query="SELECT p.propertyName FROM Property p"),
	@NamedQuery(name="MailRoom.getAddressDetails",query="SELECT m.addressedFrom FROM MailRoom m WHERE m.propertyId=:id"),
	@NamedQuery(name="MailRoom.getAddressedFrom",query="SELECT m.addressedFrom FROM MailRoom m WHERE m.propertyId=:mid"),
	
	@NamedQuery(name="Person.getFirstAndLastName",
			query="SELECT p.title,p.firstName,p.lastName FROM OwnerProperty op,Owner o,Person p WHERE op.ownerId=o.ownerId AND o.personId=p.personId and op.propertyId=:pId"),
	
	@NamedQuery(name="CommunicationType.getCommunication",
	query="SELECT c.contactContent FROM OwnerProperty op,Owner o,Person p,Contact c WHERE op.ownerId=o.ownerId AND o.personId=p.personId "
			  + "and p.personId=c.personId and op.propertyId=:pId and c.contactType=:contactType"),
			  
	@NamedQuery(name="CommunicationName.getCommunication",
	query="SELECT c.contactType FROM OwnerProperty op,Owner o,Person p,Contact c WHERE op.ownerId=o.ownerId AND o.personId=p.personId "
	   	   + "and p.personId=c.personId and op.propertyId=:pId"),
	   	   
	   	@NamedQuery(name="MailRoom.getMaxCount",query="SELECT MAX(m.mlrId) as mlrId From MailRoom m"),   	
	   	
	   	@NamedQuery(name="MailRoom.updateConsignmentNumber",query="UPDATE MailRoom m SET m.consignmentNo = :consignmentNo WHERE m.mlrId = :id"),
	   	
	   	@NamedQuery(name="MailRoom.getSentMailDetails",query="SELECT m FROM MailRoom m WHERE m.status=:status"),
	
 })
public class MailRoom  implements java.io.Serializable 
{
	 @Id 
	 @SequenceGenerator(name = "mailroomseq", sequenceName = "MAIL_REPOSITORY_SEQ")
	 @GeneratedValue(generator = "mailroomseq")
	 
	 @Column(name="MLR_ID", unique=true, nullable=false, precision=11, scale=0)
	 private int mlrId;
	
	 @Min(value=1, message = "Property Cannot Be Empty")
	 @Column(name="PROPERTY_ID", unique=true, nullable=false, precision=11, scale=0)
     private int propertyId;
	 
	 @OneToOne	 
	 @JoinColumn(name = "PROPERTY_ID", insertable = false, updatable = false, nullable = false)
     private Property propertyObj;
	
	// @NotNull	 
	 //@Size(min=1,max=500,message="Address To Cannot be empty")
     @Column(name="ADDRESSED_TO", nullable=false, length=200)
     private String addressedTo;
     
	 //@Min(value=1, message = "Addressed From Cannot be empty")
	 @NotNull	 
	 @Size(min=1,max=500,message="Address From Cannot be empty")
     @Column(name="ADDRESSED_FROM", nullable=false, length=200)
     private String addressedFrom;
     
     @Column(name="DR_GROUP_ID", precision=11, scale=0)
     private int drGroupId;
     
     //@NotNull(message = "MailNotified Date Should Be Greater Than MailBoxDate")
     @Column(name="MAIL_NOTIFIED_DT", length=11)
     private Timestamp mailNotifiedDt;
     
     @Column(name="MAILBOX_DT", length=11)
     private Timestamp mailboxDt;
          
     @Column(name="MAIL_RETURNED_DT", nullable=false, length=11)
     private Timestamp mailReturnedDt;
     
     //@NotNull(message = "MailRedirectedDate Should Be Greater Than MailBoxDate")
     @Column(name="MAIL_REDIRECTED_DT", nullable=false, length=11)
     private Timestamp mailRedirectedDt;
     
     @Column(name="STATUS", nullable=false, length=20)
     private String status;
     
     @Column(name="CREATED_BY", precision=11, scale=0)
     private String createdBy;
     
     @Column(name="LAST_UPDATED_BY", precision=11, scale=0)
     private String lastUpdatedBy;

	@Column(name="NOTE", precision=11, scale=0)
     private String note;
	
	@Column(name = "LAST_UPDATED_DT", length = 11)
	private Timestamp lastUpdatedDt;
	
	@Column(name="MAIL_REDIRECTED_ADDRESS", precision=11, scale=0)
    private String mailRedirectedStatus;
	
	@Column(name="MAIL_RETURNED", precision=11, scale=0)
    private String mailReturnedStatus;
	
	@Column(name="REASONS", precision=11, scale=0)
    private String reasons;
	
	@Column(name="CONSIGNMENT_NUMBER", precision=11, scale=0)
    private String consignmentNo;	
	
	@Column(name="RECEIVED_BY", precision=11, scale=0)
    private String receivedBy;
	
     
    /** default constructor */
    public MailRoom(){}
	
    public MailRoom(int mlrId,int propertyId,String addressedTo, String addressedFrom, int drGroupId,Date mailboxDt,String status, String createdBy, String lastUpdatedBy,String mailRedirectedStatus,String mailReturnedStatus,String reasons,String consignmentNo,String receivedBy) 
    {
        this.mlrId = mlrId;       
        this.propertyId = propertyId;
        this.addressedTo = addressedTo;
        this.addressedFrom = addressedFrom;
        this.drGroupId = drGroupId;
        this.status = status;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.mailRedirectedStatus = mailRedirectedStatus;
        this.reasons = reasons;
        this.consignmentNo = consignmentNo;
        this.receivedBy = receivedBy;
    }
    
    
    /** full constructor */
    public MailRoom(int mlrId,int propertyId,String addressedTo, String addressedFrom, int drGroupId, Timestamp mailNotifiedDt, Timestamp mailboxDt, Timestamp mailReturnedDt, Timestamp mailRedirectedDt, String status, String createdBy, String lastUpdatedBy, Timestamp lastUpdatedDt,String note,String mailRedirectedStatus,String mailReturnedStatus,String reasons,String consignmentNo,String receivedBy) 
    {
        this.mlrId = mlrId;       
        this.propertyId = propertyId;
        this.addressedTo = addressedTo;
        this.addressedFrom = addressedFrom;
        this.drGroupId = drGroupId;
        this.mailNotifiedDt = mailNotifiedDt;
        this.mailboxDt = mailboxDt;
        this.mailReturnedDt = mailReturnedDt;
        this.mailRedirectedDt = mailRedirectedDt;
        this.status = status;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDt = lastUpdatedDt;
        this.note=note;
        this.mailRedirectedStatus = mailRedirectedStatus;
        this.reasons = reasons;
        this.consignmentNo = consignmentNo;
        this.receivedBy = receivedBy;
    }
    
    public String getReceivedBy() {
		return receivedBy;
	}
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	public int getMlrId() {
		return mlrId;
	}
	public void setMlrId(int mlrId) {
		this.mlrId = mlrId;
	}

	public int getPropertyId() {
        return this.propertyId;
    }    
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
    public String getAddressedTo() {
        return this.addressedTo;
    }   
    public void setAddressedTo(String addressedTo) {
        this.addressedTo = addressedTo;
    }  
    public String getAddressedFrom() {
        return this.addressedFrom;
    }    
    public void setAddressedFrom(String addressedFrom) {
        this.addressedFrom = addressedFrom;
    } 
    public int getDrGroupId() {
        return this.drGroupId;
    }
    public void setDrGroupId(int drGroupId) {
        this.drGroupId = drGroupId;
    }
    public Timestamp getMailNotifiedDt() {
        return this.mailNotifiedDt;
    }
    public void setMailNotifiedDt(Timestamp mailNotifiedDt) {
        this.mailNotifiedDt = mailNotifiedDt;
    }
    public Timestamp getMailboxDt() {
        return this.mailboxDt;
    }
    public void setMailboxDt(Timestamp mailboxDt) {
        this.mailboxDt = mailboxDt;
    }
    public Timestamp getMailReturnedDt() {
        return this.mailReturnedDt;
    }
    public void setMailReturnedDt(Timestamp mailReturnedDt) {
        this.mailReturnedDt = mailReturnedDt;
    }
    public Timestamp getMailRedirectedDt() {
        return this.mailRedirectedDt;
    }
    public void setMailRedirectedDt(Timestamp mailRedirectedDt) {
        this.mailRedirectedDt = mailRedirectedDt;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public Timestamp getLastUpdatedDt() {
        return this.lastUpdatedDt;
    }
    public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
    }
	public Property getPropertyObj() {
		return propertyObj;
	}	
	public void setPropertyObj(Property propertyObj) {
		this.propertyObj = propertyObj;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMailRedirectedStatus() {
		return mailRedirectedStatus;
	}
	public void setMailRedirectedStatus(String mailRedirectedStatus) {
		this.mailRedirectedStatus = mailRedirectedStatus;
	}
	public String getMailReturnedStatus() {
		return mailReturnedStatus;
	}
	public void setMailReturnedStatus(String mailReturnedStatus) {
		this.mailReturnedStatus = mailReturnedStatus;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public String getConsignmentNo(){
		return consignmentNo;
	}
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}    
}