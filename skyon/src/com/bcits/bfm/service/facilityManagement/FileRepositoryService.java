package com.bcits.bfm.service.facilityManagement;

import java.sql.Blob;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.FileRepository;
import com.bcits.bfm.service.GenericService;

public interface FileRepositoryService extends GenericService<FileRepository>{

	public abstract List<FileRepository> findAll();

	public abstract FileRepository getFileRepositoryObject(
			Map<String, Object> map, String type, FileRepository fileRepository);

	public abstract void setFrDocumetStatus(int frId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);
	public List getIds(int frGroupId);
	public void updateFileRepository(Blob doc,int frId,String docType);
	public List<FileRepository> getFilerepositoryInfo(int frGroupId) ;
	public List<FileRepository> findCreatedBy();
	
	public List<FileRepository> findLastupdatedBy();
	public List<FileRepository> findDocType();
	public List<FileRepository> checkDocNameUnique(String docName) ;
	public List<FileRepository> getDocName();
}