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

import com.bcits.bfm.model.CommonTariffMaster;

import com.bcits.bfm.service.commonServicesTariffManagement.CommonServiceTariffMasterServices;

import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class CommonTariffManagmentController {

	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	CommonServiceTariffMasterServices commonServiceTariffMasterServices;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	static Logger logger = LoggerFactory.getLogger(WaterTariffManagmentController.class);

	@RequestMapping(value = "/commonServiceTariffMaster", method = RequestMethod.GET)
	public String indexTariffMaster(ModelMap model, HttpServletRequest request)throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		logger.info("::::::::::::::: Water Tariff Management ::::::::::::::::::");
		model.addAttribute("ViewName", "Tariff Management");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Water Tariff Master", 1, request);
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

		return "commonServicesTariff/commonServiceTariffMaster";
	}

	@RequestMapping(value = "/csTariff/read", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonTariffMaster> readTariff(@RequestBody Map<String, Object> model,
			HttpServletRequest req) {
		logger.info("cs tariff id" + (Integer) model.get("csTariffID"));
		return  commonServiceTariffMasterServices
				.findAllOnParentId((Integer) model.get("csTariffID"),
						(String) model.get("status"));
	}

	@RequestMapping(value = "/csTariff/create", method = RequestMethod.POST)
	public String addNode(@RequestParam("csTariffCode") String tariffcode,
			@RequestParam("csTariffID") int eltariffId,
			@RequestParam("csTreeHierarchy") String treeHierarchy,
			@RequestParam("csTariffNodetype") String tariffnodetype,
			@RequestParam("status") String status,
			@RequestParam("csTariffDescription") String tariffdescription,
			@RequestParam("csTariffName") String tariffname,

			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		logger.info("valid to" + request.getParameter("validTo"));
		logger.info("validFrom" + request.getParameter("validFrom"));

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		CommonTariffMaster tariff = new CommonTariffMaster();
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
		tariff.setCsTariffNodetype(tariffnodetype);
		tariff.setCsTariffName(tariffname);
		tariff.setCsTariffCode(tariffcode);
		tariff.setCsParentId(eltariffId);
		tariff.setCsTreeHierarchy(treeHierarchy + " > " + tariffname);
		tariff.setStatus("Active");
		tariff.setCsTariffDescription(tariffdescription);

		commonServiceTariffMasterServices.save(tariff);

		PrintWriter out;

		List<CommonTariffMaster> getId = commonServiceTariffMasterServices.getTariffDetail(
				eltariffId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getCsTariffID();

		try {
			out = response.getWriter();
			out.write(value + "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/csTariff/update", method = RequestMethod.POST)
	public String updateTariffMaster(
			@RequestParam("csTariffCode") String tariffcode,
			@RequestParam("csTariffID") int eltariffId,
			@RequestParam("csTreeHierarchy") String treeHierarchy,
			@RequestParam("status") String status,
			@RequestParam("csTariffDescription") String tariffdescription,
			@RequestParam("csTariffName") String tariffname,
			@RequestParam("csTariffNodetype") String tariffnodetype,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		logger.info("valid to update" + request.getParameter("validTo"));
		logger.info("validFrom update"
				+ request.getParameter("validFrom"));

		CommonTariffMaster tariffmaster = new CommonTariffMaster();
		tariffmaster.setCsTariffID(eltariffId);
		List<CommonTariffMaster> list = commonServiceTariffMasterServices
				.getTariffNameBasedonTariffid(eltariffId);
		Integer parentId = list.get(0).getCsParentId();
		tariffmaster.setCreatedBy((String) session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedBy((String)session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedDT((Timestamp) session
				.getAttribute("lastUpdatedDT"));
		tariffmaster.setCsTariffName(tariffname);
		tariffmaster.setCsTariffCode(tariffcode);
		tariffmaster.setCsTariffNodetype(tariffnodetype);
		tariffmaster.setCsParentId(parentId);
	
		tariffmaster.setCsTreeHierarchy(treeHierarchy);
		tariffmaster.setCsTariffDescription(tariffdescription);
		tariffmaster.setStatus(status);
		tariffmaster.setValidTo(dateTimeCalender.getdateFormat(request
				.getParameter("validTo")));
		tariffmaster.setValidFrom(dateTimeCalender.getdateFormat(request
				.getParameter("validFrom")));

		commonServiceTariffMasterServices.update(tariffmaster);

		PrintWriter out;
		List<CommonTariffMaster> getId = commonServiceTariffMasterServices.getTariffDetail(
				parentId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getCsTariffID();
		try {
			out = response.getWriter();
			out.write(value + "Updated successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/csTariffMaster/getDetails/{nodeid}", method = RequestMethod.GET)
	public @ResponseBody
	CommonTariffMaster getDetailsOnNodeId(@PathVariable Integer nodeid,
			HttpServletRequest request, HttpServletResponse response) {
		CommonTariffMaster tariff = new CommonTariffMaster();
		tariff.setCsTariffID(nodeid);
		return commonServiceTariffMasterServices.getNodeDetails(nodeid);
	}
	
	@RequestMapping(value = "/cstariff/delete", method = RequestMethod.POST)
	public @ResponseBody
	CommonTariffMaster deleteTariff(@RequestParam("csTariffID") int csTariffID,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out;
		List<CommonTariffMaster> list=commonServiceTariffMasterServices.findAllOnParentIdwoStatus(csTariffID);
		if(list.size()==0){
			commonServiceTariffMasterServices.delete(csTariffID);
		
		
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
