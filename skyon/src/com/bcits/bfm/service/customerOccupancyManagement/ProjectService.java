package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;

import com.bcits.bfm.model.Project;

 

/**
 * Interface for ProjectDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ProjectService {
	/**
	 * Perform an initial save of a previously unsaved Project entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IProjectDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Project entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Project entity);

	/**
	 * Delete a persistent Project entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IProjectDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Project entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Project entity);

	/**
	 * Persist a previously saved Project entity and return it or a copy of it
	 * to the sender. A copy of the Project entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = IProjectDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Project entity to update
	 * @return Project the persisted Project entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Project update(Project entity);

	public Project findById(int id);

	/**
	 * Find all Project entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Project property to query
	 * @param value
	 *            the property value to match
	 * @return List<Project> found by query
	 */
	public List<Project> findByProperty(String propertyName, Object value);

	public List<Project> findByProjectName(Object projectName);

	public List<Project> findByNO_OF_TOWERS(Object NO_OF_TOWERS);

	public List<Project> findByNO_OF_PROPERTIES(Object NO_OF_PROPERTIES);

	public List<Project> findByProjectAddress(Object projectAddress);

	public List<Project> findByPROJECT_PINCODE(Object PROJECT_PINCODE);

	public List<Project> findByProjectState(Object projectState);

	public List<Project> findByCreatedBy(Object createdBy);

	public List<Project> findByLastUpdatedBy(Object lastUpdatedBy);

	/**
	 * Find all Project entities.
	 * 
	 * @return List<Project> all Project entities
	 */
	public List<Project> findAll();

	public List<Project> findAllProjects();

	public List findProjectNames();
}