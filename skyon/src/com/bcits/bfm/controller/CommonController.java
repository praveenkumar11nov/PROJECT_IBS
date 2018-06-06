package com.bcits.bfm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptService;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerService;

/**
 * Controller which includes all the common business logic concerned to all the modules
 * Module: All
 * 
 * @author Manjunath Krishnappa
 * @version %I%, %G%
 * @since 0.1
 */
@Controller
public class CommonController
{
	@Resource
	private MessageSource messageSource;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PersonService personService;
	@Autowired
	private StoreItemLedgerService storeItemLedgerService;
	@Autowired
	private UomService uomService;
	@Autowired
	private StoreGoodsReceiptService storeGoodsReceiptService; 
	@Autowired
	private JobCardsService jobCardsService;
	
	
	/**
	 * Fetching list of field values for filtering purpose - drop down
	 * 
	 * @param attribute
	 *            attribute - field name
	 * @param locale
	 *            Is required to fetch the field values based on locale from properties file
	 * @return List of field values for filtering purpose - drop down
	 * @since 0.1
	 */
	@RequestMapping(value = "/common/getFilterDropDownValues/{attribute}", method = RequestMethod.GET)
	public @ResponseBody List<String> getFilterDropDownValues(@PathVariable String attribute, final Locale locale) 
	{
		List<String> list = new ArrayList<String>();
		String[] array = messageSource.getMessage(attribute, null, locale).split(",");
		for (String title : array)
			list.add(title);
		//Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list; 	
	}
	
	/**
	 * Fetching list of field values from respective class for filtering purpose - auto complete
	 * 
	 * @param className
	 *            className - class name
	 * @param attribute
	 *            attribute - field name
	 * @return List of field values from respective class for filtering purpose - auto complete
	 * @since 0.1
	 */
	@RequestMapping(value = "/common/getFilterAutoCompleteValues/{className}/{attribute}", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> getFilterAutoCompleteValues(@PathVariable String className, @PathVariable String attribute) 
	{
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(attribute);
		return (List<?>) commonService.selectDistinctQuery(className, attributeList, null);
	}
	
	/**
	 * Fetching list of field values for filtering purpose - auto complete
	 * 
	 * @param attribute
	 *            attribute - field name
	 * @param locale
	 *            Is required to fetch the field values based on locale from properties file
	 * @return List of field values for filtering purpose - auto complete
	 * @since 0.1
	 */
	@RequestMapping(value = "/common/getAllChecks/{attribute}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getAllChecks(@PathVariable String attribute, final Locale locale) 
	{
		String[] str = messageSource.getMessage(attribute, null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
		//List<String> list = Arrays.asList(str);
		//Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			map = new HashMap<String, String>();
			map.put("value", string);
			map.put("text", string);
			result.add(map);
		}
		return result;
	}
	
	/**
	 * Fetching list of field values as map i.e., key value pairs
	 * 
	 * @param attribute1
	 *            attribute1 - field name
	 * @param attribute2
	 *            attribute2 - field name
	 * @param locale
	 *            Is required to fetch the field values based on locale from properties file
	 * @return List of field values as map i.e., key value pairs
	 * @since 0.1
	 */
	public List<?> populateCategories(String attribute1, String attrubute2, String className) 
    {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        Map<String, Object> mapResult = null;
        List<String> attributesList = new ArrayList<String>();
        attributesList.add(attribute1);
        attributesList.add(attrubute2);
        List<?> categoriesList = (List<?>) commonService.selectQuery(className, attributesList, null);
        mapResultSelect.put("value", 0);
        mapResultSelect.put("text", "Select");
		listResult.add(mapResultSelect);
		for (Iterator<?> i = categoriesList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			mapResult = new HashMap<String, Object>();
			mapResult.put("value", values[0]);
			mapResult.put("text", values[1]);
			listResult.add(mapResult);
		}	
        return listResult;
    } 
	
	/**
	 * Fetching list of field values as map i.e., key value pairs
	 * 
	 * @param attribute
	 *            attribute - field name
	 * @param locale
	 *            Is required to fetch the field values based on locale from properties file
	 * @return List of field values as map i.e., key value pairs
	 * @since 0.1
	 */
	public List<?> populateCategories(String attribute, final Locale locale) 
    {
		String[] str = messageSource.getMessage(attribute, null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
		//List<String> list = Arrays.asList(str);
		//Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			map = new HashMap<String, String>();
			map.put("value", string);
			map.put("text", string);
			result.add(map);
		}
		return result;
    } 
	
	/**
	 * Fetching list of person names
	 *
	 * @return List of person names
	 * @since 0.1
	 */
	public List<?> populatePerson() 
    {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>(); 
        Map<String, Object> mapResultSelect = new HashMap<String, Object>();
        Map<String, Object> mapResult = null;
        List<String> attributesList = new ArrayList<String>();
        attributesList.add("personId");
        attributesList.add("firstName");
        attributesList.add("lastName");
        List<?> personList = commonService.selectQuery("Person", attributesList, null);
        mapResultSelect.put("value", 0);
        mapResultSelect.put("text", "Select");
		listResult.add(mapResultSelect);
		for (Iterator<?> i = personList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			mapResult = new HashMap<String, Object>();
			mapResult.put("value", values[0]);
			String personName = "";
			personName = personName.concat((String)values[1]);
			if(((String)values[2]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[2]);
			}
			mapResult.put("text", personName);
			listResult.add(mapResult);
		}	
        return listResult;
    } 
	
	/**
	 * Fetching list of person details based on person type
	 *
	 * @param personType
	 *            personType - Owner/Tenant/Family/DomesticHelp/Vendor/ConciergeVendor
	 *            
	 * @return List of person details based on person type
	 * @since 0.1
	 */
	@RequestMapping(value = "/common/getPersonListBasedOnPersonType/{personType}", method = RequestMethod.GET)
	public @ResponseBody List<?> getPersonListBasedOnPersonType(@PathVariable String personType) 
	{
		List<?> personList = personService.getAllPersonRequiredDetailsBasedOnPersonType(personType);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = personList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String)values[4]);
			if(((String)values[6]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[6]);
			}
			map = new HashMap<String, Object>();
			map.put("personId", (Integer)values[0]);
			map.put("personName", personName);
			map.put("personType", (String)values[1]);
			map.put("personStyle", (String)values[2]);
			map.put("id", (Integer)values[17]);
			result.add(map);
		}
		return result;
	} 
	
	/**
	 * Fetching list of person field values as map i.e., key value pairs
	 * 
	 * @param className
	 *            className - class name
	 * @param personFieldName
	 *            personFieldName - person field name 
	 *                      
	 * @return List of field values as map i.e., key value pairs
	 * @since 0.1
	 */
	@RequestMapping(value = "/common/getPersonNamesFilterList/{className}/{personFieldName}", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Map<String, Object>> getPersonNamesFilterList(@PathVariable String className, @PathVariable String personFieldName) 
	{
		List<?> personList = commonService.getPersonNamesFilterList(className, personFieldName);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = personList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			String personName = "";
			personName = personName.concat((String)values[1]);
			if(((String)values[2]) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat((String)values[2]);
			}
			map = new HashMap<String, Object>();
			map.put("personId", (Integer)values[0]);
			map.put("personName", personName);
			map.put("personType", (String)values[3]);
			map.put("personStyle", (String)values[4]);
			result.add(map);
		}
		return result;
	}
	
	/**
	 * Get quantity based on store, item and uom from Item ledger
	 *
	 * @return quantity
	 * @since 0.1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/common/getQuantityBasedOnContractStoreAndItem", method = RequestMethod.GET)
	public @ResponseBody Double getQuantityBasedOnItemUom(@RequestParam("storeId") int storeId, @RequestParam("vcId") int vcId, 
			@RequestParam("imId") int imId, @RequestParam("uomId") int uomId) 
	{
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		
		UnitOfMeasurement unitOfMeasurement = uomService.find(uomId);
		List<?> storeGoodsReceiptList = storeGoodsReceiptService.getRequiredStoreFields(vcId, imId);
		double quantity = 0.0;
		for (Iterator<?> i = storeGoodsReceiptList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();

			if(((String)values[0]).equalsIgnoreCase("Yes"))
				quantity = quantity + (double)values[2];
			else
				quantity = quantity + ((double)values[2] * (double)values[1]);
		}
		
		List<String> attributesList = new ArrayList<String>(); 
		attributesList.add("imBalance");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("imId", imId);
		
		List<Double> ledgerQuantityList = (List<Double>) storeItemLedgerService.selectQuery("StoreItemLedger", attributesList, params);
		
		if((storeGoodsReceiptList.size() == 0) && (ledgerQuantityList.size() == 0))
			return -3.0;
		if(storeGoodsReceiptList.size() == 0)
			return -2.0;
		else if(ledgerQuantityList.size() == 1)
		{
			Double ledgerQuantity = ledgerQuantityList.get(0);
			
			if(ledgerQuantity < quantity)
				quantity = ledgerQuantity;
			
			if(unitOfMeasurement.getBaseUom().equalsIgnoreCase("No"))
				quantity = quantity / unitOfMeasurement.getUomConversion();
			
			return Double.parseDouble(decimalFormat.format(quantity));
		}
		else
			return -1.0;
	 }
	
	/**
	 * Get quantity based on store, item and uom from Item ledger
	 *
	 * @return quantity
	 * @since 0.1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/common/getQuantityBasedOnStoreItemAndUom", method = RequestMethod.GET)
	public @ResponseBody Object getQuantityBasedOnStoreItemAndUom(@RequestParam("storeId") int storeId, @RequestParam("imId") int imId, @RequestParam("uomId") int uomId) 
	{
		DecimalFormat decimalFormat = new DecimalFormat("###.##");

		UnitOfMeasurement unitOfMeasurement = uomService.find(uomId);
		
		List<String> attributesList = new ArrayList<String>(); 
		attributesList.add("imBalance");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("imId", imId);
		
		List<Double> ledgerQuantityList = (List<Double>) storeItemLedgerService.selectQuery("StoreItemLedger", attributesList, params);
		
		if((ledgerQuantityList.size() > 0) && (ledgerQuantityList.get(0) != 0))
		{
			Double ledgerQuantity = ledgerQuantityList.get(0);

			if(unitOfMeasurement.getBaseUom().equalsIgnoreCase("No"))
				return Double.parseDouble(decimalFormat.format(ledgerQuantity / unitOfMeasurement.getUomConversion()));
			else
				return Double.parseDouble(decimalFormat.format(ledgerQuantity));
		}
		else
			return -1;
	 }

	public Map<?,?> SpringDropdownList(String attribute, Locale locale) 
	{
		String[] str = messageSource.getMessage(attribute, null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
		Map<String, String> map =new HashMap<String, String>();
		map.put(" ", "Select");
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			map.put(string, string);
		}
		return map;
	}

	/**
	 * Fetching list of maps in reverse order
	 * 
	 * @param unorderedList
	 *            Unordered list of maps
	 * @param fieldName
	 *            fieldName - Field name based on which sorting is done 
	 *                      
	 * @return List of maps in reverse order
	 * @since 0.1
	 */
	public List<Map<String, Object>> getSortedList(List<Map<String, Object>> unorderedList, String fieldName)
	{
		 List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		 Map<Object, Map<String, Object>> mapCheck = new HashMap<Object, Map<String, Object>>();
		 for (Iterator<Map<String, Object>> iterator = unorderedList.iterator(); iterator.hasNext();)
		 {
			 Map<String, Object> map = (Map<String, Object>) iterator.next();
		     mapCheck.put(map.get(fieldName), map);
		 }
		 Set<Object> idSet = mapCheck.keySet();
		 List<Object> idList = new ArrayList<Object>(idSet);
		 // create comparator for reverse order
		 Comparator<Object> cmp = Collections.reverseOrder(null);  
		 // sort the list
		 Collections.sort(idList, cmp);  
		 for (Iterator<Object> iterator = idList.iterator(); iterator.hasNext();) 
		 {
			 Object obj = (Object) iterator.next();
			 finalList.add((Map<String, Object>)mapCheck.get(obj));
		 }
		 return finalList;
	}
	
	@RequestMapping(value = "/common/checkIsStoreItemLedgerHasRecords/{className}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String checkIsStoreItemLedgerHasRecords(@PathVariable String className)
	{		
		List<StoreItemLedger> storeItemLedgersList = storeItemLedgerService.findAllStoreItemLedgers();
		if(storeItemLedgersList.size() == 0)
			return "Error: Ledger is empty";
		else if((className.equalsIgnoreCase("StoreIssue")) && (jobCardsService.getRequiredCardsAndMaterials("Issue").size() == 0))
				return "Since no job card is available, Cannot issue store item";
		else if((className.equalsIgnoreCase("StoreGoodsReturn")) && (jobCardsService.getRequiredCardsAndMaterials("Return").size() == 0))
			return "Since no job card is available, cannot return store item";
		else
			return "success";
	}
}
