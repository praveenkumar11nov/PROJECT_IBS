package com.bcits.bfm.serviceImpl.userManagement;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Permission;
import com.bcits.bfm.service.userManagement.PermissionService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.BfmLogger;

@Repository
public class PermissionServiceImpl extends GenericServiceImpl<Permission>
		implements PermissionService {

	// find product by id
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.PermissionService#findById(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Permission findById(int id) {
		Permission instance = entityManager.find(Permission.class, id);
		return instance;
	}

	// get all products

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.PermissionService#getAll()
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Permission> getAll() {
		return entityManager.createNamedQuery("Permission.findAll")
				.getResultList();
	}

	// geting permission id
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.PermissionService#getAllPerm
	 * (int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Permission> getAllPerm(int roleId) {
		return entityManager.createNamedQuery("Permission.findRoleId")
				.setParameter("roleId", roleId).getResultList();
	}

	// get task id
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.PermissionService#getTaskId()
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Permission> getTaskId() {
		return entityManager.createNamedQuery("Permission.findId")
				.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bcits.bfm.serviceImpl.userManagement.PermissionService#count()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int count() {
		List al = entityManager.createNamedQuery("Permission.count")
				.getResultList();
		int val = Integer.parseInt(al.get(0).toString());
		BfmLogger.logger.info("size " + val);
		return val;
	}

	// get permission count depending on task id
	@SuppressWarnings("rawtypes")
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.PermissionService#getPermissionCount
	 * (int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int getPermissionCount(int tkId) {
		List al = entityManager.createNamedQuery("Permission.findCount")
				.setParameter("tkId", tkId).getResultList();
		int val = Integer.parseInt(al.get(0).toString());
		BfmLogger.logger.info("size " + val);
		return val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bcits.bfm.serviceImpl.userManagement.PermissionService#getAll(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Permission> getAll(int tkId) {
		return entityManager.createNamedQuery("Permission.findAllById")
				.setParameter("tkId", tkId).getResultList();
	}

}
