package com.bcits.bfm.serviceImpl.facilityManagement;

import java.util.List;






import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetCategoryTree;
import com.bcits.bfm.model.FileRepositoryTree;
import com.bcits.bfm.model.Groups;
import com.bcits.bfm.service.facilityManagement.FileRepositoryTreeService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Transactional
@Repository
public class FileRepossitoryTreeServiceImpl extends GenericServiceImpl<FileRepositoryTree> implements FileRepositoryTreeService{

	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.facilityManagement.FileRepositoryTreeService#getFileRepoListById(java.lang.Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<FileRepositoryTree> getFileRepoListById(Integer frGroupId) {
    	List<FileRepositoryTree> fileRepo_list =null;
        if (frGroupId == null) {
        	frGroupId=1;
        	fileRepo_list = entityManager.createNamedQuery("FileRepositoryTree.find").setParameter("parentId", frGroupId).getResultList();
     		
        } else {
        	fileRepo_list = entityManager.createNamedQuery("FileRepositoryTree.find").setParameter("parentId", frGroupId).getResultList();
     		
        }
       return fileRepo_list;
    }
	
	 @SuppressWarnings("unchecked")
		@Override
		public List<FileRepositoryTree> findAllOnfileRepositoryId(int frGroupId) {
			return entityManager.createNamedQuery("FileRepositoryTree.findAllonfileRepositoryId").setParameter("frGroupId", frGroupId).getResultList();
		}
	 
	 @SuppressWarnings("unchecked")
		public FileRepositoryTree findRepositoryBasedOnId(int frGroupId){
		 return (FileRepositoryTree) entityManager.createNamedQuery("FileRepositoryTree.findAllonfileRepositoryId").setParameter("frGroupId", frGroupId).getSingleResult();
	 }
	 
	 
	 @SuppressWarnings("unchecked")
		@Override
		public List<FileRepositoryTree> findIdByParent(int parentId,String frGroupName) {
			return entityManager.createNamedQuery("FileRepositoryTree.findIdByParent").setParameter("parentId", parentId).setParameter("frGroupName", frGroupName).getResultList();
		}
	 
	 
	 @Override
		@Transactional(propagation = Propagation.SUPPORTS)
		@SuppressWarnings("unchecked")
		public List<FileRepositoryTree> checkFileGroupUnique(String frGroupName) {
			return entityManager.createNamedQuery("FileRepositoryTree.findGroupName").setParameter("frGroupName", frGroupName).getResultList();
					}
	
}
