package com.bcits.bfm.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class RequestApprovalController {

	@RequestMapping("/requestapproval")
	public String index(Model model, HttpServletRequest request) {

	
		
		return "workflow/requestapproval";
	}
	
	
	/*@RequestMapping(value = "/requestMaster/readApprovalRequestMasterUrl", method = RequestMethod.POST)
	 public @ResponseBody List<?> readApprovalRequestMasterUrl(HttpServletRequest req)
	 {
	  HttpSession session=req.getSession(false);
	  
	  String userName=(String)session.getAttribute("userName").toString();
	  System.out.println("================================================"+userName);
//	return 
	 return requestMasterApprovalService.findAllApprovalRequset(userName);
	 }*/
}
