/*
 * Crystal McDonald
 * Java1 1302
 * Week4
 * for this portion I followed Internal storage tutorial www.mybringback.com/  the android 4.0 series
 */
package com.cmcdonald.fairweather;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InternalStore extends Activity implements OnClickListener {

	Button save;
	EditText favLocation,faveZip;
	String FAVSTATE, FAVZIP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		save = (Button) findViewById(R.id.button1);
		save.setOnClickListener(this);
		faveZip = (EditText)findViewById(R.id.zipText);
		favLocation = (EditText)findViewById(R.id.locText);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FAVSTATE = favLocation.getText().toString();
		if(FAVSTATE.contentEquals("")){
			FAVSTATE = "NO LOCATION FAVORITE";
		}
		FAVZIP = faveZip.getText().toString();
		if(FAVZIP.contentEquals("")){
			FAVZIP = "NO ZIP FAVORITE";
			
			//saving internal storage 
			//stores on device and within app
			
			//save info to output to be saved
			try {
				FileOutputStream fos = openFileOutput(FAVSTATE, Context.MODE_PRIVATE);
				//info wrote to outputstream
				fos.write(FAVZIP.getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
