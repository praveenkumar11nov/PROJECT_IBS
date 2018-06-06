package com.bcits.bfm.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.WordUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.ClubHouse;
import com.bcits.bfm.model.ClubHouseBooking;
import com.bcits.bfm.model.CustomerItemsEntity;
import com.bcits.bfm.model.ServiceBooking;
import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.model.WrongParking;
import com.bcits.bfm.service.facilityManagement.BlocksService;
import com.bcits.bfm.service.helpDeskManagement.ClubHouseBookingService;
import com.bcits.bfm.service.helpDeskManagement.ClubHouseService;
import com.bcits.bfm.service.helpDeskManagement.ServiceBookingService;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.JsonResponse;

@Controller
public class ClubHouseController {
	@Autowired
	private ClubHouseService clubHouseService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private ClubHouseBookingService clubHouseBookingService;
	
	
	@Autowired
	private ServiceBookingService serviceBookingService;
	
	@RequestMapping("/clubHouseServices")
	public String readIndex(Model model,HttpServletRequest request)
	{
		model.addAttribute("ViewName", "Customer Care");
	
		return "/helpDesk/clubHouseServices";
	}	
	
	@RequestMapping("/clubHouseBooking")
	public String readIndex1(Model model,HttpServletRequest request)
	{
		model.addAttribute("ViewName", "Customer Care");
		
		return "/helpDesk/clubHouseBooking";
	}	

	@RequestMapping("/servicebooking")
	public String readIndex2(Model model,HttpServletRequest request)
	{
		
		return "/helpDesk/servicebooking";
	}	


	@RequestMapping(value = "/clubHouse/Create", method = RequestMethod.GET)
	public @ResponseBody Object createDepartment(@ModelAttribute("clubHouse") ClubHouse clubHouse, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception
	{
		
		return clubHouseService.save(clubHouse);
		
	}
	
	@RequestMapping(value = "/clubhouse/Destroy", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object salaryComponentDestroy(@ModelAttribute("clubHouse") ClubHouse clubHouse,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus) throws Exception 
	{
	
		try {
		
			clubHouseService.delete(clubHouse.getsId());
		} catch (Exception e) {
			
		}
		sessionStatus.setComplete();
		return clubHouse;
		}
	
	
	
	@RequestMapping(value="/clubHouse/Update",method = RequestMethod.GET)	
	public @ResponseBody Object updateDepartment(@ModelAttribute("clubHouse") ClubHouse clubHouse) throws InterruptedException
	{
		return clubHouseService.update(clubHouse);
	
	}

	
	@RequestMapping(value = "/clubHouse/Read", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ClubHouse> escalatedTicketRead() {
		
		
		List<ClubHouse> storeMasterEntities = clubHouseService.findAllData();
		return storeMasterEntities;
	}
	@RequestMapping(value = "/clubHouse/ReadAllServices", method = RequestMethod.GET)
	public @ResponseBody List<?> StoreReadUrl() 
	{
		return clubHouseBookingService.findAll();
	} 
	
	
	@RequestMapping(value = "/clubHouseBooking/Create", method = RequestMethod.GET)
	public @ResponseBody Object createClubHouse(@ModelAttribute("clubHouseBooking") ClubHouseBooking clubHouseBooking, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception
	{
		
	String	action=req.getParameter("serviceAction");
	if(action.equals("CallBack")){
	clubHouseBooking.setStatus("Closed");
	System.out.println("========================================"+action);
	}

		if(action.equals("Book"))
	{
		clubHouseBooking.setStatus("Booked");
		
	}
		
	return clubHouseBookingService.save(clubHouseBooking);
		
	}
	@RequestMapping(value = "/clubHouseBooking/Read", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ClubHouseBooking> readBooking() {
		
		
		List<ClubHouseBooking> clubHouseBookings = clubHouseBookingService.findAllData();
		return clubHouseBookings;
	}
	
	@RequestMapping(value = "/serviceBooking/Read", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServiceBooking> readBookings() {
		serviceBookingService.updateserviceStatus("callBack Closed");
		serviceBookingService.updateserviceStatusBook("Booked");
		
		serviceBookingService.updateserviceStatuss("callBack Opened");
		serviceBookingService.updateserviceStatusBooks("Book Rejected");
		
		List<ServiceBooking> serviceBookings = serviceBookingService.findAllData();
	
		
	
	
			
		return serviceBookings;
	}
	
	@RequestMapping(value = "/serviceBooking/mobile/Read/{email}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ServiceBooking> readBookings(HttpServletRequest req,@PathVariable String email) {
		
		serviceBookingService.updateserviceStatus("callBack Closed");
		serviceBookingService.updateserviceStatusBook("Booked");
		
		serviceBookingService.updateserviceStatuss("callBack Opened");
		serviceBookingService.updateserviceStatusBooks("Book Rejected");

		List<ServiceBooking> serviceBookings = serviceBookingService.findAll(email);
	
		return serviceBookings;
	}
	
	
	

	
	
	
	@RequestMapping(value = "/ServiceBookings/Create", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object create(@RequestBody Map<String, Object> map,@ModelAttribute("serviceBooking") ServiceBooking serviceBooking2, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception
	{
		
		ServiceBooking serviceBooking=new ServiceBooking();
		
String status=(String)map.get("serviceStatus");	
String blockId=(String)map.get("blocks");	

int property=(Integer)map.get("property");	
int name=(Integer)map.get("name");	
String startTime=(String)map.get("startTime");	
String endTime=(String)map.get("endTime");	
String createdBy=(String)map.get("createdBy");	
int sId=(Integer)map.get("sId");


String date=(String)map.get("date");

serviceBooking.setBlocks(blockId);
serviceBooking.setCreatedBy(createdBy);
serviceBooking.setDate(date);
serviceBooking.setEndTime(endTime);
serviceBooking.setName(name);
serviceBooking.setProperty(property);
serviceBooking.setServiceStatus(status);
serviceBooking.setsId(sId);
serviceBooking.setStartTime(startTime);

if(status=="Book")
{
	serviceBooking.setAction("Booked");
}

		return serviceBookingService.save(serviceBooking);
		
	}
	
	@RequestMapping(value = "/clubHouse/callback/{bId}/{operation}",method = {RequestMethod.POST,RequestMethod.GET})
	public String updateItemStatus(@PathVariable int bId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{
		serviceBookingService.setItemStatus(bId, operation, response, messageSource, locale);
		return null;
	}
	
	@RequestMapping(value = "/clubHouse/callbacks/{bId}/{operation}",method = {RequestMethod.POST,RequestMethod.GET})
	public String updateItemStatuss(@PathVariable int bId,@PathVariable String operation,ModelMap model, HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{
		serviceBookingService.setItemStatuss(bId, operation, response, messageSource, locale);
		return null;
	}
	

	}
	
	
	
	
	
	
	
	
	
	

