package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PINSTATUS")
@NamedQueries({
		@NamedQuery(name = "Pinstatus.getStatus", query = "SELECT p FROM Pinstatus p WHERE p.userid = :userid"),
		@NamedQuery(name = "Pinstatus.checkUserExists", query = "SELECT p FROM Pinstatus p WHERE p.userid = :userid") })
public class Pinstatus {
	@Id
	@Column(name = "USERID")
	private String userid;
	@Column(name = "PIN_STATUS")
	private int pinstatus;

	@Column(name = "THEME")
	private String theme;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getPinstatus() {
		return pinstatus;
	}

	public void setPinstatus(int pinstatus) {
		this.pinstatus = pinstatus;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	
}
