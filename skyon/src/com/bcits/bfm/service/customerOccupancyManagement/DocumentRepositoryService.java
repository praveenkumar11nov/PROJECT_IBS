package com.bcits.bfm.service.customerOccupancyManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.service.GenericService;

public interface DocumentRepositoryService extends GenericService<DocumentRepository> 
{

	List<DocumentRepository> getAllOnPersonId(int personId,String personType);

	Blob getDocumentOnId(Integer documentId);

	DocumentRepository getDocumentRepositoryObject(Map<String, Object> map,
			String string, DocumentRepository documentRepository);

	void uploadImageOnId(int drId, Blob blob);

	List<?> findAll();

	void updateApproveStatus(int drId, String operation,
			HttpServletResponse response);

	 List<DocumentRepository> getAllOndrgroupId(int drGroupId);
	
	List<DocumentRepository> getDocumentRepositoryObjectOnGroupId( int groupId ,String personType );

	void updateApprovedStatus( int groupId);
	
	List<DocumentRepository> getDocumentRepositoryObject( int groupId ,String personType );

	public Integer findmaxid();

	DocumentRepository getDocumentbyId(Integer documentId);

	List<?> getPersonFilterForDocumentUrl();

	List<Object[]> getPerNameFil();
	

}
