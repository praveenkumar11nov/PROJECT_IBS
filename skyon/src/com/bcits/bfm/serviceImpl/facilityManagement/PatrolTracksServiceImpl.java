package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.PatrolTracks;
import com.bcits.bfm.service.facilityManagement.PatrolTrackService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * A data access object (DAO) providing persistence and search support for PatrolTracks
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.bcits.bfm.model.PatrolTracks
 * @author Pooja.K
 * @version %I%, %G%
 * @since 0.1
 */
@Repository
public class PatrolTracksServiceImpl extends GenericServiceImpl<PatrolTracks> implements PatrolTrackService {

	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public PatrolTracks getTrackObject(Map<String, Object> map, String type, PatrolTracks patrolTracks) {
		patrolTracks = getBeanObject(map, type, patrolTracks);

		return patrolTracks;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	private PatrolTracks getBeanObject(Map<String, Object> map, String type,
			PatrolTracks patrolTracks) {
		if (type.equals("save")) 
		{
			patrolTracks.setPtName((String)map.get("ptName"));
			patrolTracks.setDescription((String)map.get("description"));
			patrolTracks.setValidTimeFrom((String)map.get("validTimeFrom"));
			patrolTracks.setValidTimeTo((String)map.get("validTimeTo"));
			patrolTracks.setStatus((String)map.get("status"));
			
		}
		return patrolTracks;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTracks> findAll() {
		return entityManager.createNamedQuery("PatrolTracks.findAll").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PatrolTracks getPatrolTrackInstanceById(int ptId) {
		return entityManager.createNamedQuery("PatrolTracks.PatrolTrackInstanceById",
				PatrolTracks.class)
				.setParameter("ptId", ptId)
				.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPatrolTrackNames() 
	{ 
		return entityManager.createNamedQuery("PatrolTracks.getAllPatrolTrackNames").getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getAllPatrolRoles() 
	{ 
		return entityManager.createNamedQuery("PatrolSettings.getAllPatrolRoles").getResultList();
	}
	/**
	 * Getting Patroltrack id based on its name.
	 * 
	 * @param ptName
	 *            Taking ptroltrack name as a parameter
	 * 
	 * @return Patroltrack id
	 * @since 0.1
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int getPatrolTrackIdBasedOnName(String ptName) {

		List<Integer> patroltrackId =  entityManager.createNamedQuery("PatrolTracks.getPatrolTrackIdBasedOnName",
				Integer.class)
				.setParameter("ptName", ptName)
				.getResultList();

		Iterator<Integer> it=patroltrackId.iterator();
		while(it.hasNext()){

			return (int) it.next();
		}
		return 0;
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
	public void setPatrolTracktStatus(int ptId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activate"))
			{

				entityManager.createNamedQuery("PatrolTracks.UpdateStatus")
				.setParameter("ptStatus", "Active")
				.setParameter("ptId", ptId)
				.executeUpdate();
				out.write("Patrol Track Activated");
			}
			
			else
			{

				entityManager.createNamedQuery("PatrolTracks.UpdateStatus")
				.setParameter("ptStatus", "InActive")
				.setParameter("ptId", ptId)
				.executeUpdate();
				out.write("Patrol Track Deactivated");
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
	public List<String> getStatusList() {
		
		return  entityManager.createNamedQuery("PatrolTracks.getStatusList").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAllPatrolTrackNames(String ptName) {
		return  entityManager.createNamedQuery("PatrolTracks.ptNames")
				.setParameter("ptName", ptName)
				.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PatrolTracks> findActiveTracks() {
		return entityManager.createNamedQuery("PatrolTracks.findActiveTracks")
				.setParameter("status", "Active")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getStatusBasedOnId(int ptId) {
		return  entityManager.createNamedQuery("PatrolTracks.getStatusBasedOnId",String.class)
				.setParameter("ptId", ptId)
				.getSingleResult();
	}

}
