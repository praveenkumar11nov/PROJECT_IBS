package com.bcits.bfm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class PatternMasterController {
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@RequestMapping("/patternMaster")
	public String index(Model model, HttpServletRequest request) {

		model.addAttribute("ViewName", "Work Flow");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Work Flow", 1, request);
		breadCrumbService.addNode("Pattern Master", 2, request);
		return "workflow/patternMaster";
	}
}
