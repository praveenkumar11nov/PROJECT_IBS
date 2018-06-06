package com.bcits.bfm.util;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.nio.charset.Charset;
 

public class CrunchifyRESTJerseyNetURLClient {

	public static void main(String[] args) {
		System.out.println("\n============Output:============ \n" + callURL("http://59.144.167.68:9100/IREOService.svc?wsdl/"));
		}
		 
		public static String callURL(String myURL) {
		System.out.println("Requested URL: " + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		 
		try {
		URL url = new URL(myURL);
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		//urlConn = url.openConnection();
		if (httpcon != null)
			httpcon.setReadTimeout(60 * 1000);
		if (httpcon != null && httpcon.getInputStream() != null) {
		in = new InputStreamReader(httpcon.getInputStream(), Charset.defaultCharset());
		BufferedReader bufferedReader = new BufferedReader(in);
		if (bufferedReader != null) {
		int cp;
		while ((cp = bufferedReader.read()) != -1) {
		sb.append((char) cp);
		}
		bufferedReader.close();
		}
		}
		in.close();
		} catch (Exception e) {
			e.printStackTrace();
		throw new RuntimeException("Exception while calling URL:" + myURL, e);
		}
		 
		return sb.toString();
		}
	
}
