import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.tempuri.IREOService;
import org.xml.sax.InputSource;

import com.bcits.bfm.model.DailyData;
import com.bcits.bfm.model.DocumentElementData;

public class IreoWeb {
public static void main(String[] args) throws ParseException {
	  JAXBContext jaxbContext;
	     
				try {
					long d1=81;
					IREOService s1=new IREOService();
					
					String data	=s1.getBasicHttpBindingIService1().dailyData("1", "27/04/2017", "harjeet", "ireo@123", d1,"6001069","31/03/2017","14/06/2017");
				    System.out.println(data);
					jaxbContext = JAXBContext.newInstance(DocumentElementData.class);
					  Unmarshaller jaxUnmarshaller = jaxbContext.createUnmarshaller();
				      InputSource inputSource = new InputSource(new ByteArrayInputStream(data.getBytes()));
				       inputSource.setEncoding("UTF-8");
				      DocumentElementData tallyResponse = (DocumentElementData) jaxUnmarshaller.unmarshal(inputSource); 
				       System.out.println(tallyResponse);
				        List<DailyData> list=tallyResponse.getInstantDatas();
				        System.out.println(list);
				}catch(Exception e){
					e.printStackTrace();
				}
	
}
}
