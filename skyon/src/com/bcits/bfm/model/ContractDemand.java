package com.bcits.bfm.model;

import java.util.Date;

public class ContractDemand extends GenericParameterForCalculation
{
private float contratDemand;

public ContractDemand(String tariffName, String rateName, float uomValue,Date startDate, Date endDate,float contratDemand)
{
		super(tariffName, rateName, uomValue, startDate, endDate);
		this.contratDemand=contratDemand;
}

public float getContratDemand() {
	return contratDemand;
}

public void setContratDemand(float contratDemand) {
	this.contratDemand = contratDemand;
}
}
