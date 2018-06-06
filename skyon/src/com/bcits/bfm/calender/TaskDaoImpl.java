package com.bcits.bfm.calender;


import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class TaskDaoImpl extends GenericServiceImpl<Task> implements TaskDao {
    
    @Override
    public List<Task> getList() {
    	try {
			final String queryString = "select model from Task model";
			Query query = entityManager.createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {			
			throw re;
		}
    }

    @Override
    public void saveOrUpdate(List<Task> tasks) {
    	
        for (Task task : tasks) {
           save(task);
        }
    }

    @Override
    public void delete(List<Task> tasks) {        
        /*for (Task task : tasks) {           
            
            List<Task> recurrenceExceptions = criteria.list();
            
            for (Task recurrenceException : recurrenceExceptions) {
                entityManager.delete(recurrenceException);
            }
            
            entityManager.delete(task.getTaskId());
        }*/
    }

	@Override
	public void Update(List<Task> tasks) {
		for (Task task : tasks) {
	           update(task);
	    }
	}
}