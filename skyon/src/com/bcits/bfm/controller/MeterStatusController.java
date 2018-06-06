package com.bcits.bfm.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bfm.model.InterestSettingsEntity;
import com.bcits.bfm.model.MeterStatusEntity;
import com.bcits.bfm.service.electricityMetersManagement.MeterStatusService;
import com.bcits.bfm.service.transactionMaster.InterestSettingService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module's Use case
 * Module: Billing Master 
 * Use Case : Meter Status
 * 
 * @author Ravi Shankar Reddy
 * @version %I%, %G%
 * @since 0.1
 */

@Controller
public class MeterStatusController {

	static Logger logger = LoggerFactory.getLogger(MeterStatusController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	@Autowired
	private MeterStatusService meterStatusService;
	
	@Autowired
	private InterestSettingService interestSettingService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping("/meterStatus")
	public String meterStatus(HttpServletRequest request, Model model) {
		logger.info("In meter status method");
		model.addAttribute("ViewName", "Masters");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Masters", 1, request);
		breadCrumbService.addNode("Manage Meter Status", 2, request);
		return "electricityBills/meterStatus";
	}
	
	@RequestMapping("/interestCalculationSettings")
	public String interestCalculationSettings(HttpServletRequest request, Model model) {
		logger.info("In Ineter Calculation settings method");
		model.addAttribute("ViewName", "Bill Generation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Bill Generation", 1, request);
		breadCrumbService.addNode("Manage Interest Calculation Settings", 2, request);
		return "transactionMaster/interestCalculationSettings";
	}
	
	 // ****************************** Meter Status Read,Create,Delete methods ********************************//

	@RequestMapping(value = "/meterStatus/meterStatusRead", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<MeterStatusEntity> readMeterStatus() {
		logger.info("In read meter status method");
		List<MeterStatusEntity> meterStatusEntities = meterStatusService.findAll();
		return meterStatusEntities;
	}

	@RequestMapping(value = "/meterStatus/meterStatusCreate", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object saveMeterStatus(@ModelAttribute("meterStatusEntity") MeterStatusEntity meterStatusEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

		logger.info("In save meter status method");
		
		validator.validate(meterStatusEntity, bindingResult);
		meterStatusService.save(meterStatusEntity);
		sessionStatus.setComplete();
		return meterStatusEntity;
		
	}
	
	@RequestMapping(value = "/meterStatus/meterStatusUpdate", method = RequestMethod.GET)
	public @ResponseBody Object editMeterStatus(@ModelAttribute("meterStatusEntity") MeterStatusEntity meterStatusEntity,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

		logger.info("In edit meter status method");
		
		validator.validate(meterStatusEntity, bindingResult);
		meterStatusService.update(meterStatusEntity);
		
		return meterStatusEntity;
	}

	@RequestMapping(value = "/meterStatus/meterStatusDestroy", method = RequestMethod.GET)
	public @ResponseBody Object deleteDepartmentAccessSettingsRead(@ModelAttribute("meterStatusEntity") MeterStatusEntity meterStatusEntity,BindingResult bindingResult, ModelMap model, SessionStatus ss,HttpServletRequest req) {
		logger.info("In delete meter status method");
		JsonResponse errorResponse = new JsonResponse();
		
		try {
			meterStatusService.delete(meterStatusEntity.getMeterStatusId());
		} catch (Exception e) {
			errorResponse.setStatus("CHILD");
			return errorResponse;
		}
		ss.setComplete();
		return meterStatusEntity;
	}
	
	 // ****************************** Interest Settings Read,Create,Delete methods ********************************//

		@RequestMapping(value = "/interestSettings/interestSettingsRead", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List<InterestSettingsEntity> readInterestSettings() {
			logger.info("In read interest settinds method");
			List<InterestSettingsEntity> settingsEntities = interestSettingService.findAll();
			return settingsEntities;
		}

		@RequestMapping(value = "/interestSettings/interestSettingsCreate", method = {RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody Object interestSettingsCreate(@ModelAttribute("interestSettingsEntity") InterestSettingsEntity interestSettingsEntity,BindingResult bindingResult,ModelMap model, HttpServletRequest req,SessionStatus sessionStatus, final Locale locale) throws ParseException {

			logger.info("In save interest settings method");
			interestSettingsEntity.setStatus("Active");
			
			List<InterestSettingsEntity> list = interestSettingService.findAllData();
			for (InterestSettingsEntity entity : list) {
				interestSettingService.delete(entity.getSettingId());
			}
			
			validator.validate(interestSettingsEntity, bindingResult);
			interestSettingService.save(interestSettingsEntity);
			sessionStatus.setComplete();
			return interestSettingsEntity;
			
		}
}
