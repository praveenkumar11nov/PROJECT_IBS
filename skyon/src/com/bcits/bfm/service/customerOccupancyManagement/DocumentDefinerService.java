package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.DocumentDefiner;
import com.bcits.bfm.model.Owner;
import com.bcits.bfm.service.GenericService;

public interface DocumentDefinerService extends GenericService<DocumentDefiner>
{
	public List<DocumentDefiner> findAll();

	public DocumentDefiner getdocumentDefinerObject(Map<String, Object> map,
			String string, DocumentDefiner documentDefiner);

	public List<DocumentDefiner> getAllBasedOnType(String personType);

	public String getDocumentFormatOnPersonType(
			int selectedDocument, String personType);

	public void updateAccessCardStatus(int ddId, String operation,
			HttpServletResponse response);
	
	public List<DocumentDefiner> getAllBasedOnTypeAndCondition(String personType, String condition );
	
}
