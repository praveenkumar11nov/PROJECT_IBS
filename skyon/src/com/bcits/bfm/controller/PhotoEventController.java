package com.bcits.bfm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RespectBinding;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.DocumentRepository;
import com.bcits.bfm.model.Faq;
import com.bcits.bfm.model.PhotoEvent;
import com.bcits.bfm.model.PhotoGallery;
import com.bcits.bfm.service.PhotoEventService;
import com.bcits.bfm.service.PhotoGalleryService;
import com.bcits.bfm.util.JsonResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.novell.ldap.LDAPException;

@Controller
public class PhotoEventController {
	static Logger lgger= LoggerFactory.getLogger(PhotoEvent.class);

	@Autowired
	private PhotoEventService photoEventService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private PhotoGalleryService photoGalleryService;
	
	@SuppressWarnings("serial")	
	@RequestMapping(value = "/photo/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readPhotoEvent() {
		List<Map<String, Object>> faqlist = new ArrayList<Map<String, Object>>();
		for (final PhotoEvent record : photoEventService.findAllPhotoevent()) {
			faqlist.add(new HashMap<String, Object>() {
				{
					put("peId", record.getPeId());
					put("eventName", record.getEventName());
					put("eventDesc", record.getEventDesc());
				}
			});
		}
		return faqlist;
	}
	
	@RequestMapping(value = "/photo/create", method = RequestMethod.POST)
	public @ResponseBody Object createPhoto(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute PhotoEvent photoEvent,
			BindingResult bindingResult) {
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("userId");
		JsonResponse errorResponse = new JsonResponse();
		photoEvent.setEventName((String)model.get("eventName"));
		photoEvent.setEventDesc((String)model.get("eventDesc"));
		
		validator.validate(photoEvent, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			photoEventService.save(photoEvent);
			return readPhotoEvent();
		}
	}
	

	@RequestMapping(value = "/photo/upload", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void uploadPhoto(@RequestParam MultipartFile files,HttpServletRequest request) throws Exception {

		System.out.println("coming to photo upload");
		HttpSession session = request.getSession(true);
		int peId = Integer.parseInt(request.getParameter("peId"));
		System.out.println("peID+++++++++++++++++++"+peId);
		//MultipartFile filess=files.get(0);
		List<PhotoGallery> documentList=photoGalleryService.executeSimpleQuery("SELECT p FROM PhotoGallery p WHERE p.peId="+peId);
		System.out.println("===documentList=="+documentList.size());
		Blob blob=null;
    	 PhotoGallery photoGallery= new PhotoGallery();
		try
		{
		blob = Hibernate.createBlob(files.getInputStream());
		System.out.println("==========blob"+blob);
		photoGallery.setImage(blob);
		photoGallery.setPeId(peId);
		photoGalleryService.save(photoGallery);
		//faqService.updateDocument(faqId,blob);
		session.setAttribute("XLERRORData", "Uploaded Succesfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			session.setAttribute("XLERRORData", "Some error occured");
		}
		 
	}
	
	@RequestMapping("/photo/download/{peId}")
	public String downloadDocument(@PathVariable Integer peId,HttpServletResponse response) throws Exception {

		 List<InputStream> list = new ArrayList<InputStream>();
		List<PhotoGallery> photoGalleryList= photoGalleryService.executeSimpleQuery("select o from PhotoGallery o where o.peId="+peId);
		if(!photoGalleryList.isEmpty()){
			//PhotoGallery photoGallery= photoGalleryList.get(0);
			for(PhotoGallery photo:photoGalleryList)
			{
				if (photo.getImage() != null) {
					try {
						/*Blob blob=photo.getImage();
						 list.add(blob.getBinaryStream());
						 OutputStream out = response.getOutputStream();
						 response.setContentType("image");
						    out = doMerge(list, out);*/
						response.setHeader("Content-Disposition","inline;filename=\"");
						
						OutputStream out = response.getOutputStream();
							//response.setContentType(document.getDocumentType());
						IOUtils.copy(photo.getImage().getBinaryStream(), out);
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
					return null;
					//return "backend/filenotfound";
				}
			}
		
		}
		else{
			
			OutputStream out = response.getOutputStream();
			IOUtils.write("File Not Found", out);
			out.flush();
			out.close();
			return null;
			
			//return "backend/filenotfound";
		}
		return null;
	}
	  /* public  OutputStream doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
	        Document document = new Document();
	        Image image =Image.getInstance(list.
	        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
	        document.open();
	        PdfContentByte cb = writer.getDirectContent();

	        for (InputStream in : list) {
	            PdfReader reader = new PdfReader(in);
	            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	                document.newPage();
	                //import the page from source pdf
	                PdfImportedPage page = writer.getImportedPage(reader, i);
	                //add the page to the destination pdf
	                cb.addTemplate(page, 0, 0);
	            }
	        }

	        outputStream.flush();
	        document.close();
	        outputStream.close();
	        return outputStream;
	    } 
	*/
	
	@RequestMapping("/photo/getImage/{pgId}")
	public String getImage(Model userName, HttpServletRequest request,HttpServletResponse response, @PathVariable int pgId)	throws LDAPException, Exception {

		System.out.println("coming to image controller");
		response.setContentType("image/jpeg");
		Blob blobImage = photoGalleryService.getImage(pgId);
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
	@RequestMapping("/photo/photoGallery/{peId}")
	public @ResponseBody List<?> readPhotoGallery(HttpServletRequest request,HttpServletResponse response, @PathVariable int peId) 
	{
		System.out.println("coming to photo gallery for gallery ");
		//List<PhotoGallery> photoGallery=photoGalleryService.getAllPhotoByeventId(peId);
		List<Map<String, Object>> faqlist = new ArrayList<Map<String, Object>>();
		for (final PhotoGallery record :photoGalleryService.getAllPhotoByeventId(peId)) {
			faqlist.add(new HashMap<String, Object>() {
				{
					put("pgId", record.getPgId());
					//put("image", record.getImage());
					/*put("eventName", record.getEventName());
					put("eventDesc", record.getEventDesc());*/
				}
			});
		}
		return faqlist;
	}
	@RequestMapping(value = {"/photo/deleteImage"})
	public @ResponseBody Object deleteImage(@RequestBody Map<String, Object> map) 
	{ 
		System.out.println("In Pets DELETE()-----------------------"+map.get("pgId"));
		PhotoGallery gallery= new PhotoGallery();
		photoGalleryService.delete((Integer)map.get("pgId"));
		return gallery;
	}
	@RequestMapping(value="/photo/gallerydelete")
	public @ResponseBody Object deleteEvent(@RequestBody Map<String,Object> map)
	{
		System.out.println("id is coming to "+map.get("peId"));
		PhotoEvent event = new PhotoEvent();
		photoEventService.delete((Integer)map.get("peId"));
		return event;
	}

	@RequestMapping(value="/photo/update")
	public @ResponseBody Object updatePhotoEvent(@RequestBody Map<String, Object> model,HttpServletRequest request,@ModelAttribute PhotoEvent photoEvent,BindingResult bindingResult)
	{
		int peId = Integer.parseInt(model.get("peId").toString());
		JsonResponse errorResponse = new JsonResponse();
		System.out.println("peid+++++++++++++"+peId);
		PhotoEvent event = photoEventService.find(peId);
		event.setPeId(peId);
		event.setEventName((String)model.get("eventName"));
		event.setEventDesc((String)model.get("eventDesc"));
		validator.validate(event, bindingResult);
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			return errorResponse;
		} else {
			photoEventService.update(event);
			return readPhotoEvent();
		}
		
	}
	@RequestMapping(value = "/photoImageSingle/update", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void updateSinglePhoto(@RequestParam MultipartFile files1,HttpServletRequest request) throws Exception {

		System.out.println("coming to photo upload");
		HttpSession session = request.getSession(true);
		/*int pgId = Integer.parseInt(model.get("pgId").toString());
		System.out.println("pgID+++++++++++++++++++"+pgId);*/
		int pgId = Integer.parseInt(request.getParameter("pgId"));
		System.out.println("pgID+++++++++++++++++++"+pgId);
		//MultipartFile filess=files.get(0);
		/*List<PhotoGallery> documentList=photoGalleryService.executeSimpleQuery("SELECT p FROM PhotoGallery p WHERE p.peId="+peId);*/
		//PhotoGallery gallery= photoGalleryService.find(pgId);
		//System.out.println("===documentList=="+documentList.size());
		Blob blob=null;
    	// PhotoGallery photoGallery= new PhotoGallery();
		try
		{
		blob = Hibernate.createBlob(files1.getInputStream());
		System.out.println("==========blob"+blob);
		//gallery.setPgId(pgId);
		//gallery.setImage(blob);
		//gallery.setPeId(gallery.getPeId());
		photoGalleryService.updateImage(pgId,blob);
		//photoGalleryService.update(gallery);
		readPhotoEvent();
		//faqService.updateDocument(faqId,blob);`
		session.setAttribute("XLERRORData", "Updated Succesfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			session.setAttribute("XLERRORData", "Some error occured");
		}
		 
	}
}
