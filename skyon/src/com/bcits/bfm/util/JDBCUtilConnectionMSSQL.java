package com.bcits.bfm.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtilConnectionMSSQL {
	/*public static void main(String[] args) {
		 String url = "jdbc:sqlserver://192.168.2.147\\SQLEXPRESS_sa:1433;integratedSecurity=true;";
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        try {
				Class.forName(driver).newInstance();
				 Connection conn = DriverManager.getConnection(url);
			        System.out.println("conn ============ "+conn);
			} catch (InstantiationException | IllegalAccessException | SQLException
					| ClassNotFoundException e) {
				e.printStackTrace();
			} 
	       
	}*/
  public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://192.168.2.147\\SQLEXPRESS_sa:1433;integratedSecurity=true;";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }
}
