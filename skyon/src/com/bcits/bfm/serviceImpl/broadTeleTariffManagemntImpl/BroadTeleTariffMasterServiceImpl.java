package com.bcits.bfm.serviceImpl.broadTeleTariffManagemntImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BroadTeleTariffMaster;
import com.bcits.bfm.service.broadTeleTariffManagment.BroadTeleTariffMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class BroadTeleTariffMasterServiceImpl extends GenericServiceImpl<BroadTeleTariffMaster> implements BroadTeleTariffMasterService{

	@SuppressWarnings("unchecked")
	@Override
	public List<BroadTeleTariffMaster> findAllOnParentId(Integer broadTeleTariffId,
			String status) {
		
		List<BroadTeleTariffMaster> tarifflist=null;
		if(broadTeleTariffId == null)
		{
			broadTeleTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("BroadTeleTariffMaster.GetONParentId").setParameter("broadTeleTariffId", broadTeleTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("BroadTeleTariffMaster.GetONParentId").setParameter("broadTeleTariffId", broadTeleTariffId).getResultList();
			
		}
		return tarifflist;
		
		 
	}

	@Override
	public BroadTeleTariffMaster getNodeDetails(Integer nodeid) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("BroadTeleTariffMaster.GetOnNodeId",BroadTeleTariffMaster.class).setParameter("broadTeleTariffId", nodeid).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BroadTeleTariffMaster> getTariffDetail(int parentId,
			String tariffname) {
		
		return entityManager.createNamedQuery("BraodTeleTariffMaster.GetTariffDetails").setParameter("broadTeleTariffId", parentId).setParameter("broadTeleTariffName",tariffname ).getResultList();
	}

	@Override
	public List<BroadTeleTariffMaster> getTariffNameBasedonTariffid(
			int broadTeleTariffId) {
		
		return entityManager.createNamedQuery("BroadBand.GETTariffNameBasedOnId",BroadTeleTariffMaster.class).setParameter("broadTeleTariffId",broadTeleTariffId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BroadTeleTariffMaster> findAllOnParentIdwoStatus(
			Integer broadTeleTariffId) {
		List<BroadTeleTariffMaster> tarifflist=null;
		if(broadTeleTariffId == null)
		{
			broadTeleTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("BroadTeleTariffMaster.GetONParentIdWoStatus").setParameter("broadTeleTariffId", broadTeleTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("BroadTeleTariffMaster.GetONParentIdWoStatus").setParameter("broadTeleTariffId", broadTeleTariffId).getResultList();
			
		}
		return tarifflist;
	}

}
