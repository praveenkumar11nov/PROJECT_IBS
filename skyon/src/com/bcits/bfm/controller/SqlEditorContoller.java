package com.bcits.bfm.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SqlEditorContoller {
	@RequestMapping(value="/sql",method={RequestMethod.POST,RequestMethod.GET})
	public String getSqlEditor(){
		System.out.println("-----------Inside SqlEditorContoller.getSqlEditor()-----------");
		return "sqlEditor";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value="/getReport",method={RequestMethod.POST,RequestMethod.GET})
	public String getReport(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws IOException, SQLException, ClassNotFoundException
	{
		int live=0;
		System.out.println("ACCESS="+request.getParameter("dbaccess"));
		if(request.getParameter("dbaccess").equalsIgnoreCase("LIVE")){
			live=1;
		}
		String USERNAME=null,PASSWORD=null,DBURL=null;
		String qry=request.getParameter("qry").trim();
		if(qry!=""||qry!=null){
			if(live==1){
				USERNAME = "ireo_skyon";
				PASSWORD = "ireo_skyon";
				DBURL    = "jdbc:oracle:thin:@52.8.66.2:1521:ORCL";
			}else{
				USERNAME = "IREO_SKYON";
				PASSWORD = "skyon123";
				DBURL    = "jdbc:oracle:thin:@192.168.2.96:1521:ORCL";
			}
			Connection conn = null;
			ResultSet rs=null;
			try {
				System.out.println("::::::::::::::::QUERY::::::::::::::::\n"+qry);
				if (qry.startsWith("select") || qry.startsWith("SELECT")){
					Class.forName("oracle.jdbc.driver.OracleDriver");
					try{
						conn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
					}catch (Exception e) {
						e.printStackTrace();
					}
					if (conn == null){
						System.out.println("--------------No Connection--------------");
					}else{
						System.out.println("--------------Connection Established--------------");
					}

					Statement statement = conn.createStatement();
					rs = statement.executeQuery(qry);

					ResultSetMetaData md = rs.getMetaData();
					int count = md.getColumnCount();

					List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
					while (rs.next()){
						Map<String, Object> columns = new LinkedHashMap<String, Object>();
						for (int column = 1; column <= count; column++){
							String type = md.getColumnTypeName(column);
							if (type.equalsIgnoreCase("Date") || (type).equalsIgnoreCase("TimeStamp")){
								String date1 = "";
								SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
								if (rs.getDate(column) != null) {
									date1 = date.format(rs.getDate(column));
									columns.put(md.getColumnLabel(column),date1);
								} 
								else{
									date1 = "N/A";
								}
							}else{
								columns.put(md.getColumnLabel(column),rs.getObject(column));
							}
						}
						rows.add(columns);
						model.addAttribute("rows", rows);
						model.addAttribute("qry", qry);
					}
					return "sqlEditor";
				}else{
					model.addAttribute("msg", "Sorry!!! you have to write only SELECT queries...");
					return "sqlEditor";
				}
			}
			catch(Exception e){
				e.printStackTrace();
				model.addAttribute("msg","Sorry!!! some errors is there in query please check...");
				return "sqlEditor";
			}
			finally{
				System.out.println("--------------In finally Block--------------");
				if(conn!=null) 
					conn.close();
				if(rs!=null)   
					rs.close();
				return "sqlEditor";
			}

		}
		return "sqlEditor";

	}
}