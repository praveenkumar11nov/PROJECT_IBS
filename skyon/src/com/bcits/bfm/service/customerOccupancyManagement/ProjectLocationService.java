package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Timestamp;

import com.bcits.bfm.model.ProjectLocation;
import com.bcits.bfm.service.GenericService;


public interface ProjectLocationService extends GenericService<ProjectLocation>
{

	ProjectLocation findOnLastUpdatedDate(Timestamp lastUpdatedDate);

}
