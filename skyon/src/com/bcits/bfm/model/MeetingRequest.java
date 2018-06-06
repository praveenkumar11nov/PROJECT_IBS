package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MEETING_REQUEST")
@NamedQueries({
	@NamedQuery(name="MeetingRequest.findAll",query="Select meeting FROM MeetingRequest meeting ORDER BY meeting.meeting_id DESC"),
	@NamedQuery(name="MeetingRequest.getAllRequestByCurrentDate",query="SELECT meeting FROM MeetingRequest meeting WHERE meeting.startTime >= TO_DATE(:decrDate,'YYYY-MM-DD') and meeting.startTime < TO_DATE(:incDate,'YYYY-MM-DD')  ORDER BY meeting.meeting_id")
})
public class MeetingRequest {
	
	@Id
	@SequenceGenerator(name="meeting_seq",sequenceName="MEETING_SEQ")
	@GeneratedValue(generator="meeting_seq")
	@Column(name="MEETING_ID")
	private int meeting_id;
	
	@Column(name="BLOCK_ID")
	private String blockId;
	
	@Column(name="PROPERTY_ID")
	private String propertyId;
	
	@Column(name="MEETING_MESSAGE")
	private String MeetingMessage;
	
	@Column(name="MEETING_SUBJECT")
	private String meetingSubject;
	
	/*@Column(name="MEETING_STATUS")
	private String meetingStatus;*/
	
	@Column(name="START_TIME")
	private Timestamp startTime;
	
	@Column(name="END_TIME")
	private Timestamp endTime;
	
	@Column(name="LOCATION")
	private String location;
	
	
	
	@Column(name="CREATED_BY")
	private String createdBy;

	public int getMeeting_id() {
		return meeting_id;
	}

	public void setMeeting_id(int meeting_id) {
		this.meeting_id = meeting_id;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getMeetingMessage() {
		return MeetingMessage;
	}

	public void setMeetingMessage(String meetingMessage) {
		MeetingMessage = meetingMessage;
	}

	public String getMeetingSubject() {
		return meetingSubject;
	}

	public void setMeetingSubject(String meetingSubject) {
		this.meetingSubject = meetingSubject;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/*public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}*/
	
	
	
	

}
