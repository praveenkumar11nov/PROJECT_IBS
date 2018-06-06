package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Country;
import com.bcits.bfm.service.customerOccupancyManagement.CountryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class CountryServiceImpl extends GenericServiceImpl<Country> implements CountryService
{
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.CountryService#findAllAddressesBasedOnPersonID(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Country> findAll() {
		BfmLogger.logger.info("finding all Country instances");
		try {
			return entityManager.createNamedQuery("Country.findAll").getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> findIdbyName(String cname) {
		return entityManager.createNamedQuery("Country.findId").setParameter("cname",cname).getResultList();
	}

	
}
