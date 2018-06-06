package com.bcits.bfm.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class WorkFlowController {
	
	private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	/*----------------------------------Manage Process-----------------------------------*/
	
	@RequestMapping(value = "/manageprocess", method = RequestMethod.GET)
	public String indexUsers(ModelMap model, HttpServletRequest request) {
		System.out.println("inside manageprocess");
		model.addAttribute("ViewName", "Work Flow Management");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Manage Process", 1, request);
		return "workflow/manageprocess";
	}

}
