package com.bcits.bfm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DocumentElement")
public class DocumentElementData {
  
private	List<DailyData> instantDatas=new ArrayList<>();

	public List<DailyData> getInstantDatas() {
		return instantDatas;
	}

	@XmlElement(name="DailyData")
	public void setInstantDatas(List<DailyData> instantDatas) {
		this.instantDatas = instantDatas;
	}
	
}
