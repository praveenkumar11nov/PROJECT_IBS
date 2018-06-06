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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.model.ItemMasterEntity;
import com.bcits.bfm.model.StoreMasterEntity;
import com.bcits.bfm.service.helpDeskManagement.ItemMasterService;
import com.bcits.bfm.service.helpDeskManagement.StoreMasterService;
import com.bcits.bfm.view.BreadCrumbTreeService;
import com.novell.ldap.LDAPException;

import java.sql.SQLException;

import org.apache.commons.io.IOUtils;


@Controller
public class ItemMasterController {
	
	@Autowired
	private ItemMasterService itemMasterService;
	
	
	@Autowired
	private LdapBusinessModel ldapBusinessModel;
	
	@Autowired
	private StoreMasterService storeMasterService;
	
	@Resource
	private MessageSource messageSource;
	@Autowired
	private BreadCrumbTreeService 		breadCrumbService;
	
	
	@RequestMapping("/itemmaster")
	public String readIndex(Model model,HttpServletRequest request)
	{
		model.addAttribute("ViewName", "Customer Care");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Care", 1, request);
		breadCrumbService.addNode("Manage Item Master ", 2, request);
		return "/helpDesk/itemmaster";
	}	
	
	
	@RequestMapping(value = "/itemmaster/createitemmaster", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Object createDepartment(@ModelAttribute("itemMasterEntity") ItemMasterEntity itemMasterEntity, ModelMap model, SessionStatus sessionStatus, final Locale locale,HttpServletRequest req) throws Exception
	{
int storeId=Integer.parseInt(req.getParameter("sId"));
		Map crit=new HashMap();
		crit.put("sId", storeId);
	
		return itemMasterService.save(itemMasterEntity);
	}

	@RequestMapping(value = "/itemmaster/readitemmaster", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ItemMasterEntity> ItemsRead() {
		
		
		List<ItemMasterEntity> itemMasterEntities = itemMasterService.findAllData();
		return itemMasterEntities;
	}
	
	
	
	
	@RequestMapping(value = "/itemmaster/readitemmasterBasedOnId", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object ItemRead(@ModelAttribute("itemMasterEntity")ItemMasterEntity itemMasterEntity,HttpServletRequest req){
		//int storeId=Integer.parseInt(req.getParameter("sId"));
	
		return itemMasterService.findAllDatas(Integer.parseInt(req.getParameter("sId")));
	}
	
	@RequestMapping(value="/itemmaster/updateitemmaster", method = {RequestMethod.GET, RequestMethod.POST })	
	public @ResponseBody Object updateStore(@ModelAttribute("itemMasterEntity")ItemMasterEntity itemMasterEntity,SessionStatus sessionStatus) throws Exception
	{
		
		itemMasterService.update(itemMasterEntity);
	
		sessionStatus.setComplete();
		return itemMasterEntity;
	}
	
	
	@RequestMapping(value = "/itemmaster/deleteitemmaster", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object salaryComponentDestroy(@ModelAttribute("itemMasterEntity") ItemMasterEntity itemMasterEntity,BindingResult bindingResult, ModelMap model,HttpServletRequest req, SessionStatus sessionStatus) throws Exception 
	{
	
		try {
		
			itemMasterService.delete(itemMasterEntity.getGid());
		} catch (Exception e) {
			
		}
		sessionStatus.setComplete();
		return itemMasterEntity;
		}
	
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/item/upload/itemImage", method = RequestMethod.POST)
	public @ResponseBody
	void save(@RequestParam MultipartFile files, HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {

		int gid = Integer.parseInt(request.getParameter("gid"));
		Blob blob;
		blob = Hibernate.createBlob(files.getInputStream());
		itemMasterService.uploadImageOnId(gid, blob);
		byte[] imsgeBytes = files.getBytes();
		
			
		
		
	}
	
	
	
	
	@RequestMapping(value = "/item/upload/itemImage/{gid}", method ={RequestMethod.POST,RequestMethod.GET})
	 public @ResponseBody
	 String save(@RequestParam("file2") MultipartFile files,HttpServletRequest request,@PathVariable int gid ){

	  // Save the files
	 
	  ItemMasterEntity visitorObj = itemMasterService.find(gid);
	  //for (MultipartFile file : files) {

	  MultipartFile file = files;
	    String name=file.getOriginalFilename().toString();
	    String str[] =  StringUtils.split(name, ".");
	   Blob blob;
	   try {
	    blob = Hibernate.createBlob(file.getInputStream());
	  //  logger.info("blob is -"+blob);
	    visitorObj.setGid(gid);;
	 
	    visitorObj.setItemImage(blob);
	
	    itemMasterService.updateVisitorImage(gid, blob);
	   

	   } catch (IOException e) {

	 
	    e.printStackTrace();
	   }



	  return "";
	 }
	@RequestMapping("/item/getItemimage/{gid}")
	 public String getImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int gid) throws LDAPException, Exception {

	  response.setContentType("image/jpeg");
	  Blob blobImage = itemMasterService.getImage(gid);
	  int blobLength = 0;
	  byte[] blobAsBytes = null;
	  if (blobImage != null) {
	   blobLength = (int) blobImage.length();
	   blobAsBytes = blobImage.getBytes(1, blobLength);
	  }
	  OutputStream ot = response.getOutputStream();
	  try {
	   ot.write(blobAsBytes);
	   ot.close();
	  } catch (Exception e) {
	   //e.printStackTrace();
	  }
	  return null;
	 }
	
	@RequestMapping("/groccery/download/{gid}")
	 public String downloadJobDocument(@PathVariable("gid") int gid, HttpServletResponse response) throws Exception{
	  
	  
	
	   ItemMasterEntity itemMasterEntity = itemMasterService.find(gid);
	    try {
	             
	              if(itemMasterEntity.getItemImage() != null){
	               response.setHeader("Content-Disposition", "inline;filename=\"" +itemMasterEntity.getItemName()+ "\"");
	               OutputStream out = response.getOutputStream();
	               response.setContentType("image/jpeg");
	               IOUtils.copy(itemMasterEntity.getItemImage().getBinaryStream(), out);
	               out.flush();

	              out.close();
	              }
       
    }
catch (IOException e){
        e.printStackTrace();
    } 
catch (SQLException e){
        e.printStackTrace();
    }
return null;
}
	
	
	
}

