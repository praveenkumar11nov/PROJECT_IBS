package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.amr.service.AMREntityService;
import com.bcits.bfm.amr.service.AMRPropertyService;
import com.bcits.bfm.model.AMRDataEntity;
import com.bcits.bfm.model.AMRProperty;
import com.bcits.bfm.model.BillingConfiguration;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.electricityBillsManagement.BillingConfigurationService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityAMRBillService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * @author user51
 *
 */

@Controller
public class AMRController {
	/**
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(AMRController.class);
	/**
	 */	@Autowired
	private AMRPropertyService amrService;
	/**
	 */
	@Autowired
	ElectricityAMRBillService amrBillService;
	/**
	 */
	@Autowired
	private MessageSource messageSource;
	/**
	 */
	@Autowired
	private BillingConfigurationService billingConfigurationService;
	/**
	 */
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AMREntityService amrEntityService;

	
	@RequestMapping(value = "/amrSetting", method = RequestMethod.GET)
	public String amrSetting(final ModelMap model,final HttpServletRequest request,final Locale locale) 
	{
		LOGGER.info("In AMR Method");
		model.addAttribute("ViewName", " AMR");
		return "electricityBills/amrSetting";
	}
	/**
	 */
	@RequestMapping(value = "/amrData", method = RequestMethod.GET)
	public String amrData(final ModelMap model,final HttpServletRequest request,final Locale locale) 
	{
		LOGGER.info("In AMR Method");
		model.addAttribute("ViewName", " AMR");
		return "electricityBills/amrData";
	}
	/**
	 */
	@RequestMapping(value = "/billingSettings", method = RequestMethod.GET)
	public String billingSettings(final ModelMap model,final HttpServletRequest request,final Locale locale) 
	{
		LOGGER.info("In AMR Method");
		model.addAttribute("ViewName", "Billing Settings");
		return "electricityBills/billingSettings";
	}
	/**
	 */
	
	@RequestMapping(value = "/amrSetting/getAllBlockUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> getAllBlockUrl() 
	{
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	    final List<?> deptList = amrService.getAllBlocks();		
		Map<String, Object> deptMap;
		for (final Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();			
			deptMap = new HashMap<String, Object>();
			deptMap.put("blockId", (Integer)values[0]);
			deptMap.put("blocksName", (String)values[1]);
			result.add(deptMap);
		}
		return result;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/getAllPropertyUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> getAllPropertyUrl() 
	{
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> deptList = amrService.getAllProperty();		
		Map<String, Object> deptMap;
		for (final Iterator<?> iterator = deptList.iterator(); iterator.hasNext();)
		{
			final Object[] values = (Object[]) iterator.next();			
			deptMap = new HashMap<String, Object>();
			deptMap.put("propertyId", (Integer)values[0]);
			deptMap.put("propertyName", (String)values[1]);
			deptMap.put("blockId", (Integer)values[2]);
			result.add(deptMap);
		}
		return result;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/amrSetting/amrSettingRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> amrSettingRead() throws IOException
	{
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = amrService.findALL();
		Map<String, Object> rateMaster;
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			rateMaster = new HashMap<String, Object>();
			rateMaster.put("amrId", (Integer)values[0]);
			rateMaster.put("blockId", (Integer)values[1]);
			rateMaster.put("blocksName", (String)values[2]);
			rateMaster.put("propertyId", (Integer)values[3]);
			rateMaster.put("propertyName", (String)values[4]);
			rateMaster.put("columnName", (String)values[5]);
			rateMaster.put("status", (String)values[6]);
			rateMaster.put("meterNumber", (String)amrService.getMeterNumber((Integer)values[3]));
			result.add(rateMaster);
		}
		return result;
	}
	
	/*@RequestMapping(value = "/amrData/amrDataRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> amrDataRead() throws IOException
	{
		LOGGER.info("============ IN AMR Read data method ===================");
		//return amrService.getList(dataSourceRequest);
		LOGGER.info("============ IN AMR Read data method ===================");
		Integer blockId=501;
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = amrService.getAMRAccountDetails(blockId);
		Map<String, Object> rateMaster;
		int i = 0;
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			rateMaster = new HashMap<String, Object>();
			if((String)values[6]!=null && (String)values[5]!=null)
			{
				final List<?> amrDate = amrBillService.findAMRDataReading((String)values[6],(String)values[5]);
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();
					rateMaster.put("presentReading",(Double)valuesAMR[1]);
					rateMaster.put("readingDate", (Timestamp)valuesAMR[0]);
					LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)valuesAMR[0]);
				}
				final List<?> amrDGDate = amrBillService.findAMRDGDataReading((String)values[6],(String)values[5]);
				for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){
					rateMaster.put("presentDGReading", (Double)iteratorDGAMR.next());
				}
				rateMaster.put("blocksName", (String)values[5]);
				rateMaster.put("propertyName", (String)values[4]);
				rateMaster.put("columnName", (String)values[5]);
				rateMaster.put("personName", (String)values[2]+" "+(String)values[3]);
				rateMaster.put("accountNumber", (String)values[1]);
				rateMaster.put("meterNumber", (String)values[0]);
				rateMaster.put("id", i);	
				result.add(rateMaster);
				i=+i;
			}
		}
		return result;
	}
	*/
	@RequestMapping(value = "/amrData/amrDataRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> amrDataRead() throws IOException
	{
		LOGGER.info("============ IN AMR Read data method ===================");
		//return amrService.getList(dataSourceRequest);
		LOGGER.info("============ IN AMR Read data method ===================");
		DateTime dtOrg = new DateTime();
		//DateTime minusone = dtOrg.minusDays(1);
		DateTime minusone = dtOrg.plusDays(1);
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		c.add(Calendar.DATE, -1);
		String strDate = new SimpleDateFormat("YYYY-MM-dd").format(c.getTime());
		String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		final List<AMRDataEntity> rateMasterList = amrEntityService.getAMRAccountDetails(strDate,pesentDate);
		Map<String, Object> rateMaster;
		int i = 0;
		for (Iterator<AMRDataEntity> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity = (AMRDataEntity) iterator.next();
			rateMaster = new HashMap<String, Object>();
			rateMaster.put("presentReading",amrDataEntity.getElReading());
			rateMaster.put("readingDate", (Timestamp)amrDataEntity.getReadingDate());
			LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)amrDataEntity.getReadingDate());
			rateMaster.put("presentDGReading", amrDataEntity.getDgReading());
		  rateMaster.put("blocksName", amrDataEntity.getBlockObj().getBlockName());
		  rateMaster.put("propertyName",amrDataEntity.getPropobj().getProperty_No() );
		//rateMaster.put("columnName", (String)values[5]);
		  rateMaster.put("personName", amrDataEntity.getPersonobj().getFirstName()+" "+amrDataEntity.getPersonobj().getLastName());
		  rateMaster.put("accountNumber", amrDataEntity.getAccountobj().getAccountNo());
		  rateMaster.put("meterNumber", amrDataEntity.getElEntity().getMeterSerialNo());
		  rateMaster.put("id", i);	
		  result.add(rateMaster);
		i=+i;
		}
		
		
		return result;
	}
	
	/**
	 */
	@RequestMapping(value = "/amrSetting/amrSettingCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody AMRProperty amrSettingCreate(@ModelAttribute("amrProperty")final AMRProperty amrProperty,final ModelMap model,final HttpServletRequest req) throws IOException
	{
		amrProperty.setBlockId(Integer.parseInt(req.getParameter("blocksName")));
		amrProperty.setPropertyId(Integer.parseInt(req.getParameter("propertyName")));
		amrService.save(amrProperty);
		return amrProperty;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/amrSettingUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody AMRProperty amrSettingUpdate(@ModelAttribute("amrProperty") final AMRProperty amrProperty,final HttpServletRequest req) throws IOException
	{
		amrProperty.setBlockId(Integer.parseInt(req.getParameter("blockId")));
		amrProperty.setPropertyId(Integer.parseInt(req.getParameter("propertyId")));
		amrProperty.setStatus(req.getParameter("status"));
		amrService.update(amrProperty);
		return amrProperty;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/amrSettingDestroy", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody AMRProperty amrSettingDestroy(@ModelAttribute("amrProperty") final AMRProperty amrProperty) throws IOException
	{
		amrService.delete(amrProperty.getAmrId());
		return amrProperty;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/amrSettingStatus/{amrId}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void amrSettingStatus(@PathVariable final int amrId,@PathVariable final String operation,final HttpServletResponse response) {

		LOGGER.info("In amrSettingStatus status change method");
		if ("activate".equalsIgnoreCase(operation))
			amrService.setAMRStatus(amrId,operation, response);
		else
			amrService.setAMRStatus(amrId,operation, response);
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/activateAll", method = {RequestMethod.GET, RequestMethod.POST })
	public void activateAll(final HttpServletResponse response) {
		LOGGER.info("In amrSettingStatus status change method");
		amrService.activateAll(response);
	}
	
	@RequestMapping(value = "/amrDate/searcchByDate", method = RequestMethod.POST)
	public @ResponseBody List<?> searcchByMonth(HttpServletRequest req) throws java.text.ParseException {
		LOGGER.info("search process pay roll for particular month -- ");
		Date month = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("month"));
		LOGGER.info("============ IN AMR Read data method On Date ==================="+month);
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(month);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date month1=(c.getTime());		
 
	    Integer blockId=Integer.parseInt(req.getParameter("blockId"));
		
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = amrService.getAMRAccountDetails(blockId);
		Map<String, Object> rateMaster;
		int i = 0;
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			rateMaster = new HashMap<String, Object>();
			if((String)values[6]!=null && (String)values[5]!=null)
			{
				//LOGGER.info("String)values[6] ========= "+(String)values[6] + "(String)values[5] ========== "+(String)values[5]);
				final List<?> amrDate = amrBillService.findAMRDataReadingOnDate((String)values[6],(String)values[5],month,month1);
				LOGGER.info("amrDate ========== "+amrDate.size());
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();
					//LOGGER.info("(Double)valuesAMR[1] == "+(Double)valuesAMR[1] +"(Timestamp)valuesAMR[0] == "+(Timestamp)valuesAMR[0]);
					rateMaster.put("presentReading",(Double)valuesAMR[1]);
					String S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)valuesAMR[0]);
					rateMaster.put("readingDate", S);
					//LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)valuesAMR[0]);
				}
				final List<?> amrDGDate = amrBillService.findAMRDGDataReadingOnDate((String)values[6],(String)values[5],month,month1);
				for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){
					/*final Object[] valuesDGAMR = (Object[]) iteratorDGAMR.next();*/
					rateMaster.put("presentDGReading", (Double)iteratorDGAMR.next());
				}
				
				rateMaster.put("blocksName", (String)values[5]);
				rateMaster.put("propertyName", (String)values[4]);
				rateMaster.put("columnName", (String)values[5]);
				rateMaster.put("personName", (String)values[2]+" "+(String)values[3]);
				rateMaster.put("accountNumber", (String)values[1]);
				rateMaster.put("meterNumber", (String)values[0]);
				rateMaster.put("id", i);	
				result.add(rateMaster);
				i=+i;
			}
		}
		return result;
		
	}
	@RequestMapping(value = "/amrDate/searcchByDateNew", method = RequestMethod.POST)
	public @ResponseBody List<?> searcchByMonthNew(HttpServletRequest req) throws java.text.ParseException {
		LOGGER.info("search process pay roll for particular month -- ");
		Date month = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("month"));
		LOGGER.info("============ IN AMR Read data method On Date ==================="+month);
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(month);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date month1=(c.getTime());
		
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();		
		Map<String, Object> rateMaster;
		String strDate = new SimpleDateFormat("YYYY-MM-dd").format(month);
		String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(month1);
		
		final List<AMRDataEntity> rateMasterList = amrEntityService.getAMRAccountDetails(strDate,pesentDate);
		
		int i = 0;
		for (Iterator<AMRDataEntity> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity = (AMRDataEntity) iterator.next();
			rateMaster = new HashMap<String, Object>();
			rateMaster.put("presentReading",amrDataEntity.getElReading());
			String S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)amrDataEntity.getReadingDate());
			rateMaster.put("readingDate", S);
			LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)amrDataEntity.getReadingDate());
			rateMaster.put("presentDGReading", amrDataEntity.getDgReading());
		  rateMaster.put("blocksName", amrDataEntity.getBlockObj().getBlockName());
		  rateMaster.put("propertyName",amrDataEntity.getPropobj().getProperty_No() );
		//rateMaster.put("columnName", (String)values[5]);
		  rateMaster.put("personName", amrDataEntity.getPersonobj().getFirstName()+" "+amrDataEntity.getPersonobj().getLastName());
		  rateMaster.put("accountNumber", amrDataEntity.getAccountobj().getAccountNo());
		  rateMaster.put("meterNumber", amrDataEntity.getElEntity().getMeterSerialNo());
		  rateMaster.put("id", i);	
		  result.add(rateMaster);
		i=+i;
		}
		return result;
		
	}
	
	/************************************************** billing settings **********************************************************************/
	
	@RequestMapping(value = "/billingSettings/billingSettingsRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> billingSettingsRead() throws IOException
	{
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = billingConfigurationService.findALL();
		Map<String, Object> rateMaster;
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			rateMaster = new HashMap<String, Object>();
			rateMaster.put("id", (Integer)values[0]);
			rateMaster.put("configName", (String)values[1]);
			rateMaster.put("configValue", (String)values[2]);
			rateMaster.put("description", (String)values[3]);
			rateMaster.put("status", (String)values[4]);
			rateMaster.put("configValueDropDown", (String)values[2]);
			result.add(rateMaster);
		}
		return result;
	}
	
	/**
	 */
	@RequestMapping(value = "/billingSettings/billingSettingsCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody BillingConfiguration billingSettingsCreate(@ModelAttribute("billingConfiguration")final BillingConfiguration billingConfiguration,final ModelMap model,final HttpServletRequest req) throws IOException
	{
		if("Due Days".equalsIgnoreCase(billingConfiguration.getConfigName()) || "CAM Service Tax Amount".equalsIgnoreCase(billingConfiguration.getConfigName()))
		{
			billingConfiguration.setConfigValue(req.getParameter("configValue"));
		}else {
			billingConfiguration.setConfigValue(req.getParameter("configValueDropDown"));
		}
		billingConfigurationService.save(billingConfiguration);
		return billingConfiguration;
	}
	
	/**
	 */
	@RequestMapping(value = "/billingSettings/billingSettingsUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody BillingConfiguration amrSettingUpdate(@ModelAttribute("billingConfiguration")final BillingConfiguration billingConfiguration,final HttpServletRequest req) throws IOException
	{
		if("Due Days".equalsIgnoreCase(billingConfiguration.getConfigName()) || "CAM Service Tax Amount".equalsIgnoreCase(billingConfiguration.getConfigName()))
		{
			billingConfiguration.setConfigValue(req.getParameter("configValue"));
		}else {
			billingConfiguration.setConfigValue(req.getParameter("configValueDropDown"));
		}
		billingConfiguration.setStatus(req.getParameter("status"));
		billingConfigurationService.update(billingConfiguration);
		return billingConfiguration;
	}
	
	/**
	 */
	@RequestMapping(value = "/billingSettings/billingSettingsDestroy", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody BillingConfiguration billingSettingsDestroy(@ModelAttribute("billingConfiguration")final BillingConfiguration billingConfiguration) throws IOException
	{
		billingConfigurationService.delete(billingConfiguration.getId());
		return billingConfiguration;
	}
	
	@RequestMapping(value = "/billingSettings/billingSettingsStatus/{id}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void billingSettingsStatus(@PathVariable final int id,@PathVariable final String operation,final HttpServletResponse response) {

		LOGGER.info("In amrSettingStatus status change method");
		if ("activate".equalsIgnoreCase(operation))
			billingConfigurationService.billingSettingsStatus(id,operation, response);
		else
			billingConfigurationService.billingSettingsStatus(id,operation, response);
	}
	
	
	@RequestMapping(value = "/billingSettings/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getBillsDataForFilter(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("BillingConfiguration", attributeList, null));

		return set;
	}
	
	/************************************************** billing settings **********************************************************************/
	
	@RequestMapping(value = "/billingSettings/getConfigUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> getConfigUrl(final Locale locale) 
	{
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	    final String[] deptList = messageSource.getMessage("configName", null, locale).split(",");	
	    Map<String, Object> deptMap;
	    for(int i=0;i<=deptList.length-1;i++)
	    {
	    	deptMap = new HashMap<String, Object>();
	    	deptMap.put("configName", deptList[i]);
	    	String configName = billingConfigurationService.checkForDuplicate(deptList[i]);
	    	if(configName == null)
	    	{
	    		result.add(deptMap);
	    	}
	    }
		return result;
	}	
	
	/**
	 */
	@RequestMapping(value = "/amrSetting/blockNameFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> blockNameFilterUrl() {	
		final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?>  i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[2]);		
		}
		return result;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/propertyNameFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> propertyNameFilterUrl() {	
		final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[4]);		
		}
		return result;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/columnNameFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> columnNameFilterUrl() {	
	   final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[5]);		
		}
		return result;
	}
	/**
	 */
	@RequestMapping(value = "/amrSetting/statusFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> statusFilterUrl() {	
		final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[6]);		
		}
		return result;
	}
	
	@RequestMapping(value = "/amrSetting/meterNumberFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> meterNumberFilterUrl() {	
		final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)amrService.getMeterNumber((Integer)values[3]));		
		}
		return result;
	}
	
	@RequestMapping(value = "/amrSetting/accountNumberFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> accountNumberFilterUrl() {	
		final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)amrService.getAccountNumber((Integer)values[3]));		
		}
		return result;
	}
	
	@RequestMapping(value = "/amrSetting/personNameFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> personNameFilterUrl() {	
		final List<?> list = amrService.findALL();
		final Set<String> result = new HashSet<String>();		
		for (final Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)amrService.getPersonName((Integer)values[3]));		
		}
		return result;
	}
	
	/**
	 */
	public AMRController() {
		super();
	}
	/**
	 */
	public AMRController(final AMRPropertyService amrService) {
		this.amrService = amrService;
	}
	
	@RequestMapping(value = "/amrTemplate/amrTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileAmrSetting(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> amrTemplateEntities =amrService.findALLAmr();
	
               
        String sheetName = "Templates";//name of sheet

    	XSSFWorkbook wb = new XSSFWorkbook();
    	XSSFSheet sheet = wb.createSheet(sheetName) ;
    	
    	XSSFRow header = sheet.createRow(0);    	
    	
    	XSSFCellStyle style = wb.createCellStyle();
    	style.setWrapText(true);
    	XSSFFont font = wb.createFont();
    	font.setFontName(HSSFFont.FONT_ARIAL);
    	font.setFontHeightInPoints((short)10);
    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    	style.setFont(font);
    	
    	header.createCell(0).setCellValue("Tower");
        header.createCell(1).setCellValue("Property");
        header.createCell(2).setCellValue("Meter Number");
        header.createCell(3).setCellValue("AMR Field Name");
        header.createCell(4).setCellValue("Status");
          
        
    
        for(int j = 0; j<=4; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:E200"));
        }
        
        int count = 1;
        //XSSFCell cell = null;
    	for (Object[] amrSetting :amrTemplateEntities  ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		//int value = count+1;

    		row.createCell(0).setCellValue((String)amrSetting[2]);
    		row.createCell(1).setCellValue((String)amrSetting[4]);
    		row.createCell(2).setCellValue((String)amrService.getMeterNumber((Integer)amrSetting[3]));
    		row.createCell(3).setCellValue((String)amrSetting[5]);
    		row.createCell(4).setCellValue((String)amrSetting[6]);
    	
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"AMRSettingTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
	//::::::::::::::::::::::::::AmrData:::::::::::::::::::::::::::::::
	
	@RequestMapping(value = "/amrDataTemplate/amrDataTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileAmrData(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, java.text.ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
		LOGGER.info("search process pay roll for particular month -- ");
		Date month = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("month"));
		LOGGER.info("search process pay roll for particular month -- "+month);
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(month);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date month1=(c.getTime());		

		LOGGER.info("============ IN AMR Read data method On Date ===================");
       
		List<Object[]> amrDataTemplateEntities =amrService.getAMRDataDetails();
	
               
        String sheetName = "Templates";//name of sheet

    	XSSFWorkbook wb = new XSSFWorkbook();
    	XSSFSheet sheet = wb.createSheet(sheetName) ;
    	
    	XSSFRow header = sheet.createRow(0);    	
    	
    	XSSFCellStyle style = wb.createCellStyle();
    	style.setWrapText(true);
    	XSSFFont font = wb.createFont();
    	font.setFontName(HSSFFont.FONT_ARIAL);
    	font.setFontHeightInPoints((short)10);
    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    	style.setFont(font);
    	
    	header.createCell(0).setCellValue("Tower");
        header.createCell(1).setCellValue("Person Name");
        header.createCell(2).setCellValue("Property");
        header.createCell(3).setCellValue("Account Number");
        header.createCell(4).setCellValue("Meter Number");
        header.createCell(5).setCellValue("Date");
        header.createCell(6).setCellValue("Present Reading");
        header.createCell(7).setCellValue("Present DG Reading");
   
          
        
    
        for(int j = 0; j<=7; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:G200"));
        }
        
        int count = 1;
    
        
    	for (Object[] amrData :amrDataTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		if((String)amrData[6]!=null && (String)amrData[5]!=null)
			{
    			String str="";
    			String str1="";
    			if((String)amrData[5]!=null){
    				str=(String)amrData[5];
    			}
    			else{
    				str="";
    			}
    			
    			row.createCell(0).setCellValue(str);
    			if((String)amrData[2]!=null){
    				str=(String)amrData[2];
    			}
    			else{
    				str="";
    			}
    			if((String)amrData[3]!=null){
    				str1=(String)amrData[3];
    			}
    			else{
    				str1="";
    			}
	    		row.createCell(1).setCellValue(str+" "+str1);
	    		if((String)amrData[4]!=null){
    				str=(String)amrData[4];
    			}
    			else{
    				str="";
    			}
	    		row.createCell(2).setCellValue(str);
	    		if((String)amrData[1]!=null){
    				str=(String)amrData[1];
    			}
    			else{
    				str="";
    			}
	    		row.createCell(3).setCellValue(str);
	    		if((String)amrData[0]!=null){
    				str=(String)amrData[0];
    			}
    			else{
    				str="";
    			}
	    		row.createCell(4).setCellValue(str);
				final List<?> amrDate = amrBillService.findAMRDataReadingOnDate((String)amrData[6],(String)amrData[5],month,month1);
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();
					
					String S ="";
					if((Timestamp)valuesAMR[0]!=null)
					{
						S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)valuesAMR[0]);
					}
					else{
						S="";
						
					}
					row.createCell(5).setCellValue(S);
					if((Double)valuesAMR[1]!=null){
	    				str=Double.toString((Double)valuesAMR[1]);
	    			}
	    			else{
	    				str="";
	    			}
					row.createCell(6).setCellValue(str);
				
				}
    		
    		final List<?> amrDGDate = amrBillService.findAMRDGDataReadingOnDate((String)amrData[6],(String)amrData[5],month,month1);
			for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){
				
				 row.createCell(7).setCellValue((Double)iteratorDGAMR.next());
			}
			
			count ++;
			}
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"AMRDataTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	//:::::::::::::::::::::::::::::::::::::endAmrData:::::::::::::::::::::::::::::::::
	
	@RequestMapping(value = "/amrPdfTemplate/amrPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileMeterPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		 List<Object[]> amrTemplateEntities =amrService.findALLAmr();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
      
       
        PdfPTable table = new PdfPTable(5);
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
        
        table.addCell(new Paragraph("Tower",font1));
        table.addCell(new Paragraph("Property",font1));
        table.addCell(new Paragraph("Meter Number",font1));
        table.addCell(new Paragraph("AMR Field Name",font1));
        table.addCell(new Paragraph("Status",font1));
        //XSSFCell cell = null;
    	for (Object[] amrSetting :amrTemplateEntities ) {
    		
        
        PdfPCell cell1 = new PdfPCell(new Paragraph((String)amrSetting[2],font3));
   
        PdfPCell cell2 = new PdfPCell(new Paragraph((String)amrSetting[4],font3));
    
        PdfPCell cell3 = new PdfPCell(new Paragraph((String)amrService.getMeterNumber((Integer)amrSetting[3]),font3));
       
        PdfPCell cell4 = new PdfPCell(new Paragraph((String)amrSetting[5],font3));
   
        PdfPCell cell5 = new PdfPCell(new Paragraph((String)amrSetting[6],font3));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.setWidthPercentage(100);
        float[] columnWidths = {6f, 6f, 6f, 6f, 5f};

        table.setWidths(columnWidths);
    		
		}
        
         document.add(table);
        document.close();

    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	baos.writeTo(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=\"AMRSettingTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	@RequestMapping(value = "/amrDataPdfTemplate/amrDataPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileDataPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, java.text.ParseException{
		
		
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";

		  
				LOGGER.info("search process pay roll for particular month -- ");
				Date month = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("month"));
				LOGGER.info("search process pay roll for particular month -- "+month);
				java.util.Calendar c = java.util.Calendar.getInstance();
				c.setTime(month);
				c.add(java.util.Calendar.DATE, 1);  // number of days to add
				//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    Date month1=(c.getTime());	
			    System.out.println(month1);

				LOGGER.info("============ IN AMR Read data method On Date ===================");
		       
		
		
		List<Object[]> amrDataTemplateEntities =amrService.getAMRDataDetails();
	    Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        PdfPTable table = new PdfPTable(8);
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        table.addCell(new Paragraph("Tower",font1));
        table.addCell(new Paragraph("Person Name",font1));
        table.addCell(new Paragraph("Property",font1));
        table.addCell(new Paragraph("Account Number",font1));
        table.addCell(new Paragraph("Meter Number",font1));
        table.addCell(new Paragraph("Date",font1));
        table.addCell(new Paragraph("Present Reading",font1));
        table.addCell(new Paragraph("Present DG Reading",font1));
        //XSSFCell cell = null;
        
    	for (Object[] amrData :amrDataTemplateEntities) {
        
        PdfPCell cell1 =null; 
   
        PdfPCell cell2 =null;
    
        PdfPCell cell3 =null; 
       
        PdfPCell cell4 =null;
   
        PdfPCell cell5 =null;
      
        PdfPCell cell6 = null;
        PdfPCell cell7 = null;
        PdfPCell cell8 = null;
        
        if((String)amrData[6]!=null && (String)amrData[5]!=null)
        	{
        	String str="";
			String str1="";
			if((String)amrData[5]!=null){
				str=(String)amrData[5];
			}
			else{
				str="";
			}
        	 
            cell1 = new PdfPCell(new Paragraph(str,font3));
            if((String)amrData[2]!=null){
				str=(String)amrData[2];
			}
			else{
				str="";
			}
			if((String)amrData[3]!=null){
				str1=(String)amrData[3];
			}
			else{
				str1="";
			}
       
            cell2 = new PdfPCell(new Paragraph(str+" "+str1,font3));
            if((String)amrData[4]!=null){
				str=(String)amrData[4];
			}
			else{
				str="";
			}
        
            cell3 = new PdfPCell(new Paragraph(str,font3));
            if((String)amrData[1]!=null){
				str=(String)amrData[1];
			}
			else{
				str="";
			}
    		
           
            cell4 = new PdfPCell(new Paragraph(str,font3));
            if((String)amrData[0]!=null){
				str=(String)amrData[0];
			}
			else{
				str="";
			}
       
            cell5 = new PdfPCell(new Paragraph(str,font3));
            
            final List<?> amrDate = amrBillService.findAMRDataReadingOnDate((String)amrData[6],(String)amrData[5],month,month1);
			for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
				final Object[] valuesAMR = (Object[]) iteratorAMR.next();
				
				String S ="";
				if((Timestamp)valuesAMR[0]!=null)
				{
					S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)valuesAMR[0]);
				}
				else{
					S="";
					
				}
			
				 cell6 = new PdfPCell(new Paragraph(S,font3));
				 if((Double)valuesAMR[1]!=null){
	    				str=Double.toString((Double)valuesAMR[1]);
	    			}
	    			else{
	    				str="";
	    			}
				 cell7 = new PdfPCell(new Paragraph(str,font3));

			        table.addCell(cell1);
			        table.addCell(cell2);
			        table.addCell(cell3);
			        table.addCell(cell4);
			        table.addCell(cell5);
			        table.addCell(cell6);
			        table.addCell(cell7);
			
				
			}
		
		     final List<?> amrDGDate = amrBillService.findAMRDGDataReadingOnDate((String)amrData[6],(String)amrData[5],month,month1);
		     for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){
			 
			 cell8 = new PdfPCell(new Paragraph(Double.toString((Double)iteratorDGAMR.next()),font3));
		     table.addCell(cell8);
		   }
	       }
        
        
        table.setWidthPercentage(100);
        float[] columnWidths = {6f, 10f, 8f, 12f, 10f, 10f, 12f, 15f};

        table.setWidths(columnWidths);
    		
		}
        
         document.add(table);
        document.close();

    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	baos.writeTo(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=\"AMRDataTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	@Scheduled(cron = "${scheduling.job.amrdata}")
	public void escalationSheduler() throws Exception{
		LOGGER.info("In save meter status method");
		
		
		
		LOGGER.info("============ IN AMR Read data method ===================");
		//return amrService.getList(dataSourceRequest);
		LOGGER.info("============ IN AMR Read data method ===================");
		
	
		final List<?> rateMasterList = amrEntityService.getAMRAccountEntityDetails();
		
		
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity=new AMRDataEntity();
			final Object[] values = (Object[]) iterator.next();
			
			if((String)values[7]!=null && (String)values[6]!=null)
			{
				final List<?> amrDate = amrBillService.findAMRDataReading((String)values[7],(String)values[6]);
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();					
					amrDataEntity.setReadingDate((Timestamp)valuesAMR[0]);
					amrDataEntity.setElReading((Double)valuesAMR[1]);					
					LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)valuesAMR[0]);
				}
				final List<?> amrDGDate = amrBillService.findAMRDGDataReading((String)values[7],(String)values[6]);
				for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){					
					amrDataEntity.setDgReading((Double)iteratorDGAMR.next());
				}
				amrDataEntity.setAccountId((Integer)values[1]);
				amrDataEntity.setBlockId((Integer)values[4]);
				amrDataEntity.setPropertyId((Integer)values[3]);
				amrDataEntity.setPersonId((Integer)values[2]);
				amrDataEntity.setMeterId((Integer)values[0]);				
				amrEntityService.save(amrDataEntity);
				
				
			}
		}
		
		
	}
	
	
/*	@Scheduled(cron = "${scheduling.job.amrdatamonth}")
	public void saveByMonth() throws java.text.ParseException {
		LOGGER.info("search process pay roll for particular month -- ");
		Date strdate=new Date();
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		c1.setTime(strdate);
		c1.add(java.util.Calendar.DATE, -1);  // number of days to add
		//Date month = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("month"));
		Date month =(c1.getTime());
		LOGGER.info("============ IN AMR Read data method On Date ==================="+month);
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(month);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date month1=(c.getTime());		
 
	   
	    
	    
		
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = amrEntityService.getAMRAccountEntityDetails();
		
		
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity=new AMRDataEntity();
			final Object[] values = (Object[]) iterator.next();
			
			if((String)values[7]!=null && (String)values[6]!=null)
			{
				final List<?> amrDate =  amrBillService.findAMRDataReadingOnDate((String)values[7],(String)values[6],month,month1);
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();					
					amrDataEntity.setReadingDate((Timestamp)valuesAMR[0]);
					amrDataEntity.setElReading((Double)valuesAMR[1]);					
					LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)valuesAMR[0]);
				}
				final List<?> amrDGDate = amrBillService.findAMRDGDataReadingOnDate((String)values[7],(String)values[6],month,month1);
				for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){					
					amrDataEntity.setDgReading((Double)iteratorDGAMR.next());
				}
				amrDataEntity.setAccountId((Integer)values[1]);
				amrDataEntity.setBlockId((Integer)values[4]);
				amrDataEntity.setPropertyId((Integer)values[3]);
				amrDataEntity.setPersonId((Integer)values[2]);
				amrDataEntity.setMeterId((Integer)values[0]);				
				amrEntityService.save(amrDataEntity);
				
				
			}
		}
	}
	
*/
	@RequestMapping(value = "/amrDataTemplate/amrDataTemplatesDetailsExportNew", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileAmrDataNew(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, java.text.ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
		LOGGER.info("search process pay roll for particular month -- ");
		Date month = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("month"));
		LOGGER.info("search process pay roll for particular month -- "+month);
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(month);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date month1=(c.getTime());		

		LOGGER.info("============ IN AMR Read data method On Date ===================");
		String strDate = new SimpleDateFormat("YYYY-MM-dd").format(month);
		String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(month1);
       
		List<AMRDataEntity> amrDataTemplateEntities =amrEntityService.getAMRAccountDetails(strDate,pesentDate);
	
               
        String sheetName = "Templates";//name of sheet

    	XSSFWorkbook wb = new XSSFWorkbook();
    	XSSFSheet sheet = wb.createSheet(sheetName) ;
    	
    	XSSFRow header = sheet.createRow(0);    	
    	
    	XSSFCellStyle style = wb.createCellStyle();
    	style.setWrapText(true);
    	XSSFFont font = wb.createFont();
    	font.setFontName(HSSFFont.FONT_ARIAL);
    	font.setFontHeightInPoints((short)10);
    	font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    	style.setFont(font);
    	
    	header.createCell(0).setCellValue("Tower");
        header.createCell(1).setCellValue("Person Name");
        header.createCell(2).setCellValue("Property");
        header.createCell(3).setCellValue("Account Number");
        header.createCell(4).setCellValue("Meter Number");
        header.createCell(5).setCellValue("Date");
        header.createCell(6).setCellValue("Present Reading");
        header.createCell(7).setCellValue("Present DG Reading");
    
        for(int j = 0; j<=7; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:G200"));
        }
        
        int count = 1;
    
        
        for (Iterator<AMRDataEntity> iterator = amrDataTemplateEntities.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity = (AMRDataEntity) iterator.next();
			
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		
    			String str="";
    			String str1="";
    			if(amrDataEntity.getBlockObj().getBlockName()!=null){
    				str=amrDataEntity.getBlockObj().getBlockName();
    			}
    			else{
    				str="";
    			}
    			
    			row.createCell(0).setCellValue(str);
    			if(amrDataEntity.getPersonobj().getFirstName()!=null){
    				str=amrDataEntity.getPersonobj().getFirstName();
    			}
    			else{
    				str="";
    			}
    			if(amrDataEntity.getPersonobj().getLastName()!=null){
    				str1=amrDataEntity.getPersonobj().getLastName();
    			}
    			else{
    				str1="";
    			}
	    		row.createCell(1).setCellValue(str+" "+str1);
	    		if(amrDataEntity.getPropobj().getProperty_No() !=null){
    				str=amrDataEntity.getPropobj().getProperty_No() ;
    			}
    			else{
    				str="";
    			}
	    		row.createCell(2).setCellValue(str);
	    		if(amrDataEntity.getAccountobj().getAccountNo()!=null){
    				str=amrDataEntity.getAccountobj().getAccountNo();
    			}
    			else{
    				str="";
    			}
	    		row.createCell(3).setCellValue(str);
	    		if(amrDataEntity.getElEntity().getMeterSerialNo()!=null){
    				str=amrDataEntity.getElEntity().getMeterSerialNo();
    			}
    			else{
    				str="";
    			}
	    		row.createCell(4).setCellValue(str);
				
					
					String S ="";
					if((Timestamp)amrDataEntity.getReadingDate()!=null)
					{
						S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)amrDataEntity.getReadingDate());
					}
					else{
						S="";
						
					}
					row.createCell(5).setCellValue(S);
					if((Double)amrDataEntity.getElReading()!=null){
	    				str=Double.toString((Double)amrDataEntity.getElReading());
	    			}
	    			else{
	    				str="";
	    			}
					row.createCell(6).setCellValue(str);
				
				 row.createCell(7).setCellValue((Double)amrDataEntity.getDgReading());
			
			
			count ++;
			
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"AMRDataTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	//:::::::::::::::::::::::::::::::::::::endAmrData:::::::::::::::::::::::::::::::::
	
	
	
	
	@RequestMapping(value = "/amrDataPdfTemplate/amrDataPdfTemplatesDetailsExportNew", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileDataPdfNew(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, java.text.ParseException{
		
		
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";

		  
				LOGGER.info("search process pay roll for particular month -- ");
				Date month = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("month"));
				LOGGER.info("search process pay roll for particular month -- "+month);
				java.util.Calendar c = java.util.Calendar.getInstance();
				c.setTime(month);
				c.add(java.util.Calendar.DATE, 1);  // number of days to add
				//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    Date month1=(c.getTime());	
			    System.out.println(month1);

				LOGGER.info("============ IN AMR Read data method On Date ===================");
		       
				String strDate = new SimpleDateFormat("YYYY-MM-dd").format(month);
				String pesentDate = new SimpleDateFormat("YYYY-MM-dd").format(month1);
		
		List<AMRDataEntity> amrDataTemplateEntities =amrEntityService.getAMRAccountDetails(strDate,pesentDate);
		 Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        document.open();
	        PdfPTable table = new PdfPTable(8);
	        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
	        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
	        table.addCell(new Paragraph("Tower",font1));
	        table.addCell(new Paragraph("Person Name",font1));
	        table.addCell(new Paragraph("Property",font1));
	        table.addCell(new Paragraph("Account Number",font1));
	        table.addCell(new Paragraph("Meter Number",font1));
	        table.addCell(new Paragraph("Date",font1));
	        table.addCell(new Paragraph("Present Reading",font1));
	        table.addCell(new Paragraph("Present DG Reading",font1));
        //XSSFCell cell = null;
        PdfPCell cell1 =null; 
   
        PdfPCell cell2 =null;
    
        PdfPCell cell3 =null; 
       
        PdfPCell cell4 =null;
   
        PdfPCell cell5 =null;
      
        PdfPCell cell6 = null;
        PdfPCell cell7 = null;
        PdfPCell cell8 = null;
        
    
        
        
        for (Iterator<AMRDataEntity> iterator = amrDataTemplateEntities.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity = (AMRDataEntity) iterator.next();
       
        
        
        	String str="";
			String str1="";
			if(amrDataEntity.getBlockObj().getBlockName()!=null){
				str=amrDataEntity.getBlockObj().getBlockName();
			}
			else{
				str="";
			}
            cell1 = new PdfPCell(new Paragraph(str,font3));
            
            
            if(amrDataEntity.getPersonobj().getFirstName()!=null){
				str=amrDataEntity.getPersonobj().getFirstName();
			}
			else{
				str="";
			}
            
			if(amrDataEntity.getPersonobj().getLastName()!=null){
				str1=amrDataEntity.getPersonobj().getLastName();
			}
			else{
				str1="";
			}
			
			
       
            cell2 = new PdfPCell(new Paragraph(str+" "+str1,font3));
            
            
            if(amrDataEntity.getPropobj().getProperty_No() !=null){
				str=amrDataEntity.getPropobj().getProperty_No() ;
			}
			else{
				str="";
			}
        
            cell3 = new PdfPCell(new Paragraph(str,font3));
            
            
            if(amrDataEntity.getAccountobj().getAccountNo()!=null){
				str=amrDataEntity.getAccountobj().getAccountNo();
			}
			else{
				str="";
			}
    		
           
            cell4 = new PdfPCell(new Paragraph(str,font3));
            
            
            if(amrDataEntity.getElEntity().getMeterSerialNo()!=null){
				str=amrDataEntity.getElEntity().getMeterSerialNo();
			}
			else{
				str="";
			}
       
            cell5 = new PdfPCell(new Paragraph(str,font3));
            
            
            
          
				String S ="";
				if((Timestamp)amrDataEntity.getReadingDate()!=null)
				{
					S = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.UK).format((Timestamp)amrDataEntity.getReadingDate());
				}
				else{
					S="";
					
				}
			
				 cell6 = new PdfPCell(new Paragraph(S,font3));
				 if((Double)amrDataEntity.getElReading()!=null){
	    				str=Double.toString((Double)amrDataEntity.getElReading());
	    			}
	    			else{
	    				str="";
	    			}
				 cell7 = new PdfPCell(new Paragraph(str,font3));
				 cell8 = new PdfPCell(new Paragraph(Double.toString((Double)amrDataEntity.getDgReading()),font3));
			     
			        table.addCell(cell1);
			        table.addCell(cell2);
			        table.addCell(cell3);
			        table.addCell(cell4);
			        table.addCell(cell5);
			        table.addCell(cell6);
			        table.addCell(cell7);
			        table.addCell(cell8);
			        table.setWidthPercentage(100);
			        float[] columnWidths = {6f, 10f, 8f, 12f, 10f, 10f, 12f, 15f};

			        table.setWidths(columnWidths);
			
				
			}
        
         document.add(table);
        document.close();

    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	baos.writeTo(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=\"AMRDataTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	@RequestMapping(value = "/amrDate/dumpByDateNew	", method = RequestMethod.POST)
	public @ResponseBody List<?> dumpByMonthNew(HttpServletRequest req) throws java.text.ParseException {
		LOGGER.info("search process pay roll for particular month -- ");
		Date month = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("month"));
		LOGGER.info("============ IN AMR Read data method On Date ==================="+month);
		LOGGER.info("search process pay roll for particular month -- ");
		//Date strdate=new Date();
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		c1.setTime(month);
		//c1.add(java.util.Calendar.DATE);  // number of days to add
		//Date month = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("month"));
		Date month2 =(c1.getTime());
		LOGGER.info("============ IN AMR Read data method On Date ==================="+month);
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(month2);
		c.add(java.util.Calendar.DATE, 1);  // number of days to add
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date month1=(c.getTime());	
		
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = amrEntityService.getAMRAccountEntityDetails();
		
		
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			AMRDataEntity amrDataEntity=new AMRDataEntity();
			final Object[] values = (Object[]) iterator.next();
			
			if((String)values[7]!=null && (String)values[6]!=null)
			{
				final List<?> amrDate =  amrBillService.findAMRDataReadingOnDate((String)values[7],(String)values[6],month2,month1);
				for(final Iterator<?> iteratorAMR = amrDate.iterator(); iteratorAMR.hasNext();){
					final Object[] valuesAMR = (Object[]) iteratorAMR.next();					
					amrDataEntity.setReadingDate((Timestamp)valuesAMR[0]);
					amrDataEntity.setElReading((Double)valuesAMR[1]);					
					LOGGER.info("readingDate:::::::::::::::::"+(Timestamp)valuesAMR[0]);
				}
				final List<?> amrDGDate = amrBillService.findAMRDGDataReadingOnDate((String)values[7],(String)values[6],month2,month1);
				for(final Iterator<?> iteratorDGAMR = amrDGDate.iterator(); iteratorDGAMR.hasNext();){					
					amrDataEntity.setDgReading((Double)iteratorDGAMR.next());
				}
				amrDataEntity.setAccountId((Integer)values[1]);
				amrDataEntity.setBlockId((Integer)values[4]);
				amrDataEntity.setPropertyId((Integer)values[3]);
				amrDataEntity.setPersonId((Integer)values[2]);
				amrDataEntity.setMeterId((Integer)values[0]);				
				amrEntityService.save(amrDataEntity);
				
				
			}
		}
		
		return null;
	}
	
}
