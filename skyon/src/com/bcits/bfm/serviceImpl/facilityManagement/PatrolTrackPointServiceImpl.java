package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PatrolTrackPoints;
import com.bcits.bfm.service.facilityManagement.PatrolTrackPointService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PatrolTrackPointServiceImpl extends GenericServiceImpl<PatrolTrackPoints> implements PatrolTrackPointService{

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackPoints> findAll() {

		return entityManager.createNamedQuery("PatrolTrackPoints.findAll").getResultList();
	}


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PatrolTrackPoints getPatrolTrackPointInstanceById(int ptpId) {
		return entityManager.createNamedQuery("PatrolTrackPoints.PatrolTrackPointInstanceById",
				PatrolTrackPoints.class)
				.setParameter("ptpId", ptpId)
				.getSingleResult();
	}


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackPoints> findAllBasedOnPtId(int ptId) {

		return entityManager.createNamedQuery("PatrolTrackPoints.findAllBasedOnPtId",PatrolTrackPoints.class)
				.setParameter("ptId", ptId)
				.getResultList();
	}

	/**
	 * Setting status for the patroltrack staff.
	 * 
	 * @param ptsId
	 *            This staff id coming from jsp 
	 * @param operation
	 *            Either 'activate' or 'de-activate' string coming from jsp
	 *            to check which operation to perform.
	 * @param response
	 *            This will send response to view
	 *
	 * @since 0.1
	 */
	@Override
	public void setPatrolTracktPointStatus(int ptpId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{

				entityManager.createNamedQuery("PatrolTrackPoints.UpdateStatus").setParameter("ptpStatus", "Active").setParameter("ptpId", ptpId).executeUpdate();
				out.write("Patrol Track Point Activated");
			}
			else
			{

				entityManager.createNamedQuery("PatrolTrackPoints.UpdateStatus").setParameter("ptpStatus", "InActive").setParameter("ptpId", ptpId).executeUpdate();
				out.write("Patrol Track Point Deactivated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	

	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getSequences() {
		return  entityManager.createNamedQuery("PatrolTrackPoints.getSequences").getResultList();
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getTimeIntervals() {
		return  entityManager.createNamedQuery("PatrolTrackPoints.getTimeIntervals").getResultList();
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getStatus() {
		return  entityManager.createNamedQuery("PatrolTrackPoints.getStatus").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getPtId(int ptId) {
		return  entityManager.createNamedQuery("PatrolTrackPoints.ptIds")
				.setParameter("ptId", ptId)
				.getResultList();
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getStatusBasedOnId(int ptpId) {
		return  entityManager.createNamedQuery("PatrolTrackPoints.getStatusBasedOnId",String.class)
				.setParameter("ptpId", ptpId)
				.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackPoints> findPatrolPointExceptId(int ptpId) {
		
		return entityManager.createNamedQuery("PatrolTrackPoints.findPatrolPointExceptId")
				.setParameter("ptpId", ptpId)
				.getResultList();
	}
	
}
