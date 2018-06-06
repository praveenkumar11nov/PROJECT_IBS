package com.bcits.bfm.service.facilityManagement;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.MaintainanceDepartment;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;

/**
 * Interface for MaintainanceDepartmentDAO.
 * 
 * @author MyEclipse Persistence Tools
 */
public interface MaintainanceDepartmentService extends GenericService<MaintainanceDepartment> {
	
	/**
	 * Find all MaintainanceDepartment entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<MaintainanceDepartment> all MaintainanceDepartment entities
	 */
	public List<MaintainanceDepartment> findAll();

	@SuppressWarnings("rawtypes")
	public List getAllUsers(int deptId);

	@SuppressWarnings("rawtypes")
	public List getExistingUsersByDepartment(int deptId);	

	@Transactional(propagation = Propagation.REQUIRED)
	public int remove(MaintainanceDepartment md);

	public String checkUser(Department dept, Users user,String jobType);	

	public MaintainanceDepartment findByUsersDepartment(Users user,
			Department dept);

	public List<Object[]> getMaintDepartments();

	public List<MaintainanceDepartment> getMPerson();
}