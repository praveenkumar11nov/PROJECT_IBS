package com.bcits.bfm.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
 
public class TestingClass{
	public static void main(String[] args) throws ParseException, MessagingException, IOException{
		/*		
		String CC1="himanibedi@gmail.com,in.pagarwal@gmail.com,anshulasawa@gmail.com,rajeevmaha@gmail.com,sanjai61@yahoo.com,tokshitij@gmail.com,nandasn1956@yahoo.com,ssingh9@hotmail.com,sandeep.kumarr@outlook.com,deepak@pioneerpublicityindia.com,ramen_sen@yahoo.com,sethiharpreet@gmail.com,bhawnavkumar@gmail.com,vipul@chibs.net,Jagjivensingh@gmail.com,barindersahni@hotmail.com,alokpradhan@hotmail.com,kanikaa.taneja@gmail.com,msachdeva17@gmail.com,angadsuri@hotmail.com,ajey_511@yahoo.co.in,suranjitas@yahoo.com,onsindia@gmail.com,gautam.vohra@gmail.com,hssandy007@yahoo.co.in,monishergill@gmail.com,anushas@hotmail.com,gaganjotsingh@yahoo.co.in,ashimamalik@gmail.com,meerasachdeva@hotmail.com,gaganarora22@gmail.com,raviraj_rastogi@yahoo.co.in,raj.kochar@petrofac.com,technocom,1@rediffmail.com,angleenm@gmail.com,simrinmalik@gmail.com,arushi.ag@gmail.com,kmhanda@gmail.com,dummy11@gmail.com,malhotra.sanjeev@yahoo.com,nagpal.v@gmail.com,radiusadvllc@yahoo.com,guptaus@gmail.com,drlatikaagarwal@gmail.com,ankitsiki@gmail.com,amit@amitarora.co.in,sunnygrover@hotmail.com,saritamiglani@gmail.com,anand.palkhiwala@ericsson.com,sbhatia100@yahoo.com,karan_bagla@yahoo.co.in,dipti.chachra@gmail.com,vinamrta@googlemail.com,lekhimanjusha@yahoo.co.in,Vikram.chopra@altiussports.com,mandyjohar@gmail.com,sachinb612@gmail.com,vibirla@gmail.com,sonia_thakur123@yahoo.co.in,kanika.d84@gmail.com,niti.atroley@gmail.com,sandhya.babu@gmail.com,ankur.arora2007@yahoo.com,siddharth@triveniapparels.com, anjuchandiok1@gmail.com,sujeetsairam@gmail.com,amrata.baghel@gmail.com,viknarendra@yahoo.com,vishallh@gmail.com,harrysingham@outlook.com,dklakhanpal@gmail.com,viveck@viveckgoel.com,shikha2312@rediffmail.com,rishavkapur@gmail.com,alispace7@gmail.com,deepakkprem@gmail.com,aarthiram17@gmail.com,Vani.rao@citi.com,kaushik.mitra@pepsico.com,ramnik.bajaj@gmail.com,rishav_gautam@hotmail.com,yogesh.malik@telenor.com,shammi.2.kapoor@nokia.com,iacorpn@airtelmail.in,captranbir@yahoo.co.in,jeetukwatra@hotmail.com,suchitdiva@gmail.com,arjan90@gmail.com,riteshsrivastava@gmail.com,kuldipbajaj11@gmail.com,pramod48@gmail.com,rbhatnagar03@gmail.com,sachin_tagra@yahoo.com,sumitmtg@yahoo.com,rahulmalik@ikminvestor.com,amgandotra@yahoo.com,lalitnagwani@yahoo.com,opk15@hotmail.com,roublerana@yahoo.co.uk,sandeepsingharora@gmail.com,prathmeshmishra@gmail.com,balaji4402@gmail.com,rohsinha@gmail.com,vikasma@hotmail.com,sunayana_saha@yahoo.com,vineetagarwal@hotmail.com,aman.lal@rediffmail.com,sbki31@HOTMAIL.com,surathsingh@gmail.com,reetugandhi@gmail.com,nakul_sehgal@yahoo.com,captvirkss@gmail.com,manoj@stellarinfo.com,pardmanbajaj@gmail.com,intravels@hotmail.com,pv7865@att.com,seemahanda30@gmail.com,hqasmi@gmail.com,skmehta181@gmail.com,rakeshnangia@yahoo.com,vivek.pandey@fluor.com,munishbhargava42@hotmail.com,ankushb@gmail.com,rajiv.sharma.sg@gmail.com,anoop_kapoor@yahoo.com,dsckash@yahoo.com,ca_rohitsaini@yahoo.com,arvind.dutt@gmail.com,nehasood27@gmail.com,anurag.sharma1680@gmail.com,sanjitb18@gmail.com,nitinmehra@gmail.com,shreya84@rediffmail.com,suman.navin@gmail.com,agarwalanshuman@yahoo.com,ritika19@gmail.com,bobbyjuneja@hotmail.com,amitahuja75@gmail.com,rakesh.kaul@pvrcinemas.com,cma.adhikari@gmail.com,link2nitesh@gmail.com,singhhemant774@gmail.com,rastogideepak@rediffmail.com,jiten.globaldesigners@gmail.com, payal.gauriar@gmail.com,kishorechhabra69@gmail.com,rahul1006@yahoo.com,richa.khanna83@gmail.com,kanchanbkumar@gmail.com,hat_d1@yahoo.co.in,poonam60@hotmail.com,jay007@gmail.com,praveen.kumar@bcits.in";
		String CC2="Alokendu.choudhury@gmail.com,vineet.narang@accenture.com,sandeepchopra19@gmail.com,rajeshkhanna1959@gmail.com,ajbali.bali@gmail.com,garuna_p@yahoo.com,atulgupt2003@yahoo.com,rajivbraja@hotmail.com,jaggilisten@gmail.com,usha.minhas@gmail.com,jkjhamb@hotmail.com,sharma.filtrationsystems@gmail.com,acariteshjain@gmail.com,cknanda11@rediffmail.com,monica07lalani@gmail.com,akanshaagg@gmail.com,pankaj_lamba@rediffmail.com,ndgdelhi@gmail.com,deepak.iiml14@gmail.com,neerajkumar72@yahoo.com,annubaral@hotmail.com,anilmalhotrag@gmail.com,mannu.bhatia@pepsico.com,maverick.dheeraj@gmail.com,abhinav.8602@gmail.com,kanchan.chandra@gmail.com,sudheer_relan@potindia.com,nidhi.mehra9@yahoo.com,bullysangha@hotmail.com,shavetakohlidas@gmail.com,balanand2@ymail.com,sanjm.hundal@gmail.com,m.talwar@arrowspeedline.inmunish@ishman.biz,supsansar@yahoo.com,bharat.yadav@infinite.com,rajanoswal@oswalcastings.co.in,sandeepkum24@gmail.com,amarjeet.mehta@gmail.com,saurabhkhandelwal@hotmail.com,grishma.sharma83@gmail.com,manik1234@hotmail.com,vijkbansal@hotmail.com,ramansinghsaluja@gmail.com,eluru4@gmail.com,vipul.mehrotra@gmail.com,geet@neotechindia.com,rohitsingh1512@gmail.com,skarakoti@outlook.com,paragbhasin@gmail.com,saurabh_grover_in@yahoo.com,naveen@erisgroup.in hemali.bhogal@gmail.com,lmittal13@gmail.com,supriya@rapidsoft.co.in,meghna2911@yahoo.com,parulpravisha@gmail.com,tarunadwani@gmail.com,Sanseeth@gmail.com,Rahul.r.rana@payback.net,pun1983@yahoo.com,vineet.pandita@gmail.com,sandeeppuri@hotmail.com,dkgsarla@yahoo.com,sujatagupta2006@hotmail.com,nitinangurala@gmail.com,ceo@vigneshwar.com,rkhanna14@hotmail.com,khanna_rakesh_in@hotmail.com,emailbky@gmail.com,rkmakar@hotmail.com,tarundeep@hotmail.com,bpaulsingh@hotmail.com,harpreetkbhatia28@gmail.com,anilanand123123@rediffmail.com,gijuneja@gmail.com,vikas.gemini@hotmail.com,manjulgrover@rediffmail.com,vishal.orion@gmail.com,Chander.Shekhar@walmart.com,mkh788@hotmail.com,anand.sangeeta@gmail.com,ravi_khattar@hotmail.com,sanjay.basu@aol.in,deepakstock@gmail.com,niharikamittra@yahoo.com,anish_a_singh@yahoo.co.in,karan1203@gmail.com,raveeshbagla@gmail.com,kapila_naveen@yahoo.co.in,arijit.gosh@honeywell.com,raotravelgurgaon@yahoo.co.in,manocha_raman@yahoo.co.in,yogesh_chuchra@yahoo.com,binitmishra@gmail.com,manand54@yahoo.com,tanujarora.tanz@gmail.com,atma@krishna.net,drgirish@yahoo.com,joedhillon@gmail.com,sidd.mag@gmail.com,rajiv.walia81@gmail.com,gianp.bhatia@gmail.com,srohatgi1964@gmail.com,anjuchandiok11@gmail.com,rkapur85@hotmail.com,dr.rashmich@gmail.com,dummy103@gmail.com,anktjn82@rediffmail.com,jawa953@hotmail.com,manishapiyushgupta@gmail.com,amit.m.agrawal@hotmail.com,garg.deepak@gmail.com,goldenarch2013@gmail.com,ravidhir1@gmail.com,anandverma1@hotmail.com,khosla.sunil@gmail.com,anil.satija@gmail.com,uajaipur1@gmail.com,consulting.grandview@gmail.com,kumarvarun.uv@gmail.com,namrata_suri@hotmail.com,smagoo71@gmail.com,manish.bharati113@gmail.com,hujaradha72@gmail.com,puneetsuri@yahoo.com,pawwy_kmc@yahoo.com,jyotikanayyar24@yahoo.com,verma.ritika@gmail.com,varun.trc@gmail.com,sunil01deepa@yahoo.co.in,sumanand_malhotra@yahoo.co.in,pasrichak@gmail.com,rahulsuri100@yahoo.com,chakravartykrishanu@rediffmail.com,vids.sridhar@gmail.com,saahilvp@hotmail.com,rkparimoo@gmail.com,satiitk63@yahoo.com,ajayaggarwal142@gmail.com,parnavsahu@hotmail.com,jaggi.sushil@gmail.com,dranil.thussu@gmail.com,krc1512@gmail.com,sunil@swatiapparels.com,vishalwadhwani1@gmail.com,jenniferprem52@gmail.com,tk_goyal@hotmail.com,phastu@hotmail.com,hiravish@hotmail.com,David.Jones@in.gt.com,bstvidya@gmail.com,pratapsanjay@rediffmail.com,captmkapoor737@yahoo.com,Vineet1.aggarwal@in.ey.com,sukrit@dewan.in,vikram@parasartstudioevents.in,facility.skyon@nimbusharbor.com,abhi14rastogi@gmail.com,dmarch23_dma@yahoo.co.in,aalokbhan40@yahoo.co.in,neerajbassi@gmail.com,tanny1111@aol.com,24.raman.k@gmail.com,polyplast_pp@rediffmail.com,manishamishra@yahoo.com,Mayankmehta85@gmail.com,dinaybhatia@hotmail.com,ankit.paruthi@gmail.com,prantadutta@yahoo.co.in,rohit008_2000@yahoo.com,sanjeevb@alabbargroup.com,imohitgupta@yahoo.co.in,nitin_sinha@ymail.com,shobhnabajaj@hotmail.com,amitabhlaldas@hotmail.com,harsh@eim.aescmiint@gmail.com,gtripathi@incainfotech.com,goyal.kirti@gmail.com,praveen.kumar@bcits.in";
		String[] AllMails = CC2.split(",");
		for(int i=0;i<AllMails.length;i++){
			System.out.println((i+1)+". "+AllMails[i]);
		}	
		
		InputStream ExcelFileToRead = new FileInputStream("C:/Users/User/Desktop/vvcontacts.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();
		int count=0;
		while (rows.hasNext()){
			count++;
			row=(XSSFRow) rows.next();
			String TO=row.getCell(0).getStringCellValue()+",";
			System.out.print(TO);
		}
		
		 */	
		
		
/*			
		//InputStream ExcelFileToRead = new FileInputStream("C:/Users/User/Desktop/contactList2.xlsx");
		InputStream ExcelFileToRead = new FileInputStream("C:/Users/User/Desktop/vvcontacts.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();
		int count=0;
		while (rows.hasNext()){
			count++;
			row=(XSSFRow) rows.next();
			String TO=row.getCell(0).getStringCellValue();
			System.out.println(count +"  "  + "TO : "+TO);
*/

		//if(count==210)break;

		//String TO="praveen.kumar@bcits.in";
		//String TO="nitya.mohan@ireo.in";
		String TO="customercare@victoryvalley-rwa.in";
		
		//String CC="praveen.kumar@bcits.in,rohitchat2016@gmail.com";
		
		//SkyonContacts
		//String BCC="himanibedi@gmail.com,in.pagarwal@gmail.com,anshulasawa@gmail.com,rajeevmaha@gmail.com,sanjai61@yahoo.com,tokshitij@gmail.com,nandasn1956@yahoo.com,ssingh9@hotmail.com,sandeep.kumarr@outlook.com,deepak@pioneerpublicityindia.com,ramen_sen@yahoo.com,sethiharpreet@gmail.com,bhawnavkumar@gmail.com,vipul@chibs.net,Jagjivensingh@gmail.com,barindersahni@hotmail.com,alokpradhan@hotmail.com,kanikaa.taneja@gmail.com,msachdeva17@gmail.com,angadsuri@hotmail.com,ajey_511@yahoo.co.in,suranjitas@yahoo.com,onsindia@gmail.com,gautam.vohra@gmail.com,hssandy007@yahoo.co.in,monishergill@gmail.com,anushas@hotmail.com,gaganjotsingh@yahoo.co.in,ashimamalik@gmail.com,meerasachdeva@hotmail.com,gaganarora22@gmail.com,raviraj_rastogi@yahoo.co.in,raj.kochar@petrofac.com,technocom,1@rediffmail.com,angleenm@gmail.com,simrinmalik@gmail.com,arushi.ag@gmail.com,kmhanda@gmail.com,dummy11@gmail.com,malhotra.sanjeev@yahoo.com,nagpal.v@gmail.com,radiusadvllc@yahoo.com,guptaus@gmail.com,drlatikaagarwal@gmail.com,ankitsiki@gmail.com,amit@amitarora.co.in,sunnygrover@hotmail.com,saritamiglani@gmail.com,anand.palkhiwala@ericsson.com,sbhatia100@yahoo.com,karan_bagla@yahoo.co.in,dipti.chachra@gmail.com,vinamrta@googlemail.com,lekhimanjusha@yahoo.co.in,Vikram.chopra@altiussports.com,mandyjohar@gmail.com,sachinb612@gmail.com,vibirla@gmail.com,sonia_thakur123@yahoo.co.in,kanika.d84@gmail.com,niti.atroley@gmail.com,sandhya.babu@gmail.com,ankur.arora2007@yahoo.com,siddharth@triveniapparels.com, anjuchandiok1@gmail.com,sujeetsairam@gmail.com,amrata.baghel@gmail.com,viknarendra@yahoo.com,vishallh@gmail.com,harrysingham@outlook.com,dklakhanpal@gmail.com,viveck@viveckgoel.com,shikha2312@rediffmail.com,rishavkapur@gmail.com,alispace7@gmail.com,deepakkprem@gmail.com,aarthiram17@gmail.com,Vani.rao@citi.com,kaushik.mitra@pepsico.com,ramnik.bajaj@gmail.com,rishav_gautam@hotmail.com,yogesh.malik@telenor.com,shammi.2.kapoor@nokia.com,iacorpn@airtelmail.in,captranbir@yahoo.co.in,jeetukwatra@hotmail.com,suchitdiva@gmail.com,arjan90@gmail.com,riteshsrivastava@gmail.com,kuldipbajaj11@gmail.com,pramod48@gmail.com,rbhatnagar03@gmail.com,sachin_tagra@yahoo.com,sumitmtg@yahoo.com,rahulmalik@ikminvestor.com,amgandotra@yahoo.com,lalitnagwani@yahoo.com,opk15@hotmail.com,roublerana@yahoo.co.uk,sandeepsingharora@gmail.com,prathmeshmishra@gmail.com,balaji4402@gmail.com,rohsinha@gmail.com,vikasma@hotmail.com,sunayana_saha@yahoo.com,vineetagarwal@hotmail.com,aman.lal@rediffmail.com,sbki31@HOTMAIL.com,surathsingh@gmail.com,reetugandhi@gmail.com,nakul_sehgal@yahoo.com,captvirkss@gmail.com,manoj@stellarinfo.com,pardmanbajaj@gmail.com,intravels@hotmail.com,pv7865@att.com,seemahanda30@gmail.com,hqasmi@gmail.com,skmehta181@gmail.com,rakeshnangia@yahoo.com,vivek.pandey@fluor.com,munishbhargava42@hotmail.com,ankushb@gmail.com,rajiv.sharma.sg@gmail.com,anoop_kapoor@yahoo.com,dsckash@yahoo.com,ca_rohitsaini@yahoo.com,arvind.dutt@gmail.com,nehasood27@gmail.com,anurag.sharma1680@gmail.com,sanjitb18@gmail.com,nitinmehra@gmail.com,shreya84@rediffmail.com,suman.navin@gmail.com,agarwalanshuman@yahoo.com,ritika19@gmail.com,bobbyjuneja@hotmail.com,amitahuja75@gmail.com,rakesh.kaul@pvrcinemas.com,cma.adhikari@gmail.com,link2nitesh@gmail.com,singhhemant774@gmail.com,rastogideepak@rediffmail.com,jiten.globaldesigners@gmail.com,payal.gauriar@gmail.com,kishorechhabra69@gmail.com,rahul1006@yahoo.com,richa.khanna83@gmail.com,kanchanbkumar@gmail.com,hat_d1@yahoo.co.in,poonam60@hotmail.com,jay007@gmail.com,praveen.kumar@bcits.in";
		//String BCC="Alokendu.choudhury@gmail.com,vineet.narang@accenture.com,sandeepchopra19@gmail.com,rajeshkhanna1959@gmail.com,ajbali.bali@gmail.com,garuna_p@yahoo.com,atulgupt2003@yahoo.com,rajivbraja@hotmail.com,jaggilisten@gmail.com,usha.minhas@gmail.com,jkjhamb@hotmail.com,sharma.filtrationsystems@gmail.com,acariteshjain@gmail.com,cknanda11@rediffmail.com,monica07lalani@gmail.com,akanshaagg@gmail.com,pankaj_lamba@rediffmail.com,ndgdelhi@gmail.com,deepak.iiml14@gmail.com,neerajkumar72@yahoo.com,annubaral@hotmail.com,anilmalhotrag@gmail.com,mannu.bhatia@pepsico.com,maverick.dheeraj@gmail.com,abhinav.8602@gmail.com,kanchan.chandra@gmail.com,sudheer_relan@potindia.com,nidhi.mehra9@yahoo.com,bullysangha@hotmail.com,shavetakohlidas@gmail.com,balanand2@ymail.com,sanjm.hundal@gmail.com,m.talwar@arrowspeedline.in,munish@ishman.bizsupsansar@yahoo.com,bharat.yadav@infinite.com,rajanoswal@oswalcastings.co.insandeepkum24@gmail.com,amarjeet.mehta@gmail.com,saurabhkhandelwal@hotmail.com,grishma.sharma83@gmail.com,manik1234@hotmail.com,vijkbansal@hotmail.com,ramansinghsaluja@gmail.com,eluru4@gmail.com,vipul.mehrotra@gmail.com,geet@neotechindia.com,rohitsingh1512@gmail.com,skarakoti@outlook.com,paragbhasin@gmail.com,saurabh_grover_in@yahoo.com,naveen@erisgroup.in,hemali.bhogal@gmail.com,lmittal13@gmail.com,supriya@rapidsoft.co.in,meghna2911@yahoo.com,parulpravisha@gmail.com,tarunadwani@gmail.com,Sanseeth@gmail.com,Rahul.r.rana@payback.net,pun1983@yahoo.com,vineet.pandita@gmail.com,sandeeppuri@hotmail.com,dkgsarla@yahoo.com,sujatagupta2006@hotmail.com,nitinangurala@gmail.com,ceo@vigneshwar.com,rkhanna14@hotmail.com,khanna_rakesh_in@hotmail.com,emailbky@gmail.com,rkmakar@hotmail.com,tarundeep@hotmail.com,bpaulsingh@hotmail.com,harpreetkbhatia28@gmail.com,anilanand123123@rediffmail.com,gijuneja@gmail.com,vikas.gemini@hotmail.com,manjulgrover@rediffmail.com,vishal.orion@gmail.com,Chander.Shekhar@walmart.com,mkh788@hotmail.com,anand.sangeeta@gmail.com,ravi_khattar@hotmail.com,sanjay.basu@aol.in,deepakstock@gmail.com,niharikamittra@yahoo.com,anish_a_singh@yahoo.co.in,karan1203@gmail.com,raveeshbagla@gmail.com,kapila_naveen@yahoo.co.in,arijit.gosh@honeywell.com,raotravelgurgaon@yahoo.co.in,manocha_raman@yahoo.co.in,yogesh_chuchra@yahoo.com,binitmishra@gmail.com,manand54@yahoo.com,tanujarora.tanz@gmail.com,atma@krishna.net,drgirish@yahoo.com,joedhillon@gmail.com,sidd.mag@gmail.com,rajiv.walia81@gmail.com,gianp.bhatia@gmail.com,srohatgi1964@gmail.com,anjuchandiok11@gmail.com,rkapur85@hotmail.com,dr.rashmich@gmail.com,dummy103@gmail.com,anktjn82@rediffmail.com,jawa953@hotmail.com,   manishapiyushgupta@gmail.com,amit.m.agrawal@hotmail.com,garg.deepak@gmail.com,goldenarch2013@gmail.com,ravidhir1@gmail.com,anandverma1@hotmail.com,khosla.sunil@gmail.com,anil.satija@gmail.com,uajaipur1@gmail.com,consulting.grandview@gmail.com,kumarvarun.uv@gmail.com,namrata_suri@hotmail.com,smagoo71@gmail.com,manish.bharati113@gmail.com,hujaradha72@gmail.com,puneetsuri@yahoo.com,pawwy_kmc@yahoo.com,jyotikanayyar24@yahoo.com,verma.ritika@gmail.com,varun.trc@gmail.com,sunil01deepa@yahoo.co.in,sumanand_malhotra@yahoo.co.in,pasrichak@gmail.com,rahulsuri100@yahoo.com,chakravartykrishanu@rediffmail.com,vids.sridhar@gmail.com,saahilvp@hotmail.com,rkparimoo@gmail.com,satiitk63@yahoo.com,ajayaggarwal142@gmail.com,parnavsahu@hotmail.com,jaggi.sushil@gmail.com,dranil.thussu@gmail.com,krc1512@gmail.com,sunil@swatiapparels.com,vishalwadhwani1@gmail.com,jenniferprem52@gmail.com,tk_goyal@hotmail.com,phastu@hotmail.com,hiravish@hotmail.com,David.Jones@in.gt.com,bstvidya@gmail.com,pratapsanjay@rediffmail.com,captmkapoor737@yahoo.com,Vineet1.aggarwal@in.ey.com,sukrit@dewan.in,vikram@parasartstudioevents.in,facility.skyon@nimbusharbor.com,abhi14rastogi@gmail.com,dmarch23_dma@yahoo.co.in,aalokbhan40@yahoo.co.in,neerajbassi@gmail.com,tanny1111@aol.com,24.raman.k@gmail.com,polyplast_pp@rediffmail.com,manishamishra@yahoo.com,Mayankmehta85@gmail.com,dinaybhatia@hotmail.com,ankit.paruthi@gmail.com,prantadutta@yahoo.co.in,rohit008_2000@yahoo.com,sanjeevb@alabbargroup.com,imohitgupta@yahoo.co.in,nitin_sinha@ymail.com,shobhnabajaj@hotmail.com,amitabhlaldas@hotmail.com,harsh@eim.aescmiint@gmail.com,gtripathi@incainfotech.com,goyal.kirti@gmail.com,praveen.kumar@bcits.in";
		
		//VVContacts
		//String BCC="shyambaweja@yahoo.com,rita.srivastava@bakrie.co.id,vikas.sheena@GMAIL.COM,nkbhoan@yahoo.com,mohitmehra.mehra@gmail.com,vtech.consultancy@gmail.com,neelamtalwar@gmail.com,havovi_joshi@hotmail.com,vishalsama@hotmail.com,unisisentt@yahoo.com,tauqueerfazal@gmail.com,devesh0311@yahoo.co.in,kapilvishal@gmail.com,arudo24@yahoo.co.in,manav.abrol@gmail.com,vikas.anand@dhl.com,nasa61@yahoo.com,Prableens@gmail.com,rajat_gt@yahoo.com,sandeeppuri@hotmail.com,sandeepbansal14@gmail.com,captjka@gmail.com,anshoobajaj@gmail.com,rishimutreja@gmail.com,nisha@gmail.com,sushma.rani.169@gmail.com,amercanhospitality9@gmail.com,Kaushal_mttl@yahoo.co.in,ashesh.ambasta@itc.in,saddy9@hotmail.com,harlinasodhi@gmail.com,rishitrack@gmail.com,Vikrant.bhatnagar@gmail.com,Saurabh_grover_in@yahoo.com,sanjeevkrishan@rediffmail.com,namita@ggmassociates.com,gaurgoyal@gmail.com,kunalshangloo@gmail.com,singhdevendra@hotmail.com,amritajoneja2005@yahoo.com,gupta.nishtha@gmail.com,amehrotra1961@gmail.com,wadia.siddharth@gmail.com,kapil_goel@keysight.com,CREWING@GLOBUSMAINE.COM,ashmakhanna@gmail.com,arunbisiness@gmail.com,guptaharishanker@gmail.com,datinvst@gmail.com,sanjivkhanduja78@hotmail.com,anharkauli@yahoo.co.in,kunikpatel@gmail.com,drpankaj06@yahoo.co.in,rajangovil@hotmail.com,mallika.mittal19@gmail.com,jivitej@gmail.com,sree2005in@gmail.com,Captrnagar@yahoo.com,sbhanja@gmail.com,vsvendingsolutions12@gmail.com,surinder.bahl@yahoo.in,vijay.motiramani@outlook.com,prashantarora@hotmail.com,ravin2@gmail.com,satyam2411@yahoo.co.in,banlaks@yahoo.com,aroranovita@gmail.com,rcsrivastava@gmail.com,entirunelveli@gmail.com,ravibhushan0390@gmail.com,waliaj@hotmail.com,kokils@hotmail.com,rohitchat2016@gmail.com,patilgc@gmail.com,reshmi.tikmani@gmail.com,dummy111@gmail.com,kamaljeet.bajwa@gmail.com,panda.av@gmail.com,ha.hitesh@gmail.com,akstrai@gmail.com,nksagar260@gmail.com,bedishivali@gmail.com,wadhwa_pradeep@yahoo.com,puneet30183@gmail.com,pyush_here@hotmail.com,sharan_sandhu1@hotmail.com,atharshahab@gmail.com,atulmal55@gmail.com,trishant.singh@gmail.com,anjanakumari@outlook.com,bnb.zodiac@yahoo.com,abhishek.aggarwal@gurucul.com,praveen.kumar@bcits.in";
		String BCC="rohit.chaturvedi@ireo.in,wallsolutions.int@hotmail.com,a.kapur02@gmail.com,reenalowe@gmail.com,rk@vortexsystems.in,sachin.sahay@itc.in,sabbyr@gmail.com,uvais.momin@gmail.com,dummy5@gmail.com,anilgirdhar1@gmail.com,renu@easysourceindia.com,sanjeeb.kumar@avivaindia.com,kamaljitbhullar@gmail.com,raja.choudhary@gmail.com,rohankhanna@gmail.com,pjose2141@gmail.com,tanay@metalexindia.com,kuldipbajaj11@gamil.com,arvind@khanna.net.in,toshi@rediffmail.com,sanjayrattan@hotmail.com,rupesh_baranwal@yahoo.com,vishal.saha@gmail.com,gkuk12@gmail.com,akhilmohan@ima-india.com,Vaman.sabharwal@gmail.com,jsyad59@yahoo.co.in,ravinindwani@yahoo.co.in,akash.kakar@gmail.com,vijaiswal@gmail.com,Ashish.Verma@ap.cushwake.com,kapila_taneja@yahoo.co.in,rahatj@gmail.com,sidsat1975@gmail.com,abc@gmail.com,dummy@gmail.com,ashokagarwal@hotmail.com,rameshverma45@gmail.com,rameshnsl@gmail.com,rpathak202@yahoo.com,kartik.mohindra@gmail.com,geetanjali.pathania@gmail.com";
		
		System.err.println("------------------------Sending Email TO : "+TO+" -----------------------");
		String useraccount  = "customercare@victoryvalley-rwa.in"; 
		//String useraccount  = "customercare@skyon-rwa.com";
		String userpassword = "ireo@1234";
/*		
		String mailsmtphost 	 	   = "smtpout.asia.secureserver.net";
		String socketFactoryport	   = "";
		String mailsmtpport 	 	   = "80";
		String mailsmtpsocketFactory   = "javax.net.ssl.SSLSocketFactory";
		String mailsmtpauth			   = "true";
		String EmailgateWayusername    = "customercare@victoryvalley-rwa.in"; 
		String EmailgateWaypassword    = "ireo@1234";
		String EmailgateWaydisplayName = "Victory Valley RWA";
		String mailstoreprotocol 	   = "imap";
		String mailimaphost 	   	   = "imap.secureserver.net";
		String mailimapport 	  	   = "143";
*/		
		Properties props = new Properties();
		props.put("mail.smtp.host","smtpout.asia.secureserver.net");
		props.put("mail.smtp.socketFactory.port","");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.port","80");
		props.put("mail.imap.host","imap.secureserver.net");
		props.put("mail.imap.port","143");
		props.put("mail.store.protocol","imap");

		Properties props1 = new Properties();
		props1.put("mail.transport.protocol","smtp");
		props1.put("mail.smtps.host","smtpout.asia.secureserver.net");
		props1.put("mail.smtps.auth","true");

		Session mailSession = Session.getDefaultInstance(props1);
		Transport transport;

		try {
			String IREO_SKYON = "<html><head></head><body><table><tr>"
					+ "<td>Dear Skyon Residents</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>"
					+ "<tr><td>Our warmest greetings to you and your family this Holi. May this Holi  be the start of your successful and fulfilling life at Skyon!</td>"
					+ "</tr><tr><td>Join us on Friday, March 2nd 2018 from 10:00am at Skyon, as we celebrate the Festival of Holi with your loved ones and enjoy the festivities</td>"
					+ "</tr><tr><td>and spend moments of happiness with your family and friends- Come, Celebrate the festival of colors, Get drenched with different hues,</td>"
					+ "</tr><tr><td>Share the love and fun this festival of Holi at Ireo Skyon!</td>"
					+ "</tr><tr><td>&nbsp;</td>"
					+ "</tr><tr><td><img src=\"cid:image\" style='width: 100%;'></td>"
					+ "</tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Nitya Mohan</b></td>"
					+ "</tr><tr><td><b>Assistant Vice President|Facility Management|Head Resident Services</b></td>"
					+ "</tr><tr><td><b>Mobile  : (91) 9560866440; Tel: (91-124) 4795000; 4795004 (Board), 4795846/ 5847 (Direct)</td>"
					+ "</tr><tr><td><b>Address : Ireo Campus, Archview Drive, Ireo City, Golf Course Extension Road, Gurgaon – 122101, Haryana</b></td>"
					+ "</tr><tr><td><b>Website : www.ireoworld.com</b></td>"
					+ "</tr><tr><td><b></b></td>"
					+ "</tr></table></body></html>";
			
			String IREO_CITY = "<html><head></head><body><table>"
			+ "<tr><td>Dear Ireo Residents</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>"
			+ "Our warmest greetings to you and your family this Holi from Ireo Management. May this Holi  be the start of your successful and "
			+ "fulfilling life at Ireo City!  Celebrate the festival of colors, Get drenched with different hues, Share the love and fun, this "
			+ "festival of Holi at Ireo City."
			+ "</td></tr>"
			+ "<tr><td>&nbsp;</td></tr>"
			+ "<tr><td>&nbsp;</td></tr>"
			+ "<tr><td><img src=\"cid:image\" style='width: 100%;'></td></tr>"
			+ "<tr><td>&nbsp;</td></tr>"
			+ "<tr><td>&nbsp;</td></tr>"
			+ "<tr><td><b>Nitya Mohan</b></td></tr>"
			+ "<tr><td><b>Assistant Vice President|Facility Management|Head Resident Services</b></td></tr>"
			+ "<tr><td><b>Mobile  : (91) 9560866440; Tel: (91-124) 4795000; 4795004 (Board), 4795846/ 5847 (Direct)</td></tr>"
			+ "<tr><td><b>Address : Ireo Campus, Archview Drive, Ireo City, Golf Course Extension Road, Gurgaon – 122101, Haryana</b></td></tr></tr>"
			+ "<tr><td><b>Website : www.ireoworld.com</b></td></tr>"
			+ "</table></body></html>";



			transport = mailSession.getTransport();
			int imapPort=Integer.parseInt("143");
			MimeMessage message = new MimeMessage(mailSession);
			//message.setSubject("Holi Celebrations 2018 @Ireo Skyon");
			message.setSubject("Holi Celebrations 2018 @Ireo City");
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(IREO_CITY, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			//String filename = "C:\\Users\\User\\Desktop\\HOLI_celebration.png";
			String filename = "C:\\Users\\User\\Desktop\\HOLI_IREOCITY.jpg";
			
			DataSource source = new FileDataSource(filename);
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);



			Address[] from = InternetAddress.parse(useraccount);
			message.addFrom(from);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(TO));	 
			//message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(CC));
			message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(BCC));

			transport.connect("smtpout.asia.secureserver.net", Integer.parseInt("80"), useraccount, userpassword);
			transport.sendMessage(message, message.getAllRecipients());
			Store store = mailSession.getStore("imap");

			System.err.println("store is opening");
			store.connect("imap.secureserver.net".trim(),imapPort,useraccount.trim(),userpassword.trim());
			System.err.println("connected");
			Message [] messages = new Message[1];
			messages[0] = message;

			Folder folder = store.getFolder("Sent Items");
			folder.open(Folder.READ_WRITE);   System.err.println("open the Folder");
			folder.appendMessages(messages);  System.err.println("message sent to Sent Items Folder");
			folder.close(false);
			store.close();
			transport.close();

			System.err.println("Mail Sent Successfully.........");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	//}
/*====================================================ENDS===================================================================*/
/*
		String mails ="sachin.hs@bcits.in,praveen.kumar@bcits.in,praveen.shanukumar@gmail.com";
		String[] AllMails = mails.split(",");
		for(int i=0;i<AllMails.length;i++){
			System.out.println((i+1)+". "+AllMails[i]);

			String TO=AllMails[i];
			String CC="";
			String BCC="";

			System.err.println("------------------------sending Email to "+TO+"-----------------------");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				@Override
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
							"bcitsiworknotification@gmail.com", "Bcits@123");
				}
			});

			Message message = new MimeMessage(session);	        
			message.setFrom(new InternetAddress("NityaMohan"));	       
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(TO));	 
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(CC));
			message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(BCC));	
			message.setSubject("Holi Celebrations - Ireo Skyon");	       

			String signature ="<tr><td>------------------------- Send Through java application <b>Praveen Kumar | Bcits Pvt Ltd | Bangalore</b> -------------------------</td></tr><tr><td>&nbsp;</td></tr>";
			//String signature ="";

			String msg =      "<html><head></head><body><table>"+signature+"<tr>"
					+ "<td>Dear Skyon Residents</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>"
					+ "<tr><td>Our warmest greetings to you and your family this Holi. May this Holi  be the start of your successful and fulfilling life at Skyon!</td>"
					+ "</tr><tr><td>Join us on Friday, March 2nd 2018 from 10:00am at Skyon, as we celebrate the Festival of Holi with your loved ones and enjoy the festivities</td>"
					+ "</tr><tr><td>and spend moments of happiness with your family and friends- Come, Celebrate the festival of colors, Get drenched with different hues,</td>"
					+ "</tr><tr><td>Share the love and fun this festival of Holi at Ireo Skyon!</td>"
					+ "</tr><tr><td>&nbsp;</td>"
					+ "</tr><tr><td><img src=\"cid:image\" style='width: 100%;'></td>"
					+ "</tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Nitya Mohan</b></td>"
					+ "</tr><tr><td><b>Assistant Vice President|Facility Management|Head Resident Services</b></td>"
					+ "</tr><tr><td><b>Mobile  : (91) 9560866440; Tel: (91-124) 4795000; 4795004 (Board), 4795846/ 5847 (Direct)</td>"
					+ "</tr><tr><td><b>Address : Ireo Campus, Archview Drive, Ireo City, Golf Course Extension Road, Gurgaon – 122101, Haryana</b></td>"
					+ "</tr><tr><td><b>Website : www.ireoworld.com</b></td>"
					+ "</tr><tr><td><b></b></td>"
					+ "</tr></table></body></html>";

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(msg, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			String filename = "C:\\Users\\User\\Desktop\\HOLI_celebration.png"; 
			DataSource source = new FileDataSource(filename);
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email sent to "+TO+" successfully....");
		}	*/
	}
	
	
	
	
	
	
	   
		   /*String date="Wed Nov 30 00:00:00 IST 2017";
		   SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		   Date from = (Date)sdf.parse(date);
		   Calendar cal = Calendar.getInstance();
		   cal.setTime(from);
		   String formDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
		   System.out.println("from : " + formDate);*/
		   
		   
		   /*
		   	  // age calculation by using logic in java
		         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		         Date birthDate = sdf.parse("11/11/1993"); //Yeh !! It's my date of birth :-)
		         Age age = calculateAge(birthDate);
		         //My age is
		         System.out.println(age);			*/
   
	
   private static Age calculateAge(Date birthDate){
      int years = 0;
      int months = 0;
      int days = 0;
      //create calendar object for birth day
      Calendar birthDay = Calendar.getInstance();
      birthDay.setTimeInMillis(birthDate.getTime());
      //create calendar object for current day
      long currentTime = System.currentTimeMillis();
      Calendar now = Calendar.getInstance();
      now.setTimeInMillis(currentTime);
      //Get difference between years
      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
      int currMonth = now.get(Calendar.MONTH) + 1;
      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
      //Get difference between months
      months = currMonth - birthMonth;
      //if month difference is in negative then reduce years by one and calculate the number of months.
      if (months < 0){
         years--;
         months = 12 - birthMonth + currMonth;
         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            months--;
      }else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)){
         years--;
         months = 11;
      }
      //Calculate the days
      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)){
         int today = now.get(Calendar.DAY_OF_MONTH);
         now.add(Calendar.MONTH, -1);
         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
      }else {
         days = 0;
         if (months == 12){
            years++;
            months = 0;
         }
      }
      //Create new Age object
      return new Age(days, months, years);
   }
}

class Age{
   private int days;
   private int months;
   private int years;
   private Age(){
      //Prevent default constructor
   }
   public Age(int days, int months, int years){
      this.days = days;
      this.months = months;
      this.years = years;
   }
   public int getDays(){
      return this.days;
   }
   public int getMonths(){
      return this.months;
   }
   public int getYears(){
      return this.years;
   }
   @Override
   public String toString(){
      return years + " Years, " + months + " Months, " + days + " Days";
   }
}