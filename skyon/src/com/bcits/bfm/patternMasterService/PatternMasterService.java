package com.bcits.bfm.patternMasterService;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;



public interface PatternMasterService {
	
	
	
	List<?> exicuteDistinctDepartmentsQuery();	
	List<TransactionMaster> findAll();	
	void updateDepartmentStatus(int deptId, String operation,HttpServletResponse response, MessageSource messageSource);
	String executeGetDeptNameBasedOnId(int deptId);
	List<Department> findAllDepartment();
	List<TransactionMaster> findAllTransactionName();
	void updateTransactionStatus(int tId, String operation,
			HttpServletResponse response, MessageSource messageSource);
	List<?> findLevel(int tId);
	List<?> readTransactionNames(int officeId);
	List<?> findAllDesignations(int tId,int officeId);
	List<?> readProcessIdForUniqueness();
	List<?> readDesignamtionNames();
	

}
