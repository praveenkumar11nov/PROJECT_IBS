package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.ELTariffMaster;
import com.bcits.bfm.service.tariffManagement.EL_Tariff_MasterServices;
import com.bcits.bfm.service.tariffManagement.TariffCalculationService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.util.SessionData;
import com.bcits.bfm.view.BreadCrumbTreeService;

@Controller
public class TariffManagementController 
{
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private EL_Tariff_MasterServices tariffMasterService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	DateTimeCalender dateTimeCalender = new DateTimeCalender();
	
	static Logger logger = LoggerFactory.getLogger(TariffManagementController.class);	
			                        
	@Autowired 
	private TariffCalculationService tariffcalc;
	
	@RequestMapping(value = "/tariffmaster", method = RequestMethod.GET)
	public String indexTariffMaster(ModelMap model, HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		logger.info("::::::::::::::: Electricity Tariff Management ::::::::::::::::::");
		model.addAttribute("ViewName", " Tariff");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Tariff", 1, request);
		breadCrumbService.addNode("Manage Electricity Tariff Master", 2, request);
		model.addAttribute("username",
				SessionData.getUserDetails().get("userID"));
		
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
			String[] nodeArray ={"Tariff Rate Node","Hierarchical Node","State Node"};
			for (int i = 0; i < nodeArray.length; i++)
			{
				map1 = new HashMap<String, String>();
				map1.put("value", nodeArray[i]);
				map1.put("name", nodeArray[i]);
				output.add(map1);
			}
			 model.addAttribute("tariffNodetype", output);
		 
		 
		return "tariff/tariffmaster";
	}
	
	@RequestMapping(value = "/tariff/create", method = RequestMethod.POST)
	public String addNode(@RequestParam("tariffCode")String tariffcode,
			@RequestParam("elTariffID") int eltariffId,@RequestParam("treeHierarchy")String treeHierarchy,@RequestParam("tariffNodetype")String tariffnodetype,
			@RequestParam("status") String status,@RequestParam("tariffDescription") String tariffdescription,@RequestParam("tariffName")String tariffname,
			
			HttpServletRequest request, HttpServletResponse response/*,@ModelAttribute("tariffm")EL_Tariff_Master tariffm,Map<String, Object> map1*/ ) throws ParseException{

		logger.info("valid to"+request.getParameter("validTo"));
		logger.info("validFrom"+request.getParameter("validFrom"));
		
		 HttpSession session = request.getSession(true);
		 session.getAttribute("userId");		 
	     ELTariffMaster tariff=new ELTariffMaster();	

	tariff.setCreatedBy((String)session.getAttribute(("userId")));  
	tariff.setLastUpdatedDT((Timestamp)session.getAttribute("lastUpdatedDT"));
	
	tariff.setValidTo(dateTimeCalender.getdateFormat(request.getParameter("validTo")));
	tariff.setValidFrom(dateTimeCalender.getdateFormat(request.getParameter("validFrom")));
	
	
	    tariff.setTariffNodetype(tariffnodetype);
	     tariff.setTariffName(tariffname);
		tariff.setTariffCode(tariffcode);
		tariff.setParentId(eltariffId);
		tariff.setTreeHierarchy(treeHierarchy+" > "+tariffname);		
		tariff.setStatus("Active");
		tariff.setTariffDescription(tariffdescription);
		String treeh=tariff.getTreeHierarchy();
		String[] treeSplit=treeh.split(">");
		String stateStatus=treeSplit[1];	
		logger.info("::::::::::::::::::::"+stateStatus);	
		
		String replac=stateStatus.replace("Active", "");		
		logger.info("########::::::::::::"+replac);
		logger.info("########$$$$$$$$$"+replac.trim());
		tariff.setStateName(replac.trim());
	     tariffMasterService.save(tariff);
	    PrintWriter out;	
		//List<ELTariffMaster> getId=tariffMasterService.getTariffDetail(eltariffId,tariffname);
		//logger.info(""+getId.size());
		
		//int value=getId.get(0).getElTariffID();
		
		try{
			out = response.getWriter();
			//out.write(value + "");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	
	
	@RequestMapping(value = "/tariff/update", method = RequestMethod.POST)
	public String 	updateTariffMaster(@RequestParam("tariffcode")String tariffcode,
			@RequestParam("eltariffId") int eltariffId,@RequestParam("treeHierarchy")String treeHierarchy,
			@RequestParam("status") String status,@RequestParam("tariffdescription") String tariffdescription,@RequestParam("tariffName")String tariffname,
			@RequestParam("tariffNodetype")String tariffnodetype,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		
		
		HttpSession session = request.getSession(true);
		session.getAttribute("userId");
		
		logger.info("valid to update"+request.getParameter("validTo"));
		logger.info("validFrom update"+request.getParameter("validFrom"));
		
		ELTariffMaster tariffmaster =new ELTariffMaster();
		tariffmaster.setElTariffID(eltariffId);
		List<Integer> list=tariffMasterService. getTariffNameBasedonTariffid(eltariffId);
		Integer parentId=list.get(0);
		tariffmaster.setCreatedBy((String)session.getAttribute(("userId")));  
		java.util.Date date= new java.util.Date();
		tariffmaster.setLastUpdatedDT(new Timestamp(date.getTime()));
		/*tariffmaster.setLastUpdatedDT((Timestamp)session.getAttribute("lastUpdatedDT"));*/
		tariffmaster.setTariffName(tariffname);
		tariffmaster.setTariffCode(tariffcode);
		tariffmaster.setTariffNodetype(tariffnodetype);
		tariffmaster.setParentId(parentId);;
		tariffmaster.setTreeHierarchy(treeHierarchy);
		tariffmaster.setTariffDescription(tariffdescription);
		tariffmaster.setStatus(status);
		tariffmaster.setValidTo(dateTimeCalender.getdateFormat(request.getParameter("validTo")));
		tariffmaster.setValidFrom(dateTimeCalender.getdateFormat(request.getParameter("validFrom")));
		String treeh=tariffmaster.getTreeHierarchy();
		String[] treeSplit=treeh.split(">");
		String stateStatus=treeSplit[1];	
		logger.info("::::::::::::::::::::"+stateStatus);	
		
		String replac=stateStatus.replace("Active", "");
		
		//logger.info(":::::::::::::::::::::"+state);
		logger.info("########::::::::::::"+replac);
		logger.info("########$$$$$$$$$"+replac.trim());
		tariffmaster.setStateName(replac.trim());
		tariffMasterService.update(tariffmaster);
		
		PrintWriter out;
	//List<ELTariffMaster> getId=tariffMasterService.getTariffDetail(parentId,tariffname);
		//logger.info(""+getId.size());
		try {
			out=response.getWriter();
			out.write("Updated successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@RequestMapping(value = "/tariff/delete", method = RequestMethod.POST)
	public @ResponseBody
	ELTariffMaster deleteAssets(@RequestParam("eltariffId") int eltariffId,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out;
		List<ELTariffMaster> list=tariffMasterService.findAllOnParentIdwotStatus(eltariffId);
		if(list.size()==0){
			tariffMasterService.delete(eltariffId);
		
		
		try {
			out = response.getWriter();
			out.write("Deleted Successfully!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}}
	 else {
		try {
			out = response.getWriter();
			out.write("Only Leaf node can be deleted!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		
		return null;
	}
	
	@RequestMapping(value = "/tariff/read", method = RequestMethod.POST)
	public @ResponseBody
	List<ELTariffMaster> readTariff(@RequestBody Map<String, Object> model,HttpServletRequest req){
		
	
		logger.info("el tariff id"+(Integer) model.get("elTariffID"));
		return tariffMasterService.findAllOnParentId((Integer) model.get("elTariffID"),(String)model.get("status"));
		}
	
	@RequestMapping(value = "/tariff/readall", method = RequestMethod.POST)
	public @ResponseBody
	
	
List<ELTariffMaster> readAllTariff(@RequestBody Map<String, Object> model){
		
	
		logger.info("\n\n\n Controller "+(Integer) model.get("elTariffID"));
		
		
		return tariffMasterService.findAllOnParentIdwotStatus((Integer) model.get("elTariffID"));
	
	}
	
	@RequestMapping(value="/tariffMaster/gettariffname/{tariff_Name}",method=RequestMethod.POST)
	public @ResponseBody ELTariffMaster getTariffNameOnstatus(@PathVariable("tariff_Name")String tariffname,HttpServletRequest req,HttpServletResponse res){
		
		
		logger.info(tariffname);
		PrintWriter out;
		List<String> list=tariffMasterService.getTariffName(tariffname);
		if(list.size()==0){
			try {
				out = res.getWriter();
				out.write("OK");
			} catch (IOException e) {
				e.printStackTrace();
			}}else{
				try {
					out = res.getWriter();
					out.write("Namefound");
				} catch (IOException e) {
					e.printStackTrace();
				}}
			
		return null;
	}
	
	@RequestMapping(value="/tariffMaster/getDetails/{nodeid}",method=RequestMethod.GET)
	public @ResponseBody ELTariffMaster getDetailsOnNodeId(@PathVariable Integer nodeid,HttpServletRequest request,HttpServletResponse response)
	{
		ELTariffMaster tariff=new ELTariffMaster();
		tariff.setElTariffID(nodeid);
		
		
	   	return tariffMasterService.getNodeDetails(nodeid);
	}


	
}