package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;









import com.bcits.bfm.patternMasterEntity.PettyTransactionManager;
import com.bcits.bfm.patternMasterService.PettyTransactionManagerService;
import com.bcits.bfm.patternMasterService.TransactionMasterService;
import com.bcits.bfm.util.DateTimeCalender;
import com.bcits.bfm.view.BreadCrumbTreeService;
/*import com.smartadmin.core.dao.GenericDAO;
import com.smartadmin.dao.IPettySettlementMasterDAO;
import com.smartadmin.entity.Category;
import com.smartadmin.entity.PettySettlementMaster;
import com.smartadmin.entity.PettyTransactionManager;
import com.smartadmin.entity.RegisterMaster;
import com.smartadmin.entity.RequestApprovalMaster;
import com.smartadmin.entity.RequestMaster;
import com.smartadmin.service.CategoryService;
import com.smartadmin.service.EmployeeMasterService;
import com.smartadmin.service.PettySettlementApprovalService;
import com.smartadmin.service.PettySettlementMasterService;
import com.smartadmin.service.PettyTransactionManagerService;
import com.smartadmin.service.RegisterMasterService;
import com.smartadmin.service.RequestMasterApprovalService;
import com.smartadmin.service.RequestMasterService;
import com.smartadmin.util.DateTimeCalender;
import com.smartadmin.util.JsonResponse;*/
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class PettyCashManagementController {
	private static final Log logger = LogFactory.getLog(PettyCashManagementController.class);
	
	@Autowired
	private PettyTransactionManagerService pettyTransactionManagerService;
	
	@Autowired
	private TransactionMasterService transactionMasterService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@RequestMapping("/workFlowEngine")
	public String index(Model model, HttpServletRequest request) {

		model.addAttribute("ViewName", "Work Flow");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Work Flow", 1, request);
		breadCrumbService.addNode("Work Flow Engine", 2, request);
		
		return "workflow/workFlowEngine";
	}
	
	
	@RequestMapping(value = "/transactionManager/readAllProcessNamesUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> readAllProcessNamesUrl(Model model,HttpServletRequest request,HttpServletResponse response,ModelMap model1) 
	{
		return  pettyTransactionManagerService.readAllProcessNames();
	}
	
	
	@RequestMapping(value = "/transactionManager/readAllTransactionTypeUrl", method = RequestMethod.GET)
	public @ResponseBody List<?> transactionReadUrl(Model model,HttpServletRequest request,HttpServletResponse response,ModelMap model1) 
	{
		//HttpSession session=request.getSession(false);
	//	Integer officeId=(Integer) session.getAttribute("officeId");
		return  pettyTransactionManagerService.getTransactionType();
	}
	
	@RequestMapping(value = "/transactionManager/createTransactionManagerUrl", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object createTransactionManager(@ModelAttribute("pettyTransactionManager")PettyTransactionManager pettyTransactionManager, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception 
	{
		
		return	pettyTransactionManagerService.save(pettyTransactionManager);
		//return pettyTransactionManagerService.readTransactionManager(officeId);   	
	}
	
	@RequestMapping(value = "/transactionManager/readTransactionManagerUrl", method = RequestMethod.POST)
	public @ResponseBody List<?> getReadTransactionManagerData( HttpServletRequest req)
	{
		
		
		//return pettyTransactionManagerService.findall();
		return pettyTransactionManagerService.readTransactionManager();  
	}

	
	@RequestMapping(value = "/transactionManager/updateTransactionManagerUrl", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object TransactionManagerUrlUpdate(@ModelAttribute("pettyTransactionManager")PettyTransactionManager pettyTransactionManager,BindingResult bindingResult, ModelMap model,SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws ParseException {

	logger.info("In WorkFlow Update Method");

	pettyTransactionManagerService.update(pettyTransactionManager);
	return pettyTransactionManagerService.readTransactionManager();
	}
	
	
	
	
	
}
