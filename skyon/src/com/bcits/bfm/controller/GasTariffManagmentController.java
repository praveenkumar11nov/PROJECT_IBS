package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.GasTariffMaster;

import com.bcits.bfm.service.gasTariffManagment.GasTariffMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class GasTariffManagmentController {

	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
   DateTimeCalender dateTimeCalender = new DateTimeCalender();
   
   @Autowired
	GasTariffMasterService gasTariffMasterService;
   
	static Logger logger = LoggerFactory.getLogger(GasTariffManagmentController.class);	
	
	
	@RequestMapping(value = "/gasTariffMaster", method = RequestMethod.GET)
	public String indexTariffMaster(ModelMap model, HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		logger.info("::::::::::::::: Gas Tariff Management ::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage GasTariff Master", 2, request);
		model.addAttribute("username",SessionData.getUserDetails().get("userID"));
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;
		String[] statusArray ={"Active"};
		for (int i = 0; i < statusArray.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", statusArray[i]);
			map.put("name", statusArray[i]);
			result.add(map);
		}
		 model.addAttribute("status", result);
		 List<Map<String, String>> output = new ArrayList<Map<String, String>>();

			Map<String, String> map1 = null;
			String[] nodeArray ={"Tariff Rate Node","Hierarchical Node"};
			for (int i = 0; i < nodeArray.length; i++)
			{
				map1 = new HashMap<String, String>();
				map1.put("value", nodeArray[i]);
				map1.put("name", nodeArray[i]);
				output.add(map1);
			}
			 model.addAttribute("tariffNodetype", output);
		 
		 
		return "gasTariff/gasTariffMaster";
	}
	
	@RequestMapping(value = "/gasTariff/read", method = RequestMethod.POST)
	public @ResponseBody
	List<GasTariffMaster> readTariff(@RequestBody Map<String, Object> model,
			HttpServletRequest req) {
		logger.info("el tariff id" + (Integer) model.get("elTariffID"));
		return gasTariffMasterService.findAllOnParentId(((Integer)model.get("gasTariffId")),
				(String) model.get("gasStatus"));
				
	}
	@RequestMapping(value = "/gasTariff/create", method = RequestMethod.POST)
	public String addNode(@RequestParam("gastariffCode") String tariffcode,
			@RequestParam("gasTariffId") int eltariffId,
			@RequestParam("gastreeHierarchy") String treeHierarchy,
			@RequestParam("gastariffNodetype") String tariffnodetype,
			@RequestParam("gasStatus") String status,
			@RequestParam("gastariffDescription") String tariffdescription,
			@RequestParam("gastariffName") String tariffname,

			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		logger.info("valid to" + request.getParameter("validTo"));
		logger.info("validFrom" + request.getParameter("validFrom"));

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		GasTariffMaster tariff = new GasTariffMaster();
		//tariff.setLastUpdatedDT((Timestamp) session
				//.getAttribute("lastUpdatedDT"));
		tariff.setValidTo(dateTimeCalender.getdateFormat(request
				.getParameter("validTo")));
		tariff.setValidFrom(dateTimeCalender.getdateFormat(request
				.getParameter("validFrom")));
		tariff.setCreatedBy((String) session.
				getAttribute(("userId")));
		tariff.setLastUpdatedBy((String)session.
				getAttribute(("userId")));		
		tariff.setGastariffNodetype(tariffnodetype);
		tariff.setGastariffName(tariffname);
		tariff.setGastariffCode(tariffcode);
		tariff.setGasparentId(eltariffId);
		tariff.setGastreeHierarchy(treeHierarchy + " > " + tariffname);
		tariff.setGasStatus("Active");
		tariff.setGastariffDescription(tariffdescription);

		gasTariffMasterService.save(tariff);

		PrintWriter out;

		List<GasTariffMaster> getId = gasTariffMasterService.getTariffDetail(
				eltariffId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getGasTariffId();

		try {
			out = response.getWriter();
			out.write(value + "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/gasTariff/update", method = RequestMethod.POST)
	public String updateTariffMaster(
			@RequestParam("gastariffCode") String tariffcode,
			@RequestParam("gasTariffId") int eltariffId,
			@RequestParam("gastreeHierarchy") String treeHierarchy,
			@RequestParam("gastariffNodetype") String tariffnodetype,
			@RequestParam("gasStatus") String status,
			@RequestParam("gastariffDescription") String tariffdescription,
			@RequestParam("gastariffName") String tariffname,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		logger.info("valid to update" + request.getParameter("validTo"));
		logger.info("validFrom update"
				+ request.getParameter("validFrom"));

		GasTariffMaster tariffmaster = new GasTariffMaster();
		tariffmaster.setGasTariffId(eltariffId);
		List<GasTariffMaster> list = gasTariffMasterService
				.getTariffNameBasedonTariffid(eltariffId);
		Integer parentId = list.get(0).getGasparentId();
		tariffmaster.setCreatedBy((String) session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedBy((String)session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedDT((Timestamp) session
				.getAttribute("lastUpdatedDT"));
		tariffmaster.setGastariffName(tariffname);
		tariffmaster.setGastariffCode(tariffcode);
		tariffmaster.setGastariffNodetype(tariffnodetype);
		tariffmaster.setGasparentId(parentId);
	
		tariffmaster.setGastreeHierarchy(treeHierarchy);
		tariffmaster.setGastariffDescription(tariffdescription);
		tariffmaster.setGasStatus(status);
		tariffmaster.setValidTo(dateTimeCalender.getdateFormat(request
				.getParameter("validTo")));
		tariffmaster.setValidFrom(dateTimeCalender.getdateFormat(request
				.getParameter("validFrom")));

		gasTariffMasterService.update(tariffmaster);

		PrintWriter out;
		List<GasTariffMaster> getId = gasTariffMasterService.getTariffDetail(
				parentId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getGasTariffId();
		try {
			out = response.getWriter();
			out.write(value + "Updated successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/gasTariffMaster/getDetails/{nodeid}", method = RequestMethod.GET)
	public @ResponseBody
	GasTariffMaster getDetailsOnNodeId(@PathVariable Integer nodeid,
			HttpServletRequest request, HttpServletResponse response) {
		GasTariffMaster tariff = new GasTariffMaster();
		tariff.setGasTariffId(nodeid);
		return gasTariffMasterService.getNodeDetails(nodeid);
	}
	
	@RequestMapping(value = "/gastariff/delete", method = RequestMethod.POST)
	public @ResponseBody
	GasTariffMaster deleteTariff(@RequestParam("gasTariffId") int wtTariffId,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out;
		List<GasTariffMaster> list=gasTariffMasterService.findAllOnParentIdwoStatus(wtTariffId);
		if(list.size()==0){
			gasTariffMasterService.delete(wtTariffId);
		
		
		try {
			out = response.getWriter();
			out.write("Deleted Successfully!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} else {
		try {
			out = response.getWriter();
			out.write("Only Leaf node can be deleted!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		
		return null;
	}
	
	
}
