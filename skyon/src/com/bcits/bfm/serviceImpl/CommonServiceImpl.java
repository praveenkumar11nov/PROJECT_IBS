package com.bcits.bfm.serviceImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Common;
import com.bcits.bfm.service.CommonService;

@Repository
public class CommonServiceImpl extends GenericServiceImpl<Common> implements CommonService
{
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<?> selectQueryOrderBy(final String className, final List<String> attributesList, final Map<String, Object> params,Map<String, Object> orderByList) 
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
	    queryString.append(this.getQueryClauses(params, orderByList));

	    final Query query = this.entityManager.createQuery(queryString.toString());
	    return (List<?>) query.getResultList();
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
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Date getMaxDate(final String className, final List<String> attributesList, final Map<String, Object> params) 
	{
		final StringBuffer queryString = new StringBuffer(
	        		"SELECT ");

		if((attributesList == null) || (attributesList.size() == 0))
		{
			queryString.append("o");
		}
		
		else if(attributesList.size() == 1)
		{
			queryString.append(" Max (o.");
			queryString.append(attributesList.get(0));
			queryString.append(")");
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

	    System.out.println("Query Strig gor get max date is :"+queryString);
	    final Query query = this.entityManager.createQuery(queryString.toString());

	    return (Date) query.getSingleResult();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> selectDistinctQuery(String className,
			List<String> attributesList, Map<String, Object> params)
	{
		final StringBuffer queryString = new StringBuffer(
        		"SELECT DISTINCT ");

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
	
	 @Override
		//select p.personId, p.firstName, p.lastName, p.personType, p.personStyle from Account a Inner join a.person p order by p.firstName
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<?> getPersonNamesFilterList(final String className, final String personFieldName) 
		{
			final StringBuffer queryString = new StringBuffer(
	        		"SELECT DISTINCT p.personId, p.firstName, p.lastName, p.personType, p.personStyle FROM ");

		    queryString.append(className).append(" o ");
		    queryString.append("INNER JOIN o.");
		    queryString.append(personFieldName).append(" x ");
		    queryString.append("INNER JOIN x.person p ");
 		    queryString.append("ORDER BY p.firstName");
		
		    final Query query = this.entityManager.createQuery(queryString.toString());
		
		    return (List<?>) query.getResultList();
		}
}
