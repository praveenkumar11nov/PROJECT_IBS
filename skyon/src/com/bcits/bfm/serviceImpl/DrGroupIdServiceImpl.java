package com.bcits.bfm.serviceImpl;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.service.DrGroupIdService;

@Repository
public class DrGroupIdServiceImpl extends GenericServiceImpl<DrGroupId> implements DrGroupIdService
{

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getNextVal(String createdBy, Timestamp lastUpdatedDt)
	{
		return entityManager.createNamedQuery("DrGroupId.getNextVal", Integer.class)
				.setParameter("createdBy", createdBy)
				.setParameter("lastUpdatedDt", lastUpdatedDt)
				.getSingleResult();
	}

}
