package com.bcits.bfm.view;

import java.io.*;
import java.net.*;

public class Socket_client {
	 public static void main(String[] args) throws IOException 
	    {
	        Socket s = new Socket();
	    String host = "122.166.217.53";
	    PrintWriter s_out = null;
	    BufferedReader s_in = null;
	         
	        try
	        {
	        s.connect(new InetSocketAddress(host , 9999));
	        System.out.println("Connected");
	             
	        //writer for socket
	            s_out = new PrintWriter( s.getOutputStream(), true);
	            //reader for socket
	            s_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	        }
	         
	        //Host not found
	        catch (UnknownHostException e) 
	        {
	            System.err.println("Don't know about host : " + host);
	            System.exit(1);
	        }
	         
	        //Send message to server  "GET /beer/ HTTP/1.0";
	    String message = "GET / HTTP/1.1";
	    s_out.println( message );
	             
	    System.out.println("Message send:::::::::::::::::");
	         
	    //Get response from server
	    String response;
	    
	    System.out.println("response length:::::"+s_in.readLine().length());
	    while ((response = s_in.readLine()) != null) 
	    {
	        System.out.println( response );
	    }
	    }
}
