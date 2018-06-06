package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.City;
import com.bcits.bfm.service.customerOccupancyManagement.CityService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class CityServiceImpl extends GenericServiceImpl<City> implements CityService
{
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.CityService#findAllAddressesBasedOnPersonID(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<City> findAll() {
		BfmLogger.logger.info("finding all City instances");
		try {
			return entityManager.createNamedQuery("City.findAll").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getIdBasedOnCityName(String cityName)
	{
		return entityManager.createNamedQuery("City.getIdBasedOnCityName", Integer.class).setParameter("cityName", cityName).getSingleResult();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> findNamebyId(int id) {
		return entityManager.createNamedQuery("City.findName").setParameter("id",id).getResultList();
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> cityListAfterPassingCityNameAndStId(String cityName,
			int stateId) {
		return entityManager.createNamedQuery("City.cityListAfterPassingCityNameAndStId").setParameter("cityName", cityName).setParameter("stateId",stateId ).getResultList();		

	}
}
