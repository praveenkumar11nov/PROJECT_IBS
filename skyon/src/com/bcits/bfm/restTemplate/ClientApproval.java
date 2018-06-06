package com.bcits.bfm.restTemplate;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;



@Component
public class ClientApproval {
	private static final Log logger = LogFactory.getLog(ClientApproval.class);
	
	
	@Autowired
	 Client client;

	
	@SuppressWarnings("unchecked")
	@JsonIgnore
	public  void createApprovalProcess(String url,String processKey, NameValuePair[] pair,String taskIdUrl,Blob image){
		
		String urId = client.getUserId();
		String pwd = client.getPassword();
		String host = client.getHost();
		String port = client.getPort();
		String serviceURL=client.getServiceURL();
		String getTaskUrl="";
		String passTaskIdUrl="";
		//byte[] immAsBytes=null;
		ResponseEntity<String> responseEntity=null;
		ResponseEntity<String> response1=null;

		try{

			HttpHeaders header=new ClientApproval().getHeadersForAuthentication(urId,pwd);
			System.out.println("Headers from authentication "+header.getContentType());
			
			RestTemplate restTemplate =new ClientApproval().getRestTemplet();
			String response =new ClientApproval().getAuthentication(urId,pwd,host,port,serviceURL).getBody();
    		logger.info("Authentication Successfull..."+response);
    		
    		if (response.contains("success")){
    			logger.info("Starting Approval process");
    			Map<String, Object> bodyMap = new HashMap<String, Object>();
    			bodyMap.put("processDefinitionKey", processKey);
    			bodyMap.put("variables", pair);
    	
    			HttpEntity<Map<String, Object>> requestEntity2 = new HttpEntity<Map<String, Object>>(bodyMap,header);
    			restTemplate.exchange(url, HttpMethod.POST, requestEntity2, String.class);
    			
    			ResponseEntity<String>  getTaskId=new ClientApproval().getTasksId(taskIdUrl,urId,pwd,host,port,serviceURL);
    			getTaskUrl=getTaskId.getBody();
    			logger.info("***********************"+getTaskUrl);
    			JSONObject jsonObject=new JSONObject(getTaskUrl);
    			JSONArray jsonData=jsonObject.getJSONArray("data");
    			String taskInstanceId=null;
    			int n=jsonData.length();
    			for (int i = 0; i <n; i++) {
    				JSONObject taskId = jsonData.getJSONObject(i);
					taskInstanceId=taskId.getString("id");
				}
    			logger.info("***********************"+taskInstanceId);
    			passTaskIdUrl="http://"+ host.trim()+":"+ port.trim()+"/"+ serviceURL.trim()+"/runtime/tasks"+"/"+taskInstanceId+"/attachments";
    			/*int blobLength = (int) image.length();  
    			logger.info("**********Length Of the image*************"+blobLength);
    			byte[] blobAsBytes = image.getBytes(1, blobLength);
    			logger.info("**********byte*************"+blobAsBytes);
    			InputStream binaryContent = new ByteArrayInputStream(blobAsBytes);
    			String imageString=new String(blobAsBytes);
    			
    			File attachFile = new File("C:/Electricity Rate Master.png");

    			MultiValueMap<String, Object> bodyMap2 = new LinkedMultiValueMap<String, Object>();
    			header.add("Content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);*/
    			Map<String, Object> bodyMap2 = new HashMap<String, Object>();
    			
    			bodyMap2.put("name","Binary");
    			bodyMap2.put("description", "Binary");
    			bodyMap2.put("type", "simpleType");
    			//bodyMap2.put("file",attachFile);
    			
    			//String fileName="c:/Electricity Rate Master.png";
    			
    		/*	Map<String, Object> imageMap = new HashMap<String, Object>();
    			
    			imageMap.put("value",new HttpEntity(binaryContent,bodyMap2));*/
    			
    		//	header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    			//header.setContentType(MediaType.MULTIPART_FORM_DATA);
    		
    			HttpEntity<Map<String, Object>> requestEntity3 = new HttpEntity<Map<String, Object>>(bodyMap2,header);
    			//RestTemplate rest2=new RestTemplate();
    			//rest2.getMessageConverters().add(new FormHttpMessageConverter());
    			response1=restTemplate.exchange(passTaskIdUrl,HttpMethod.POST,requestEntity3,String.class);
    			
    			logger.info("Return the value..."+response1.getBody());
    			
    			}
    		logger.info("Successfully processed Activation process...");
    	}catch(Exception e){
    		logger.info("server down...!internal status error!!!"+e.getMessage());
    		e.printStackTrace();
    		
    	}
	}
	
public void approvalProcess(String url,String processKey, NameValuePair[] pair){
		
		String urId = client.getUserId();
		String pwd = client.getPassword();
		String host = client.getHost();
		String port = client.getPort();
		String serviceURL=client.getServiceURL();
		

		try{

			HttpHeaders header=new ClientApproval().getHeadersForAuthentication(urId,pwd);
			System.out.println("Headers from authentication "+header.getContentType());
			
			RestTemplate restTemplate =new ClientApproval().getRestTemplet();
			String response =new ClientApproval().getAuthentication(urId,pwd,host,port,serviceURL).getBody();
    		logger.info("Authentication Successfull..."+response);
    		
    		if (response.contains("success")){
    			logger.info("Starting Approval process");
    			Map<String, Object> bodyMap = new HashMap<String, Object>();
    			bodyMap.put("processDefinitionKey", processKey);
    			bodyMap.put("variables", pair);
    	
    			HttpEntity<Map<String, Object>> requestEntity2 = new HttpEntity<Map<String, Object>>(bodyMap,header);
    			restTemplate.exchange(url, HttpMethod.POST, requestEntity2, String.class);
    			
    			logger.info("Successfully processed Activation process...");
			}
    		
    	}catch(Exception e){
    		logger.info("server down...!internal status error!!!"+e.getMessage());
    		e.printStackTrace();
    		
    	}
	}
	public HttpHeaders getHeadersForAuthentication(String UserId,String Password){
		String userpwd = UserId +":"+ Password;
		String userpassword = new String(Base64.encodeBase64(userpwd.getBytes(Charset.forName("US-ASCII"))));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + userpassword);
		headers.setAccept(Collections.singletonList(new MediaType("application","json")));
		headers.setContentType(new MediaType("application","json"));
		
		return headers;
	}
	
	public RestTemplate getRestTemplet(){
		RestTemplate restTemplate=new RestTemplate() ;
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new SourceHttpMessageConverter());
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		return restTemplate;
	}
	
	
	public ResponseEntity<String> getAuthentication(String UserId,String Password,String host,String port,String serviceURL){
		
		ResponseEntity<String> responseEntity=null;
		
		String loginUrl="http://"+ host.trim()+":"+ port.trim()+"/"+ serviceURL.trim()+"/login";
	
		
		Attendant attendent = new Attendant();
		attendent.setUserId(UserId);
		attendent.setPassword(Password);
		
		HttpHeaders header=new ClientApproval().getHeadersForAuthentication(UserId,Password);
		RestTemplate restTemplate = new ClientApproval().getRestTemplet();
		
		HttpEntity<Attendant> requestEntity = new HttpEntity<Attendant>(attendent,header);
		try{
			logger.info("Login URL..."+loginUrl);
    		responseEntity = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);
    		
    		return responseEntity;
		}catch(Exception e){
			e.printStackTrace();
		}
		return responseEntity;
	}
	
	
	public ResponseEntity<String>  getUserById(String userDetailsURl){
		ResponseEntity<String> responseEntity=null;
		String urId = client.getUserId();
		String pwd = client.getPassword();
		String host = client.getHost();
		String port = client.getPort();
		String serviceURL=client.getServiceURL();
		try{

			HttpHeaders header=new ClientApproval().getHeadersForAuthentication(urId,pwd);
			System.out.println("Headers from authentication "+header.getContentType());
			
			RestTemplate restTemplate =new ClientApproval().getRestTemplet();
			String response =new ClientApproval().getAuthentication(urId,pwd,host,port,serviceURL).getBody();
    		logger.info("Authentication Successfull..."+response);
    		
    		if (response.contains("success")){
    			logger.info("Starting Approval process");
    		
    			HttpEntity<Map<String, Object>> requestEntity2 = new HttpEntity<Map<String, Object>>(header);
    			responseEntity=restTemplate.exchange(userDetailsURl, HttpMethod.GET, requestEntity2, String.class);
    			
    			logger.info("Successfully processed Activation process...");
    			return responseEntity;
			}
    	}catch(Exception e){
    		logger.info("server down...!internal status error!!!"+e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		
		return responseEntity;
	}
	public ResponseEntity<String>  getEstateManagerMail(String estateManagerDetails){
		ResponseEntity<String> responseEntity=null;
		String urId = client.getUserId();
		String pwd = client.getPassword();
		String host = client.getHost();
		String port = client.getPort();
		String serviceURL=client.getServiceURL();
		try{

			HttpHeaders header=new ClientApproval().getHeadersForAuthentication(urId,pwd);
			System.out.println("Headers from authentication "+header.getContentType());
			
			RestTemplate restTemplate =new ClientApproval().getRestTemplet();
			String response =new ClientApproval().getAuthentication(urId,pwd,host,port,serviceURL).getBody();
    		logger.info("Authentication Successfull..."+response);
    		
    		if (response.contains("success")){
    			logger.info("Starting Approval process");
    		
    			HttpEntity<Map<String, Object>> requestEntity31 = new HttpEntity<Map<String, Object>>(header);
    			responseEntity=restTemplate.exchange(estateManagerDetails, HttpMethod.GET, requestEntity31, String.class);
    			
    			logger.info("Successfully processed Activation process...");
    			return responseEntity;
			}
    	}catch(Exception e){
    		logger.info("server down...!internal status error!!!"+e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		
		return responseEntity;
	}
	public ResponseEntity<String> getExecutiveManagerMail(
			String executiveDetails) {
		// TODO Auto-generated method stub
		ResponseEntity<String> responseEntity=null;
		String urId = client.getUserId();
		String pwd = client.getPassword();
		String host = client.getHost();
		String port = client.getPort();
		String serviceURL=client.getServiceURL();
		try{

			HttpHeaders header=new ClientApproval().getHeadersForAuthentication(urId,pwd);
			System.out.println("Headers from authentication "+header.getContentType());
			
			RestTemplate restTemplate =new ClientApproval().getRestTemplet();
			String response =new ClientApproval().getAuthentication(urId,pwd,host,port,serviceURL).getBody();
    		logger.info("Authentication Successfull..."+response);
    		
    		if (response.contains("success")){
    			logger.info("Starting Approval process");
    		
    			HttpEntity<Map<String, Object>> requestEntity2 = new HttpEntity<Map<String, Object>>(header);
    			responseEntity=restTemplate.exchange(executiveDetails, HttpMethod.GET, requestEntity2, String.class);
    			
    			logger.info("Successfully processed Activation process...");
    			return responseEntity;
			}
    	}catch(Exception e){
    		logger.info("server down...!internal status error!!!"+e.getMessage());
    		e.printStackTrace();
    		
    	}
		return responseEntity;
	}
	public ResponseEntity<String> getTasksId(String taskIdUrl,String urId,String pwd,String host,String port,String serviceURL) {
		ResponseEntity<String> responseEntity=null;
	
		try{

			HttpHeaders header=new ClientApproval().getHeadersForAuthentication(urId,pwd);
			System.out.println("Headers from authentication "+header.getContentType());
			
			RestTemplate restTemplate =new ClientApproval().getRestTemplet();
			String response =new ClientApproval().getAuthentication(urId,pwd,host,port,serviceURL).getBody();
    		logger.info("Authentication Successfull..."+response);
    		
    		if (response.contains("success")){
    			logger.info("Starting Approval process");
    		
    			HttpEntity<Map<String, Object>> requestEntity2 = new HttpEntity<Map<String, Object>>(header);
    			responseEntity=restTemplate.exchange(taskIdUrl, HttpMethod.GET, requestEntity2, String.class);
    			
    			logger.info("Successfully processed Activation process...");
    			return responseEntity;
			}
    	}catch(Exception e){
    		logger.info("server down...!internal status error!!!"+e.getMessage());
    		e.printStackTrace();
    		
    	}
		return responseEntity;
	}

	
	
	
}
