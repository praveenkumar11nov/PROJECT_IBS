package com.bcits.bfm.serviceImpl.customerOccupancyManagement;


import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Owner;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class OwnerServiceImpl extends GenericServiceImpl<Owner> implements OwnerService{
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.OwnerService#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Owner> findAll() {
		try {
			final String queryString = "select model from Owner model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getOwnerId(int personId) {
		List<Integer> ownerIds = entityManager.createNamedQuery("Owner.getOwnerIdOnPersonId",Integer.class)
				.setParameter("personId", personId)
				.getResultList();
	
		Iterator<Integer> it=ownerIds.iterator();
while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
		
	}
	
	
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getPropertyIdBasedOnownerId(int ownerId) {
		
		return  entityManager.createNamedQuery("OwnerProperty.getAllPropertyOnOwnerId",Integer.class)
				.setParameter("ownerId", ownerId)
				.getResultList();
		
	}

	@Override
	public Integer findBaseOnOwnerId(Integer object) {
		// TODO Auto-generated method stub
		 	return (Integer)entityManager.createNamedQuery("OwnerProp.ownerId").setParameter("ownerId", object).getSingleResult();
	}

	@Override
	public List<Integer> getALLPersonIds() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Owners.AllPersonIds",Integer.class).getResultList();
	}

	
}


