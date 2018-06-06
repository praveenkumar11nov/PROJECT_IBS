package com.bcits.bfm.restTemplate;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Client{  
	
	
	@Value("${Unique.Rest.Uid}")
	private String userId;
	
	@Value("${Unique.Rest.Password}")
	private String password;
	
	@Value("${Unique.Rest.port}")
	private String port;
	
	@Value("${Unique.Rest.Ip}")
	private String host;
	
	@Value("${Unique.Activiti.url}")
	private String serviceURL;
	
	

	public Client() {

	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}

	public String getServiceURL() {
		return serviceURL;
	}


	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}


	

	



}
