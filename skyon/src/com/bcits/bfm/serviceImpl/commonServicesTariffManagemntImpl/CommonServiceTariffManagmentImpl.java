package com.bcits.bfm.serviceImpl.commonServicesTariffManagemntImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CommonTariffMaster;
import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTariffMasterServices;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CommonServiceTariffManagmentImpl extends GenericServiceImpl<CommonTariffMaster> implements CommonServiceTariffMasterServices{

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonTariffMaster> findAllOnParentId(Integer csTariffId,
			String status) {
		List<CommonTariffMaster> tarifflist=null;
		if(csTariffId == null)
		{
			csTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("CommonTariffMasterTree.getTariffListbyId").setParameter("csParentId", csTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("CommonTariffMasterTree.getTariffListbyId").setParameter("csParentId", csTariffId).getResultList();
			
		}
		return tarifflist;
		
	}

	@Override
	public CommonTariffMaster getNodeDetails(Integer csTariffID) {
		
		return entityManager.createNamedQuery("CommonTariffMasterTree.GetNodeDetails",CommonTariffMaster.class).setParameter("csTariffID", csTariffID).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonTariffMaster> getTariffDetail(int csParentId,
			String csTariffName) {
		
		return entityManager.createNamedQuery("CommonTariffMasterTree.getDetails").setParameter("csParentId", csParentId).setParameter("csTariffName", csTariffName).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonTariffMaster> getTariffNameBasedonTariffid(int csTariffID) {
		
		return entityManager.createNamedQuery("CommonTariffMaster.GETTariffNameBasedOnId").setParameter("csTariffID", csTariffID).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonTariffMaster> findAllOnParentIdwoStatus(Integer csTariffId) {
		List<CommonTariffMaster> tarifflist=null;
		if(csTariffId == null)
		{
			csTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("CommonTariffMasterTree.getTariffListbyIdwoStatus").setParameter("csParentId", csTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("CommonTariffMasterTree.getTariffListbyIdwoStatus").setParameter("csParentId", csTariffId).getResultList();
			
		}
		return tarifflist;
	}

}
