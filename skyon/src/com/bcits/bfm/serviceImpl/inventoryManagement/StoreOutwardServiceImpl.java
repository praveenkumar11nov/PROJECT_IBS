package com.bcits.bfm.serviceImpl.inventoryManagement;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreOutward;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.VendorsManagement.VendorsService;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.service.inventoryManagement.StoreOutwardService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoreOutward entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .StoreOutward
 * @author MyEclipse Persistence Tools
 */
@Repository
public class StoreOutwardServiceImpl extends GenericServiceImpl<StoreOutward> implements StoreOutwardService {
	
	@Autowired
	private ItemMasterService itemMasterService;
	@Autowired
	private StoreMasterService storeMasterService;
	@Autowired
	private UomService uomService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private VendorsService vendorsService;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<StoreOutward> findAll() {
		
	    return entityManager.createNamedQuery("StoreOutward.findAll").getResultList();
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> readAll() {
		
		List<?> storeOutwardList = entityManager.createNamedQuery("StoreOutward.findAll").getResultList();

		List<Map<String, Object>> storeOutwards = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = storeOutwardList.iterator(); i.hasNext();) {
			storeOutwards.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("storeoutwardId", (Integer)values[0]);
					put("storeName", (String)values[7]);
					put("storeId", (Integer)values[6]);
					put("imName", (String)values[5]);
					put("imId", (Integer)values[4]);
					put("uom", (String)values[15]);
					put("uomId", (Integer)values[14]);
					put("itemReturnQuantity", (Double)values[16]);
					put("returnedby", (String)values[8]+" " +(String)values[9]);
					put("returnedbyId", (Integer)values[12]);
					put("returnedto", (String)values[10]+ " "+(String)values[11]);
					put("returnedtoId", (Integer)values[13]);
					put("reason", (String)values[1]);
					put("createdBy", (String)values[2]);					
					put("status", (String)values[3]);
					
				}
			});
		}
		return storeOutwards;
	}

	@Override
	public StoreOutward setParameters(Map<String, Object> map,String user) {
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		StoreOutward storeOutward=new StoreOutward();
		storeOutward.setCreatedBy(user);
		storeOutward.setLasUpdatedBy(user);
		storeOutward.setDateOfReturn(new Timestamp(new Date().getTime()));
		storeOutward.setItemMasterId(Integer.parseInt(map.get("imName").toString()));
		storeOutward.setItemReturnQuantity( Double.parseDouble(decimalFormat.format(map.get("itemReturnQuantity"))));
		storeOutward.setReasonForReturn((String) map.get("reason"));
		storeOutward.setStoreMasterId(Integer.parseInt(map.get("storeName").toString()));
		storeOutward.setUomId(Integer.parseInt(map.get("uom").toString()));
		storeOutward.setUserId(Integer.parseInt(map.get("returnedby").toString()));
		storeOutward.setVendorId(Integer.parseInt(map.get("returnedto").toString()));
		storeOutward.setStatus("Approve");
		return storeOutward;
	}

	@Override
	public StoreOutward setParametersforupdate(Map<String, Object> map,	String user) {
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		StoreOutward storeOutward=new StoreOutward();
		storeOutward.setCreatedBy((String) map.get("createdBy"));
		storeOutward.setSrId(Integer.parseInt(map.get("storeoutwardId").toString()));
		storeOutward.setLasUpdatedBy(user);
		storeOutward.setDateOfReturn(new Timestamp(new Date().getTime()));
		if(map.get("imName") instanceof String){
			storeOutward.setItemMasterId(Integer.parseInt(map.get("imId").toString()));
		}else{
			storeOutward.setItemMasterId(Integer.parseInt(map.get("imName").toString()));
		}
		storeOutward.setItemReturnQuantity( Double.parseDouble(decimalFormat.format(map.get("itemReturnQuantity"))));
		if(map.get("storeName") instanceof String){
			storeOutward.setStoreMasterId(Integer.parseInt(map.get("storeId").toString()));
		}else{
			storeOutward.setStoreMasterId(Integer.parseInt(map.get("storeName").toString()));
		}
		storeOutward.setReasonForReturn((String) map.get("reason"));
		if(map.get("uom") instanceof String){
			storeOutward.setUomId(Integer.parseInt(map.get("uomId").toString()));
		}else{
			storeOutward.setUomId(Integer.parseInt(map.get("uom").toString()));
		}
		if(map.get("returnedby") instanceof String){
			storeOutward.setUserId(Integer.parseInt(map.get("returnedbyId").toString()));
		}else{
			storeOutward.setUserId(Integer.parseInt(map.get("returnedby").toString()));
		}
		if(map.get("returnedto") instanceof String){
			storeOutward.setVendorId(Integer.parseInt(map.get("returnedtoId").toString()));
		}else{
			storeOutward.setVendorId(Integer.parseInt(map.get("returnedto").toString()));
		}
		storeOutward.setStatus((String) map.get("status"));
		return storeOutward;
	}

	@Override
	public void setoutStatus(int psId, String operation,
			HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			if (operation.equalsIgnoreCase("activate")) {
				entityManager.createNamedQuery("StoreOutward.UpdateStatus")
						.setParameter("psStatus", "Approved")
						.setParameter("psId", psId).executeUpdate();
				out.write("Stock Outward is Appoved");
			} else {				
				out.write("Stock Outward Already Approved");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> readAllStore() {
		return entityManager.createNamedQuery("StoreOutward.findAll").getResultList();
	}	
}