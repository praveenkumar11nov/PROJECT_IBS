package com.bcits.bfm.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcits.bfm.model.AccessCards;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.ParkingSlots;
import com.bcits.bfm.model.PreRegisteredVisitors;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.Visitor;
//import com.bcits.bfm.model.VisitorParking;
import com.bcits.bfm.model.VisitorVisits;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardSevice;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.customerOccupancyManagement.PropertyService;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.facilityManagement.MailRoomService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsService;
import com.bcits.bfm.service.facilityManagement.PreRegisteredVisitorService;
//import com.bcits.bfm.service.facilityManagement.VisitorParkingService;
import com.bcits.bfm.service.facilityManagement.VisitorService;
import com.bcits.bfm.service.facilityManagement.VisitorVisitsService;
import com.bcits.bfm.util.FilterUnit;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;























import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



















//import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
public class VisitorManagementController extends FilterUnit {
	
	static Logger logger = LoggerFactory.getLogger(VisitorManagementController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	@Autowired
	private PersonService personService;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private VisitorService visitorService;

	@Autowired
	VisitorVisitsService visitorVisitsService;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private Validator validator;

	@Autowired
	private ParkingSlotsService parkingSlotsService;

	@Autowired
	private MessageSource messageSource;

	
	@Autowired
	private MailRoomService mailService;
	
	@Autowired
	private AccessCardSevice accessCardSevice;
	
	@Autowired
	private PreRegisteredVisitorService preRegisteredVisitorService;
	
	
	@RequestMapping("/storevisitor")
	public String storeVisitorIndex(Model model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Visitor ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Visitor", 1, request);
		breadCrumbService.addNode("Manage Visitor Master", 2, request);
		logger.info("Visitor Master");	
		return "visitor/storevisitor";
	}

	
	/** 
     * this method is used to read visitor details like(Name,Address,Contact Number) from the database
     * @return         : returns the records of visitor's
     *  @see com.bcits.bfm.service.facilityManagement.visitorService#getAllVisitorRecord
     * @since           1.0
     */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/storevisitor/read", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readVisitorPersonalDetails() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Visitor record : visitorService.getAllVisitorRecord()) {
			result.add(new HashMap<String, Object>() {
				{
					put("vmContactNo", record.getVmContactNo());
					put("vmId", record.getVmId());
					put("vmFrom", record.getVmFrom());
					put("vmName", record.getVmName());
					put("gender", record.getGender());
					
					List<VisitorVisits> list=visitorVisitsService.executeSimpleQuery("select vv from VisitorVisits vv where vv.vmId="+record.getVmId());
					for (VisitorVisits vvlist : list) {
						if("OUT".equalsIgnoreCase(vvlist.getVvstatus())){
						put("statusCheck", vvlist.getVvstatus());
						}
					}
				}
			});
		}
		return result;
	}

	
	/** 
     * this method is used to filter visitor contact number like from the database
     * @return         : returns the contact number of visitor's
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getAllVisitorRecord
     * @since           1.0
     */
	@RequestMapping(value = "/storevisitor/getContactNO_filter", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getVisitorContact() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (final Visitor record : visitorService.getAllVisitorRecord()) {
			result.add(new HashMap<String, Object>() {
				{
					put("vmContactNo", record.getVmContactNo());
				}
			});
		}
		return result;
	}

	

	/** 
     * this method is used to update visitor record and to store in  the database
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getVisitorObject
     * @return         : returns the contact number of visitor's
     * @since           1.0
     */
	@RequestMapping(value = "/storevisitor/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateVisitorPersonalDetails(@RequestBody Map<String, Object> map,
			@ModelAttribute("storevisitor") Visitor visitor,
			BindingResult bindingResult, ModelMap model,
			SessionStatus sessionStatus, final Locale locale) {

		logger.info("iam in update");
		visitor = visitorService.getVisitorObject(map, "update", visitor);

		validator.validate(visitor, bindingResult);
		JsonResponse errorResponse = new JsonResponse();

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			logger.info(""+bindingResult);
			return errorResponse;

		} else {

			try {
				visitorService.update(visitor);
				logger.info("updated -");
			} catch (Exception e) {
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorResponse);
				logger.info(""+bindingResult);
				return errorResponse;
			}
		}
		return visitor;
	}

	@RequestMapping("/visitordetails")
	public String VisitorIndex(Model model,HttpServletRequest request) {
		model.addAttribute("ViewName", "Visitor ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Visitor", 1, request);
		breadCrumbService.addNode("Manage Visitors", 2, request);
		logger.info("Visitors");
		return "visitor/visitordetails";
	}

	
	/** 
     * this method is used to update visitor status and to store in database
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#updateVsisitorVisitStatusByButton( vvId, vvstatus, vpStatus, vvLastUpdatedDt, voutDt)
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#findVisitorIn()
     * @return         : returns the status of visitor
     * @since           1.0
     */
	@RequestMapping(value = "/visitordetails/updateVisitorStatus", method = RequestMethod.POST)
	public void updateStatusForDeliveryForSingle(@RequestParam("id1") String id,@RequestParam("reason") String reason,HttpServletResponse response) throws IOException
	{
		Date date=new Date();
		logger.info("id is="+id);
		String idValues[]=id.split(",");
		
		for(int i=0;i<idValues.length;i++)
		{
			List<VisitorVisits> mr=visitorVisitsService.findVisitorIn();
			int ii=Integer.parseInt(idValues[i]);
			Iterator<VisitorVisits> it=mr.iterator();
			while(it.hasNext())
			{
				VisitorVisits m=it.next();
				
				if(m.getVvId()==ii)
				{
					int vmid=m.getVisitor().getVmId();
					String status=m.getVvstatus();
					visitorVisitsService.updateVsisitorVisitStatusByButton(Integer.parseInt(idValues[i]), "OUT","Vacant", new Timestamp(date.getTime()), new Timestamp(date.getTime()),reason);
			}
		}
		}
		PrintWriter out=response.getWriter();
		out.write("Visitor exited from Skyon");
	}
	
	/** 
     * this method is used to read Block Name from  the database
     * @see com.bcits.bfm.service.facilityManagement.blocksService#findAll()
     * @return         : returns the block name and block id  
     * @since           1.0
     */
	@RequestMapping(value = "/visitorvisits/readTowerNames", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getTowerNames() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Blocks record : blocksService.findAll()) {
            result.add(new HashMap<String, Object>() 
            {{
            	put("blockId", record.getBlockId());
            	put("blockName", record.getBlockName());
            }});
        }
		return result;
	}
	
	
	/** 
     * this method is used to read Block Name ,Block Id,Property Number,Property Id from  the database
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getAllVisitorRecord()
     * @return         : returns the block name, Block Id,Property Number,Property Id.  
     * @since           1.0
     */
	@RequestMapping(value = "/visitorvisits/readblocks", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getblockName() {
		logger.info(""+propertyService.findAll().size());
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Property record :mailService.findPropertyNames()) {
			result.add(new HashMap<String, Object>() {
				{
					put("blockId", record.getBlocks().getBlockId());
					put("blockName", record.getBlocks().getBlockName());
					put("propertyId", record.getPropertyId());
					put("property_No", record.getProperty_No());
				}
			});
		}
		
		return result;
	}
	

	
	
	
	/** 
     * this method is used to read visitor details  from  the database having status 'IN'
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getAllVisitorRecord()
     * @return         : returns list visitor records  .
     * @since           1.0
     */
	@RequestMapping(value = "/visitordetails/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readVisitor() {
	//return visitorVisitsService.findVistors();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();   
		for(final VisitorVisits record:visitorVisitsService.findVisitorIn()){
			
			result.add(new HashMap<String,Object>(){
				
				{
					put("vvId", record.getVvId());
					put("category",record.getCategory());
					put("vvstatus",record.getVvstatus());
					put("vpurpose", record.getVPurpose());
					put("vehicleNo", record.getVehicleNo());
					put("reason", record.getReason());
					
					final String timeformat = "dd/MM/yyyy HH:mm";
					SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
					Timestamp intime =record.getVInDt();
					if(intime!=null){
						put("vinDt", sdf.format(intime));
					}
					else{
					}
					Timestamp outtime = record.getVOutDt();
					if(outtime!=null){
					put("voutDt", sdf.format(outtime));
					}
					else{
					}
					put("vpExpectedHours", record.getVpExpectedHours());
					put("vpStatus", record.getVpStatus());
					put("vvCreatedBy",  record.getVvCreatedBy());
					put("vvLastUpdatedBy", record.getVvLastUpdatedBy());
					put("vvLastUpdatedDt", record.getVvLastUpdatedDt());
					if(record.getAcId()==null){
					}else{
						put("acId", record.getAccessCards().getAcId());
						put("acNo", record.getAccessCards().getAcNo());
					}
					put("blockId", record.getProperty().getBlocks().getBlockId());
					put("blockName", record.getProperty().getBlocks().getBlockName());
					put("property_No", record.getProperty().getProperty_No());
					put("propertyId", record.getProperty().getPropertyId());
					if(record.getPsId()==null){
					}else
					{
					put("psId", record.getParkingSlots().getPsId());
					put("psSlotNo", ((VisitorVisits) record).getParkingSlots().getPsSlotNo());
					}
					put("vmId", record.getVisitor().getVmId());
					put("vmContactNo", record.getVisitor().getVmContactNo());
					put("vmFrom",  record.getVisitor().getVmFrom());
					put("vmName", record.getVisitor().getVmName());
					put("gender", record.getVisitor().getGender());
					put("vmCreatedBy", record.getVisitor().getVmCreatedBy());
					put("documentNameType",record.getVisitor().getDocumentNameType());
					put("vmLastUpdatedBy", record.getVisitor().getVmLastUpdatedBy());
					
					put("vmLastUpdatedDt", record.getVisitor().getVmLastUpdatedDt());					
				}
			});
		}
		return result;
	}

	/** 
     * this method is used to read Visitor Master Id from  the database
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getAllVisitorRecord()
     * @return         : returns  Visitor Id.  
     * @since           1.0
     */
	@RequestMapping(value="/visitordetails/filtervmId",method=RequestMethod.GET)
	public @ResponseBody List<?> getvmIdToFilter(){
		List<String> result = new ArrayList<String>();
		List<Visitor> list=visitorService.getAllVisitorRecord();
		for (Iterator<Visitor> iterator = list.iterator(); iterator.hasNext();) {
			Visitor visitor = (Visitor) iterator.next();
			result.add(Integer.toString(visitor.getVmId()));
		}
		return result;
	}
	
	
	
	
	/** 
     * this method is used to read All record of Visitor  from  the database whether status is (IN or OUT)
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#findAll()
     * @return         : returns  list of visitor records.  
     * @since           1.0
     */
	@RequestMapping(value = "/visitorAllRecorddetails/read", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readVisitorAllRecord() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();   
		for(final VisitorVisits record:visitorVisitsService.findAll()){
			
			result.add(new HashMap<String,Object>(){
				
				{
					put("vvId", record.getVvId());
					put("category",record.getCategory());
					put("vvstatus",record.getVvstatus());
					put("vpurpose", record.getVPurpose());
					put("vehicleNo", record.getVehicleNo());
					put("reason", record.getReason());
					final String timeformat = "dd/MM/yyyy HH:mm";
					SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
					Timestamp intime =record.getVInDt();
					if(intime!=null){
						put("vinDt", sdf.format(intime));
					}
					else{
					}
					Timestamp outtime = record.getVOutDt();
					if(outtime!=null){
						logger.info("visitor Out time is "+sdf.format(outtime));
					put("voutDt", sdf.format(outtime));
					}
					else{
						put("voutDt",null);
					}
					put("vpExpectedHours", record.getVpExpectedHours());
					put("vpStatus", record.getVpStatus());
					put("vvCreatedBy",  record.getVvCreatedBy());
					put("vvLastUpdatedBy", record.getVvLastUpdatedBy());
					put("vvLastUpdatedDt", record.getVvLastUpdatedDt());
					if(record.getAcId()==null){
					}else{
						put("acId", record.getAccessCards().getAcId());
						put("acNo", record.getAccessCards().getAcNo());
					}
					put("blockId", record.getProperty().getBlocks().getBlockId());
					put("blockName", record.getProperty().getBlocks().getBlockName());
					put("property_No", record.getProperty().getProperty_No());
					put("propertyId", record.getProperty().getPropertyId());
				
					if(record.getPsId()==null){
					}else
					{
					put("psId", record.getParkingSlots().getPsId());
					put("psSlotNo", ((VisitorVisits) record).getParkingSlots().getPsSlotNo());
					}
					put("vmId",Integer.toString( record.getVisitor().getVmId()));
					put("vmContactNo", record.getVisitor().getVmContactNo());
					put("vmFrom",  record.getVisitor().getVmFrom());
					put("vmName", record.getVisitor().getVmName());
					put("gender", record.getVisitor().getGender());
					put("vmCreatedBy", record.getVisitor().getVmCreatedBy());
					put("vmLastUpdatedBy", record.getVisitor().getVmLastUpdatedBy());
					put("vmLastUpdatedDt", record.getVisitor().getVmLastUpdatedDt());	
					put("documentNameType",record.getVisitor().getDocumentNameType());
				}
			});
		}
		return result;
	}
	
	
	
	
	/** 
     * this method is used to create Visitor record and to store in database('VISITOR_MASTER' and 'VISITOR_VISITS')
     * @return         : returns created  Visitor record information.  
     * @since           1.0
     */
	@RequestMapping(value = "/visitordetails/create", method = RequestMethod.POST)
	public @ResponseBody
	Object createVisitor(@RequestBody Map<String, Object> map,
			@ModelAttribute("visitordetails") VisitorVisits visitorVisits,
			HttpServletRequest request,HttpServletResponse  httpServletResponse, BindingResult bindingResult,
			ModelMap model, SessionStatus sessionStatus, final Locale locale) {
		
		logger.info("-----------inside save of details--------------");
		String username = (String) SessionData.getUserDetails().get("userID");
		HttpSession session = request.getSession(false);
		session.getAttribute("userId");
		logger.info("user name is "+ session.getAttribute("userId"));
		Visitor visitor = new Visitor();
		visitor.setVmCreatedBy(username);
		visitor.setVmLastUpdatedBy(username);
		visitor.setGender((String)map.get("gender"));
		visitor.setVmContactNo((String) map.get("vmContactNo"));
		logger.info("contact no is "+ map.get("vmContactNo"));
		String desc=(String) map.get("vmFrom");
		String desc_trim=desc.trim();
		visitor.setVmFrom(desc_trim);
		visitor.setVmName(WordUtils.capitalizeFully((String) map.get("vmName")));
		visitor.setVmLastUpdatedDt(new Timestamp(new Date().getTime()));
		/*--------------------------visitorvisits------------------------------------*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		visitorVisits.setVvCreatedBy(username);
		visitorVisits.setVvLastUpdatedBy(username);
		if( map.get("acNo")==""){
			logger.info("inside if condition of acNo");
		}else{
			int acId=accessCardSevice.getAccesCardIdBasedOnNo((String) map.get("acNo"));
			visitorVisits.setAcId(acId);	
		}
		if(map.get("psSlotNo")!="" && map.get("vehicleNo")!=""){
			
			logger.info("parking slot number is "+ map.get("psSlotNo"));
			visitorVisits.setVehicleNo((String)map.get("vehicleNo"));
			visitorVisits.setVpExpectedHours((Integer) map.get("vpExpectedHours"));
			int psId=visitorVisitsService.findIdBasedOnParkingSlot((String)map.get("psSlotNo"));
			visitorVisits.setPsId(psId);
			visitorVisits.setVpStatus("Occupied");
			}else{
				visitorVisits.setVpExpectedHours(0);
				visitorVisits.setVpStatus("Vacant");
			}
		visitorVisits.setCategory((String)map.get("category"));
		int propertyId=propertyService.getPropertyId((String)map.get("property_No")).get(0);
		visitorVisits.setPropertyId(propertyId);
		String desc_purpose=(String) map.get("vpurpose");
		String desc_trim_puurpose=desc_purpose.trim();
		visitorVisits.setVPurpose(desc_trim_puurpose);
		visitorVisits.setVvstatus("IN");
		
		java.util.Date date = new java.util.Date();
		
		visitorVisits.setVInDt(new Timestamp(date.getTime()));
		visitorVisits.setVOutDt(null);
		visitorVisits.setVvLastUpdatedDt(new Timestamp(date.getTime()));
		/*-------------------------------------------*/
		validator.validate(visitorVisits, bindingResult);
		logger.info(" bindingResult    " + bindingResult);
		JsonResponse errorResponse = new JsonResponse();
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			logger.info(""+bindingResult);
			return errorResponse;
		} else {
			try {
				visitorService.save(visitor);
				Integer vmId = visitorService.getVisitorIdBasedOnLastUpdateDt(visitor.getVmLastUpdatedDt(), username);
				visitorVisits.setVmId(vmId);
				visitorVisitsService.save(visitorVisits);
			} catch (Exception e) {
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorResponse);
				logger.info(""+bindingResult);
				return errorResponse;
			}
		}
		return visitor;
	}

	/** 
     * this method is used to update Visitor record and to store in database('VISITOR_MASTER' and 'VISITOR_VISITS')
     * @return         : returns updated  Visitor record information.  
     * @since           1.0
     */
	@RequestMapping(value = "/visitordetails/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateVisitor(@RequestBody Map<String, Object> map,@ModelAttribute("visitordetails")VisitorVisits visitorVisits,
			                             HttpServletRequest request, BindingResult bindingResult,
			                             ModelMap model, SessionStatus sessionStatus, final Locale locale) throws ParseException {
		
		logger.info("-----------inside save of details--------------");
		String username = (String) SessionData.getUserDetails().get("userID");

		Visitor visitor = new Visitor();
		/*----------------vistors updation method-----------------*/
		visitor.setVmCreatedBy((String)map.get("vmCreatedBy"));
		visitor.setVmLastUpdatedBy(username);
		visitor.setVmId(Integer.parseInt((String) map.get("vmId")));
		visitor.setVmContactNo((String) map.get("vmContactNo"));
		String descFrom=(String) map.get("vmFrom");
		String descFrom_trim=descFrom.trim();
		visitor.setVmFrom(descFrom_trim);
		visitor.setVmName(WordUtils.capitalizeFully((String) map.get("vmName")));
		visitor.setVmLastUpdatedDt(new Timestamp(new Date().getTime()));
		visitor.setGender((String)map.get("gender"));
		validator.validate(visitor, bindingResult);
		Map.Entry mapEntry = null;
		Map.Entry mapEntryparking=null;
		try {
			/*--------------------------visitorvisits------------------------------------*/
			visitorVisits.setVvId((Integer) map.get("vvId"));

			if (!(((String) map.get("vmName")).length() == 0)) {
				List<Integer> visitorId = visitorService.getVisitorId((String) map.get("vmName"),(String) map.get("vmContactNo"));
				if (visitorId.size() > 0)
					visitorVisits.setVmId(visitorId.get(0));
			}
			
			if(map.get("category")==""){
			}else{
			visitorVisits.setCategory((String)map.get("category"));
			}
			
			visitorVisits.setVvCreatedBy(username);

			logger.info("+++ac Number ios+=+"+map.get("acNo"));
			if((map.get("acNo")!=null) && ( map.get("acNo") instanceof java.util.Map<?,?>))
			{
				Map<?, ?> map1= (Map<?, ?>) map.get("acNo");
				
				Iterator iterator = map1.entrySet().iterator();
				while (iterator.hasNext()) {
					 mapEntry = (Map.Entry) iterator.next();
					 logger.info("The key is: " + mapEntry.getKey()
						+ ",value is :" + mapEntry.getValue());
				}
				int acId=(int) mapEntry.getValue();
				logger.info("acId is="+acId);
				//int acId=accessCardSevice.getAccesCardIdBasedOnNo((String)map.get("acNo"));
				visitorVisits.setAcId(acId);
			}
			else if((map.get("acNo")!=null) && ( map.get("acNo") instanceof java.lang.String))
			{
				int acId=accessCardSevice.getAccesCardIdBasedOnNo((String)map.get("acNo"));
				visitorVisits.setAcId(acId);
				
			}
			
		    List<Integer> propertyId = propertyService.getPropertyId((String) map.get("property_No"));
			if (propertyId.size() > 0){
				
					visitorVisits.setPropertyId(propertyId.get(0));
					
			}else{
					
			}
			
			logger.info("parking slot no is="+map.get("psSlotNo"));
			if((map.get("psSlotNo") != null) && ( map.get("psSlotNo") instanceof java.util.Map<?,?>) && (map.get("vehicleNo") != null) ){
				Map<?, ?> map1= (Map<?, ?>) map.get("psSlotNo");
				Iterator iterator = map1.entrySet().iterator();
				
				while (iterator.hasNext()) {
					 mapEntryparking = (Map.Entry) iterator.next();
					logger.info("The key is: " + mapEntryparking.getKey()
						+ ",value is :" + mapEntryparking.getValue());
				}
				
			visitorVisits.setVehicleNo((String)map.get("vehicleNo"));
			String psSlotNo = (String) map1.get("psSlotNo");
			logger.info("parking slot number is="+psSlotNo);
			int psId = visitorService.getSlotId(psSlotNo).get(0);
			visitorVisits.setPsId(psId);
			visitorVisits.setVpStatus("Occupied");
			visitorVisits.setVpExpectedHours((Integer)map.get("vpExpectedHours"));
			}
			else if((map.get("psSlotNo")!=null) && ( map.get("psSlotNo") instanceof java.lang.String) && (map.get("vehicleNo") != null))
			{
				String psSlotNo = (String) map.get("psSlotNo");
				logger.info("parking slot number is**************="+psSlotNo);
				int psId = visitorService.getSlotId(psSlotNo).get(0);
				visitorVisits.setPsId(psId);
				visitorVisits.setVehicleNo((String)map.get("vehicleNo"));
				visitorVisits.setVpStatus((String) map.get("vpStatus"));
				visitorVisits.setVpExpectedHours((Integer)map.get("vpExpectedHours"));
			}
			else{
				visitorVisits.setVpStatus((String) map.get("vpStatus"));
			}
			String desc_purpose=(String) map.get("vpurpose");
			String desc_trim_purpose=desc_purpose.trim();	
			visitorVisits.setVPurpose(desc_trim_purpose);
			visitorVisits.setVvstatus((String) map.get("vvstatus"));
			String dt = (String) map.get("vinDt");
			DateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date oldDate;
			if (dt != null) {
				oldDate = (Date) oldFormatter.parse(dt);
				visitorVisits.setVInDt(new Timestamp(oldDate.getTime()));
			} 
			else
			{
				
			}
			java.util.Date date = new java.util.Date();
			
			visitorVisits.setVvLastUpdatedDt(new Timestamp(date.getTime()));
			validator.validate(visitorVisits, bindingResult);
			logger.info("saved -");
			} catch (Exception e) {

			e.printStackTrace();

			}
			JsonResponse errorResponse = new JsonResponse();

			if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			logger.info(""+bindingResult);
			return errorResponse;

			} else {

			try {
				visitorService.update(visitor);
				visitorVisitsService.update(visitorVisits);
				logger.info("updated -");
			} catch (Exception e) {
				errorResponse.setStatus("EXCEPTION");
				errorResponse.setResult(errorResponse);
				logger.info(""+bindingResult);
				return errorResponse;
			}
		}
		return visitor;
	}
	

	@RequestMapping(value = "/visitorvisits")
	public String VisitorVisitIndex(ModelMap modelmap,Model model,HttpServletRequest request) {

		modelmap.addAttribute("username",SessionData.getUserDetails().get("userID"));
		model.addAttribute("ViewName", "Visitor Management");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Visitor Entry Details", 1, request);
		logger.info("Visitor Entry Details");

		return "visitor/visitorvisits";
	}

		

	
	

	@RequestMapping(value = "/visitorparking")
	public String VisitorParkingIndex(Model model,HttpServletRequest request) {
		
		model.addAttribute("ViewName", "User Management");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Visitor Parking", 1, request);
		logger.info("Visitor Parking");
		return "visitor/visitorparking";
	}

	

	
	
	

	
	@RequestMapping("/visitorwizard")
	public String storeVisitorwizard(Model model,HttpServletRequest request) {
		model.addAttribute("ViewName", "Visitor ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Visitor", 1, request);
		breadCrumbService.addNode("Manage Visitor Wizard", 2, request);
		logger.info("Visitor Wizard");
		return "visitor/visitorwizard";
	}
	
	
	
	/** 
     * this method is used to check the status of visitor visited whether 'IN' or 'OUT' from  database
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getVisitorVisitsStatusBasedvInDt(int vmId)
     *  @param	contactNo	:contactNo
     *  @param	visitorname	:visitorname
     * @return              : returns status of visitor.  
     * @since                1.0
     */
	@RequestMapping(value="/visitorwizard/setrecord/{contactNo}/{visitorname}",method=RequestMethod.GET)
	public List<Visitor> getVisitorExistence(@PathVariable String contactNo,@PathVariable String visitorname,HttpServletResponse httpServletResponse,ModelMap model) throws IOException{
		
		
		PrintWriter writer=httpServletResponse.getWriter();
		List<Integer>  id=visitorService.getVisitorId(WordUtils.capitalizeFully(visitorname), contactNo);
		if(id.size()>0){
		Integer vmNameId=id.get(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		List vvstatus=visitorVisitsService.getVisitorVisitsStatusBasedvInDt(vmNameId);
		writer.write((String) vvstatus.get(0));
		}else{
			
		}
		return null;
	}
	
	

	/** 
     * this method is used to find the visitor address  from  database , if visitor record is available.
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getvisitorRecord(int vmId)
     *  @param	contactNo	:contactNo
     *  @param	visitorname	:visitorname
     * @return              : returns address of Visitor.  
     * @since                1.0
     */
	@RequestMapping(value="/visitorwizard/setvisitorAddress/{contactNo}/{visitorname}",method=RequestMethod.GET)
	public List<Visitor> getVisitorAddress(@PathVariable String contactNo,@PathVariable String visitorname,HttpServletResponse httpServletResponse,ModelMap model) throws IOException{
		
		
		PrintWriter writer=httpServletResponse.getWriter();
		List<Integer>  id=visitorService.getVisitorId(WordUtils.capitalizeFully(visitorname), contactNo);
		if(id.size()>0){
		Integer vmNameId=id.get(0);
			String visitorlist=visitorService.getvisitorRecord(id.get(0));
			writer.write(visitorlist);
		}
		return null;
		}
	
	
	
	/** 
     * this method is used to create the visitor record  and to store in database('VISITOR_MASTER' and 'VISITOR_VISITS').
     * @return         : returns created record of Visitor.  
     * @since           1.0
     */
	@RequestMapping(value = "/visitorwizard/create", method = RequestMethod.POST)
	public String upload(HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		logger.info("Inside Visitor wizard method");
		
		VisitorVisits visitorVisits = new VisitorVisits();
		int id = 0;
		Integer vmId=0;
		Visitor visitor = new Visitor();
		String name = request.getParameter("vmName").trim();
		String address = request.getParameter("vmFrom");
		String contactNo = request.getParameter("vmContactNo");
		String gender=request.getParameter("gender");
		String username = (String) SessionData.getUserDetails().get("userID");
		logger.info("user Id is " + visitorService.getVisitorId(WordUtils.capitalizeFully(name), contactNo));
		if( visitorService.getVisitorId(WordUtils.capitalizeFully(name), contactNo).size()>0){
		List<Integer> visitorId = visitorService.getVisitorId(WordUtils.capitalizeFully(name), contactNo);
	
			id = visitorId.get(0);
			logger.info("visitor Id is" + id);
			visitorVisits.setVmId(id);
			visitorService.updateAddress(id, address);
		}else{

		visitor.setVmCreatedBy(username);
		visitor.setVmLastUpdatedBy(username);
		visitor.setVmContactNo(contactNo);
		visitor.setVmName(WordUtils.capitalizeFully(name));
		visitor.setVmFrom(address.trim());
		visitor.setVmLastUpdatedDt(new Timestamp(new Date().getTime()));
		visitor.setGender(gender);
		visitorService.save(visitor);
	    vmId = visitorService.getVisitorIdBasedOnLastUpdateDt(visitor.getVmLastUpdatedDt(), username);
		
		visitorVisits.setVmId(vmId);
		}
		String purpose = request.getParameter("vpurpose");
		logger.info("purpose is - " + purpose);
		
		int propertyName=propertyService.getPropertyId(request.getParameter("property_No")).get(0);
		logger.info("property No is"+propertyName);
		
		String category=request.getParameter("category");
		logger.info("category is--"+category);
		
		String parkingstatus = request.getParameter("status");
		
		visitorVisits.setVvCreatedBy(username);
		visitorVisits.setVvLastUpdatedBy(username);
		
		if(request.getParameter("acId")==null){
			
			
		}else{
			logger.info("aceess caerds no is-"+request.getParameter("acId").length());
			int acId=accessCardSevice.getAccesCardIdBasedOnNo(request.getParameter("acId"));
			logger.info("access card number is" + acId);
			visitorVisits.setAcId(acId);
		}
		visitorVisits.setCategory(category);
		visitorVisits.setPropertyId(propertyName);
		visitorVisits.setVPurpose(purpose.trim());
		visitorVisits.setVvstatus("IN");
		java.util.Date date = new java.util.Date();
		
		visitorVisits.setVInDt(new Timestamp(date.getTime()));
		visitorVisits.setVvLastUpdatedDt(new Timestamp(date.getTime()));
		
		
		
		if((request.getParameter("psSlotNo")!="Select-Parking-Slot" && request.getParameter("psSlotNo").length()>0 ) && (request.getParameter("vehicleNo")!="" && request.getParameter("vehicleNo")!=null )){
			
			int expectedhours = Integer.parseInt(request.getParameter("vpExpectedHours"));
			String vehicleNo=request.getParameter("vehicleNo").trim();
			logger.info("vehicle number is --"+vehicleNo);	
			logger.info("parking slot is -"+request.getParameter("psSlotNo"));
			
		int psId=visitorVisitsService.findIdBasedOnParkingSlot(request.getParameter("psSlotNo"));
		visitorVisits.setVehicleNo(vehicleNo);
		visitorVisits.setPsId(psId);
		visitorVisits.setVpStatus("Occupied");
		visitorVisits.setVpExpectedHours(expectedhours);
		
		}
		else{
			logger.info("inside else condition");
			visitorVisits.setVpStatus("Vacant");
			
			visitorVisits.setVpExpectedHours(0);	
		}
		
		visitorVisitsService.save(visitorVisits);

		


		try {
			PrintWriter out = response.getWriter();
			
			List<Integer> listInt =visitorService.getVisitorId(WordUtils.capitalizeFully(name), contactNo);
			logger.info("Visitor ID list-- "+listInt);
			if(listInt.size()>0){
				logger.info("Id is-"+id+"-===-="+listInt.get(0));
			out.write(""+listInt.get(0));
			}else{
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}


	
	@RequestMapping("/visitor/imageUpload")
	public @ResponseBody
	void save(@RequestParam List<MultipartFile> files,
			HttpServletRequest request) {
		// Save the files
		String vmId = request.getParameter("vmId");

		logger.info("VmId From Upload-" + vmId);
		logger.info(" files value is -- " + files);
		Visitor visitorObj = visitorService.find(Integer.parseInt(vmId));
		for (MultipartFile file : files) {

			 String name=file.getOriginalFilename().toString();
				String str[] =  StringUtils.split(name, ".");
			Blob blob;
			try {
				blob = Hibernate.createBlob(file.getInputStream());
				logger.info("blob is -"+blob);
				visitorObj.setVmId(Integer.parseInt(vmId));
				visitorObj.setDocument(blob);
				visitorService.updateVisitorDocument(Integer.parseInt(vmId), blob, str[1]);
				logger.info("In Try block");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("Exception is====="+e);
				e.printStackTrace();
			}

		}
		//return "";
	}
	
	
	
	
	
	@RequestMapping(value = "/visitor/imageUpload/mobile/{vmId}", method ={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	String save(@RequestParam("file2") MultipartFile files,HttpServletRequest request,@PathVariable int vmId ){

		// Save the files
		logger.info("jobDocId"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		//String vmId = request.getParameter("vmId");

		logger.info("VmId From Upload-" + vmId);
		logger.info(" files value is -- " + files);
		Visitor visitorObj = visitorService.find(vmId);
		//for (MultipartFile file : files) {

		MultipartFile	file = files;
			 String name=file.getOriginalFilename().toString();
				String str[] =  StringUtils.split(name, ".");
			Blob blob;
			try {
				blob = Hibernate.createBlob(file.getInputStream());
				logger.info("blob is -"+blob);
				visitorObj.setVmId(vmId);
				visitorObj.setImage(blob);
				visitorObj.setDocument(blob);
				visitorService.updateVisitorDocument(vmId, blob, str[1]);
				visitorService.updateVisitorImage(vmId, blob);
				logger.info("In Try block");

			} catch (IOException e) {

				logger.info("Exception is====="+e);
				e.printStackTrace();
			}


	//	}
		return "";
	}
	
	
	
	
	
	
	/** 
     * this method is used to upload visitor scanned document(images/ pdf) based on visitor Id in the  database
     * @see com.bcits.bfm.service.facilityManagement.visitorService#updateVisitorDocument(int vmId,Blob blob,String documentNameType)
     * @return         :  
     * @since           1.0
     */
	@RequestMapping(value = "/visitor/imageUploaddoc", method = RequestMethod.POST)
	 public @ResponseBody void uploadNoticeDocument(@RequestParam MultipartFile files, HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {

	  String type = null;
	  int vmId= Integer.parseInt(request.getParameter("vmId"));
	  logger.info("file original name is --"+files.getOriginalFilename());
	  String name=files.getOriginalFilename().toString();
	  String str[] =  StringUtils.split(name, ".");
	  
	for(int i=0;i<str.length;i++){
		logger.info(i+"----"+str[i]);
		type=str[str.length-1];
	}
	  Blob blob;
	  blob = Hibernate.createBlob(files.getInputStream());
	  logger.info("blob size is --"+blob.length());
	  visitorService.updateVisitorDocument(vmId, blob,type);
	 }
	
	
	/** 
     * this method is used to upload visitor image based on visitor Id in the  database
     * @see com.bcits.bfm.service.facilityManagement.visitorService#updateVisitorImage(int vmId,Blob blob)
     * @return         :  
     * @since           1.0
     */
	@RequestMapping(value = "/visitor/visitorImageUploaddoc", method = RequestMethod.POST)
	public void upload(MultipartHttpServletRequest multirequest,
			HttpServletResponse response,HttpServletRequest request) throws IOException {
		Iterator<String> itr = multirequest.getFileNames();
		MultipartFile mpf = multirequest.getFile(itr.next());
		byte[] imsgeBytes = mpf.getBytes();

		  int vmId= Integer.parseInt(request.getParameter("vmId"));
		//int vmId=Integer.parseInt(multirequest.getParameter("ravi"));
		logger.info("visitor id is-"+vmId);
		HttpSession session = multirequest.getSession(true);
		String username = (String) session.getAttribute("userId");
		Blob blob;
		blob = Hibernate.createBlob(mpf.getInputStream());
		visitorService.updateVisitorImage(vmId, blob);
		
		//return "visitor/visitordetails";
	}
	
	
	
	
	
	
	
	/** 
     * this method is used to read visitor image based on visitor Id from the  database  to display in grid
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getImage(int vmId)
    * @param	vmId	:vmId  
     * @since           1.0
     */
	@RequestMapping("/vistor/getvisitorimage/{vmId}")
	public String getImage(Model userName, HttpServletRequest request,
			HttpServletResponse response, @PathVariable int vmId)
			throws IOException, SQLException {

		logger.info("Get Image");
		response.setContentType("image/jpeg");
		Blob plist = visitorService.getImage(vmId);
		int blobLength = 0;
		 byte[] blobAsBytes = null;
		 if (plist != null) {
		 blobLength = (int) plist.length();
		 blobAsBytes = plist.getBytes(1, blobLength);
		 }
		OutputStream ot = response.getOutputStream();

		try {
			ot.write(blobAsBytes);
			ot.close();
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
		}

		return null;
	}
	
	/** 
     * this method is used to read visitor image based on visitor Id from the  database to display in visitor wizard
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getImage(int vmId)
     * @param	vmId	:vmId  
     * @since           1.0
     */
	@RequestMapping(value="/vistor/getVisitorImageBasedOnSearch/{vmId}",method=RequestMethod.GET)
	public String getImageBaesdOnSearch(Model userName, HttpServletRequest request,
			HttpServletResponse response,@PathVariable int vmId)
			throws IOException, SQLException {

		logger.info("Get Image");
		logger.info("visitor id is-"+vmId);
		response.setContentType("image/jpeg");
		Blob plist = visitorService.getImage(vmId);
		int blobLength = 0;
		byte[] blobAsBytes=null;
		 if (plist != null) {
		 blobLength = (int) plist.length();
		 blobAsBytes = plist.getBytes(1, blobLength);
		 }
		OutputStream ot = response.getOutputStream();

		try {
			ot.write(blobAsBytes);
			ot.close();
		} catch (Exception e) {
			try {
			} catch (Exception e1) {
			}
		}

		return null;
	}
	
	
	
	
	/** 
     * this method is used to read list of visitor name from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getVisitorNameForFilter()
     * @return         :  list of name
     * @since           1.0
     */
	@RequestMapping(value="/storevisitor/nameForFilter",method=RequestMethod.GET)
	public @ResponseBody List<Visitor> getVisitorNameForFilter(){
	return visitorService.getVisitorNameForFilter();
	}
	
	
	/** 
     * this method is used to read list of expected hours  from the  database to filter the record
     * @return         :  list of expected hours
     * @since           1.0
     */
	@RequestMapping(value="/storevisitor/expectedHoursForFilter",method=RequestMethod.GET)
	public @ResponseBody List<?> getExpectedHoursForFilter(){
		
		List<String> result = new ArrayList<String>();
		return result;
		
	}

	
	/** 
     * this method is used to read list of visitor contact number  from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getVisitorContactNoForFilter()
     * @return         :  list of contact number
     * @since           1.0
     */
	@RequestMapping(value="/storevisitor/contactNoForFilter",method=RequestMethod.GET)
	public @ResponseBody List<Visitor> getVisitorContactNoForFilter(){
		
		return visitorService.getVisitorContactNoForFilter();
	}
	
	/** 
     * this method is used to read list of visitor address from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getVisitorAddressForFilter()
     * @return         :  list of visitor address
     * @since           1.0
     */
	@RequestMapping(value="/storevisitor/addressForFilter",method=RequestMethod.GET)
	public @ResponseBody List<Visitor> getVisitorAddressForFilter(){
		
		return visitorService.getVisitorAddressForFilter();
		
	}
	
	/** 
     * this method is used to read list of property  number from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getPropertyNoForFilter
     * @return         :  list of property number
     * @since           1.0
     */
	@RequestMapping(value="/property/read_propertyNameForFilter",method=RequestMethod.GET)
	public @ResponseBody List<Property> getPropertyNameForFilter(){
		
		return visitorVisitsService.getPropertyNoForFilter();
	
	}
	
	/** 
     * this method is used to read list of vehicle number from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getVisitorVehicleNoForFilter
     * @return         :  list of vehicle number
     * @since           1.0
     */
	@RequestMapping(value="/visitordetails/filterVehicleNo",method=RequestMethod.GET)
	public @ResponseBody List<?> getVehcileNoToFilter(){
		
      return  visitorVisitsService.getVisitorVehicleNoForFilter();
      
	}
	
	/** 
     * this method is used to read list of parking slot number from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getParkingSlot_Vparking_Null()
     *  * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getVisitorParkingSlotslist()
     * @return         :  list of parking slot number
     * @since           1.0
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/visitorparking/parkingslotNo", method = RequestMethod.GET)
	public @ResponseBody
	List<ParkingSlots> readSlotNo() {

		List<ParkingSlots> list = null;
		
		if (visitorVisitsService.findVisitorIn().size() == 0) {
			list=visitorVisitsService.getParkingSlot_Vparking_Null();
		} 
		else {
			list=visitorVisitsService.getVisitorParkingSlotslist();
		}
		return list;
	}
	
	
	/** 
     * this method is used to get list of access card  from the  database
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getfindAccessCardVisitor()
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getfindAccessCardVisitorNotNull()
     * @return         :  list of access card
     * @since           1.0
     */
	@RequestMapping(value="/visitorwizard/getaccessCardNo",method=RequestMethod.GET)
	public @ResponseBody List<?> getAccessCardNo(){
		
		
		List<AccessCards> list=null;
		
		if(visitorVisitsService.findAll().size()==0)
		{
			list=visitorVisitsService.getfindAccessCardVisitor();
		}else{
			list=visitorVisitsService.getfindAccessCardVisitorNotNull();
		}
		
		return list;
	}
	
	/** 
     * this method is used to read list of Access Card Number from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.accessCardSevice#findAll
     * @return         :  list of Access Card Number
     * @since           1.0
     */
	@RequestMapping(value = "/visitordetails/getAccessNoForFilter", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getAccessNoForFilter() {
		

		List<String> result = new ArrayList<String>();
		for (AccessCards record:accessCardSevice.findAll())
		{
			logger.info("access card number is"+ record.getAcNo());
			result.add(record.getAcNo());
		}
		return result;
	}
	
	
	
	
	
	/** 
     * this method is used to read list of parking slot number from the  database to filter the record
     * @see com.bcits.bfm.service.facilityManagement.visitorService#getparkingSlotNoForFilter()
     * @return         :  list of parking slot number
     * @since           1.0
     */
	@RequestMapping(value="/parkingSlot/SlotNo",method=RequestMethod.GET)
	public @ResponseBody List<?> getParkingSlotNoForFilter(){
		
		return visitorService.getparkingSlotNoForFilter();
		
	}
	
	
	
	
	
	
	@RequestMapping(value="/visitorwizard/searchVisitorParkingRecord",method=RequestMethod.GET)
	public @ResponseBody VisitorVisits getVisitorParkingSearchResult(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map) throws IOException  {
		VisitorVisits visitorVisits=null;
		logger.info("visitor id is "+request.getParameter("visitorId"));
		int vmId=Integer.parseInt(request.getParameter("visitorId"));
		Visitor visitor=new Visitor();
		try{
			visitorVisits.setVisitor(visitor);
		 logger.info("visitor visits is-"+visitorVisits);
		 return visitorVisits ;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return visitorVisits;
		
	}
	
	
	
	/** 
     * this method is used to search a list visitor record based on Visitor Id from the  database 
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#getVisitorVisitRecordBasedOnId(vmId)
     * @return         :  visitor record based on Id
     * @since           1.0
     */
	@RequestMapping(value="/visitorwizard/searchVisitorVisitsRecord",method=RequestMethod.GET)
	public @ResponseBody VisitorVisits getVisitorVisitsSearchResult(HttpServletRequest request,HttpServletResponse response) throws IOException  
	{	
		VisitorVisits visitorVisits=null;
		logger.info("id is--"+request.getParameter("visitorId"));
		Visitor visitor=new Visitor();
		int vmId=Integer.parseInt(request.getParameter("visitorId"));
		try
		{
			visitorVisits=(VisitorVisits) visitorVisitsService.getVisitorVisitRecordBasedOnId(vmId);
			logger.info("vistor_visits obj--------"+visitorVisits);
			visitor = visitorVisits.getVisitor();
			visitor.setDocument(null);
			visitor.setImage(null);

			visitorVisits.getProperty().setPropertyDocument(null);
			visitorVisits.setVisitor(visitor);
		
			logger.info("vistor obj----"+visitor);
			return visitorVisits;			 
		}
		catch(Exception e)
		{
		    e.printStackTrace();
			return visitorVisits ;
		}
	}
	
	/********************************** Search Visitor Based on Visitor Name ************************************************/
	@RequestMapping(value="/visitorwizard/searchVisitorBasedOnName",method=RequestMethod.GET)
	public @ResponseBody VisitorVisits searchVisitorBasedOnVisitorName(HttpServletRequest request,HttpServletResponse response) throws IOException  
	{	
		VisitorVisits visitorVisits=null;
		logger.info("Visitor Searching With Name is -------- "+request.getParameter("visitorName"));
		Visitor visitor=new Visitor();
		String visitorName = request.getParameter("visitorName");
		try
		{
			visitorVisits=(VisitorVisits) visitorVisitsService.searchVisitorBasedOnName(visitorName);
			visitor = visitorVisits.getVisitor();
			visitor.setDocument(null);
			visitor.setImage(null);
			visitorVisits.setVisitor(visitor);
			return visitorVisits;			 
		}
		catch(Exception e)
		{
		    e.printStackTrace();
			return visitorVisits ;
		}
	}	
	/********************************** end For Searching Visitor Based on Visitor Name ************************************************/
	
	/********************************** Search Visitor Based on Property Number ************************************************/
	@RequestMapping(value="/visitorwizard/searchVisitorBasedOnProperty",method=RequestMethod.GET)
	public @ResponseBody VisitorVisits searchVisitorBasedOnVisitopropertyNo(HttpServletRequest request,HttpServletResponse response) throws IOException  
	{	
		VisitorVisits visitorVisits=null;
		logger.info("Visitor Searching With Property_No is -------- "+request.getParameter("propertyNo"));
		Visitor visitor=new Visitor();
		String propertyNo = request.getParameter("propertyNo");
		try
		{
			visitorVisits=(VisitorVisits) visitorVisitsService.searchVisitorBasedOnPropertyNo(propertyNo);
			visitor = visitorVisits.getVisitor();
			visitor.setDocument(null);
			visitor.setImage(null);
			visitorVisits.setVisitor(visitor);
			return visitorVisits;			 
		}
		catch(Exception e)
		{
		    e.printStackTrace();
			return visitorVisits ;
		}
	}	
	/********************************** End For Searching Visitor Based on Property Number ************************************************/
	
	
	
	/** 
     * this method is used to update visitor record based on Visitor Id and to store in   database .
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#updateVsisitorVisitStatusByButton(int vvId,String vvstatus,String vpStatus,Timestamp vvLastUpdatedDt,Timestamp voutDt)
     * @return         :  updated message
     * @since           1.0
     */
	@RequestMapping(value="/visitorwizardUpdatesearchRecord/updateSearchRecord",method=RequestMethod.POST)
	public @ResponseBody String updateVisitorRecordBasedOnSearch(HttpServletRequest request){
		Date date=new Date();
		int vmId=Integer.parseInt(request.getParameter("visitorId"));
		//int vvId=Integer.parseInt(request.getParameter("vvId"));
		
		List<Integer> ids=visitorVisitsService.getVisitorVvIdForFilter(vmId);
		logger.info("size-----------------"+ids.size()+"------values-------"+ids);
		if(ids.size()>0)
		{
			String flag="true";
			for(Integer i:ids)
			{
				VisitorVisits status=visitorVisitsService.getSingleResult("select v from VisitorVisits v where v.vvId="+i);
				
				logger.info("Vistor Obj--------------"+status);
				if(!status.getVvstatus().equalsIgnoreCase("OUT"))
				{
					flag="true";
				visitorVisitsService.updateVsisitorVisitStatusByButton(i, "OUT","Vacant", new Timestamp(date.getTime()), new Timestamp(date.getTime()),null);
				}
				else
				{
					flag="false";
				}
				
			}
			if(flag.equals("true"))
			{
				return "Visitor Exits From Skyon";
			}
			else
			{
				return "Vistor Already Exited";
			}
		}
		else
		{
			return "Vistor Id is Not Found";
		}
		//String username = (String) SessionData.getUserDetails().get("userID");
		//visitorVisitsService.updateVsisitorVisitStatusByButton(vvId, "OUT","Vacant", new Timestamp(date.getTime()), new Timestamp(date.getTime()),null);
		//return "Visitor Exits From Ireo";
	}	
	
	/** 
     * this method is used to read visitor record from grid  based on Visitor Id .
     * @see com.bcits.bfm.service.facilityManagement.visitorVisitsService#findVisitorVisitsDetailsBasedOnId(Id)
     * @param		Id	:Id
     * @return          : Visitor record
     * @since           1.0
     */
	@RequestMapping(value="/visitordetails/readVisitorRecordBasedOnSearch/{Id}",method=RequestMethod.GET)
	public @ResponseBody List<?> SearchVisitorBasedOnId(HttpServletRequest request,@PathVariable int Id){
		logger.info("visitor ID is "+Id);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final VisitorVisits record : visitorVisitsService.findVisitorVisitsDetailsBasedOnId(Id)) {
			result.add(new HashMap<String, Object>() {
				{
					put("vvId", record.getVvId());
					put("category",record.getCategory());
					put("vvstatus",record.getVvstatus());
					put("vpurpose", record.getVPurpose());
					put("vehicleNo", record.getVehicleNo());
					final String timeformat = "dd/MM/yyyy HH:mm";
					SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
					Timestamp intime =record.getVInDt();
					if(intime!=null){
						put("vinDt", sdf.format(intime));
					}
					else{
					}
					Timestamp outtime = record.getVOutDt();
					if(outtime!=null){
					put("voutDt", sdf.format(outtime));
					}
					else{
					}
					put("vpExpectedHours", record.getVpExpectedHours());
					put("vpStatus", record.getVpStatus());
					put("vvCreatedBy",  record.getVvCreatedBy());
					put("vvLastUpdatedBy", record.getVvLastUpdatedBy());
					put("vvLastUpdatedDt", record.getVvLastUpdatedDt());
					if(record.getAcId()==null){
						put("acNo", "N/A");
					}else{
						put("acId", record.getAccessCards().getAcId());
						put("acNo", record.getAccessCards().getAcNo());
					}
					put("blockId", record.getProperty().getBlocks().getBlockId());
					put("blockName", record.getProperty().getBlocks().getBlockName());
					put("property_No", record.getProperty().getProperty_No());
					put("propertyId", record.getProperty().getPropertyId());
					
				
					if(record.getPsId()==null){
						put("psSlotNo","N/A");
					}else
					{
					put("psId", record.getParkingSlots().getPsId());
					put("psSlotNo", ((VisitorVisits) record).getParkingSlots().getPsSlotNo());
					}
					put("vmId", record.getVisitor().getVmId());
					put("vmContactNo", record.getVisitor().getVmContactNo());
					put("vmFrom",  record.getVisitor().getVmFrom());
					put("vmName", record.getVisitor().getVmName());
					put("gender", record.getVisitor().getGender());
					put("vmCreatedBy", record.getVisitor().getVmCreatedBy());
					put("vmLastUpdatedBy", record.getVisitor().getVmLastUpdatedBy());
					put("vmLastUpdatedDt", record.getVisitor().getVmLastUpdatedDt());					
				}
			});
		}
		return result;
	}
	
	
	/** 
     * this method is used to store  visitor's captured image in database  based on Visitor Id  .
     * @see com.bcits.bfm.service.facilityManagement.visitorService#updateVisitorImage((int vmId,Blob blob)
     * @param	vmId	:vmId
     * @param  blob     :blob
     * @since           1.0
     */
	@SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping(value = "/visitor/visitorImageCapturedUpload/{vmId}", method = RequestMethod.POST)
	public void upload(HttpServletRequest request,
			HttpServletResponse response,@PathVariable int vmId) throws ServletException, IOException {
		
		  String type=request.getParameter("type");
		  String image=request.getParameter("image");
		  String byteStr = image.split(",")[1];  
		  byte[] bytes = Base64.decodeBase64(byteStr);
		  Blob blob;
		  blob = Hibernate.createBlob(bytes);
		  visitorService.updateVisitorImage(vmId, blob);
			
	}
	
	/** 
     * this method is used to view/download  visitor uploaded document from database .
     * @see com.bcits.bfm.service.facilityManagement.visitorService#find(int vmId)
     * @param	vmId	:vmId
     * @since           1.0
     */
	
	@RequestMapping(value = "/visitor/visitorDocViewUrl/{vmId}")
	public void ssIncidentsStatus(@PathVariable int vmId,HttpServletResponse response) throws IOException
	{
		Visitor visitor = visitorService.find(vmId);
		if(visitor.getDocument()!=null)
		{
			PrintWriter out = response.getWriter();
			out.write("pass");
		}
		else
		{
			PrintWriter out = response.getWriter();
			out.write("fail");
		}
	}
	
	
	@RequestMapping("/visitordetails/document/download/{vmId}")
	public String downloadVisitorDocument(@PathVariable("vmId") Integer vmId, HttpServletResponse response)throws Exception 
	{
		Visitor visitor=visitorService.find(vmId);
			try {
				if (visitor.getDocument() != null) {
					String ext=visitor.getDocumentNameType();
					response.setHeader("Content-Disposition", "inline;filename=\"" +visitor.getVmName()+"."+visitor.getDocumentNameType());
					OutputStream out = response.getOutputStream();
					response.setContentType(visitor.getDocumentNameType());
					IOUtils.copy(visitor.getDocument().getBinaryStream(), out);
					out.flush();
					out.close();
				} else {
					return "Document Not Found";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		
	}
	
	
	
	
	@RequestMapping(value = "/visitorvisits/readValues/{vmId}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> readVisitorVisitBasedOnId(@PathVariable int vmId) {

		
		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (final Object record : visitorVisitsService.findVisitorVisitsDetailsBasedOnId(vmId)) {
			result.add(new HashMap<String, Object>() {
				{
					
					
					put("vmId", ((VisitorVisits) record).getVmId());
					put("vmName", ((VisitorVisits) record).getVisitor().getVmName());
					put("vmContactNo", ((VisitorVisits) record).getVisitor().getVmContactNo());
					
					put("category",((VisitorVisits) record).getCategory());
					//put("gender",((VisitorVisits)record).getGender());
	
							put("vvId", ((VisitorVisits) record).getVvId());
					put("acId", ((VisitorVisits) record).getAcId());
					put("vpurpose", ((VisitorVisits) record).getVPurpose());
					
					final String timeformat = "dd/MM/yyyy HH:mm";
					SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
					
					Timestamp intime = ((VisitorVisits) record).getVInDt();
				if(intime!=null){
						put("vinDt", sdf.format(intime));
					}
					else{
						
						//put("vinDt", null);
					}
					
					
					
				Timestamp outtime = ((VisitorVisits) record).getVOutDt();
				if(outtime!=null){
					put("voutDt", sdf.format(outtime));
					}
					else{
						
						//visitorvisitMap.put("voutDt", null);
					}
					
					
					
					
					
					put("createdBy",
							((VisitorVisits) record).getVvCreatedBy());
					put("lastUpdatedBy",
							((VisitorVisits) record).getVvCreatedBy());

					put("propertyId", ((VisitorVisits) record)
							.getProperty().getPropertyId());
					put("blockName", ((VisitorVisits) record).getProperty().getBlocks().getBlockName());
					put("property_No", ((VisitorVisits) record)
							.getProperty().getProperty_No());
					put("vvstatus", ((VisitorVisits) record).getVvstatus());					
							
				
					
				}
			});
		}

		return result;
	
		// return visitorVisitsService.findAllVisitorVisits();
		//return visitorVisitsService.getVisitorVisitsfields();

	}
		
	@SuppressWarnings({ "serial", "unused" })
	@RequestMapping(value = "/preRequestedUsers/read", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<?> preRequestedUsers() 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();   
		for(final VisitorVisits record:visitorVisitsService.findAll())
		{	
			final String requestedUser = record.getVisitor().getPreRegistereduser();
			if(requestedUser!=null)
	    	{
				result.add(new HashMap<String,Object>()
			    {{
			    
			    	put("vmId",record.getVisitor().getVmId());
			    	put("vmName",record.getVisitor().getVmName());
					put("vmContactNo",record.getVisitor().getVmContactNo());
					put("vmFrom",record.getVisitor().getVmFrom());
					put("visitorPassword",record.getVisitor().getVisitorPassword());
					put("gender", record.getVisitor().getGender());
					put("vmCreatedBy", record.getVisitor().getVmCreatedBy());
					put("vmLastUpdatedBy", record.getVisitor().getVmLastUpdatedBy());
				}});
	    	}
		}
		return result;		
	}
	
	
	//////////////////////////  PRE REGISTERED USERS  /////////////////////////////////////
	@RequestMapping("/preRegisteredVisitors")
	public String preRegisteredVisitorsIndex(Model model, HttpServletRequest request) {
		model.addAttribute("ViewName", "Visitor ");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Visitor", 1, request);
		breadCrumbService.addNode("Manage Pre-Registerd Users", 2, request);
		return "visitor/preRegisteredVisitors";
	}
	
	@RequestMapping(value = "/preRegisteredUsers/readPreRegisteredUsersUrl", method = RequestMethod.POST)
	public @ResponseBody List<?> readVendorIncidentsUrl(HttpServletRequest request)
	{
		HttpSession session=request.getSession(true);
		return preRegisteredVisitorService.getVisitorRequiredDetails();
	}
	
	//For PreRegisteredFilters
	@RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorNameForFilter", method = RequestMethod.GET)
	public @ResponseBody Set<?> getPreRegVisitorNameForFilter()
	{ 
		  Set<String> result = new HashSet<String>();
		    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
			{	
		    	result.add(vc.getVisitorName());		    	
			}
	        return result;
	}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorContactNoForFilter ", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorContactNoForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getVisitorContactNo());		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorGenderForFilter ", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorGenderForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getGender());		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorBlockNameForFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorBlockNameForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getBlocks().getBlockName());		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorPropertyNoForFilter  ", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorPropertyNoForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getProperty().getProperty_No());		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorPasswordForFilter ", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorPasswordForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getVisitorPassword());		    	
				}
		        return result;
		}
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorNoOfVisitorsForFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorNoOfVisitorsForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	int noVis = vc.getNoOfVisitors();
			    	String noOfVisitors = String.valueOf(noVis);
			    	result.add(noOfVisitors);		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorCreatedByForFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorCreatedByForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getCreatedBy());		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value = "/preRegisteredUsers/getPreRegVisitorStatusForFilter", method = RequestMethod.GET)
		public @ResponseBody Set<?> getPreRegVisitorStatusForFilter()
		{ 
			  Set<String> result = new HashSet<String>();
			    for (PreRegisteredVisitors vc : preRegisteredVisitorService.findAll())
				{	
			    	result.add(vc.getStatus());		    	
				}
		        return result;
		}
	  
	  @RequestMapping(value="/visitorwizard/searchPreRegisteredVisitors",method=RequestMethod.GET)
	  public @ResponseBody List<?> searchPreRegisteredVisitors(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException  
	  {
		  String visitorContactNo = request.getParameter("visitorContactNo");
		  String visitorPassword = request.getParameter("visitorPassword");
		  List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (final PreRegisteredVisitors record : preRegisteredVisitorService.executeSimpleQuery("select v from PreRegisteredVisitors v where v.visitorContactNo="+visitorContactNo+" and v.visitorPassword="+visitorPassword+" order by v.viId desc"))
	        {
	            result.add(new HashMap<String, Object>() 
	            {{
	            	int viId = record.getViId();
	            	put("viId", record.getViId());
	            	put("blockId", record.getBlocks().getBlockId());
	            	put("blockName", record.getBlocks().getBlockName());
	            	put("propertyId", record.getProperty().getPropertyId());
	            	put("property_No", record.getProperty().getProperty_No());
	            	
	            	put("vmName",record.getVisitorName());
	            	put("vmContactNo",record.getVisitorContactNo());
	            	put("vmFrom",record.getVisitorFrom());
	            	put("vehicleNo",record.getVehicleNo());
	            	put("gender",record.getGender());
	            	put("parkingRequired",record.getParkingRequired());
	            	
	            }});
	            
	        }
			return result;
	  }
	  
	  
	  @RequestMapping(value = "/preRegisteredVisitor/createPreRegVisitor", method = RequestMethod.POST)
   	  public String uploadPreRegisteredVisitor(HttpServletResponse response,HttpServletRequest request) throws IOException 
   	  {		  
		  String visitorName = request.getParameter("visitorName");
		  String visitorContactNo = request.getParameter("visitorContactNo");
		  String visitorFrom = request.getParameter("visitorFrom");
		  String visitorGender = request.getParameter("visitorGender");
		  String visitorVehicleNo = request.getParameter("visitorVehicleNo");
		  String propertyNo = request.getParameter("propertyNo");
		  String psSlotNo = request.getParameter("psSlotno");
		  String visitorPurpose = request.getParameter("vpurpose");
		  String username = (String) SessionData.getUserDetails().get("userID");
		  Visitor visitor = new Visitor();
		  visitor.setVmName(visitorName);
		  visitor.setVmContactNo(visitorContactNo);
		  visitor.setVmFrom(visitorFrom);
		  visitor.setGender(visitorGender);
		  visitor.setVmCreatedBy(username);
		  visitor.setVmLastUpdatedBy(username);
		  visitor.setVmLastUpdatedDt(new Timestamp(new Date().getTime()));
		  visitorService.save(visitor);
		  
		  int visitorMasterId = visitor.getVmId();
		  VisitorVisits visitorVisits = new VisitorVisits();
		  int propertyId = preRegisteredVisitorService.getPropertyIdBasedOnPropertyNo(propertyNo);		  
		  
		  if(psSlotNo.equalsIgnoreCase("Select-Parking-Slot")){
		  }
		  else{
			  int parkingSlotId = preRegisteredVisitorService.getParkinSlotIdBasedOnPsSlotNo(psSlotNo);
			  visitorVisits.setPsId(parkingSlotId);
		  }
		  
		  visitorVisits.setVmId(visitorMasterId);
		  visitorVisits.setPropertyId(propertyId);
		  visitorVisits.setVPurpose(visitorPurpose);
		  visitorVisits.setVvstatus("IN");
		  visitorVisits.setVehicleNo(visitorVehicleNo);
		  visitorVisits.setVvLastUpdatedDt(new Timestamp(new Date().getTime()));
		  visitorVisitsService.save(visitorVisits);
		  return null;
   	  }

	  @RequestMapping("/vistor/getImageBasedOnSearch")
		public void getImageInVisitorWizardDisplay(Model userName, HttpServletRequest request,HttpServletResponse response, @RequestParam("visitorContactNo") String contactNo)	throws Exception {

			response.setContentType("image/jpeg");
			Blob blobImage = preRegisteredVisitorService.getReRegisteredImageForWizardView(contactNo);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			OutputStream ot = response.getOutputStream();
			try {
				ot.write(blobAsBytes);
				ot.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
			//return null;
		}
	  
	  @RequestMapping("/users/visitor/getVisitorimage/{viId}")
		public void getImageForPreRegisteredView(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int viId)	throws Exception {

			response.setContentType("image/jpeg");
			Blob blobImage = preRegisteredVisitorService.getImageForPreRegisteredView(viId);
			int blobLength = 0;
			byte[] blobAsBytes = null;
			if (blobImage != null) {
				blobLength = (int) blobImage.length();
				blobAsBytes = blobImage.getBytes(1, blobLength);
			}
			OutputStream ot = response.getOutputStream();
			try {
				ot.write(blobAsBytes);
				ot.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
			//return null;
		}
	  
	  @RequestMapping(value = "/storeVisitorTemplate/storeVisitorTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportCVSVisitorMasterFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
	        
	       
			List<Visitor> visitorTemplateEntities = visitorService.getAllVisitorRecord();
		
	               
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
	    	
	        header.createCell(0).setCellValue("Contact-No");
	        header.createCell(1).setCellValue("Visitor-Name");
	        header.createCell(2).setCellValue("Gender");
	        header.createCell(3).setCellValue("Address");
	       
	        for(int j = 0; j<=3; j++){
	    		header.getCell(j).setCellStyle(style);
	            sheet.setColumnWidth(j, 8000); 
	            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:D200"));
	        }
	        
	        int count = 1;
	     
	    	for (Visitor visitor :visitorTemplateEntities ) {
	    		
	    		XSSFRow row = sheet.createRow(count);
	    	

	    		row.createCell(0).setCellValue(visitor.getVmContactNo());
	    		row.createCell(1).setCellValue(visitor.getVmName());
	    		
	    		
	    	    row.createCell(2).setCellValue(visitor.getGender());
	    		
	    		row.createCell(3).setCellValue(visitor.getVmFrom());
	    		
	    		count ++;
			}
	    	
	    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
	    	wb.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
	        
	        ServletOutputStream servletOutputStream;

			servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline;filename=\"VisitorMasterTemplates.xlsx"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
	  
	  @RequestMapping(value = "/storeVisitorPdfTemplate/storeVisitorPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
		public String exportVisitorMasterPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
			
			
	 
			String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
	        
	       
			List<Visitor> visitorTemplateEntities = visitorService.getAllVisitorRecord();
		
			List<Object[]> domesticTemplateEntities =personService.getAllDomestic();    
	      
	        Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        document.open();
	        
	       
	        
	        PdfPTable table = new PdfPTable(5);
	        
	        Font font1 = new Font(Font.FontFamily.HELVETICA  , 9, Font.BOLD);
	        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
	        
	     
	        table.addCell(new Paragraph("Image",font1));
	        table.addCell(new Paragraph("Contact-No",font1));
	        table.addCell(new Paragraph("Visitor-Name",font1));
	        table.addCell(new Paragraph("Gender",font1));
	        table.addCell(new Paragraph("Address",font1));
	       
	       
	    	for (Visitor visitor :visitorTemplateEntities) {
	    		
	    		Blob blobImage = visitorService.getImage(visitor.getVmId());
	    		int blobLength = 0;
	    		 Image image2=null;
	    		byte[] blobAsBytes = null;
	    		if (blobImage != null) {
	    			blobLength = (int) blobImage.length();
	    			blobAsBytes = blobImage.getBytes(1, blobLength);
	    			
	    			image2 = Image.getInstance(blobAsBytes);
	    		}
	    		
	    		else{
	    			 for (Object[] domestic :domesticTemplateEntities) {
	    		           
	    			          
	    					
	    		    		 Blob blobImage1 = personService.getImage((Integer)domestic[0]);
	    		    	
	    		    	
	    		    		if (blobImage1 != null) {
	    		    			blobLength = (int) blobImage1.length();
	    		    			blobAsBytes = blobImage1.getBytes(1, blobLength);
	    		    			image2= Image.getInstance(blobAsBytes);
	    		    		}
	    		    		// Image image2 = Image.getInstance(blobAsBytes);
	    		       
	    		
	    			//image2.scaleAbsolute(60f, 60f);
	    		}
	    		}
	    		//java.awt.Image image = new ImageIcon(this.getClass().getResource("/resources/images/grandArch2.png")).getImage();
	        PdfPCell cell1 = new PdfPCell(image2,true);
	    		
	        PdfPCell cell2 = new PdfPCell(new Paragraph(visitor.getVmContactNo(),font3));
	       
	        PdfPCell cell3 = new PdfPCell(new Paragraph(visitor.getVmName(),font3));
	        
	    
	        PdfPCell cell4 = new PdfPCell(new Paragraph(visitor.getGender(),font3));
	       
	        PdfPCell cell5 = new PdfPCell(new Paragraph(visitor.getVmFrom(),font3));
	        
	   

	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        
	       
	        table.setWidthPercentage(100);
	        float[] columnWidths = {5f, 8f, 8f, 8f, 8f};

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
			response.setHeader("Content-Disposition", "inline;filename=\"VisitorMasterTemplates.pdf"+"\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			//servletOutputStream.w
			servletOutputStream.flush();
			servletOutputStream.close();
			
			return null;
		}
		
	  @RequestMapping(value = "/visitorTemplate/visitorTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
			public String exportCVSVisitorFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
				
				
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
		        
		       
				List<VisitorVisits> visitorTemplateEntities = visitorVisitsService.findVisitorIn();
			
		               
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
		    	
		        header.createCell(0).setCellValue("Visitor Id");
		        header.createCell(1).setCellValue("Contact Number");
		        header.createCell(2).setCellValue("Visitor Name");
		        header.createCell(3).setCellValue("Address");
		        header.createCell(4).setCellValue("Gender");
		        header.createCell(5).setCellValue("Category");
		        header.createCell(6).setCellValue("Property No");
		        header.createCell(7).setCellValue("Access Card No");
		        header.createCell(8).setCellValue("Purpose Of Visit");
		        header.createCell(9).setCellValue("Visitor Status");
		        header.createCell(10).setCellValue("Exit Reason");
		        header.createCell(11).setCellValue("Entry Date Time");
		        header.createCell(12).setCellValue("Exit Date Time");
		        header.createCell(13).setCellValue("Vehicle No");
		        header.createCell(14).setCellValue("Parking Slot No");
		        header.createCell(15).setCellValue("Expected hours");
		        header.createCell(16).setCellValue("Parking-Status");
		       
		       
		       
		        for(int j = 0; j<=16; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:Q200"));
		        }
		        
		        int count = 1;
		     
		    	for (VisitorVisits visitor :visitorTemplateEntities ) {
		    		
		    		XSSFRow row = sheet.createRow(count);
		    		
		    		
		    		row.createCell(0).setCellValue(visitor.getVisitor().getVmId());
		    		row.createCell(1).setCellValue(visitor.getVisitor().getVmContactNo());
		    		
		    		
		    	    row.createCell(2).setCellValue(visitor.getVisitor().getVmName());
		    		
		    		row.createCell(3).setCellValue(visitor.getVisitor().getVmFrom());
		    		row.createCell(4).setCellValue(visitor.getVisitor().getGender());
		    		row.createCell(5).setCellValue(visitor.getCategory());
		    		
		    		
		    	    row.createCell(6).setCellValue(visitor.getProperty().getProperty_No());
		    
		    	    if(visitor.getAccessCards()!=null )
	    		    {
	    		    	if(visitor.getAccessCards().getAcNo()!=null)
	    		    	  row.createCell(7).setCellValue(visitor.getParkingSlots().getPsSlotNo());	
	    		    }
	    		    else
	    		    {
	    		    	row.createCell(7).setCellValue("");		
	    		    }
	    		
		    		
		    		row.createCell(8).setCellValue(visitor.getVPurpose());
		    		row.createCell(9).setCellValue(visitor.getVvstatus());
		    		
		    		
		    	    row.createCell(10).setCellValue(visitor.getReason());
		    	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		    	    
		    	    if(visitor.getVInDt()!=null)
		    	    row.createCell(11).setCellValue(sdf.format(visitor.getVInDt()));
		    	    if(visitor.getVOutDt()!=null)
		    		row.createCell(12).setCellValue(sdf.format(visitor.getVOutDt()));
		    		row.createCell(13).setCellValue(visitor.getVehicleNo());
		    		
		    		    if(visitor.getParkingSlots()!=null )
		    		    {
		    		    	if(visitor.getParkingSlots().getPsSlotNo()!=null)
		    		    	  row.createCell(14).setCellValue(visitor.getParkingSlots().getPsSlotNo());	
		    		    }
		    		    else
		    		    {
		    		    	row.createCell(14).setCellValue("");		
		    		    }
		    		
		    		
		    		row.createCell(15).setCellValue(visitor.getVpExpectedHours());
		    		
		    		row.createCell(16).setCellValue(visitor.getVpStatus());
			    		
			    
			    		
		    		
		    		
		    		count ++;
				}
		    	
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"VisitorTemplates.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
		  
		  @RequestMapping(value = "/visitorPdfTemplate/visitorPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
			public String exportVisitorPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
				
				
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
		        
		       
				List<VisitorVisits> visitorTemplateEntities = visitorVisitsService.findVisitorIn();
				List<Object[]> domesticTemplateEntities =personService.getAllDomestic();   
		              
		      
		        Document document = new Document();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        PdfWriter.getInstance(document, baos);
		        document.open();
		        
		       
		        
		        PdfPTable table = new PdfPTable(18);
		        
		        Font font1 = new Font(Font.FontFamily.HELVETICA  , 7, Font.BOLD);
		        Font font3 = new Font(Font.FontFamily.HELVETICA, 7);
		        
		      
		    	table.addCell(new Paragraph("Visitor Id",font1));
		        table.addCell(new Paragraph("Image",font1));
		        table.addCell(new Paragraph("Contact Number",font1));
		        table.addCell(new Paragraph("Visitor Name",font1));
		        table.addCell(new Paragraph("Address",font1));
		        table.addCell(new Paragraph("Gender",font1));
		        table.addCell(new Paragraph("Category",font1));
		        table.addCell(new Paragraph("Property No",font1));
		        table.addCell(new Paragraph("Access Card No",font1));
		        table.addCell(new Paragraph("Purpose Of Visit",font1));
		        table.addCell(new Paragraph("Visitor Status",font1));
		        table.addCell(new Paragraph("Exit Reason",font1));
		        table.addCell(new Paragraph("Entry Date Time",font1));
		        table.addCell(new Paragraph("Exit Date Time",font1));
		        table.addCell(new Paragraph("Vehicle No",font1));
		        table.addCell(new Paragraph("Parking Slot No",font1));
		        table.addCell(new Paragraph("Expected hours",font1));
		        table.addCell(new Paragraph("Parking-Status",font1));
		       
		       
		    	for (VisitorVisits visitor :visitorTemplateEntities) {
		    		
		    		 PdfPCell cell1 = new PdfPCell(new Paragraph(Integer.toString(visitor.getVisitor().getVmId()),font3));
		    		Blob blobImage = visitorService.getImage(visitor.getVmId());
		    		int blobLength = 0;
		    		Image image2=null;
		    		byte[] blobAsBytes = null;
		    		if (blobImage != null) {
		    			blobLength = (int) blobImage.length();
		    			blobAsBytes = blobImage.getBytes(1, blobLength);
		    			image2 = Image.getInstance(blobAsBytes);
		    		}
		    		else{
		    			 for (Object[] domestic :domesticTemplateEntities) {
		    		           
		    			          
		    					
		    		    		 Blob blobImage1 = personService.getImage((Integer)domestic[0]);
		    		    	
		    		    	
		    		    		if (blobImage1 != null) {
		    		    			blobLength = (int) blobImage1.length();
		    		    			blobAsBytes = blobImage1.getBytes(1, blobLength);
		    		    			image2= Image.getInstance(blobAsBytes);
		    		    		}
		    		    		
		    		}
		    		}
		    		 
		        PdfPCell cell2 = new PdfPCell(image2,true);
		    		
		        PdfPCell cell3 = new PdfPCell(new Paragraph(visitor.getVisitor().getVmContactNo(),font3));
		       
		        PdfPCell cell4 = new PdfPCell(new Paragraph(visitor.getVisitor().getVmName(),font3));
		        
		    
		        PdfPCell cell5 = new PdfPCell(new Paragraph(visitor.getVisitor().getVmFrom(),font3));
		       
		        PdfPCell cell6 = new PdfPCell(new Paragraph(visitor.getVisitor().getGender(),font3));
		     
			        PdfPCell cell7 = new PdfPCell(new Paragraph(visitor.getCategory(),font3));
		       
		        PdfPCell cell8 = new PdfPCell(new Paragraph(visitor.getProperty().getProperty_No(),font3));
		        String str="";
		    
		        if(visitor.getAccessCards()!=null )
    		    {
    		    	if(visitor.getAccessCards().getAcNo()!=null)
    		    	
    		        str =visitor.getAccessCards().getAcNo();
    		    }
    		    else
    		    {
    		    	str =""; 	
    		    }
    		
		        
		        PdfPCell cell9 = new PdfPCell(new Paragraph(str,font3));
		       
		        PdfPCell cell10 = new PdfPCell(new Paragraph(visitor.getVPurpose(),font3));
		        PdfPCell cell11 = new PdfPCell(new Paragraph(visitor.getVvstatus(),font3));
			       
		        PdfPCell cell12 = new PdfPCell(new Paragraph(visitor.getReason(),font3));
		        
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		       
		 
	    	    if(visitor.getVInDt()!=null){
	    	    	str=sdf.format(visitor.getVInDt());
	    	    }
	    	    PdfPCell cell13 = new PdfPCell(new Paragraph(str,font3));
	    	  
	    	    if(visitor.getVOutDt()!=null){
	    	    	 str=sdf.format(visitor.getVOutDt());
	    	    }
	    	    	 
	    	    PdfPCell  cell14 = new PdfPCell(new Paragraph(str,font3));
		       
		        PdfPCell cell15 = new PdfPCell(new Paragraph(visitor.getVehicleNo(),font3));
		       
		        
		        if(visitor.getParkingSlots()!=null )
    		    {
    		    	if(visitor.getParkingSlots().getPsSlotNo()!=null)
    		    		str=visitor.getParkingSlots().getPsSlotNo();
    		    	 
    		    }
    		    else
    		    {
    		    	str="";		
    		    }
		        PdfPCell cell16 = new PdfPCell(new Paragraph(str,font3));
		        PdfPCell cell17 = new PdfPCell(new Paragraph(Integer.toString(visitor.getVpExpectedHours()),font3));
		        PdfPCell cell18 = new PdfPCell(new Paragraph(visitor.getVpStatus(),font3));
		       
		   

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
		        table.addCell(cell17);
		        table.addCell(cell18);
		        
		        
		       
		        table.setWidthPercentage(100);
		        float[] columnWidths = {7f, 8f, 7f, 8f, 8f, 6f, 7f, 6f, 7f, 6f, 7f, 6f, 8f, 8f, 7f, 8f, 8f, 8f};

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
				response.setHeader("Content-Disposition", "inline;filename=\"VisitorTemplates.pdf"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
		  
		  @RequestMapping(value = "/preRegisteredVisitorTemplate/preRegisteredVisitorTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
			public String exportCVSVisitorDetailsFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
				
				
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".xlsx";
		        
		       
				List<Object[]> visitorTemplateEntities = preRegisteredVisitorService.getVisitorDetails();
			
		               
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
		    	
		        header.createCell(0).setCellValue("Visitor Name");
		        header.createCell(1).setCellValue("Visitor Contact Number");
		        header.createCell(2).setCellValue("Block Name");
		        header.createCell(3).setCellValue("Property Name");
		        header.createCell(4).setCellValue("Visitor Password");
		        header.createCell(5).setCellValue("Number Of Visitors");
		        header.createCell(6).setCellValue("Pre-Registered By");
		        header.createCell(7).setCellValue("Status");
		        
		       
		       
		       
		        for(int j = 0; j<=7; j++){
		    		header.getCell(j).setCellStyle(style);
		            sheet.setColumnWidth(j, 8000); 
		            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H200"));
		        }
		        
		        int count = 1;
		     
		    	for (Object[] values :visitorTemplateEntities ) {
		    		
		    		XSSFRow row = sheet.createRow(count);
		    		
		    		
		    		row.createCell(0).setCellValue((String)values[1]);
		    		row.createCell(1).setCellValue((String)values[2]);
		    		
		    		
		    	    row.createCell(2).setCellValue((String)values[11]);
		    		
		    		row.createCell(3).setCellValue((String)values[9]);
		    		row.createCell(4).setCellValue((String)values[16]);
		    		row.createCell(5).setCellValue((Integer)values[15]);
		    		
		    		
		    	    row.createCell(6).setCellValue((String)values[17]);
		    
		    		
		    		row.createCell(7).setCellValue((String)values[7]);
		    		
		    		
		    		
		    		count ++;
				}
		    	
		    	FileOutputStream fileOut = new FileOutputStream(fileName);    	
		    	wb.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		        
		        ServletOutputStream servletOutputStream;

				servletOutputStream = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline;filename=\"PreRegisteredUserTemplates.xlsx"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
		  
		  
		  @RequestMapping(value = "/preRegisteredVisitorPdfTemplate/preRegisteredVisitorPdfTemplatesDetailsExport", method = {RequestMethod.POST,RequestMethod.GET})
			public String exportCVSVisitorDetailsPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, DocumentException, SQLException{
				
				
		 
				String fileName = ResourceBundle.getBundle("application").getString("bfm.exportCsvFilePath")+DateFormatUtils.format(new Date(), "MMM yyyy")+".pdf";
		        
		       
				List<Object[]> visitorTemplateEntities = preRegisteredVisitorService.getVisitorDetails();
			
		              
		      
		        Document document = new Document();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        PdfWriter.getInstance(document, baos);
		        document.open();
		        
		       
		        
		        PdfPTable table = new PdfPTable(8);
		        
		        Font font1 = new Font(Font.FontFamily.HELVETICA  , 8, Font.BOLD);
		        Font font3 = new Font(Font.FontFamily.HELVETICA, 8);
		        

		       
		        
		      
		    	table.addCell(new Paragraph("Visitor Name",font1));
		        table.addCell(new Paragraph("Visitor Contact Number",font1));
		        table.addCell(new Paragraph("Block Name",font1));
		        table.addCell(new Paragraph("Property Name",font1));
		        table.addCell(new Paragraph("Visitor Password",font1));
		        table.addCell(new Paragraph("Number Of Visitors",font1));
		        table.addCell(new Paragraph("Pre-Registered By",font1));
		        table.addCell(new Paragraph("Status",font1));
		        
		       
		       
		    	for (Object[] values :visitorTemplateEntities) {
		    		
		    		
		    		
		    		
		         PdfPCell cell1 = new PdfPCell(new Paragraph((String)values[1],font3));
		    		
		    		
		         PdfPCell cell2 = new PdfPCell(new Paragraph((String)values[2],font3));
		       
		         PdfPCell cell3 = new PdfPCell(new Paragraph((String)values[11],font3));
		        
		    
		         PdfPCell cell4 = new PdfPCell(new Paragraph((String)values[9],font3));
		       
		         PdfPCell cell5 = new PdfPCell(new Paragraph((String)values[16],font3));
		     
			     PdfPCell cell6 = new PdfPCell(new Paragraph(Integer.toString((Integer)values[15]),font3));
		       
		         PdfPCell cell7= new PdfPCell(new Paragraph((String)values[17],font3));
		      
		    
		         PdfPCell cell8 = new PdfPCell(new Paragraph((String)values[7],font3));
		       
		        

		        table.addCell(cell1);
		        table.addCell(cell2);
		        table.addCell(cell3);
		        table.addCell(cell4);
		        table.addCell(cell5);
		        table.addCell(cell6);
		        table.addCell(cell7);
		        table.addCell(cell8);
		        
		        
		       
		        table.setWidthPercentage(100);
		        float[] columnWidths = {10f, 18f, 10f, 10f, 15f, 15f, 15f, 9f};

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
				response.setHeader("Content-Disposition", "inline;filename=\"PreRegisteredUserTemplates.pdf"+"\"");
				FileInputStream input = new FileInputStream(fileName);
				IOUtils.copy(input, servletOutputStream);
				//servletOutputStream.w
				servletOutputStream.flush();
				servletOutputStream.close();
				
				return null;
			}
		  
			
}
