package com.bcits.bfm.serviceImpl.facilityManagement;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.MaintainanceDepartment;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.facilityManagement.MaintainanceDepartmentService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * A data access object (DAO) providing persistence and search support for
 * MaintainanceDepartment entities. Transaction control of the save(), update()
 * and delete() operations must be handled externally by senders of these
 * methods or must be manually added to each of these methods for data to be
 * persisted to the JPA datastore.
 * 
 * @see .MaintainanceDepartment
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MaintainanceDepartmentServiceImpl extends GenericServiceImpl<MaintainanceDepartment> implements MaintainanceDepartmentService {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private UsersService usersService;
	
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
	@SuppressWarnings("unchecked")
	public List<MaintainanceDepartment> findAll(){
		
	   return entityManager.createNamedQuery("MaintainanceDepartment.findAll").getResultList();

	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllUsers(int deptId) {
		
	   return entityManager.createNamedQuery("MaintainanceDepartment.getAllUsers").setParameter("deptId", deptId).getResultList();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getExistingUsersByDepartment(int deptId) {
		
	  return entityManager.createNamedQuery("MaintainanceDepartment.getExistingUsersByDepartment").setParameter("department", departmentService.find(deptId)).getResultList();
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MaintainanceDepartment findByUsersDepartment(Users urid, Department deptId) {
		MaintainanceDepartment main = null;
		List<MaintainanceDepartment> md=entityManager.createNamedQuery("MaintainanceDepartment.findByUsersDepartment").setParameter("users", urid).getResultList();
		if(md.size()>0){
			main=md.get(0);
		}
		return main;
	}

	@Override
	public int remove(MaintainanceDepartment md) {
		
	   return entityManager.createNamedQuery("MaintainanceDepartment.remove").setParameter("mtDpIt", md.getMtDpIt()).executeUpdate();
 
	}


	@SuppressWarnings("unchecked")
	@Override	
	public String checkUser(Department dept, Users user,String jobType) {
		
		List<MaintainanceDepartment> list = entityManager.createNamedQuery("MaintatinanceGroup.CheckUser").setParameter("id", user).setParameter("roleId", dept).setParameter("jobType", jobType).getResultList();
		String status = "";
		if (list.size() > 0) {
			status = "Exists";
		} else {
			status = "NotExists";
		}
		return status;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getMaintDepartments() {
		return entityManager.createNamedQuery("Maintenance.getDepartments").getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MaintainanceDepartment> getMPerson() {
		return entityManager.createNamedQuery("MaintainanceDepartment.getMPerson").getResultList();

	}


	
	
}