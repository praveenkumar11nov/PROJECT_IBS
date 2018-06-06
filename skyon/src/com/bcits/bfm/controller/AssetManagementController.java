package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.KeySelector.Purpose;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.AssetCategoryTree;
import com.bcits.bfm.model.AssetLocationTree;
import com.bcits.bfm.model.AssetMaintenance;
import com.bcits.bfm.model.AssetMaintenanceCost;
import com.bcits.bfm.model.AssetMaintenanceSchedule;
import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.model.AssetPhysicalSurvey;
import com.bcits.bfm.model.AssetPhysicalSurveyReport;
import com.bcits.bfm.model.AssetServiceHistory;
import com.bcits.bfm.model.AssetSpares;
import com.bcits.bfm.model.AssetWarranty;
import com.bcits.bfm.model.CostCenter;
import com.bcits.bfm.model.Department;
import com.bcits.bfm.model.JobTypes;
import com.bcits.bfm.model.MaintainanceDepartment;
import com.bcits.bfm.model.MaintainanceTypes;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.GenericService;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.service.customerOccupancyManagement.AddressService;
import com.bcits.bfm.service.customerOccupancyManagement.ContactService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.AssetCategoryTreeService;
import com.bcits.bfm.service.facilityManagement.AssetLocationTreeService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceCostService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceScheduleService;
import com.bcits.bfm.service.facilityManagement.AssetMaintenanceService;
import com.bcits.bfm.service.facilityManagement.AssetOwnerShipService;
import com.bcits.bfm.service.facilityManagement.AssetPhysicalSurveyReportService;
import com.bcits.bfm.service.facilityManagement.AssetPhysicalSurveyService;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.service.facilityManagement.AssetServiceHistoryService;
import com.bcits.bfm.service.facilityManagement.AssetSparesService;
import com.bcits.bfm.service.facilityManagement.AssetWarrantyService;
import com.bcits.bfm.service.facilityManagement.CostCenterService;
import com.bcits.bfm.service.facilityManagement.JobCalenderService;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.service.facilityManagement.MaintainanceDepartmentService;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class AssetManagementController extends FilterUnit {

	static Logger logger = LoggerFactory.getLogger(AssetManagementController.class);

	@Autowired
	private Validator validator;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	private AssetCategoryTreeService assetCategoryTreeService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private AssetSparesService assetSparesService;

	@Autowired
	private AssetWarrantyService assetWarrantyService;

	@Autowired
	private AssetMaintenanceService assetMaintenanceService;

	@Autowired
	private AssetMaintenanceCostService assetMaintenanceCostService;

	@Autowired
	private AssetMaintenanceScheduleService assetMaintenanceScheduleService;

	@Autowired
	private AssetOwnerShipService assetOwnerShipService;

	@Autowired
	private AssetServiceHistoryService assetServiceHistoryService;

	@Autowired
	private AssetPhysicalSurveyService assetPhysicalSurveyService;

	@Autowired
	private AssetPhysicalSurveyReportService assetPhysicalSurveyReportService;

	@Autowired
	private ItemMasterService itemMasterService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private AssetCategoryTreeService assetCategoryService;

	@Autowired
	private AssetLocationTreeService assetLocationTreeService;

	@Autowired
	private JobCalenderService jobCalenderService;

	@Autowired
	private MaintainanceTypesService maintainanceTypesService;

	@Autowired
	private MaintainanceDepartmentService maintainanceDepartmentService;

	@Autowired
	private JobTypesService jobTypesService;

	@Autowired
	private CostCenterService costCenterService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();


	/* =================================== Asset Master ================================ */

	@RequestMapping(value = "/assetmaster", method = RequestMethod.GET)
	public String indexAssetMaster(ModelMap model, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		model.addAttribute("assetNames", readAllAssets());

		return "asset/asset";
	}

	@RequestMapping(value = "/asset/read", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	List<?> readAllAssets() {
		return assetService.setResponse();
	}



	@RequestMapping(value = "/asset/saveorupdate/{action}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssets(@ModelAttribute("asset")Asset asset, BindingResult result,@PathVariable String action, SessionStatus status, HttpServletRequest req) throws ParseException
	{
		
		String mobstatus=req.getParameter("mobstatus");
		if(mobstatus!=null){
			String createby=req.getParameter("createdBy");
			String updateby=req.getParameter("updatedBy");
			
			asset.setCreatedBy(createby);
			asset.setUpdatedBy(updateby);
			asset.setAssetStatus("Created");
			
			
			
			String assetYear = req.getParameter("assetYear");
			asset.setAssetYear(dateTimeCalender.getDateToStore(assetYear));

			String purchaseDate = req.getParameter("purchaseDate");
			asset.setPurchaseDate(dateTimeCalender.getDateToStore(purchaseDate));

			String warrantyTill = req.getParameter("warrantyTill");
			asset.setWarrantyTill(dateTimeCalender.getDateToStore(warrantyTill));
			
			asset.setMaintainanceDepartment(maintainanceDepartmentService.find(Integer.parseInt(req.getParameter("mtDpIt"))));

			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
			Calendar cal = Calendar.getInstance(tz);		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			sdf.setCalendar(cal);		
			cal.setTime(asset.getAssetYear());
			cal.add(Calendar.YEAR, Integer.parseInt(asset.getUseFullLife()));		
			Date expiryYear = cal.getTime();		
			java.sql.Date sqlExpiryDate=new java.sql.Date(expiryYear.getTime());

			asset.setAssetLifeExpiry(sqlExpiryDate);

			if(StringUtils.equalsIgnoreCase(req.getParameter("ownerShip").trim(),"Leased")){

				asset.setSupplier("Leased");
				
			}else{
				asset.setAssetVendorId(1);
			}

			asset.setAssetDesc(asset.getAssetDesc().trim());
			asset.setAssetNotes(asset.getAssetNotes().trim());
			validator.validate(asset, result);

			if(action.equalsIgnoreCase("create")){

				assetService.save(asset);

				AssetWarranty assetWarranty = new AssetWarranty();
				assetWarranty.setAssetId(asset.getAssetId());
				assetWarranty.setWarrantyFromDate(asset.getPurchaseDate());
				assetWarranty.setWarrantyToDate(asset.getWarrantyTill());
				assetWarranty.setWarrantyType("Original");
				assetWarranty.setWarrantyValid("Yes");
				assetWarranty.setCreatedBy(createby);
				assetWarranty.setLastUpdatedBy(updateby);
				assetWarrantyService.save(assetWarranty);
			}
			else{
				
				assetService.update(asset);
			}
			
			return assetService.setResponse();
			
			
		}else{
			
			HttpSession session = req.getSession(true);
			String userName = (String) session.getAttribute("userId");
			
			asset.setCreatedBy(userName);
			asset.setUpdatedBy(userName);
			asset.setAssetStatus("Created");
		
		String assetYear = req.getParameter("assetYear");
		asset.setAssetYear(dateTimeCalender.getDateToStore(assetYear));

		String purchaseDate = req.getParameter("purchaseDate");
		asset.setPurchaseDate(dateTimeCalender.getDateToStore(purchaseDate));

		String warrantyTill = req.getParameter("warrantyTill");
		asset.setWarrantyTill(dateTimeCalender.getDateToStore(warrantyTill));
		
		asset.setMaintainanceDepartment(maintainanceDepartmentService.find(Integer.parseInt(req.getParameter("mtDpIt"))));

		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
		Calendar cal = Calendar.getInstance(tz);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setCalendar(cal);		
		cal.setTime(asset.getAssetYear());
		cal.add(Calendar.YEAR, Integer.parseInt(asset.getUseFullLife()));		
		Date expiryYear = cal.getTime();		
		java.sql.Date sqlExpiryDate=new java.sql.Date(expiryYear.getTime());

		asset.setAssetLifeExpiry(sqlExpiryDate);

		if(StringUtils.equalsIgnoreCase(req.getParameter("ownerShip").trim(),"Leased")){

			asset.setSupplier("Leased");
			
		}else{
			asset.setAssetVendorId(1);
		}

		asset.setAssetDesc(asset.getAssetDesc().trim());
		asset.setAssetNotes(asset.getAssetNotes().trim());
		validator.validate(asset, result);

		if(action.equalsIgnoreCase("create")){

			assetService.save(asset);

			AssetWarranty assetWarranty = new AssetWarranty();
			assetWarranty.setAssetId(asset.getAssetId());
			assetWarranty.setWarrantyFromDate(asset.getPurchaseDate());
			assetWarranty.setWarrantyToDate(asset.getWarrantyTill());
			assetWarranty.setWarrantyType("Original");
			assetWarranty.setWarrantyValid("Yes");
			assetWarranty.setCreatedBy(userName);
			assetWarranty.setLastUpdatedBy(userName);
			assetWarrantyService.save(assetWarranty);
		}
		else{
			
			assetService.update(asset);
		}
		return assetService.setResponse();
		}
	}

	@RequestMapping(value = "/asset/delete", method = RequestMethod.GET)
	public @ResponseBody
	Asset deleteAssets(@ModelAttribute("asset") Asset asset) {
		assetService.delete(asset.getAssetId());
		return asset;
	}
	
	
	 
		@SuppressWarnings("deprecation")
		@RequestMapping(value = "/asset/upload/{assetId}", method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String save(@RequestParam("file2") MultipartFile files,HttpServletRequest request,@PathVariable int assetId ) throws IOException {
		

		    //void uploadAssetDocument(@RequestParam MultipartFile files,	HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {

			logger.info("method : uploadAssetDocument");

			logger.info("Asset Id : "+assetId);
			
			String docType = request.getParameter("type");
			Blob blob;
			blob = Hibernate.createBlob(files.getInputStream());
			
			
			assetService.uploadAssetOnId(assetId, blob , docType);
			
			return "";
		}


	/**
	 * Upload Asset related documents
	 * 
	 * @param files			Multipart File
	 * @return          	none
	 * @since           	1.0
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/asset/upload", method = RequestMethod.POST)
	public @ResponseBody
	void uploadAssetDocument(@RequestParam MultipartFile files,	HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {

		logger.info("method : uploadAssetDocument");
		int assetId = Integer.parseInt(request.getParameter("assetId"));

		logger.info("Asset Id : "+assetId);

		String docType = request.getParameter("type");
		logger.info("*****Doc Type**************"+docType);
		Blob blob;
		blob = Hibernate.createBlob(files.getInputStream());
		assetService.uploadAssetOnId(assetId, blob , docType);
	}

	/**
	 * Download notice document of the staff
	 * 
	 * @param snId			Staff Notice Id
	 * @return          	document or get redirect to file-not-found error page 
	 * @since           	1.0
	 */
	@RequestMapping("/asset/download/{assetId}")
	public String downloadAsset(@PathVariable("assetId") Integer assetId,HttpServletResponse response) throws Exception {
        logger.info("in download method1");



		logger.info("method : downloadNotice");		
		Asset asset = assetService.find(assetId);
		if (asset.getAssetDocument() != null) {
			
			logger.info("in download method2");
			try {
				response.setHeader("Content-Disposition","inline;filename=\"" + asset.getAssetName());
				OutputStream out = response.getOutputStream();
				if(StringUtils.equalsIgnoreCase(".doc", asset.getAssetDocumentType()) || StringUtils.equalsIgnoreCase(".docx", asset.getAssetDocumentType()))
					response.setContentType("application/msword");
				else if(StringUtils.equalsIgnoreCase(".xls", asset.getAssetDocumentType()) || StringUtils.equalsIgnoreCase(".xlsx", asset.getAssetDocumentType())){
					logger.info("*****Doc Type**************"+asset.getAssetDocumentType());
					response.setContentType("application/vnd.ms-excel");
				}
				else
					response.setContentType(asset.getAssetDocumentType());
				IOUtils.copy(asset.getAssetDocument().getBinaryStream(), out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			
			logger.info("in download method3");
			return "filenotfound";
		}
	}
//------------------------------------download pdf mob-------------------------------------//
	
	
	/**
	 * Download notice document of the staff
	 * 
	 * @param snId			Staff Notice Id
	 * @return          	document or get redirect to file-not-found error page 
	 * @since           	1.0
	 */
	@RequestMapping("/asset/downloadmobile/{assetId}")
	public String downloadAssetMobile(@PathVariable("assetId") Integer assetId,HttpServletResponse response) throws Exception {
         logger.info("in download method1");

		logger.info("method : downloadNotice");		
		Asset asset = assetService.find(assetId);
		if (asset.getAssetDocument() != null) {
			
			logger.info("in download method2");
			try {
				response.setHeader("Content-Disposition","inline;filename=\"" + asset.getAssetName());
				OutputStream out = response.getOutputStream();
				if(StringUtils.equalsIgnoreCase(".doc", asset.getAssetDocumentType()) || StringUtils.equalsIgnoreCase(".docx", asset.getAssetDocumentType()))
					response.setContentType("application/msword");
				else if(StringUtils.equalsIgnoreCase(".xls", asset.getAssetDocumentType()) || StringUtils.equalsIgnoreCase(".xlsx", asset.getAssetDocumentType()))
					response.setContentType("application/vnd.ms-excel");
				else
					response.setContentType(asset.getAssetDocumentType());
				IOUtils.copy(asset.getAssetDocument().getBinaryStream(), out);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			
			logger.info("in download method33");
			return "";
		}
	}
	
	
	
	
	
	
	
	
	
	
	

	/* ================================== Asset : Asset Spares ================================== */

	@RequestMapping(value = "/asset/spares/read/{assetId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetSpares(@PathVariable int assetId) {
		return assetSparesService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/spares/create/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetSpares(@ModelAttribute("assetspares")AssetSpares assetSpares, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{
		assetSpares.setAssetId(assetId);
		String partYear = req.getParameter("partYear");
		assetSpares.setPartYear(dateTimeCalender.getDateToStore(partYear));
		validator.validate(assetSpares, result);		
		assetSparesService.save(assetSpares);
		return assetSparesService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/spares/update/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateAssetSpares(@ModelAttribute("assetspares")AssetSpares assetSpares, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{
		assetSpares.setAssetId(assetId);
		String partYear = req.getParameter("partYear");
		assetSpares.setPartYear(dateTimeCalender.getDateToStore(partYear));
		validator.validate(assetSpares, result);	
		assetSparesService.update(assetSpares);
		return assetSparesService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/spares/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetSpares deleteAssetSpares(@ModelAttribute("assetspares")AssetSpares assetSpares, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetSparesService.delete(assetSpares.getAsId());
		return assetSpares;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/spares/itemmaster", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getSpareFromItemMaster(@RequestParam("assetId")int assetId, @RequestParam("asId") int asId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		for (final Object[] itemObject : itemMasterService.getAllAssetSpares(assetId)) {
			result.add(new HashMap<String, Object>() {{
				put("imName", (String)itemObject[1]);
				put("imId", (Integer)itemObject[0]);
				put("imGroup", (String)itemObject[2]);
				put("imDescription", (String)itemObject[3]);
			}});
		}
		if(asId!=0){
			final List<AssetSpares> assetSpares = assetSparesService.getAssetSparesBasedOnId(asId);
			result.add(new HashMap<String, Object>() {{
				put("imName", assetSpares.get(0).getItemMaster().getImName());
				put("imId", assetSpares.get(0).getItemMaster().getImId());
				put("imGroup", assetSpares.get(0).getItemMaster().getImGroup());
				put("imDescription", assetSpares.get(0).getItemMaster().getImDescription());
			}});
		}
		return result;
	}

	/* ================================== Asset : Asset Warranty ==================================  */

	@RequestMapping(value = "/asset/warranty/read/{assetId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetWarranty(@PathVariable int assetId,Map<String,Object> map) {

		return assetWarrantyService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/warranty/create/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetWarranty(@ModelAttribute("assetwarranty")AssetWarranty assetWarranty, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{
		assetWarranty.setAssetId(assetId);		
		String warrantyFrom = req.getParameter("warrantyFromDate");
		String warrantyTo = req.getParameter("warrantyToDate");
		assetWarranty.setWarrantyFromDate(dateTimeCalender.getDateToStore(warrantyFrom));
		assetWarranty.setWarrantyToDate(dateTimeCalender.getDateToStore(warrantyTo));	
		validator.validate(assetWarranty, result);
		assetWarrantyService.save(assetWarranty);
		return assetWarrantyService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/warranty/update/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> uopdateAssetWarranty(@ModelAttribute("assetwarranty")AssetWarranty assetWarranty, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{
		assetWarranty.setAssetId(assetId);	
		String warrantyFrom = req.getParameter("warrantyFromDate");
		String warrantyTo = req.getParameter("warrantyToDate");
		assetWarranty.setWarrantyFromDate(dateTimeCalender.getDateToStore(warrantyFrom));
		assetWarranty.setWarrantyToDate(dateTimeCalender.getDateToStore(warrantyTo));	
		validator.validate(assetWarranty, result);
		assetWarrantyService.update(assetWarranty);
		return assetWarrantyService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/warranty/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetWarranty deleteAssetSpares(@ModelAttribute("assetwarranty")AssetWarranty assetWarranty, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetWarrantyService.delete(assetWarranty.getAwId());
		return assetWarranty;
	}

	@Scheduled(cron = "0 0 5 * * ?")
	public void checkWarranty(){

		List<AssetWarranty> assetWarrantyList = assetWarrantyService.executeSimpleQuery("select obj from AssetWarranty obj");	
		for(AssetWarranty assetWarranty: assetWarrantyList){	
			if(assetWarranty.getWarrantyToDate().getTime()< new Date().getTime() && !StringUtils.equalsIgnoreCase(assetWarranty.getWarrantyValid(),"No")){
				assetWarrantyService.executeDeleteQuery("Update AssetWarranty a set a.warrantyValid='No' where a.awId="+assetWarranty.getAwId());
			}
		}	
	}

	/* Cron to create Job Card before asset expiry*/
	@Scheduled(cron = "0 0 5 * * ?")
	public void jcWarrantyExpiry(){
		assetWarrantyService.warrantyJCCron();
	}


	/* ================================== Asset Master :  Maintenance ================================== */

	@RequestMapping(value = "/asset/maintenance/read/{assetId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetMP(@PathVariable int assetId,Map<String,Object> map) {
		return assetMaintenanceService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/maintenance/create/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetMP(@ModelAttribute("maintenance")AssetMaintenance assetMaintenance, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{
		assetMaintenance.setAssetId(assetId);
		String lastmainTained = req.getParameter("lastMaintained");
		if(lastmainTained!=null && !StringUtils.isEmpty(lastmainTained))
			assetMaintenance.setLastMaintained(dateTimeCalender.getDateToStore(lastmainTained));	
		validator.validate(assetMaintenance, result);
		assetMaintenanceService.save(assetMaintenance);
		return assetMaintenanceService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/maintenance/update/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> uopdateAssetMP(@ModelAttribute("maintenance")AssetMaintenance assetMaintenance, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{
		assetMaintenance.setAssetId(assetId);
		String lastmainTained = req.getParameter("lastMaintained");
		if(lastmainTained!=null && !StringUtils.isEmpty(lastmainTained))
			assetMaintenance.setLastMaintained(dateTimeCalender.getDateToStore(lastmainTained));		
		validator.validate(assetMaintenance, result);
		assetMaintenanceService.update(assetMaintenance);
		return assetMaintenanceService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/maintenance/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetMaintenance deleteAssetMP(@ModelAttribute("maintenance")AssetMaintenance assetMaintenance, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetMaintenanceService.delete(assetMaintenance.getAmpId());
		return assetMaintenance;
	}


	/* ================================== Asset Master : Maintenance Cost ================================== */

	@RequestMapping(value = "/asset/maintenancecost/read/{assetId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetMC(@PathVariable int assetId,Map<String,Object> map) {
		return assetMaintenanceCostService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/maintenancecost/create/{assetId}", method ={ RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
	List<?> createAssetMC(@ModelAttribute("maintenancecost")AssetMaintenanceCost assetMaintenanceCost, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{

		/*List<AssetServiceHistory> ashlist = assetServiceHistoryService.executeSimpleQuery("select a from AssetServiceHistory a where a.assetId="+assetId);
		if(!ashlist.isEmpty() && ashlist!=null)
			assetMaintenanceCost.setAshId(ashlist.get(0).getAshId());*/
		String amcDate = req.getParameter("amcDate");
		assetMaintenanceCost.setAmcDate(dateTimeCalender.getDateToStore(amcDate));		
		validator.validate(assetMaintenanceCost, result);
		assetMaintenanceCost.setAssetId(assetId);
		assetMaintenanceCostService.save(assetMaintenanceCost);
		return assetMaintenanceCostService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/maintenancecost/update/{assetId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> uopdateAssetMC(@ModelAttribute("maintenancecost")AssetMaintenanceCost assetMaintenanceCost, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, @PathVariable int assetId, HttpServletRequest req) throws ParseException{		
		List<AssetMaintenanceCost> amlist = assetMaintenanceCostService.getAmListBasedOnAmId(assetMaintenanceCost.getAmcId());	

		if(amlist.size()>0)
		{
			if(amlist.get(0).getAssetServiceHistory()!=null){
				assetMaintenanceCost.setAssetServiceHistory(assetServiceHistoryService.find(amlist.get(0).getAssetServiceHistory().getAshId()));
				String amcDate = req.getParameter("amcDate");
				assetMaintenanceCost.setAmcDate(dateTimeCalender.getDateToStore(amcDate));		
				validator.validate(assetMaintenanceCost, result);
				assetMaintenanceCost.setAssetId(assetId);
				assetMaintenanceCostService.update(assetMaintenanceCost);	
			}else{
				assetMaintenanceCost.setAssetServiceHistory(null);
				String amcDate = req.getParameter("amcDate");
				assetMaintenanceCost.setAmcDate(dateTimeCalender.getDateToStore(amcDate));		
				validator.validate(assetMaintenanceCost, result);
				assetMaintenanceCost.setAssetId(assetId);
				assetMaintenanceCostService.update(assetMaintenanceCost);
			}
		}

		return assetMaintenanceCostService.setResponse(assetId);
	}

	@RequestMapping(value = "/asset/maintenancecost/delete", method = RequestMethod.GET)
	public @ResponseBody
	void deleteAssetMC(HttpServletRequest request) {
		
		assetMaintenanceCostService.deleteAssetBasedOnAmcId(request.getParameter("amcId"));
	}

	/* ================================== Asset OwnerShip ==================================  */

	@RequestMapping(value = "/assetownership", method = RequestMethod.GET)
	public String indexAssetOwnerShip(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset OwnerShip", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/assetownership";
	}	

	@RequestMapping(value = "/asset/ownership/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetOwnerShip() {
		List<Map<String, Object>> response = assetOwnerShipService.setResponse();
		return response;
	}

	@RequestMapping(value = "/asset/ownership/create", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetOwnerShip(@ModelAttribute("assetownership") AssetOwnerShip assetOwnerShip, BindingResult result, SessionStatus status, HttpServletRequest req) throws ParseException {
		String aoStartDate = req.getParameter("aoStartDate");
		assetOwnerShip.setAoStartDate(dateTimeCalender.getDateToStore(aoStartDate));

		String aoEndDate = req.getParameter("aoEndDate");
		assetOwnerShip.setAoEndDate(dateTimeCalender.getDateToStore(aoEndDate));


		assetOwnerShipService.save(assetOwnerShip);
		List<Map<String, Object>> response = assetOwnerShipService.setResponse();
		return response ;
	}

	@RequestMapping(value = "/asset/ownership/update", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateAssetOwnerShip(@ModelAttribute("assetownership") AssetOwnerShip assetOwnerShip,BindingResult result, SessionStatus status, HttpServletRequest req) throws ParseException {
		String aoStartDate = req.getParameter("aoStartDate");
		assetOwnerShip.setAoStartDate(dateTimeCalender.getDateToStore(aoStartDate));

		String aoEndDate = req.getParameter("aoEndDate");
		assetOwnerShip.setAoEndDate(dateTimeCalender.getDateToStore(aoEndDate));	
		validator.validate(assetOwnerShip, result);	
		assetOwnerShipService.update(assetOwnerShip);
		List<Map<String, Object>> response = assetOwnerShipService.setResponse();
		return response ;
	}

	@RequestMapping(value = "/asset/ownership/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetOwnerShip deleteAssetOwnership(@ModelAttribute("assetownership")AssetOwnerShip assetOwnerShip, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetOwnerShipService.delete(assetOwnerShip.getAoId());
		return assetOwnerShip;
	}

	/* ================================== Asset Service History ==================================	*/

	@RequestMapping(value = "/assetservicehistory", method = RequestMethod.GET)
	public String indexAssetServiceHistory(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset Service History", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/assetservicehistory";
	}	

	@RequestMapping(value = "/asset/servicehistory/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetServiceHistory() {
		return assetServiceHistoryService.setResponse();
	}

	@RequestMapping(value = "/asset/servicehistory/create", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetServiceHistory(@ModelAttribute("assetservicehistory") AssetServiceHistory assetServiceHistory,BindingResult result, SessionStatus status, HttpServletRequest req) throws ParseException {

		int costOccured = Integer.parseInt(req.getParameter("costIncurred"));
		String ashDate = req.getParameter("ashDate");
		assetServiceHistory.setAshDate(dateTimeCalender.getDateToStore(ashDate));
		assetServiceHistory.setProblemDesc(assetServiceHistory.getProblemDesc().trim());
		assetServiceHistory.setServiceDesc(assetServiceHistory.getServiceDesc().trim());

		validator.validate(assetServiceHistory, result);	

		if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder"),"Paid Service")){
			AssetMaintenanceCost assetMaintenanceCost = assetMaintenanceCostService.mantenanceCostSetter(assetServiceHistory,req,"save");
			assetMaintenanceCost.setAssetId(assetServiceHistory.getAssetId());
			assetMaintenanceCostService.update(assetMaintenanceCost);
		}else if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder")," Warranty") && costOccured > 0){
			AssetMaintenanceCost assetMaintenanceCost = assetMaintenanceCostService.mantenanceCostSetter(assetServiceHistory,req,"save");
			assetMaintenanceCost.setAssetId(assetServiceHistory.getAssetId());
			assetMaintenanceCostService.update(assetMaintenanceCost);
		}else{
			assetServiceHistoryService.save(assetServiceHistory);
		}
		return assetServiceHistoryService.setResponse() ;
	}

	@RequestMapping(value = "/asset/servicehistory/update", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateAssetServiceHistory(@ModelAttribute("assetservicehistory") AssetServiceHistory assetServiceHistory,BindingResult result, SessionStatus status, HttpServletRequest req) throws ParseException {		

		int costOccured = Integer.parseInt(req.getParameter("costIncurred"));
		String ashDate = req.getParameter("ashDate");
		assetServiceHistory.setAshDate(dateTimeCalender.getDateToStore(ashDate));
		assetServiceHistory.setProblemDesc(assetServiceHistory.getProblemDesc().trim());
		assetServiceHistory.setServiceDesc(assetServiceHistory.getServiceDesc().trim());
		validator.validate(assetServiceHistory, result);		


		List<AssetMaintenanceCost> assetMaintenanceCosts = assetMaintenanceCostService.executeSimpleQuery("select obj from AssetMaintenanceCost obj where obj.assetServiceHistory.ashId = "+assetServiceHistory.getAshId());

		assetServiceHistory.setAshDate(dateTimeCalender.getDateToStore(ashDate));
		validator.validate(assetServiceHistory, result);	
		if(assetMaintenanceCosts.size()>0){
			if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder"),"Paid Service")){
				AssetMaintenanceCost assetMaintenanceCost = assetMaintenanceCostService.mantenanceCostSetter(assetServiceHistory,req,"update");
				assetMaintenanceCost.setAssetId(assetServiceHistory.getAssetId());
				assetMaintenanceCostService.update(assetMaintenanceCost);
			}else if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder")," Warranty") && costOccured > 0){
				AssetMaintenanceCost assetMaintenanceCost = assetMaintenanceCostService.mantenanceCostSetter(assetServiceHistory,req,"update");
				assetMaintenanceCost.setAssetId(assetServiceHistory.getAssetId());
				assetMaintenanceCostService.update(assetMaintenanceCost);
			}
			else if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder")," Warranty") && costOccured == 0){
				List<AssetMaintenanceCost> list = assetMaintenanceCostService.executeSimpleQuery("SELECT a FROM AssetMaintenanceCost a WHERE a.assetServiceHistory.ashId="+assetServiceHistory.getAshId());
				if(list.size()>0)
					assetMaintenanceCostService.executeDeleteQuery("Delete AssetMaintenanceCost a WHERE a.assetServiceHistory.ashId="+assetServiceHistory.getAshId());
				assetServiceHistoryService.update(assetServiceHistory);
			}
		}
		else{
			if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder"),"Paid Service")){
				AssetMaintenanceCost assetMaintenanceCost = assetMaintenanceCostService.mantenanceCostSetter(assetServiceHistory,req,"save");
				assetMaintenanceCost.setAssetId(assetServiceHistory.getAssetId());
				assetMaintenanceCostService.update(assetMaintenanceCost);
			}else if(StringUtils.equalsIgnoreCase(req.getParameter("servicedUnder")," Warranty") && costOccured > 0){
				AssetMaintenanceCost assetMaintenanceCost = assetMaintenanceCostService.mantenanceCostSetter(assetServiceHistory,req,"save");
				assetMaintenanceCost.setAssetId(assetServiceHistory.getAssetId());
				assetMaintenanceCostService.update(assetMaintenanceCost);
			}
			else{
				List<AssetMaintenanceCost> list = assetMaintenanceCostService.executeSimpleQuery("SELECT a FROM AssetMaintenanceCost a WHERE a.assetServiceHistory.ashId="+assetServiceHistory.getAshId());
				if(list.size()>0)
					assetMaintenanceCostService.executeDeleteQuery("Delete AssetMaintenanceCost a WHERE a.assetServiceHistory.ashId="+assetServiceHistory.getAshId());
				assetServiceHistoryService.update(assetServiceHistory);
			}
		}



		return assetServiceHistoryService.setResponse() ;
	}

	@RequestMapping(value = "/asset/servicehistory/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetServiceHistory deleteAssetServiceHistory(@ModelAttribute("AssetServiceHistory")AssetServiceHistory assetServiceHistory, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetServiceHistoryService.delete(assetServiceHistory.getAshId());
		return assetServiceHistory;
	}

	/* ================================== Physical Survey ================================== */

	@RequestMapping(value = "/assetphysicalsurvey", method = RequestMethod.GET)
	public String indexAssetPhysicalSurvey(ModelMap model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset Physical Survey", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/assetphysicalsurvey";
	}	

	@RequestMapping(value = "/asset/physicalsurvey/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetPhysicalSurvey() {
		return assetPhysicalSurveyService.setResponse();
	}

	@RequestMapping(value = "/asset/physicalsurvey/create", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetPhysicalSurvey(@ModelAttribute("assetphysicalsurvey") AssetPhysicalSurvey assetPhysicalSurvey,BindingResult result, SessionStatus status, HttpServletRequest req) throws ParseException {	


		String surveyDate = req.getParameter("surveyDate");
		if(surveyDate!=null && !StringUtils.isEmpty(surveyDate))
			assetPhysicalSurvey.setSurveyDate(dateTimeCalender.getDateToStore(surveyDate));	

		String surveyCompleteDate = req.getParameter("surveyCompleteDate");
		if(surveyCompleteDate!=null && !StringUtils.isEmpty(surveyCompleteDate))
			assetPhysicalSurvey.setSurveyCompleteDate(dateTimeCalender.getDateToStore(surveyCompleteDate));		

		String locIdsStr = assetPhysicalSurvey.getAssetLocIds();
		String catIdsStr = assetPhysicalSurvey.getAssetCatIds();

		AssetPhysicalSurveyReport assetPhysicalSurveyReport = new AssetPhysicalSurveyReport();	
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("SELECT a FROM Asset a WHERE 1=1 AND a.assetStatus='Approved' AND ");
		if(!StringUtils.isEmpty(catIdsStr) && StringUtils.isEmpty(locIdsStr) ){	
			strbuf.append("a.assetCatId IN("+catIdsStr+")");
		}
		if(!StringUtils.isEmpty(locIdsStr) && StringUtils.isEmpty(catIdsStr)){	
			strbuf.append("a.assetLocId IN("+locIdsStr+")");
		}
		if(!StringUtils.isEmpty(catIdsStr) && !StringUtils.isEmpty(locIdsStr)){
			strbuf.append("a.assetCatId IN("+catIdsStr+") OR a.assetLocId IN("+locIdsStr+")");
		}
		List<Asset> assetList = assetService.executeSimpleQuery(strbuf.toString());		
		Set<AssetPhysicalSurveyReport> assetPhysicalSurveyReportsset = assetPhysicalSurvey.getAssetPhysicalSurveyReports();	

		for(int i=0;i<assetList.size();i++){

			if(StringUtils.equalsIgnoreCase(assetList.get(i).getAssetStatus(),"Approved")){
				assetPhysicalSurveyReport = setAssetPhysicalSurveyReport(assetList.get(i).getAssetId());
				assetPhysicalSurveyReportsset.add(assetPhysicalSurveyReport);
				assetPhysicalSurvey.setAssetPhysicalSurveyReports(assetPhysicalSurveyReportsset);
			}
		}
		validator.validate(assetPhysicalSurvey, result);

		assetPhysicalSurvey.setSurveyDescription(assetPhysicalSurvey.getSurveyDescription().trim());

		assetPhysicalSurveyService.update(assetPhysicalSurvey);
		return assetPhysicalSurveyService.setResponse() ;
	}

	@RequestMapping(value = "/asset/physicalsurvey/{apsmId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object personStatus(@PathVariable int apsmId, @PathVariable String operation,HttpServletResponse response){		
		List<AssetPhysicalSurveyReport> checkChildForPhySurvey =assetPhysicalSurveyReportService.checkChildForPhySurvey(apsmId);
		logger.info("checkChildForPhySurvey size "+checkChildForPhySurvey.size());	

		if(StringUtils.containsIgnoreCase(operation, "Completed") && checkChildForPhySurvey.size() > 0){
			JsonResponse resp1 = new JsonResponse();
			resp1.setStatus("DENIED");
			resp1.setResult("Please complete the left "+checkChildForPhySurvey.size()+" Assets Survey");
			return resp1;
		}


		if(StringUtils.equalsIgnoreCase("Started", operation)){
			List<AssetPhysicalSurveyReport> list = assetPhysicalSurveyReportService.executeSimpleQuery("select obj from AssetPhysicalSurveyReport obj where obj.apsmId="+apsmId);
			if(list.size()==0){

				JsonResponse resp2 = new JsonResponse();
				resp2.setStatus("cant");
				resp2.setResult("Can't start the survey, no assets found");
				return resp2;
			}

		}
		assetPhysicalSurveyService.setPhysicalSurveyStatus(apsmId, operation, response);

		if(StringUtils.equalsIgnoreCase("Completed", operation)){			
			List<AssetPhysicalSurveyReport> assetPhysicalSurveyReport = assetPhysicalSurveyReportService.executeSimpleQuery("select obj from AssetPhysicalSurveyReport obj where obj.apsmId="+apsmId);
			for(int i=0;i<assetPhysicalSurveyReport.size();i++){				
				assetService.executeDeleteQuery("update Asset a set a.assetCondition='"+assetPhysicalSurveyReport.get(i).getAssetCondition()+"' where a.assetId="+assetPhysicalSurveyReport.get(i).getAssetId());			
				/*Asset asset = assetService.find(assetPhysicalSurveyReport.get(i).getAssetId());
				asset.setAssetCondition(assetPhysicalSurveyReport.get(i).getAssetCondition());
				assetService.update(asset);*/
			}
		}
		return null;
	}

	public AssetPhysicalSurveyReport setAssetPhysicalSurveyReport(int assetId){
		AssetPhysicalSurveyReport assetPhysicalSurveyReport = new AssetPhysicalSurveyReport();
		assetPhysicalSurveyReport.setAssetId(assetId);
		assetPhysicalSurveyReport.setAssetCondition(" --- ");
		assetPhysicalSurveyReport.setAssetNotes("");
		return assetPhysicalSurveyReport;
	}

	@RequestMapping(value = "/asset/physicalsurvey/update", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateAssetPhysicalSurvey(@ModelAttribute("assetphysicalsurvey") AssetPhysicalSurvey assetPhysicalSurvey,BindingResult result, SessionStatus status, HttpServletRequest req) throws ParseException {	
		String apsmDate = req.getParameter("apsmDate");
		if(apsmDate!=null && !StringUtils.isEmpty(apsmDate))
			assetPhysicalSurvey.setApsmDate(dateTimeCalender.getDateToStore(apsmDate));	
		String surveyDate = req.getParameter("surveyDate");
		if(surveyDate!=null && !StringUtils.isEmpty(surveyDate))
			assetPhysicalSurvey.setSurveyDate(dateTimeCalender.getDateToStore(surveyDate));
		String surveyCompleteDate = req.getParameter("surveyCompleteDate");
		if(surveyCompleteDate!=null && !StringUtils.isEmpty(surveyCompleteDate))
			assetPhysicalSurvey.setSurveyCompleteDate(dateTimeCalender.getDateToStore(surveyCompleteDate));	
		String locIdsStr = assetPhysicalSurvey.getAssetLocIds();
		String catIdsStr = assetPhysicalSurvey.getAssetCatIds();	
		assetPhysicalSurveyReportService.executeDeleteQuery("DELETE AssetPhysicalSurveyReport apsr WHERE apsr.apsmId="+assetPhysicalSurvey.getApsmId());
		AssetPhysicalSurveyReport assetPhysicalSurveyReport = new AssetPhysicalSurveyReport();		
		StringBuffer strbuf = new StringBuffer();

		strbuf.append("SELECT a FROM Asset a WHERE 1=1 AND a.assetStatus='Approved' AND ");


		if(!StringUtils.isEmpty(catIdsStr) && StringUtils.isEmpty(locIdsStr) ){	
			strbuf.append("a.assetCatId IN("+catIdsStr+")");
		}
		if(!StringUtils.isEmpty(locIdsStr) && StringUtils.isEmpty(catIdsStr)){	
			strbuf.append("a.assetLocId IN("+locIdsStr+")");
		}
		if(!StringUtils.isEmpty(catIdsStr) && !StringUtils.isEmpty(locIdsStr)){
			strbuf.append("a.assetCatId IN("+catIdsStr+") OR a.assetLocId IN("+locIdsStr+")");
		}
		List<Asset> assetList = assetService.executeSimpleQuery(strbuf.toString());		
		Set<AssetPhysicalSurveyReport> assetPhysicalSurveyReportsset = assetPhysicalSurvey.getAssetPhysicalSurveyReports();		
		for(int i=0;i<assetList.size();i++){	
			if(StringUtils.equalsIgnoreCase(assetList.get(i).getAssetStatus(),"Approved")){
				assetPhysicalSurveyReport = setAssetPhysicalSurveyReport(assetList.get(i).getAssetId());
				assetPhysicalSurveyReportsset.add(assetPhysicalSurveyReport);
				assetPhysicalSurvey.setAssetPhysicalSurveyReports(assetPhysicalSurveyReportsset);
			}
		}	
		validator.validate(assetPhysicalSurvey, result);
		assetPhysicalSurvey.setSurveyDescription(assetPhysicalSurvey.getSurveyDescription().trim());
		assetPhysicalSurveyService.update(assetPhysicalSurvey);
		return assetPhysicalSurveyService.setResponse() ;
	}

	@RequestMapping(value = "/asset/physicalsurvey/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetPhysicalSurvey deleteAssetPhysicalSurvey(@ModelAttribute("assetphysicalsurvey")AssetPhysicalSurvey assetPhysicalSurvey, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetPhysicalSurveyService.delete(assetPhysicalSurvey.getApsmId());
		return assetPhysicalSurvey;
	}

	/* ================================== Physical Survey : Physical Survey Report ================================== */

	@RequestMapping(value = "/asset/physicalsurveyreport/read/{apsmId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetPhysicalSurveyReport(@PathVariable int apsmId) {
		return assetPhysicalSurveyReportService.setResponse(apsmId);
	}

	@RequestMapping(value = "/asset/physicalsurveyreport/create/{apsmId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetPhysicalSurveyReport(@ModelAttribute("assetphysicalsurvey") AssetPhysicalSurveyReport assetPhysicalSurveyReport,@PathVariable int apsmId) {
		assetPhysicalSurveyReport.setApsmId(apsmId);
		assetPhysicalSurveyReportService.save(assetPhysicalSurveyReport);
		return assetPhysicalSurveyReportService.setResponse(apsmId);
	}

	@RequestMapping(value = "/asset/physicalsurveyreport/update/{apsmId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateAssetPhysicalSurveyReport(@ModelAttribute("assetphysicalsurvey") AssetPhysicalSurveyReport assetPhysicalSurveyReport,@PathVariable int apsmId,HttpServletRequest request) {	
		assetPhysicalSurveyReport.setApsmId(apsmId);


		assetPhysicalSurveyReportService.update(assetPhysicalSurveyReport);
		return assetPhysicalSurveyReportService.setResponse(apsmId);
	}

	/* ==================================  Maintenance Schedule ================================== */

	@RequestMapping(value = "/assetmaintenanceschedule", method = RequestMethod.GET)
	public String indexAssetMaintenanceSchedule(ModelMap model, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset Maintenance Schedule", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/assetmaintenanceschedule";
	}

	@RequestMapping(value = "/asset/maintenanceschedule/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetMaintSch() {
		return assetMaintenanceScheduleService.setResponse();
	}


	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/maintenanceschedule/readjobcalender/{amsId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readjobcalender(@PathVariable int amsId) {

		logger.info("method : readStaffTraining");
		List<Map<String, Object>> manpower = new ArrayList<Map<String, Object>>();
		for (final Object[] record : jobCalenderService.findRequiredFieldOnAmsId(amsId)) {
			manpower.add(new HashMap<String, Object>() {{
				put("jobCalenderId", (Integer)record[0]);
				put("title", (String) record[1]+" ("+new java.sql.Date(((Date) record[4]).getTime())+")");
				put("jobNumber", (String) record[2]);
				put("jobGroup", (String) record[3]);
				put("start",ConvertDate.TimeStampString(new java.sql.Timestamp(((Date)record[4]).getTime())));
				put("end",ConvertDate.TimeStampString(new java.sql.Timestamp(((Date)record[5]).getTime())));
			}
			});
		}
		return manpower;
	}

	@RequestMapping(value = "/asset/maintenanceschedule/create", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createAssetMaintSch(@ModelAttribute("maintenanceschedule")AssetMaintenanceSchedule assetMaintenanceSchedule, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, HttpServletRequest req) throws ParseException{
		String taskStartDate = req.getParameter("taskStartDate");
		assetMaintenanceSchedule.setTaskStartDate(dateTimeCalender.getDateToStore(taskStartDate));
		String taskEndDate = req.getParameter("taskEndDate");
		assetMaintenanceSchedule.setTaskEndDate(dateTimeCalender.getDateToStore(taskEndDate));
		String taskLastExecuted = req.getParameter("taskLastExecuted");
		assetMaintenanceSchedule.setTaskLastExecuted(dateTimeCalender.getDateToStore(taskLastExecuted));

		assetMaintenanceSchedule.setTaskDescription(assetMaintenanceSchedule.getTaskDescription().trim());
		assetMaintenanceSchedule.setExpectedResult(assetMaintenanceSchedule.getExpectedResult().trim());

		validator.validate(assetMaintenanceSchedule, result);

		assetMaintenanceScheduleService.save(assetMaintenanceSchedule);
		return assetMaintenanceScheduleService.setResponse();
	}

	@RequestMapping(value = "/asset/maintenanceschedule/update", method = RequestMethod.GET)
	public @ResponseBody
	List<?> uopdateAssetMaintSch(@ModelAttribute("maintenanceschedule")AssetMaintenanceSchedule assetMaintenanceSchedule, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException{

		String taskStartDate = req.getParameter("taskStartDate");
		assetMaintenanceSchedule.setTaskStartDate(dateTimeCalender.getDateToStore(taskStartDate));	
		String taskEndDate = req.getParameter("taskEndDate");
		assetMaintenanceSchedule.setTaskEndDate(dateTimeCalender.getDateToStore(taskEndDate));	
		String taskLastExecuted = req.getParameter("taskLastExecuted");
		assetMaintenanceSchedule.setTaskLastExecuted(dateTimeCalender.getDateToStore(taskLastExecuted));
		assetMaintenanceSchedule.setTaskDescription(assetMaintenanceSchedule.getTaskDescription().trim());
		assetMaintenanceSchedule.setExpectedResult(assetMaintenanceSchedule.getExpectedResult().trim());
		validator.validate(assetMaintenanceSchedule, result);
		assetMaintenanceScheduleService.update(assetMaintenanceSchedule);
		return assetMaintenanceScheduleService.setResponse();
	}

	@RequestMapping(value = "/asset/maintenanceschedule/delete", method = RequestMethod.GET)
	public @ResponseBody
	AssetMaintenanceSchedule deleteAssetMaintSch(@ModelAttribute("maintenanceschedule")AssetMaintenanceSchedule assetMaintenanceSchedule, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		assetMaintenanceScheduleService.delete(assetMaintenanceSchedule.getAmsId());
		return assetMaintenanceSchedule;
	}


	@RequestMapping(value = "/asset/assetmaintenanceschedule/updatestatus/{amsId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody AssetMaintenanceSchedule assetMSStatus(@PathVariable int amsId, @PathVariable String operation,HttpServletResponse response){		

		assetMaintenanceScheduleService.setAmsStatus(amsId, operation, response);
		if(StringUtils.equalsIgnoreCase(operation, "Approved"))
			createJobCalender(amsId);
		return null;
	}


	//@Scheduled(cron = "*/20 * * * * ?")
	public void createJobCalender(int amsId){

		List<AssetMaintenanceSchedule> amsList = assetMaintenanceScheduleService.executeSimpleQuery("SELECT a FROM AssetMaintenanceSchedule a WHERE a.amsStatus = 'Approved' AND a.amsId="+amsId);
		for(AssetMaintenanceSchedule assetMaintenanceSchedule : amsList){
			DateTime startDate = new DateTime(assetMaintenanceSchedule.getTaskStartDate().getTime());
			DateTime endDate = new DateTime(assetMaintenanceSchedule.getTaskEndDate().getTime());
			List<DateTime> dates = new ArrayList<DateTime>();
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Daily")){
				int noOfDays = Days.daysBetween(startDate, endDate).getDays();
				for (int i=0; i <= noOfDays; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);
					dates.add(d);
				}
				for(int i =0; i <= noOfDays;i++){
					assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Weekly")){
				int noOfWeeks = Weeks.weeksBetween(startDate, endDate).getWeeks();
				for (int i=0; i <= noOfWeeks; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.weeks(), i);
					dates.add(d);
				}
				for(int i =0; i <= noOfWeeks;i++){
					assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Monthly")){
				int noOfMonths = Months.monthsBetween(startDate, endDate).getMonths();
				for (int i=0; i <= noOfMonths; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.months(), i);
					dates.add(d);
				}
				for(int i =0; i<= noOfMonths;i++){
					assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Annually")){
				int noOfYears = Years.yearsBetween(startDate, endDate).getYears();
				for (int i=0; i <= noOfYears; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.years(), i);
					dates.add(d);
				}
				for (int i =0; i <= noOfYears; i++){
					assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Fortnightly")){
				int noOfFortnightly = Weeks.weeksBetween(startDate, endDate).getWeeks();
				for (int i=0; i <= noOfFortnightly; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.weeks(), i);
					dates.add(d);
				}
				for(int i =0; i<= noOfFortnightly; i++){
					if(i%2==0)
						assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Quarterly")){
				int noOfQuarterly = Months.monthsBetween(startDate, endDate).getMonths();
				for (int i=0; i <= noOfQuarterly; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.months(), i);
					dates.add(d);
				}
				for(int i =0;i<=noOfQuarterly;i++){
					if(i%3==0)
						assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}
			if(StringUtils.equalsIgnoreCase(assetMaintenanceSchedule.getTaskFrequency(),"Once in 6 Month")){
				int noOfHalfYear = Months.monthsBetween(startDate, endDate).getMonths();
				for (int i=0; i <= noOfHalfYear; i++) {
					DateTime d = startDate.withFieldAdded(DurationFieldType.months(), i);
					dates.add(d);
				}
				for(int i =0; i<= noOfHalfYear;i++){
					if(i%6==0)
						assetMaintenanceScheduleService.populateJobCalender(assetMaintenanceSchedule,dates,i);
				}
			}			
		}
	}

	/* ================================== Asset Category ================================== */

	@RequestMapping(value = "/assetcat", method = RequestMethod.GET)
	public String indexAssetCat(ModelMap model, HttpServletRequest request)throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset Category",2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/assetcategory";
	}

	@RequestMapping(value = "/asset/category/read", method = RequestMethod.POST)
	public @ResponseBody
	List<AssetCategoryTree> readAsset(@RequestBody Map<String, Object> model) {
		return assetCategoryTreeService.findAllOnParentId((Integer) model.get("assetcatId"));
	}

	@RequestMapping(value = "/asset/cat/read", method = RequestMethod.GET)
	public @ResponseBody
	List<AssetCategoryTree> readAsset(HttpServletRequest req) {
		Integer setId=null;
		if(req.getParameter("assetcatId")!=null)
			setId = Integer.valueOf(req.getParameter("assetcatId"));
		return assetCategoryTreeService.findAllOnParentId(setId);
	}

	@RequestMapping(value = "/asset/cat/add", method = RequestMethod.POST)
	public String addNode(@RequestParam("nodename") String nodename,
			@RequestParam("assetcatId") Integer assetcatId, @RequestParam("treeHierarchy") String treeHierarchy,
			HttpServletRequest request, HttpServletResponse response) {
		AssetCategoryTree asset = new AssetCategoryTree();
		PrintWriter out;
		asset.setAssetcatText(nodename);
		asset.setParentId(assetcatId);
		asset.setTreeHierarchy(treeHierarchy+" > "+nodename);
		assetCategoryTreeService.save(asset);
		List<AssetCategoryTree> getId = assetCategoryTreeService.findIdByParent(assetcatId, nodename);
		int Value = getId.get(0).getAssetcatId();
		try {
			out = response.getWriter();
			out.write(Value + "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}




	@RequestMapping(value = "/asset/cat/update", method = RequestMethod.POST)
	public String editNode(@RequestParam("nodename") String assetcatText,
			@RequestParam("assetcatId") int assetcatId, @RequestParam("treeHierarchy") String treeHierarchy,
			HttpServletRequest request, HttpServletResponse response) {
		AssetCategoryTree asset = new AssetCategoryTree();
		asset.setAssetcatId(assetcatId);
		List<AssetCategoryTree> list = assetCategoryTreeService.findAllOnAssetCatId(assetcatId);
		Integer parentId = list.get(0).getParentId();
		asset.setAssetcatText(assetcatText);
		asset.setParentId(parentId);
		asset.setTreeHierarchy(treeHierarchy);
		assetCategoryTreeService.update(asset);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write("Updated Successfully!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/asset/cat/delete", method = RequestMethod.POST)
	public void deletNode(@RequestParam("assetcatId") int assetcatId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out;
		List<AssetCategoryTree> list = assetCategoryTreeService
				.findAllOnParentId(assetcatId);
		if (list.size() == 0) {
			try {
				assetCategoryTreeService.delete(assetcatId);
				out = response.getWriter();
				out.write("Deleted Successfully!!!");
			} catch (Exception e) {
				out = response.getWriter();
				out.write("Can't be deleted! Asset is Assigned to this category");
			}
		} else {
			out = response.getWriter();
			out.write("Only Leaf node can be deleted!!!");
		}
	}

	@RequestMapping(value="/asset/cat/getdetailoncatid",method=RequestMethod.POST) 
	public @ResponseBody
	AssetCategoryTree getCategoryDetailOnCatId(@RequestParam("assetcatId") int assetcatId) {

		return assetCategoryTreeService.find(assetcatId);
	}

	/* ================================== Asset Location ================================== */

	@RequestMapping(value = "/assetloc", method = RequestMethod.GET)
	public String indexAssetLoc(ModelMap model,HttpServletRequest request) 
	{
		model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Asset Location", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/assetlocation";
	}	

	@RequestMapping(value = "/asset/location/read", method = RequestMethod.POST)
	public @ResponseBody List<AssetLocationTree> readAssetLocationTree(@RequestBody Map<String, Object> model) {
		return assetLocationTreeService.findAllOnParentId((Integer)model.get("assetlocId"));
	}

	@RequestMapping(value = "/asset/loc/read", method = RequestMethod.GET)
	public @ResponseBody
	List<AssetLocationTree> readAssetLocationTree(HttpServletRequest req) {
		Integer setId=null;
		if(req.getParameter("assetlocId")!=null)
			setId = Integer.valueOf(req.getParameter("assetlocId"));
		return assetLocationTreeService.findAllOnParentId(setId);
	}


	@RequestMapping(value = "/asset/loc/getlocationtype", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getLocationType(HttpServletRequest request) {

		/*List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final String manpower : assetLocationTreeService.findAllLocationType()) {
			result.add(new HashMap<String, Object>() {{
				if(manpower!=null && manpower != ""){
					put("locationType", manpower);
				}
			}
			});
		}*/
		return assetLocationTreeService.findAllLocationType();
	}


	@RequestMapping(value="/asset/loc/add",method=RequestMethod.POST) public
	String addAssetLocation(
			@RequestParam("nodename") String nodename,
			@RequestParam("assetlocId") Integer assetlocId,
			@RequestParam("treeHierarchy") String treeHierarchy,
			@RequestParam("locationAddress") String locationAddress ,
			@RequestParam("locationContactDetails") String locationContactDetails ,
			@RequestParam("locationGeoCode") String locationGeoCode ,
			@RequestParam("locationType") String locationType ,
			HttpServletRequest request,HttpServletResponse response) {	
		AssetLocationTree asset=new AssetLocationTree();
		PrintWriter out;
		asset.setAssetlocText(nodename);
		asset.setParentId(assetlocId);
		asset.setTreeHierarchy(treeHierarchy+" > "+nodename);
		asset.setLocationAddress(locationAddress);
		asset.setContactDetails(locationContactDetails);
		asset.setGeoCode(locationGeoCode);
		asset.setLocationType(locationType);

		assetLocationTreeService.save(asset);
		List<AssetLocationTree> getId=assetLocationTreeService.findIdByParent(assetlocId,nodename);
		int Value = getId.get(0).getAssetlocId();
		try {
			out = response.getWriter();
			out.write(Value+"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="/asset/loc/update",method=RequestMethod.POST) public
	String editAssetLocation(
			@RequestParam("nodename") String nodename,
			@RequestParam("assetlocId") Integer assetlocId,
			@RequestParam("treeHierarchy") String treeHierarchy,
			@RequestParam("locationAddress") String locationAddress ,
			@RequestParam("locationContactDetails") String locationContactDetails ,
			@RequestParam("locationGeoCode") String locationGeoCode ,
			@RequestParam("locationType") String locationType ,
			HttpServletRequest request,HttpServletResponse response) {
		PrintWriter out;
		AssetLocationTree asset=new AssetLocationTree();
		asset.setAssetlocId(assetlocId);
		List<AssetLocationTree> list=assetLocationTreeService.findAllOnAssetLocId(assetlocId);
		Integer parentId=list.get(0).getParentId();
		asset.setAssetlocText(nodename);
		asset.setParentId(parentId);
		asset.setTreeHierarchy(treeHierarchy);
		asset.setLocationAddress(locationAddress);
		asset.setContactDetails(locationContactDetails);
		asset.setGeoCode(locationGeoCode);
		asset.setLocationType(locationType);
		assetLocationTreeService.update(asset);
		try {
			out = response.getWriter();
			out.write("Updated Successfully!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="/asset/loc/delete",method=RequestMethod.POST) public
	void deletAssetLocation(@RequestParam("assetlocId") int assetlocId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out;
		List<AssetLocationTree> list=assetLocationTreeService.findAllOnParentId(assetlocId);
		if(list.size()==0){

			try{
				assetLocationTreeService.delete(assetlocId);
				out = response.getWriter();
				out.write("Deleted Successfully!!!");
			}catch(Exception e){
				out = response.getWriter();
				out.write("Can't be deleted, Assets are assigned to this Location");
			}
		}
		else{
			out = response.getWriter();
			out.write("Only Leaf node can be deleted!!!");		
		}
	}

	@RequestMapping(value="/asset/loc/getdetailonlocid",method=RequestMethod.POST) 
	public @ResponseBody
	AssetLocationTree getLocationDetailOnLocId(@RequestParam("assetlocId") int assetlocId) {

		return assetLocationTreeService.find(assetlocId);
	}


	/* ================================== Asset : Miscellaneous ================================== */

	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/getAllAssetsOnCatId/{assetCatId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAssetOnCatIdForDropDown(@PathVariable int assetCatId,HttpServletRequest request) {
		String ownerShip = request.getParameter("from");
		String query = "";
		if(StringUtils.equalsIgnoreCase(ownerShip, "ownership")){
			query = "SELECT a FROM Asset a WHERE a.assetCatId = "+assetCatId+" AND a.assetId NOT IN (SELECT ao.assetId FROM AssetOwnerShip ao WHERE ao.aoEndDate > sysdate ) AND a.assetStatus= 'Approved'";
		}else{
			query = "SELECT a FROM Asset a WHERE a.assetCatId = "+assetCatId+" AND a.assetStatus = 'Approved'";
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<Asset> assetLisdt = assetService.executeSimpleQuery(query);		
		for (final Asset assetCatDropDown : assetLisdt) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", assetCatDropDown.getAssetName());
				put("assetId", assetCatDropDown.getAssetId());
			}});
		}

		if(StringUtils.equalsIgnoreCase(ownerShip, "ownership") && request.getParameter("assetId") != null){
			int assetId = Integer.valueOf(request.getParameter("assetId"));

			if(assetId!=0){
				final List<Asset> assetList = assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetId = "+assetId+" AND a.assetStatus= 'Approved'");
				result.add(new HashMap<String, Object>() {{
					put("assetName", assetList.get(0).getAssetName());
					put("assetId", assetList.get(0).getAssetId());
				}});
			}
		}

		return result;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/getAllAssetsOnLocId/{assetLocId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAssetOnLocId(@PathVariable int assetLocId,HttpServletRequest request) {

		String ownerShip = request.getParameter("from");
		String query = "";
		if(StringUtils.equalsIgnoreCase(ownerShip, "ownership")){
			query = "SELECT a FROM Asset a WHERE a.assetLocId = "+assetLocId+" AND a.assetId NOT IN (SELECT ao.assetId FROM AssetOwnerShip ao WHERE ao.aoEndDate > sysdate ) AND a.assetStatus= 'Approved'";
		}else{
			query = "SELECT a FROM Asset a WHERE a.assetLocId = "+assetLocId+" AND a.assetStatus = 'Approved'";
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<Asset> assetLisdt = assetService.executeSimpleQuery(query);		
		for (final Asset assetCatDropDown : assetLisdt) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", assetCatDropDown.getAssetName());
				put("assetId", assetCatDropDown.getAssetId());
			}});
		}

		if(StringUtils.equalsIgnoreCase(ownerShip, "ownership") && request.getParameter("assetId") != null ){
			int assetId = Integer.valueOf(request.getParameter("assetId"));
			if(assetId!=0){
				final List<Asset> assetList = assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetId = "+assetId+" AND a.assetStatus= 'Approved'");
				result.add(new HashMap<String, Object>() {{
					put("assetName", assetList.get(0).getAssetName());
					put("assetId", assetList.get(0).getAssetId());
				}});
			}
		}

		return result;
	}



	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/readMaintainanceType", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readMaintainanceType(HttpServletRequest request) {
		String query = "SELECT m FROM MaintainanceTypes m";

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<MaintainanceTypes> maintainanceTypes = maintainanceTypesService.executeSimpleQuery(query);		
		for (final MaintainanceTypes maintainanceTypes2 : maintainanceTypes) {
			result.add(new HashMap<String, Object>() {{
				put("maintainanceType", maintainanceTypes2.getMaintainanceType());
				put("mtId", maintainanceTypes2.getMtId());
			}});
		}


		return result;
	}


	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/readJobType", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readJobType(HttpServletRequest request) {
		String query = "SELECT j FROM JobTypes j";

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<JobTypes> jobTypes = jobTypesService.executeSimpleQuery(query);		
		for (final JobTypes record : jobTypes) {
			result.add(new HashMap<String, Object>() {{
				put("jtType", record.getJtType());
				put("jtId", record.getJtId());
			}});
		}
		return result;
	}

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@RequestMapping(value = "/asset/readMaintDept", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readMaintDept(HttpServletRequest request) {
		//String query = "SELECT m FROM MaintainanceDepartment m";

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		Set<String> ids=new HashSet();
		for (final Object[] record : maintainanceDepartmentService.getMaintDepartments()) {
			if(!(ids.contains(record[1]+""))){
				result.add(new HashMap<String, Object>() {{
					put("mtDpIt", (Integer)record[0]);
					put("mtDpName", (String)record[1]);
				}});

				ids.add(record[1]+"");
			}
		}
		return result;
	}




	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/getAllAssetsForAll", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAssetForAll(HttpServletRequest request) {
		String ownerShip = request.getParameter("from");

		String query = "";
		if(StringUtils.equalsIgnoreCase(ownerShip, "ownership")){
			query = "SELECT a FROM Asset a WHERE a.assetId NOT IN (SELECT ao.assetId FROM AssetOwnerShip ao WHERE ao.aoEndDate > sysdate ) AND a.assetStatus= 'Approved'";
		}else{
			query = "SELECT a FROM Asset a WHERE a.assetStatus = 'Approved'";
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<Asset> assetLisdt = assetService.executeSimpleQuery(query);		
		for (final Asset assetCatDropDown : assetLisdt) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", assetCatDropDown.getAssetName());
				put("assetId", assetCatDropDown.getAssetId());
				put("assetLocation", assetCatDropDown.getAssetLocationTree().getTreeHierarchy());
			}});
		}

		if(StringUtils.equalsIgnoreCase(ownerShip, "ownership") && request.getParameter("assetId") != null ){
			int assetId = Integer.valueOf(request.getParameter("assetId"));
			if(assetId!=0){
				final List<Asset> assetList = assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetId = "+assetId+" AND a.assetStatus= 'Approved'");
				result.add(new HashMap<String, Object>() {{
					put("assetName", assetList.get(0).getAssetName());
					put("assetId", assetList.get(0).getAssetId());
					put("assetLocation", assetList.get(0).getAssetLocationTree().getTreeHierarchy());
				}});
			}
		}

		return result;
	}


	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/readAssetAll", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetAll() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		for (final Asset assetCatDropDown : assetService.findAll()) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", assetCatDropDown.getAssetName());
				put("assetId", assetCatDropDown.getAssetId());
			}});
		}
		return result;	
	}



	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/readassetforownersship/{assetId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readAssetForOwnersShipUrl(@PathVariable Integer assetId) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		List<Asset> assetLisdt = assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetId NOT IN (SELECT ao.assetId FROM AssetOwnerShip ao WHERE ao.aoEndDate > sysdate ) AND a.assetStatus= 'Approved'");		
		for (final Asset assetCatDropDown : assetLisdt) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", assetCatDropDown.getAssetName());
				put("assetId", assetCatDropDown.getAssetId());
			}});
		}
		if(assetId != 0){
			final List<Asset> assetList = assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetId = "+assetId+" AND a.assetStatus= 'Approved'");
			result.add(new HashMap<String, Object>() {{
				put("assetName", assetList.get(0).getAssetName());
				put("assetId", assetList.get(0).getAssetId());
			}});
		}
		return result;	
	}




	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/assetsoncatselect/{assetCatId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAssetOnCatIdAtCategory(@PathVariable int assetCatId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  

		for (final Asset asset : assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetCatId="+assetCatId)) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", asset.getAssetName());
				put("assetId", asset.getAssetId());
				put("assetCondition", asset.getAssetCondition());
				put("assetType", asset.getAssetType());
				put("assetTag", asset.getAssetTag());
				put("assetStatus", asset.getAssetStatus());
			}});
		}
		return result;	
	}

	@RequestMapping(value = "/asset/allassetsoncatselect/{assetCatId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAllAssetOnCatIdAtCategory(@PathVariable int assetCatId) throws InterruptedException {

		Set<AssetCategoryTree> spawns = new HashSet<AssetCategoryTree>();

		List<AssetCategoryTree> nextInLine = assetCategoryTreeService.executeSimpleQuery("SELECT a FROM AssetCategoryTree a WHERE a.parentId = "+assetCatId);
		logger.info("-------------> "+nextInLine.size());
		while (!nextInLine.isEmpty())
		{
			String ids="";
			for (AssetCategoryTree child: nextInLine)
			{
				ids+=child.getAssetcatId()+",";
				spawns.add(child);
			}
			ids+="100";
			Thread.sleep(2000);
			nextInLine = assetCategoryTreeService.executeSimpleQuery("SELECT a FROM AssetCategoryTree a WHERE a.parentId IN ("+ids+")");
		}

		logger.info(">>>>>>>>>"+spawns.size()+"==========>>"+spawns);
		List<Asset> assetList = new ArrayList<Asset>();
		for (AssetCategoryTree assetCategoryTree : spawns) {
			List<Asset> assetEntityList = assetService.executeSimpleQuery("select o from Asset o where o.assetCatId = "+assetCategoryTree.getAssetcatId());
			if(!assetEntityList.isEmpty()){
				for (Asset asset : assetEntityList) {
					Asset asetEntity = new Asset();
					asetEntity.setAssetName(asset.getAssetName());
					asetEntity.setAssetStatus(asset.getAssetStatus());
					asetEntity.setAssetCondition(asset.getAssetCondition());
					asetEntity.setAssetType(asset.getAssetType());
					asetEntity.setAssetTag(asset.getAssetTag());
					assetList.add(asetEntity);
				}
			}
		}
		return assetList;	
	}

	@RequestMapping(value = "/asset/allassetsonlocselect/{assetLocId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAllAssetOnLocIdAtLocation(@PathVariable int assetLocId) throws InterruptedException {

		List<AssetLocationTree> spawns = new ArrayList<AssetLocationTree>();

		List<AssetLocationTree> nextInLine = assetLocationTreeService.executeSimpleQuery("SELECT a FROM AssetLocationTree a WHERE a.parentId = "+assetLocId);
		while (!nextInLine.isEmpty())
		{
			String ids="";
			for (AssetLocationTree child: nextInLine)
			{
				ids+=child.getAssetlocId()+",";
				spawns.add(child);
			}
			ids+="100";
			Thread.sleep(2000);
			nextInLine = assetLocationTreeService.executeSimpleQuery("SELECT a FROM AssetLocationTree a WHERE a.parentId IN ("+ids+")");
		}

		logger.info(">>>>>>>>>"+spawns.size()+"==========>>"+spawns);
		List<Asset> assetList = new ArrayList<Asset>();
		for (AssetLocationTree assetLocationTree : spawns) {
			List<Asset> assetEntityList = assetService.executeSimpleQuery("select o from Asset o where o.assetLocId = "+assetLocationTree.getAssetlocId());
			if(!assetEntityList.isEmpty()){
				for (Asset asset : assetEntityList) {
					Asset asetEntity = new Asset();
					asetEntity.setAssetName(asset.getAssetName());
					asetEntity.setAssetStatus(asset.getAssetStatus());
					asetEntity.setAssetCondition(asset.getAssetCondition());
					asetEntity.setAssetType(asset.getAssetType());
					asetEntity.setAssetTag(asset.getAssetTag());
					assetList.add(asetEntity);
				}
			}
		}
		return assetList;	
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/assetsonlocselect/{assetLocId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readAssetOnLocIdAtLocation(@PathVariable int assetLocId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
		for (final Asset asset : assetService.executeSimpleQuery("SELECT a FROM Asset a WHERE a.assetLocId="+assetLocId)) {
			result.add(new HashMap<String, Object>() {{
				put("assetName", asset.getAssetName());
				put("assetId", asset.getAssetId());
				put("assetCondition", asset.getAssetCondition());
				put("assetType", asset.getAssetType());
				put("assetTag", asset.getAssetTag());
				put("assetStatus", asset.getAssetStatus());
			}});
		}
		return result;	
	}


	@RequestMapping(value = "/asset/updatestatus/{assetId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Asset assetStatus(@PathVariable int assetId, @PathVariable String operation,HttpServletResponse response){		

		assetService.setAssetStatus(assetId, operation, response);
		return null;
	}

	@RequestMapping(value = "/asset/updatecategory/{categoryId}/{assetId}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody void updateAssetCategory(@PathVariable int categoryId, @PathVariable int assetId,HttpServletResponse response){		
		assetService.executeDeleteQuery("update Asset a set a.assetCatId="+categoryId+" where a.assetId="+assetId);
	}


	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/getstaff", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllPersonNames(HttpServletRequest request) {
		logger.info("method : getAllPersonNames");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Object[] manpower : personService.findAllStaff()) {
			result.add(new HashMap<String, Object>() {{
				put("pn_Name", (String) manpower[1] + " " + (String) manpower[2]);
				put("personId", manpower[0]);
				put("designation", manpower[10]);
				put("department", manpower[11]);
			}});
		}
		return result;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/asset/getstaff1", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getstaff1(HttpServletRequest request) {
		logger.info("method : getAllPersonNames");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Object[] manpower : personService.findAllStaff1()) {
			result.add(new HashMap<String, Object>() {{
				put("pn_Name", (String) manpower[1] + " " + (String) manpower[2]);
				put("personId", manpower[0]);
				put("designation", manpower[10]);
				put("department", manpower[11]);
			}});
		}
		return result;
	}



	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/asset/getDate/{manuFacDate}")
	public void getDate(@PathVariable("manuFacDate") String manuFacDate,HttpServletResponse response) throws ParseException {

		logger.info("manuFacDate   "+ manuFacDate);
		//Date d = dateTimeCalender.getDateToStore(manuFacDate);
		PrintWriter out;
		String dateStr ="";
		try{
			java.util.Date date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH).parse(manuFacDate);
			dateStr = new SimpleDateFormat("dd/MM/YYYY").format(date);
			logger.info("dateStr    "+ dateStr);
			out = response.getWriter();
			out.write(dateStr);

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	@RequestMapping(value = "/costcenter", method = RequestMethod.GET)
	public String indexCostCenter(ModelMap model, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		 model.addAttribute("ViewName", "Asset");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Asset", 1, request);
		breadCrumbService.addNode("Cost Center", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		return "asset/costcenter";
	}

	@RequestMapping(value = "/asset/costcenter/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readCostCenter() {
		return costCenterService.setResponse();
	}

	@RequestMapping(value = "/asset/costcenter/create", method = RequestMethod.GET)
	public @ResponseBody
	List<?> createCostCenter(@ModelAttribute("costcenter")CostCenter costCenter, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, HttpServletRequest req) throws ParseException{
		costCenterService.save(costCenter);
		return costCenterService.setResponse();
	}

	@RequestMapping(value = "/asset/costcenter/update", method = RequestMethod.GET)
	public @ResponseBody
	List<?> updateCostCenter(@ModelAttribute("costcenter")CostCenter costCenter, BindingResult result,
			ModelMap model, SessionStatus sessionStatus, final Locale locale, HttpServletRequest req) throws ParseException{
		costCenterService.update(costCenter);
		return costCenterService.setResponse();
	}
	@RequestMapping(value = "/asset/costcenter/delete", method = RequestMethod.GET)
	public @ResponseBody
	CostCenter deleteCostCenter(@ModelAttribute("costcenter") CostCenter costCenter,BindingResult result) {
		costCenterService.delete(costCenter.getCcId());
		return costCenter;
	}

	@RequestMapping(value = "/asset/costcenter/filter/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> costCenterMaster(@PathVariable String feild) {
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("CostCenter",attributeList, null));
		
		return set;
	}

	@RequestMapping(value = "/asset/costcenter", method = RequestMethod.GET)
	public @ResponseBody
	List<CostCenter> readCostCenterList() {
		return costCenterService.executeSimpleQuery("select o from CostCenter o");
	}
	
	@RequestMapping(value = "/assetInvoiceTemplate/assetInvoiceTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportAssetFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> assetTemplateEntities =assetService.findAllAsset();
	
               
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
    	
    	header.createCell(0).setCellValue("Asset Name");
        header.createCell(1).setCellValue("Asset Type");
        header.createCell(2).setCellValue("Department Name");
        header.createCell(3).setCellValue("Purchase Date");
        header.createCell(4).setCellValue("Cost Center");
        header.createCell(5).setCellValue("PO Number");        
        header.createCell(6).setCellValue("Ownership");
        header.createCell(7).setCellValue("Supplier");
        header.createCell(8).setCellValue("Vendor Name");
        header.createCell(9).setCellValue("Condition");        
        header.createCell(10).setCellValue("Manufacturers");
        header.createCell(11).setCellValue("Manuf. Year");
        header.createCell(12).setCellValue("Valid Till");
        header.createCell(13).setCellValue("Model Number");        
        header.createCell(14).setCellValue("Manuf. SI No");
        header.createCell(15).setCellValue("Status");
    
        for(int j = 0; j<=15; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:P200"));
        }
        
        int count = 1;
    
    	for (Object[] values :assetTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    	

    		row.createCell(0).setCellValue((String)values[1]);
    		
    		row.createCell(1).setCellValue((String)values[6]);
    		
    	    row.createCell(2).setCellValue((String)values[18]);
    	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	    String str="";
    	    if((Date)values[31]!=null){
				java.util.Date purchaseDateUtil = (Date)values[31];
				java.sql.Date purchaseDateSql = new java.sql.Date(purchaseDateUtil.getTime());
				str= sdf.format(purchaseDateSql);
			}else{
				str="";
			}
			
    		
    		row.createCell(3).setCellValue(str);
    		row.createCell(4).setCellValue((String)values[15]);
    		row.createCell(5).setCellValue((String)values[3]);
    		row.createCell(6).setCellValue((String)values[7]);
    		row.createCell(7).setCellValue((String)values[34]);
    		String str1="";
    		if((String)values[27]!=null){
    			str=(String)values[27];
    		}
    		else{
    			str="";
    		}
    		if((String)values[28]!=null){
    			str1=(String)values[28];
    		}
    		else{
    			str1="";
    		}
    		row.createCell(8).setCellValue(str+" "+str1);
    		row.createCell(9).setCellValue((String)values[11]);
    		row.createCell(10).setCellValue((String)values[14]);
    		if((Date)values[19]!=null){
    			java.util.Date assetYearUtil =  (Date)values[19];
    			java.sql.Date assetYearSql = new java.sql.Date(assetYearUtil.getTime());
    			str=sdf.format(assetYearSql);	
    		}else{
    			str="";
    		}
    		
    		row.createCell(11).setCellValue(str);
    		
    		if((Date)values[32]!=null){
				java.util.Date warrantyTillUtil = (Date)values[32];
				java.sql.Date warrantyTillSql = new java.sql.Date(warrantyTillUtil.getTime());
				str= sdf.format(warrantyTillSql);
			}else{
				str="";
			}
    		
    		row.createCell(12).setCellValue(str);
    		row.createCell(13).setCellValue((String)values[21]);
    		row.createCell(14).setCellValue((String)values[22]);
    		row.createCell(15).setCellValue((String)values[26]);
    		
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"AssetMasterTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/assetOwnerInvoiceTemplate/assetOwnerInvoiceTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportAssetOwnerFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> assetOwnerTemplateEntities =assetOwnerShipService.findAllOwnership();
	
               
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
    	
    	
    	header.createCell(0).setCellValue("Asset Name");
        header.createCell(1).setCellValue("OwnerShip Name");
        header.createCell(2).setCellValue("Maintainance Owner Name");
        header.createCell(3).setCellValue("OwnerShip Start Date");
        header.createCell(4).setCellValue("OwnerShip End Date");
       
        for(int j = 0; j<=4; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:E200"));
        }
        
        int count = 1;
    
    	for (Object[] values :assetOwnerTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		
    		row.createCell(0).setCellValue((String)values[2]);
    		String str="";
    		String str1="";
    		if((String)values[3]!=null){
    			str=(String)values[3];
    		}
    		else{
    			str="";
    		}
    		if((String)values[4]!=null){
    			str1=(String)values[4];
    		}
    		else{
    			str1="";
    		}
    		
    		row.createCell(1).setCellValue(str+" "+str1);
    		if((String)values[7]!=null){
    			str=(String)values[7];
    		}
    		else{
    			str="";
    		}
    		if((String)values[8]!=null){
    			str1=(String)values[8];
    		}
    		else{
    			str1="";
    		}
    		
    	    row.createCell(2).setCellValue(str+" "+str1);
    	    
    	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	    
            if((Date)values[9]!=null){
    			java.util.Date aoStartDateUtil = (Date)values[9];
    			java.sql.Date aoStartDateSql = new java.sql.Date(aoStartDateUtil.getTime());
    			str=sdf.format(aoStartDateSql);
    		}else{
    			logger.info("Ownership date not exists");
    			str="";
    		}
    	    
			
    		row.createCell(3).setCellValue(str);
    		if((Date)values[10]!=null){
    			java.util.Date aoEndDateUtil = (Date)values[10];
    			java.sql.Date aoEndDateSql = new java.sql.Date(aoEndDateUtil.getTime());
    			str=sdf.format(aoEndDateSql);
    		}else{
    			str="";
    		}
    		row.createCell(4).setCellValue(str);
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"AssetOwnershipTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	@RequestMapping(value = "/assetServiceTemplate/assetServiceTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportAssetServiceFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> assetServiceTemplateEntities = assetServiceHistoryService.findAll();
	
               
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
    	
    	
    	header.createCell(0).setCellValue("Asset Name");
        header.createCell(1).setCellValue("Service Date");
        header.createCell(2).setCellValue("Problem Description");
        header.createCell(3).setCellValue("Service Description");
        header.createCell(4).setCellValue("Service Type");
        header.createCell(5).setCellValue("Serviced Under");
        header.createCell(6).setCellValue("Cost Incurred");
        header.createCell(7).setCellValue("Serviced By");
       
       
        for(int j = 0; j<=7; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
        }
        
        int count = 1;
    
    	for (Object[] values :assetServiceTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    		
    		row.createCell(0).setCellValue((String)values[2]);
    		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
     	    String str="";
    		if((Date)values[3]!=null){
				java.util.Date apsmDateUtil = (Date)values[3];
				java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
				str=sdf.format(apsmDateSql);
				}else{
					str= "";
				}
    		
    		row.createCell(1).setCellValue(str);
    		
    		row.createCell(2).setCellValue((String)values[4]);
    		
    		row.createCell(3).setCellValue((String)values[5]);
            row.createCell(4).setCellValue((String)values[6]);
    		
    		row.createCell(5).setCellValue((String)values[7]);
            row.createCell(6).setCellValue((Integer)values[15]);
            String str1="";
    		if((String)values[9]!=null){
    			str=(String)values[9];
    		}
    		else{
    			str="";
    		}
    		if((String)values[10]!=null){
    			str1=(String)values[10];
    		}
    		else{
    			str1="";
    		}
    		
    		row.createCell(7).setCellValue(str+" "+str1);
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"AssetServiceHistoryTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/assetInvoicePdfTemplate/assetPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfTenant(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> assetTemplateEntities =assetService.findAllAsset();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(16);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 7);
        
       
       
        table.addCell(new Paragraph("Asset Name",font1));
        table.addCell(new Paragraph("Asset Type",font1));
        table.addCell(new Paragraph("Department Name",font1));
        table.addCell(new Paragraph("Purchase Date",font1));
        table.addCell(new Paragraph("Cost Center",font1));
        table.addCell(new Paragraph("PO Number",font1));
        table.addCell(new Paragraph("Ownership",font1));
        table.addCell(new Paragraph("Supplier",font1));
        table.addCell(new Paragraph("Vendor Name",font1));
        table.addCell(new Paragraph("Condition",font1));
        table.addCell(new Paragraph("Manufacturers",font1));
        table.addCell(new Paragraph("Manuf. Year",font1));
        table.addCell(new Paragraph("Valid Till",font1));
        table.addCell(new Paragraph("Model Number",font1));
        table.addCell(new Paragraph("Manuf. SI No",font1));
        table.addCell(new Paragraph("Status",font1));
        
       
      
    	for (Object[] values :assetTemplateEntities) {
    		
    		
    		 PdfPCell cell1 = new PdfPCell(new Paragraph((String)values[1],font3));
             PdfPCell cell2 = new PdfPCell(new Paragraph((String)values[6],font3));
        
   
        PdfPCell cell3 = new PdfPCell(new Paragraph((String)values[18],font3));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    String str="";
	    if((Date)values[31]!=null){
			java.util.Date purchaseDateUtil = (Date)values[31];
			java.sql.Date purchaseDateSql = new java.sql.Date(purchaseDateUtil.getTime());
			str= sdf.format(purchaseDateSql);
		}else{
			str="";
		}
    
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
       
        PdfPCell cell5 = new PdfPCell(new Paragraph((String)values[15],font3));
   
        PdfPCell cell6 = new PdfPCell(new Paragraph((String)values[3],font3));
      
        PdfPCell cell7 = new PdfPCell(new Paragraph((String)values[7],font3));
        
        PdfPCell cell8 = new PdfPCell(new Paragraph((String)values[34],font3));
        String str1="";
		if((String)values[27]!=null){
			str=(String)values[27];
		}
		else{
			str="";
		}
		if((String)values[28]!=null){
			str1=(String)values[28];
		}
		else{
			str1="";
		}
        
        PdfPCell cell9 = new PdfPCell(new Paragraph(str+" "+str1,font3));
       
        PdfPCell cell10 = new PdfPCell(new Paragraph((String)values[11],font3));
        
        PdfPCell cell11 = new PdfPCell(new Paragraph((String)values[14],font3));
        if((Date)values[19]!=null){
			java.util.Date assetYearUtil =  (Date)values[19];
			java.sql.Date assetYearSql = new java.sql.Date(assetYearUtil.getTime());
			str=sdf.format(assetYearSql);	
		}else{
			str="";
		}
        
        PdfPCell cell12 = new PdfPCell(new Paragraph(str,font3));
        if((Date)values[32]!=null){
			java.util.Date warrantyTillUtil = (Date)values[32];
			java.sql.Date warrantyTillSql = new java.sql.Date(warrantyTillUtil.getTime());
			str= sdf.format(warrantyTillSql);
		}else{
			str="";
		}
		
        
        PdfPCell cell13 = new PdfPCell(new Paragraph(str,font3));
        PdfPCell cell14 = new PdfPCell(new Paragraph((String)values[21],font3));
        
        PdfPCell cell15 = new PdfPCell(new Paragraph((String)values[22],font3));
        
        PdfPCell cell16 = new PdfPCell(new Paragraph((String)values[26],font3));
       
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        table.addCell(cell13);
        table.addCell(cell14);
        table.addCell(cell15);
        table.addCell(cell16);
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f , 10f, 10f, 10f, 10f, 10f, 10f, 10f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"AssetMasterTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/assetOwnerInvoicePdfTemplate/assetOwnerPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportAssetOwnerPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> assetOwnerTemplateEntities =assetOwnerShipService.findAllOwnership();
		
        
	      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(5);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
       
       
        table.addCell(new Paragraph("Asset Name",font1));
        table.addCell(new Paragraph("OwnerShip Name",font1));
        table.addCell(new Paragraph("Maintainance Owner Name",font1));
        table.addCell(new Paragraph("OwnerShip Start Date",font1));
        table.addCell(new Paragraph("OwnerShip End Date",font1));
        
       
      
    	for (Object[] values :assetOwnerTemplateEntities) {
    		
    		String str="";
            String str1="";
    		 PdfPCell cell1 = new PdfPCell(new Paragraph((String)values[2],font3));
    		 if((String)values[3]!=null){
      			str=(String)values[3];
      		}
      		else{
      			str="";
      		}
      		if((String)values[4]!=null){
      			str1=(String)values[4];
      		}
      		else{
      			str1="";
      		}
             PdfPCell cell2 = new PdfPCell(new Paragraph(str+" "+str1,font3));
             
     		if((String)values[7]!=null){
     			str=(String)values[7];
     		}
     		else{
     			str="";
     		}
     		if((String)values[8]!=null){
     			str1=(String)values[8];
     		}
     		else{
     			str1="";
     		}
        PdfPCell cell3 = new PdfPCell(new Paragraph(str+" "+str1,font3));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 
        if((Date)values[9]!=null){
			java.util.Date aoStartDateUtil = (Date)values[9];
			java.sql.Date aoStartDateSql = new java.sql.Date(aoStartDateUtil.getTime());
			str=sdf.format(aoStartDateSql);
		}else{
			logger.info("Ownership date not exists");
			str="";
		}
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
		
		if((Date)values[10]!=null){
			java.util.Date aoEndDateUtil = (Date)values[10];
			java.sql.Date aoEndDateSql = new java.sql.Date(aoEndDateUtil.getTime());
			str=sdf.format(aoEndDateSql);
		}else{
			str="";
		}
		
		PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
	   

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 10f, 15f, 15f, 10f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"AssetOwnershipTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/assetServicePdfTemplate/assetServicePdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportAssetServicePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> assetServiceTemplateEntities = assetServiceHistoryService.findAll();
		
        
	      
		 Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        document.open();
	        
	       
	        
	        PdfPTable table = new PdfPTable(8);
	        
	        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
	        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
	        
	        
	       
	       
	        table.addCell(new Paragraph("Asset Name",font1));
	        table.addCell(new Paragraph("Service Date",font1));
	        table.addCell(new Paragraph("Problem Description",font1));
	        table.addCell(new Paragraph("Service Description",font1));
	        table.addCell(new Paragraph("Service Type",font1));
	        table.addCell(new Paragraph("Serviced Under",font1));
	        table.addCell(new Paragraph("Cost Incurred",font1));
	        table.addCell(new Paragraph("Serviced By",font1));
	        
	       
	      
	    	for (Object[] values :assetServiceTemplateEntities) {
	    	
	    		 PdfPCell cell1 = new PdfPCell(new Paragraph((String)values[2],font3));
	    		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	      	    String str="";
	     		if((Date)values[3]!=null){
	 				java.util.Date apsmDateUtil = (Date)values[3];
	 				java.sql.Date apsmDateSql = new java.sql.Date(apsmDateUtil.getTime());
	 				str=sdf.format(apsmDateSql);
	 				}else{
	 					str= "";
	 				}
	             PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
	        
	   
	             PdfPCell cell3 = new PdfPCell(new Paragraph((String)values[4],font3));
	       
	             PdfPCell cell4 = new PdfPCell(new Paragraph((String)values[5],font3));
			
			
			     PdfPCell cell5 = new PdfPCell(new Paragraph((String)values[6],font3));
			     PdfPCell cell6 = new PdfPCell(new Paragraph((String)values[7],font3));
					
					
			     PdfPCell cell7 = new PdfPCell(new Paragraph(Integer.toString((Integer)values[15]),font3));
			  
	             String str1="";
	     		if((String)values[9]!=null){
	     			str=(String)values[9];
	     		}
	     		else{
	     			str="";
	     		}
	     		if((String)values[10]!=null){
	     			str1=(String)values[10];
	     		}
	     		else{
	     			str1="";
	     		}
			     PdfPCell cell8 = new PdfPCell(new Paragraph(str+" "+str1,font3));
		   

	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        table.addCell(cell7);
	        table.addCell(cell8);
	        
	        table.setWidthPercentage(100);
	        float[] columnWidths = {10f, 10f, 15f, 15f, 10f, 10f, 10f, 10f};

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
			response.setHeader("Content-Disposition", "inline;filename=\"AssetServiceHistoryTemplates.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
	}
	
	@RequestMapping(value = "/assetdata/upload", method ={ RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object importExcelDataForAsset(@RequestParam MultipartFile files,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException, java.text.ParseException {
		
		JsonResponse errorResponse = new JsonResponse();
		logger.info("Inside Excel Upload Data");
		String fileExtention="";
		logger.info("************File Name**************"+files.getOriginalFilename());

		if(files.getOriginalFilename().lastIndexOf(".") != -1 && files.getOriginalFilename().lastIndexOf(".") != 0){

			logger.info("**********File extention********"+files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".")+1));
			fileExtention=files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".")+1);

			if(!("xls".equalsIgnoreCase(fileExtention)) && !("xlsx".equalsIgnoreCase(fileExtention))){
				throw new Error("File is not valid");
			}
		}
		
		int i=0;		
		//int j=0;
		//int k=0;
		String assetName=null;
		String assetType=null;
		String category=null;
		String perchaseDate=null;
		int ccId=0;
		String poNumber=null;
		String categoryName=null;
		String assetLocation=null;
		String assetGeoTag=null;
		String asssetCondition=null;
		String manufacturer=null;
		String manufactureryear=null;
		String assetmodelNo=null;
		String manserNo=null;
		String assetUsefulLife=null;
		String warentytill=null;
		String assetownership=null;
		String assetdesc=null;
		String assetNotes=null;
		int catId=0;
		int locationId=0;
		MaintainanceDepartment maintainanceDepartment=null;
		CostCenter costCenter=null;
		List<Asset> assetList=new ArrayList<Asset>();
		
		if("xls".equalsIgnoreCase(fileExtention)){

			//XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			try{
			HSSFWorkbook workbook = new HSSFWorkbook(files.getInputStream());
			HSSFSheet  sheet = workbook.getSheetAt(0);
			
			
			Iterator rows = sheet.rowIterator();
			
			int rowStart = Math.min(0, sheet.getFirstRowNum());
			int rowEnd = Math.max(400, sheet.getLastRowNum());
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row ro = sheet.getRow(rowNum);
				if(ro!=null)
				{
			while(rows.hasNext())
			{
				final HSSFRow  row = ((HSSFRow ) rows.next());

				logger.info("*****ROW COUNT*****"+row.getRowNum());
				Iterator cells = row.cellIterator();
				
				if(row.getRowNum()==0){
					continue; //just skip the rows if row number is 0 or 1
				}
				try{
					logger.info("result-------"+row.getCell(0).getStringCellValue());
				if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue()) || row.getCell(0).getStringCellValue() != null){
					logger.info("*****Block Name*******"+row.getCell(0).getStringCellValue());
					assetName=row.getCell(0).getStringCellValue();

				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Name is Null at row:"+(row.getRowNum()) +"& Column No 1");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				}
				catch(Exception e){
					logger.info("FAILED 1");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Name is Null at row:"+(row.getRowNum()) +"& Column No 1");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try
				{
				  if(!StringUtils.isEmpty(row.getCell(1).getStringCellValue()) && row.getCell(1).getStringCellValue() != null){
					logger.info("*****Block Name*******"+row.getCell(1).getStringCellValue());
					assetType=row.getCell(1).getStringCellValue();
					
					if(assetType.equalsIgnoreCase("movable")|| assetType.equalsIgnoreCase("immovable") )
					{
						
					}

				
				
				}
				else{
					logger.info("FAILED");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Type is movable/immovable only at row :"+(row.getRowNum()) +"& Column No 2");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				
				
				}
				}
				catch(Exception e){
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Type Null at row :"+(row.getRowNum()) +"& Column No 2");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
				if(!StringUtils.isEmpty(row.getCell(2).getStringCellValue()) && row.getCell(2).getStringCellValue() != null){
					logger.info("*****Block Name*******"+row.getCell(2).getStringCellValue());
					category=row.getCell(2).getStringCellValue();
					List<Department> list=departmentService.executeSimpleQuery("SELECT m From Department m WHERE upper(m.dept_Name)='"+""+category.toUpperCase( ).trim()+"'");
					logger.info("==========list"+list.get(0).getDept_Id());
					//Department department=list.get(0);
					List<MaintainanceDepartment> list1=maintainanceDepartmentService.executeSimpleQuery("SELECT ma From MaintainanceDepartment ma WHERE ma.department.dept_Id="+list.get(0).getDept_Id());
					System.out.println("hgyukhjgujhiuiutt"+list1.get(0).getMtDpIt());
					//department.get
					if(list.size()>0 && list1.size()>0)
					{
						
					int mdepId=list.get(0).getDept_Id();
					maintainanceDepartment=maintainanceDepartmentService.find(list1.get(0).getMtDpIt());
					
					}
					else
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Department Name Does't exist at row :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					

				}
				else
				{
					logger.info("*****getCell(2) in else*******"+row.getCell(2).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Department Name Does't exist at row :"+(row.getRowNum()) +"& Column No 2");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e)
				{
					logger.info("*****getCell(2) in else*******"+row.getCell(2).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Department Name is null at row :"+(row.getRowNum()) +"& Column No 2");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
				/*if(!StringUtils.isEmpty(row.getCell(3).getStringCellValue())&& row.getCell(3).getStringCellValue() != null){*/
					if(row.getCell(3).getDateCellValue() != null){
					logger.info("*****getCell(3)*******"+row.getCell(3).getDateCellValue());
					String date1=row.getCell(3).getDateCellValue().toString();
					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date date = (Date)formatter.parse(date1);
					System.out.println(date);
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					perchaseDate = format.format(date);
					//System.out.println(result);
					//Date date=row.getCell(3).getDateCellValue();
					logger.info("========"+perchaseDate);
					// perchaseDate=row.getCell(3).getStringCellValue();
					 String[] arr=perchaseDate.split("/");
						String month=arr[0];
						String day=arr[1];
						String year=arr[2];
						int dayInt=0;
						int monthint=0;
						int yearint=0;
						try
						{
						 dayInt=Integer.parseInt(day);
						 monthint=Integer.parseInt(month);
						 yearint=Integer.parseInt(year);
						 
						 if(monthint==02 && dayInt>29)
						 {
							 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Perchase Date is in invalid format(Feb month date can't be)>29 at row:"+(row.getRowNum()) +"& Column No 3");
									}
								};
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
						 }
						}
						catch(Exception e)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Perchase Date is in invalid format at row:"+(row.getRowNum()) +"& Column No 3");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						logger.info("dayInt"+dayInt+"monthint"+monthint+"yearint"+yearint);
						logger.info("ay.length()"+day.length()+"month.length()"+month.length()+"year.length()"+year.length());
						/*if(i==0){
							break;
						}*/
						if(day.length()!=2 || month.length()!=2 || year.length()!=4)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Perchase Date is in invalid format at row:"+(row.getRowNum()) +"& Column No 3");
								}
							};	
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						
						if(dayInt>31 || monthint>12){
							
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Perchase Date is in invalid format at row:"+(row.getRowNum()) +"& Column No 3");
								}
							};	
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
				
				
				}
				
				else
				{
					logger.info("*****getCell(3)*******"+row.getCell(3).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Perchase Date is Null at row:"+(row.getRowNum()) +"& Column No 3");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e){
					logger.info("*****getCell(3)*******"+row.getCell(3).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Perchase Date is not in valid format at row:"+(row.getRowNum()) +"& Column No 3");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
				if(!StringUtils.isEmpty(row.getCell(4).getStringCellValue())&& row.getCell(4).getStringCellValue() != null){
					logger.info("*****getCell(4)*******"+row.getCell(4).getStringCellValue());
					String costcenter=row.getCell(4).getStringCellValue();
					List<CostCenter> list=costCenterService.executeSimpleQuery("SELECT C FROM CostCenter c WHERE c.name='"+""+costcenter.trim()+"'");
                    
                    
                    if(list.size()>0)
                    {
                    	ccId=list.get(0).getCcId();
                    	logger.info("cost center id=================="+ccId);
                    	costCenter=costCenterService.find(ccId);
                    	logger.info("cost center object"+costCenter+""+costCenter.getCcId());
                    }
                    
                    else
                    {
                    	logger.info("*****getCell(4) else*******"+row.getCell(4).getStringCellValue());
                    	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
    						{
    							put("cannotImport", "Cost Center does't exist at row :"+(row.getRowNum()) +"& Column No 4");
    						}
    					};
    					errorResponse.setStatus("cannotImport");
    					errorResponse.setResult(errorMapResponse);
    					i=1;
    					break;
                    }
				
				
				}
				
			       else
                   {
                   	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
   						{
   							put("cannotImport", "Cost Center does't exist at row :"+(row.getRowNum()) +"& Column No 4");
   						}
   					};
   					errorResponse.setStatus("cannotImport");
   					errorResponse.setResult(errorMapResponse);
   					i=1;
   					break;
                   }
				}
				catch(Exception e)
				{
				 	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
   						{
   							put("cannotImport", "Cost Center is null at row :"+(row.getRowNum()) +"& Column No 4");
   						}
   					};
   					errorResponse.setStatus("cannotImport");
   					errorResponse.setResult(errorMapResponse);
   					i=1;
   					break;
				}
				try{
				//logger.info("cell type========"+row.getCell(5).getCellType());
				if(row.getCell(5).getCellType()==1)
				{
				if(!StringUtils.isEmpty(row.getCell(5).getStringCellValue()) && row.getCell(5).getStringCellValue() != null){
					/*if(row.getCell(5).getNumericCellValue()>=0){*/
					logger.info("*****getCell(5)*******"+row.getCell(5).getStringCellValue());
					poNumber=row.getCell(5).getStringCellValue();
					logger.info("==================po number===="+poNumber);
					if(poNumber.length()<3)
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "PO Number length should be at least 3 :"+(row.getRowNum()) +"& Column No 5");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
				  }
				else
				{
					logger.info("*****getCell(5) else*******"+row.getCell(5).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "PO Number is Null at row :"+(row.getRowNum()) +"& Column No 5");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				
				}
				if(row.getCell(5).getCellType()==0)
				{
					int poNo=(int) row.getCell(5).getNumericCellValue();
					poNumber=Integer.toString(poNo);
					logger.info("==================po number===="+poNumber);
					if(poNumber.length()<3)
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "PO Number length should be at least 3 :"+(row.getRowNum()) +"& Column No 5");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					
					
				}
				
			
				}
			
				catch(Exception e){
					poNumber="Not/Applicable";
					/*e.printStackTrace();
					logger.info("*****getCell(5) else*******"+e);*/
					/*HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "PO Number is Null at row :"+(row.getRowNum()) +"& Column No 5");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
				if(!StringUtils.isEmpty(row.getCell(6).getStringCellValue()) && row.getCell(6).getStringCellValue() != null){
					logger.info("*****getCell(6)*******"+row.getCell(6).getStringCellValue());
					 categoryName=row.getCell(6).getStringCellValue();
					List<AssetCategoryTree> list=assetCategoryService.executeSimpleQuery("SELECT ac FROM AssetCategoryTree ac WHERE ac.assetcatText='"+""+categoryName.trim()+"'");
					if(list.size()>0)
					{
						 catId=list.get(0).getAssetcatId();
					}
					
					else
					{
						logger.info("*****getCell(6) else*******"+row.getCell(6).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Category does't exist at row:"+(row.getRowNum()) +"& Column No 6");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					
				  }
				
				else
				{
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Categoryr is Null at row:"+(row.getRowNum()) +"& Column No 6");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e){
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Categoryr is Null at row:"+(row.getRowNum()) +"& Column No 6");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				try{
				if(!StringUtils.isEmpty(row.getCell(7).getStringCellValue()) && row.getCell(7).getStringCellValue() != null){
					logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
					assetLocation=row.getCell(7).getStringCellValue();
					List<AssetLocationTree> list=assetLocationTreeService.executeSimpleQuery("SELECT al FROM AssetLocationTree al WHERE al.assetlocText='"+""+assetLocation.trim()+"'");
					if(list.size()>0)
					{
						locationId=list.get(0).getAssetlocId();
					}
				
					
					else
					{
						logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Location does't exist at row:"+(row.getRowNum()) +"& Column No 7");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
				
				  }
				
				else
				{
					logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Location is Null at row :"+(row.getRowNum()) +"& Column No 7");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e)
				{
					logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Location is Null at row :"+(row.getRowNum()) +"& Column No 7");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
					if(row.getCell(8).getCellType()==1)
					{
				if(!StringUtils.isEmpty(row.getCell(8).getStringCellValue()) && row.getCell(8).getStringCellValue() != null){
					logger.info("*****getCell(8)*******"+row.getCell(8).getStringCellValue());
				   assetGeoTag=row.getCell(8).getStringCellValue();
				
				
				}
					}
				
				else if(row.getCell(8).getCellType()==0)
				{
					
					 assetGeoTag=Double.toString(row.getCell(8).getNumericCellValue());
					
					/*logger.info("*****getCell(8) else*******"+row.getCell(8).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Geo Tag is Null at row:"+(row.getRowNum()) +"& Column No 8");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
					
				}
				}
				catch(Exception e)
				{
					logger.info("*****getCell(8) else*******"+row.getCell(8).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Geo Tag is Null at row:"+(row.getRowNum()) +"& Column No 8");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
				if(!StringUtils.isEmpty(row.getCell(9).getStringCellValue())){
					logger.info("*****getCell(9)*******"+row.getCell(9).getStringCellValue());
					asssetCondition=row.getCell(9).getStringCellValue();
				
				
				}
				else
				{
					logger.info("*****getCell(9) else*******"+row.getCell(9).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 9");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e)
				{
					logger.info("*****getCell(9) else*******"+row.getCell(9).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 9");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;	
				}
				try{
					if(row.getCell(10).getCellType()==1)
					{
				if(!StringUtils.isEmpty(row.getCell(10).getStringCellValue()) && row.getCell(10).getStringCellValue() != null){
					logger.info("*****getCell(10)*******"+row.getCell(10).getStringCellValue());
					manufacturer=row.getCell(10).getStringCellValue();
				
				
				}
					}
				
				else if(row.getCell(10).getCellType()==0)
				{
				
					manufacturer=Double.toString(row.getCell(10).getNumericCellValue());
					
					/*	logger.info("*****getCell(10) else*******"+row.getCell(10).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 10");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
					
				}
				}
				catch(Exception e)
				{
					
					manufacturer="N/A";
					//logger.info("*****getCell(10) else*******"+row.getCell(10).getStringCellValue());
					/*HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 10");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
				if(row.getCell(11).getDateCellValue() != null){
					logger.info("*****getCell(11)*******"+row.getCell(11).getDateCellValue());
					String date1=row.getCell(11).getDateCellValue().toString();
					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date date = (Date)formatter.parse(date1);
					System.out.println(date);
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					manufactureryear = format.format(date);
					logger.info("manufactureryear=="+manufactureryear);
					//manufactureryear=row.getCell(11).getStringCellValue();
					 String[] arr=manufactureryear.split("/");
						String day=arr[1];
						String month=arr[0];
						String year=arr[2];
						int dayInt=0;
						int monthint=0;
						int yearint=0;
						try
						{
						 dayInt=Integer.parseInt(day);
						 monthint=Integer.parseInt(month);
						 yearint=Integer.parseInt(year);
						 if(monthint==02 && dayInt>29)
						 {
							 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "mnf Date is in invalid format(Feb month date can't be)>29 at row:"+(row.getRowNum()) +"& Column No 3");
									}
								};
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
						 }
						}
						catch(Exception e)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Mnf Year is in invalid format at row:"+(row.getRowNum()) +"& Column No 11");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						if(day.length()!=2 || month.length()!=2 || year.length()!=4)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Mnf Year is in invalid format at row:"+(row.getRowNum()) +"& Column No 11");
								}
							};	
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						
						if(dayInt>31 || monthint>12){
							
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Mnf Year is in invalid format at row:"+(row.getRowNum()) +"& Column No 11");
								}
							};	
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
				
				
				}
				
				else
				{
					logger.info("*****getCell(11)*******"+row.getCell(11).getDateCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Manufacturer Year is Null at row:"+(row.getRowNum()) +"& Column No 11");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e){
					logger.info("***** Catch block getCell(11)*******");
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Manufacturer Year is Not in valid format at row:"+(row.getRowNum()) +"& Column No 11");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
					logger.info("row.getCell(12).getCellType()"+row.getCell(12).getCellType());
					if(row.getCell(12).getCellType()==1)
					{
				if(!StringUtils.isEmpty(row.getCell(12).getStringCellValue()) && row.getCell(12).getStringCellValue() != null){
					logger.info("*****getCell(12)*******"+row.getCell(12).getStringCellValue());
					assetmodelNo=row.getCell(12).getStringCellValue();
					if(assetmodelNo.length()<3)
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Model number length should be at least 3:"+(row.getRowNum()) +"& Column No 12");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
				
				
				}
					}
				
				else if(row.getCell(12).getCellType()==0)
				{
					logger.info("*****getCell(12)*******"+row.getCell(12).getNumericCellValue());
					assetmodelNo=Double.toString(row.getCell(12).getNumericCellValue());
					if(assetmodelNo.length()<3)
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Model number length should be at least 3:"+(row.getRowNum()) +"& Column No 12");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					/*logger.info("*****getCell(12)*******"+row.getCell(12).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Model number is Null at row:"+(row.getRowNum()) +"& Column No 12");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
					
				}
				else if(row.getCell(12).getCellType()==3)
				{
					assetmodelNo="Not/Applicable";
				}
				}
				catch(Exception e){
					
					logger.info("*****getCell(12)******* catch block");
					assetmodelNo="Not/Applicable";
					/*logger.info("*****getCell(12)*******"+row.getCell(12).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Model number is Null at row:"+(row.getRowNum()) +"& Column No 12");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
					if(row.getCell(13).getCellType()==1)
					{
				if(!StringUtils.isEmpty(row.getCell(13).getStringCellValue()) && row.getCell(13).getStringCellValue() != null){
					logger.info("*****getCell(13)*******"+row.getCell(13).getStringCellValue());
					manserNo=row.getCell(13).getStringCellValue();
				
				
				}
					}
					else if(row.getCell(13).getCellType()==0)
					{
						manserNo=Double.toString(row.getCell(13).getNumericCellValue());
					}
				
			/*	else
				{
					logger.info("*****getCell(13) else*******"+row.getCell(13).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Mnf serial number number is Null at row:"+(row.getRowNum()) +"& Column No 13");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}*/
				}
				catch(Exception e){
				
					manserNo="Not/Applicable";
					/*	logger.info("*****getCell(13) else*******"+row.getCell(13).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Mnf serial number number is Null at row:"+(row.getRowNum()) +"& Column No 13");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
					if(row.getCell(14).getCellType()==1){
				if(!StringUtils.isEmpty(row.getCell(14).getStringCellValue()) && row.getCell(14).getStringCellValue() != null){
					
					assetUsefulLife=row.getCell(14).getStringCellValue();
					}
				}
				else if(row.getCell(14).getCellType()==0)
				{
					int usefullife=(int) row.getCell(14).getNumericCellValue();
					logger.info("==============asset useful life===="+usefullife);
					
					if(usefullife>0)
					
					assetUsefulLife=Integer.toString(usefullife);
				
				
				  
					
					/*logger.info("*****getCell(14) else*******"+row.getCell(14).getNumericCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Useful Life is not valid  at row:"+(row.getRowNum()) +"& Column No 14");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
					
				}
				}
				catch(Exception e){
					
					assetUsefulLife="Not/Applicable";
					
					/*logger.info("*****getCell(14) else*******"+row.getCell(14).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Useful Life is not in valid format at row:"+(row.getRowNum()) +"& Column No 14");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
					if(row.getCell(15).getCellType()==1)
					{
				if(!StringUtils.isEmpty(row.getCell(15).getStringCellValue()) && row.getCell(15).getStringCellValue() != null){
					logger.info("************"+row.getCell(15).getStringCellValue());
					assetdesc=row.getCell(15).getStringCellValue();
				
				
				}
					}
					else if(row.getCell(15).getCellType()==0){
						assetdesc=Double.toString(row.getCell(15).getNumericCellValue());
						
					}
				}
				catch(Exception e){
				
					
					assetdesc="Not/Applicable";
					/*	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset desc Life is is Null at row:"+(row.getRowNum()) +"& Column No 15");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
					if(row.getCell(16).getCellType()==1)
					{
				if(!StringUtils.isEmpty(row.getCell(16).getStringCellValue()) && row.getCell(16).getStringCellValue() != null){
					logger.info("************"+row.getCell(16).getStringCellValue());
					assetNotes=row.getCell(16).getStringCellValue();
				
				
				}
					}
					else if(row.getCell(16).getCellType()==0)
					{
						assetNotes=Double.toString(row.getCell(16).getNumericCellValue());	
					}
				}
				catch(Exception e){
					
					assetNotes="Not/Applicable";
					/*HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Notes is is Null at row:"+(row.getRowNum()) +"& Column No 16");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;*/
				}
				try{
				if(row.getCell(17).getDateCellValue()!= null){
					logger.info("************"+row.getCell(17).getDateCellValue());
					String date1=row.getCell(17).getDateCellValue().toString();
					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date date = (Date)formatter.parse(date1);
					System.out.println(date);
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					warentytill = format.format(date);
					logger.info("warentytill======="+warentytill);
					//warentytill=row.getCell(17).getStringCellValue();
					 String[] arr=warentytill.split("/");
						String day=arr[1];
						String month=arr[0];
						String year=arr[2];
						int dayInt=0;
						int monthint=0;
						int yearint=0;
						try
						{
						 dayInt=Integer.parseInt(day);
						 monthint=Integer.parseInt(month);
						 yearint=Integer.parseInt(year);
						 if(monthint==02 && dayInt>29)
						 {
							 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Warenty Date is in invalid format(Feb month date can't be)>29 at row:"+(row.getRowNum()) +"& Column No 3");
									}
								};
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
						 }
						}
						catch(Exception e)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Warenty date is in invalid format at row:"+(row.getRowNum()) +"& Column No 17");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						if(day.length()!=2 || month.length()!=2 || year.length()!=4)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Warenty date is in invalid format at row:"+(row.getRowNum()) +"& Column No 17");
								}
							};	
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						
						if(dayInt>31 || monthint>12){
							
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "warenty date is in invalid format at row:"+(row.getRowNum()) +"& Column No 17");
								}
							};	
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
				
				
				}
				else
				{
					logger.info("*****getCell(17) else*******"+row.getCell(17).getDateCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Warenty Date is is Null at row:"+(row.getRowNum()) +"& Column No 17");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e){
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Warenty Date is not an valid format at row:"+(row.getRowNum()) +"& Column No 17");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				try{
				if(!StringUtils.isEmpty(row.getCell(18).getStringCellValue()) && row.getCell(18).getStringCellValue() != null){
					logger.info("************"+row.getCell(18).getStringCellValue());
					assetownership=row.getCell(18).getStringCellValue();
				
				
				}
				else
				{
					logger.info("*****getCell(18) else*******"+row.getCell(18).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Ownership is is Null at row:"+(row.getRowNum()) +"& Column No 18");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				}
				catch(Exception e){
					//logger.info("*****getCell(18) else*******"+row.getCell(18).getStringCellValue());
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Asset Ownership is is Null at row:"+(row.getRowNum()) +"& Column No 18");
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
				}
				
				
			Asset asset=new Asset();
			DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
			logger.info("assetyear before parse"+manufactureryear);
			Date assetyear = df.parse(manufactureryear);
			logger.info("assetyear after parse"+assetyear);
			asset.setAssetYear(assetyear);
			logger.info("purchaseDate before parse"+perchaseDate);
			Date purchaseDate=df.parse(perchaseDate);
			logger.info("purchaseDate after parse"+purchaseDate);
			asset.setPurchaseDate(purchaseDate);
			Date warenty=df.parse(warentytill);
			logger.info("warenty before parse"+warentytill);
			asset.setWarrantyTill(warenty);
			logger.info("warenty after parse"+warenty);
			
			int com=assetyear.compareTo(warenty);
			int comp1=assetyear.compareTo(purchaseDate);
			int comp2=warenty.compareTo(purchaseDate);
			logger.info("======="+com+""+comp1+""+comp2);
			
			logger.info("warenty date"+warenty);
			logger.info("purchaseDate=="+purchaseDate);
			if(warenty.before(purchaseDate)){
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "warenty date should be greater than purchase date at row  "+row.getRowNum());
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
				i=1;
				break;
				
			}
			if(purchaseDate.before(assetyear)){
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("cannotImport", "Mnf date should be greater than purchase date at row  "+row.getRowNum());
					}
				};
				errorResponse.setStatus("cannotImport");
				errorResponse.setResult(errorMapResponse);
				i=1;
				break;
				
			}
			
			asset.setMaintainanceDepartment(maintainanceDepartment);
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
			Calendar cal = Calendar.getInstance(tz);		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			sdf.setCalendar(cal);		
			cal.setTime(asset.getAssetYear());
			cal.add(Calendar.YEAR, Integer.parseInt(assetUsefulLife));		
			Date expiryYear = cal.getTime();		
			java.sql.Date sqlExpiryDate=new java.sql.Date(expiryYear.getTime());
            asset.setAssetLifeExpiry(sqlExpiryDate);

            if(StringUtils.equalsIgnoreCase(assetownership.trim(),"Leased")){

				asset.setSupplier("Leased");
				
			}else{
				asset.setAssetVendorId(1);
			}
            asset.setAssetDesc(assetdesc);
			asset.setAssetNotes(assetNotes);
			asset.setCcId(ccId);
			//asset.setCostCenter(costCenter);
			asset.setAssetCatId(catId);
			asset.setAssetLocId(locationId);
			asset.setOwnerShip(assetownership);
			asset.setAssetName(assetName);
			asset.setAssetStatus("in use");
			asset.setAssetType(assetType);
			asset.setAssetCondition(asssetCondition);
			asset.setAssetManufacturer(manufacturer);
			asset.setAssetPoDetail(poNumber);
			asset.setAssetModelNo(assetmodelNo);
			asset.setAssetManufacturerSlNo(manserNo);
			asset.setUseFullLife(assetUsefulLife);
			asset.setAssetNotes(assetNotes);
			logger.info("going to generate asset tag");
			//logger.info(categoryName.length()+""+assetLocation.length()+""+assetmodelNo.length()+""+poNumber.length()+""+assetName.length());
			logger.info(categoryName+""+assetLocation+""+assetmodelNo+""+poNumber+""+assetName);
			String assetTag=categoryName.substring(0,2).concat("/").concat(assetLocation.substring(0, 2)).concat("/")
			.concat(assetmodelNo.substring(0,2)).concat("/").concat(poNumber.substring(0, 2)).concat("-").concat(assetName.substring(0,2));
			asset.setAssetTag(assetTag);
		/*	String catTag=categoryName.substring(0, 3);
			String locTag=assetLocation.substring(0, 3);
			String modelTag=assetmodelNo.substring(0,3);
			String poTag=poNumber.substring(0, 3);
			String assetNameTag=assetName.substring(0,3);*/
			//.concat("/").concat(assetLocation.subSequence(0, 3));
			
			//validator.validate(asset, result);
			
			assetList.add(asset);
			
			//assetService.save(asset);	
				
				
			
			}
			}
		}
			if(i==0)
			{
			for(Asset asset:assetList)
			{
				
				assetService.save(asset);
				
				AssetWarranty assetWarranty = new AssetWarranty();
				assetWarranty.setAssetId(asset.getAssetId());
				assetWarranty.setWarrantyFromDate(asset.getPurchaseDate());
				assetWarranty.setWarrantyToDate(asset.getWarrantyTill());
				assetWarranty.setWarrantyType("Original");
				assetWarranty.setWarrantyValid("Yes");
				//assetWarranty.setCreatedBy(userName);
				//assetWarranty.setLastUpdatedBy(userName);
				assetWarrantyService.save(assetWarranty);
				
			}
			
			}
			
			}
			catch (Exception ex)
			{
				//ex.printStackTrace();
				XSSFWorkbook workbook1 = new XSSFWorkbook(files.getInputStream());
				XSSFSheet  sheet = workbook1.getSheetAt(0);
				
				
				Iterator rows = sheet.rowIterator();
				
				int rowStart = Math.min(0, sheet.getFirstRowNum());
				int rowEnd = Math.max(400, sheet.getLastRowNum());
				for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
					Row ro = sheet.getRow(rowNum);
					if(ro!=null)
					{
				while(rows.hasNext())
				{
					final XSSFRow  row = ((XSSFRow ) rows.next());

					logger.info("*****ROW COUNT*****"+row.getRowNum());
					Iterator cells = row.cellIterator();
					
					if(row.getRowNum()==0){
						continue; //just skip the rows if row number is 0 or 1
					}
					try{
						logger.info("result-------"+row.getCell(0).getStringCellValue());
					if(!StringUtils.isEmpty(row.getCell(0).getStringCellValue()) || row.getCell(0).getStringCellValue() != null){
						logger.info("*****Block Name*******"+row.getCell(0).getStringCellValue());
						assetName=row.getCell(0).getStringCellValue();

					}
					else{
						logger.info("FAILED");
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Name is Null at row:"+(row.getRowNum()) +"& Column No 1");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					}
					catch(Exception e){
						logger.info("FAILED 1");
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Name is Null at row:"+(row.getRowNum()) +"& Column No 1");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try
					{
					  if(!StringUtils.isEmpty(row.getCell(1).getStringCellValue()) && row.getCell(1).getStringCellValue() != null){
						logger.info("*****Block Name*******"+row.getCell(1).getStringCellValue());
						assetType=row.getCell(1).getStringCellValue();
						
						if(assetType.equalsIgnoreCase("movable")|| assetType.equalsIgnoreCase("immovable") )
						{
							
						}

					
					
					}
					else{
						logger.info("FAILED");
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Type is movable/immovable only at row :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					
					
					}
					}
					catch(Exception e){
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Type Null at row :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
					if(!StringUtils.isEmpty(row.getCell(2).getStringCellValue()) && row.getCell(2).getStringCellValue() != null){
						logger.info("*****Block Name*******"+row.getCell(2).getStringCellValue());
						category=row.getCell(2).getStringCellValue();
						List<Department> list=departmentService.executeSimpleQuery("SELECT m From Department m WHERE upper(m.dept_Name)='"+""+category.toUpperCase().trim()+"'");
						logger.info("==========list"+list.get(0).getDept_Id());
						//Department department=list.get(0);
						List<MaintainanceDepartment> list1=maintainanceDepartmentService.executeSimpleQuery("SELECT ma From MaintainanceDepartment ma WHERE ma.department.dept_Id="+list.get(0).getDept_Id());
						System.out.println("hgyukhjgujhiuiutt"+list1.get(0).getMtDpIt());
						//department.get
						if(list.size()>0 && list1.size()>0)
						{
							
						int mdepId=list.get(0).getDept_Id();
						maintainanceDepartment=maintainanceDepartmentService.find(list1.get(0).getMtDpIt());
						
						}
						else
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Department Name Does't exist at row :"+(row.getRowNum()) +"& Column No 2");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
						

					}
					else
					{
						logger.info("*****getCell(2) in else*******"+row.getCell(2).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Department Name Does't exist at row :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e)
					{
						logger.info("*****getCell(2) in else*******"+row.getCell(2).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Department Name is null at row :"+(row.getRowNum()) +"& Column No 2");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
					/*if(!StringUtils.isEmpty(row.getCell(3).getStringCellValue())&& row.getCell(3).getStringCellValue() != null){*/
						if(row.getCell(3).getDateCellValue() != null){
						logger.info("*****getCell(3)*******"+row.getCell(3).getDateCellValue());
						String date1=row.getCell(3).getDateCellValue().toString();
						DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
						Date date = (Date)formatter.parse(date1);
						System.out.println(date);
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
						perchaseDate = format.format(date);
						//System.out.println(result);
						//Date date=row.getCell(3).getDateCellValue();
						logger.info("========"+perchaseDate);
						// perchaseDate=row.getCell(3).getStringCellValue();
						 String[] arr=perchaseDate.split("/");
							String month=arr[0];
							String day=arr[1];
							String year=arr[2];
							int dayInt=0;
							int monthint=0;
							int yearint=0;
							try
							{
							 dayInt=Integer.parseInt(day);
							 monthint=Integer.parseInt(month);
							 yearint=Integer.parseInt(year);
							 
							 if(monthint==02 && dayInt>29)
							 {
								 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
										{
											put("cannotImport", "Perchase Date is in invalid format(Feb month date can't be)>29 at row:"+(row.getRowNum()) +"& Column No 3");
										}
									};
									errorResponse.setStatus("cannotImport");
									errorResponse.setResult(errorMapResponse);
									i=1;
									break;
							 }
							}
							catch(Exception e)
							{
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Perchase Date is in invalid format at row:"+(row.getRowNum()) +"& Column No 3");
									}
								};
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
							}
							logger.info("dayInt"+dayInt+"monthint"+monthint+"yearint"+yearint);
							logger.info("ay.length()"+day.length()+"month.length()"+month.length()+"year.length()"+year.length());
							/*if(i==0){
								break;
							}*/
							if(day.length()!=2 || month.length()!=2 || year.length()!=4)
							{
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Perchase Date is in invalid format at row:"+(row.getRowNum()) +"& Column No 3");
									}
								};	
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
							}
							
							if(dayInt>31 || monthint>12){
								
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Perchase Date is in invalid format at row:"+(row.getRowNum()) +"& Column No 3");
									}
								};	
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
								
							}
					
					
					}
					
					else
					{
						logger.info("*****getCell(3)*******"+row.getCell(3).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Perchase Date is Null at row:"+(row.getRowNum()) +"& Column No 3");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e){
						logger.info("*****getCell(3)*******"+row.getCell(3).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Perchase Date is not in valid format at row:"+(row.getRowNum()) +"& Column No 3");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
					if(!StringUtils.isEmpty(row.getCell(4).getStringCellValue())&& row.getCell(4).getStringCellValue() != null){
						logger.info("*****getCell(4)*******"+row.getCell(4).getStringCellValue());
						String costcenter=row.getCell(4).getStringCellValue();
						List<CostCenter> list=costCenterService.executeSimpleQuery("SELECT C FROM CostCenter c WHERE c.name='"+""+costcenter.trim()+"'");
	                    
	                    
	                    if(list.size()>0)
	                    {
	                    	ccId=list.get(0).getCcId();
	                    	logger.info("cost center id=================="+ccId);
	                    	costCenter=costCenterService.find(ccId);
	                    	logger.info("cost center object"+costCenter+""+costCenter.getCcId());
	                    }
	                    
	                    else
	                    {
	                    	logger.info("*****getCell(4) else*******"+row.getCell(4).getStringCellValue());
	                    	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
	    						{
	    							put("cannotImport", "Cost Center does't exist at row :"+(row.getRowNum()) +"& Column No 4");
	    						}
	    					};
	    					errorResponse.setStatus("cannotImport");
	    					errorResponse.setResult(errorMapResponse);
	    					i=1;
	    					break;
	                    }
					
					
					}
					
				       else
	                   {
	                   	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
	   						{
	   							put("cannotImport", "Cost Center does't exist at row :"+(row.getRowNum()) +"& Column No 4");
	   						}
	   					};
	   					errorResponse.setStatus("cannotImport");
	   					errorResponse.setResult(errorMapResponse);
	   					i=1;
	   					break;
	                   }
					}
					catch(Exception e)
					{
					 	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
	   						{
	   							put("cannotImport", "Cost Center is null at row :"+(row.getRowNum()) +"& Column No 4");
	   						}
	   					};
	   					errorResponse.setStatus("cannotImport");
	   					errorResponse.setResult(errorMapResponse);
	   					i=1;
	   					break;
					}
					try{
					//logger.info("cell type========"+row.getCell(5).getCellType());
					if(row.getCell(5).getCellType()==1)
					{
					if(!StringUtils.isEmpty(row.getCell(5).getStringCellValue()) && row.getCell(5).getStringCellValue() != null){
						/*if(row.getCell(5).getNumericCellValue()>=0){*/
						logger.info("*****getCell(5)*******"+row.getCell(5).getStringCellValue());
						poNumber=row.getCell(5).getStringCellValue();
						logger.info("==================po number===="+poNumber);
						if(poNumber.length()<3)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "PO Number length should be at least 3 :"+(row.getRowNum()) +"& Column No 5");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
					  }
					else
					{
						logger.info("*****getCell(5) else*******"+row.getCell(5).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "PO Number is Null at row :"+(row.getRowNum()) +"& Column No 5");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					
					}
					if(row.getCell(5).getCellType()==0)
					{
						int poNo=(int) row.getCell(5).getNumericCellValue();
						poNumber=Integer.toString(poNo);
						logger.info("==================po number===="+poNumber);
						if(poNumber.length()<3)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "PO Number length should be at least 3 :"+(row.getRowNum()) +"& Column No 5");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
						}
						
						
					}
					
				
					}
				
					catch(Exception e){
						poNumber="Not/Applicable";
						/*e.printStackTrace();
						logger.info("*****getCell(5) else*******"+e);*/
						/*HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "PO Number is Null at row :"+(row.getRowNum()) +"& Column No 5");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
					if(!StringUtils.isEmpty(row.getCell(6).getStringCellValue()) && row.getCell(6).getStringCellValue() != null){
						logger.info("*****getCell(6)*******"+row.getCell(6).getStringCellValue());
						 categoryName=row.getCell(6).getStringCellValue();
						List<AssetCategoryTree> list=assetCategoryService.executeSimpleQuery("SELECT ac FROM AssetCategoryTree ac WHERE ac.assetcatText='"+""+categoryName.trim()+"'");
						if(list.size()>0)
						{
							 catId=list.get(0).getAssetcatId();
						}
						
						else
						{
							logger.info("*****getCell(6) else*******"+row.getCell(6).getStringCellValue());
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Category does't exist at row:"+(row.getRowNum()) +"& Column No 6");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
						
					  }
					
					else
					{
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Categoryr is Null at row:"+(row.getRowNum()) +"& Column No 6");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e){
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Categoryr is Null at row:"+(row.getRowNum()) +"& Column No 6");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					try{
					if(!StringUtils.isEmpty(row.getCell(7).getStringCellValue()) && row.getCell(7).getStringCellValue() != null){
						logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
						assetLocation=row.getCell(7).getStringCellValue();
						List<AssetLocationTree> list=assetLocationTreeService.executeSimpleQuery("SELECT al FROM AssetLocationTree al WHERE al.assetlocText='"+""+assetLocation.trim()+"'");
						if(list.size()>0)
						{
							locationId=list.get(0).getAssetlocId();
						}
					
						
						else
						{
							logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Location does't exist at row:"+(row.getRowNum()) +"& Column No 7");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
					
					  }
					
					else
					{
						logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Location is Null at row :"+(row.getRowNum()) +"& Column No 7");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e)
					{
						logger.info("*****getCell(7)*******"+row.getCell(7).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Location is Null at row :"+(row.getRowNum()) +"& Column No 7");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
						if(row.getCell(8).getCellType()==1)
						{
					if(!StringUtils.isEmpty(row.getCell(8).getStringCellValue()) && row.getCell(8).getStringCellValue() != null){
						logger.info("*****getCell(8)*******"+row.getCell(8).getStringCellValue());
					   assetGeoTag=row.getCell(8).getStringCellValue();
					
					
					}
						}
					
					else if(row.getCell(8).getCellType()==0)
					{
						
						 assetGeoTag=Double.toString(row.getCell(8).getNumericCellValue());
						
						/*logger.info("*****getCell(8) else*******"+row.getCell(8).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Geo Tag is Null at row:"+(row.getRowNum()) +"& Column No 8");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
						
					}
					}
					catch(Exception e)
					{
						logger.info("*****getCell(8) else*******"+row.getCell(8).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Geo Tag is Null at row:"+(row.getRowNum()) +"& Column No 8");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
					if(!StringUtils.isEmpty(row.getCell(9).getStringCellValue())){
						logger.info("*****getCell(9)*******"+row.getCell(9).getStringCellValue());
						asssetCondition=row.getCell(9).getStringCellValue();
					
					
					}
					else
					{
						logger.info("*****getCell(9) else*******"+row.getCell(9).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 9");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e)
					{
						logger.info("*****getCell(9) else*******"+row.getCell(9).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 9");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;	
					}
					try{
						if(row.getCell(10).getCellType()==1)
						{
					if(!StringUtils.isEmpty(row.getCell(10).getStringCellValue()) && row.getCell(10).getStringCellValue() != null){
						logger.info("*****getCell(10)*******"+row.getCell(10).getStringCellValue());
						manufacturer=row.getCell(10).getStringCellValue();
					
					
					}
						}
					
					else if(row.getCell(10).getCellType()==0)
					{
					
						manufacturer=Double.toString(row.getCell(10).getNumericCellValue());
						
						/*	logger.info("*****getCell(10) else*******"+row.getCell(10).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 10");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
						
					}
					}
					catch(Exception e)
					{
						
						manufacturer="N/A";
						//logger.info("*****getCell(10) else*******"+row.getCell(10).getStringCellValue());
						/*HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Condition is Null at row:"+(row.getRowNum()) +"& Column No 10");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
					if(row.getCell(11).getDateCellValue() != null){
						logger.info("*****getCell(11)*******"+row.getCell(11).getDateCellValue());
						String date1=row.getCell(11).getDateCellValue().toString();
						DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
						Date date = (Date)formatter.parse(date1);
						System.out.println(date);
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
						manufactureryear = format.format(date);
						logger.info("manufactureryear=="+manufactureryear);
						//manufactureryear=row.getCell(11).getStringCellValue();
						 String[] arr=manufactureryear.split("/");
							String day=arr[1];
							String month=arr[0];
							String year=arr[2];
							int dayInt=0;
							int monthint=0;
							int yearint=0;
							try
							{
							 dayInt=Integer.parseInt(day);
							 monthint=Integer.parseInt(month);
							 yearint=Integer.parseInt(year);
							 if(monthint==02 && dayInt>29)
							 {
								 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
										{
											put("cannotImport", "mnf Date is in invalid format(Feb month date can't be)>29 at row:"+(row.getRowNum()) +"& Column No 3");
										}
									};
									errorResponse.setStatus("cannotImport");
									errorResponse.setResult(errorMapResponse);
									i=1;
									break;
							 }
							}
							catch(Exception e)
							{
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Mnf Year is in invalid format at row:"+(row.getRowNum()) +"& Column No 11");
									}
								};
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
							}
							if(day.length()!=2 || month.length()!=2 || year.length()!=4)
							{
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Mnf Year is in invalid format at row:"+(row.getRowNum()) +"& Column No 11");
									}
								};	
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
							}
							
							if(dayInt>31 || monthint>12){
								
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Mnf Year is in invalid format at row:"+(row.getRowNum()) +"& Column No 11");
									}
								};	
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
								
							}
					
					
					}
					
					else
					{
						logger.info("*****getCell(11)*******"+row.getCell(11).getDateCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Manufacturer Year is Null at row:"+(row.getRowNum()) +"& Column No 11");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e){
						logger.info("***** Catch block getCell(11)*******");
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Manufacturer Year is Not in valid format at row:"+(row.getRowNum()) +"& Column No 11");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
						logger.info("row.getCell(12).getCellType()"+row.getCell(12).getCellType());
						if(row.getCell(12).getCellType()==1)
						{
					if(!StringUtils.isEmpty(row.getCell(12).getStringCellValue()) && row.getCell(12).getStringCellValue() != null){
						logger.info("*****getCell(12)*******"+row.getCell(12).getStringCellValue());
						assetmodelNo=row.getCell(12).getStringCellValue();
						if(assetmodelNo.length()<3)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Asset Model number length should be at least 3:"+(row.getRowNum()) +"& Column No 12");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
					
					
					}
						}
					
					else if(row.getCell(12).getCellType()==0)
					{
						logger.info("*****getCell(12)*******"+row.getCell(12).getNumericCellValue());
						assetmodelNo=Double.toString(row.getCell(12).getNumericCellValue());
						if(assetmodelNo.length()<3)
						{
							HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
								{
									put("cannotImport", "Asset Model number length should be at least 3:"+(row.getRowNum()) +"& Column No 12");
								}
							};
							errorResponse.setStatus("cannotImport");
							errorResponse.setResult(errorMapResponse);
							i=1;
							break;
							
						}
						/*logger.info("*****getCell(12)*******"+row.getCell(12).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Model number is Null at row:"+(row.getRowNum()) +"& Column No 12");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
						
					}
					else if(row.getCell(12).getCellType()==3)
					{
						assetmodelNo="Not/Applicable";
					}
					}
					catch(Exception e){
						
						logger.info("*****getCell(12)******* catch block");
						assetmodelNo="Not/Applicable";
						/*logger.info("*****getCell(12)*******"+row.getCell(12).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Model number is Null at row:"+(row.getRowNum()) +"& Column No 12");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
						if(row.getCell(13).getCellType()==1)
						{
					if(!StringUtils.isEmpty(row.getCell(13).getStringCellValue()) && row.getCell(13).getStringCellValue() != null){
						logger.info("*****getCell(13)*******"+row.getCell(13).getStringCellValue());
						manserNo=row.getCell(13).getStringCellValue();
					
					
					}
						}
						else if(row.getCell(13).getCellType()==0)
						{
							manserNo=Double.toString(row.getCell(13).getNumericCellValue());
						}
					
				/*	else
					{
						logger.info("*****getCell(13) else*******"+row.getCell(13).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Mnf serial number number is Null at row:"+(row.getRowNum()) +"& Column No 13");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}*/
					}
					catch(Exception e){
					
						manserNo="Not/Applicable";
						/*	logger.info("*****getCell(13) else*******"+row.getCell(13).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Mnf serial number number is Null at row:"+(row.getRowNum()) +"& Column No 13");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
						if(row.getCell(14).getCellType()==1){
					if(!StringUtils.isEmpty(row.getCell(14).getStringCellValue()) && row.getCell(14).getStringCellValue() != null){
						
						assetUsefulLife=row.getCell(14).getStringCellValue();
						}
					}
					else if(row.getCell(14).getCellType()==0)
					{
						int usefullife=(int) row.getCell(14).getNumericCellValue();
						logger.info("==============asset useful life===="+usefullife);
						
						if(usefullife>0)
						
						assetUsefulLife=Integer.toString(usefullife);
					
					
					  
						
						/*logger.info("*****getCell(14) else*******"+row.getCell(14).getNumericCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Useful Life is not valid  at row:"+(row.getRowNum()) +"& Column No 14");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
						
					}
					}
					catch(Exception e){
						
						assetUsefulLife="Not/Applicable";
						
						/*logger.info("*****getCell(14) else*******"+row.getCell(14).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Useful Life is not in valid format at row:"+(row.getRowNum()) +"& Column No 14");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
						if(row.getCell(15).getCellType()==1)
						{
					if(!StringUtils.isEmpty(row.getCell(15).getStringCellValue()) && row.getCell(15).getStringCellValue() != null){
						logger.info("************"+row.getCell(15).getStringCellValue());
						assetdesc=row.getCell(15).getStringCellValue();
					
					
					}
						}
						else if(row.getCell(15).getCellType()==0){
							assetdesc=Double.toString(row.getCell(15).getNumericCellValue());
							
						}
					}
					catch(Exception e){
					
						
						assetdesc="Not/Applicable";
						/*	HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset desc Life is is Null at row:"+(row.getRowNum()) +"& Column No 15");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
						if(row.getCell(16).getCellType()==1)
						{
					if(!StringUtils.isEmpty(row.getCell(16).getStringCellValue()) && row.getCell(16).getStringCellValue() != null){
						logger.info("************"+row.getCell(16).getStringCellValue());
						assetNotes=row.getCell(16).getStringCellValue();
					
					
					}
						}
						else if(row.getCell(16).getCellType()==0)
						{
							assetNotes=Double.toString(row.getCell(16).getNumericCellValue());	
						}
					}
					catch(Exception e){
						
						assetNotes="Not/Applicable";
						/*HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Notes is is Null at row:"+(row.getRowNum()) +"& Column No 16");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;*/
					}
					try{
					if(row.getCell(17).getDateCellValue()!= null){
						logger.info("************"+row.getCell(17).getDateCellValue());
						String date1=row.getCell(17).getDateCellValue().toString();
						DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
						Date date = (Date)formatter.parse(date1);
						System.out.println(date);
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
						warentytill = format.format(date);
						logger.info("warentytill======="+warentytill);
						//warentytill=row.getCell(17).getStringCellValue();
						 String[] arr=warentytill.split("/");
							String day=arr[1];
							String month=arr[0];
							String year=arr[2];
							int dayInt=0;
							int monthint=0;
							int yearint=0;
							try
							{
							 dayInt=Integer.parseInt(day);
							 monthint=Integer.parseInt(month);
							 yearint=Integer.parseInt(year);
							 if(monthint==02 && dayInt>29)
							 {
								 HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
										{
											put("cannotImport", "Warenty Date is in invalid format(Feb month date can't be)>29 at row:"+(row.getRowNum()) +"& Column No 3");
										}
									};
									errorResponse.setStatus("cannotImport");
									errorResponse.setResult(errorMapResponse);
									i=1;
									break;
							 }
							}
							catch(Exception e)
							{
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Warenty date is in invalid format at row:"+(row.getRowNum()) +"& Column No 17");
									}
								};
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
							}
							if(day.length()!=2 || month.length()!=2 || year.length()!=4)
							{
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "Warenty date is in invalid format at row:"+(row.getRowNum()) +"& Column No 17");
									}
								};	
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
							}
							
							if(dayInt>31 || monthint>12){
								
								HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
									{
										put("cannotImport", "warenty date is in invalid format at row:"+(row.getRowNum()) +"& Column No 17");
									}
								};	
								errorResponse.setStatus("cannotImport");
								errorResponse.setResult(errorMapResponse);
								i=1;
								break;
								
							}
					
					
					}
					else
					{
						logger.info("*****getCell(17) else*******"+row.getCell(17).getDateCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Warenty Date is is Null at row:"+(row.getRowNum()) +"& Column No 17");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e){
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Warenty Date is not an valid format at row:"+(row.getRowNum()) +"& Column No 17");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					try{
					if(!StringUtils.isEmpty(row.getCell(18).getStringCellValue()) && row.getCell(18).getStringCellValue() != null){
						logger.info("************"+row.getCell(18).getStringCellValue());
						assetownership=row.getCell(18).getStringCellValue();
					
					
					}
					else
					{
						logger.info("*****getCell(18) else*******"+row.getCell(18).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Ownership is is Null at row:"+(row.getRowNum()) +"& Column No 18");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
						
					}
					}
					catch(Exception e){
						//logger.info("*****getCell(18) else*******"+row.getCell(18).getStringCellValue());
						HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
							{
								put("cannotImport", "Asset Ownership is is Null at row:"+(row.getRowNum()) +"& Column No 18");
							}
						};
						errorResponse.setStatus("cannotImport");
						errorResponse.setResult(errorMapResponse);
						i=1;
						break;
					}
					
					
				Asset asset=new Asset();
				DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
				logger.info("assetyear before parse"+manufactureryear);
				Date assetyear = df.parse(manufactureryear);
				logger.info("assetyear after parse"+assetyear);
				asset.setAssetYear(assetyear);
				logger.info("purchaseDate before parse"+perchaseDate);
				Date purchaseDate=df.parse(perchaseDate);
				logger.info("purchaseDate after parse"+purchaseDate);
				asset.setPurchaseDate(purchaseDate);
				Date warenty=df.parse(warentytill);
				logger.info("warenty before parse"+warentytill);
				asset.setWarrantyTill(warenty);
				logger.info("warenty after parse"+warenty);
				
				int com=assetyear.compareTo(warenty);
				int comp1=assetyear.compareTo(purchaseDate);
				int comp2=warenty.compareTo(purchaseDate);
				logger.info("======="+com+""+comp1+""+comp2);
				
				logger.info("warenty date"+warenty);
				logger.info("purchaseDate=="+purchaseDate);
				if(warenty.before(purchaseDate)){
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "warenty date should be greater than purchase date at row  "+row.getRowNum());
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				if(purchaseDate.before(assetyear)){
					HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
						{
							put("cannotImport", "Mnf date should be greater than purchase date at row  "+row.getRowNum());
						}
					};
					errorResponse.setStatus("cannotImport");
					errorResponse.setResult(errorMapResponse);
					i=1;
					break;
					
				}
				
				asset.setMaintainanceDepartment(maintainanceDepartment);
				TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
				Calendar cal = Calendar.getInstance(tz);		
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				sdf.setCalendar(cal);		
				cal.setTime(asset.getAssetYear());
				cal.add(Calendar.YEAR, Integer.parseInt(assetUsefulLife));		
				Date expiryYear = cal.getTime();		
				java.sql.Date sqlExpiryDate=new java.sql.Date(expiryYear.getTime());
	            asset.setAssetLifeExpiry(sqlExpiryDate);

	            if(StringUtils.equalsIgnoreCase(assetownership.trim(),"Leased")){

					asset.setSupplier("Leased");
					
				}else{
					asset.setAssetVendorId(1);
				}
	            asset.setAssetDesc(assetdesc);
				asset.setAssetNotes(assetNotes);
				asset.setCcId(ccId);
				//asset.setCostCenter(costCenter);
				asset.setAssetCatId(catId);
				asset.setAssetLocId(locationId);
				asset.setOwnerShip(assetownership);
				asset.setAssetName(assetName);
				asset.setAssetStatus("in use");
				asset.setAssetType(assetType);
				asset.setAssetCondition(asssetCondition);
				asset.setAssetManufacturer(manufacturer);
				asset.setAssetPoDetail(poNumber);
				asset.setAssetModelNo(assetmodelNo);
				asset.setAssetManufacturerSlNo(manserNo);
				asset.setUseFullLife(assetUsefulLife);
				asset.setAssetNotes(assetNotes);
				logger.info("going to generate asset tag");
				//logger.info(categoryName.length()+""+assetLocation.length()+""+assetmodelNo.length()+""+poNumber.length()+""+assetName.length());
				logger.info(categoryName+""+assetLocation+""+assetmodelNo+""+poNumber+""+assetName);
				String assetTag=categoryName.substring(0,2).concat("/").concat(assetLocation.substring(0, 2)).concat("/")
				.concat(assetmodelNo.substring(0,2)).concat("/").concat(poNumber.substring(0, 2)).concat("-").concat(assetName.substring(0,2));
				asset.setAssetTag(assetTag);
			/*	String catTag=categoryName.substring(0, 3);
				String locTag=assetLocation.substring(0, 3);
				String modelTag=assetmodelNo.substring(0,3);
				String poTag=poNumber.substring(0, 3);
				String assetNameTag=assetName.substring(0,3);*/
				//.concat("/").concat(assetLocation.subSequence(0, 3));
				
				//validator.validate(asset, result);
				
				assetList.add(asset);
				
				//assetService.save(asset);	
					
					
				
				}
				}
			}
				if(i==0)
				{
				for(Asset asset:assetList)
				{
					
					assetService.save(asset);
					
					AssetWarranty assetWarranty = new AssetWarranty();
					assetWarranty.setAssetId(asset.getAssetId());
					assetWarranty.setWarrantyFromDate(asset.getPurchaseDate());
					assetWarranty.setWarrantyToDate(asset.getWarrantyTill());
					assetWarranty.setWarrantyType("Original");
					assetWarranty.setWarrantyValid("Yes");
					//assetWarranty.setCreatedBy(userName);
					//assetWarranty.setLastUpdatedBy(userName);
					assetWarrantyService.save(assetWarranty);
					
				}
				
				}
				
			}
			
			
			
			
		}
		
		
		return errorResponse;
		
	}
}