package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.AssetPhysicalSurvey;
import com.bcits.bfm.service.facilityManagement.AssetPhysicalSurveyService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetPhysicalSurveyServiceImpl extends GenericServiceImpl<AssetPhysicalSurvey> implements AssetPhysicalSurveyService
{

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() {
		List<AssetPhysicalSurvey> physicalSurveyList =  entityManager.createNamedQuery("AssetPhysicalSurvey.findAllList").getResultList();		
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Iterator<?> i = physicalSurveyList.iterator(); i.hasNext();) {
			response.add(new HashMap<String, Object>() {{
				
				final Object[] values = (Object[])i.next();
				put("apsmId", (Integer)values[0]);
				
				if((Date)values[1]!=null){
				java.util.Date apsmDateUtil = (Date)values[1];
				java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
				put("apsmDate", apsmDateSql);
				}else{
					put("apsmDate", "");
				}
				
				if((Date)values[2]!=null){
				java.util.Date surveyDateUtil = (Date)values[2];
				java.sql.Date surveyDateSql = new java.sql.Date(surveyDateUtil.getTime());
				put("surveyDate",surveyDateSql);
				}
				else{
					put("surveyDate", "");
				}
				put("surveyName", (String)values[3]);
				put("surveyDescription", (String)values[4]);
				
				if((Date)values[5]!=null){
				java.util.Date surveyCompleteDateUtil = (Date)values[5];
				java.sql.Date surveyCompleteDateSql = new java.sql.Date(surveyCompleteDateUtil.getTime());
				put("surveyCompleteDate", surveyCompleteDateSql);
				}
				else{
					put("surveyCompleteDate", "");
				}
				
				put("assetCatId", (Integer)values[6]);
				put("assetCatHierarchy", (String)values[7]);
				
				put("assetLocId", (Integer)values[8]);
				put("assetLocHierarchy", (Integer)values[9]);
				
				put("assetCatIds", (String)values[10]);
				put("assetLocIds", (String)values[11]);
				
				put("physicalSurveyStatus", (String)values[12]);
				
				put("createdBy", (String)values[13]);
				put("lastUpdatedBy", (String)values[14]);
				
				java.util.Date lastUpdatedDtUtil = (Date)values[15];
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
				
				
			}});
		}
		return response;
	}

	@Override
	public void setPhysicalSurveyStatus(int apsmId, String operation,
			HttpServletResponse response) {
		
			try
			{
				PrintWriter out = response.getWriter();

				/*if(operation.equalsIgnoreCase("Started"))
				{
					entityManager.createNamedQuery("AssetPhysicalSurvey.setStatus").setParameter("physicalSurveyStatus", "Started").setParameter("apsmId", apsmId).executeUpdate();
					out.write(operation);
				}
				else
				{*/
				java.util.Date date= new java.util.Date();
				if(StringUtils.containsIgnoreCase(operation, "Started"))
					entityManager.createNamedQuery("AssetPhysicalSurvey.setStatusStarted").setParameter("physicalSurveyStatus", operation).setParameter("apsmId", apsmId).setParameter("surveyDate",new Timestamp(date.getTime())).executeUpdate();
				else if(StringUtils.containsIgnoreCase(operation, "Completed"))
					entityManager.createNamedQuery("AssetPhysicalSurvey.setStatus").setParameter("physicalSurveyStatus", operation).setParameter("apsmId", apsmId).setParameter("surveyCompleteDate",new Timestamp(date.getTime())).executeUpdate();
				else
					entityManager.createNamedQuery("AssetPhysicalSurvey.setStatus").setParameter("physicalSurveyStatus", operation).setParameter("apsmId", apsmId).setParameter("surveyCompleteDate",null).executeUpdate();
				
				out.write(operation);
				//}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		
	}


	

}
