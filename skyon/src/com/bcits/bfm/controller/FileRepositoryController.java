package com.bcits.bfm.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.model.FileRepository;
import com.bcits.bfm.model.FileRepositoryTree;
import com.bcits.bfm.service.facilityManagement.FileRepositoryService;
import com.bcits.bfm.service.facilityManagement.FileRepositoryTreeService;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;


@Controller
public class FileRepositoryController {

	
	static Logger logger = LoggerFactory
			.getLogger(FileRepositoryController.class);
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private Validator validator; 
	
	@Autowired
	private JsonResponse jsonErrorResponse;	
	
	@Autowired
	private FileRepositoryService fileRepositoryService;
	
	
	@Autowired
	private FileRepositoryTreeService fileRepositoryTreeService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CommonController commonController;
	
	
	
	
	/* ------------------------------------------File repository Tree-------------------------  */
	
	
	@RequestMapping(value="/filerepository")
	public String filerepositoryindex(Model model,HttpServletRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		model.addAttribute("ViewName", "File Drawing");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("File Drawing", 1, request);
		breadCrumbService.addNode("Manage Document Category", 2, request);
		
		return "filedraw/filerepository";
	}
	
	@RequestMapping(value = "/filerepository/frgroup/readtree", method = RequestMethod.GET)
	public @ResponseBody
	List<FileRepositoryTree> readFileRepo_Group(HttpServletRequest req) {
		Integer setId=null;
		if(req.getParameter("frGroupId")!=null)
			setId = Integer.valueOf(req.getParameter("frGroupId"));
		return fileRepositoryTreeService.getFileRepoListById(setId);
	}
	
	
	@RequestMapping(value = "/filerepository/readtree", method = RequestMethod.POST)
	public @ResponseBody List<FileRepositoryTree> readfileRepositoryTree(@RequestBody Map<String, Object> model) {
		return fileRepositoryTreeService.getFileRepoListById((Integer)model.get("frGroupId"));
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/filerepository/create/node",method=RequestMethod.POST)
	public String createFilenode(@RequestParam("frGroupId") Integer frGroupId,@RequestParam("nodename") String nodename,@RequestParam("treeHierarchy") String treeHierarchy,@RequestParam("nodedescription") String nodedescription,HttpServletResponse response) throws IOException{
		
		FileRepositoryTree fileRepositoryTree=new FileRepositoryTree();
		PrintWriter writer;
		List lis=fileRepositoryTreeService.checkFileGroupUnique(WordUtils.capitalizeFully(nodename));
		logger.info("====================="+lis.size()+"-----------------"+lis);
		
		if(lis.size()>0){
			writer=response.getWriter();
			writer.write("File group Already Exist !!!");
		}else{
			String frnodename=nodename;
			String frnodename_trim=frnodename.trim();
		fileRepositoryTree.setFrGroupName(WordUtils.capitalizeFully(frnodename_trim));
		fileRepositoryTree.setParentId(frGroupId);
		String nodedesc=nodedescription;
		String nodedesc_trim=nodedesc.trim();
        fileRepositoryTree.setFrGroupDescription(nodedesc_trim);
        fileRepositoryTree.setTreeHierarchy(treeHierarchy);
		fileRepositoryTreeService.save(fileRepositoryTree);
		
		List<FileRepositoryTree> getId=fileRepositoryTreeService.findIdByParent(frGroupId, WordUtils.capitalizeFully(nodename));
		
		int value=getId.get(0).getFrGroupId();
		
		try{
			writer=response.getWriter();
			writer.write(value+"");
		}catch(IOException e){
			e.printStackTrace();
		}
		}
		return null;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/filerepository/update/node",method=RequestMethod.POST)
	public String updateFileRepoNode(@RequestParam("frGroupId") Integer frGroupId,@RequestParam("nodename") String frGroupName,@RequestParam("nodedescription") String frdescription,HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter writer;
		FileRepositoryTree fileRepositoryTree=new FileRepositoryTree();
		//fileRepositoryTree.setFileRepositoryId(fileRepositoryId);
		List lis=fileRepositoryTreeService.checkFileGroupUnique(WordUtils.capitalizeFully(frGroupName));
		logger.info("====================="+lis.size()+"-----------------"+lis);
		
		fileRepositoryTree.setFrGroupId(frGroupId);
		
		List<FileRepositoryTree> list_fileRepositoryTree=fileRepositoryTreeService.findAllOnfileRepositoryId(frGroupId);
		Integer parentId=list_fileRepositoryTree.get(0).getParentId();
		String frgroupname=frGroupName;
		String frgroupname_trim=frgroupname.trim();
		fileRepositoryTree.setFrGroupName(WordUtils.capitalizeFully(frgroupname_trim));
		String nodedesc=frdescription;
		String nodedesc_trim=nodedesc.trim();
		fileRepositoryTree.setFrGroupDescription(nodedesc_trim);
		fileRepositoryTree.setParentId(parentId);
		fileRepositoryTreeService.update(fileRepositoryTree);
		
		try{
			writer=response.getWriter();
			writer.write("updated Successfully !!!!");
		}catch(IOException e){
			e.printStackTrace();
		}
		//}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/filerepository/delete/node",method=RequestMethod.POST)
	public String deleteFilerepoNode(@RequestParam("frGroupId") Integer frGroupId,HttpServletRequest request,HttpServletResponse response){
		PrintWriter writer;
		List<FileRepositoryTree> list_filerepo=fileRepositoryTreeService.getFileRepoListById(frGroupId);
		List<FileRepository> file_Ids=fileRepositoryService.getIds(frGroupId);
		logger.info("list size ="+file_Ids.size());
		if(list_filerepo.size()==0 && file_Ids.size()==0 && frGroupId!=2){
			fileRepositoryTreeService.delete(frGroupId);
			try{
				writer=response.getWriter();
				writer.write("Deleted Sucessfully !!!");
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			try{
				writer=response.getWriter();
				writer.write("Only Leaf node And Empty leaf  can be deleted !!!");
			}catch(IOException e){
			}
		}
		return null;
	}
	
	@RequestMapping(value="/filerepository/getDetails/{nodeid}",method=RequestMethod.GET)
	public @ResponseBody FileRepositoryTree getfileRepoDetails(@PathVariable("nodeid") int nodeid){
		logger.info("i am in get details method of node");
		logger.info("node id is"+nodeid);
		FileRepositoryTree fileRepositoryTree=null;
		try{
		fileRepositoryTree=(FileRepositoryTree) fileRepositoryTreeService.findRepositoryBasedOnId(nodeid);
		return fileRepositoryTree;
		}catch(Exception e)
		{
			e.printStackTrace();
			return fileRepositoryTree;
		}
	}
	
	
	
	/* ------------------------------------------File repository Tree-------------------------  */
	
	/* ------------------------------------------File repository Master-------------------------  */
	
	@RequestMapping(value="/filerepositorymaster")
	public String filerepositorymasterindex(Model model,HttpServletRequest request, final Locale locale) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		model.addAttribute("ViewName", "File Drawing");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("File Drawing", 1, request);
		breadCrumbService.addNode("Manage Document", 2, request);
		
		model.addAttribute("status", commonController.populateCategories("values.project.status", locale));
		
		
		return "filedraw/filerepositorymaster";
	}
	
	
	@RequestMapping(value = "/filerepositorymaster/getfileRepositoryDocName", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getDocNames() {
		return 	fileRepositoryService.getDocName();
	}
	
	
	
	
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/filerepositorymaster/read", method = RequestMethod.POST)
	public @ResponseBody List<?> readfileRepositorymaster(@RequestBody Map<String, Object> model) {
	
	
		List<Map<String, Object>> file_list=new ArrayList<Map<String,Object>>();
		for(final FileRepository record:fileRepositoryService.findAll()){
			file_list.add(new HashMap<String,Object>(){{
				put("frId",record.getFrId());
				put("frGroupId",record.getFileRepositoryTree().getFrGroupId());
				put("frGroupName",record.getFileRepositoryTree().getFrGroupName());
				put("docName",record.getDocName());
				put("docDescription", record.getDocDescription());
				put("docType", record.getDocType());
				put("frSearchTag", record.getFrSearchTag());
				put("status", record.getStatus());
				put("createdby", record.getCreatedby());
				put("lastupdatedBy",record.getLastupdatedBy());
				
			}
			});
		}
		
	return file_list;
	}	
	
	
	
	
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value="/filerepositorymaster/create",method=RequestMethod.POST)
	public @ResponseBody Object createfileRepositoryMaster(@RequestBody Map<String, Object> map,ModelMap model,
			@ModelAttribute("filerepositorymaster") FileRepository fileRepositoryMaster,
			BindingResult bindingResult,SessionStatus sessionStatus, final Locale locale,HttpServletResponse response) throws IOException{
		
		
		List lis=fileRepositoryService.checkDocNameUnique(WordUtils.capitalizeFully((String)map.get("docName")));
		fileRepositoryMaster=fileRepositoryService.getFileRepositoryObject(map,"save",fileRepositoryMaster);
		validator.validate(fileRepositoryMaster, bindingResult);
		
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());
			return jsonErrorResponse;
		}
		 else {
				
					fileRepositoryMaster =  fileRepositoryService.save(fileRepositoryMaster);
					sessionStatus.setComplete();
		return fileRepositoryMaster;
		}
		
	}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value="/fileRepositoryMaster/update",method=RequestMethod.POST)
	public @ResponseBody Object updatefileRepositoryMaster(@RequestBody Map<String, Object> map,@ModelAttribute("filerepositorymaster")FileRepository fileRepositoryMaster,BindingResult bindingResult, ModelMap mod,
			SessionStatus sessionStatus, final Locale locale){
		logger.info("Inside update method");
		List lis=fileRepositoryService.checkDocNameUnique(WordUtils.capitalizeFully((String)map.get("docName")));
		
		fileRepositoryMaster=fileRepositoryService.getFileRepositoryObject(map,"update",fileRepositoryMaster);
        validator.validate(fileRepositoryMaster, bindingResult);
		if (bindingResult.hasErrors()) {
			jsonErrorResponse.setStatus("FAIL");
			jsonErrorResponse.setResult(bindingResult.getAllErrors());

			return jsonErrorResponse;
		}
		
		 else {
			 fileRepositoryMaster =  fileRepositoryService.update(fileRepositoryMaster);
			 fileRepositoryMaster.setDrFileBlob(null);
			 sessionStatus.setComplete();
		     return fileRepositoryMaster;
		}
	}
	
	/*@RequestMapping(value = "/filerepositorymaster/delete", method = RequestMethod.POST)
	public @ResponseBody FileRepository deleteDocument(@RequestBody Map<String, Object> map) throws IOException {
		FileRepository fileRepository = new FileRepository();
		logger.info("========================="+map.get("frId"));
		int frId =Integer.parseInt((String) map.get("frId"));
		fileRepositoryService.delete(frId);
		return fileRepository;
	}
	*/
	
	@RequestMapping(value = "/filerepositorymaster/delete", method = RequestMethod.POST)
	public @ResponseBody Object deleteUploadedDocument(@RequestBody Map<String, Object> map,@ModelAttribute("fileRepository") FileRepository fileRepository,SessionStatus sessionStatus) throws Exception{
     logger.info("In destroy FileRepository details method");
	    
	    JsonResponse errorResponse = new JsonResponse();
		if("Active".equalsIgnoreCase(map.get("status").toString())) {
			errorResponse.setStatus("AciveItemMasterDestroyError");
			return errorResponse;				
		}else{
		try {
			logger.info("========================="+map.get("frId"));
			int frId =Integer.parseInt((String) map.get("frId"));
			fileRepositoryService.delete(frId);
			
		} catch (Exception e) {
			
		}
		sessionStatus.setComplete();
		return fileRepository;
		}
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/filerepositorymaster/imageUploaddoc",method=RequestMethod.POST)
	public @ResponseBody void uploadFileDocument(@RequestParam MultipartFile files,HttpServletRequest request,HttpServletResponse response)throws IOException,SQLException{
		String type = null;
		logger.info("=============i am inside upload image===========--------");
		logger.info("&&&&&&&&&&&&&&"+request.getParameter("frId"));
		String name=files.getOriginalFilename().toString();
		String str[] =  StringUtils.split(name, ".");
		logger.info("==============================="+str.length);
		for(int i=0;i<str.length;i++){
			logger.info(i+"----"+str[i]);
			type=str[str.length-1];
		}
		int frId=Integer.parseInt(request.getParameter("frId"));
		Blob blob;
		blob=Hibernate.createBlob(files.getInputStream());
		logger.info("blob length is "+blob.length());
		
		
		fileRepositoryService.updateFileRepository(blob, frId,type);
		
	}
	
	
	@RequestMapping("/filerepositorymaster/uploaded_document/download/{frId}")
	public String downloadTrainigCertificate(
			@PathVariable("frId") Integer frId, HttpServletResponse response)
			throws Exception {
		FileRepository fp = fileRepositoryService.find(frId);
		if (fp.getDrFileBlob() != null) {
			try {

				response.setHeader("Content-Disposition", "inline;filename=\""
						+ fp.getDocName() + " "
						+ " - Repository Document."+fp.getDocType()+"\"");
				OutputStream out = response.getOutputStream();
				
				response.setContentType(fp.getDocType());
				IOUtils.copy(fp.getDrFileBlob().getBinaryStream(), out);
				out.flush();
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			 OutputStream out = response.getOutputStream();
         	IOUtils.write("File Not Found", out);
         	out.flush();
         	out.close();
			//return "filenotfound";
		}
return null;
	}

	
	
	/* ------------------------------------------File repository Master-------------------------  */
	@RequestMapping(value = "/filerepositorymaster/DocumentStatus/{frId}/{operation}", method = RequestMethod.POST)
	public String FrDocumentStatus(@PathVariable int frId,@PathVariable String operation,ModelMap model,HttpServletRequest request,HttpServletResponse response, final Locale locale)
	{	
		
		
		fileRepositoryService.setFrDocumetStatus(frId, operation, response, messageSource, locale);
		return null;
	}
	
	
	
	/*-------------------------filtering part for file repository-------------------------*/
	
	@RequestMapping(value="/filerepositorymaster/frGroupNameForFilter",method=RequestMethod.GET)
	public @ResponseBody List<?> FrGroupNAmeForFilter(){
		List<Map<String, Object>>  result=new ArrayList<Map<String,Object>>();
		
 		return result;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/downloaddocument/{frlist}")
	public String download(@PathVariable("frlist") int nodeid,HttpServletResponse response) throws Exception 
	{
		FileRepository fileRepository=fileRepositoryService.find(nodeid);
		
		try {
			
			if (fileRepository.getDrFileBlob() != null) {
				String ext=fileRepository.getDocType();
				
				response.setHeader("Content-Disposition", "inline;filename=\"" +fileRepository.getDocName()+"."+fileRepository.getDocType()+ "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(fileRepository.getDocType());
			IOUtils.copy(fileRepository.getDrFileBlob().getBinaryStream(), out);
			out.flush();
			out.close();
			
			} else {
				return " File Repository Document Not Found";
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	
				}
	
	
	@SuppressWarnings({ "unused", "serial" })
	@RequestMapping(value = "/filerepositroy/tree/viewdocument", method = RequestMethod.POST)
	public @ResponseBody
	List<?> getContacts(@RequestParam("frGroupId") int frGroupId,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("nodeid is================"+frGroupId);		
		List<FileRepository> listIds=fileRepositoryService.getFilerepositoryInfo(frGroupId);
		
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		for(final FileRepository record:fileRepositoryService.getFilerepositoryInfo(frGroupId)){
			
			result.add(new HashMap<String,Object>(){
				{
				put("frId",record.getFrId());
				put("docType",record.getDocType());
				put("docName",record.getDocName());
				if( record.getDrFileBlob() == null ){
					put("docImage","null");
				}
				else
				put("docImage","notNull");
				}
			});
		}
		return result;	
		
	}
	
	@RequestMapping(value = "/filerepositroy/getfilterValuesForcreatedBy", method = RequestMethod.GET)
	public @ResponseBody
	List<FileRepository> getCreatedBy(HttpServletRequest request, HttpServletResponse response) {
		return fileRepositoryService.findCreatedBy();
	}
	
	@RequestMapping(value = "/filerepositroy/getfilterValuesForLastUpdatedBy", method = RequestMethod.GET)
	public @ResponseBody
	List<FileRepository> getLastUpdatedBy(HttpServletRequest request, HttpServletResponse response) {
		return fileRepositoryService.findLastupdatedBy();
	}
	
	
	
	@RequestMapping(value = "/filerepositroy/getfilterValuesDocType", method = RequestMethod.GET)
	public @ResponseBody
	List<FileRepository> getDocType(HttpServletRequest request, HttpServletResponse response) {
		return fileRepositoryService.findDocType();
	}
	
	
	/** 
     * this method is used to read FileRepository Id from  the database
     * @see com.bcits.bfm.service.facilityManagement.fileRepositoryService#findAll()
     * @return         : returns  frId.  
     * @since           1.0
     */
	@RequestMapping(value="/filerepositroy/getfilterfrId",method=RequestMethod.GET)
	public @ResponseBody List<?> getfrIdToFilter(){
		List<String> result = new ArrayList<String>();
		List<FileRepository> list=fileRepositoryService.findAll();
		for (Iterator<FileRepository> iterator = list.iterator(); iterator.hasNext();) {
			FileRepository fileRepository = (FileRepository) iterator.next();
			result.add(Integer.toString(fileRepository.getFrId()));
		}
		return result;
	}
	
	
	
      }
