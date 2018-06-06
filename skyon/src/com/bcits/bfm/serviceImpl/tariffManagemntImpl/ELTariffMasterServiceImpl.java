package com.bcits.bfm.serviceImpl.tariffManagemntImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.service.tariffManagement.ELTariffMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class ELTariffMasterServiceImpl extends GenericServiceImpl<ELTariffMaster> implements ELTariffMasterService
{

	@Override
	public List<ELTariffMaster> getTariffMasterByType(String string)
	{
		return entityManager.createNamedQuery("ELTariffMaster.getTariffMasterByType",ELTariffMaster.class).setParameter("status", "Current").setParameter("tariffNodeType", string).getResultList();
	}

	@Override
	public List<ELTariffMaster> findALL()
	{
		
		return entityManager.createNamedQuery("ELTariffMaster.findAll",ELTariffMaster.class).getResultList();
	}

	@Override
	public String getTariffName(int elTariffID) {
		return (String) entityManager.createNamedQuery("ELTariffMaster.getTariffName").setParameter("elTariffID", elTariffID).getSingleResult();
	}

	@Override
	public List<?> getAllStates() {
		return entityManager.createNamedQuery("ELTariffMaster.getAllStates").getResultList();
	}

	@Override
	public List<String> getStateName() {
		return entityManager.createNamedQuery("ELTariffMaster.getStateName",String.class).getResultList();
	}

	@Override
	public List<?> getAllTariffMasters() {
		return entityManager.createNamedQuery("ELTariffMaster.getAllTariffMasters").getResultList();
	}

	@Override
	public List<ELTariffMaster> getAllTariffNodes(String stateName) {
		return entityManager.createNamedQuery("ELTariffMaster.getAllTariffNodes",ELTariffMaster.class).setParameter("stateName", stateName).getResultList();
	}

	@Override
	public String getStateName(int elTariffID) {
		return (String) entityManager.createNamedQuery("ELTariffMaster.getStateNameById").setParameter("elTariffID", elTariffID).getSingleResult();
	}
	
}
