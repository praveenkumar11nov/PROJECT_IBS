package com.bcits.bfm.serviceImpl.electricityBillsManagementImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.httpclient.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.service.electricityBillsManagement.ElectricityAMRBillService;

@Repository
public class ElectricityAMRBillServiceImpl  implements ElectricityAMRBillService   {
	
	@PersistenceContext(unitName="MSSQLDataSource")
	public EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public float getPresentReading(String tableName, String columnName,Date previousDate,Date presentdate) {
		/*String cname=columnName.toLowerCase()+"Kwh";
		String queryString = "select Round (Max(tw."+cname+"),0) from "+tableName+" tw where  tw.datestr <= '"+ new java.sql.Date(presentdate.getTime())+"'";
		float uomvalue=0f;
		@SuppressWarnings("unchecked")
		List<Double> ls=entityManager.createQuery(queryString).getResultList();
		for (Double object : ls) {
			uomvalue=object.floatValue();
		}
		return uomvalue;*/
		
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(presentdate);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
	    Date month1=(c.getTime());	
		
		String queryString = "select Round (tw."+columnName.toLowerCase()+"Kwh"+",0) from "+tableName+" tw where  tw.datestr >= '"+ new java.sql.Date(presentdate.getTime())+"' AND tw.datestr < '"+ new java.sql.Date(month1.getTime())+"' order by tw.datestr DESC";
		Query query = entityManager.createQuery(queryString); 
		Double unit=(Double) query.setMaxResults(1).getSingleResult();
		return unit.floatValue();
	}

	@Override
	public List<?> findAMRDataReading(String columnName, String blockName) {
		String queryString = "select tw.datestr, Round (tw."+columnName.toLowerCase()+"Kwh"+",0)  from "+"Tower_"+blockName.substring(blockName.length() - 1)+"_EB_kwh tw order by tw.datestr DESC";
		Query query = entityManager.createQuery(queryString);  
		/*query.setHint("org.hibernate.cacheable", Boolean.TRUE);*/
		return query.setMaxResults(1).getResultList();
		//return entityManager.createQuery(queryString).setMaxResults(1).getResultList();
	}

	@Override
	public Timestamp findAMRDataDate(String columnName, String blockName) {
		Timestamp timestamp;
		String queryString = "select Max(tw.datestr) from "+"Tower_"+blockName.substring(blockName.length() - 1)+"_EB_kwh tw";
		timestamp = (Timestamp)entityManager.createQuery(queryString).setMaxResults(1).getSingleResult();
		return timestamp;
	}

	@Override
	public List<?> findAMRDGDataReading(String columnName, String blockName) {
		String queryString = "select Round (tw."+columnName.toLowerCase()+"Kwh"+",0) from "+"Tower_"+blockName.substring(blockName.length() - 1)+"_DG_kwh tw order by tw.datestr DESC";
		Query query = entityManager.createQuery(queryString);  
		/*query.setHint("org.hibernate.cacheable", Boolean.TRUE);*/
		return query.setMaxResults(1).getResultList();
	}

	@Override
	public float getPresentDGReading(String tableName, String columnName,Date getbilldate, Date presentdate) {
		/*String cname=columnName.toLowerCase()+"Kwh";
		String queryString = "select Round (Max(tw."+cname+"),0) from "+tableName+" tw where  tw.datestr <= '"+ new java.sql.Date(presentdate.getTime())+"'";
		float uomvalue=0f;
		@SuppressWarnings("unchecked")
		List<Double> ls=entityManager.createQuery(queryString).getResultList();
		for (Double object : ls) {
			uomvalue=object.floatValue();
		}
		return uomvalue;*/
		
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(presentdate);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
	    Date month1=(c.getTime());	
		
		String queryString = "select Round (tw."+columnName.toLowerCase()+"Kwh"+",0) from "+tableName+" tw where  tw.datestr >= '"+ new java.sql.Date(presentdate.getTime())+"' AND tw.datestr < '"+ new java.sql.Date(month1.getTime())+"' order by tw.datestr DESC";
		Query query = entityManager.createQuery(queryString);
		Double unit=(Double) query.setMaxResults(1).getSingleResult();
		return unit.floatValue();
	}

	@Override
	public List<?> findAMRDataReadingOnDate(String columnName, String blockName,Date month,Date month1) {
	
		String queryString = "select tw.datestr, Round (tw."+columnName.toLowerCase()+"Kwh"+",0) from "+"Tower_"+blockName.substring(blockName.length() - 1)+"_EB_kwh tw where  tw.datestr >= '"+ new java.sql.Date(month.getTime())+"' AND tw.datestr < '"+ new java.sql.Date(month1.getTime())+"' order by tw.datestr DESC";
		Query query = entityManager.createQuery(queryString);  
		
		/*query.setHint("org.hibernate.cacheable", Boolean.TRUE);*/
		return query.setMaxResults(1).getResultList();
		//return entityManager.createQuery(queryString).setMaxResults(1).getResultList();
	}

	@Override
	public List<?> findAMRDGDataReadingOnDate(String columnName, String blockName,Date month, Date month1) {
		String queryString = "select Round (tw."+columnName.toLowerCase()+"Kwh"+",0) from "+"Tower_"+blockName.substring(blockName.length() - 1)+"_DG_kwh tw where  tw.datestr >= '"+ new java.sql.Date(month.getTime())+"' AND tw.datestr < '"+ new java.sql.Date(month1.getTime())+"' order by tw.datestr DESC";
		Query query = entityManager.createQuery(queryString);  
		/*query.setHint("org.hibernate.cacheable", Boolean.TRUE);*/
		return query.setMaxResults(1).getResultList();
	}

}
