package com.bcits.bfm.service.facilityManagement;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JobCardsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JobCardsService extends GenericService<JobCards> {
	/**
	 * Find all JobCards entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<JobCards> all JobCards entities
	 */
	public List<JobCards> findAll();

	public List<?> readData();
	public List<Object[]> readAllJobCards();

	public void setStatus(int jcId, String operation, HttpServletResponse response,final Locale locale)throws IOException, Exception;

	JobCards setParameters(JobCards jobCards, Map<String, Object> map);

	public void deleteCards(JobCards jobCards);

	public void setsupendStatus(int jcId, String operation,
			HttpServletResponse response) throws IOException;

	public void getAllDetails(int jcId, HttpServletResponse response) throws IOException, JSONException;

	public List<?> getRequiredCardsAndMaterials(String status);

	public List<JobCalender> findAllJobCalenderId();

	public void saveRecords(JobCards jcards);

	public List<JobCards> getId(JobCalender jc);

	List<JobCards> findByProperty(String propertyName, Object value);

	public void sendAlert(JobCards jcard,final Locale locale);

	public List<?> readDataforMobile(String username);

	public void deleteJobCard(int jobCardId);
	
}