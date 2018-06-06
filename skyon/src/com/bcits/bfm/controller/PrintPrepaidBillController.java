package com.bcits.bfm.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.PrepaidBillDetails;
import com.bcits.bfm.model.PrepaidBillDocument;
import com.bcits.bfm.service.PrepaidBillDocService;
import com.bcits.bfm.service.PrepaidBillService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;




@Controller

public class PrintPrepaidBillController {
	
	@Autowired
	private PrepaidBillDocService prepaidBillDocService;
	
	@Autowired
	private PrepaidBillService prepaidBillService;
	
	
	
	
	 @RequestMapping(value="bill/printAllBill/prepaidBillGeneration/{presentdate}", method = {RequestMethod.POST, RequestMethod.GET })
	    public void method1(HttpServletRequest request,HttpServletResponse res, @PathVariable String presentdate) throws ParseException, SQLException, DocumentException, IOException, PrintException{
	       
		 System.out.println("----------- In Test Method ---------------");
	        
		 Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
	        
		 List<PrepaidBillDetails> billEntities = prepaidBillDocService.downloadAllBills(monthDate);
		 System.out.println("billEntities "+billEntities);
	         List<InputStream> list = new ArrayList<InputStream>();
	         System.out.println("billEntities "+billEntities);
	         System.out.println(""+list);
	         
	        for (PrepaidBillDetails prepaidBill : billEntities) {
	            Blob blob = prepaidBillDocService.getBlog(prepaidBill.getBillNo());
	            list.add(blob.getBinaryStream());
	            }
	        OutputStream out = res.getOutputStream();
			res.setContentType("application/pdf");
		    out = doMerge(list, out);
		    
		  /********************************   Print Code ************************************************/  
		    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
	        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
	        patts.add(Sides.DUPLEX);
	        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
	        if (ps.length == 0) {
	            throw new IllegalStateException("No Printer found");
	        }
	        System.out.println("Available printers: " + Arrays.asList(ps));

	        PrintService myService = null;
	        for (PrintService printService : ps) {
	            if (printService.getName().equals("Your printer name")) {
	                myService = printService;
	                break;
	            }
	        }

	        if (myService == null) {
	            throw new IllegalStateException("Printer not found");
	        }
	  /* optional 1 */   //FileInputStream fis = new FileInputStream("D:/22303286_acknowledgement.pdf");
	        
	  /* optional  2*/
	        FileInputStream fis = null;
	        IOUtils.copyLarge(fis, out);
	  /* optional  2*/
	        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
	        DocPrintJob printJob = myService.createPrintJob();
	        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
	        fis.close(); 
	        /********************************   Print Code ************************************************/ 
		    
			out.flush();
			out.close();
	        System.out.println("Merge success");
	    }
	 
	 
	 
	 
	/* -------------------------------------------------------*/
	 
	 public  OutputStream doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
	        Document document = new Document();
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
	 
	 
	 
	 
	 
	 
	 
	/* --------------------------------------------------------------------------------------*/
	 

	 @RequestMapping(value="bill/propertyNoWise/prepaidBillGeneration/{propertyNo}/{presentdate}", method = {
				RequestMethod.POST, RequestMethod.GET })
		    public void printAccontWiseBill(HttpServletRequest request,HttpServletResponse res,@PathVariable String propertyNo, @PathVariable String presentdate) throws ParseException, SQLException, DocumentException, IOException, PrintException{
		        
		 
		 
		 System.out.println("----------- In Test Method ---------------");
		      
		        System.out.println("presentdate "+presentdate);
		        System.out.println("propertyNo "+propertyNo);
		        Date monthDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(presentdate);
		       // Date fromDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(fromMonth);
	
		        
		        int propertyId=Integer.parseInt(propertyNo);
		        
		        
		        List<PrepaidBillDetails> prepaidEntities = prepaidBillDocService.downloadAllBillsOnPropertyNo(monthDate,propertyId);
		        System.out.println("prepaidEntities "+prepaidEntities);
		        List<InputStream> list = new ArrayList<InputStream>();
		        System.out.println("list "+list);
		       
		        for (PrepaidBillDetails prepaidBillDetails : prepaidEntities) {
		        	
		        	System.out.println("PrepaidBillDetails.getBillNo()::::::::::"+prepaidBillDetails.getBillNo());
		            Blob blob = prepaidBillDocService.getBlog(prepaidBillDetails.getBillNo());
		            list.add(blob.getBinaryStream());
		            }
		        OutputStream out = res.getOutputStream();
				res.setContentType("application/pdf");
			    out = doMerge(list, out);
			    
			  /********************************   Print Code ************************************************/  
			    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
		        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
		        patts.add(Sides.DUPLEX);
		        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
		        if (ps.length == 0) {
		            throw new IllegalStateException("No Printer found");
		        }
		        System.out.println("Available printers: " + Arrays.asList(ps));

		        PrintService myService = null;
		        for (PrintService printService : ps) {
		            if (printService.getName().equals("Your printer name")) {
		                myService = printService;
		                break;
		            }
		        }

		        if (myService == null) {
		            throw new IllegalStateException("Printer not found");
		        }
		  /* optional 1 */   //FileInputStream fis = new FileInputStream("D:/22303286_acknowledgement.pdf");
		        
		  /* optional  2*/
		        FileInputStream fis = null;
		        IOUtils.copyLarge(fis, out);
		  /* optional  2*/
		        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		        DocPrintJob printJob = myService.createPrintJob();
		        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
		        fis.close(); 
		        /********************************   Print Code ************************************************/ 
			    
				out.flush();
				out.close();
		        System.out.println("Merge success");
		    }
	 
	 
	 
	 
	 
	/* -------------------------------------------------------------------------------------------------------*/
	 
@RequestMapping(value="/prePaidBill/getPropertyNamesUrl", method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody List<?> readPropNums(){
		 
		 List<Map<String, Object>> resultList=new ArrayList<>();
		 Map<String, Object> mapList=null;
		 List<?> adjustList=prepaidBillService.ReadPropertys();
		 for(Iterator<?> iterator=adjustList.iterator();iterator.hasNext();){
			 final Object[] value=(Object[]) iterator.next();
			 mapList=new HashMap<>();
			 mapList.put("propertyId", value[0]);
			 mapList.put("property_No", value[1]);
			 resultList.add(mapList);
		 }
		 
		 return resultList;
	 }
	 
	 

}
