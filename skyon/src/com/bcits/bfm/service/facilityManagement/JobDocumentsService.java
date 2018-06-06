package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.JobDocuments;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JobDocumentsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JobDocumentsService extends GenericService<JobDocuments> {	
	/**
	 * Find all JobDocuments entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<JobDocuments> all JobDocuments entities
	 */
	public List<JobDocuments> findAll();

	List<?> readData(int jcId);

	JobDocuments setParameters(int jcId, JobDocuments jobDocuments,
			String userName, Map<String, Object> map);
	
	Object deleteJobDocuments(JobDocuments jobDocuments);

	public void uploadImageOnId(int jobDocId, Blob blob);
}