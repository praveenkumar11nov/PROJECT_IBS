package com.bcits.bfm.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "VENDOR_INCIDENTS")
@NamedQueries
({
	@NamedQuery(name="VendorIncidents.findAll",query="SELECT vi FROM VendorIncidents vi ORDER BY vi.vcSlaId DESC"),
	@NamedQuery(name="VendorIncidents.updateStatus",query="UPDATE VendorIncidents vi SET vi.slaStatus = :newStatus WHERE vi.vcSlaId = :id"),
	@NamedQuery(name="VendorIncidents.getSLAForSelectedContract",query="SELECT vc.vendorSla FROM VendorContracts vc WHERE vc.vcId = :vcId"),
	/*@NamedQuery(name = "VendorContracts.UpdateStatus",query="UPDATE VendorContracts vc SET  vc.status = :status WHERE vc.vcId = :vcId"),*/
	@NamedQuery(name="VendorIncidents.findAllList",query="SELECT vi.vcSlaId,vc.vcId,vc.contractName,vc.contractNo,vi.incidentDt,vi.incidentDescription,vi.expectedSla,vi.slaReached,vi.slaComments,vi.slaStatus,vi.slaAlertedDt FROM VendorIncidents vi INNER JOIN vi.vendorContracts vc ORDER BY vi.vcSlaId DESC"),

})
public class VendorIncidents implements java.io.Serializable
{	
	@Id
    @SequenceGenerator(name = "incidentsVendSeq", sequenceName = "INCIDENTS_VEND_SEQ")
	@GeneratedValue(generator = "incidentsVendSeq")
	@Column(name = "VC_SLA_ID", unique = true, nullable = false, precision = 11, scale = 0)
	private int vcSlaId;
	
	@Column(name="VC_ID",unique=true, nullable=false, precision=11, scale=0)
    private int vcId;
	
	/*@Transient
	private String vendorInvoiceDetails;*/
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VC_ID", insertable = false, updatable = false, nullable = false)
	private VendorContracts vendorContracts;
	
	@Column(name = "INCIDENT_DT")
	private Date incidentDt;
	
	@Column(name = "INCIDENT_DESCRIPTION", length = 45)
	private String incidentDescription;
	
	@Column(name = "EXPECTED_SLA", precision = 11, scale = 0)
	private int expectedSla;
	
	@Column(name = "SLA_REACHED", precision = 11, scale = 0)
	private int slaReached;
	
	@Column(name = "SLA_COMMENTS", length = 45)
	private String slaComments;
	
	@Column(name = "SLA_STATUS", length = 45)
	private String slaStatus;
	
	@Column(name = "SLA_ALERTED_DT")
	private Date slaAlertedDt;

	public VendorIncidents(){}

	public VendorIncidents(int vcSlaId, VendorContracts vendorContracts) 
	{
		this.vcSlaId = vcSlaId;
		this.vendorContracts = vendorContracts;
	}
	public VendorIncidents(int vcSlaId, VendorContracts vendorContracts,Date incidentDt, String incidentDescription, int expectedSla,int slaReached, String slaComments, String slaStatus,Date slaAlertedDt) 
	{
		this.vcSlaId = vcSlaId;
		this.vendorContracts = vendorContracts;
		this.incidentDt = incidentDt;
		this.incidentDescription = incidentDescription;
		this.expectedSla = expectedSla;
		this.slaReached = slaReached;
		this.slaComments = slaComments;
		this.slaStatus = slaStatus;
		this.slaAlertedDt = slaAlertedDt;
	}	
	public int getVcSlaId() 
	{
		return this.vcSlaId;
	}
	public void setVcSlaId(int vcSlaId) 
	{
		this.vcSlaId = vcSlaId;
	}		
	public int getVcId() 
	{
		return vcId;
	}
	public void setVcId(int vcId) 
	{
		this.vcId = vcId;
	}
	public VendorContracts getVendorContracts() 
	{
		return this.vendorContracts;
	}
	public void setVendorContracts(VendorContracts vendorContracts) 
	{
		this.vendorContracts = vendorContracts;
	}	
	public Date getIncidentDt() {
		return this.incidentDt;
	}
	public void setIncidentDt(Date incidentDt) {
		this.incidentDt = incidentDt;
	}	
	public String getIncidentDescription() 
	{
		return this.incidentDescription;
	}
	public void setIncidentDescription(String incidentDescription) 
	{
		this.incidentDescription = incidentDescription;
	}	
	public int getExpectedSla() 
	{
		return this.expectedSla;
	}
	public void setExpectedSla(int expectedSla) 
	{
		this.expectedSla = expectedSla;
	}	
	public int getSlaReached() 
	{
		return this.slaReached;
	}
	public void setSlaReached(int slaReached) 
	{
		this.slaReached = slaReached;
	}	
	public String getSlaComments() 
	{
		return this.slaComments;
	}
	public void setSlaComments(String slaComments) 
	{
		this.slaComments = slaComments;
	}	
	public String getSlaStatus() 
	{
		return this.slaStatus;
	}
	public void setSlaStatus(String slaStatus) 
	{
		this.slaStatus = slaStatus;
	}	
	public Date getSlaAlertedDt() 
	{
		return this.slaAlertedDt;
	}
	public void setSlaAlertedDt(Date slaAlertedDt) 
	{
		this.slaAlertedDt = slaAlertedDt;
	}
	/*public String getVendorInvoiceDetails() {
		return vendorInvoiceDetails;
	}

	public void setVendorInvoiceDetails(String vendorInvoiceDetails) {
		this.vendorInvoiceDetails = vendorInvoiceDetails;
	}*/
}