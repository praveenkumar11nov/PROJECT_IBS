package com.bcits.bfm.util;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang.builder.*;

public class JPQLQueryChecker
{
	public static void main(String[] args) throws Exception 
	{
		//String unitName = args[0];
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bfm");
		EntityManager em = emf.createEntityManager();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		for (;;) 
		{
			System.out.print("JP QL> ");
			String query = reader.readLine();
			
			if (query.equals("quit")) 
			{
				break;
			}
			
			if (query.length() == 0) 
			{
				continue;
			}
			
			try 
			{
				@SuppressWarnings("rawtypes")
				List result = em.createQuery(query).getResultList();
				if (result.size() > 0) 
				{
					int count = 0;
					for (Object o : result) 
					{
						System.out.print(++count + " ");
						printResult(o);
					}
				} 
				else 
				{
					System.out.println("0 results returned");
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void printResult(Object result) throws Exception 
	{
		if (result == null) 
		{
			System.out.print("NULL");
		} 
		else if (result instanceof Object[]) 
		{
			Object[] row = (Object[]) result;
			System.out.print("[");
			
			for (int i = 0; i < row.length; i++) 
			{
				printResult(row[i]);
			}
			System.out.print("]");
		} 
		else if (result instanceof Long || result instanceof Double || result instanceof String) 
		{
			System.out.print(result.getClass().getName() + ": " + result);
		} 
		else 
		{
			System.out.print(ReflectionToStringBuilder.toString(result, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		System.out.println();
	}
}

