package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for JobCalenderDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface JobCalenderService extends GenericService<JobCalender>{

	public JobCalender findById(int id);

	/**
	 * Find all JobCalender entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the JobCalender property to query
	 * @param value
	 *            the property value to match
	 * @return List<JobCalender> found by query
	 */
	public List<JobCalender> findByProperty(String propertyName, Object value);

	public List<JobCalender> findByTitle(Object title);

	public List<JobCalender> findByDescription(Object description);

	public List<JobCalender> findByIsAllDay(Object isAllDay);

	public List<JobCalender> findByRecurrenceRule(Object recurrenceRule);

	public List<JobCalender> findByRecurrenceId(Object recurrenceId);

	public List<JobCalender> findByRecurenceException(Object recurenceException);

	public List<JobCalender> findByScheduleType(Object scheduleType);

	public List<JobCalender> findByJobNumber(Object jobNumber);

	public List<JobCalender> findByJobGroup(Object jobGroup);

	public List<JobCalender> findByExpectedDays(Object expectedDays);

	public List<JobCalender> findByJobPriority(Object jobPriority);

	public List<JobCalender> findByJobAssets(Object jobAssets);

	/**
	 * Find all JobCalender entities.
	 * 
	 * @return List<JobCalender> all JobCalender entities
	 */
	public List<JobCalender> findAll();

	public List<?> readAll();

	public void saveOrUpdate(List<JobCalender> jobCalenderList);


	void deleteJobCalender(int i);

	public List<Object[]> findRequiredFieldOnAmsId(int amsId);

	void Update(List<JobCalender> jobCalenderList);

}