package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.DocumentDefiner;
import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentDefinerService;
import com.bcits.bfm.service.customerOccupancyManagement.DocumentRepositoryService;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module's Usecase
 * Module: Customer Occupancy 
 * Use Case : Document Repository
 * 
 * @author Mohammed Farooq
 * @version %I%, %G%
 * @since 0.1
 */
/**
 * @author user47
 *
 */

@Controller
public class DocumentRepositoryController 
{
	@Autowired
	private DocumentDefinerService documentDefinerService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private DocumentRepositoryService documentRepositoryService;
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private CommonService commonService;
	/**
	 * Request for Document Definer page
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param locale
	 * @return documentdefiner View page
	 */
	@RequestMapping("/documentdefiner")
	public String documentRepoIndex(HttpServletRequest request,HttpServletResponse response, Model model,final Locale locale)
	{
		 model.addAttribute("ViewName", "Customer Occupancy");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage Document Definer", 2, request);
		model.addAttribute("ddType", commonController.populateCategories("value.person.type", locale));
		model.addAttribute("documentFormat",commonController.populateCategories("value.document.format", locale));
		model.addAttribute("statusYesNo",commonController.populateCategories("values.address.addressPrimary", locale));
		return "com/documentdefiner";
	}
	
	/**
	 * Read all document definer
	 * @return List of documents defined
	 */
	@RequestMapping(value = "/documentdefiner/read",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	List<?> readDocumentDefiner() 
	{
		return documentDefinerService.findAll();
	}
	
	/**
	 * Get document type to populate in dropdownlist,Filters
	 * @param locale
	 * @return list of document type
	 */
	@RequestMapping(value = "/documentdefiner/getddtype", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getDocumentType(final Locale locale) 
	{
		String[] personStyle = messageSource.getMessage("value.person.type", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < personStyle.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", personStyle[i]);
			map.put("name", personStyle[i]);
			
			result.add(map);
		}
	    return result;
	}
	
	
	/**
	 * Get all document formats to populate in dropdownlist,filters
	 * @param locale
	 * @return list of document type
	 */
	@RequestMapping(value = "/documentdefiner/getddformat", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> getDocumentFormat(final Locale locale) 
	{
		String[] personStyle = messageSource.getMessage("value.document.format", null, locale).split(",");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < personStyle.length; i++)
		{
			map = new HashMap<String, String>();
			map.put("value", personStyle[i]);
			map.put("name", personStyle[i]);
			
			result.add(map);
		}
	    return result;
	}
	
	
	/**
	 * Create or Update the document definer
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @param model
	 * @param documentDefiner
	 * @return DocumentDefiner Object
	 */
	@RequestMapping("/documentdefiner/cu")
	public @ResponseBody Object CreateUpdateDocumentDefiner(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@ModelAttribute("documentdefiner") DocumentDefiner documentDefiner)
	{
		String operation = request.getParameter("action");
		if(operation.equalsIgnoreCase("create"))
		{
			documentDefiner = documentDefinerService.getdocumentDefinerObject(map, "save", documentDefiner);
			documentDefinerService.save(documentDefiner);
		}
		else if(operation.equalsIgnoreCase("update"))
		{
			documentDefiner = documentDefinerService.getdocumentDefinerObject(map, "update", documentDefiner);
			documentDefinerService.update(documentDefiner);
		}
		
		return documentDefiner;
	}
	
	/**
	 * Get documents based on personType to populate in dropdown (personType = Owner,Tenant,Domestic Help,Pet)
	 * 
	 * @param personType
	 * @return list of document
	 */
	@RequestMapping("/documentdefiner/getAllDocument/{personType}")
	public @ResponseBody List<Map<String, String>> getDdBasedonPersonType(@PathVariable String personType)
	{
		List<DocumentDefiner> documentDefiner =(List<DocumentDefiner>)documentDefinerService.getAllBasedOnType(personType);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (DocumentDefiner record : documentDefiner)
		{
				map = new HashMap<String, String>();
				map.put("name", record.getDdName());
				map.put("ddId", record.getDdId()+"");
				result.add(map);
		}
	    return result;
	}
	
	
	
	/**
	 * Ajax Call to get the document format for Client side validation for document upload
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/documentDefiner/getDocumentFormat")
	public @ResponseBody void getDdFormatBasedOnPersonType(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int selectedDocument = Integer.parseInt(request.getParameter("documentTypeSelected"));
		String personType = request.getParameter("personType");
		String ddFormat = documentDefinerService.getDocumentFormatOnPersonType(selectedDocument,personType);
		PrintWriter out = response.getWriter();
		out.write(ddFormat);
		 
	}
	
	/**
	 * Get all document names for filters
	 * @return list of document names
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/documentdefiner/getDocumentNames",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<String> getUniqueDocumentNames()
	{
		List list = documentDefinerService.executeSimpleQuery("SELECT DISTINCT(d.ddName) FROM DocumentDefiner d");
		List<String> tempList = new ArrayList<String>();
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			tempList.add((String) it.next());
		}
		return tempList;
	}
	
	/**
	 * Document Status Activate/Deactivate based on documentId(ddId)
	 * 
	 * @param ddId
	 * @param operation
	 * @param response
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/documentdefiner/documentStatus/{ddId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public String accessCardStatus(@PathVariable int ddId, @PathVariable String operation, HttpServletResponse response, final Locale locale)
	{
		documentDefinerService.updateAccessCardStatus (ddId,operation,response);
		return null;
	}
	
	
	/**
	 * Request to View the Document Repository
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return documentrepository view page
	 */
	@RequestMapping("/documentrepository")
	public String documentrepositoryIndex(HttpServletRequest request,HttpServletResponse response, Model model)
	{
		 model.addAttribute("ViewName", "Customer Occupancy");
			breadCrumbService.addNode("nodeID", 0, request);
			breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage Document Repository", 2, request);
		return "com/documentrepository";
	}
	
	/**
	 * Document Repository Read
	 * 
	 * @return list of document repository objects
	 */
	@RequestMapping(value = "/documentrepository/read",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> readdocumentrepository() 
	{
		return documentRepositoryService.findAll();
	}
	
	@RequestMapping(value = "/documentrepository/commonFilterForDocumentUrl/{feild}", method = RequestMethod.GET)
	public @ResponseBody Set<?> getHelpTopicContentsForFilter(@PathVariable String feild) {
		
		List<String> attributeList = new ArrayList<String>();
		attributeList.add(feild);
		HashSet<Object> set = new HashSet<Object>(commonService.selectQuery("DocumentRepository",attributeList, null));
		
		return set;
	}
	
	@RequestMapping(value = "/documentrepository/personFilterForDocumentUrl", method = RequestMethod.GET)
	public @ResponseBody Set<?> personFilterForDocumentUrl() 
	{
		Set<String> data =new HashSet<String>();
		for(final Object[] s:documentRepositoryService.getPerNameFil())
		{
			data.add(((String)s[0]+" "+(String)s[1]));
		}
		
		return 	data;
	}
	/**
	 * Approve Or Reject Document 
	 * 
	 * @param drId
	 * @param operation
	 * @param response
	 * @param locale
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/documentrepository/documentApprove/{drId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public String documentRepoApproveDocument(@PathVariable int drId, @PathVariable String operation, HttpServletResponse response, final Locale locale) throws IOException
	{
		PrintWriter out=response.getWriter();
		DocumentRepository d=documentRepositoryService.find(drId);
		if(d!=null)
		{
			if(d.getDocumentFile()!=null&&operation.equalsIgnoreCase("Approve"))
			{
				documentRepositoryService.updateApproveStatus(drId,operation,response);
			}
			else
			{
				out.println("Without Document You Cannot Approve");
			}
			
		}
		
	
		return null;
	}
	
}
