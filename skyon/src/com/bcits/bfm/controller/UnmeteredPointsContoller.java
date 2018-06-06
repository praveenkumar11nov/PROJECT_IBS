package com.bcits.bfm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.OTConsumptions;
import com.bcits.bfm.model.OtherInstallation;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.electricityMetersManagement.UnMeteredChildService;
import com.bcits.bfm.service.electricityMetersManagement.UnmeteredPointService;
import com.bcits.bfm.view.BreadCrumbTreeService;


@Controller
public class UnmeteredPointsContoller {

	static Logger logger = LoggerFactory.getLogger(UnmeteredPointsContoller.class);
	
	@Autowired
	private UnmeteredPointService unmeteredPointService;
	
	@Autowired
	private UnMeteredChildService unMeteredChildService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonService commonService ;
	
	@RequestMapping("/unmeteredPoints")
	public String unmeteredPoints(HttpServletRequest request, Model model) {
		logger.info("In un metered points method");
		model.addAttribute("ViewName", "UnAssed Points");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage UnAssed Points", 2, request);
		return "electricityBills/unmeteredPoints";
	}
	
	@RequestMapping(value = "/unmeteredPoints/read", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readMeterDetails() {
		logger.info("In read meter Details method");
		List<?> otherInstallation = unmeteredPointService.findAll();
		return otherInstallation;
	}
	
	@RequestMapping(value = "/unmeteredPoints/create", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveUnmeteredDetails(@ModelAttribute("otherInstallation") OtherInstallation otherInstallation,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception {

		logger.info(" In save UnmeteredPointsContoller details method ");
		unmeteredPointService.save(otherInstallation);		
		 return otherInstallation;	
	}
	
	@RequestMapping(value = "/unmeteredPoints/update", method = RequestMethod.GET)
	public @ResponseBody Object editUnmeteredDetails(@ModelAttribute("otherInstallation") OtherInstallation otherInstallation,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {
		logger.info(" In update UnmeteredPointsContoller details method ");
	      return unmeteredPointService.update(otherInstallation);
	}
	
	@RequestMapping(value = "/unmeterd/unmeteredStatus/{id}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	  public void metersStatus(@PathVariable int id,@PathVariable String operation, HttpServletResponse response) {
	   
	   logger.info("In Service  Status Change Method");
	   if (operation.equalsIgnoreCase("activate"))
		   unmeteredPointService.setParameterStatus(id,operation, response);
	   else
		   unmeteredPointService.setParameterStatus(id,operation, response);
	  }
	
	
	
	@RequestMapping(value = "/unmeteredPoints/destroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteUnmeteredParameter(@ModelAttribute("otherInstallation") OtherInstallation otherInstallation,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {

		logger.info("In delete unmeteredPoints destroy method");
		/*JsonResponse errorResponse = new JsonResponse();
		final Locale locale = null;
		System.out.println("+++++"+req.getParameter("status"));
		if (req.getParameter("status").equalsIgnoreCase("Active")) {
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				
				private static final long serialVersionUID = 1L;

				{
					put("AciveParameterDestroyError", messageSource.getMessage("UnmeteredParameterMaster.AciveParameterDestroyError", null,
							locale));
				}
			};
			errorResponse.setStatus("AciveParameterDestroyError");
			errorResponse.setResult(errorMapResponse);

			return errorResponse;
		} else {
			try {
				unmeteredPointService.delete(otherInstallation.getId());
			} catch (Exception e) {

				errorResponse.setStatus("CHILD");
				return errorResponse;
			}
			ss.setComplete();
			return otherInstallation;
		}*/
		
		
		List<?> otherInstallationEntities=unMeteredChildService.getChildId(otherInstallation.getId());

		
		 for (Iterator<?> iterator = otherInstallationEntities.iterator(); iterator.hasNext();)
			{
			
		         unMeteredChildService.delete((Integer)iterator.next());
		
			}
		unmeteredPointService.delete(otherInstallation.getId());

		return otherInstallation;
	}
	
	
	// ****************************** UnMetered Parameter Master Filters Data methods ********************************/

			@RequestMapping(value = "/unmeteredPoints/filter/{feild}", method = RequestMethod.GET)
			public @ResponseBody Set<?> unmeteredParameterMaster(@PathVariable String feild) {
				List<String> attributeList = new ArrayList<String>();
				attributeList.add(feild);
				HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("OtherInstallation",attributeList, null));
				
				return set;
			}
	
	@RequestMapping(value = "/unmeteredPoints/childReadUrl/{id}", method = RequestMethod.GET)
	public @ResponseBody List<?> readChildDetails(@PathVariable int id) {
		return unMeteredChildService.findAll(id);
	}
	
	@RequestMapping(value="/unmeteredPoints/childCreateUrl/{id}",method = RequestMethod.GET)
	public @ResponseBody Object savechild(@ModelAttribute("oTConsumptions") OTConsumptions oTConsumptions,@PathVariable int id,HttpServletRequest req)  {
		unMeteredChildService.save(oTConsumptions);
		return oTConsumptions;
    }	

	@RequestMapping(value="/unmeteredPoints/childUpdateUrl/{id}",method = RequestMethod.GET)
	public @ResponseBody Object updatechild(@ModelAttribute("oTConsumptions") OTConsumptions oTConsumptions,@PathVariable int id)  {
		logger.info("In Child Detail Update Method");
		oTConsumptions.setId(id);
		unMeteredChildService.update(oTConsumptions);
		return oTConsumptions;
     }	
	
	
	@RequestMapping(value = "/unmetered/getunits", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody List<Object> getunits(HttpServletRequest req,HttpServletResponse response, @RequestBody String body) throws ParseException, JSONException {

		double presentUOMReading = 0;
		double uomValue = 0.0;
		double previousUOMReading = 0;
		if(req.getParameter("previousReading")!=null || !req.getParameter("previousReading").equals("")){
			previousUOMReading = Double.parseDouble(req.getParameter("previousReading"));
		}
		
		String typeOfService = req.getParameter("typeOfservice");
		int accountId = Integer.parseInt(req.getParameter("id"));
		if (req.getParameter("presentReading")==null || req.getParameter("presentReading").equals("") || req.getParameter("presentReading").isEmpty()) {
			presentUOMReading = 0f;
		} else {
			presentUOMReading = Double.parseDouble(req.getParameter("presentReading"));
		}

		if (typeOfService.equalsIgnoreCase("Yes")) {
			uomValue = presentUOMReading - previousUOMReading;
		
		}
		
		Float previous = getPreviousReading(typeOfService, accountId);

		
		List<Object> list = new ArrayList<Object>();

		if (presentUOMReading < previousUOMReading ) {
			
			list.add("Present");
		} else {
			list.add(uomValue);
		}
		list.add(previous);

		return list;
	}

	private Float getPreviousReading(String typeOfService, int accountId) {
		
 	double previousreading = unMeteredChildService.getEntityByAccountId(accountId, typeOfService);	
	
	return (float) previousreading;
	
       }
	
	@RequestMapping(value = "/unAssetPoints/getPreviousReading/{installId}", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object displayConversation(@PathVariable int installId) {
		
		int otConsumptionId = unMeteredChildService.getMaxConsuptionId(installId);
		double previousReading = 0.0;
		if (otConsumptionId != 0) {
			OTConsumptions consumptions = unMeteredChildService.find(otConsumptionId);
			previousReading = consumptions.getPresentReading();
		}
		return previousReading;
	}
	@RequestMapping(value = "/UnAssedPoint/getUnMeteredReading", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Integer getUmeteredunits(HttpServletRequest req,HttpServletResponse response, @RequestBody String body) throws ParseException, JSONException {
		Integer capacity=Integer.parseInt(req.getParameter("capacity"));
	String phase=(req.getParameter("phase"));
		Integer hour=Integer.parseInt(req.getParameter("hour"));
		logger.info("capcity:::::::"+capacity+"phase::::::"+phase+"hour"+hour);
		Integer unit=(capacity*hour*30);
	return unit;
	}
}
