package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.bcits.bfm.model.AssetCategoryTree;
import com.bcits.bfm.service.facilityManagement.AssetCategoryTreeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Transactional
@Repository
public class AssetCategoryTreeServiceImpl extends GenericServiceImpl<AssetCategoryTree> implements AssetCategoryTreeService{
    
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<AssetCategoryTree> findAllOnParentId(Integer assetcatId) {
    	List<AssetCategoryTree> assetlist =null;
    	
        if (assetcatId == null) {
        	assetcatId=1;
        	assetlist = entityManager.createNamedQuery("AssetCategoryTree.getAssetListById").setParameter("parentId", assetcatId).getResultList();
     		
        } else {
        	assetlist = entityManager.createNamedQuery("AssetCategoryTree.getAssetListById").setParameter("parentId", assetcatId).getResultList();
     		
        }
       return assetlist;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<AssetCategoryTree> findAllOnAssetCatId(int assetCatId) {
		return entityManager.createNamedQuery("AssetCategoryTree.findAllOnAssetCatId").setParameter("assetcatId", assetCatId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetCategoryTree> findIdByParent(int parentId,String assetcatText) {
		return entityManager.createNamedQuery("AssetCategoryTree.findIdByParent").setParameter("parentId", parentId).setParameter("assetcatText", assetcatText).getResultList();
	}
}