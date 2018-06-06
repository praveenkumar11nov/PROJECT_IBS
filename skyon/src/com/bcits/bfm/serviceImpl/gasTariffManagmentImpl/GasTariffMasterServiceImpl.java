package com.bcits.bfm.serviceImpl.gasTariffManagmentImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.GasTariffMaster;
import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class GasTariffMasterServiceImpl extends GenericServiceImpl<GasTariffMaster> implements GasTariffMasterService{

	@SuppressWarnings("unchecked")
	@Override
	public List<GasTariffMaster> findAllOnParentId(Integer gasTariffId,
			String status) {
		List<GasTariffMaster> tarifflist=null;
		if(gasTariffId == null)
		{
			gasTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("GasTariffMasterTree.getTariffListbyId").setParameter("parentId", gasTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("GasTariffMasterTree.getTariffListbyId").setParameter("parentId", gasTariffId).getResultList();
			
		}
		return tarifflist;
	}

	@Override
	public GasTariffMaster getNodeDetails(Integer gasTariffId) {
		
		return entityManager.createNamedQuery("GasTariffMaster.GetNodeDetails",GasTariffMaster.class).setParameter("gasTariffId", gasTariffId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GasTariffMaster> getTariffDetail(int gasparentId, String gastariffName) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("GasTariffMasterTree.getDetails").setParameter("gasparentId", gasparentId).setParameter("gastariffName", gastariffName).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GasTariffMaster> getTariffNameBasedonTariffid(int gasTariffId) {
	
		return entityManager.createNamedQuery("GasTariffMaster.GETTariffNameBasedOnId").setParameter("gasTariffId", gasTariffId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GasTariffMaster> findAllOnParentIdwoStatus(Integer gasTariffId
) {
		List<GasTariffMaster> tarifflist=null;
		if(gasTariffId == null)
		{
			gasTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("GasTariffMasterTree.getTariffListbyIdwoStatus").setParameter("gasparentId", gasTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("GasTariffMasterTree.getTariffListbyIdwoStatus").setParameter("gasparentId", gasTariffId).getResultList();
			
		}
		return tarifflist;
	}

}
