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


import com.bcits.bfm.model.WTTariffMaster;
import com.bcits.bfm.service.waterTariffManagement.WTTariffMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class WaterTariffManagmentController {

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	WTTariffMasterService wtariffMasterService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	static Logger logger = LoggerFactory.getLogger(WaterTariffManagmentController.class);

	@RequestMapping(value = "/wtTariffmaster", method = RequestMethod.GET)
	public String indexTariffMaster(ModelMap model, HttpServletRequest request)throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		logger.info("::::::::::::::: Water Tariff Management ::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Water Tariff Master", 2, request);
		model.addAttribute("username",
				SessionData.getUserDetails().get("userID"));

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;
		String[] statusArray = { "Active" };
		for (int i = 0; i < statusArray.length; i++) {
			map = new HashMap<String, String>();
			map.put("value", statusArray[i]);
			map.put("name", statusArray[i]);
			result.add(map);
		}
		model.addAttribute("status", result);
		List<Map<String, String>> output = new ArrayList<Map<String, String>>();

		Map<String, String> map1 = null;
		String[] nodeArray = { "Tariff Rate Node", "Hierarchical Node" };
		for (int i = 0; i < nodeArray.length; i++) {
			map1 = new HashMap<String, String>();
			map1.put("value", nodeArray[i]);
			map1.put("name", nodeArray[i]);
			output.add(map1);
		}
		model.addAttribute("tariffNodetype", output);

		return "waterTariff/wtTariffmaster";
	}

	@RequestMapping(value = "/wtTariff/read", method = RequestMethod.POST)
	public @ResponseBody
	List<WTTariffMaster> readTariff(@RequestBody Map<String, Object> model,
			HttpServletRequest req) {
		logger.info("el tariff id" + (Integer) model.get("elTariffID"));
		return wtariffMasterService
				.findAllOnParentId((Integer) model.get("wtTariffId"),
						(String) model.get("status"));
	}

	@RequestMapping(value = "/wtTariff/create", method = {RequestMethod.POST})
	public String addNode(@RequestParam("tariffCode") String tariffcode,
			@RequestParam("wtTariffId") int eltariffId,
			@RequestParam("treeHierarchy") String treeHierarchy,
			@RequestParam("tariffNodetype") String tariffnodetype,
			@RequestParam("status") String status,
			@RequestParam("tariffDescription") String tariffdescription,
			@RequestParam("tariffName") String tariffname,

			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		logger.info("valid to" + request.getParameter("validTo"));
		logger.info("validFrom" + request.getParameter("validFrom"));

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		WTTariffMaster tariff = new WTTariffMaster();
		tariff.setLastUpdatedDT((Timestamp) session
				.getAttribute("lastUpdatedDT"));
		tariff.setValidTo(dateTimeCalender.getdateFormat(request
				.getParameter("validTo")));
		tariff.setValidFrom(dateTimeCalender.getdateFormat(request
				.getParameter("validFrom")));
		tariff.setCreatedBy((String) session.
				getAttribute(("userId")));
		tariff.setLastUpdatedBy((String)session.
				getAttribute(("userId")));
		tariff.setTariffNodetype(tariffnodetype);
		tariff.setTariffName(tariffname);
		tariff.setTariffCode(tariffcode);
		tariff.setParentId(eltariffId);
		tariff.setTreeHierarchy(treeHierarchy + " > " + tariffname);
		tariff.setStatus("Active");
		tariff.setTariffDescription(tariffdescription);

		wtariffMasterService.save(tariff);

		PrintWriter out;

		List<WTTariffMaster> getId = wtariffMasterService.getTariffDetail(
				eltariffId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getWtTariffId();

		try {
			out = response.getWriter();
			out.write(value + "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/wtTariff/update", method = RequestMethod.POST)
	public String updateTariffMaster(
			@RequestParam("tariffCode") String tariffcode,
			@RequestParam("wtTariffId") int eltariffId,
			@RequestParam("treeHierarchy") String treeHierarchy,
			@RequestParam("status") String status,
			@RequestParam("tariffDescription") String tariffdescription,
			@RequestParam("tariffName") String tariffname,
			@RequestParam("tariffNodetype") String tariffnodetype,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		logger.info("valid to update" + request.getParameter("validTo"));
		logger.info("validFrom update"
				+ request.getParameter("validFrom"));

		WTTariffMaster tariffmaster = new WTTariffMaster();
		tariffmaster.setWtTariffId(eltariffId);
		List<WTTariffMaster> list = wtariffMasterService
				.getTariffNameBasedonTariffid(eltariffId);
		Integer parentId = list.get(0).getParentId();
		tariffmaster.setCreatedBy((String) session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedBy((String)session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedDT((Timestamp) session
				.getAttribute("lastUpdatedDT"));
		tariffmaster.setTariffName(tariffname);
		tariffmaster.setTariffCode(tariffcode);
		tariffmaster.setTariffNodetype(tariffnodetype);
		tariffmaster.setParentId(parentId);
	
		tariffmaster.setTreeHierarchy(treeHierarchy);
		tariffmaster.setTariffDescription(tariffdescription);
		tariffmaster.setStatus(status);
		tariffmaster.setValidTo(dateTimeCalender.getdateFormat(request
				.getParameter("validTo")));
		tariffmaster.setValidFrom(dateTimeCalender.getdateFormat(request
				.getParameter("validFrom")));

		wtariffMasterService.update(tariffmaster);

		PrintWriter out;
		List<WTTariffMaster> getId = wtariffMasterService.getTariffDetail(
				parentId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getWtTariffId();
		try {
			out = response.getWriter();
			out.write(value + "Updated successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/wtTariffMaster/getDetails/{nodeid}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	WTTariffMaster getDetailsOnNodeId(@PathVariable Integer nodeid,
			HttpServletRequest request, HttpServletResponse response) {
		WTTariffMaster tariff = new WTTariffMaster();
		tariff.setWtTariffId(nodeid);
		return wtariffMasterService.getNodeDetails(nodeid);
	}
	
	@RequestMapping(value = "/wttariff/delete", method = RequestMethod.POST)
	public @ResponseBody
	WTTariffMaster deleteTariff(@RequestParam("wtTariffId") int wtTariffId,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out;
		List<WTTariffMaster> list=wtariffMasterService.findAllOnParentIdwoStatus(wtTariffId);
		if(list.size()==0){
			wtariffMasterService.delete(wtTariffId);
		
		
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
