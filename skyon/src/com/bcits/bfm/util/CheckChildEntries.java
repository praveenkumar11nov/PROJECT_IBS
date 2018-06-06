package com.bcits.bfm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CheckChildEntries
{
	@Value("${datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${datasource.url}")
	private String url;
	
	@Value("${datasource.username}")
	private String username;
	
	@Value("${datasource.password}")
	private String password;
	
	public Set<String> getChildEntries(String fieldName, int id, String parentClasses)
	{
		Set<String> entrySet = new HashSet<String>();
		
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		 
		try {
 
			Class.forName(driverClassName);
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 
		Connection connection1 = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
 
		try {
 
			connection1 = DriverManager.getConnection(
					url, username,
					password);
			stmt1 = connection1.createStatement();
			
			/*rs = stmt.executeQuery("SELECT DISTINCT a.table_name, "
					+ " a.column_name, "
					+ " a.constraint_name , "
					+ " c.owner "
					+ " FROM ALL_CONS_COLUMNS A, ALL_CONSTRAINTS C "
					+ " where A.CONSTRAINT_NAME = C.CONSTRAINT_NAME "
					+ " and C.CONSTRAINT_TYPE = 'R' "
					+ " and c.owner like 'IREO_2' AND a.column_name='PERSON_ID' ORDER BY a.table_name");*/
			
			rs1 = stmt1.executeQuery("SELECT DISTINCT a.table_name"
					+ " FROM ALL_CONS_COLUMNS A, ALL_CONSTRAINTS C "
					+ " where A.CONSTRAINT_NAME = C.CONSTRAINT_NAME "
					+ " and C.CONSTRAINT_TYPE = 'R' "
					+ " and c.owner like 'IREO_2' AND a.column_name='"+fieldName+"'");
			
			while(rs1.next()) 
			{
			   String className = rs1.getString("table_name"); 
			   stmt2 = connection1.createStatement();
			   rs2 = stmt2.executeQuery("SELECT "+fieldName+" FROM "+className+" WHERE "+fieldName+" = "+id);
			   while(rs2.next())
			   {
				   if(!(className.contains(parentClasses)))
					   entrySet.add(className);
			   }
			    	 
			}
			return entrySet;
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
		finally
		{
			if (connection1 != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		}
		
		return null;
	}
}
