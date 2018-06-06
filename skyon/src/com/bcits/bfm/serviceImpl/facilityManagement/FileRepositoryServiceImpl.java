package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.controller.FileRepositoryController;
import com.bcits.bfm.model.FileRepository;
import com.bcits.bfm.service.facilityManagement.FileRepositoryService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Transactional
@Repository
public class FileRepositoryServiceImpl extends GenericServiceImpl<FileRepository> implements FileRepositoryService  {

	static Logger logger = LoggerFactory
			.getLogger(FileRepositoryController.class);
	
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.facilityManagement.FileRepositoryService#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<FileRepository> findAll(){
		List fpList=null;
		try{
			 fpList =entityManager.createNamedQuery("FileRepository.findAll").getResultList();
			 return fpList;
		}catch(Exception e){
			e.printStackTrace();
			return fpList;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FileRepository> findCreatedBy(){
		List fpList=null;
		try{
			 fpList =entityManager.createNamedQuery("FileRepository.createdBy").getResultList();
			 return fpList;
		}catch(Exception e){
			e.printStackTrace();
			return fpList;
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FileRepository> findLastupdatedBy(){
		List fpList=null;
		try{
			 fpList =entityManager.createNamedQuery("FileRepository.lastupdatedBy").getResultList();
			 return fpList;
		}catch(Exception e){
			e.printStackTrace();
			return fpList;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FileRepository> findDocType(){
		List fpList=null;
		try{
			 fpList =entityManager.createNamedQuery("FileRepository.DOC_TYPE").getResultList();
			 return fpList;
		}catch(Exception e){
			e.printStackTrace();
			return fpList;
		}
	}
	
	 
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.facilityManagement.FileRepositoryService#getFileRepositoryObject(java.util.Map, java.lang.String, com.bcits.bfm.model.FileRepository)
	 */
	@Override
	public FileRepository getFileRepositoryObject(Map<String, Object> map,String type,FileRepository fileRepository){
		fileRepository=getBeanObjectForFileRepository(map,type,fileRepository);
		
		return fileRepository;
	}

	@SuppressWarnings("unchecked")
	public List getIds(int frGroupId){
		return entityManager.createNamedQuery("FileRepository.findIds").setParameter("frGroupId", frGroupId).getResultList();
	}
	
   @Transactional(propagation=Propagation.NEVER)
	private FileRepository getBeanObjectForFileRepository(Map<String, Object> map, String type,
			FileRepository fileRepository) {
		// TODO Auto-generated method stub
	   String username=(String)SessionData.getUserDetails().get("userID");
	   if(type.equals("save")){
		   
		   logger.info("---"+map.get("frGroupId")+"===="+map.get("docName")+"====="+map.get("docDescription")+"====="+"=="+map.get("frSearchTag"));
		   fileRepository.setCreatedby(username);
		   fileRepository.setStatus("Inactive");
	   }
	   if(type.equals("update")){
		   logger.info("----"+map.get("frId"));
		   fileRepository.setFrId(Integer.parseInt((String) map.get("frId")));
		   fileRepository.setCreatedby((String)map.get("createdby"));
		   fileRepository.setStatus((String)map.get("status"));
		   if(map.get("docType")==null){
			   
		   }else{
			   fileRepository.setDocType((String)map.get("docType"));  
		   }
	   }
	   
		   fileRepository.setFrGroupId((Integer)map.get("frGroupId")); 
	  
		   String docname=(String)map.get("docName");
		   String decname_trim=docname.trim();
		  
		   fileRepository.setDocName(WordUtils.capitalizeFully(WordUtils.capitalizeFully(decname_trim)));
		   String docDescription=(String)map.get("docDescription");
		   String docdescription_trim=docDescription.trim();
		   fileRepository.setDocDescription(docdescription_trim);
		  
		 /*  String frSearchtag=(String)map.get("frSearchTag");
		   String frSearchtag_trim=frSearchtag.trim();*/
		   logger.info("Fr search tag is="+(String)map.get("frSearchTag"));
		   fileRepository.setFrSearchTag((String)map.get("frSearchTag"));
		   
		   
		   
		   fileRepository.setLastupdatedBy(username);
		   Date date=new Date();
		   fileRepository.setLastUpdatedDt(new Timestamp(date.getTime()));
           return fileRepository;
	}
	
   
   
   
   /* (non-Javadoc)
 * @see com.bcits.bfm.serviceImpl.facilityManagement.FileRepositoryService#setFrDocumetStatus(int, java.lang.String, javax.servlet.http.HttpServletResponse, org.springframework.context.MessageSource, java.util.Locale)
 */

@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setFrDocumetStatus(int frId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
	   logger.info("*************in side status method****");
		try {
			PrintWriter out = response.getWriter();

			Date date=new Date();
			
			FileRepository fileRepository = entityManager.createNamedQuery("FileRepository.checkDocument",FileRepository.class).setParameter("frId", frId).getSingleResult();
			if(fileRepository.getDrFileBlob()==null ||fileRepository.getDrFileBlob().equals("")){
				out.write("Please Add  Document");
			}
			else{
			if (operation.equalsIgnoreCase("approved")) {
				entityManager.createNamedQuery("FileRepository.UpdateStatus")
						.setParameter("status", "Active")
						.setParameter("frId", frId).setParameter("lastUpdatedDt",new Timestamp(date.getTime())).executeUpdate();
				out.write("Document Approved");
			} else {

				entityManager.createNamedQuery("FileRepository.UpdateStatus")
				.setParameter("status", "Inactive")
				.setParameter("frId", frId).setParameter("lastUpdatedDt",new Timestamp(date.getTime())).executeUpdate();
				out.write("Document Rejected");
				
				
			}
		}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

@Transactional(propagation = Propagation.REQUIRED)
public void updateFileRepository(Blob drFileBlob,int frId,String docType){
	 entityManager.createNamedQuery("updateFileRepository").setParameter("drFileBlob", drFileBlob).setParameter("docType", docType).setParameter("frId", frId).executeUpdate();
	
}

@Override
@Transactional(propagation = Propagation.SUPPORTS)
public List<FileRepository> getFilerepositoryInfo(int frGroupId) {
	List<FileRepository> al= entityManager.createNamedQuery("FileRepository.findInfo").setParameter("frGroupId", frGroupId).getResultList();
	return al;
}

@Override
@Transactional(propagation = Propagation.SUPPORTS)
@SuppressWarnings("unchecked")
public List<FileRepository> checkDocNameUnique(String docName) {
	return entityManager.createNamedQuery("FileRepository.findDocName").setParameter("docName", docName).getResultList();
			}

@Transactional(propagation = Propagation.SUPPORTS)
@SuppressWarnings("unchecked")
public List<FileRepository> getDocName() {
	return entityManager.createNamedQuery("FileRepository.findName").getResultList();
			}




}
