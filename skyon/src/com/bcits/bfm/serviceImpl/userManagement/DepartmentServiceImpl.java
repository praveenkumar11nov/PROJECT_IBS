package com.bcits.bfm.serviceImpl.userManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.DesignationService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.SessionData;

@Repository
public class DepartmentServiceImpl extends GenericServiceImpl<Department>
		implements DepartmentService {
	
	private static final Log logger = LogFactory.getLog(DepartmentServiceImpl.class);
	
	@Autowired
	private DesignationService designationService;
	
	@Autowired
	private UsersService usersService;
	
	Department obj = null;
	//int userid = 1;

	@Override
	public Department get(int id) {
		return entityManager.find(Department.class, id);
	}

	/** 
     * this method is used to find All Record of Department from database
     * @return         : returns all records of department
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Department> getAll() {
		return entityManager.createNamedQuery("Department.findAll")
				.getResultList();
	}

	/** 
     * this method is used to save the record of Department abd to store in database
     * @return         : returns created record of department
     * @since           1.0
     */
/*	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Department save(Department d) {
		if (!(checkNameExistence(d).size() > 0)) {
			entityManager.persist(d);
			return d;
		}
		return null;
	}*/

	
	/** 
     * this method is used to check the uniqueness of the department name from the database
     * @param dept_Name:dept_Name
     * @return         : returns department name is unique or not
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	private List<Department> checkNameExistence(Department d) {
		return entityManager.createNamedQuery("Department.checkNameExistence")
				.setParameter("dept_Name", d.getDept_Name()).getResultList();
	}

	/** 
     * this method is used to remove  department record from the database
     * @param id:id
     * @return         : returns deleted department Id from  database
     * @since           1.0
     */
	@Override
	public void remove(int id) {
		entityManager.createNamedQuery("Department.Delete")
				.setParameter("id", id).executeUpdate();
	}

	
	
	/** 
     * this method is used to check the uniqueness of the department name from the database
     * @param dept_Name:dept_Name
     * @return         : returns boolean value(true/false) 
     * @since           1.0
     */
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public boolean checkDepartmentUnique(Department department, String type) {
		List<String> list_department = entityManager
				.createNamedQuery("Department.CheckGroup")
				.setParameter("dept_Name", department.getDept_Name())
				.getResultList();
		if (type == "update") {
			if ((list_department.size() > 0)) {
				if (list_department.get(0).equals(
						find(department.getDept_Id()).getDept_Name()))
					return true;
				else
					return false;
			} else
				return true;

		} else {
			if ((list_department.size() > 0))
				return false;
			else
				return true;
		}

	}
	
	/** 
     * this method is used to read department details from the database
     * @return         : returns department details
     * @since           1.0
     */
	public Department getDepartmentObject(Map<String, Object> map, String type,
			Department department) {

		department = getBeanObject(map, type, department);

		return department;
	}

	@Transactional(propagation = Propagation.NEVER)
	private Department getBeanObject(Map<String, Object> map, String type,
			Department department) {
		
		String username=(String) SessionData.getUserDetails().get("userID");
		if (type.equals("update")) {
			
			department.setDept_Status(((String)map.get("dept_Status")));
			
			department.setDept_Id((Integer) map.get("dept_Id"));
			department.setCreatedBy((String)map.get("createdBy"));
			department.setLastUpdatedBy(username);
		}

		if (type.equals("save")) {

			department.setDept_Status("Inactive");
			department.setCreatedBy(username);
			department.setLastUpdatedBy(username);
		}
		department.setDept_Name(WordUtils.capitalizeFully((String) map.get("dept_Name")));
		if(map.get("dept_Desc")==null){
			
		}else{
			String desc=(String) map.get("dept_Desc");
			String desc_trim=desc.trim();
			department.setDept_Desc(desc_trim);
		}
		
		
		
		java.util.Date date= new java.util.Date();
		department.setLastUpdatedDt(new Timestamp(date.getTime()));
		return department;
	}
	
	
	
	
	/** 
     * this method is used to update the status of department and to store in database
     * @param  depId    :deptId
     * @return          : returns the records of visitor's
     * @since           1.0
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setDepartmentStatus(int depId, String operation, HttpServletResponse response, MessageSource messageSource, Locale locale)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			if(operation.equalsIgnoreCase("activate"))
			{
				entityManager.createNamedQuery("Department.UpdateStatus").setParameter("dept_Status", "Active").setParameter("depId", depId).executeUpdate();
				out.write("Department Activated");
			}
			else
			{
				List<String> checkList = usersService.checkDepartmentExistence(depId);
				if(checkList.size() == 0)
				{
					entityManager.createNamedQuery("Department.UpdateStatus").setParameter("dept_Status", "Inactive").setParameter("depId", depId).executeUpdate();
					out.write("Department Deactivated");
					
				}
				else
					out.write("Cannot deactivate this department since Users are already assigned");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	/** 
     * this method is used to find  department from database
     * @param  depId    :deptId
     * @return          : returns the records of visitor's
     * @since           1.0
     */
	 @SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Department> getDepartmentDetails(int deptId){
		 
		 return (List<Department>) entityManager.createNamedQuery("departmentDetails").setParameter("DeptId", deptId).getResultList();
	 }
	 
	 @Override
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<Integer> getDepartmentIdBasedOnDepartmentName(String departmentName){
			
			return entityManager.createNamedQuery("Department.getDepartmentIdBasedOnDepartmentName",
					Integer.class)
					.setParameter("departmentName", departmentName)
					.getResultList();
		}
	

	 
	 /** 
	     * this method is used to department Id from database based on department name
	     * @param  dept_Name    : dept_Name
	     * @return              : returns the records of visitor's
	     * @since                 1.0
	     */
	 @SuppressWarnings("rawtypes")
	 @Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public int getDepartmentIdBasedOnName(String dept_Name) {
		 
			List<Integer> departmentId =  entityManager.createNamedQuery("Department.getDepartmentIdBasedOnName",
					Integer.class)
					.setParameter("dnName", dept_Name)
					.getResultList();
			logger.info("departmentId  "+departmentId);
			
			Iterator it=departmentId.iterator();
			while(it.hasNext()){
				
				return (int) it.next();
			}
			return 0;
	}

	 /** 
	     *this method is used to find list of all active department  from database.
	     * @return          : returns list of active department.
	     * @since           1.0
	     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Department> getAllActiveDepartments() {
		return entityManager.createNamedQuery("Department.getAllActiveDepartments").getResultList();
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<Department> findAllWithoutInvalidDefault() {
		return entityManager.createNamedQuery("Department.findAllWithoutInvalidDefault").getResultList();
	}
	
	 
	/** 
     *this method is used to find department name list from database.
     * @return          : returns list of department name
     * @since           1.0
     */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Department> getDepartmentsNameForFilter() {
		return entityManager.createNamedQuery("departmentName").getResultList();
	}
	
	/** 
     *this method is used to find Department Name from database based on Id.
     *@param id         :id
     * @since           1.0
     */
	 @Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	public String getDepartmentNameBasedOnId(int id) {
		 
			return  entityManager.createNamedQuery("department.getDptName",String.class)
					.setParameter("id", id)
					.getSingleResult();
			
	}
	
}
