package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.CarMake;
import com.bcits.bfm.model.CarModel;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.ParkingSlotsAllotment;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.model.Vehicles;
import com.bcits.bfm.model.WrongParking;
import com.bcits.bfm.service.customerOccupancyManagement.OwnerService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.customerOccupancyManagement.TenantPropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.CarMakeService;
import com.bcits.bfm.service.facilityManagement.CarModelService;
import com.bcits.bfm.service.facilityManagement.OwnerPropertyService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsService;
import com.bcits.bfm.service.facilityManagement.VehicleService;
import com.bcits.bfm.service.facilityManagement.WrongParkingService;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * @author Manjunath Kotagi
 * 
 * Parking Management Controller
 *
 */
@Controller
public class ParkingManagementController extends FilterUnit {
	@Autowired
	private ParkingSlotsService parkingSlotsService;
	@Autowired
	private ParkingSlotsAllotmentService parkingSlotsAllotmentService;
	@Autowired
	private WrongParkingService wrongParkingService;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private OwnerPropertyService ownerPropertyService;
	@Autowired
	private TenantPropertyService tenantPropertyService;
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private CarMakeService carMakeService;
	@Autowired
	private CarModelService carModelService;
	@Autowired
	private PersonService personService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private Validator validator;
	@Autowired
	private BlocksService blocksService;
	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	private static final Log logger = LogFactory
			.getLog(ParkingSlotsAllotmentServiceImpl.class);

	/*------------------------------------------------------ParkingSlots Master--------------------------------------------------------------*/

	
	
	/**
	 * Displaying Parking Slot Master Page
	 * @param model : none
	 * @param request : none
	 * @return parking slots master
	 */
	@RequestMapping("/parkingslots")
	public String parkingSlotsIndex(ModelMap model, HttpServletRequest request) {
		logger.info("Parking Slots Master Displaying");
		HttpSession session = request.getSession(true);
		if(session.getAttribute("XLERRORData")!=null){
			model.addAttribute("XLERRORData", session.getAttribute("XLERRORData"));
		}
		model.addAttribute("ViewName", "Parking Slots Master");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Parking Slots Master", 1, request);
		breadCrumbService.addNode("Manage Parking Slots Master", 2, request);
		session.removeAttribute("XLERRORData");
		return "parkingmanagement/parkingslots";
	}
	
	/**
	 * Reading records of Parking Slot Master
	 * @return parking slots master records
	 */
	@SuppressWarnings("serial")	
	@RequestMapping(value = "/parkingslotsdetails/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readParkingSlots() {
		logger.info("Parking Slots Master Reading Records");
		List<Map<String, Object>> parkingSlots = new ArrayList<Map<String, Object>>();
		for (final ParkingSlots record : parkingSlotsService.findAll()) {
			parkingSlots.add(new HashMap<String, Object>() {
				{
					put("psId", record.getPsId());
					put("block", record.getBlockId().getBlockName());
					put("blockId", record.getBlockId().getBlockId());
					put("psSlotNo", record.getPsSlotNo());
					put("psActiveDate", ConvertDate.TimeStampString(record.getPsActiveDate()));
					put("psOwnership", record.getPsOwnership());
					put("psParkingMethod", record.getPsParkingMethod());
					put("createdBy", record.getCreatedBy());
					put("status", record.getStatus());

				}
			});
		}
		return parkingSlots;
	}
	
	@RequestMapping(value = "/parkingslot/importXL", method = RequestMethod.POST)
	public String importXL(@RequestParam MultipartFile  files,HttpServletRequest request,ModelMap model) throws IOException {
		
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("userId");
		
		try{
			//Create Workbook instance holding reference to .xlsx file
	        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

	        //Get first/desired sheet from the workbook
	        XSSFSheet sheet = workbook.getSheetAt(0);

	        //Iterate through each rows one by one
	        Iterator<Row> rowIterator = sheet.iterator();
	        while (rowIterator.hasNext())
	        {
	            Row row = rowIterator.next();
	            ParkingSlots parkingSlots=new ParkingSlots();
	            parkingSlots.setPsSlotNo(row.getCell(0).getStringCellValue().toUpperCase());
	            Date date=row.getCell(1).getDateCellValue();
	            parkingSlots.setPsActiveDate(new Timestamp(date.getTime()));
	            parkingSlots.setPsOwnership(row.getCell(2).getStringCellValue().toUpperCase());
	            parkingSlots.setPsParkingMethod(row.getCell(3).getStringCellValue().toUpperCase());
	            parkingSlots.setStatus((row.getCell(4).getBooleanCellValue())+"");
	            parkingSlots.setBlockId(blocksService.findbyName(row.getCell(5).getStringCellValue()));
	            parkingSlots.setCreatedBy(userName);
	            parkingSlots.setLastUpdatedBy(userName);
	            parkingSlots.setAllocation_status("false");
	            parkingSlotsService.save(parkingSlots);            
	        }       
		}catch(Exception e){
			e.getMessage();
			if(e.getMessage().contains("Could not commit JPA transaction"))
				session.setAttribute("XLERRORData", "XL File Consists Invalid Date or Duplicate Data");				
			else
				session.setAttribute("XLERRORData", "Unable to Parse XL Sheet,Please Contact Administrator");
		}			
		return "redirect:/parkingslots";
	}

	
	/**
	 * Reading records of Parking Slot Allocation in Hierarchy
	 * @param paramId : parking slot id
	 * @return parking slot allocation details
	 */
	@RequestMapping(value = "/parkingslotsdetails/NestedReadUrl/{paramId}", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readNestedReadUrl(@PathVariable int paramId) {
		logger.info("Parking Slots Allocation Reading Records Inside Parking Slot Master");
		List<?> requestMaster=parkingSlotsService.readNestedReadUrl(paramId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> requestMasterData = null;
		for (Iterator<?> iterator = requestMaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 requestMasterData = new HashMap<String, Object>();
			 
			 requestMasterData.put("owner", (String)values[0]);
			 requestMasterData.put("allotmentDateFrom",ConvertDate.TimeStampString((Timestamp)values[1]));	
			 if(values[2]!=null){
			 requestMasterData.put("allotmentDateTo",ConvertDate.TimeStampString((Timestamp)values[2]));
			 }
			 requestMasterData.put("psRent",(Integer)values[3]);
			 String status = (String)values[4];
				String result1 = "";
				if (status.equalsIgnoreCase("true")) {
					result1 = "Activated";
				} else {
					result1 = "De-Activated";
				}
			 requestMasterData.put("status",result1);
		     result.add(requestMasterData); 
		 }
		return result;
	}

	
	/**
	 * Reading Blocks Details in Parking Slot Master
	 * @return blocks
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/parkingslotsdetails/getBlocks", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getBlocks() {
		logger.info("Parking Slots Master Displaying Blocks into DropDown");
		List<Map<String, Object>> blocks = new ArrayList<Map<String, Object>>();
		for (final Blocks record : blocksService.findAll()) {
			blocks.add(new HashMap<String, Object>() {
				{
					put("blockId", record.getBlockId());
					put("block", record.getBlockName());					
					put("allowedSlot", record.getNumOfParkingSlots());					
					put("noofproperty", record.getNumOfProperties());					
				}
			});
		}
		return blocks;
	}
	
	
	/**
	 * Number of Slot allowed for Specified Block
	 * @param blockId : blockId
	 * @param response : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingslots/getnoofslots/{blockId}")
	public void ParkingSlotStatus(@PathVariable int blockId,HttpServletResponse response) throws IOException {
		parkingSlotsService.getallowedSlots(blockId,response);		
	}
	
	/**
	 * Number of Slot created for Specified Block
	 * @param blockId blockId
	 * @param response none
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingslots/availableSlots/{blockId}")
	public void availableSlots(@PathVariable int blockId,HttpServletResponse response) throws IOException {
		Blocks  b=blocksService.find(blockId);
		PrintWriter out=response.getWriter();
		String str=b.getNumOfParkingSlots()+"";
		out.write(str);
		
	}

	/**
	 * Reading block names to filter
	 * @return bock names
	 */
	@RequestMapping(value = "/parkingslotsdetails/getBlocksForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getBlocksForFilter() {
		logger.info("Parking Slots Master Displaying Blocks into Filter");
		List<ParkingSlots> blocks = parkingSlotsService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlots> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getBlockId().getBlockName());
		}
		return al;
	}

	
	/**
	 * Reading slot names to filter
	 * @return slot names
	 */
	@RequestMapping(value = "/parkingslotsdetails/getSlotForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getSlotForFilter() {
		logger.info("Parking Slots Master Displaying Slots into Filter");
		List<ParkingSlots> blocks = parkingSlotsService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlots> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getPsSlotNo());
		}
		return al;
	}

	/**
	 * Reading slot ownership to filter
	 * @return ownership
	 */
	@RequestMapping(value = "/parkingslotsdetails/getOwnershipForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getOwnershipForFilter() {
		logger.info("Parking Slots Master Displaying Ownership into Filter");
		List<ParkingSlots> blocks = parkingSlotsService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlots> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getPsOwnership());
		}
		return al;
	}

	/**
	 * Reading slot ParkingMethod to filter
	 * @return parkingMethod
	 */
	@RequestMapping(value = "/parkingslotsdetails/getParkingMethodForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getParkingMethodForFilter() {
		logger.info("Parking Slots Master Displaying ParkingMethod into Filter");
		List<ParkingSlots> blocks = parkingSlotsService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlots> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getPsParkingMethod());
		}
		return al;
	}	
	

	/**
	 * Creating Parking Slots Record
	 * @param map : parkingslot details
	 * @param parkingslots : parkingslot Object
	 * @param bindingResult : bindingresult Object
	 * @param request : none
	 * @return parkingslots Object
	 */
	@RequestMapping(value = "/parkingslotsdetails/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createParkingSlots(@RequestBody Map<String, Object> map,
			@ModelAttribute ParkingSlots parkingslots,
			BindingResult bindingResult, HttpServletRequest request) {
		logger.info("Parking Slot Master Adding Records");
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("userId");
		String msg = "";
		JsonResponse errorResponse = new JsonResponse();
		List<ParkingSlots> ps = parkingSlotsService.findAll();
		Iterator<ParkingSlots> it = ps.iterator();
		while (it.hasNext()) {
			ParkingSlots pslot = it.next();
			if (pslot.getPsSlotNo().equalsIgnoreCase(
					map.get("psSlotNo").toString())) {
				msg = "Exists";
			}
		}
		if (!(msg.equals(""))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Parking Slot Already Exists");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			parkingslots = parkingSlotsService.getBeanObject(map);
			validator.validate(parkingslots, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				parkingslots.setCreatedBy(userName);
				parkingslots.setLastUpdatedBy(userName);
				parkingslots = parkingSlotsService.save(parkingslots);
				logger.info("Parking Slot Master Adding Records Successful");
				return parkingslots;
			}
		}

	}

	/**
	 * Updating Parking Slot Master Records
	 * @param model : parkingslot details
	 * @param parkingslots : parkingslot Object
	 * @param bindingResult : bindingresult Object
	 * @param request : none
	 * @return parkingslot Object
	 */
	@RequestMapping(value = "/parkingslotsdetails/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateParkingSlots(@RequestBody Map<String, Object> model,
			@ModelAttribute ParkingSlots parkingslots,
			BindingResult bindingResult,HttpServletRequest request) {
		logger.info("Parking Slot Master Updating Records");
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		String msg = "";
		List<ParkingSlots> ps = parkingSlotsService.findAll();
		Iterator<ParkingSlots> it = ps.iterator();
		while (it.hasNext()) {
			ParkingSlots pslot = it.next();			
			if (pslot.getPsId() != (Integer.parseInt(model.get("psId")
					.toString()))) {
				if (pslot.getPsSlotNo().equalsIgnoreCase(
						model.get("psSlotNo").toString())) {
					msg = "Exists";
				}
			}
		}
		if (!(msg.equals(""))) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Parking Slot Already Exists");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {

			parkingslots = parkingSlotsService.getBeanObject(model);
			validator.validate(parkingslots, bindingResult);

			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				parkingslots.setPsId(Integer.parseInt(model.get("psId").toString()));
				parkingslots.setCreatedBy((String) model.get("createdBy"));
				parkingslots.setLastUpdatedBy(userName);
				parkingslots = parkingSlotsService.update(parkingslots);
				logger.info("Parking Slot Master Updating Records Successful");
				return parkingslots;
			}
		}

	}

	/**
	 * @param psId : parkingSlot id
	 * @param operation : status of activate/deactivate Button
	 * @param response : none
	 */
	@RequestMapping(value = "/parkingslots/ParkingSlotStatus/{psId}/{operation}", method = RequestMethod.POST)
	public void ParkingSlotStatus(@PathVariable int psId,
			@PathVariable String operation, HttpServletResponse response) {
		parkingSlotsService.setParkingSlotStatus(psId, operation, response);
		logger.info("Parking Slot Master Changing Status Successful");
	}

	/*------------------------------------------------------ParkingSlotsAllotment--------------------------------------------------------------*/

	/**
	 * Displaying Parking Slot Allocation Page
	 * @param model : none
	 * @param request : none
	 * @return parking slots allotement
	 */
	@RequestMapping("/parkingslotallotment")
	public String parkingslotsAllotmentIndex(ModelMap model,
			HttpServletRequest request) {
		logger.info("Parking Slots Allocation Displaying Page");
		model.addAttribute("ViewName", "Parking Slot Master");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Parking Slot Master", 1, request);
		breadCrumbService.addNode("Manage Parking Slots Allocation", 2, request);
		return "parkingmanagement/parkingslotallotment";
	}

	/**
	 * Reading Parking Slot Allocation Records
	 * @return parkingslotallocation records
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readParkingslotsallocationdetails() {
		logger.info("Parking Slots Allocation Displaying All Records");
		parkingSlotsAllotmentService.checkTimeout();
		List<?> requestMaster=parkingSlotsAllotmentService.findAllParkingSlot();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> parkingSlotsAllotmentData = null;
		for (Iterator<?> iterator = requestMaster.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 parkingSlotsAllotmentData = new HashMap<String, Object>();
			 
			 parkingSlotsAllotmentData.put("psaId", (Integer)values[0]);
			 parkingSlotsAllotmentData.put("parkingSlotsNo", (String)values[1]);
			 parkingSlotsAllotmentData.put("psId", (Integer)values[2]);
			 parkingSlotsAllotmentData.put("property", (String)values[3]);
			 parkingSlotsAllotmentData.put("propertyId", (Integer)values[4]);
			 parkingSlotsAllotmentData.put("allotmentDateFrom",ConvertDate.TimeStampString((Timestamp)values[5]));	
			 if(values[6]!=null){
				 parkingSlotsAllotmentData.put("allotmentDateTo",ConvertDate.TimeStampString((Timestamp)values[6]));
			 }
			 parkingSlotsAllotmentData.put("psRent",(Integer)values[7]);
			 if (values[8] != null) {
				 parkingSlotsAllotmentData.put("psRentLastRevised", ConvertDate
						 .TimeStampString((Timestamp)values[8]));
			 }
			 if (values[9] != null) {
				 parkingSlotsAllotmentData.put("psRentLastRaised", ConvertDate
						 .TimeStampString((Timestamp)values[9]));
			 }
			 parkingSlotsAllotmentData.put("createdBy",(String)values[10]);
			 parkingSlotsAllotmentData.put("lastUpdatedBy",(String)values[11]);
			 parkingSlotsAllotmentData.put("status",(String)values[12]);
			 
			parkingSlotsAllotmentData.put("block",(String)values[13]);
			parkingSlotsAllotmentData.put("blockId",(Integer)values[14]);
		    result.add(parkingSlotsAllotmentData); 
		 }
		return result;

	}
	
	/**
	 * Checking parking method status
	 * @param psId : parking slot id
	 * @param response : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingslotsAllocation/checkfixed/{psId}")
	public void checkfixed(@PathVariable int psId,HttpServletResponse response) throws IOException {
		logger.info("Parking Slots Allocation getting OwnerShip of Slots");
		String msg=getParkingMethod(psId);
		PrintWriter out = response.getWriter();		
		out.write(msg);
	}


	/**
	 * Reading parking method status
	 * @param psId : parking slot id
	 * @return parkingmethod
	 */
	public String getParkingMethod(int psId) {
		logger.info("Parking Slots Allocation Getting Parking Method Of Slots");
		List<ParkingSlots> ps = parkingSlotsService.findAll();
		String msg = "";
		Iterator<ParkingSlots> it = ps.iterator();
		while (it.hasNext()) {
			ParkingSlots p = it.next();
			if (p.getPsId() == psId) {
				msg = p.getPsParkingMethod();
				if (msg.equalsIgnoreCase("Fixed")) {
					List<ParkingSlotsAllotment> psa = parkingSlotsAllotmentService.findAll();
					Iterator<ParkingSlotsAllotment> it1 = psa.iterator();
					while (it1.hasNext()) {
						if (it1.next().getParkingSlots().getPsId() == psId) {
							msg = "Exists";
						}
					}
				}
			}
		}
		return msg;
	}

	/**
	 * Reading block names from parking slot allocation
	 * @return block names 
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/getBlocksForFilterAllocation", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getBlocksForFilterAllocation() {
		logger.info("Parking Slots Allocation Displaying Blocks into Filter");
		List<ParkingSlotsAllotment> blocks = parkingSlotsAllotmentService
				.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlotsAllotment> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getBlocks().getBlockName());
		}
		return al;
	}

	/**
	 * Reading slot names from parking slot allocation
	 * @return slot names 
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/getSlotForFilterAllocation", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getSlotForFilterAllocation() {
		logger.info("Parking Slots Allocation Displaying Slots into Filter");
		List<ParkingSlotsAllotment> blocks = parkingSlotsAllotmentService
				.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlotsAllotment> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getParkingSlots().getPsSlotNo());
		}
		return al;
	}

	/**
	 * Reading property numbers from parking slot allocation
	 * @return propertynumbers
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/getPropertyForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getPropertyForFilter() {
		logger.info("Parking Slots Allocation Displaying Property into Filter");
		List<ParkingSlotsAllotment> blocks = parkingSlotsAllotmentService
				.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlotsAllotment> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getProperty().getProperty_No());
		}
		return al;
	}

	/**
	 * Reading Rent from parking slot allocation
	 * @return rent
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/getRentForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getRentForFilter() {
		logger.info("Parking Slots Allocation Displaying Rent into Filter");
		List<ParkingSlotsAllotment> blocks = parkingSlotsAllotmentService
				.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<ParkingSlotsAllotment> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getPsRent() + "");
		}
		return al;
	}

	/**
	 * Creating Parking Slot Allocation Records
	 * @param map : parkingslotallocation details
	 * @param psa : parkingslotallocation Object
	 * @param bindingResult : bindingresult Object
	 * @param request : none
	 * @return parkingslotallocation
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createParkingSlotsAllocation(@RequestBody Map<String, Object> map,@ModelAttribute ParkingSlotsAllotment psa,BindingResult bindingResult, HttpServletRequest request) {
		logger.info("Parking Slot Allocation Adding Records");
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		String error = "";
		List<ParkingSlotsAllotment> psa1 = parkingSlotsAllotmentService.findAll();
		Iterator<ParkingSlotsAllotment> it1 = psa1.iterator();
		while (it1.hasNext()) {
			ParkingSlotsAllotment psa2 = it1.next();
			if (psa2.getParkingSlots().getPsId() == Integer.parseInt(map.get("parkingSlotsNo").toString())) {
				if (psa2.getAllotmentDateTo() != null) {
					if (psa2.getAllotmentDateTo().getTime() > ConvertDate.formattedDate(map.get("allotmentDateFrom").toString()).getTime()) {
						error = "Slot Is Not Available Till "+ psa2.getAllotmentDateTo();
					}
				}
			}
		}
		String exist = getParkingMethod(Integer.parseInt(map.get("parkingSlotsNo").toString()));
		if (exist.equalsIgnoreCase("Exists")) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", "Slot Type is Fixed,And Already Assigned");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else if (!(error.equals(""))) {
			final String e = error;
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", e);
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			psa = parkingSlotsAllotmentService.getBeanObject(map);
			validator.validate(psa, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				psa.setCreatedBy(userName);
				psa.setLastUpdatedBy(userName);
				psa = parkingSlotsAllotmentService.save(psa);
				logger.info("Parking Slot Allocation Adding Records Successful");
				return readParkingslotsallocationdetails();
			}
		}
	}

	/**
	 * Updating Parking Slot Allocation Records
	 * @param model : parkingslotallocation details
	 * @param psa  : parkingslotallocation Object
	 * @return parkingslotallocation
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/destroy", method = RequestMethod.POST)
	public @ResponseBody Object destroy(@RequestBody Map<String, Object> model,@ModelAttribute ParkingSlotsAllotment psa) {
		logger.info("Parking Slot Allocation Deleting Record");
		ParkingSlotsAllotment parkingSlotsAllotment = parkingSlotsAllotmentService.find(Integer.parseInt(model.get("psaId").toString()));
		JsonResponse errorResponse = new JsonResponse();

		if (parkingSlotsAllotment.getStatus().equalsIgnoreCase("true")) {
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid",
							"Please De-Allocate Parking Slot Then Delete The Parking Slot");
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			parkingSlotsAllotmentService.setAllocationStatusInParkingSlot(Integer.parseInt(model.get("psaId").toString()), "false");
			parkingSlotsAllotmentService.freeAllocation(parkingSlotsAllotment);
			logger.info("Parking Slot Allocation Deleting Record Successful");
		}
		return readParkingslotsallocationdetails();
	}

	/**
	 * Reading Blocks Details
	 * @return blocks object
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/parkingslotsallocationdetails/getBlocksinAllocation", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getBlocksinAllocation() {
		logger.info("Parking Slots Allocation Displaying Blocks into DropDown");
		List<Map<String, Object>> blocks = new ArrayList<Map<String, Object>>();
		for (final Blocks record : blocksService.findAll()) {
			blocks.add(new HashMap<String, Object>() {
				{
					put("block", record.getBlockName());
					put("blockId", record.getBlockId());
				}
			});
		}
		return blocks;
	}

	/**
	 * Reading Parking Slot Details
	 * @return parkingslot object
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/parkingslotsallocationdetails/getParkingSlots", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getParkingSlots() {
		logger.info("Parking Slots Allocation Displaying Slots into DropDown");
		List<Map<String, Object>> parkingSlots = new ArrayList<Map<String, Object>>();
		for (final ParkingSlots record : parkingSlotsService.getAll()) {
			parkingSlots.add(new HashMap<String, Object>() {
				{
					put("parkingSlotsNo", record.getPsSlotNo());
					put("psId", record.getPsId());
					put("parkingMethod",
							record.getPsOwnership() + " "
									+ record.getPsParkingMethod());
					put("blockId", record.getBlockId().getBlockId());

				}
			});
		}
		return parkingSlots;
	}

	/**
	 * Reading Slots for specified block
	 * @param blockId : blockId
	 * @param response : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingslotsallocationdetails/getParkingSlotsSize/{blockId}", method = RequestMethod.POST)
	public void getParkingSlotsSize(@PathVariable int blockId,
			HttpServletResponse response) throws IOException {
		logger.info("Parking Slots Allocation Displaying Slots size");
		List<ParkingSlots> ps = parkingSlotsService.getAll();
		Iterator<ParkingSlots> it = ps.iterator();	
		int count = 0;
		while (it.hasNext()) {
			ParkingSlots p = it.next();
			if (p.getBlockId().getBlockId() == blockId) {
				count++;
			}
		}
		PrintWriter out = response.getWriter();
		out.print(count);
	}

	/**
	 * Reading Property Details
	 * @return propety Object
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/parkingslotsallocationdetails/getPropertyNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getParkingSlotsProperty() {
		logger.info("Parking Slots Allocation Displaying Properties into DropDown");
		List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();
		for (final Property record : propertyService.findAllforParking()) {
				properties.add(new HashMap<String, Object>() {
					{
						put("propertyId", record.getPropertyId());
						put("property", record.getProperty_No());
						String ownertype="";
						String ownerResident="";
						String person="";
						String persontype="";
						List<OwnerProperty> ownerproperty=ownerPropertyService.getOwnerType(record);
						Iterator<OwnerProperty> it=ownerproperty.iterator();
						while(it.hasNext()){
							OwnerProperty o=it.next();
							String str="";
							if(o.getOwner().getPerson().getLastName()!=null && o.getOwner().getPerson().getLastName()!=""){
								str=o.getOwner().getPerson().getLastName();
							}
							person +=o.getOwner().getPerson().getFirstName()+" "+str+":";
							ownertype+=o.getPrimaryOwner()+":";
							ownerResident+=o.getResidential()+":";
							persontype+="OWNER"+":";
						}										
						List<TenantProperty> tenantProperty=tenantPropertyService.findTenantPropertyBasedOnProperty(record);						
						Iterator<TenantProperty> it1=tenantProperty.iterator();
						while(it1.hasNext()){
							TenantProperty o=it1.next();
							String str="";
							if(o.getTenantId().getPerson().getLastName()!=null && o.getTenantId().getPerson().getLastName()!=""){
								str=o.getTenantId().getPerson().getLastName();
							}
							person +=o.getTenantId().getPerson().getFirstName()+" "+str+":";							
							persontype+="TENANT"+":";
							ownertype+="N/A"+":";
							ownerResident+="N/A"+":";
						}
						put("ownertype",ownertype);
						put("person",person);
						put("ownerResident",ownerResident);
						put("persontype",persontype);											
						
					}
				});
					
		}
		return properties;
	}
	@SuppressWarnings("serial")
	@RequestMapping(value = "/parkingslotsallocationdetails/getAllPropertyNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getAllPropertyNames() {
		logger.info("Parking Slots Allocation Displaying Properties into DropDown");
		List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();
		for (final Property record : propertyService.findAllforParking()) {
				properties.add(new HashMap<String, Object>() {
					{
						put("propertyId", record.getPropertyId());
						put("property", record.getProperty_No());
					}
				});
		}
		return properties;
	}
					
	/**
	 * Reading Number of Slots Available for specified property
	 * @param req : none
	 * @param res : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingSlots/getNumberParkingSlots", method = RequestMethod.POST)
	public void getNumberParkingSlots(HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("Parking Slots Allocation Displaying Number Of Slots Available for Property");
		String str = req.getParameter("vegReg");
		List<Property> vehicle = propertyService.findAllforParking();
		int msg = 0;
		Iterator<Property> it = vehicle.iterator();
		while (it.hasNext()) {
			Property v = it.next();
			int str1 = v.getPropertyId();
			if (str1 == Integer.parseInt(str)) {
				msg = v.getNo_of_ParkingSlots();
				break;
			}
		}
		int count = 0;
		List<ParkingSlotsAllotment> parkingSlots = parkingSlotsAllotmentService
				.findAll();
		Iterator<ParkingSlotsAllotment> it1 = parkingSlots.iterator();
		while (it1.hasNext()) {
			ParkingSlotsAllotment v = it1.next();
			if (v.getStatus().equalsIgnoreCase("true")) {
				if (v.getProperty().getPropertyId() == Integer.parseInt(str)) {
					count++;
				}
			}
		}
		PrintWriter out = res.getWriter();
		out.write((msg - count) + "");
	}
	
	/**
	 * Updating Parking slot Status
	 * @param psId : psId
	 * @param psaId : psaId
	 * @param operation : status of parking slot
	 * @param model : parking slot details
	 * @param request : none
	 * @param response  : none
	 * @param locale specifies locale of system
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingslotsAllocation/ParkingSlotAllocationStatus/{psId}/{psaId}/{operation}", method = RequestMethod.POST)
	public void ParkingSlotAllocationStatus(@PathVariable int psId,
			@PathVariable int psaId, @PathVariable String operation,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response, final Locale locale)
			throws IOException {
		if (operation.equals("allocate")) {
			String str = parkingSlotsAllotmentService
					.getStatusFromParkingSlot(psId);
			if (!(str.equals(""))) {
				PrintWriter out = response.getWriter();
				out.write("Parking Slot Already is Activated To " + str);
			} else {
				parkingSlotsAllotmentService.setParkingSlotAllocationStatus(
						psaId, operation, response);
				parkingSlotsAllotmentService.setAllocationStatusInParkingSlot(
						psaId, "true");
				logger.info("Parking Slot Allocation Changing Status Successful");
			}
		} else {
			parkingSlotsAllotmentService.setParkingSlotAllocationStatus(psaId,
					operation, response);
			parkingSlotsAllotmentService.setAllocationStatusInParkingSlot(
					psaId, "false");
			logger.info("Parking Slot Allocation Changing Status Successful");
		}
	}

	/**
	 * Reading Ownership of Parking Slot
	 * @param psId : psId
	 * @param response : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkingslotsAllocation/getOwnership/{psId}", method = RequestMethod.POST)
	public void getOwnership(@PathVariable int psId,
			HttpServletResponse response) throws IOException {
		logger.info("Parking Slots Allocation getting OwnerShip of Slots");
		List<ParkingSlots> ps = parkingSlotsService.findAll();
		String msg = "";
		PrintWriter out = response.getWriter();
		Iterator<ParkingSlots> it = ps.iterator();
		while (it.hasNext()) {
			ParkingSlots p = it.next();
			if (p.getPsId() == psId) {
				msg = p.getPsOwnership();
				break;
			}
		}
		out.write(msg);
	}

	/*------------------------------------------------Vehicles-------------------------------------------------------------------*/

	/**
	 * Displaying Vehicles Page
	 * @param model : none
	 * @param request : none
	 * @return vehicles
	 */
	@RequestMapping("/vehicles")
	public String vehiclesIndex(ModelMap model, HttpServletRequest request) {
		logger.info("Vehicles Displaying Page");
		model.addAttribute("ViewName", "Parking Slot Master");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Parking Slot Master", 1, request);
		breadCrumbService.addNode("Manage Vehicles", 2, request);
		return "parkingmanagement/vehicles";
	}

	/**
	 * Reading vehicles Details
	 * @return vehicles Object
	 */
	@RequestMapping(value = "/vehicledetails/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readVehicle() {
		logger.info("Vehicles Displaying All Records");
		/*List<Map<String, Object>> vehicles = new ArrayList<Map<String, Object>>();
		for (final Vehicles record : vehicleService.findAll()) {
			vehicles.add(new HashMap<String, Object>() {
				{
					String str="";
					SELECT m.vhId,m.person.firstName,m.person.lastName,m.person.personId,m.vhRegistrationNo,m.vhMake,m.vhModel,
					 m.vhTagNo,m.vhStartDate,m.vhEndDate,m.slotType,m.validSlotsNo,m.createdBy,m.lastUpdatedBy,m.lastUpdatedDate,m.status,
					 m.property.property_No,m.property.property.propertyId FROM Vehicles m ORDER BY m.vhId DESC
					if(record.getPerson().getLastName()!=null){
						str=record.getPerson().getLastName();
					}
					put("vhId", record.getVhId());
					put("owner", record.getPerson().getFirstName() + " "+str );
					put("Idowner", record.getPerson().getPersonId());
					put("vhRegistrationNo", record.getVhRegistrationNo());
					put("vhMake", record.getVhMake());
					put("vhModel", record.getVhModel());
					put("vhTagNo", record.getVhTagNo());
					put("vhStartDate", ConvertDate.TimeStampString(record.getVhStartDate()));
					if (record.getVhEndDate() != null)
						put("vhEndDate", ConvertDate.TimeStampString(record.getVhEndDate()));
					put("slotType", record.getSlotType());
					put("validSlotsNo", record.getValidSlotsNo());
					put("createdBy", record.getCreatedBy());
					put("lastUpdatedBy", record.getLastUpdatedBy());
					put("lastUpdatedDate", ConvertDate.TimeStampString(record.getLastUpdatedDt()));
					put("status", record.getStatus());
					put("property", record.getProperty().getProperty_No());
					put("propertyId", record.getProperty().getPropertyId());
				}
			});
		}
		return vehicles;*/
		List<?> vehicleRecords=vehicleService.findAllVehicals();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> vehicleMasterData = null;
		for (Iterator<?> iterator = vehicleRecords.iterator(); iterator.hasNext();)
		{
			 final Object[] values = (Object[]) iterator.next();
			 vehicleMasterData = new HashMap<String, Object>();
			 String str="";
			 if(values[2]!=null){
					str=(String)values[2];
				}
			 vehicleMasterData.put("vhId", (Integer)values[0]);
			 vehicleMasterData.put("owner", (String)values[1] + " "+str );
			 vehicleMasterData.put("Idowner",(Integer)values[3]);
			 vehicleMasterData.put("vhRegistrationNo",(String)values[4]);
			 vehicleMasterData.put("vhMake",(String)values[5]);
			 vehicleMasterData.put("vhModel", (String)values[6]);
			 vehicleMasterData.put("vhTagNo", (String)values[7]);
			 vehicleMasterData.put("vhStartDate", ConvertDate.TimeStampString((Timestamp)values[8]));
				if (values[9] != null)
					vehicleMasterData.put("vhEndDate", ConvertDate.TimeStampString((Timestamp)values[9]));
				vehicleMasterData.put("slotType",(String) values[10]);
				vehicleMasterData.put("validSlotsNo", (String)values[11]);
				vehicleMasterData.put("createdBy", (String)values[12]);
				vehicleMasterData.put("lastUpdatedBy", (String)values[13]);
				vehicleMasterData.put("lastUpdatedDate", ConvertDate.TimeStampString((Timestamp)values[14]));
				vehicleMasterData.put("status",(String)values[15]);
				vehicleMasterData.put("property",(String)values[16]);
				vehicleMasterData.put("propertyId",(Integer) values[17]);
			
		     result.add(vehicleMasterData); 
		 }
		return result;
	}

	/**
	 * Reading registartion Name into Filter
	 * @return registration name
	 */
	@RequestMapping(value = "/vehicledetails/getRegNameForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getRegNameForFilter() {
		logger.info("Vehicle Displaying Registration Number into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhRegistrationNo());
		}
		return al;
	}
	
	/**
	 * Reading registartion Name into DropDown
	 * @return registration details
	 */
	@RequestMapping(value = "/vehicledetails/getReg", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getReg() {
		logger.info("Vehicle Displaying Registration Number ");
		List<Vehicles> blocks = vehicleService.findAll();
		List<String> al = new ArrayList<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhRegistrationNo());
		}
		return al;
	}

	/**
	 * Reading vehicle make into DropDown
	 * @return vehicle make
	 */
	@RequestMapping(value = "/vehicledetails/getMake", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getMake() {
		logger.info("Vehicle Displaying Car Make");
		List<CarMake> cars = carMakeService.findAll();
		List<String> al = new ArrayList<String>();
		Iterator<CarMake> it = cars.iterator();
		while (it.hasNext()) {
			al.add(it.next().getCarName());
		}
		return al;
	}
	
	/**
	 * Reading vehicle model into DropDown
	 * @return vehicle model
	 */@RequestMapping(value = "/vehicledetails/getModel", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getModel() {
		logger.info("Vehicle Displaying Car Make");
		List<CarModel> cars = carModelService.findAll();
		List<String> al = new ArrayList<String>();
		Iterator<CarModel> it = cars.iterator();
		while (it.hasNext()) {
			al.add(it.next().getModelName());
		}
		return al;
	}
	
	
	 /**
		 * Reading slot Name into Filter
		 * @return slot name
	*/
	 @RequestMapping(value = "/vehicledetails/getSlotForFiltervehicle", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getSlotForFiltervehicle() {
		logger.info("Vehicle Displaying Slots into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getValidSlotsNo());
		}
		return al;
	}

	 /**
		 * Reading property Name into Filter
		 * @return property name
	*/
	@RequestMapping(value = "/vehicledetails/getPropertyForFiltervehicle", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getPropertyForFiltervehicle() {
		logger.info("Vehicle Displaying Owners into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			Vehicles v = it.next();
			String str="";					
			if(v.getPerson().getLastName()!=null){
				str=v.getPerson().getLastName();
			}
			
			al.add(v.getPerson().getFirstName() + " "
					+ str);
		}
		return al;
	}

	 /**
	 * Reading vehicle make into Filter
	 * @return vehicle make
	 */
	@RequestMapping(value = "/vehicledetails/getVehicleMakeForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getVehicleMakeForFilter() {
		logger.info("Vehicle Displaying Vehicles Make into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhMake());
		}
		return al;
	}

	/**
	 * Reading vehicle model into Filter
	 * @return vehicle model
	 */
	@RequestMapping(value = "/vehicledetails/getVehicleModeleForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getVehicleModeleForFilter() {
		logger.info("Vehicle Displaying Vehicle Model into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhModel());
		}
		return al;
	}

	/**
	 * Reading tag name into Filter
	 * @return tag name
	 */
	@RequestMapping(value = "/vehicledetails/getTagNameForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getTagNameForFilter() {
		logger.info("Vehicle Displaying Tag Number into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhTagNo());
		}
		return al;
	}
	
	/**
	 * Reading tag name into drop down
	 * @return tag name
	 */
	@RequestMapping(value = "/vehicledetails/getTag", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getTag() {
		logger.info("Vehicle Displaying Tag Number");
		List<Vehicles> blocks = vehicleService.findAll();
		List<String> al = new ArrayList<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhTagNo());
		}
		return al;
	}
	
	/**
	 * Reading created by into Filter
	 * @return created by
	 */
	@RequestMapping(value = "/vehicledetails/getCreatedByForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getCreatedByForFilter() {
		logger.info("Vehicle Displaying Created By into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getCreatedBy());
		}
		return al;
	}
	
	/**
	 * Reading updated by into Filter
	 * @return updated by
	 */
	@RequestMapping(value = "/vehicledetails/getUpdatedByForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getUpdatedByForFilter() {
		logger.info("Vehicle Displaying Updated By into Filter");
		List<Vehicles> blocks = vehicleService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<Vehicles> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getLastUpdatedBy());
		}
		return al;
	}

	
	/**
	 * Creating Vehicle Records
	 * @param map : vehicle details
	 * @param vehicles : vehicles Object
	 * @param bindingResult : bindingResult Object
	 * @param request : none
	 * @return vehicles details
	 */
	@RequestMapping(value = "/vehicledetails/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createVehicleDetails(@RequestBody Map<String, Object> map,
			@ModelAttribute Vehicles vehicles, BindingResult bindingResult,
			HttpServletRequest request) {
		logger.info("Vehicles Adding Records");
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		String msg = "";
		List<Vehicles> veh = vehicleService.findAll();
		Iterator<Vehicles> it = veh.iterator();
		while (it.hasNext()) {
			Vehicles vehicle = it.next();
			if (vehicle.getVhRegistrationNo().equalsIgnoreCase(
					(String) map.get("vhRegistrationNo"))) {
				msg = "Vehicle Registration Already Exists";
				break;
			}
			if (vehicle.getVhTagNo().equalsIgnoreCase(
					map.get("vhTagNo").toString())) {
				msg = "Vehicle Tag Number Already Exists";
				break;
			}if(vehicle.getValidSlotsNo().equalsIgnoreCase(map.get("validSlotsNo").toString())){
				if(vehicle.getStatus().equalsIgnoreCase("true")){
					if(vehicle.getVhEndDate()!=null){
						if(vehicle.getVhEndDate().getTime()>=ConvertDate.formattedDate(map.get("vhStartDate").toString()).getTime()){
							msg = "This Slot Is Not Available For This Period Of Time";
							break;
						}
					}	
				}							
			}
		}
		if (!(msg.equals(""))) {
			final String error = msg;
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", error);
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			vehicles = vehicleService.getBeanObject(map);
			validator.validate(vehicles, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				vehicles.setCreatedBy(userName);
				vehicles.setLastUpdatedBy(userName);
				vehicles.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				vehicles = vehicleService.save(vehicles);
				logger.info("Vehicles Adding Records Successful");
				return readVehicle();
			}
		}
	}

	/**
	 * Reading owner name and tenant name into drop down
	 * @return ownername and tenantname
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vehicledetails/getOwnerNames", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getOwnerNamesvehicledetails() {
		logger.info("Vehicles Displaying Owners To Dropdown");
		List<OwnerProperty> ownerProperty = ownerPropertyService.findAll();
		List<TenantProperty> tenantProperty = tenantPropertyService.findAll();

		List<OwnerProperty> ownerPropertyList = new ArrayList<OwnerProperty>();
		List<TenantProperty> tenantPropertyList = new ArrayList<TenantProperty>();

		List<Property> property = parkingSlotsAllotmentService.getPropertyId();

		Iterator<Property> it = property.iterator();
		while (it.hasNext()) {
			int ps = it.next().getPropertyId();
			Iterator<OwnerProperty> it1 = ownerProperty.iterator();
			Iterator<TenantProperty> it2 = tenantProperty.iterator();
			while (it1.hasNext()) {
				OwnerProperty op = it1.next();
				int o = op.getPropertyId();
				if (o == ps) {
					ownerPropertyList.add(op);
				}
			}

			while (it2.hasNext()) {
				TenantProperty tp = it2.next();
				if (tp.getProperty().getPropertyId() == ps) {
					tenantPropertyList.add(tp);
				}
			}
		}

		List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
		for (final OwnerProperty record : ownerPropertyList) {
			getOwnerNames.add(new HashMap<String, Object>() {
				{
					String str="";
					if(record.getOwner().getPerson().getLastName()!=null){
						str=record.getOwner().getPerson().getLastName();
					}
					put("owner", record.getOwner().getPerson().getFirstName()
							+ " " + str);
					put("ownerId", record.getOwner().getPerson().getPersonId());
					put("propertyId", record.getProperty().getPropertyId());
					put("personId", record.getOwner().getPerson().getPersonId());
					put("persontType", record.getOwner().getPerson()
							.getPersonType());
				}
			});
		}
		for (final TenantProperty record : tenantPropertyList) {
			getOwnerNames.add(new HashMap<String, Object>() {
				{
					String str="";
					if(record.getTenantId().getPerson().getLastName()!=null){
						str=record.getTenantId().getPerson().getLastName();
					}
					put("owner", record.getTenantId().getPerson()
							.getFirstName()
							+ " "
							+str );
					put("ownerId", record.getTenantId().getPerson()
							.getPersonId());
					put("propertyId", record.getProperty().getPropertyId());
					put("personId", record.getTenantId().getPerson()
							.getPersonId());
					put("persontType", record.getTenantId().getPerson()
							.getPersonType());
				}
			});
		}

		Set<Map<String, Object>> getOwnerNamess = new HashSet<Map<String, Object>>(
				getOwnerNames);
		return getOwnerNamess;
	}

	/**
	 * Reading property name into drop down
	 * @return propertyname
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vehicledetails/getProperty", method = RequestMethod.GET)
	public @ResponseBody
	Set<?> getProperty() {
		logger.info("Vehicles Displaying Properties To Dropdown");
		List<ParkingSlotsAllotment> psa = parkingSlotsAllotmentService
				.findAll();
		List<Property> property = new ArrayList<Property>();
		Iterator<ParkingSlotsAllotment> it = psa.iterator();
		while (it.hasNext()) {
			ParkingSlotsAllotment ps = it.next();

			if (ps.getStatus().equals("true")) {
				property.add(ps.getProperty());
			}
		}
		Set<Map<String, Object>> getProperty = new HashSet<Map<String, Object>>();
		for (final Property record : property) {
			getProperty.add(new HashMap<String, Object>() {
				{
					put("property", record.getProperty_No());
					put("propertyId", record.getPropertyId());
					put("block", record.getBlocks().getBlockName());
					put("floor", record.getProperty_Floor());
				}
			});
		}
		return getProperty;
	}

	/**
	 * Reading slotname into drop down
	 * @return slotname
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vehicledetails/getSlotNumbers", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getSlotNumbersvehicledetails() {
		logger.info("Vehicles Displaying Slots into DropDown");
		List<Map<String, Object>> getOwnerNames = new ArrayList<Map<String, Object>>();
		for (final ParkingSlotsAllotment record : parkingSlotsAllotmentService
				.findAll()) {
			getOwnerNames.add(new HashMap<String, Object>() {
				{
					put("propertyId", record.getProperty().getPropertyId());
					put("validSlotsId", record.getParkingSlots().getPsId());
					put("validSlotsNo", record.getParkingSlots().getPsSlotNo());
					put("parkingMethod", record.getParkingSlots()
							.getPsOwnership()
							+ " "
							+ record.getParkingSlots().getPsParkingMethod());
				}
			});
		}
		return getOwnerNames;
	}

	/**
	 * Reading VehiclesMake into drop down
	 * @return VehiclesMake
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vehicledetails/getVehiclesMake", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getVehiclesMake() {
		logger.info("Vehicles Displaying Vehicle Make To Dropdown");
		List<Map<String, Object>> getVehiclesMake = new ArrayList<Map<String, Object>>();
		for (final CarMake record : carMakeService.findAll()) {
			getVehiclesMake.add(new HashMap<String, Object>() {
				{
					put("carId", record.getCarId());
					put("carName", record.getCarName());
				}
			});
		}
		return getVehiclesMake;
	}

	/**
	 * Reading VehicleModel into drop down
	 * @return VehicleModel
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vehicledetails/getVehicleModel", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getVehiclesModel() {
		logger.info("Vehicles Displaying Vehicle Model To Dropdown");
		List<Map<String, Object>> getVehiclesModel = new ArrayList<Map<String, Object>>();
		for (final CarModel record : carModelService.findAll()) {
			getVehiclesModel.add(new HashMap<String, Object>() {
				{
					put("carId", record.getCarMake().getCarId());
					put("modelName", record.getModelName());
					put("modelId", record.getCarmId());
				}
			});
		}
		return getVehiclesModel;
	}

	/**
	 * Updating Vehicle Records
	 * @param map : Vehicle Details
	 * @param vehicles : vehicles Object
	 * @param bindingResult : bindingResult Object
	 * @param request : none
	 * @return vehicle Details
	 */
	@RequestMapping(value = "/vehicledetails/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateVehicleDetails(@RequestBody Map<String, Object> map,
			@ModelAttribute Vehicles vehicles, BindingResult bindingResult,
			HttpServletRequest request) {
		logger.info("Vehicles Updating Records");
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		String msg = "";
		List<Vehicles> veh = vehicleService.findAll();
		Iterator<Vehicles> it = veh.iterator();
		while (it.hasNext()) {
			Vehicles vehicle = it.next();
			if (vehicle.getVhId() != (Integer.parseInt(map.get("vhId").toString()))) {
				if (vehicle.getVhRegistrationNo().equalsIgnoreCase(
						(String) map.get("vhRegistrationNo"))) {
					msg = "Vehicle Registration Already Exists";
					break;
				}
				if (vehicle.getVhTagNo().equalsIgnoreCase(
						map.get("vhTagNo").toString())) {
					msg = "Vehicle Tag Number Already Exists";
					break;
				}if(vehicle.getValidSlotsNo().equalsIgnoreCase(map.get("validSlotsNo").toString())){
					if(vehicle.getStatus().equalsIgnoreCase("true")){
						if(vehicle.getVhEndDate()!=null){
							if(vehicle.getVhEndDate().getTime()>=ConvertDate.formattedDate(map.get("vhStartDate").toString()).getTime()){
								msg = "This Slot Is Not Available For This Period Of Time";
								break;
							}
						}	
					}							
				}
			}
		}
		if (!(msg.equals(""))) {
			final String error = msg;
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", error);
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			vehicles = vehicleService.getBeanObject(map);
			validator.validate(vehicles, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				vehicles.setVhId(Integer.parseInt(map.get("vhId").toString()));
				vehicles.setCreatedBy((String) map.get("createdBy"));
				vehicles.setLastUpdatedBy(userName);
				vehicles.setLastUpdatedDt(new Timestamp(new Date().getTime()));
				vehicles = vehicleService.update(vehicles);
				logger.info("Vehicles Updating Records Successful");
				return readVehicle();
			}
		}

	}

	/**
	 * Updating Vehicle Status
	 * @param vhId : vhId
	 * @param operation : vehicle status
	 * @param model : vehicle details
	 * @param request : none
	 * @param response : none
	 * @param locale : System Locale
	 */
	@RequestMapping(value = "/vehicles/vehicleStatus/{vhId}/{operation}", method = RequestMethod.POST)
	public void vehicleStatus(@PathVariable int vhId,
			@PathVariable String operation, ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			final Locale locale) {
		vehicleService.setvehicleStatus(vhId, operation, response);
		logger.info("Vehicles Changing Status Successful");
	}

	/*---------------------------------------------------Wrong Parking-----------------------------------------------------------------*/

	/**
	 * Displaying Wrong Parking Page
	 * @param model : none
	 * @param request : none
	 * @return wrongparking
	 */
	@RequestMapping("/wrongparking")
	public String wrongparkingIndex(ModelMap model, HttpServletRequest request) {
		logger.info("Wrong Parking Displaying Page");
		model.addAttribute("ViewName", "Parking Slot Master");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Parking Slot Master", 1, request);
		breadCrumbService.addNode("Manage Wrong Parking", 2, request);
		return "parkingmanagement/wrongparking";
	}

	/**
	 * Reading Wrong Parking Records
	 * @return wrongparking details
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/wrongparking/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readWrongParking() {
		logger.info("Wrong Parking Displaying All Records");
		List<Map<String, Object>> wrongParking = new ArrayList<Map<String, Object>>();
		List<?> wrongParkingList = wrongParkingService.findAllList();

		for (final Iterator<?> i = wrongParkingList.iterator(); i.hasNext();) {
			wrongParking.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					
					put("wpId", (Integer)values[0]);
					put("vehicleNo", (String)values[1]);
					put("vhTagNoWrong", (String)values[2]);
					put("psSlotNo", (String)values[3]);
					put("wpDt", ConvertDate.TimeStampString((Timestamp)values[4]));
					put("actionTaken", (String)values[5]);
					put("createdBy", (String)values[6]);
					put("lastUpdatedBy", (String)values[7]);
					put("lastUpdatedDate", ConvertDate.TimeStampString((Timestamp)values[8]));
					put("status", (String)values[9]);
				}
			});
		}
		return wrongParking;
	}

	/**
	 * Reading RegName into Filter
	 * @return RegName
	 */
	@RequestMapping(value = "/wrongparking/getRegNameForFilterWrongParking", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getRegNameForFilterWrongParking() {
		logger.info("Wrong Parking Displaying Registration Number into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVehicleNo());
		}
		return al;
	}

	/**
	 * Reading TagName into Filter
	 * @return TagName
	 */
	@RequestMapping(value = "/wrongparking/getTagNameForFilterWrongParking", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getTagNameForFilterWrongParking() {
		logger.info("Wrong Parking Displaying Tag Number into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getVhTagNo());
		}
		return al;
	}

	/**
	 * Reading SlotName into Filter
	 * @return SlotName
	 */
	@RequestMapping(value = "/wrongparking/getSlotForFilterWrongParking", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getSlotForFilterWrongParking() {
		logger.info("Wrong Parking Displaying Slot Number into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getPsSlotNo());
		}
		return al;
	}

	/**
	 * Reading Action Taken into Filter
	 * @return actionTaken
	 */
	@RequestMapping(value = "/wrongparking/getActionForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getActionForFilter() {
		logger.info("Wrong Parking Displaying Action Taken into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getActionTaken());
		}
		return al;
	}

	/**
	 * Reading Action Taken into Filter
	 * @return actionTaken
	 */
	@RequestMapping(value = "/wrongparking/getNoticeeForFilter", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getNoticeeForFilter() {
		logger.info("Wrong Parking Displaying Notice Generated into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getStatus());
		}
		return al;
	}
		
	/**
	 * Reading CreatedBy into Filter
	 * @return CreatedBy
	 */
	@RequestMapping(value = "/wrongparking/getCreatedByForFilterWrongParking", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getCreatedByForFilterWrongParking() {
		logger.info("Wrong Parking Displaying Created By into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getCreatedBy());
		}
		return al;
	}

	/**
	 * Reading UpdatedBy into Filter
	 * @return UpdatedBy
	 */
	@RequestMapping(value = "/wrongparking/getUpdatedByForFilterWrongParking", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getUpdatedByForFilterWrongParking() {
		logger.info("Wrong Parking Displaying Updated By into Filter");
		List<WrongParking> blocks = wrongParkingService.findAll();
		Set<String> al = new HashSet<String>();
		Iterator<WrongParking> it = blocks.iterator();
		while (it.hasNext()) {
			al.add(it.next().getLastUpdatedBy());
		}
		return al;
	}

	/**
	 * Reading SlotNumbers into Filter
	 * @return SlotNumbers
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/wrongparking/getSlotNumbers", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getSlotNumbersWrongParking() {
		logger.info("Wrong Parking Displaying Slots into DropDown");
		List<Map<String, Object>> getSlotNames = new ArrayList<Map<String, Object>>();
		for (final ParkingSlots record : parkingSlotsService.findAll()) {
			getSlotNames.add(new HashMap<String, Object>() {
				{
					put("psId", record.getPsId());
					put("psSlotNo", record.getPsSlotNo());
				}
			});
		}
		return getSlotNames;
	}

	/**
	 * Reading wrongSlots into Filter
	 * @return wrongSlots
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/vehicledetails/getwrongSlots/{vehReg}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getwrongSlots(@PathVariable String vehReg) {
		logger.info("Wrong Parking Displaying Slots into Dropdown");
		List<Vehicles> veh = vehicleService.findAll();
		List<ParkingSlots> ps = parkingSlotsService.findAll();
		List<ParkingSlots> pslots = new ArrayList<ParkingSlots>();
		Iterator<Vehicles> it = veh.iterator();
		while (it.hasNext()) {
			Vehicles v = it.next();
			if (v.getVhRegistrationNo().equals(vehReg)) {
				Iterator<ParkingSlots> it1 = ps.iterator();
				while (it1.hasNext()) {
					ParkingSlots p = it1.next();
					if (!(p.getPsSlotNo().equals(v.getValidSlotsNo()))) {
						pslots.add(p);
					}
				}
			}
		}
		List<Map<String, Object>> getSlotNames = new ArrayList<Map<String, Object>>();
		for (final ParkingSlots record : pslots) {
			getSlotNames.add(new HashMap<String, Object>() {
				{
					put("psSlotNo", record.getPsSlotNo());
				}
			});
		}
		return getSlotNames;
	}

	
	/**
	 * Creating Wrong PArking Records
	 * @param map : wrong parking details
	 * @param wrongParking : wrongParking Object
	 * @param bindingResult : bindingResult Object
	 * @param request : none
	 * @return wrongparking Object
	 */
	@RequestMapping(value = "/wrongparking/create", method = RequestMethod.POST)
	public @ResponseBody
	Object addWrongParkingDetails(@RequestBody Map<String, Object> map,
			@ModelAttribute WrongParking wrongParking,
			BindingResult bindingResult, HttpServletRequest request) {
		logger.info("Wrong Parking Adding Records");
		String mobstatus = "web";
	    mobstatus=map.get("createdBy").toString();
	    String userName;
	    if(mobstatus!=null && !(mobstatus.equals(""))){
	     userName = map.get("username").toString();
	    }
	    else{
	    HttpSession session = request.getSession(true);
	    userName = (String) session.getAttribute("userId");
	    }
		JsonResponse errorResponse = new JsonResponse();
		String msg = "";
		List<WrongParking> wp = wrongParkingService.findAll();
		Iterator<WrongParking> it = wp.iterator();
		while (it.hasNext()) {
			WrongParking wparking = it.next();
			if (wparking.getVehicleNo().equalsIgnoreCase(
					(String) map.get("vehicleNo"))) {
				if (wparking.getwpDt().getTime() == ConvertDate.getDate(map.get("wpDt").toString()).getTime()) {
					msg = "Duplicate Wrong Parking Entry For "+ wparking.getVehicleNo() + " At Same Time";
					break;
				}

			}
		}
		if (!(msg.equals(""))) {
			final String error = msg;
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", error);
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			wrongParking = wrongParkingService.getBeanObject(map);
			validator.validate(wrongParking, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				wrongParking.setCreatedBy(userName);
				wrongParking.setLastUpdatedBy(userName);
				wrongParkingService.save(wrongParking);
				logger.info("Wrong Parking Adding Records Successful");
				return readWrongParking();
			}
		}

	}

	/**
	 * Updating Wrong PArking Records
	 * @param map : wrong parking details
	 * @param wrongParking : wrongParking Object
	 * @param bindingResult : bindingResult Object
	 * @param request : none
	 * @return wrongparking Object
	 */
	@RequestMapping(value = "/wrongparking/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateWrongParkingDetails(@RequestBody Map<String, Object> map,
			HttpServletRequest request,
			@ModelAttribute WrongParking wrongParking,
			BindingResult bindingResult) {
		logger.info("Wrong Parking Updating Records");
		String mobstatus = "web";
	    mobstatus=map.get("status").toString();
	    String userName;
	    if(mobstatus==null||mobstatus ==""){
	     userName = map.get("username").toString();
	    }
	    else{
	    HttpSession session = request.getSession(true);
	    userName = (String) session.getAttribute("userId");
	    }
		JsonResponse errorResponse = new JsonResponse();
		String msg = "";

		List<WrongParking> wp = wrongParkingService.findAll();
		Iterator<WrongParking> it = wp.iterator();
		while (it.hasNext()) {
			WrongParking wparking = it.next();
			if(wparking.getWpId()!=Integer.parseInt(map.get("wpId").toString())){
				if (wparking.getVehicleNo().equalsIgnoreCase(
						(String) map.get("vehicleNo"))) {
					if (wparking.getwpDt().getTime() == ConvertDate.formattedDate(
							map.get("wpDt").toString()).getTime()) {
						msg = "Duplicate Wrong Parking Entry For "
								+ wparking.getVehicleNo() + " At Same Time";
						break;
					}

				}
			}
			
		}
		if (!(msg.equals(""))) {
			final String error = msg;
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("invalid", error);
				}
			};
			errorResponse.setStatus("invalid");
			errorResponse.setResult(errorMapResponse);
			return errorResponse;
		} else {
			wrongParking = wrongParkingService.getBeanObject(map);
			validator.validate(wrongParking, bindingResult);
			if (bindingResult.hasErrors()) {
				errorResponse.setStatus("FAIL");
				errorResponse.setResult(bindingResult.getAllErrors());
				return errorResponse;
			} else {
				wrongParking.setWpId(Integer.parseInt(map.get("wpId")
						.toString()));
				wrongParking.setCreatedBy(userName);
				wrongParking.setLastUpdatedBy(userName);
				wrongParkingService.update(wrongParking);
				logger.info("Wrong Parking Updating Records SuccessFul");
				return readWrongParking();
			}
		}

	}

	/**
	 * Reading Registration numbers
	 * @return reg numbers
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/wrongparking/getRegNumbers", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getRegNumbers() {
		logger.info("Wrong Parking Displaying Registration Number into DropDown");
		List<Map<String, Object>> getRegNames = new ArrayList<Map<String, Object>>();
		for (final Vehicles record : vehicleService.findAll()) {
			if(record.getStatus().equalsIgnoreCase("true")){
				getRegNames.add(new HashMap<String, Object>() {
					{
						put("vehicleNo", record.getVhRegistrationNo());
						put("allottedSlot", record.getValidSlotsNo());
						put("personId", record.getPerson().getPersonId());
						String contact=vehicleService.getContacts(record.getPerson().getPersonId());
						put("contact", contact);
						put("firstName", record.getPerson().getFirstName());
						put("lastName", record.getPerson().getLastName());						
					}
				});
			}			
		}
		return getRegNames;
	}	
	
	/**
	 * Reading Tag numebr details
	 * @param req : none
	 * @param res : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/wrongParking/vehicleTagNo", method = RequestMethod.POST)
	public void vehicleTagNo(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		logger.info("Wrong Parking Displaying Tag Number");
		String str = req.getParameter("vegReg");
		List<Vehicles> vehicle = vehicleService.findAll();
		String msg = "";
		Iterator<Vehicles> it = vehicle.iterator();
		while (it.hasNext()) {
			Vehicles v = it.next();
			if (v.getVhRegistrationNo().equalsIgnoreCase(str)) {
				if (v.getStatus().equals("true")) {
					msg = v.getVhTagNo() + "=" + v.getValidSlotsNo();
					break;
				}
			}
		}
		PrintWriter out = res.getWriter();
		out.write(msg);

	}

	/**
	 * Updating WrongParking Status
	 * @param wpId : wpId
	 * @param operation : status of WrongParking
	 * @param response : none
	 * @param locale : System Locale
	 */
	@RequestMapping(value = "/WrongParking/wrongParkingStatus/{wpId}/{operation}", method = RequestMethod.POST)
	public void wrongParkingStatus(@PathVariable int wpId,
			@PathVariable String operation, HttpServletResponse response,
			final Locale locale) {
		wrongParkingService.setWrongParkingStatus(wpId, operation, response,locale);
		logger.info("Wrong Parking Changing Status Successful");
	}

	
	/**
	 * Checking Existance of Parking slot
	 * @param val : parkingslotid
	 * @param model : parkingslot details
	 * @param res : none
	 * @throws IOException
	 */
	@RequestMapping(value = "/wrongparkingSlot/check/{val}", method = RequestMethod.POST)
	public void wrongparkingSlot(@PathVariable int val, ModelMap model,
			HttpServletResponse res) throws IOException {
		logger.info("Wrong Parking Checking Existance of Slot");
		ParkingSlots o = parkingSlotsService.find(val);
		PrintWriter out = res.getWriter();
		try {
			String name = o.getPsSlotNo();
			out.write(name);
		} catch (NullPointerException e) {
			out.write("FAIL");
		}
	}
	
	
	
	/**
	 * Upload wRONG pARKING  IMAGES from mobile
	 * 
	 * @param files			Multipart File
	 * @return          	none
	 * @since           	1.0
	 */
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/wrongparking/upload/{wpId}", method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String save(@RequestParam("file2") MultipartFile files,HttpServletRequest request,@PathVariable int wpId ) {
		// Save the files
		
		logger.info("jobDocId"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		int wParkingId = wpId;
		@SuppressWarnings("unused")
		WrongParking jdObject = null;
	//	for (MultipartFile file : files){
			jdObject =  new WrongParking();
			jdObject = wrongParkingService.find(wParkingId);
			Blob blob;
			try{				
				blob = Hibernate.createBlob(files.getInputStream());
				wrongParkingService.uploadImage(wpId, blob);
			} 
			catch (IOException e){
				e.printStackTrace();
			}		
	//	}		

		return "";
	}
	
	
	@RequestMapping("/downloadImage/{wpId}")
	public String downloadJobDocument(@PathVariable("wpId")	int wpId, HttpServletResponse response) throws Exception{
		WrongParking wpImage = wrongParkingService.find(wpId);
			 try {
		            
		            if(wpImage.getImage() != null){
		            //	response.setHeader("Content-Disposition", "inline;filename=\"" +wpImage.getImageName()+ "\"");
			            OutputStream out = response.getOutputStream();
		            //	response.setContentType(wpImage.getDocumentType());
		            	IOUtils.copy(wpImage.getImage().getBinaryStream(), out);
		            	out.flush();
		            	out.close();
		            }	
		            else{
		            	return "filenotfound";
		            }		           
		        }
			 	catch (IOException e){
		            e.printStackTrace();
		        } catch (SQLException e){
		            e.printStackTrace();
		        }
			 return null;
	}
	
	@RequestMapping(value = "/parkingMasterTemplate/parkingMasterTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSParkingSlotsFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<ParkingSlots> parkingSlotsTemplateEntities = parkingSlotsService.findAll();
		
               
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
    	
        header.createCell(0).setCellValue("Block Name");
        header.createCell(1).setCellValue("Ownership");
        header.createCell(2).setCellValue("Parking Method");
        header.createCell(3).setCellValue("Slot No");
        header.createCell(4).setCellValue("Active Date");        
       
        for(int j = 0; j<=4; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:E200"));
        }
        
        int count = 1;
      
    	for (ParkingSlots parkingSlot :parkingSlotsTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    	

    		row.createCell(0).setCellValue(parkingSlot.getBlockId().getBlockName());
    		row.createCell(1).setCellValue(parkingSlot.getPsOwnership());
    		
    		
    	    row.createCell(2).setCellValue(parkingSlot.getPsParkingMethod());
    		
    		row.createCell(3).setCellValue(parkingSlot.getPsSlotNo());
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		row.createCell(4).setCellValue(sdf.format(parkingSlot.getPsActiveDate()));
    		
    
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"ParkingSlotsTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/parkingMasterPdfTemplate/parkingMasterPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSParkingSlotsPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<ParkingSlots> parkingSlotsTemplateEntities = parkingSlotsService.findAll();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(5);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
        
        
     
        
        table.addCell(new Paragraph("Block Name",font1));
        table.addCell(new Paragraph("Ownership",font1));
        table.addCell(new Paragraph("Parking Method",font1));
        table.addCell(new Paragraph("Slot No",font1));
        table.addCell(new Paragraph("Active Date",font1));
        
    	for (ParkingSlots parkingSlot :parkingSlotsTemplateEntities ) {
    		
    		
    		
             PdfPCell cell1 = new PdfPCell(new Paragraph(parkingSlot.getBlockId().getBlockName(),font3));
       
        PdfPCell cell2 = new PdfPCell(new Paragraph(parkingSlot.getPsOwnership(),font3));
        
		
    
        PdfPCell cell3 = new PdfPCell(new Paragraph(parkingSlot.getPsParkingMethod(),font3));
       
        PdfPCell cell4 = new PdfPCell(new Paragraph(parkingSlot.getPsSlotNo(),font3));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
   
        PdfPCell cell5 = new PdfPCell(new Paragraph(sdf.format(parkingSlot.getPsActiveDate()),font3));
        
  
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
       
        table.setWidthPercentage(100);
        float[] columnWidths = {12f, 10f, 15f, 10f, 10f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"ParkingSlotsTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	@RequestMapping(value = "/parkingAllocationTemplate/parkingAllocationTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSParkingAllocationSlotsFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
        
       
		List<ParkingSlotsAllotment> parkingSlotsAllocationTemplateEntities = parkingSlotsAllotmentService.findAllParkingAllocationSlot();
		
               
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
    	
    	 
       
        header.createCell(0).setCellValue("Block Name");
        header.createCell(1).setCellValue("Parking Slots");
        header.createCell(2).setCellValue("Property Number");
        header.createCell(3).setCellValue("Allotment Date From");
        header.createCell(4).setCellValue("Allotment Date To"); 
        header.createCell(5).setCellValue("Rent Rate");
        header.createCell(6).setCellValue("Rent Last Revised");
        header.createCell(7).setCellValue("Rent Last Raised");
       
        for(int j = 0; j<=7; j++){
    		header.getCell(j).setCellStyle(style);
            sheet.setColumnWidth(j, 8000); 
            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
        }
        
        int count = 1;
      
    	for (ParkingSlotsAllotment parkingSlotAllocation :parkingSlotsAllocationTemplateEntities ) {
    		
    		XSSFRow row = sheet.createRow(count);
    		
    	
            String str="";
            if(parkingSlotAllocation.getBlocks()!=null){
    			if(parkingSlotAllocation.getBlocks().getBlockName()!=null){
    				str=parkingSlotAllocation.getBlocks().getBlockName();
    			}
    			else{
    				str="";
    			}
    			
    		}
    		else{
    			str="";
    		}
    		row.createCell(0).setCellValue(str);
    		if(parkingSlotAllocation.getParkingSlots()!=null){
    			if(parkingSlotAllocation.getParkingSlots().getPsSlotNo()!=null){
    				str=parkingSlotAllocation.getParkingSlots().getPsSlotNo();
    			}
    			else{
    				str="";
    			}
    			
    		}
    		else{
    			str="";
    		}
    		row.createCell(1).setCellValue(str);
    		
    		if(parkingSlotAllocation.getProperty()!=null){
    			if(parkingSlotAllocation.getProperty().getProperty_No()!=null){
    				str=parkingSlotAllocation.getProperty().getProperty_No();
    			}
    			else{
    				str="";
    			}
    			
    		}
    		else{
    			str="";
    		}
    	    row.createCell(2).setCellValue(str);
    	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	    if(parkingSlotAllocation.getAllotmentDateFrom()!=null){
    			str=sdf.format(parkingSlotAllocation.getAllotmentDateFrom());
    		}
    		else{
    			str="";
    		}
    		row.createCell(3).setCellValue(str);
    		if(parkingSlotAllocation.getAllotmentDateTo()!=null){
    			str=sdf.format(parkingSlotAllocation.getAllotmentDateTo());
    		}
    		else{
    			str="";
    		}
    		row.createCell(4).setCellValue(str);
    		row.createCell(5).setCellValue(Integer.toString(parkingSlotAllocation.getPsRent()));
    		if(parkingSlotAllocation.getPsRentLastRevised()!=null){
    			str=sdf.format(parkingSlotAllocation.getPsRentLastRevised());
    		}
    		else{
    			str="";
    		}
    		row.createCell(6).setCellValue(str);
    		if(parkingSlotAllocation.getPsRentLastRaised()!=null){
    			str=sdf.format(parkingSlotAllocation.getPsRentLastRaised());
    		}
    		else{
    			str="";
    		}
    		row.createCell(7).setCellValue(str);
    		
    
    		count ++;
		}
    	
    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
    	wb.write(fileOut);
    	fileOut.flush();
    	fileOut.close();
        
        ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"ParkingSlotsAllocationTemplates.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
	@RequestMapping(value = "/parkingAllocationPdfTemplate/parkingAllocationPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
	public String exportCVSParkingAllocationSlotsPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
		
		
 
		String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
        
       
		List<ParkingSlotsAllotment> parkingSlotsAllocationTemplateEntities = parkingSlotsAllotmentService.findAllParkingAllocationSlot();
	
              
      
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        
       
        
        PdfPTable table = new PdfPTable(8);
        
        Font font1 = new Font(Font.FontFamily.HELVETICA  , 7, Font.BOLD);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 7);
        
        
     
        
        table.addCell(new Paragraph("Block Name",font1));
        table.addCell(new Paragraph("Parking Slots",font1));
        table.addCell(new Paragraph("Property Number",font1));
        table.addCell(new Paragraph("Allotment Date From",font1));
        table.addCell(new Paragraph("Allotment Date To",font1));
        table.addCell(new Paragraph("Rent Rate",font1));
        table.addCell(new Paragraph("Rent Last Revised",font1));
        table.addCell(new Paragraph("Rent Last Raised",font1));
        
    	for (ParkingSlotsAllotment parkingSlotAllocation :parkingSlotsAllocationTemplateEntities ) {
    		
    		
    		String str="";
            if(parkingSlotAllocation.getBlocks()!=null){
    			if(parkingSlotAllocation.getBlocks().getBlockName()!=null){
    				str=parkingSlotAllocation.getBlocks().getBlockName();
    			}
    			else{
    				str="";
    			}
    			
    		}
    		else{
    			str="";
    		}
             PdfPCell cell1 = new PdfPCell(new Paragraph(str,font3));
             if(parkingSlotAllocation.getParkingSlots()!=null){
     			if(parkingSlotAllocation.getParkingSlots().getPsSlotNo()!=null){
     				str=parkingSlotAllocation.getParkingSlots().getPsSlotNo();
     			}
     			else{
     				str="";
     			}
     			
     		}
     		else{
     			str="";
     		}
        PdfPCell cell2 = new PdfPCell(new Paragraph(str,font3));
        
        if(parkingSlotAllocation.getProperty()!=null){
			if(parkingSlotAllocation.getProperty().getProperty_No()!=null){
				str=parkingSlotAllocation.getProperty().getProperty_No();
			}
			else{
				str="";
			}
			
		}
		else{
			str="";
		}
    
        PdfPCell cell3 = new PdfPCell(new Paragraph(str,font3));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    if(parkingSlotAllocation.getAllotmentDateFrom()!=null){
			str=sdf.format(parkingSlotAllocation.getAllotmentDateFrom());
		}
		else{
			str="";
		}
        PdfPCell cell4 = new PdfPCell(new Paragraph(str,font3));
        if(parkingSlotAllocation.getAllotmentDateTo()!=null){
			str=sdf.format(parkingSlotAllocation.getAllotmentDateTo());
		}
		else{
			str="";
		}
        PdfPCell cell5 = new PdfPCell(new Paragraph(str,font3));
        PdfPCell cell6 = new PdfPCell(new Paragraph(Integer.toString(parkingSlotAllocation.getPsRent()),font3));
        if(parkingSlotAllocation.getPsRentLastRevised()!=null){
			str=sdf.format(parkingSlotAllocation.getPsRentLastRevised());
		}
		else{
			str="";
		}
        PdfPCell cell7 = new PdfPCell(new Paragraph(str,font3));
        if(parkingSlotAllocation.getPsRentLastRaised()!=null){
			str=sdf.format(parkingSlotAllocation.getPsRentLastRaised());
		}
		else{
			str="";
		}
        PdfPCell cell8 = new PdfPCell(new Paragraph(str,font3));
        
  
       

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
       
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 13f, 14f, 16f, 13f, 10f, 15f, 14f};

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
		response.setHeader("Content-Disposition", "inline;filename=\"ParkingSlotsAllocationTemplates.pdf"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		//servletOutputStream.w
		servletOutputStream.flush();
		servletOutputStream.close();
		
		return null;
	}
	
	
	
	
	
	
	
}
