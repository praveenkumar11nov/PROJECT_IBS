package com.bcits.bfm.service.userManagement;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.service.GenericService;

public interface DepartmentService extends GenericService<Department> {

	public Department get(int id);

public List<Department> getAll();

	public Department save(Department d);

	public Department getDepartmentObject(Map<String, Object> map, String type,
			Department department);

	public void remove(int id);

	public boolean checkDepartmentUnique(Department department, String type);

	void setDepartmentStatus(int depId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale);
	public List<Department> getDepartmentDetails(int deptId);
	
	public List<Integer> getDepartmentIdBasedOnDepartmentName(String departmentName);
	public int getDepartmentIdBasedOnName(String dept_Name);

	List<Department> findAllWithoutInvalidDefault();

	List<Department> getAllActiveDepartments();

	List<Department> getDepartmentsNameForFilter();
	
	public String getDepartmentNameBasedOnId(int id);
	
	
	
	
	
	
	
	
}