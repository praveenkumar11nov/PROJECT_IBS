package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CLUB_HOUSE_BOOKING")

@NamedQueries({

	@NamedQuery(name="ClubHouseBooking.findAllData",query="select c from ClubHouseBooking c"),
	})
public class ClubHouseBooking{
	
	@Id
	@Column(name = "SB_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name="CLUB_HOUSE_BOOKING_MASTER",sequenceName="CLUB_HOUSE_BOOKING_MASTER_SEQ")
	@GeneratedValue(generator="CLUB_HOUSE_BOOKING_MASTER")
	private int sbId;
	
	@Column(name="SERVICE_ID")
	private int sId;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_ID", insertable = false, updatable = false)
	private ClubHouse clubHouse;
	
	
	@Column(name="SEERVICE_NAME")
	private String serviceName;
	
	@Column(name="SEERVICE_DESC")
	private String serviceDesc;
	
	@Column(name="SEERVICE_ACTION")
	private String serviceAction;

	@Column(name="STATUS")
	private String status;
	
	

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	
	public String getServiceAction() {
		return serviceAction;
	}

	public void setServiceAction(String serviceAction) {
		this.serviceAction = serviceAction;
	}

	public int getSbId() {
		return sbId;
	}

	public void setSbId(int sbId) {
		this.sbId = sbId;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClubHouse getClubHouse() {
		return clubHouse;
	}

	public void setClubHouse(ClubHouse clubHouse) {
		this.clubHouse = clubHouse;
	}
	

}
