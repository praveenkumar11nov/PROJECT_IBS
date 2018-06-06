package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.CustomerItemsEntity;
import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentRepositoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;
import com.bcits.bfm.util.SessionData;

@Repository
public class DocumentRepositoryServiceImpl extends GenericServiceImpl<DocumentRepository> implements DocumentRepositoryService 
{

	@Override
	public List<DocumentRepository> getAllOnPersonId(int personId,String personType) 
	{
		BfmLogger.logger.info("finding all DocumentDefiner instances");
		try {
			return entityManager.createNamedQuery("DocumentRepository.findAllByPersonId").setParameter("drGroupId", personId).setParameter("documentType", personType).getResultList();
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find all failed", re);
			throw re;
		}
	}
	

	@Override
	public Blob getDocumentOnId(Integer documentId) {
		BfmLogger.logger.info("Downloading Document");
		
			return (Blob) entityManager.createNamedQuery("DocumentRepository.findAllBydrId",Blob.class).setParameter("drId", documentId).getSingleResult();
		
	}

	@Override
	public DocumentRepository getDocumentRepositoryObject(
			Map<String, Object> map, String operation,
			DocumentRepository documentRepository) 
	{
		// TODO Auto-generated method stub
		
		if(operation.equalsIgnoreCase("save"))
		{
			documentRepository.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
			documentRepository.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		else if(operation.equalsIgnoreCase("update"))
		{
			documentRepository.setDrId((Integer)map.get("drId"));
			documentRepository.setCreatedBy((String)map.get("createdBy"));
			documentRepository.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		documentRepository.setLastUpdatedDate(new Timestamp(new Date().getTime()));
		documentRepository.setDocumentNumber((String)map.get("documentNumber"));
		documentRepository.setDocumentFormat((String)map.get("documentFormat"));
		documentRepository.setDocumentName((String)map.get("documentName"));
		documentRepository.setDocumentDescription((String)map.get("documentDescription"));
		documentRepository.setApproved("No");
		return documentRepository;
	}
	
	@Override
	public void uploadImageOnId(int drId,Blob document) {
		entityManager.createNamedQuery("DocumentRepository.uploadDocumentOnDrId")
				.setParameter("drId", drId)
				.setParameter("document", document)
				.executeUpdate();
		
	}

	@Override
	public List<?> findAll() 
	{
		// TODO Auto-generated method stub
		List<DocumentRepository> list = entityManager.createNamedQuery("DocumentRepository.findAll").getResultList();		
		List<Map<String, Object>> documentRepoList =  new ArrayList<Map<String, Object>>(); 
		/*for (final DocumentRepository record : list) 
        {
			
			documentRepoList.add(new HashMap<String, Object>() {{
				put("drId" ,record.getDrId());
				put("drGroupId",record.getDrGroupId());
				String str = "";
				if(record.getPerson().getLastName() != null)
				{
					str = record.getPerson().getLastName();
				}
				put("personName",record.getPerson().getFirstName()+"  "+str);
				put("approved",record.getApproved());
				put("documentName",record.getDocumentName());
				put("documentNumber",record.getDocumentNumber());
				put("documentDescription",record.getDocumentDescription());
				put("documentFormat",record.getDocumentFormat());
			}});
        }*/
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
		{
			final Object[] values=(Object[])iterator.next();
			documentRepoList.add(new HashMap<String, Object>() {{
				put("drId" ,(Integer)values[0]);
				put("drGroupId",(Integer)values[1]);
				String str = "";
				if((String)values[2] != null)
				{
					str = (String)values[2];
				}
				put("personName",(String)values[3]+" "+str);
				put("approved",(String)values[4]);
				put("documentName",(String)values[5]);
				put("documentNumber",(String)values[6]);
				put("documentDescription",(String)values[7]);
				put("documentFormat",(String)values[8]);
			}});
			
			
			
		}
		return documentRepoList;
	}

	@Override
	public void updateApproveStatus(int drId, String operation,
			HttpServletResponse response) 
	{
		// TODO Auto-generated method stub
		try
		{
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("Approve"))
			{
				entityManager.createNamedQuery("DocumentRepository.UpdateApproveStatus").setParameter("approved", "Yes").setParameter("drId", drId).executeUpdate();
				out.write("Document Approved");
			}
			else if(operation.equalsIgnoreCase("invalid"))
			{
				out.write("No Defined Document Records Found, User can't be activated");
			}
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentRepository> getDocumentRepositoryObjectOnGroupId(
			int groupId , String personType ) {
		return entityManager.createNamedQuery("DocumentRepository.getDocumentRepositoryObjectOnGroupId")
				.setParameter("groupId", groupId)
				.setParameter("personType", personType)
				.getResultList();
	}
	@Override
	public List<DocumentRepository> getAllOndrgroupId(int drGroupId) {
		
		try {
			final String queryString = "SELECT dr FROM DocumentRepository dr WHERE dr.drGroupId = :drGroupId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("drGroupId", drGroupId);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		}
		
	}
	@Override
	public void updateApprovedStatus(int groupId) 
	{
		final String queryString = "UPDATE DocumentRepository model SET model.approved='Yes' WHERE model.drGroupId="+groupId+"";
		Query query = entityManager.createQuery(queryString);
		query.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentRepository> getDocumentRepositoryObject(int groupId,
			String personType) {
		return entityManager.createNamedQuery("DocumentRepository.getDocumentRepositoryObject")
				.setParameter("groupId", groupId)
				.setParameter("personType", personType)
				.getResultList();
	}


	@Override
	public Integer findmaxid() {

	return	 entityManager.createNamedQuery("DocumentRepository.getMaximunId",Integer.class).getSingleResult();
	
	}
	/*@SuppressWarnings("rawtypes")
	private List readItems(List<?> department)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> departmentData = null;
		for (Iterator<?> iterator = department.iterator(); iterator.hasNext();)
		{
			 final DocumentRepository values = (DocumentRepository) iterator.next();
			 departmentData = new HashMap<String, Object>();
			 departmentData.put("drGroupId",values.getDrGroupId());
			
		     result.add(departmentData); 
		 }
		 return result;
	}
*/


	@Override
	public DocumentRepository getDocumentbyId(Integer documentId) {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("Dcumentrepoitry.getDocumentObjectBuid",DocumentRepository.class).setParameter("drId", documentId).getSingleResult();
	}


	@Override
	public List<?> getPersonFilterForDocumentUrl() {
		return entityManager.createNamedQuery("DocumentRepository.getPersonFilterForDocumentUrl").getResultList();

	}


	@Override
	public List<Object[]> getPerNameFil() {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("DocumentPers.NameFilter").getResultList();
	}
		
}
