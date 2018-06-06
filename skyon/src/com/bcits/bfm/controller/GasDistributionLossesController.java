package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.GasDistributionLosses;
import com.bcits.bfm.model.GasRateMaster;
import com.bcits.bfm.service.electricityBillsManagement.GasDistributionLossesService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class GasDistributionLossesController {
static Logger logger = LoggerFactory.getLogger(GasDistributionLossesController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private GasDistributionLossesService gasDistributionLossesService;
	
	@RequestMapping("/gasDistributionLosses")
	public String tariffMasterDashBoard(HttpServletRequest request, Model model) {
		logger.info("-------------- In Gas Distribution Losses DashBoard Method ------------------- ");
		model.addAttribute("ViewName", "Bills Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bills Generation", 1, request);
		breadCrumbService.addNode("Manage Distribution Losses", 2, request);
		return "electricityBills/gasDistributionLosses";
	}
	
 	@RequestMapping(value = "/gasDistributionLosses/gasDistributionLossesCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object gasDistributionLossesCreate(@ModelAttribute("gasDistributionLosses") GasDistributionLosses gasDistributionLosses, BindingResult bindingResult,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus, final Locale locale) throws ParseException
	{
 		logger.info(",;;;;;;"+req.getParameter("lossPercentage"));
 		
 		logger.info("::::::::::::::::::::::::Creating Gas Distribution Losses :::::::::::::::::::::::::");
 		gasDistributionLosses.setStatus("Created");
 		gasDistributionLossesService.save(gasDistributionLosses);
		return gasDistributionLosses;
	}
 	
 	
	@RequestMapping(value = "/gasDistributionLosses/gasDistributionLossesRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> gasDistributionLossesRead(ModelMap model,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		logger.info(":::::::::::::::::::::::: Reading Gas Distribution Losses :::::::::::::::::::::::::");
		
		//List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;
		//result = gasDistributionLossesService.findAll();
		return gasDistributionLossesService.findAll();
	}
	
	@RequestMapping(value = "/gasDistributionLosses/gasDistributionLossesUpdate", method = RequestMethod.GET)
	public @ResponseBody Object gasDistributionLossesUpdate(@ModelAttribute("gasDistributionLosses") GasDistributionLosses gasDistributionLosses,BindingResult bindingResult,ModelMap model, SessionStatus ss,HttpServletRequest req) throws ParseException
	{
		logger.info("The Gas Distribution Losses withd Id :::::::::::::: "+gasDistributionLosses.getGdlid()+" is updating");
		gasDistributionLossesService.update(gasDistributionLosses);
		return gasDistributionLosses;
	}
	
	@RequestMapping(value = "/gasDistributionLosses/gasDistributionLossesDestroy", method = RequestMethod.GET)
	public @ResponseBody Object gasDistributionLossesDestroy(@ModelAttribute("gasDistributionLosses") GasDistributionLosses gasDistributionLosses,BindingResult bindingResult,ModelMap model, SessionStatus ss)
	{
		logger.info("The Gas Distribution Losses withd Id :::::::::::::: "+gasDistributionLosses.getGdlid()+" is deleting");
		try{
			gasDistributionLossesService.delete(gasDistributionLosses.getGdlid());
		}
		catch(Exception e){
			JsonResponse errorResponse = new JsonResponse();
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return gasDistributionLosses;
	}
	
	@RequestMapping(value = "/gasDistributionLosses/getSubMeterReading/{month}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Map<Object,Object> getSubMeterReading(HttpServletRequest req, HttpServletResponse response,@PathVariable String month)throws ParseException {
		logger.info(" month ---  "+month);
		Date monthdate = new SimpleDateFormat("MMMM yyyy").parse(month);
		
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(monthdate);
		int month1 = cal.get(Calendar.MONTH);
		 int actualmonth = month1 + 1;
         int year = cal.get(Calendar.YEAR);
         
         logger.info(":::::::month1::::"+month1+":::::::actualmonth::::::"+actualmonth+":::::year:::::"+year);
		
	/*	String paraMeterName4 = "Meter Constant";
		String bcMeterFactor = null;
		if (billParameterService.getParameterValue(billEntity.getElBillId(), billEntity.getTypeOfService(),paraMeterName4) != null) {
			bcMeterFactor = billParameterService.getParameterValue(billEntity.getElBillId(),billEntity.getTypeOfService(), paraMeterName4);
			logger.info("bcMeterFactor -------------- " + bcMeterFactor);
		}*/
		
        // Map<Object,Object> ga=
		return gasDistributionLossesService.getDistributionLoss(actualmonth,year);
	}
	@RequestMapping(value = "/gasDistributionLosses/getLossInDistibution/{mainMeterReading}/{subMeter}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Map<Object,Object> getLossInDistibution(HttpServletRequest req, HttpServletResponse response,@PathVariable float mainMeterReading,@PathVariable float subMeter)throws ParseException {
		
		Map<Object,Object> res=new HashMap<Object, Object>();
		float loss=(mainMeterReading-subMeter);
				float lossPercentage=((loss/subMeter)*100);
				res.put("loss", loss);
				res.put("lossPercentage", lossPercentage);
				logger.info(":::::::loss::::::"+loss);
				logger.info(":::::::lossPercentage::::::"+lossPercentage);
		
        return res;
	}
	@RequestMapping(value = "/gasDistributionLosses/setGasDistributionLossestatus/{gdlid}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void tariffStatus(@PathVariable int gdlid, @PathVariable String operation, HttpServletResponse response)
	{		
		logger.info("Gas Distribution Losses Activate and Deactivate method");
		logger.info("The operateion is :::::::::::::::: "+operation+" With Id ::::::::::: "+gdlid);
		gasDistributionLossesService.setDistributionLossesStatus(gdlid, operation, response);
	}
	
}
