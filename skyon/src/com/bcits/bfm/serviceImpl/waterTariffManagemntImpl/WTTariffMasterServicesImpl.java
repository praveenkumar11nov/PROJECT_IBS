package com.bcits.bfm.serviceImpl.waterTariffManagemntImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class WTTariffMasterServicesImpl extends GenericServiceImpl<WTTariffMaster> implements WTTariffMasterService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WTTariffMaster> findAllOnParentId(Integer WTTariffId,String status) {
		List<WTTariffMaster> tarifflist=null;
		if(WTTariffId == null)
		{
			WTTariffId=1;
			
			tarifflist=entityManager.createNamedQuery("WTTariffMasterTree.getTariffListbyId").setParameter("parentId", WTTariffId).getResultList();
			
		}
		else
		{
			
			tarifflist=entityManager.createNamedQuery("WTTariffMasterTree.getTariffListbyId").setParameter("parentId", WTTariffId).getResultList();
			
		}
		return tarifflist;
	 }

		@Override
		public WTTariffMaster getNodeDetails(Integer nodeid) {
			 return entityManager.createNamedQuery("WTTariffMaster.GetNodeDetails",WTTariffMaster.class).setParameter("nodeid", nodeid).getSingleResult();
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<WTTariffMaster> getTariffDetail(int parentId,String tariffname) {
            return entityManager.createNamedQuery("WTTariffMasterTree.getDetails").setParameter("parentId", parentId).setParameter("tariffname", tariffname).getResultList();
		}

		@Override
		public List<WTTariffMaster> getTariffNameBasedonTariffid(int wtTariffId) 
		{
			return entityManager.createNamedQuery("WTTariffMaster.GETTariffNameBasedOnId",WTTariffMaster.class).setParameter("wtTariffId",wtTariffId).getResultList();
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<WTTariffMaster> findAllOnParentIdwoStatus(Integer WTTariffId) {
			List<WTTariffMaster> tarifflist=null;
			if(WTTariffId == null)
			{
				WTTariffId=1;
				
				tarifflist=entityManager.createNamedQuery("WTTariffMasterTree.getTariffListbyIdwoStatus").setParameter("parentId", WTTariffId).getResultList();
				
			}
			else
			{
				
				tarifflist=entityManager.createNamedQuery("WTTariffMasterTree.getTariffListbyIdwoStatus").setParameter("parentId", WTTariffId).getResultList();
				
			}
			return tarifflist;
		}
}
