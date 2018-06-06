package com.bcits.bfm.patternMasterServiceImpl;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;












import com.bcits.bfm.model.Designation;
import com.bcits.bfm.patternMasterEntity.PettyTransactionManager;
import com.bcits.bfm.patternMasterEntity.TransactionMaster;
import com.bcits.bfm.patternMasterService.TransactionMasterService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;



@Repository
public class TransactionMastServiceImpl extends GenericServiceImpl<com.bcits.bfm.patternMasterEntity.TransactionMaster> implements TransactionMasterService {
	
	
	@Override
	public List<TransactionMaster> findAll() {
		return readTransactionMaster(entityManager.createNamedQuery("TransactionMaster.findAlls").getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List readTransactionMaster(List<?> transactionMaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> transactionMasterData = null;
		for (Iterator<?> iterator = transactionMaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 transactionMasterData = new HashMap<String, Object>();
		
			 
			 transactionMasterData.put("tId", (Integer)values[0]);
			 transactionMasterData.put("name",(String)values[1]);		
			 transactionMasterData.put("code",(String)values[2]);
			 transactionMasterData.put("description", (String)values[3]);
			 transactionMasterData.put("level", (Integer)values[4]);
			 transactionMasterData.put("transactionStatus", (String)values[5]);
			 transactionMasterData.put("processName", (String)values[6]);
			
		     result.add(transactionMasterData); 
		 }
		 return result;
	}

	@Override
	public List<?> findAllDept() {
		return getdeptnames(entityManager.createNamedQuery("Department.findAllDeptNames").getResultList());
	}
	@SuppressWarnings("rawtypes")
	private List getdeptnames(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("dept_Id",(Integer) values[0]);
			 storeData.put("dept_Name",(String)values[1]);		
			
		     result.add(storeData); 
		 }
		 return result;
	}
	
	@Override
	public List<?> findAllDesig() {
		return getStorenames(entityManager.createNamedQuery("Designation.findAllDesigNames").getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List getStorenames(List<?> storemaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> storeData = null;
		for (Iterator<?> iterator = storemaster.iterator(); iterator.hasNext();)
		{
			 final Designation values = (Designation) iterator.next();
			 storeData = new HashMap<String, Object>();
			 
			 storeData.put("dnId",values.getDn_Id());
			 storeData.put("dnName",values.getDn_Name());	
			 storeData.put("dept_Id",values.getDept_Id());	
			
		     result.add(storeData); 
		 }
		 return result;
	}
	@SuppressWarnings("serial")
	@Override
	public List<?> findLevel(int tId) {
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		@SuppressWarnings("unchecked")
		List<TransactionMaster> transctionMasterDataList=entityManager.createNamedQuery("TransactionMaster.findLevel").setParameter("tId", tId).getResultList();
		for (final TransactionMaster transctionMaster :transctionMasterDataList)
		{
			result.add(new HashMap<String, Object>() {	 
				
				
				{  
					put("id", transctionMaster.gettId());
					put("code",transctionMaster.getCode());
					put("level",transctionMaster.getLevel());
					put("transactionStatus",transctionMaster.getTransactionStatus());
					
				}});
				
			}
			return result;	
		
	}

	@Override
	public List<?> findAl(int processid) {
		return  readdata(entityManager.createNamedQuery("TransactionMaster.findAl").setParameter("processid", processid).getResultList());
	}
	
	@SuppressWarnings("rawtypes")
	private List readdata(List<?> transactionMaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> transactionMasterData = null;
		for (Iterator<?> iterator = transactionMaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 transactionMasterData = new HashMap<String, Object>();
		
			 transactionMasterData.put("tId", (Integer)values[0]);
			 transactionMasterData.put("name",(String)values[1]);		
			 transactionMasterData.put("code",(String)values[2]);
			 transactionMasterData.put("description", (String)values[3]);
			 transactionMasterData.put("level", (Integer)values[4]);
			 transactionMasterData.put("transactionStatus", (String)values[5]);

			 transactionMasterData.put("processId", (int)values[6]);
		     result.add(transactionMasterData); 
		 }
		 return result;
	}

	@Override
	public List<TransactionMaster> findall(String name) {
		return  readdatass(entityManager.createNamedQuery("TransactionMaster.findTransactionNames").setParameter("name", name).getResultList());
	}

	@SuppressWarnings("rawtypes")
	private List readdatass(List<?> transactionMaster)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> transactionMasterData = null;
		for (Iterator<?> iterator = transactionMaster.iterator(); iterator.hasNext();)
		{
			 final TransactionMaster values = (TransactionMaster) iterator.next();
			 transactionMasterData = new HashMap<String, Object>();
		
			 transactionMasterData.put("tId",values.gettId());	
			 transactionMasterData.put("name",values.getName());		
			
		     result.add(transactionMasterData); 
		 }
		 return result;
	}

	@Override
	public List<?> readProcessNameForUniqueness() {
		return entityManager.createNamedQuery("TransactionMaster.processNames").getResultList();
	}

	@Override
	public void setItemStatus(int tId, String operation,
			HttpServletResponse response, MessageSource messageSource,
			Locale locale) {
		
		try
		{
			PrintWriter out = response.getWriter();	
			if(operation.equalsIgnoreCase("activate"))
			{
				
				Integer level=entityManager.createNamedQuery("TransactionMaster.getLevelofTransaction",Integer.class).setParameter("tId", tId).getSingleResult();
				Integer lNum=entityManager.createNamedQuery("TransactionMaster.getLevelFromTransDetail",Integer.class).setParameter("tId", tId).getSingleResult();
				if(lNum==null){
					lNum=0;
				}
				int level1=level;
				int lNum2=lNum;
				System.out.println(level+"-------------------"+lNum);
				if(level1==0 || level1==lNum2){
				entityManager.createNamedQuery("TransactionMaster.UpdateStatus").setParameter("status", "Active").setParameter("tId", tId).executeUpdate();
				out.write("Pattern Activated");
				}else{
					out.write("Pattern Cannot Be Activated");
				}
				
			}
			else
			{
				PettyTransactionManager pettyTransactionManager =entityManager.createNamedQuery("PettyTransactionManager.findIdOfPettyTransactionManager",PettyTransactionManager.class).getSingleResult();
				if(pettyTransactionManager.gettId()==tId){
					   out.write("Pattern is under Progress so cannot be Deactivated");				
					
				}
				else{
			   entityManager.createNamedQuery("TransactionMaster.UpdateStatus").setParameter("status", "Inactive").setParameter("tId", tId).executeUpdate();
			   out.write("Pattern Deactivated");
				}
			}
		
		}
		catch(Exception e){
		   e.printStackTrace();
	    }
	}

	@Override
	public void setTallyStatusUpDateforXML(int billId) {
		// TODO Auto-generated method stub
		
	}
}
