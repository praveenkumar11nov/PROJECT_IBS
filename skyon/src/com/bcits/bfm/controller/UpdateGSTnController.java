package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.service.PrepaidPaymentAdjustmentService;
import com.bcits.bfm.util.JsonResponse;

@Controller
public class UpdateGSTnController 
{
	static Logger logger = LoggerFactory.getLogger(UpdateGSTnController.class);
	
	@PersistenceContext(unitName="bfm")
	protected EntityManager entityManager;
	
	@Autowired
	private PrepaidPaymentAdjustmentService prepaidPaymentAdjustmentService;
	
	@RequestMapping(value="/updategst")
	public String updategst(Model model)
	{
		return "updateGSTn";
	}
	
	@RequestMapping(value="/camBills/getPropertyNumbers")
	 public @ResponseBody List<?> readPropNums(){
		 
		 List<Map<String, Object>> resultList=new ArrayList<>();
		 Map<String, Object> mapList=null;
		 
		 String query = "SELECT x.PROPERTY_ID,x.PROPERTY_NO FROM("
					+ "	SELECT a.PERSON_ID,p.PROPERTY_ID,p.PROPERTY_NO 	FROM ACCOUNT a LEFT JOIN PROPERTY p ON a.PROPERTYID=p.PROPERTY_ID)X "
					+ " LEFT JOIN PERSON pe ON X.PERSON_ID=PE.PERSON_ID ORDER BY x.PROPERTY_NO ASC";

		   List<?> list2 = entityManager.createNativeQuery(query).getResultList();
		// List<?> adjustList=prepaidPaymentAdjustmentService.ReadPropertys();
		 
		 for(Iterator<?> iterator=list2.iterator();iterator.hasNext();){
			 final Object[] value=(Object[]) iterator.next();
			 mapList=new HashMap<>();
			 mapList.put("propertyId", value[0]);
			 mapList.put("property_No", value[1]);
			 resultList.add(mapList);
		 }
		 
		 return resultList;
	 }
	
	@RequestMapping(value = "/camBills/readGstnTable", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String,String>> readGstnTable(HttpServletRequest request)
	{
		System.err.println("====================================readGstnTable Method====================================");
		List<?> list2 = entityManager.createNativeQuery("SELECT * FROM GSTN_DETAILS").getResultList();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> wizardMap = null;
		
		for (Iterator<?> iterator = list2.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();
			wizardMap = new HashMap<String, String>();
			wizardMap.put("rowid", String.valueOf(values[0]));
			wizardMap.put("accountid", String.valueOf(values[1]));
			wizardMap.put("propertyId", String.valueOf(values[2]));
			wizardMap.put("personid", String.valueOf(values[3]));
			wizardMap.put("accountno", String.valueOf(values[4]));
			wizardMap.put("property_No", String.valueOf(values[5]));
			wizardMap.put("personname", String.valueOf(values[6]));
			wizardMap.put("gstin", String.valueOf(values[7]));
			
			result.add(wizardMap);
		}System.err.println(result);
		return result;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/camBills/createAndUpdateGSTinNumber/{code}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createAndUpdateGSTinNumber(HttpServletRequest request,@PathVariable int code)
	{
		try {
			if(code==1){
				System.out.println("------------->Create GSTN <Code="+code+">------------->");
				String pname = request.getParameter("property_No");
				String gno   = request.getParameter("gstin");
				System.err.println("=========================== PropertyName="+pname+" & GSTIN No="+gno+" ====================");
				String findOtherDetails =
						"SELECT x.ACCOUNT_ID,x.PROPERTY_ID,x.PERSON_ID,x.ACCOUNT_NO,x.PROPERTY_NO,(PE.FIRST_NAME||' '||PE.LAST_NAME) AS name FROM"
								+ "("
								+ " SELECT a.ACCOUNT_ID,p.PROPERTY_ID,a.PERSON_ID,a.ACCOUNT_NO,p.PROPERTY_NO FROM ACCOUNT a "
								+ " LEFT JOIN PROPERTY p ON a.PROPERTYID=p.PROPERTY_ID"
								+ ")X LEFT JOIN PERSON pe ON X.PERSON_ID=PE.PERSON_ID WHERE x.PROPERTY_NO='"+pname+"' ORDER BY x.PROPERTY_NO ASC";
				System.out.println("findOtherDetails="+findOtherDetails);
				Object[] result =  (Object[]) entityManager.createNativeQuery(findOtherDetails).getSingleResult();

				List<?> list2 = entityManager.createNativeQuery("SELECT * FROM GSTN_DETAILS").getResultList();
				for (Iterator<?> iterator = list2.iterator(); iterator.hasNext();){
					final Object[] values = (Object[]) iterator.next();
					if(result[4].toString().equalsIgnoreCase(values[5].toString())){
						System.out.println("===========MSG=Property No Already Exists");						
						JsonResponse errorResponse = new JsonResponse();
						@SuppressWarnings("serial")
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {{
							put("invalid","Property No Already Exists");
						}};
						errorResponse.setStatus("AlreadyExists");
						errorResponse.setResult(errorMapResponse);
						
						return errorResponse;
					}
				}
				String saveQuery = "INSERT INTO GSTN_DETAILS VALUES(GSTN_DETAILS_SEQ.nextval,'"+result[0]+"','"+result[1]+"',"
						+ "'"+result[2]+"','"+result[3]+"','"+result[4]+"','"+result[5]+"','"+gno+"')";
				System.out.println("saveQuery="+saveQuery);
				return entityManager.createNativeQuery(saveQuery).executeUpdate();
			
			}else if(code==2){
				System.out.println("------------->Update GSTN <Code="+code+">------------->");
				String pname = request.getParameter("property_No");
				String gno   = request.getParameter("gstin");
				System.err.println("=========================== PropertyName="+pname+" & GSTIN No="+gno+" ====================");
				String updateQuery = "UPDATE GSTN_DETAILS SET GSTN_NUMBER='"+gno+"' WHERE PROPERTY_NO='"+pname+"'";
				System.out.println("updateQuery="+updateQuery);
				return entityManager.createNativeQuery(updateQuery).executeUpdate();

			}else{
				System.err.println("-------------->Invalid Code-------------->");
				return null;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Exception Came";
		}
	}
}
