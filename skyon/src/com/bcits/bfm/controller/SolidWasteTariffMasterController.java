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

import com.bcits.bfm.model.SolidWasteTariffMaster;
import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.model.SolidWasteTariffMaster;

import com.bcits.bfm.service.solidWasteTariffManagment.SolidWasteTariffMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;


@Controller
public class SolidWasteTariffMasterController {
	@Autowired
	private BreadCrumbTreeService breadCrumbService;

	@Autowired
	SolidWasteTariffMasterService solidWasteTariffMasterService;

	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	static Logger logger = LoggerFactory.getLogger(BroadTeleTariffManagmentController.class);

	@RequestMapping(value = "/solidWasteTariffMaster", method = RequestMethod.GET)
	public String indexTariffMaster(ModelMap model, HttpServletRequest request)throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		logger.info("::::::::::::::: Broad Band Tele Controller ::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Solid Waste Tariff Master", 2, request);
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

		return "solidWasteTariff/solidWasteTariffmaster";
	}

	@RequestMapping(value = "/swTariff/read", method = RequestMethod.POST)
	public @ResponseBody
	List<SolidWasteTariffMaster> readTariff(@RequestBody Map<String, Object> model,
			HttpServletRequest req) {
		logger.info("el tariff id" + (Integer) model.get("solidWasteTariffId"));
		return solidWasteTariffMasterService
				.findAllOnParentId((Integer) model.get("solidWasteTariffId"),
						(String) model.get("status"));
	}

	@RequestMapping(value = "/swTariff/create", method = RequestMethod.POST)
	public String addNode(@RequestParam("solidWasteTariffCode") String tariffcode,
			@RequestParam("solidWasteTariffId") int eltariffId,
			@RequestParam("solidWastetreeHierarchy") String treeHierarchy,
			@RequestParam("solidWastetariffNodetype") String tariffnodetype,
			@RequestParam("status") String status,
			@RequestParam("solidWastetariffDescription") String tariffdescription,
			@RequestParam("solidWasteTariffName") String tariffname,

			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		logger.info("valid to" + request.getParameter("validTo"));
		logger.info("validFrom" + request.getParameter("validFrom"));

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		SolidWasteTariffMaster tariff = new SolidWasteTariffMaster();
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
		tariff.setSolidWastetariffNodetype(tariffnodetype);
		tariff.setSolidWasteTariffName(tariffname);
		tariff.setSolidWasteTariffCode(tariffcode);
		tariff.setSolidWasteparentId(eltariffId);
		tariff.setSolidWastetreeHierarchy(treeHierarchy + " > " + tariffname);
		tariff.setStatus("Active");
		tariff.setSolidWastetariffDescription(tariffdescription);

		solidWasteTariffMasterService.save(tariff);

		PrintWriter out;

		List<SolidWasteTariffMaster> getId = solidWasteTariffMasterService.getTariffDetail(
				eltariffId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getSolidWasteTariffId();

		try {
			out = response.getWriter();
			out.write(value + "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	@RequestMapping(value = "/swTariffMaster/getDetails/{nodeid}", method = RequestMethod.GET)
	public @ResponseBody
	SolidWasteTariffMaster getDetailsOnNodeId(@PathVariable Integer nodeid,
			HttpServletRequest request, HttpServletResponse response) {
		SolidWasteTariffMaster tariff = new SolidWasteTariffMaster();
		tariff.setSolidWasteTariffId(nodeid);
		return solidWasteTariffMasterService.getNodeDetails(nodeid);
	}
	
	
	
	@RequestMapping(value = "/swTariff/update", method = RequestMethod.POST)
	public String updateTariffMaster(
			@RequestParam("solidWasteTariffCode") String tariffcode,
			@RequestParam("solidWasteTariffId") int eltariffId,
			@RequestParam("solidWastetreeHierarchy") String treeHierarchy,
			@RequestParam("solidWastetariffNodetype") String tariffnodetype,
			@RequestParam("status") String status,
			@RequestParam("solidWastetariffDescription") String tariffdescription,
			@RequestParam("solidWasteTariffName") String tariffname,

			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {

		HttpSession session = request.getSession(true);
		session.getAttribute("userId");

		logger.info("valid to update" + request.getParameter("validTo"));
		logger.info("validFrom update"
				+ request.getParameter("validFrom"));

		SolidWasteTariffMaster tariffmaster = new SolidWasteTariffMaster();
		tariffmaster.setSolidWasteTariffId(eltariffId);
		List<SolidWasteTariffMaster> list =solidWasteTariffMasterService
				.getTariffNameBasedonTariffid(eltariffId);;
		Integer parentId = list.get(0).getSolidWasteparentId();
		tariffmaster.setCreatedBy((String) session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedBy((String)session.getAttribute(("userId")));
		tariffmaster.setLastUpdatedDT((Timestamp) session
				.getAttribute("lastUpdatedDT"));
		tariffmaster.setSolidWasteTariffName(tariffname);
		tariffmaster.setSolidWasteTariffCode(tariffcode);
		tariffmaster.setSolidWastetariffNodetype(tariffnodetype);
		tariffmaster.setSolidWasteparentId(parentId);
	
		tariffmaster.setSolidWastetreeHierarchy(treeHierarchy);
		tariffmaster.setSolidWastetariffDescription(tariffdescription);
		tariffmaster.setStatus(status);
		tariffmaster.setValidTo(dateTimeCalender.getdateFormat(request
				.getParameter("validTo")));
		tariffmaster.setValidFrom(dateTimeCalender.getdateFormat(request
				.getParameter("validFrom")));

		solidWasteTariffMasterService.update(tariffmaster);

		PrintWriter out;
		List<SolidWasteTariffMaster> getId = solidWasteTariffMasterService.getTariffDetail(
				parentId, tariffname);
		logger.info(""+getId.size());

		int value = getId.get(0).getSolidWasteTariffId();
		try {
			out = response.getWriter();
			out.write(value + "Updated successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value = "/swtariff/delete", method = RequestMethod.POST)
	public @ResponseBody
	ELTariffMaster deleteAssets(@RequestParam("wtTariffId") int wtTariffId,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out;
		List<SolidWasteTariffMaster> list=solidWasteTariffMasterService.findAllOnParentIdwoStatus(wtTariffId);
		if(list.size()==0){
			solidWasteTariffMasterService.delete(wtTariffId);
		
		
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
