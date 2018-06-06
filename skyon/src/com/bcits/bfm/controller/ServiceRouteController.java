package com.bcits.bfm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.ServiceRoute;
import com.bcits.bfm.service.serviceRoute.ServiceRouteService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class ServiceRouteController {

	static Logger logger = LoggerFactory.getLogger(ServiceRouteController.class);
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private ServiceRouteService srservice;
	
	
	@RequestMapping("/serviceRoute")
	public String serviceRoute(HttpServletRequest request, Model model) {
		logger.info("In service Route Method");
		model.addAttribute("ViewName", "Service Route");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Service Point", 1, request);
		model.addAttribute("username",
				SessionData.getUserDetails().get("userID"));
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;
		String[] statusArray ={"Monthly","ODD Months","Even Months","1st Quarter","2nd Quarter",
	"3rd Quarter","4th Quarter"};


		for (int i = 0; i < statusArray.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", statusArray[i]);
			map.put("name", statusArray[i]);
			result.add(map);
		}
		 model.addAttribute("readcycle", result);
		 List<Map<String, String>> output = new ArrayList<Map<String, String>>();

			Map<String, String> map1 = null;
			String[] nodeArray ={"Service Route","Service Point"};
			for (int i = 0; i < nodeArray.length; i++)
			{
				map1 = new HashMap<String, String>();
				map1.put("value", nodeArray[i]);
				map1.put("name", nodeArray[i]);
				output.add(map1);
			}
			 model.addAttribute("serviceroutenodetype", output);
		return "servicePoints/serviceRoute";
	}

	
	
	@RequestMapping(value = "/servicePoints/srread", method ={ RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	List<ServiceRoute> readserviceRoute(){
		
	List<ServiceRoute> routes = srservice.findAllOnParentId();
			
		return routes;
				
				
		}
	
	@RequestMapping(value = "/servicePoints/srcreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveServiceRoute(@ModelAttribute("serviceRoute")ServiceRoute serviceRoute,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws ParseException {
			
		logger.info("In ServiceRouteCreateMethod Create Method");
		serviceRoute.setReadDay(dateTimeCalender.getDateToStore(req.getParameter("readDay")));
		srservice.save(serviceRoute);
		System.out.println("srId---------------"+serviceRoute.getSrId());
		List<ServiceRoute> routes = srservice.findOnSrId(serviceRoute.getSrId());
	    return routes;
			}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/servicePoints/srupdate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object updateServiceRoute(@ModelAttribute("serviceRoute")ServiceRoute serviceRoute,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale) throws ParseException {
		
		logger.info("In ServiceRouteCreateMethod Create Method");
		serviceRoute.setReadDay(dateTimeCalender.getDateToStore(req.getParameter("readDay")));
		srservice.update(serviceRoute);
	
	return serviceRoute;
		
	}

	
	
	

	@RequestMapping(value = "/servicePoints/srdelete", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody
	Object deleteService(@RequestParam("srId") int Srid,@ModelAttribute("serviceRoute")ServiceRoute serviceRoute,
			BindingResult bindingResult,ModelMap model, SessionStatus ss) {
		
		logger.info("Service Route Delete method");
		logger.info("The Service Route withd Id :::::::::::::: "+serviceRoute.getSrId()+" is deleting");
		try{
			srservice.delete(serviceRoute.getSrId());
		}
		catch(Exception e){
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
	
		
		return serviceRoute;
	}

	}
	
	
	
	

