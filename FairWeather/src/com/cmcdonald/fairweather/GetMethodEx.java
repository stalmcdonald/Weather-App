package com.cmcdonald.fairweather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetMethodEx {

	//referencing the internet
	public String getInternetData() throws Exception{
		//allows us to read info
		BufferedReader in = null;
		String data = null;
		try{
			HttpClient client = new DefaultHttpClient();
			
			//website referring to to process info
			URI website = new URI("http://www.devboxpro.com");
			//get info from web
			HttpGet request = new HttpGet();
			request.setURI(website);
			//response executes client
			HttpResponse response = client.execute(request);
			//convert info into a string
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			//check out bucky's java series
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.seperator");
			while ((l = in.readLine()) != null){
				sb.append(l + nl);
			}
			in.close();
			data = sb.toString();
			return data;
		}finally{
			if (in != null){
				try{
					in.close();
					return data;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
