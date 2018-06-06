package com.bcits.bfm.serviceImpl.solidWasteTariffManagemntImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class SolidWasteTariffMasterServiceImpl extends GenericServiceImpl<SolidWasteTariffMaster> implements SolidWasteTariffMasterService{

	@SuppressWarnings("unchecked")
	@Override
	public List<SolidWasteTariffMaster> findAllOnParentId(Integer solidWasteTariffId,
			String status) {
		List<SolidWasteTariffMaster> tarifflist=null;
		if(solidWasteTariffId==null){
			solidWasteTariffId=1;
        tarifflist=entityManager.createNamedQuery("SolidWatsteTariff.getTariffListbyId").setParameter("solidWasteTariffId", solidWasteTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("SolidWatsteTariff.getTariffListbyId").setParameter("solidWasteTariffId", solidWasteTariffId).getResultList();
		}
		return tarifflist;
	}

	@Override
	public SolidWasteTariffMaster getNodeDetails(Integer nodeid) {
		
		return entityManager.createNamedQuery("SolidWasteTariff.getNodeDetails",SolidWasteTariffMaster.class).setParameter("solidWasteTariffId", nodeid).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolidWasteTariffMaster> getTariffDetail(int parentId,
			String tariffname) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("SolidWAsteTariffMAster.getDetails").setParameter("solidWasteTariffId", parentId).setParameter("solidWastetariffName", tariffname).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolidWasteTariffMaster> getTariffNameBasedonTariffid(
			int solidWasteTariffId) {
		
		return entityManager.createNamedQuery("SolidWasteTariffMaster.getTAriffNameBasedonID").setParameter("solidWasteTariffId", solidWasteTariffId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolidWasteTariffMaster> findAllOnParentIdwoStatus(
			Integer solidWasteTariffId) {
		List<SolidWasteTariffMaster> tarifflist=null;
		if(solidWasteTariffId==null){
			solidWasteTariffId=1;
        tarifflist=entityManager.createNamedQuery("SolidWatsteTariff.getTariffListbyIdWoStatus").setParameter("solidWasteTariffId", solidWasteTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("SolidWatsteTariff.getTariffListbyIdWoStatus").setParameter("solidWasteTariffId", solidWasteTariffId).getResultList();
		}
		return tarifflist;
	}

}
