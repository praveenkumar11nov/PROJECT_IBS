package com.bcits.bfm.model;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Lostandfound entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOSTANDFOUND")
@NamedQueries({
	@NamedQuery(name = "lostandfound.getImage", query = "SELECT p.image FROM Lostandfound p WHERE p.id= :lostandfoundId" ),
	@NamedQuery(name = "lostandfound.uploadImageOnId", query = "UPDATE Lostandfound p SET p.image= :blob WHERE p.id= :lostandfoundId" ),
})
public class Lostandfound implements java.io.Serializable {

	// Fields

	private int id;
	private int personId;
	private int blockId;
	private int propertyId;
	private String type;
	private String subject;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Timestamp date;
	private Timestamp lastUpdatedDt;
	private Blob image;
	private String status;
	
	// Property accessors
	@Id
	@Column(name = "LOSTFOUND_ID", unique = true, nullable = false, precision = 6, scale = 0)
	@SequenceGenerator(name = "LOSTANDFOUND_SEQUENCE", sequenceName = "LOSTANDFOUND_SEQUENCE")
	@GeneratedValue(generator = "LOSTANDFOUND_SEQUENCE")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "PERSON_ID", nullable = true)
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	@Column(name = "BLOCK_ID", nullable = true)
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	
	@Column(name = "PROPERTY_ID", nullable = true)
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	@Column(name = "ISSUE_TYPE", nullable = false, length = 45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "SUBJECT", nullable = false, length = 1000)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "DESCRIPTION", nullable = false, length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 100)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", nullable = false, length = 100)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "ISSUE_DATE", nullable = false, length = 11)
	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Column(name = "LAST_UPDATED_DT", nullable = false, length = 11)
	public Timestamp getLastUpdatedDt() {
		return this.lastUpdatedDt;
	}

	public void setLastUpdatedDt(Timestamp lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	@Lob
	@Column(name = "MATERIAL_IMAGE", nullable = true)
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
	@Column(name = "STATUS", nullable = true)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}