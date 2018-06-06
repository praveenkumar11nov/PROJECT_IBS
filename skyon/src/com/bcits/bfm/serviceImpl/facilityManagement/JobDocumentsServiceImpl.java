package com.bcits.bfm.serviceImpl.facilityManagement;


import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.JobDocuments;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobDocumentsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.ConvertDate;

/**
 * A data access object (DAO) providing persistence and search support for
 * JobDocuments entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see .JobDocuments
 * @author MyEclipse Persistence Tools
 */
@Repository
public class JobDocumentsServiceImpl extends GenericServiceImpl<JobDocuments> implements JobDocumentsService {
	
	@Autowired
	private JobCardsService jobCardsService;
	
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
	@SuppressWarnings("unchecked")
	public List<JobDocuments> findAll() {
		try {
			final String queryString = "select model from JobDocuments model ORDER BY model.jobDocId DESC";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {			
			throw re;
		}
	}
	
	@Override
	public List<?> readData(int jcId) {
		List<Map<String, Object>> jobDocuments = new ArrayList<Map<String, Object>>();	
		for (final JobDocuments record :readJobDocuments(jcId)) {
			jobDocuments.add(new HashMap<String, Object>() {				
				private static final long serialVersionUID = 1L;
				{				
					put("jobDocId", record.getJobDocId());
					put("documentName", record.getDocumentName());
					put("documentType", record.getDocumentType());
					put("jobcards", record.getJobCards().getJcId());										
					put("documentDescription", record.getDocumentDescription());					
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getUpdatedBy());
					put("lastUpdatedDate",ConvertDate.TimeStampString(record.getLastUpdatedDate()));
				}
			});
		}

		return jobDocuments;	
	}
	
	public List<JobDocuments> readJobDocuments(int jcId) {
		@SuppressWarnings("unchecked")
		List<JobDocuments> jobDocuments = entityManager.createNamedQuery("JobCards.readJobDocuments").setParameter("jcId", jobCardsService.find(jcId)).getResultList();
		return jobDocuments;
	}
	
	@Override
	public JobDocuments setParameters(int jcId, JobDocuments jobDocuments, String userName,Map<String, Object> map) {
		if(map.get("createdBy")==null||map.get("createdBy")=="")
			jobDocuments.setCreatedBy(userName);
		jobDocuments.setDocumentName((String) map.get("documentName"));
		jobDocuments.setJobCards(jobCardsService.find(jcId));
		jobDocuments.setDocumentDescription((String) map.get("documentDescription"));		
		jobDocuments.setDocumentType((String) map.get("documentType"));	
		jobDocuments.setUpdatedBy(userName);		
		jobDocuments.setLastUpdatedDate(new Timestamp(new Date().getTime()));		
		return jobDocuments;
	}	
	@Override
	public Object deleteJobDocuments(JobDocuments jobDocuments) {		
		try {
			final String queryString = "DELETE JobDocuments WHERE jobDocId="+jobDocuments.getJobDocId();
			Query query = entityManager.createQuery(queryString);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			
			throw re;
		}		
	}

	@Override
	public void uploadImageOnId(int jobDocId, Blob blob) {
		entityManager.createNamedQuery("JobDocuments.uploadDocumentOnDrId").setParameter("jobDocId", jobDocId)
		.setParameter("document", blob)
		.executeUpdate();
	}
}