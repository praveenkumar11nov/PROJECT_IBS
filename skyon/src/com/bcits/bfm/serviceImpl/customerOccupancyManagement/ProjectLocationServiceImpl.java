package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ProjectLocation;
import com.bcits.bfm.service.customerOccupancyManagement.ProjectLocationService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ProjectLocationServiceImpl extends GenericServiceImpl<ProjectLocation> implements ProjectLocationService
{
	@SuppressWarnings("unchecked")
	@Override
	public ProjectLocation findOnLastUpdatedDate(Timestamp lastUpdatedDate) 
	{
		// TODO Auto-generated method stub
		return (ProjectLocation) entityManager.createNamedQuery("ProjectLocation.getProjectLocationId").setParameter("lastUpdatedDate", lastUpdatedDate).getSingleResult();
	}

}
