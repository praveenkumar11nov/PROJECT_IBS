package com.bcits.bfm.ldap;

import java.util.List;

public class ItemDetails {
	
	private String url;
	private String role;
	private String text;
	private List<ThirdLevelDetails> thirdLevel;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<ThirdLevelDetails> getThirdLevel() {
		return thirdLevel;
	}
	public void setThirdLevel(List<ThirdLevelDetails> thirdLevel) {
		this.thirdLevel = thirdLevel;
	}
	
	
	

}
