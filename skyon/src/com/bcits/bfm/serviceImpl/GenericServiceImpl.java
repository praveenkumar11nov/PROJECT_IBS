package com.bcits.bfm.serviceImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.service.GenericService;
import com.bcits.bfm.util.BfmLogger;

/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 */
@Transactional(readOnly = true)
public abstract class GenericServiceImpl<T> implements GenericService<T> {

	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * Perform an initial save of a previously unsaved Entity. All subsequent
	 * persist actions of this entity should use the #update() method. This
	 * operation must be performed within the a database transaction context for
	 * the entity's data to be permanently saved to the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * UsersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @param t
	 *            Entity to persist
	 * @return Entity instance
	 * @since 0.1
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public T save(final T t) {		
		Session session = entityManager.unwrap(Session.class);
		BfmLogger.logger.info("saving "+t+" instance");
		try {
			session.persist(t);
			BfmLogger.logger.info(t+" saved successful");
			session.flush();
			session.clear();
			return t;
			
		} catch (RuntimeException re) {
			BfmLogger.logger.error("saving "+t+" failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Entity. This operation must be performed within the a
	 * database transaction context for the entity's data to be permanently
	 * deleted from the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * UsersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @param id
	 *            Entity property
	 * @since 0.1
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(final Object id) {
		
		BfmLogger.logger.info("deleting instance");
		try {
			this.entityManager.remove(this.entityManager.getReference(type, id));
			BfmLogger.logger.info("delete successful");
		} catch (RuntimeException re) {
			BfmLogger.logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Entity and return it or a copy of it to the
	 * sender. A copy of the Users entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
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
	 * entity = UsersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @param t
	 *            Entity instance to update
	 * @return Updated entity instance
	 * @since 0.1
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public T update(final T t) {
		
		BfmLogger.logger.info("updating "+t+" instance");
		try {
			T result = this.entityManager.merge(t);
			BfmLogger.logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			BfmLogger.logger.error("update failed", re);
			throw re;
		}
	}

	/**
	 * Find all Entity instances with a specific property value.

	 * @param id
	 *            property to query
	 * @return T found by query
	 */
	@Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
	@Override
	public T find(final Object id) {		
		BfmLogger.logger.info("finding instance with id: " + id);
		try {
			return (T) this.entityManager.find(type, id);
		} catch (RuntimeException re) {
			BfmLogger.logger.error("find failed", re);
			throw re;
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public long countAll(final Map<String, Object> params) 
	{

	        final StringBuffer queryString = new StringBuffer(
	                "SELECT count(o) from ");

	        queryString.append(type.getSimpleName()).append(" o ");
	        queryString.append(this.getQueryClauses(params, null));

	        final Query query = this.entityManager.createQuery(queryString.toString());

	        return (Long) query.getSingleResult();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<?> selectObjectQuery(final Map<String, Object> params) 
	{
		final StringBuffer queryString = new StringBuffer(
	        		"SELECT o from ");

	    queryString.append(type.getSimpleName()).append(" o ");
	    queryString.append(this.getQueryClauses(params, null));

	    final Query query = this.entityManager.createQuery(queryString.toString());

	    return (List<?>) query.getResultList();
	}
	
	 private String getQueryClauses(final Map<String, Object> params, final Map<String, Object> orderParams) 
	 {
	     final StringBuffer queryString = new StringBuffer();
	     if ((params != null) && !params.isEmpty()) 
	     {
	             queryString.append(" where ");
	             for (final Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) 
	             {
	                     final Map.Entry<String, Object> entry = it.next();
	                     if (entry.getValue() instanceof Boolean) 
	                     {
	                             queryString.append(entry.getKey()).append(" is ").append(entry.getValue()).append(" ");
	                     } 
	                     else 
	                     {
	                            if (entry.getValue() instanceof Number) 
	                            {
	                                    queryString.append(entry.getKey()).append(" = ")
	                                                   .append(entry.getValue());
	                            }
	                            else 
	                            {
	                                    // string equality
	                                    queryString.append(entry.getKey()).append(" = '")
	                                                   .append(entry.getValue()).append("'");
	                            }
	                     }
	                     if (it.hasNext()) 
	                     {
	                            queryString.append(" and ");
	                     }
	             }
	     }
	     if ((orderParams != null) && !orderParams.isEmpty()) 
	     {
	             queryString.append(" order by ");
	             for (final Iterator<Map.Entry<String, Object>> it = orderParams
	                            .entrySet().iterator(); it.hasNext();) 
	             {
	                     final Map.Entry<String, Object> entry = it.next();
	                     queryString.append(entry.getKey()).append(" ");
	                     if (entry.getValue() != null) 
	                     {
	                             queryString.append(entry.getValue());
	                     }
	                     if (it.hasNext()) 
	                     {
	                            queryString.append(", ");
	                     }
	             }
	     }
	     return queryString.toString();
	}

	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.SUPPORTS)
	 @Override
	 public List<T> getByNamedQuery(String namedQuery,
			 Map<String, Object> map) {
		 Query q = entityManager.createNamedQuery(namedQuery);

		 if(map!=null){
			 Iterator<String> it = map.keySet().iterator();
			 while (it.hasNext()) {
				 String key = it.next();
				 q.setParameter(key, map.get(key));
			 }
		 }
		 return q.getResultList();
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.SUPPORTS)
	 @Override
	 public List<T> executeSimpleQuery(String query) {
		 Query q = entityManager.createQuery(query);
		 return q.getResultList();
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.SUPPORTS)
	 @Override
	 public T getSingleResult(String query) {
		 try{
			Query q = entityManager.createQuery(query);
		 	return (T) q.getSingleResult();
		 }catch(Exception e){
			//e.printStackTrace();
		 }
		 return null;
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED)
	 @Override
	 public void executeDeleteQuery(String query) {
		entityManager.createQuery(query).executeUpdate();
	 }
	 
		@Transactional(propagation = Propagation.SUPPORTS)
		@Override
		public List<?> selectQuery(final String className, final List<String> attributesList, final Map<String, Object> params) 
		{
			final StringBuffer queryString = new StringBuffer(
		        		"SELECT ");

			if((attributesList == null) || (attributesList.size() == 0))
			{
				queryString.append("o");
			}
			
			else if(attributesList.size() == 1)
			{
				queryString.append("o.");
				queryString.append(attributesList.get(0));
			}
			
			else if(attributesList.size() > 1)
			{
				for (Iterator<String> iterator = attributesList.iterator(); iterator.hasNext();)
				{
					String string = (String) iterator.next();
					
					queryString.append("o.");
					queryString.append(string);
					queryString.append(", ");
				}
				
				queryString.replace(0, queryString.length() - 1, queryString.substring(0, queryString.length() - 2));
			}
			else
				queryString.append("o");
			
			queryString.append(" from ");
		    queryString.append(className).append(" o ");
		    queryString.append(this.getQueryClauses(params, null));

		    final Query query = this.entityManager.createQuery(queryString.toString());

		    return (List<?>) query.getResultList();
		}
		
		@Override
		public int batchSave(final List<T> entityList) {				
		Session session = entityManager.unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < entityList.size(); ++i) {
			try {
				session.persist(entityList.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
				BfmLogger.logger.info(entityList.get(i) + " saved successful");
			} catch (RuntimeException re) {
				BfmLogger.logger.error("saving " + entityList.get(i)+ " failed", re);
				throw re;
			}
		}
		return insertedCount;
		} 
		
}