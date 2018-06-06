package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.OwnerAuditTrail;
import com.bcits.bfm.service.OwnerAuditTrailService;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class OwnerAuditController {
	
	@Autowired
	private OwnerAuditTrailService ownerAuditTrailService;

	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@RequestMapping(value="/ownerAudit",method=RequestMethod.GET)
	public String index(Model model,HttpServletRequest request)
	{
		 model.addAttribute("ViewName", "Customer Occupancy");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Customer Occupancy", 1, request);
			breadCrumbService.addNode("Manage OwnerAudit", 2, request);
		return "/com/ownerAuditTrail";
	}
	
	@RequestMapping(value="/ownerAudit/read1",method=RequestMethod.GET)
	public @ResponseBody List<?> readAllData()
	{
		
		return ownerAuditTrailService.getAllData();
		
	}
	
	@RequestMapping(value="/ownerAudit/nameFilter",method=RequestMethod.GET)
	public @ResponseBody Set<?> readNameFilter()
	{
		Set<String> data=new HashSet<String>();
		
		for(OwnerAuditTrail ownerAuditTrail:ownerAuditTrailService.getFiltterdata())
		{
			
			data.add(ownerAuditTrail.getOwnerName());
		}
		return data;
		
	}
	
	@RequestMapping(value="/ownerAudit/updatedFilter",method=RequestMethod.GET)
	public @ResponseBody List<?> readNameFilter1()
	{
		List<String> data=new ArrayList<String>();
		
		for(OwnerAuditTrail ownerAuditTrail:ownerAuditTrailService.getFiltterdata())
		{
			
			data.add(ownerAuditTrail.getUpdated_field());
		}
		return data;
		
	}
	@RequestMapping(value="/ownerAudit/currentFilter",method=RequestMethod.GET)
	public @ResponseBody List<?> readNameFilter2()
	{
		List<String> data=new ArrayList<String>();
		
		for(OwnerAuditTrail ownerAuditTrail:ownerAuditTrailService.getFiltterdata())
		{
			
			data.add(ownerAuditTrail.getOwner_current());
		}
		return data;
		
	}
	@RequestMapping(value="/ownerAudit/previousFilter",method=RequestMethod.GET)
	public @ResponseBody List<?> readNameFilter3()
	{
		List<String> data=new ArrayList<String>();
		
		for(OwnerAuditTrail ownerAuditTrail:ownerAuditTrailService.getFiltterdata())
		{
			
			data.add(ownerAuditTrail.getOwner_previous());
		}
		return data;
		
	}
	
	@RequestMapping(value="/ownerAudit/updatedBy",method=RequestMethod.GET)
	public @ResponseBody Set<?> readNameFilter4()
	{
		Set<String> data=new HashSet<String>();
		
		for(OwnerAuditTrail ownerAuditTrail:ownerAuditTrailService.getFiltterdata())
		{
			
			data.add(ownerAuditTrail.getLastUpdatedBy());
		}
		return data;
		
	}
}
