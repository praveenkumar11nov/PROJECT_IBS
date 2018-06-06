package com.bcits.bfm.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.ElectricityBillEntity;
import com.bcits.bfm.model.ServiceParametersEntity;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillLineItemService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillParameterService;
import com.bcits.bfm.service.electricityBillsManagement.ElectricityBillsService;
import com.bcits.bfm.service.facilityManagement.BillingParameterMasterService;
import com.bcits.bfm.service.facilityManagement.ServiceParameterMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceMasterService;
import com.bcits.bfm.service.serviceMasterManagement.ServiceParameterService;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class AverageClaculationController {
	static Logger logger = LoggerFactory.getLogger(AdvanceBillingController.class);
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	TariffCalculationService tariffCalculationService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();

	@Autowired
	TariffCalculationController calculationController;

	@Autowired
	ElectricityBillsService electricityBillsService;

	@Autowired
	ElectricityBillLineItemService electricityBillLineItemService;

	@Autowired
	BillingParameterMasterService billingParameterMasterService;

	@Autowired
	ElectricityBillParameterService billParameterService;

	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	private ServiceParameterService parameterService;
	
	@Autowired
	private ServiceParameterMasterService serviceParameterMasterService;

	@Autowired
	TariffCalculationService calculationService;

	
	@RequestMapping("/averageCalculation")
	public String accessCardsIndex(HttpServletRequest request, Model model) {
		logger.info("In Averagecalculation Method");
		model.addAttribute("ViewName", "Average Calculation");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Average Calculation", 1, request);

		return "electricityBills/averageCalculation";
	}
	
	@RequestMapping(value = "/avgUnit/calculateAverageUnit", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<Object> calculateAvgUnits(HttpServletRequest req,
			HttpServletResponse response, @RequestBody String body)
			throws ParseException, JSONException {
		String serviceName = req.getParameter("serviceName");
		int accountId = Integer.parseInt(req.getParameter("accountId"));
		String accountNo = req.getParameter("accountNo");
		int serviceID = Integer.parseInt(req.getParameter("serviceID"));
		
		Date presentdate = new SimpleDateFormat("dd/MM/yyyy").parse(req.getParameter("avgDate"));
		
		String avgTYpe = req.getParameter("avgType");
		
		logger.info(":::::::::serviceName::::::" + serviceName);
		logger.info(":::::::::accountId::::::" + accountId);
		logger.info(":::::::::accountNo::::::" + accountNo);
		logger.info(":::::::::presentdate::::::" + presentdate);
		logger.info(":::::::::avgTYpe::::::" + avgTYpe);
		logger.info(":::::::::serviceID::::::" + serviceID);
		
		float avgUnit=0;
		if(avgTYpe.equalsIgnoreCase("Last 1 Year Consumption")){
			avgUnit=avgOFpreviousOneYear(accountId,presentdate,serviceName);	
		}else if(avgTYpe.equalsIgnoreCase("Similar Apartment Type")){
			avgUnit=avgUnitforSimilarFlatType(accountId,presentdate,serviceName);
		}else{
			avgUnit=avgUnitforThreeYearData(accountId,presentdate,serviceName);
		}
		
		List<Integer> serviceParameterMasterIdList = serviceMasterService.getServiceParameterBasedOnMasterId(serviceID);
		ServiceParametersEntity parametersEntity = null;
		if(serviceParameterMasterIdList.isEmpty()){
			parametersEntity = new ServiceParametersEntity();
			
			parametersEntity.setServiceMastersEntity(serviceMasterService.find(serviceID));
			parametersEntity.setServiceParameterSequence(parameterService.getSequenceForAverageUnits(serviceID)+1);
			parametersEntity.setStatus("Active");
			parametersEntity.setSpmId(parameterService.getServiceParameterMasterId("Average Unit"));
			parametersEntity.setServiceParameterDataType(serviceParameterMasterService.find(parameterService.getServiceParameterMasterId("Average Unit")).getSpmdataType());
			parametersEntity.setServiceParameterValue(""+avgUnit);
			
			parameterService.save(parametersEntity);
			
		}else{
			parametersEntity = parameterService.find(serviceParameterMasterIdList.get(0));
			parametersEntity.setServiceParameterValue(""+avgUnit);
			
			parameterService.update(parametersEntity);
		}
		
		PrintWriter out;
		try {
		   out=response.getWriter();
			out.write("Updated Successfully");
			
		} catch (Exception e) {
			
		}
	
		return null;	
	}
	
	private float avgUnitforThreeYearData(int accountId, Date currentBillDate,
			String typeOfService) {
		float avgunit=tariffCalculationService.claculateAvgforthreeYearData(accountId, currentBillDate, typeOfService);
		return avgunit;
	}

	public float avgOFpreviousOneYear(int accountId,Date currentBillDate,String typeOfService){
		
		Float uomValue = 0.0f;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(currentBillDate);
		cal1.add(Calendar.YEAR, -1);
		Date lastYear = cal1.getTime();
		int bvmId = billingParameterMasterService.getServicMasterId(
				typeOfService, "Units");
		List<ElectricityBillEntity> billIdList = electricityBillsService
				.getBillEntityByAccountId(accountId, typeOfService);
		List<String> listValus = new ArrayList<>();
		float avgUnits=0;
        if(!billIdList.isEmpty())
        {
    		for (ElectricityBillEntity electricityBillEntity : billIdList) {
    			listValus = billParameterService.getAverageUnits(electricityBillEntity.getElBillId(), bvmId, lastYear,currentBillDate);
    			for (String string : listValus) {
    				avgUnits +=Float.parseFloat(string);
    			}
    		}
    		uomValue = avgUnits /billIdList.size();
        }
/*		for (ElectricityBillEntity electricityBillEntity : billIdList) {
			listValus = billParameterService.getAverageUnits(
					electricityBillEntity.getElBillId(), bvmId, lastYear,
					currentBillDate);
			for (String string : listValus) {
				maxValue.add(Float.parseFloat(string));
			}
		}
		if (!listValus.isEmpty()) {
			if (maxValue.size() == 1) {
				uomValue = (Collections.max(maxValue)) / maxValue.size();
			} else {
				uomValue = (Collections.max(maxValue) - Collections
						.min(maxValue)) / maxValue.size();
			}
			logger.info(":::::::::::::: Average consumption units  ::::::::::::: "
					+ uomValue);
		}*/
		
		
	return uomValue;
	}
	public float avgUnitforSimilarFlatType(int accountId,Date currentBillDate,String typeOfService){
		
	float avgunit=tariffCalculationService.claculateAvgforSimilarTypeofFlat(accountId, currentBillDate, typeOfService);
		
		
	return avgunit;
	}
	
}
