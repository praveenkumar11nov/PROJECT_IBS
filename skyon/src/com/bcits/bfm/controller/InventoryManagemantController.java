package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.ItemMaster;
import com.bcits.bfm.model.StoreAdjustments;
import com.bcits.bfm.model.StoreCategory;
import com.bcits.bfm.model.StoreGoodsReceipt;
import com.bcits.bfm.model.StoreGoodsReceiptItems;
import com.bcits.bfm.model.StoreGoodsReturns;
import com.bcits.bfm.model.StoreIssue;
import com.bcits.bfm.model.StoreItemLedger;
import com.bcits.bfm.model.StoreItemLedgerDetails;
import com.bcits.bfm.model.StoreMaster;
import com.bcits.bfm.model.StoreMovement;
import com.bcits.bfm.model.StoreOutward;
import com.bcits.bfm.model.StorePhysicalInventory;
import com.bcits.bfm.model.StorePhysicalInventoryReport;
import com.bcits.bfm.model.UnitOfMeasurement;
import com.bcits.bfm.model.VendorContractLineitems;
import com.bcits.bfm.model.VendorContracts;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.DrGroupIdService;
import com.bcits.bfm.service.VendorsManagement.ItemMasterService;
import com.bcits.bfm.service.VendorsManagement.UomService;
import com.bcits.bfm.service.VendorsManagement.VendorContractsLineItemsService;
import com.bcits.bfm.service.VendorsManagement.VendorContractsService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.JcMaterialsService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.inventoryManagement.StoreAdjustmentsService;
import com.bcits.bfm.service.inventoryManagement.StoreCategoryService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptItemsService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReceiptService;
import com.bcits.bfm.service.inventoryManagement.StoreGoodsReturnsService;
import com.bcits.bfm.service.inventoryManagement.StoreIssueService;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerDetailsService;
import com.bcits.bfm.service.inventoryManagement.StoreItemLedgerService;
import com.bcits.bfm.service.inventoryManagement.StoreMasterService;
import com.bcits.bfm.service.inventoryManagement.StoreMovementService;
import com.bcits.bfm.service.inventoryManagement.StoreOutwardService;
import com.bcits.bfm.service.inventoryManagement.StorePhysicalInventoryReportService;
import com.bcits.bfm.service.inventoryManagement.StorePhysicalInventoryService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.util.ValidationUtil;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class InventoryManagemantController extends FilterUnit {
	
	static Logger logger = LoggerFactory.getLogger(InventoryManagemantController.class);

	
	@Resource
	private BreadCrumbTreeService breadCrumbService;
	@Resource
	private JsonResponse errorResponse;
	@Resource
	private ValidationUtil validationUtil;
	@Resource
	private DateTimeCalender dateTimeCalender;
	@Autowired
	private DrGroupIdService drGroupIdService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private StoreMasterService storeMasterService;
	@Autowired
	private StoreGoodsReceiptService storeGoodsReceiptService;
	@Autowired
	private VendorContractsService vendorContractsService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private PersonService personService;
	@Autowired
	private StoreGoodsReceiptItemsService storeGoodsReceiptItemsService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private UomService uomService;
	@Autowired
	private StoreItemLedgerService storeItemLedgerService;
	@Autowired
	private StoreIssueService storeIssueService;
	@Autowired
	private JobCardsService jobCardsService;
	@Autowired
	private ItemMasterService itemMasterService;
	@Autowired
	private StoreMovementService storeMovementService;
	@Autowired
	private StoreAdjustmentsService storeAdjustmentsService;
	@Autowired
	private StoreGoodsReturnsService storeGoodsReturnsService;
	@Autowired
	private VendorContractsLineItemsService vendorContractsLineItemsService;
	@Autowired
	private StoreItemLedgerDetailsService storeItemLedgerDetailsService;
	@Autowired
	private StorePhysicalInventoryService storePhysicalInventoryService;
	@Autowired
	private StoreCategoryService storeCategoryService;
	@Autowired
	private StorePhysicalInventoryReportService storePhysicalInventoryReportService;
	@Autowired
	private JcMaterialsService jcMaterialsService;
	@Autowired
	private StoreOutwardService storeOutwardService;
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Receipts --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreGoodsReceipt", method = RequestMethod.GET)
	public String indexStoreGoodsReceipt(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		
		breadCrumbService.addNode("Manage Store Goods Receipts", 1, request);
		model.addAttribute("receiptTypes", commonController.populateCategories("receiptType", locale));
		return "inventory/storeGoodsReceipt";
	}
	

	/** 
	 *  Reading Store Goods Receipt data.  
	 *
	 * @param  None 
	 * @return   List of Store Goods Receipts.
	 * @since  1.0
	 */
	@RequestMapping(value = "/storeGoodsReceipt/read", method = RequestMethod.POST)
	public @ResponseBody Object readStoreGoodsReceipt(final Locale locale){
		try
		{
			return storeGoodsReceiptService.getRequiredStoreGoodsReceipts();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeGoodsReceipt/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStoreMaster(@PathVariable String operation, @ModelAttribute("storeGoodsReceipt") StoreGoodsReceipt storeGoodsReceipt, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			storeGoodsReceipt.setLastUpdatedDt(new Timestamp(new Date().getTime()));
			if(storeGoodsReceipt.getPoRecDate() instanceof java.util.Date)
				storeGoodsReceipt.setPoRecDt(dateTimeCalender.getTimestampFromDateAndTime(storeGoodsReceipt.getPoRecDate(), storeGoodsReceipt.getPoRecTime()));
			if((validationUtil.customValidateModelObject(storeGoodsReceipt, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeGoodsReceipt, null, locale);
			if(operation.equals("create"))
			{
				storeGoodsReceipt.setLedgerUpdateDt(null);
				drGroupIdService.save(new DrGroupId(storeGoodsReceipt.getCreatedBy(), storeGoodsReceipt.getLastUpdatedDt()));
				storeGoodsReceipt.setDrGroupId(drGroupIdService.getNextVal(storeGoodsReceipt.getCreatedBy(), storeGoodsReceipt.getLastUpdatedDt()));
				storeGoodsReceiptService.save(storeGoodsReceipt);
			}
			else
			{
				List<StoreGoodsReceiptItems> storeGoodsReceiptItemsList = storeGoodsReceiptItemsService.findAllStoreGoodsReceiptItems(storeGoodsReceipt.getStgrId());
				
				Set<StoreGoodsReceiptItems> storeGoodsReceiptItemsSet = new HashSet<StoreGoodsReceiptItems>(storeGoodsReceiptItemsList);
				
				storeGoodsReceipt.setStoreGoodsReceiptItemsSet(storeGoodsReceiptItemsSet);
				storeGoodsReceiptService.update(storeGoodsReceipt);
			}
			
			sessionStatus.setComplete();
			return storeGoodsReceipt;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeGoodsReceipt/checkAllMinimumRequiredStoreGoodsReceiptConstraints", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Object checkAllMinimumRequiredStoreGoodsReceiptConstraints() {	
		List<?> storesList = storeMasterService.getStoresBasedOnContractExistence();
		List<?> personList = personService.getAllPersonRequiredDetailsBasedOnPersonType("Staff");
		if((storesList.size() > 0) && (personList.size() > 0))
			return "success";
		else
		{
			List<String> response = new ArrayList<String>();
			response.add("Cannot add new Store Good because of the following one or more reason/reasons<br/>");
			response.add("1. No Approved Vendor contracts were found which are not already assigned.<br/>");
			response.add("2. Even if contracts are available,<br/>");
			response.add("	 (a) Items might not be present inside that contract.<br/>");
			response.add("   (b) Store might be inactive.<br/>");
			response.add("   (c) Items may not be of requisition type 'Item Supply'.<br/>");
			response.add("3. No active staffs are available.<br/>");
			return response;
		}
			
	} 
	
	@RequestMapping(value = "/storeGoodsReceipt/getStoreMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> getStoreMasterComboBoxUrl() {	
		List<?> storesList = storeMasterService.getStoresBasedOnContractExistence();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = storesList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("storeId", (Integer)values[0]);
			map.put("storeName", (String)values[1]);
			map.put("storeLocation", (String)values[2]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReceipt/getStoreNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getStoreNamesFilterUrl() {	
		List<StoreGoodsReceipt> storesList = storeGoodsReceiptService.getRequiredStoreGoodsReceipts();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreGoodsReceipt> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreGoodsReceipt storeGoodsReceipt = (StoreGoodsReceipt) iterator.next();
			map = new HashMap<String, Object>();
			map.put("storeName", storeGoodsReceipt.getStoreName());
			map.put("storeLocation", storeGoodsReceipt.getStoreLocation());
			result.add(map);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/storeGoodsReceipt/getVendorContractComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<?> getVendorContractComboBoxUrl() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		List<VendorContracts> vendorContractsList = storeGoodsReceiptService.getAllRequiredVCWhosHasChildrenVCLI();
		Map<String, Object> map = null;
		for (Iterator<VendorContracts> iterator = vendorContractsList.iterator(); iterator
				.hasNext();)
		{
			VendorContracts vendorContracts = (VendorContracts) iterator.next();
			map = new HashMap<String, Object>();
			map.put("storeId", vendorContracts.getStoreId());
			map.put("vcId", vendorContracts.getVcId());
			map.put("contractName", vendorContracts.getContractName());
			map.put("contractNo", vendorContracts.getContractNo());
			result.add(map);
			
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReceipt/getContractNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getContractNamesFilterUrl() {
		List<StoreGoodsReceipt> storesList = storeGoodsReceiptService.getRequiredStoreGoodsReceipts();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreGoodsReceipt> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreGoodsReceipt storeGoodsReceipt = (StoreGoodsReceipt) iterator.next();
			map = new HashMap<String, Object>();
			map.put("contractName", storeGoodsReceipt.getContractName());
			map.put("contractNo", storeGoodsReceipt.getContractNo());
			result.add(map);
		}
		return result;
	}

	
	
	/*--------------------------------------------------------------------------- End -----------------------------------------------------------------------------*/
	 
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Receipt Items --------------------------------------------
	 */
	
	@RequestMapping(value = "/storeGoodsReceiptItems/read/{stgrId}", method = RequestMethod.POST)
	public @ResponseBody Object readStoreGoodsReceiptItems(@PathVariable int stgrId, final Locale locale) {
		try
		{
			return storeGoodsReceiptItemsService.findAllStoreGoodsReceiptItems(stgrId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeGoodsReceiptItems/modify/{operation}/{stgrId}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStoreGoodsReceiptItems(@PathVariable String operation, @PathVariable int stgrId, @ModelAttribute("storeGoodsReceiptItems") StoreGoodsReceiptItems storeGoodsReceiptItems, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		StoreGoodsReceipt storeGoodsReceipt = null;
		try
		{	
			if((validationUtil.customValidateModelObject(storeGoodsReceiptItems, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeGoodsReceiptItems, null, locale);
			
			if(operation.equals("create"))
			{
				String user = (String) SessionData.getUserDetails().get("userID");
				Timestamp timestamp = new Timestamp(new Date().getTime());
				drGroupIdService.save(new DrGroupId(user, timestamp));
				storeGoodsReceiptItems.setDrGroupId(drGroupIdService.getNextVal(user, timestamp));
				
				storeGoodsReceipt = storeGoodsReceiptService.find(stgrId);
				Set<StoreGoodsReceiptItems> storeGoodsReceiptItemsSet = storeGoodsReceipt.getStoreGoodsReceiptItemsSet(); 
				storeGoodsReceiptItemsSet.add(storeGoodsReceiptItems);
				storeGoodsReceipt.setStoreGoodsReceiptItemsSet(storeGoodsReceiptItemsSet);
				storeGoodsReceiptService.update(storeGoodsReceipt);
			}
			
			else if(operation.equals("update"))
				storeGoodsReceiptItemsService.update(storeGoodsReceiptItems);
			else
				storeGoodsReceiptItemsService.delete(storeGoodsReceiptItems.getSgriId());
			
			sessionStatus.setComplete();
			return storeGoodsReceiptItems;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeGoodsReceiptItems/getItemsSGRComboBoxUrl/{vcId}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getItemsSGRComboBoxUrl(@PathVariable int vcId) {
		List<VendorContractLineitems> storeItemLedgersList = vendorContractsLineItemsService.getAllVCLineItemsBasedOnVcId(vcId);
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
		for (Iterator<VendorContractLineitems> iterator = storeItemLedgersList.iterator(); iterator
				.hasNext();)
		{
			VendorContractLineitems vendorContractLineitems = (VendorContractLineitems) iterator
					.next();
			map = new HashMap<String, Object>();
			map.put("imId", vendorContractLineitems.getImId());
			map.put("imName", vendorContractLineitems.getItemMaster().getImName());
			map.put("imType", vendorContractLineitems.getItemMaster().getImType());
			map.put("imGroup", vendorContractLineitems.getItemMaster().getImGroup());
			map.put("uom", vendorContractLineitems.getUnitOfMeasurement().getUom());
			map.put("quantity", vendorContractLineitems.getQuantity());
			map.put("rate", vendorContractLineitems.getRate());
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReceiptItems/getUnitOfMeasurementSGRComboBoxUrl/{vcId}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getUnitOfMeasurementComboSGRBoxUrl(@PathVariable int vcId) {	
		
		List<VendorContractLineitems> vcliList = vendorContractsLineItemsService.getAllVCLineItemsBasedOnVcId(vcId);
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
		for (Iterator<VendorContractLineitems> iterator = vcliList.iterator(); iterator
				.hasNext();)
		{
			VendorContractLineitems vendorContractLineitems = (VendorContractLineitems) iterator
					.next();
			
			List<UnitOfMeasurement> uomList = uomService.findUomBasedOnItemId(vendorContractLineitems.getImId());
			
			for (Iterator<UnitOfMeasurement> iterator2 = uomList.iterator(); iterator2.hasNext();)
			{
				UnitOfMeasurement unitOfMeasurement = (UnitOfMeasurement) iterator2
						.next();
				
				map = new HashMap<String, Object>();
				map.put("imId", unitOfMeasurement.getImId());
				map.put("uomId", unitOfMeasurement.getUomId());
				map.put("uom", unitOfMeasurement.getUom());
				map.put("baseUom", unitOfMeasurement.getBaseUom());
				map.put("code", unitOfMeasurement.getCode());
				map.put("uomConversion", unitOfMeasurement.getUomConversion());
				
				if(unitOfMeasurement.getUomId() == vendorContractLineitems.getUomId())
				{
					map.put("quantity", vendorContractLineitems.getQuantity());
					map.put("rate", vendorContractLineitems.getRate());
				}
				else
				{
					DecimalFormat decimalFormat = new DecimalFormat("###.##");

					Double quantity = Double.parseDouble(decimalFormat.format((vendorContractLineitems.getQuantity() / unitOfMeasurement.getUomConversion())/vendorContractLineitems.getUnitOfMeasurement().getUomConversion()));
					Double rate = Double.parseDouble(decimalFormat.format((vendorContractLineitems.getRate() * unitOfMeasurement.getUomConversion())/vendorContractLineitems.getUnitOfMeasurement().getUomConversion()));
					map.put("quantity", quantity);
					map.put("rate", rate);
				}
				result.add(map);
			}
			
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReceiptItems/sgriStatus/{sgriId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String storeGoodsReceiptItemsStatus(@PathVariable int sgriId){		
		//ledger update
		StoreGoodsReceiptItems storeGoodsReceiptItems = storeGoodsReceiptItemsService.find(sgriId);
		StoreGoodsReceipt storeGoodsReceipt = storeGoodsReceiptService.find(storeGoodsReceiptItems.getStgrId());
		//StoreItemLedger storeItemLedger = updateStoreItemLegder(storeGoodsReceiptItems, storeGoodsReceipt, "Credit", storeGoodsReceiptItems.getItemQuantity());
		StoreItemLedger storeItemLedger = updateStoreItemLegder(storeGoodsReceipt.getStoreId(), storeGoodsReceiptItems.getImId(), storeGoodsReceiptItems.getUomId(), storeGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Credit", storeGoodsReceiptItems.getItemQuantity());
		storeGoodsReceiptService.updateLedgerDate(storeGoodsReceipt.getStgrId(), storeItemLedger.getSilDt());
		
		StoreItemLedger newStoreItemLedger = storeItemLedgerService.find(storeItemLedger.getSilId());
		//Store categories update
		
		try{
			
			updateCategories(newStoreItemLedger.getStoreId(), newStoreItemLedger.getStoreMaster().getStoreName(), newStoreItemLedger.getImId(), newStoreItemLedger.getItemMaster().getImName());
		}catch(Exception e){
			
		}
		
		//status
		return storeGoodsReceiptItemsService.setStoreGoodsReceiptItemsStatus(sgriId, storeItemLedger.getSilDt());
	}

	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Issues --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreIssue", method = RequestMethod.GET)
	public String indexStoreIssue(ModelMap model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Store Issues", 2, request);
		return "inventory/storeIssue";
	}
	
	@RequestMapping(value = "/storeIssue/read", method = RequestMethod.POST)
	public @ResponseBody Object readstoreIssue(final Locale locale) {
		try
		{
			List<StoreIssue> storeIssuesList =  storeIssueService.findAllStoreIssues();
			return storeIssueService.getAllStoresRequiredFields(storeIssuesList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeIssue/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifystoreIssue(@PathVariable String operation, @ModelAttribute("storeIssue") StoreIssue storeIssue, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			Double addedQuantity=storeIssue.getImQuantity();
			int storeId=storeIssue.getStoreId();
			int itemId=storeIssue.getImId();
			int uomId=storeIssue.getUomId();
			
			List<StoreItemLedger> storeLadger=storeItemLedgerService.getBanceQuantity(storeId,itemId,uomId);
			
			double balance = 0;
			for(StoreItemLedger storeItemLedger:storeLadger){
				balance=storeItemLedger.getImBalance();
			}
			
			ItemMaster im=itemMasterService.find(storeIssue.getImId());
			int optimalquantity=im.getImOptimal_Stock();
			int finalquantity=(int) (balance-addedQuantity);
			
			if(finalquantity<optimalquantity){
				@SuppressWarnings("serial")
				HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
					{
						put("invalid", "Item Requested is Under Optimal Level");
					}
				};
				errorResponse.setStatus("invalid");
				errorResponse.setResult(errorMapResponse);
				return errorResponse;
			}
			
			
			if(storeIssue.getStriDate() instanceof java.util.Date)
				storeIssue.setStriDt(dateTimeCalender.getTimestampFromDateAndTime(storeIssue.getStriDate(), storeIssue.getStriTime()));
			
			storeIssue.setLedgerUpdateDt(new Timestamp(new Date().getTime()));
			UnitOfMeasurement finalUnitOfMeasurement = uomService.find(storeIssue.getUomId());
			
			Map<String, Object> params = new HashMap<>();
			params.put("vcId", storeIssue.getVcId());
			params.put("imId", storeIssue.getImId());
			VendorContractLineitems vendorContractLineitems = ((List<VendorContractLineitems>) vendorContractsLineItemsService.selectObjectQuery(params)).get(0);
			
			DecimalFormat decimalFormat = new DecimalFormat("###.##");
			Double rate = 0.0;
			if(finalUnitOfMeasurement.getUomId() == vendorContractLineitems.getUomId())
				rate = Double.parseDouble(decimalFormat.format(vendorContractLineitems.getRate()));
			else
				rate = Double.parseDouble(decimalFormat.format((vendorContractLineitems.getRate() * finalUnitOfMeasurement.getUomConversion())/vendorContractLineitems.getUnitOfMeasurement().getUomConversion()));
			
			//Ledger debit
			StoreItemLedger storeItemLedger = updateStoreItemLegder(storeIssue.getStoreId(), storeIssue.getImId(), storeIssue.getUomId(), rate, storeIssue.getLedgerUpdateDt(), "Debit", storeIssue.getImQuantity());
			
			StoreItemLedger newStoreItemLedger = storeItemLedgerService.find(storeItemLedger.getSilId());
			//Store categories update
			updateCategories(newStoreItemLedger.getStoreId(), newStoreItemLedger.getStoreMaster().getStoreName(), newStoreItemLedger.getImId(), newStoreItemLedger.getItemMaster().getImName());
			
			if((validationUtil.customValidateModelObject(storeIssue, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeIssue, null, locale);
			if(operation.equals("create")){				
				jcMaterialsService.updateIssue(storeIssue.getJcId(),storeIssue.getImId(),storeIssue.getStoreId(),storeIssue.getImQuantity(),storeIssue.getUomId(),"Issued");
				storeIssueService.save(storeIssue);				
			}
			else{		
				jcMaterialsService.updateIssue(storeIssue.getJcId(),storeIssue.getImId(),storeIssue.getStoreId(),storeIssue.getImQuantity(),storeIssue.getUomId(),"Issued");
				storeIssueService.update(storeIssue);
			}
			sessionStatus.setComplete();
			return storeIssue;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeIssue/getJobNosSIFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getJobNosSIFilterUrl() {
		List<StoreIssue> storeIssuesList = storeIssueService.findAllStoreIssues();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreIssue> iterator = storeIssuesList.iterator(); iterator.hasNext();){
			StoreIssue storeIssue = (StoreIssue) iterator.next();
			map = new HashMap<String, Object>();
			map.put("jobNo", storeIssue.getJcMaterials().getJobCards().getJobNo());
			map.put("jobName", storeIssue.getJcMaterials().getJobCards().getJobName());
			map.put("jobGroup", storeIssue.getJcMaterials().getJobCards().getJobGroup());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeIssue/getJobCardsSIComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> getJobCardsComboBoxUrl() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<?> jobCardsList = jobCardsService.getRequiredCardsAndMaterials("Issue");
		
		for (Iterator<?> i = jobCardsList.iterator(); i.hasNext();){
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("jcId", (Integer)values[9]);
			map.put("jobNo", (String)values[1]);
			map.put("jobName", (String)values[2]);
			map.put("jobQuantity", (String)values[10]);
			map.put("uom", (String)values[11]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeIssue/getStoreNamesSIFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getStoreNamesSIFilterUrl() {	
		List<StoreIssue> storeIssuesList = storeIssueService.findAllStoreIssues();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreIssue> iterator = storeIssuesList.iterator(); iterator.hasNext();)
		{
			StoreIssue storeIssue = (StoreIssue) iterator.next();
			map = new HashMap<String, Object>();
			map.put("storeName", storeIssue.getStoreMaster().getStoreName());
			map.put("storeLocation", storeIssue.getStoreMaster().getStoreLocation());
			result.add(map);
		}
		return result;
	}	
	
	@RequestMapping(value = "/storeIssue/getStoreMasterSIComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> getStoreMasterSIComboBoxUrl(){	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<?> jobCardsList = jobCardsService.getRequiredCardsAndMaterials("Issue");
		
		for (Iterator<?> i = jobCardsList.iterator(); i.hasNext();){
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("jcId", (Integer)values[9]);
			map.put("storeId", (Integer)values[3]);
			map.put("storeName", (String)values[4]);
			map.put("storeLocation", (String)values[5]);
			result.add(map);
		}
		return result;
	} 
	
	
	@RequestMapping(value = "/storeIssue/getImNamesSIFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getImNamesFilterUrl() {
		List<StoreIssue> storeIssuesList = storeIssueService.findAllStoreIssues();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreIssue> iterator = storeIssuesList.iterator(); iterator.hasNext();){
			StoreIssue storeIssue = (StoreIssue) iterator.next();
			map = new HashMap<String, Object>();
			map.put("imName", storeIssue.getItemMaster().getImName());
			map.put("imType", storeIssue.getItemMaster().getImType());
			map.put("imGroup", storeIssue.getItemMaster().getImGroup());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeIssue/getItemsSIComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> getItemsComboBoxUrl() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<?> jobCardsList = jobCardsService.getRequiredCardsAndMaterials("Issue");
		
		for (Iterator<?> i = jobCardsList.iterator(); i.hasNext();){
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("storeId", (Integer)values[3]);
			map.put("imId", (Integer)values[6]);
			map.put("imName", (String)values[7]);
			map.put("imType", (String)values[8]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeIssue/getUomSIFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getUomFilterUrl() {
		List<StoreIssue> storeIssuesList = storeIssueService.findAllStoreIssues();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreIssue> iterator = storeIssuesList.iterator(); iterator.hasNext();){
			StoreIssue storeIssue = (StoreIssue) iterator.next();
			map = new HashMap<String, Object>();
			map.put("uom", storeIssue.getUnitOfMeasurement().getUom());
			map.put("code", storeIssue.getUnitOfMeasurement().getCode());
			map.put("baseUom", storeIssue.getUnitOfMeasurement().getBaseUom());
			map.put("imName", storeIssue.getItemMaster().getImName());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeIssue/getUnitOfMeasurementSIComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<UnitOfMeasurement> getUoMComboBoxUrl() 
	{	
		return uomService.findAll();
	} 
	
	@RequestMapping(value = "/storeIssue/getContractNamesSIFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getContractNamesSIFilterUrl() {
		List<StoreIssue> storeIssuesList = storeIssueService.findAllStoreIssues();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreIssue> iterator = storeIssuesList.iterator(); iterator.hasNext();){
			StoreIssue storeIssue = (StoreIssue) iterator.next();
			map = new HashMap<String, Object>();
			map.put("contractName", storeIssue.getVendorContracts().getContractName());
			map.put("contractNo", storeIssue.getVendorContracts().getContractNo());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeIssue/getVendorContractSIComboBoxUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<?> getVendorContractSIComboBoxUrl() {
		List<?> contractsList = storeGoodsReceiptService.getRequiredContractDetails();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = contractsList.iterator(); i.hasNext();){
			final Object[] values = (Object[]) i.next();
		
			map = new HashMap<String, Object>();
			map.put("storeId", (Integer)values[0]);
			map.put("vcId", (Integer)values[1]);
			map.put("contractName", (String)values[2]);
			map.put("contractNo", (String)values[3]);
			//map.put("imId", (Integer)values[4]);
			result.add(map);
		}
		return result;
	} 
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Item Transfers (Movements) --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreItemTransfer", method = RequestMethod.GET)
	public String indexStoreItemTransfer(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		
		breadCrumbService.addNode("Manage Store Item Transfers", 2, request);
		
		//model.addAttribute("stmStatus", commonController.populateCategories("values.project.status", locale));
		
		return "inventory/storeItemTransfer";
	}
	
	@RequestMapping(value = "/storeMovement/read", method = RequestMethod.POST)
	public @ResponseBody Object readStoreMovement(final Locale locale) {
		try
		{
			return storeMovementService.getAllStoreMovementsRequiredFields();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeMovement/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStoreMovement(@PathVariable String operation, @ModelAttribute("storeMovement") StoreMovement storeMovement, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			DecimalFormat decimalFormat = new DecimalFormat("###.##");
			
			if((validationUtil.customValidateModelObject(storeMovement, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeMovement, null, locale);
			
			Double finalQuantityInTermsOfBaseUom  = 0.0;
			UnitOfMeasurement finalUnitOfMeasurement = uomService.find(storeMovement.getUomId());
			
			if(finalUnitOfMeasurement.getBaseUom().equalsIgnoreCase("No"))
				finalQuantityInTermsOfBaseUom = Double.parseDouble(decimalFormat.format(storeMovement.getItemQuantity() * finalUnitOfMeasurement.getUomConversion()));
			else
				finalQuantityInTermsOfBaseUom  = storeMovement.getItemQuantity();
				
			List<?> storeGoodsReceiptList = storeGoodsReceiptService.getRequiredStoreFields(storeMovement.getVcId(), storeMovement.getImId());
			
			for (Iterator<?> i = storeGoodsReceiptList.iterator(); i.hasNext();){
				final Object[] values = (Object[]) i.next();

				StoreGoodsReceiptItems oldStoreGoodsReceiptItems = storeGoodsReceiptItemsService.find((Integer)values[3]);
				StoreGoodsReceipt oldStoreGoodsReceipt = storeGoodsReceiptService.find(oldStoreGoodsReceiptItems.getStgrId());
				
				//if base uom
				if(((String)values[0]).equalsIgnoreCase("Yes"))
				{
					Double updatingQuantity = 0.0;
					if(finalQuantityInTermsOfBaseUom > (double)values[2])
						updatingQuantity = (double)values[2];
					else
						updatingQuantity = finalQuantityInTermsOfBaseUom;
					
					//add new store good receipt item with respect to other store with type store transfer
					Map<String, Object> map = updateStoreItems(storeMovement, finalUnitOfMeasurement.getUomConversion(), oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, updatingQuantity);
					StoreGoodsReceiptItems newStoreGoodsReceiptItems = (StoreGoodsReceiptItems) map.get("child");
					StoreGoodsReceipt newStoreGoodsReceipt = (StoreGoodsReceipt) map.get("parent");
					
					//ledger credit from fromstore
					//updateStoreItemLegder(oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, "Debit", updatingQuantity);
					updateStoreItemLegder(oldStoreGoodsReceipt.getStoreId(), oldStoreGoodsReceiptItems.getImId(), oldStoreGoodsReceiptItems.getUomId(), oldStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Debit", updatingQuantity);
					
					oldStoreGoodsReceipt.setLedgerUpdateDt(newStoreGoodsReceipt.getLedgerUpdateDt());
					storeGoodsReceiptService.updateLedgerDate(oldStoreGoodsReceipt.getStgrId(), newStoreGoodsReceipt.getLedgerUpdateDt());
					
					//ledger debit to tostore
					//updateStoreItemLegder(newStoreGoodsReceiptItems, newStoreGoodsReceipt, "Credit", (updatingQuantity / finalUnitOfMeasurement.getUomConversion()));
					StoreItemLedger storeItemLedger = updateStoreItemLegder(newStoreGoodsReceipt.getStoreId(), newStoreGoodsReceiptItems.getImId(), newStoreGoodsReceiptItems.getUomId(), newStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Credit", Double.parseDouble(decimalFormat.format((updatingQuantity / finalUnitOfMeasurement.getUomConversion()))));
					
					StoreItemLedger newStoreItemLedger = storeItemLedgerService.find(storeItemLedger.getSilId());
					//Store categories update
					updateCategories(newStoreItemLedger.getStoreId(), newStoreItemLedger.getStoreMaster().getStoreName(), newStoreItemLedger.getImId(), newStoreItemLedger.getItemMaster().getImName());
					
					//insert new adjustment (credit wrt to old item)
					//updateStoreAdjustments(oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, "Debit", updatingQuantity);
					//updateStoreItemLegder(oldStoreGoodsReceipt.getStoreId(), oldStoreGoodsReceiptItems.getImId(), oldStoreGoodsReceiptItems.getUomId(), oldStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Debit", updatingQuantity);
					updateStoreAdjustments(oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, "Debit", Double.parseDouble(decimalFormat.format(updatingQuantity)), "Store Transfer");
					
					//insert new adjustment (debit wrt to new item)
					updateStoreAdjustments(newStoreGoodsReceiptItems, newStoreGoodsReceipt, "Credit", Double.parseDouble(decimalFormat.format(updatingQuantity / finalUnitOfMeasurement.getUomConversion())), "Store Transfer");
					
					//add new entry in store movement with partno, and other extra details
					updateStoreMovement(storeMovement, oldStoreGoodsReceiptItems, Double.parseDouble(decimalFormat.format(updatingQuantity / finalUnitOfMeasurement.getUomConversion())));
					
					if(finalQuantityInTermsOfBaseUom > updatingQuantity)
					{
						//delete from old store good receipt item
						/*storeGoodsReceiptItemsService.delete(oldStoreGoodsReceiptItems.getSgriId());*/
						
						//reduce final quantity
						finalQuantityInTermsOfBaseUom = finalQuantityInTermsOfBaseUom - updatingQuantity;
					}
					else 
					{
						
						finalQuantityInTermsOfBaseUom = 0.0;
					}
					
				}
				else
				{
					Double dummyQuantityInTermsOfBaseUom = Double.parseDouble(decimalFormat.format((double)values[2] * (double)values[1])) ;
					
					Double updatingQuantity = 0.0;
					if(finalQuantityInTermsOfBaseUom > dummyQuantityInTermsOfBaseUom)
						updatingQuantity = dummyQuantityInTermsOfBaseUom;
					else
						updatingQuantity = finalQuantityInTermsOfBaseUom;
					
					//add new store good receipt item with respect to other store with type store transfer
					Map<String, Object> map = updateStoreItems(storeMovement, finalUnitOfMeasurement.getUomConversion(), oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, updatingQuantity);
					StoreGoodsReceiptItems newStoreGoodsReceiptItems = (StoreGoodsReceiptItems) map.get("child");
					StoreGoodsReceipt newStoreGoodsReceipt = (StoreGoodsReceipt) map.get("parent");
					
					//ledger credit from fromstore
					//updateStoreItemLegder(oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, "Debit", (updatingQuantity / oldStoreGoodsReceiptItems.getUnitOfMeasurement().getUomConversion()));
					updateStoreItemLegder(oldStoreGoodsReceipt.getStoreId(), oldStoreGoodsReceiptItems.getImId(), oldStoreGoodsReceiptItems.getUomId(), oldStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Debit", Double.parseDouble(decimalFormat.format(updatingQuantity / oldStoreGoodsReceiptItems.getUnitOfMeasurement().getUomConversion())));
					oldStoreGoodsReceipt.setLedgerUpdateDt(newStoreGoodsReceipt.getLedgerUpdateDt());
					storeGoodsReceiptService.updateLedgerDate(oldStoreGoodsReceipt.getStgrId(), newStoreGoodsReceipt.getLedgerUpdateDt());
					
					//ledger debit to tostore
					//updateStoreItemLegder(newStoreGoodsReceiptItems, newStoreGoodsReceipt, "Credit", (updatingQuantity / finalUnitOfMeasurement.getUomConversion()));
					StoreItemLedger storeItemLedger = updateStoreItemLegder(newStoreGoodsReceipt.getStoreId(), newStoreGoodsReceiptItems.getImId(), newStoreGoodsReceiptItems.getUomId(), newStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Credit", Double.parseDouble(decimalFormat.format(updatingQuantity / finalUnitOfMeasurement.getUomConversion())));
					
					StoreItemLedger newStoreItemLedger = storeItemLedgerService.find(storeItemLedger.getSilId());
					//Store categories update
					updateCategories(newStoreItemLedger.getStoreId(), newStoreItemLedger.getStoreMaster().getStoreName(), newStoreItemLedger.getImId(), newStoreItemLedger.getItemMaster().getImName());
					
					//insert new adjustment (credit wrt to old item)
					updateStoreAdjustments(oldStoreGoodsReceiptItems, oldStoreGoodsReceipt, "Debit", Double.parseDouble(decimalFormat.format(updatingQuantity / (double)values[1])), "Store Transfer");
					
					//insert new adjustment (debit wrt to new item)
					updateStoreAdjustments(newStoreGoodsReceiptItems, newStoreGoodsReceipt, "Credit", Double.parseDouble(decimalFormat.format(updatingQuantity / finalUnitOfMeasurement.getUomConversion())), "Store Transfer");
					
					//add new entry in store movement with partno, and other extra details
					updateStoreMovement(storeMovement, oldStoreGoodsReceiptItems, Double.parseDouble(decimalFormat.format(updatingQuantity / finalUnitOfMeasurement.getUomConversion())));
					
					if(finalQuantityInTermsOfBaseUom > updatingQuantity)
					{
						//delete from old store good receipt item
						/*storeGoodsReceiptItemsService.delete(oldStoreGoodsReceiptItems.getSgriId());*/
						
						//reduce final quantity
						finalQuantityInTermsOfBaseUom = Double.parseDouble(decimalFormat.format(finalQuantityInTermsOfBaseUom - updatingQuantity));
					}
					else 
					{
						
						finalQuantityInTermsOfBaseUom = 0.0;
					}
				}
				
				if(finalQuantityInTermsOfBaseUom == 0.0)
					break;
					
			}
			
			
			
			sessionStatus.setComplete();
			return storeMovement;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeMovement/stmStatus/{stmId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void storeMovementStatus(@PathVariable int stmId, HttpServletResponse response)
	{		
		//status
		storeMovementService.setStoreMovementStatus(stmId, response);
		
		//Ledger 
		StoreMovement currentStoreMovement = storeMovementService.find(stmId);
		UnitOfMeasurement currentUnitOfMeasurement = uomService.find(currentStoreMovement.getUomId());
		
		//from store update
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("storeId", currentStoreMovement.getFromStoreId());
		params1.put("imId", currentStoreMovement.getImId());
		List<StoreItemLedger> fromStoreItemLedgers = (List<StoreItemLedger>) storeItemLedgerService.selectObjectQuery(params1);
		
		StoreItemLedger fromStoreItemLedger = fromStoreItemLedgers.get(0);
		
		if(currentUnitOfMeasurement.getBaseUom().equalsIgnoreCase("Yes")){
			fromStoreItemLedger.setImBalance((fromStoreItemLedger.getImBalance()) - (currentStoreMovement.getItemQuantity()));
		}
		else{
			Map<String, Object> paramNew = new HashMap<String, Object>();
			paramNew.put("baseUom", "Yes");
			paramNew.put("imId", currentStoreMovement.getImId());
			UnitOfMeasurement unitOfMeasurementBaseUOM = (UnitOfMeasurement) uomService.selectObjectQuery(paramNew).get(0);
			fromStoreItemLedger.setImUom(unitOfMeasurementBaseUOM.getUomId());
			double quantity = (currentStoreMovement.getItemQuantity() * (currentUnitOfMeasurement.getUomConversion()));
			fromStoreItemLedger.setImBalance(fromStoreItemLedger.getImBalance() - quantity);
		}
		
		fromStoreItemLedger.setSilDt(new Timestamp(new Date().getTime()));
		fromStoreItemLedger.setStoreEntryFrom("Adjustment");
		storeItemLedgerService.update(fromStoreItemLedger);
		
		//to store update
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("storeId", currentStoreMovement.getToStoreId());
		params2.put("imId", currentStoreMovement.getImId());
		List<StoreItemLedger> toStoreItemLedgers = (List<StoreItemLedger>) storeItemLedgerService.selectObjectQuery(params2);
		
		StoreItemLedger storeItemLedger = new StoreItemLedger();
		
		if((toStoreItemLedgers.size()) == 0){
			storeItemLedger.setSilDt(new Timestamp(new Date().getTime()));
			storeItemLedger.setStoreEntryFrom("New");
			storeItemLedger.setStoreId(currentStoreMovement.getToStoreId());
			storeItemLedger.setImId(currentStoreMovement.getImId());
			
			if(currentUnitOfMeasurement.getBaseUom().equalsIgnoreCase("Yes")){
				storeItemLedger.setImUom(currentStoreMovement.getUomId());
				storeItemLedger.setImBalance(currentStoreMovement.getItemQuantity());
			}
			else{
				Map<String, Object> paramNew = new HashMap<String, Object>();
				paramNew.put("baseUom", "Yes");
				paramNew.put("imId", currentStoreMovement.getImId());
				UnitOfMeasurement unitOfMeasurementBaseUOM = (UnitOfMeasurement) uomService.selectObjectQuery(paramNew).get(0);
				storeItemLedger.setImUom(unitOfMeasurementBaseUOM.getUomId());
				double quantity = (currentStoreMovement.getItemQuantity() * (currentUnitOfMeasurement.getUomConversion()));
				storeItemLedger.setImBalance(quantity);
			}
			
			storeItemLedgerService.save(storeItemLedger);
		}
		else
		{
			storeItemLedger = toStoreItemLedgers.get(0);
			
			storeItemLedger.setSilDt(new Timestamp(new Date().getTime()));
			storeItemLedger.setStoreEntryFrom("Adjustment");
			
			if(currentUnitOfMeasurement.getBaseUom().equalsIgnoreCase("Yes"))
				storeItemLedger.setImBalance((storeItemLedger.getImBalance()) + (currentStoreMovement.getItemQuantity()));
			else{
				Map<String, Object> paramNew = new HashMap<String, Object>();
				paramNew.put("baseUom", "Yes");
				paramNew.put("imId", currentStoreMovement.getImId());
				UnitOfMeasurement unitOfMeasurementBaseUOM = (UnitOfMeasurement) uomService.selectObjectQuery(paramNew).get(0);
				storeItemLedger.setImUom(unitOfMeasurementBaseUOM.getUomId());
				double quantity = (currentStoreMovement.getItemQuantity() * (currentUnitOfMeasurement.getUomConversion()));
				storeItemLedger.setImBalance(storeItemLedger.getImBalance() + quantity);
			}
			
			storeItemLedgerService.update(storeItemLedger);
		}
	}
	
	@RequestMapping(value = "/storeMovement/checkIsStoreItemLedgerEmpty", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String checkIsStoreItemLedgerEmpty(){		
		List<StoreItemLedger> storeItemLedgersList = storeItemLedgerService.findAllStoreItemLedgers();
		long storeMastersCount = storeMasterService.countAll(null);
		
		if(storeItemLedgersList.size() == 0)
			return "Error: Ledger is empty";
		else if(storeMastersCount == 2)
			return "No other store to transfer";
		else if(storeItemLedgersList.size() > 0)
		{
			boolean quantityCheck = false;
			for (Iterator<StoreItemLedger> iterator = storeItemLedgersList.iterator(); iterator.hasNext();)
			{
				StoreItemLedger storeItemLedger = (StoreItemLedger) iterator.next();
				if(storeItemLedger.getImBalance() > 0)
					quantityCheck = true;
			}
			if(quantityCheck == false)
				return "Error: Ledger having stores but with empty item quantities";
			return "success";
		}
		else
			return "success";
	}
	
	@RequestMapping(value = "/storeMovement/getToStoreMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getToStoreMasterComboBoxUrl() {	
		List<StoreMaster> storesChildList = storeItemLedgerService.findRequiredAllStoresFromItemLedger();
		
		List<Map<String, Object>> storesNewList = new ArrayList<Map<String, Object>>();
		Map<String, Object> storesMap = null;
		List<StoreMaster> storesList = storeMasterService.getAllStoresRequiredFields();
		
		if(storesChildList.size() == 1)
		{
			for (Iterator<StoreMaster> iterator = storesList.iterator(); iterator.hasNext();){
				StoreMaster storeMaster = (StoreMaster) iterator.next();
				
				if(storesChildList.get(0).getStoreId() != storeMaster.getStoreId()) {
					storesMap = new HashMap<String, Object>();
					storesMap.put("toStoreId", storeMaster.getStoreId());
					storesMap.put("storeName", storeMaster.getStoreName());
					storesMap.put("storeLocation", storeMaster.getStoreLocation());
					
					storesNewList.add(storesMap);
				}
			}
		}
		else
		{
			for (Iterator<StoreMaster> iterator = storesList.iterator(); iterator.hasNext();){
				storesMap = new HashMap<String, Object>();
				StoreMaster storeMaster = (StoreMaster) iterator.next();
				
				storesMap.put("toStoreId", storeMaster.getStoreId());
				storesMap.put("storeName", storeMaster.getStoreName());
				storesMap.put("storeLocation", storeMaster.getStoreLocation());
				
				storesNewList.add(storesMap);
			}
		}
		return storesNewList;
	} 
	
	@RequestMapping(value = "/storeMovement/getToStoreNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getToStoreNamesFilterUrl() {	
		List<StoreMovement> storesList = storeMovementService.getAllStoreMovementsRequiredFields();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreMovement> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreMovement storeMovement = (StoreMovement) iterator.next();
			map = new HashMap<String, Object>();
			map.put("toStoreName", storeMovement.getToStoreName());
			map.put("storeLocation", storeMovement.getToStoreLocation());
			result.add(map);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/storeMovement/getFromStoreMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getFromStoreMasterComboBoxUrl() {	
		List<Map<String, Object>> storesNewList = new ArrayList<Map<String, Object>>();
		Map<String, Object> storesMap = null;
		List<StoreMaster> storesMasterList = storeMasterService.getAllStoresRequiredFields();
		List<StoreMaster> storesChildList = storeItemLedgerService.findRequiredAllStoresFromItemLedger();
		for (Iterator<StoreMaster> iterator = storesMasterList.iterator(); iterator.hasNext();){
			StoreMaster storeMaster = (StoreMaster) iterator.next();
			
			for (Iterator<StoreMaster> iterator1 = storesChildList.iterator(); iterator1.hasNext();){
				StoreMaster storeItemLedgerstoreChild = (StoreMaster) iterator1.next();
				
				if(storeMaster.getStoreId() != storeItemLedgerstoreChild.getStoreId()){
					storesMap = new HashMap<String, Object>();
					storesMap.put("toStoreId", storeMaster.getStoreId());
						
					storesMap.put("fromStoreId", storeItemLedgerstoreChild.getStoreId());
					storesMap.put("storeName", storeItemLedgerstoreChild.getStoreName());
					storesMap.put("storeLocation", storeItemLedgerstoreChild.getStoreLocation());	
						
					storesNewList.add(storesMap);
				}
			}
		}
		return storesNewList;
	} 
	
	@RequestMapping(value = "/storeMovement/getFromStoreNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getFromStoreNamesFilterUrl() 
	{	
		List<StoreMovement> storesList = storeMovementService.getAllStoreMovementsRequiredFields();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreMovement> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreMovement storeMovement = (StoreMovement) iterator.next();
			map = new HashMap<String, Object>();
			map.put("fromStoreName", storeMovement.getFromStoreName());
			map.put("storeLocation", storeMovement.getFromStoreLocation());
			result.add(map);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/storeMovement/getContractNamesSMFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getContractNamesSMFilterUrl() 
	{
		List<StoreGoodsReceipt> storesList = storeGoodsReceiptService.getRequiredStoreGoodsReceipts();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreGoodsReceipt> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreGoodsReceipt storeGoodsReceipt = (StoreGoodsReceipt) iterator.next();
			map = new HashMap<String, Object>();
			map.put("contractName", storeGoodsReceipt.getContractName());
			map.put("contractNo", storeGoodsReceipt.getContractNo());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeMovement/getVendorContractSMComboBoxUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<?> getVendorContractSMComboBoxUrl() 
	{
		List<?> contractsList = storeGoodsReceiptService.getRequiredContractDetails();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = contractsList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			
			map = new HashMap<String, Object>();
			map.put("fromStoreId", (Integer)values[0]);
			map.put("vcId", (Integer)values[1]);
			map.put("contractName", (String)values[2]);
			map.put("contractNo", (String)values[3]);
			//map.put("imId", (Integer)values[4]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeMovement/getImNamesSMFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getImNamesSMFilterUrl() {
		List<StoreMovement> storesList = storeMovementService.getAllStoreMovementsRequiredFields();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreMovement> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreMovement storeMovement = (StoreMovement) iterator.next();
			map = new HashMap<String, Object>();
			map.put("imName", storeMovement.getImName());
			map.put("imType", storeMovement.getImType());
			map.put("imGroup", storeMovement.getImGroup());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeMovement/getItemsSMComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getItemsSMComboBoxUrl() {
		List<?> storeGoodsReceiptList = storeGoodsReceiptService.getAllRequiredItemsFieldsForStoreMovement();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
		for (Iterator<?> i = storeGoodsReceiptList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("vcId", (Integer)values[0]);
			map.put("imId", (Integer)values[1]);
			map.put("imName", (String)values[2]);
			map.put("imType", (String)values[3]);
			map.put("imGroup", (String)values[4]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeMovement/getUomSMFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getUomSMFilterUrl() {
		List<StoreMovement> storesList = storeMovementService.getAllStoreMovementsRequiredFields();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreMovement> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreMovement storeMovement = (StoreMovement) iterator.next();
			map = new HashMap<String, Object>();
			map.put("uom", storeMovement.getUom());
			map.put("code", storeMovement.getCode());
			map.put("baseUom", storeMovement.getBaseUom());
			map.put("imName", storeMovement.getImName());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeIssue/getUnitOfMeasurementSMComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<UnitOfMeasurement> getUnitOfMeasurementComboBoxUrl() 
	{	
		return uomService.findAll();
	} 
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Outward --------------------------------------------
	 */
	
	@RequestMapping(value = "/stockoutward", method = RequestMethod.GET)
	public String stockoutward(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Store Goods Outward", 2, request);
		return "inventory/stockoutward";
	}
	
	@RequestMapping(value = "/stockoutward/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readstockoutward(final Locale locale) {
		return storeOutwardService.readAll();			 
	}
	
	@RequestMapping(value = "/stockoutward/storeNamesoutwardFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> storeNamesoutwardFilterUrl() {	
		List<StoreOutward> list = storeOutwardService.findAll();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[7]);		
		}
		return result;
	}
	@RequestMapping(value = "/stockoutward/itemNamesoutwardFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> itemNamesoutwardFilterUrl() {	
		List<StoreOutward> list = storeOutwardService.findAll();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[5]);		
		}
		return result;		
		
	}
	@RequestMapping(value = "/stockoutward/uomNamesoutwardFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> uomNamesoutwardFilterUrl() {	
		List<StoreOutward> list = storeOutwardService.findAll();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[15]);		
		}
		return result;
		
	}
	@RequestMapping(value = "/stockoutward/quantityoutwardFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> quantityoutwardFilterUrl() {	
		List<StoreOutward> list = storeOutwardService.findAll();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add(String.valueOf((Double)values[16]));		
		}
		return result;
		
	}
	@RequestMapping(value = "/stockoutward/returnedtoNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> returnedtoNamesFilterUrl() {	
		List<StoreOutward> list = storeOutwardService.findAll();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[10]+ " "+(String)values[11]);		
		}
		return result;
		
	}
	@RequestMapping(value = "/stockoutward/returnedbyNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<String> returnedbyNamesFilterUrl() {	
		List<StoreOutward> list = storeOutwardService.findAll();
		Set<String> result = new HashSet<String>();		
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			result.add((String)values[8]+" " +(String)values[9]);		
		}
		return result;
		
	}
	
	@RequestMapping(value = "/stockoutward/storeNameCombo", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> storeNameCombo() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<StoreItemLedger> storeList = storeItemLedgerService.findAllStoreItemLedgers();
		
		for (StoreItemLedger storeItem:storeList)
		{
			
			map = new HashMap<String, Object>();
			map.put("storeName", storeItem.getStoreName());
			map.put("storeId", storeItem.getStoreId());
			map.put("storeLocation", storeItem.getStoreLocation());
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/stockoutward/itmNameCombo", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> itmNameCombo() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<StoreItemLedger> storeList = storeItemLedgerService.findAllStoreItemLedgers();
		
		for (StoreItemLedger storeItem:storeList)
		{
			
			map = new HashMap<String, Object>();
			map.put("imName", storeItem.getImName());
			map.put("imType", storeItem.getImType());			
			map.put("imId", storeItem.getImId());
			map.put("storeId", storeItem.getStoreId());
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/stockoutward/uomNameCombo", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> uomNameCombo() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<StoreItemLedger> storeList = storeItemLedgerService.findAllStoreItemLedgers();
		
		for (StoreItemLedger storeItem:storeList)
		{
			
			map = new HashMap<String, Object>();
			map.put("uom", storeItem.getUom());
			map.put("uomId", storeItem.getImUom());	
			map.put("imId", storeItem.getImId());
			result.add(map);
		}
		return result;
	} 
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/stockoutward/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifystockoutward(@PathVariable String operation,@RequestBody Map<String, Object> map,HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		if(operation.equals("create"))
		{
			DecimalFormat decimalFormat = new DecimalFormat("###.##");			
			StoreOutward stockoutward=storeOutwardService.setParameters(map,userName);
			storeOutwardService.save(stockoutward);
			return stockoutward;
		}
		else{
			StoreOutward stockoutward=storeOutwardService.setParametersforupdate(map,userName);
			storeOutwardService.update(stockoutward);
			return stockoutward;
		}	
	}
	
	@RequestMapping(value = "/stockoutward/deleteItemOutward", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object deleteItemOutward(@RequestBody Map<String, Object> map,@ModelAttribute("storeOutward") StoreOutward storeOutward)
	{
		logger.info("=========StoreOutwardId:"+Integer.parseInt(map.get("storeoutwardId").toString()));
		storeOutwardService.delete(Integer.parseInt(map.get("storeoutwardId").toString()));
		return storeOutward;
	}
	
	@RequestMapping(value = "/stockoutward/outwardStatus/{psId}/{operation}", method = RequestMethod.POST)
	public void ParkingSlotStatus(@PathVariable int psId,@PathVariable String operation, HttpServletResponse response) {
		storeOutwardService.setoutStatus(psId, operation, response);
		StoreOutward stockoutward=storeOutwardService.find(psId);
		StoreMaster sm=storeMasterService.find(stockoutward.getStoreMasterId());
		ItemMaster im=itemMasterService.find(stockoutward.getItemMasterId());
		UnitOfMeasurement uom=uomService.find(stockoutward.getUomId());
		updateStoreItemLegder(sm.getStoreId(),im.getImId() ,uom.getUomId() , 0, new Timestamp(new Date().getTime()), "Debit",stockoutward.getItemReturnQuantity());
		
	}
	/**
	 * Use Case: ----------------------------------------- Manage Store Item Returns --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreGoodsReturns", method = RequestMethod.GET)
	public String indexStoreGoodsReturns(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Store Item Returns", 2, request);
		//model.addAttribute("person", new Person());
		
		//model.addAttribute("sgrStatus", commonController.populateCategories("values.project.status", locale));
		
		return "inventory/storeGoodsReturns";
	}
	
	@RequestMapping(value = "/storeGoodsReturns/read", method = RequestMethod.POST)
	public @ResponseBody Object readStoreGoodsReturns(final Locale locale) {
		try
		{
			List<?> list = storeGoodsReturnsService.findAllStoreGoodsReturns();
			return storeGoodsReturnsService.getAllStoreGoodsReturnsRequiredFields(list);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeGoodsReturns/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStoreGoodsReturns(@PathVariable String operation, @ModelAttribute("storeGoodsReturns") StoreGoodsReturns storeGoodsReturns, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			if((validationUtil.customValidateModelObject(storeGoodsReturns, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeGoodsReturns, null, locale);

			if(operation.equals("create"))
			{
				DecimalFormat decimalFormat = new DecimalFormat("###.##");

				//ledger credit to tostore
				updateStoreItemLegder(storeGoodsReturns.getStoreId(), storeGoodsReturns.getImId(), storeGoodsReturns.getUomId(), 0, new Timestamp(new Date().getTime()), "Credit", storeGoodsReturns.getItemReturnQuantity());
				
				//insert adjustment (debit)
				updateStoreAdjustments(storeGoodsReturns, storeGoodsReturns, "Credit", Double.parseDouble(decimalFormat.format(storeGoodsReturns.getItemReturnQuantity())), "Store Return");
				jcMaterialsService.updateIssue(storeGoodsReturns.getJcId(),storeGoodsReturns.getImId(),storeGoodsReturns.getStoreId(),storeGoodsReturns.getItemReturnQuantity(),storeGoodsReturns.getUomId(),"Returned");
				
				storeGoodsReturnsService.save(storeGoodsReturns);
			}
			else{
				jcMaterialsService.updateIssue(storeGoodsReturns.getJcId(),storeGoodsReturns.getImId(),storeGoodsReturns.getStoreId(),storeGoodsReturns.getItemReturnQuantity(),storeGoodsReturns.getUomId(),"Returned");
				storeGoodsReturnsService.update(storeGoodsReturns);
			}
			
			sessionStatus.setComplete();
			return storeGoodsReturns;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	@RequestMapping(value = "/storeGoodsReturns/sgrStatus/{sgrId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String storeGoodsReturnsStatus(@PathVariable int sgrId)
	{		
		return storeGoodsReturnsService.setStoreGoodsReturnStatus(sgrId);
		
	}
	
	@RequestMapping(value = "/storeGoodsReturns/getJobNosSGREFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getJobNosSGREFilterUrl() {
		List<?> list = storeGoodsReturnsService.findAllStoreGoodsReturns();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("jobNo",(String)values[22]);
			map.put("jobName", (String)values[23] );
			map.put("jobGroup", (String)values[24] );
			result.add(map);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/storeGoodsReturns/getJobCardsSGREComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> getJobCardsSGREComboBoxUrl() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<?> jobCardsList = jobCardsService.getRequiredCardsAndMaterials("Return");
		
		for (Iterator<?> i = jobCardsList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("jcId", (Integer)values[9]);
			map.put("jobNo", (String)values[1]);
			map.put("jobName", (String)values[2]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReturns/getSGRStoreNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getSGRStoreNamesFilterUrl() {	
		List<?> list = storeGoodsReturnsService.findAllStoreGoodsReturns();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("storeName", (String)values[12]);
			map.put("storeLocation", (String)values[25]);
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeGoodsReturns/getImNamesSGReturnFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getImNamesSGReturnFilterUrl() {
		List<?> list = storeGoodsReturnsService.findAllStoreGoodsReturns();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("imName", (String)values[13]);
			map.put("imType", (String)values[26]);
			map.put("imGroup", (String)values[27]);

			result.add(map);
		}
		return result;
	}

	@RequestMapping(value = "/storeGoodsReturns/getUomSGRFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getUomSGRFilterUrl() {
		List<?> list = storeGoodsReturnsService.findAllStoreGoodsReturns();
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = list.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("uom", (String)values[14]);
			map.put("code", (String)values[28]);
			map.put("baseUom", (String)values[29]);

			result.add(map);		
		}
		return result;
	}
	
	@RequestMapping(value = "/storeGoodsReturns/getSGRStoreMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> getSGRStoreMasterComboBoxUrl(){	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<?> jobCardsList = jobCardsService.getRequiredCardsAndMaterials("Return");
		
		for (Iterator<?> i = jobCardsList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("jcId", (Integer)values[9]);
			map.put("storeId", (Integer)values[3]);
			map.put("storeName", (String)values[4]);
			map.put("storeLocation", (String)values[5]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReturns/getItemsSGRComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody Set<Map<String, Object>> getImNamesSGRFilterUrl() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		
		List<?> jobCardsList = jobCardsService.getRequiredCardsAndMaterials("Return");
		
		for (Iterator<?> i = jobCardsList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("storeId", (Integer)values[3]);
			map.put("imId", (Integer)values[6]);
			map.put("imName", (String)values[7]);
			map.put("imType", (String)values[8]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeGoodsReturns/getUnitOfMeasurementSGRComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<UnitOfMeasurement> getUnitOfMeasurementSGRComboBoxUrl() 
	{	
		return uomService.findAll();
	}
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Adjustments --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreAdjustments", method = RequestMethod.GET)
	public String indexStoreAdjustments(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Store Adjustments", 2, request);
		//model.addAttribute("person", new Person());
		
		//model.addAttribute("saStatus", commonController.populateCategories("values.project.status", locale));
		
		return "inventory/storeAdjustments";
	}
	
	@RequestMapping(value = "/storeAdjustments/read", method = RequestMethod.POST)
	public @ResponseBody Object readStoreAdjustments(final Locale locale) {
		try
		{
			List<StoreAdjustments> list = storeAdjustmentsService.findAllStoreAdjustments();
			return storeAdjustmentsService.getAllStoreAdjustmentsRequiredFields(list);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeAdjustments/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStoreAdjustments(@PathVariable String operation, @ModelAttribute("storeAdjustments") StoreAdjustments storeAdjustments, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			if((validationUtil.customValidateModelObject(storeAdjustments, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeAdjustments, null, locale);
			
			if(storeAdjustments.getSaDate() instanceof java.util.Date)
				storeAdjustments.setSaDt(dateTimeCalender.getTimestampFromDateAndTime(storeAdjustments.getSaDate(), storeAdjustments.getSaTime()));
			storeAdjustments.setLedgerUpdateDt(new Timestamp(new Date().getTime()));
			
			if(operation.equals("create"))
			{	
				UnitOfMeasurement finalUnitOfMeasurement = uomService.find(storeAdjustments.getUomId());
				
				Map<String, Object> params = new HashMap<>();
				params.put("vcId", storeAdjustments.getVcId());
				params.put("imId", storeAdjustments.getImId());
				VendorContractLineitems vendorContractLineitems = ((List<VendorContractLineitems>) vendorContractsLineItemsService.selectObjectQuery(params)).get(0);
				
				DecimalFormat decimalFormat = new DecimalFormat("###.##");
				
				if(finalUnitOfMeasurement.getUomId() == vendorContractLineitems.getUomId())
					storeAdjustments.setRate(Double.parseDouble(decimalFormat.format(vendorContractLineitems.getRate())));
				else
					storeAdjustments.setRate(Double.parseDouble(decimalFormat.format((vendorContractLineitems.getRate() * finalUnitOfMeasurement.getUomConversion())/vendorContractLineitems.getUnitOfMeasurement().getUomConversion())));
				
				if(storeAdjustments.getContractStatus().equalsIgnoreCase("new"))
				{
					//add new store good receipt item with respect to other store with type store adjustment
					Map<String, Object> map = updateStoreItems(storeAdjustments, 1.0, null, null, storeAdjustments.getItemQuantity());
					StoreGoodsReceiptItems newStoreGoodsReceiptItems = (StoreGoodsReceiptItems) map.get("child");
					StoreGoodsReceipt newStoreGoodsReceipt = (StoreGoodsReceipt) map.get("parent");
					
					//ledger debit to tostore
					//updateStoreItemLegder(newStoreGoodsReceiptItems, newStoreGoodsReceipt, "Credit", storeAdjustments.getItemQuantity());
					StoreItemLedger storeItemLedger = updateStoreItemLegder(newStoreGoodsReceipt.getStoreId(), newStoreGoodsReceiptItems.getImId(), newStoreGoodsReceiptItems.getUomId(), newStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Credit", storeAdjustments.getItemQuantity());
					
					StoreItemLedger newStoreItemLedger = storeItemLedgerService.find(storeItemLedger.getSilId());
					//Store categories update
					updateCategories(newStoreItemLedger.getStoreId(), newStoreItemLedger.getStoreMaster().getStoreName(), newStoreItemLedger.getImId(), newStoreItemLedger.getItemMaster().getImName());
					
				}
				else
				{
					if(storeAdjustments.getItemQuantity() > 0)
					{
						//add new store good receipt item with respect to other store with type store adjustment
						Map<String, Object> map = updateStoreItems(storeAdjustments, 1.0, null, null, storeAdjustments.getItemQuantity());
						StoreGoodsReceiptItems newStoreGoodsReceiptItems = (StoreGoodsReceiptItems) map.get("child");
						StoreGoodsReceipt newStoreGoodsReceipt = (StoreGoodsReceipt) map.get("parent");
						
						//ledger debit to tostore
						//updateStoreItemLegder(newStoreGoodsReceiptItems, newStoreGoodsReceipt, "Credit", storeAdjustments.getItemQuantity());
						StoreItemLedger storeItemLedger = updateStoreItemLegder(newStoreGoodsReceipt.getStoreId(), newStoreGoodsReceiptItems.getImId(), newStoreGoodsReceiptItems.getUomId(), newStoreGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Credit", storeAdjustments.getItemQuantity());
						
						StoreItemLedger newStoreItemLedger = storeItemLedgerService.find(storeItemLedger.getSilId());
						//Store categories update
						updateCategories(newStoreItemLedger.getStoreId(), newStoreItemLedger.getStoreMaster().getStoreName(), newStoreItemLedger.getImId(), newStoreItemLedger.getItemMaster().getImName());
						
					}
					else
					{	
						StoreGoodsReceipt storeGoodsReceipt = new StoreGoodsReceipt();
						storeGoodsReceipt.setStoreId(storeAdjustments.getStoreId());
						
						StoreGoodsReceiptItems storeGoodsReceiptItems = new StoreGoodsReceiptItems();
						storeGoodsReceiptItems.setImId(storeAdjustments.getImId());
						storeGoodsReceiptItems.setUomId(storeAdjustments.getUomId());
						storeGoodsReceiptItems.setRate(storeAdjustments.getRate());
						storeGoodsReceiptItems.setLastUpdatedDt(new Timestamp(new Date().getTime()));
						
						//ledger credit to tostore
						//updateStoreItemLegder(storeGoodsReceiptItems, storeGoodsReceipt, "Debit", (-1) * storeAdjustments.getItemQuantity());
						updateStoreItemLegder(storeGoodsReceipt.getStoreId(), storeGoodsReceiptItems.getImId(), storeGoodsReceiptItems.getUomId(), storeGoodsReceiptItems.getRate(), new Timestamp(new Date().getTime()), "Debit", (-1) * storeAdjustments.getItemQuantity());
					}
				}
				
				storeAdjustmentsService.save(storeAdjustments);
			}
			else
				storeAdjustmentsService.update(storeAdjustments);
			
			sessionStatus.setComplete();
			return storeAdjustments;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeAdjustments/saStatus/{saId}", method = { RequestMethod.GET, RequestMethod.POST })
	public void storeAdjustmentStatus(@PathVariable int saId, HttpServletResponse response){		
		//status
		storeAdjustmentsService.setstoreAdjustmentStatus(saId, response);
		
		//Ledger 
		
	}
	@RequestMapping(value = "/storeAdjustments/getSAStoreNamesFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getSAStoreNamesFilterUrl() {	
		List<StoreAdjustments> storesList = storeAdjustmentsService.findAllStoreAdjustments();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreAdjustments> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreAdjustments storeAdjustments = (StoreAdjustments) iterator.next();
			map = new HashMap<String, Object>();
			map.put("storeName", storeAdjustments.getStoreMaster().getStoreName());
			map.put("storeLocation", storeAdjustments.getStoreMaster().getStoreLocation());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeAdjustments/getSAStoreMasterComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getSAStoreMasterComboBoxUrl() {	
		List<?> storesList = storeMasterService.getStoresBasedOnContractExistenceForAdjustment();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<?> i = storesList.iterator(); i.hasNext();)
		{
			final Object[] values = (Object[]) i.next();
			map = new HashMap<String, Object>();
			map.put("storeId", (Integer)values[0]);
			map.put("storeName", (String)values[1]);
			map.put("storeLocation", (String)values[2]);
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeAdjustments/getContractNamesSAFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getContractNamesSAFilterUrl() {
		List<StoreAdjustments> storesList = storeAdjustmentsService.findAllStoreAdjustments();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreAdjustments> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreAdjustments storeAdjustments = (StoreAdjustments) iterator.next();
			map = new HashMap<String, Object>();
			map.put("contractName", storeAdjustments.getVendorContracts().getContractName());
			map.put("contractNo", storeAdjustments.getVendorContracts().getContractNo());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeAdjustments/getVendorContractSAComboBoxUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<?> getVendorContractSAComboBoxUrl() {
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		List<VendorContracts> vendorContractsList = storeGoodsReceiptService.getAllRequiredVCWhosHasChildrenVCLIIncludingDuplicates();
		Map<String, Object> map = null;
		for (Iterator<VendorContracts> iterator = vendorContractsList.iterator(); iterator
				.hasNext();)
		{
			VendorContracts vendorContracts = (VendorContracts) iterator.next();
			map = new HashMap<String, Object>();
			map.put("storeId", vendorContracts.getStoreId());
			map.put("vcId", vendorContracts.getVcId());
			map.put("contractName", vendorContracts.getContractName());
			map.put("contractNo", vendorContracts.getContractNo());
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeAdjustments/getImNamesSAFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getImNamesSAFilterUrl() {
		List<StoreAdjustments> storesList = storeAdjustmentsService.findAllStoreAdjustments();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreAdjustments> iterator = storesList.iterator(); iterator.hasNext();)
		{
			StoreAdjustments storeAdjustments = (StoreAdjustments) iterator.next();
			map = new HashMap<String, Object>();
			map.put("imName", storeAdjustments.getItemMaster().getImName());
			map.put("imType", storeAdjustments.getItemMaster().getImType());
			map.put("imGroup", storeAdjustments.getItemMaster().getImGroup());
			result.add(map);
		}
		return result;
	}
	
	@RequestMapping(value = "/storeAdjustments/getItemsSAComboBoxUrl", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getItemsSAComboBoxUrl() 
	{
		List<VendorContractLineitems> storeItemLedgersList = vendorContractsLineItemsService.findAll();
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
		for (Iterator<VendorContractLineitems> iterator = storeItemLedgersList.iterator(); iterator
				.hasNext();)
		{
			VendorContractLineitems vendorContractLineitems = (VendorContractLineitems) iterator
					.next();
			map = new HashMap<String, Object>();
			map.put("vcId", vendorContractLineitems.getVcId());
			map.put("imId", vendorContractLineitems.getImId());
			map.put("imName", vendorContractLineitems.getItemMaster().getImName());
			map.put("imType", vendorContractLineitems.getItemMaster().getImType());
			map.put("imGroup", vendorContractLineitems.getItemMaster().getImGroup());
			map.put("uom", vendorContractLineitems.getUnitOfMeasurement().getUom());
			map.put("quantity", vendorContractLineitems.getQuantity());
			map.put("rate", vendorContractLineitems.getRate());
			result.add(map);
		}
		return result;
	} 
	
	@RequestMapping(value = "/storeAdjustments/getUomSAFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getUomSAFilterUrl() 
	{
		Set<Map<String, Object>> storesNewList = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		List<StoreAdjustments> storesList = storeAdjustmentsService.findAllStoreAdjustments();

		for (Iterator<StoreAdjustments> iterator1 = storesList.iterator(); iterator1.hasNext();)
		{
			StoreAdjustments storeAdjustments = (StoreAdjustments) iterator1.next();

			map = new HashMap<String, Object>();
			map.put("uom", storeAdjustments.getUnitOfMeasurement().getUom());
			map.put("code", storeAdjustments.getUnitOfMeasurement().getCode());
			map.put("baseUom", storeAdjustments.getUnitOfMeasurement().getBaseUom());
			map.put("imName", storeAdjustments.getItemMaster().getImName());
			
			storesNewList.add(map);
		}	
		return storesNewList;
	}
	
	@RequestMapping(value = "/storeAdjustments/getUnitOfMeasurementSAComboBoxUrl/{vcId}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getUnitOfMeasurementComboSABoxUrl(@PathVariable int vcId) 
	{	
		//return uomService.findAll();
		
		List<VendorContractLineitems> vcliList = vendorContractsLineItemsService.getAllVCLineItemsBasedOnVcId(vcId);
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		
		for (Iterator<VendorContractLineitems> iterator = vcliList.iterator(); iterator
				.hasNext();)
		{
			VendorContractLineitems vendorContractLineitems = (VendorContractLineitems) iterator
					.next();
			
			List<UnitOfMeasurement> uomList = uomService.findUomBasedOnItemId(vendorContractLineitems.getImId());
			
			for (Iterator<UnitOfMeasurement> iterator2 = uomList.iterator(); iterator2.hasNext();)
			{
				UnitOfMeasurement unitOfMeasurement = (UnitOfMeasurement) iterator2
						.next();
				
				map = new HashMap<String, Object>();
				map.put("imId", unitOfMeasurement.getImId());
				map.put("uomId", unitOfMeasurement.getUomId());
				map.put("uom", unitOfMeasurement.getUom());
				map.put("baseUom", unitOfMeasurement.getBaseUom());
				map.put("code", unitOfMeasurement.getCode());
				map.put("uomConversion", unitOfMeasurement.getUomConversion());
				
				if(unitOfMeasurement.getUomId() == vendorContractLineitems.getUomId())
				{
					map.put("quantity", vendorContractLineitems.getQuantity());
					map.put("rate", vendorContractLineitems.getRate());
				}
				else
				{
					DecimalFormat decimalFormat = new DecimalFormat("###.##");

					Double quantity = Double.parseDouble(decimalFormat.format((vendorContractLineitems.getQuantity() * unitOfMeasurement.getUomConversion())/vendorContractLineitems.getUnitOfMeasurement().getUomConversion()));
					Double rate = Double.parseDouble(decimalFormat.format((vendorContractLineitems.getRate() * unitOfMeasurement.getUomConversion())/vendorContractLineitems.getUnitOfMeasurement().getUomConversion()));

					map.put("quantity", quantity);
					map.put("rate", rate);
				}
				result.add(map);
			}
			
		}
		return result;
	} 

	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Item Ledger --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreItemLedger", method = RequestMethod.GET)
	public String indexStoreItemLedger(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Item Ledger", 2, request);
		//model.addAttribute("person", new Person());
		model.addAttribute("saStatus", commonController.populateCategories("values.project.status", locale));
		model.addAttribute("uom", commonController.populateCategories("uomId", "uom", "UnitOfMeasurement"));
		
		return "inventory/storeItemLedger";
	}
	
	@RequestMapping(value = "/storeItemLedger/read", method = RequestMethod.POST)
	public @ResponseBody Object readStoreItemLedger(final Locale locale) {
		try
		{
			return storeItemLedgerService.findAllStoreItemLedgers();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeItemLedger/getStoreNamesFilterUrlStoreLedger", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getStoreNamesFilterUrlStoreLedger() {	
		List<StoreItemLedger> storeItemLedgersList = storeItemLedgerService.findAllStoreItemLedgers();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreItemLedger> iterator = storeItemLedgersList.iterator(); iterator.hasNext();)
		{
			StoreItemLedger storeItemLedger = (StoreItemLedger) iterator.next();
			map = new HashMap<String, Object>();
			StoreMaster sm= storeMasterService.find(storeItemLedger.getStoreId());
			map.put("storeName", sm.getStoreName());
			map.put("storeLocation", sm.getStoreLocation());
			result.add(map);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/storeItemLedger/getImNamesFilterUrlStoreLedger", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getImTypesFilterUrlStoreLedger() {	
		List<StoreItemLedger> storeItemLedgersList = storeItemLedgerService.findAllStoreItemLedgers();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreItemLedger> iterator = storeItemLedgersList.iterator(); iterator.hasNext();)
		{
			StoreItemLedger storeItemLedger = (StoreItemLedger) iterator.next();
			map = new HashMap<String, Object>();
			ItemMaster im=itemMasterService.find(storeItemLedger.getImId());
			map.put("imName", im.getImName());
			map.put("imType", im.getImType());
			map.put("imGroup", im.getImGroup());
			result.add(map);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/storeItemLedger/getUomFilterUrlStoreLedger", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Set<Map<String, Object>> getUomFilterUrlStoreLedger() {	
		List<StoreItemLedger> storeItemLedgersList = storeItemLedgerService.findAllStoreItemLedgers();	
		Set<Map<String, Object>> result = new HashSet<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Iterator<StoreItemLedger> iterator = storeItemLedgersList.iterator(); iterator.hasNext();)
		{
			StoreItemLedger storeItemLedger = (StoreItemLedger) iterator.next();
			map = new HashMap<String, Object>();
			UnitOfMeasurement uom=uomService.find(storeItemLedger.getImUom());
			map.put("uom", uom.getUom());
			map.put("code", uom.getCode());
			map.put("baseUom", uom.getBaseUom());
			result.add(map);
		}
		
		return result;
	}
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Item Ledger Details --------------------------------------------
	 */
	
	@RequestMapping(value = "/storeItemLedgerDetails/read/{silId}", method = RequestMethod.POST)
	public @ResponseBody Object readStoreItemLedgerDetails(@PathVariable int silId, final Locale locale) {
		try
		{
			return storeItemLedgerDetailsService. getAllStoreItemLedgerDetailsBasedOnSILId(silId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 * Use Case: ----------------------------------------- Manage Store Master --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStoreMaster", method = RequestMethod.GET)
	public String indexStoreMaster(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Store Master", 2, request);
		model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
		return "inventory/storeMaster";
	}
	
	@RequestMapping(value = "/storeMaster/read", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody Object readStoreMaster(final Locale locale) {
		try
		{
			//List<StoreMaster> list =  storeMasterService.findAllStoreMasters();
			return storeMasterService.getAllStoresRequiredFields();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	@RequestMapping(value = "/storeMaster/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStoreMaster(@PathVariable String operation, @ModelAttribute("storeMaster") StoreMaster storeMaster, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			if((validationUtil.customValidateModelObject(storeMaster, null, locale)) != null)
				return validationUtil.customValidateModelObject(storeMaster, null, locale);

			if(operation.equals("create"))
				storeMasterService.save(storeMaster);
			else
				storeMasterService.update(storeMaster);
			
			sessionStatus.setComplete();
			return storeMaster;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storeMaster/storeStatus/{storeId}/{operation}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String storeStatus(@PathVariable int storeId, @PathVariable String operation, HttpServletResponse response) throws SQLException
	{		
		return storeMasterService.setStoreStatus(storeId, operation, response);
	}
	
	@RequestMapping(value = "/storeMaster/getStoreNamesForFilterList", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<String> getStoreNamesForFilterList() 
	{
		return storeMasterService.getStoreNamesForFilterList();
	}
	
	@RequestMapping(value = "/storeMaster/getstoreInchargeStaffNamesFilterList", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Map<String, Object>> getstoreInchargeStaffNamesFilterList() {
		List<?> personList = storeMasterService.getstoreInchargeStaffNamesFilterList();
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
	@RequestMapping(value = "/storeMaster/storeLocationFilterUrl", method ={ RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<String> getStoreLocationFilterUrl() 
	{
		return storeMasterService.getStoreLocationFilterUrl();
	}
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	
	/**
	 *  ----------------------------------------- Manage Store Physical Inventory --------------------------------------------
	 */
	
	@RequestMapping(value = "/indexStorePhysicalInventory", method = RequestMethod.GET)
	public String indexStorePhysicalInventory(ModelMap model, HttpServletRequest request, final Locale locale) {
		model.addAttribute("ViewName", "Inventory ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("inventory", 1, request);
		breadCrumbService.addNode("Manage Store Physical Inventory", 2, request);
		model.addAttribute("spiCondition", commonController.populateCategories("spiCondition", locale));
		return "inventory/storePhysicalInventory";
	}
	
	@RequestMapping(value = "/storePhysicalInventory/read", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody Object readStorePhysicalInventory(final Locale locale) {
		try
		{
			return storePhysicalInventoryService.findAllStorePhysicalInventory();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}

	@RequestMapping(value = "/storePhysicalInventory/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStorePhysicalInventory(@PathVariable String operation, @ModelAttribute("storePhysicalInventory") StorePhysicalInventory storePhysicalInventory, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			storePhysicalInventory.setSpiDt(new Timestamp(new Date().getTime()));
			storePhysicalInventory.setSurveyDt(null);
			storePhysicalInventory.setSurveyCompleteDt(null);
			
			storePhysicalInventory.setStorePhysicalInventoryReportsSet(getStorePhysicalInventoryReports(storePhysicalInventory));
			
			if((validationUtil.customValidateModelObject(storePhysicalInventory, null, locale)) != null)
				return validationUtil.customValidateModelObject(storePhysicalInventory, null, locale);

			if(operation.equals("create"))
				storePhysicalInventoryService.save(storePhysicalInventory);
			else
				storePhysicalInventoryService.update(storePhysicalInventory);
			
			sessionStatus.setComplete();
			return storePhysicalInventory;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Set<StorePhysicalInventoryReport> getStorePhysicalInventoryReports(
			StorePhysicalInventory storePhysicalInventory){
		Set<StorePhysicalInventoryReport>  storePhysicalInventoryReportsSet = new HashSet<StorePhysicalInventoryReport>();
		String categoryIds = storePhysicalInventory.getCategoryIds();
		String[] categoryIdsArr = categoryIds.split(",");
		for (String string : categoryIdsArr)
		{
			if(string.startsWith("I"))
			{
				String[] innerCategoryIdsArr = string.split("-");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("storeId", Integer.parseInt(innerCategoryIdsArr[2]));
				params.put("imId", Integer.parseInt(innerCategoryIdsArr[1]));
				StoreItemLedger storeItemLedger = ((List<StoreItemLedger>) storeItemLedgerService.selectObjectQuery(params)).get(0);
				
				StorePhysicalInventoryReport storePhysicalInventoryReport = new StorePhysicalInventoryReport();
				storePhysicalInventoryReport.setExpectedBalance(storeItemLedger.getImBalance());
				storePhysicalInventoryReport.setImId(storeItemLedger.getImId());
				storePhysicalInventoryReport.setStoreId(storeItemLedger.getStoreId());
				storePhysicalInventoryReport.setUomId(storeItemLedger.getImUom());
				storePhysicalInventoryReport.setSpiCondition("Under Review");
				storePhysicalInventoryReport.setAvailableBalance(0.0);
				
				storePhysicalInventoryReportsSet.add(storePhysicalInventoryReport);
			}
		}
		return storePhysicalInventoryReportsSet;
	}

	@RequestMapping(value = "/storePhysicalInventory/getStorePhysicalInventoryCategories", method = RequestMethod.GET)
    public @ResponseBody List<?> getStorePhysicalInventoryCategories(final Locale locale) {
		String[] str = messageSource.getMessage("storePhysicalInventoryCategories", null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
    	List<Map<String, String>> categoriesList = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			map = new HashMap<String, String>();
			map.put("storePhysicalInventoryCategories", string);
			categoriesList.add(map);
		}
		return categoriesList;
    }
	
	@RequestMapping(value = "/storePhysicalInventory/getStorePhysicalInventorySubCategories", method = RequestMethod.GET)
    public @ResponseBody List<?> getStorePhysicalInventorySubCategories(final Locale locale) {
		String[] str = messageSource.getMessage("storePhysicalInventoryCategories", null, locale).split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, str);
    	List<Map<String, String>> categoriesList = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			
			for (Iterator<String> iterator1 = list.iterator(); iterator1.hasNext();)
			{
				String string1 = (String) iterator1.next();
				map = new HashMap<String, String>();
				map.put("storePhysicalInventoryCategories", string);
				if(!string.equalsIgnoreCase(string1))
				{	
					map.put("storePhysicalInventorySubCategories", string1);
					categoriesList.add(map);
				}
			}
			
		}
		return categoriesList;
    }
	
	@RequestMapping(value = "/storePhysicalInventory/category/read", method = RequestMethod.GET)
	public @ResponseBody List<StoreCategory> readStoreTree(HttpServletRequest req) {
		//setChildEntriesFroTree();
		
		String storeCategoryId=null;
		logger.info("Store Id::::::::::\n\n\n====="+req.getParameter("storeCategoryId"));
		if(req.getParameter("storeCategoryId")!=null)
			storeCategoryId = req.getParameter("storeCategoryId");
		return storeGoodsReceiptService.findAllForTreeByStoreCategoryId(storeCategoryId);
	}
	
	
	@RequestMapping(value = "/storePhysicalInventory/spiStatus/{spiId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String updateSpiStatus(@PathVariable int spiId, @PathVariable String operation) throws InterruptedException
	{		
		boolean flag = true;
		
		StorePhysicalInventory storePhysicalInventory = storePhysicalInventoryService.find(spiId);
		
		if(operation.equalsIgnoreCase("Started"))
			storePhysicalInventory.setSurveyDt(new Timestamp(new Date().getTime()));
		else if(operation.equalsIgnoreCase("Completed"))
		{
			Set<StorePhysicalInventoryReport> storePhysicalInventoryReportsSet = storePhysicalInventory.getStorePhysicalInventoryReportsSet();
			for (Iterator<StorePhysicalInventoryReport> iterator = storePhysicalInventoryReportsSet
					.iterator(); iterator.hasNext();)
			{
				StorePhysicalInventoryReport storePhysicalInventoryReport = (StorePhysicalInventoryReport) iterator
						.next();
				if(storePhysicalInventoryReport.getSpiCondition().equalsIgnoreCase("Under Review"))
				{
					flag = false;
				}
			}
			storePhysicalInventory.setSurveyCompleteDt(new Timestamp(new Date().getTime()));
		}
			
		if(flag)
		{
			storePhysicalInventory.setSpiStatus(operation);
			storePhysicalInventoryService.update(storePhysicalInventory);
			return "Survey "+operation;
		}
		else
			return "Cannot complete the survey since report condition is still under review";
	}
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	/**
	 * Use Case: ----------------------------------------- Manage Store Physical Inventory Report --------------------------------------------
	 */
	
	@RequestMapping(value = "/storePhysicalInventoryReport/read/{spiId}", method = RequestMethod.POST)
	public @ResponseBody Object readStorePhysicalInventoryReport(@PathVariable int spiId, final Locale locale) {
		try
		{
			return storePhysicalInventoryReportService.getAllStorePhysicalInventoryReportsBasedOnSPIId(spiId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storePhysicalInventoryReport/modify/{operation}", method = {RequestMethod.POST ,RequestMethod.GET})
	public @ResponseBody Object modifyStorePhysicalInventoryReport(@PathVariable String operation, @ModelAttribute("storePhysicalInventoryReport") StorePhysicalInventoryReport storePhysicalInventoryReport, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale){
		try
		{	
			
				
			if(storePhysicalInventoryReport.getSpiCondition().equalsIgnoreCase("Available"))
				storePhysicalInventoryReport.setAvailableBalance(storePhysicalInventoryReport.getExpectedBalance());
			if((validationUtil.customValidateModelObject(storePhysicalInventoryReport, null, locale)) != null)
				return validationUtil.customValidateModelObject(storePhysicalInventoryReport, null, locale);

			if(operation.equals("update")){
				storePhysicalInventoryReportService.update(storePhysicalInventoryReport);
				StoreItemLedger silId=storeItemLedgerDetailsService.getObject(storePhysicalInventoryReport.getStoreId(),storePhysicalInventoryReport.getImId());
				storeItemLedgerService.updateItems(silId,storePhysicalInventoryReport.getStoreId(),storePhysicalInventoryReport.getImId(),storePhysicalInventoryReport.getAvailableBalance());
				
			}
			
			sessionStatus.setComplete();
			return storePhysicalInventoryReport;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorResponse.throwException(locale);
		}
	}
	
	@RequestMapping(value = "/storePhysicalInventoryReport/getConditions", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getConditions(final Locale locale) {
		String[] str = messageSource.getMessage("spiCondition", null, locale).split(",");
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
			map.put("spiCondition", string);
			result.add(map);
		}
		return result;
	} 
	
	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
	/**
	 *  ----------------------------------------- Common --------------------------------------------
	 */
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> updateStoreItems(Object object, Double uomConversion,
			StoreGoodsReceiptItems oldStoreGoodsReceiptItems, StoreGoodsReceipt oldStoreGoodsReceipt, Double finalQuantity){
		StoreMovement storeMovement = new StoreMovement();
		StoreAdjustments storeAdjustments = new StoreAdjustments();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(object instanceof StoreMovement)
		{
			storeMovement = (StoreMovement) object;
			params.put("storeId",  storeMovement.getToStoreId());
			params.put("vcId", storeMovement.getVcId());
		}
		else
		{
			storeAdjustments = (StoreAdjustments) object;
			params.put("storeId",  storeAdjustments.getStoreId());
			params.put("vcId", storeAdjustments.getVcId());
		}
		
		String user = (String) SessionData.getUserDetails().get("userID");
		Timestamp dt = new Timestamp(new Date().getTime());
		
		StoreGoodsReceipt newStoreGoodsReceipt = null;
		StoreGoodsReceiptItems storeGoodsReceiptItems = null;
		
		List<StoreGoodsReceipt> storeGoodsReceiptsList = (List<StoreGoodsReceipt>) storeGoodsReceiptService.selectObjectQuery(params); 
		if(storeGoodsReceiptsList.size() == 0)
		{
			newStoreGoodsReceipt = new StoreGoodsReceipt();
			
			newStoreGoodsReceipt.setCreatedBy(user);
			newStoreGoodsReceipt.setLastUpdatedDt(dt);
			drGroupIdService.save(new DrGroupId(newStoreGoodsReceipt.getCreatedBy(), newStoreGoodsReceipt.getLastUpdatedDt()));
			newStoreGoodsReceipt.setDrGroupId(drGroupIdService.getNextVal(newStoreGoodsReceipt.getCreatedBy(), newStoreGoodsReceipt.getLastUpdatedDt()));
			newStoreGoodsReceipt.setLastUpdatedBy(newStoreGoodsReceipt.getCreatedBy());
			newStoreGoodsReceipt.setLedgerUpdateDt(newStoreGoodsReceipt.getLastUpdatedDt());
			
			storeGoodsReceiptItems = getStoreGoodsReceiptItem(object, uomConversion, oldStoreGoodsReceiptItems, user, dt, finalQuantity);
			Set<StoreGoodsReceiptItems> StoreGoodsReceiptItemsSet = new HashSet<StoreGoodsReceiptItems>();
			StoreGoodsReceiptItemsSet.add(storeGoodsReceiptItems);
			newStoreGoodsReceipt.setStoreGoodsReceiptItemsSet(StoreGoodsReceiptItemsSet);
			
			//CHANGE
			if(object instanceof StoreMovement)
			{	
				newStoreGoodsReceipt.setCheckedByStaffId(oldStoreGoodsReceipt.getCheckedByStaffId());
				newStoreGoodsReceipt.setPoRecDt(oldStoreGoodsReceipt.getPoRecDt());
				newStoreGoodsReceipt.setReceivedByStaffId(oldStoreGoodsReceipt.getReceivedByStaffId());
				newStoreGoodsReceipt.setShippingDocumentNumber(oldStoreGoodsReceipt.getShippingDocumentNumber());
				newStoreGoodsReceipt.setVcId(1);
				
				newStoreGoodsReceipt.setStoreId(storeMovement.getToStoreId());
				
			}
			else
			{
				newStoreGoodsReceipt.setCheckedByStaffId(storeAdjustments.getApprovedByStaffId());
				newStoreGoodsReceipt.setPoRecDt(storeAdjustments.getLedgerUpdateDt());
				newStoreGoodsReceipt.setReceivedByStaffId(storeAdjustments.getApprovedByStaffId());
				newStoreGoodsReceipt.setShippingDocumentNumber(storeAdjustments.getShippingDocumentNumber());
				newStoreGoodsReceipt.setVcId(storeAdjustments.getVcId());
				
				newStoreGoodsReceipt.setStoreId(storeAdjustments.getStoreId());
				
				//save parent along with child
				newStoreGoodsReceipt = storeGoodsReceiptService.save(newStoreGoodsReceipt);
			}
			
		}
		
		else
		{	
			boolean flag = true;
			newStoreGoodsReceipt = storeGoodsReceiptsList.get(0);
			Set<StoreGoodsReceiptItems> storeGoodsReceiptItemsSet = newStoreGoodsReceipt.getStoreGoodsReceiptItemsSet();
			
			
			if(flag)
			{
				storeGoodsReceiptItems = getStoreGoodsReceiptItem(object, uomConversion, oldStoreGoodsReceiptItems, user, dt, finalQuantity);
				storeGoodsReceiptItemsSet.add(storeGoodsReceiptItems);
			}
			
			//CHANGE
			if(object instanceof StoreAdjustments)
				newStoreGoodsReceipt = storeGoodsReceiptService.update(newStoreGoodsReceipt);
			
		 }
		
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("child", newStoreGoodsReceiptItemsList.get(0));
		map.put("child", storeGoodsReceiptItems);
		map.put("parent", newStoreGoodsReceipt);
		return map;
	}

	private StoreGoodsReceiptItems getStoreGoodsReceiptItem(Object object, Double uomConversion, 
			StoreGoodsReceiptItems oldStoreGoodsReceiptItems, String user,
			Timestamp dt, Double quantity){
		StoreGoodsReceiptItems storeGoodsReceiptItems = new StoreGoodsReceiptItems();
		Timestamp checkDT = new Timestamp(new Date().getTime() + 5000);
		
		//CHANGE
		StoreMovement storeMovement = new StoreMovement();
		StoreAdjustments storeAdjustments = new StoreAdjustments();
		
		if(object instanceof StoreMovement)
		{
			storeMovement = (StoreMovement) object;
			storeGoodsReceiptItems.setUomId(storeMovement.getUomId());
			
			storeGoodsReceiptItems.setImId(oldStoreGoodsReceiptItems.getImId());
			storeGoodsReceiptItems.setItemManufactureDate(oldStoreGoodsReceiptItems.getItemManufactureDate());
			storeGoodsReceiptItems.setItemExpiryDate(oldStoreGoodsReceiptItems.getItemExpiryDate());
			storeGoodsReceiptItems.setItemManufacturer(oldStoreGoodsReceiptItems.getItemManufacturer());
			storeGoodsReceiptItems.setPartNo(oldStoreGoodsReceiptItems.getPartNo());
			storeGoodsReceiptItems.setSpecialInstructions(oldStoreGoodsReceiptItems.getSpecialInstructions());
			storeGoodsReceiptItems.setWarrantyValidTill(oldStoreGoodsReceiptItems.getWarrantyValidTill());
			storeGoodsReceiptItems.setRate(oldStoreGoodsReceiptItems.getRate());
		}
		else
		{
			storeAdjustments = (StoreAdjustments) object;
			storeGoodsReceiptItems.setUomId(storeAdjustments.getUomId());
			
			storeGoodsReceiptItems.setImId(storeAdjustments.getImId());
			storeGoodsReceiptItems.setItemManufactureDate(storeAdjustments.getItemManufactureDate());
			storeGoodsReceiptItems.setItemManufacturer(storeAdjustments.getItemManufacturer());
			storeGoodsReceiptItems.setPartNo(storeAdjustments.getPartNo());
			storeGoodsReceiptItems.setRate(storeAdjustments.getRate());
		}
		
		storeGoodsReceiptItems.setCreatedBy(user);
		storeGoodsReceiptItems.setLastUpdatedDt(dt);
		drGroupIdService.save(new DrGroupId(user, checkDT));
		storeGoodsReceiptItems.setDrGroupId(drGroupIdService.getNextVal(user, checkDT));
		storeGoodsReceiptItems.setLastUpdatedBy(user);
		storeGoodsReceiptItems.setSgriStatus("Active");
		storeGoodsReceiptItems.setReceiptType("Adjustment");
		storeGoodsReceiptItems.setActivationDt(dt);
		storeGoodsReceiptItems.setItemQuantity(quantity / uomConversion);
		
		return storeGoodsReceiptItems;
	}

	@SuppressWarnings("unchecked")
	private StoreItemLedger updateStoreItemLegder(int storeId, int imId,
			int uomId, double rate, Timestamp lastUpdatedDt, String transactionType,
			Double finalQuantity){
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("imId", imId);
		List<StoreItemLedger> storeItemLedgers = (List<StoreItemLedger>) storeItemLedgerService.selectObjectQuery(params);
		
		StoreItemLedger storeItemLedger = new StoreItemLedger();
		
		Set<StoreItemLedgerDetails> storeItemLedgerDetailsSet = null;
		
		UnitOfMeasurement unitOfMeasurement = uomService.find(uomId);
		
		if((storeItemLedgers.size()) == 0)
		{
			storeItemLedger = getstoreItemLedger(storeId, imId);
			storeItemLedgerDetailsSet = new HashSet<StoreItemLedgerDetails>();
			
			if(unitOfMeasurement.getBaseUom().equalsIgnoreCase("Yes"))
			{
				storeItemLedger.setImUom(uomId);
				storeItemLedger.setImBalance(Double.parseDouble(decimalFormat.format(finalQuantity)));
				//storeItemLedgerDetails.setQuantity(quantity);
			}
			else
			{
				Map<String, Object> paramNew = new HashMap<String, Object>();
				paramNew.put("baseUom", "Yes");
				paramNew.put("imId", imId);
				UnitOfMeasurement unitOfMeasurementBaseUOM = (UnitOfMeasurement) uomService.selectObjectQuery(paramNew).get(0);
				storeItemLedger.setImUom(unitOfMeasurementBaseUOM.getUomId());
				double quantity = (finalQuantity * (unitOfMeasurement.getUomConversion()));
				
				storeItemLedger.setImBalance(Double.parseDouble(decimalFormat.format(quantity)));
				//storeItemLedgerDetails.setQuantity(quantity);
			}
			
			storeItemLedgerDetailsSet.add(getstoreItemLedgerDetails(uomId, rate, transactionType, finalQuantity));
			storeItemLedger.setStoreItemLedgerDetailsSet(storeItemLedgerDetailsSet);
			storeItemLedger = storeItemLedgerService.save(storeItemLedger);
		}
		else
		{
			storeItemLedger = storeItemLedgers.get(0);
			storeItemLedgerDetailsSet = storeItemLedger.getStoreItemLedgerDetailsSet();
			
			if(unitOfMeasurement.getBaseUom().equalsIgnoreCase("Yes"))
			{	
				if(transactionType.equalsIgnoreCase("Credit"))
					storeItemLedger.setImBalance(Double.parseDouble(decimalFormat.format((storeItemLedger.getImBalance()) + (finalQuantity))));
				else
					storeItemLedger.setImBalance(Double.parseDouble(decimalFormat.format((storeItemLedger.getImBalance()) - (finalQuantity))));
			}
			else
			{
				Map<String, Object> paramNew = new HashMap<String, Object>();
				paramNew.put("baseUom", "Yes");
				paramNew.put("imId", imId);
				UnitOfMeasurement unitOfMeasurementBaseUOM = (UnitOfMeasurement) uomService.selectObjectQuery(paramNew).get(0);
				storeItemLedger.setImUom(unitOfMeasurementBaseUOM.getUomId());
				double quantity = (finalQuantity * (unitOfMeasurement.getUomConversion()));
				
				if(transactionType.equalsIgnoreCase("Credit"))
					storeItemLedger.setImBalance(Double.parseDouble(decimalFormat.format(storeItemLedger.getImBalance() + quantity)));
				else
					storeItemLedger.setImBalance(Double.parseDouble(decimalFormat.format(storeItemLedger.getImBalance() - quantity)));
			}
			
			storeItemLedger.setLastUpdatedDt(lastUpdatedDt);
			storeItemLedger.setSilDt(new Timestamp(new Date().getTime()));
			storeItemLedger.setStoreEntryFrom("Adjustment");
			
			storeItemLedgerDetailsSet.add(getstoreItemLedgerDetails(uomId, rate, transactionType, finalQuantity));
			storeItemLedger.setStoreItemLedgerDetailsSet(storeItemLedgerDetailsSet);
			storeItemLedger = storeItemLedgerService.update(storeItemLedger);
		}
		
		return storeItemLedger;
	}

	private StoreItemLedgerDetails getstoreItemLedgerDetails(int uomId,
			double rate, String transactionType, Double finalQuantity){
		StoreItemLedgerDetails storeItemLedgerDetails = new StoreItemLedgerDetails();
		storeItemLedgerDetails.setTransactionType(transactionType);
		storeItemLedgerDetails.setUomId(uomId);
		
		storeItemLedgerDetails.setRate(rate);
		
		DecimalFormat decimalFormat = new DecimalFormat("###.##");

		if(transactionType.equalsIgnoreCase("Debit")){
			storeItemLedgerDetails.setQuantity(Double.parseDouble(decimalFormat.format(finalQuantity * (-1))));
			storeItemLedgerDetails.setStatus("Issued");			
		}
		else{
			storeItemLedgerDetails.setQuantity(Double.parseDouble(decimalFormat.format(finalQuantity)));
			storeItemLedgerDetails.setStatus("Returned");
		}
		
		return storeItemLedgerDetails;
	}

	private StoreItemLedger getstoreItemLedger(int storeId, int imId){
		StoreItemLedger storeItemLedger = new StoreItemLedger();

		storeItemLedger.setSilDt(new Timestamp(new Date().getTime()));
		storeItemLedger.setStoreEntryFrom("New");
		storeItemLedger.setStoreId(storeId);
		storeItemLedger.setImId(imId);
		return storeItemLedger;
	}
	
	private void updateStoreAdjustments(Object object1, 
			Object object2, String type, Double updatingQuantity, String adjustmentType){
		StoreAdjustments storeAdjustments = new StoreAdjustments();
		
		StoreGoodsReceiptItems oldStoreGoodsReceiptItems = new StoreGoodsReceiptItems();
		StoreGoodsReceipt oldStoreGoodsReceipt = new StoreGoodsReceipt();
		StoreGoodsReturns storeGoodsReturns = new StoreGoodsReturns();
		
		if(object1 instanceof StoreGoodsReceiptItems)
		{
			oldStoreGoodsReceiptItems = (StoreGoodsReceiptItems) object1;
			storeAdjustments.setImId(oldStoreGoodsReceiptItems.getImId());
			storeAdjustments.setUomId(oldStoreGoodsReceiptItems.getUomId());
		}
		else
		{	
			storeGoodsReturns = (StoreGoodsReturns) object1;
			Timestamp timestamp = new Timestamp(new Date().getTime());
			storeAdjustments.setImId(storeGoodsReturns.getImId());
			storeAdjustments.setUomId(storeGoodsReturns.getUomId());
			storeAdjustments.setStoreId(storeGoodsReturns.getStoreId());
			storeAdjustments.setVcId(1);
			storeAdjustments.setSaDt(timestamp);
			storeAdjustments.setApprovedByStaffId(storeGoodsReturns.getReturnedByStaffId());
			storeAdjustments.setLedgerUpdateDt(timestamp);
		}
		 
		if(object2 instanceof StoreGoodsReceipt)
		{	
			oldStoreGoodsReceipt =  (StoreGoodsReceipt) object2;
			storeAdjustments.setStoreId(oldStoreGoodsReceipt.getStoreId());
			storeAdjustments.setVcId(oldStoreGoodsReceipt.getVcId());
			storeAdjustments.setSaDt(oldStoreGoodsReceipt.getLedgerUpdateDt());
			storeAdjustments.setApprovedByStaffId(oldStoreGoodsReceipt.getCheckedByStaffId());
			storeAdjustments.setLedgerUpdateDt(oldStoreGoodsReceipt.getLedgerUpdateDt());
		}
		else
			storeGoodsReturns = (StoreGoodsReturns) object1;

		storeAdjustments.setReasonForAdjustment(adjustmentType);
		storeAdjustments.setSaNumber(12345);
		
		if(type.equalsIgnoreCase("Debit"))
			storeAdjustments.setItemQuantity(updatingQuantity * (-1));
		else
			storeAdjustments.setItemQuantity(updatingQuantity);
		
		storeAdjustmentsService.save(storeAdjustments);
	}
	
	private void updateStoreMovement(StoreMovement storeMovement, StoreGoodsReceiptItems oldStoreGoodsReceiptItems, double finalQuantity) throws CloneNotSupportedException
	{
		StoreMovement newStoreMovement = (StoreMovement) storeMovement.clone();
		newStoreMovement.setItemExpiryDate(oldStoreGoodsReceiptItems.getItemExpiryDate());
		newStoreMovement.setItemManufacturer(oldStoreGoodsReceiptItems.getItemManufacturer());
		newStoreMovement.setPartNo(oldStoreGoodsReceiptItems.getPartNo());
		newStoreMovement.setSpecialInstructions(oldStoreGoodsReceiptItems.getSpecialInstructions());
		//newStoreMovement.setStmStatus("Active");
		newStoreMovement.setWarrantyValidTill(oldStoreGoodsReceiptItems.getWarrantyValidTill());
		//set quantity
		newStoreMovement.setItemQuantity(finalQuantity);
		
		storeMovementService.save(newStoreMovement);
	}
	
	private void updateCategories(int storeId, String storeName, int imId, String imName){
		
		String storecategoryid="S-"+storeId;
		String storeNameselected=storeName;
		String parent="P-2";
		
		String storecategorychileid="I-"+imId+"-"+storeId;
		String storeNamechildselected=imName;
		String parentchild="S-"+storeId;	
		
		StoreCategory storeCategoryParent = new StoreCategory();		
		storeCategoryParent.setStoreCategoryId(storecategoryid.toString());
		storeCategoryParent.setStoreCategoryName(storeNameselected.toString());
		storeCategoryParent.setParentId(parent.toString());
		storeCategoryService.update(storeCategoryParent);	
		
		StoreCategory storeCategoryChild = new StoreCategory();
		storeCategoryChild.setStoreCategoryId(storecategorychileid.toString());
		storeCategoryChild.setStoreCategoryName(storeNamechildselected.toString());
		storeCategoryChild.setParentId(parentchild.toString());
		storeCategoryService.update(storeCategoryChild);
	}
	
	

	@RequestMapping(value ="/storeItemTemplate/storeItemTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileStoreItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> storeItemTemplateEntities =storeItemLedgerService.findAllItemLedgers();
	
               
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
    	
    	header.createCell(0).setCellValue("Store");
        header.createCell(1).setCellValue("Item");
        header.createCell(2).setCellValue("Balance");
        header.createCell(3).setCellValue("Base Unit Of Measurment");
        header.createCell(4).setCellValue("Ledger Update Date Time");
       /* header.createCell(5).setCellValue("Transaction Type");
        header.createCell(6).setCellValue("Quantity");
        header.createCell(7).setCellValue("Status");
        header.createCell(8).setCellValue("Unit Of Measurment");
        header.createCell(9).setCellValue("Rate Per Quantity");
        header.createCell(10).setCellValue("Ledger Update Date Time");
        */
        
    
        for(int j = 0; j<=4; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:E200"));
        }
        
        int count = 1;
       
    	for (Object[] storeItem :storeItemTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);

    		String str="";
    		
    		if((String)storeItem[10]!=null){
				str=(String)storeItem[10];
			}
            else{
            	str="";
            }


    		row.createCell(0).setCellValue(str);
    		if((String)storeItem[11]!=null){
				str=(String)storeItem[11];
			}
            else{
            	str="";
            }
    		
		    row.createCell(1).setCellValue(str);
    		if((Double)storeItem[5]!=null){
    			double d=(Double)storeItem[5];
    			int i=(int)d;
				str=Integer.toString(i);
			}
            else{
            	str="";
            }
    	    row.createCell(2).setCellValue(str);
    	    if((String)storeItem[12]!=null){
				str=(String)storeItem[12];
			}
            else{
            	str="";
            }
    		row.createCell(3).setCellValue(str);
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
    		if((Timestamp)storeItem[3] != null){
    			str=sdf.format((Timestamp)storeItem[3]);
    		}
    		else{
    			str="";
    		}
    		row.createCell(4).setCellValue(str);
    		/*List<StoreItemLedgerDetails> storeItemChildTemplateEntities=storeItemLedgerDetailsService. getAllStoreItemLedgerDetailsBasedOnSILId((Integer) storeItem[0]);
    		for (StoreItemLedgerDetails storeItemChild :storeItemChildTemplateEntities ) {
    			
    			System.out.println("iiiiiiiiiiiiiii"+storeItemChild.getTransactionType());
    			row.createCell(5).setCellValue(storeItemChild.getTransactionType());
    		
    			System.out.println("qqqqqqqqqqqqqqqq"+storeItemChild.getQuantity());
    			row.createCell(6).setCellValue(storeItemChild.getQuantity());
    			System.out.println("sssssssssssssssssss"+storeItemChild.getStatus());
    			row.createCell(7).setCellValue(storeItemChild.getStatus());
    			row.createCell(8).setCellValue(storeItemChild.getQuantityPerBaseUom());
    			row.createCell(9).setCellValue(storeItemChild.getRate());
    			row.createCell(10).setCellValue(sdf.format(storeItemChild.getLastUpdatedDt()));
    			//row.createCell(11).setCellValue(storeItemChild.getQuantity());
    		}*/
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"StoreItemLedgerTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/storeItemPdfTemplate/storeItemPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfStoreItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> storeItemTemplateEntities =storeItemLedgerService.findAllItemLedgers();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(5);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
       
       
        table.addCell(new Paragraph("Store",font1));
        table.addCell(new Paragraph("Item",font1));
        table.addCell(new Paragraph("Balance",font1));
        table.addCell(new Paragraph("Base Unit Of Measurment",font1));
        table.addCell(new Paragraph("Ledger Update Date Time",font1));
        
    	for (Object[] storeItem :storeItemTemplateEntities) {
    		
    		String str="";
    		if((String)storeItem[10]!=null){
				str=(String)storeItem[10];
			}
            else{
            	str="";
            }

             PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
             if((String)storeItem[11]!=null){
   				str=(String)storeItem[11];
   			}
               else{
               	str="";
               }
             
   
        PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
        if((Double)storeItem[5]!=null){
        	double d=(Double)storeItem[5];
			int i=(int)d;
			str=Integer.toString(i);
		}
        else{
        	str="";
        }
        PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
        if((String)storeItem[12]!=null){
				str=(String)storeItem[12];
			}
          else{
          	str="";
          }
       
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
        
        
     
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
		if((Timestamp)storeItem[3] != null){
			str=sdf.format((Timestamp)storeItem[3]);
		}
		else{
			str="";
		}
   
        PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
        
     

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        
        table.setWidthPercentage(100);
        float[] columnWidths = {8f, 8f, 10f, 18f, 18f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"StoreItemLedgerTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/stockOutwardTemplate/stockOutwardTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileStock(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<Object[]> stockTemplateEntities =storeOutwardService.readAllStore();
	
               
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
    	
    	header.createCell(0).setCellValue("Store Name");
        header.createCell(1).setCellValue("Item Name");
        header.createCell(2).setCellValue("Unit of Return");
        header.createCell(3).setCellValue("Quantity");
        header.createCell(4).setCellValue("Returned By");
        header.createCell(5).setCellValue("Returned To");
        header.createCell(6).setCellValue("Reason for Return");
        
        
        
    
        for(int j = 0; j<=6; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:G200"));
        }
        
        int count = 1;
       
    	for (Object[] storeItem :stockTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);

    		String str="";
    		String str1="";
    		
    		if((String)storeItem[7]!=null){
				str=(String)storeItem[7];
			}
            else{
            	str="";
            }


    		row.createCell(0).setCellValue(str);
    		if((String)storeItem[5]!=null){
				str=(String)storeItem[5];
			}
            else{
            	str="";
            }
    		
		    row.createCell(1).setCellValue(str);
		    if((String)storeItem[15]!=null){
				str=(String)storeItem[15];
			}
            else{
            	str="";
            }
    		
    	    row.createCell(2).setCellValue(str);
    	    if((Double)storeItem[16]!=null){
    	    	 double d=(Double)storeItem[16];
           	     int a=(int)d;
   			     str=Integer.toString(a);
			}
            else{
            	str="";
            }
    		row.createCell(3).setCellValue(str);
    		if((String)storeItem[8]!=null){
				str=(String)storeItem[8];
			}
            else{
            	str="";
            }
    		if((String)storeItem[9]!=null){
				str1=(String)storeItem[9];
			}
            else{
            	str1="";
            }
    		
    	    row.createCell(4).setCellValue(str+" "+str1);
    	    if((String)storeItem[10]!=null){
				str=(String)storeItem[10];
			}
            else{
            	str="";
            }
    		if((String)storeItem[11]!=null){
				str1=(String)storeItem[11];
			}
            else{
            	str1="";
            }
    		
    	    row.createCell(5).setCellValue(str+" "+str1);
    		
    	    if((String)storeItem[1]!=null){
				str=(String)storeItem[1];
			}
            else{
            	str="";
            }
    		
    	    row.createCell(6).setCellValue(str);
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"StockOutWardTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/stockOutwardPdfTemplate/stockOutwardPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfStock(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<Object[]> stockTemplateEntities =storeOutwardService.readAllStore();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(7);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        

       
        table.addCell(new Paragraph("Store Name",font1));
        table.addCell(new Paragraph("Item Name",font1));
        table.addCell(new Paragraph("Unit of Return",font1));
        table.addCell(new Paragraph("Quantity",font1));
        table.addCell(new Paragraph("Returned By",font1));
        table.addCell(new Paragraph("Returned To",font1));
        table.addCell(new Paragraph("Reason for Return",font1));
    	for (Object[] storeItem :stockTemplateEntities) {
    		
    		String str="";
    		String str1="";
    		
    	
    	    if((String)storeItem[7]!=null){
				str=(String)storeItem[7];
			}
            else{
            	str="";
            }


             PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
             if((String)storeItem[5]!=null){
 				str=(String)storeItem[5];
 			}
             else{
             	str="";
             }
   
           PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
           if((String)storeItem[15]!=null){
				str=(String)storeItem[15];
			}
           else{
           	str="";
           }
   		
          PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
          if((Double)storeItem[16]!=null){
        	  double d=(Double)storeItem[16];
        	  int a=(int)d;
			   str=Integer.toString(a);
			}
          else{
          	str="";
          }
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
        
        
        if((String)storeItem[8]!=null){
			str=(String)storeItem[8];
		}
        else{
        	str="";
        }
		if((String)storeItem[9]!=null){
			str1=(String)storeItem[9];
		}
        else{
        	str1="";
        }
   
        PdfPCell cell5 = new PdfPCell(new Paragraph(str+" "+str1,font3));
        if((String)storeItem[10]!=null){
			str=(String)storeItem[10];
		}
        else{
        	str="";
        }
		if((String)storeItem[11]!=null){
			str1=(String)storeItem[11];
		}
        else{
        	str1="";
        }
		 PdfPCell cell6 = new PdfPCell(new Paragraph(str+" "+str1,font3));
        if((String)storeItem[1]!=null){
			str=(String)storeItem[1];
		}
        else{
        	str="";
        }
        PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
     

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        
        table.setWidthPercentage(100);
        float[] columnWidths = {12f, 10f, 15f, 11f, 16f, 16f, 18f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"StockOutWardTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/storeInventoryTemplate/storeInventoryTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileStoreInventory(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<StorePhysicalInventory> storeInventoryTemplateEntities =storePhysicalInventoryService.findAllStorePhysicalInventory();
	
               
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
    	
    	header.createCell(0).setCellValue("Survey Created Date Time");
        header.createCell(1).setCellValue("Survey Started Date Time");
        header.createCell(2).setCellValue("Survey Name");
        header.createCell(3).setCellValue("Survey Description");
        header.createCell(4).setCellValue("Survey Completed Date Time");
        header.createCell(5).setCellValue("Status");
     
        for(int j = 0; j<=5; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:F200"));
        }
        
        int count = 1;
       
    	for (StorePhysicalInventory storeInventory :storeInventoryTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);

    		String str="";
    	
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm ");
    		if(storeInventory.getSpiDt()!=null){
				str=sdf.format(storeInventory.getSpiDt());
			}
            else{
            	str="";
            }

            row.createCell(0).setCellValue(str);
    		if(storeInventory.getSurveyDt()!=null){
				str=sdf.format(storeInventory.getSurveyDt());
			}
            else{
            	str="";
            }
    		
		    row.createCell(1).setCellValue(str);
		    if(storeInventory.getSurveyName()!=null){
				str=storeInventory.getSurveyName();
			}
            else{
            	str="";
            }
    		
    	    row.createCell(2).setCellValue(str);
    	    if(storeInventory.getSurveyDescription()!=null){
				str=storeInventory.getSurveyDescription();
			}
            else{
            	str="";
            }
    		row.createCell(3).setCellValue(str);
    		if(storeInventory.getSurveyCompleteDt()!=null){
				str=sdf.format(storeInventory.getSurveyCompleteDt());
			}
            else{
            	str="";
            }
    		
    	    row.createCell(4).setCellValue(str);
    	    if(storeInventory.getSpiStatus()!=null){
				str=storeInventory.getSpiStatus();
			}
            else{
            	str="";
            }
    	    row.createCell(5).setCellValue(str);
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"StorePhysicalInventrySurveyTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/storeInventoryPdfTemplate/storeInventoryPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfStoreInventory(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<StorePhysicalInventory> storeInventoryTemplateEntities =storePhysicalInventoryService.findAllStorePhysicalInventory();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(6);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
       
       
        table.addCell(new Paragraph("Survey Created Date Time",font1));
        table.addCell(new Paragraph("Survey Started Date Time",font1));
        table.addCell(new Paragraph("Survey Name",font1));
        table.addCell(new Paragraph("Survey Description",font1));
        table.addCell(new Paragraph("Survey Completed Date Time",font1));
        table.addCell(new Paragraph("Status",font1));
        
    	for (StorePhysicalInventory storeInventory :storeInventoryTemplateEntities) {
    		
    		
    		String str="";
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm ");
    		if(storeInventory.getSpiDt()!=null){
				str=sdf.format(storeInventory.getSpiDt());
			}
            else{
            	str="";
            }

             PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
             if(storeInventory.getSurveyDt()!=null){
 				str=sdf.format(storeInventory.getSurveyDt());
 			}
             else{
             	str="";
             }
     		
   
        PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
        if(storeInventory.getSurveyName()!=null){
			str=storeInventory.getSurveyName();
		}
        else{
        	str="";
        }
        PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
        if(storeInventory.getSurveyDescription()!=null){
			str=storeInventory.getSurveyDescription();
		}
        else{
        	str="";
        }
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
        
        
     
        if(storeInventory.getSurveyCompleteDt()!=null){
			str=sdf.format(storeInventory.getSurveyCompleteDt());
		}
        else{
        	str="";
        }
		
   
        PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
        
        if(storeInventory.getSpiStatus()!=null){
			str=storeInventory.getSpiStatus();
		}
        else{
        	str="";
        }
        PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        
        table.setWidthPercentage(100);
        float[] columnWidths = {16f, 16f, 13f, 13f, 18f, 12f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"StorePhysicalInventrySurveyTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/storeIssueTemplate/storeIssueTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportFileStoreIssue(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<StoreIssue> storeIssuesList =  storeIssueService.findAllStoreIssues();
	
               
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
    	
    	header.createCell(0).setCellValue("Job No");
        header.createCell(1).setCellValue("Store Name");
        header.createCell(2).setCellValue("Item Name");
        header.createCell(3).setCellValue("Unit Of Issue ");
        header.createCell(4).setCellValue("Vendor Contract Name");
        header.createCell(5).setCellValue("Issue Quantity");
        header.createCell(6).setCellValue("Issued Date Time");
        header.createCell(7).setCellValue("Reason For Issue");
        header.createCell(8).setCellValue("Ledger Update Date Time");
     
     
     
        for(int j = 0; j<=8; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:J200"));
        }
        
        int count = 1;
       
    	for (StoreIssue storeIssue :storeIssuesList ) {
    		
    		XSSFRow row = sheet.createRow(count);

    		String str="";
    	
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
    		
    		if(storeIssue.getJcMaterials().getJobCards().getJobNo()!=null){
				str=storeIssue.getJcMaterials().getJobCards().getJobNo();
			}
            else{
            	str="";
            }
    		
		    row.createCell(0).setCellValue(str);
		    if(storeIssue.getStoreMaster().getStoreName()!=null){
				str=storeIssue.getStoreMaster().getStoreName();
			}
            else{
            	str="";
            }
    		
    	    row.createCell(1).setCellValue(str);
    	    if(storeIssue.getItemMaster().getImName()!=null){
				str=storeIssue.getItemMaster().getImName();
			}
            else{
            	str="";
            }
    		row.createCell(2).setCellValue(str);
    		if(storeIssue.getUnitOfMeasurement().getUom()!=null){
				str=storeIssue.getUnitOfMeasurement().getUom();
			}
            else{
            	str="";
            }
    		
    	    row.createCell(3).setCellValue(str);
    	    if(storeIssue.getVendorContracts().getContractName()!=null){
				str=storeIssue.getVendorContracts().getContractName();
			}
            else{
            	str="";
            }
    	    row.createCell(4).setCellValue(str);
    	    
    	    
    	    if(storeIssue.getImQuantity()!=null){
    	    	double d=storeIssue.getImQuantity();
    	    	int a=(int)d;
				str=Integer.toString(a);
			}
            else{
            	str="";
            }
    		
		    row.createCell(5).setCellValue(str);
		    if(storeIssue.getStriDt()!=null){
				str=sdf.format(storeIssue.getStriDt());
			}
            else{
            	str="";
            }
    		
    	    row.createCell(6).setCellValue(str);
    	    if(storeIssue.getReasonForIssue()!=null){
				str=storeIssue.getReasonForIssue();
			}
            else{
            	str="";
            }
    		row.createCell(7).setCellValue(str);
    		if(storeIssue.getLedgerUpdateDt()!=null){
				str=sdf.format(storeIssue.getLedgerUpdateDt());
			}
            else{
            	str="";
            }
    		
    	    row.createCell(8).setCellValue(str);
    	    
    		
    		
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"StoreIssueTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/storeIssuePdfTemplate/storeIssuePdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportPdfStoreIssue(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<StoreIssue> storeIssuesList =  storeIssueService.findAllStoreIssues();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(9);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        

       
     
       
        table.addCell(new Paragraph("Job No",font1));
        table.addCell(new Paragraph("Store Name",font1));
        table.addCell(new Paragraph("Item Name",font1));
        table.addCell(new Paragraph("Unit Of Issue",font1));
        table.addCell(new Paragraph("Vendor Contract Name",font1));
        table.addCell(new Paragraph("Issue Quantity",font1));
        table.addCell(new Paragraph("Issued Date Time",font1));
        table.addCell(new Paragraph("Reason For Issue",font1));
        table.addCell(new Paragraph("Ledger Update Date Time",font1));
       
    	for (StoreIssue storeIssue :storeIssuesList ) {
    		
    		String str="";
        	
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
    		
    		if(storeIssue.getJcMaterials().getJobCards().getJobNo()!=null){
				str=storeIssue.getJcMaterials().getJobCards().getJobNo();
			}
            else{
            	str="";
            }
    		
    		PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
		    if(storeIssue.getStoreMaster().getStoreName()!=null){
				str=storeIssue.getStoreMaster().getStoreName();
			}
            else{
            	str="";
            }
    		
		    PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
    	    if(storeIssue.getItemMaster().getImName()!=null){
				str=storeIssue.getItemMaster().getImName();
			}
            else{
            	str="";
            }
    	    PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
    		if(storeIssue.getUnitOfMeasurement().getUom()!=null){
				str=storeIssue.getUnitOfMeasurement().getUom();
			}
            else{
            	str="";
            }
    		
    		PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
    	    if(storeIssue.getVendorContracts().getContractName()!=null){
				str=storeIssue.getVendorContracts().getContractName();
			}
            else{
            	str="";
            }
    	    PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
    	    
    	    
    	    if(storeIssue.getImQuantity()!=null){
    	    	double d=storeIssue.getImQuantity();
    	    	int a=(int)d;
				str=Integer.toString(a);
			}
            else{
            	str="";
            }
    		
    	    PdfPCell cell6 = new PdfPCell(new Paragraph(str,font3));
		    if(storeIssue.getStriDt()!=null){
				str=sdf.format(storeIssue.getStriDt());
			}
            else{
            	str="";
            }
    		
		    PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
    	    if(storeIssue.getReasonForIssue()!=null){
				str=storeIssue.getReasonForIssue();
			}
            else{
            	str="";
            }
    	    PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
    		if(storeIssue.getLedgerUpdateDt()!=null){
				str=sdf.format(storeIssue.getLedgerUpdateDt());
			}
            else{
            	str="";
            }
    		
    		PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));
    	    
    		
    		
    		
    		
      
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
       
        
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 10f, 8f, 10f, 16f, 10f, 14f, 10f, 18f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"StoreIssueTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
	

	/**
	 *  ----------------------------------------- End --------------------------------------------
	 */
}
