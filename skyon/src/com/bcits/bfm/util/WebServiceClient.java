package com.bcits.bfm.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceClient {

	public static void main(String[] args) {
		
		try {
			//http://59.144.167.68:9100/ireoservice.svc

			URL url = new URL("https://pguat.paytm.com/oltp/HANDLER_INTERNAL/getTxnStatus?JsonData={%22MID%22:%22klbGlV59135347348753%22,%22ORDERID%22:%22ORDER48886809916%22,%22CHECKSUMHASH%22:%22wctrePBbNfJkGyNXRg8sHBTJZWGJEFMBuUtO3qoFkL2ox8HIe9YSzTU%2FpswpDbtAhS%2bkWiHgr5nmu9z9cn8rbzGsV0qy8D16c2negimoD%2Fs%3D%22}");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		
				
		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		}
		
	}

