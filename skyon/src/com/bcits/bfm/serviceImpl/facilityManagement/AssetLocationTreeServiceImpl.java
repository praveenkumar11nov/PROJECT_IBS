package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetLocationTree;
import com.bcits.bfm.service.facilityManagement.AssetLocationTreeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Transactional
@Repository
public class AssetLocationTreeServiceImpl extends GenericServiceImpl<AssetLocationTree> implements AssetLocationTreeService{
    
    @SuppressWarnings("unchecked")
    @Override
    public List<AssetLocationTree> findAllOnParentId(Integer assetlocId) {
    	List<AssetLocationTree> assetlist =null;
        if (assetlocId == null) {
        	assetlocId=1;
        	assetlist = entityManager.createNamedQuery("AssetLocationTree.findAllOnParentId").setParameter("parentId", assetlocId).getResultList();
        } else {
        	assetlist = entityManager.createNamedQuery("AssetLocationTree.findAllOnParentId").setParameter("parentId", assetlocId).getResultList();
        }
        
       return assetlist;
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<AssetLocationTree> findAllOnAssetLocId(int textval) {
		return entityManager.createNamedQuery("AssetLocationTree.findAllOnAssetLocId").setParameter("assetlocId", textval).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetLocationTree> findIdByParent(Integer assetlocId,
			String nodename) {
		return entityManager.createNamedQuery("AssetLocationTree.findIdByParent").setParameter("parentId", assetlocId).setParameter("assetlocText", nodename).getResultList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllLocationType() {		
			BfmLogger.logger.info("finding all locationType");
			try {
				return entityManager.createNamedQuery("AssetLocationTree.findAllLocationType").getResultList();
			} catch (RuntimeException re) {
				BfmLogger.logger.error("find all failed", re);
				throw re;
			}
		
	}
	
	
}