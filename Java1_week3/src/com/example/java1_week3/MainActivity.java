/*
 * Crystal McDonald
 * Java 1 1302
 * Week 3
 */
package com.example.java1_week3;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import java.net.URLEncoder;

import com.cmcdonald.lib.FileStuff;
import com.cmcdonald.lib.WebStuff;
import com.example.java1_week3.TempDisplay;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	Context _context;
	LinearLayout _appLayout;
	SearchForm _search; 
	TempDisplay _temperature;
	LocDisplay _locations;
	Boolean _connected = false;
	HashMap<String, String> _history;
	TextView _title;
	TextView _date;
	TextView _temp;
	TextView _text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LinearLayout
        _context = this;
        _appLayout = new LinearLayout(this);
        _history = getHistory();
        Log.i("HISTORY READ",_history.toString());
        
       TextView locLabel = new TextView(this);
        locLabel.setText("Find weather by location. Enter your zip code now.");
        _appLayout.addView(locLabel);
//		 _title = (TextView) findViewById(R.id._title);
//	 _date = (TextView) findViewById(R.id._date);
//	 _temp = (TextView) findViewById(R.id._temp);
//		 _text = (TextView) findViewById(R.id._text;
        
        //add it to view to see it
      		_search = new SearchForm(_context, "Enter Zip Code", "Go");
      		
      		//get buttons and fields
      		//add search handler
      		//building this way using more of a class method instead of tags approach
      		//EditText searchField = _search.getField();
      		Button searchButton = _search.getButton();
      		
      		//Adding functionality to get buttons to do something
      		searchButton.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v){
      				Log.i("CITY ENTERED: ",_search.getField().getText().toString());
      				getTemps(_search.getField().getText().toString());
      			}
      			
      		});
      		
      		
      		//Detects the network connection
      		_connected = WebStuff.getConnectionStatus(_context);
      		if(_connected){
      			Log.i("NETWORK CONNECTION ", WebStuff.getConnnectionType(_context));
      		}
      		
      		//add stock display
      		_temperature = new TempDisplay(_context);
      		
      		//add faves display
      		_locations = new LocDisplay(_context);
      		
      		//add views to main layout
      		//added button to LinearLayout
      		_appLayout.addView(_search);
      		_appLayout.addView(_temperature);
      		_appLayout.addView(_locations);
      		
      		
      		//to display under the search bar
      		_appLayout.setOrientation(LinearLayout.VERTICAL);
        
        setContentView(_appLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
//    private void getTemps(String symbol){
//    	Log.i("CLICK", symbol);
//    	String baseURL = "http://weather.yahooapis.com/forecastrss?p=95758";
//    	String yql = "select * from csv where url in ('http://weather.yahooapis.com/forecastrss?p=NLXX0002', 'http://weather.yahooapis.com/forecastrss?p=NLXX0....', ....)";
//    	String qs;
//    	try{
//    		qs = URLEncoder.encode(yql, "UTF-8");
//    	} catch (Exception e){
//    		Log.e("BAD URL", "ENCODING PROBLEM");
//    		qs = "";
//    	}
 	
//        	String baseURL = "weather.yahooapis.com/forecastrss?w=2459115&d=1(" + symbol + ")";
        	
        	
        	//getting the temp.
    	//create a final url to pass to it
//    	URL finalURL;
//    	try{
//    		finalURL = new URL(baseURL + "?q=" + qs + "&format=json");
//    		//call temps in Async Tasks
//    		TempRequest tr = new TempRequest();
//    		tr.execute(finalURL);
//    		
//    	} catch (MalformedURLException e){
//    		Log.e("BAD URL", "MALFORMED URL");
//    		finalURL = null;
  //Create my custom API URL
    private void getTemps(String zip){
    	Log.i("CLICK",zip);
    	//JSON output.  Weather by zip.
    	String baseURL = "http://query.yahooapis.com/v1/public/yql?q=%20SELECT%20*%20FROM%20weather.forecast%20WHERE%20location%3D%22" + zip + "%22&format=json&callback";
    	//http://weather.yahooapis.com/forecastrss?p=" + "95758"   is hard coded for Elk Grove only. http://weather.yahooapis.com/forecastrss?p=" + zip; outputs xml version. 
    
    	URL finalURL;
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
    
    //create method to get history from Hard drive
    @SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
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
    	
    	@Override
    	protected void onPostExecute(String result){
    		Log.d("URL RESPONSE",result);
    		
    		try{
    		//parsing through JSON Data   accepts a string as a parameter
    		JSONObject json = new JSONObject(result);
    		JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel");
    		if(results.getString("ttl").compareTo("Yahoo! Weather Error")==0){
    			Toast toast = Toast.makeText(_context, "Invalid Zip ", Toast.LENGTH_LONG);
    			toast.show();
    		}else{
    			Toast toast = Toast.makeText(_context, "Valid Zip" + results.get("title"), Toast.LENGTH_LONG);
    			toast.show();
    			
    			//makes sure history is there
    			_history.put(results.getString("string"), results.toString());
    			//target file to write history to harddrive
    			FileStuff.storeObjectFile(_context, "history", _history, false);
    			FileStuff.storeStringFile(_context, "temp", results.toString(), true);
    		}
    		} catch (JSONException e){
    			Log.e("JSON", "JSON OBJECT EXCEPTION " + e.toString());
    		}
    	}
    	
    }
//    		try{		
//    			JSONObject json = new JSONObject(result); 
//    			JSONObject productObject = json.getJSONObject("results");
//    			JSONArray list = productObject.getJSONArray("channel");
//    			
//    			JSONObject listObjs = list.getJSONObject(0);
//
//
//					JSONObject titleObj = listObjs.getJSONObject("item");
//	    			String wloc = titleObj.getString("title");
//	    			Log.i("ITEM", wloc.toString());
//	    			
//	    			JSONObject conditionObj = listObjs.getJSONObject("item").getJSONObject("condition");
//	    			String wdate = conditionObj.getString("date");
//	    			String fcast = conditionObj.getString("temp");
//	    			String wtext = conditionObj.getString("text");
//	    			Log.i("CONDITION", wdate.toString() + fcast.toString() + wtext.toString());
//	    			
//	    			String id = list.getJSONObject(0).getString("title");
//	    			Log.i("ID", id.toString());
//					String name = list.getJSONObject(0).getString("date");
//					String type = list.getJSONObject(0).getString("temp");
//					String price = list.getJSONObject(0).getString("text");
//					
//					_title.setText(title);
//					_date.setText(date);
//					_temp.setText(temp);
//					_text.setText(text);
					
//					Log.i("JSON RESULTS",name.toString());
//			Log.i("JSON RESULTS",list.toString());
//				
//			}catch (JSONException e){
//				Log.e("JSON","JSON OBJECT EXCEPTION"+ e.toString());
//				
//			}
//    		
//    	}
//		
//	}
}
