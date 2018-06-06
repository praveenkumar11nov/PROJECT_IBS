package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CLUB_HOUSE_MASTER")

@NamedQueries({
	@NamedQuery(name="ClubHouse.findAllService",query="select c from ClubHouse c WHERE c.sId=:sId"),
	@NamedQuery(name="ClubHouse.findAllData",query="select c from ClubHouse c"),
	@NamedQuery(name = "ClubHouse.findAllServiceNames", query = "SELECT s.sId,s.serviceName FROM ClubHouse s  "),
	@NamedQuery(name = "ClubHouse.findServiceName", query = "SELECT s.serviceName FROM ClubHouse s WHERE s.sId=:sId")
	})
public class ClubHouse {
	
	@Id
	@Column(name = "SERVICE_ID", unique = true, nullable = false, precision = 11, scale = 0)
	@SequenceGenerator(name="CLUB_HOUSE_MASTER",sequenceName="CLUB_HOUSE_MASTER_SEQ")
	@GeneratedValue(generator="CLUB_HOUSE_MASTER")
	private int sId;
	
	@Column(name="SEERVICE_NAME")
	private String serviceName;
	
	@Column(name="SEERVICE_DESC")
	private String serviceDesc;

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

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
	

}
