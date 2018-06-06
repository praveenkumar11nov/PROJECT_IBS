package com.bcits.bfm.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.AdjustmentMasterEntity;
import com.bcits.bfm.model.BillingConfiguration;
import com.bcits.bfm.service.billingCollectionManagement.AdjustmentMasterService;


@Controller
public class AdjustmentMasterController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdjustmentMasterController.class);
	
	@Autowired
	AdjustmentMasterService adjustmentMasterService;
	
	@RequestMapping(value = "/adjustmentMaster", method = RequestMethod.GET)
	public String billingSettings(final ModelMap model,final HttpServletRequest request,final Locale locale) 
	{
		LOGGER.info("In AMR Method");
		model.addAttribute("ViewName", "Adjustment Master");
		return "billingPayments/adjustmentMaster";
	}
	
	
	@RequestMapping(value = "/adjustmentMaster/adjustmentMasterRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<Map<String, Object>> billingSettingsRead() throws IOException
	{
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		final List<?> rateMasterList = adjustmentMasterService.findALL();
		Map<String, Object> rateMaster;
		for (final Iterator<?> iterator = rateMasterList.iterator(); iterator.hasNext();) {
			final Object[] values = (Object[]) iterator.next();
			rateMaster = new HashMap<String, Object>();
			//SELECT a.adjMasterId,a.adjName,a.status,a.description FROM AdjustmentMasterEntity a
			rateMaster.put("adjMasterId", (Integer)values[0]);
			rateMaster.put("adjName", (String)values[1]);
			rateMaster.put("status", (String)values[2]);
			rateMaster.put("description", (String)values[3]);
			//rateMaster.put("status", (String)values[4]);			
			result.add(rateMaster);
		}
		return result;
		
	}
	@RequestMapping(value = "/adjustmentMaster/adjustmentMasterCreate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody AdjustmentMasterEntity adjustmentMasterCreate(@ModelAttribute("adjustmentMasterEntity")final AdjustmentMasterEntity adjustmentMasterEntity,final ModelMap model,final HttpServletRequest req) throws IOException
	{
		
		adjustmentMasterEntity.setStatus("Active");
		adjustmentMasterService.save(adjustmentMasterEntity);
		return adjustmentMasterEntity;
	}
	@RequestMapping(value = "/adjustmentMaster/adjustmentMasterDestroy", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody AdjustmentMasterEntity adjustmentMasterDestroy(@ModelAttribute("adjustmentMasterEntity")final AdjustmentMasterEntity adjustmentMasterEntity) throws IOException
	{
		adjustmentMasterService.delete(adjustmentMasterEntity.getAdjMasterId());
		return adjustmentMasterEntity;
	}
	@RequestMapping(value = "/adjustmentMaster/adjustmentMasterUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody AdjustmentMasterEntity adjustmentMasterUpdate(@ModelAttribute("adjustmentMasterEntity")final AdjustmentMasterEntity adjustmentMasterEntity,final HttpServletRequest req) throws IOException
	{
		
		//billingConfiguration.setStatus(req.getParameter("status"));
		adjustmentMasterService.update(adjustmentMasterEntity);
		return adjustmentMasterEntity;
	}
	
	@RequestMapping(value = "/adjustmentMaster/adjustmentMasterStatus/{id}/{operation}", method = {RequestMethod.GET, RequestMethod.POST })
	public void billingSettingsStatus(@PathVariable final int id,@PathVariable final String operation,final HttpServletResponse response) {

		LOGGER.info("In amrSettingStatus status change method");
		if ("activate".equalsIgnoreCase(operation))
			adjustmentMasterService.adjustmentMasterStatus(id,operation, response);
		else
			adjustmentMasterService.adjustmentMasterStatus(id,operation, response);
	}
	
	 @RequestMapping(value = "/adjustmentMaster/getAllAdjustmentName", method = RequestMethod.GET)
		public @ResponseBody List<?> getAllPaidAccountNumbers() {
			return adjustmentMasterService.getAllAdjustmentName();
		}
}
