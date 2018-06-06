package com.bcits.bfm.patternMasterService;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.service.GenericService;


@Service
public interface TransactionMasterService extends GenericService<com.bcits.bfm.patternMasterEntity.TransactionMaster>  {

	public List<?> findAll();

	public List<?> findAllDesig();

	public List<?> findLevel(int tId);

	public List<?> findAl(int processid);

	public List<?> findAllDept();

	public List<TransactionMaster> findall(String name);

	public List<?> readProcessNameForUniqueness();

	public void setItemStatus(int tId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);
	
	void setTallyStatusUpDateforXML(int billId);
}
