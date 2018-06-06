package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.CarMake;
import com.bcits.bfm.service.facilityManagement.CarMakeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CarMakeServiceImpl extends GenericServiceImpl<CarMake> implements CarMakeService{
	@SuppressWarnings("unchecked")
	public List<CarMake> findAll() {
		try {
			final String queryString = "select model from CarMake model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}	
	public CarMake findbyName(String carName) {
		try {
			final String queryString = "select model from CarMake model where model.carName=:carName";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("carName", carName);
			List<CarMake> al=query.getResultList();
			return al.get(0);
		} catch (RuntimeException re) {
			throw re;
		}
	}	
}