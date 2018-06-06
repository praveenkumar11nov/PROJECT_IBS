package com.bcits.bfm.serviceImpl.customerOccupancyManagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Arms;
import com.bcits.bfm.service.customerOccupancyManagement.ArmsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;

@Repository
public class ArmsServiceImpl extends GenericServiceImpl<Arms> implements ArmsService
{
	@Autowired
	private MessageSource messageSource;
	
	public Locale locale;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.ArmsService#findAllArmsBasedOnPersonID(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Arms> findAllArmsBasedOnPersonID(int personId) {
		return entityManager.createNamedQuery("Arms.findAllArmsBasedOnPersonID").setParameter("personId", personId).getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.ArmsService#getArmsIdBasedOnCreatedByAndLastUpdatedDt(java.lang.String, java.sql.Timestamp)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getArmsIdBasedOnCreatedByAndLastUpdatedDt(String createdBy,
			Timestamp lastUpdatedDt) {
		return entityManager.createNamedQuery("Arms.getArmsIdBasedOnCreatedByAndLastUpdatedDt", Integer.class).setParameter("createdBy", createdBy).setParameter("lastUpdatedDt", lastUpdatedDt).getSingleResult();		
	}
	
	/* (non-Javadoc)
	 * @see com.bcits.bfm.serviceImpl.customerOccupancyManagement.ArmsService#getArmsObject(java.util.Map, java.lang.String, com.bcits.bfm.model.Arms)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Arms getArmsObject(Map<String, Object> map, String type,
			Arms arms) {
		
		if(type == "update")
		{
			arms.setArmsId((Integer) map.get("armsId"));
			arms.setPersonId((Integer) map.get("personId"));
			arms.setCreatedBy((String) map.get("createdBy"));
			arms.setDrGroupId((Integer) map.get("drGroupId"));
		}
		else if (type == "save")
		{
			arms.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
		}
		
		arms.setArmMake((String) map.get("armMake"));

		arms.setIssuingAuthority((String) map.get("issuingAuthority"));
		arms.setLicenceNo((String) map.get("licenceNo"));
		
		String licenceDate = (String) map.get("licenceValidity");
		if((licenceDate != null) && (licenceDate.length() > 0))			
			arms.setLicenceValidity(dateTimeCalender.getDate1(licenceDate));
		if(map.get("noOfRounds") instanceof java.lang.Integer)
			arms.setNoOfRounds((Integer) map.get("noOfRounds"));
		
		/*arms.setTypeOfArm(CheckAndInsertNewArm((String) map.get("typeOfArm")));*/
		arms.setTypeOfArm((String) map.get("typeOfArm"));

		arms.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));	
		arms.setLastUpdatedDt(new Timestamp(new Date().getTime()));
		
		;

		return arms;

	}

/*	@Transactional(propagation = Propagation.SUPPORTS)
	private String CheckAndInsertNewArm(String typeOfArm)
	{
		Properties prop = new Properties();
    	InputStream input = null;
    	OutputStream output = null;
    	
    	String[] msg = messageSource.getMessage("values.arms.check", null, locale).split(",");
		Set<String> msgSet = new HashSet<String>();
		for (String string : msg)
		{
			msgSet.add(string);
		}
		if(!(msgSet.contains(typeOfArm)))
		{
			msgSet.add(typeOfArm);
		}
		
		String finalMsg = "";
		
		for (Iterator<String> iterator = msgSet.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			
			finalMsg = finalMsg + string;
			finalMsg = finalMsg + ",";
		}
		finalMsg = finalMsg.substring(0, (finalMsg.length() - 1));
 
    	try 
    	{
 
    		String filename = "messages/welcome.properties";
    		input = Arms.class.getClassLoader().getResourceAsStream(filename);
    		output = new FileOutputStream("classpath:messages/welcome.properties");
    		if(input==null)
    		{
    			
    		}
    		else
    		{
    			//load a properties file from class path, inside static method
        		prop.load(input);
     
                //get the property value and print it out
              
                
               
                prop.setProperty("values.arms.check", finalMsg);
                prop.store(output, null);
    		}

    	} 
    	catch (IOException ex) 
    	{
    		ex.printStackTrace();
        } 
    	finally
    	{
        	if(input!=null)
        	{
        		try 
        		{
        			input.close();
        		} 
        		catch (IOException e) 
        		{
        			e.printStackTrace();
        		}
        	}
        	if(output!=null)
        	{
	        	try 
	    		{
	    			output.close();
	    		} 
	    		catch (IOException e) 
	    		{
	    			e.printStackTrace();
	    		}
        	}
        }
 
		return typeOfArm;
	}*/
}
