import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;    
import java.util.Set;

import javax.mail.*;    
import javax.mail.internet.*;    
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bfm.model.Address;
import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.model.City;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Country;
import com.bcits.bfm.model.DrGroupId;
import com.bcits.bfm.model.Owner;
import com.bcits.bfm.model.OwnerProperty;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.Property;
import com.bcits.bfm.model.State;
import com.bcits.bfm.model.Tenant;
import com.bcits.bfm.model.TenantProperty;
import com.bcits.bfm.util.ConvertDate;
import com.bcits.bfm.util.JsonResponse;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
class Mailer{  
	public static void send(final String from,final String password,String to,String sub,String msg) throws java.text.ParseException
	{
		HttpURLConnection conn 		= 	null;
		String response 			= 	"error: " + "GET" + " failed ";
	    //String url 					=   "http://192.168.2.156:1992/bsmartjvvnl/Jvvnl/BillFecthApi?kno=210911025077";//Pending-Bills
	    //String url 					=   "http://192.168.2.156:1992/bsmartjvvnl/Jvvnl/BillFecthApi?kno=210911003943";//No-Dues-Pending
	    String url 					=   "http://192.168.2.156:1992/bsmartjvvnl/Jvvnl/BillFecthApi?kno=210911012345";//Due-Date-Expired
	    //String url 					=   "http://192.168.2.156:1992/bsmartjvvnl/Jvvnl/BillFecthApi?kno=210911011111";//Invalid-KNO
	    String type 				=   "application/text";
	    String method 				=   "POST";
	    
		try
		{
			URL u = new URL(url);
	        conn = (HttpURLConnection)u.openConnection();
			conn.setRequestProperty("Content-Type", type);
			conn.setRequestProperty("Accept", type);
			conn.setRequestMethod(method);
			String line = "";
			StringBuffer sb = new StringBuffer();
			BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()) );
			while((line = input.readLine()) != null)
			    sb.append(line);
			input.close();
			conn.disconnect();
			response=sb.toString();
			String responseFromServer=response;
			
			System.err.println("ResponseFromServer="+responseFromServer);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		
/*		String duedate = "08-02-2018";
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		System.err.println(sdf.parse(sdf.format(currentDate)));
		System.err.println(sdf.parse(duedate));
		
		if(sdf.parse(sdf.format(currentDate)).compareTo(sdf.parse(duedate))>=1){ 
				System.out.println("due date expired");
		}else{
			System.out.println("due date not expired");
		}
		
		*/
		
		
		
		//System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<XML>\n<STATUS>Y</STATUS>\n<Knumber>12345678</Knumber>\n<CUSTOMERNAME>AnilKumar</CUSTOMERNAME>\n<INVOICEID>110011518</INVOICEID>\n<BILLDATE>01-Jun-15</BILLDATE>\n<DUEDATE>30-Jun-15</DUEDATE>\n<AMOUNTPAYABLE>481.00</AMOUNTPAYABLE>\n<MSG>Pending Bill</MSG>\n</XML>");
		/*  
		//Get properties object    
		Properties props = new Properties(); 
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		//get Session   
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from,password);
			}
		});  
		//compose message    
		try {    
			MimeMessage message = new MimeMessage(session);    
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
			message.setSubject(sub);    
			message.setText(msg);    
			//send message  
			Transport.send(message);    
			System.out.println("==================>message sent successfully");    
		} catch (MessagingException e) {throw new RuntimeException(e);}    

	*/}  
}  
public class TestClass{    
 public static void main(String[] args) throws java.text.ParseException {    
     //from,password,to,subject,message  
     Mailer.send("havewordswithpraveen@gmail.com","praveen@11","praveen.kumar@bcits.in","hello message","How r u?");  
     //change from, password and to  
 }    
}  




/*
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
public class TestClass 
{
	    public static String sourcePath;
	    public static String destinationPath;

	    public static void main(String[] args) throws JRException, ParseException 
	    {		//Convert Jasper 
	    	    String sdoCode="2";
	    		int sdoLength=sdoCode.length();
	    		System.out.println("sdoLength "+sdoLength);
	        	sourcePath = "C:\\Users\\user\\Desktop\\GSTIN\\CAMBILL.jasper";
	        	destinationPath = "C:\\Users\\user\\Desktop\\GSTIN\\CAMBILL.jrxml"; 

	        	JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(sourcePath);
	        	JRXmlWriter.writeReport(report, destinationPath, "UTF-8");	
	    }
}			*/
