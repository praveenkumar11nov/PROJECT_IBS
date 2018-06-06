package com.bcits.bfm.service;

import java.sql.Timestamp;

import com.bcits.bfm.model.DrGroupId;

public interface DrGroupIdService extends GenericService<DrGroupId>
{

	public Integer getNextVal(String createdBy, Timestamp lastUpdatedDt);

}