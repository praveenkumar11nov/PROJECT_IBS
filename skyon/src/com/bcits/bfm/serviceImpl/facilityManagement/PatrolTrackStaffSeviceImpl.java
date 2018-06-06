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

import com.bcits.bfm.model.PatrolTrackStaff;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.facilityManagement.PatrolTrackStaffService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class PatrolTrackStaffSeviceImpl extends GenericServiceImpl<PatrolTrackStaff> implements PatrolTrackStaffService {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackStaff> findAll() {
		return entityManager.createNamedQuery("PatrolTrackStaff.findAll").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PatrolTrackStaff getPatrolTrackStaffInstanceById(int ptsId) {
		return entityManager.createNamedQuery("PatrolTrackStaff.getPatrolTrackStaffInstanceById",
				PatrolTrackStaff.class)
				.setParameter("ptsId", ptsId)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackStaff> findAllBasedOnPtId(int ptId) {
		return entityManager.createNamedQuery("PatrolTrackStaff.findAllBasedOnPtId",PatrolTrackStaff.class)
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
	public void setPatrolTracktStaffStatus(int ptsId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{

				entityManager.createNamedQuery("PatrolTrackStaff.UpdateStatus").setParameter("ptsStatus", "Active").setParameter("ptsId", ptsId).executeUpdate();
				out.write("Patrol Track Staff Activated");
			}
			else
			{

				entityManager.createNamedQuery("PatrolTrackStaff.UpdateStatus").setParameter("ptsStatus", "InActive").setParameter("ptsId", ptsId).executeUpdate();
				out.write("Patrol Track Staff Deactivated");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTrackStaff> findStaffRecord(int ptId, int acId,int staffId) {
		return entityManager.createNamedQuery("PatrolTrackStaff.findStaffRecord",PatrolTrackStaff.class)
				.setParameter("ptId", ptId)
				.setParameter("acId", acId)
				.setParameter("staffId", staffId)
				.getResultList();
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getStatusBasedOnId(int ptsId) {
		return  entityManager.createNamedQuery("PatrolTrackStaff.getStatusBasedOnId",String.class)
				.setParameter("ptsId", ptsId)
				.getSingleResult();
	}

	@Override
	public Person getPersonBasedOnId(Integer integer) {
		return entityManager.createNamedQuery("PatrolTrackStaff.getPersonBasedOnId",Person.class).setParameter("personId", integer).getSingleResult();
	}

	@Override
	public List<?> getPatrolTrackStaffData() {
		return entityManager.createNamedQuery("PatrolTrackStaff.PatrolTrackStaffData").getResultList();
	}

}
