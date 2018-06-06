package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="PHOTO_EVENT")
public class PhotoEvent 
{
	@Id
	@SequenceGenerator(name = "peSeq", sequenceName = "PHOTO_EVENT_SEQ")
	@GeneratedValue(generator = "peSeq")
	@Column(name="PE_ID")
	private int peId;
	
	@Column(name="EVENT_NAME")
	private String eventName;
	
	@Column(name="EVENT_DESC")	
	private String eventDesc;

	public int getPeId() {
		return peId;
	}

	public void setPeId(int peId) {
		this.peId = peId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	
	

}
