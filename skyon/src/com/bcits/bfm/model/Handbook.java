package com.bcits.bfm.model;


import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Handbook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HANDBOOK")
public class Handbook implements java.io.Serializable {

	// Fields

	private int handbookId;
	private Blob handbookContent;
	private String handbookType;

	
	@Id
	@Column(name = "HANDBOOK_ID", unique = true, nullable = false, precision = 6, scale = 0)
	@SequenceGenerator(name = "HANDBOOK_SEQ", sequenceName = "HANDBOOK_SEQ")
	@GeneratedValue(generator = "HANDBOOK_SEQ")
	public int getHandbookId() {
		return this.handbookId;
	}

	public void setHandbookId(int handbookId) {
		this.handbookId = handbookId;
	}

	@Column(name = "HANDBOOK_CONTENT", nullable = false)
	public Blob getHandbookContent() {
		return this.handbookContent;
	}

	public void setHandbookContent(Blob handbookContent) {
		this.handbookContent = handbookContent;
	}

	@Column(name = "HANDBOOK_TYPE", nullable = false)
	public String getHandbookType() {
		return this.handbookType;
	}

	public void setHandbookType(String handbookType) {
		this.handbookType = handbookType;
	}

}