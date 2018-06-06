package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.ProjectLocation;
import com.bcits.bfm.model.State;
import com.bcits.bfm.service.customerOccupancyManagement.StateService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class StateServiceImpl extends GenericServiceImpl<State> implements StateService
{
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.StateService#findAllAddressesBasedOnPersonID(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<State> findAll() {
		BfmLogger.logger.info("finding all State instances");
		try {
			return entityManager.createNamedQuery("State.findAll").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Integer getIdBasedOnStateName(String stateName)
	{
		return entityManager.createNamedQuery("State.getIdBasedOnStateName", Integer.class).setParameter("stateName", stateName).getSingleResult();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<State> findIdbyName(String sname) {
		return entityManager.createNamedQuery("State.findId").setParameter("sname",sname).getResultList();
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<State> findNamebyId(int id) {
		return entityManager.createNamedQuery("State.findName").setParameter("id",id).getResultList();
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectLocation> findAllProjectLocation() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("ProjectLocation.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<State> getStateListAfterPassingStateName(String stateName,
			int countryId) {
		
		return entityManager.createNamedQuery("State.getStateListAfterPassingStateName").setParameter("stateName",stateName).setParameter("countryId",countryId ).getResultList();
	}
}
