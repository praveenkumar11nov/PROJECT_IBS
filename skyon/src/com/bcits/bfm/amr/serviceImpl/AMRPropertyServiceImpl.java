package com.bcits.bfm.amr.serviceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.amr.service.AMRPropertyService;
import com.bcits.bfm.model.AMRData;
import com.bcits.bfm.model.AMRProperty;
import com.bcits.bfm.model.ElectricityMetersEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityAMRBillService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DataSourceRequest;
import com.bcits.bfm.util.DataSourceRequest.GroupDescriptor;
import com.bcits.bfm.util.DataSourceResult;
/**
 * @author user51
 *
 */
@Repository
public class AMRPropertyServiceImpl extends GenericServiceImpl<AMRProperty> implements AMRPropertyService {
	/**
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(AMRPropertyServiceImpl.class);
	
	@Autowired
	ElectricityAMRBillService amrBillService;
	
	/**
	 */
	@Override
	public List<?> getAllBlocks() {
		return entityManager.createNamedQuery("AMRProperty.getAllBlocks").getResultList();
	}
	/**
	 */
	@Override
	public List<?> getAllProperty() {
		return entityManager.createNamedQuery("AMRProperty.getAllProperty").getResultList();
	}
	/**
	 */
	@Override
	public List<?> findALL() {
		return entityManager.createNamedQuery("AMRProperty.findALL").getResultList();
	}
	/**
	 */
	@Override
	public void setAMRStatus(final int amrId, final String operation,final HttpServletResponse response) {
		try
		{
			final PrintWriter out = response.getWriter();
			if("activate".equalsIgnoreCase(operation))
			{
				entityManager.createNamedQuery("AMRProperty.setAMRStatus").setParameter("status", "Active").setParameter("amrId", amrId).executeUpdate();
				out.write("AMR Setting is actived");
			}
			else{
				entityManager.createNamedQuery("AMRProperty.setAMRStatus").setParameter("status", "Inactive").setParameter("amrId", amrId).executeUpdate();
				out.write("AMR Setting is inactived");
			}
		} 
		catch (IOException e){
			LOGGER.info("========== Error while creating print write object ===============");
		}
	}
	/**
	 */
	@Override
	public void activateAll(final HttpServletResponse response) {
		try
		{
			final PrintWriter out = response.getWriter();
		    entityManager.createNamedQuery("AMRProperty.activateAll").setParameter("status", "Active").executeUpdate();
		    out.write("All records activated");
		} 
		catch (IOException e){
			LOGGER.info("========== Error while creating print write object ===============");
		}
	}
	/**
	 */
	@Override
	public String getColumnName(final int blockId, final int propertyId) {
		try {
			 return entityManager.createNamedQuery("AMRProperty.getColumnName",String.class).setParameter("blockId", blockId).setParameter("propertyId", propertyId).getSingleResult();	
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public String getMeterNumber(Integer propertyId) {
		try {
			 return entityManager.createNamedQuery("AMRProperty.getMeterNumber",String.class).setParameter("propertyId", propertyId).getSingleResult();
		} catch (Exception e) {
		 return " ";
		}
	}

	@Override
	public String getPersonName(Integer propertyId) {
		String personName = "";
		List<?> rateMasterList = entityManager.createNamedQuery("AMRProperty.getPersonName").setParameter("propertyId", propertyId).getResultList();
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			personName=(String)values[0]+" "+(String)values[1];	
		}
		 return personName;
	}
	@Override
	public String getAccountNumber(Integer propertyId) {
		try {
			 return entityManager.createNamedQuery("AMRProperty.getAccountNumber",String.class).setParameter("propertyId", propertyId).getSingleResult();
		} catch (Exception e) {
		 return " ";
		}
	}
	@Override
	public String getMeterNumberByAccountNumber(String accountNumber) {
		try {
			 return entityManager.createNamedQuery("AMRProperty.getMeterNumberByAccountNumber",String.class).setParameter("accountNumber", accountNumber).getSingleResult();
		} catch (Exception e) {
		 return " ";
		}
	}
	@Override
	public List<?> getAMRAccountDetails(Integer blockId) {
		
		//select eme.meterSerialNo,a.accountNo,p.firstName,p.lastName,(select pp.property_No from Property pp where pp.propertyId = a.propertyId),(select pp.blocks.blockName from Property pp where pp.propertyId = a.propertyId),(select amr.columnName from AMRProperty amr where amr.propertyId = a.propertyId) from ElectricityMetersEntity eme inner join eme.account a inner join a.person p"),
		
		//entityManager.c
		if(blockId==111222){
		
			return entityManager.createNamedQuery("AMRProperty.getAMRAccountDetails").getResultList();
		}
		return entityManager.createNativeQuery("SELECT eme.METER_SL_NO,a.ACCOUNT_NO,p.FIRST_NAME,p.LAST_NAME,pp.PROPERTY_NO,b.BLOCK_NAME,amr.COLUMN_NAME FROM AMR_PROPERTY amr INNER JOIN ACCOUNT a ON a.PROPERTYID=amr.PROPERTY_ID INNER JOIN METER eme ON eme.ACCOUNT_ID=a.ACCOUNT_ID INNER JOIN PERSON p ON p.PERSON_ID=a.PERSON_ID INNER JOIN PROPERTY pp ON pp.PROPERTY_ID=a.PROPERTYID INNER JOIN BLOCK  b  ON b.BLOCK_ID=pp.BLOCK_ID where amr.BLOCK_ID='"+blockId+"' ").getResultList();
				
				//
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findALLAmr() {
		return entityManager.createNamedQuery("AMRProperty.findALL").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAMRDataDetails() {
		 return entityManager.createNamedQuery("AMRProperty.getAMRAccountDetails").getResultList();
	}
	
	@Override
	public DataSourceResult getList(DataSourceRequest request) {
		Session session = entityManager.unwrap(Session.class);
		Class<?> clazz =ElectricityMetersEntity.class;
		Criteria criteria = session.createCriteria(clazz,"eme");
		DataSourceResult dataSourceResult = request.toDataSourceResult(criteria,clazz,session);
		List<GroupDescriptor> groups = request.getGroup();  
        if (groups != null && !groups.isEmpty()) {        	
        	dataSourceResult.setData(request.group(dataItems(criteria), session,clazz));
        } else {
        	criteria.addOrder(Order.desc("id"));
        	dataSourceResult.setData(dataItems(criteria));
        }
        return dataSourceResult;
	}
	
	private List<AMRData> dataItems(Criteria criteria){
		 criteria.createAlias("eme.account","a");
		 criteria.createAlias("a.person","p");
		 ProjectionList proList=Projections.projectionList();
		 proList.add(Projections.property("eme.meterSerialNo"),"meterSerialNo");
		 proList.add(Projections.property("a.accountNo"),"accountNo");
	     proList.add(Projections.property("p.firstName"),"firstName");
	     proList.add(Projections.property("p.lastName"),"lastName");
	     criteria.setProjection(proList);
	   	 List<Object[]> data = criteria.list();
	   	int i = 1;
		List<AMRData> amrData=new ArrayList<>();
		for (Object[] list: data) {
			AMRData data2 =new AMRData();
			List<?> blocks=getBlockProperty((String)list[1]);
			for (final Iterator<?> iterator = blocks.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();
				data2.setPropertyName((String)values[0]);
				data2.setBlocksName((String)values[1]);
				
				final List<?> amrDate = amrBillService.findAMRDataReading((String)values[2],(String)values[1]);
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();
					data2.setPresentReading((Double)valuesAMR[1]);
					java.sql.Timestamp timeStamp = (Timestamp) valuesAMR[0];
					java.sql.Date date = new java.sql.Date(timeStamp.getTime()); 
					data2.setReadingDate(date);
				}
				final List<?> amrDGDate = amrBillService.findAMRDGDataReading((String)values[2],(String)values[1]);
				for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){
					data2.setPresentDGReading((Double)iteratorDGAMR.next());
				}
				
			}
			data2.setId(i);
			data2.setMeterNumber((String)list[0]);
			data2.setAccountNumber((String)list[1]);
			data2.setPersonName((String)list[2] + " "+(String)list[3]);
			amrData.add(data2);
			i=+1;
	} 
	return amrData;
	}
	private List<?> getBlockProperty(String accountNo) {
		 return entityManager.createNamedQuery("AMRProperty.getBlockProperty").setParameter("accountNo", accountNo).getResultList();
	}
	
}
