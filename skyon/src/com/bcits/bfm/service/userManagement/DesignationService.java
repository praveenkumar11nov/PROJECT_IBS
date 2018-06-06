package com.bcits.bfm.service.userManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Designation;
import com.bcits.bfm.service.GenericService;

public interface DesignationService extends GenericService<Designation> {

	public Designation get(int id);

	public List<Designation> getAll();

	public void saveList(List<Designation> designations);

	public void updateList(List<Designation> designations);

	public void delete(List<Designation> designations);

	

	void setDesignationStatus(int dnId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);

	
	public List<Designation> getDesignationDetails(int id);
	
	public List<Integer> getDesignationIdBasedOnDesignationName(String designationName);
	
	
	/*----------------------by ravi-----------------*/
	@SuppressWarnings("rawtypes")
	public List getAllDesignationRequiredFields();
	
	@SuppressWarnings("rawtypes")
	public List getDesignationList(List designationlist);
	public Designation getDesignationObject(Map<String, Object> map, String type, Designation designation);

	public List<?> getDistinctDepartments();
	public boolean checkDesignationUnique(Designation designation, String type);

	public int setAllPrevDepartmentsBasedOnDeptId(int depId);

	public List<Designation> getAllActiveDesignations();

	List<Designation> geteDesignationsNameForFilter();
	
	public String getDesignationNameBasedOnId(int id);
	
	
	
	

}