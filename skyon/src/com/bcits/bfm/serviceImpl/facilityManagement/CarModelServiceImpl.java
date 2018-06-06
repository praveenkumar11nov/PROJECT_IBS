package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.bcits.bfm.model.CarModel;
import com.bcits.bfm.service.facilityManagement.CarModelService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CarModelServiceImpl extends GenericServiceImpl<CarModel> implements CarModelService {
	
	@SuppressWarnings("unchecked")
	public List<CarModel> findAll() {
		try {
			final String queryString = "select model from CarModel model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
	}	
}