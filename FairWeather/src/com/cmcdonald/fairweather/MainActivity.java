/*
 * Crystal McDonald
 * Java 1 1302
 * Week 4
 * used tutorial www.mybringback.com Android Series
 */
package com.cmcdonald.fairweather;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import com.cmcdonald.lib.FileStuff;
import com.cmcdonald.lib.WebStuff;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	static final String baseURL = "http://query.yahooapis.com/v1/public/yql?q=%20SELECT%20*%20FROM%20weather.forecast%20WHERE%20location%3D"; 
	//static final String baseURL="http://www.google.com/ig/api?weather=";
	URL finalURL;{
	try{
		finalURL = new URL(baseURL);
		Log.i("my url:", baseURL);
		TempRequest tr = new TempRequest();
		tr.execute(finalURL);
		
	} catch (MalformedURLException e){
		Log.e("BAD URL", "MALFORMED URL");
		finalURL = null;
	}
	}	
	 TextView tv;
	 EditText city, state, zip;
	 Boolean _connected = false;

		

	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle icicle) {
           super.onCreate(icicle);

           setContentView(R.layout.weather);
           Button b = (Button)findViewById(R.id.bWeather);
           
           tv = (TextView)findViewById(R.id.tvWeather);
           city = (EditText)findViewById(R.id.etCity);
           state = (EditText)findViewById(R.id.etState);
           zip = (EditText)findViewById(R.id.etZip);
           b.setOnClickListener(this);
  
   }

   // get text from edit view and set to string in textview
	public void onClick(View v) {
 		
		// TODO Auto-generated method stub
		String c = city.getText().toString();
		String s = state.getText().toString();
		String z = zip.getText().toString();
		
		StringBuilder URL = new StringBuilder(baseURL);
		//URL.append(c + "," + s );
//		URL.append(z +"%22&format=json&callback");
		URL.append('"' + z +'"' );
		String fullUrl = URL.toString();
		try{
			URL website = new URL(fullUrl);
			//getting xmlreader to parse data
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			HandlingXMLStuff getInfo = new HandlingXMLStuff();
			xr.setContentHandler(getInfo);
			xr.parse(new InputSource(website.openStream()));
			String information = getInfo.getInformation();
			tv.setText(information);
			//dummy data error catch
		}catch (Exception e){
			tv.setText("The weather is absolutely Perfect! Relax and enjoy life." + "\r\n" +  "\r\n" +
						"Forecast:   "+ "60¼f" +"\r\n"	+
						"Zip:    " + z + "\r\n"	+
						"City:   " + c + "\r\n"+
						"State:   " + s + "\r\n"	
						
						
			);
		}
//		 //Detects the network connection
// 		_connected = WebStuff.getConnectionStatus(tv);
// 		if(_connected){
// 			Log.i("NETWORK CONNECTION ", WebStuff.getConnnectionType(tv));
// 		}
	}
	
	//create method to get history from Hard drive
    @SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
    	Context _context = null;
		Object stored = FileStuff.readObjectFile(_context, "history", false);
    	
    	HashMap<String, String> history;
    	if(stored == null){
    		Log.i("HISTORY", "NO HISTORY FILE FOUND");
    		history = new HashMap<String, String>();
    	}else{
    		history = (HashMap<String, String>)stored;
    	}
    	return history;
    }
    
    private class TempRequest extends AsyncTask<URL,Void,String>{
    	//override 2 separate functions
    	@Override
    	protected String doInBackground(URL...urls){
    		String response = "";
    		//pass an array even though it only holds one
    		for(URL url: urls){
    			response = WebStuff.getURLSTringResponse(url);
    		}
    		return response;
    	}
		
    }
}

