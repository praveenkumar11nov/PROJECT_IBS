package com.bcits.bfm.serviceImpl.helpDeskManagementImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.CustomerCareNotification;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.helpDeskManagement.CustomerCareNotificationService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class CustomerCareNotificationServiceImpl extends GenericServiceImpl<CustomerCareNotification> implements CustomerCareNotificationService {

	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private PropertyService propertyService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public CustomerCareNotification getObject(Map<String, Object> map,
			String type, CustomerCareNotification customerCareNotification,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("userId");
		Set<Integer> blockIdSet = new HashSet<Integer>();
		String blockIds="";
		String propertNos ="";
		
		if( type == "save" ){
			String radioBtn = (String)map.get("radioBtn");
			String radioBtn2 = (String)map.get("radioBtn2");
			if( radioBtn.equals("property") )
			{
				final List<Map<String,Object>> propertyMap = (List<Map<String,Object>>)map.get("propertyNo");
				for (Iterator iterator = propertyMap.iterator(); iterator.hasNext();) {
					Map<String, Object> map2 = (Map<String, Object>) iterator.next();
					
					propertNos = propertNos + map2.get("propertyId")+","; 
					System.out.println(propertNos);
					int blckId = propertyService.getBlockIdBasedOnPropertyId((Integer)map2.get("propertyId"));
					blockIdSet.add(blckId);
					
					
				}
				String propertyNo = propertNos.substring(0,propertNos.length()-1); 
				System.out.println(propertyNo);
				customerCareNotification.setPropertyId(propertyNo);
				for( Integer rec : blockIdSet){
					blockIds = blockIds+rec+",";
				}
				String finalBkId = blockIds.substring(0,blockIds.length()-1);
				customerCareNotification.setBlockId(finalBkId);
			}
			if( radioBtn.equals("block") )
			{
				if( radioBtn2.equals("all") )
				{
					customerCareNotification.setPropertyId("All Properties Of This Block");
					customerCareNotification.setBlockId("All Blocks");
				}
				else{
				final List<Map<String,Object>> blockMap = (List<Map<String,Object>>)map.get("blockName");
				for (Iterator iterator = blockMap.iterator(); iterator.hasNext();) {
					Map<String, Object> map2 = (Map<String, Object>) iterator.next();
					
					blockIds = blockIds + map2.get("blockId")+","; 
					
				}
				String blockId = blockIds.substring(0,blockIds.length()-1); 
				customerCareNotification.setPropertyId("All Properties Of This Block");
				customerCareNotification.setBlockId(blockId);
				}
			}
			customerCareNotification.setSubject((String)map.get("subject"));
			customerCareNotification.setMessage((String)map.get("message"));
			customerCareNotification.setViewStatus((Integer)map.get("viewStatus"));
			customerCareNotification.setCreatedBy(userName);
			customerCareNotification.setLastUpdatedBy(userName);
			
		}
		
		return customerCareNotification;
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getNotificationObject() {
		@SuppressWarnings("unchecked")
		List<CustomerCareNotification> customerCareNotificationList =entityManager.createNamedQuery("CustomerCareNotification.findAll").getResultList();
		List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>(); 
		for (final CustomerCareNotification record : customerCareNotificationList) {
			
			
			list.add(new HashMap<String, Object>() {{
				put("gnId", record.getCnId());
				put("message",record.getMessage());
				put("subject",record.getSubject());
				
				String blockids = record.getBlockId();
				if( blockids.equals("None") ||  blockids.equals("All Blocks") ){
					put("blockName",blockids);
				}
				else{
				String[] splitedBlockids = blockids.split(",");
				String blockNames = "";
				for (int i = 0; i < splitedBlockids.length; i++) {
					int blockId = Integer.parseInt(splitedBlockids[i]);
					Blocks block = blocksService.getBlockNameByBlockId(blockId);
					blockNames = blockNames+block.getBlockName()+",";
					
				}
				 String finalBlockNamea = blockNames.substring(0,blockNames.length()-1);
				put("blockName",finalBlockNamea);
				put("blockId","");
				}
			
				String propertyids = record.getPropertyId();
				if( propertyids.equals("All Properties") ||  propertyids.equals("All Properties Of This Block") ){
					put("propertyNo",propertyids);
				}
				else{
				String[] splitedPropertyIds = propertyids.split(",");
				String propertyNos = "";
				for (int i = 0; i < splitedPropertyIds.length; i++) {
					int propertyId = Integer.parseInt(splitedPropertyIds[i]);
					String propertyNo = propertyService.getPropertyNameBasedOnPropertyId(propertyId);
					propertyNos = propertyNos+propertyNo+",";
					
				}
				 String finalPropertyNos = propertyNos.substring(0,propertyNos.length()-1);
				put("propertyNo",finalPropertyNos);
				put("propertyId","");
				}
				put("cnId",record.getCnId());
				put("viewStatus",record.getViewStatus());
				put("createdBy",record.getCreatedBy());
				put("lastUpdatedBy",record.getLastUpdatedBy());
				
			}});
			
		}
		
		return list;
	}
}
