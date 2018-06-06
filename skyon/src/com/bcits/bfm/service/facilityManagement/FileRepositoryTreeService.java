package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import com.bcits.bfm.model.FileRepositoryTree;
import com.bcits.bfm.service.GenericService;

public interface FileRepositoryTreeService  extends GenericService<FileRepositoryTree>{

	public abstract List<FileRepositoryTree> getFileRepoListById(Integer frGroupId);
	public List<FileRepositoryTree> findAllOnfileRepositoryId(int frGroupId) ;
	public List<FileRepositoryTree> findIdByParent(int parentId,String frGroupName);
	public FileRepositoryTree findRepositoryBasedOnId(int frGroupId);
	public List<FileRepositoryTree> checkFileGroupUnique(String nodename);
}