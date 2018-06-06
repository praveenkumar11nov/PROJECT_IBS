package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.Iterator;
import java.util.List;





import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AccessRepository;
import com.bcits.bfm.service.customerOccupancyManagement.AccessRepositoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AccessRepositoryServiceImpl extends GenericServiceImpl<AccessRepository> implements AccessRepositoryService {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessRepository> findAll() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("AccessRepository.findAll").getResultList();
	}


	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getAccessRepositoryIdBasedOnName(String arName) {
		List<Integer> accessRepositoryId =  entityManager.createNamedQuery("AccessRepository.getAccessRepositoryIdBasedOnName",
					Integer.class)
					.setParameter("arName", arName)
					.getResultList();
		Iterator it=accessRepositoryId.iterator();
		while(it.hasNext()){
			
			return (int) it.next();
		}
		return 0;
		 
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getRepositoryName() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("AccessRepository.getRepositoryName").getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AccessRepository> getRepositoryName(int arName){
		
				return entityManager.createNamedQuery("AccessRepository.name")
						.setParameter("arName", arName)
						.getResultList();
	}
}
