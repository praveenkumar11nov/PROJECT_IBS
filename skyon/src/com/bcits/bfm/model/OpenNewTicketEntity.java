package com.bcits.bfm.model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="HELP_DESK_TICKET")  
@NamedQueries({
	@NamedQuery(name = "OpenNewTicketEntity.findAll", query = "SELECT ont.ticketId,ont.ticketNumber,ont.dept_Id,ont.ipAddress,ont.topicId,ont.issueSubject,ont.issueDetails,ont.priorityLevel,ont.lastResonse,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketAssignedDate,ont.ticketUpdateDate,ont.ticketStatus,ont.deptAcceptanceStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,do.dept_Name,hto.topicName,ont.deptAcceptedDate,ont.typeOfTicket,ont.personId,ont.propertyId FROM OpenNewTicketEntity ont INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE EXTRACT(month from ont.ticketCreatedDate)=(select EXTRACT(month from ont.ticketCreatedDate) from OpenNewTicketEntity ont where ont.ticketCreatedDate = (select MAX(ont.ticketCreatedDate) from OpenNewTicketEntity ont)) AND EXTRACT(year from ont.ticketCreatedDate)=(select EXTRACT(year from ont.ticketCreatedDate) from OpenNewTicketEntity ont where ont.ticketCreatedDate = (select MAX(ont.ticketCreatedDate) from OpenNewTicketEntity ont)) ORDER BY ont.ticketId DESC"),
	@NamedQuery(name = "OpenNewTicketEntity.findAllForPerson", query = "SELECT ont.ticketId,ont.ticketNumber,ont.propertyId,ont.personId,ont.dept_Id,ont.ipAddress,ont.topicId,ont.issueSubject,ont.issueDetails,ont.priorityLevel,ont.lastResonse,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketAssignedDate,ont.ticketUpdateDate,ont.ticketStatus,ont.deptAcceptanceStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,po.property_No,po.blockId,b.blockName,do.dept_Id,do.dept_Name,p.firstName,p.lastName,hto.topicName,ont.deptAcceptedDate,ont.typeOfTicket FROM OpenNewTicketEntity ont,Property po,Blocks b,Person p INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.personId=p.personId AND ont.propertyId=po.propertyId AND po.blockId=b.blockId AND ont.personId=:personId ORDER BY ont.ticketId DESC"),
	@NamedQuery(name = "OpenNewTicketEntity.findAllTicketsBasedOnDept", query = "SELECT ont.ticketId,ont.ticketNumber,ont.dept_Id,ont.ipAddress,ont.topicId,ont.issueSubject,ont.issueDetails,ont.priorityLevel,ont.lastResonse,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketAssignedDate,ont.ticketUpdateDate,ont.ticketStatus,ont.deptAcceptanceStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,do.dept_Name,hto.topicName,ont.deptAcceptedDate,ont.typeOfTicket,ont.personId,ont.propertyId FROM OpenNewTicketEntity ont INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.dept_Id =:dept_Id AND EXTRACT(month from ont.ticketCreatedDate)=(select EXTRACT(month from ont.ticketCreatedDate) from OpenNewTicketEntity ont where ont.ticketCreatedDate = (select MAX(ont.ticketCreatedDate) from OpenNewTicketEntity ont)) AND EXTRACT(year from ont.ticketCreatedDate)=(select EXTRACT(year from ont.ticketCreatedDate) from OpenNewTicketEntity ont where ont.ticketCreatedDate = (select MAX(ont.ticketCreatedDate) from OpenNewTicketEntity ont)) ORDER BY ont.ticketId DESC"),
	@NamedQuery(name="OpenNewTicketEntity.ticketStatusUpdateFromInnerGrid",query="UPDATE OpenNewTicketEntity a SET a.ticketStatus = :ticketStatus WHERE a.ticketId = :ticketId"),
	@NamedQuery(name="OpenNewTicketEntity.getPersons",query="select p from Person p WHERE p.personId =:personId"),
	@NamedQuery(name="OpenNewTicketEntity.getDepartements",query="select d.dept_Name from Department d WHERE d.dept_Id =:dept_Id"),
	@NamedQuery(name = "OpenNewTicketEntity.setDepartementAcceptanceStatus", query = "UPDATE OpenNewTicketEntity ope SET ope.deptAcceptanceStatus = :deptAcceptanceStatus WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name="OpenNewTicketEntity.findAllPropertyPersonOwnerList",query="select DISTINCT pt.propertyId,p.firstName,p.lastName,p.personId,p.personType,p.personStyle from OwnerProperty op INNER JOIN op.property pt INNER JOIN op.owner o INNER JOIN o.person p WHERE p.personStatus = 'Active'"),
	@NamedQuery(name="OpenNewTicketEntity.findAllPropertyPersonTenantList",query="select tp.propertyId,p.firstName,p.lastName,p.personId,p.personType,p.personStyle from TenantProperty tp INNER JOIN tp.tenantId t INNER JOIN t.person p WHERE p.personStatus = 'Active'"),
	@NamedQuery(name="OpenNewTicketEntity.departementTicketAcceptanceStatus",query="UPDATE OpenNewTicketEntity el SET el.deptAcceptanceStatus = :deptAcceptanceStatus WHERE el.ticketId = :ticketId"),
	@NamedQuery(name = "OpenNewTicketEntity.ticketStatusUpdateAsClose", query = "UPDATE OpenNewTicketEntity ope SET ope.ticketStatus = :ticketStatus WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "OpenNewTicketEntity.ticketUpdateClosedDate", query = "UPDATE OpenNewTicketEntity ope SET ope.ticketClosedDate = :ticketClosedDate WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "OpenNewTicketEntity.updateAssignTicketDate", query = "UPDATE OpenNewTicketEntity ope SET ope.ticketAssignedDate = :ticketAssignedDate WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "OpenNewTicketEntity.lastResponseDateUpdate", query = "UPDATE OpenNewTicketEntity ope SET ope.lastResonse = :lastResonse WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "OpenNewTicketEntity.findAllTicketsBasedOnResponseTime", query = "SELECT ont.ticketId,ont.topicId,ont.ticketNumber,ont.ticketCreatedDate,hto.level1SLA,hto.level2SLA,hto.level3SLA,hto.level1User,hto.level2User,hto.level3User,uo1.urLoginName,uo2.urLoginName,uo3.urLoginName,hto.level1NotifiedUsers,hto.level2NotifiedUsers,hto.level3NotifiedUsers FROM OpenNewTicketEntity ont INNER JOIN ont.helpTopicObj hto INNER JOIN hto.usersObj1 uo1 INNER JOIN hto.usersObj2 uo2 INNER JOIN hto.usersObj3 uo3 WHERE ont.lastResonse = null ORDER BY ont.ticketId ASC"),
	@NamedQuery(name = "OpenNewTicketEntity.deptAcceptedDate", query = "UPDATE OpenNewTicketEntity ope SET ope.deptAcceptedDate = :deptAcceptedDate WHERE ope.ticketId = :ticketId"),
	@NamedQuery(name = "OpenNewTicketEntity.findAllTicketsBasedOnUrId", query = "SELECT ont.ticketId,ont.ticketNumber,ont.dept_Id,ont.ipAddress,ont.topicId,ont.issueSubject,ont.issueDetails,ont.priorityLevel,ont.lastResonse,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketAssignedDate,ont.ticketUpdateDate,ont.ticketStatus,ont.deptAcceptanceStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,do.dept_Name,hto.topicName,ont.deptAcceptedDate,ont.typeOfTicket,ont.personId,ont.propertyId FROM OpenNewTicketEntity ont INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.ticketId IN (SELECT ta.ticketId FROM TicketAssignEntity ta WHERE ta.urId=:urId) ORDER BY ont.ticketId DESC"),
	@NamedQuery(name="OpenNewTicketEntity.getRoleName",query="SELECT r.rlName FROM Role r WHERE r.rlId IN (SELECT r.rlId FROM Role r, UserRole ur WHERE r.rlId = ur.uro_Rl_Id AND ur.uro_Ur_Id = :urId) AND r.rlName IN (SELECT ro.rlName FROM HelpDeskSettingsEntity hse,Role ro WHERE ro.rlId= hse.rlId)"),
	@NamedQuery(name = "OpenNewTicketEntity.findPersonForFilters", query = "select DISTINCT(p.personId), p.firstName, p.lastName, p.personType, p.personStyle from OpenNewTicketEntity ont,Person p WHERE p.personId=ont.personId"),
	@NamedQuery(name = "OpenNewTicketEntity.getPersonsList", query = "SELECT distinct p.personId FROM HelpDeskSettingsEntity hse,Users u INNER JOIN u.person p INNER JOIN u.department d INNER JOIN u.userRoles ur WHERE u.deptId=:deptId AND hse.rlId=ur.uro_Rl_Id"),
	@NamedQuery(name = "OpenNewTicketEntity.getAllBlockNames", query = "SELECT DISTINCT(b.blockName) FROM OpenNewTicketEntity ont, Property pt, Blocks b WHERE ont.propertyId = pt.propertyId AND pt.blockId = b.blockId"),
	@NamedQuery(name = "OpenNewTicketEntity.getTowerNames", query = "SELECT b.blockId,b.blockName FROM Blocks b"),
	@NamedQuery(name = "OpenNewTicketEntity.readPropertyNames", query = "SELECT p.blockId,p.propertyId,p.property_No,b.blockName FROM Property p INNER JOIN p.blocks b ORDER BY p.property_No"),
	@NamedQuery(name = "OpenNewTicketEntity.getUrLoginNameBasedOnMailId", query = "SELECT u.urLoginName FROM Users u INNER JOIN u.person p INNER JOIN p.contacts c WHERE c.contactPrimary='Yes' AND c.contactType='Email' AND c.contactContent=:email"),
	@NamedQuery(name = "OpenNewTicketEntity.getHelpDeskDetails", query = "Select count(*),(select count(*) from OpenNewTicketEntity ot where ot.ticketStatus='Open'),(select count(*) from OpenNewTicketEntity t where t.ticketStatus='Re-Open') from OpenNewTicketEntity o group by 1"),
	@NamedQuery(name = "OpenNewTicketEntity.getTicketReports", query = "SELECT ont.ticketNumber,ont.issueSubject,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketUpdateDate,ont.ticketStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,po.property_No,do.dept_Name,hto.topicName,hto.normalSLA,ont.ticketId FROM OpenNewTicketEntity ont,Property po,Blocks b,Person p INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.personId=p.personId AND ont.propertyId=po.propertyId AND po.blockId=b.blockId ORDER BY ont.ticketId DESC"),
    @NamedQuery(name = "HelpDeskEntity.findassigne",query="SELECT ps.createdBy FROM  TicketPostReplyEntity ps WHERE ps.openNewTicketEntity.ticketId=:ticketId"),
    @NamedQuery(name = "OpenNewTicketEntity.getTicketReportsBasedOnStatus", query = "SELECT ont.ticketNumber,ont.issueSubject,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketUpdateDate,ont.ticketStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,po.property_No,do.dept_Name,hto.topicName,hto.normalSLA,ont.ticketId FROM OpenNewTicketEntity ont,Property po,Blocks b,Person p INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.personId=p.personId AND ont.propertyId=po.propertyId AND po.blockId=b.blockId AND ont.ticketStatus=:status ORDER BY ont.ticketId DESC"),
    @NamedQuery(name = "HelpDeskEntity.findassignedNotes",query="SELECT ps.createdBy,ps.response FROM  TicketPostReplyEntity ps WHERE ps.openNewTicketEntity.ticketId=:ticketId"),
    @NamedQuery(name = "OpenNewTicketEntity.getTicketReportsBasedOnBehalf", query = "SELECT ont.ticketNumber,ont.issueSubject,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketUpdateDate,ont.ticketStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,po.property_No,do.dept_Name,hto.topicName,hto.normalSLA,ont.ticketId,p.firstName,p.lastName FROM OpenNewTicketEntity ont,Property po,Blocks b,Person p INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.personId=p.personId AND ont.propertyId=po.propertyId AND po.blockId=b.blockId AND ont.ticketStatus=:status AND ont.createdBy NOT LIKE '%.com' AND ont.createdBy NOT LIKE '%.in' ORDER BY ont.ticketId DESC"),
    @NamedQuery(name = "OpenNewTicketEntity.getTicketReportPersonal", query = "SELECT ont.ticketNumber,ont.issueSubject,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketUpdateDate,ont.ticketStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,po.property_No,do.dept_Name,hto.topicName,hto.normalSLA,ont.ticketId,p.firstName,p.lastName FROM OpenNewTicketEntity ont,Property po,Blocks b,Person p INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE ont.personId=p.personId AND ont.propertyId=po.propertyId AND po.blockId=b.blockId AND ont.ticketStatus=:status AND ont.createdBy  LIKE '%.com' OR ont.createdBy  LIKE '%.in' ORDER BY ont.ticketId DESC"),
    @NamedQuery(name = "OpenNewTicketEntity.findTicketSummary", query = "SELECT DISTINCT(ot.topicId),hto.topicName from OpenNewTicketEntity ot INNER JOIN ot.helpTopicObj hto"),
    @NamedQuery(name = "OpenNewTicketEntity.getCountForOpenTickets", query = "SELECT COUNT(*) from OpenNewTicketEntity ot WHERE ot.ticketStatus= :status AND ot.topicId=:topicId ORDER BY ot.ticketId DESC"),
    @NamedQuery(name = "OpenNewTicketEntity.getTopicNameBasedOnTopicId", query = "SELECT ht.topicName FROM HelpTopicEntity ht WHERE ht.topicId=:topicId"),
    @NamedQuery(name = "OpenNewTicketEntity.getPersonNameBasedOnPersonId",query="select p.firstName,p.lastName FROM Person p WHERE p.personId =:personId"),
    @NamedQuery(name = "OpenNewTicketEntity.getUserIdBasedOnPersonId", query = "SELECT DISTINCT ur.urId FROM Users ur WHERE ur.personId=:personId"),
    @NamedQuery(name = "OpenNewTicketEntity.getPropertyDataBasedOnPropertyId", query = "SELECT pt.property_No,pt.blockId,b.blockName FROM Property pt INNER JOIN pt.blocks b WHERE pt.propertyId=:propertyId"),
    @NamedQuery(name = "OpenNewTicketEntity.getAllSearchByMonth", query ="SELECT ont.ticketId,ont.ticketNumber,ont.dept_Id,ont.ipAddress,ont.topicId,ont.issueSubject,ont.issueDetails,ont.priorityLevel,ont.lastResonse,ont.ticketCreatedDate,ont.ticketClosedDate,ont.ticketReopenDate,ont.ticketAssignedDate,ont.ticketUpdateDate,ont.ticketStatus,ont.deptAcceptanceStatus,ont.createdBy,ont.lastUpdatedBy,ont.lastUpdatedDT,do.dept_Name,hto.topicName,ont.deptAcceptedDate,ont.typeOfTicket,ont.personId,ont.propertyId FROM OpenNewTicketEntity ont INNER JOIN ont.departmentObj do INNER JOIN ont.helpTopicObj hto WHERE TRUNC(ont.ticketCreatedDate) BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD') AND TO_DATE(:toDate,'YYYY-MM-DD') ORDER BY ont.ticketId DESC"),





    @NamedQuery(name = "OpenNewTicketEntity.getPersonNameBasedOnPersonId1",query="select p.firstName,p.lastName FROM Person p WHERE p.personId =:personId"),
    @NamedQuery(name = "OpenNewTicketEntity.getPropertyDataBasedOnPropertyId1", query = "SELECT pt.property_No,pt.blockId,b.blockName FROM Property pt INNER JOIN pt.blocks b WHERE pt.propertyId=:propertyId"),


})
public class OpenNewTicketEntity {

	@Id
	@Column(name="TICKET_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@SequenceGenerator(name = "helpDeskTicket_seq", sequenceName = "HELP_DESK_TICKET_SEQ") 
	@GeneratedValue(generator = "helpDeskTicket_seq")
	private int ticketId;
	
	@Column(name="TICKET_NUMBER")
	@NotEmpty(message="Ticket number is required")
	private String ticketNumber;
	
	@Column(name="PROPERTY_ID")
	private int propertyId;
	
	/*@OneToOne	 
	@JoinColumn(name = "PROPERTY_ID", insertable = false, updatable = false, nullable = false)
    private Property propertyObj;*/
	
	@Column(name="PERSON_ID")
	private int personId;
	
	/*@OneToOne
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, nullable = false)
    private Person personObj;*/
	
	@Column(name="DEPT_ID", unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message = "Department is not Selected")
	private int dept_Id;
	
	@OneToOne	 
	@JoinColumn(name = "DEPT_ID", insertable = false, updatable = false, nullable = false)
    private Department departmentObj;
		
	@Column(name="IP_ADDRESS")
	private String ipAddress;
	
	@Column(name="TOPIC_ID",unique=true, nullable=false, precision=11, scale=0)
	@NotNull(message = "Topic is not selected")
	private int topicId;
	
	@OneToOne	 
	@JoinColumn(name = "TOPIC_ID", insertable = false, updatable = false, nullable = false)
	private HelpTopicEntity helpTopicObj;
	
	@Column(name="ISSUE_SUBJECT")
	@NotEmpty(message="Issue subject is required")
	private  String issueSubject;
	
	@Column(name="ISSUE_DETAILS")
	@NotEmpty(message="Issue subject is required")
	@Size(min = 1, max = 4000, message = "Issue details content can have maximum {max} letters")
	private  String issueDetails;
	
	@Column(name="TICKET_PRIORITY")
	@NotEmpty(message="Priorty level is not selected")
	private  String priorityLevel;
	
	@Column(name="LAST_RESPONSE")
	private Timestamp lastResonse;
	
	@Column(name="TICKET_CREATED_DT")
	private Timestamp ticketCreatedDate;
	
	@Column(name="TICKET_CLOSED_DT")
	private Timestamp ticketClosedDate;
	
	@Column(name="TICKET_REOPENED_DT")
	private Timestamp ticketReopenDate;
	
	@Column(name="TICKET_ASSIGNED_DT")
	private Timestamp ticketAssignedDate;
	
	@Column(name="LAST_EDITED_TICKET")
	private Timestamp ticketUpdateDate;
		
	@Column(name="STATUS")
	@NotEmpty(message="Ticket status is required")
	private  String ticketStatus;
	
	@Column(name="DEPT_ACCEPTANCE_STATUS")
	private  String deptAcceptanceStatus;
	
	@Column(name="DEPT_ACCEPTED_DT")
	private  Timestamp deptAcceptedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="LAST_UPDATED_DT")
	private Timestamp lastUpdatedDT = new Timestamp(new java.util.Date().getTime());
	
	@Column(name="TICKET_TYPE")
	private String typeOfTicket;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public HelpTopicEntity getHelpTopicObj() {
		return helpTopicObj;
	}

	public void setHelpTopicObj(HelpTopicEntity helpTopicObj) {
		this.helpTopicObj = helpTopicObj;
	}

	public String getIssueSubject() {
		return issueSubject;
	}

	public void setIssueSubject(String issueSubject) {
		this.issueSubject = issueSubject;
	}

	public String getIssueDetails() {
		return issueDetails;
	}

	public void setIssueDetails(String issueDetails) {
		this.issueDetails = issueDetails;
	}

	public String getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public Timestamp getLastResonse() {
		return lastResonse;
	}

	public void setLastResonse(Timestamp lastResonse) {
		this.lastResonse = lastResonse;
	}

	public Timestamp getTicketCreatedDate() {
		return ticketCreatedDate;
	}

	public void setTicketCreatedDate(Timestamp ticketCreatedDate) {
		this.ticketCreatedDate = ticketCreatedDate;
	}

	public Timestamp getTicketClosedDate() {
		return ticketClosedDate;
	}

	public void setTicketClosedDate(Timestamp ticketClosedDate) {
		this.ticketClosedDate = ticketClosedDate;
	}

	public Timestamp getTicketReopenDate() {
		return ticketReopenDate;
	}

	public void setTicketReopenDate(Timestamp ticketReopenDate) {
		this.ticketReopenDate = ticketReopenDate;
	}

	public Timestamp getTicketAssignedDate() {
		return ticketAssignedDate;
	}

	public void setTicketAssignedDate(Timestamp ticketAssignedDate) {
		this.ticketAssignedDate = ticketAssignedDate;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
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

	public Timestamp getTicketUpdateDate() {
		return ticketUpdateDate;
	}

	public void setTicketUpdateDate(Timestamp ticketUpdateDate) {
		this.ticketUpdateDate = ticketUpdateDate;
	}
		
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
		
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public int getDept_Id() {
		return dept_Id;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}
	
	public String getDeptAcceptanceStatus() {
		return deptAcceptanceStatus;
	}

	public void setDeptAcceptanceStatus(String deptAcceptanceStatus) {
		this.deptAcceptanceStatus = deptAcceptanceStatus;
	}

	public Timestamp getDeptAcceptedDate() {
		return deptAcceptedDate;
	}

	public void setDeptAcceptedDate(Timestamp deptAcceptedDate) {
		this.deptAcceptedDate = deptAcceptedDate;
	}

	public String getTypeOfTicket() {
		return typeOfTicket;
	}

	public void setTypeOfTicket(String typeOfTicket) {
		this.typeOfTicket = typeOfTicket;
	}

/*	@PrePersist
	 protected void onCreate() {
	//String mobstatus=req.getParameter("mobstatus");
		
		// if(mobstatus!=null)
		// {
		
	//	lastUpdatedBy = (String) SessionData.getUserDetails().get("personID");
		
		// }
		
	 lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
		//lastUpdatedBy = (String) SessionData.getUserDetails().get("personID");
	  createdBy = (String) SessionData.getUserDetails().get("userID");
	  ticketCreatedDate =  new Timestamp(new java.util.Date().getTime());
	  System.out.println(lastUpdatedBy);
	 }
	 
	 @PreUpdate
	 protected void onUpdate() {
	  lastUpdatedBy = (String) SessionData.getUserDetails().get("userID");
	 }*/
	
}
