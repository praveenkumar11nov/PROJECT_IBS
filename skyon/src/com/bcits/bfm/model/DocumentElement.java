package com.bcits.bfm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DocumentElement")
public class DocumentElement {
	private List<BillData> billDatas = new ArrayList<>();

	public List<BillData> getBillDatas() {
		return billDatas;
	}

	@XmlElement(name="BillData")
	public void setBillDatas(List<BillData> billDatas) {
		this.billDatas = billDatas;
	}
	

}
